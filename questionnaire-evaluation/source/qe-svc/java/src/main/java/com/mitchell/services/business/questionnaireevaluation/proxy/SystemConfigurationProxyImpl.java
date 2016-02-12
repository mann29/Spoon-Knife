package com.mitchell.services.business.questionnaireevaluation.proxy;

import com.mitchell.systemconfiguration.SystemConfiguration;

public class SystemConfigurationProxyImpl implements SystemConfigurationProxy {

	public String getSettingValue(String settingName) {
		return	 SystemConfiguration.getSettingValue(settingName);
	}
	
	public String getSettingValue(String settingName, String defaultValue) {
		
		String settingValue = SystemConfiguration.getSettingValue(settingName);

        if (settingValue == null) {
            settingValue = defaultValue;
        }
        return settingValue;
	}

	/**
	 * 
	 * @param settingName
	 *            Custom Setting Name
	 * @param defaultValue
	 *            Default Value of the Custom Setting
	 * @return Value of the Custom Setting
	 */

	public String getSystemConfigurationSettingValue(final String settingName,
			final String defaultValue) {
		String settingValue = SystemConfiguration.getSettingValue(settingName);
		if (settingValue == null) {
			settingValue = defaultValue;
		}
		return settingValue;
	}
}
