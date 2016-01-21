package com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.arc5;

/**
 * Constants for DS02 (and DS01) records processing.
 */
public interface DSRecordsConstants {
    
    // SF constants.
    public static String DS01_SF_INS_COMPANY_ID = "SF";
    public static String DS01_MEDS_OPERATOR_ID = "SF272EDI";

    // Field lengths.
	public static int DS02_VEH_YEAR_MAX_LENGTH = 4;
    public static int DS02_VEH_MAKE_MAX_LENGTH = 12;
    public static int DS02_VEH_MODEL_MAX_LENGTH = 11;
    public static int DS02_VEH_BODY_STYLE_MAX_LENGTH = 12;
    
    public static int DS02_PRINCIPAL_DAMAGE_MAX_LENGTH = 60;

    public static int DS02_PRIOR_DAMAGE_MAX_LENGTH = 32;

    public static int DS02_VEH_LOCATION_ADDR_LINE1_MAX_LENGTH = 35;
    public static int DS02_VEH_LOCATION_CITY_MAX_LENGTH = 30;
    public static int DS02_VEH_LOCATION_STATE_MAX_LENGTH = 2;
    public static int DS02_VEH_LOCATION_ZIP_MAX_LENGTH = 9;

    // SCR #11292
    public static int DS02_ACCIDENT_LOCATION_DESCRIPTION_MAX_LENGTH = 35;
    // end SCR #11292
    public static int DS02_ACCIDENT_LOCATION_ADDR_LINE1_MAX_LENGTH = 35;
    public static int DS02_ACCIDENT_LOCATION_ADDR_LINE2_MAX_LENGTH = 35;
    public static int DS02_ACCIDENT_LOCATION_CITY_MAX_LENGTH = 30;
    public static int DS02_ACCIDENT_LOCATION_STATE_MAX_LENGTH = 2;
    public static int DS02_ACCIDENT_LOCATION_ZIP_MAX_LENGTH = 9;

    public static int DS02_FACTS_OF_LOSS_LINE1_MAX_LENGTH = 78;
    public static int DS02_FACTS_OF_LOSS_LINE2_MAX_LENGTH = 78;
    public static int DS02_FACTS_OF_LOSS_LINE3_MAX_LENGTH = 78;
    public static int DS02_FACTS_OF_LOSS_LINE4_MAX_LENGTH = 20;

    public static int DS02_SPECIAL_INSTRUCTIONS_LINE1_MAX_LENGTH = 78;
    public static int DS02_SPECIAL_INSTRUCTIONS_LINE2_MAX_LENGTH = 78;
    public static int DS02_SPECIAL_INSTRUCTIONS_LINE3_MAX_LENGTH = 78;
    public static int DS02_SPECIAL_INSTRUCTIONS_LINE4_MAX_LENGTH = 28;

    // Header strings.
    public static String DS02_HEADER_PRINCIPAL_DAMAGE = "PRINCIPAL DAMAGE:";
    public static String DS02_HEADER_PRIOR_DAMAGE = "PRIOR DAMAGE:";
    public static String DS02_HEADER_VEH_LOCATION = "VEHICLE LOCATION:";
    public static String DS02_HEADER_ACCIDENT_LOCATION = "ACCIDENT LOCATION:";
    public String DS02_HEADER_FACTS_OF_LOSS = "FACTS OF LOSS:";
    public String DS02_HEADER_SPECIAL_INSTRUCTIONS = "SPECIAL INSTRUCTIONS:";

    // Leading spaces values.
    public static String DS02_VEH_LOCATION_LEADING_SPACES = "    ";
    public static String DS02_ACCIDENT_LOCATION_LEADING_SPACES = "    ";
    public static String DS02_FACTS_OF_LOSS_LEADING_SPACES = "  ";
    public static String DS02_SPECIAL_INSTRUCTIONS_LEADING_SPACES = "  ";
    
    // NVPs for MitchellEnvelope.
    public static String NVP_SF_PRINCIPAL_DAMAGE_DESCRIPTION = "SF_PrincipalDamageDescription";
    public static String NVP_SF_ESTIMATE_ASSIGNMENT_FACTS = "SF_EstimateAssignmentFacts";
    // SCR #11292
    public static String NVP_SF_ACCIDENT_LOCATION_DESCRIPTION = "SF_AccidentLocationDescription";
}
