package com.mitchell.services.business.partialloss.appraisalassignment;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.SystemConfigProxy;
import com.mitchell.systemconfiguration.SystemConfiguration;

/**
 * Class to read AAS SET file
 * 
 */
public final class AppraisalAssignmentConfig implements IAppraisalAssignmentConfig {

	private static final String SET_WORK_AREA_DIR = "/APASHEL/Filesystem/WorkAreaDir";

	private static final String createAAActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/CreateAAActivityLog";
	private static final String createSupplementAAActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/CreateSupplementAAActivityLog";
	private static final String cancelAAActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/CancelAAActivityLog";
	private static final String unCancelAAActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/UnCancelAAActivityLog";
	private static final String dispatchAAActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/DispatchAAActivityLog";
	private static final String completeAAActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/CompleteAAActivityLog";
	private static final String updateAAActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/UpdateAAActivityLog";
	private static final String assignAAActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/AssignAAActivityLog";
	private static final String reAssignAAActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/ReAssignAAActivityLog";
	private static final String addressValidateAAActivityLogPassed = "/AppraisalAssignment/ExposureActivityLogMessages/AddressValidateAAActivityLogSuccess";
	private static final String addressValidateAAActivityLogFailed = "/AppraisalAssignment/ExposureActivityLogMessages/AddressValidateAAActivityLogFailure";
	private static final String inProgressAAActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/InProgressAAActivityLog";
	private static final String rejectedAAActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/RejectedAAActivityLog";

	private static final String unScheduleAAActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/UnScheduleAAActivityLog";
	// /////// MAXIMA ASSIGNMENT ACTIVITY LOGGING /////////
	private static final String createAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/CreateAAAssignmentActivityLogDesc";
	private static final String createSupplementAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/CreateSupplementAAAssignmentActivityLogDesc";
	private static final String cancelAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/CancelAAAssignmentActivityLogDesc";
	private static final String unCancelAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/UnCancelAAAssignmentActivityLogDesc";
	private static final String unScheduleAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/UnScheduleAAAssignmentActivityLogDesc";
	private static final String dispatchAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/DispatchAAAssignmentActivityLogDesc";
	private static final String dispatchSupplementAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/DispatchSupplementAAAssignmentActivityLogDesc";
	private static final String updateAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/UpdateAAAssignmentActivityLogDesc";
	private static final String assignAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/AssignAAAssignmentActivityLogDesc";
	private static final String reAssignAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/ReAssignAAAssignmentActivityLogDesc";
	private static final String onHoldAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/HoldAAAssignmentActivityLogDesc";
	private static final String removeOnHoldAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/RemoveHoldAAAssignmentActivityLogDesc";
	private static final String dbUpdateDispositionAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/UpdateDispositionAAAssignmentActivityLogDesc";
	// Add New
	private static final String dbReceivedAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/ReceivedAAAssignmentActivityLogDesc";
	private static final String dbRejectedAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/RejectedAAAssignmentActivityLogDesc";
	private static final String dbClosedAAAssignmentActivityLog = "/AppraisalAssignment/AssignmentActivityLogEvents/ClosedAAAssignmentActivityLogDesc";
	private static final String driveInAppointmentActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/DriveInAppointmentActivityLog";

	private static final String assignToDispatchCenterActivityLog = "/AppraisalAssignment/ExposureActivityLogMessages/AssignToDispatchCenterActivityLog";
	private static final String testAssignmentDispatcherList = "/AppraisalAssignment/TestAssignmentDispatcher";

	// Added for Time-Zone mapping
	private static final String timeZoneMappingList = "/AppraisalAssignment/TimeZoneMappingList";

	private static SystemConfigProxy systemConfigProxy = null;

	/**
	 * Getting assignToDispatch Center Activity log value from AAS SET file.
	 * 
	 * @return assignToDispatchActivityLog value
	 */
	public String getAssignToDispatchCenterActivityLog()
			throws MitchellException {
		return getSetting(assignToDispatchCenterActivityLog);
	}

	/**
	 * Getting CreateAAActivityLog value from AAS SET file.
	 * 
	 * @return CreateAAActivityLog value
	 */
	public String getCreateAAActivityLog() throws MitchellException {
		return getSetting(createAAActivityLog);
	}

	/**
	 * Getting CreateSupplementAAActivityLog value from AAS SET file.
	 * 
	 * @return CreateSupplementAAActivityLog value
	 */
	public String getCreateSupplementAAActivityLog() throws MitchellException {
		return getSetting(createSupplementAAActivityLog);
	}

	/**
	 * Getting CancelAAActivityLog value from AAS SET file.
	 * 
	 * @return CancelAAActivityLog value
	 */
	public String getCancelAAActivityLog() throws MitchellException {
		return getSetting(cancelAAActivityLog);
	}

	/**
	 * Getting UnCancelAAActivityLog value from AAS SET file.
	 * 
	 * @return UnCancelAAActivityLog value
	 */
	public String getUnCancelAAActivityLog() throws MitchellException {
		return getSetting(unCancelAAActivityLog);
	}

	/**
	 * Getting DispatchAAActivityLog value from AAS SET file.
	 * 
	 * @return DispatchAAActivityLog value
	 */
	public String getDispatchAAActivityLog() throws MitchellException {
		return getSetting(dispatchAAActivityLog);
	}

	/**
	 * Getting CompleteAAActivityLog value from AAS SET file.
	 * 
	 * @return CompleteAAActivityLog value
	 */
	public String getCompleteAAActivityLog() throws MitchellException {
		return getSetting(completeAAActivityLog);
	}

	/**
	 * Getting InProgressAAActivityLog value from AAS SET file.
	 * 
	 * @return InProgressAAActivityLog value
	 */
	public String getInProgressAAActivityLog() throws MitchellException {
		return getSetting(inProgressAAActivityLog);
	}

	/**
	 * Getting RejectedAAActivityLog value from AAS SET file.
	 * 
	 * @return RejectedAAActivityLog value
	 */
	public String getRejectedAAActivityLog() throws MitchellException {
		return getSetting(rejectedAAActivityLog);
	}

	/**
	 * Getting UnScheduleAAActivityLog value from AAS SET file.
	 * 
	 * @return UnScheduleAAActivityLog value
	 */
	public String getUnScheduleAAActivityLog() throws MitchellException {
		return getSetting(unScheduleAAActivityLog);
	}

	/**
	 * Getting UpdateAAActivityLog value from AAS SET file.
	 * 
	 * @return UpdateAAActivityLog value
	 */
	public String getUpdateAAActivityLog() throws MitchellException {
		return getSetting(updateAAActivityLog);
	}

	/**
	 * Getting AssignAAActivityLog value from AAS SET file.
	 * 
	 * @return AssignAAActivityLog value
	 */
	public String getAssignAAActivityLog() throws MitchellException {
		return getSetting(assignAAActivityLog);
	}

	/**
	 * Getting ReAssignAAActivityLog value from AAS SET file.
	 * 
	 * @return ReAssignAAActivityLog value
	 */
	public String getReAssignAAActivityLog() throws MitchellException {
		return getSetting(reAssignAAActivityLog);
	}

	/**
	 * Getting AddressValidateAAActivityLogSuccess value from AAS SET file.
	 * 
	 * @return AddressValidateAAActivityLogSuccess value
	 */
	public String getAddressValidateAAActivityLogPassed()
			throws MitchellException {
		return getSetting(addressValidateAAActivityLogPassed);
	}

	/**
	 * Getting AddressValidateAAActivityLogFailure value from AAS SET file.
	 * 
	 * @return AddressValidateAAActivityLogFailure value
	 */
	public String getAddressValidateAAActivityLogFailed()
			throws MitchellException {
		return getSetting(addressValidateAAActivityLogFailed);
	}

	/**
	 * Returns the value in settings at {@link #SET_WORK_AREA_DIR}.
	 * 
	 * @return value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public static String getWorkAreaDir() throws MitchellException {
		return getSetting(SET_WORK_AREA_DIR, true);

	}

	/**
	 * Return a string value from the SystemConfiguration Service for a
	 * specified setting name.
	 * 
	 * @param name
	 *            String containing the name of the setting to retrieve.
	 * @param mandatory
	 *            boolean variable
	 * @return value String value for the setting name
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	private static String getSetting(final String name, final boolean mandatory)
			throws MitchellException {
		final String value = SystemConfiguration.getSettingValue(name);

		if (mandatory && (value == null || value.length() == 0)) {
		}
		//
		return value;
	}

	// /////// MAXIMA ASSIGNMENT ACTIVITY LOGGING /////////

	/**
	 * Getting CreateAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return CreateAAAssignmentActivityLogDesc value
	 */
	public String getCreateAAAssignmentActivityLog() throws MitchellException {
		return getSetting(createAAAssignmentActivityLog);
	}

	/**
	 * Getting CreateSupplementAAAssignmentActivityLogDesc value from AAS SET
	 * file.
	 * 
	 * @return CreateSupplementAAAssignmentActivityLogDesc value
	 */
	public String getCreateSupplementAAAssignmentActivityLog()
			throws MitchellException {
		return getSetting(createSupplementAAAssignmentActivityLog);
	}

	/**
	 * Getting CancelAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return CancelAAAssignmentActivityLogDesc value
	 */
	public String getCancelAAAssignmentActivityLog() throws MitchellException {
		return getSetting(cancelAAAssignmentActivityLog);
	}

	/**
	 * Getting UnCancelAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return UnCancelAssignmentActivityLogDesc value
	 */
	public String getUnCancelAAAssignmentActivityLog() throws MitchellException {
		return getSetting(unCancelAAAssignmentActivityLog);
	}

	/**
	 * Getting UnScheduleAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return UnScheduleAAAssignmentActivityLogDesc value
	 */
	public String getUnScheduleAAAssignmentActivityLog()
			throws MitchellException {
		return getSetting(unScheduleAAAssignmentActivityLog);
	}

	/**
	 * Getting DispatchAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return DispatchAAAssignmentActivityLogDesc value
	 */
	public String getDispatchAAAssignmentActivityLog() throws MitchellException {
		return getSetting(dispatchAAAssignmentActivityLog);
	}

	/**
	 * Getting DispatchSupplementAAActivityLogDesc value from AAS SET file.
	 * 
	 * @return DispatchSupplementAAActivityLogDesc value
	 */
	public String getDispatchSupplementAAAssignmentActivityLog()
			throws MitchellException {
		return getSetting(dispatchSupplementAAAssignmentActivityLog);
	}

	/**
	 * Getting UpdateAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return UpdateAAAssignmentActivityLogDesc value
	 */
	public String getUpdateAAAssignmentActivityLog() throws MitchellException {
		return getSetting(updateAAAssignmentActivityLog);
	}

	/**
	 * Getting AssignAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return AssignAAAssignmentActivityLogDesc value
	 */
	public String getAssignAAAssignmentActivityLog() throws MitchellException {
		return getSetting(assignAAAssignmentActivityLog);
	}

	/**
	 * Getting ReAssignAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return ReAssignAAAssignmentActivityLogDesc value
	 */
	public String getReAssignAAAssignmentActivityLog() throws MitchellException {
		return getSetting(reAssignAAAssignmentActivityLog);
	}

	/**
	 * Getting HoldAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return HoldAAAssignmentActivityLogDesc value
	 */
	public String getOnHoldAAAssignmentActivityLog() throws MitchellException {
		return getSetting(onHoldAAAssignmentActivityLog);
	}

	/**
	 * Getting RemoveHoldAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return RemoveHoldAAAssignmentActivityLogDesc value
	 */
	public String getRemoveOnHoldAAAssignmentActivityLog()
			throws MitchellException {
		return getSetting(removeOnHoldAAAssignmentActivityLog);
	}

	/**
	 * Getting UpdateDispositionAAAssignmentActivityLogDesc value from AAS SET
	 * file.
	 * 
	 * @return UpdateDispositionAAAssignmentActivityLogDesc value
	 */
	public String getUpdateDispositionAAAssignmentActivityLog()
			throws MitchellException {
		return getSetting(dbUpdateDispositionAAAssignmentActivityLog);
	}

	// Add New
	/**
	 * Getting ReceivedAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return ReceivedAAAssignmentActivityLogDesc value
	 */
	public String getReceivedAAAssignmentActivityLog() throws MitchellException {
		return getSetting(dbReceivedAAAssignmentActivityLog);
	}

	/**
	 * Getting RejectedAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return RejectedAAAssignmentActivityLogDesc value
	 */
	public String getRejectedAAAssignmentActivityLog() throws MitchellException {
		return getSetting(dbRejectedAAAssignmentActivityLog);
	}

	/**
	 * Getting ClosedAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return ClosedAAAssignmentActivityLogDesc value
	 */
	public String getClosedAAAssignmentActivityLog() throws MitchellException {
		return getSetting(dbClosedAAAssignmentActivityLog);
	}

	/**
	 * Getting DriveInAppointmentActivityLog value from AAS SET file.
	 * 
	 * @return DriveInAppointmentActivityLog value
	 */
	public String getDriveInAppointmentActivityLog() throws MitchellException {
		return getSetting(driveInAppointmentActivityLog);
	}

	/**
	 * Getting testAssignmentDispatcherList value from AAS SET file.
	 * 
	 * @return testAssignmentDispatcherList value
	 */
	public String getTestAssignmentDispatcherList() throws MitchellException {
		return getSetting(testAssignmentDispatcherList);
	}

	/**
	 * Getting timeZoneMappingList value from AAS SET file
	 * 
	 * @return the timeZoneMappingList
	 * @throws MitchellException
	 */
	public String getTimezonemappingList() throws MitchellException {
		return getSetting(timeZoneMappingList);
	}

	/**
	 * Return a string value from the SystemConfiguration Service for a
	 * specified setting name.
	 * 
	 * @param name
	 *            String containing the name of the setting to retrieve.
	 */
	private static String getSetting(final String name)
			throws MitchellException {

		final String settingValue = systemConfigProxy.getSettingValue(name);
		if (settingValue == null) {
			throw new MitchellException("AppraisalAssignmentConfig",
					"getSetting",
					"Could not find any value for following setting = " + name);
		}
		return settingValue;
	}

	public void setSystemConfigProxy(SystemConfigProxy systemConfigProxy) {
		this.systemConfigProxy = systemConfigProxy;
	}
}