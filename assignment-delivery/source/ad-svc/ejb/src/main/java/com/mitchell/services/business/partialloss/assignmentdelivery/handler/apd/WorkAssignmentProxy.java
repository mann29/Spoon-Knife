package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;

public interface WorkAssignmentProxy {

    public Long getWorkAssignmentReferenceID(Long taskId) throws MitchellException;

    public Long getWorkAssignmentClaimExposureID(Long taskId) throws MitchellException;

    public Long getWorkAssignmentClaimID(Long taskId) throws MitchellException;
}
