package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd;

import javax.jms.JMSException;

import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.services.core.messagebus.BadXmlFormatException;

public interface MessagePostingAgent {

	String postMessage(MitchellWorkflowMessageDocument mwmDoc) throws BadXmlFormatException, ServiceLocatorException, JMSException;
	
}
