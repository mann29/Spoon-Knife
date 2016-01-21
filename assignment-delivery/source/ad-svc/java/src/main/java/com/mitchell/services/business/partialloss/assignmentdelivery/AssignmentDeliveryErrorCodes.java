package com.mitchell.services.business.partialloss.assignmentdelivery;

public class AssignmentDeliveryErrorCodes {
    
	// non specific error encountered
	public static final int GENERAL_ERROR = 106800;

	// custom setting error
	public static final int CUSTOM_SETTING_ERROR = 106801;

	// handler type not found
	public static final int HANDLER_CLASS_NOT_FOUND = 106802;

	// can not instantiate handler class
	public static final int HANDLER_CLASS_INSTANTIATION_ERROR = 106803;

	// does not have access permissions for hanlder class
	public static final int HANDLER_CLASS_ILLEGAL_ACCESS_ERROR = 106804;

	// file archieve general error
	public static final int MCF_FAR_ERROR = 106805;

	// app log error
	public static final int APP_LOG_ERROR = 106806;

	// bms to mie conversion related error
	public static final int BMS_MIE_ERROR = 106807;

	// userinfo is null or empty
	public static final int USERINFO_INVALID = 106808;

	// client encountered JNDI look up error
	public static final int JNDI_LOOKUP_ERROR = 106809;

	// client encountered EJB create error
	public static final int EJB_CREATE_ERROR = 106810;

	// client encountered remote invocation error
	public static final int EJB_REMOTE_ERROR = 106811;

	// Template File not found
	public static final int TEMPLATE_FILE_NOT_FOUND = 106812;

	// Property Value not found
	public static final int PROPERTY_VALUE_NOT_FOUND = 106813;
    
    // Invalid Mitchell Envelope Document
    public static final int INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR = 106814;  

    // Invalid ARC5 delivery directory.
    public static final int INVALID_ARC5_DELIVERY_DIRECTORY = 106815;
    
    // No UserInfo ECM Appl Access Code.
    public static final int NO_USERINFO_ECM_APPL_ACCESS_CODE = 106816;  
    public static final String NO_USERINFO_ECM_APPL_ACCESS_CODE_MSG = "No UserInfo ECM Appl Access Code";

    public static final int ERROR_GETTING_CIECA_BMS_DOC_FROM_MITCHELLENVELOPE = 106817;

    public static final int ERROR_RENAMING_MIE_FILE_FOR_ARU_PICKUP = 106818;

    public static final int ERROR_UNMARSHALING_MIE_FILE = 106819;

    public static final int MISSING_SECOND_ESTIMATE_IN_MIE_FILE = 106820;

    public static final int ERROR_CREATING_ARC5_MIE_FILE = 106821;

    public static final int ERROR_COPYING_ARC5_MIE_FILE = 106822;
    
    // New for Jetta/SIP 3.5 --
    //
    public static final int INVALID_AAAINFO_DOC_IN_MITCHELL_ENVELOPE_ERROR = 106823;  

    public static final int INCOMPLETE_CONTEXT_INFO_FOR_ECM_ALERT_ERROR = 106824;  

    public static final int ERROR_DOCUMENT_RETRIEVAL_FAILURE_FROM_DOCSTORE = 106825;  

    public static final int EMAIL_NOTIFICATION_ERROR = 106826; 
    
    public static final int FILE_ATTACHMENT_ERROR = 106827; 
    
    public static final int ERROR_RETRIEVING_SUPPLEMENT_REQUEST = 106828; 

    public static final int FAX_NOTIFICATION_ERROR = 106829;

    // For Supplement Capture Project (Q1-2010) 
    //
    public static final int ERROR_RETRIEVING_HTML_SUPPLEMENT_REQUEST = 106830;
    
    // For Assignment Email Delivery, 106831 - 106840 defined in AbstractMsgBusDelHndlr.
    public static final int ERROR_START =106800;
    public static final int ERROR_END = 106899;
    public static final int ERROR_XSLT = 106841;
    
    public static final int ERROR_ASG_EMAIL = 106842;
    public static final int ERROR_UPLOAD_SUCCESS_EMAIL = 106843;
    public static final int ERROR_UPLOAD_FAIL_EMAIL = 106844;
    public static final int ERROR_SUPP_ASG_EMAIL = 106845;
    public static final int ERROR_NO_EMAIL = 106846;
    public static final int ERROR_ASG_FAX = 106847;
    
    public static final int ERROR_GETTING_CULTURE = 106848;
    public static final String ERROR_GETTING_CULTURE_MSG = "Error Getting Culture from DataBase";
    
    public static final int ERROR_GETTING_RESOURCE_BUNDLE = 106849;    
    public static final String ERROR_GETTING_RESOURCE_BUNDLE_MSG = "Error in getting Resource Bundle";
    
}
