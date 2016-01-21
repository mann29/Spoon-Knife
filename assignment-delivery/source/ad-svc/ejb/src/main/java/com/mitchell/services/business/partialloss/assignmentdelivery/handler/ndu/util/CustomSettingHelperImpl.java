package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util;

import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.core.customsettings.CustomSettingsSrvcXML;
import com.mitchell.services.core.customsettings.ejb.CustomSettingsEJBRemote;
import com.mitchell.services.core.customsettings.types.xml.Profile;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;
import com.mitchell.utils.misc.AppUtilities;

public class CustomSettingHelperImpl implements CustomSettingHelper {
	
	private static String CLASS_NAME = CustomSettingHelperImpl.class.getName();
	private static Logger logger = Logger.getLogger(CLASS_NAME);
	private CustomSettingsEJBRemote ejb = null;
	
	public void initialize() throws MitchellException {
		try {
			if (ejb == null) {
				ejb = CustomSettingsSrvcXML.getEJB();
			}
		} catch (MitchellException e) {
			final String description = new StringBuilder(
					"Exception in initialize method of CustomSettingHelperImpl, with trace:")
					.append(AppUtilities.getStackTraceString(e, true))
					.toString();

			logger.severe(description);

			throw new MitchellException(CLASS_NAME, "initialize",
					description, e);
		}
	}

	public String getCompanyCustomSetting(final String coCode,
			final String groupName, final String settingName)
			throws MitchellException {

		String retval = null;

		try {
			if(ejb != null){
				Profile profile = ejb.getDefaultProfile(coCode, coCode,
						AssignmentDeliveryConstants.COMPANY_TYPE);
	
				if (profile != null) {
					SettingValue value = ejb.getValue(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE,
							profile.getId(), groupName, settingName);
					if (value != null && value.getValue() != null
							&& value.getValue().length() > 0) {
						retval = value.getValue();
					}
				} else {
					final String desc = new StringBuilder(
							"Null default profile returned. Group=")
							.append(groupName).append(" Setting=")
							.append(settingName).append(" CoCode=").append(coCode)
							.toString();
	
					logger.severe(desc);
					throw new MitchellException(CLASS_NAME,
							"getCompanyCustomSetting", desc);
	
				}
			}
		} catch (Exception e) {
			final String errorDesc = new StringBuilder(
					"Exception in CustomSetting co:").append(coCode)
					.append(",with trace:")
					.append(AppUtilities.getStackTraceString(e, true))
					.toString();

			logger.severe(errorDesc);
			throw new MitchellException(CLASS_NAME, "getCompanyCustomSetting",
					errorDesc, e);
		}
		return retval;
	}
	 /**
     * To get custom setting value at user level
     * @param orgCode-used to get the profile and then value
     * @param coCode-used to get the profile and then value
     * @param groupName-used to get custom setting value for this grp name
     * @param settingName-used to get custom setting value for this setting name
     * @return custom setting value
     * @throws MitchellException
     */
	public String getUserCustomSetting(String orgCode, String coCode,
            String groupName, String settingName) throws MitchellException {
        final String methodName = "getUserCustomSetting";
		String settingValue = null;
        
        try {
           if(ejb != null){
	            final Profile profile = ejb
	            .getDefaultProfile(orgCode, coCode, AssignmentDeliveryConstants.USER_TYPE);
	
	            if (profile != null) {
	
	                final SettingValue settingValueObj
	                = ejb.getValue(
	                        orgCode, coCode, AssignmentDeliveryConstants.USER_TYPE,
	                        profile.getId(), groupName,
	                        settingName);
	
	                if (settingValueObj != null
	                        && settingValueObj.getValue() != null
	                        && settingValueObj.getValue().trim().length() > 0) {
	
	                    settingValue = settingValueObj.getValue();
	
	                }
	
	            }else {
					final String desc = new StringBuilder(
							"Null default profile returned. Group=")
							.append(groupName).append(" Setting=")
							.append(settingName).append(" CoCode=").append(coCode)
							.toString();
	
					logger.severe(desc);
					throw new MitchellException(CLASS_NAME,
							methodName, desc);
			}
           }
        } catch(MitchellException me){
        	throw me;
        }catch (Exception e) {
             final String errorDesc = new StringBuilder(
					"RemoteException in CustomSetting co/user").append(coCode).append("/").append(orgCode)
		            .toString();

			logger.severe(errorDesc);
			throw new MitchellException(CLASS_NAME, methodName,
					errorDesc, e);
        }

        return settingValue;
    }
	
	

	public void setEjb(CustomSettingsEJBRemote ejb) {
		this.ejb = ejb;
	}
}