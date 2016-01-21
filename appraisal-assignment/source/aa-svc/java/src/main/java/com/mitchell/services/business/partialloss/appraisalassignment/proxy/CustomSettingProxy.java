package com.mitchell.services.business.partialloss.appraisalassignment.proxy; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.customsettings.types.xml.Group;
import com.mitchell.services.core.customsettings.types.xml.Profile;
import com.mitchell.services.core.customsettings.types.xml.Profiles;
import com.mitchell.services.core.customsettings.types.xml.SettingValues;
import java.rmi.RemoteException;

public interface CustomSettingProxy 
{ 
    
    public Profile getDefaultProfile(String orgCode,String coCode, String workGroup)throws  RemoteException, MitchellException ;
    
    public com.mitchell.services.core.customsettings.types.xml.SettingValue getValue(String orgCode, String coCode,String workGroup , int profileId, String groupName, String settingName)throws  RemoteException, MitchellException ;
    
    public SettingValues getValuesByGroup(Group group)throws  RemoteException, MitchellException ;
    
    public Profiles getProfiles(String officeID, String coCode, String grpCode)throws  RemoteException, MitchellException ;
    
    public String getCompanyCustomSettings(String coCode, String groupName, String settingName)
    throws MitchellException;
    
} 
