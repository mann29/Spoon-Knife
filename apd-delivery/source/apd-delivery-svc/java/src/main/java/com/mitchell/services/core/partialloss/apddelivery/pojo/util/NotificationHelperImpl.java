package com.mitchell.services.core.partialloss.apddelivery.pojo.util;

import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDBroadcastMessageType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.core.notification.types.EmailRequestDocument;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.NotificationProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;

public class NotificationHelperImpl implements NotificationHelper {
    
    /**
     * class name.
     */
    private static final String CLASS_NAME = 
                            NotificationHelperImpl.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private NotificationProxy notificationProxy;
    private XsltTransformer xsltTransformer;
    private SystemConfigurationProxy systemConfigurationProxy;
    
    /**
     * @param recipientUserInfoDoc
     * @param apdContext
     * @thows MitchellException
     * Mitchell Exception
     */
    public void sendEmailToRecipient(
                                UserInfoDocument recipientUserInfoDoc, 
                                APDDeliveryContextDocument apdContext)
                                throws MitchellException {
    	String methodName = "sendEmailToRecipient";
    	logger.entering(CLASS_NAME, methodName);
        UserInfoType recipientUserInfo = recipientUserInfoDoc.getUserInfo();
        APDBroadcastMessageType bMsg = 
            apdContext.getAPDDeliveryContext().getAPDBroadcastMessage();
        UserInfoType senderUserInfo = 
            bMsg.getAPDCommonInfo().getSourceUserInfo().getUserInfo();
        UserInfoDocument senderUserInfoDoc = 
                                        UserInfoDocument.Factory.newInstance();
        senderUserInfoDoc.setUserInfo(senderUserInfo);
        
        if (recipientUserInfo.isSetEmail()) {
            EmailRequestDocument emailReqDoc = 
                notificationProxy.buildEmailRequest(
                                            systemConfigurationProxy.getSenderName(), 
                                            systemConfigurationProxy.getSenderEmailId(), 
                                            recipientUserInfo.getEmail(), 
                                            bMsg.getMessageSubject(), 
                                            getTransformedMessageContent(
                                                apdContext.xmlText()));
            notificationProxy.notifyByEmail(
                                emailReqDoc, 
                                senderUserInfoDoc, 
                                bMsg.getAPDCommonInfo().getWorkItemId(), 
                                APDDeliveryConstants.APP_NAME, 
                                APDDeliveryConstants.MODULE_NAME, 
                                APDDeliveryConstants.BUSINESS_SERVICE_NAME);
        }
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * @param apdContext
     * @thows MitchellException
     * Mitchell Exception
     */ 
    public void sendEmailToSender(APDDeliveryContextDocument apdContext)
                                throws MitchellException {
    	String methodName = "sendEmailToSender";
    	logger.entering(CLASS_NAME, methodName);
        APDBroadcastMessageType bMsg = 
            apdContext.getAPDDeliveryContext().getAPDBroadcastMessage();
        UserInfoType userInfo = 
            bMsg.getAPDCommonInfo().getSourceUserInfo().getUserInfo();
        UserInfoDocument userInfoDoc = 
                                        UserInfoDocument.Factory.newInstance();
        userInfoDoc.setUserInfo(userInfo);
        
        
        if (userInfo.isSetEmail()) {
            EmailRequestDocument emailReqDoc = 
                notificationProxy.buildEmailRequest(
                                            systemConfigurationProxy.getSenderName(), 
                                            systemConfigurationProxy.getSenderEmailId(),
                                            userInfo.getEmail(), 
                                            bMsg.getMessageSubject(), 
                                            getTransformedMessageContent(
                                                apdContext.xmlText()));
            notificationProxy.notifyByEmail(
                                emailReqDoc, 
                                userInfoDoc, 
                                bMsg.getAPDCommonInfo().getWorkItemId(), 
                                APDDeliveryConstants.APP_NAME, 
                                APDDeliveryConstants.MODULE_NAME, 
                                APDDeliveryConstants.BUSINESS_SERVICE_NAME);
        }
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * @param xmlText
     * @return String
     * @throws MitchellException
     * Mitchell Exception
     */
    private String getTransformedMessageContent(String xmlText) 
                                                    throws MitchellException {
        String xsltPath = 
            systemConfigurationProxy.getBroadcastMessageEmailXsltFilePath();
        String transformedMessageContent = 
            xsltTransformer.transformXmlString(
                xsltPath, 
                xmlText);
        return transformedMessageContent;
    }
    
    
    
    /**
     * Gets the NotificationProxy.
     *
     * @return NotificationProxy
     */
    public NotificationProxy getNotificationProxy() {
        return notificationProxy;
    }

    /**
     * Sets the NotificationProxy.
     *
     * @param notificationProxy
     */
    public void setNotificationProxy(final NotificationProxy notificationProxy) {
        this.notificationProxy = notificationProxy;
    }
    
    /**
     * Gets the XsltTransformer.
     *
     * @return XsltTransformer
     */
    public XsltTransformer getXsltTransformer() {
        return xsltTransformer;
    }

    /**
     * Sets the XsltTransformer.
     *
     * @param xsltTransformer
     */
    public void setXsltTransformer(final XsltTransformer xsltTransformer) {
        this.xsltTransformer = xsltTransformer;
    }
    
    /**
     * Gets the SystemConfigurationProxy.
     *
     * @return the SystemConfigurationProxy
     */
    public SystemConfigurationProxy getSystemConfigurationProxy() {
        return systemConfigurationProxy;
    }

    /**
     * Sets the SystemConfigurationProxy.
     *
     * @param systemConfigurationProxy
     */
    public void setSystemConfigurationProxy(SystemConfigurationProxy systemConfigurationProxy) {
        this.systemConfigurationProxy = systemConfigurationProxy;
    }
    
}
