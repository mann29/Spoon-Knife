package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;

/**
 * The Interface CustomSettingProxy.
 */
public interface CustomSettingProxy {

    /**
     * Gets the user custom setting.
     *
     * @param orgCode the org code
     * @param coCode the co code
     * @param groupName the group name
     * @param settingName the setting name
     * @return the user custom setting
     * @throws MitchellException the mitchell exception
     */
    String getUserCustomSetting(final String orgCode, final String coCode,
            final String groupName, final String settingName)
            throws MitchellException;
    
    /**
     * This method gets company custom settings.
     * 
     * @param coCode
     * @param groupName
     * @param settingName
     * @return String
     * @throws MitchellException
     * Mitchell Exception
     */
    String getCompanyCustomSetting(String coCode,
            String groupName, String settingName) throws MitchellException;
    
    String getNICBReportDeliveryEventSetting(String companyCode) throws Exception;
}
