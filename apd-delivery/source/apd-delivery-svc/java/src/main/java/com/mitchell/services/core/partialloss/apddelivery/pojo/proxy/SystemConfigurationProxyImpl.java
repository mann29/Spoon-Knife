package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import java.util.logging.Logger;

import com.mitchell.systemconfiguration.SystemConfiguration;

/**
 * The Class SystemConfigurationProxyImpl.
 */
public class SystemConfigurationProxyImpl implements
    SystemConfigurationProxy
{

  /**
   * class name.
   */
  private static final String CLASS_NAME = SystemConfigurationProxyImpl.class
      .getName();

  /**
   * logger instance.
   */
  private static Logger logger = Logger.getLogger(CLASS_NAME);

  public String getBroadcastMessageEmailXsltFilePath()
  {
    return getSettingValue(
        "/APDDeliveryService/BroadcastMessageDelivery/BroadcastMessageXsltPath",
        null);
  }

  public String getPartiallossSIPDelGroup()
  {
    return getSettingValue(
        "/APDDeliveryService/BroadcastMessageDelivery/PartiallossSIPDelGroup",
        null);
  }

  public String getPartiallossSIPDelDestinationSettingName()
  {
    return getSettingValue(
        "/APDDeliveryService/BroadcastMessageDelivery/PartiallossSIPDelDestinationSettingName",
        null);
  }

  public String getPartiallossSIPDelDestinationEclaim()
  {
    return getSettingValue(
        "/APDDeliveryService/BroadcastMessageDelivery/PartiallossSIPDelDestinationEclaim",
        null);
  }

  public String getBroadcastMessageGroup()
  {
    return getSettingValue(
        "/APDDeliveryService/BroadcastMessageDelivery/BroadcastMessageGroup",
        null);
  }

  public String getEmailToRcpntReqSettingName()
  {
    return getSettingValue(
        "/APDDeliveryService/BroadcastMessageDelivery/EmailToRcpntReqSettingName",
        null);
  }

  public String getJndiFactory()
  {
    return getSettingValue("/APDDeliveryServiceJavaClient/JNDIFactory", null);
  }

  public String getProviderUrl()
  {
    return getSettingValue("/APDDeliveryServiceJavaClient/ProviderUrl", "");
  }

  public String getAPDQueueConnectionfactory()
  {
    return getSettingValue("/APDDeliveryService/QueueConnectionFactory", null);
  }

  public String getAPDBroadcastMessageQueueName()
  {
    return getSettingValue(
        "/APDDeliveryService/BroadcastMessageDelivery/Queue", null);
  }

  /**
   * ECalert message description
   */
  public String getECAlertMessageDesc()
  {
    return getSettingValue(
        "/APDDeliveryService/BroadcastMessageDelivery/ECAlertMessage", null);
  }

  /**
   * Apple codes for wcap user
   */
  public String getWCAPUserApplCode()
  {
    return getSettingValue(
        "/APDDeliveryService/BroadcastMessageDelivery/WCAPUserApplCode", null);
  }

  /**
   * Apple codes for estimator
   */
  public String getEstimatorApplCode()
  {
    return getSettingValue(
        "/APDDeliveryService/BroadcastMessageDelivery/EstimatorApplCode", null);
  }

  /**
   * event id for WCAP user
   */
  public String getEventIdForWCAPUser()
  {
    return getSettingValue(
        "/APDDeliveryService/EventId/BroadCastMessageWCAPUser", null);
  }

  public String getSenderName()
  {
    return getSettingValue(
        "/APDDeliveryService/BroadcastMessageDelivery/SenderName", null);
  }

  public String getSenderEmailId()
  {
    return getSettingValue(
        "/APDDeliveryService/BroadcastMessageDelivery/SenderEmailId", null);
  }

  public String getPublishToMessageQueue()
  {
    return getSettingValue("/APDDeliveryService/PublishToMessageQueue", null);
  }

  /**
   * Gets the setting value.
   *
   * @param name the name
   * @param defaultValue the default value
   * @return the setting value
   */
  public String getSettingValue(
          final String name, final String defaultValue) {
      
      String value = SystemConfiguration.getSettingValue(name);
      
      if (value == null || value.trim().length() <= 0) {
          value = defaultValue;
      }
      
      return value;
      
  }

  public String getSettingValue(String name) {
      return SystemConfiguration.getSettingValue(name);
  }

  public String getEJBJndi()
  {
    return getSettingValue("/APDDeliveryServiceJavaClient/EJBJndi", null);

  }

  /**
   * Xpath of event id for publishing Alerts delivery to message bus.
   */
  private static final String ALERT_DELIVERY_EVENT_ID = "/APDDeliveryService/EventId/AlertDelivery";
  /**
   * Xpath of event id for publishing RA delivery to message bus.
   */
  private static final String RA_DELIVERY_EVENT_ID = "/APDDeliveryService/EventId/RADelivery";
  /**
   * Xpath of event id for publishing Estimate delivery to message bus.
   */
  private static final String ESTIMATE_DELIVERY_EVENT_ID = "/APDDeliveryService/EventId/EstimateDelivery";

  /**
   * Xpath of event id for publishing Appraisal Assignment Notification
   * delivery to message bus.
   */
  private static final String APPRAISAL_ASMT_NTFN_DELIVERY_EVENT_ID = "/APDDeliveryService/EventId/AppraisalAssignmentNotificationDelivery";

  /**
   * Xpath of event id for publishing Staff Supplement to message bus.
   */
  private static final String REQ_FOR_STAFF_SUPPLEMENT_DELIVERY_EVENT_ID = "/APDDeliveryService/EventId/ReqForStaffSupplementDelivery";

  /**
   * Estimate upload Alert delivery activity operation.
   */
  private static final String ESTIMATE_UPLOAD_ALERT_DELIVERY_ACTIVITY_OPERATION = "/APDDeliveryService/ActivityOperation/EstimateUploadAlertDelivery";

  /**
   * Artifact Alert delivery to Shop user activity operation.
   */
  private static final String ARTIFACT_ALERT_DELIVERY_TO_SHOP_ACTIVITY_OPERATION = "/APDDeliveryService/ActivityOperation/ArtifactAlertDeliveryToShop";

  /**
   * Artifact Alert delivery to Staff user activity operation.
   */
  private static final String ARTIFACT_ALERT_DELIVERY_TO_STAFF_ACTIVITY_OPERATION = "/APDDeliveryService/ActivityOperation/ArtifactAlertDeliveryToStaff";

  /**
   * Estimate delivery activity operation.
   */
  private static final String ESTIMATE_DELIVERY_ACTIVITY_OPERATION = "/APDDeliveryService/ActivityOperation/EstimateDelivery";

  /**
   * Staff Supplement Accepted activity operation.
   */
  private static final String REQ_FOR_STAFF_SUPPLEMENT_ACCEPTED_ACTIVITY_OPERATION = "/APDDeliveryService/ActivityOperation/ReqForStaffSupplementAccepted";

  /**
   * Staff Supplement Rejected activity operation.
   */
  private static final String REQ_FOR_STAFF_SUPPLEMENT_REJECTED_ACTIVITY_OPERATION = "/APDDeliveryService/ActivityOperation/ReqForStaffSupplementRejected";

  /**
   * Appraisal Assignment Notification update activity operation.
   */
  private static final String APPR_ASMT_NTFN_UPDATE_ACTIVITY_OPERATION = "/APDDeliveryService/ActivityOperation/AppraisalAssignmentNotificationDeliveryUpdate";

  /**
   * Appraisal Assignment Notification cancel activity operation.
   */
  private static final String APPR_ASMT_NTFN_CANCEL_ACTIVITY_OPERATION = "/APDDeliveryService/ActivityOperation/AppraisalAssignmentNotificationDeliveryCancel";

  /**
   * Xpath of Apple code to recognize Platform user.
   */
  private static final String APP_CODE = "/APDDeliveryService/AppCode";

  /**
   * Xpath of Apple code to recognize Staff user.
   */
  private static final String APP_CODE_FOR_STAFF = "/APDDeliveryService/AppCodeForStaff";

  /**
   * Email SENDER NAME.
   */
  private static final String SENDER_NAME = "/APDDeliveryService/EmailNotification/SenderName";

  /**
   * SENDER'S EMAIL ID.
   */
  private static final String SENDER_EMAIL_ID = "/APDDeliveryService/EmailNotification/SenderEmailId";

  /**
   * Email Subject for create Repair.
   */
  private static final String EMAIL_SUBJECT_FOR_CREATE_REPAIR = "/APDDeliveryService/EmailNotification/SubjectForCreateRepair";

  /**
   * Email Subject for Update Repair.
   */
  private static final String EMAIL_SUBJECT_FOR_UPDATE_REPAIR = "/APDDeliveryService/EmailNotification/SubjectForUpdateRepair";

  /**
   * Email Subject for Complete Repair.
   */
  private static final String EMAIL_SUBJECT_FOR_COMPLETE_REPAIR = "/APDDeliveryService/EmailNotification/SubjectForCompleteRepair";

  /**
   * Email Subject for cancel Repair.
   */
  private static final String EMAIL_SUBJECT_FOR_CANCEL_REPAIR = "/APDDeliveryService/EmailNotification/SubjectForCancelRepair";

  /**
   * Email Subject for create Rework.
   */
  private static final String EMAIL_SUBJECT_FOR_CREATE_REWORK = "/APDDeliveryService/EmailNotification/SubjectForCreateRework";

  /**
   * Email Subject for Update Rework.
   */
  private static final String EMAIL_SUBJECT_FOR_UPDATE_REWORK = "/APDDeliveryService/EmailNotification/SubjectForUpdateRework";

  /**
   * Email Subject for Complete Rework.
   */
  private static final String EMAIL_SUBJECT_FOR_COMPLETE_REWORK = "/APDDeliveryService/EmailNotification/SubjectForCompleteRework";

  /**
   * Email Subject for cancel Rework.
   */
  private static final String EMAIL_SUBJECT_FOR_CANCEL_REWORK = "/APDDeliveryService/EmailNotification/SubjectForCancelRework";

  /**
   * Path of XSLT.
   */
  private static final String XSLT_PATH_FOR_EMAIL = "/APDDeliveryService/EmailNotification/XsltPath";

  /**
   * Definition type Repair Asmt non-IF.
   */
  private static final String REPAIR_ASSIGNMENT_DEFINITION_TYPE = "/APDDeliveryService/DefinitionType/RepairAssignmentDefType";

  /**
   * Definition type Rework Asmt non-IF.
   */
  private static final String REWORK_ASSIGNMENT_DEFINITION_TYPE = "/APDDeliveryService/DefinitionType/ReworkAssignmentDefType";

  /**
   * Definition type Repair Asmt IF.
   */
  private static final String REPAIR_ASSIGNMENT_DEFINITION_TYPE_IF = "/APDDeliveryService/DefinitionType/RepairAssignmentDefTypeIF";

  /**
   * Definition type Rework Asmt IF.
   */
  private static final String REWORK_ASSIGNMENT_DEFINITION_TYPE_IF = "/APDDeliveryService/DefinitionType/ReworkAssignmentDefTypeIF";

  /**
   * Definition type Appraisal Assignment Notification.
   */
  private static final String APPR_ASMT_NTFN_DEFINITION_TYPE = "/APDDeliveryService/DefinitionType/AppraisalAssignmentNotification";

  /**
   * Estimate upload types for alerts.
   */
  private static final String ESTIMATE_UPLOAD_TYPES = "/APDDeliveryService/EstimateUploadTypes";

  /**
   * APDDelivery Service company org type.
   */
  private static final String APD_DELIVERY_SERVICE_COMPANY_ORG_TYPE = "/APDDeliveryService/NICBReportDelivery/CompanyOrgType";

  /**
   * NICB Service company group name.
   */
  private static final String NICB_SERVICE_COMPANY_GROUP_NAME = "/APDDeliveryService/NICBReportDelivery/CompanySettingGroupName";

  /**
   * Event id for NICB Report Delivery Handler.
   */
  private static final String PARTIALLOSS_NICB_REPORT_DELIVER_HANDLERS = "/APDDeliveryService/NICBReportDelivery/NICBReportDeliveryHandler";

  /**
   * PDF Attachment types for Repair
   */
  private static final String PDF_ATTACHMENT_TYPES = "/APDDeliveryService/PDFAttachmentTypesForRepair";

  /**
   * Text Attachment types for Repair
   */
  private static final String TEXT_ATTACHMENT_TYPES = "/APDDeliveryService/TextAttachmentTypesForRepair";

  /**
   * MIE Estimate Attachment types
   */
  private static final String MIE_ESTIMATE_ATTACHMENT_TYPES = "/APDDeliveryService/MIEEstimateAttachmentTypes";

  /**
   * Group name for repair workflow settings
   */
  private static final String REPAIR_WORKFLOW_SETTINGS = "/APDDeliveryService/CS_REPAIR_STATUS_NOTIFICATION_GROUP";

  private static final String REPAIR_WORKFLOW_SETTINGS_PROPERTY = "/APDDeliveryService/CS_REPAIR_STATUS_NOTIFICATION_PROPERTY";

  /**
   * AssignmentEmailEnabled.
   */
  private static final String ASSIGNMENT_EMAIL_ENABLED = "/APDDeliveryService/EmailNotification/AssignmentEmailEnabled";

  /**
   * OverrideRCEndPointEnabled.
   */
  private static final String OVERRIDE_RC_END_POINT_ENABLED = "/APDDeliveryService/OverrideRCEndPointEnabled";

  /**
   * AppCode for routing Assignments to eClaim Instead of RC.
   */
  private static final String APPCODE_RC_OVERRIDE = "/APDDeliveryService/AppCodeRCOverride";
  
  /**
   * Get OverrideCanUploadEstimateFlagCoCodes value.
   */
  private static final String OVERRIDE_CAN_UPLOAD_ESTIMATE_FLAG_CO_CODES = "/APDDeliveryService/OverrideCanUploadEstimateFlagCoCodes";  
  
  /**
   * Get AppCodeIgnoreEClaimRoute value.
   */
  private static final String IGNORE_ECLAIM_ROUTE = "/APDDeliveryService/AppCodeIgnoreEClaimRoute";  
  
  /**
   * Get ignoreRCAppCode value.
   */
  private static final String IGNORE_RC = "/APDDeliveryService/AppCodeIgnoreRC";  

  /**
   * Get event id for publishing Alerts delivery to message bus.
   * 
   * @return String
   */
  public String getAlertDeliveryEventId()
  {
    return getStringSettingValue(ALERT_DELIVERY_EVENT_ID);
  }

  /**
   * Get event id for publishing RA delivery to message bus.
   * 
   * @return String
   */
  public String getRADeliveryEventId()
  {
    return getStringSettingValue(RA_DELIVERY_EVENT_ID);
  }

  /**
   * Get event id for publishing Estimate delivery to message bus.
   * 
   * @return String
   */
  public String getEstimateDeliveryEventId()
  {
    return getStringSettingValue(ESTIMATE_DELIVERY_EVENT_ID);
  }

  /**
   * Get event id for publishing Appraisal Assignment Notification delivery to
   * message bus.
   * 
   * @return String
   */
  public String getApprAsmtNtfnDeliveryEventId()
  {
    return getStringSettingValue(APPRAISAL_ASMT_NTFN_DELIVERY_EVENT_ID);
  }

  /**
   * Get Estimate upload Alert delivery activity operation.
   * 
   * @return String
   */
  public String getEstimateUploadAlertDeliveryActivityOperation()
  {
    return getStringSettingValue(ESTIMATE_UPLOAD_ALERT_DELIVERY_ACTIVITY_OPERATION);
  }

  /**
   * Get Artifact Alert delivery to Shop activity operation.
   * 
   * @return String
   */
  public String getArtifactAlertDeliveryToShopActivityOperation()
  {
    return getStringSettingValue(ARTIFACT_ALERT_DELIVERY_TO_SHOP_ACTIVITY_OPERATION);
  }

  /**
   * Get Artifact Alert delivery to Staff activity operation.
   * 
   * @return String
   */
  public String getArtifactAlertDeliveryToStaffActivityOperation()
  {
    return getStringSettingValue(ARTIFACT_ALERT_DELIVERY_TO_STAFF_ACTIVITY_OPERATION);
  }

  /**
   * Get Estimate delivery activity operation.
   * 
   * @return String
   */
  public String getEstimateDeliveryActivityOperation()
  {
    return getStringSettingValue(ESTIMATE_DELIVERY_ACTIVITY_OPERATION);
  }

  /**
   * Get Apple code to recognize Platform user.
   * 
   * @return String
   */
  public String getAppCode()
  {
    return getStringSettingValue(APP_CODE);
  }

  /**
   * Get email sender's address.
   * 
   * @return String
   */
  public String getEmailSenderEmailId()
  {
    return getStringSettingValue(SENDER_EMAIL_ID);
  }

  /**
   * Get email sender's name.
   * 
   * @return String
   */
  public String getEmailSenderName()
  {
    return getStringSettingValue(SENDER_NAME);
  }

  /**
   * Get Subject of email for create Repair.
   * 
   * @return String
   */
  public String getSubjectForCreateRepair()
  {
    return getStringSettingValue(EMAIL_SUBJECT_FOR_CREATE_REPAIR);
  }

  /**
   * Get Subject of email for update Repair.
   * 
   * @return String
   */
  public String getSubjectForUpdateRepair()
  {
    return getStringSettingValue(EMAIL_SUBJECT_FOR_UPDATE_REPAIR);
  }

  /**
   * Get Subject of email for complete Repair.
   * 
   * @return String
   */
  public String getSubjectForCompleteRepair()
  {
    return getStringSettingValue(EMAIL_SUBJECT_FOR_COMPLETE_REPAIR);
  }

  /**
   * Get Subject of email for cancel Repair.
   * 
   * @return String
   */
  public String getSubjectForCancelRepair()
  {
    return getStringSettingValue(EMAIL_SUBJECT_FOR_CANCEL_REPAIR);
  }

  /**
   * Get Subject of email for Create Rework.
   * 
   * @return String
   */
  public String getSubjectForCreateRework()
  {
    return getStringSettingValue(EMAIL_SUBJECT_FOR_CREATE_REWORK);
  }

  /**
   * Get Subject of email for update Rework.
   * 
   * @return String
   */
  public String getSubjectForUpdateRework()
  {
    return getStringSettingValue(EMAIL_SUBJECT_FOR_UPDATE_REWORK);
  }

  /**
   * Get Subject of email for complete Rework.
   * 
   * @return String
   */
  public String getSubjectForCompleteRework()
  {
    return getStringSettingValue(EMAIL_SUBJECT_FOR_COMPLETE_REWORK);
  }

  /**
   * Get Subject of email for Cancel Rework.
   * 
   * @return String
   */
  public String getSubjectForCancelRework()
  {
    return getStringSettingValue(EMAIL_SUBJECT_FOR_CANCEL_REWORK);
  }

  /**
   * Get xslt file path.
   * 
   * @return String
   */
  public String getXsltFilePath()
  {
    return getStringSettingValue(XSLT_PATH_FOR_EMAIL);
  }

  /**
   * Get Staff Supplement Delivery Event Id.
   * 
   * @return String
   */
  public String getReqForStaffSupplementDeliveryEventId()
  {
    return getStringSettingValue(REQ_FOR_STAFF_SUPPLEMENT_DELIVERY_EVENT_ID);
  }

  /**
   * Get Staff Supplement Accepted Activity Operation.
   * 
   * @return String
   */
  public String getReqForStaffSupplementAcceptedActivityOperation()
  {
    return getStringSettingValue(REQ_FOR_STAFF_SUPPLEMENT_ACCEPTED_ACTIVITY_OPERATION);
  }

  /**
   * Get Staff Supplement Rejected Activity Operation.
   * 
   * @return String
   */
  public String getReqForStaffSupplementRejectedActivityOperation()
  {
    return getStringSettingValue(REQ_FOR_STAFF_SUPPLEMENT_REJECTED_ACTIVITY_OPERATION);
  }

  /**
   * Get Appraisal Assignment Notification update Activity Operation.
   * 
   * @return String
   */
  public String getApprAsmtNtfnUpdateActivityOperation()
  {
    return getStringSettingValue(APPR_ASMT_NTFN_UPDATE_ACTIVITY_OPERATION);
  }

  /**
   * Get Appraisal Assignment Notification cancel Activity Operation.
   * 
   * @return String
   */
  public String getApprAsmtNtfnCancelActivityOperation()
  {
    return getStringSettingValue(APPR_ASMT_NTFN_CANCEL_ACTIVITY_OPERATION);
  }

  /**
   * Get Definition type of Repair Asmt for non-IF.
   */
  public String getRprAsmtDefType()
  {
    return getStringSettingValue(REPAIR_ASSIGNMENT_DEFINITION_TYPE);
  }

  /**
   * Get Definition type of Rework Asmt for non-IF.
   */
  public String getReworkAsmtDefType()
  {
    return getStringSettingValue(REWORK_ASSIGNMENT_DEFINITION_TYPE);
  }

  /**
   * Get Definition type of Repair Asmt for IF.
   */
  public String getRprAsmtDefTypeIF()
  {
    return getStringSettingValue(REPAIR_ASSIGNMENT_DEFINITION_TYPE_IF);
  }

  /**
   * Get Definition type of Rework Asmt for IF.
   */
  public String getReworkAsmtDefTypeIF()
  {
    return getStringSettingValue(REWORK_ASSIGNMENT_DEFINITION_TYPE_IF);
  }

  /**
   * Get Definition type of Appraisal Assignment Notification.
   */
  public String getApprAsmtNtfnDefType()
  {
    return getStringSettingValue(APPR_ASMT_NTFN_DEFINITION_TYPE);
  }

  /**
   * Get Estimate upload types for alerts.
   */
  public String getEstimateUploadTypes()
  {
    return getStringSettingValue(ESTIMATE_UPLOAD_TYPES);
  }

  /**
   * This method returns app code for Staff.
   */
  public String getAppCodeForStaff()
  {
    return getStringSettingValue(APP_CODE_FOR_STAFF);
  }

  /**
   * This method returns APDDeliveryServiceCompanyOrgType.
   * 
   * @return String
   */
  public String getAPDDeliveryServiceCompanyOrgType()
  {
    return getStringSettingValue(APD_DELIVERY_SERVICE_COMPANY_ORG_TYPE);
  }

  /**
   * This method returns NICBServiceCompanyGroupName.
   * 
   * @return String
   */
  public String getNICBServiceCompanyGroupName()
  {
    return getStringSettingValue(NICB_SERVICE_COMPANY_GROUP_NAME);
  }

  /**
   * This method returns NICBServiceCompanyGroupName.
   * 
   * @return String
   */
  public String getNICBReportDeliveryHandler()
  {
    return getStringSettingValue(PARTIALLOSS_NICB_REPORT_DELIVER_HANDLERS);
  }

  /**
   * Get PDF Attachment types for alerts
   */
  public String getPDFAttachmentTypesForRepair()
  {
    return getStringSettingValue(PDF_ATTACHMENT_TYPES);
  }

  /**
   * Get Text Attachment types for alerts
   */
  public String getTextAttachmentTypesForRepair()
  {
    return getStringSettingValue(TEXT_ATTACHMENT_TYPES);
  }

  /**
   * Get MIE Estimate Attachment types for alerts
   */
  public String getMIEEstimateAttachmentTypes()
  {
    return getStringSettingValue(MIE_ESTIMATE_ATTACHMENT_TYPES);
  }

  /**
   * Get repair workflow setting flag for repair
   */
  public String getWorkFlowSettingFlagForRepair()
  {
    return getStringSettingValue(REPAIR_WORKFLOW_SETTINGS);

  }

  /**
   * Get repair workflow setting property for repair
   */
  public String getWorkFlowSettingPropertyForRepair()
  {
    return getStringSettingValue(REPAIR_WORKFLOW_SETTINGS_PROPERTY);

  }

  /**
   * Get AssignmentEmailEnabled value.
   * 
   * @return String
   */
  public String getAssignmentEmailEnabled()
  {
    return getStringSettingValue(ASSIGNMENT_EMAIL_ENABLED);
  }

  /**
   * Get OverrideRCEndPointEnabled value.
   * 
   * @return String
   */
  public String getOverrideRCEndPointEnabled()
  {
    return getStringSettingValue(OVERRIDE_RC_END_POINT_ENABLED);
  }

  /**
   * Get AppCodeRCOverride value from Set file.
   * 
   * @return String
   */
  public String getAppCodeRCOverride()
  {
    return getStringSettingValue(APPCODE_RC_OVERRIDE);
  }
  
  /**
   * Get OverrideCanUploadEstimateFlagCoCodes value from Set file.
   * 
   * @return String
   */
  public String getOverrideCanUploadEstimateFlagCoCodes() {
    return getStringSettingValue(OVERRIDE_CAN_UPLOAD_ESTIMATE_FLAG_CO_CODES);
  }  

  /**
   * This method returns the String value from SET file for a given xpath.
   * 
   * @param settingName
   *          Given xpath
   * @return String
   */
  private String getStringSettingValue(String settingName)
  {
    String methodName = "getStringSettingValue";
    logger.entering(CLASS_NAME, methodName);
    String returnValue = SystemConfiguration.getSettingValue(settingName);
    validateStringSettingValue(settingName, returnValue);
    logger.exiting(CLASS_NAME, methodName);
    return returnValue;
  }

  /**
   * This method validates the value from SET file for a given xpath. It
   * throws a RuntimeException if the value comes out to be null or blank.
   * 
   * @param settingName
   *          Given xpath
   * @param value
   *          value
   */
  private void validateStringSettingValue(String settingName, String value)
  {
    if (!(value != null && value.length() > 0)) {
      throw new RuntimeException("The " + settingName + " value cannot be null");
    }
  }

  /**
   * Get ignoreEClaimRoute APP Code value.
   * 
   * @return String
   */
	public String getIgnoreEclaimRoute() {
		return getStringSettingValue(IGNORE_ECLAIM_ROUTE);
	}
	
	public String getIgnoreRCApplicationCode() {
		return getStringSettingValue(IGNORE_RC);
	}

}
