package com.mitchell.services.core.partialloss.apddelivery.pojo;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;

public interface EmailDeliveryHandler {

	/**
	 * This method is used to deliver RepairAssignment via Email.
	 * @param apdContextDoc
	 *              APDDeliveryContextDocument
	 * @throws MitchellException
	 *              MitchellException
	 */
	public abstract void deliverRepairAssignment(
			APDDeliveryContextDocument apdContextDoc) throws MitchellException;

}