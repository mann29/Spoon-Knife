package com.mitchell.services.business.questionnaireevaluation.proxy;

import org.apache.log4j.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.customsettings.CustomSettingsSrvcXML;
import com.mitchell.utils.misc.AppUtilities;

public class CustomSettingProxyImpl implements CustomSettingProxy {

   private static final String CLASS_NAME = CustomSettingProxyImpl.class
         .getName();
   private Logger logger = Logger.getLogger(CLASS_NAME);

   public String getCustomValue(String orgCode, String coCode,
         String groupName, String settingName) throws MitchellException {

      String settingValue = null;

      try {
         final com.mitchell.services.core.customsettings.types.xml.Profile profile = CustomSettingsSrvcXML
               .getEJB().getDefaultProfile(orgCode, coCode, "COMPANY");

         if (profile != null) {
            final com.mitchell.services.core.customsettings.types.xml.SettingValue value = CustomSettingsSrvcXML
                  .getEJB().getValue(orgCode, coCode, "COMPANY",
                        profile.getId(), groupName, settingName);
            if (value != null && value.getValue() != null
                  && value.getValue().length() > 0) {
               settingValue = value.getValue();
            }
         }

      } catch (MitchellException mE) {
         final String desc = "Exception from calling getValue: " + mE;
         logger.fatal(desc + "Got Exception:"
               + AppUtilities.getStackTraceString(mE));
      
      }catch (Exception ex) {
         final String desc = "Other Exception from CustomSettingProxyImpl: "
               + ex;
         logger.fatal(desc + AppUtilities.getStackTraceString(ex));
      
      }

      return settingValue;

   }

   

}