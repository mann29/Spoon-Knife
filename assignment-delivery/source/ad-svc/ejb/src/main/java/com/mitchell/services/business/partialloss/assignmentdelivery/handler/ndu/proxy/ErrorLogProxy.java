package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy;

import com.mitchell.common.exception.MitchellException;

public interface ErrorLogProxy {
	MitchellException logError(MitchellException mitchellException);
}
