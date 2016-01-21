package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.service;

import java.util.ArrayList;

import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;

public interface  AssignmentEmailDeliveryService {
	public void deliveryAssignmentEmailNotification(
			final APDDeliveryContextDocument aPDDeliveryContextDocument,
			final ArrayList partsListAttachment,
			final String emailType) throws Exception;
}
