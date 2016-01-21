package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.rmi.RemoteException;
import java.util.Calendar;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.ItineraryViewDocument;
import com.mitchell.schemas.task.TaskDocument;
import com.mitchell.schemas.workassignment.HoldInfoType;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;

public interface WorkAssignmentHandler {

    public WorkAssignment getWorkAssignmentByTaskId(long workAssignmentTaskID, UserInfoDocument logdInUsrInfo)
            throws MitchellException;

    public WorkAssignment uncancelWorkAssignment(WorkAssignmentDocument updatedWorkAssignmentDocument,
            UserInfoDocument loggedInUserInfoDocument) throws MitchellException;

    public WorkAssignment saveWorkAssignmentStatus(WorkAssignment workAssignment, String newDispositionCode,
            String reasonCode, String comment, UserInfoDocument loggedInUserInfoDocument,ItineraryViewDocument itineraryXML) throws MitchellException;

    public WorkAssignment saveWorkAssignment(WorkAssignmentDocument workAssignmentDocument,
            UserInfoDocument logdInUsrInfo) throws MitchellException;

    public WorkAssignmentDocument setupWorkAssignmentRequest(WorkAssignmentDocument workAssignmentDocument,
            String disposition, String requestFor, UserInfoDocument logdInUsrInfo);

    public WorkAssignmentDocument populateWorkAssignment(TaskDocument taskDocument,
            UserInfoDocument loggedInUserInfoDocument) throws MitchellException;

    public String getGroupCodeFromWorkAssignmentDocument(WorkAssignmentDocument workAssignmentDocument);

    public WorkAssignment assignWorkAssignment(WorkAssignmentDocument workAssignmentDocument, String disposition,
            String dispatchCenter, UserInfoDocument assigneeInfo, Calendar scheduleDateTime,
            UserDetailDocument userDetail, UserInfoDocument logdInUsrInfo) throws Exception;

    public WorkAssignment unscheduleWorkAssignment(WorkAssignment workAssignment, final String reasonCode, final String reasonNotes, UserInfoDocument logdInUsrInfo)
            throws MitchellException;
            
    public WorkAssignment saveRescheduleOrIncompleteWorkAssignment(WorkAssignment workAssignment, final String reasonCode, final String reasonNotes, UserInfoDocument logdInUsrInfo, String disposition)
            throws MitchellException;            

    public void saveAssignmentBeenUpdatedFlag(long workAssignmentTaskId, String flag,
            UserInfoDocument logedUserInfoDocument) throws MitchellException;

    public WorkAssignment saveWorkAssignment(Long documentId, long claimId, long claimExposureId, String claimMask,
            String event, AppraisalAssignmentDTO appraisalAssignmentDTO, UserInfoDocument logdInUsrInfo, final HoldInfoType waHoldInfoType,final long moiOrgId)
            throws Exception;

    public Calendar getScheduleDateTime(WorkAssignmentDocument workAssignmentDocument);

    public String getAssigneeIdFromWorkAssignmentDocument(WorkAssignmentDocument workAssignmentDocument);

    public WorkAssignment updateGroupIdInWorkAssignment(final String dispatchCenter,final UserInfoDocument loggedInUserInfoDocument,final WorkAssignmentDocument workAssignmentDocument) throws MitchellException;

    public WorkAssignment saveUpdateWorkAssignment(WorkAssignmentDocument workassignmentdocument, UserInfoDocument userinfodocument)
        throws RemoteException, MitchellException;
    
    public WorkAssignment unscheduleWorkAssignment(WorkAssignment workAssignment, final String reasonCode,
			final String reasonNotes, final UserInfoDocument logdInUsrInfo, ItineraryViewDocument itineraryXML)
			throws MitchellException;
}
