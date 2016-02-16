package com.mitchell.services.core.partialloss.apddelivery.pojo.delegator; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;

/**
 * This is a proxy for APDBroadcastMessageDelegatorImpl.
 *  
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 */
public interface APDBroadcastMessageDelegator { 
    /**
     * This method should send Broadcast Messages to respective Recipient/Sender.
     * 
     * @param apdContext
     * APDDeliveryContextDocument object
     * @throws MitchellException
     * Mitchell Exception
     */
    public void sendBroadcastMessage(APDDeliveryContextDocument apdContext) throws MitchellException;
} 
