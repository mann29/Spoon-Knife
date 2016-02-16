package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.customsettings.CustomSettingsSrvcXML;
import com.mitchell.services.core.customsettings.ejb.CustomSettingsEJBRemote;
import com.mitchell.services.core.customsettings.types.xml.Profile;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.CommonUtil;
import com.mitchell.utils.misc.AppUtilities;

/**
 * The Class CustomSettingProxyImpl.
 */
public class CustomSettingProxyImpl implements CustomSettingProxy
{

  /**
   * class name.
   */
  private static final String CLASS_NAME = CustomSettingProxyImpl.class
      .getName();
  /**
   * logger instance.
   */
  private static Logger logger = Logger.getLogger(CLASS_NAME);

  /**
   * The Constant USER_TYPE.
   */
  public static final String USER_TYPE = "USER";

  public static final String COMPANY_TYPE = "COMPANY";

  private SystemConfigurationProxy systemConfigurationProxy;

  private CommonUtil commonUtil;

  private APDCommonUtilProxy apdCommonUtilProxy;

  /**
   * Gets the user custom setting.
   * 
   * @param orgCode
   *          the org code
   * @param coCode
   *          the co code
   * @param groupName
   *          the group name
   * @param settingName
   *          the setting name
   * @return the user custom setting
   * @throws MitchellException
   *           the mitchell exception
   */
  public String getUserCustomSetting(final String orgCode, final String coCode,
      final String groupName, final String settingName)
      throws MitchellException
  {

    String methodName = "getUserCustomSetting";
    logger.entering(CLASS_NAME, methodName);

    String settingValue = null;
    // Fix 97154 : remove try-catch for RemoteException.
    // try {
    CustomSettingsEJBRemote customEjbRemote = CustomSettingsSrvcXML.getEJB();
    Profile profile = customEjbRemote.getDefaultProfile(orgCode, coCode,
        USER_TYPE);

    if (profile != null) {

      SettingValue settingValueObj = customEjbRemote.getValue(orgCode, coCode,
          USER_TYPE, profile.getId(), groupName, settingName);

      if (settingValueObj != null && settingValueObj.getValue() != null
          && settingValueObj.getValue().trim().length() > 0) {

        settingValue = settingValueObj.getValue();

      }

    } else {

      final String desc = new StringBuffer(
          "Null default profile returned. Group=").append(groupName)
          .append(" Setting=").append(settingName).append(" CoCode=")
          .append(coCode).append(" OrgCode=").append(orgCode).toString();

      apdCommonUtilProxy.logSEVEREMessage(desc);
      throw new MitchellException(APDDeliveryConstants.ERROR_CUSTOM_SETTING,
          CLASS_NAME, "getUserCustomSetting", desc);
    }

    logger.exiting(CLASS_NAME, methodName);
    return settingValue;
  }

  /**
   * This method gets company custom settings.
   * 
   * @param coCode
   * @param groupName
   * @param settingName
   * @return String
   * @throws MitchellException
   *           Mitchell Exception
   */
  public String getCompanyCustomSetting(final String coCode,
      final String groupName, final String settingName)
      throws MitchellException
  {

    String methodName = "getCompanyCustomSetting";
    logger.entering(CLASS_NAME, methodName);

    String retval = null;

    CustomSettingsEJBRemote ejb = CustomSettingsSrvcXML.getEJB();

    try {
      Profile profile = ejb.getDefaultProfile(coCode, coCode, COMPANY_TYPE);

      if (profile != null) {
        SettingValue value = ejb.getValue(coCode, coCode, COMPANY_TYPE,
            profile.getId(), groupName, settingName);
        if (value != null && value.getValue() != null
            && value.getValue().length() > 0) {
          retval = value.getValue();
        }
      } else {
        final String desc = new StringBuffer(
            "Null default profile returned. Group=").append(groupName)
            .append(" Setting=").append(settingName).append(" CoCode=")
            .append(coCode).toString();

        apdCommonUtilProxy.logSEVEREMessage(desc);
        throw new MitchellException(APDDeliveryConstants.ERROR_CUSTOM_SETTING,
            CLASS_NAME, "getCompanyCustomSetting", desc);

      }
    } catch (Exception e) {
      final String desc = new StringBuffer("Exception in CustomSetting co:")
          .append(coCode).append(",with trace:")
          .append(AppUtilities.getStackTraceString(e, true)).toString();

      apdCommonUtilProxy.logSEVEREMessage(desc);

      throw new MitchellException(APDDeliveryConstants.ERROR_CUSTOM_SETTING,
          CLASS_NAME, "getCompanyCustomSetting", desc, e);

    }
    logger.exiting(CLASS_NAME, methodName);
    return retval;
  }

  /**
   * This method returns customs setting for NICB Report Delivery Handler.
   * 
   * @param companyCode
   *          String
   * @throws Exception
   *           Exception
   */
  public String getNICBReportDeliveryEventSetting(String companyCode)
      throws Exception
  {

    String methodName = "getNICBReportDeliveryEventSetting";
    logger.entering(CLASS_NAME, methodName);

    String nicbReportDeliverySettingValue = null;

    if (companyCode != null && companyCode.trim().length() > 0) {
      String companyOrgType = systemConfigurationProxy
          .getAPDDeliveryServiceCompanyOrgType();

      String companySettingGroupName = systemConfigurationProxy
          .getNICBServiceCompanyGroupName();

      CustomSettingsEJBRemote ejb = CustomSettingsSrvcXML.getEJB();
      Profile defaultProfile = ejb.getDefaultProfile(companyCode, companyCode,
          companyOrgType);
      if (defaultProfile == null) {
        throw new Exception("Default Profile does not exist for company:"
            + companyCode);
      }

      SettingValue settingValue = ejb.getValue(companyCode, companyCode,
          companyOrgType, defaultProfile.getName(), companySettingGroupName,
          systemConfigurationProxy.getNICBReportDeliveryHandler());

      if (settingValue != null) {
        String value = settingValue.getValue();
        // Fix 117663.
        if (!commonUtil.isNullOrEmpty(value)) {
          if (Integer.parseInt(value) > 0) {
            nicbReportDeliverySettingValue = value;
          }
        }
      }
    }

    logger.exiting(CLASS_NAME, methodName);
    return nicbReportDeliverySettingValue;
  }

  /**
   * @return the systemConfigurationProxy
   */
  public SystemConfigurationProxy getSystemConfigurationProxy()
  {
    return systemConfigurationProxy;
  }

  /**
   * @param systemConfigurationProxy
   *          the systemConfigurationProxy to set
   */
  public void setSystemConfigurationProxy(
      SystemConfigurationProxy systemConfigurationProxy)
  {
    this.systemConfigurationProxy = systemConfigurationProxy;
  }

  /**
   * @return the commonUtil
   */
  public CommonUtil getCommonUtil()
  {
    return commonUtil;
  }

  /**
   * @param commonUtil
   *          the commonUtil to set
   */
  public void setCommonUtil(CommonUtil commonUtil)
  {
    this.commonUtil = commonUtil;
  }

  /**
   * @return the apdCommonUtilProxy
   */
  public APDCommonUtilProxy getApdCommonUtilProxy()
  {
    return apdCommonUtilProxy;
  }

  /**
   * @param apdCommonUtilProxy the apdCommonUtilProxy to set
   */
  public void setApdCommonUtilProxy(APDCommonUtilProxy apdCommonUtilProxy)
  {
    this.apdCommonUtilProxy = apdCommonUtilProxy;
  }

}
