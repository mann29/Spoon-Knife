package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.partialloss.apddelivery.utils.MessagingContext;

/**
 * The Interface BroadcastMessageProcessorProxy.
 */
public interface BroadcastMessageProcessorProxy { 
    
    /**
     * This method will call BroadcastMessageProcessor service.
     * 
     * @throws MitchellException
     * Mitchell Exception
     */
    void sendMessage() throws MitchellException;
    
    void publishToMessageBus(MessagingContext msgContext) 
                                                    throws MitchellException;
} 
