package com.mitchell.services.core.partialloss.apddelivery.pojo.util;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDBroadcastMessageType;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryContext;

/**
 * The Interface AppLogHelper.
 */
public interface AppLogHelper {
    
    
    /**
     * @param bMsg
     * APDBroadcastMessageType object
     * @param recipientUserInfo
     * UserInfoType object
     * @throws MitchellException
     * Mitchell Exception
     */
    /* void appLogBroadcastMsgEventWCAPRecipeint(APDBroadcastMessageType bMsg,
                                                UserInfoType recipientUserInfo)
                                                    throws MitchellException;
    /**
     * @param bMsg
     * APDBroadcastMessageType object
     * @throws MitchellException
     * Mitchell Exception
     */
    /*void appLogBroadcastMsgEventRecipeintNotWCAPOrEsmtr(
                                                APDBroadcastMessageType bMsg) 
                                                throws MitchellException;
    /**
     * @param bMsg
     * APDBroadcastMessageType object
     * @param recipientUserInfo
     * UserInfoType object
     * @throws MitchellException
     * Mitchell Exception
     */
    /*void appLogBroadcastMsgEventESTIMATORRecipient(
                                        APDBroadcastMessageType bMsg,
                                        UserInfoType recipientUserInfo)
                                                throws MitchellException;
                                                
         */                                       

     /**
     * This method performs applogging for broadcast message work flow.
     * @param bMsg
     * @param recipientUserInfo
     * @param messageId
     * @param message
     * @throws MitchellException
     */
    public void appLogBroadcastMsg(
                                APDBroadcastMessageType bMsg,
                                UserInfoType recipientUserInfo,
                                String messageId,
                                String message)throws MitchellException;
     
     /**
     * This method calls AppLogging service to do Applog.
     * @param apdContext
     * @param userInfoDoc
     * @throws MitchellException
     */
    public void doAppLog(APDDeliveryContext apdContext,
             UserInfoDocument userInfoDoc)throws MitchellException; 
}
