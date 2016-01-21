package com.mitchell.services.business.partialloss.assignmentdelivery;

import javax.jms.JMSException;

import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.MessagePostingAgent;
import com.mitchell.services.core.messagebus.BadXmlFormatException;
import com.mitchell.services.core.messagebus.MessageDispatcher;

public class MockMessagePostingAgent implements MessagePostingAgent {
	private MitchellWorkflowMessageDocument payload;
	public MitchellWorkflowMessageDocument getPayload() {
		return payload;
	}
	public void setPayload(MitchellWorkflowMessageDocument payload) {
		this.payload = payload;
	}
	public String postMessage(MitchellWorkflowMessageDocument mwmDoc) throws BadXmlFormatException, ServiceLocatorException, JMSException {
		// TODO Auto-generated method stub
//		MessageDispatcher.postMessage(mwmDoc, null, null);
		payload = mwmDoc;
		return "2345-fake";
	}

}
