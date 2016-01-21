package com.mitchell.services.business.partialloss.appraisalassignment.proxy; 

import com.mitchell.common.exception.MitchellException;

public interface SystemConfigProxy 
{ 
     public String getSettingValue(final String name) throws MitchellException;
} 
