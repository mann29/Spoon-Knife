package com.mitchell.services.business.partialloss.assignmentdelivery.impl;

import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;

public interface AssignmentDeliveryService {

	public abstract void deliverAssignment(AssignmentServiceContext context)
			throws AssignmentDeliveryException;

	// --  New method for Jetta/SIP3.5 - 
	public abstract void cancelAssignment(AssignmentServiceContext context)
			throws AssignmentDeliveryException;
            

}