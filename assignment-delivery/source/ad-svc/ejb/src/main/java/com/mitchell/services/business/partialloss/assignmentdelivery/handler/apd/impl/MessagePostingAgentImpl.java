package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import javax.jms.JMSException;

import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.MessagePostingAgent;
import com.mitchell.services.core.messagebus.BadXmlFormatException;
import com.mitchell.services.core.messagebus.MessageDispatcher;

public class MessagePostingAgentImpl implements MessagePostingAgent {

	public String postMessage(MitchellWorkflowMessageDocument mwmDoc)
			throws BadXmlFormatException, ServiceLocatorException, JMSException {
		return MessageDispatcher.postMessage(mwmDoc, null, null);
	}

}
