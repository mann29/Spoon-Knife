package com.mitchell.services.core.partialloss.apddelivery.events;

import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.services.core.messagebus.BadXmlFormatException;
import javax.jms.JMSException;

public interface EventPublisher {
    public String postMessage(MitchellWorkflowMessageDocument mitchellWorkflowMessageDocument) throws JMSException, BadXmlFormatException, ServiceLocatorException;
}


