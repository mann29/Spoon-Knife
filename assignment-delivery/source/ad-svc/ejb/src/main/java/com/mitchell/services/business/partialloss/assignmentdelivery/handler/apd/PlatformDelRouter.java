package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;

public interface PlatformDelRouter {
	public void route(APDDeliveryContextDocument payload) throws   MitchellException;
}
