package com.mitchell.services.core.partialloss.apddelivery.pojo;

import java.util.ArrayList;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;

public interface APDDeliveryService {

	/**
	 * This method first gets a delievery handler object based on message type.
	 * Afterwards it calls deliverArtifact() method on respective delievery handler.
	 * <p>
	 * 
	 * @param apdContext
	 * A XML bean of type APDDeliveryContextDocument. 
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract void deliverArtifact(APDDeliveryContextDocument apdContext)
			throws MitchellException;

	/**
	 * This method is used to deliver AppraisalAssignment to ECLAIM.
	 * @param apdContext
	 * An instance of APDDeliveryContextDocument.
	 * @param partsListAttachments
	 * ArrayList of partsList as attachments
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract void deliverAppraisalAssignment(
			APDDeliveryContextDocument apdContext,
			ArrayList partsListAttachments) throws MitchellException;

}