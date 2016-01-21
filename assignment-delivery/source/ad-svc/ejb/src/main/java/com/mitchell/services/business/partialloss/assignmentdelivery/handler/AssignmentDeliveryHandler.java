package com.mitchell.services.business.partialloss.assignmentdelivery.handler;

import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;

public interface AssignmentDeliveryHandler {
	public void deliverAssignment(AssignmentServiceContext context)
			throws AssignmentDeliveryException;

    // --  New method for Jetta/SIP3.5 - 
	public void cancelAssignment(AssignmentServiceContext context)
			throws AssignmentDeliveryException;

}
