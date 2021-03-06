package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import javax.jms.JMSException;

import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.services.core.messagebus.BadXmlFormatException;

public interface MessageBusProxy {
	public String postMessage(MitchellWorkflowMessageDocument workflowMessage) throws BadXmlFormatException, ServiceLocatorException, JMSException;
}
