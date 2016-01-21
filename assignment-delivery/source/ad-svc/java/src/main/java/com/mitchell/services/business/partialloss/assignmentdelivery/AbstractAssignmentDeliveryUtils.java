package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;

public abstract class AbstractAssignmentDeliveryUtils {

	public AbstractAssignmentDeliveryUtils() {
		super();
	}

	public abstract UserInfoDocument retrieveUserInfo(String companyCode, String userId)
			throws Exception;

	public abstract UserDetailDocument retrieveUserDetail(String companyCode, String userId)
			throws Exception;

	public abstract void sendFaxNotification(UserInfoDocument userInfo, String fromName,
			String fromAddress, String fromNumber, String toFaxNumber, String subject, String messageBody,
			String xsltFilePath, String workItemId) throws AssignmentDeliveryException;

	public abstract void sendEmailNotification(UserInfoDocument userInfo, String fromName,
			String fromAddress, String toAddresses, String toCCAddresses, String subject, String messageBodyText,
			String messageBodyHTML, String xsltFilePath, String workItemId)
			throws AssignmentDeliveryException;

	public abstract String getUserCustomSetting(UserInfoDocument userInfo, String settingName)
			throws AssignmentDeliveryException;

}