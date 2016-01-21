package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd;

import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;

public interface ApdIntAssDelHandler {
    /**
     * Deliver Assignment to work-process handler for WC/RC integration
     * 
     * @param context
     *            Context to deliver under.
     * @throws AssignmentDeliveryException
     */
    public void deliverAssignment(APDDeliveryContextDocument context) throws AssignmentDeliveryException;

    // public void cancelAssignment(APDDeliveryContextDocument context) throws
    // AssignmentDeliveryException;
}
