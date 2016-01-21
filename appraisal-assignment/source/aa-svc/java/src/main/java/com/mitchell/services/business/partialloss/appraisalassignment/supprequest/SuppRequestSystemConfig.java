package com.mitchell.services.business.partialloss.appraisalassignment.supprequest;

import com.mitchell.systemconfiguration.SystemConfiguration;
import java.io.File;

public class SuppRequestSystemConfig 
{ 

    // TempDir
    public static final String CONFIG_TEMP_DIR = "/AppraisalAssignment/SuppRequest/TempDir";

    // OutputHTMLDocsFlag
    public static final String CONFIG_OUTPUT_HTML = "/AppraisalAssignment/SuppRequest/OutputHTMLDocsFlag";
    
    // TemplateBaseDir
    private static final String CONFIG_TEMPLATE_BASE_DIR = "/AppraisalAssignment/SuppRequest/TemplateBaseDir";
    
    // EMail Properties
    private static final String CONFIG_EMAIL_FROM_NAME = "/AppraisalAssignment/SuppRequest/Email/FromDisplayName";
    private static final String CONFIG_EMAIL_FROM_EMAIL_ADDRESS = "/AppraisalAssignment/SuppRequest/Email/FromEmailAddress";
    
    // Text Formatter Properties    
    private static final String CONFIG_TEXT_SUBMIT_SUPPLEMENT_COMMENT_TEMPLATE = "/AppraisalAssignment/SuppRequest/TextFormat/SubmitSupplementCmntTemplate";
    private static final String CONFIG_TEXT_SUBMIT_SUPPLEMENT_COMMENT_TEMPLATE_PROPS = "/AppraisalAssignment/SuppRequest/TextFormat/SubmitSupplementCmntTemplateProps";
    private static final String CONFIG_TEXT_EMAIL_SUBJECT_TEMPLATE = "/AppraisalAssignment/SuppRequest/TextFormat/EmailSubjectTemplate";
    private static final String CONFIG_TEXT_EMAIL_SUBJECT_TEMPLATE_PROPS = "/AppraisalAssignment/SuppRequest/TextFormat/EmailSubjectTemplateProps";
    private static final String CONFIG_TEXT_TEMPLATE_PREFIX = "/AppraisalAssignment/SuppRequest/TextFormat/TemplatePrefix";
    private static final String CONFIG_TEXT_TEMPLATE_SUFFIX = "/AppraisalAssignment/SuppRequest/TextFormat/TemplateSuffix";
    
    // MIE Formatter Settings
    private static final String CONFIG_MIE_TEXT_TEMPLATE_FILE = "/AppraisalAssignment/SuppRequest/TextFormat/MIE/TemplateFile";
    private static final String CONFIG_MIE_TEXT_TEMPLATE_PROPERTIES = "/AppraisalAssignment/SuppRequest/TextFormat/MIE/TemplateProps";
    private static final String CONFIG_MIE_TEXT_ANNOTATION_TEMPLATE_FILE = "/AppraisalAssignment/SuppRequest/TextFormat/MIE/AnnotationTemplateFile";
    private static final String CONFIG_MIE_TEXT_ANNOTATION_TEMPLATE_PROPERTIES = "/AppraisalAssignment/SuppRequest/TextFormat/MIE/AnnotationTemplateProps";    
    
    // added Related & unRelated Prior Damage Formatter Settings
    private static final String CONFIG_PRIOR_DAMAGE_TEXT_TEMPLATE_FILE = "/AppraisalAssignment/SuppRequest/TextFormat/PriorDamage/TemplateFile";
    private static final String CONFIG_PRIOR_DAMAGE_TEXT_TEMPLATE_PROPERTIES = "/AppraisalAssignment/SuppRequest/TextFormat/PriorDamage/TemplateProps";
    
    // EMS Formatter Settings
    private static final String CONFIG_EMS_TEXT_TEMPLATE_FILE = "/AppraisalAssignment/SuppRequest/TextFormat/EMS/TemplateFile";
    private static final String CONFIG_EMS_TEXT_TEMPLATE_PROPERTIES = "/AppraisalAssignment/SuppRequest/TextFormat/EMS/TemplateProps";
    
    // added Supplement Instructions Formatter Settings for MIE BMS & CCC/AUDATEX BMS
    private static final String CONFIG_MIE_SUPP_INSTRUCTIONS_TEXT_TEMPLATE_FILE= "/AppraisalAssignment/SuppRequest/TextFormat/SupplementInstructions/MIETemplateFile";
    private static final String CONFIG_CCCAUDATEX_SUPP_INSTRUCTIONS_TEXT_TEMPLATE_FILE= "/AppraisalAssignment/SuppRequest/TextFormat/SupplementInstructions/CCCAudatexTemplateFile";

    // added Supplement Instructions Formatter Settings for GTMOTIVE BMS
	private static final String CONFIG_GMR_SUPP_INSTRUCTIONS_TEXT_TEMPLATE_FILE = "/AppraisalAssignment/SuppRequest/TextFormat/SupplementInstructions/GMRTemplateFile";
    
    // TempDir
    public static String getTempDir() {
        return getStringValue(CONFIG_TEMP_DIR);
    } 

    // OutputHTMLDocsFlag
    public static String getOutputHTMLDocsFlag() {
        return getStringValue(CONFIG_OUTPUT_HTML);
    } 
    
    public static String getTemplateBaseDir() {
        return getStringValue(CONFIG_TEMPLATE_BASE_DIR);
    }       
    
    // Email Properties
    public static String getFromEmailDisplayName() {
        return getStringValue(CONFIG_EMAIL_FROM_NAME);        
    }
    
    public static String getFromEmailAddress() {
        return getStringValue(CONFIG_EMAIL_FROM_EMAIL_ADDRESS);        
    }    

    // Text Formatter
    public static String getSubmitSupplementCommentTemplate() {
        return getStringValue(CONFIG_TEXT_SUBMIT_SUPPLEMENT_COMMENT_TEMPLATE);    
    }    
    
    public static String getSubmitSupplementCommentTemplateProps() {
        return getStringValue(CONFIG_TEXT_SUBMIT_SUPPLEMENT_COMMENT_TEMPLATE_PROPS);
    }    
    
    
    public static String getTextEmailSubjectTemplate() {
        return getStringValue(CONFIG_TEXT_EMAIL_SUBJECT_TEMPLATE);    
    }
    
    public static String getTextEmailSubjectTemplateProps() {
        return getStringValue(CONFIG_TEXT_EMAIL_SUBJECT_TEMPLATE_PROPS);    
    }
    
    public static String getTextTemplatePrefix() {
        return getStringValue(CONFIG_TEXT_TEMPLATE_PREFIX);    
    }
    
    public static String getTextTemplateSuffix() {
        return getStringValue(CONFIG_TEXT_TEMPLATE_SUFFIX);    
    }                            
    
    
    // MIE Formatter Settings
    public static String getMieTextTemplateFile() {
        String temp = getTemplateBaseDir() + File.separator + getStringValue(CONFIG_MIE_TEXT_TEMPLATE_FILE);
        return temp;    
    }                                
    
    public static String getMieTextTemplateProperties() {
        return getStringValue(CONFIG_MIE_TEXT_TEMPLATE_PROPERTIES);    
    }
    
    public static String getMieAnnotationTextTemplateFile() {
        String temp = getTemplateBaseDir() + File.separator + 
                getStringValue(CONFIG_MIE_TEXT_ANNOTATION_TEMPLATE_FILE);
        return temp;
    }                                
    
    public static String getMieAnnotationTextTemplateProperties() {
        return getStringValue(CONFIG_MIE_TEXT_ANNOTATION_TEMPLATE_PROPERTIES);    
    } 
    
    // added Related & unRelated Prior Damage Formatter Settings
    public static String getPriorDamageTextTemplateFile() {
        String temp = getTemplateBaseDir() + File.separator + 
                getStringValue(CONFIG_PRIOR_DAMAGE_TEXT_TEMPLATE_FILE);
        return temp;           
    }  
    
    public static String getPriorDamageTextTemplateProperties() {
        return getStringValue(CONFIG_PRIOR_DAMAGE_TEXT_TEMPLATE_PROPERTIES);  
    }                                 
    
    // added Supplement Instructions Formatter Settings for MIE BMS
    public static String getMieSuppInstructionsTextTemplateFile() {
        String temp = getTemplateBaseDir() + File.separator + 
                getStringValue(CONFIG_MIE_SUPP_INSTRUCTIONS_TEXT_TEMPLATE_FILE);
        return temp;        
    }
     
     // added Supplement Instructions Formatter Settings for CCC/AUDATEX BMS
     public static String getCCCAudatexSuppInstructionsTextTemplateFile() {
        String temp = getTemplateBaseDir() + File.separator + 
                getStringValue(CONFIG_CCCAUDATEX_SUPP_INSTRUCTIONS_TEXT_TEMPLATE_FILE);
        return temp;           
     }
     
	/**
	 * added Supplement Instructions Formatter Settings for GTMOTIVE
	 * 
	 * @return String
	 */
	public static String getGMRSuppInstructionsTextTemplateFile() {
		String temp = getTemplateBaseDir()
				+ File.separator
				+ getStringValue(CONFIG_GMR_SUPP_INSTRUCTIONS_TEXT_TEMPLATE_FILE);
		return temp;
	}
     
     
    // EMS Formatter Settings
    public static String getEMSTextTemplateFile() {
        String temp = getTemplateBaseDir() + File.separator + 
                getStringValue(CONFIG_EMS_TEXT_TEMPLATE_FILE);
        return temp;          
    }                                
    
    public static String getEMSTextTemplateProperties() {
        return getStringValue(CONFIG_EMS_TEXT_TEMPLATE_PROPERTIES);   
    }                                    
    
    
    // Get the boolean value
    /*
     * Commented to resolve the codepro comments.
    private static boolean getBooleanValue(String key)
    {
        boolean enabled = false;
        String value = SystemConfiguration.getSettingValue(key);
        validateSettingValue(value, key);
        
        if("true".equalsIgnoreCase(value))
        {
            enabled = true;            
        }
        
        return enabled;
    }    
    */
    // get the string setting value
    private static String getStringValue(String key)
    {
        String value = SystemConfiguration.getSettingValue(key);
        validateSettingValue(value, key);
        return value.trim();
    }
    
    // validate the settings
    private static void validateSettingValue(String value, String settingName)
    {
        if(value == null)
        {
            throw new RuntimeException(" The Setting " + settingName + " value cannot be null");
        }        
    }    
    
} 
