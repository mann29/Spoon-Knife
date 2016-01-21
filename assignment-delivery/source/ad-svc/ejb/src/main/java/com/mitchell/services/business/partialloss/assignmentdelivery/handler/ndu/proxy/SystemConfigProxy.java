package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy;

public interface SystemConfigProxy {
	String getStringValue(String key, String defaultValue);
	String getStringValue(String key);
}
