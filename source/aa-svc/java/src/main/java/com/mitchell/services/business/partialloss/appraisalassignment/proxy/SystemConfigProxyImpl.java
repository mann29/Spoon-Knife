package com.mitchell.services.business.partialloss.appraisalassignment.proxy; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.systemconfiguration.SystemConfiguration;

public class SystemConfigProxyImpl implements SystemConfigProxy
{ 
    public String getSettingValue(final String name) throws MitchellException
    {
        return SystemConfiguration.getSettingValue(name);
    }
} 
