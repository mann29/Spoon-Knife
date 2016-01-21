package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util;

import com.mitchell.common.exception.MitchellException;

public interface CustomSettingHelper {
	
	String getCompanyCustomSetting(final String coCode,
			final String groupName, final String settingName) throws MitchellException;
	 
    /**
     * To get custom setting value at user level
     * @param orgCode
     * @param coCode
     * @param groupName
     * @param settingName
     * @return custom setting value
     * @throws MitchellException
     */
	String getUserCustomSetting(final String orgCode, final String coCode,
	            final String groupName, final String settingName)
	            throws MitchellException;
		
}
