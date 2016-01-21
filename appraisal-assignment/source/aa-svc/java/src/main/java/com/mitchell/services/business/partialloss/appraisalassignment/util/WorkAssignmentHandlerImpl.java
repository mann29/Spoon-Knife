package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.AssignmentScheduleType;
import com.mitchell.schemas.ItineraryViewDocument;
import com.mitchell.schemas.task.TaskDocument;
import com.mitchell.schemas.task.TaskType;
import com.mitchell.schemas.workassignment.AssigneeUserType;
import com.mitchell.schemas.workassignment.AssignorInfoType;
import com.mitchell.schemas.workassignment.CommQualiferCodeType;
import com.mitchell.schemas.workassignment.EventDefinitionType;
import com.mitchell.schemas.workassignment.EventInfoType;
import com.mitchell.schemas.workassignment.HoldInfoType;
import com.mitchell.schemas.workassignment.PersonInfoType;
import com.mitchell.schemas.workassignment.PersonNameType;
import com.mitchell.schemas.workassignment.PrimaryIDsType;
import com.mitchell.schemas.workassignment.ScheduleConstraintsType;
import com.mitchell.schemas.workassignment.ScheduleInfoType;
import com.mitchell.schemas.workassignment.ScheduleMethodType;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentStatusType;
import com.mitchell.schemas.workassignment.WorkAssignmentType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.mapping.WorkAssignmentMapper;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.ClaimProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.WorkAssignmentProxy;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.services.core.userinfo.utils.UserTypeConstants;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;
import com.mitchell.utils.misc.UUIDFactory;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class WorkAssignmentHandlerImpl implements WorkAssignmentHandler {
	private static Logger			logger		= Logger.getLogger(WorkAssignmentHandlerImpl.class.getName());
	private static final String		CLASS_NAME	= WorkAssignmentHandlerImpl.class.getName();

	private WorkAssignmentProxy		workAssignmentProxy;
	private AASErrorLogUtil			errorLogUtil;
	private UserInfoUtils			userInfoUtils;
	private WorkAssignmentMapper	workAssignmentMapper;
	private ClaimProxy				claimProxy;
	private AASCommonUtils			commonUtils;

	public void setWorkAssignmentProxy(final WorkAssignmentProxy workAssignmentProxy) {
		this.workAssignmentProxy = workAssignmentProxy;
	}

	public void setErrorLogUtil(final AASErrorLogUtil errorLogUtil) {

		this.errorLogUtil = errorLogUtil;
	}

	public void setUserInfoUtils(final UserInfoUtils userInfoUtils) {
		this.userInfoUtils = userInfoUtils;
	}

	public void setWorkAssignmentMapper(final WorkAssignmentMapper workAssignmentMapper) {
		this.workAssignmentMapper = workAssignmentMapper;
	}

	public void setClaimProxy(final ClaimProxy claimProxy) {
		this.claimProxy = claimProxy;
	}

	public AASCommonUtils getCommonUtils() {
		return commonUtils;
	}

	public void setCommonUtils(AASCommonUtils commonUtils) {
		this.commonUtils = commonUtils;
	}

	public WorkAssignmentDocument populateWorkAssignment(final TaskDocument taskDocument,
			final UserInfoDocument loggedInUserInfoDocument) throws MitchellException {
		final String methodName = "populateWorkAssignment";
		logger.entering(CLASS_NAME, methodName);

		String claimNum = AppraisalAssignmentConstants.CLAIM_NUMBER;
		WorkAssignmentDocument workAssignmentDocument = null;
		WorkAssignmentType waType = null;
		EventDefinitionType.Enum eventType = null;

		final TaskType taskType = taskDocument.getTask();

		try {

			if (logger.isLoggable(Level.FINE)) {
				logger.fine("Input Received :::" + taskDocument);
			}

			// CREATE CASE
			// Check TaskID
			if (!taskType.isSetTaskId()) {

				// For create scenario make a new workItemID
				if (logger.isLoggable(java.util.logging.Level.INFO)) {
					logger.info("Creating WorkAssignmentDocument for LuckAssignment task::");
				}

				workAssignmentDocument = WorkAssignmentDocument.Factory.newInstance();
				waType = workAssignmentDocument.addNewWorkAssignment();
				waType.setVersion(new BigDecimal("1.0"));

				// 1. Set "AssignmentType" Mandatory
				if (taskType.isSetAssignmentType()) {
					waType.setType(taskType.getAssignmentType());
				}

				PrimaryIDsType primaryIDsType = null;
				primaryIDsType = waType.addNewPrimaryIDs();

				// 2. Setting WorkItemID
				// 3. Setting ClaimNumber/CompanyCode/GroupID
				primaryIDsType.setWorkItemID(UUIDFactory.getInstance().getCeicaUUID());
				primaryIDsType.getWorkItemID();

				// Company code
				if (taskType.isSetCompanyCode()) {
					primaryIDsType.setCompanyCode(taskType.getCompanyCode());
				}

				// Claim Number
				if (taskType.isSetClaimNumber() && taskType.isSetCompanyCode()) {
					claimNum = taskType.getClaimNumber();
					final String compCo = taskType.getCompanyCode();
					final String userId = loggedInUserInfoDocument.getUserInfo().getUserID();

					// final ClaimRemote claimRemote =
					// ClaimClientSupport.getEJB();
					if (logger.isLoggable(java.util.logging.Level.INFO)) {
						final StringBuffer strBuf = new StringBuffer();
						strBuf.append("Calling parseClaimNumber method of Claim Service with \tCompanyCode : ")
								.append(compCo).append("\tUserID : ").append(userId);
						logger.info(strBuf.toString());
					}

					final String[] parsedClaim = claimProxy.parseClaimNumber(claimNum, compCo, userId);
					if (parsedClaim == null || parsedClaim.length != 2 || parsedClaim[0] == null
							|| parsedClaim[0].length() == 0) {
						throw new MitchellException(CLASS_NAME, methodName, "Could not get a parsed claim number from: ["
								+ claimNum + "]");
					}
					if (logger.isLoggable(java.util.logging.Level.INFO)) {
						logger.info("ParsedClaim length::" + parsedClaim.length);
						logger.info("Claim Number::" + parsedClaim[0]);
						logger.info("Claim Number Suffix::" + parsedClaim[1]);
					}
					primaryIDsType.setClaimNumber(parsedClaim[0]);
					if (parsedClaim[1] != null) {
						primaryIDsType.setClaimSuffix(parsedClaim[1]);
					}
				}

				// GroupID
				// Mandatory field
				primaryIDsType.setGroupID(taskType.getDispatchCenterName());

				// 4. Disposition
				if (taskType.isSetDisposition()) {
					waType.setDisposition(taskType.getDisposition());
				}

				// Status
				waType.setStatus(WorkAssignmentStatusType.OPENED);

				waType.addNewScheduleConstraints();
				waType.getScheduleConstraints().setScheduleMethod(ScheduleMethodType.MANUAL);

				// 7. AssignorID
				AssignorInfoType assignorInfoType = null;
				assignorInfoType = waType.addNewAssignorInfo();
				if (loggedInUserInfoDocument.getUserInfo() != null
						&& loggedInUserInfoDocument.getUserInfo().getUserID() != null
						&& !"".equalsIgnoreCase(loggedInUserInfoDocument.getUserInfo().getUserID())) {
					assignorInfoType.setAssignorID(loggedInUserInfoDocument.getUserInfo().getUserID());
				}
				eventType = EventDefinitionType.CREATED_EVENT;

			} else {

				// UPDATE CASE
				final long taskID = taskType.getTaskId();

				if (logger.isLoggable(java.util.logging.Level.INFO)) {
					logger.info("Updating WorkAssignmentDocument for LuckAssignment task with TaskID::" + taskID);
				}

				final WorkAssignment workAssignment = getWorkAssignmentByTaskId(taskID, loggedInUserInfoDocument);
				workAssignmentDocument = WorkAssignmentDocument.Factory.parse(workAssignment.getWorkAssignmentCLOBB());
				waType = workAssignmentDocument.getWorkAssignment();
				eventType = EventDefinitionType.UPDATED_EVENT;

			}

			// Duration

			if (taskType.isSetDuration()) {
				waType.getScheduleConstraints().setDuration(taskType.getDuration());
			}

			// Set EventInfo
			EventInfoType eventInfoType = null;
			if (waType.getEventInfo() == null) {
				eventInfoType = waType.addNewEventInfo();
			} else {
				eventInfoType = waType.getEventInfo();
			}

			eventInfoType.setEvent(eventType);
			eventInfoType.setEventDateTime(Calendar.getInstance());
			if (loggedInUserInfoDocument.getUserInfo() != null && loggedInUserInfoDocument.getUserInfo().getUserID() != null
					&& !"".equalsIgnoreCase(loggedInUserInfoDocument.getUserInfo().getUserID())) {
				eventInfoType.setUpdatedByID(loggedInUserInfoDocument.getUserInfo().getUserID());
			}

			ScheduleInfoType scheduleInfoType = null;
			if (waType.getCurrentSchedule() != null) {
				scheduleInfoType = waType.getCurrentSchedule();
			} else {
				scheduleInfoType = waType.addNewCurrentSchedule();
			}

			// ScheduleDateTime
			if (taskType.isSetScheduleDateTime()) {
				scheduleInfoType.setScheduleStartDateTime(taskType.getScheduleDateTime());
			}

			// AssigneeID
			if (taskType.getAssigneeId() != null && !"".equals(taskType.getAssigneeId())) {

				ScheduleInfoType currentSchedule = waType.getCurrentSchedule();
				final UserInfoDocument assigneeInfoDocument = userInfoUtils.retrieveUserInfo(waType.getPrimaryIDs()
						.getCompanyCode(), taskType.getAssigneeId());
				userInfoUtils.getUserDetailDoc(waType.getPrimaryIDs().getCompanyCode(), taskType.getAssigneeId());
				final UserInfoType assigneeInfoType = assigneeInfoDocument.getUserInfo();
				PersonInfoType assigneePersonInfoType = null;
				final String assigneeId = assigneeInfoType.getUserID();

				// Set Assignee Name
				assigneePersonInfoType = setAssigneeInfo(assigneeInfoType);
				currentSchedule.setAssignee(assigneePersonInfoType);

				if (logger.isLoggable(java.util.logging.Level.INFO)) {
					logger.info("CurrentSchedule updated with assignee info");
				}

				currentSchedule = setUserType(assigneeInfoType.getOrgCode(), assigneeInfoType.getUserID(), currentSchedule);

				currentSchedule.setAssigneeID(assigneeId);

				if (logger.isLoggable(java.util.logging.Level.INFO)) {
					logger.info("AssigneeID updated");
				}

			}
		} catch (final Exception e) {
			logger.severe("Error Mapping CreateTaskXML to WorkAssignmentDocument::" + e.getMessage());
			throw new MitchellException(CLASS_NAME, methodName, "Error Mapping CreateTaskXML to WorkAssignmentDocument!", e);
		}
		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			logger.info("populateWorkAssignment executed Successfully !!!");
		}
		logger.exiting(CLASS_NAME, methodName);

		return workAssignmentDocument;

	}

	/**
	 * This method calls WorkAssignment Service to retrieve Work Assignment
	 * using WorkAssignmentTaskID.
	 * @param taskId long
	 * @return WorkAssignment object.
	 * @throws Exception
	 */
	public WorkAssignment getWorkAssignmentByTaskId(final long workAssignmentTaskID, final UserInfoDocument logdInUsrInfo)
			throws MitchellException {

		final String METHOD_NAME = "getWorkAssignmentByTaskId";

		logger.entering(CLASS_NAME, METHOD_NAME);
		if (logger.isLoggable(java.util.logging.Level.FINE)) {
			logger.fine("Input Received workAssignmentTaskID: " + workAssignmentTaskID);
		}

		WorkAssignment workAssignment = null;
		try {

			workAssignment = workAssignmentProxy.getWorkAssignmentByTaskID(Long.valueOf(workAssignmentTaskID));

			if (workAssignment == null) {
				throw new MitchellException(CLASS_NAME, METHOD_NAME,
					"Received NULL work assignment from Work Assignment Service. WorkAssignmentTaskID : "
							+ workAssignmentTaskID);
			}
			if (logger.isLoggable(java.util.logging.Level.INFO)) {
				logger.info("Fetched WorkAssignment from getWorkAssignmentByTaskID of WorkAssignment Service for workAssignmentTaskID:"
						+ workAssignmentTaskID);
			}

		} catch (final Exception ex) {
			// Get claim number from WA doc , may you put unkknown
			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_GETTING_AA_WABYTASKID_AND_WAPARSEXML,
				CLASS_NAME, METHOD_NAME, ErrorLoggingService.SEVERITY.FATAL,
				AppraisalAssignmentConstants.ERROR_GETTING_AA_WABYTASKID_AND_WAPARSEXML_MSG, "workAssignmentTaskID: "
						+ workAssignmentTaskID, ex, logdInUsrInfo, null, null);
		}
		return workAssignment;
	}

	public WorkAssignment saveWorkAssignment(final WorkAssignmentDocument workAssignmentDocument,
			final UserInfoDocument logdInUsrInfo) throws MitchellException {
		final String METHOD_NAME = "saveWorkAssignment";
		logger.entering(CLASS_NAME, METHOD_NAME);
		String workItemID = "";
		WorkAssignment workAssignment = null;
		if (logger.isLoggable(java.util.logging.Level.FINE)) {
			StringBuilder localMethodParams = new StringBuilder();
			localMethodParams.append("workAssignmentDocument : " + workAssignmentDocument.toString());
			logger.fine("Input Received ::\n" + localMethodParams);
		}
		String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;

		try {
			WorkAssignmentType workAssignmentType = workAssignmentDocument.getWorkAssignment();
			
			String assignmentType = workAssignmentType.getType();
			String disposition = workAssignmentType.getDisposition();
			// added if for ghost save in wa_work_assignment_hist table for
			// cancelled, incomplete and rescheduled assignments
			if ((AppraisalAssignmentConstants.ORIGINAL_ASSIGNMENT_TYPE.equalsIgnoreCase(assignmentType) || AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
					.equalsIgnoreCase(assignmentType))
					&& (AppraisalAssignmentConstants.DISPOSITION_CANCELLED.equalsIgnoreCase(disposition)
							|| AppraisalAssignmentConstants.DISPOSITION_RESCHEDULE.equalsIgnoreCase(disposition) || AppraisalAssignmentConstants.DISPOSITION_INCOMPLETE
								.equalsIgnoreCase(disposition))) {
				
				// event for wa_work_assignment_hist table
				String event = workAssignmentType.getDisposition();
				
				Long waWorkAssignmentHistID = workAssignmentProxy.saveWorkAssignmentHist(workAssignmentType.getPrimaryIDs()
						.getWorkAssignmentID(), event);

				if (waWorkAssignmentHistID == null) {
					throw new MitchellException(CLASS_NAME, METHOD_NAME,
						"Received NULL WorkAssignmentHistID from Work Assignment Service: ");
				}
			}
			
			claimNumber = workAssignmentType.getPrimaryIDs().getClaimNumber();
			workItemID = workAssignmentType.getPrimaryIDs().getWorkItemID();
			workAssignment = workAssignmentProxy.save(workAssignmentDocument);

			if (workAssignment == null) {
				throw new MitchellException(CLASS_NAME, METHOD_NAME,
					"Received NULL work assignment from Work Assignment Service: ");
			}

			if (!(AppraisalAssignmentConstants.LUNCH_ASSIGNMENT_TYPE.equalsIgnoreCase(workAssignmentDocument
					.getWorkAssignment().getType()))) {

			final Long documentId = workAssignment.getReferenceId();
				
			}
		} catch (final Exception ex) {

			final StringBuilder msgDetail = new StringBuilder();
			msgDetail.append("workItemID : ").append(workItemID).append("ClaimNumber : ").append(claimNumber);

			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_SAVE_AA_SAVEWA, CLASS_NAME, METHOD_NAME,
				ErrorLoggingService.SEVERITY.FATAL, AppraisalAssignmentConstants.ERROR_SAVE_AA_SAVEWA_MSG,
				msgDetail.toString(), ex, logdInUsrInfo, workItemID, claimNumber);

		}
		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			logger.info("Saved WorkAssignment through WorkAssignment Service");
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return workAssignment;

	}

	/**
	 * Assigns or re-assigns work assignment.
	 * @param workAssignmentDocument Work Assignment Document object of
	 *            WorkAssignment Service.
	 * @param disposition Disposition to set.
	 * @param assigneeInfo Estimator's UserInfo Document to whom this assignment
	 *            is assigned.
	 * @return updated <code>WorkAssignment</code> object.
	 * @throws Exception Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public WorkAssignment assignWorkAssignment(final WorkAssignmentDocument workAssignmentDocument,
			final String disposition, final String dispatchCenter, final UserInfoDocument assigneeInfo,
			final Calendar scheduleDateTime, final UserDetailDocument userDetail, final UserInfoDocument logdInUsrInfo)
			throws Exception {

		final String METHOD_NAME = "assignWorkAssignment";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(java.util.logging.Level.FINE)) {
			final StringBuilder localMethodParams = new StringBuilder();
			localMethodParams.append("WorkAssignmentDocument: " + workAssignmentDocument)
					.append("\ndisposition: " + disposition).append("\n dispatchCenter: " + dispatchCenter)
					.append("\nassigneeInfo: " + assigneeInfo).append("\nuserDetail: " + userDetail);
			logger.fine("Input Received ::\n" + localMethodParams);
		}

		PersonInfoType assigneePersonInfoType = null;
		EventInfoType eventInfo = null;
		WorkAssignmentType workAssignmentType = null;
		WorkAssignment workAssignment = null;
		final Calendar calendar = Calendar.getInstance();
		String assigneeId = null;
		String dbAssigneeID = "";
		workAssignmentType = workAssignmentDocument.getWorkAssignment();
		dbAssigneeID = workAssignmentType.getCurrentSchedule().getAssigneeID();
		final String dbDisposition = workAssignmentType.getDisposition();
		String claimNumber = null;

		String workItemID = "";

		claimNumber = workAssignmentType.getPrimaryIDs().getClaimNumber();
		workItemID = workAssignmentType.getPrimaryIDs().getWorkItemID();

		if (assigneeInfo != null) {
			assigneeId = assigneeInfo.getUserInfo().getUserID();
		}

		// Manoj This check should be done at method begin level
		if (dbDisposition.equalsIgnoreCase("CANCELLED") || dbDisposition.equalsIgnoreCase("COMPLETED")) {
			throw new MitchellException(CLASS_NAME, "assignWorkAssignment",
				"For assign/re-assign, the disposition status must be OPENED. Current DB disposition status : "
						+ dbDisposition);
		}

		try {

			// setting groupId
			if (dispatchCenter != null) {
				workAssignmentType.getPrimaryIDs().setGroupID(dispatchCenter);
			}

			// setting eventInfo
			eventInfo = EventInfoType.Factory.newInstance();
			eventInfo.setEventDateTime(calendar);
			eventInfo.setUpdatedByID(logdInUsrInfo.getUserInfo().getUserID());
			if (dbAssigneeID == null || dbAssigneeID.trim().length() == 0 || dbAssigneeID.equalsIgnoreCase(assigneeId)
					|| assigneeId == null) {
				if (disposition.trim().equalsIgnoreCase("DISPATCHED")) {
					eventInfo.setEvent(EventDefinitionType.DISPATCHED_EVENT);
				} else {
					eventInfo.setEvent(EventDefinitionType.UPDATED_EVENT);
				}
			} else {
				eventInfo.setEvent(EventDefinitionType.REASSIGNED_EVENT);
			}
			workAssignmentType.setEventInfo(eventInfo);

			// setting disposition
			workAssignmentType.setDisposition(disposition);

			// setting CurrentSchedule
			ScheduleInfoType currentSchedule = workAssignmentType.getCurrentSchedule();

			// setting ScheduleInfo - setScheduelDateTime
			if (scheduleDateTime != null) {
				currentSchedule.setScheduleStartDateTime(scheduleDateTime);
			}

			if (assigneeInfo != null && userDetail != null) {
				UserInfoType assigneeInfoType = assigneeInfo.getUserInfo();

				assigneeId = assigneeInfoType.getUserID();

				assigneePersonInfoType = setAssigneeInfo(assigneeInfoType);

				currentSchedule.setAssignee(assigneePersonInfoType);

				if (logger.isLoggable(java.util.logging.Level.INFO)) {
					logger.info("CurrentSchedule updated with assignee info");
				}

				if (userDetail.getUserDetail().isSetPhone()) {
					final String phone = userDetail.getUserDetail().getPhone();
					if (phone != null && !"".equalsIgnoreCase(phone)) {
						if (assigneePersonInfoType == null) {
							assigneePersonInfoType = PersonInfoType.Factory.newInstance();
						}
						final com.mitchell.schemas.workassignment.CommunicationsType commType = assigneePersonInfoType
								.addNewCommunications();
						commType.setCommQualifier(CommQualiferCodeType.WP);
						commType.setCommPhone(phone);

						if (logger.isLoggable(java.util.logging.Level.INFO)) {
							logger.info("CommunicationType updated from User phone Details");
						}

					}
				}

				if (userDetail.getUserDetail().isSetEmail()) {
					final String eMail = userDetail.getUserDetail().getEmail();
					if (eMail != null && !"".equalsIgnoreCase(eMail)) {
						if (assigneePersonInfoType == null) {
							assigneePersonInfoType = PersonInfoType.Factory.newInstance();
						}

						final com.mitchell.schemas.workassignment.CommunicationsType commType = assigneePersonInfoType
								.addNewCommunications();
						commType.setCommQualifier(CommQualiferCodeType.EM);
						commType.setCommEmail(eMail);

						if (logger.isLoggable(java.util.logging.Level.INFO)) {
							logger.info("CommunicationType updated from User Email Details");
						}

					}
				}

				if (userDetail.getUserDetail().isSetAddress()) {
					final com.mitchell.services.core.userinfo.types.AddressType addressType = userDetail.getUserDetail()
							.getAddress();
					if (addressType != null) {
						String address2 = null;
						final String address1 = addressType.getAddress1();
						if (addressType.isSetAddress2()) {
							address2 = addressType.getAddress2();
						}

						if (assigneePersonInfoType == null) {
							assigneePersonInfoType = PersonInfoType.Factory.newInstance();
						}
						final com.mitchell.schemas.workassignment.CommunicationsType commType = assigneePersonInfoType
								.addNewCommunications();
						commType.setCommQualifier(CommQualiferCodeType.AL);
						final com.mitchell.schemas.workassignment.AddressType address = commType.addNewAddress();
						address.setAddress1(address1);
						if ((address2 != null && !"".equals(address2))) {
							address.setAddress2(address2);
						}
						address.setCity(addressType.getCity());
						address.setStateProvince(addressType.getState());
						address.setPostalCode(addressType.getZip());
						address.setCountryCode(addressType.getCountry());

						if (logger.isLoggable(java.util.logging.Level.INFO)) {
							logger.info("AddressType updated from User Address Details");
						}
					}
				}

				// set usertype in currentschedule

				currentSchedule = setUserType(assigneeInfoType.getOrgCode(), assigneeInfoType.getUserID(), currentSchedule);

				currentSchedule.setAssigneeID(assigneeId);

			}
		} catch (final Exception exception) {

			if (claimNumber == null) {
				claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
			}

			final StringBuilder msgDetail = new StringBuilder();
			msgDetail.append("\tWorkIemId : ").append(workItemID).append("\tClaimNumber : ").append(claimNumber);

			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_ASSIGN_AA_ASSIGNUIDTOWA, CLASS_NAME,
				METHOD_NAME, ErrorLoggingService.SEVERITY.FATAL,
				AppraisalAssignmentConstants.ERROR_ASSIGN_AA_ASSIGNUIDTOWA_MSG, msgDetail.toString(), exception,
				logdInUsrInfo, workItemID, claimNumber);
		}

		if (logger.isLoggable(java.util.logging.Level.FINE)) {
			logger.fine("workAssignmentDocument before calling WorkAssignment" + workAssignmentDocument);
		}
		workAssignment = saveWorkAssignment(workAssignmentDocument, logdInUsrInfo);

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return workAssignment;
	}

	private PersonInfoType setAssigneeInfo(final UserInfoType assigneeInfoType) throws Exception {
		final String METHOD_NAME = "setAssigneeInfo";
		logger.entering(CLASS_NAME, METHOD_NAME);
		PersonInfoType assigneePersonInfoType = null;
		PersonNameType personNameType = null;
		String firstName = null;
		String lastName = null;
		if (assigneeInfoType.isSetFirstName()) {
			firstName = assigneeInfoType.getFirstName().trim();
		}
		if (assigneeInfoType.isSetLastName()) {
			lastName = assigneeInfoType.getLastName().trim();
		}
		if ((firstName != null && !"".equals(firstName)) || (lastName != null && "".equals(lastName))) {
			assigneePersonInfoType = PersonInfoType.Factory.newInstance();
			personNameType = PersonNameType.Factory.newInstance();
			if (firstName != null && !"".equals(firstName)) {
				personNameType.setFirstName(firstName);
			}
			if (lastName != null && !"".equals(lastName)) {
				personNameType.setLastName(lastName);
			}
			assigneePersonInfoType.setPersonName(personNameType);
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return assigneePersonInfoType;
	}

	private ScheduleInfoType setUserType(final String orgCode, final String userID, final ScheduleInfoType currentSchedule)
			throws Exception {
		final String METHOD_NAME = "setUserType";
		logger.entering(CLASS_NAME, METHOD_NAME);
		String userType = null;

		userType = userInfoUtils.getUserType(orgCode, userID);

		if (UserTypeConstants.STAFF_TYPE.equalsIgnoreCase(userType)) {
			currentSchedule.setAssigneeUserType(AssigneeUserType.STAFF);
		} else if (UserTypeConstants.SHOP_TYPE.equalsIgnoreCase(userType)
				|| UserTypeConstants.DRP_SHOP_TYPE.equalsIgnoreCase(userType)) {

			currentSchedule.setAssigneeUserType(AssigneeUserType.BODYSHOP);
		} else if (UserTypeConstants.DRP_IA_TYPE.equalsIgnoreCase(userType)
				|| UserTypeConstants.IA_TYPE.equalsIgnoreCase(userType)) {
			currentSchedule.setAssigneeUserType(AssigneeUserType.INDEPENDENT_APP);
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return currentSchedule;
	}

	/**
	 * Unschedules WorkAssignment
	 * @param workAssignmentDocument Work Assignment Document object of
	 *            WorkAssignment Service.
	 * @param disposition Disposition to set.
	 * @return updated <code>WorkAssignment</code> object.
	 * @throws Exception Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public WorkAssignment unscheduleWorkAssignment(WorkAssignment workAssignment, final String reasonCode,
			final String reasonNotes, final UserInfoDocument logdInUsrInfo) throws MitchellException {

		final String METHOD_NAME = "unscheduleWorkAssignment";
		logger.entering(CLASS_NAME, METHOD_NAME);
		// DISPOSITION SET TO NOT READY
		final String disposition = AppraisalAssignmentConstants.DISPOSITION_NOT_READY;
		EventInfoType eventInfo = null;
		final Calendar calendar = Calendar.getInstance();
		String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
		String workItemID = "";
		final String dbWAStatus = workAssignment.getWorkAssignmentStatus();
		WorkAssignmentDocument workAssignmentDocument = null;
		WorkAssignmentType workAssignmentType = null;

		// If disposition is CANCELLED and COMPLETED we cannot unschedule
		// assignment
		try {

			if (logger.isLoggable(java.util.logging.Level.FINE)) {
				StringBuilder localMethodParams = new StringBuilder();
				localMethodParams.append("workAssignment : " + workAssignment.getWorkAssignmentCLOBB());
				logger.fine("Input Received ::\n" + localMethodParams);
			}

			// Status should be check rather than Dispostion...in future may be
			// there are more than one dispostion under status ..
			if (dbWAStatus.equalsIgnoreCase("CANCELLED") || dbWAStatus.equalsIgnoreCase("CLOSED")) {
				throw new MitchellException(CLASS_NAME, METHOD_NAME,
					"For unScheduleAssignment, the  assignment  status must be OPENED. Currently Appraisal assignment= "
							+ workAssignment.getTaskID() + " is  : " + dbWAStatus);
			}

			workAssignmentDocument = WorkAssignmentDocument.Factory.parse(workAssignment.getWorkAssignmentCLOBB());
			claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getClaimNumber();
			workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getWorkItemID();
			workAssignmentType = workAssignmentDocument.getWorkAssignment();

			eventInfo = EventInfoType.Factory.newInstance();
			eventInfo.setEventDateTime(calendar);
			eventInfo.setUpdatedByID(logdInUsrInfo.getUserInfo().getUserID());
			eventInfo.setEvent(EventDefinitionType.REASSIGNED_EVENT);
			workAssignmentType.setEventInfo(eventInfo);

			// Checking reasonCode
			if (reasonCode != null && !"".equalsIgnoreCase(reasonCode)) {
				workAssignmentType.setEventReasonCode(reasonCode);
			}

			// Checking comment
			if (reasonNotes != null && !"".equalsIgnoreCase(reasonNotes)) {
				workAssignmentType.getEventInfo().setEventMemo(reasonNotes);
			}

			// setting disposition & status
			workAssignmentType.setDisposition(disposition);

			// unSetting CurrentSchedule assignee info
			final ScheduleInfoType currentSchedule = workAssignmentType.getCurrentSchedule();

			if (currentSchedule.isSetAssignee()) {
				currentSchedule.unsetAssignee();
			}

			if (currentSchedule.isSetAssigneeID()) {
				currentSchedule.unsetAssigneeID();
			}

			if (currentSchedule.isSetAssigneeUserType()) {
				currentSchedule.unsetAssigneeUserType();
			}

			if (currentSchedule.isSetScheduleStartDateTime()) {
				currentSchedule.unsetScheduleStartDateTime();
			}

			if (logger.isLoggable(java.util.logging.Level.FINE)) {
				logger.fine("workAssignmentDocument before calling WorkAssignment :: " + workAssignmentDocument);
			}

			workAssignment = saveWorkAssignment(workAssignmentDocument, logdInUsrInfo);

			if (logger.isLoggable(java.util.logging.Level.INFO)) {
				logger.info("unschedule WorkAssignment  ");
			}

		} catch (final Exception ex) {
			final StringBuilder msgDetail = new StringBuilder();
			msgDetail.append("\tWorkIemId : ").append(workItemID).append("\tClaimNumber : ").append(claimNumber);

			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA_UNSCHEDULEWA, CLASS_NAME,
				METHOD_NAME, ErrorLoggingService.SEVERITY.FATAL,
				AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA_UNSCHEDULEWA_MSG, msgDetail.toString(), ex, logdInUsrInfo,
				workItemID, claimNumber);
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);

		return workAssignment;
	}

	public WorkAssignment unscheduleWorkAssignment(WorkAssignment workAssignment, final String reasonCode,
			final String reasonNotes, final UserInfoDocument logdInUsrInfo, ItineraryViewDocument itineraryXML)
			throws MitchellException {

		final String METHOD_NAME = "unscheduleWorkAssignment";
		logger.entering(CLASS_NAME, METHOD_NAME);
		// DISPOSITION SET TO NOT READY
		final String disposition = AppraisalAssignmentConstants.DISPOSITION_NOT_READY;
		EventInfoType eventInfo = null;
		final Calendar calendar = Calendar.getInstance();
		String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
		String workItemID = "";
		final String dbWAStatus = workAssignment.getWorkAssignmentStatus();
		WorkAssignmentDocument workAssignmentDocument = null;
		WorkAssignmentType workAssignmentType = null;

		// If disposition is CANCELLED and COMPLETED we cannot unschedule
		// assignment
		try {

			if (logger.isLoggable(java.util.logging.Level.FINE)) {
				StringBuilder localMethodParams = new StringBuilder();
				localMethodParams.append("workAssignment : " + workAssignment.getWorkAssignmentCLOBB());
				logger.fine("Input Received ::\n" + localMethodParams);
			}

			// Status should be check rather than Dispostion...in future may be
			// there are more than one dispostion under status ..
			if (dbWAStatus.equalsIgnoreCase("CANCELLED") || dbWAStatus.equalsIgnoreCase("CLOSED")) {
				throw new MitchellException(CLASS_NAME, METHOD_NAME,
					"For unScheduleAssignment, the  assignment  status must be OPENED. Currently Appraisal assignment= "
							+ workAssignment.getTaskID() + " is  : " + dbWAStatus);
			}

			workAssignmentDocument = WorkAssignmentDocument.Factory.parse(workAssignment.getWorkAssignmentCLOBB());
			claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getClaimNumber();
			workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getWorkItemID();
			workAssignmentType = workAssignmentDocument.getWorkAssignment();

			eventInfo = EventInfoType.Factory.newInstance();
			eventInfo.setEventDateTime(calendar);
			eventInfo.setUpdatedByID(logdInUsrInfo.getUserInfo().getUserID());
			eventInfo.setEvent(EventDefinitionType.REASSIGNED_EVENT);
			workAssignmentType.setEventInfo(eventInfo);

			// Checking reasonCode
			if (reasonCode != null && !"".equalsIgnoreCase(reasonCode)) {
				workAssignmentType.setEventReasonCode(reasonCode);
			}

			// Checking comment
			if (reasonNotes != null && !"".equalsIgnoreCase(reasonNotes)) {
				workAssignmentType.getEventInfo().setEventMemo(reasonNotes);
			}

			// setting disposition & status
			workAssignmentType.setDisposition(disposition);

			// unSetting CurrentSchedule assignee info
			final ScheduleInfoType currentSchedule = workAssignmentType.getCurrentSchedule();

			if (currentSchedule.isSetAssignee()) {
				currentSchedule.unsetAssignee();
			}

			if (currentSchedule.isSetAssigneeID()) {
				currentSchedule.unsetAssigneeID();
			}

			if (currentSchedule.isSetAssigneeUserType()) {
				currentSchedule.unsetAssigneeUserType();
			}

			if (currentSchedule.isSetScheduleStartDateTime()) {
				currentSchedule.unsetScheduleStartDateTime();
			}

			logger.info("itinerary xml:" + itineraryXML);
			if ((null != itineraryXML) && (null != itineraryXML.getItineraryView())
					&& (null != itineraryXML.getItineraryView().getAssignmentSchedule())) {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Setting Urgency PreferredScheduleDate PreferredScheduleEndTime and PreferredScheduleStartTime from itineraryXML");
				}

				ScheduleConstraintsType constraintsType = workAssignmentType.getScheduleConstraints();

				AssignmentScheduleType assignmentScheduleType = itineraryXML.getItineraryView().getAssignmentSchedule();

				if (null != assignmentScheduleType.getUrgency()) {
					constraintsType.setPriority(this.commonUtils.convertPriorityToString(assignmentScheduleType.getUrgency()
							.intValue()));
				}

				if (null != assignmentScheduleType.getPreferredScheduleDate()) {
					constraintsType.setPreferredScheduleDate(assignmentScheduleType.getPreferredScheduleDate());
				}

				if (null != assignmentScheduleType.getPreferredScheduleStartTime()) {
					constraintsType.setPreferredScheduleStartTime(assignmentScheduleType.getPreferredScheduleStartTime());
				} else {
					if (null != constraintsType.getPreferredScheduleStartTime())
						constraintsType.unsetPreferredScheduleStartTime();
				}

				if (null != assignmentScheduleType.getPreferredScheduleEndTime()) {
					constraintsType.setPreferredScheduleEndTime(assignmentScheduleType.getPreferredScheduleEndTime());
				} else {
					if (null != constraintsType.getPreferredScheduleEndTime())
						constraintsType.unsetPreferredScheduleEndTime();
				}

			}

			if (logger.isLoggable(java.util.logging.Level.INFO)) {
				logger.info("workAssignmentDocument before calling WorkAssignment :: " + workAssignmentDocument);
			}

			workAssignment = saveWorkAssignment(workAssignmentDocument, logdInUsrInfo);

			if (logger.isLoggable(java.util.logging.Level.INFO)) {
				logger.info("unschedule WorkAssignment  ");
			}

		} catch (final Exception ex) {
			final StringBuilder msgDetail = new StringBuilder();
			msgDetail.append("\tWorkIemId : ").append(workItemID).append("\tClaimNumber : ").append(claimNumber);

			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA_UNSCHEDULEWA, CLASS_NAME,
				METHOD_NAME, ErrorLoggingService.SEVERITY.FATAL,
				AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA_UNSCHEDULEWA_MSG, msgDetail.toString(), ex, logdInUsrInfo,
				workItemID, claimNumber);
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);

		return workAssignment;
	}

	public WorkAssignment uncancelWorkAssignment(final WorkAssignmentDocument updatedWorkAssignmentDocument,
			final UserInfoDocument loggedInUserInfoDocument) throws MitchellException {
		final String METHOD_NAME = "uncancelWorkAssignment";
		WorkAssignment workAssignment = null;
		String workItemID = "";
		String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;

		try {

			workItemID = updatedWorkAssignmentDocument.getWorkAssignment().getPrimaryIDs().getWorkItemID();
			claimNumber = updatedWorkAssignmentDocument.getWorkAssignment().getPrimaryIDs().getClaimNumber();

			workAssignment = workAssignmentProxy.unCancel(updatedWorkAssignmentDocument, loggedInUserInfoDocument);

			if (logger.isLoggable(java.util.logging.Level.INFO)) {
				logger.info("UnCancelled through unCancel method of WorkAssignment Service :isUncancelled value ");
			}

			if (workAssignment == null) {
				throw new MitchellException(CLASS_NAME, METHOD_NAME,
					"Received Null WorkAssignment from WorAssignment Service in  uncancelWorkAssignment: ");
			}
		} catch (final Exception exception) {
			final StringBuilder msgDetail = new StringBuilder();
			msgDetail.append("\tWorkIemId : ").append(workItemID).append("\tClaimNumber : ").append(claimNumber);

			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_UNCANCEL_AA_WASAVE, CLASS_NAME, METHOD_NAME,
				ErrorLoggingService.SEVERITY.FATAL, AppraisalAssignmentConstants.ERROR_UNCANCEL_AA_WASAVE_MSG,
				msgDetail.toString(), exception, loggedInUserInfoDocument, workItemID, claimNumber);
		}
		return workAssignment;
	}

	public WorkAssignment saveWorkAssignmentStatus(WorkAssignment workAssignment, String newDispositionCode,
			String reasonCode, String comment, final UserInfoDocument loggedInUserInfoDocument,
			ItineraryViewDocument itineraryXML) throws MitchellException {
		final String METHOD_NAME = "saveWorkAssignmentStatus";
		logger.entering(CLASS_NAME, METHOD_NAME);
		WorkAssignmentDocument updatedWorkAssignmentDocument = null;
		WorkAssignmentDocument workAssignmentDocument = null;
		String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
		String workItemID = "";
		try {
			logger.info("itineraryXML  **commentbefore** "+itineraryXML);
			// Checking itineraryXML
			if (null != itineraryXML && null != itineraryXML.getItineraryView()) {
				if (logger.isLoggable(java.util.logging.Level.INFO)) {
					logger.info("Setting detail from itineraryXML");
				}
				
				if (comment==null || "".equalsIgnoreCase(comment.trim())){
					comment = itineraryXML.getItineraryView().getNotes();
					logger.info("Inside saveWorkAssignmentStatus **commentAfter** "+comment);
					}

				// Checking reasonCode
				if (null != itineraryXML.getItineraryView().getReasonCode()
						&& !"".equalsIgnoreCase(itineraryXML.getItineraryView().getReasonCode())) {
					reasonCode = itineraryXML.getItineraryView().getReasonCode();
				}

				// Checking newDispositionCode
				if (null != itineraryXML.getItineraryView().getDisposition()
						&& !"".equalsIgnoreCase(itineraryXML.getItineraryView().getDisposition())) {
					newDispositionCode = itineraryXML.getItineraryView().getDisposition();
				}
			}

			// Retrieve work assignment xml from CLOB.
			final String XmlW = workAssignment.getWorkAssignmentCLOBB();
			workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);

			if (logger.isLoggable(java.util.logging.Level.INFO)) {
				logger.info("Retrieved Work Assignment Document from WorkAssignmentService");
			}

			claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getClaimNumber();
			workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getWorkItemID();
			updatedWorkAssignmentDocument = setupWorkAssignmentRequest(workAssignmentDocument, newDispositionCode,
				newDispositionCode, loggedInUserInfoDocument);
			final WorkAssignmentType workAssignmentType = updatedWorkAssignmentDocument.getWorkAssignment();

			// Checking itineraryXML
			if (null != itineraryXML && null != itineraryXML.getItineraryView()
					&& null != itineraryXML.getItineraryView().getAssignmentSchedule()) {
				if (logger.isLoggable(java.util.logging.Level.INFO)) {
					logger.info("Setting Urgency PreferredScheduleDate PreferredScheduleEndTime and PreferredScheduleStartTime from itineraryXML");
				}
				/*
				 * Set Schedule detail in workAssignmentType
				 */
				com.mitchell.schemas.workassignment.ScheduleConstraintsType constraintsType = workAssignmentType
						.getScheduleConstraints();

				AssignmentScheduleType assignmentScheduleType = itineraryXML.getItineraryView().getAssignmentSchedule();

				// Checking Urgency
				if (null != assignmentScheduleType.getUrgency()) {
					constraintsType.setPriority(commonUtils.convertPriorityToString(assignmentScheduleType.getUrgency()
							.intValue()));
				}

				// Checking PreferredScheduleDate
				if (null != assignmentScheduleType.getPreferredScheduleDate()) {
					constraintsType.setPreferredScheduleDate(assignmentScheduleType.getPreferredScheduleDate());
				}

				// Checking PreferredScheduleEndTime
				if (null != assignmentScheduleType.getPreferredScheduleEndTime()) {
					constraintsType.setPreferredScheduleEndTime(assignmentScheduleType.getPreferredScheduleEndTime());
				}

				// Checking PreferredScheduleStartTime
				if (null != assignmentScheduleType.getPreferredScheduleStartTime()) {
					constraintsType.setPreferredScheduleStartTime(assignmentScheduleType.getPreferredScheduleStartTime());
				}
			}

			// Checking reasonCode
			if (reasonCode != null && !"".equalsIgnoreCase(reasonCode)) {
				workAssignmentType.setEventReasonCode(reasonCode);
			}

			// Checking comment
			if (comment != null && !"".equalsIgnoreCase(comment)) {
				workAssignmentType.getEventInfo().setEventMemo(comment);
			
			}

			logger.info("Entering saveWorkAssignment()");
			// Save updated work assignment xml to Work Assignment Service
			workAssignment = saveWorkAssignment(updatedWorkAssignmentDocument, loggedInUserInfoDocument);

		} catch (final Exception ex) {
			final StringBuilder msgDetail = new StringBuilder();
			msgDetail.append("\tWorkIemId : ").append(workItemID).append("\tClaimNumber : ").append(claimNumber);
			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_SAVE_AA_SAVEWA, CLASS_NAME, METHOD_NAME,
				ErrorLoggingService.SEVERITY.FATAL, AppraisalAssignmentConstants.ERROR_SAVE_AA_SAVEWA_MSG,
				msgDetail.toString(), ex, loggedInUserInfoDocument, workItemID, claimNumber);
		}

		return workAssignment;

	}

	/**
	 * This method is helper method to setup WorkAssignment Document values.
	 * @param workAssignmentDocument
	 * @param disposition
	 * @param reqFor
	 * @return
	 */
	public WorkAssignmentDocument setupWorkAssignmentRequest(final WorkAssignmentDocument workAssignmentDocument,
			final String disposition, final String requestFor, final UserInfoDocument logdInUsrInfo) {

		final String METHOD_NAME = "setupWorkAssignmentRequest";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(java.util.logging.Level.FINE)) {
			StringBuilder localMethodParams = new StringBuilder();
			localMethodParams.append("workAssignmentDocument:" + workAssignmentDocument)
					.append("\ndisposition: " + disposition).append("\nrequestFor: " + requestFor);
			logger.fine("Input Received ::\n" + localMethodParams);
		}

		WorkAssignmentType workAssignmentType = null;
		final Calendar calendar = Calendar.getInstance();
		workAssignmentType = workAssignmentDocument.getWorkAssignment();

		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			logger.info("Creating eventInfoType");
		}

		final EventInfoType eventInfoType = EventInfoType.Factory.newInstance();
		eventInfoType.setUpdatedByID(logdInUsrInfo.getUserInfo().getUserID());
		eventInfoType.setEventDateTime(calendar);
		if (AppraisalAssignmentConstants.DISPOSITION_CANCELLED.equals(disposition)
				|| AppraisalAssignmentConstants.DISPOSITION_REJECTED.equals(disposition)) {
			eventInfoType.setEvent(EventDefinitionType.CANCELLED_EVENT);
			workAssignmentType.setStatus(WorkAssignmentStatusType.CANCELLED);
		} else if (AppraisalAssignmentConstants.DISPOSITION_DISPATCHED.equals(disposition)) {
			eventInfoType.setEvent(EventDefinitionType.DISPATCHED_EVENT);
		} else if (AppraisalAssignmentConstants.DISPOSITION_COMPLETED.equals(disposition)) {
			eventInfoType.setEvent(EventDefinitionType.COMPLETED_EVENT);
			workAssignmentType.setStatus(WorkAssignmentStatusType.CLOSED);
		} else {
			eventInfoType.setEvent(EventDefinitionType.UPDATED_EVENT);
			workAssignmentType.setStatus(WorkAssignmentStatusType.OPENED);
		}

		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			logger.info("Created eventInfoType with disposition :" + disposition);
		}

		workAssignmentType.setEventInfo(eventInfoType);

		// disposition
		workAssignmentType.setDisposition(disposition);

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return workAssignmentDocument;
	}

	public void saveAssignmentBeenUpdatedFlag(final long workAssignmentTaskId, final String flag,
			final UserInfoDocument logedUserInfoDocument) throws MitchellException {

		final String METHOD_NAME = "saveAssignmentBeenUpdatedFlag";

		try {
			if (flag.equalsIgnoreCase("Y")) {

				workAssignmentProxy.saveAssignmentBeenUpdatedFlag(workAssignmentTaskId, "Y", logedUserInfoDocument);
			}
			if (logger.isLoggable(java.util.logging.Level.INFO)) {
				logger.info("Updated AssignmentHasBeen update flag");
			}
		} catch (final Exception exception) {
			final String msgDetail = "WorkAssignment TaskId ::" + workAssignmentTaskId;
			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_SAVEASSIGNMENTBEENUPDATEDFLAG, CLASS_NAME,
				METHOD_NAME, ErrorLoggingService.SEVERITY.FATAL,
				AppraisalAssignmentConstants.ERROR_SAVEASSIGNMENTBEENUPDATEDFLAG_MSG, msgDetail, exception,
				logedUserInfoDocument, null, null);
		}
	}

	/**
	 * This method is helper method which saves work assignment using
	 * WorkAssignment Service.
	 * @param documentId
	 * @param claimId
	 * @param claimExposureId
	 * @param claimMask
	 * @return WorkAssignment returns updated WorkAssignment
	 * @throws Exception
	 */
	public WorkAssignment saveWorkAssignment(final Long documentId, final long claimId, final long claimExposureId,
			final String claimMask, final String event, final AppraisalAssignmentDTO appraisalAssignmentDTO,
			final UserInfoDocument logdInUsrInfo, final HoldInfoType waHoldInfoType, final long moiOrgId) throws Exception {
		final String METHOD_NAME = "saveWorkAssignment";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(Level.FINE)) {
			final StringBuilder strBuf = new StringBuilder();
			strBuf.append("Input Received :: documentId: ").append(documentId).append("\t claimId: ").append(claimId)
					.append("\t claimExposureId: ").append(claimExposureId).append("\t claimMask: ").append(claimMask)
					.append("\t event: ").append(event);
			logger.fine(strBuf.toString());
		}

		WorkAssignmentDocument workAssignmentDocument = null;
		WorkAssignment workAssignment = null;
		String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;

		String workItemID = "";
		try {
			workAssignmentDocument = convertMitchellEnvelopeDocToWorkAssignmentDoc(documentId, claimId, claimExposureId,
				claimMask, event, appraisalAssignmentDTO, logdInUsrInfo);
			claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getClaimNumber();
			workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getWorkItemID();

			if (waHoldInfoType != null) {
				workAssignmentDocument.getWorkAssignment().setHoldInfo(waHoldInfoType);
			}
			if (AppraisalAssignmentConstants.MOI_ORG_ID_UNSET_IN_CLOB == moiOrgId) {
				if (workAssignmentDocument.getWorkAssignment().isSetManagedByOrgID())
					workAssignmentDocument.getWorkAssignment().unsetManagedByOrgID();
			} else {
				workAssignmentDocument.getWorkAssignment().setManagedByOrgID(moiOrgId);
			}
			final String timeZone = appraisalAssignmentDTO.getTimeZone();

			if (!("".equalsIgnoreCase(timeZone)) && (null != timeZone)) {
				final com.mitchell.schemas.workassignment.TimeZoneType.Enum timeZoneEnum = com.mitchell.schemas.workassignment.TimeZoneType.Enum
						.forString(timeZone);
				if (workAssignmentDocument.getWorkAssignment().getLocationInfo() != null && timeZoneEnum != null)
					workAssignmentDocument.getWorkAssignment().getLocationInfo().setLocationTimeZone(timeZoneEnum);
			}

			// Set SubType in WorkAssignment
			final String subType = appraisalAssignmentDTO.getSubType();
			if (subType != null && !"".equals(subType)) {
				workAssignmentDocument.getWorkAssignment().setSubType(subType);
			}

		} catch (final Exception exception) {

			final StringBuilder strBuf = new StringBuilder();
			strBuf.append("Input Received :: documentId: ").append(documentId).append("\t claimId: ").append(claimId)
					.append("\t claimExposureId: ").append(claimExposureId).append("\t claimMask: ").append(claimMask)
					.append("\t event: ").append(event);

			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_SAVE_AA_SAVEWA, CLASS_NAME, METHOD_NAME,
				ErrorLoggingService.SEVERITY.FATAL, AppraisalAssignmentConstants.ERROR_SAVE_AA_SAVEWA_MSG,
				strBuf.toString(), exception, logdInUsrInfo, workItemID, claimNumber);

		}

		workAssignment = saveWorkAssignment(workAssignmentDocument, logdInUsrInfo);

		logger.exiting(CLASS_NAME, METHOD_NAME);
		return workAssignment;
	}

	/**
	 * <p>
	 * This method is helper method which retrieves values from MitchellEnvelope
	 * Document, passes that information to WorkAssignmentMapper to convert
	 * MitchellEnvelope Document to WorkAssignment Document
	 * </p>
	 * @param documentId Document ID of EstimatePackage Service.
	 * @param claimId Claim ID of Claim Service.
	 * @param claimExposureId Claim Exposure ID of Claim Service.
	 * @param claimMask Carrier specific Claim Mask parsing Rule.
	 * @return WorkAssignment Document
	 * @throws Exception Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public WorkAssignmentDocument convertMitchellEnvelopeDocToWorkAssignmentDoc(final Long documentId, final long claimId,
			final long claimExposureId, final String claimMask, final String event,
			final AppraisalAssignmentDTO appraisalAssignmentDTO, final UserInfoDocument logdInUsrInfo) throws Exception {

		final String METHOD_NAME = "convertMitchellEnvelopeDocToWorkAssignmentDoc";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(java.util.logging.Level.FINE)) {
			final StringBuilder strBuf = new StringBuilder();
			strBuf.append("Input Received :: documentId: ").append(documentId).append("\t claimId: ").append(claimId)
					.append("\t claimExposureId: ").append(claimExposureId).append("\t claimMask: ").append(claimMask)
					.append("\t event: ").append(event);

			logger.fine(strBuf.toString());
		}

		String status = null;
		String disposition = null;
		WorkAssignmentDocument workAssignmentDocument = null;
		final MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(appraisalAssignmentDTO.getMitchellEnvelopDoc());

		disposition = appraisalAssignmentDTO.getDisposition();

		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			logger.info("Fetched Disposition from appraisalAssignmentDTO: " + disposition);
		}
		if (disposition == null || "".equals(disposition)) {
			throw new MitchellException(CLASS_NAME, "convertMitchellEnvelopeDocToWorkAssignmentDoc",
				"Received NULL disposition in AppraisalAssignmentDTO.");
		}

		status = appraisalAssignmentDTO.getStatus();

		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			logger.info("Fetched Status from appraisalAssignmentDTO: " + status);
		}

		if (status == null || "".equals(status)) {
			throw new MitchellException(CLASS_NAME, "convertMitchellEnvelopeDocToWorkAssignmentDoc",
				"Received NULL status in AppraisalAssignmentDTO.");
		}

		workAssignmentDocument = workAssignmentMapper.createworkAssignmentDoc(meHelper.getDoc(), claimId, claimExposureId,
			documentId, disposition, status, event, appraisalAssignmentDTO.getWaTaskId(), claimMask, logdInUsrInfo
					.getUserInfo().getUserID());

		if (workAssignmentDocument == null) {
			throw new MitchellException(CLASS_NAME, "convertMitchellEnvelopeDocToWorkAssignmentDoc",
				"Received NULL work assignment document from WorkAssignmentMapper. \nClaimID : " + claimId
						+ "\nClaimExposureID : " + claimExposureId + "\nDocumentID : " + documentId + "\nDisposition : "
						+ disposition + "\nStatus : " + status + "\nMitchellEnvelopeDocument : \n" + meHelper.getDoc());
		}

		if (logger.isLoggable(java.util.logging.Level.FINE)) {
			logger.fine("Coverted ME Doc to workAssignmentDocument, workAssignmentDocument is : " + workAssignmentDocument);
		}

		// Update ReqAssociateDataCompletedInd in workAssignmentDocument i.e
		// WorkAssignment/ReqAssociateDataCompletedInd
		workAssignmentDocument.getWorkAssignment().setReqAssociateDataCompletedInd(
			appraisalAssignmentDTO.getReqAssociateDataCompletedInd());

		workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getWorkItemID();
		workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getClaimNumber();

		if (appraisalAssignmentDTO.getEventReasonCode() != null && !"".equals(appraisalAssignmentDTO.getEventReasonCode())) {
			workAssignmentDocument.getWorkAssignment().setEventReasonCode(appraisalAssignmentDTO.getEventReasonCode());
			// Populate Event Memo when reason code is other
			if (appraisalAssignmentDTO.getEventMemo() != null && !"".equals(appraisalAssignmentDTO.getEventMemo())) {
				workAssignmentDocument.getWorkAssignment().getEventInfo()
						.setEventMemo(appraisalAssignmentDTO.getEventMemo());
			}
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return workAssignmentDocument;
	}

	/**
	 * Method return AssigneeId from WorkAssignmentDocument.
	 * @param workAssignmentDocument WorkAssignmentDocument.
	 * @return String AssigneeId.
	 */
	public String getAssigneeIdFromWorkAssignmentDocument(final WorkAssignmentDocument workAssignmentDocument) {
		final String METHOD_NAME = "getAssigneeIdFromWorkAssignmentDocument";
		logger.entering(CLASS_NAME, METHOD_NAME);
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Input Received ::\n" + workAssignmentDocument.toString());
		}
		String assigneeId = null;
		if (workAssignmentDocument.getWorkAssignment().getCurrentSchedule().isSetAssigneeID()) {
			assigneeId = workAssignmentDocument.getWorkAssignment().getCurrentSchedule().getAssigneeID();
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return assigneeId;
	}

	/**
	 * Method return GroupCode from CIECADocument.
	 * @param workAssignmentDocument WorkAssignmentDocument.
	 * @return String GroupCode.
	 */
	public String getGroupCodeFromWorkAssignmentDocument(final WorkAssignmentDocument workAssignmentDocument) {
		final String METHOD_NAME = "getGroupCodeFromWorkAssignmentDocument";
		logger.entering(CLASS_NAME, METHOD_NAME);
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Input Received ::\n" + workAssignmentDocument.toString());
		}
		String groupCode = null;
		if (workAssignmentDocument.getWorkAssignment().getPrimaryIDs().isSetGroupID()) {
			groupCode = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getGroupID();
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return groupCode;
	}

	/**
	 * Method return scheduleDateTime from AdditionalTaskConstraintsDocument.
	 * @param workAssignmentDocument WorkAssignmentDocument.
	 * @return Calendar scheduleDateTime.
	 */
	public Calendar getScheduleDateTime(final WorkAssignmentDocument workAssignmentDocument) {
		final String METHOD_NAME = "getScheduleDateTime";
		logger.entering(CLASS_NAME, METHOD_NAME);
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Input Received ::\n" + workAssignmentDocument.toString());
		}
		Calendar scheduleDateTime = null;
		if (workAssignmentDocument.getWorkAssignment().getCurrentSchedule().isSetScheduleStartDateTime()) {
			scheduleDateTime = workAssignmentDocument.getWorkAssignment().getCurrentSchedule().getScheduleStartDateTime();
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return scheduleDateTime;
	}

	/**
	 * Method is used for updating the WorkAssignmentDocument with
	 * DispatchCenter in WorkAssignmentService
	 * @param dispatchCenter dispatchCenter.
	 * @param workAssignmentDocument.
	 * @return WorkAssignmentDocument workAssignmentDocument.
	 */

	public WorkAssignment updateGroupIdInWorkAssignment(final String dispatchCenter,
			final UserInfoDocument loggedInUserInfoDocument, final WorkAssignmentDocument workAssignmentDocument)
			throws MitchellException {
		final String METHOD_NAME = "updateGroupIdInWorkAssignment";
		logger.entering(CLASS_NAME, METHOD_NAME);
		WorkAssignment workAssignment = new WorkAssignment();
		try {
			final WorkAssignmentDocument updatedWorkAssignmentDocument = setupWorkAssignmentRequest(workAssignmentDocument,
				workAssignmentDocument.getWorkAssignment().getDisposition(), METHOD_NAME, loggedInUserInfoDocument);
			final WorkAssignmentType workAssignmentType = updatedWorkAssignmentDocument.getWorkAssignment();
			// Setting the GroupID
			if (dispatchCenter != null && !"".equalsIgnoreCase(dispatchCenter))
				workAssignmentType.getPrimaryIDs().setGroupID(dispatchCenter);

			workAssignment = saveWorkAssignment(updatedWorkAssignmentDocument, loggedInUserInfoDocument);

		} catch (final MitchellException saveWorkAssignmentexception) {
			final String errorMessage = "Error while saving the WorkAssignmentDocument with GroupID in WorkAssignmentService";
			throw new MitchellException(CLASS_NAME, METHOD_NAME, errorMessage, saveWorkAssignmentexception);
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return workAssignment;
	}

	public WorkAssignment saveUpdateWorkAssignment(WorkAssignmentDocument workAssignmentDocument,
			UserInfoDocument logdInUsrInfo) throws RemoteException, MitchellException {
		logger.entering(CLASS_NAME, "saveUpdateWorkAssignment");

		if (logger.isLoggable(Level.FINE)) {
			StringBuffer localMethodParams = new StringBuffer();
			localMethodParams.append("workAssignmentDocument : " + workAssignmentDocument.toString());
			logger.fine("Input Received ::\n" + localMethodParams);
		}

		WorkAssignment workAssignment = workAssignmentProxy.save(workAssignmentDocument);

		if (workAssignment == null) {
			throw new MitchellException(CLASS_NAME, "saveUpdateWorkAssignment",
				"Received NULL work assignment from Work Assignment Service: ");
		}

		if (!"LUNCH".equalsIgnoreCase(workAssignmentDocument.getWorkAssignment().getType())) {
			Long documentId = workAssignment.getReferenceId();
			if (documentId == null) {
				throw new MitchellException(CLASS_NAME, "saveUpdateWorkAssignment",
					"Received NULL DocumentId from Work Assignment Service: ");
			}
		}

		if (logger.isLoggable(Level.INFO)) {
			logger.info("Saved WorkAssignment through WorkAssignment Service");
		}

		logger.exiting(CLASS_NAME, "saveUpdateWorkAssignment");
		return workAssignment;
	}

	/**
	 * save Reschedule or Incomplete WorkAssignment
	 * @param workAssignmentDocument Work Assignment Document object of
	 *            WorkAssignment Service.
	 * @param disposition Disposition to set.
	 * @return updated <code>WorkAssignment</code> object.
	 * @throws Exception Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public WorkAssignment saveRescheduleOrIncompleteWorkAssignment(WorkAssignment workAssignment, String reasonCode,
			String reasonNotes, UserInfoDocument logdInUsrInfo, String disposition) throws MitchellException {
		final String METHOD_NAME = "saveRescheduleOrIncompleteWorkAssignment";
		logger.entering(CLASS_NAME, METHOD_NAME);

		String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
		String workItemID = "";

		try {
			String workAssignmentCLOBB = workAssignment.getWorkAssignmentCLOBB();

			if (logger.isLoggable(java.util.logging.Level.FINE)) {
				StringBuilder localMethodParams = new StringBuilder();
				localMethodParams.append("workAssignment : " + workAssignment.getWorkAssignmentCLOBB());
				logger.fine("Input Received ::\n" + localMethodParams);
			}

			WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory.parse(workAssignmentCLOBB);
			claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getClaimNumber();
			workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getWorkItemID();
			WorkAssignmentType workAssignmentType = workAssignmentDocument.getWorkAssignment();

			// Checking reasonCode
			if (reasonCode != null && !"".equalsIgnoreCase(reasonCode)) {
				workAssignmentType.setEventReasonCode(reasonCode);
			}

			// Checking comment
			if (reasonNotes != null && !"".equalsIgnoreCase(reasonNotes)) {
				workAssignmentType.getEventInfo().setEventMemo(reasonNotes);
			}

			if (workAssignmentType.getEventInfo() != null) {
				workAssignmentType.getEventInfo().setEvent(EventDefinitionType.UPDATED_EVENT);
			}

			/*
			 * setting disposition & status
			 */
			workAssignmentType.setDisposition(disposition);

			if (logger.isLoggable(Level.FINE)) {
				logger.fine("Saving workAssignmentDocument ::\n" + workAssignmentDocument);
			}

			workAssignment = saveWorkAssignment(workAssignmentDocument, logdInUsrInfo);

			if (logger.isLoggable(java.util.logging.Level.INFO)) {
				logger.info("save Incomplete or Reschedule WorkAssignment: " + disposition);
			}

		} catch (Exception e) {
			final StringBuilder msgDetail = new StringBuilder();
			msgDetail.append("\tWorkIemId : ").append(workItemID).append("\tClaimNumber : ").append(claimNumber);

			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_RESCHEDULE_OR_INCOMPLETE_AA_SAVEWA, CLASS_NAME,
				METHOD_NAME, ErrorLoggingService.SEVERITY.FATAL,
				AppraisalAssignmentConstants.ERROR_RESCHEDULE_OR_INCOMPLETE_AA_SAVEWA_MSG, msgDetail.toString(), e,
				logdInUsrInfo, workItemID, claimNumber);
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);

		return workAssignment;
	}
}
