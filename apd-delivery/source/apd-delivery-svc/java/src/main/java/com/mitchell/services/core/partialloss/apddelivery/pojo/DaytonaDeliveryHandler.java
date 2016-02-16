package com.mitchell.services.core.partialloss.apddelivery.pojo;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import org.apache.xmlbeans.XmlException;

import javax.jms.JMSException;
import java.net.UnknownHostException;

public interface DaytonaDeliveryHandler {
    void deliverAlert(APDDeliveryContextDocument context) throws MitchellException, UnknownHostException, JMSException, XmlException;
}
