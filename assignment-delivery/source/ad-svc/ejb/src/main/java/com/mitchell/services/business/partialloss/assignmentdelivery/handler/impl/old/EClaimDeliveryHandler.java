package com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.old;

import java.io.File;
import java.util.logging.Level;

import com.cieca.bms.CIECADocument;
import com.cieca.bms.ClaimInfoType;
import com.cieca.bms.PolicyInfoType;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfig;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AssignmentDeliveryHandler;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.HandlerUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.arc7.DispatchReportBuilder;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.eclaim.BmsToMieConverter;
import com.mitchell.services.business.partialloss.eclaiminboxsvc.ECInboxSvcClient;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingType;
import com.mitchell.services.core.applog.client.AppLogging;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.services.core.mcfsvc.AttachmentObject;
import com.mitchell.services.core.mcfsvc.MCFBuildRequest;
import com.mitchell.services.core.mcfsvc.MCFBuildRequestNVPairs;
import com.mitchell.services.core.mcfsvc.client.MCFServiceEJBClient;
import com.mitchell.services.core.mcfsvc.ejb.MCFServiceEJBRemote;
import com.mitchell.services.core.mcfsvc.utils.MCFServiceConstants;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.utils.mcf.MCFConstants;

public class EClaimDeliveryHandler implements AssignmentDeliveryHandler {
	private final String CLASS_NAME = "EClaimDeliveryHandler";
	private final String ECINBOX_SUBMISSION_SUCCESS_APPLOG_TRNS_TYPE_FILE = "106800";
	private final String NVPAIR_ASG_DELIVERY_FAR_ID_NAME = "asgDeliveryFileArchiveId";
	private final String NVPAIR_ASG_DELIVERY_CIECA_BMS_ID_NAME = "asgDeliveryCiecaBmsId";
    
    // Added for SIP3.5 - 
	private final String ECLAIM_CANCELLATION_ALERT_SENT_SUCCESS_APPLOG_TRNS_TYPE_FILE = "106802";
	private final String SHOP_SUP_REQ_NOTIFICATION_SENT_SUCCESS_APPLOG_TRNS_TYPE_FILE = "106803";

	private final AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(
			this.getClass().getName());

	private AssignmentDeliveryUtils assignDeliveryUtils = new AssignmentDeliveryUtils();
    private BmsToMieConverter converter = new BmsToMieConverter();
    private DispatchReportBuilder drBuilder = new DispatchReportBuilder();
    
    private void logEClaimSubmissionEvent(AssignmentServiceContext context,
			CIECADocument ciecaDoc, int status, 
            long arid, String workItemId, String appLogTxType)
			throws AssignmentDeliveryException {
		final String methodName = "logEClaimDeliveryEvent";
		mLogger.entering(CLASS_NAME, methodName);

		try {
			AppLoggingDocument appLogDoc = AppLoggingDocument.Factory
					.newInstance();
			AppLoggingType appType = appLogDoc.addNewAppLogging();

			ClaimInfoType claimInfo = ciecaDoc.getCIECA().getAssignmentAddRq().getClaimInfo();
			if (claimInfo.getClaimNum() != null) {
				appType.setClaimNumber(claimInfo.getClaimNum());
			}

			// PloicyInfoType is not a mandatory element of ClaimInfo so check its nullablity
            PolicyInfoType policyInfo = claimInfo.getPolicyInfo();
			if (policyInfo != null && policyInfo.getPolicyNum() != null) {
				appType.setPolicyNumber(policyInfo.getPolicyNum());
			}

            UserInfoDocument userInfo = context.getUserInfo();
            mLogger.fine("userInfo in logEClaimDeliveryEvent........ "+ userInfo.toString());
            UserInfoDocument drpUserInfo = context.getDrpUserInfo();
            /**
             * Modified for SCR# 0009310
             * Modified By: Kiran Saini
             */
            //String userInfoUserIdForAppLog = userInfo.getUserInfo().getOrgID();
            String userInfoUserIdForAppLog = userInfo.getUserInfo().getUserID();
            UserInfoDocument userInfoForAppLog = userInfo;

            String reviewerUserId = null;
            String reviewerCompanyCode = null;
            
            boolean isDrp = false;
            
            if (drpUserInfo != null) {
                mLogger.info("drpUserInfo in logEClaimDeliveryEvent.. "+ drpUserInfo.toString());
                isDrp = true;
                
                userInfoUserIdForAppLog = drpUserInfo.getUserInfo().getUserID();
                mLogger.info("drpuserInfoUserIdForAppLog is " + userInfoUserIdForAppLog);
                userInfoForAppLog = drpUserInfo;

                reviewerUserId = userInfo.getUserInfo().getUserID();
                reviewerCompanyCode = userInfo.getUserInfo().getOrgCode();
    //          reviewerOrgId = userInfo.getUserInfo().getOrgID();
    // REF      long reviewerOrgId = Long.parseLong(reviewerInfoDoc.getUserInfo().getOrgID());
            }
            
			appType.setCurrentWorkflowId(workItemId);
			appType.setMitchellUserId(userInfoUserIdForAppLog);
            mLogger.info("userInfoUserIdForAppLog is ....." + userInfoUserIdForAppLog);
			appType.setModuleName(AssignmentDeliveryConstants.MODULE_NAME);
			appType.setStatus(status);
			appType.setTransactionType(appLogTxType);

			AppLoggingNVPairs appLogNvPairs = new AppLoggingNVPairs();
			
            mLogger.info("logEClaimSubmissionEvent: Archive ID = " +arid);
            if(arid > 0) {
               appLogNvPairs.addFileArchiveId(NVPAIR_ASG_DELIVERY_FAR_ID_NAME,
                        String.valueOf(arid));
            }
            
            // Only Add for MCF delivery case (ECINBOX_SUBMISSION_SUCCESS_APPLOG_TRNS_TYPE_FILE)
            //  Do not add for Cancel or Supplement Emails cases.            
            if((ciecaDoc.getCIECA().getAssignmentAddRq().getDocumentInfo().getDocumentID() != null) &&
               ( appLogTxType == ECINBOX_SUBMISSION_SUCCESS_APPLOG_TRNS_TYPE_FILE)) {
                appLogNvPairs.addCiecaBMSRqUID(NVPAIR_ASG_DELIVERY_CIECA_BMS_ID_NAME,ciecaDoc.getCIECA().getAssignmentAddRq().getDocumentInfo().getDocumentID());
            }

            mLogger.info("logEClaimSubmissionEvent: isDrp = " +isDrp);
            if (isDrp) {
                appLogNvPairs.addPair("ReviewerUserId", reviewerUserId);
                appLogNvPairs.addPair("ReviewerCompanyCode", reviewerCompanyCode);
            }

			MitchellWorkflowMessageDocument messageDoc = AppLogging
					.logAppEvent(appLogDoc, userInfoForAppLog, workItemId,
							AssignmentDeliveryConstants.APPLICATION_NAME,
							AssignmentDeliveryConstants.APPLICATION_NAME,
							appLogNvPairs);
		} catch (Exception e) {
			mLogger.severe(e.getMessage());
			ErrorLoggingService.logError(
                    AssignmentDeliveryErrorCodes.APP_LOG_ERROR, null, 
                    CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, 
                    workItemId, e.getMessage(), null, 0, e);
			throw mLogger.createException(
					AssignmentDeliveryErrorCodes.APP_LOG_ERROR, workItemId, 
                    e);
		} finally {
			mLogger.exiting(CLASS_NAME, methodName);
		}
	}
    
    /**
     * Tests if the supplies string represtents a boolean true
     * @param value String value to test for boolean true
     * @return true if the lowercase supplied string is 'true' or
     * contains the character 'y' or 't'.
     */
    private boolean isTrue(String value) {
        value = (null != value) ? value.toLowerCase() : "";
        return "true".equals(value) ||
            value.indexOf("y") != -1 ||
            value.indexOf("t") != -1;
    }    


    //----------------------------------------------------------------------
    // --  Update deliverAssignment method for Jetta/SIP3.5 - 
    //----------------------------------------------------------------------
	//
	public void deliverAssignment(AssignmentServiceContext context)
			throws AssignmentDeliveryException {
    
		final String methodName = "deliverAssignment(AssignmentServiceContext context)";
		mLogger.entering(CLASS_NAME, methodName);
		mLogger.info("Context in Assignment Deolvery Service:::"+context.toString());
        Estimate estimate = null;        
        
        // Validate the input param
        context.validate();
        mLogger.fine("Input AssignmentServiceContext is valid.");
        
        int status = 0;
		long arid = 0;
        String workItemId = context.getWorkItemId();
        UserInfoDocument userInfo = context.getUserInfo();
        String appLogTxType = ECINBOX_SUBMISSION_SUCCESS_APPLOG_TRNS_TYPE_FILE;
        
        // If user does not have ECM appl access code, error log a warning.
        boolean isECMUser = HandlerUtils.hasApplCode(userInfo,
                AssignmentDeliveryConstants.APPL_ACCESS_CODE_ECLAIM_MANAGER);

        if (!isECMUser) {
            int errorCode = AssignmentDeliveryErrorCodes.NO_USERINFO_ECM_APPL_ACCESS_CODE;
            String message = AssignmentDeliveryErrorCodes.NO_USERINFO_ECM_APPL_ACCESS_CODE_MSG;

            HandlerUtils.logWarningError(userInfo, errorCode, message, CLASS_NAME, methodName,
                    workItemId);

            mLogger.warning("No ECM appl access code for AssignmentDelivery context:\n"
                    + context.toString());
        }

        HandlerUtils handlerUtils = new HandlerUtils();

        int numOfAttachments = 0;
		File mieFile = null;
        File mcfFile = null;
        File dsFile =  null;
		File orgEstMieFile = null;
        File suppToOrigNoticeFile = null;    
        File supplementRequestDocFile = null;
        File [] attachmentFileList = null;

        String isRequiredDispatchReport = null;            
        AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = null;
        boolean bSupplementConvertedToOriginalFlag = false;                
        
        try {
            
            // Get CIECADocument from MithellEnvelope
            CIECADocument ciecaDoc = handlerUtils.getCiecaDocFromMitchellEnv(context.getMitchellEnvDoc(), workItemId);
            
            if(ciecaDoc != null) {
                  if ( mLogger.isLoggable( java.util.logging.Level.FINE ) ) {
                    mLogger.info( "\n*****deliverAssignment, inbound ciecaDoc:\n" + ciecaDoc.toString() ); 
                  }
            }
        
            // Get the AdditionalAppraisalAssignmentInfo.xml doc, if exists
            if(mLogger.isLoggable(Level.INFO)) {
                mLogger.info( "\n*****deliverAssignment, context.getMitchellEnvDoc():\n " + context.getMitchellEnvDoc().toString() ); 
                mLogger.info( "\n*****deliverAssignment, before handlerUtils.getAAAInfoDocFromMitchellEnv " ); 
            }
            
            aaaInfoDoc = handlerUtils.getAAAInfoDocFromMitchellEnv(context.getMitchellEnvDoc(), workItemId, false);

            if(aaaInfoDoc != null) {
                if(mLogger.isLoggable(Level.INFO)) {
                    mLogger.info( "\n*****deliverAssignment, inbound aaaInfoDoc:\n " + aaaInfoDoc.toString() ); 
                }
            }
            
            if(mLogger.isLoggable(Level.INFO)) {
                mLogger.info( "*****deliverAssignment, after handlerUtils.getAAAInfoDocFromMitchellEnv " ); 
            }
            
            // Determine Assignment Type - Must be either Original or Supplement
            boolean isOriginal = false;
            boolean isOriginalOrSupplement = false;
            
            isOriginal =  handlerUtils.isOriginalAssignment(ciecaDoc);
            isOriginalOrSupplement =  handlerUtils.isOriginalOrSupplementAssignment(ciecaDoc);
            
    
            // If Not Original Or Supplement, reject delivery and throw fatal error
            if (!isOriginalOrSupplement) {
               if(mLogger.isLoggable(Level.INFO)) { 
                    mLogger.info("Debug:  Not Original Or Supplement, reject delivery and throw error. ");
               }
                mLogger.severe("MitchellEnvelope contains CIECA Document with an Invalid Assignment Type.");
                ErrorLoggingService.logError(
                        AssignmentDeliveryErrorCodes.INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR, 
                        null, CLASS_NAME, methodName, 
                        ErrorLoggingService.SEVERITY.FATAL, workItemId, 
                        "MitchellEnvelope contains CIECA Document with an Invalid Assignment Type.", null, 0, null);                
                throw mLogger.createException( 
                        AssignmentDeliveryErrorCodes.INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR, workItemId);                
            }

            //
            // Send notification Email Case - Non-Staff Supplement Assignments       
            if ( (!isOriginal) && context.isDrp() ) {

                 boolean returnException = false;
               	 arid = 0;                      //  NOTE: no archive ID for Send notification Email Case
                 appLogTxType = SHOP_SUP_REQ_NOTIFICATION_SENT_SUCCESS_APPLOG_TRNS_TYPE_FILE;        
                
                 // Send Email Notification to Non-Staff User.
                 if(mLogger.isLoggable(Level.INFO)) { 
                     mLogger.info("********Debug:  Have Non-Staff Supplement Assignment Case. ");
                     mLogger.info("\n\n********Send Email/Fax Notification to Non-Staff User. ");
                 }

                 handlerUtils.sendNonStaffSuppNotification (context, workItemId, returnException);
                                 
                 if(mLogger.isLoggable(Level.INFO)) { 
                     mLogger.info("********After Send Email/Fax Notification to Non-Staff User - Supplement Request)\n\n");
                 }                                        
            //===============================================================================================
            //
            } else {   // All other cases involve deliverying an MCF Package
        
              // If original assignment, transform assignment bms to MIE and add assignment MIE as first attached object.
              if (isOriginal) {

                 if(mLogger.isLoggable(Level.INFO)) { 
                     mLogger.info("********Debug:  Have Original Assignment. ");
                 }
                 
                // Transform BMS document to a Mie document
                mieFile = converter.transformBmsAsgToMieAsg(context);

                if(mLogger.isLoggable(Level.INFO)) { 
                    mLogger.info("Successfully converted BMSAssignment document to MIE file = " +  mieFile.getAbsoluteFile());
                }            

                // Add the MIE into attachment objects list
                AttachmentObject attachmentObject1 = new AttachmentObject(mieFile, AttachmentObject.OBJ_TYPE_MIE);
                // AttachmentObject attachmentObject1 = new AttachmentObject(mieFile, 97);
                // CG: Updated for SIP2.
                attachmentObject1.setTabNumber(1);
                context.getAttachmentObjects().add(attachmentObject1);

                if(mLogger.isLoggable(Level.INFO)) { 
                    mLogger.info("*********** After attach MIE file ");
                }
                            
                // Check for case of original converted from an supplement, 
                // If SupplementConvertedToOriginalFlag is TRUE, Add additional notice text file as attachement 
                // 
                if(aaaInfoDoc != null) {

                    if(mLogger.isLoggable(Level.INFO)) { 
                         mLogger.info("*********** Have aaaInfoDoc, now check bSupplementConvertedToOriginalFlag  ");
                    }
                    
                    if(aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().isSetAssignmentDetails()) {
                        if(aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails().isSetSupplementConvertedToOriginalFlag()) {
                            bSupplementConvertedToOriginalFlag = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails().getSupplementConvertedToOriginalFlag();
                            if(mLogger.isLoggable(Level.INFO)) { 
                              mLogger.info("*********** Have aaaInfoDoc, bSupplementConvertedToOriginalFlag=  "+bSupplementConvertedToOriginalFlag);
                            }
                        }
                    }
                }

                // Special Case: Supplement Assignment Converted To Original
                //
                if (bSupplementConvertedToOriginalFlag) {

                   if(mLogger.isLoggable(Level.INFO)) { 
                       mLogger.info("Debug:  Have SupplementConvertedToOriginal case. ");
                   }
                   suppToOrigNoticeFile = handlerUtils.getsuppToOrigTextFile(userInfo, workItemId);    

                   if(suppToOrigNoticeFile.exists()) {
                      if(mLogger.isLoggable(Level.INFO)) { 
                          mLogger.info("Debug:  After handlerUtils.getsuppToOrigTextFile. suppToOrigNoticeFile exists!! ");
                      }
                   } else {
                          mLogger.warning("Debug:  After handlerUtils.getsuppToOrigTextFile. suppToOrigNoticeFile does not exist!! ");
                   }
                }
           
              // Else, supplement assignment cases.
              } else {

                if(mLogger.isLoggable(Level.INFO)) { 
                       mLogger.info("********Debug:  Have Supplement Assignment. ");
                }
                // Staff User -Supplement Case             
                if(!context.isDrp()){
    
                    // Get Original Estimate MIE (by DocumentID) from AdditionalAppraisalAssignmentInfo.xml
                    //
                    // Staff User - Supplement Assignment Case
                    //      Get OriginalEstimateDocumentID, if present, need Original Estimate MIE.
                    //
                    if(aaaInfoDoc != null) {
                        if(aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().isSetAssignmentDetails()) {
                            if(aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails().isSetRelatedEstimateDocumentID()) {
        
                                if(mLogger.isLoggable(Level.INFO)) { 
                                   mLogger.info("********Debug: Staff User - Supplement Assignment Case, have RelatedEstimateDocumentID. ");
                                }
                            
                                Long relatedEstimateDocumentID = new Long(aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails().getRelatedEstimateDocumentID());
                                if ( relatedEstimateDocumentID == null ) {
                                  throw new AssignmentDeliveryException(
                                                    AssignmentDeliveryErrorCodes.INVALID_AAAINFO_DOC_IN_MITCHELL_ENVELOPE_ERROR,
                                                   "EClaimDeliveryHandler", "deliverAssignment",
                                                   "Related EstimateDocumentId is null in AdditionalAppraisalAssignmentInfo." ); 
                                }

                                if(mLogger.isLoggable(Level.INFO)) { 
                                   mLogger.info("********Debug:  relatedEstimateDocumentID= "+relatedEstimateDocumentID.longValue());
                                }
                                
                                
                                orgEstMieFile = handlerUtils.getOrigEstimateMieFileFromDocStore(relatedEstimateDocumentID, workItemId);    
                                
                                // TODO - Need Additional exception handling when file is NULL ??
                                
                                // Add the Original Estimate MIE into attachment objects list as first attached object.
                                if( orgEstMieFile != null) { 

                                    if(mLogger.isLoggable(Level.INFO)) { 
                                         mLogger.info("********Debug:  Adding Original Estimate MIE as MCF attachement. ");
                                    }
                                    
                                    AttachmentObject attachmentObject1 = new AttachmentObject(orgEstMieFile, AttachmentObject.OBJ_TYPE_MIE);
                                    attachmentObject1.setTabNumber(1);
                                    attachmentObject1.setDesc1( "Estimate" );  // This is needed so that MCFService recognizes the
                                                                               // object as an Estimate MIE.
                                    attachmentObject1.setDesc2( "Estimate" );  // This is just to be safe.
                                    context.getAttachmentObjects().add(attachmentObject1);

                                    if(mLogger.isLoggable(Level.INFO)) { 
                                        mLogger.info("********Debug:  After Adding Original Estimate MIE as MCF attachement. ");
                                    }
                                    
                                    // Retrieve the Estimate object from CCDB
                                    estimate = handlerUtils.getEstimateFromCCDB( relatedEstimateDocumentID.longValue() );                                    
                                    if ( estimate == null ) {
                                      throw new AssignmentDeliveryException(
                                                    AssignmentDeliveryErrorCodes.BMS_MIE_ERROR,
                                                   "EClaimDeliveryHandler", "deliverAssignment",
                                                   "Estimate is null from CCDB. Estimate ID=" + relatedEstimateDocumentID.longValue() ); 
                                    }

                                }
                            }                        
                        }

                        if(mLogger.isLoggable(Level.INFO)) { 
                            mLogger.info("********Debug: Staff User - Supplement Assignment Case, \n***** Before handlerUtils.createSupplementRequestDoc");
                        }

                        // Create Supplement Request Doc (returned as a Text buffer)                        
                        String suppNotificationDocText = handlerUtils.createSupplementRequestDoc(context, workItemId);    

                        if(mLogger.isLoggable(Level.INFO)) { 
                             mLogger.info("********Debug:  After handlerUtils.createSupplementRequestDoc, suppNotificationDocText=\n "+suppNotificationDocText);
                        }

                        // Only add the suppplement request doc attachement file 
                        //      Upon successful generation of Supplement Request doc
                        //
                        if(suppNotificationDocText.length() >0 ) {
                            // Save Supplement Request Text as a new disk file
                            String fileExt = AssignmentDeliveryConstants.FILE_EXTENSION_TXT;
                            String filePrefix1 = AssignmentDeliveryConstants.SUPPLEMENT_REQUEST_PREFIX;
                            supplementRequestDocFile = handlerUtils.writeSupplementRequestDocFile(suppNotificationDocText, filePrefix1, fileExt, workItemId);    
                            if(supplementRequestDocFile.exists()) {
                                if(mLogger.isLoggable(Level.INFO)) { 
                                     mLogger.info("********Debug:  After handlerUtils.writeSupplementRequestDocFile, supp Asb FilePath=\n "+supplementRequestDocFile.getAbsoluteFile());
                                }
                            }
                            
                            if(mLogger.isLoggable(Level.INFO)) { 
                                mLogger.info("Debug:  Now Attaching Supplement Request Doc File. ");
                            }
                            
                            AttachmentObject attachmentObjectSup = new AttachmentObject(supplementRequestDocFile, MCFConstants.ATTACH_OBJ_TEXT_NOTE);
                            attachmentObjectSup.setTabNumber(1);
    
                            // Set Desc1 and Desc2 for Supplement_Request
                            attachmentObjectSup.setDesc2("Supplement-Request");
                            attachmentObjectSup.setDesc1("Supplement-Request");
    
                            context.getAttachmentObjects().add(attachmentObjectSup);
    
                            String attObjDesc1Prop = attachmentObjectSup.getDesc1();
                            mLogger.info("********** Debug: After Attaching Supplement Request Doc File, Verify attachmentObjectSup.getDesc1(), attObjDesc1Prop= "+attObjDesc1Prop);
                        }                            

                    } else {
                        if(mLogger.isLoggable(Level.INFO)) { 
                            mLogger.info("\n********Debug: Staff User - Supplement Assignment Case, have No aaaInfoDoc. ");
                        }
                    }

                  }  // End Staff Supplement Case
                           
              }  //  End Supplement case


            //  -- Add Dispatch report as first attachment.
            //
            // Create Dispatch report from MitchellEnvelope
            isRequiredDispatchReport = assignDeliveryUtils.getUserCustomSetting(
                context.getUserInfo(), 
                AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_DISPATCH_RP_FLAG);
            if(isTrue(isRequiredDispatchReport)) {
                dsFile = drBuilder.createDispatchReport(context);
                // CG: Updated for SIP2.
                AttachmentObject attachmentObject2 = new AttachmentObject(dsFile, AttachmentObject.OBJ_TYPE_DISPATCH_REPORT);
                attachmentObject2.setTabNumber(1);
                context.getAttachmentObjects().add(attachmentObject2);
            }
            
            //  -- Attach Notice text file indicating a converted Original Asg from Supplement Asg.
            //
            if (bSupplementConvertedToOriginalFlag && suppToOrigNoticeFile.exists()) {
                
                if(mLogger.isLoggable(Level.INFO)) { 
                     mLogger.info("Debug:  Now Attaching TextFile Notice - SupplementConvertedToOriginal. ");
                }
                
                AttachmentObject attachmentObject3 = new AttachmentObject(suppToOrigNoticeFile, MCFConstants.ATTACH_OBJ_TEXT_NOTE);
                attachmentObject3.setTabNumber(1);
                context.getAttachmentObjects().add(attachmentObject3);

                // Set Desc1 and Desc2 for TextFile Notice - SupplementConvertedToOriginal
                attachmentObject3.setDesc2("Notice_Supplement-Converted-To-Original");
                attachmentObject3.setDesc1("Notice_Supplement-Converted-To-Original");

                if(mLogger.isLoggable(Level.INFO)) { 
                    mLogger.info("Debug:  After Attaching TextFile Notice - SupplementConvertedToOriginal. ");
                }
                
            }

            //  -- Add other File Attachment Objects after Dispatch Report
            //
            // For All FileAttachements included in the  AdditionalAppraisalAssignmentInfoDocument,                    
            // Add File Attachements found in aaaInfoDoc into the AssignmentServiceContext as Attachment Objects 
            //
            if(aaaInfoDoc != null) {
                if(aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().isSetAssociatedAttachments()) {
                    if(aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssociatedAttachments().isSetFileAttachments()) {
                      numOfAttachments = 0;
                      numOfAttachments = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssociatedAttachments().getFileAttachments().sizeOfFileAttachmentArray();

                      if(mLogger.isLoggable(Level.INFO)) { 
                          mLogger.info("\n\n******************************************************************************************************************");         
                          mLogger.info("\n***********Debug: Before addFileAttachementsToADContext, Have aaaInfoDoc, numOfAttachments= "+numOfAttachments);
                          mLogger.info("******************************************************************************************************************\n\n");         
                      }
                      
                      if (numOfAttachments > 0) {

                          attachmentFileList = handlerUtils.addFileAttachementsToADContext(context, aaaInfoDoc, workItemId, false);    
                          
                          // TODO - Need additional Exception Handling for retrieval of Attachements ??

                          if(mLogger.isLoggable(Level.INFO)) { 
                             mLogger.info("*****Debug: deliverAssignment: After addFileAttachementsToADContext...");
                          }

                      }
                    }   
                    
                }
            }

            
            // Get Template file path from Custom Settings
            /**
             * Added custom settings of Eclaim Template File Name for staff and non staff user for
             * SIP Assignment Enhancement 0000252: Augment SIP Assignment Delivery Custom Setting for eClaim Template 
             * By:- Vandana Gautam
             * Modified Date :-05/28/2009
             */
            
            String templateDirPath=assignDeliveryUtils.getUserCustomSetting(userInfo, AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_TEMPLATE);
            
            if(mLogger.isLoggable(Level.INFO)) { 
                mLogger.info("********Successfully found the Template DIR PATH = " + templateDirPath);
            }
            
            String templateFileName;
            
            if(context.isDrp()){
                templateFileName = templateDirPath + File.separator + assignDeliveryUtils.getUserCustomSetting(userInfo, AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_TEMPLATE_FILE_FOR_NON_STAFF );

                if(mLogger.isLoggable(Level.INFO)) { 
                    mLogger.info("*******DRPUSER **************");
                    mLogger.info("********Successfully found the Template DIR FILE FOR NON STAFF = " + templateFileName);
                }
            }else{
                templateFileName = templateDirPath + File.separator + assignDeliveryUtils.getUserCustomSetting(userInfo, AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_TEMPLATE_FILE_FOR_STAFF );

                if(mLogger.isLoggable(Level.INFO)) { 
                    mLogger.info("*******not a DRPUSER **************");
                    mLogger.info("********Successfully found the Template DIR FILE FOR  STAFF = " + templateFileName);
                }
            }
        
            File templateFile = new File(templateFileName);
            
            if(mLogger.isLoggable(Level.INFO)) { 
                mLogger.info("*******templateFile **************"+ templateFile.getAbsolutePath());
            }
            /*
            if(!templateFile.exists()) {
                ErrorLoggingService.logError(
                        AssignmentDeliveryErrorCodes.TEMPLATE_FILE_NOT_FOUND, null, 
                        CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, 
                        workItemId, "Template file = " + templateFileName + " does not exist", 
                        null, 0, null);
                throw mLogger.createException(
                        AssignmentDeliveryErrorCodes.TEMPLATE_FILE_NOT_FOUND, workItemId, "Template file = " + templateFileName + " does not exist");
            }
            mLogger.info("Successfully found the Template file = " + templateFileName);
            */
            
            if(mLogger.isLoggable(Level.INFO)) { 
                mLogger.info("\n\n*****Debug deliverAssignment: Before MCFService - MCFBuildRequest...");
            }
            
            // Create MCF file from BMSAssignment, Template, Attachment Objects and UserInfo
            MCFServiceEJBRemote ejb = MCFServiceEJBClient.getMCFServiceEJB();
            
            // CG: Updated for SIP2.
            MCFBuildRequest mcfBuildReq = new MCFBuildRequest(userInfo, ciecaDoc, templateFile,
                    context.getAttachmentObjects(), MCFServiceConstants.MCF_ASSIGNMENT_VER_34,
                    context.isDrp(), context.getDrpUserInfo());
            
            mcfBuildReq.setWorkAssignmentId(context.getWorkAssignmentId());
            
            // If we have an estimate then call MCFService passing in the commit date
            if ( estimate != null ) {
              MCFBuildRequestNVPairs nvPairs = new MCFBuildRequestNVPairs();
              nvPairs.addAdminEstimateCommitDate( estimate.getCommitDate() );
              mcfFile = ejb.buildAssignmentMCF( mcfBuildReq, nvPairs.getPairs() );
            }
            else {
              mcfFile = ejb.buildAssignmentMCF(mcfBuildReq);
            }
            
            if(mLogger.isLoggable(Level.INFO)) { 
                mLogger.info("*****Debug deliverAssignment: After MCFService - MCFBuildRequest...\n\n");
                mLogger.info("Successfully created MCF File = " + mcfFile.getAbsoluteFile());
            }

            // For EvtPub integration, rename the MIE as follows - let filename = original filename:
            // newfilename = asg_CoCd_UserId___WorkItemId___filename
            // Note, the original filename's first field is the ClaimNumber
			String mcfFilename = mcfFile.getName();
			
			String coCdForMcfFilename = null;
			String userIdForMcfFilename = null;

			// Determine file prefixes.
			if (context.isDrp() == false) {
				coCdForMcfFilename = userInfo.getUserInfo().getOrgCode();
				userIdForMcfFilename = userInfo.getUserInfo().getUserID();
			}
			else {
				coCdForMcfFilename = context.getUserInfo().getUserInfo().getOrgCode();
				userIdForMcfFilename = context.getUserInfo().getUserInfo().getUserID();
			}

			String mcfNewFilename=null;
            /**
             * SIP Assignment MCF file name has been modified for claimId & ExposureId
             * Resolution: CID_XXXXX & EID_XXXXX added in mcf file name. 
             * Modified Date :-05/07/2010
             * Modified By : Preet Singh
             */
           
            HandlerUtils utilObj=new HandlerUtils();
            String[] result=utilObj.getClaimExposureIdsResult(context);
            if(result!=null){
               String claimId=result[0].trim();
               String claimExposureId=result[1].trim();
               //Format : asg_companyCode_userId___workItemId___CID-XXXXX_EID-XXXXX___mcfFilename
               //Example : asg_ME_RBME01___c77c5e2e-4c9d-7074-9fc3-2fc8000f65ed___CID-509066_EID-316581___ME-1221212-30_20100507-031423.754-21.MCF
                    
                mcfNewFilename = "asg_" + coCdForMcfFilename + "_" + userIdForMcfFilename
                        + "___" + workItemId + "___" +"CID-"+claimId+ "_" +"EID-"+claimExposureId+"___"+ mcfFilename;
                        
                        //mLogger.info("ClaimId & Exposure ID : " + claimId+ ","+claimExposureId);
                        //mLogger.info("NewFileName : " + mcfNewFilename);
            }else{
                mLogger.info("Default MCFFilename created.");
                //Set old mcf file name in case of value of result variable is false
                mcfNewFilename = "asg_" + coCdForMcfFilename + "_" + userIdForMcfFilename
                        + "___" + workItemId + "___" + mcfFilename;   
            }
            mLogger.info("MCFFileName : " + mcfNewFilename);
			//
			String mcfFileDir = mcfFile.getParent();
			String mcfNewFullpath = mcfFileDir + "/" + mcfNewFilename;
			
			// File (or directory) with new name
			File newMcfFile = new File(mcfNewFullpath);

			// Rename file.
			boolean success = mcfFile.renameTo(newMcfFile);
			if (!success) {
				ErrorLoggingService
						.logError(AssignmentDeliveryErrorCodes.GENERAL_ERROR, null, CLASS_NAME,
								methodName, ErrorLoggingService.SEVERITY.FATAL, workItemId,
								"Could not rename file " + mcfFile + " to " + mcfNewFullpath, null,
								0, null);
			}
			else {
                if(mLogger.isLoggable(Level.INFO)) { 
        			mLogger.info("Successfully renamed MCF File to: " + newMcfFile.getAbsoluteFile());
                }
			}

            // Further logic relies on the 'old' filename var.
            mcfFile = newMcfFile;

            if(mLogger.isLoggable(Level.INFO)) { 
                mLogger.info("\n\n*****Debug deliverAssignment: Now archiving the MCF file: "+newMcfFile.getAbsoluteFile());
            }
            
            // Archieve MCF File
            arid = handlerUtils.archieveAssignment(userInfo, mcfFile, workItemId);
            mLogger.info("Successfully archived MCF file, Archive ID = " +arid);

            // Call ECInBox service to send the MCF file
            if(context.isDrp() == false) {
                mLogger.info("Delivering assignment to ECInbox Service for estimator id =  " + userInfo.getUserInfo().getUserID());
                ECInboxSvcClient.sendFile(userInfo.getUserInfo().getOrgCode(), 
                                      userInfo.getUserInfo().getUserID(), 
                                      mcfFile.getAbsolutePath(), 
                                      true, workItemId);
            }
			else {

                if(mLogger.isLoggable(Level.INFO)) { 
                    mLogger.info("Delivering assignment to ECInbox Service for estimator id =  "
						+ context.getDrpUserInfo().getUserInfo().getUserID()
						+ " and reviewer id = "
						+ context.getUserInfo().getUserInfo().getUserID());
                }

				// Add reviewer info to EClaim Inbox call, so the Inbox applog contains the insurance carrier details,
				// later required for the EvtPub output.
                ECInboxSvcClient.sendFile(context.getDrpUserInfo().getUserInfo().getOrgCode(), 
                                      context.getDrpUserInfo().getUserInfo().getUserID(),
                                      // Add reviewer info.
                                      context.getUserInfo().getUserInfo().getOrgCode(),
                                      context.getUserInfo().getUserInfo().getUserID(),
                                      mcfFile.getAbsolutePath(), 
                                      true, workItemId);
            }
            
            if(mLogger.isLoggable(Level.INFO)) { 
                mLogger.info("Successfully delivered the MCF file to ECInbox Service");
            }

            if(mLogger.isLoggable(Level.INFO)) { 
                mLogger.info("\n\n*****Debug deliverAssignment: Now submitting APPLOG Event!!\n\n");
            }
            
            /***
             * 
             *  Moved Applog to allow for all assignment cases (inc Shop Supplements)
                        // Write the log in App Log (call super class method)
                        logEClaimSubmissionEvent(context, ciecaDoc, status, arid, workItemId);
            
                        if(mLogger.isLoggable(Level.INFO)) { 
                            mLogger.info("Successfully logged the EClaim Submission Event.");
                        }
             * 
             * 
             */
            
          } // End Else - All other cases involve deliverying an MCF Package

          // Write the log in App Log (call super class method)
          logEClaimSubmissionEvent(context, ciecaDoc, status, arid, workItemId,appLogTxType);

          if(mLogger.isLoggable(Level.INFO)) { 
              mLogger.info("Successfully logged the EClaim Submission Event.");
          }
                  
		} catch (AssignmentDeliveryException adse) {
                throw adse;   
		} catch (Exception e) {
			mLogger.severe(e.getMessage());
			ErrorLoggingService.logError(
                    AssignmentDeliveryErrorCodes.GENERAL_ERROR, null, 
                    CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, 
                    workItemId, e.getMessage(), null, 0, e);
            throw mLogger.createException( 
                    AssignmentDeliveryErrorCodes.GENERAL_ERROR, workItemId, 
                    e);
		} finally {
            try {
               if(mieFile!=null) {
                  if(mieFile.exists()) {
                    mieFile.delete();
                    if(mLogger.isLoggable(Level.INFO)) { 
                         mLogger.info("Successfully cleaned the mie file "  
                                + mieFile.getName() + " from " 
                                + AssignmentDeliveryConfig.getTempDir());
                    }
                  }
               }
               final String origEstFileMessage = "Successfully cleaned the orgEstMieFile mie file ";
               final String supReqDocMessage = "Successfully cleaned the Supplement Request Doc file ";
               cleanupFileIfExists(orgEstMieFile, origEstFileMessage);
               cleanupFileIfExists(supplementRequestDocFile, supReqDocMessage);
               if(isTrue(isRequiredDispatchReport)) {                
                    dsFile.delete();
                    if(mLogger.isLoggable(Level.INFO)) { 
                        mLogger.info("Successfully cleaned the dispatch report file "  
                                + dsFile.getName() + " from " 
                                + AssignmentDeliveryConfig.getTempDir());
                    }
               }
               if(numOfAttachments >0) {
                  for(int i = 0; i < numOfAttachments; i++) {
                      if(attachmentFileList[i].exists()) {
                        attachmentFileList[i].delete();
                        if(mLogger.isLoggable(Level.INFO)) { 
                            mLogger.info("Successfully cleaned the attachment file: "  
                                    + attachmentFileList[i].getName() + " from " 
                                    + AssignmentDeliveryConfig.getTempDir());
                        }
                      }
                      if(mLogger.isLoggable(Level.INFO)) { 
                         mLogger.info("\nFinished Attachment file cleanup!!\n");
                      }
                  }
               }
                    
            } catch (Exception e) {
                mLogger.severe(e.getMessage());
                ErrorLoggingService.logError(
                        AssignmentDeliveryErrorCodes.GENERAL_ERROR, null, 
                        CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, 
                        workItemId, e.getMessage(), null, 0, e);
                throw mLogger.createException( 
                        AssignmentDeliveryErrorCodes.GENERAL_ERROR, workItemId, 
                        e);
            }

            mLogger.exiting(CLASS_NAME, methodName);
		}
	}

	/**
	 * @param orgEstMieFile
	 * @param origEstFileMessage
	 * @throws AssignmentDeliveryException
	 */
	public void cleanupFileIfExists(File orgEstMieFile,
			final String origEstFileMessage) throws AssignmentDeliveryException {
		if(orgEstMieFile!=null) {
		      if(orgEstMieFile.exists()) {
		        orgEstMieFile.delete();
		        if(mLogger.isLoggable(Level.INFO)) { 
					mLogger.info(origEstFileMessage  
		                    + orgEstMieFile.getName() + " from " 
		                    + AssignmentDeliveryConfig.getTempDir());
		        }
		      }
		   }
	}

    //
    // --  New method for Jetta/SIP3.5 - 
    //
	public void cancelAssignment(AssignmentServiceContext context) throws AssignmentDeliveryException {
    
		final String methodName = "cancelAssignment(AssignmentServiceContext context)";
		mLogger.entering(CLASS_NAME, methodName);

        // TODO is validation required for this ADS Cancel interface? 

        // Validate the input param
        // context.validate();
        // mLogger.fine("Input AssignmentServiceContext is valid.");

        int status = 0;
		long arid = 0;  // NOTE: no archive ID for Cancel interface, nothing to archive.
        String workItemId = context.getWorkItemId();

        String appLogTxType = ECLAIM_CANCELLATION_ALERT_SENT_SUCCESS_APPLOG_TRNS_TYPE_FILE;        

        HandlerUtils handlerUtils = new HandlerUtils();
        
        try {
            // Get CIECADocument from MitchellEnvelope
            CIECADocument ciecaDoc = handlerUtils.getCiecaDocFromMitchellEnv(context.getMitchellEnvDoc(), workItemId);
        
            // Get ClaimNumber from Cieca Doc
            String claimNumber = "";
            if(ciecaDoc != null) {
               claimNumber = ciecaDoc.getCIECA().getAssignmentAddRq().getClaimInfo().getClaimNum();
            } 

            if(mLogger.isLoggable(Level.INFO)) { 
                 mLogger.info("***** cancelAssignment: DEBUG - In ciecaDoc, claimNumber = [ " + claimNumber + " ]");
            }
            
            // Determine Assignment Type - Must be either Original or Supplement
            boolean isOriginal = false;
            boolean isOriginalOrSupplement = false;
            
            isOriginal =  handlerUtils.isOriginalAssignment(ciecaDoc);
            isOriginalOrSupplement =  handlerUtils.isOriginalOrSupplementAssignment(ciecaDoc);

            // If Not Original Or Supplement, reject delivery and throw fatal error
            if (!isOriginalOrSupplement) {
                
                mLogger.severe("Debug:  Not Original Or Supplement, reject delivery and throw error. ");

                mLogger.severe("MitchellEnvelope contains CIECA Document with an Invalid Assignment Type.");
                ErrorLoggingService.logError(
                        AssignmentDeliveryErrorCodes.INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR, 
                        null, CLASS_NAME, methodName, 
                        ErrorLoggingService.SEVERITY.FATAL, workItemId, 
                        "MitchellEnvelope contains CIECA Document with an Invalid Assignment Type.", null, 0, null);                
                throw mLogger.createException( 
                        AssignmentDeliveryErrorCodes.INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR, workItemId);                
            }
            
            
            // Determine correct UserInfo to provide to EClaim Alert Service - UserInfo or DRPUserInfo
            //
            UserInfoDocument userInfoDoc = null;
            if(context.isDrp()){
                userInfoDoc = context.getDrpUserInfo();
                if(mLogger.isLoggable(Level.INFO)) { 
                   mLogger.info("*******DRPUSER for Cancellation **************");
                   mLogger.info("********Successfully found DRP UserInfo  = " + userInfoDoc);
                }
            }else{
                userInfoDoc = context.getUserInfo();
                if(mLogger.isLoggable(Level.INFO)) { 
                   mLogger.info("*******not a DRPUSER for Cancellation **************");
                   mLogger.info("********Successfully found non-DRP UserInfo = " + userInfoDoc);
                }
            }
    
            // Design/Implementation Assumptions & Constraints (09.15.09) --
            //
            //  1a. CompanyCode and UserID will be obtained from the proper UserInfo doc provided in the ADS Context.
            //  1b. workItemID will be obtained from the ADS Context.
            //  2.  The Message Content for "msg" sent to ECM AlertService
            //      will be provided in a SET File Param with a ciecaClaimNumber appended.
            //  3.  The Originator will be a constant defined as the calling service/app.
            //     (  see reference ECALIM_ALERT_ORIGIN - StateFarmMCFJava/WorkflowConstants.java )    
    
            // Submit the Cancellation Global Alert to EClaim --
            //  Note: UserInfoDoc is a Required Input --
            if(userInfoDoc != null) {
                handlerUtils.sendCancellationECAlert(claimNumber, userInfoDoc, isOriginal, workItemId);
            
                // Write the log in App Log (call super class method)
                //       NOTE: no archive ID (arid) for Cancel interface, nothing to archive
                arid = 0;  
                logEClaimSubmissionEvent(context, ciecaDoc, status, arid, workItemId,appLogTxType);
                if(mLogger.isLoggable(Level.INFO)) { 
                    mLogger.info("Successfully logged the EClaim Submission Event for Cancellation.");
                }

            } else {
                mLogger.severe("cancelAssignment: Assignment Delivery Svc Cancellation unable to submit EClaim Alert - missing required Inputs..");
               
                ErrorLoggingService.logError(
                        AssignmentDeliveryErrorCodes.INCOMPLETE_CONTEXT_INFO_FOR_ECM_ALERT_ERROR, 
                        null, CLASS_NAME, methodName, 
                        ErrorLoggingService.SEVERITY.FATAL, workItemId, 
                        "Assignment Delivery Service Cancellation unable to submit EClaim Alert.", null, 0, null);                
                throw mLogger.createException( 
                        AssignmentDeliveryErrorCodes.INCOMPLETE_CONTEXT_INFO_FOR_ECM_ALERT_ERROR, workItemId);                

            }
        
		} catch (AssignmentDeliveryException adse) {
                throw adse;   
		} catch (Exception e) {
			mLogger.severe(e.getMessage());
			ErrorLoggingService.logError(
                    AssignmentDeliveryErrorCodes.GENERAL_ERROR, null, 
                    CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, 
                    workItemId, e.getMessage(), null, 0, e);
            throw mLogger.createException( 
                    AssignmentDeliveryErrorCodes.GENERAL_ERROR, workItemId, 
                    e);
		} finally {
            try {
                if(mLogger.isLoggable(Level.INFO)) { 
                    mLogger.info("Successfully completed cancelAssignment.");
                }

                // mieFile.delete();
                // mLogger.info("Successfully cleaned the mie file "  
                //            + mieFile.getName() + "from " 
                //            + AssignmentDeliveryConfig.getTempDir());
            } catch (Exception e) {
                mLogger.severe(e.getMessage());
                ErrorLoggingService.logError(
                        AssignmentDeliveryErrorCodes.GENERAL_ERROR, null, 
                        CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, 
                        workItemId, e.getMessage(), null, 0, e);
                throw mLogger.createException( 
                        AssignmentDeliveryErrorCodes.GENERAL_ERROR, workItemId, 
                        e);
            }

            mLogger.exiting(CLASS_NAME, methodName);
		}
    }        
}
