package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.core.customsettings.CustomSettingsSrvcXML;
import com.mitchell.services.core.customsettings.ejb.CustomSettingsEJBRemote;
import com.mitchell.services.core.customsettings.types.xml.Group;
import com.mitchell.services.core.customsettings.types.xml.Profile;
import com.mitchell.services.core.customsettings.types.xml.Profiles;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;
import com.mitchell.services.core.customsettings.types.xml.SettingValues;
import com.mitchell.utils.misc.AppUtilities;

public class CustomSettingProxyImpl implements CustomSettingProxy
{
  private CustomSettingsEJBRemote customSettingsEJBRemote;

  private static final String CLASSNAME = CustomSettingProxyImpl.class.getName();
  Logger logger = Logger.getLogger(CLASSNAME);

  public void init()
  {

    try {
      customSettingsEJBRemote = CustomSettingsSrvcXML.getEJB();
    } catch (final Exception ex) {
      logger.error(AppUtilities.getStackTraceString(ex, true));
    }

  }

  public Profile getDefaultProfile(String orgCode, String coCode,
      String workGroup)
      throws RemoteException, MitchellException
  {

    return customSettingsEJBRemote
        .getDefaultProfile(orgCode, coCode, workGroup);
  }

  public com.mitchell.services.core.customsettings.types.xml.SettingValue getValue(
      String orgCode, String coCode, String workGroup, int profileId,
      String groupName, String settingName)
      throws RemoteException, MitchellException
  {
    return customSettingsEJBRemote.getValue(orgCode, coCode, workGroup,
        profileId, groupName, settingName);
  }

  public Profiles getProfiles(String officeID, String coCode, String grpCode)
      throws RemoteException, MitchellException
  {
    return customSettingsEJBRemote.getProfiles(officeID, coCode, grpCode);
  }

  public SettingValues getValuesByGroup(Group group)
      throws RemoteException, MitchellException
  {
    return customSettingsEJBRemote.getValuesByGroup(group);
  }
  
	/**
	 * getCompanyCustomSettings
	 * 
	 * @param coCode
	 * @param groupName
	 * @param settingName 
	 * @throws MitchellException
	 * 
	 */
  /* This API is used to retreive the Company Custom Settings for Collaborative WF*/
  
	public String getCompanyCustomSettings(String coCode, String groupName, String settingName)
    throws MitchellException{

		String retval = null;
	      
        try {    
            // Get the EJB
            CustomSettingsEJBRemote ejb = CustomSettingsSrvcXML.getEJB();
            
            if(logger.isInfoEnabled()){
                logger.info("Calling Custom Settings to get Profile for - CoCode-" + coCode + ",CoCode-" + coCode+",orgType-COMPANY");
            }
        
            // Get the Default Profile
            Profile profile = ejb.getDefaultProfile( coCode, coCode, AppraisalAssignmentConstants.COMPANY_TYPE );
            
            if(profile!=null){
                if(logger.isInfoEnabled()){
                    logger.info("The Default Profile Id for Co Code " + coCode  + " - " + profile.getId());
                    logger.info("Calling Custom Settings to get SettingValue for ProfileId-" + profile.getId() 
                            + ",CoCode-" + coCode + ",CoCode-" + coCode+",orgType-COMPANY, groupName-"+ 
                            groupName+",settingName-" + settingName);                
                }
                
                // Get the Setting
                SettingValue value = ejb.getValue( coCode, coCode, AppraisalAssignmentConstants.COMPANY_TYPE,
                                             profile.getId(),
                                             groupName,
                                             settingName );
            
                if ( value != null && value.getValue() != null && value.getValue().length() > 0 ) {
                    retval = value.getValue();
                }
                
                if(logger.isInfoEnabled()){
                    logger.info("The setting value is-" + retval);                
                }
            }
            else {
                logger.warn("Couldn't get default Profile for  Company Type - CoCode-" + coCode);
            }

        }
        catch (MitchellException me ){
            logger.fatal("Exception while getting Custom Settings for CoCode--" +coCode);
            throw me;
        }
        catch ( Exception e ){
            throw new MitchellException(AppraisalAssignmentConstants.ERROR_UNEXPECTED_EXCEPTION,
                CLASSNAME, "getCompanyCustomSettings",
                "Exception calling CustomSettings EJB. Group=" + groupName +
                " Setting=" + settingName + " CoCode=" + coCode, e );
        }
      
        return retval;
	}
}
