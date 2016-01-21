package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import org.apache.xmlbeans.XmlObject;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.ItineraryViewDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentDeliveryServiceDTO;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;

public interface AppraisalAssignmentServiceHandler {

    int cancelAppraisalAssignment(long workAssignmentTaskID, String cancellationReason, long TCN, String notes,
            UserInfoDocument loggedInUserInfoDocument) throws MitchellException;

    Estimate getLatestEstimate(String insuranceCarrierCoCode, String clientClaimNumber,
            UserInfoDocument estimatorUserInfoDocument) throws MitchellException;

    Estimate getLatestEstimateNoFiltering(String insuranceCarrierCoCode, String clientClaimNumber) throws MitchellException;

    AppraisalAssignmentDTO saveAppraisalAssignment(AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
            UserInfoDocument logedInUserInfoDocument) throws MitchellException;

    boolean isAssignmentDataReady(MitchellEnvelopeDocument mitchellEnvDoc) throws MitchellException;

    String isAssignmentReadyForDispatch(boolean mandatoryFieldFlag, String assigneeID, String groupCode,
            java.util.Calendar scheduleDateTime, UserInfoDocument userInfoDocument) throws MitchellException;

    void writeAssignmentActivityLog(long assignmentId, String eventName, String eventDescription,
            String createdBy) throws MitchellException;

    boolean uncancelAppraisalAssignment(long workAssignmentTaskID, UserInfoDocument loggedInUserInfoDocument)
            throws MitchellException;

    boolean dispatchAppraisalAssignment(long workAssignmentTaskID, UserInfoDocument loggedInUserInfoDocument)
            throws MitchellException;

    void updateReviewAssignment(long relatedEstimateDocumentId, UserInfoDocument estimatorUserInfo,UserInfoDocument  logdInUsrInfo)
    throws MitchellException;

    String getExpertiseSkillsByVehicleType(final String vehicleType)
		      throws MitchellException;
    
    boolean dispatchSupplementAppraisalAssignment(long workAssignmentTaskID, long estimateDocId,
            UserInfoDocument loggedInUserInfoDocument) throws MitchellException;

    AppraisalAssignmentDTO updateAppraisalAssignment(AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
            UserInfoDocument logedUserInfoDocument) throws MitchellException;

    long saveLunchAssignmentType(XmlObject taskDocumentXmlObject, UserInfoDocument assignorUserInfoDocument)
            throws MitchellException;

    String retrieveSupplementRequestDoc(long estimateDocId, long estimatorOrgId, long reviewerOrgId)
            throws MitchellException;

    MitchellEnvelopeDocument retrieveSupplementRequestXMLDocAsMEDoc(AssignmentDeliveryServiceDTO asgSvcCtx,
            String workItemId) throws MitchellException;

    int uncancelAppraisalAssignment(long workAssignmentTaskID, long requestTCN,
            UserInfoDocument loggedInUserInfoDocument) throws MitchellException;

    String retrieveSupplementRequestXMLDoc(long estimateDocId, long estimatorOrgId, long reviewerOrgId)
            throws MitchellException;

    int assignScheduleAppraisalAssignment(XmlObject assignTaskXmlObject,
            UserInfoDocument loggedInUserInfoDocument) throws MitchellException;

    int onHoldAppraisalAssignment(long workAssignmentTaskID, long TCN, String selectedOnHoldTypeFromCarrier,
            String notes, UserInfoDocument loggedInUserInfoDocument) throws MitchellException;

    int removeOnHoldAppraisalAssignment(long workAssignmentTaskID, long TCN,
            UserInfoDocument loggedInUserInfoDocument) throws MitchellException;

    int unScheduleAppraisalAssignment(long workAssignmentTaskID, long requestTCN,
            UserInfoDocument loggedInUserInfoDocument) throws MitchellException;

    int dispatchAppraisalAssignment(long workAssignmentTaskID, long TCN,
            UserInfoDocument loggedInUserInfoDocument) throws MitchellException;

    int assignmentStatusUpdate(long workAssignmentTaskID, String newDispositionCode, String reasonCode,
            String comment, long requestTCN, UserInfoDocument loggedInUserInfoDocument, ItineraryViewDocument itineraryXML) throws MitchellException;

   int assignmentStatusUpdateItineraryView(ItineraryViewDocument ItineraryXML, UserInfoDocument loggedInUserInfoDocument) throws MitchellException;
    
    int workAssignmentStatusUpdate(long workAssignmentTaskID, String newDispositionCode, String reasonCode,
            String comment, long requestTCN, UserInfoDocument loggedInUserInfoDocument) throws MitchellException;

    void cancelSupplementTask(String claimSuffixNumber, UserInfoDocument bodyShopUserInfo, String note,
            String reviewCoCd, String reviewUserId) throws MitchellException;

    void createSupplementTask(String claimSuffixNumber, UserInfoDocument bodyShopUserInfo, String workItemId,
            String note, String reviewCoCd, String reviewUserId) throws MitchellException;

    void createAssignSupplementTaskToNCRTUSer(String claimSuffixNumber, UserInfoDocument bodyShopUserInfo, String workItemId,
            String note, String reviewCoCd, String reviewUserId) throws MitchellException;

    void rejectSupplementTask(long taskID, UserInfoDocument estimatorUserInfo) throws MitchellException;

    void addVehLctnTrackingHist(Long claimSuffixID, String vehicleTrackingStatus, String companyCode,
            String reviewerId) throws MitchellException;
    
    AppraisalAssignmentDTO saveSSOAppraisalAssignment(final AppraisalAssignmentDTO inputAppraisalAssignmentDTO, 
                                                             final UserInfoDocument logedInUserInfoDocument) 
                                                             throws MitchellException;
    
    AppraisalAssignmentDTO saveSSOAppraisalAssignment(final AppraisalAssignmentDTO inputAppraisalAssignmentDTO, 
            final UserInfoDocument logedInUserInfoDocument, boolean createClaimIfNeeded) 
            throws MitchellException;    
    
    int assignedToDispatchCenter(final long workAssignmentTaskID, final String dispatchCenter, 
            final UserInfoDocument loggedInUserInfoDocument) throws MitchellException;            
    
    void doAppraisalAssignmentAppLog (final int eventId, 
                       final long taskId, 
                       final UserInfoDocument userInfoDocument,
                       final XmlObject mitchellEnvelopeDocument) throws MitchellException;
	
    java.util.HashMap updateAppraisalAssignmentAddress(final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
            final UserInfoDocument logedInUserInfoDocument) throws MitchellException;
	
    String createMinimalSupplementAssignmentBMS(final String claimSuffix, 
			final long estimateDocumentID, final long assignorOrgId ) throws MitchellException;
}
