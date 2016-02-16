package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;

/**
 * The Interface ErrorLogProxy.
 */
public interface ErrorLogProxy {

    /**
     * @param workItemId
     *          String workItemId
     * @param message
     *          String message
     * @param me
     *          MitchellException me
     */
    void logCorrelatedError(
                String workItemId, 
                String message,
                MitchellException me);
    /**
     * Log error.
     *
     * @param errorType the error type
     * @param classNm the class nm
     * @param methodNm the method nm
     * @param desc the desc
     * @param t the t
     * @return the string
     */
    String logError(
                int errorType, 
                String classNm,
                String methodNm, 
                String desc,
                Throwable t);

    /**
     * Log error and throw mitchell exception.
     *
     * @param errorType the error type
     * @param classNm the class nm
     * @param methodNm the method nm
     * @param desc the desc
     * @param t the t
     * @throws MitchellException the mitchell exception
     */
    void logErrorAndThrowMitchellException(
                int errorType,
                String classNm,
                String methodNm, 
                String desc,
                Throwable t) 
                throws MitchellException;

    /**
     * Log error.
     *
     * @param mitchellException the mitchell exception
     * @return the string
     */
    String logError(
                MitchellException mitchellException);

    // Fix 97154 : Remove unused method from impl and interface.
    /**
     * Log error.
     *
     * @param mitchellException the mitchell exception
     * @param claimNumber the claim number
     * @return the string
     
    String logError(
                MitchellException mitchellException,
                String claimNumber); */
            
    /**
     * Log error and throw mitchell exception.
     * @param mitchellException
     * @throws MitchellException
     */
    void logErrorAndThrowMitchellException(
                MitchellException mitchellException) 
                throws MitchellException;
    
    /**
     * Log error and throw mitchell exception.
     * @param classNm
     * @param methodNm
     * @param throwalbe
     * @throws MitchellException
     */
    void logErrorAndThrowMitchellException(
                String classNm,
                String methodNm,
                Throwable throwalbe) 
                throws MitchellException;
    
    /**
     * Log error.
     * @param errorType
     * @param correlationId
     * @param className
     * @param methodName
     * @param severity
     * @param workItemID
     * @param description
     * @param companyCode
     * @param orgID
     * @param details
     * @param detailType
     * @return
     */
    String logError(
                int errorType, 
                String correlationId,
                String className, 
                String methodName,
                String severity,  
                String workItemID,
                String description, 
                String companyCode,
                int orgID, 
                String details, 
                String detailType);    
}
