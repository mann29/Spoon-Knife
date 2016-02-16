package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.utils.misc.AppUtilities;
import java.util.logging.Logger;

/**
 * The Class ErrorLogProxyImpl.
 */
public final class ErrorLogProxyImpl implements ErrorLogProxy {
    
    /**
     * class name.
     */
    private static final String CLASS_NAME = 
                            ErrorLogProxyImpl.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    /**
     * @param workItemId
     *          String workItemId
     * @param message
     *          String message
     * @param me
     *          MitchellException me
     */
    public void logCorrelatedError(String workItemId, String message,
                                     MitchellException me) {
    	String methodName = "logCorrelatedError";
    	logger.entering(CLASS_NAME, methodName);
    	
        if (message != null) {
            StringBuffer sbuff = new StringBuffer();
            sbuff.append("<InputTextMessageBody>");
            sbuff.append(message);
            sbuff.append("</InputTextMessageBody>");
            
            ErrorLoggingService.logError(APDDeliveryConstants.ERROR_CORRELATED_ERROR,
                me.getCorrelationId(),
                me.getClassName(), 
                me.getMethodName(),
                ErrorLoggingService.SEVERITY.NONFATAL,
                workItemId,
                APDDeliveryConstants.ERROR_CORRELATED_ERROR_MSG,
                null,
                0,
                sbuff.toString(), "XML");
        }
        logger.exiting(CLASS_NAME, methodName);
    }

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
    public String logError(
                    final int errorType, 
                    final String classNm,
                    final String methodNm,
                    final String desc, final Throwable t) {
    	String methodName = "logError";
    	logger.entering(CLASS_NAME, methodName);
        MitchellException mitExc =
            new MitchellException(errorType, classNm, methodNm, desc, t);
        mitExc.setSeverity(MitchellException.SEVERITY.WARNING);

    	logger.exiting(CLASS_NAME, methodName);
        return ErrorLoggingService.logCommonError(mitExc);
    }

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
    public void logErrorAndThrowMitchellException(
                    final int errorType,
                    final String classNm, 
                    final String methodNm,
                    final String desc, 
                    final Throwable t)
                    throws MitchellException {
    	String methodName = "logErrorAndThrowMitchellException";
    	logger.entering(CLASS_NAME, methodName);
    	
        MitchellException mitExc =
            new MitchellException(errorType, classNm, methodNm, desc, t);
        mitExc.setSeverity(MitchellException.SEVERITY.WARNING);

        String corrId = ErrorLoggingService.logCommonError(mitExc);
        mitExc.setCorrelationId(corrId);
        logger.exiting(CLASS_NAME, methodName);
        throw mitExc;
        
    }

    /**
     * Log error.
     *
     * @param mitchellException the mitchell exception
     * @return the string
     */
    public String logError(
                    final MitchellException mitchellException) {
        return ErrorLoggingService.logError(mitchellException);
    }

    // Fix 97154 : Comment unused method from Impl and Interface.
    /*
    public String logError(
                    final MitchellException mitchellException,
                    final String claimNumber) {
        return ErrorLoggingService.logError(mitchellException, claimNumber);
    } */
    
    /**
     * Log error and throw mitchell exception.
     * @param mitchellException
     * @throws MitchellException
     */
    public void logErrorAndThrowMitchellException(
                    final MitchellException mitchellException) 
                    throws MitchellException {
    	String methodName = "logErrorAndThrowMitchellException";
    	logger.entering(CLASS_NAME, methodName);
    	
        final String corrId = ErrorLoggingService.logError(mitchellException);
        mitchellException.setCorrelationId(corrId);
        logger.exiting(CLASS_NAME, methodName);
        throw mitchellException;
    }
    
    /**
     * Log error and throw mitchell exception.
     * @param classNm
     * @param methodNm
     * @param throwalbe
     * @throws MitchellException
     */
    public void logErrorAndThrowMitchellException(
                    final String className, 
                    final String methodName,
                    final Throwable throwable) 
                    throws MitchellException {
        
        final String desc = new StringBuffer("Unexpected Error:")
        .append(AppUtilities.getStackTraceString(throwable, true))
        .toString();
        
        final MitchellException mitExc =
        new MitchellException(APDDeliveryConstants.ERROR_UNEXPECTED,
        className, methodName, desc, throwable);
        
        ErrorLoggingService.logError(mitExc);
        
        throw mitExc;
    }

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
    public String logError(
                    final int errorType, 
                    final String correlationId,
                    final String className, 
                    final String methodName,
                    final String severity, 
                    final String workItemID,
                    final String description, 
                    final String companyCode,
                    final int orgID, 
                    final String details,
                    final String detailType) {
        
        return ErrorLoggingService.logError(
                            errorType, correlationId, className,
                            methodName, severity, workItemID,
                            description, companyCode, orgID,
                            details, detailType);
    }
}
