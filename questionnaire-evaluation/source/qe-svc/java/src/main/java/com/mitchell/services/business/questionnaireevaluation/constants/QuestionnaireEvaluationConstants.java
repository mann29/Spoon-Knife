package com.mitchell.services.business.questionnaireevaluation.constants;

/**
 * Class contains all the constants to be used in Evaluation Service.
 */

public class QuestionnaireEvaluationConstants{

  /**
   * Default version for xml file..
   */
  public static final double DEFAULT_VERSION = 1.0;

 
  /**
   * Save Evaluation Success Log Description..
   */
  public static final String LOSS_EVALUATION_SUCCESS_LOG = "Loss Evaluation Saved Successfully";

  /**
   * Save Evaluation Success update Log Description..
   */
  public static final String LOSS_EVALUATION_SUCCESS_UPDATE_LOG = "Loss Evaluation Updated Successfully";

  /**
   * Save Evaluation Success link Log Description..
   */
  public static final String LOSS_EVALUATION_SUCCESS_LINK_LOG = "Loss Evaluation Linked Claim Successfully";

  /**
   * Root Directory..
   */
  public static final String SYSTEM_CONFIG_SETTINGS_ROOT = "/QuestionnaireEvaluationService";

  /**
   * Loss Evaluation Directory Path..
   */
  public static final String SYSTEM_CONFIG_LE_DIRECTORY_PATH = SYSTEM_CONFIG_SETTINGS_ROOT
      + "/FileConfiguration/EvaluationTempDirectory";

  /**
   * Loss Evaluation File Name..
   */
  public static final String SYSTEM_CONFIG_LE_FILE_NAME = "EVL";

  public static final String COPY_DOC_STORE_SET_VALUE = SYSTEM_CONFIG_SETTINGS_ROOT
      + "/AllowedCopyToNAS";

  /**
   * Underscore..
   */
  public static final String UNDERSCORE = "_";

  /**
   * Hyphen..
   */
  public static final String HYPHEN = "-";

  /**
   * xml extn..
   */
  public static final String XML_EXTN = ".xml";

  /**
   * filePathToDelete..
   */
  public static final String SYSTEM_CONFIG_FILE_PATH_TO_DELETE = SYSTEM_CONFIG_SETTINGS_ROOT
      + "/filePathToDelete";

  /**
   * sourceFilePath..
   */
  public static final String SYSTEM_CONFIG_FILE_PATH = SYSTEM_CONFIG_SETTINGS_ROOT
      + "/sourceFilePath";

  /**
   * destFilePath..
   */
  public static final String SYSTEM_CONFIG_TARGET_FILE_PATH = SYSTEM_CONFIG_SETTINGS_ROOT
      + "/FileConfiguration/NASLocation";

  /**
   * AllowedCompanyCodes..
   */
  public static final String SYSTEM_CONFIG_COMPANY_CODES = SYSTEM_CONFIG_SETTINGS_ROOT
      + "/AllowedCompanyCodes";

  /**
   * AllowedCompanyCodes..
   */
  public static final String SYSTEM_CONFIG_EVALUATION_TYPE = SYSTEM_CONFIG_SETTINGS_ROOT
      + "/AllowedEvaluationType";

  /**
   * Link contigency evaluation to claim event Id
   */
  public static final String SYSTEM_CONFIG_CONTINGENCY_EVENT_ID = SYSTEM_CONFIG_SETTINGS_ROOT
      + "/LinkClaimEvalContingencyEventId";

  
  
  /**
   * Questionnaire Reporting Listener Id 
   */
  public static final String SYSTEM_CONFIG_QUESTIONNAIRE_REPORTING_LISTENER_ID = SYSTEM_CONFIG_SETTINGS_ROOT
		  + "/QuestionnaireReportingListenerId";
  
  
  public static final String SYSTEM_CONFIG_QUSTNNR_ID = SYSTEM_CONFIG_SETTINGS_ROOT
  + "/QuestionnaireId";
  
  
  /**
   * Is Questionnaire Reporting Allowed..
   * 
   * 
   */
  public static final String IS_QUESTIONNAIRE_REPORTING_ALLOWED = SYSTEM_CONFIG_SETTINGS_ROOT
		  + "/IsReportingAllowed";

  /**
   * Appplication name..
   */
  public static final String APPLICATION_NAME = "WORKCENTER";

  /**
   * Module name..
   */
  public static final String MODULE_NAME = "QUESTIONNAIRE_EVALUATION_SERVICE";

  /**
   * Success Save Appplication Activity Logs Event Status Code ..
   */
  public static final String SAVE_EVALUATION_SUCCESS = "157601";

  /**
   * Success Save Appplication Activity Logs Event Status Code ..
   */
  public static final String UPDATE_EVALUATION_SUCCESS = "157602";

  /**
   * Appplication Activity Logs Event Status Codes ..
   */

  /**
   * Success Delete Appplication Activity Logs Event Status Code ..
   */
  public static final String DELETE_EVALUATION_SUCCESS = "157603";

  /**
   * Success Delete Appplication Activity Logs Event Status Code ..
   */
  public static final String LINKING_CLAIM_SUCCESS = "157604";

  /**
   * Evaluation Claim linking Logs Event Status Code ..
   */
  public static final String LINKING_CLAIM_STATUS = "157605";

  /**
   * Appplication Response Map Keys..
   */
  public static final String RESPONSE_KEY_DOCUMENTID = "DocumentId";

  /**
   * Version..
   */
  public static final String RESPONSE_KEY_VERSION = "Version";

  /**
   * EvaluationXMLReturned..
   */
  public static final String RESPONSE_XML = "EvaluationXMLReturned";

  /**
   * ClaimId..
   */
  public static final String CLAIM_ID = "ClaimId";

  /**
   * EvaluationId..
   */
  public static final String EVALUATION_ID = "EvaluationId";

  /**
   * SuffixId..
   */
  public static final String SUFFIX_ID = "SuffixId";

  /**
   * WorkItemId..
   */
  public static final String WORKITEM_ID = "WorkItemId";

  /**
   * UserId..
   */
  public static final String USER_ID = "UserId";

  /**
   * ClaimNumber..
   */
  public static final String CLAIM_NUMBER = "ClaimNumber";

  /**
   * DocumentId..
   */
  public static final String DOCUMENT_ID = "DocumentId";

  /**
   * ExposureId..
   */
  public static final String EXPOSURE_ID = "ExposureId";

  /**
   * Pipe operator symbol..
   */
  public static final String PIPE_OPERATOR = "|";

  /**
   * Document Id null message
   */
  public static final String DOCUMENT_ID_NULL_MESSAGE = " DocumentID returned by EPS is null ";

  // ********************************************************************************
  // Add all the Mitchell Error Types Here ..

  public static final int MIN_QEVAL_ERROR_CODE = 157601;
  public static final int MAX_QEVAL_ERROR_CODE = 157699;

  /**
   * Error parsing EvaluationDetails document in Save Evaluation request.
   */
  public static final int ERROR_PARSING_DOCUMENT = 157601;

  /**
   * Invalid evaluationID..
   */
  public static final int INVALID_EVALUATION_ID = 157602;

  /**
   * Invalid SuffixID..
   */
  public static final int INVALID_SUFFIX_ID = 157603;

  /**
   * Invalid ClaimID..
   */
  public static final int INVALID_CLAIM_ID = 157604;

  /**
   * Invalid evaluationType..
   */
  public static final int INVALID_EVALUATION_TYPE = 157605;

  /**
   * Error saving evaluationDetails..
   */
  public static final int ERROR_SAVING_DETAILS = 157606;

  /**
   * Error deleting evaluation details..
   */
  public static final int ERROR_DELETING_DETAILS = 157608;

  /**
   * Generic exception..
   */
  public static final int GENERIC_ERROR = 157609;

  /**
   * Exeption Getting EJB ..
   */
  public static final int ERROR_CLIENT_EJB = 157610;

  /**
   * Error Linking Claim to Evaluation..
   */
  public static final int ERROR_LINKING_CLAIM = 157611;

  /**
   * Copying file to NAS exception..
   */
  public static final int ERROR_COPYING_TO_NAS = 157612;

  /**
   * Invalid evaluation version ..
   */
  public static final int ERROR_INVALID_VERSION = 157613;

  /**
   * Error updating evaluationDetails ..
   */
  public static final int ERROR_UPDATING_DETAILS = 157614;

  /**
   * Error documentId.
   */
  public static final int INVALID_DOCUMENT_ID = 157615;

  /**
   * Error calling EPS..
   */
  public static final int ERROR_CALLING_EPS = 157616;

  /**
   * Error calling TFS..
   */
  public static final int ERROR_CALLING_TFS = 157617;

  /**
   * Error calling Claim Service..
   */
  public static final int ERROR_CALLING_CLAIM_SERVICE = 157607;

  /**
   * Error publishing to message bus
   */
  public static final String ERROR_PUBLISHING_TO_MESSAGE_BUS_MSG = "Error occurred while publishing message to MessageBus.";
  public static final int ERROR_PUBLISHING_TO_MESSAGE_BUS = 157618;
  /*
   * contingency evalution flag constant
   */
  public static final String CONTINGENCY_EVALUATION_FLAG = "ContingencyEvaluationFlag";
  /*
   * Error publishing edog event
   */
  public static final int ERROR_PUBLISH_EDOG_EVENT = 157619;

  public static final String ERROR_PUBLISH_EDOG_EVENT_MSG = "Error occurred while publishing EDOG event";

  /* Business service name */
  public static final String BUSINESS_SERVICE_NAME = "CLAIM_SERVICE";

  public static final String LINK_STATUS = "LinkStatus";

  public static final int ERROR_SQL = 157620;

  public static final String ERROR_SQL_MSG = "SQL Exception occurred";

  public static final int ERROR_ATTACHING_DOCID_TO_QID_CLAIM = 157621;

  public static final String ERROR_ATTACHING_DOCID_TO_QID_CLAIM_MSG = "Error attaching DocId to Claim Questionnaire";

  public static final int ERROR_ASSOCIATING_QTNR_TO_CLAIMSUFFIX = 157622;

  public static final String ERROR_ASSOCIATING_QTNR_TO_CLAIMSUFFIX_MSG = "Error Associating Questionnaire To Claim Suffix";

  public static final int ERROR_UNKNOWN = 157623;

  public static final String ERROR_UNKNOWN_MSG = "Unexpected Error";

  public static final String DATASOURCE = SYSTEM_CONFIG_SETTINGS_ROOT
      + "/DB/DataSourceName";

  public static final String JNDIFACTORY = SYSTEM_CONFIG_SETTINGS_ROOT
      + "/QEJavaClient/JndiFactory";

  public static final String PROVIDERURL = SYSTEM_CONFIG_SETTINGS_ROOT
      + "/QEJavaClient/ProviderUrl";

  public static final String CATEGORY_LOSS_EVALUATION = "LossEvaluation";

  public static final String CATEGORY_ER = "ER";

  public static final String CATEGORY_PR = "PR";

  public static final String CATEGORY_FR = "FR";

  public static final String CATEGORY_TLR = "TLR";

  public static final String ERROR_OCCURED_WHILE_SET_DOCID_UPDATE = "Error occurred while updating questionnaire Eval DocumentId for a claim associated with a set";

  // change by Nazir
  public static final String CSET_CLIAM_GROUP_NAME = "CLAIMSERVICES_CLAIM_SETTINGS";
  public static final String CSET_CLAIM_SETTING_CLAIMNUMBERMASK = "CLAIMSERVICES_CLAIM_CLAIMNUMBERMASK";
  public static final String QE_DOCUMENT_CIECA_TYPE = "CIECABMS";
  public static final String QE_DOCUMENT_SUFFIXRQRS_TYPE = "MITCHELLSIUFFIXRQRS";
  public static final String QE_CORRELATION_ID = "CorrelationId";

  public static final int QE_ASSIGNMENT_ADDRQRS_PARSE_ERROR = 157624;
  public static final int QE_CIECA_PARSE_ERROR = 157625;
  public static final int QE_CIECA_VALIDATION_ERROR = 157626;
  public static final int QE_SUFFIXRQRSDOC_VALIDATION_ERROR = 157627;
  public static final int QE_CALL_TRANS_ENGINE_SERVICE_ERROR = 157628;
  public static final int QE_CALL_SERVICE_UNKNOWN_ERROR = 157629;
  public static final int QE_ERROR_SAVE_EVALUATION_LINKQE_CLAIM = 157630;
  public static final int QE_CALL_USERINFO_SERVICE_ERROR = 157631;
  public static final int QE_INPUT_EVALUATIONRQ_ISNULL = 157632;
  public static final int QE_SAVECLAIM_NULL_EXCEPTION = 157633;

  public static final String CSET_CARRIER_GROUP_SETTING = "CARRIER_GLOBAL_SETTINGS";
  public static final String CSET_DEFAULT_ADJUSTER_ID = "DEFAULT_ADJUSTER_ID";
  public static final int INVALID_OR_MISSING_ADJUSTER_ERROR = 157634;
  //Questionnaire-Reporting Error Constant
  public static final int ERROR_POST_EVAL_IN_REPORTING = 157645;
  
  // FNOL-QUESTIONNAIR Constants
  public static final int ERROR_SAVING_QNTNRE_EVAL = 157646;
  public static final int IO_ERROR_IN_UPDT_EVAL = 157647;
  public static final int XML_EXCPTN_IN_UPDT_EVAL = 157648;
  public static final int ERROR_GETTING_QUESTIONNAIRE_FROM_DB = 157649;
  public static final int INVALID_INPUT = 157650;
  public static final int INVALID_COMPANY_CODE = 157651;
  
  public static final String ERROR_COMMON_DAO_MSG = "Mitchell Common Dao Exception occured";
  public static final String ERROR_GETTING_QUESTIONNAIRE_MSG = "Error occured while getting the Questionnaire";
  public static final String ERROR_GETTING_NXT_QUSTNNRE = "Error occured while getting next questionnaire";
  public static final String INVALID_INPUT_MSG = "input is not correct. Either input QuestionnaireRqRsDTO or Context DTO is null";
  public static final String CSET_SYSTEM_SETTING_USER_ID="SYSTEM_PROCESSING_USER_ID";
  public static final String INVALID_COMPANY_CODE_MSG="company code is null or invalid";
  
  public static final String ERROR_CREATING_CACHE_KEY_FOR_QUESTIONNAIRE_DESC = "Error occured while creating cache key for questionnaire";
  
  public static final int QE_FNOL_CONTEXT_ID_ISNULL = 175400;  
  public static final String ERROR_NULL_CONTEXT_ID = "Exception thrown due to null context ID. ";
  public static final String EVALUATION_TYPE  = "LOSSEVALUATION";
  public static final String SCORING_TYPE_PERCENTAGE = "PERCENTAGE";
  public static final String SCORING_TYPE_POINT = "POINT";
  public static final String EVALUATION_VERSION = "1.0";
  public static final String GROUP_NAME = "WORKCENTER_ASSIGNMENT_CARRIER_SETTINGS";
  public static final String SETTING_NAME = "LOSS_EVAL_QUESTIONNAIRE_ID";
  public static final String FORMATTED_TEXT = "false";

  public static final int ERROR_CREATING_CACHE_KEY_FOR_QUESTIONNAIRE = 157652;
  
  
  public static final int ERROR_GETTING_FIRST_QUSTN = 157653;
  public static final int ERROR_GETTING_NEXT_SAVED_QUSTN = 157654;
  public static final String ERROR_GETTING_FIRST_QUSTN_MSG = "Exception thrown while getting the first question";
  public static final String ERROR_GETTING_NEXT_SAVED_QUSTN_MSG = "Exception thrown while getting the next question of the partially saved evaluation";
  public static final int ILLEGAL_ACCESS_EXP = 157655;
  public static final String ILLEGAL_ACCESS_EXP_MSG = "Illegal access exception occured while initializing the resources";
  public static final int NUMBER_FORMATE_EXP = 157656;
  public static final String NUMBER_FORMATE_EXCEPTION = "Questionnair ID is invalid. Not a Number";
  public static final int INVALID_EVALUATION_DOC = 157657;
  public static final String INVALID_EVAL_DOC_MSG = "Evaluation doc is not present or invalid";
  public static final String ANSWR_CONTL_TYPE = "RANGE_VALUE";
  public static final String IS_LEAF = "T";
  
 
}
