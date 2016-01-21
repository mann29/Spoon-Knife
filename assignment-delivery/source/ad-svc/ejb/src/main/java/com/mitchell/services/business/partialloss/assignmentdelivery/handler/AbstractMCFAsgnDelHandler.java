package com.mitchell.services.business.partialloss.assignmentdelivery.handler;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;

import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoType;
import com.mitchell.schemas.appraisalassignment.AssignmentDetailsType;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.EclaimsInBoxServiceClient;
import com.mitchell.services.business.partialloss.eclaiminboxsvc.ECInboxSvcException;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.services.core.mcfsvc.AttachmentObject;
import com.mitchell.services.core.mcfsvc.MCFBuildRequest;
import com.mitchell.services.core.mcfsvc.ejb.MCFServiceEJBRemote;
import com.mitchell.services.core.mcfsvc.exception.MCFServiceException;
import com.mitchell.services.core.mcfsvc.utils.MCFServiceConstants;
import com.mitchell.services.core.mum.types.mie.AD04Type;
import com.mitchell.services.core.mum.types.mie.AD34Type;
import com.mitchell.services.core.mum.types.mie.EstimateType;
import com.mitchell.services.core.mum.types.mie.MieDocument;
import com.mitchell.services.core.mum.types.mie.MieMum;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;

/**
 * @author <a href="mailto://prashant.khanwale@mitchell.com"> Prashant sadashiv
 *         Khanwale </a> Created/Modified on Oct 5, 2010
 */
/**
 * @author <a href="mailto://prashant.khanwale@mitchell.com"> Prashant sadashiv
 *         Khanwale </a> Created/Modified on Oct 15, 2010
 */
public abstract class AbstractMCFAsgnDelHandler extends
    AbstractAssignmentDeliveryHandler implements AssignmentDeliveryHandler
{

  private static final boolean DO_NOT_RETURN_EXCEPTION = false;
  protected static final int NOT_ARCHIVED_YET = 0;
  protected final String SHOP_SUP_REQ_NOTIFICATION_SENT_SUCCESS_APPLOG_TRNS_TYPE_FILE = "106803";
  protected final String ECLAIM_CANCELLATION_ALERT_SENT_SUCCESS_APPLOG_TRNS_TYPE_FILE = "106802";
  protected EclaimsInBoxServiceClient eclaimsInBoxServiceClient;
  protected MCFServiceEJBRemote mcfServiceEJBRemote;
  private static final String CLASS_NAME = AbstractMCFAsgnDelHandler.class.getName();

  private long archiveMcfFile(final String workItemId,
      final UserInfoDocument userInfo, final File mcfFile)
  {
    long archivalId;
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("\n\n*****Debug deliverAssignment: Now archiving the MCF file: "
              + mcfFile.getAbsoluteFile());
    }

    // Archieve MCF File
    archivalId = handlerUtils.archieveAssignment(userInfo, mcfFile, workItemId);

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("Successfully archived MCF file, Archive ID = " + archivalId);
    }

    return archivalId;
  }

  private File attachDispatchReportIfRequired(
      final AssignmentServiceContext context, final int type)
      throws AssignmentDeliveryException, Exception
  {
    File dsFile = null;
    final boolean isDispatchReportRequired = isTrue(assignmentDeliveryUtils
        .getUserCustomSetting(
            context.getUserInfo(),
            AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_DISPATCH_RP_FLAG));
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("*****AbstractMCFAsgnDelHandler:: Debug deliverAssignment: isDispatchReportRequired= ["
              + isDispatchReportRequired + "] ");
      mLogger
          .info("*****AbstractMCFAsgnDelHandler:: Debug deliverAssignment: context.getUserInfo()\n"
              + context.getUserInfo().toString());
    }
    if (isDispatchReportRequired) {
      dsFile = drBuilder.createDispatchReport(context);
      final AttachmentObject attachmentObject2 = new AttachmentObject(dsFile,
          type);
      attachmentObject2.setTabNumber(1);
      context.getAttachmentObjects().add(attachmentObject2);
    }
    return dsFile;
  }

  private File attachMieFileAsFirstAttachment(
      final AssignmentServiceContext context)
      throws Exception
  {
    File mieFile;
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("********Debug:  Have Original Assignment. ");
    }

    // Transform BMS document to a Mie document
    mieFile = converter.transformBmsAsgToMieAsg(context);

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("Successfully converted BMSAssignment document to MIE file = "
              + mieFile.getAbsoluteFile());
    }

    // Add the MIE into attachment objects list
    final AttachmentObject attachmentObject1 = new AttachmentObject(mieFile,
        AttachmentObject.OBJ_TYPE_MIE);
    // AttachmentObject attachmentObject1 = new AttachmentObject(mieFile,
    // 97);
    // CG: Updated for SIP2.
    attachmentObject1.setTabNumber(1);
    context.getAttachmentObjects().add(attachmentObject1);

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("*********** After attach MIE file ");
    }
    return mieFile;
  }

  protected Estimate attachOriginalEstimateAsFirstAttachmentIfPresent(
      final AssignmentServiceContext context, Estimate estimate,
      final AbstractHandlerUtils handlerUtils, final File orgEstMieFile,
      final Long relatedEstimateDocumentID)
      throws MitchellException, AssignmentDeliveryException
  {
    if (orgEstMieFile != null) {

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********Debug:  Adding Original Estimate MIE as MCF attachement. ");
      }

      final AttachmentObject attachmentObject1 = new AttachmentObject(
          orgEstMieFile, AttachmentObject.OBJ_TYPE_MIE);
      attachmentObject1.setTabNumber(1);
      attachmentObject1.setDesc1(getEstimateDescription()); // This is
                                                            // needed so
                                                            // that
      // MCFService recognizes the
      // object as an Estimate
      // MIE.
      // TODO : Look into "just to be safe".
      // ~prashant.khanwale@mitchell.com, Jul 21, 2010
      // attachmentObject1.setDesc2("Estimate"); // This is just to be
      // safe.
      context.getAttachmentObjects().add(attachmentObject1);

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********Debug:  After Adding Original Estimate MIE as MCF attachement. ");
      }

      // Retrieve the Estimate object from CCDB
      estimate = handlerUtils.getEstimateFromCCDB(relatedEstimateDocumentID
          .longValue());
      if (estimate == null) {
        throw new AssignmentDeliveryException(
            AssignmentDeliveryErrorCodes.BMS_MIE_ERROR,
            "EClaimDeliveryHandler", "deliverAssignment",
            "Estimate is null from CCDB. Estimate ID="
                + relatedEstimateDocumentID.longValue());
      }

    }
    return estimate;
  }

  /**
   * 
   * @param context
   * @param workItemId
   * @param handlerUtils
   * @param supplementRequestDocFile
   * @return
   * @throws Exception
   *           <b>NOTE</b> This method's contract should be consistent with
   *           {@link #doesProtocolAcceptAdditionAttachments() protocols
   *           ability to accept additional attachments }. If the protocol
   *           does not support additional attachments, the method should
   *           throw {@link java.lang.UnsupportedOperationException
   *           UnsupportedOperationException}
   */
  protected abstract File attachSupplimentNotificationDocumentTextIfNeeded(
      AssignmentServiceContext context, String workItemId,
      AbstractHandlerUtils handlerUtils, File supplementRequestDocFile)
      throws Exception;

  private File buildMcfFileFromTemplateUsingEstimate(
      final AssignmentServiceContext context, final Estimate estimate,
      final UserInfoDocument userInfo, final CIECADocument ciecaDoc,
      final File templateFile)
      throws MCFServiceException, RemoteException, AssignmentDeliveryException
  {
    File mcfFile;
    // Create MCF file from BMSAssignment, Template, Attachment Objects and
    // UserInfo

    // CG: Updated for SIP2.
    final MCFBuildRequest mcfBuildReq = new MCFBuildRequest(userInfo, ciecaDoc,
        templateFile, context.getAttachmentObjects(),
        MCFServiceConstants.MCF_ASSIGNMENT_VER_34, context.isDrp(),
        context.getDrpUserInfo());

    mcfBuildReq.setWorkAssignmentId(context.getWorkAssignmentId());

    // If we have an estimate then call MCFService passing in the commit
    // date
    if (estimate != null) {
    	
      mcfFile = mcfServiceEJBRemote.buildAssignmentMCF(mcfBuildReq, estimate.getCommitDate());
      
    } else {// Only applicable for original
      if (isMcfDestinationRequired()) {
        mcfBuildReq.setMCFDestinationType(getHandlerType(context));
      }
      mcfFile = mcfServiceEJBRemote.buildAssignmentMCF(mcfBuildReq);
    }

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("*****Debug deliverAssignment: After MCFService - MCFBuildRequest...\n\n");
      mLogger.info("Successfully created MCF File = "
          + mcfFile.getAbsoluteFile());
    }
    return mcfFile;
  }

  public abstract void cancelAssignment(AssignmentServiceContext context)
      throws AssignmentDeliveryException;

  /**
   * 
   * @param attachmentFileList
   * @throws AssignmentDeliveryException
   */
  private void cleanUpAttachementFilesIfPresent(final File[] attachmentFileList)
      throws AssignmentDeliveryException
  {
    for (int i = 0; attachmentFileList != null
        && i != attachmentFileList.length; i++) {
      cleanUpFileIfPresent(attachmentFileList[i], "attachment file " + (i + 1));
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("\nFinished Attachment file cleanup!!\n");
      }
    }
  }

  public void deliverAssignment(final AssignmentServiceContext context)
      throws AssignmentDeliveryException
  {

    final String thisMethod = "deliverAssignment(AssignmentServiceContext context)";
    //mLogger.info("deliverAssignment in AbstractMCFAsgnDelHandler DRP USERINFO:::"+context.getDrpUserInfo());
    
    mLogger.info("deliverAssignment in AbstractMCFAsgnDelHandler  for DRP Allowed:::"+context.isDrp());
    guard(this);// Guard against dependency violation.
    entering(thisMethod);
    Estimate estimate = null;
    validate(context);
    final String workItemId = context.getWorkItemId();
    final UserInfoDocument userInfo = context.getUserInfo();
    
    //mLogger.info("USERINFO in CONTEXT:::"+context.getUserInfo());
    // Remove errlog warning noise for missing ECMgrAppCode, as per Scott B.
    //   warnIfNonEcm(context, thisMethod, workItemId, userInfo);

    // Effectively the default status if all goes well.
    String appLogTxType = ECINBOX_SUBMISSION_SUCCESS_APPLOG_TRNS_TYPE_FILE;

    File mieFile = null;
    File dsFile = null;
    File suppToOrigNoticeFile = null;
    File supplementRequestDocFile = null;
    File[] attachmentFileList = new File[0];
    File orgEstMieFile = null; // Wait till the end to cleanup. Undocumented
                               // communication between this component and
                               // mcfServiceEJBRemote
    long archivalId = NOT_ARCHIVED_YET;

    try {
      final boolean isCrossSupplementAllowed = assignmentDeliveryConfigBridge.isCrossSupplementationAllowed();
      
      mLogger.fine("isCrossSupplementAllowed ::::"+isCrossSupplementAllowed);
      
      final CIECADocument ciecaDoc = extractCiecaDocument(context);
      final AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = findAdditionalAppraisalAssignmentInfoDocument(context);
      rejectDeliveryIfNeitherOriginalNorSuppliment(thisMethod, workItemId,
          ciecaDoc);
      final boolean isOriginal = handlerUtils.isOriginalAssignment(ciecaDoc);
      if ((!isOriginal) && context.isDrp()) {
        // Send notification Email Case - Non-Staff Supplement
        // Assignments
        handleSupplementDrpCase(context);
        appLogTxType = SHOP_SUP_REQ_NOTIFICATION_SENT_SUCCESS_APPLOG_TRNS_TYPE_FILE;
        if(isCrossSupplementAllowed)
        	handleSupplementDRPMCFDelivery(context, aaaInfoDoc, ciecaDoc, userInfo, workItemId, thisMethod);
      } else { // All other cases involve deliverying an MCF Package

        // If original assignment, transform assignment bms to MIE and
        // add assignment MIE as first attached object.
        if (isOriginal) {
          mieFile = attachMieFileAsFirstAttachment(context);
          // Check for case of original converted from an supplement,
          // If SupplementConvertedToOriginalFlag is TRUE, Add
          // additional notice text file as attachement
          //
          // Special Case: Supplement Assignment Converted To Original
          //
          if (wasSupplementConvertedToOriginal(aaaInfoDoc)) {
            if (doesProtocolAcceptAdditionAttachments()) {
              suppToOrigNoticeFile = extractSupplimentToOriginalNoticeFileIfRequired(
                  workItemId, userInfo, handlerUtils);
            } else {
              // TODO somehow signal it in the dispatch report
            }
          }
          // Else, supplement assignment cases.
        } else {
          if (mLogger.isLoggable(Level.FINE)) {
            mLogger.fine("Have Supplement Assignment. ");
          }
          // Staff User -Supplement Case
          if (!context.isDrp()) {
            // Get Original Estimate MIE (by DocumentID) from
            // AdditionalAppraisalAssignmentInfo.xml
            //
            // Staff User - Supplement Assignment Case
            // Get OriginalEstimateDocumentID, if present, need
            // Original Estimate MIE.
            //
            if (aaaInfoDoc != null) {
              final AdditionalAppraisalAssignmentInfoType additionalAppraisalAssignmentInfo = aaaInfoDoc
                  .getAdditionalAppraisalAssignmentInfo();
              if (additionalAppraisalAssignmentInfo.isSetAssignmentDetails()) {
                final AssignmentDetailsType assignmentDetails = additionalAppraisalAssignmentInfo
                    .getAssignmentDetails();

                if (assignmentDetails.isSetRelatedEstimateDocumentID()) {
                  if (mLogger.isLoggable(Level.FINE)) {
                    mLogger.fine("Staff User - Supplement Assignment Case, "
                        + "have RelatedEstimateDocumentID. ");
                  }
                  final Long relatedEstimateDocumentID = Long.valueOf(
                      assignmentDetails.getRelatedEstimateDocumentID());
                  orgEstMieFile = findOriginalEstimateFile(workItemId,
                      handlerUtils, relatedEstimateDocumentID);// Used
                                                               // by
                                                               // mcfServiceEJBRemote
                  if(isCrossSupplementAllowed) {
                  	orgEstMieFile = updateAD34InMIE(orgEstMieFile, context);
                  }
                  // TODO - Need Additional exception
                  // handling when file is NULL ??
                  estimate = attachOriginalEstimateAsFirstAttachmentIfPresent(
                      context, estimate, handlerUtils, orgEstMieFile,
                      relatedEstimateDocumentID);
                  handleSupplementHistory(userInfo,
							relatedEstimateDocumentID);
                }
              }
              if (doesProtocolAcceptAdditionAttachments()) {
                supplementRequestDocFile = attachSupplimentNotificationDocumentTextIfNeeded(
                    context, workItemId, handlerUtils, supplementRequestDocFile);
              }
            } else {
              if (mLogger.isLoggable(Level.INFO)) {
                mLogger
                    .info("\n********Debug: Staff User - Supplement Assignment Case, have No aaaInfoDoc. ");
              }
            }

          } // End Staff Supplement Case

        } // End Supplement case

        // -- Add Dispatch report as first attachment.
        //
        // Create Dispatch report from MitchellEnvelope

        dsFile = attachDispatchReportIfRequired(context,
            getDispatchReportType());
        if (doesProtocolAcceptAdditionAttachments()) {
          attachmentFileList = prepareAdditionalAttachments(context,
              workItemId, suppToOrigNoticeFile, aaaInfoDoc);
        }
        archivalId = handleMCFDelivery(context, thisMethod, estimate,
					workItemId, userInfo, ciecaDoc);

      } // End Else - All other cases involve deliverying an MCF Package

      // Write the log in App Log (call super class method)
      logEClaimSubmissionEvent(context, ciecaDoc, archivalId, workItemId,
          appLogTxType);

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("Successfully logged the EClaim Submission Event.");
      }

    } catch (final AssignmentDeliveryException adse) {
      throw adse;
    } catch (final Exception e) {
      logException(thisMethod, workItemId, e);
    } finally {
        cleaUp(thisMethod, workItemId, mieFile, dsFile,
					supplementRequestDocFile, attachmentFileList, orgEstMieFile);

      mLogger.exiting(getClassName(), thisMethod);
    }
  }

  
	private void cleaUp(final String thisMethod, final String workItemId,
			File mieFile, File dsFile, File supplementRequestDocFile,
			File[] attachmentFileList, File orgEstMieFile)
			throws AssignmentDeliveryException {
		try {
		    cleanUpFileIfPresent(mieFile, "MIE File");
		    cleanUpFileIfPresent(orgEstMieFile, "Original estiamte file");
		    cleanUpFileIfPresent(supplementRequestDocFile, "Supplement Request doc File");
		    cleanUpFileIfPresent(dsFile, "Dispatch Report File");
		    cleanUpAttachementFilesIfPresent(attachmentFileList);
		} catch (final Exception e) {
		    logException(thisMethod, workItemId, e);
		}
	}

	private void handleSupplementDRPMCFDelivery(AssignmentServiceContext context, 
									 AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc, 
									 CIECADocument ciecaDoc, 
									 UserInfoDocument userInfo, 
									 String workItemId, 
									 String thisMethod) throws Exception {
			final String methodName = "handleSupplementDRPMCFDelivery";
			mLogger.entering(CLASS_NAME,methodName);
	        Estimate estimate = null;
			File mieFile = null;
	        File dsFile = null;
	        File suppToOrigNoticeFile = null;
	        File supplementRequestDocFile = null;
	        File[] attachmentFileList = new File[0];
	        File orgEstMieFile = null;
	       try {
			  if (aaaInfoDoc != null) {
	              final AdditionalAppraisalAssignmentInfoType additionalAppraisalAssignmentInfo = aaaInfoDoc
	                      .getAdditionalAppraisalAssignmentInfo();
	              if (additionalAppraisalAssignmentInfo.isSetAssignmentDetails()) {
	                  final AssignmentDetailsType assignmentDetails = additionalAppraisalAssignmentInfo
	                          .getAssignmentDetails();
	
	                  if (assignmentDetails.isSetRelatedEstimateDocumentID()) {
	                      if (mLogger.isLoggable(Level.FINE)) {
	                          mLogger.fine("Staff User - Supplement Assignment Case, "
	                                  + "have RelatedEstimateDocumentID. ");
	                      }
	                      final Long relatedEstimateDocumentID = Long.valueOf(
	                              assignmentDetails.getRelatedEstimateDocumentID());
	                      orgEstMieFile = findOriginalEstimateFile(workItemId, handlerUtils,
	                              relatedEstimateDocumentID);
	                        
	                      orgEstMieFile = updateAD34InMIE(orgEstMieFile, context);
	                      
	                      estimate = attachOriginalEstimateAsFirstAttachmentIfPresent(context, estimate,
	                              handlerUtils, orgEstMieFile, relatedEstimateDocumentID);
	                      handleSupplementHistory(userInfo,
									relatedEstimateDocumentID);
	                  }
	              }
	              if (doesProtocolAcceptAdditionAttachments()) {
	                  supplementRequestDocFile = attachSupplimentNotificationDocumentTextIfNeeded(context,
	                          workItemId, handlerUtils, supplementRequestDocFile);
	              }
	          } else {
	              if (mLogger.isLoggable(Level.INFO)) {
	                  mLogger.info("\n********Debug: Staff User - Supplement Assignment Case, have No aaaInfoDoc. ");
	              }
	          }
			  
	          dsFile = attachDispatchReportIfRequired(context, getDispatchReportType());
	          if (doesProtocolAcceptAdditionAttachments()) {
	              attachmentFileList = prepareAdditionalAttachments(context, workItemId, suppToOrigNoticeFile,
	                      aaaInfoDoc);
	          }
	          
	          long archivalId = handleMCFDelivery(context, thisMethod, estimate,
						workItemId, userInfo, ciecaDoc);
	          
	          logEClaimSubmissionEvent(context, ciecaDoc, archivalId, workItemId, ECINBOX_SUBMISSION_SUCCESS_APPLOG_TRNS_TYPE_FILE);
	
	          if (mLogger.isLoggable(Level.INFO)) {
	              mLogger.info("Successfully logged the EClaim Submission Event.");
	          }
	      } finally {
	    	  cleaUp(thisMethod, workItemId, mieFile, dsFile,
						supplementRequestDocFile, attachmentFileList, orgEstMieFile);
	      }
	      mLogger.exiting(CLASS_NAME,methodName);
	}

	private void handleSupplementHistory(final UserInfoDocument userInfo,
			final Long relatedEstimateDocumentID) throws RemoteException,
			MitchellException {
		if (requiresSupplementHistory()) {
		    if (mLogger.isLoggable(Level.FINE)) {
		        mLogger.fine("RSH log is required for [" + String.valueOf(relatedEstimateDocumentID) + "]");
		    }
		    handlerUtils.writeRequestSupplementHistory(relatedEstimateDocumentID
		            .longValue(), userInfo.getUserInfo().getUserID(), userInfo
		            .getUserInfo().getOrgCode());
		}else{
		    if (mLogger.isLoggable(Level.FINE)) {
		        mLogger.fine("RSH log is NOT required for [" + String.valueOf(relatedEstimateDocumentID) + "]");
		    }
		}
	}

	private long handleMCFDelivery(final AssignmentServiceContext context,
			final String thisMethod, Estimate estimate,
			final String workItemId, final UserInfoDocument userInfo,
			final CIECADocument ciecaDoc) throws AssignmentDeliveryException,
			MCFServiceException, RemoteException, ECInboxSvcException {
		File mcfFile;
		long archivalId;
		final File templateFile = findTemplateFileFromCustomSettings(context, userInfo);

		mcfFile = buildMcfFileFromTemplateUsingEstimate(context, estimate, userInfo, ciecaDoc, templateFile);

		mcfFile = handleEvtPubIntegration(context, thisMethod, workItemId, userInfo, mcfFile);

		archivalId = archiveMcfFile(workItemId, userInfo, mcfFile);

		sendAssignmentToEclaimsInBox(context, workItemId, userInfo, mcfFile);
		return archivalId;
	}
	
	  protected File updateAD34InMIE(final File mieFile, final AssignmentServiceContext context) throws MitchellException, IOException {
	    	final String METHOD_NAME = "updateAD34InMIE";
	    	mLogger.entering(CLASS_NAME,METHOD_NAME);
	    	
	    	if (context != null  
	    			&& mieFile != null 
	    			&& mieFile.getAbsoluteFile() != null) {
		    		final MieMum mieMum = new MieMum();
		    		final String mieFilename = mieFile.getAbsoluteFile().toString();
		    		final UserInfoDocument userInfoDocument = context.getUserInfo();
		    		final UserInfoDocument drpUserInfoDocument = context.getDrpUserInfo();
		    		AD34Type aD34Type = null;
		    		mieMum.unmarshalNative(mieFilename);
		    		MieDocument mieDocument = mieMum.getDocument();
		    		EstimateType[] estimateType = mieDocument.getMie().getEstimateArray();
		    		if(estimateType != null && estimateType.length > 0) {
		        		EstimateType currentEstimateType = estimateType[0];
		        		
		        		// 1. Update AD34 fields
		        		aD34Type = currentEstimateType.getAD34();
				    	if(aD34Type != null) {	
				    		/* Populate the below tags for Non-Staff
				    		 	i) AD34/Company_ID	
				    		 	ii) AD34/Reviewer_ID 
				    		 	iii) AD34/Reviewer_Co_ID and 
				    		 	iv) AD34/Ind_Adj_Company_ID
				    		 */
			    			if(context.isDrp()) {
			    				String companyId = null;
			        			String reviewerId = null;
			        			String reviewerCoId = null;
			        			String IndAdjCoId = null;

			        			if( userInfoDocument != null) {
			    					final UserInfoType userInfoType = userInfoDocument.getUserInfo();
			    					companyId = userInfoType.getOrgCode();
			    					reviewerId = userInfoType.getUserID();
			    					reviewerCoId = companyId;	    
			    				}
			        			if( drpUserInfoDocument != null) {
			    					IndAdjCoId = drpUserInfoDocument.getUserInfo().getOrgCode();
			        			}
			    				 if (mLogger.isLoggable(Level.INFO)) {
			    					 	final StringBuffer messageBuffer = new StringBuffer();
			    					 	messageBuffer.append("CompanyID = ").append(companyId).
			    					 				  append("ReviewerID = ").append(reviewerId).
			    					 				  append("ReviewerCoID = ").append(reviewerCoId).
			    					 				  append("IndAdjCompanyID = ").append(IndAdjCoId);
			    					 				
			    					 	mLogger.info(messageBuffer.toString());
			    		            }
			    				
			    				aD34Type.setCompanyID(companyId);
			    				aD34Type.setReviewerID(reviewerId);
			    				aD34Type.setReviewerCoID(reviewerCoId);
			    				aD34Type.setIndAdjCompanyID(IndAdjCoId);
			    				
			    			} /* Blank out the below tags for Non-Staff
				    		 	i) AD34/Reviewer_ID 
				    		 	ii) AD34/Reviewer_Co_ID and 
				    		 	iii) AD34/Ind_Adj_Company_ID
			    			 */ 
			    			else { 
			    	    		
			    				aD34Type.setReviewerCoID(null);
			    				aD34Type.setReviewerID(null);
			    				aD34Type.setIndAdjCompanyID(null);
			    			}
			    			
			    			aD34Type.setMEDSOperatorID(AssignmentDeliveryConstants.MIE_MEDS_OPERATOR_ID);
			    			//2. Also update CT02/DataSetCRC to 0000000000000 to avoid CRC check at ultramate
//			    			currentEstimateType.getCT02().setDataSetCRC(AssignmentDeliveryConstants.MIE_DATASET_CRC_VALUE);
//			    			mieMum.marshalNative(mieFilename);   
				    	}
//				    	2. AD04/Estimator_ID	    			
		    			final AD04Type aD04Type = currentEstimateType.getAD04();
		    			if(aD04Type != null) { 
		    				if(context.isDrp() && drpUserInfoDocument != null) {
		    					aD04Type.setEstimatorID(drpUserInfoDocument.getUserInfo().getUserID());
		    				} else if(!context.isDrp() && userInfoDocument != null) {
		    					aD04Type.setEstimatorID(userInfoDocument.getUserInfo().getUserID());
		    				}
		    			}
		    			
		    			//3. Also update CT02/DataSetCRC to 0000000000000 to avoid CRC check at ultramate
		    			currentEstimateType.getCT02().setDataSetCRC(AssignmentDeliveryConstants.MIE_DATASET_CRC_VALUE);
		    			
			    		mieMum.marshalNative(mieFilename);
		    		}
	    	}
	    	
	    	mLogger.exiting(CLASS_NAME,METHOD_NAME);
			return mieFile;
	    }
  /**
   * Can information be attached beyond dispatch report?
   * 
   * @return
   */
  protected abstract boolean doesProtocolAcceptAdditionAttachments();

  /**
   * Entring a method.
   * 
   * @param thisMethod
   */
  protected void entering(final String thisMethod)
  {
    mLogger.entering(getClassName(), thisMethod);
  }

  /**
   * Existing a method
   * 
   * @param methodName
   */
  protected void exiting(final String methodName)
  {
    mLogger.exiting(getClassName(), methodName);
  }

  private CIECADocument extractCiecaDocument(
      final AssignmentServiceContext context)
      throws Exception
  {
    // Get CIECADocument from MithellEnvelope
    final CIECADocument ciecaDoc = handlerUtils.getCiecaDocFromMitchellEnv(
        context.getMitchellEnvDoc(), context.getWorkItemId());

    if (ciecaDoc != null) {
      if (mLogger.isLoggable(java.util.logging.Level.FINE)) {
        mLogger.info("\n*****deliverAssignment, inbound ciecaDoc:\n"
            + ciecaDoc.toString());
      }
    }
    return ciecaDoc;
  }

  /**
   * 
   * @param workItemId
   * @param userInfo
   * @param handlerUtils
   * @return
   * @throws Exception
   *           <b>NOTE</b> This method's contract should be consistent with
   *           {@link #doesProtocolAcceptAdditionAttachments() protocols
   *           ability to accept additional attachments }. If the protocol
   *           does not support additional attachments, the method should
   *           throw {@link java.lang.UnsupportedOperationException
   *           UnsupportedOperationException}
   * 
   */
  protected abstract File extractSupplimentToOriginalNoticeFileIfRequired(
      String workItemId, UserInfoDocument userInfo,
      AbstractHandlerUtils handlerUtils)
      throws Exception;

  /**
   * Extract and return additional appriasal info document.
   * 
   * @param context
   * @return
   * @throws Exception
   */
  protected AdditionalAppraisalAssignmentInfoDocument findAdditionalAppraisalAssignmentInfoDocument(
      final AssignmentServiceContext context)
      throws Exception
  {
    AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc;
    // Get the AdditionalAppraisalAssignmentInfo.xml doc, if exists
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("\n*****deliverAssignment, context.getMitchellEnvDoc():\n "
          + context.getMitchellEnvDoc().toString());
      mLogger
          .info("\n*****deliverAssignment, before handlerUtils.getAAAInfoDocFromMitchellEnv ");
    }

    aaaInfoDoc = handlerUtils.getAAAInfoDocFromMitchellEnv(
        context.getMitchellEnvDoc(), context.getWorkItemId(),
        DO_NOT_RETURN_EXCEPTION);

    if (aaaInfoDoc != null) {
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("\n*****deliverAssignment, inbound aaaInfoDoc:\n "
            + aaaInfoDoc.toString());
      }
    }

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("*****deliverAssignment, after handlerUtils.getAAAInfoDocFromMitchellEnv ");
    }
    return aaaInfoDoc;
  }

  /**
   * Find original estimate file if available
   * 
   * @param workItemId
   * @param handlerUtils
   * @param relatedEstimateDocumentID
   * @return
   * @throws Exception
   */
  protected File findOriginalEstimateFile(final String workItemId,
      final AbstractHandlerUtils handlerUtils,
      final Long relatedEstimateDocumentID)
      throws Exception
  {
    File orgEstMieFile;
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("********Debug:  relatedEstimateDocumentID= "
          + relatedEstimateDocumentID.longValue());
    }
    orgEstMieFile = handlerUtils.getOrigEstimateMieFileFromDocStore(
        relatedEstimateDocumentID, workItemId);
    return orgEstMieFile;
  }

  private File findTemplateFileFromCustomSettings(
      final AssignmentServiceContext context, final UserInfoDocument userInfo)
      throws AssignmentDeliveryException
  {
    // Get Template file path from Custom Settings
    /**
     * Added custom settings of Eclaim Template File Name for staff and non
     * staff user for SIP Assignment Enhancement 0000252: Augment SIP
     * Assignment Delivery Custom Setting for eClaim Template By:- Vandana
     * Gautam Modified Date :-05/28/2009
     */

    final String templateDirPath = assignmentDeliveryUtils
        .getUserCustomSetting(userInfo,
            AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_TEMPLATE);

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("Successfully found the Template DIR PATH = "
          + templateDirPath);
    }

    String templateFileName = null;

    if (context.isDrp()) {
      templateFileName = templateDirPath
          + File.separator
          + assignmentDeliveryUtils
              .getUserCustomSetting(
                  userInfo,
                  AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_TEMPLATE_FILE_FOR_NON_STAFF);

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("Successfully found the Template DIR FILE FOR NON STAFF = "
                + templateFileName);
      }

    } else {
      templateFileName = templateDirPath
          + File.separator
          + assignmentDeliveryUtils
              .getUserCustomSetting(
                  userInfo,
                  AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_TEMPLATE_FILE_FOR_STAFF);

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("Successfully found the Template DIR FILE FOR  STAFF = "
            + templateFileName);
      }

    }

    final File templateFile = new File(templateFileName);
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("templateFile " + templateFile.getAbsolutePath());
    }
    return templateFile;
  }

  /**
   * Get the class name as you would like it in the logs.
   * 
   * @return
   */
  protected abstract String getClassName();

  /**
   * Get the integer constant that identifies a dispatch report type.
   * 
   * @return
   */
  protected abstract int getDispatchReportType();

  /**
   * Getter
   * 
   * @return
   */
  public EclaimsInBoxServiceClient getEclaimsInBoxServiceClient()
  {
    return eclaimsInBoxServiceClient;
  }

  /**
   * @return
   */
  protected String getEstimateDescription()
  {
    return "Estimate";
  }

  /**
   * Get handler type.
   * 
   * @param context
   * @return
   * @throws AssignmentDeliveryException
   */
  protected String getHandlerType(final AssignmentServiceContext context)
      throws AssignmentDeliveryException
  {
    throw new UnsupportedOperationException("This is only valid for ARC7");
  }

  /**
   * Getter
   * 
   * @return
   */
  public MCFServiceEJBRemote getMcfServiceEJBRemote()
  {
    return mcfServiceEJBRemote;
  }

  /*
   * Guard against dependency voilations
   */
  private void guard(final AbstractMCFAsgnDelHandler my)
  {
    final StringBuffer reason = new StringBuffer("");
    if (my.appLoggerBridge == null) {
      reason.append(",appLoggerBridge");
    }
    if (my.assignmentDeliveryConfigBridge == null) {
      reason.append(",assignmentDeliveryConfigBridge");
    }
    if (my.assignmentDeliveryUtils == null) {
      reason.append(",assignmentDeliveryUtils");
    }
    if (my.converter == null) {
      reason.append(",converter");
    }
    if (my.drBuilder == null) {
      reason.append(",drBuilder");
    }
    if (my.eclaimsInBoxServiceClient == null) {
      reason.append(",eclaimsInBoxServiceClient");
    }
    if (my.errorLoggingService == null) {
      reason.append(",errorLoggingService");
    }
    if (my.handlerUtils == null) {
      reason.append(",handlerUtils");
    }
    if (my.mcfServiceEJBRemote == null) {
      reason.append(",mcfServiceEJBRemote");
    }
    if (my.mLogger == null) {
      reason.append(",mLogger");
    }
    if (thereIs(reason)) {
      throw new IllegalStateException(
          "Please makse sure all the dependencies are satisfied before using this component. "
              + "The following dependencies remain unsatisfied ["
              + reason
              + "]");
    }
  }

  /* Encapsulated EVT PUB integration. */
  private File handleEvtPubIntegration(final AssignmentServiceContext context,
      final String methodName, final String workItemId,
      final UserInfoDocument userInfo, File mcfFile)
  {
    // For EvtPub integration, rename the MIE as follows - let filename =
    // original filename:
    // newfilename = asg_CoCd_UserId___WorkItemId___filename
    // Note, the original filename's first field is the ClaimNumber
    final String mcfFilename = mcfFile.getName();

    String coCdForMcfFilename = null;
    String userIdForMcfFilename = null;

    // Determine file prefixes.
    if (context.isDrp() == false) {
      coCdForMcfFilename = userInfo.getUserInfo().getOrgCode();
      userIdForMcfFilename = userInfo.getUserInfo().getUserID();
    } else {
      // TODO Is this a bug? Should we use DRP user info?
      // ~prashant.khanwale@mitchell.com, Jul 28, 2010
      coCdForMcfFilename = context.getUserInfo().getUserInfo().getOrgCode();
      userIdForMcfFilename = context.getUserInfo().getUserInfo().getUserID();
    }

    String mcfNewFilename = null;
    /**
     * SIP Assignment MCF file name has been modified for claimId &
     * ExposureId Resolution: CID_XXXXX & EID_XXXXX added in mcf file name.
     * Modified Date :-05/10/2010 Modified By : Preet Singh
     */
    final String[] result = handlerUtils.getClaimExposureIdsResult(context);
    if (result != null) {
      final String claimId = result[0].trim();
      final String claimExposureId = result[1].trim();
      // Format :
      // asg_companyCode_userId___workItemId___CID-XXXXX_EID-XXXXX___mcfFilename
      // Example :
      // asg_ME_RBME01___c77c5e2e-4c9d-7074-9fc3-2fc8000f65ed___CID-509066_EID-316581___ME-1221212-30_20100507-031423.754-21.MCF

      StringBuffer sbuff = new StringBuffer();
      sbuff.append("asg_");
      sbuff.append(coCdForMcfFilename);
      sbuff.append("_");
      sbuff.append(userIdForMcfFilename);
      sbuff.append("___");
      sbuff.append(workItemId);
      sbuff.append("___CID-");
      sbuff.append(claimId);
      sbuff.append("_EID-");
      sbuff.append(claimExposureId);
      sbuff.append("___");
      sbuff.append(mcfFilename);

      mcfNewFilename = sbuff.toString();

    } else {

      mLogger.info("Default MCFFilename created.");

      // Build new filename - put 3 "_" around WorkItemId, in case this
      // contains single "_"s.
      // Set old mcf file name in case of value of result variable is
      // false

      StringBuffer sbuff = new StringBuffer();
      sbuff.append("asg_");
      sbuff.append(coCdForMcfFilename);
      sbuff.append("_");
      sbuff.append(userIdForMcfFilename);
      sbuff.append("___");
      sbuff.append(workItemId);
      sbuff.append("___");
      sbuff.append(mcfFilename);

      mcfNewFilename = sbuff.toString();

    }

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("MCFFileName : " + mcfNewFilename);
    }

    //
    final String mcfFileDir = mcfFile.getParent();
    final String mcfNewFullpath = mcfFileDir + "/" + mcfNewFilename;
    // File (or directory) with new name
    final File newMcfFile = new File(mcfNewFullpath);

    // Rename file.
    final boolean fileRenamed = mcfFile.renameTo(newMcfFile);
    if (!fileRenamed) {
      errorLoggingService.logError(AssignmentDeliveryErrorCodes.GENERAL_ERROR,
          null, getClassName(), methodName, ErrorLoggingService.SEVERITY.FATAL,
          workItemId, "Could not rename file " + mcfFile + " to "
              + mcfNewFullpath, null, 0, null);
    } else {
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("Successfully renamed MCF File to: "
            + newMcfFile.getAbsoluteFile());
      }
    }

    // Further logic relies on the 'old' filename var.
    mcfFile = newMcfFile;
    return mcfFile;
  }

  /**
   * 
   * @param context
   * @throws Exception
   */
  protected void handleSupplementDrpCase(final AssignmentServiceContext context)
      throws Exception
  {
    final boolean DO_NOT_RETURN_EXCEPTION = false;
    // Send Email Notification to Non-Staff User.
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("********Debug:  Have Non-Staff Supplement Assignment Case. ");
      mLogger
          .info("\n\n********Send Email/Fax Notification to Non-Staff User. ");
    }

    handlerUtils.sendNonStaffSuppNotification(context, context.getWorkItemId(),
        DO_NOT_RETURN_EXCEPTION);

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("********After Send Email/Fax Notification to Non-Staff User - Supplement Request)\n\n");
    }
  }

  /**
   * Is the destination needed in MCF
   * 
   * @return
   */
  protected abstract boolean isMcfDestinationRequired();

  /**
   * Tests if the supplies string represtents a boolean true. Converted from
   * legacy code. Tests for equality to the string "true" or the presence of
   * the letter "y" or "t" anywhere in the input string. Modify with caution.
   * 
   * @param value
   *          String value to test for boolean true
   * @return true if the lowercase supplied string is 'true' or contains the
   *         character 'y' or 't'.
   * @deprecated A better alternative needs to be defined. Please don't
   *             perptuate the use of this method beyond current scope.
   */
  protected boolean isTrue(String value)
  {
    value = (null != value) ? value.toLowerCase() : "";
    return "true".equals(value) || value.indexOf("y") != -1
        || value.indexOf("t") != -1;
  }

  /**
   * Generate App-log.
   * 
   * @param context
   * @param ciecaDoc
   * @param arid
   * @param workItemId
   * @param appLogTxType
   * @throws AssignmentDeliveryException
   */
  protected void logEClaimSubmissionEvent(
      final AssignmentServiceContext context, final CIECADocument ciecaDoc,
      final long arid, final String workItemId, final String appLogTxType)
      throws AssignmentDeliveryException
  {
    final String thisMethod = "logEClaimDeliveryEvent";
    entering(thisMethod);

    try {
      final UserInfoDocument userInfo = context.getUserInfo();

      if (mLogger.isLoggable(Level.FINE)) {
        mLogger.fine("userInfo in logEClaimDeliveryEvent........ "
            + userInfo.toString());
      }

      final UserInfoDocument drpUserInfo = context.getDrpUserInfo();
      String userInfoUserIdForAppLog = userInfo.getUserInfo().getUserID();// Default
                                                                          // values
                                                                          // if
                                                                          // non
                                                                          // DRP
      UserInfoDocument userInfoForAppLog = userInfo;// Default values if
                                                    // non DRP
      String reviewerUserId = null;
      String reviewerCompanyCode = null;
      final boolean isDrp = drpUserInfo != null;
      if (isDrp) {

        if (mLogger.isLoggable(Level.FINE)) {
          mLogger.fine("drpUserInfo in logEClaimDeliveryEvent.. "
              + drpUserInfo.toString());
        }

        userInfoUserIdForAppLog = drpUserInfo.getUserInfo().getUserID();

        if (mLogger.isLoggable(Level.FINE)) {
          mLogger.fine("drpuserInfoUserIdForAppLog is "
              + userInfoUserIdForAppLog);
        }

        userInfoForAppLog = drpUserInfo;
        reviewerUserId = userInfo.getUserInfo().getUserID();
        reviewerCompanyCode = userInfo.getUserInfo().getOrgCode();
      }
      final AppLoggingDocument appLogDoc = prepareAppLoggingDocument(ciecaDoc,
          STATUS_TO_KEEP_LOGGING_API_HAPPY, workItemId, appLogTxType,
          userInfoUserIdForAppLog);

      final AppLoggingNVPairs appLogNvPairs = prepareAppLoggingNameValuePairs(
          ciecaDoc, arid, appLogTxType, isDrp, reviewerUserId,
          reviewerCompanyCode);

      appLoggerBridge.logEvent(appLogDoc, userInfoForAppLog, workItemId,
          AssignmentDeliveryConstants.APPLICATION_NAME,
          AssignmentDeliveryConstants.APPLICATION_NAME, appLogNvPairs);
      mLogger.info("Successfully logged the EClaim Submission Event");
    } catch (final Exception e) {
      mLogger.severe(e.getMessage());
      errorLoggingService.logError(AssignmentDeliveryErrorCodes.APP_LOG_ERROR,
          null, getClassName(), thisMethod, ErrorLoggingService.SEVERITY.FATAL,
          workItemId, e.getMessage(), null, 0, e);
      throw mLogger.createException(AssignmentDeliveryErrorCodes.APP_LOG_ERROR,
          workItemId, e);
    } finally {
      exiting(thisMethod);
    }
  }

  /**
   * Prepare additional attachements as needed.
   * 
   * @param context
   * @param workItemId
   * @param suppToOrigNoticeFile
   * @param aaaInfoDoc
   * @return
   * @throws Exception
   *           </br> <b>NOTE</b> This method's contract should be consistent
   *           with {@link #doesProtocolAcceptAdditionAttachments()
   *           protocols ability to accept additional attachments }. If the
   *           protocol does not support additional attachments, the method
   *           should throw {@link java.lang.UnsupportedOperationException
   *           UnsupportedOperationException}
   * 
   */
  protected abstract File[] prepareAdditionalAttachments(
      AssignmentServiceContext context, final String workItemId,
      File suppToOrigNoticeFile,
      final AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc)
      throws Exception;

  /**
   * Guard against the obvious.
   * 
   * @param thisMethod
   * @param workItemId
   * @param ciecaDoc
   * @throws AssignmentDeliveryException
   */
  protected void rejectDeliveryIfNeitherOriginalNorSuppliment(
      final String thisMethod, final String workItemId,
      final CIECADocument ciecaDoc)
      throws AssignmentDeliveryException
  {
    // We can either accept an original assignment or a supplement estimate.
    // If it's neither, error out

    final boolean isOriginalOrSupplement = handlerUtils
        .isOriginalOrSupplementAssignment(ciecaDoc);
    // Determine Assignment Type - Must be either Original or Supplement
    // If Not Original Or Supplement, reject delivery and throw fatal error
    if (!isOriginalOrSupplement) {
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("Debug:  Not Original Or Supplement, reject delivery and throw error. ");
      }
      mLogger
          .severe("MitchellEnvelope contains CIECA Document with an Invalid Assignment Type.");
      errorLoggingService
          .logError(
              AssignmentDeliveryErrorCodes.INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR,
              null,
              getClassName(),
              thisMethod,
              ErrorLoggingService.SEVERITY.FATAL,
              workItemId,
              "MitchellEnvelope contains CIECA Document with an Invalid Assignment Type.",
              null, 0, null);
      throw mLogger
          .createException(
              AssignmentDeliveryErrorCodes.INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR,
              workItemId);
    }
  }

  /**
   * True if the delivery protocol mandates supplement history.
   * 
   * @return
   */
  protected boolean requiresSupplementHistory()
  {
    return false;
  }

  private void sendAssignmentToEclaimsInBox(
      final AssignmentServiceContext context, final String workItemId,
      final UserInfoDocument userInfo, final File mcfFile)
      throws ECInboxSvcException
  {
    // Call ECInBox service to send the MCF file
    if (context.isDrp() == false) {

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("Delivering assignment to ECInbox Service for estimator id =  "
                + userInfo.getUserInfo().getUserID());
      }

      eclaimsInBoxServiceClient.sendFile(userInfo.getUserInfo().getOrgCode(),
          userInfo.getUserInfo().getUserID(), mcfFile.getAbsolutePath(), true,
          workItemId);

    } else {

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("Delivering assignment to ECInbox Service for estimator id =  "
                + context.getDrpUserInfo().getUserInfo().getUserID()
                + " and reviewer id = "
                + context.getUserInfo().getUserInfo().getUserID());
      }

      // Add reviewer info to EClaim Inbox call, so the Inbox applog
      // contains the insurance carrier details,
      // later required for the EvtPub output.
      eclaimsInBoxServiceClient.sendFile(context.getDrpUserInfo().getUserInfo()
          .getOrgCode(), context.getDrpUserInfo().getUserInfo().getUserID(),
          // Add reviewer info.
          context.getUserInfo().getUserInfo().getOrgCode(), context
              .getUserInfo().getUserInfo().getUserID(),
          mcfFile.getAbsolutePath(), true, workItemId);
    }

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("Successfully delivered the MCF file to ECInbox Service");
    }
  }

  /**
   * Setter
   * 
   * @param eclaimsInBoxServiceClient
   */
  public void setEclaimsInBoxServiceClient(
      final EclaimsInBoxServiceClient eclaimsInBoxServiceClient)
  {
    this.eclaimsInBoxServiceClient = eclaimsInBoxServiceClient;
  }

  /**
   * Setter
   * 
   * @param mcfServiceEJBRemote
   */
  public void setMcfServiceEJBRemote(
      final MCFServiceEJBRemote mcfServiceEJBRemote)
  {
    this.mcfServiceEJBRemote = mcfServiceEJBRemote;
  }

  private boolean thereIs(final StringBuffer reason)
  {
    if (reason == null) {
      return false;
    }
    return reason.length() != 0;
  }

  private void validate(final AssignmentServiceContext context)
  {
    // Validate the input param
    context.validate();
    mLogger.fine("Input AssignmentServiceContext is valid.");
  }

  /*
   * @deprecated, Remove errlog warning noise for missing ECMgrAppCode, as per
   * Scott
   */
  private void warnIfNonEcm(final AssignmentServiceContext context,
      final String methodName, final String workItemId,
      final UserInfoDocument userInfo)
  {
    // If user does not have ECM appl access code, error log a warning.
    final boolean isECMUser = HandlerUtils.hasApplCode(userInfo,
        AssignmentDeliveryConstants.APPL_ACCESS_CODE_ECLAIM_MANAGER);
  }

  /**
   * Was the supplement converted to original.
   * 
   * @param aaaInfoDoc
   * @return
   */
  protected boolean wasSupplementConvertedToOriginal(
      final AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc)
  {
    return aaaInfoDoc != null
        && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo() != null
        && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .isSetAssignmentDetails()
        && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .getAssignmentDetails().isSetSupplementConvertedToOriginalFlag()
        && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .getAssignmentDetails().getSupplementConvertedToOriginalFlag();
  }

}
