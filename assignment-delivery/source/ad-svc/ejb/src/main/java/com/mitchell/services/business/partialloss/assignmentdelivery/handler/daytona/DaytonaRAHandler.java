package com.mitchell.services.business.partialloss.assignmentdelivery.handler.daytona;

import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;

public interface DaytonaRAHandler {
    void deliverAssignment(APDDeliveryContextDocument document) throws AssignmentDeliveryException;
}
