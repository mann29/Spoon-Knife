package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.systemconfiguration.SystemConfiguration;

/**
 * Service configuration.
 */

public class AssignmentDeliveryConfig {
      private final static String CLASS_NAME = AssignmentDeliveryConfig.class.getName();

	private final static AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(CLASS_NAME);

	/**
	 * The setting path for the JNDI Provider Url is: <code>{@value}</code>.
	 */
	private static final String SET_JNDI_PROVIDERURL = "/AssignmentDeliveryJavaClient/JNDI/ProviderUrl";

	/**
	 * The setting path for the JNDI EBJ Name is: <code>{@value}</code>.
	 */
	private static final String SET_JNDI_EJBNAME = "/AssignmentDeliveryJavaClient/JNDI/EJBName";
	
	/**
	 * The setting path for the JNDI Factory is: <code>{@value}</code>.
	 */
	private static final String SET_JNDI_FACTORY = "/AssignmentDeliveryJavaClient/JNDI/JNDIFactory";
	
	/**
	 * The setting path for the StagingSubDir is: <code>{@value}</code>.
	 */
	private static final String SET_FILEARCHIVE_STAGING_SUBDIR = "/AssignmentDelivery/FileArchive/StagingSubDir";

	/**
	 * The setting path for the TempDir is: <code>{@value}</code>.
	 */
	private static final String SET_TEMP_DIR = "/AssignmentDelivery/BmsToMie/TempDir";

	/**
	 * The setting prefix for the Handler is: <code>{@value}</code>.
	 */
	private static final String SET_HANDLERS_PREFIX = "/AssignmentDelivery/Handlers/";

	/**
	 * The setting prefix for the CancellationAlertMsg is: <code>{@value}</code>.
	 */
	private static final String SET_CANCEL_ALERT_MESSAGE = "/AssignmentDelivery/AlertMessages/CancellationAlertMsg";

	/**
	 * The setting prefix for the CancellationAlertMsg is: <code>{@value}</code>.
	 */
	private static final String SET_CONV_SUPP_TO_ORIG_NOTICE_FILEPATH = "/AssignmentDelivery/ConvSuppToOrigNoticeFilePath";

    /**
     * The setting path for the Simple Notification is: <code>{@value}</code>.
     */
    private static final String SET_SIMPLE_NOTIFICATION_FLAG = "/AssignmentDelivery/Options/SimpleNotificationFlag";

    /**
     * The setting path for the Error Email Notification from display name is: <code>{@value}</code>.
     */
    private static final String SET_ERROR_EMAIL_FROM_NAME = "/AssignmentDelivery/Notification/Error/Email/FromDisplayName";
    
    /**
     * The setting path for the Error Email Notification from address is: <code>{@value}</code>.
     */
    private static final String SET_ERROR_EMAIL_FROM_ADDR = "/STDADWF/Notification/Error/Email/FromAddr";
    
    /**
     * The setting path for the Error Fax Notification from name is: <code>{@value}</code>.
     */
    private static final String SET_ERROR_FAX_FROM_NAME = "/AssignmentDelivery/Notification/Error/Fax/FromName";
    
    /**
     * The setting path for the Error Fax Notification from address is: <code>{@value}</code>.
     */
    private static final String SET_ERROR_FAX_FROM_ADDR = "/AssignmentDelivery/Notification/Error/Fax/FromAddr";
    
    /**
     * The setting path for the Error Fax Notification from number is: <code>{@value}</code>.
     */
    private static final String SET_ERROR_FAX_FROM_NUMBER = "/AssignmentDelivery/Notification/Error/Fax/FromNumber";
    
    /**
     * The setting path for the Secondary Email Notification from display name is: <code>{@value}</code>.
     */
    private static final String SET_PRIMARY_EMAIL_FROM_NAME = "/AssignmentDelivery/Notification/Primary/Email/FromDisplayName";
    
    /**
     * The setting path for the Secondary Email Notification from address is: <code>{@value}</code>.
     */
    private static final String SET_PRIMARY_EMAIL_FROM_ADDR = "/AssignmentDelivery/Notification/Primary/Email/FromAddr";
    
    /**
     * The setting path for the Secondary Fax Notification from name is: <code>{@value}</code>.
     */
    private static final String SET_PRIMARY_FAX_FROM_NAME = "/AssignmentDelivery/Notification/Primary/Fax/FromName";
    
    /**
     * The setting path for the Secondary Fax Notification from address is: <code>{@value}</code>.
     */
    private static final String SET_PRIMARY_FAX_FROM_ADDR = "/AssignmentDelivery/Notification/Primary/Fax/FromAddr";
    
    /**
     * The setting path for the Secondary Fax Notification from number is: <code>{@value}</code>.
     */
    private static final String SET_PRIMARY_FAX_FROM_NUMBER = "/AssignmentDelivery/Notification/Primary/Fax/FromNumber";


    private static final String SET_FEATURE_CROSS_SUPPLEMENTATION_ALLOWED = "/AssignmentDelivery/IsCrossSupplementationAllowed";
    
    
    // AAA email/fax related changes
    private static final String OVERRIDE_RECIPIENT_EMAIL_FOR_RCCONNECT = "/AssignmentDelivery/OverrideEmailIdForRCConnectCoCd";
    private static final String OVERRIDE_RECIPIENT_FAX_FOR_RCCONNECT = "/AssignmentDelivery/OverrideFaxForRCConnectCoCd";
    private static final String ACCESS_RCCONNECT_BASIC = "/AssignmentDelivery/ShopBasicAppCode";
    private static final String ACCESS_RCCONNECT_PREMIUM ="/AssignmentDelivery/ShopPremiumAppCode";

    // DataSource related change
    private static final String SET_EPD_DATA_SOURCE = "/AssignmentDelivery/Database/EPDDataSource";
	/**
	 * Returns the value in settings at {@link #SET_JNDI_PROVIDERURL}
	 */
	public static String getJNDIProviderUrl()
                throws AssignmentDeliveryException {
		return getSetting(SET_JNDI_PROVIDERURL);
	}

	/**
	 * Returns the value in settings at {@link #SET_JNDI_EJBNAME}
	 */
	public static String getEJBName() 
            throws AssignmentDeliveryException {
    	return getSetting(SET_JNDI_EJBNAME);
	}
	
	/**
	 * Returns the value in settings at {@link #SET_JNDI_EJBNAME}
	 */
	public static String getJNDIFactory() 
            throws AssignmentDeliveryException {
    	return getSetting(SET_JNDI_FACTORY);
	}

		/**
	 * Returns the value in settings at {@link #SET_FILEARCHIVE_STAGING_SUBDIR}
	 */
	public static String getFileArchiveStagingSubdir() 
            throws AssignmentDeliveryException {
		return getSetting(SET_FILEARCHIVE_STAGING_SUBDIR);
	}

	/**
	 * Returns the value in settings at {@link #SET_TEMP_DIR}
	 */
	public static String getTempDir() 
            throws AssignmentDeliveryException {
		return getSetting(SET_TEMP_DIR);
	}

	/**
	 * Returns the value in settings at {@link #SET_CANCEL_ALERT_MESSAGE}
	 */
	public static String getCancellationAlertMsg() 
            throws AssignmentDeliveryException {
		return getSetting(SET_CANCEL_ALERT_MESSAGE);
	}

	/**
	 * Returns the value in settings at {@link #SET_CONV_SUPP_TO_ORIG_NOTICE_FILEPATH}
	 */
	public static String getSuppToOrigNoticeFilePath() 
            throws AssignmentDeliveryException {
		return getSetting(SET_CONV_SUPP_TO_ORIG_NOTICE_FILEPATH);
	}

    /**
     * Returns the value in settings at {@link #SET_SIMPLE_NOTIFICATION_FLAG}
     */
    public static boolean getSimpleNotificationFlag()
      throws AssignmentDeliveryException
    {
        return getBooleanValue(getSetting(SET_SIMPLE_NOTIFICATION_FLAG));
        
    }

    
    /**
     * Returns the value in settings at {@link #SET_ERROR_EMAIL_FROM_NAME}
     */
    public static String getErrorEmailFromName()
      throws AssignmentDeliveryException
    {
        return getSetting(SET_ERROR_EMAIL_FROM_NAME);
    }
  
    /**
     * Returns the value in settings at {@link #SET_ERROR_EMAIL_FROM_ADDR}
     */
    public static String getErrorEmailFromAddr()
      throws AssignmentDeliveryException
    {
        return getSetting(SET_ERROR_EMAIL_FROM_ADDR);
    }
  
    /**
     * Returns the value in settings at {@link #SET_ERROR_FAX_FROM_NAME}
     */
    public static String getErrorFaxFromName()
      throws AssignmentDeliveryException
    {
        return getSetting(SET_ERROR_FAX_FROM_NAME);
    }
  
    /**
     * Returns the value in settings at {@link #SET_ERROR_FAX_FROM_ADDR}
     */
    public static String getErrorFaxFromAddr()
      throws AssignmentDeliveryException
    {
        return getSetting(SET_ERROR_FAX_FROM_ADDR);
    }

    /**
     * Returns the value in settings at {@link #SET_ERROR_FAX_FROM_NUMBER}
     */
    public static String getErrorFaxFromNumber()
      throws AssignmentDeliveryException
    {
        return getSetting(SET_ERROR_FAX_FROM_NUMBER);
    }
  
    /**
     * Returns the value in settings at {@link #SET_PRIMARY_EMAIL_FROM_NAME}
     */
    public static String getPrimaryEmailFromName()
      throws AssignmentDeliveryException
    {
        return getSetting(SET_PRIMARY_EMAIL_FROM_NAME);
    }
  
    /**
     * Returns the value in settings at {@link #SET_PRIMARY_EMAIL_FROM_ADDR}
     */
    public static String getPrimaryEmailFromAddr()
      throws AssignmentDeliveryException
    {
        return getSetting(SET_PRIMARY_EMAIL_FROM_ADDR);
    }

    /**
     * Returns the value in settings at {@link #SET_PRIMARY_FAX_FROM_NAME}
     */
    public static String getPrimaryFaxFromName()
      throws AssignmentDeliveryException
    {
        return getSetting(SET_PRIMARY_FAX_FROM_NAME);
    }
  
    /**
     * Returns the value in settings at {@link #SET_PRIMARY_FAX_FROM_ADDR}
     */
    public static String getPrimaryFaxFromAddr()
      throws AssignmentDeliveryException
    {
        return getSetting(SET_PRIMARY_FAX_FROM_ADDR);
    }
  
    /**
     * Returns the value in settings at {@link #SET_PRIMARY_FAX_FROM_NUMBER}
     */
    public static String getPrimaryFaxFromNumber()
      throws AssignmentDeliveryException
    {
        return getSetting(SET_PRIMARY_FAX_FROM_NUMBER);
    }
        
	/**
	 * Returns the value in settings at {@link #SET_HANDLERS_PREFIX}
	 */
	public static String getHanlderName(String handlerType)
            throws AssignmentDeliveryException {
		return getSetting(SET_HANDLERS_PREFIX + handlerType);
	}
    
	/**
	 * Return a string value from the SystemConfiguration Service for a
	 * specified setting name.
	 * 
	 * @param name
	 *            String containing the name of the setting to retrieve.
	 */
	private static String getSetting(String name) 
            throws AssignmentDeliveryException {
        String methodName = "getSetting";                
                
        String settingValue = SystemConfiguration.getSettingValue(name);
        if(settingValue == null) {
			mLogger.severe("Could not find any value for following setting = " + name);
            ErrorLoggingService.logError(
                    AssignmentDeliveryErrorCodes.PROPERTY_VALUE_NOT_FOUND, null, 
                    CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, 
                    null, "Could not find any value for following setting = " + name, 
                    null, 0, null);
            throw mLogger.createException(
                AssignmentDeliveryErrorCodes.PROPERTY_VALUE_NOT_FOUND, 
                "Could not find any value for following setting = " + name);
        }
        return settingValue;
	}
	
	/**
	   * Return a string value from the SystemConfiguration Service for a specified
	   * setting name.
	   * 
	   * @param name String containing the name of the setting to retrieve.
	   * @param mandatory If true then a MitchellException is thrown if the returned
	   *          value
	   *          from SystemConfig is null or empty.
	   */
	  public static String getSetting(String name, boolean mandatory)
	      throws MitchellException
	  {
	    String value = SystemConfiguration.getSettingValue(name);

	    if (mLogger.isLoggable(java.util.logging.Level.FINE)) {
	    	mLogger.fine("[" + name + " = " + value + "]");
	    }

	    if (mandatory && (value == null || value.length() == 0)) {
	      throw new MitchellException(AssignmentDeliveryErrorCodes.CUSTOM_SETTING_ERROR,
	          "StdADWFConfig", "getSetting",
	          "Mandatory SystemConfiguration setting not defined. Setting=" + name);
	    }
	    //
	    return value;
	  }

	/**
	 * return an int for a given string value.
	 */
	private static int getIntValue(String value) {
		if ((value == null) || (value.length() == 0)) {
			return 0;
		}
		;
		return Integer.parseInt(value);
	}

	private static boolean getBooleanValue(String value) {
		if ((value == null) || (value.length() == 0)) {
			return false;
		}
		;
		return Boolean.valueOf(value).booleanValue();
	}
    
	public static boolean isCrossSupplementationAllowed()
            throws AssignmentDeliveryException {
		final String settingValue = getSetting(SET_FEATURE_CROSS_SUPPLEMENTATION_ALLOWED);
		return getBooleanValue(settingValue);
	}
    
	public static String getRCConnectBasic() throws AssignmentDeliveryException {
		return getSetting(ACCESS_RCCONNECT_BASIC);
	}

	public static String getRCConnectPremium() throws AssignmentDeliveryException {
		return getSetting(ACCESS_RCCONNECT_PREMIUM);
	}

	public static String getCoCdForOverrideEmailRC() throws AssignmentDeliveryException {

		return getSetting(OVERRIDE_RECIPIENT_EMAIL_FOR_RCCONNECT);
	}
	
	public static String getCoCdForOverrideFaxRC() throws AssignmentDeliveryException {

		return getSetting(OVERRIDE_RECIPIENT_FAX_FOR_RCCONNECT);
	}
	
	/**
	 * Get EPD DataSource.
	 */
	public static String getEPDDataSource() throws MitchellException {
		String retval = getSetting(SET_EPD_DATA_SOURCE, false);
		if (retval == null) {
			retval = "EPDDataSource";
		}
		return retval;
	  }

}
