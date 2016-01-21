package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import java.util.ArrayList;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.core.partialloss.apddelivery.client.APDDeliveryClient;

public class APDProxyImpl implements APDProxy {

    // @Override
    public void deliverAppraisalAssignment(final APDDeliveryContextDocument arg0, final ArrayList arg1)
            throws  MitchellException {
        APDDeliveryClient.getAPDDeliveryEJB().deliverAppraisalAssignment(arg0, arg1);
    }

    // @Override
    public void deliverArtifact(final APDDeliveryContextDocument arg0) throws MitchellException {
        APDDeliveryClient.getAPDDeliveryEJB().deliverArtifact(arg0);
    }

   
}
