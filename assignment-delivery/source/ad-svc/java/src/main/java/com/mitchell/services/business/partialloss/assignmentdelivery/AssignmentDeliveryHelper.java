package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;

public interface AssignmentDeliveryHelper {

	public abstract String getUserCustomSetting(UserInfoDocument userInfo,
			String settingName) throws AssignmentDeliveryException;

	/**
	 * This method sends an Assignment Notification Email to the provided user
	 *   
	 * @param userInfo         UserInfoDocument for current user
	 * @param fromName         String containing From Fax Name
	 * @param fromAddress      String containing From Email Address
	 * @param fromNumber       String containing From Phone Number
	 * @param toAddresses      String containing list of To Email Addresses
	 * @param toCCAddresses    String containing list of CC Email Addresses
	 * @param subject          String containing subject of Email Message
	 * @param messageBodyText  String containing body of the Email Message (Simple Text version)
	 * @param messageBodyHTML  String containing body of the Email Message (HTML version)
	 * @param xsltFilePath     String containing filepath to stylesheet (for HTML formatted Email only) 
	 * @param workItemId       workItemID for this workflow instance
	 *                       
	 * @return Returns  - none
	 *  
	 */
	public abstract void sendEmailNotification(UserInfoDocument userInfo,
			String fromName, String fromAddress, String toAddresses,
			String toCCAddresses, String subject, String messageBodyText,
			String messageBodyHTML, String xsltFilePath, String workItemId)
			throws AssignmentDeliveryException;

	/**
	 * This method sends an Assignment Notification FAX to the provided user
	 *   
	 * @param userInfo         UserInfoDocument for current user
	 * @param fromName         String containing From Fax Name
	 * @param fromAddress      String containing From Email Address
	 * @param fromNumber       String containing From Phone Number
	 * @param toFaxNumber      String containing list of To Fax Numbers
	 * @param subject          String containing subject of Fax Message
	 * @param messageBody      String containing body of the Fax Message
	 * @param xsltFilePath     String containing filepath to stylesheet (for HTML formatted Fax only) 
	 * @param workItemId       workItemID for this workflow instance
	 *                       
	 * @return Returns  - none
	 *  
	 */
	public abstract void sendFaxNotification(UserInfoDocument userInfo,
			String fromName, String fromAddress, String fromNumber,
			String toFaxNumber, String subject, String messageBody,
			String xsltFilePath, String workItemId)
			throws AssignmentDeliveryException;

	/**
	 * This method retrieves a UserDetailDocument for the given CompanyCode and UserId
	 *   
	 * @param companyCode   Company Code for the UserDetailDocument required.
	 * @param userId        UserID for the UserDetailDocument required.
	 *                       
	 * @return Returns      UserDetailDocument
	 *  
	 */
	public abstract UserDetailDocument retrieveUserDetail(String companyCode,
			String userId) throws Exception;

	/**
	 * This method retrieves a UserInfoDocument for the given CompanyCode and UserId
	 *   
	 * @param companyCode   Company Code for the UserDetailDocument required.
	 * @param userId        UserID for the UserDetailDocument required.
	 *                       
	 * @return Returns      UserInfoDocument
	 *  
	 */
	public abstract UserInfoDocument retrieveUserInfo(String companyCode,
			String userId) throws Exception;

}