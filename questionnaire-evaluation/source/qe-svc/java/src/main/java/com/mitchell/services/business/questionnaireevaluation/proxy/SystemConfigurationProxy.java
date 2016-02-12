package com.mitchell.services.business.questionnaireevaluation.proxy;

/**
 * @author preeti.sharma
 *
 */
public interface SystemConfigurationProxy {
	
	public String getSettingValue(String settingName);
	public String getSettingValue(String settingName, String defaultValue);
	 public String getSystemConfigurationSettingValue(final String settingName, final String defaultValue);
}
