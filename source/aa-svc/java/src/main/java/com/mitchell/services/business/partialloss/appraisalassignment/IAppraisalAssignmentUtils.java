package com.mitchell.services.business.partialloss.appraisalassignment;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.CustomSettingProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.UserInfoProxy;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;

public interface IAppraisalAssignmentUtils {

	// Setters
	public abstract void setUserInfoProxy(UserInfoProxy userInfoProxy);

	public abstract void setCustomSettingProxy(
			CustomSettingProxy customSettingProxy);

	/**
	 * This utility method retrieves User's/Carrier's Custom Settings based on
	 * user ID/co code
	 * 
	 * @param orgCode
	 *            Org Code
	 * @param coCode
	 *            Mitchell Company Code for the carrier.
	 * @param groupName
	 *            Group Name
	 * @param settingName
	 *            Custom Setting Name
	 * @return Returns Carrier Setting value.
	 * @throws MitchellException
	 *             Throws an Exception in case of unable to retrieve carrier
	 *             custom settings.
	 */
	public abstract String retrieveCustomSettings(String orgCode,
			String coCode, String groupName, String settingName,
			String customSettingLevel) throws MitchellException;

	/**
	 * This method will call an overloaded method with an extra parameter Custom
	 * setting level: by default COMPANY type
	 * 
	 * @param orgCode
	 *            -used to get the profile and then value
	 * @param coCode
	 *            -used to get the profile and then value
	 * @param groupName
	 *            -used to get custom setting value for this grp name
	 * @param settingName
	 *            -used to get custom setting value for this setting name
	 * @return custom setting value
	 * @throws MitchellException
	 */
	public abstract String retrieveCustomSettings(String orgCode,
			String coCode, String groupName, String settingName)
			throws MitchellException;

	/**
	 * This method is to get custom setting value at user level first if null
	 * found get setting value at company level
	 * 
	 * @param coCd
	 *            to get user/comp level custom setting
	 * @param userInfo
	 *            to get user level custom setting
	 * @param groupName
	 *            to get user/comp level custom setting
	 * @param settingName
	 *            to get user/comp level custom setting
	 * @throws MitchellException
	 * @return custom setting value
	 * @throws ClaimException
	 */
	public abstract String getCustomSettingValue(String coCd, String userId,
			String groupName, String settingName) throws MitchellException;

	/**
	 * 
	 * 
	 * This Method will get the expertise based on the Vehicle Type supported by
	 * BMS
	 * 
	 */
	public abstract String getExpertiseSkillsByVehicleType(String vehicleType)
			throws MitchellException;

	/**
	 * This utility method retrieves Carrier's Custom Settings based on Company
	 * Code.
	 * 
	 * @param coCode
	 *            Mitchell Company Code for the carrier.
	 * @return Returns Map of Carrier Settings.
	 * @throws MitchellException
	 *             Throws an Exception in case of unable to retrieve carrier
	 *             custom settings.
	 */
	public abstract java.util.HashMap retrieveCarrierSettings(String coCode)
			throws MitchellException;

	// R.Bird 07.21.10 - Changed to public for access by
	// AppraisalAssignmentMandFieldUtils
	// private SettingValue[] getSettingsByGroupAndProfile(String coCode,
	//
	/**
	 * 
	 * @param coCode
	 *            Company Code
	 * @param orgCode
	 *            Organization Code
	 * @param orgType
	 *            Organization Type
	 * @param groupName
	 *            Group Name
	 * @return Array of ettingValue
	 * @throws Exception
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public abstract SettingValue[] getSettingsByGroupAndProfile(String coCode,
			String orgCode, String orgType, String groupName) throws Exception;

	/**
	 * 
	 * @param coCode
	 *            Company Code
	 * @param userID
	 *            User Id
	 * @return Dispatch center as String
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public abstract String retrieveUserDispatchCenter(String coCode,
			String userID) throws MitchellException;

	/**
	 * This method retrieves a UserInfoDocument for the given CompanyCode and
	 * UserId.
	 * 
	 * @param companyCode
	 *            Company Code for the UserDetailDocument required.
	 * @param userId
	 *            UserID for the UserDetailDocument required.
	 * 
	 * @return Returns UserInfoDocument
	 * 
	 * @throws Exception
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public abstract UserInfoDocument retrieveUserInfo(String companyCode,
			String userId) throws Exception;

	/**
	 * This method retrieves a UserDetailDocument for the given CompanyCode and
	 * UserId.
	 * 
	 * @param companyCode
	 *            Company Code for the UserDetailDocument required.
	 * @param userId
	 *            UserID for the UserDetailDocument required.
	 * 
	 * @return Returns UserDetailDocument
	 * 
	 * @throws Exception
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public abstract UserDetailDocument retrieveUserDetail(String companyCode,
			String userId) throws Exception;

	/**
	 * This utility method retrieves Carrier's Custom Settings based on Company
	 * Code.
	 * 
	 * @param orgCode
	 *            Org Code
	 * @param coCode
	 *            Mitchell Company Code for the carrier.
	 * @param groupName
	 *            Group Name
	 * @param settingName
	 *            Custom Setting Name
	 * @return Returns Map of Carrier Settings.
	 * @throws MitchellException
	 *             Throws an Exception in case of unable to retrieve carrier
	 *             custom settings.
	 */
	public abstract String getConfigScheduleInd(String orgCode, String coCode,
			String groupName, String settingName) throws MitchellException;

}