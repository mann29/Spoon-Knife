package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.client.AppraisalAssignmentClient;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentDeliveryServiceDTO;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;


public class AppraisalAssignmentProxyImpl implements AppraisalAssignmentProxy {

    private static final String CLZ_NAME = AppraisalAssignmentProxyImpl.class.getName();
    
    public MitchellEnvelopeDocument retrieveSupplementRequestXMLDocAsMEDoc(
    		AssignmentDeliveryServiceDTO adsDto, String workItemId)
            throws MitchellException {

        MitchellEnvelopeDocument mitchellEnvelopeDocument = null;
        try {
            
              mitchellEnvelopeDocument = AppraisalAssignmentClient.getAppraisalAssignmentEJB().retrieveSupplementRequestXMLDocAsMEDoc(adsDto, workItemId);
        } catch (Exception e) {
            throw new MitchellException(
                    AssignmentDeliveryErrorCodes.ERROR_SUPP_ASG_EMAIL,
                    CLZ_NAME,
                    "retrieveSupplementRequestXMLDocAsMEDoc",
                    e.getMessage(),
                    e);
        }
        
        return mitchellEnvelopeDocument;
    }

}
