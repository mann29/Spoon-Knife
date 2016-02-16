package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class APDCommonUtilProxyImpl.
 * 
 * @see APDCommonUtilProxy
 */
public class APDCommonUtilProxyImpl implements APDCommonUtilProxy { 
    
    /**
     * class name.
     */
    private static final String CLASS_NAME = 
                                        APDCommonUtilProxyImpl.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
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
    public boolean checkApplCode(UserInfoType userInfo, String applCode) 
                                                    throws MitchellException {
        boolean isApplCode = false;        
            String[] appCodeArray = userInfo.getAppCodeArray();
            for (int i = 0; i < appCodeArray.length; i++) {
                if (appCodeArray[i].matches(applCode)) {
                    isApplCode = true;
                    break;
                }
            }
        return isApplCode;
    }
    
    /**
     * @param value
     * String value to be analysed
     * @return boolean
     * true- value is null or empty
     * false- value is not null or empty
     */   
    public boolean isNullOrEmpty(String value) {
        boolean isNullOrEmpty = false;
        if (value == null || value.trim().equals("")) {
            isNullOrEmpty = true;
        }
        return isNullOrEmpty;
    }
    
    /**
    * This method checks whether the Logger Level is FINE and then
    * logs the entire XML as a String.
    * 
    * @param message
    * <code>String</code> message
    * 
    */
    public void logFINEMessage(String message) {
        if (logger.isLoggable(Level.FINE)) {
        logger.fine(message);
        }
    }
    
    /**
    * This method checks whether the Logger Level is INFO and then
    * logs the message as a String.
    * 
    * @param message
    * <code>String</code> message
    */
    public void logINFOMessage(String message) {
        if (logger.isLoggable(Level.INFO)) {
        logger.info(message);
        }
    }
    
    /**
     * This method checks whether the Logger Level is SEVERE and then
     * logs the message as a String.
     * 
     * @param message
     * <code>String</code> message
     */
     public void logSEVEREMessage(String message) {
         if (logger.isLoggable(Level.SEVERE)) {
         logger.severe(message);
         }
     }
    
    
    
} 
