package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.notification.client.Notification;
import com.mitchell.services.core.notification.types.EmailRequestDocument;
import com.mitchell.services.core.notification.types.FaxRequestDocument;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * The Class NotificationProxyImpl.
 */
public final class NotificationProxyImpl implements NotificationProxy {
    
    /**
     * class name.
     */
    private static final String CLASS_NAME = 
                            NotificationProxyImpl.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);

    /**
     * Builds the email request.
     *
     * @param fromDisplayName
     *            the from display name
     * @param fromAddress
     *            the from address
     * @param toAddress
     *            the to address
     * @param subject
     *            the subject
     * @param messageBody
     *            the message body
     * @return the email request document
     * @throws MitchellException
     *             the mitchell exception
     */
    public EmailRequestDocument buildEmailRequest(final String fromDisplayName,
            final String fromAddress, final String toAddress,
            final String subject, final String messageBody)
            throws MitchellException {
        return Notification
        .buildEmailRequest(fromDisplayName, fromAddress,
                toAddress, subject, messageBody);
    }

    /**
     * Notify by email.
     *
     * @param emailRequestDoc
     *            the email request doc
     * @param userInfoDoc
     *            the user info doc
     * @param workItemId
     *            the work item id
     * @param appName
     *            the app name
     * @param moduleName
     *            the module name
     * @param businessServiceName
     *            the business service name
     * @throws MitchellException
     *             the mitchell exception
     */
    public void notifyByEmail(final EmailRequestDocument emailRequestDoc,
            final UserInfoDocument userInfoDoc, final String workItemId,
            final String appName, final String moduleName,
            final String businessServiceName) throws MitchellException {

        Notification
        .notifyByEmail(emailRequestDoc, userInfoDoc,
                workItemId, appName, moduleName, businessServiceName);
    }

    public FaxRequestDocument buildFaxRequest(final String fromName,
            final String fromEmployeeId, final String fromEmailAddress,
            final String fromCompany, final String fromDepartment,
            final String fromPhoneNumber, final String subject,
            final String toName, final String toCompany,
            final String toFaxNumber,
            final String toAltFaxNumber, final String toPhoneNumber,
            final String coverText, final boolean coverRequired,
            final String coverSheetFilePath)
            throws MitchellException {
        
        return Notification.buildFaxRequestDoc(
                fromName, fromEmployeeId, fromEmailAddress, fromCompany,
                fromDepartment, fromPhoneNumber, subject, toName,
                toCompany, toFaxNumber, toAltFaxNumber, toPhoneNumber,
                coverText, coverRequired, coverSheetFilePath);
    }

    public void appendCoverTexts(final FaxRequestDocument faxRequestDocument,
            final String[] coverTexts) throws MitchellException {
        Notification.appendCoverTexts(faxRequestDocument, coverTexts);
    }

    public void stageFaxAttachment(final FaxRequestDocument faxRequestDocument,
            final String filePath) throws MitchellException {
        Notification.stageFaxAttachment(faxRequestDocument, filePath);
    }

    public void notifyByFax(final FaxRequestDocument faxRequestDocument,
            final UserInfoDocument userInfoDocument, final String workItemId,
            final String appName, final String moduleName) throws MitchellException {
        
    	if (logger.isLoggable(Level.INFO)) {
            logger.info("FaxReqDoc:" + faxRequestDocument);
        }
        Notification.notifyByFax(faxRequestDocument,
                userInfoDocument, workItemId, appName, moduleName);
    }

}
