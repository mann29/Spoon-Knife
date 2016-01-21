package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import java.rmi.RemoteException;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.services.core.workassignment.WorkAssignmentException;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;

public interface WorkAssignmentProxy {
    public WorkAssignment getWorkAssignmentByTaskID(Long taskId) throws MitchellException;

    public WorkAssignment[] getWorkAssignmentsByClaimIdExposureId(String companyCode, long claimId, long exposureId,
            String[] waType) throws MitchellException;

    public WorkAssignment save(WorkAssignmentDocument waDoc) throws MitchellException;

    public WorkAssignment unCancel(WorkAssignmentDocument waDoc, UserInfoDocument loggedInUserInfo)
            throws MitchellException;

    public boolean saveAssignmentBeenUpdatedFlag(long workAssignmentTaskId, String value,
            UserInfoDocument loggedInUserInfo) throws MitchellException;
    
    public Long saveWorkAssignmentHist(long taskID, String event) throws MitchellException;
}
