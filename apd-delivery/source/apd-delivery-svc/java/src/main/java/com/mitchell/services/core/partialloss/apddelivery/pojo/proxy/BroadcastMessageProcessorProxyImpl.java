package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.TrackingInfoDocument;
import com.mitchell.services.core.partialloss.apddelivery.utils.MessagingContext;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.mitchell.services.core.messagebus.MessageDispatcher;
import com.mitchell.services.core.messagebus.WorkflowMsgUtil;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.utils.misc.AppUtilities;

/**
 * The Class BroadcastMessageProcessorProxyImpl.
 */
public class BroadcastMessageProcessorProxyImpl 
                            implements BroadcastMessageProcessorProxy { 
                                
    /**
     * class name.
     */
    private static final String CLASS_NAME = BroadcastMessageProcessorProxyImpl.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    /**
     * This method will call BroadcastMessageProcessor service.
     * 
     * @throws MitchellException
     * Mitchell Exception
     */
    public void sendMessage() throws MitchellException {
       
    }
    
    public void publishToMessageBus(MessagingContext msgContext) 
                                                    throws MitchellException {
        
        String methodName = "publishToMessageBus";
        logger.entering(CLASS_NAME, methodName);
               
        TrackingInfoDocument trackInfoDoc = WorkflowMsgUtil.createTrackingInfo(
        APDDeliveryConstants.APP_NAME, // Name of the source application
        APDDeliveryConstants.BUSINESS_SERVICE_NAME,   // Name of the Business Service
        APDDeliveryConstants.MODULE_NAME, // Name of the source module
        msgContext.getWorkItemId(), // workItemId of the business service instance// This should be a UUID
        msgContext.getUserInfoDoc(), // UserInfoDoc for the user associated with the instance of this workflow
        msgContext.getComment());  // COMMENT
    
        // init the msg
        MitchellWorkflowMessageDocument mwmDoc = WorkflowMsgUtil.createWorkflowMessage(trackInfoDoc);
        
        try {
            // insert ME
            mwmDoc = WorkflowMsgUtil.insertDataBlock(mwmDoc, 
                                    msgContext.getMitchellEnvelopeDocument(), 
                                    msgContext.getEventId());
            if (logger.isLoggable(Level.FINE)) {
            	logger.fine("MWM document is:\n" + mwmDoc.toString());
            }
            
            
            
            MessageDispatcher.postMessage(
                                                    msgContext.getEventId(),
                                                    mwmDoc.xmlText(), 
                                                    null, 
                                                    null);
                                                    
        } catch (Exception e) {
            throw new MitchellException(
                            APDDeliveryConstants.ERROR_PUBLISHING_TO_MESSAGE_BUS,
                            CLASS_NAME,
                            methodName,
                            msgContext.getWorkItemId(),
                            APDDeliveryConstants.ERROR_PUBLISHING_TO_MESSAGE_BUS_MSG
                                + "\n" 
                                + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
    }   
} 
