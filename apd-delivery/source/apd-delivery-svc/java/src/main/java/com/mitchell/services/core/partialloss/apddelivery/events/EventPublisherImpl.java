package com.mitchell.services.core.partialloss.apddelivery.events;

import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.MitchellWorkflowMessageSourceType;
import com.mitchell.common.types.MitchellWorkflowMessageTrackingInfoType;
import com.mitchell.common.types.MitchellWorkflowMessageType;
import com.mitchell.services.core.messagebus.BadXmlFormatException;
import com.mitchell.services.core.messagebus.MessageDispatcher;

import javax.jms.JMSException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.logging.Logger;

public class EventPublisherImpl implements  EventPublisher{

    private MessageDispatcher messageDispatcher;

    public String postMessage(MitchellWorkflowMessageDocument mitchellWorkflowMessageDocument) throws JMSException, BadXmlFormatException, ServiceLocatorException {

        return messageDispatcher.postMessage(mitchellWorkflowMessageDocument, null, null);
    }

    public MessageDispatcher getMessageDispatcher() {
        return messageDispatcher;
    }

    public void setMessageDispatcher(MessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
    }
}


