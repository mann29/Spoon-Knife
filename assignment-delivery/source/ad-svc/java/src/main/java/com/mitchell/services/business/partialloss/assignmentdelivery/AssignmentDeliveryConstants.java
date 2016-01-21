package com.mitchell.services.business.partialloss.assignmentdelivery;

public interface AssignmentDeliveryConstants {
    
	public String APPLICATION_NAME = "PARTIALLOSS";

	public String MODULE_NAME = "ASSIGNMENT_DELIVERY_SERVICE";

	public String CUSTOM_SETTING_GROUP_NAME = "PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_SETTINGS";
    
    public String CUSTOM_SETTING_NAME_DESTINATION = "PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_DESTINATION";

    public String CUSTOM_SETTING_NAME_ECLAIM_TEMPLATE = "PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_ECLAIM_TEMPLATE";

    public String CUSTOM_SETTING_NAME_ECLAIM_DISPATCH_RP_FLAG = "PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_ECLAIM_DISPATCH_RPT_FLAG";

    public String CUSTOM_SETTING_NAME_ECLAIM_DISPATCH_RP = "PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_ECLAIM_DISPATCH_RPT";

    public String CUSTOM_SETTING_NAME_RC_DISPATCH_RP = "PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_RC_DISPATCH_RPT";

    public String CUSTOM_SETTING_NAME_WCAP_DISPATCH_RP = "PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_WCAP_DISPATCH_RPT";

	public String DEFAULT_STAGING_SUBDIR = "/assignmentdelivery/mcf";

	public String DEFAULT_TEMP_DIR = "/tmp";
	
	public static final String DEFAULT_ENCODING= "UTF-8"; //"ISO-8859-1";

    // EClaim Manager Appl Access Code.
    public static final String APPL_ACCESS_CODE_ECLAIM_MANAGER = "ECMGR";
    
    // System configuration items.
    public String SYS_CONFIG_BMS_TO_MIE_TEMP_DIR = "/AssignmentDelivery/BmsToMie/TempDir";

    public String SYS_CONFIG_ARC5_COPYDIR = "/AssignmentDelivery/Arc5/CopyDir";
    
    public String SYS_CONFIG_HOSTNAME = "/AssignmentDelivery/Hostname";
    
    public String SYS_CONFIG_BOOLEAN_CLEANUP = "/AssignmentDelivery/Cleanup";
    
    /**
     * Added custom settings of Eclaim Template File Name for staff and non staff user for
     * SIP Assignment Enhancement 0000252: Augment SIP Assignment Delivery Custom Setting for eClaim Template 
     * By:- Vandana Gautam
     * Modified Date :-05/28/2009
     */
    
    public String CUSTOM_SETTING_NAME_ECLAIM_TEMPLATE_FILE_FOR_STAFF = "PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_ECLAIM_TEMPLATE_FILE_FOR_STAFF";
    
    public String CUSTOM_SETTING_NAME_ECLAIM_TEMPLATE_FILE_FOR_NON_STAFF = "PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_ECLAIM_TEMPLATE_FILE_FOR_NON_STAFF";

    // Custom Setting for Primary Notification Email Template - Non-Staff Supplement Assignments
    public String CUSTOM_SETTING_NAME_ECLAIM_PRIM_NOTIF_TEMPLATE = "PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_PRIMARY_NOTIFICATION_TEMPLATE";

	public String SUBJECT = "subject";

    // MitchellEnvelope Metadata constants for CIECA BMS as Envelope Body
    public static final String ME_METADATA_CIECA_IDENTIFIER = "CIECABMSAssignmentAddRq";
    public static final String ME_METADATA_CIECA_IDENTIFIER_APD = "AssignmentBMS";
    public static final String ME_METADATA_CIECA_MEDOCTYPE = "CIECA_BMS_ASG_XML";

    // MitchellEnvelope Metadata constants for AdditionalAppraisalAssignmentInfo as Envelope Body
	public static final String ME_METADATA_AAAINFO_IDENTIFIER = "AdditionalAppraisalAssignmentInfo";
    public static final String ME_AAINFO_XML_BEAN_CLASS_TYPE = "com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument";
    public static final String ME_AAINFO_VEHICLE_DETAILS_INFO_IDENTIFIER = "VehicleDetailsInfo";
    
    // MitchellEnvelope Metadata constants for AdditionalTaskConstraints as Envelope Body
    public static final String ME_ADDITIONAL_TASK_CONST_ID = "AdditionalTaskConstraints";

    public static final String ECLAIM_ALERT_ORIGIN = "Assignment Delivery Service"; //   "StateFarm MCF Estimate Workflow";    

    // Constants for Name/Value pairs of MitchellEnvelope
    public static final String MITCHELL_ENV_NAME_COMPANY_CODE = "MitchellCompanyCode";
    public static final String MITCHELL_ENV_NAME_INBOUND_FILE_NAME = "InboundFileName";
    public static final String MITCHELL_ENV_NAME_DATE_FILE_RECEVIED = "DateFileReceived";
    public static final String MITCHELL_ENV_NAME_MITCHELL_WORKITEMID = "MitchellWorkItemId";
    public static final String MITCHELL_ENV_NAME_SIMPLE_MSG = "SimpleMessage";

    public static final String CONV_SUPP_TO_ORIG_NOTICE_FILENAME = "Convert_Supp_To_Original_Notice.txt"; 
    
    // Supplement Request Notification constants
    public static final String SUPPLEMENT_REQUEST_PREFIX = "SupplementRequest_";
    public static final String FILE_EXTENSION_TXT = ".txt";
     
    // Email Type constants
	public static final String STAFF_EMAIL_TYPE = "STAFF_EMAIL_TYPE";
	public static final String SHOP_PREMIUM_EMAIL_TYPE = "SHOP_PREMIUM_EMAIL_TYPE";
	public static final String IA_PREMIUM_EMAIL_TYPE = "IA_PREMIUM_EMAIL_TYPE";
	public static final String SHOP_BASIC_EMAIL_TYPE = "SHOP_BASIC_EMAIL_TYPE";
	public static final String IA_BASIC_EMAIL_TYPE = "IA_BASIC_EMAIL_TYPE";
	public static final String ASSIGNMENT_TYPE_ORIGINAL = "ORIGINAL_ESTIMATE"; 
	
	public static final String MIE_DATASET_CRC_VALUE = "0000000000000";
	public static final String MIE_MEDS_OPERATOR_ID = "ADS_SYS";
	
	public static final String DEFAULT_CULTURE_CODE = "en-US";
	
	public static final String POI = "poi_";
	public static final String LOSS_TYPE = "LossType_";
	
	public static final String DATE_PREFER = "0001-01-01";
	
	public static final String CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME = "CARRIER_GLOBAL_SETTINGS";
    public static final String SETTING_NOTIFICATION_XSLT_SETTING_NAME = "NOTIFICATION_XSLT_CLASS";
    public static final String LOCALE_SEPARATOR = "-";
    public static final String COMPANY_TYPE = "COMPANY";
    public static final String USER_TYPE = "USER";
    public static final String SET_FILE_XSLT_PATH = "/AssignmentDelivery/AssignmentEmail/XSLTPath";
    public static final String ASSIGNMENT_EMAIL_WITH_UNDERSCORE = "/AssignmentEmail_";
    public static final String ASSIGNMENT_EMAIL_XSLT = "/AssignmentEmail.xslt";
    public static final String DOT_XSLT = ".xslt";
    
    public static final String CUSTOM_SETTING_CLAIM_SETTINGS_GRP = "CLAIMSERVICES_CLAIM_SETTINGS";
    public static final String SETTING_SUFFIX_LABEL = "CLAIMSERVICES_CLAIM_SUFFIX_ID_FIELD_LABEL";
    public static final String SETTING_CLAIM_LABEL = "CLAIMSERVICES_CLAIM_CLAIM_OFFICE_FIELD_LABEL";
    public static final String SETTING_CLAIM_MASK = "CLAIMSERVICES_CLAIM_CLAIMNUMBERMASK";
    
    public static final String ASG_TYPE_SV = "SV";
    public static final String ASG_TYPE_EM = "EM";
    public static final String ASG_TYPE_SUPPLEMENT = "Supplement";
    public static final String ASG_TYPE_ORIGINAL = "Original";
    
    public static final String STATIC_IMAGE_BASE_URL = "/AssignmentDelivery/StaticImageBaseUrl";
        
    public static final String ASG_SUBTYPE = "AsgSubType_";
    public static final String BLANK_SPACE = " ";
    public static final String HIPHEN = "-";
    public static final String UNDERSCORE = "_";
    public static final String SETTING_DATACENTER = "DATA_CENTER";
    public static final String DATACENTER_EU = "EU";
}
