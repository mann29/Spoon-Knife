package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentDeliveryServiceDTO;
public interface AppraisalAssignmentProxy {
    MitchellEnvelopeDocument retrieveSupplementRequestXMLDocAsMEDoc(
    		AssignmentDeliveryServiceDTO dto,
            String workItemId) throws MitchellException;
}
