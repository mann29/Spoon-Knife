package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.systemconfiguration.SystemConfiguration;
import java.util.Stack;
import java.util.logging.Logger;

public abstract class AbstractAssignmentDeliveryLogger extends Logger implements AssignmentDeliveryLoggerInterface {

	protected AbstractAssignmentDeliveryLogger(String name,
			String resourceBundleName) {
		super(name, resourceBundleName);
	}
	
}
