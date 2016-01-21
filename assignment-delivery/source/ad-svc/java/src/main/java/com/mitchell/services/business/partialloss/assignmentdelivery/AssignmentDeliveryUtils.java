package com.mitchell.services.business.partialloss.assignmentdelivery; 

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.customsettings.CustomSettingsSrvcXML;
import com.mitchell.services.core.customsettings.ejb.CustomSettingsEJBRemote;
import com.mitchell.services.core.customsettings.types.xml.Profile;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;

// Added for SIP3.5 - 
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;
import com.mitchell.services.core.notification.client.Notification;
import com.mitchell.services.core.notification.types.EmailRequestDocument;
import com.mitchell.services.core.notification.types.FaxRequestDocument;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.utils.misc.StringUtilities;
import java.util.logging.Level;

public class AssignmentDeliveryUtils extends AbstractAssignmentDeliveryUtils
{ 
    private final String CLASS_NAME = "AssignmentDeliveryUtils";
  
    private final String CUSTOM_SETTING_USER_LEVEL = "USER";
    private final String CUSTOM_SETTING_COMPANY_LEVEL = "COMPANY";

	private final AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(
			this.getClass().getName());

    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryUtilsContract#getUserCustomSetting(com.mitchell.common.types.UserInfoDocument, java.lang.String)
	 */
	public String getUserCustomSetting(UserInfoDocument userInfo, String settingName) 
            throws AssignmentDeliveryException
    {
        String methodName = "getUserCustomSetting";
        
        String retVal = null;        
        try {
            String userId = userInfo.getUserInfo().getUserID();
            String compId = userInfo.getUserInfo().getOrgCode();
            
            CustomSettingsEJBRemote ejb = CustomSettingsSrvcXML.getEJB();
            Profile profile = ejb.getDefaultProfile(userId, compId, CUSTOM_SETTING_USER_LEVEL);

            if(profile != null) {
                SettingValue value = 
                        ejb.getValue(userId, compId, CUSTOM_SETTING_USER_LEVEL,
                                profile.getId(), 
                                AssignmentDeliveryConstants.CUSTOM_SETTING_GROUP_NAME, 
                                settingName); 
                if(value != null && value.getValue() != null 
                        && value.getValue().length() != 0) {
                    retVal = value.getValue();    
                } else {
                    throw new Exception("Could not find value for Custom Setting [userId = "
                             + userId + ", compId = " + compId + ", settingName = " 
                             + settingName + ", groupName = " 
                             + AssignmentDeliveryConstants.CUSTOM_SETTING_GROUP_NAME + "]");
                }
            }
        } catch(Exception e) {
			mLogger.severe(e.getMessage());
            ErrorLoggingService.logError(
                    AssignmentDeliveryErrorCodes.CUSTOM_SETTING_ERROR, null, 
                    CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, 
                    null, e.getMessage(), null, 0, e);
			throw mLogger.createException(
					AssignmentDeliveryErrorCodes.CUSTOM_SETTING_ERROR, e);
        }
        
        return retVal;
    }

    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryUtilsContract#sendEmailNotification(com.mitchell.common.types.UserInfoDocument, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */      
	public void sendEmailNotification(UserInfoDocument userInfo, String fromName, String fromAddress, 
                                      String toAddresses,String toCCAddresses, String subject,  
                                      String messageBodyText, String messageBodyHTML,
                                      String xsltFilePath, String workItemId) 
        throws AssignmentDeliveryException
    {
        final String methodName = "sendEmailNotification";
        mLogger.entering(CLASS_NAME, methodName);
        
        try {
            EmailRequestDocument requestDoc;
            
            if(AssignmentDeliveryConfig.getSimpleNotificationFlag()) {

                if(mLogger.isLoggable(Level.FINER)) {
                    mLogger.info("sendEmailNotification: messageBodyText = " + messageBodyText);
                    // mLogger.finer("MessageBody = " + messageBody.xmlText());
                }

                mLogger.info("sendEmailNotification: before Notification.buildEmailRequest ... ");
        
                if (messageBodyHTML != null) {
                    // new
                    requestDoc = Notification.buildEmailRequest(fromName, fromAddress, toAddresses, subject, messageBodyText, messageBodyHTML);
                } else {
                    //SIP35 requestDoc = Notification.buildEmailRequest(fromName, fromAddress, toAddresses, subject, messageBody);        
                    requestDoc = Notification.buildEmailRequest(fromName, fromAddress, toAddresses, subject, messageBodyText);
                }

                mLogger.info("sendEmailNotification: after Notification.buildEmailRequest... Done");

                // requestDoc = Notification.buildEmailRequest(fromName, fromAddress, toAddress, subject, messageBody.xmlText());

                // Add Add EmailCCRecipients
                if(!StringUtilities.isEmpty(toCCAddresses)) {
                        Notification.setEmailCCs(requestDoc,toCCAddresses,null);
                }             
                
            }
            else {
            
                    mLogger.info("****  Sending Email Notification Messages as XML with XLST not supported.");
                    requestDoc = null;
            /**
             * NOT SUPPORTED AT THIS TIME
                if(mLogger.isLoggable(Level.INFO)) {
                    mLogger.info("Sending Notification Message as XML.");
                }
                if(mLogger.isLoggable(Level.FINER)) {
                    mLogger.finer("MessageBody = " + messageBody.xmlText());
                }
                requestDoc = Notification.buildXmlEmailRequestDoc(fromName, fromAddress, toAddress,
                    subject, messageBody, xsltFilePath);
             * 
             */
            }

            mLogger.info("sendEmailNotification: before Notification.notifyByEmail... ");
            
          Notification.notifyByEmail(requestDoc, userInfo, workItemId, 
                    AssignmentDeliveryConstants.MODULE_NAME,
                    AssignmentDeliveryConstants.MODULE_NAME,
                    AssignmentDeliveryConstants.MODULE_NAME); 

            mLogger.info("sendEmailNotification: after Notification.notifyByEmail... Done ");
                                        
            if(mLogger.isLoggable(Level.INFO)) {
                mLogger.info("sendEmailNotification: Successfully sent email notification");                
            }
        } catch (Exception e) {

            String errMsg = "Unable to send email notification: " + e.getMessage();        
            mLogger.severe(errMsg);
            ErrorLoggingService.logError(
                    AssignmentDeliveryErrorCodes.GENERAL_ERROR, 
                    null, CLASS_NAME, methodName, 
                    ErrorLoggingService.SEVERITY.FATAL, workItemId, 
                    errMsg, null, 0, e);  
                    throw mLogger.createException( 
                        AssignmentDeliveryErrorCodes.GENERAL_ERROR, workItemId);
        }

        mLogger.exiting(CLASS_NAME, methodName);                
    }

    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryUtilsContract#sendFaxNotification(com.mitchell.common.types.UserInfoDocument, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */      
	public void sendFaxNotification(UserInfoDocument userInfo, String fromName, String fromAddress, 
                                      String fromNumber, String toFaxNumber, 
                                      String subject,  String messageBody, 
                                      String xsltFilePath, String workItemId) 
        throws AssignmentDeliveryException
    {

        //    
        // NOTE: For SIP 3.5 - Shop Supplement Notification is only supporting Simple Text for EMAIL/FAX messages
        //
    
        final String methodName = "sendFaxNotification";
        mLogger.entering(CLASS_NAME, methodName);
        
        try {
            FaxRequestDocument requestDoc;
            
            if(AssignmentDeliveryConfig.getSimpleNotificationFlag()) {
                if(mLogger.isLoggable(Level.INFO)) {
                    mLogger.info("sendFaxNotification: Sending Notification Message as html formatted message.");
                    // mLogger.info("sendFaxNotification: Sending Notification Message as simple String.");
                }
                if(mLogger.isLoggable(Level.INFO)) {
                    mLogger.info("sendFaxNotification: MessageBody = \n" + messageBody+"****END***\n\n");
                 // mLogger.finer("sendFaxNotification: MessageBody(xmlText) = " + messageBody.xmlText());
                }

                mLogger.info("sendFaxNotification: before Notification.buildFaxRequest ... ");
                
                requestDoc = Notification.buildFaxRequestDoc(fromName, null, fromAddress, null, null, fromNumber, 
                                        subject, null, null, toFaxNumber, null, null, messageBody, false, null);
                                         
                mLogger.info("sendFaxNotification: after Notification.buildFaxRequest... Done");
   
            }
            else {
            
                    mLogger.info("****  Sending Fax Notification Messages as XML with XLST not supported now.");
                    requestDoc = null;
            /**
             * NOT SUPPORTED FOR SIP 3.5/Jetta
             *
                if(mLogger.isLoggable(Level.INFO)) {
                    mLogger.info("Sending FAX Notification Message as XML.");
                }
                if(mLogger.isLoggable(Level.FINER)) {
                    mLogger.finer("MessageBody(xmlText) = " + messageBody.xmlText());
                }
                requestDoc = Notification.buildXmlFaxRequestDoc(fromName, null, fromAddress, null,
                    null, fromNumber, subject, null, null, toFaxNumber, null, null, messageBody, xsltFilePath, false, null);
                if(mLogger.isLoggable(Level.FINER)) {
                    mLogger.finer("Fax notification requestDoc = \n" + requestDoc.toString());                
                }                    
             * 
             */
            }

            mLogger.info("sendFaxNotification: before Notification.notifyByFax... ");
                                
            Notification.notifyByFax(requestDoc, userInfo, workItemId,
                    AssignmentDeliveryConstants.MODULE_NAME,
                    AssignmentDeliveryConstants.MODULE_NAME);                    

            mLogger.info("sendFaxNotification: after Notification.notifyByFax... Done ");
                                        
            if(mLogger.isLoggable(Level.INFO)) {
                mLogger.info("sendFaxNotification: Successfully sent Fax notification");                
            }
        } catch (Exception e) {

            String errMsg = "Unable to send Fax notification: " + e.getMessage();        
            mLogger.severe(errMsg);
            ErrorLoggingService.logError(
                    AssignmentDeliveryErrorCodes.GENERAL_ERROR, 
                    null, CLASS_NAME, methodName, 
                    ErrorLoggingService.SEVERITY.FATAL, workItemId, 
                    errMsg, null, 0, e);  
                    throw mLogger.createException( 
                        AssignmentDeliveryErrorCodes.GENERAL_ERROR, workItemId);
        }

        mLogger.exiting(CLASS_NAME, methodName);                
    }

    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryUtilsContract#retrieveUserDetail(java.lang.String, java.lang.String)
	 */      
	public UserDetailDocument retrieveUserDetail(String companyCode, String userId) throws Exception
    {
        final String methodName = "retrieveUserDetail";
        mLogger.entering(CLASS_NAME, methodName);

        UserInfoClient userInfoClient = new UserInfoClient();
        UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();

		UserDetailDocument userDetailDoc = ejb.getUserDetail(companyCode, userId);
        
        mLogger.exiting(CLASS_NAME, methodName);
        
        return userDetailDoc;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryUtilsContract#retrieveUserInfo(java.lang.String, java.lang.String)
	 */      
	public UserInfoDocument retrieveUserInfo(String companyCode, String userId) throws Exception
    {
        final String methodName = "retrieveUserInfo";
        mLogger.entering(CLASS_NAME, methodName);
        
        UserInfoClient userInfoClient = new UserInfoClient();
        UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();

		UserInfoDocument userInfoDoc = ejb.getUserInfo(companyCode, userId, "");
        
        mLogger.exiting(CLASS_NAME, methodName);
        return userInfoDoc;
    }    
     
} 
