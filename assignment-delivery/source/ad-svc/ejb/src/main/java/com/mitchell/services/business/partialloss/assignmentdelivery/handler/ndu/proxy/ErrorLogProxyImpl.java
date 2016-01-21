package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;

public class ErrorLogProxyImpl implements ErrorLogProxy {

	public MitchellException logError(final MitchellException mitchellException) {
		final String correlationId =
			ErrorLoggingService.logCommonError(mitchellException);
		mitchellException.setCorrelationId(correlationId);
		return mitchellException;
	}

}
