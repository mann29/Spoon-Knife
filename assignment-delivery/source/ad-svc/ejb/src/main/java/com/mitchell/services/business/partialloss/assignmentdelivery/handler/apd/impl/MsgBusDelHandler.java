package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;

public interface MsgBusDelHandler {

	public abstract void deliverAssignment(
			final APDDeliveryContextDocument document)
			throws AssignmentDeliveryException;

}