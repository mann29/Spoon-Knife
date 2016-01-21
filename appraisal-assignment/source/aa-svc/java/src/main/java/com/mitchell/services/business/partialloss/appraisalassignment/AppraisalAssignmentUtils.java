package com.mitchell.services.business.partialloss.appraisalassignment;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.NodeType;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.monitor.MonitoringLogger;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.CustomSettingProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.UserInfoProxy;
import com.mitchell.services.core.customsettings.types.xml.Group;
import com.mitchell.services.core.customsettings.types.xml.Profile;
import com.mitchell.services.core.customsettings.types.xml.Profiles;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;
import com.mitchell.services.core.customsettings.types.xml.SettingValues;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.systemconfiguration.SystemConfiguration;

/**
 * This is a utility class for retrieving Custom Settings with the
 * CustomSettings Service.
 */
public final class AppraisalAssignmentUtils implements IAppraisalAssignmentUtils {

	// CONSTANTS
	private static final String CLASS_NAME = AppraisalAssignmentUtils.class
			.getName();
	java.util.logging.Logger mLogger = java.util.logging.Logger
			.getLogger(CLASS_NAME);
	static final String COMPANY = "COMPANY";
	static final String USER = "USER";
	static final String OFFICE = "OFFICE";
	public static final String MAP_CREATE_NAME = "Create";
	public static final String MAP_CLOSE_REQ_NAME = "Close_Req";
	public static final String MAP_REJECT_REQ_NAME = "Reject_Req";

	// Member Variables
	private UserInfoProxy userInfoProxy;
	private CustomSettingProxy customSettingProxy;

	// Setters
	public void setUserInfoProxy(final UserInfoProxy userInfoProxy) {
		this.userInfoProxy = userInfoProxy;
	}

	public void setCustomSettingProxy(
			final CustomSettingProxy customSettingProxy) {
		this.customSettingProxy = customSettingProxy;
	}

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
	public String retrieveCustomSettings(final String orgCode,
			final String coCode, final String groupName,
			final String settingName, String customSettingLevel)
			throws MitchellException {
		final String methodName = "retrieveCustomSettings";
		String retval = null;
		try {
			MonitoringLogger.doLog(CLASS_NAME, methodName,
					"Start Calling customSetting.getDefaultProfile()");
			final com.mitchell.services.core.customsettings.types.xml.Profile profile = customSettingProxy
					.getDefaultProfile(orgCode, coCode, customSettingLevel);
			MonitoringLogger.doLog(CLASS_NAME, methodName,
					"End Calling customSetting.getDefaultProfile()");

			if (profile != null) {
				MonitoringLogger.doLog(CLASS_NAME, methodName,
						"Start Calling customSetting.getValue()");
				final com.mitchell.services.core.customsettings.types.xml.SettingValue value = customSettingProxy
						.getValue(orgCode, coCode, customSettingLevel,
								profile.getId(), groupName, settingName);
				MonitoringLogger.doLog(CLASS_NAME, methodName,
						"End Calling customSetting.getValue()");
				if (value != null && value.getValue() != null
						&& value.getValue().length() > 0) {
					retval = value.getValue();
				}
			} else {
				throw new MitchellException(this.getClass().getName(),
						methodName, "Null default profile returned. Group="
								+ groupName + " Setting=" + settingName
								+ " CoCode=" + coCode + " OrgCode=" + orgCode);
			}
		} catch (final java.rmi.RemoteException e) {
			throw new MitchellException(this.getClass().getName(), methodName,
					"Exception calling CustomSettings EJB. Group=" + groupName
							+ " Setting=" + settingName + " CoCode=" + coCode
							+ " OrgCode=" + orgCode, e);
		} catch (final MitchellException e) {
			e.setDescription(e.getDescription() + " Group=" + groupName
					+ " Setting=" + settingName + " CoCode=" + coCode
					+ " OrgCode=" + orgCode);
			throw e;
		}
		return retval;
	}

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
	public String retrieveCustomSettings(String orgCode, String coCode,
			String groupName, String settingName) throws MitchellException {
		return retrieveCustomSettings(orgCode, coCode, groupName, settingName,
				AppraisalAssignmentConstants.COMPANY_TYPE);
	}

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
	public String getCustomSettingValue(final String coCd, String userId,
			String groupName, String settingName) throws MitchellException {
		final String methodName = "getCustomSettingValue";
		mLogger.entering(CLASS_NAME, methodName);
		String value = null;
		if (userId != null) {
			// get custom setting at user level
			value = retrieveCustomSettings(userId, coCd, groupName,
					settingName, AppraisalAssignmentConstants.USER_TYPE);

		} else {
			// get custom setting at company level
			value = retrieveCustomSettings(coCd, coCd, groupName, settingName);
		}

		mLogger.exiting(CLASS_NAME, methodName);
		return value;
	}

	/**
	 * 
	 * 
	 * This Method will get the expertise based on the Vehicle Type supported by
	 * BMS
	 * 
	 */
	public String getExpertiseSkillsByVehicleType(String vehicleType)
			throws MitchellException {
		// Put This in Appraisal Assignment SET file
		String tempStr = "Auto"; // Default Value

		String tzMappingStr = SystemConfiguration
				.getSettingValue(AppraisalAssignmentConstants.VehicleTypeExpertiseSkillsList);
		if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
			mLogger.info("Mapping String from SET File::::" + tzMappingStr);
		}
		try {
			String[] tzMappingList = tzMappingStr.split(";");

			for (String tz : tzMappingList) {

				String[] tzMapping1 = tz.split(":");
				String tzMapping = tzMapping1[0];
				if (tzMapping.contains(vehicleType)) {
					tempStr = tzMapping1[1];
				}

			}
		} catch (Exception e) {
			throw new MitchellException(CLASS_NAME,
					"getExpertiseSkillsByVehicleType",
					"Error in the mapping for expertise by vehicle type: "
							+ vehicleType);
		}

		return tempStr;
	}

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
	public java.util.HashMap retrieveCarrierSettings(final String coCode)
			throws MitchellException {
		final String methodName = "retrieveCarrierSettings";
		final java.util.HashMap map = new java.util.HashMap();
		mLogger.entering(CLASS_NAME, methodName);
		MonitoringLogger
				.doLog(CLASS_NAME,
						methodName,
						"Start Calling AppraisalAssignmentUtils.retrieveCarrierSettings for company code:"
								+ coCode);
		try {

			MonitoringLogger
					.doLog(CLASS_NAME, methodName,
							"Start Calling AppraisalAssignmentUtils.getSettingsByGroupAndProfile");
			final SettingValue[] valueArray = getSettingsByGroupAndProfile(
					coCode, coCode, COMPANY,
					AppraisalAssignmentConstants.CSET_GROUP_NAME);
			MonitoringLogger
					.doLog(CLASS_NAME, methodName,
							"End Calling AppraisalAssignmentUtils.getSettingsByGroupAndProfile");
			for (int i = 0; i < valueArray.length; i++) {
				if (valueArray[i] == null
						|| null == valueArray[i].getPropertyName()) {
					continue;
				}

				if (valueArray[i]
						.getPropertyName()
						.toUpperCase()
						.equals(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_UPDATED_TASK_RECEIVER_EVENT_CODE_ID)) {
					if (valueArray[i].getValue() != null) {
						map.put("Update", valueArray[i].getValue());
					}
					if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
						final StringBuffer msg = new StringBuffer();
						msg.append("Array counter: ")
								.append(i)
								.append(" ,CSET_SETTING_REQUEST_UPDATED_TASK_RECEIVER_EVENT_CODE_ID = ")
								.append(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_UPDATED_TASK_RECEIVER_EVENT_CODE_ID);
						mLogger.info(msg.toString());
					}
				}
				// Retrieve Event Code - REASSIGNED
				else if (valueArray[i]
						.getPropertyName()
						.toUpperCase()
						.equals(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_REASSIGNED_TASK_RECEIVER_EVENT_CODE_ID)) {
					if (valueArray[i].getValue() != null) {
						map.put("Reassign", valueArray[i].getValue());
					}
					if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
						final StringBuffer msg = new StringBuffer();
						msg.append("Array counter: ")
								.append(i)
								.append(" ,CSET_SETTING_REQUEST_REASSIGNED_TASK_RECEIVER_EVENT_CODE_ID = ")
								.append(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_REASSIGNED_TASK_RECEIVER_EVENT_CODE_ID);
						mLogger.info(msg.toString());
					}
				}
				// Retrieve Event Code - CANCELLED
				else if (valueArray[i]
						.getPropertyName()
						.toUpperCase()
						.equals(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_CANCELLED_TASK_RECEIVER_EVENT_CODE_ID)) {
					if (valueArray[i].getValue() != null) {
						map.put("Cancel", valueArray[i].getValue());
					}
					if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
						final StringBuffer msg = new StringBuffer();
						msg.append("Array counter: ")
								.append(i)
								.append(" ,CSET_SETTING_REQUEST_CANCELLED_TASK_RECEIVER_EVENT_CODE_ID = ")
								.append(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_CANCELLED_TASK_RECEIVER_EVENT_CODE_ID);
						mLogger.info(msg.toString());
					}
				}
				// Retrieve Event Code - COMPLETED
				else if (valueArray[i]
						.getPropertyName()
						.toUpperCase()
						.equals(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_COMPLETED_TASK_RECEIVER_EVENT_CODE_ID)) {
					if (valueArray[i].getValue() != null) {
						map.put("Complete", valueArray[i].getValue());
					}
					if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
						final StringBuffer msg = new StringBuffer();
						msg.append("Array counter: ")
								.append(i)
								.append(" ,CSET_SETTING_REQUEST_COMPLETED_TASK_RECEIVER_EVENT_CODE_ID = ")
								.append(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_COMPLETED_TASK_RECEIVER_EVENT_CODE_ID);
						mLogger.info(msg.toString());
					}
				} else if (valueArray[i]
						.getPropertyName()
						.toUpperCase()
						.equals(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_WORKGROUP_ID_CODE_ID)) {
					if (valueArray[i].getValue() != null) {
						map.put("workgroup_id", valueArray[i].getValue());
					}
					if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
						final StringBuffer msg = new StringBuffer();
						msg.append("Array counter: ")
								.append(i)
								.append(" ,CSET_SETTING_REQUEST_OVERDUE_TASK_RECEIVER_EVENT_CODE_ID = ")
								.append(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_WORKGROUP_ID_CODE_ID);
						mLogger.info(msg.toString());
					}
				}
				// Retrieve Event Code - DISPATCHED TASK
				else if (valueArray[i]
						.getPropertyName()
						.toUpperCase()
						.equals(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_DISPATCHED_TASK_RECEIVER_EVENT_CODE_ID)) {
					if (valueArray[i].getValue() != null) {
						map.put("Dispatch", valueArray[i].getValue());
					}
					if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
						final StringBuffer msg = new StringBuffer();
						msg.append("Array counter: ")
								.append(i)
								.append(" ,CSET_SETTING_REQUEST_DISPATCHED_TASK_RECEIVER_EVENT_CODE_ID = ")
								.append(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_DISPATCHED_TASK_RECEIVER_EVENT_CODE_ID);
						mLogger.info(msg.toString());
					}
				}
				// Create Event Code - Created TASK
				else if (valueArray[i]
						.getPropertyName()
						.toUpperCase()
						.equals(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_CREATED_TASK_RECEIVER_EVENT_CODE_ID)) {
					if (valueArray[i].getValue() != null) {
						map.put(MAP_CREATE_NAME, valueArray[i].getValue());
					}
					if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
						final StringBuffer msg = new StringBuffer();
						msg.append("Array counter: ")
								.append(i)
								.append(" ,CSET_SETTING_REQUEST_CREATED_TASK_RECEIVER_EVENT_CODE_ID = ")
								.append(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_CREATED_TASK_RECEIVER_EVENT_CODE_ID);
						mLogger.info(msg.toString());
					}
				}
				// Close request Event Code - Close request TASK
				else if (valueArray[i]
						.getPropertyName()
						.toUpperCase()
						.equals(AppraisalAssignmentConstants.CSET_SETTING_REQ_CLOSED_EVENT_ID)) {
					if (valueArray[i].getValue() != null) {
						map.put(MAP_CLOSE_REQ_NAME, valueArray[i].getValue());
					}
					if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
						final StringBuffer msg = new StringBuffer();
						msg.append("Array counter: ")
								.append(i)
								.append(" ,CSET_SETTING_REQ_CLOSED_EVENT_ID = ")
								.append(AppraisalAssignmentConstants.CSET_SETTING_REQ_CLOSED_EVENT_ID);
						mLogger.info(msg.toString());
					}
				}
				// Reject request Event Code - Reject request TASK
				else if (valueArray[i]
						.getPropertyName()
						.toUpperCase()
						.equals(AppraisalAssignmentConstants.CSET_SETTING_REQ_REJECTED_EVENT_ID)) {
					if (valueArray[i].getValue() != null) {
						map.put(MAP_REJECT_REQ_NAME, valueArray[i].getValue());
					}
					if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
						final StringBuffer msg = new StringBuffer();
						msg.append("Array counter: ")
								.append(i)
								.append(" ,CSET_SETTING_REQ_REJECTED_EVENT_ID = ")
								.append(AppraisalAssignmentConstants.CSET_SETTING_REQ_REJECTED_EVENT_ID);
						mLogger.info(msg.toString());
					}
				}

			}
		} catch (final java.lang.NumberFormatException numberFormatException) {
			throw new MitchellException(
					CLASS_NAME,
					"retrieveCarrierSettings",
					"Unable to retrieve settings for carrrier (NumberFormatException).",
					numberFormatException);
		} catch (final Exception exception) {
			throw new MitchellException(CLASS_NAME, "retrieveCarrierSettings",
					"Unable to retrieve settings for carrrier.", exception);
		}

		MonitoringLogger
				.doLog(CLASS_NAME,
						methodName,
						"End Calling AppraisalAssignmentUtils.retrieveCarrierSettings for company code:"
								+ coCode);
		mLogger.exiting(CLASS_NAME, methodName);
		return map;
	}

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
	public SettingValue[] getSettingsByGroupAndProfile(final String coCode,
			final String orgCode, final String orgType, final String groupName)
			throws Exception {
		final String methodName = "getSettingsByGroupAndProfile";
		mLogger.entering(CLASS_NAME, methodName);

		SettingValue[] valueArray = null;

		// get default profile
		MonitoringLogger.doLog(CLASS_NAME, methodName,
				"Start Calling customSettingProxy.getDefaultProfile for orgCode :"
						+ orgCode);
		final Profile profile = customSettingProxy.getDefaultProfile(orgCode,
				coCode, orgType);
		MonitoringLogger.doLog(CLASS_NAME, methodName,
				"End Calling customSettingProxy.getDefaultProfile for orgCode :"
						+ orgCode);

		if (profile != null) {
			// construct the Group Object - to get the settigs for the group
			final Group group = Group.Factory.newInstance();

			// set know values
			group.setName(groupName);
			group.setCoCd(coCode);
			group.setOrgCd(orgCode);
			group.setOrgTypeName(orgType);
			group.setProfileName(profile.getName());

			MonitoringLogger.doLog(CLASS_NAME, methodName,
					"Start Calling customSettingProxy.getValuesByGroup");
			// get setting values
			final SettingValues settingValues = customSettingProxy
					.getValuesByGroup(group);
			MonitoringLogger.doLog(CLASS_NAME, methodName,
					"End Calling customSettingProxy.getValuesByGroup");
			if (settingValues != null) {
				valueArray = settingValues.getSettingValueArray();
			} else {
				throw new MitchellException(CLASS_NAME,
						"getSettingsByGroupAndProfile",
						"No custom setting values found for company: " + coCode);
			}

		} else {
			throw new MitchellException(CLASS_NAME,
					"getSettingsByGroupAndProfile",
					"No custom setting profile found for company: " + coCode);
		}

		if (valueArray.length == 0) {
			throw new MitchellException(CLASS_NAME,
					"getSettingsByGroupAndProfile",
					"No custom setting values found for company: " + coCode);
		}

		mLogger.exiting(CLASS_NAME, methodName);
		return valueArray;
	}

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
	public String retrieveUserDispatchCenter(final String coCode,
			final String userID) throws MitchellException {
		final String methodName = "retrieveUserDispatchCenter";
		mLogger.entering(CLASS_NAME, methodName);

		String valueToReturn = "";
		SettingValue[] arrSettingValues = null;
		if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
			mLogger.info("Getting CustomSettingsEJBRemote");
		}
		try {

			arrSettingValues = this.getSettingsByGroupAndProfile(coCode,
					userID, USER, AppraisalAssignmentConstants.CSET_GROUP_NAME);

			if (arrSettingValues != null && arrSettingValues.length > 0) {
				for (int i = 0; i < arrSettingValues.length; i++) {
					if ("WORKGROUP_ID".equalsIgnoreCase(arrSettingValues[i]
							.getPropertyName())) {
						valueToReturn = arrSettingValues[i].getValue();
					}
				}
			}
			if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
				mLogger.info("valueToReturn" + valueToReturn);
			}
			if (valueToReturn == null || valueToReturn.trim().length() == 0) {
				String officeID = "";

				final UserInfoDocument userInfoDocument = userInfoProxy
						.getUserInfo(coCode, userID, "");

				if (userInfoDocument != null
						&& userInfoDocument.getUserInfo() != null
						&& userInfoDocument.getUserInfo().getUserHier() != null) {
					NodeType nt = userInfoDocument.getUserInfo().getUserHier()
							.getHierNode();
					while (nt != null) {
						if (OFFICE.equalsIgnoreCase(nt.getLevel())) {
							officeID = nt.getCode();
							break;
						} else {
							nt = nt.getHierNode();
						}
					}
				}

				if (officeID != null && officeID.trim().length() != 0) {
					final Profiles profiles = customSettingProxy.getProfiles(
							officeID, coCode, OFFICE);
					if (profiles != null
							&& profiles.getProfileArray().length > 0) {
						final int profileID = profiles.getProfileArray(0)
								.getId();
						if (profileID > 0) {
							final SettingValue sv = customSettingProxy
									.getValue(
											coCode,
											coCode,
											USER,
											profileID,
											AppraisalAssignmentConstants.CSET_GROUP_NAME,
											AppraisalAssignmentConstants.CSET_SETTING_REQUEST_WORKGROUP_ID_CODE_ID);
							valueToReturn = sv.getValue();
						}
					}
				}
			}
		} catch (final Exception exception) {
			throw new MitchellException(CLASS_NAME, methodName,
					"Unable to retrieve Dispatch Center for UserID : " + userID
							+ " and OrgCode/CoCode: " + coCode, exception);
		}
		if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
			mLogger.info("Final return valueToReturn" + valueToReturn);
		}

		mLogger.exiting(CLASS_NAME, methodName);
		return valueToReturn;

	}

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
	public UserInfoDocument retrieveUserInfo(final String companyCode,
			final String userId) throws Exception {
		final String methodName = "retrieveUserInfo";
		mLogger.entering(CLASS_NAME, methodName);

		final UserInfoDocument userInfoDoc = userInfoProxy.getUserInfo(
				companyCode, userId, "");

		mLogger.exiting(CLASS_NAME, methodName);
		return userInfoDoc;
	}

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
	public UserDetailDocument retrieveUserDetail(final String companyCode,
			final String userId) throws Exception {
		final String methodName = "retrieveUserDetail";
		mLogger.entering(CLASS_NAME, methodName);

		final UserDetailDocument userDetailDoc = userInfoProxy.getUserDetails(
				companyCode, userId);

		mLogger.exiting(CLASS_NAME, methodName);

		return userDetailDoc;
	}

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
	public String getConfigScheduleInd(final String orgCode,
			final String coCode, final String groupName,
			final String settingName) throws MitchellException {
		String retval = null;
		try {
			final com.mitchell.services.core.customsettings.types.xml.Profile profile = customSettingProxy
					.getDefaultProfile(orgCode, coCode, "COMPANY");
			if (profile != null) {
				final com.mitchell.services.core.customsettings.types.xml.SettingValue value = customSettingProxy
						.getValue(orgCode, coCode, "COMPANY", profile.getId(),
								groupName, settingName);
				if (value != null && value.getValue() != null
						&& value.getValue().length() > 0) {
					retval = value.getValue();
				}
			} else {
				throw new MitchellException(this.getClass().getName(),
						"getUserCustomSetting",
						"Null default profile returned. Group=" + groupName
								+ " Setting=" + settingName + " CoCode="
								+ coCode + " OrgCode=" + orgCode);
			}
		} catch (final java.rmi.RemoteException e) {
			throw new MitchellException(this.getClass().getName(),
					"getUserCustomSetting",
					"Exception calling CustomSettings EJB. Group=" + groupName
							+ " Setting=" + settingName + " CoCode=" + coCode
							+ " OrgCode=" + orgCode, e);
		} catch (final MitchellException e) {
			e.setDescription(e.getDescription() + " Group=" + groupName
					+ " Setting=" + settingName + " CoCode=" + coCode
					+ " OrgCode=" + orgCode);
			throw e;
		}
		return retval;
	}

}
