package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu;

import java.util.ArrayList;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;

public interface AssignmentEmailDeliveryHandler {

	void deliverCreation(APDDeliveryContextDocument apdContextDoc, boolean isOverrideEmailAddress,String culture) throws MitchellException;
	
	void deliverCancelation(APDDeliveryContextDocument apdContextDoc) throws MitchellException;
	
	void deliverUploadSuccess(APDDeliveryContextDocument apdContextDoc) throws MitchellException;
	
	void deliverUploadFail(APDDeliveryContextDocument apdContextDoc) throws MitchellException;
	
	void deliverSupplementEmail(
	        APDDeliveryContextDocument apdContextDoc,
	        ArrayList<?> partsListAttachment, boolean isOverrideEmailAddress,String culture) throws MitchellException;
	
	void deliverSTAFFEmail(
	        APDDeliveryContextDocument apdContextDoc,String culture) throws MitchellException;
	
	void deliverIAEmail(
	        APDDeliveryContextDocument apdContextDoc,String culture) throws MitchellException;
	
	void deliverIAEmail(
	        APDDeliveryContextDocument apdContextDoc,boolean isOverrideEmailAddress,String culture) throws MitchellException;

}
