package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import com.cieca.bms.AssignmentAddRqDocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.ClaimServiceProxy;
import com.mitchell.services.technical.claim.client.ClaimServiceClient;

public class ClaimServiceProxyImpl implements ClaimServiceProxy {

    public AssignmentAddRqDocument getAssignmentBms(UserInfoDocument userInfoDocument, String clientClaimNumber) throws MitchellException {
        return (AssignmentAddRqDocument) ClaimServiceClient
                .getEjb()
                .readClaimInfoIntoBms(userInfoDocument, clientClaimNumber);
    }
}
