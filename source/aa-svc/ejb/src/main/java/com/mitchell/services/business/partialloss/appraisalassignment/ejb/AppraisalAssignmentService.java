package com.mitchell.services.business.partialloss.appraisalassignment.ejb;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.apache.xmlbeans.XmlObject;

import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.ItineraryViewDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentDeliveryServiceDTO;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;

public interface AppraisalAssignmentService {

	@PostConstruct
	public void initialize();

	/**
	 * <p>
	 * This method retrieves a Supplement Request Doc in XML format for use as
	 * Email Notification or UI Print-Preview
	 * </p>
	 * 
	 * @param estimateDocId
	 *            Estimate DocID for original Estimate.
	 * @param estimatorOrgId
	 *            OrgId of the estimator
	 * @param reviewerOrgId
	 *            OrgId of the reviewer
	 * @return Supplement Request Doc in XML format.
	 * @throws MitchellException
	 * 
	 */
	public String retrieveSupplementRequestXMLDoc(final long estimateDocId,
			final long estimatorOrgId, final long reviewerOrgId)
			throws MitchellException;

	/**
	 * This method cancels multiple assignments. This methods internally invokes
	 * the single cancelAppraisalAssignment method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param cancellationReason
	 *            - cancellationReason object
	 * @param notes
	 *            - notes object
	 * @param assignorUserInfoDocument
	 *            - assignorUserInfoDocument object
	 * @return HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public java.util.HashMap cancelMultipleAppraisalAssignments(
			final HashMap workAssignmentTaskID_TCN,
			final String cancellationReason, final String notes,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException;

	/**
	 * This method accepts EstimateDocumentID and calls CARRHelper to Update
	 * Review Assignment for Supplement case.
	 * 
	 * @param relatedEstimateDocumentId
	 * @throws MitchellException
	 * 
	 *
	 */
	public void updateReviewAssignment(long relatedEstimateDocumentId,
			UserInfoDocument estimatorUserInfo, UserInfoDocument logdInUsrInfo)
			throws MitchellException;

	/**
	 * This method uncancels multiple assignments. This methods internally
	 * invokes the single uncancelAppraisalAssignment method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param assignorUserInfoDocument
	 *            - assignorUserInfoDocument object
	 * @return HashMap - HashMap object
	 * @throws MitchellException
	 *             - Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public java.util.HashMap unCancelMultipleAppraisalAssignments(
			final HashMap workAssignmentTaskID_TCN,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException;

	/**
	 * This method Assign/ReAssign with Assignee id and ScheduleInformation from
	 * Dispatch Board This methods internally invokes the single
	 * assignScheduleAppraisalAssignment_Requires_NewTX method
	 * 
	 * @param scheduleInfoXMLDocument
	 *            - XmlObject object of ScheduleInfoXMLDocument.
	 * @param assignorUserInfoDocument
	 *            - assignorUserInfoDocument object
	 * @return java.util.HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public java.util.HashMap assignScheduleResourceToMultipleAssignments(
			final XmlObject scheduleInfoXmlObject,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException;

	
	/**
	 * This method getExpertiseSkillsByVehicleType return the expertise based on the Vehicle Type
	 * 
	 * @param vehicleType
	 *            - String.
	 * @return String - String object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	  public String getExpertiseSkillsByVehicleType(
		      final String vehicleType)
		      throws MitchellException;
	
	/**
	 * Unschedule Assignments with workassignmenttaskIds_TCN and
	 * assignorInfoDocument from Dispatch Board This methods internally invokes
	 * the single unScheduleAppraisalAssignment_Requires_NewTX method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param assignorUserInfoDocument
	 *            - assignorUserInfoDocument object
	 * @return java.util.HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public java.util.HashMap unScheduleMultipleAppraisalAssignments(
			final HashMap workAssignmentTaskID_TCN,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException;

	/**
	 * onHoldAppraisalAssignment with workassignmenttaskIds_TCN ,
	 * selectedOnHoldTypeFromCarrier and assignorInfoDocument from Dispatch
	 * Board This methods internally invokes the single
	 * onHoldAppraisalAssignment_Requires_NewTX method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param selectedOnHoldTypeFromCarrier
	 *            - selectedOnHoldTypeFromCarrier object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @param notes
	 *            - notes object
	 * @return HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public java.util.HashMap onHoldMultipleAppraisalAssignments(
			final HashMap workAssignmentTaskID_TCN,
			final String selectedOnHoldTypeFromCarrier, final String notes,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * This method internally calls the removeOnHoldAppraisalAssignment method
	 * to remove an AppraisalAssignment from hold.
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param TCN
	 *            - TCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int removeOnHoldAppraisalAssignment_Requires_NewTX(
			final long workAssignmentTaskID, final long TCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * removeOnHoldMultipleAppraisalAssignments with workassignmenttaskIds_TCN
	 * and assignorInfoDocument from Dispatch Board This methods internally
	 * invokes the single removeOnHoldAppraisalAssignment_Requires_NewTX method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return java.util.HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public java.util.HashMap removeOnHoldMultipleAppraisalAssignments(
			final HashMap workAssignmentTaskID_TCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 
	 * This method dispatches multiple assignments for Jetta This method
	 * internally invokes the single dispatchAppraisalAssignment
	 * @deprecated This method has been replaced by {@link use this method #HashMap dispatchMultipleAppraisalAssignments(HashMap workAssignmentTaskID_TCN, UserInfoDocument assignorUserInfoDocument))}
	 
	 */

	public java.util.HashMap dispatchMultipleAppraisalAssignments(
			long[] workAssignmentTaskID,
			UserInfoDocument assignorUserInfoDocument) throws MitchellException;

	/**
	 * This method dispatches multiple assignments from the Dispatch Board. This
	 * methods internally invokes the single
	 * dispatchAppraisalAssignment_Requires_NewTX method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param assignorUserInfoDocument
	 *            - assignorUserInfoDocument object
	 * @return HashMap - HashMap object
	 * @throws MitchellException
	 *             - Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public HashMap dispatchMultipleAppraisalAssignments(
			final HashMap workAssignmentTaskID_TCN,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method is responsible for canceling of Appraisal Assignment from
	 * dispatch board. This method does following:
	 * </p>
	 * <ul>
	 * <li>Retrieves work assignment using taskID from Work Assignment Service.</li>
	 * <li>Updates work assignment with CANCELLED request and cancellation
	 * reason.</li>
	 * <li>Saves updated work assignment to Work Assignment Service.</li>
	 * <li>Creates Claim Activity Log.</li>
	 * </ul>
	 * 
	 * @param workAssignmentTaskID
	 *            WorkAssignmentTaskID of Work Assignment Service.
	 * @param cancellationReason
	 *            cancellation reason for cancelling Work Assignment.
	 * @param TCN
	 *            TCN from request to check stale data.
	 * @param loggedInUserInfoDocument
	 *            UserInfo Document of Logged In user.
	 * @param notes
	 *            notes
	 * @return <code>true</code> if successfully processed the request and
	 *         <code>false</code> if unsuccessfully processed.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int cancelAppraisalAssignment(final long workAssignmentTaskID,
			final String cancellationReason, final long TCN,
			final String notes, final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method retrieves latest estimate for a claim number. Method calls
	 * EstimatePackage Service to retrieve all available estimates. Then it
	 * finds latest estimate and returns to caller.
	 * </p>
	 * 
	 * @param insuranceCarrierCoCode
	 *            Mitchell Company code of Insurance Company.
	 * @param clientClaimNumber
	 *            Claim Number
	 * @param estimatorUserInfoDocument
	 *            Estimator's UserInfo Document
	 * @return latest Estimate object.
	 * @throws MitchellException
	 * 
	 */
	public Estimate getLatestEstimate(final String insuranceCarrierCoCode,
			final String clientClaimNumber,
			final UserInfoDocument estimatorUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method retrieves latest estimate for a claim number. Method calls
	 * EstimatePackage Service to retrieve all available estimates. Then it
	 * finds latest estimate and returns to caller.
	 * </p>
	 *
	 * @param insuranceCarrierCoCode
	 *            Mitchell Company code of Insurance Company.
	 * @param clientClaimNumber
	 *            Claim Number
	 * @return latest Estimate object.
	 * @throws MitchellException
	 *
	 */
	public Estimate getLatestEstimateNoFiltering(final String insuranceCarrierCoCode,
									  final String clientClaimNumber)
			throws MitchellException;

	/**
	 * <p>
	 * This method is responsible for create and/or save of Appraisal
	 * Assignment. This method does following major tasks:
	 * </p>
	 * <ul>
	 * <li>Create/Save Claim information base on rules to Claim Service.</li>
	 * <li>Create work assignment using WorkAssignment Service</li>
	 * <li>Save MitchellEnvelope Document to EstimatePackage Service.</li>
	 * <li>Update Work Assignment.</li>
	 * <li>Create Claim-suffix Activity Log.</li>
	 * </ul>
	 * 
	 * @param inputAppraisalAssignmentDTO
	 *            Object of AppraisalAssignmentDTO.
	 * @param logedInUserInfoDocument
	 *            UserInfo Document of Logged in User.
	 * @return Updated AppraisalAssignmentDTO which has latest MitchellEnvelope
	 *         Document along with other data like TCN, WorkAssignmentTaskID,
	 *         DocumentID etc.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public AppraisalAssignmentDTO saveAppraisalAssignment(
			final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
			final UserInfoDocument logedInUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method performs business-rule validation of the input
	 * MitchellEnvelopeDoc to verify that Appraisal Assignment IsReady for
	 * Dispatch
	 * </p>
	 * <ul>
	 * <br>
	 * ** NOTE1: <br>
	 * ** Mandatory field business-rule validation is based on comparison of XML
	 * fields <br>
	 * ** in the provided Appraisal Assignment MitchellEnvelope with the <br>
	 * ** WCAA Company-Level Custom Settings for confirmation that all <br>
	 * ** required/mandatory fields are present. <br>
	 * ** NOTE2: <br>
	 * ** Additionally, the Appraisal Assignment Svc Custom Setting
	 * DISPATCH_WITH_INVALID_ADDRESS <br>
	 * ** is referenced to provide an override option for dispatching
	 * Carrier-Feed Assignments <br>
	 * ** even for the case of an Invalid/Missing InspectionSite Address
	 * </ul>
	 * 
	 * @param mitchellEnvDoc
	 *            Object of MitchellEnvelopeDocument
	 * @param loggedInUserInfoDocument
	 *            UserInfo Document of Logged In user.
	 * @return boolean - returns TRUE if all mandatory fields are present and
	 *         Vehicle Location Business rule is TRUE
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 * 
	 */
	public boolean isAssignmentDataReady(
			final MitchellEnvelopeDocument mitchellEnvDoc)
			throws MitchellException;

	/**
	 * This method checks whether the Mandatory/Required fields are filled up or
	 * not
	 * 
	 * @param mandatoryFieldFlag
	 * @param assigneeID
	 * @param groupCode
	 * @param scheduleDateTime
	 * @return String
	 * 
	 */
	public String isAssignmentReadyForDispatch(
			final boolean mandatoryFieldFlag, final String assigneeID,
			final String groupCode, final java.util.Calendar scheduleDateTime,
			final UserInfoDocument userInfoDocument) throws MitchellException;

	/**
	 * This method performs AssignmentHistory Logging
	 * 
	 * @param eventName
	 * @param eventDescription
	 * @param workAssignment
	 * @throws MitchellException
	 * @throws Exception
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 * 
	 */
	public void writeAssignmentActivityLog(final long assignmentId,
			final String eventName, final String eventDescription,
			final String createdBy) throws MitchellException;

	/**
	 * <p>
	 * This method is responsible for Uncancelling the Appraisal Assignment.
	 * This method does following:
	 * </p>
	 * <ul>
	 * <li>Retrieves work assignment using taskID from Work Assignment Service.</li>
	 * <li>Updates work assignment with NOT READY/UNCANCELLED request.</li>
	 * <li>Saves updated work assignment to Work Assignment Service.</li>
	 * <li>Retrieves MitchellEnvelope from EstimatePackage Service and removes
	 * Estimator information and saves MitchellEnvelope to EstimatePackage
	 * Service</li>
	 * </ul>
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param requestTCN
	 *            - requestTCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public boolean uncancelAppraisalAssignment(final long workAssignmentTaskID,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * 
	 * <p>
	 * 
	 * This method is responsible for dispatching of Appraisal Assignment. This
	 * 
	 * method does following:
	 * 
	 * </p>
	 * 
	 * <ul>
	 * 
	 * <li>Retrieves and updates Work Assginment to set disposition to
	 * 
	 * "Dispatched".</li>
	 * 
	 * <li>Creates Claim Activity Log.</li>
	 * 
	 * </ul>
	 * 
	 * 
	 * 
	 * @param workAssignmentTaskId
	 * 
	 *            WorkAssignmentTaskID of Work Assignment Service.
	 * 
	 * @param loggedInUserInfoDocument
	 * 
	 *            UserInfo Document of Logged In user.
	 * 
	 * @return <code>true</code> if successfully processed the request and
	 * 
	 *         <code>false</code> if unsuccessfully processed.
	 * 
	 * @throws MitchellException
	 * 
	 *             Throws MitchellException to the caller in case of any
	 * 
	 *             exception arise.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @deprecated This method has been replaced by {@link use this method #int
	 *             dispatchAppraisalAssignment()}
	 */
	public boolean dispatchAppraisalAssignment(final long workAssignmentTaskID,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method is responsible for dispatching supplement Appraisal
	 * Assignment. This method internally calls dispatchAppraisalAssignment()
	 * method.
	 * </p>
	 * 
	 * @param workAssignmentTaskId
	 *            WorkAssignmentTaskID of Work Assignment Service.
	 * @param loggedInUserInfoDocument
	 *            UserInfo Document of Logged In user.
	 * @return <code>true</code> if successfully processed the request and
	 *         <code>false</code> if unsuccessfully processed.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public boolean dispatchSupplementAppraisalAssignment(
			final long workAssignmentTaskID, final long estimateDocId,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method is responsible for updating Appraisal Assignment. This method
	 * does following major tasks:
	 * </p>
	 * <ul>
	 * <li>Retrieves Claim Mask for carrier.</li>
	 * <li>Retrieves work assignment from WorkAssignment Service.</li>
	 * <li>Updates MitchellEnvelope using EstimatePackage Service</li>
	 * <li>Saves updated Work Assignment in WorkAssignment Service.</li>
	 * <li>Creates Claim-Suffix Activity Log.</li>
	 * </ul>
	 * 
	 * @param inputAppraisalAssignmentDTO
	 *            Object of AppraisalAssignmentDTO.
	 * @param logedUserInfoDocument
	 *            UserInfo Document of Logged in User.
	 * @return Updated AppraisalAssignmentDTO which has latest MitchellEnvelope
	 *         Document along with other data like TCN, WorkAssignmentTaskID,
	 *         DocumentID etc.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public AppraisalAssignmentDTO updateAppraisalAssignment(
			final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
			final UserInfoDocument logedUserInfoDocument)
			throws MitchellException;

	/**
	 * This method creates/updates the LuchType assignment.
	 * 
	 * @param taskDocumentXmlObject
	 *            of TaskDocument type.
	 * @param assignorUserInfoDocument
	 * @throws MitchellException
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 * 
	 */
	public long saveLunchAssignmentType(final XmlObject taskDocumentXmlObject,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method retrieves a Supplement Request Doc (text) for use as Email
	 * Notification or UI Print-Preview
	 * </p>
	 * 
	 * @param estimateDocId
	 *            Estimate DocID for original Estimate.
	 * @param estimatorOrgId
	 *            OrgId of the estimator
	 * @param reviewerOrgId
	 *            OrgId of the reviewer
	 * @return Supplement Request Doc (text).
	 * @throws MitchellException
	 * 
	 */
	public String retrieveSupplementRequestDoc(final long estimateDocId,
			final long estimatorOrgId, final long reviewerOrgId)
			throws MitchellException;

	/**
	 * <p>
	 * This interface method that generates/retrieves a Supplement Request HTML
	 * document from required inputs (a) an Estimate Document Id and (b) a pair
	 * of UserInfo Ids provided in the input AssignmentServiceContext asgSvcCtx
	 * </p>
	 * 
	 * @param context
	 *            Assignment Delivery - AssignmentServiceContext object
	 * @param workItemId
	 *            workItemID for this workflow instance
	 * 
	 * @return Returns Supplement Request HTML document
	 *         (MitchellEnvelopeDocument)
	 * @throws MitchellException
	 * 
	 */
	public MitchellEnvelopeDocument retrieveSupplementRequestXMLDocAsMEDoc(
			final AssignmentDeliveryServiceDTO asgSvcCtx, final String workItemId)
			throws MitchellException;

	/**
	 * <p>
	 * This method internally calls the uncancelAppraisalAssignment method for
	 * Uncancelling the Appraisal Assignment.
	 * </p>
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param requestTCN
	 *            - requestTCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int uncancelAppraisalAssignment_Requires_NewTX(
			final long workAssignmentTaskID, final long requestTCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method is responsible for Uncancelling the Appraisal Assignment.
	 * This method does following:
	 * </p>
	 * <ul>
	 * <li>Retrieves work assignment using taskID from Work Assignment Service.</li>
	 * <li>Updates work assignment with NOT READY/UNCANCELLED request.</li>
	 * <li>Saves updated work assignment to Work Assignment Service.</li>
	 * <li>Retrieves MitchellEnvelope from EstimatePackage Service and removes
	 * Estimator information and saves MitchellEnvelope to EstimatePackage
	 * Service</li>
	 * </ul>
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param requestTCN
	 *            - requestTCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int uncancelAppraisalAssignment(final long workAssignmentTaskID,
			final long requestTCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method internally calls the cancelAppraisalAssignment method for
	 * cancelling the Appraisal Assignment.
	 * </p>
	 * 
	 * @param workAssignmentTaskID
	 *            WorkAssignmentTaskID of Work Assignment Service.
	 * @param cancellationReason
	 *            cancellation reason for cancelling Work Assignment.
	 * @param TCN
	 *            TCN from request to check stale data.
	 * @param loggedInUserInfoDocument
	 *            UserInfo Document of Logged In user.
	 * @param notes
	 *            notes
	 * @return <code>true</code> if successfully processed the request and
	 *         <code>false</code> if unsuccessfully processed.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int cancelAppraisalAssignment_Requires_NewTX(
			final long workAssignmentTaskID, final String cancellationReason,
			final long TCN, final String notes,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method internally calls the assignScheduleAppraisalAssignment method
	 * for assigning assignee to Appraisal Assignment
	 * </p>
	 * 
	 * @param assignTaskXmlObject
	 *            - XmlObject object of AssignTaskType containing schedule info
	 * @param loggedInUserInfoDocument
	 *            - UserInfoDocument for logged in user
	 * @return <code>0</code> if successfully processed the request and
	 *         <code>1</code> if unsuccessfully processed. <code>2</code> if
	 *         stale data.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int assignScheduleAppraisalAssignment_Requires_NewTX(
			final XmlObject assignTaskXmlObject,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method is responsible for assigning assignee to Appraisal Assignment
	 * from dispatch board. This method does following major tasks:
	 * </p>
	 * <ul>
	 * <li>Retrieves assignee's user information from UserInfo Service.</li>
	 * <li>Retrieves, updates and saves work assignment to WorkAssignment
	 * Service.</li>
	 * <li>Saves updates MitchellEnvelope to EstimatePackage Service</li>
	 * <li>Creates Claim-Suffix Activity Log.</li>
	 * </ul>
	 * 
	 * @param assignTaskXmlObject
	 *            - XmlObject object of AssignTaskType containing schedule info
	 * @param loggedInUserInfoDocument
	 *            - UserInfoDocument for logged in user
	 * @return <code>0</code> if successfully processed the request and
	 *         <code>1</code> if unsuccessfully processed. <code>2</code> if
	 *         stale data.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int assignScheduleAppraisalAssignment(
			final XmlObject assignTaskXmlObject,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * This method internally calls the onHoldAppraisalAssignment method to put
	 * AppraisalAssignment on hold.
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param TCN
	 *            - TCN object
	 * @param selectedOnHoldTypeFromCarrier
	 *            - selectedOnHoldTypeFromCarrier object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @param notes
	 *            - notes object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int onHoldAppraisalAssignment_Requires_NewTX(
			final long workAssignmentTaskID, final long TCN,
			final String selectedOnHoldTypeFromCarrier, final String notes,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * onHoldAppraisalAssignment each Assignment with workassignmenttaskId ,TCN
	 * , selectedOnHoldTypeFromCarrier and assignorInfoDocument from Dispatch
	 * Board
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param TCN
	 *            - TCN object
	 * @param selectedOnHoldTypeFromCarrier
	 *            - selectedOnHoldTypeFromCarrier object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @param notes
	 *            - notes object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int onHoldAppraisalAssignment(final long workAssignmentTaskID,
			final long TCN, final String selectedOnHoldTypeFromCarrier,
			final String notes, final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * removeOnHoldAppraisalAssignment each Assignment with workassignmenttaskId
	 * ,TCN and assignorInfoDocument from Dispatch Board
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param TCN
	 *            - TCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int removeOnHoldAppraisalAssignment(final long workAssignmentTaskID,
			final long TCN, final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * Unschedule each Assignment with workassignmenttaskId ,TCN and
	 * assignorInfoDocument from Dispatch Board This method internally calls the
	 * unScheduleAppraisalAssignment method.
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param requestTCN
	 *            - requestTCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int unScheduleAppraisalAssignment_Requires_NewTX(
			final long workAssignmentTaskID, final long requestTCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * Unschedule each Assignment with workassignmenttaskId ,TCN and
	 * assignorInfoDocument from Dispatch Board
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param requestTCN
	 *            - requestTCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public int unScheduleAppraisalAssignment(final long workAssignmentTaskID,
			final long requestTCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * This method dispatches a single assignment.
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param TCN
	 *            - TCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int dispatchAppraisalAssignment(final long workAssignmentTaskID,
			final long TCN, final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * This method dispatches a single assignment. This method internally calls
	 * the dispatchAppraisalAssignment method.
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param TCN
	 *            - TCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int dispatchAppraisalAssignment_Requires_NewTX(
			final long workAssignmentTaskID, final long TCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * Updates the Disposition in WorkAssignment and performs Claim Activity &
	 * Assignment Activity Logging
	 * 
	 * @param workAssignmentTaskID
	 * @param newDispositionCode
	 *            Supports IN_PROGRESS, REJECTED, CANCELLED, COMPLETED
	 * @param reasonCode
	 * @param comment
	 * @param requestTCN
	 * @param loggedInUserInfoDocument
	 * @return int value would be zero for SUCCESS, ONE for FAILURE, TWO for
	 *         STALE DATA
	 * @throws Exception
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public int assignmentStatusUpdate(final long workAssignmentTaskID,
			final String newDispositionCode, final String reasonCode,
			final String comment, final long requestTCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;
	
	/**
	 * Updates ItineraryView Disposition in WorkAssignment and performs Claim Activity &
	 * Assignment Activity Logging
	 * 
	 * @param workAssignmentTaskID
	 * @param newDispositionCode
	 *            Supports IN_PROGRESS, REJECTED, CANCELLED, COMPLETED
	 * @param reasonCode
	 * @param comment
	 * @param requestTCN
	 * @param loggedInUserInfoDocument
	 * @return int value would be zero for SUCCESS, ONE for FAILURE, TWO for
	 *         STALE DATA
	 * @throws Exception
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public int assignmentStatusUpdateItineraryView(XmlObject ItineraryXML,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;
	
	

	/**
	 * Updates the Disposition in WorkAssignment without any Logging.
	 * 
	 * @param workAssignmentTaskID
	 * @param newDispositionCode
	 * @param reasonCode
	 * @param comment
	 * @param requestTCN
	 * @param loggedInUserInfoDocument
	 * @return int value would be zero for SUCCESS, ONE for FAILURE, TWO for
	 *         STALE DATA
	 * @throws Exception
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public int workAssignmentStatusUpdate(final long workAssignmentTaskID,
			final String newDispositionCode, final String reasonCode,
			final String comment, final long requestTCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * This method cancel a request of Supplement Assigment for a cartain
	 * claim-suffix number
	 * 
	 * @param claimSuffixNumber
	 *            it contains calim_number-suffix-number
	 * @param reviewerUserInfo
	 *            it contains UserInfoDocument object with DRP user info
	 * 
	 * @throws MitchellException
	 * 
	 */

	public void cancelSupplementTask(final String claimSuffixNumber,
			final UserInfoDocument bodyShopUserInfo, final String note,
			final String reviewCoCd, final String reviewUserId)
			throws MitchellException;

	/**
	 * This method creates a request of Supplement Assignment on a certain
	 * claim-suffix number
	 * 
	 * 
	 * @param claimSuffixNumber
	 *            calim_number-suffix-number
	 * @param reviewerUserInfo
	 *            UserInfoDocument object with DRP user info
	 * @param workItemId
	 *            work item id (PAUH has the info)
	 * 
	 * @throws MitchellException
	 * 
	 */
	public void createSupplementTask(final String claimSuffixNumber,
			final UserInfoDocument bodyShopUserInfo, final String workItemId,
			final String note, final String reviewCoCd,
			final String reviewUserId) throws MitchellException;

	/**
	 * This method creates a request of Supplement Assignment on a certain
	 * claim-suffix number
	 * 
	 * 
	 * @param claimSuffixNumber
	 *            calim_number-suffix-number
	 * @param reviewerUserInfo
	 *            UserInfoDocument object with DRP user info
	 * @param workItemId
	 *            work item id (PAUH has the info)
	 * 
	 * @throws MitchellException
	 * 
	 */
	public void createAssignSupplementTaskToNCRTUSer(final String claimSuffixNumber,
			final UserInfoDocument bodyShopUserInfo, final String workItemId,
			final String note, final String reviewCoCd,
			final String reviewUserId) throws MitchellException;
	
	
	
	/**
	 * This method reject the request of Suplement Assignment
	 * 
	 * @param taskID
	 *            task ID of the request.
	 * @param estimatorUserInfo
	 *            the WC user
	 * 
	 * @exception MitchellException
	 * 
	 */
	public void rejectSupplementTask(final long taskID,
			final UserInfoDocument estimatorUserInfo) throws MitchellException;

	/**
	 * 
	 * 
	 * @exception MitchellException
	 * 
	 */
	public void addVehLctnTrackHist(final Long claimSuffixID,
			final String vehicleTrackingStatus, final String companyCode,
			final String reviewerId) throws MitchellException;

	/**
	 * <p>
	 * This method is responsible for save/save&send/save&dispatch of AppraisalAssignment from New Assignemnt Page. This method does following major tasks:
	 * </p>
	 * <ul>
	 * <li>Create work assignment using WorkAssignment Service</li>
	 * <li>Save MitchellEnvelope Document to EstimatePackage Service.</li>
	 * <li>Dispatch Work Assignment for save & dispatch fucntionality.</li>
	 * <li>Create Claim-suffix Activity Log.</li>
	 * <li>Does App Log.</li>
	 * </ul>
	 * 
	 * @param inputAppraisalAssignmentDTO
	 *            Object of AppraisalAssignmentDTO.
	 * @param logedInUserInfoDocument
	 *            UserInfo Document of Logged in User.
	 * @return Updated AppraisalAssignmentDTO.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 *
	 */
	public AppraisalAssignmentDTO saveSSOAppraisalAssignment(
			final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
			final UserInfoDocument logedInUserInfoDocument)
			throws MitchellException;
	
	/**
	 * <p>
	 * This method is responsible for save/save&send/save&dispatch of AppraisalAssignment:
	 * </p>
	 * <ul>
	 * <li>Create Claim-suffix if createClaimIfNeeded and claim/suffix doesn't exist.</li>
	 * <li>Create work assignment using WorkAssignment Service</li>
	 * <li>Save MitchellEnvelope Document to EstimatePackage Service.</li>
	 * <li>Dispatch Work Assignment for save & dispatch fucntionality.</li>
	 * <li>Create Claim-suffix Activity Log.</li>
	 * <li>Does App Log.</li>
	 * </ul>
	 * 
	 * @param inputAppraisalAssignmentDTO
	 *            Object of AppraisalAssignmentDTO.
	 * @param logedInUserInfoDocument
	 *            UserInfo Document of Logged in User.
	 * @param createClaimIfNeeded
	 *            Boolean which indicates if claim needs to be created if doesn't exist.
	 *            
	 * @return Updated AppraisalAssignmentDTO.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 *
	 */
	public AppraisalAssignmentDTO saveSSOAppraisalAssignment(
			final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
			final UserInfoDocument logedInUserInfoDocument,
			boolean createClaimIfNeeded)
			throws MitchellException;	

	/**
	 * This method assign assignment(s) to dispatch center. This methods internally invokes
	 * the single assignedToDispatchCenter_Requires_NewTX method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param dispatchCenter
	 *            - dispatchCenter object
	 * @param assignorUserInfoDocument
	 *            - assignorUserInfoDocument object
	 * @return HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public java.util.HashMap assignedToDispatchCenter(
			final HashMap workAssignmentTaskIDAndTCN,
			final String dispatchCenter,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method changes the dispatch center for a single assignment.
	 * </p>
	 * 
	 * @param workAssignmentTaskID
	 *            WorkAssignmentTaskID of Work Assignment Service.
	 * @param dispatchCenter
	 *            dispatchCenter.
	 * @param loggedInUserInfoDocument
	 *            UserInfo Document of Logged In user.
	 * @return <code>true</code> if successfully processed the request and
	 *         <code>false</code> if unsuccessfully processed.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public int assignedToDispatchCenter_Requires_NewTX(
			final long workAssignmentTaskID, final String dispatchCenter,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException;

	/**
	 * <p>
	 * This method does the appLogging for AppraisalAssignmentService
	 * </p>
	 * 
	 * @param eventId
	 *            eventId of Appraisal Assignment applog ID.
	 * @param taskId
	 *            taskId for the workAssignment taskfor which applogging needs to be done.
	 * @param userInfoDocument
	 *            UserInfo Document of user initiating the applog.
	 * @param mitchellEnvelopeDocument
	 *            MitchellEnvelopeDocument for AppraisalAssignment
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public void doAppraisalAssignmentAppLog(final int eventId,
			final long taskId, final UserInfoDocument userInfoDocument,
			final XmlObject mitchellEnvelopeDocument) throws MitchellException;
	
	public java.util.HashMap updateAppraisalAssignmentAddress(final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
	final UserInfoDocument logedInUserInfoDocument) 
	throws MitchellException;
	
	/**
	 * <p>
	 * This method creates the Supplement Assignment BMS with the minimum 
	 * required aggregates
	 * </p>
	 * 
	 * @param claimSuffix
	 *          object of claimSuffix
	 * @param estimateDocumentID
	 * 			document id of estimate
	 * @param assignorOrgId
	 * 			orgId of assignor for the estimate
	 * @return String	
	 * 			object of supplement assignment BMS	 
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.        
	 */            
	public String createMinimalSupplementAssignmentBMS(final String claimSuffix, 
			final long estimateDocumentID, final long assignorOrgId ) throws MitchellException;

	/***
	 * This method will fetch the appraisal assignment
	 * using estimate package client and will
	 * process ME of appraisal assignment DTO
	 * and will return the CIECADocument
	 * @param documentId
	 * @return
	 * @throws MitchellException
	 */
	XmlObject getCiecaFromAprasgDto(long documentId) throws MitchellException;
}