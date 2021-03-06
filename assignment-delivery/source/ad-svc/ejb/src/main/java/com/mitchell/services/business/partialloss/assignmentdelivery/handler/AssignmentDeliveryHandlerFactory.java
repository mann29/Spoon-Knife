package com.mitchell.services.business.partialloss.assignmentdelivery.handler;

import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLogger;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;

public class AssignmentDeliveryHandlerFactory extends AbstractAssDelHandlerFactory {
    private static final String CLASS_NAME = AssignmentDeliveryHandlerFactory.class.getName();
    private static final AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(
			CLASS_NAME);

    public static AssignmentDeliveryHandler getAssignmentDeliveryHandler(String handlerClassName) 
            throws AssignmentDeliveryException {
		final String methodName = "getAssignmentDeliveryHandler";
		mLogger.entering(methodName);

        AssignmentDeliveryHandler handler = null;

        try {
            Class cls = Class.forName(handlerClassName);
            handler = (AssignmentDeliveryHandler)cls.newInstance();
        } catch (ClassNotFoundException cnfe) {
			mLogger.severe(cnfe.getMessage());
            ErrorLoggingService.logError(
                    AssignmentDeliveryErrorCodes.HANDLER_CLASS_NOT_FOUND, null, 
                    CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, 
                    null, cnfe.getMessage(), null, 0, cnfe);
            throw mLogger.createException(
					AssignmentDeliveryErrorCodes.HANDLER_CLASS_NOT_FOUND, cnfe);
        } catch (InstantiationException ie) {
			mLogger.severe(ie.getMessage());
            ErrorLoggingService.logError(
                    AssignmentDeliveryErrorCodes.HANDLER_CLASS_INSTANTIATION_ERROR, null, 
                    CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, 
                    null, ie.getMessage(), null, 0, ie);
			throw mLogger.createException(
					AssignmentDeliveryErrorCodes.HANDLER_CLASS_INSTANTIATION_ERROR, ie);
        } catch (IllegalAccessException iae) {
			mLogger.severe(iae.getMessage());
            ErrorLoggingService.logError(
                    AssignmentDeliveryErrorCodes.HANDLER_CLASS_ILLEGAL_ACCESS_ERROR, null, 
                    CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, 
                    null, iae.getMessage(), null, 0, iae);
			throw mLogger.createException(
					AssignmentDeliveryErrorCodes.HANDLER_CLASS_ILLEGAL_ACCESS_ERROR, iae);
        } finally {
    		mLogger.exiting(methodName);
        } 
        
        return handler;
    }

	public AssignmentDeliveryHandler delegatedGetInstance(String key) throws AssignmentDeliveryException {
		return getAssignmentDeliveryHandler(key);
	}
}
