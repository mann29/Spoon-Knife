package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy;

import com.mitchell.systemconfiguration.SystemConfiguration;

public class SystemConfigProxyImpl implements SystemConfigProxy {

	public String getStringValue(final String key, final String defaultValue) {
		final String value = SystemConfiguration.getSettingValue(key);
		
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	public String getStringValue(String key) {
		return SystemConfiguration.getSettingValue(key);
	}
}
