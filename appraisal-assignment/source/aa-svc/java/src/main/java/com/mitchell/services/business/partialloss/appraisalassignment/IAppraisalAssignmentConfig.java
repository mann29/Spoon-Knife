package com.mitchell.services.business.partialloss.appraisalassignment;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.SystemConfigProxy;

public interface IAppraisalAssignmentConfig {

	/**
	 * Getting assignToDispatch Center Activity log value from AAS SET file.
	 * 
	 * @return assignToDispatchActivityLog value
	 */
	public abstract String getAssignToDispatchCenterActivityLog()
			throws MitchellException;

	/**
	 * Getting CreateAAActivityLog value from AAS SET file.
	 * 
	 * @return CreateAAActivityLog value
	 */
	public abstract String getCreateAAActivityLog() throws MitchellException;

	/**
	 * Getting CreateSupplementAAActivityLog value from AAS SET file.
	 * 
	 * @return CreateSupplementAAActivityLog value
	 */
	public abstract String getCreateSupplementAAActivityLog()
			throws MitchellException;

	/**
	 * Getting CancelAAActivityLog value from AAS SET file.
	 * 
	 * @return CancelAAActivityLog value
	 */
	public abstract String getCancelAAActivityLog() throws MitchellException;

	/**
	 * Getting UnCancelAAActivityLog value from AAS SET file.
	 * 
	 * @return UnCancelAAActivityLog value
	 */
	public abstract String getUnCancelAAActivityLog() throws MitchellException;

	/**
	 * Getting DispatchAAActivityLog value from AAS SET file.
	 * 
	 * @return DispatchAAActivityLog value
	 */
	public abstract String getDispatchAAActivityLog() throws MitchellException;

	/**
	 * Getting CompleteAAActivityLog value from AAS SET file.
	 * 
	 * @return CompleteAAActivityLog value
	 */
	public abstract String getCompleteAAActivityLog() throws MitchellException;

	/**
	 * Getting InProgressAAActivityLog value from AAS SET file.
	 * 
	 * @return InProgressAAActivityLog value
	 */
	public abstract String getInProgressAAActivityLog()
			throws MitchellException;

	/**
	 * Getting RejectedAAActivityLog value from AAS SET file.
	 * 
	 * @return RejectedAAActivityLog value
	 */
	public abstract String getRejectedAAActivityLog() throws MitchellException;

	/**
	 * Getting UnScheduleAAActivityLog value from AAS SET file.
	 * 
	 * @return UnScheduleAAActivityLog value
	 */
	public abstract String getUnScheduleAAActivityLog()
			throws MitchellException;

	/**
	 * Getting UpdateAAActivityLog value from AAS SET file.
	 * 
	 * @return UpdateAAActivityLog value
	 */
	public abstract String getUpdateAAActivityLog() throws MitchellException;

	/**
	 * Getting AssignAAActivityLog value from AAS SET file.
	 * 
	 * @return AssignAAActivityLog value
	 */
	public abstract String getAssignAAActivityLog() throws MitchellException;

	/**
	 * Getting ReAssignAAActivityLog value from AAS SET file.
	 * 
	 * @return ReAssignAAActivityLog value
	 */
	public abstract String getReAssignAAActivityLog() throws MitchellException;

	/**
	 * Getting AddressValidateAAActivityLogSuccess value from AAS SET file.
	 * 
	 * @return AddressValidateAAActivityLogSuccess value
	 */
	public abstract String getAddressValidateAAActivityLogPassed()
			throws MitchellException;

	/**
	 * Getting AddressValidateAAActivityLogFailure value from AAS SET file.
	 * 
	 * @return AddressValidateAAActivityLogFailure value
	 */
	public abstract String getAddressValidateAAActivityLogFailed()
			throws MitchellException;

	/**
	 * Getting CreateAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return CreateAAAssignmentActivityLogDesc value
	 */
	public abstract String getCreateAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting CreateSupplementAAAssignmentActivityLogDesc value from AAS SET
	 * file.
	 * 
	 * @return CreateSupplementAAAssignmentActivityLogDesc value
	 */
	public abstract String getCreateSupplementAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting CancelAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return CancelAAAssignmentActivityLogDesc value
	 */
	public abstract String getCancelAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting UnCancelAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return UnCancelAssignmentActivityLogDesc value
	 */
	public abstract String getUnCancelAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting UnScheduleAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return UnScheduleAAAssignmentActivityLogDesc value
	 */
	public abstract String getUnScheduleAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting DispatchAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return DispatchAAAssignmentActivityLogDesc value
	 */
	public abstract String getDispatchAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting DispatchSupplementAAActivityLogDesc value from AAS SET file.
	 * 
	 * @return DispatchSupplementAAActivityLogDesc value
	 */
	public abstract String getDispatchSupplementAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting UpdateAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return UpdateAAAssignmentActivityLogDesc value
	 */
	public abstract String getUpdateAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting AssignAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return AssignAAAssignmentActivityLogDesc value
	 */
	public abstract String getAssignAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting ReAssignAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return ReAssignAAAssignmentActivityLogDesc value
	 */
	public abstract String getReAssignAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting HoldAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return HoldAAAssignmentActivityLogDesc value
	 */
	public abstract String getOnHoldAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting RemoveHoldAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return RemoveHoldAAAssignmentActivityLogDesc value
	 */
	public abstract String getRemoveOnHoldAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting UpdateDispositionAAAssignmentActivityLogDesc value from AAS SET
	 * file.
	 * 
	 * @return UpdateDispositionAAAssignmentActivityLogDesc value
	 */
	public abstract String getUpdateDispositionAAAssignmentActivityLog()
			throws MitchellException;

	// Add New
	/**
	 * Getting ReceivedAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return ReceivedAAAssignmentActivityLogDesc value
	 */
	public abstract String getReceivedAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting RejectedAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return RejectedAAAssignmentActivityLogDesc value
	 */
	public abstract String getRejectedAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting ClosedAAAssignmentActivityLogDesc value from AAS SET file.
	 * 
	 * @return ClosedAAAssignmentActivityLogDesc value
	 */
	public abstract String getClosedAAAssignmentActivityLog()
			throws MitchellException;

	/**
	 * Getting DriveInAppointmentActivityLog value from AAS SET file.
	 * 
	 * @return DriveInAppointmentActivityLog value
	 */
	public abstract String getDriveInAppointmentActivityLog()
			throws MitchellException;

	/**
	 * Getting testAssignmentDispatcherList value from AAS SET file.
	 * 
	 * @return testAssignmentDispatcherList value
	 */
	public abstract String getTestAssignmentDispatcherList()
			throws MitchellException;

	/**
	 * Getting timeZoneMappingList value from AAS SET file
	 * 
	 * @return the timeZoneMappingList
	 * @throws MitchellException
	 */
	public abstract String getTimezonemappingList() throws MitchellException;

	public abstract void setSystemConfigProxy(
			SystemConfigProxy systemConfigProxy);

}