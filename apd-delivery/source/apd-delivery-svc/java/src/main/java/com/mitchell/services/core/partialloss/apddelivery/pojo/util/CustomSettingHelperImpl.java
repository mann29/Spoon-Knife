package com.mitchell.services.core.partialloss.apddelivery.pojo.util;

import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.CustomSettingProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ErrorLogProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.utils.misc.AppUtilities;

/**
 * The Class CustomSettingHelperImpl.
 */
public final class CustomSettingHelperImpl implements CustomSettingHelper {
    
    /**
     * class name.
     */
    private static final String CLASS_NAME = 
    	CustomSettingHelperImpl.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private CustomSettingProxy customSettingProxy;
    private ErrorLogProxy errorLogProxy;
    private SystemConfigurationProxy systemConfigurationProxy;
        
    /**
     * @param userInfoDocument
     * UserInfoDocument object
     * @return String
     * Custom setting value
     * @throws MitchellException
     * Mitchell Exception
     */
    public String getSIPAssignmentDeliveryDestination(UserInfoDocument userInfoDocument)
                                                    throws MitchellException {
    	String methodName = "getSIPAssignmentDeliveryDestination";
    	logger.entering(CLASS_NAME, methodName);
        String sipAssignmentDelivery = null;
        try {
            String value = customSettingProxy.getUserCustomSetting(
                    userInfoDocument.getUserInfo().getUserID(),
                    userInfoDocument.getUserInfo().getOrgCode(),
                    systemConfigurationProxy.getPartiallossSIPDelGroup(),
                    systemConfigurationProxy.getPartiallossSIPDelDestinationSettingName());
            
            if (value != null && value.trim().length() > 0) {
    
                sipAssignmentDelivery = value;
            }
        } catch (Exception e) {
            final String desc = "Exception in reading user custom setting:"
                + AppUtilities.getStackTraceString(e, true);
            final MitchellException me =
                new MitchellException(
                        APDDeliveryConstants.ERROR_CUSTOM_SETTING, CLASS_NAME,
                        "getSIPAssignmentDeliveryDestination",
                        desc, e);
            me.setCompanyCode(userInfoDocument.getUserInfo().getOrgCode());
            errorLogProxy.logError(me);
        }
        logger.exiting(CLASS_NAME, methodName);
        return sipAssignmentDelivery;
    }
    
    /**
     * @param userInfoDocument
     * UserInfoDocument object
     * @return String
     * Custom setting value
     * @throws MitchellException
     * Mitchell Exception
     */
    public String isEmailRequired(UserInfoDocument userInfoDocument)
                                                    throws MitchellException {
    	String methodName = "isEmailRequired";
    	logger.entering(CLASS_NAME, methodName);
        String emailRquiredVal = null;
        try {
            String value = customSettingProxy.getUserCustomSetting(
                    userInfoDocument.getUserInfo().getUserID(),
                    userInfoDocument.getUserInfo().getOrgCode(),
                    systemConfigurationProxy.getBroadcastMessageGroup(),
                    systemConfigurationProxy.getEmailToRcpntReqSettingName());
            
            if (value != null && value.trim().length() > 0) {
    
                emailRquiredVal = value;
            }
        } catch (Exception e) {
            final String desc = "Exception in reading user custom setting:"
                + AppUtilities.getStackTraceString(e, true);
            final MitchellException me =
                new MitchellException(
                        APDDeliveryConstants.ERROR_CUSTOM_SETTING, CLASS_NAME,
                        "",
                        desc, e);
            me.setCompanyCode(userInfoDocument.getUserInfo().getOrgCode());
            errorLogProxy.logError(me);
        }
        logger.exiting(CLASS_NAME, methodName);
        return emailRquiredVal;
    }
    /**
     * Gets the custom setting proxy.
     *
     * @return the custom setting proxy
     */
    public CustomSettingProxy getCustomSettingProxy() {
        return customSettingProxy;
    }

    /**
     * Sets the custom setting proxy.
     *
     * @param customSettingProxy the new custom setting proxy
     */
    public void setCustomSettingProxy(CustomSettingProxy customSettingProxy) {
        this.customSettingProxy = customSettingProxy;
    }
    
    /**
     * Gets the ErrorLogProxy.
     *
     * @return the ErrorLogProxy
     */
    public ErrorLogProxy getErrorLogProxy() {
        return errorLogProxy;
    }

    /**
     * Sets the ErrorLogProxy.
     *
     * @param errorLogProxy
     */
    public void setErrorLogProxy(ErrorLogProxy errorLogProxy) {
        this.errorLogProxy = errorLogProxy;
    }
    
    /**
     * Gets the SystemConfigurationProxy.
     *
     * @return the SystemConfigurationProxy
     */
    public SystemConfigurationProxy getSystemConfigurationProxy() {
        return systemConfigurationProxy;
    }

    /**
     * Sets the SystemConfigurationProxy.
     *
     * @param systemConfigurationProxy
     */
    public void setSystemConfigurationProxy(SystemConfigurationProxy systemConfigurationProxy) {
        this.systemConfigurationProxy = systemConfigurationProxy;
    }
    
}
