package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;

import org.apache.xmlbeans.XmlException;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.TrackingInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoType;
import com.mitchell.schemas.appraisalassignment.MOIDetailsType;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.MessageBusProxy;
import com.mitchell.services.core.messagebus.WorkflowMsgUtil;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class MessageBusHandlerImpl implements MessageBusHandler{
	private static final String CLASS_NAME=MessageBusHandlerImpl.class.getSimpleName();
	private static final Logger logger = Logger.getLogger(CLASS_NAME);
	private static final String WORK_ASSIGNEMNT = "workAssignment";
	private static final String WOR_ASG_DOC = "WorkAssignmentDocument";
	private static final String SAVE_SEND_NOTIFY_MSG = "SaveAndSend Notification Message";
	
	private MessageBusProxy messageBusProxy;
		
	public MessageBusProxy getMessageBusProxy() {
		return messageBusProxy;
	}

	public void setMessageBusProxy(MessageBusProxy messageBusProxy) {
		this.messageBusProxy = messageBusProxy;
	}

	public void publishEventToMessageBus(final int subscribedEvent,
				final WorkAssignmentDocument workAssignmentDocument,
				final UserInfoDocument userInfoDocument) throws MitchellException, XmlException, JMSException{
        final MitchellEnvelopeHelper helper = MitchellEnvelopeHelper.newInstance();
		helper.addNewEnvelopeBody(WORK_ASSIGNEMNT, workAssignmentDocument, WOR_ASG_DOC);
		
		final TrackingInfoDocument trackInfoDoc
        = WorkflowMsgUtil.createTrackingInfo(
                    AppraisalAssignmentConstants.APP_NAME,
                    AppraisalAssignmentConstants.APP_NAME,
                    AppraisalAssignmentConstants.MODULE_NAME,
                    workAssignmentDocument.getWorkAssignment()
                    .getPrimaryIDs().getWorkItemID(),
                    userInfoDocument,
                    "SaveAndSend Notification Message");
        MitchellWorkflowMessageDocument mwmDocument = WorkflowMsgUtil.createWorkflowMessage(trackInfoDoc);
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Publishing message to messageBus" + mwmDocument);
		}
		mwmDocument = WorkflowMsgUtil.insertDataBlock(mwmDocument, helper.getDoc(), subscribedEvent);
  
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Publishing message to messageBus after inserting data block to MWM" + mwmDocument);
		}
        messageBusProxy.postMessage(mwmDocument);
		
	}
	
	/**
	 * This method is overloaded method to add name value pair to
	 * mwm doc if additional appraisal assignment info doc is present.
	 * @param subscribedEvent
	 * @param workAssignmentDocument
	 * @param userInfoDocument
	 * @param additionalAprAsgnmentInfoDoc
	 * @throws MitchellException
	 * @throws XmlException
	 * @throws JMSException
	 */
	public void publishEventToMessageBus(final int subscribedEvent,
			final WorkAssignmentDocument workAssignmentDocument,
			final UserInfoDocument userInfoDocument, AdditionalAppraisalAssignmentInfoDocument additionalAprAsgnmentInfoDoc) throws MitchellException, XmlException, JMSException{
	
		final MitchellEnvelopeHelper helper = MitchellEnvelopeHelper.newInstance();
		helper.addNewEnvelopeBody(WORK_ASSIGNEMNT, workAssignmentDocument, WOR_ASG_DOC);
		String moiType = getMoiTypeFromAdditionalAprAsgInfoDoc(additionalAprAsgnmentInfoDoc);
		if (moiType != null) {
			helper.addEnvelopeContextNVPair(
					AppraisalAssignmentConstants.MOI_TYPE, moiType);
		}
        
        final TrackingInfoDocument trackInfoDoc
        = WorkflowMsgUtil.createTrackingInfo(
                    AppraisalAssignmentConstants.APP_NAME,
                    AppraisalAssignmentConstants.APP_NAME,
                    AppraisalAssignmentConstants.MODULE_NAME,
                    workAssignmentDocument.getWorkAssignment()
                    .getPrimaryIDs().getWorkItemID(),
                    userInfoDocument,
                    "SaveAndSend Notification Message");
        MitchellWorkflowMessageDocument mwmDocument = WorkflowMsgUtil.createWorkflowMessage(trackInfoDoc);
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Publishing message to messageBus" + mwmDocument);
		}
		mwmDocument = WorkflowMsgUtil.insertDataBlock(mwmDocument, helper.getDoc(), subscribedEvent);
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Publishing message to messageBus after inserting data block to MWM" + mwmDocument);
		}
        messageBusProxy.postMessage(mwmDocument);
}	
	/**
	 * This method does fetch moi type
	 * from additional appraisal assignment 
	 * info doc 
	 * @param aditionalAprAsgInfoDoc
	 * @return
	 */
	private String getMoiTypeFromAdditionalAprAsgInfoDoc(
			final AdditionalAppraisalAssignmentInfoDocument aditionalAprAsgInfoDoc) {
		String moiType = null;
		if (aditionalAprAsgInfoDoc != null
				&& aditionalAprAsgInfoDoc
						.getAdditionalAppraisalAssignmentInfo() != null) {
			AdditionalAppraisalAssignmentInfoType additionalAprAsgInfoType = aditionalAprAsgInfoDoc
					.getAdditionalAppraisalAssignmentInfo();
			if (additionalAprAsgInfoType.getMOIDetails() != null) {
				MOIDetailsType moiDetailsType = additionalAprAsgInfoType
						.getMOIDetails();
				if (moiDetailsType.getTempUserSelectedMOI() != null) {
					moiType = moiDetailsType.getTempUserSelectedMOI()
							.getMethodOfInspection();
				}
			}
		}
		return moiType;
	}

}