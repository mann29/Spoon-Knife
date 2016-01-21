package com.mitchell.services.business.partialloss.assignmentdelivery.impl;

import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
/**
 * 
 * @author <a href="mailto:prashant.khanwale@mitchell.com"> Prashant Sadashiv Khanwale </a></br>
 * Created Jul 15, 2010
 */
public interface AssignementDeliveryRouter {

	public abstract void deliverAssignment(AssignmentServiceContext context)
			throws AssignmentDeliveryException;

	// --  New method for Jetta/SIP3.5 - 
	public abstract void cancelAssignment(AssignmentServiceContext context)
			throws AssignmentDeliveryException;

}