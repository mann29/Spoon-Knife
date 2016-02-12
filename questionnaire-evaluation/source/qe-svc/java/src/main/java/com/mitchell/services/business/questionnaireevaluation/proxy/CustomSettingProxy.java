package com.mitchell.services.business.questionnaireevaluation.proxy;

import com.mitchell.common.exception.MitchellException;

public interface CustomSettingProxy { 
    
     String  getCustomValue(String orgCode, String coCode,
         String groupName, String settingName) throws MitchellException;
      
} 
