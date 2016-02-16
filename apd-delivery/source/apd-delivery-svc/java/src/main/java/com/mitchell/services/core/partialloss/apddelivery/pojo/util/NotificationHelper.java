package com.mitchell.services.core.partialloss.apddelivery.pojo.util;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;

/**
 * The Interface NotificationHelper.
 */
public interface NotificationHelper {
    
    /**
     * @param recipientUserInfoDoc
     * @param apdContext
     * @thows MitchellException
     * Mitchell Exception
     */
    void sendEmailToRecipient(
                            UserInfoDocument recipientUserInfoDoc, 
                            APDDeliveryContextDocument apdContext)
                            throws MitchellException;
    
    /**
     * @param apdContext
     * @thows MitchellException
     * Mitchell Exception
     */            
    void sendEmailToSender(
                        APDDeliveryContextDocument apdContext)
                        throws MitchellException;
}
