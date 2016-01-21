package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.WorkAssignmentProxy;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;
import com.mitchell.services.core.workassignment.client.WorkAssignmentClient;

public class WorkAssignmentProxyImpl implements WorkAssignmentProxy {

    private WorkAssignmentClient client = getWorkAssignmentClient();

    private WorkAssignment getWorkAssignmentWithTaskID(Long taskId) throws MitchellException {
        return this.client.getWorkAssignmentByTaskID(taskId);
    }

    public Long getWorkAssignmentReferenceID(Long taskId) throws MitchellException {
        return this.getWorkAssignmentWithTaskID(taskId).getReferenceId();
    }

    public Long getWorkAssignmentClaimExposureID(Long taskId) throws MitchellException {
        return this.getWorkAssignmentWithTaskID(taskId).getClaimExposureID();
    }

    public Long getWorkAssignmentClaimID(Long taskId) throws MitchellException {
        return this.getWorkAssignmentWithTaskID(taskId).getClaimID();
    }

    private WorkAssignmentClient getWorkAssignmentClient() {
        return new WorkAssignmentClient();
    }

}
