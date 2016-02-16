package com.mitchell.services.core.partialloss.apddelivery.utils; 

/**
 * 
 * @version %I%, %G%
 * @since 1.0
 */
public class APDDeliveryConstants { 
    
    /**
     * default constructor.
     */
    protected APDDeliveryConstants() { }
    
    public static final String APP_NAME = "CORESERVICES";
    public static final String BUSINESS_SERVICE_NAME = "CLAIM_SERVICE";
    public static final String MODULE_NAME = "APD_DELIVERY_SERVICE";
    
    // Message/ Artifact Types
    public static final String ORIGINAL_ESTIMATE_ARTIFACT_TYPE = "ORIGINAL_ESTIMATE";
    public static final String ALERT_ARTIFACT_TYPE = "ALERT";
    public static final String NICB_REPORT_TYPE = "NICB_REPORT";
    public static final String BROADCAST_MESSAGE_TYPE = "BROADCAST_MESSAGE";
    public static final String ESTIMATE_STATUS_ARTIFACT_TYPE = "ESTIMATE_STATUS";
    public static final String SUPPLEMENT_ARTIFACT_TYPE = "SUPPLEMENT"; 
    public static final String REPAIR_ASSIGNMENT_ARTIFACT_TYPE = "REPAIR_ASSIGNMENT";
    public static final String REWORK_ASSIGNMENT_ARTIFACT_TYPE = "REWORK_ASSIGNMENT";
    public static final String APPR_ASMT_NTFN_ARTIFACT_TYPE = "APPR_ASGMT_NOTIFY";
    public static final String REPAIR_ASSIGNMENT_DEFINITION_TYPE = "RepairAssignment";
    public static final String REWORK_ASSIGNMENT_DEFINITION_TYPE = "ReworkAssignment";
    public static final String REPAIR_ASSIGNMENT_DEFINITION_TYPE_IF = "RepairAssignment_IF";
    public static final String REWORK_ASSIGNMENT_DEFINITION_TYPE_IF = "ReworkAssignment_IF";
    public static final String REQUEST_STAFF_SUPPLEMENT_ARTIFACT_TYPE = "REQ_SUPP_ASGN";
    
    // Message Status
    public static final String MSG_STATUS_DISPATCHED = "DISPATCHED";
    public static final String MSG_STATUS_CANCEL = "CANCEL";
    public static final String MSG_STATUS_CREATE = "CREATE";
    public static final String MSG_STATUS_UPDATE = "UPDATE";
    public static final String MSG_STATUS_COMPLETE = "COMPLETE";
    

    // EJB constants - START
    public static final int ERROR_EJB = 159600;
    public static final String ERROR_EJB_MSG = "Error getting APDDeliveryServiceEjb EJB";
    // EJB constants - END
    
    // Alert constants - START
    public static final int ERROR_INSUFFICIENT_ALERT_INPUT = 159601;
    public static final int ERROR_ALERT_DELIVERY = 159602;
    public static final String ERROR_INSUFFICIENT_ALERT_INPUT_MSG = "Insufficient Alert i/p to complete the request";
    public static final String ERROR_ALERT_DELIVERY_MSG = "Error occurred while delivering Alert";
    public static final String ERROR_ALERTS_INFO_NULL = "Error in APDAlertInfoType- found null";
    public static final String ALERTS_MESSAGE_STATUS = "UPLOAD_STATUS";
    // Alert constants - END
    
    // NICB Report constants- START
    public static final int ERROR_NICB_REPORT_DELIVERY = 159615;
    public static final String ERROR_NICB_REPORT_DELIVERY_MSG = "Error occurred while delivering NICB report";
    public static final String ERROR_NICB_REPORT_DELIVERY_INFO_NULL = "Error in APDNICBInfoType- found null";
    public static final String ERROR_NICB_REPORT_DLVR_HDLR_EVT_ID_NOT_FOUND = "NICB Report Delivery Handler event ID not found";
    public static final String NICB_REPORT_DELIVERY_SUCCESS_MSG = "NICB Report is successfully delivered";
    // NICB Report constants- END
    
    // Appraisal Assignment constants- START
    public static final int ERROR_DELIVER_APPRAISAL_ASMT = 159603;
    public static final String ERROR_DELIVER_APPRAISAL_ASMT_MSG = "Error while delivering Appraisal Assignment";
    // Appraisal Assignment constants- END
    
    // Repair/Rework Assignment constants - START
    public static final int ERROR_RA_DELIVERY = 159604;
    public static final String ERROR_RA_INFO_NULL = "Error in APDRepairAssignmentInfoType- found null";
    public static final String ERROR_RA_DELIVERY_MSG = "Error occurred while delivering Repai/Rework Assignment";
    // Repair/Rework Assignment constants - END
    
    // Appraisal Assignment Notification constants- START
    public static final int ERROR_APPR_ASMT_NTFN_INFO_NULL = 159613;
    public static final int ERROR_APPR_ASMT_NTFN_DELIVERY = 159614;
    public static final String ERROR_APPR_ASMT_NTFN_INFO_NULL_MSG = "APDAppraisalAssignmentNotificationInfoType found null";
    public static final String ERROR_APPR_ASMT_NTFN_DELIVERY_MSG = "Error occurred while delivering Appraisal Assignment Notification";
    // Appraisal Assignment Notification constants- END
    
    // Request For Staff Supplement Constants - START
    public static final String REQ_STAFF_SUPPLEMENT_INFO_NULL = "Error in ReqStaffSupplementInfo- found null";
    public static final String REQ_STAFF_SUPPLEMENT_ACCEPT = "ACCEPTED";
    public static final String REQ_STAFF_SUPPLEMENT_REJECT = "REJECTED";
    //Request For Staff Supplement Constants - END
    
    //Event Status Constants --START
    public static final int ERROR_IN_ESTIMATE_STATUS_INPUT = 159605;
    public static final int ERROR_IN_POPULATING_MITCHELL_ENVELOPE = 159606;
    public static final int ERROR_INSUFFICIENT_ESTIMATE_INPUT = 159607;
    public static final String ERROR_IN_ESTIMATE_STATUS_INPUT_MSG = "Error In Estimate Status Event  i/p to complete the request";
    public static final String ERROR_IN_POPULATING_MITCHELL_ENVELOPE_MSG = "Error in Creating the Mitchell Envelope"; 
    public static final String ERROR_INSUFFICIENT_ESTIMATE_INPUT_MSG = "Mandatory Fields are Missing in BaseAPDCommmonType";
    public static final String MESSAGE_STATUS = "ESTIMATE_REVIEW_RESULTS";
    //Event Status Constants --END
    
    // other constants - START
    public static final int ERROR_INVALID_APD_DELIVERY_CONTEXT = 159608;
    public static final int ERROR_DELIVER_ARTIFACT = 159609;
    public static final int ERROR_USER_INFO_SERVICE = 159610;
    public static final int ERROR_PUBLISHING_TO_MESSAGE_BUS = 159611;
    public static final int ERROR_UNSUPPORTED_USER_TYPE = 159612;
    public static final String ERROR_INVALID_APD_DELIVERY_CONTEXT_MSG = "Invalid APDDeliveryContext";
    public static final String ERROR_DELIVER_ARTIFACT_MSG = "Error occurred in delivering Artifact";
    public static final String ERROR_USER_INFO_SERVICE_MSG = "Error occurred in UserInfoService";
    public static final String ERROR_PUBLISHING_TO_MESSAGE_BUS_MSG = "Error occurred while publishing message to MessageBus.";
    public static final String ERROR_UNSUPPORTED_USER_TYPE_MSG = "Usupported user type";
    public static final String ERROR_INVALID_ARTIFACT_TYPE_MSG = "Invalid Artifact Type";
    public static final String ERROR_TARGET_DRP_USERINFO_NULL_MSG = "Error in TargetDRPUserInfo- found null";
    // other constants - END
    
    // Constants for applogging - START
    public static final int USER_IS_NOT_SHOP_USER = 159691;
    public static final int DELIVERY_END_POINT_NON_PLATFORM = 159692;
    public static final int EMAIL_ID_NOT_FOUND_FOR_SHOP = 159693;
    public static final int NICB_REPORT_DELIVERED_TO_NON_PLATFORM = 159694;
    // Constants for applogging - END
    
    // Error 
    public static final int ERROR_PARSE_JMS_MSG = 159616;
    public static final int ERROR_APD_BROADCAST_MESSAGE_MDB = 159617;
    public static final int ERROR_CUSTOM_SETTING = 159618;
    public static final int ERROR_UNEXPECTED = 159619;
    public static final int ERROR_XSLT = 159620;
    public static final int ERROR_SENDING_BROADCAST_MSG_JMS_QUEUE = 159621;
    public static final int ERROR_CORRELATED_ERROR = 159622;
    public static final int ERROR_SENDING_BROADCAST_MSG_RUNTIME_ERROR = 159623;
    public static final int ERROR_RECIPIENT_USERINFO = 159624;
    
    public static final String ERROR_PARSE_JMS_MSG_MSG = "Error occurred in parsing the JMS message";
    public static final String ERROR_APD_BROADCAST_MESSAGE_MDB_MSG = "Error occured in Broadcast Message MDB";
    public static final String ERROR_CUSTOM_SETTING_MSG = "Error occured in Custom Settings";
    public static final String ERROR_UNEXPECTED_MSG = "An unexpected error has occured";
    public static final String ERROR_XSLT_MSG = "Error occured in XSLT transformation";
    public static final String ERROR_SENDING_BROADCAST_MSG_JMS_QUEUE_MSG = "Error while sending message to Broadcast Message JMS Queue";
    public static final String ERROR_CORRELATED_ERROR_MSG = "Correlated Error message";
    public static final String ERROR_SENDING_BROADCAST_MSG_RUNTIME_ERROR_MSG = "Runtime Error in Send Broadcast Message";    
    public static final String ERROR_RECIPIENT_USERINFO_MSG = "Error in RecipientUserInfo : found Null";
    
    public static final String MESSAGE_TYPE = "MessageType";
    public static final String MESSAGE_STATUS2 = "MessageStatus";
    public static final String NOTES = "Notes";
    public static final String UPDATE_NOTES = "UpdatedNotes";
    public static final String MITCHELL_COMPANY_CODE = "MitchellCompanyCode";
    public static final String CANCELLATION_REASON = "Reason";
    public static final String MITCHELL_WORK_ITEM_ID = "MitchellWorkItemId";
    public static final String TASK_ID = "TaskId";
    public static final String CORRELATION_ID = "CorrelationId";
    public static final String PUBLIC_INDEX = "PublicIndex";
    public static final String CLAIM_NUMBER = "ClaimNumber";
    public static final String SHOP_ID = "ShopId";
    public static final String REVIEWER_CO_CD = "ReviewerCoCd";
    public static final String REVIEWER_ID = "ReviewerId";
    public static final String USER_CO_CD = "UserCoCd";
    public static final String USER_ID = "UserId";
    public static final String ESTIMATE_AUTHOR = "EstimateAuthor";
    public static final String ESTIMATOR_NAME = "EstimatorName";
    public static final String ASSIGNMENT_BMS_IDENTIFIER_CREATE = "AssignmentBMS";
    public static final String ASSIGNMENT_BMS_IDENTIFIER_UPDATE = "UpdatedAssignmentBMS";
    public static final String ESTIMATE_BMS_IDENTIFIER = "EstimateBMS";
    public static final String WC_ATTACHMENT_INFO_IDENTIFIER_CREATE = "WCAttachmentInfo";
    public static final String WC_ATTACHMENT_INFO_IDENTIFIER_UPDATE = "UpdatedWCAttachmentInfo";
    public static final String WORK_PROCESS_UPDATE_MESSAGE = "WorkProcessUpdateMessage";
    public static final String WORK_PROCESS_INITIATION_MESSAGE_IDENTIFIER = "WorkProcessInitiationMessage";
    public static final String ESTIMATOR_USER_ID = "EstimatorUserId";
    public static final String SCHEDULE_DATE_TIME = "ScheduleDateTime";
    public static final String SUFFIX_ID = "SuffixId";
	public static final String CAN_UPLOAD_ESTIMATE = "CanUploadEstimate";
    
    public static final String ASSIGNMENT_BMS_MITCHELL_DOC_TYPE = "CIECA_BMS_ASG_XML";
    public static final String ESTIMATE_BMS_MITCHELL_DOC_TYPE = "CIECA_BMS_EST_XML";
    public static final String WC_ATTACHMENT_INFO_MITCHELL_DOC_TYPE = "AttachmentInfo";
    public static final String WORK_PROCESS_INITIATION_MESSAGE_MITCHELL_DOC_TYPE = "WorkProcessInitiationMessage";
    
    public static final String CARRIER_ROLE = "CARRIER";
    public static final String SHOP_ROLE = "SHOP";
    public static final String WORK_PROCESS_VERSION = "1.0";
    
    public static final String RA_MESSAGE_STATUS_CREATE = "Create";
    public static final String RA_MESSAGE_STATUS_UPDATE = "Update";
    public static final String RA_MESSAGE_STATUS_CANCEL = "Cancel";
    public static final String RA_MESSAGE_STATUS_COMPLETE = "Complete";
    
    public static final String APPR_ASMT_NTFN_MESSAGE_STATUS_CREATE = "Create";
    public static final String APPR_ASMT_NTFN_MESSAGE_STATUS_UPDATE = "Update";
    public static final String APPR_ASMT_NTFN_MESSAGE_STATUS_CANCEL = "Cancel";
    
    public static final String ACTIVITY_OPERATION_UPDATE_RA = "UPDATE_REPAIR_ASSIGNMENT";
    public static final String ACTIVITY_OPERATION_CANCEL_RA = "CANCEL_REPAIR_ASSIGNMENT";
    public static final String ACTIVITY_OPERATION_COMPLETE_RA = "COMPLETE_REPAIR_ASSIGNMENT";
    
    public static final String ACTIVITY_OPERATION_UPDATE_REWORK = "UPDATE_REWORK_ASSIGNMENT";
    public static final String ACTIVITY_OPERATION_CANCEL_REWORK = "CANCEL_REWORK_ASSIGNMENT";
    public static final String ACTIVITY_OPERATION_COMPLETE_REWORK = "COMPLETE_REWORK_ASSIGNMENT";
    
    public static final String BROADCAST_ALERT_ORIGIN = "APDDelivery";   
    public static final int BROADCAST_MSG_RECIPIENT_NOT_WCAP_OR_ESTIMATOR = 159695;
    public static final int BROADCAST_MSG_WCAP_RECIPIENT = 159696;      
    public static final String BROADCAST_MSG_RECIPIENT_NOT_WCAP_OR_ESTIMATOR_MSG = "Broadcast Message recipient is neither WCAP nor Estimator";
    public static final String BROADCAST_MSG_WCAP_RECIPIENT_MSG = "Broadcast Message recipient is WCAP user";
    public static final int BROADCAST_MSG_ESTIMATOR_RECIPIENT = 159697;
    public static final String BROADCAST_MSG_ESTIMATOR_RECIPIENT_MSG = "Broadcast Message recipient is Estimator user";
    
    
    public static final String USER_TYPE_BODYSHOP = "SHOP";
    public static final String REPAIR_STATUS_NOTIFICATION = "REPAIR_STATUS_NOTIFICATION";
    
    public static final String BC_MSG_IDENTIFIER = "BroadcastMessage";
    public static final String BC_MSG_MITCHELLDOCTYPE = "BroadcastMessage";
    public static final String BC_MSG_XMLBEANCLASSNAME = "com.mitchell.schemas.BroadcastMessageDocument";
    public static final String TRUE = "true";
    
    // External Estimation System 
    public static final String CUSTOM_SETTING_GROUP_NAME_CARRIER_GLOBAL_SETTINGS = "CARRIER_GLOBAL_SETTINGS";
    public static final String CUSTOM_SETTING_EXTERNAL_ESTIMATING_PLATFORM_INTEGRATION = "EXTERNAL_ESTIMATING_PLATFORM_INTEGRATION";
    public static final String GTE_CUSTOM_SETTING_EXTERNAL_ESTIMATING_PLATFORM_INTEGRATION_VALUE = "GTE";
	
	// PBI 205005 : Add RC Thumbnails
    public static final String DOCTYPE_PHOTO = "PHOTO";
    public static final String ATTACHMENTTYPE_THUMBNAIL= "THUMBNAIL";
    public static final String ATTACHMENTTYPE_IMAGE= "IMG";
    
    public static final String PREFERRED_SCHEDULE_DATE = "PreferredScheduleDate";
    public static final String PREFERRED_SCHEDULE_WINDOW = "PreferredScheduleWindow";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd'T'HH:mm:ss";
} 
