package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.notification.client.Notification;
import com.mitchell.services.core.notification.types.EmailRequestDocument;
import java.util.logging.Logger;

/**
 * The Class NotificationProxyImpl.
 */
public final class NotificationProxyImpl implements NotificationProxy {

	/**
	 * class name.
	 */
	private static final String CLASS_NAME = NotificationProxyImpl.class
			.getName();
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
		String methodName = "buildEmailRequest";
		logger.entering(CLASS_NAME, methodName);
		EmailRequestDocument emailReqDoc = Notification.buildEmailRequest(fromDisplayName, fromAddress,
				toAddress, subject, messageBody);
		logger.exiting(CLASS_NAME, methodName);
		return emailReqDoc;
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
		String methodName = "notifyByEmail";
		logger.entering(CLASS_NAME, methodName);
		
		Notification.notifyByEmail(emailRequestDoc, userInfoDoc, workItemId,
				appName, moduleName, businessServiceName);
		logger.exiting(CLASS_NAME, methodName);
	}

}
