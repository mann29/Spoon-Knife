package com.mitchell.services.core.partialloss.apddelivery.pojo.util; 

import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.services.core.messagebus.JMSHeaderProperties;
import javax.jms.JMSException;

/**
 * Proxy for JMS functions.
 */
public interface JMSSender { 
    
    /**
     * @param message
     * @param correlationId
     * @param argHeaderProps
     * @return String
     * @throws JMSException
     * @throws ServiceLocatorException
     */
    public String sendMessageToBroadcastMessageQueue(
                                        String message, 
                                        String correlationId,
                                        JMSHeaderProperties argHeaderProps)
                                        throws JMSException, ServiceLocatorException;
} 
