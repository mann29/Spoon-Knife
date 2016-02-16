package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

/**
 * The Interface SystemConfigurationProxy.
 */
public interface SystemConfigurationProxy {

	String getBroadcastMessageEmailXsltFilePath();

	String getPartiallossSIPDelGroup();

	String getPartiallossSIPDelDestinationSettingName();

	String getPartiallossSIPDelDestinationEclaim();

	String getBroadcastMessageGroup();

	String getEmailToRcpntReqSettingName();

	String getJndiFactory();

	String getProviderUrl();

	String getAPDQueueConnectionfactory();

	String getAPDBroadcastMessageQueueName();

	String getECAlertMessageDesc();

	String getWCAPUserApplCode();

	String getEstimatorApplCode();

	String getEventIdForWCAPUser();

	String getSenderName();

	String getSenderEmailId();

	String getPublishToMessageQueue();

	String getEJBJndi();

	// Fix 117663 : Adding methods from APDDeliverySystemConfig.
	String getAlertDeliveryEventId();

	String getRADeliveryEventId();

	String getEstimateDeliveryEventId();

	String getApprAsmtNtfnDeliveryEventId();

	String getEstimateUploadAlertDeliveryActivityOperation();

	String getArtifactAlertDeliveryToShopActivityOperation();

	String getArtifactAlertDeliveryToStaffActivityOperation();

	String getEstimateDeliveryActivityOperation();

	String getAppCode();

	String getSubjectForCreateRepair();

	String getSubjectForUpdateRepair();

	String getSubjectForCompleteRepair();

	String getSubjectForCancelRepair();

	String getSubjectForCreateRework();

	String getSubjectForUpdateRework();

	String getSubjectForCompleteRework();

	String getSubjectForCancelRework();

	String getXsltFilePath();

	String getReqForStaffSupplementDeliveryEventId();

	String getReqForStaffSupplementAcceptedActivityOperation();

	String getReqForStaffSupplementRejectedActivityOperation();

	String getApprAsmtNtfnUpdateActivityOperation();

	String getApprAsmtNtfnCancelActivityOperation();

	String getRprAsmtDefType();

	String getReworkAsmtDefType();

	String getRprAsmtDefTypeIF();

	String getReworkAsmtDefTypeIF();

	String getApprAsmtNtfnDefType();

	String getEstimateUploadTypes();

	String getAppCodeForStaff();

	String getAPDDeliveryServiceCompanyOrgType();

	String getNICBServiceCompanyGroupName();

	String getNICBReportDeliveryHandler();

	String getPDFAttachmentTypesForRepair();

	String getTextAttachmentTypesForRepair();

	String getMIEEstimateAttachmentTypes();

	String getWorkFlowSettingFlagForRepair();

	String getWorkFlowSettingPropertyForRepair();

	String getAssignmentEmailEnabled();

	String getOverrideRCEndPointEnabled();

	String getAppCodeRCOverride();
	
	String getOverrideCanUploadEstimateFlagCoCodes();

	String getEmailSenderEmailId();

	String getEmailSenderName();

    String getSettingValue(
            String name, String defaultValue);
    
    String getSettingValue(String name);    
    
    String getIgnoreEclaimRoute();
    
    String getIgnoreRCApplicationCode();
}
