package com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.eclaim;

import java.io.File;
import java.util.logging.Level;

import com.cieca.bms.CIECADocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.ExtractClassName;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractHandlerUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractMCFAsgnDelHandler;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.services.core.mcfsvc.AttachmentObject;
import com.mitchell.utils.mcf.MCFConstants;

public class EClaimDeliveryHandler extends  AbstractMCFAsgnDelHandler {
	private static final String CLASS_NAME = ExtractClassName.from(EClaimDeliveryHandler.class);
	protected String getClassName(){
		return CLASS_NAME;
	}

	protected File[] prepareAdditionalAttachments(
			AssignmentServiceContext context, final String workItemId,
			File suppToOrigNoticeFile,
			final AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc)
			throws Exception {
		File[] attachmentFileList;
		// -- Attach Notice text file indicating a converted Original
		// Asg from Supplement Asg.
		//
		attachSupplementToOriginalConversionNotice(context,
				suppToOrigNoticeFile,
				wasSupplementConvertedToOriginal(aaaInfoDoc));
		// -- Add other File Attachment Objects after Dispatch Report
		//
		// For All FileAttachements included in the
		// AdditionalAppraisalAssignmentInfoDocument,
		// Add File Attachements found in aaaInfoDoc into the
		// AssignmentServiceContext as Attachment Objects
		//
		attachmentFileList = carryOverAttachementsFromAaaInfoDoc(
				context, workItemId, handlerUtils, aaaInfoDoc);
		return attachmentFileList;
	}

	public int getDispatchReportType() {
		return AttachmentObject.OBJ_TYPE_DISPATCH_REPORT;
	}

	public boolean doesProtocolAcceptAdditionAttachments() {
		return true;
	}

	private File[] carryOverAttachementsFromAaaInfoDoc(AssignmentServiceContext context,
			String workItemId, AbstractHandlerUtils handlerUtils,
			AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc)
			throws Exception {
		File[] attachmentFileList = new File[0];
		if(aaaInfoDoc != null) {
		    if(aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().isSetAssociatedAttachments()) {
		        File[] attachmentFileList1 = attachmentFileList;
				int numOfAttachments;
				if(aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssociatedAttachments().isSetFileAttachments()) {
				  numOfAttachments = 0;
				  numOfAttachments = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssociatedAttachments().getFileAttachments().sizeOfFileAttachmentArray();
				
				  if(mLogger.isLoggable(Level.INFO)) { 
				      mLogger.info("\n\n******************************************************************************************************************");         
				      mLogger.info("\n***********Debug: Before addFileAttachementsToADContext, Have aaaInfoDoc, numOfAttachments= "+numOfAttachments);
				      mLogger.info("******************************************************************************************************************\n\n");         
				  }
				
				  if (numOfAttachments > 0) {
				
				      attachmentFileList1 = handlerUtils.addFileAttachementsToADContext(context, aaaInfoDoc, workItemId, false);    
				      
				      // TODO - Need additional Exception Handling for retrieval of Attachements ??
				
				      if(mLogger.isLoggable(Level.INFO)) { 
				         mLogger.info("*****Debug: deliverAssignment: After addFileAttachementsToADContext...");
				      }
				
				  }
				}
				attachmentFileList = attachmentFileList1;   
		        
		    }
		}
		return attachmentFileList;
	}

	private void attachSupplementToOriginalConversionNotice(
			AssignmentServiceContext context, File suppToOrigNoticeFile,
			boolean wasSupplementConvertedToOriginal) {
		if (wasSupplementConvertedToOriginal && suppToOrigNoticeFile.exists()) {
		    
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
	}

	protected File attachSupplimentNotificationDocumentTextIfNeeded(
			AssignmentServiceContext context, String workItemId,
			AbstractHandlerUtils handlerUtils, File supplementRequestDocFile)
			throws Exception {
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
		return supplementRequestDocFile;
	}

	protected File extractSupplimentToOriginalNoticeFileIfRequired(
			String workItemId, UserInfoDocument userInfo,
			AbstractHandlerUtils handlerUtils) throws Exception {
		File suppToOrigNoticeFile;
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
		return suppToOrigNoticeFile;
	}

	//
    // --  New method for Jetta/SIP3.5 - 
    //
	public void cancelAssignment(AssignmentServiceContext context) throws AssignmentDeliveryException {
    
		final String thisMethod = "cancelAssignment(AssignmentServiceContext context)";
		entering(thisMethod);

        // TODO is validation required for this ADS Cancel interface? 

        // Validate the input param
        // context.validate();
        // mLogger.fine("Input AssignmentServiceContext is valid.");

		long arid = 0;  // NOTE: no archive ID for Cancel interface, nothing to archive.
        String workItemId = context.getWorkItemId();

        String appLogTxType = ECLAIM_CANCELLATION_ALERT_SENT_SUCCESS_APPLOG_TRNS_TYPE_FILE;        

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
            

            // If Not Original Or Supplement, reject delivery and throw fatal error
			rejectDeliveryIfNeitherOriginalNorSuppliment(thisMethod, workItemId, ciecaDoc);
            
            // Determine Assignment Type - Must be either Original or Supplement
            boolean isOriginal = false;
            
            isOriginal =  handlerUtils.isOriginalAssignment(ciecaDoc);
            
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
                logEClaimSubmissionEvent(context, ciecaDoc, arid, workItemId,appLogTxType);
                if(mLogger.isLoggable(Level.INFO)) { 
                    mLogger.info("Successfully logged the EClaim Submission Event for Cancellation.");
                }

            } else {
                mLogger.severe("cancelAssignment: Assignment Delivery Svc Cancellation unable to submit EClaim Alert - missing required Inputs..");
               
                errorLoggingService.logError(
                        AssignmentDeliveryErrorCodes.INCOMPLETE_CONTEXT_INFO_FOR_ECM_ALERT_ERROR, 
                        null, getClassName(), thisMethod, 
                        ErrorLoggingService.SEVERITY.FATAL, workItemId, 
                        "Assignment Delivery Service Cancellation unable to submit EClaim Alert.", null, 0, null);                
                throw mLogger.createException( 
                        AssignmentDeliveryErrorCodes.INCOMPLETE_CONTEXT_INFO_FOR_ECM_ALERT_ERROR, workItemId);                

            }
        
		} catch (AssignmentDeliveryException adse) {
                throw adse;   
		} catch (Exception e) {
			logException(thisMethod, workItemId, e);
		} finally {
            try {
                if(mLogger.isLoggable(Level.INFO)) { 
                    mLogger.info("Successfully completed cancelAssignment.");
                }
            } catch (Exception e) {
                logException(thisMethod, workItemId, e);
            }
            mLogger.exiting(getClassName(), thisMethod);
		}
    }

	protected boolean isMcfDestinationRequired() {
		return false;
	}
}
