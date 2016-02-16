package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.notification.types.EmailRequestDocument;

/**
 * The Interface NotificationProxy.
 */
public interface NotificationProxy {
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
	EmailRequestDocument buildEmailRequest(String fromDisplayName,
			String fromAddress, String toAddress, String subject,
			String messageBody) throws MitchellException;

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
	void notifyByEmail(EmailRequestDocument emailRequestDoc,
			UserInfoDocument userInfoDoc, String workItemId, String appName,
			String moduleName, String businessServiceName)
			throws MitchellException;

}
