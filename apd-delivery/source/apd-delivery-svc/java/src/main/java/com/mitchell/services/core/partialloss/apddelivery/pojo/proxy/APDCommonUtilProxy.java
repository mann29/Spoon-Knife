package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoType;

/**
 * The Interface APDCommonUtilProxy.
 */
public interface APDCommonUtilProxy { 
    /**
     * @param userInfo
     * UserInfoType object
     * @param applCode
     * Appl code
     * @return boolean
     * true- applCode is there in userInfo
     * false- applCode is not there in userInfo
     * @throws MitchellException
     * Mitchell Exception
     */
    boolean checkApplCode(UserInfoType userInfo, String applCode) 
                                                    throws MitchellException;
    
    /**
     * @param value
     * String value to be analysed
     * @return boolean
     * true- value is null or empty
     * false- value is not null or empty
     */                                                    
    boolean isNullOrEmpty(String value);
    
    /**
    * This method checks whether the Logger Level is FINE and then
    * logs the entire XML as a String.
    * 
    * @param message
    * <code>String</code> message
    * 
    */
    void logFINEMessage(String message);
    
    /**
    * This method checks whether the Logger Level is INFO and then
    * logs the message as a String.
    * 
    * @param message
    * <code>String</code> message
    */
    void logINFOMessage(String message);
    
    /**
     * This method checks whether the Logger Level is SEVERE and then
     * logs the message as a String.
     * @param message
     */
    void logSEVEREMessage(String message);
    
} 
