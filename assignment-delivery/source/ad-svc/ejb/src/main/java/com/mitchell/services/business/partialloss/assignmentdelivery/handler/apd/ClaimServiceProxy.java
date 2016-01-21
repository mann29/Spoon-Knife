package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd;

import com.cieca.bms.AssignmentAddRqDocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;

public interface ClaimServiceProxy {
    AssignmentAddRqDocument getAssignmentBms(UserInfoDocument userInfoDocument, String clientClaimNumber) throws MitchellException;
}
