package com.mitchell.services.core.partialloss.apddelivery.utils; 

import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.TrackingInfoDocument;
import com.mitchell.services.core.messagebus.MessageDispatcher;
import com.mitchell.services.core.messagebus.WorkflowMsgUtil;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.utils.misc.AppUtilities;


/**
 * <p>
 * This class common utility methods used in APD Delivery Service.
 * </p>
 * 
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 */
public class CommonUtilImpl implements CommonUtil { 
    /**
     * class name.
     */
    private static final String CLASS_NAME =  CommonUtilImpl.class.getName();
    
    /**
     * logger.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private SystemConfigurationProxy systemConfigurationProxy;
    
    private APDCommonUtilProxy apdCommonUtilProxy;
    
    /**
     * Sole constructor.  (For invocation by subclass constructors, 
     * typically implicit.)
     */
    public CommonUtilImpl() {
    
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.CommonUtil#isNullOrEmpty(java.lang.String)
	 */
    public boolean isNullOrEmpty(String value) {
        boolean isNullOrEmpty = false;
        if (value == null || value.trim().equals("")) {
            isNullOrEmpty = true;
        }
        return isNullOrEmpty;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.CommonUtil#publishToMessageBus(com.mitchell.services.core.partialloss.apddelivery.utils.MessagingContext)
	 */
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
        MitchellWorkflowMessageDocument mwmDoc = 
                        WorkflowMsgUtil.createWorkflowMessage(trackInfoDoc);
        try {
            // insert ME
            mwmDoc = WorkflowMsgUtil.insertDataBlock(mwmDoc, 
                                    msgContext.getMitchellEnvelopeDocument(), 
                                    msgContext.getEventId());
                          
            apdCommonUtilProxy.logFINEMessage("Message posted to MB " + mwmDoc.toString());
                      
            
            // Fix 117663 : using systemConfigurationProxy for SystemConfiguration.
            String publishToMessageQueue = 
            	systemConfigurationProxy.getPublishToMessageQueue();
                    
            
            if ("true".equalsIgnoreCase(publishToMessageQueue)) {
                MessageDispatcher.postMessage(
                                                    msgContext.getEventId(),
                                                    mwmDoc.xmlText(), 
                                                    null, 
                                                    null);
            } 
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

	/**
	 * @return the systemConfigurationProxy
	 */
	public SystemConfigurationProxy getSystemConfigurationProxy() {
		return systemConfigurationProxy;
	}

	/**
	 * @param systemConfigurationProxy the systemConfigurationProxy to set
	 */
	public void setSystemConfigurationProxy(
			SystemConfigurationProxy systemConfigurationProxy) {
		this.systemConfigurationProxy = systemConfigurationProxy;
	}

	/**
	 * @return the apdCommonUtilProxy
	 */
	public APDCommonUtilProxy getApdCommonUtilProxy() {
		return apdCommonUtilProxy;
	}

	/**
	 * @param apdCommonUtilProxy the apdCommonUtilProxy to set
	 */
	public void setApdCommonUtilProxy(APDCommonUtilProxy apdCommonUtilProxy) {
		this.apdCommonUtilProxy = apdCommonUtilProxy;
	}
	
} 
