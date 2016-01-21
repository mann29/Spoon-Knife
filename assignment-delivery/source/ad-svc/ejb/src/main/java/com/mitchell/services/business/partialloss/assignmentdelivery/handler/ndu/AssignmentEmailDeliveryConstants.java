package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu;

import java.util.HashMap;

public final class AssignmentEmailDeliveryConstants {

	private AssignmentEmailDeliveryConstants() {
		
	}
	
	public static final String SYS_CONFIG_EMAIL_CREATION_SUBJECT =
			"/AssignmentDelivery/AssignmentEmail/CreationSubject";

	public static final String SYS_CONFIG_EMAIL_CANCEL_SUBJECT =
			"/AssignmentDelivery/AssignmentEmail/CancelSubject";

	public static final String SYS_CONFIG_EMAIL_UPLOAD_SUCCESS_SUBJECT =
			"/AssignmentDelivery/AssignmentEmail/UploadSuccessSubject";

	public static final String SYS_CONFIG_EMAIL_UPLOAD_FAIL_SUBJECT =
			"/AssignmentDelivery/AssignmentEmail/UploadFailSubject";
	
	public static final String SYS_CONFIG_EMAIL_FROMADDRESS =
			"/AssignmentDelivery/AssignmentEmail/FromAddress";

	public static final String SYS_CONFIG_EMAIL_FROMDISPLAYNAME =
			"/AssignmentDelivery/AssignmentEmail/FromDisplayName";
	
	public static final String SYS_CONFIG_EMAIL_URLLINK =
			"/AssignmentDelivery/AssignmentEmail/URLLink";
	
    public static final String SYS_CONFIG_EMAIL_URLLINK_DRP =
        "/AssignmentDelivery/AssignmentEmail/URLLink4DRP";
	
	public static final String SYS_CONFIG_XSLT_PATH =
			"/AssignmentDelivery/AssignmentEmail/XSLTPath";
	
	public static final String SYS_CONFIG_TEMP_FOLDER =
	        "/AssignmentDelivery/BmsToMie/TempDir";
	
	public static final String APPLICATION_NAME = "PARTIALLOSS";

	public static final String MODULE_NAME = "ASSIGNMENT_DELIVERY_SERVICE";
	
	public static final String EMAIL_OVERRIDE_CHECK="OverrideEmailAddressForRC";
	
	public static final String EVENT_LOG_ASG_EMAIL_SENT = "106810";
	
	public static final String EVENT_LOG_UPLOAD_SUCCESS_EMAIL_SENT = "106811";
	
	public static final String EVENT_LOG_UPLOAD_FAIL_EMAIL_SENT = "106812";
	
	public static final String EVENT_LOG_SUPP_ASG_EMAIL_SENT = "106813";
	
	public static final String EVENT_LOG_ASG_FAX_SENT = "106814";
	
	private static final HashMap<String, String> causeOfLossMap = new HashMap<String, String>(26);
	
	private static final HashMap<String, String> poiMap = new HashMap<String, String>(65);
	
    private static final HashMap<String, String> lossCategoryMap = new HashMap<String, String>(14);
    
    public static final String ME_METADATA_ADDITIONAL_APPRAISAL_ASSIGNMENT_INFO_IDENTIFIER = "AdditionalAppraisalAssignmentInfo";
	
	public static final String MITCHELL_ENV_NAME_APPRAISAL_ASSIGNMENT_SUB_TYPE="AssignmentSubTypeCode";
	
	static {
	    causeOfLossMap.put("CTM", "Contamination/Pollution");
	    causeOfLossMap.put("EQK", "Earthquake");
	    causeOfLossMap.put("ANI", "Animal");
	    causeOfLossMap.put("CLS", "Collision");
	    causeOfLossMap.put("EXP", "Explosion");
	    causeOfLossMap.put("FIR", "Fire");
	    causeOfLossMap.put("FLD", "Flood");
	    causeOfLossMap.put("FLO", "Falling Objects");
	    causeOfLossMap.put("GLS", "Glass");
	    causeOfLossMap.put("HAL", "Hail");
	    causeOfLossMap.put("HUR", "Hurricane");
	    causeOfLossMap.put("LSL", "Landslide");
	    causeOfLossMap.put("MEC", "Mechanical Breakdown");
	    causeOfLossMap.put("MUD", "Mudslide");
	    causeOfLossMap.put("NUC", "Nuclear Hazard");
	    causeOfLossMap.put("OTH", "Other");
	    causeOfLossMap.put("SMK", "Smoke");
	    causeOfLossMap.put("THF", "Theft");
	    causeOfLossMap.put("TOR", "Tornado");
	    causeOfLossMap.put("VMM", "Vandalism/Malicious Mischief");
	    causeOfLossMap.put("VOL", "Volcanic");
	    causeOfLossMap.put("WAR", "War/Military Action");
	    causeOfLossMap.put("WND", "Windstorm");
	    causeOfLossMap.put("ZZZ", "Mutually Defined");
	    causeOfLossMap.put("PTH", "Partial Theft");
	    causeOfLossMap.put("RRP", "Rejected Repair");
	    //new added values of "cause of loss"
	    causeOfLossMap.put("PRK", "Parking");
	    causeOfLossMap.put("PED", "Pedestrian");
	    causeOfLossMap.put("BKU", "Backing Up");
	    causeOfLossMap.put("WEA", "Other Weather Related");
	    causeOfLossMap.put("MVC", "Multi-Vehicle Collision");
	    causeOfLossMap.put("TOW", "Towing Accident");
	    causeOfLossMap.put("ROR", "Run-off-road Collision");
	    
	    poiMap.put("1", "Right Front Corner");
	    poiMap.put("2", "Right Front Side");
	    poiMap.put("3", "Right Side");
	    poiMap.put("4", "Right Rear Side");
	    poiMap.put("5", "Right Rear Corner");
	    poiMap.put("6", "Rear Center");
	    poiMap.put("7", "Left Rear Corner");
	    poiMap.put("8", "Left Rear Side");
	    poiMap.put("9", "Left Side");
	    poiMap.put("10", "Left Front Side");
	    poiMap.put("11", "Left Front Corner");
	    poiMap.put("12", "Front Center");
	    poiMap.put("13", "Rollover");
	    poiMap.put("14", "Unknown");
	    poiMap.put("15", "Total Loss");
	    poiMap.put("16", "Non-Collision");
	    poiMap.put("17", "Left And Right");
	    poiMap.put("18", "Front And Rear");
	    poiMap.put("19", "All Over");
	    poiMap.put("20", "Stripped");
	    poiMap.put("21", "Undercarriage");
	    poiMap.put("22", "Total Burn");
	    poiMap.put("23", "Interior Burn");
	    poiMap.put("24", "Engine Burn");
	    poiMap.put("25", "Hood");
	    poiMap.put("26", "Deck Lid");
	    poiMap.put("27", "Roof");
	    poiMap.put("28", "Undercarriage_ToBeDeleted");
	    poiMap.put("29", "Fresh Water");
	    poiMap.put("30", "Salt Water");
	    poiMap.put("31", "Stripped_ToBeDeleted");
	    poiMap.put("32", "Vandalized");
	    poiMap.put("33", "No Damage");
	    poiMap.put("34", "All Over_ToBeDeleted");
	    poiMap.put("35", "Engine");
	    poiMap.put("36", "Windshield");
	    poiMap.put("37", "Left Windshield");
	    poiMap.put("38", "Right Windshield");
	    poiMap.put("39", "Back Glass");
	    poiMap.put("40", "Left Front Door Glass");
	    poiMap.put("41", "Right Front Door Glass");
	    poiMap.put("42", "Left Front Vent Glass");
	    poiMap.put("43", "Right Front Vent Glass");
	    poiMap.put("44", "Left Rear Door Glass");
	    poiMap.put("45", "Right Rear Door Glass");
	    poiMap.put("46", "Left Rear Vent Glass");
	    poiMap.put("47", "Right Rear Vent Glass");
	    poiMap.put("48", "Left Rear Quarter Glass");
	    poiMap.put("49", "Right Rear Quarter Glass");
	    poiMap.put("50", "Left Side Middle Glass");
	    poiMap.put("51", "Right Side Middle Glass");
	    poiMap.put("52", "Left Headlamp");
	    poiMap.put("53", "Right Headlamp");
	    poiMap.put("54", "Left Taillamp");
	    poiMap.put("55", "Right Taillamp");
	    poiMap.put("56", "Sunroof");
	    poiMap.put("57", "Left T-Top");
        poiMap.put("58", "Right T-Top");
        poiMap.put("59", "Slider");
        poiMap.put("60", "Left Back Glass");
        poiMap.put("61", "Right Back Glass");
        poiMap.put("62", "Left Mirror");
        poiMap.put("63", "Right Mirror");
        poiMap.put("64", "Other");
        poiMap.put("65", "All Glass");
        
        lossCategoryMap.put("C", "Collision");
        lossCategoryMap.put("M", "Comprehensive");
        lossCategoryMap.put("L", "Liability");
        lossCategoryMap.put("O", "Other");
        lossCategoryMap.put("P", "Property");
        lossCategoryMap.put("U", "Unknown");
        lossCategoryMap.put("A", "Animal");
        lossCategoryMap.put("E", "Mechanical Inspection");
        lossCategoryMap.put("F", "Fire");
        lossCategoryMap.put("H", "Hail");
        lossCategoryMap.put("I", "Single Interest");
        lossCategoryMap.put("S", "Water/Flood/Submersion");
        lossCategoryMap.put("T", "Theft");
        lossCategoryMap.put("V", "Vandalism");
	}
	
	public static String getCauseOfLossDescFromCode(final String code) {
	    
	    String desc = null;
	    if (causeOfLossMap.containsKey(code)) {
	        desc = (String)causeOfLossMap.get(code);
	    }
	    return desc;
	}
	
	public static String getPoiDesc(final String code){
	    String desc = null;
	    
	    if (poiMap.containsKey(code)) {
	        desc = (String)poiMap.get(code);
	    }
	    return desc;
	}
	
    public static String getLossCategory(final String code) {
        String desc = null;
        
        if (lossCategoryMap.containsKey(code)) {
            desc = (String)lossCategoryMap.get(code);
        }
        
        return desc;
    }
}
