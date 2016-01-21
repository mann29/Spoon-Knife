package com.mitchell.services.business.partialloss.appraisalassignment;

/**
 * This class represents constants of AppraisalAssignment Service.
 * 
 * 
 * 
 */
public final class AppraisalAssignmentConstants
{

  public static final String WORK_ASSIGNMENT_CREATE_EVENT = "CREATED_EVENT";
  public static final String WORK_ASSIGNMENT_UPDATE_EVENT = "UPDATED_EVENT";
  public static final String WORK_ASSIGNMENT_DISPATCHED_EVENT = "DISPATCHED_EVENT";
  public static final String WORK_ASSIGNMENT_REASSIGNED_EVENT = "REASSIGNED_EVENT";
  public static final String WORK_ASSIGNMENT_CANCELLED_EVENT = "CANCELLED_EVENT";
  public static final String VehicleTypeExpertiseSkillsList = "/AppraisalAssignment/VehicleTypeExpertiseSkillsList";
  public static final String CLAIM_ID = "ClaimId";
  public static final String EXPOSURE_ID = "ExposureId";
  public static final String SYSTEM_USERID = "SystemUserId";

  
  public static final String DISPOSITION_DISPATCHED = "DISPATCHED";
  public static final String DISPOSITION_CANCELLED = "CANCELLED";
  public static final String DISPOSITION_NOT_READY = "NOT READY";
  public static final String DISPOSITION_READY = "READY";
  public static final String DISPOSITION_COMPLETED = "COMPLETED";
  public static final String DISPOSITION_IN_PROGRESS = "IN_PROGRESS";
  public static final String DISPOSITION_REJECTED = "REJECTED";

  public static final String LUNCH_ASSIGNMENT_TYPE = "LUNCH";

  public static final String SERVICE_CENTER = "SER_CEN";
  public static final String MITCHELL_ENV_NAME_COMPANY_CODE = "MitchellCompanyCode";
  public static final String MITCHELL_ENV_NAME_MITCHELL_WORKITEMID = "MitchellWorkItemId";
  public static final String MITCHELL_ENV_NAME_CLAIM_MASK = "ClaimMask";
  public static final String MITCHELL_ENV_NAME_ASSIGNMENT_DOC_ID = "AppraisalAssignmentDocId";
  public static final String MITCHELL_ENV_NAME_SYSTEM_USER_ID = "SystemUserId";
  public static final String MITCHELL_ENV_NAME_DISPOSITION = "Disposition";
  public static final String MITCHELL_ENV_NAME_STATUS = "Status";
  public static final String MITCHELL_ENV_NAME_WA_TASK_ID = "WorkAssignmentTaskId";
  public static final String MITCHELL_ENV_NAME_ASSIGNMENT_CLAIM_ID = "AppraisalAssignmentClaimId";
  public static final String MITCHELL_ENV_NAME_ASSIGNMENT_CLAIM_EXPOSURE_ID = "AppraisalAssignmentClaimExposureId";
  public static final String ORIGINAL_ASSIGNMENT_TYPE = "ORIGINAL_ESTIMATE";
  public static final String SUPPLEMENT_ASSIGNMENT_TYPE = "SUPPLEMENT";
  public static final String INBOUND_FILE_NAME_PREFIX = "AppraisalAssignment";
  public static final String FILE_EXTENSION_XML = ".xml";
  public static final String MODULE_NAME = "APPRAISAL_ASSIGNMENT_SERVICE";
  public static final String APP_NAME = "PARTIALLOSS";
  public static final String CLAIM_NUMBER = "UNKNOWN";
  public static final String WORK_ITEM_TYPE = "ASSIGNMENT";
  public static final String DEFAULT_WORKFLOW_USERID = "WORKFLOW";
  public static final String CSET_GROUP_NAME = "APPRAISAL_ASSIGNMENT_SERVICE_SETTINGS";
  public static final String CSET_SETTING_REQUEST_UPDATED_TASK_RECEIVER_EVENT_CODE_ID = "EVENT_ID_UPDATED";
  public static final String CSET_SETTING_REQUEST_REASSIGNED_TASK_RECEIVER_EVENT_CODE_ID = "EVENT_ID_REASSIGNED";
  public static final String CSET_SETTING_REQUEST_CANCELLED_TASK_RECEIVER_EVENT_CODE_ID = "EVENT_ID_CANCELLED";
  public static final String CSET_SETTING_REQUEST_COMPLETED_TASK_RECEIVER_EVENT_CODE_ID = "EVENT_ID_COMPLETED";
  public static final String CSET_SETTING_REQUEST_CREATE_TASK_SAN_EVENT_CODE_ID = "EVENT_ID_CREATE_SAN";
  public static final String CSET_SETTING_REQUEST_WORKGROUP_ID_CODE_ID = "WORKGROUP_ID";
  public static final String CSET_SETTING_REQUEST_DISPATCHED_TASK_RECEIVER_EVENT_CODE_ID = "EVENT_ID_DISPATCHED";
  public static final String CSET_CLIAM_GROUP_NAME = "CLAIMSERVICES_CLAIM_SETTINGS";
  public static final String CSET_SETTING_WCA_SCHED_REQD_STAFF = "CONFIG_SCHEDULE";
  public static final String CSET_SETTING_REQUEST_CLAIMSERVICES_CLAIM_CLAIMNUMBERMASK = "CLAIMSERVICES_CLAIM_CLAIMNUMBERMASK";
  public static final String CSET_SETTING_REQUEST_CREATED_TASK_RECEIVER_EVENT_CODE_ID = "EVENT_ID_CREATED";
  public static final String CSET_SETTING_REQ_CLOSED_EVENT_ID = "EVENT_ID_REQ_CLOSED";
  public static final String CSET_SETTING_REQ_REJECTED_EVENT_ID = "EVENT_ID_REQ_REJECTED";
  public static final String CSET_SETTING_ENABLE_WC_DISPATCH_CULDESAC = "ENABLE_WC_DISPATCH_CULDESAC";
  
  // Collaborative Custom Setting GroupName and Setting Name
  public static final String SYSCONF_COLLOBORATIVE_GROUPNAME = "CARR_STANDARD_WORKFLOW_SETTINGS";
  public static final String SYSCONF_COLLOBORATIVE_SETTINGNAME = "CARR_STANDARD_WORKFLOW_USE_COLLABORATIVE_WORKFLOW";
  public static final String COMPANY_TYPE = "COMPANY";
  public static final String USER_TYPE = "USER";
  public static final String CUSTOM_SETTING_CLAIM_SETTINGS_GRP = "CLAIMSERVICES_CLAIM_SETTINGS";
  // custom setting name for Suffix label
  public static final String SETTING_SUFFIX_LABEL = "CLAIMSERVICES_CLAIM_SUFFIX_ID_FIELD_LABEL";
  //custom setting name for Claim Mask
  public static final String SETTING_CLAIM_MASK = "CLAIMSERVICES_CLAIM_CLAIMNUMBERMASK";
  
  public static final String STATUS_Y = "Y";
  public static final String ESTIMATECATEGORY_COPY = "COPY";
  public static final String STATUS_N = "N";
  
  public static final String DEFAULT_ENCODING="UTF-8";
  // public static final int APPLOG_STATUS_PROCESSED_NO_ERROR = 0;

  public static final int APPLOG_SAVE_ASSIGNMENT_SUCCESS = 108301;
  public static final int APPLOG_SAVE_SUPPLEMENT_ASSIGNMENT_SUCCESS = 108303;
  public static final int APPLOG_CANCEL_ASSIGNMENT_SUCCESS = 108305;
  public static final int APPLOG_DISPATCH_ASSIGNMENT_SUCCESS = 108307;
  public static final int APPLOG_GET_LATEST_ESTIMATE_SUCCESS = 108309;
  public static final int APPLOG_UNCANCEL_ASSIGNMENT_SUCCESS = 108311;
  public static final int APPLOG_UPDATE_ASSIGNMENT_SUCCESS = 108313;
  public static final int APPLOG_ASSIGN_ASSIGNMENT_SUCCESS = 108315;
  public static final int APPLOG_IS_ASSIGNMENT_EVER_DISPATCHED_SUCCESS = 108317;

  // ------------------- BEGIN : MAXIMA AppLog Codes
  // ------------------------------
  public static final int APPLOG_UNSCHEDULE_ASSIGNMENT_SUCCESS = 108321;
  public static final int APPLOG_ONHOLD_ASSIGNMENT_SUCESS = 108322;
  public static final int APPLOG_REMOVE_ONHOLD_ASSIGNMENT_SUCESS = 108323;
  public static final int APPLOG_UPDATE_DISPOSITION_ASSIGNMENT_SUCCESS = 108324;
  // ------------------- END : MAXIMA AppLog Codes
  // ------------------------------

  public static final int APPLOG_CREATE_TASK_REQ_SUPP_SUCCESS = 108325;
  public static final int APPLOG_CANCEL_TASK_REQ_SUPP_SUCCESS = 108326;
  public static final int APPLOG_REJECT_TASK_REQ_SUPP_SUCCESS = 108327;
  public static final int APPLOG_APD_CALL_SUCCESS = 108328;
  public static final int APPLOG_VECHILE_UPDATE_SUCCESS = 108329;

  // ------------------ Added for reschedule or incomplete assignment status update app log
  public static final int APPLOG_RESCHEDULE_SUCCESS = 108330;
  public static final int APPLOG_INCOMPLETE_SUCCESS = 108331;

  // ------------------ Added for duplicate update event bug fix
  public static final int APPLOG_SAVE_AND_SEND_AA_SUCCESS = 108332;

  // ------------------ Added for update appraisal assignment API
  public static final int APPLOG_UPDATE_ADDRESS_SUCCESS = 108333;

  public static final int MIN_SERVICE_ERROR = 108301;
  public static final int MAX_SERVICE_ERROR = 108499;

  public static final int ERROR_SAVE_AA_STORECLAIMINFO = 108301;
  public static final int ERROR_SAVE_AA_SAVEWA = 108302;
  public static final int ERROR_SAVE_AA_SAVEBMS = 108303;

  public static final int ERROR_SAVE_AA_EXPOSUREACTIVITYLOG = 108306;
  public static final int ERROR_AA_POPULATEAADTO = 108307;
  public static final int ERROR_SAVE_SUPP_AA_GETCONTAINERME = 108308;
  public static final int ERROR_SAVE_SUPP_AA_SAVEBMS = 108309;
  public static final int ERROR_SAVE_SUPP_AA_GETAA = 108310;
  public static final int ERROR_UPDATE_AA_UPDATEREVIEWASSIGNMENT = 108311;
  public static final int ERROR_SAVE_SUPP_AA_SAVEWA = 108312;
  public static final int ERROR_SAVE_SUPP_AA_EXPOSUREACTIVITYLOG = 108313;
  public static final int ERROR_SAVE_SUPP_AA_POPULATEAADTO = 108314;
  public static final int ERROR_DISPATCH_AA_SAVEWA = 108315;
  public static final int ERROR_DISPATCH_AA_EXPOSUREACTIVITYLOG = 108316;
  public static final int ERROR_GET_LATEST_ESTIMATE = 108317;
  public static final int ERROR_CANCEL_AA_WABYTASKID = 108318;
  public static final int ERROR_CANCEL_AA_WAPARSEXML = 108319;
  public static final int ERROR_CANCEL_AA_WASAVE = 108320;
  public static final int ERROR_CANCEL_AA_EXPOSUREACTIVITYLOG = 108321;
  public static final int ERROR_UNCANCEL_AA_WABYTASKID = 108322;
  public static final int ERROR_UNCANCEL_AA_WAPARSEXML = 108323;
  public static final int ERROR_UNCANCEL_AA_WASAVE = 108324;
  public static final int ERROR_UNCANCEL_AA_EXPOSUREACTIVITYLOG = 108326;
  public static final int ERROR_UPDATE_AA_GETCONTAINERME = 108327;
  public static final int ERROR_UPDATE_AA_UPDATEBMS = 108328;
  public static final int ERROR_UPDATE_AA_GETAA = 108329;
  public static final int ERROR_UPDATE_AA_SAVEWA = 108330;
  public static final int ERROR_UPDATE_AA_EXPOSUREACTIVITYLOG = 108331;
  public static final int ERROR_ASSIGN_AA_GETUSERINFO = 108333;
  public static final int ERROR_ASSIGN_AA_ASSIGNUIDTOWA = 108334;
  public static final int ERROR_ASSIGN_AA_ASSIGNUIDTOMEBMS = 108335;
  public static final int ERROR_ASSIGN_AA_EXPOSUREACTIVITYLOG = 108336;
  public static final int ERROR_INIT_APPRAISALASSIGNMENT = 108337;
  public static final int ERROR_AA_FETCHCLAIMMASK = 108338;
  public static final int ERROR_SAVE_SUPP_AA_FETCHCLAIMMASK = 108339;
  public static final int ERROR_UPDATE_AA_WABYTASKID = 108341;
  public static final int ERROR_UPDATE_AA_UPDATECLAIM = 108342;
  public static final int ERROR_ASSIGN_AA_WABYTASKID_AND_WAPARSEXML = 108343;
  public static final int ERROR_VALIDATEINSPECTIONSITEADDRESS = 108345;
  public static final int ERROR_SAVEASSIGNMENTBEENUPDATEDFLAG = 108346;
  public static final int ERROR_CLIENT_EJB = 108347;

  // Error Codes 108348 and 108349 - RESERVED BY SUPPLEMENT REQUEST
  // -- see SupplementRequestConstants.java
  public static final int ERR_NOSUCHUSER = 108348;
  public static final int ERR_SUBMIT_SUPPLEMENT_REQUEST = 108349;
  public static final int ERROR_DISPATCH_AA_UPDATEREVIEWASSIGNMENT = 108350;

  // -- New Error Codes for Supplement Capture Project (Q1-2010)
  public static final int ERROR_RETRIEVING_SUPPLEMENT_REQUEST = 108351;
  public static final int INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR = 108352;
  public static final int INVALID_AAAINFO_DOC_IN_MITCHELL_ENVELOPE_ERROR = 108353;
  public static final int ERROR_SAVING_AA_SUPP_EMAIL_DOC = 108354;
  public static final int ERROR_SUPP_REQUEST_EMAIL_EXPOSUREACTIVITYLOG = 108355;

  public static final String ERROR_SAVE_AA_STORECLAIMINFO_MSG = "WCAAService: Error creating Claim in Save Appraisal Assignment request.";
  public static final String ERROR_SAVE_AA_SAVEWA_MSG = "WCAAService: Error saving Work Assignment.";
  public static final String ERROR_SAVE_AA_SAVEBMS_MSG = "WCAAService: Error saving MitchellEnvelope Document in Save Appraisal Assignment request.";
  public static final String ERROR_SAVE_AA_GETAA_MSG = "WCAAService: Error retrieving Appraisal Assignment in Save Appraisal Assignment request.";
  public static final String ERROR_SAVE_AA_UPDATEWA_MSG = "WCAAService: Error updating Work Assignment in Save Appraisal Assignment request.";
  public static final String ERROR_SAVE_AA_EXPOSUREACTIVITYLOG_MSG = "WCAAService: Error creating Claim-Suffix Activity in Appraisal Assignment.";
  public static final String ERROR_AA_POPULATEAADTO_MSG = "WCAAService: Error creating AssignmentDTO and setting Disposition in Appraisal Assignment request.";
  public static final String ERROR_SAVE_SUPP_AA_GETCONTAINERME_MSG = "WCAAService: Error getting WCAAContainer from ME in Save Supplement Appraisal Assignment request.";
  public static final String ERROR_SAVE_SUPP_AA_SAVEBMS_MSG = "WCAAService: Error saving MitchellEnvelope Document in Save Supplement Appraisal Assignment request.";
  public static final String ERROR_SAVE_SUPP_AA_GETAA_MSG = "WCAAService: Error retrieving Appraisal Assignment in Save Supplement Appraisal Assignment request.";
  public static final String ERROR_UPDATE_AA_UPDATEREVIEWASSIGNMENT_MSG = "WCAAService: Error calling/updating update review assignment in Update/Dispatch Supplement Appraisal Assignment request.";
  public static final String ERROR_DISPATCH_AA_UPDATEREVIEWASSIGNMENT_MSG = "WCAAService: Error calling/updating update review assignment in Dispatch Supplement Appraisal Assignment request.";
  public static final String ERROR_SAVE_SUPP_AA_SAVEWA_MSG = "WCAAService: Error saving Work Assignment in Save Supplement Appraisal Assignment request.";
  public static final String ERROR_SAVE_SUPP_AA_EXPOSUREACTIVITYLOG_MSG = "WCAAService: Error creating Claim-Suffix Activity in Save Supplement Appraisal Assignment request.";
  public static final String ERROR_SAVE_SUPP_AA_POPULATEAADTO_MSG = "WCAAService: Error creating AssignmentDTO and setting Disposition in Save Supplement Appraisal Assignment request.";
  public static final String ERROR_DISPATCH_AA_SAVEWA_MSG = "WCAAService: Error getting/updating/saving Work Assignment in Dispatch Appraisal Assignment request.";
  public static final String ERROR_DISPATCH_AA_EXPOSUREACTIVITYLOG_MSG = "WCAAService: Error creating Claim-Suffix Activity in Dispatch Appraisal Assignment request.";
  public static final String ERROR_GET_LATEST_ESTIMATE_MSG = "WCAAService: Error getting list of Estimates in Get Latest Assignment request.";
  public static final String ERROR_CANCEL_AA_WABYTASKID_MSG = "WCAAService: Error getting Work Assignment by TaskID in Cancel Appraisal Assignment request.";
  public static final String ERROR_CANCEL_AA_WAPARSEXML_MSG = "WCAAService: Error parsing Work Assignment XML in Cancel Appraisal Assignment request.";
  public static final String ERROR_CANCEL_AA_WASAVE_MSG = "WCAAService: Error saving Work Assignment in Cancel Appraisal Assignment request.";
  public static final String ERROR_CANCEL_AA_EXPOSUREACTIVITYLOG_MSG = "WCAAService: Error creating Claim-Suffix Activity in Cancel Appraisal Assignment request.";
  public static final String ERROR_UNCANCEL_AA_WABYTASKID_MSG = "WCAAService: Error getting Work Assignment by TaskID in UNCancel Appraisal Assignment request.";
  public static final String ERROR_UNCANCEL_AA_WAPARSEXML_MSG = "WCAAService: Error parsing Work Assignment XML in UNCancel Appraisal Assignment request.";
  public static final String ERROR_UNCANCEL_AA_WASAVE_MSG = "WCAAService: Error saving Work Assignment in UNCancel Appraisal Assignment request.";
  public static final String ERROR_UNCANCEL_AA_GET_UPDATE_ESTIMATE_MSG = "WCAAService: Error retrieving and updating MitchellEnvelope Document in UNCancel Appraisal Assignment request.";
  public static final String ERROR_UNCANCEL_AA_EXPOSUREACTIVITYLOG_MSG = "WCAAService: Error creating Claim-Suffix Activity in UNCancel Appraisal Assignment request.";
  public static final String ERROR_UPDATE_AA_GETCONTAINERME_MSG = "WCAAService: Error getting WCAAContainer from ME in Update Appraisal Assignment request.";
  public static final String ERROR_UPDATE_AA_UPDATEBMS_MSG = "WCAAService: Error updating MitchellEnvelope Document in Update Appraisal Assignment request.";
  public static final String ERROR_UPDATE_AA_GETAA_MSG = "WCAAService: Error updating Appraisal Assignment in Update Appraisal Assignment request.";
  public static final String ERROR_UPDATE_AA_SAVEWA_MSG = "WCAAService: Error saving Work Assignment in Update Appraisal Assignment request.";
  public static final String ERROR_UPDATE_AA_EXPOSUREACTIVITYLOG_MSG = "WCAAService: Error creating Claim-Suffix Activity in Update Appraisal Assignment request.";
  // Unused error code in both MAXIMA and Jetta.
  // public static final String ERROR_UPDATE_AA_POPULATEAADTO_MSG =
  // "WCAAService: Error creating AssignmentDTO and setting Disposition in Update Appraisal Assignment request.";
  public static final String ERROR_ASSIGN_AA_GETUSERINFO_MSG = "WCAAService: Error getting User Information from UserInfo Service in Assign Appraisal Assignment request.";
  public static final String ERROR_ASSIGN_AA_ASSIGNUIDTOWA_MSG = "WCAAService: Error retrieving work assignment or assigning UserID to work assignment in Assign Appraisal Assignment request.";
  public static final String ERROR_ASSIGN_AA_ASSIGNUIDTOMEBMS_MSG = "WCAAService: Error retrieving ME-BMS or assigning UserID to ME-BMS in Assign Appraisal Assignment request.";
  public static final String ERROR_ASSIGN_AA_EXPOSUREACTIVITYLOG_MSG = "WCAAService: Error creating Claim-Suffix Activity in Assign Appraisal Assignment request.";
  public static final String ERROR_INIT_APPRAISALASSIGNMENT_MSG = "WCAAService: Error initializing/setting MitchellEnvelope and USerInfo in Initialize method request.";
  public static final String ERROR_AA_FETCHCLAIMMASK_MSG = "WCAAService: Error fetching ClaimMask Carrier Custom Settings in Appraisal Assignment request.";
  public static final String ERROR_SAVE_SUPP_AA_FETCHCLAIMMASK_MSG = "WCAAService: Error fetching ClaimMask Carrier Custom Settings in Save Supplement Appraisal Assignment request.";
  // Unused error code in both MAXIMA and Jetta.
  // public static final String ERROR_UPDATE_AA_FETCHCLAIMMASK_MSG =
  // "WCAAService: Error fetching ClaimMask Carrier Custom Settings in Update Appraisal Assignment request.";
  public static final String ERROR_UPDATE_AA_WABYTASKID_MSG = "WCAAService: Error getting Work Assignment by TaskID and parsing WA XML in Update Appraisal Assignment request.";
  public static final String ERROR_UPDATE_AA_UPDATECLAIM_MSG = "WCAAService: Error updating claim from assignment bms in Update Appraisal Assignment request.";
  public static final String ERROR_ASSIGN_AA_WABYTASKID_AND_WAPARSEXML_MSG = "WCAAService: Error getting Work Assignment by TaskID and/or parsing Work Assignment XML in Assign Appraisal Assignment request.";
  public static final String ERROR_IS_ASSIGNMENT_EVER_DISPATCHED_MSG = "WCAAService: Error checking assignment ever dispatched or not in Is Assignment Ever Dispatched request.";
  public static final String ERROR_VALIDATEINSPECTIONSITEADDRESS_MSG = "WCAAService: Error validating inspection site address through GEOService.";
  public static final String ERROR_SAVEASSIGNMENTBEENUPDATEDFLAG_MSG = "WCAAService: Error while updating flag named AssignmentBeenUpdated in WorkAssignment.";
  public static final String ERROR_GETTING_AA_WAPARSEXML_MSG = "WCAAService: Error parsing Work Assignment XML.";
  public static final String ERROR_GETTING_AA_WABYTASKID_AND_WAPARSEXML_MSG = "WCAAService: Error getting Work Assignment by TaskID and/or parsing Work Assignment XML in Appraisal Assignment request.";

  public static final String ERROR_CLIENT_EJB_MSG = "WCAAService Client EJB: Error getting Appraisal Assignment Service EJB.";

  // Start changes: for FAILED_NOTREADY integration.
  // Below error code, message is temporary and will finalize at the end of
  // the development
  public final static int ERROR_FAILED_NOTREADY = 153444;
  public final static String ERROR_FAILED_NOTREADY_MSG = "WCAAHelper: Error For FAILED_NOTREADY.";
  // End changes: for FAILED_NOTREADY integration.

  // -- New Error Codes/Messages for Supplement Capture Project (Q1-2010)
  public static final String ERROR_RETRIEVING_SUPPLEMENT_REQUEST_MSG = "WCAAService: Error retrieving Shop Supplement Request doc.";
  public static final String INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR_MSG = "WCAAService: MitchellEnvelope does not contain a valid CIECA Document";
  public static final String INVALID_AAAINFO_DOC_IN_MITCHELL_ENVELOPE_ERROR_MSG = "WCAAService: MitchellEnvelope does not contain a valid AdditionalAppraisalAssignmentInfo Document";
  public static final String ERROR_SAVING_AA_SUPP_EMAIL_DOC_MSG = "WCAAService: Error occurred storing Supplement Appraisal Assignment Request Email doc to the database.";
  public static final String ERROR_SUPP_REQUEST_EMAIL_EXPOSUREACTIVITYLOG_MSG = "WCAAService: Error creating Claim-Suffix Activity for Sending Supplement Appraisal Assignment E-Mail request.";

  // It is just a dummy, will change in future
  public static final int BMS_TO_WA_MAPPING_ERROR = 999999;

  public static final int STANDARD_PRIORITY_INT = 99;
  public static final int ELEVATED_PRIORITY_INT = 50;
  public static final int HIGH_PRIORITY_INT = 11;
  public static final int MUST_SEE_DATE_PRIORITY_INT = 1;
  public static final int MUST_SEE_TIME_PRIORITY_INT = 0;

  // Supplement Request Notification constants
  public static final String SUPPLEMENT_REQUEST_PREFIX = "SupplementRequest_";
  public static final String ME_DOC_SUPPLEMENT_REQUEST_PREFIX = "MEDoc_SupplementRequest_";
  public static final String FILE_EXTENSION_TXT = ".txt";
  public static final String FILE_EXTENSION_HTML = ".html";

  // MitchellEnvelope Metadata constants for CIECA BMS as Envelope Body
  public static final String ME_METADATA_CIECA_IDENTIFIER = "CIECABMSAssignmentAddRq";
  public static final String ME_METADATA_CIECA_MEDOCTYPE = "CIECA_BMS_ASG_XML";

  // MitchellEnvelope Metadata constants for AdditionalAppraisalAssignmentInfo
  // as Envelope Body
  public static final String ME_METADATA_AAAINFO_IDENTIFIER = "AdditionalAppraisalAssignmentInfo";
  public static final String ME_METADATA_AAAINFO_MEDOC_TYPE = "AdditionalAppraisalAssignmentInfo";

  public static final String SUP_REQUEST_ACT_LOG_MESSAGE = "Supplement Assignment was sent via e-mail";
  public static final String SUP_REQUEST_REF_TABLE = "SUP_AA_REQ_EMAIL_XML";

  // MAXIMA CONSTANTS
  public static final int SUCCESS = 0;
  public static final int FAILURE = 1;
  public static final int STALE_DATA = 2;
  public static final int CANCELED_CLOSED_TASK = 3;
  public static final int RESULT_UNKNOWN = 4;

  // ==================================== BEGIN DISPATCH BORAD
  // ====================================

  // ------------BEGIN: Error codes Multiple appraisal assignment for Dispatch
  // Board
  public static final int ERROR_GETTING_AA_WABYTASKID_AND_WAPARSEXML = 108356;
  public static final int ERROR_GETTING_AA_WAPARSEXML = 108358;
  public static final int ERROR_DISPATCHING_MULTIPLE_AA = 108359;
  public static final int ERROR_ASSIGNING_MULTIPLE_WCAA_AA = 108360;
  public static final int ERROR_CANCELLING_MULTIPLE_AA = 108361;
  public static final int ERROR_UNCANCELLING_MULTIPLE_AA = 108362;
  public static final int ERROR_SCHEDULING_MULTIPLE_AA = 108363;
  public static final int ERROR_UNSCHEDULING_MULTIPLE_AA = 108364;
  public static final int ERROR_HOLDLING_MULTIPLE_AA = 108365;
  public static final int ERROR_UNHOLDLING_MULTIPLE_AA = 108366;

  public static final String ERROR_DISPATCHING_MULTIPLE_AA_MSG = "WCAAService: Error Dispatching Multiple Appraisal Assignments.";
  public static final String ERROR_CANCELLING_MULTIPLE_AA_MSG = "WCAAService: Error Cancelling Multiple Appraisal Assignments.";
  public static final String ERROR_UNCANCELLING_MULTIPLE_AA_MSG = "WCAAService : Error Uncancelling Multiple Appraisal Assignments.";
  public static final String ERROR_SCHEDULING_MULTIPLE_AA_MSG = "WCAAService : Error Scheduling Multiple Appraisal Assignments.";
  public static final String ERROR_UNSCHEDULING_MULTIPLE_AA_MSG = "WCAAService : Error Unscheduling Multiple Appraisal Assignments.";
  public static final String ERROR_HOLDLING_MULTIPLE_AA_MSG = "WCAAService : Error Holding Multiple Appraisal Assignments.";
  public static final String ERROR_UNHOLDLING_MULTIPLE_AA_MSG = "WCAAService : Error Unholding Multiple Appraisal Assignments.";
  public static final String ERROR_ASSIGNING_MULTIPLE_WCAA_AA_MSG = "WCAAService: Error Assigning Multiple Appraisal Assignments.";
  // ------------END: Error codes Multiple appraisal assignment for Dispatch
  // Board

  // ------------BEGIN: Error codes Single appraisal assignment for Dispatch
  // Board

  public static final int ERROR_UNSCHEDULE_AA_UNSCHEDULEWA = 108367;
  public static final int ERROR_UNSCHEDULE_AA_UNSCHEDULEMEBMS = 108368;
  public static final int ERROR_STATUS_UPDATE_AA = 108369;
  public static final int ERROR_ONHOLD_AA_SAVINGWA = 108370;
  public static final int ERROR_CLAIM_SERVICE = 108371;
  public static final int ERROR_VEHICLE_TRACKING_UPDATE = 108372;
  // -----------------SINGLE DISPATCH BOARD----------------------------
  public static final int ERROR_UNSCHEDULE_AA = 108373;
  public static final int ERROR_OPERATION_NOT_SUPPORTED = 108374;
  public static final int ERROR_SCHEDULING_AA = 108375;
  public static final int ERROR_CANCELLING_AA = 108376;
  public static final int ERROR_UNCANCELLING_AA = 108377;
  public static final int ERROR_DISPATCHING_AA = 108378;
  public static final int ERROR_UNHOLDLING_AA = 108380;

  public static final String ERROR_UNSCHEDULE_AA_UNSCHEDULEWA_MSG = "WCAAService : Error removing schedule information from work assignment in UnSchedule Appraisal Assignment request.";
  public static final String ERROR_UNSCHEDULE_AA_UNSCHEDULEMEBMS_MSG = "WCAAService : Error retieving ME-BMS or removing schedule information from  ME-BMS in UnSchedule Appraisal Assignment request.";
  public static final String ERROR_STATUS_UPDATE_AA_MSG = "WCAAService : Error updating Appraisal Assignment STATUS/DISPOSITION.";
  public static final String ERROR_ONHOLD_AA_SAVINGWA_MSG = "WCAAService : Error saving work assignment in OnHold Appraisal Assignment request.";
  public static final String ERROR_OPERATION_NOT_SUPPORTED_MSG = "WCAAService:Operation Not Supported while updating Disposition.";
  // -----------------SINGLE DISPATCH BOARD MSG----------------------------
  public static final String ERROR_UNSCHEDULE_AA_MSG = "WCAAService : Error in UnSchedule Appraisal Assignment request.";
  public static final String ERROR_SCHEDULING_AA_MSG = "WCAAService : Error in Schedule Appraisal Assignment request.";
  public static final String ERROR_CANCELLING_AA_MSG = "WCAAService : Error in Cancel Appraisal Assignment request.";
  public static final String ERROR_UNCANCELLING_AA_MSG = "WCAAService : Error in UnCancel Appraisal Assignment request.";
  public static final String ERROR_DISPATCHING_AA_MSG = "WCAAService : Error in Dispatch Appraisal Assignment request.";
  public static final String ERROR_UNHOLDLING_AA_MSG = "WCAAService : Error in UnHolding Appraisal Assignment request.";
  public static final String ERROR_CLAIM_SERVICE_MSG = "WCAAService : Error from Claim Service.";
  public static final String ERROR_VEHICLE_TRACKING_UPDATE_MSG = "WCAAService : Error in Updating Vehicle Tracking Status.";

  // ------------END: Error codes Single appraisal assignment for Dispatch
  // Board

  // ==================================== END DISPATCH BORAD
  // ====================================

  // ----Begin----Q3-2010 - WCAA Custom Settings - Required/Mandatory Fields
  // Constants

  public static final int ERROR_VALIDATING_AA_MANDATORY_FIELDS = 108381;
  public static final int ERROR_DISPATCH_AA_RETRIEVE_ME = 108382;
  // Error codes
  public static final int ERROR_SAVE_AA = 108383;
  public static final int ERROR_CANCEL_AA = 108384;
  public static final int ERROR_DISPATCH_AA = 108385;
  public static final int ERROR_ASSIGNMENT_READYFORDISPATCH = 108386;
  public static final int ERROR_UNCANCELAPPRAISALASSIGNMENT = 108387;
  public static final int ERROR_DISPATCH__SUPPLEMENT_AA = 108388;
  public static final int ERROR_WRITEING_ASSIGNMENTACTIVITYLOG = 108389;
  public static final int ERROR_SAVE_LUNCH_ASSIGNMENT = 108390;
  public static final int ERROR_VALIDATING_CREATE_TASK_DOCUMENT = 108391;
  public static final int ERROR_IN_XSL_TRANSFORMATION = 108392;

  public static final int ERROR_REMOTE_EXCEPTION = 108393;
  public static final int ERROR_WAS_DAO_EXCEPTION = 108394;
  public static final int ERROR_INVALID_WA_CLOB = 108395;
  public static final int ERROR_CREATE_TASK_REQ_SUPP = 108396;
  public static final int ERROR_CANCEL_TASK_REQ_SUPP = 108397;
  public static final int ERROR_REJECT_TASK_REQ_SUPP = 108398;

  public static final String ERROR_IN_XSL_TRANSFORMATION_MSG = "WCAAService: Error in XSLT transformation request.";
  public static final String ERROR_SAVE_LUNCH_ASSIGNMENT_MSG = "WCAAService: Error saving Lunch assignment.";
  public static final String ERROR_VALIDATING_CREATE_TASK_DOCUMENT_MSG = "WCAAService: Error Validating the TaskDocument.";
  public static final String ERROR_VALIDATING_AA_MANDATORY_FIELDS_MSG = "WCAAService: Error validating mandatory fields for READY/NOT READY status on given Appraisal Assignment.";

  public static final String CSET_SETTING_AAS_DISPATCH_WITH_INVALID_ADDRESS = "DISPATCH_WITH_INVALID_ADDRESS";
  public static final String ERROR_DISPATCH_AA_RETRIEVE_ME_MSG = "WCAAService: Error retrieving ME-BMS in Dispatch Appraisal Assignment request.";
  // Error messages.
  public static final String ERROR_SAVE_AA_MSG = "WCAAService: Error in Save Appraisal Assignment request.";
  public static final String ERROR_CANCEL_AA_MSG = "WCAAService: Error in Cancel Appraisal Assignment request.";
  public static final String ERROR_ASSIGNMENT_READYFORDISPATCH_MSG = "WCAAService: Error in AssignmentReadyforDispatch.";
  public static final String ERROR_DISPATCH_AA_MSG = "WCAAService: Error in dispatchAppraisalAssignment.";
  public static final String ERROR_WRITEING_ASSIGNMENTACTIVITYLOG_MSG = "WCAAService: Error in writeAssignmentActivityLog.";
  public static final String ERROR_ERROR_UNCANCELAPPRAISALASSIGNMENT_MSG = "WCAAService: Error in uncancelAppraisalAssignment.";
  public static final String ERROR_DISPATCH__SUPPLEMENT_AA_MSG = "WCAAService: Error in dispatchSupplementAppraisalAssignment.";
  // Q3-2010 - WCAA Custom Settings - Required/Mandatory Fields Constants

  // Not applicable for mandatory fields - misc/extra custom settings
  // public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_1 =
  // "CARRIER_REPLY_EMAIL_ADDRESS";
  // public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_2 =
  // "RESOURCE_RATINGS";
  // public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_3 =
  // "DUPLICATE_ALLOWED";
  // public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_4 =
  // "APPRAISER_CAPACITY";

  public static final String CSET_GROUP_NAME_WCA_REQD_FLDS = "WORKCENTER_ASSIGNMENT_CARRIER_SETTINGS";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_5 = "CONFIG_ADJUSTER";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_6 = "CONFIG_POLICY_NUM";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_7 = "CONFIG_DEDUCTIBLE_AMT";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_8 = "CONFIG_INSD_FIRST_NAME";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_9 = "CONFIG_INSD_ADDRESS1";

  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_10 = "CONFIG_INSD_ADDRESS2";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_11 = "CONFIG_INSD_CITY";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_12 = "CONFIG_INSD_STATE";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_13 = "CONFIG_INSD_ZIP";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_14 = "CONFIG_HOME_PHONE";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_15 = "CONFIG_INSD_WORK_PH";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_16 = "CONFIG_INSD_CELL_PH";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_17 = "CONFIG_INSD_FAX_PH";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_18 = "CONFIG_INSD_EMAIL";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_19 = "CONFIG_CLMT_FIRST_NAME";

  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_20 = "CONFIG_CLMT_ADDRESS1";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_21 = "CONFIG_CLMT_ADDRESS2";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_22 = "CONFIG_CLMT_CITY";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_23 = "CONFIG_CLMT_STATE";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_24 = "CONFIG_CLMT_ZIP";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_25 = "CONFIG_CLMT_HOME_PH";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_26 = "CONFIG_CLMT_WORK_PH";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_27 = "CONFIG_CLMT_CELL_PH";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_28 = "CONFIG_CLMT_FAX";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_29 = "CONFIG_CLMT_EMAIL";

  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_30 = "CONFIG_OWNR_FIRST_NAME";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_31 = "CONFIG_OWNR_LAST_NAME";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_32 = "CONFIG_OWNR_ADDRESS1";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_33 = "CONFIG_OWNR_ADDRESS2";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_34 = "CONFIG_OWNR_CITY";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_35 = "CONFIG_OWNR_STATE";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_36 = "CONFIG_OWNR_ZIP";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_37 = "CONFIG_OWNR_HOME_PH";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_38 = "CONFIG_OWNR_WORK_PH";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_39 = "CONFIG_OWNR_CELL_PH";

  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_40 = "CONFIG_OWNR_FAX";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_41 = "CONFIG_OWNR_EMAIL";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_42 = "CONFIG_VIN_NUM";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_43 = "CONFIG_VEHICLE_TYPE";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_44 = "CONFIG_VEHICLE_YEAR";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_45 = "CONFIG_VEHICLE_MAKE";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_46 = "CONFIG_VEHICLE_MODEL";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_47 = "CONFIG_VEHICLE_SUB_MDL";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_48 = "CONFIG_VEHICLE_BODY_STY";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_49 = "CONFIG_VEHICLE_ENG";

  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_50 = "CONFIG_VEHICLE_TRANS";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_51 = "CONFIG_VEHICLE_DRV_TRAIN";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_52 = "CONFIG_VEHICLE_MIL";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_53 = "CONFIG_EXT_COLOR";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_54 = "CONFIG_VEHICLE_LIC_PLATE";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_55 = "CONFIG_VEHICLE_LIC_PLATE_ST";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_56 = "CONFIG_DRIV_STATUS";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_57 = "CONFIG_PRIMARY_POI";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_58 = "CONFIG_SECONDARY_POI";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_59 = "CONFIG_DT_REPORTED";

  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_60 = "CONFIG_URG";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_61 = "CONFIG_ASSIGN_DUR";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_62 = "CONFIG_INSPEC_ADDRESS1";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_63 = "CONFIG_INSPEC_ADDRESS2";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_64 = "CONFIG_INSPEC_CITY";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_65 = "CONFIG_INSPEC_STATE";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_66 = "CONFIG_INSPEC_ZIP";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_67 = "CONFIG_INSPEC_TYPE";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_68 = "CONFIG_INSPEC_NAME";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_69 = "CONFIG_INSPEC_CONT_FIRST";

  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_70 = "CONFIG_INSPEC_CONT_LAST";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_71 = "CONFIG_INSPEC_CONT_PH";
  public static final String CSET_SETTING_WCA_REQD_FLDS_REFID_72 = "CONFIG_NOTES";

  // ----End----Q3-2010 - WCAA Custom Settings - Required/Mandatory Fields
  // Constants

  // ---------------BEGIN: Event Names defined for Assignment Activity Logging
  // ----------------------
  public static final String DB_DISPATCH_AA_ASSIGNMENT_ACTIVITYLOG = "Dispatched";

  public static final String DB_DISPATCHSUPPLEMENT_AA_ASSIGNMENT_ACTIVITYLOG = "WCAA_Dispatch_Supplement_Assignment";
  public static final String DB_UNCANCEL_AA_ASSIGNMENT_ACTIVITYLOG = "Uncancelled";

  public static final String DB_CANCEL_AA_ASSIGNMENT_ACTIVITYLOG = "Canceled";
  public static final String DB_ASSIGN_AA_ASSIGNMENT_ACTIVITYLOG = "Assigned";
  public static final String DB_REASSIGN_AA_ASSIGNMENT_ACTIVITYLOG = "Reassigned";

  public static final String DB_ONHOLD_AA_ASSIGNMENT_ACTIVITYLOG = "Hold";
  public static final String DB_REMOVE_ONHOLD_AA_ASSIGNMENT_ACTIVITYLOG = "Remove Onhold";

  public static final String DB_UNSCHEDULE_AA_ASSIGNMENT_ACTIVITYLOG = "Unscheduled";

  public static final String UPDATE_DISPOSITION_AA_ASSIGNMENT_ACTIVITYLOG = "WCAA_Update_Disposition_Assignment";

  public static final String CANCEL_AA_ASSIGNMENT_ACTIVITYLOG = "WCAA_Cancel_Assignment";
  public static final String UNCANCEL_AA_ASSIGNMENT_ACTIVITYLOG = "WCAA_Uncancel_Assignment";
  public static final String DISPATCH_AA_ASSIGNMENT_ACTIVITYLOG = "WCAA_Dispatch_Assignment";
  public static final String DISPATCHSUPPLEMENT_AA_ASSIGNMENT_ACTIVITYLOG = "WCAA_Dispatch_Supplement_Assignment";
  public static final String ASSIGN_AA_ASSIGNMENT_ACTIVITYLOG = "WCAA_Assign_Assignment";
  public static final String REASSIGN_AA_ASSIGNMENT_ACTIVITYLOG = "WCAA_Reassign_Assignment";
  public static final String CREATE_AA_ASSIGNMENT_ACTIVITYLOG = "Created";
  public static final String CREATE_SUPPLEMENT_AA_ASSIGNMENT_ACTIVITYLOG = "Supplement Created";

  public static final String RECEIVED_AA_ASSIGNMENT_ACTIVITYLOG = "Received";
  public static final String REJECTED_AA_ASSIGNMENT_ACTIVITYLOG = "Rejected";
  public static final String CLOSED_AA_ASSIGNMENT_ACTIVITYLOG = "Closed";

  // ---------------END: Event Names defined for Assignment Activity Logging
  // ----------------------

  public static final String DRIVE_IN = "DI";

  public static final String APPRAISER_AFFILIATION_INSURER = "IN";
  public static final String APPRAISER_AFFILIATION_INDP_APPRAISER = "IP";
  public static final String APPRAISER_AFFILIATION_REPAIR_FACILITY = "69";

  public static final String DISPOSITION_RESCHEDULE = "RESCHEDULE";
  public static final String DISPOSITION_INCOMPLETE = "INCOMPLETE";
  public static final String STATUS_NOTES_DISPOSITION_IN_PROGRESS = "IN PROGRESS";
  public static final String STATUS_NOTES_SEPERATOR = " : ";

  public static final int ERROR_ASSIGN_TO_DISPATCH_CENTER_AA = 108399;
  public static final String ERROR_ASSIGN_TO_DISPATCH_CENTER_AA_MSG = "WCAA: Error assigning assignments to dispatch center.";

  // ----------------------- Added for reschedule or incomplete assignment status updates
  public static final int ERROR_RESCHEDULE_OR_INCOMPLETE_AA_SAVEWA = 108357;
  public static final String ERROR_RESCHEDULE_OR_INCOMPLETE_AA_SAVEWA_MSG = "WCAA: Error saving a Reschedule or Incomplete disposition status.";
  public static final int ERROR_RESCHEDULE_OR_INCOMPLETE_AA = 108379;
  public static final String ERROR_RESCHEDULE_OR_INCOMPLETE_AA_MSG = "WCAAService : Error in Reschedule or Incomplete Appraisal Assignment request.";

  // ------------------------ Added for update appraisal assignment address
  public static final int ERROR_UPDATING_APPRAISAL_ASSIGNMENT_ADDRESS = 108400;
  public static final String ERROR_UPDATING_APPRAISAL_ASSIGNMENT_ADDRESS_MSG = "WCAAService : Error updating appraisal assignments address.";
  public static final int ERROR_MISSING_OR_NULL_TASKID = 108401;
  public static final String ERROR_MISSING_OR_NULL_TASKID_MSG = "Received NULL/0 work assignment task Id from input.";
  public static final int ERROR_NULL_OR_LESS_THEN_0_TCN = 108402;
  public static final String ERROR_NULL_OR_LESS_THEN_0_TCN_MSG = "Received NULL/LESS THAN '0' TCN from input.";

  public static final int ERROR_PARALLEL_PROCESSING_DEADLETTER_QUEUE = 108403;
  public static final int ERROR_CORRELATED_INFO = 108404;
  public static final int ERROR_UNEXPECTED_EXCEPTION = 108405;
  public static final int ERROR_PARALLEL_PROCESSING_ERROR = 108406;
  public static final int ERROR_PARALLEL_ASSIGNSCHEDULE_PROCESSING_ERROR = 108407;
  public static final int ERROR_PARALLEL_RESPONSE_RETRIEVE_ERROR = 108408;
  public static final int ERROR_PARALLEL_REQUEST_SUBMIT_ERROR = 108409;

  public static final String MOI_DRIVE_IN = "SCDI";
  public static final String MOI_DROP_OFF = "SCDO";
  public static final String MOI_TOW_IN = "SCTW";

  public static final long MOI_ORG_ID_BLANK = -1;
  public static final long MOI_ORG_ID_UNSET_IN_CLOB = -2;

  // Status
  public static final String WA_STATUS_CLOSED = "CLOSED";
  public static final String WA_STATUS_CANCELLED = "CANCELLED";
  public static final int SAVE_AND_SEND_EVENT = 108301;
  
  public static final int ERROR_GETTING_CULTURE = 108410;
  public static final String ERROR_GETTING_CULTURE_MSG = "Error Getting Culture from DB.";
  
  
  public static final int ERROR_GETTING_CURRENCY = 108411;
  public static final String ERROR_GETTING_CURRENCY_MSG = "Error Getting Currency from DB.";
  
  public static final String SUPPLEMENT_ASSIGNMENT_DOC_VERSION_CODE = "SV";
  public static final String SUPPLEMENT_ASSIGNMENT_BMS_VERSION = "2.10.0";
  public static final String SUPPLEMENT_ASSIGNMENT_DOC_TYPE = "A";
  public static final String PRIMARY_CONTRACT_TYPE_CLAIMNT = "claimant";
  public static final String PRIMARY_CONTRACT_TYPE_INSURED = "insured";

    public static final String SUPPLEMENT_REQUEST_DOC_STORE_ID = "SupplementRequestDocStoreId";
    public static final String SUP_AA_REQ_EMAIL_XML_ATTACHMENT_TYPE = "SUP_AA_REQ_EMAIL_XML";
    
   
    public static final String UNDER_SCORE = "_";
    public static final String TXT_FILE_EXTN = ".txt";
    public static final String NULL_STRING = null;
    public static final String HYPHEN_STRING = "-";
    
    public static final String SUPPLEMENT_REQUEST_DOC_BUILDR_BEAN = "SupplementRequestDocBuildr";
    public static final String SUPPLEMENT_NOTIFICATION_BEAN = "SupplementNotification";
    public static final String USER_INFO_UTILS_BEAN = "UserInfoUtils";
    public static final String CULTURE_DAO_BEAN = "CultureDAO";
    public static final String APPRAISAL_ASSIGNMENT_UTILS_BEAN = "AppraisalAssignmentUtils";
    public static final String AAS_COMMON_UTILS_BEAN = "AASCommonUtils";
    public static final String AAS_EMAIL_UTILS_BEAN = "AASEmailProxy";
    public static final String DAYTONA_SHOP_IDENTIFIER_BEAN = "ShopIdentifierProxy";

    public static final String CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME = "CARRIER_GLOBAL_SETTINGS";
    public static final String SETTING_NOTIFICATION_XSLT_SETTING_NAME = "NOTIFICATION_XSLT_CLASS";
    public static final String STATIC_IMAGE_BASE_URL = "/AppraisalAssignment/StaticImageBaseUrl";
	public static final int ERROR_GETTING_RESOURCE_BUNDLE =108412;
	public static final String ERROR_GETTING_RESOURCE_BUNDLE_MSG = "Error Getting Resource File.";
	public static final String APPRAISAL_ASSIGN_RES = "AppraisalAssignment";
    public static final String MOI_TYPE="MoiType";
    public static final int ERROR_PROCESSING_FAILURE_RESPONSE_MESSAGE = 108413;
    public static final int ERROR_PROCESSING_FAILURE_RESPONSE_DTO = 108414;
    public static final int APPLOG_PROCESSING_FAILURE_RESPONSE_EVENT = 108334;
    public static final String ERROR_EMPTY_DOCS = "Error preparing AssignmentFailureResponseDTO-UserInfoDocument/AssignmentInfoDocument is null";
    public static final String ERROR_PREPARE_RESP = "Error Processing FailureResponse for Assignment Failure response event publishing";
    public static final String KEY_SOURCE_TRANSACTION_TYPE = "SOURCE_TRANSACTION_TYPE";
    public static final String KEY_FAILURE_MESSAGE = "FAILURE_MESSAGE";
    public static final String KEY_SOURCE_FAILURE_ID = "FAILURE_TYPE";
    public static final String KEY_SOURCE_TRANSACTION_ID = "SOURCE_TRANSACTION_ID";
    public static final String KEY_EXTERNAL_TASK_ID = "EXTERNAL_TASK_ID";
    public static final String KEY_SUBMITTER_ID = "SUBMITTER_ID";
    public static final String VALIDATION_RESULTS_DOC = "Validations Results";
    public static final String WARNING_MSG = "Warning";
    public static final String ERROR_MSG = "Error";
    public static final String MITCHELL_ENV_NAME_ASSIGNMENT_TYPE ="APPRAISAL_ASSIGNMENT";
    public static final String MITCHELL_ENV_NAME_CLAIM_NUMBER ="ClaimNumber";
    public static final String MITCHELL_ENV_NAME_PARTNER_KEY ="PartnerKey";
    public static final String MITCHELL_ENV_NAME_CORRELATION_ID ="Correlation_Id";
    public static final String MITCHELL_ENV_NAME_SUBMITTER_ID ="SubmitterId";
    
    
}
