package com.mitchell.services.business.partialloss.assignmentdelivery.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfig;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractAssDelHandlerFactory;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AssignmentDeliveryHandler;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;

/**
 * <p>
 * The implementation class for the service.
 * 
 */
public class AssignmentDeliveryImpl implements AssignmentDeliveryService {
    private final String CLASS_NAME = "AssignmentDeliveryImpl";
    private AbstractAssDelHandlerFactory assignmentDeliveryHandlerFactory;
    public AbstractAssDelHandlerFactory getAssignmentDeliveryHandlerFactory() {
		return assignmentDeliveryHandlerFactory;
	}

	public void setAssignmentDeliveryHandlerFactory(
			AbstractAssDelHandlerFactory assignmentDeliveryHandlerFactory) {
		this.assignmentDeliveryHandlerFactory = assignmentDeliveryHandlerFactory;
	}

	private final AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(this.getClass()
            .getName());

    private final Logger log = Logger.getLogger(AssignmentDeliveryImpl.class.getName());

    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.impl.AssignmentDeliveryService#deliverAssignment(com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext)
	 */
    public void deliverAssignment(AssignmentServiceContext context)
            throws AssignmentDeliveryException {
        final String methodName = "deliverAssignment(AssignmentServiceContext)";
        mLogger.entering(methodName);
        //Assert proper state.
        guard();

        AssignmentDeliveryUtils assignDeliveryUtils = new AssignmentDeliveryUtils();

        try {
            String handlerType = assignDeliveryUtils.getUserCustomSetting(context.getUserInfo(),
                    AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_DESTINATION);

            if (log.isLoggable(Level.FINE)) {
                log.fine("handlerType: " + handlerType);
            }

            String handlerName = AssignmentDeliveryConfig.getHanlderName(handlerType);

            if (log.isLoggable(Level.FINE)) {
                log.fine("handlerName: " + handlerName);
            }

            AssignmentDeliveryHandler handler = assignmentDeliveryHandlerFactory.getInstance(handlerName);
		
			           handler.deliverAssignment(context);
        }
        catch (AssignmentDeliveryException adse) {
            throw adse;
        }
        catch (Exception e) {
            mLogger.severe(e.getMessage());
            ErrorLoggingService.logError(AssignmentDeliveryErrorCodes.GENERAL_ERROR, null,
                    CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, context
                            .getWorkItemId(), e.getMessage(), null, 0, e);
            throw mLogger.createException(AssignmentDeliveryErrorCodes.GENERAL_ERROR, context
                    .getWorkItemId(), e);
        }
        finally {
            mLogger.exiting(methodName);
        }
    }

    private void guard() {
		if (assignmentDeliveryHandlerFactory == null || assignmentDeliveryHandlerFactory.getHandlerMap() == null){
			throw new IllegalStateException("Please initialize the Assignement Deleivery Service bean,  class [" + getClass().getName()+"]");
		}
		
	}

	// --  New method for Jetta/SIP3.5 - 
    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.impl.AssignmentDeliveryService#cancelAssignment(com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext)
	 */
    public void cancelAssignment(AssignmentServiceContext context)
            throws AssignmentDeliveryException {
        final String methodName = "cancelAssignment(AssignmentServiceContext)";
        mLogger.entering(methodName);

        AssignmentDeliveryUtils assignDeliveryUtils = new AssignmentDeliveryUtils();

        try {
            String handlerType = assignDeliveryUtils.getUserCustomSetting(context.getUserInfo(),
                    AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_DESTINATION);

            if (log.isLoggable(Level.FINE)) {
                log.fine("handlerType: " + handlerType);
            }

            String handlerName = AssignmentDeliveryConfig.getHanlderName(handlerType);

            if (log.isLoggable(Level.FINE)) {
                log.fine("handlerName: " + handlerName);
            }

            AssignmentDeliveryHandler handler = assignmentDeliveryHandlerFactory.getInstance(handlerName);

            handler.cancelAssignment(context);
        }
        catch (AssignmentDeliveryException adse) {
            throw adse;
        }
        catch (Exception e) {
            mLogger.severe(e.getMessage());
            ErrorLoggingService.logError(AssignmentDeliveryErrorCodes.GENERAL_ERROR, null,
                    CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, context
                            .getWorkItemId(), e.getMessage(), null, 0, e);
            throw mLogger.createException(AssignmentDeliveryErrorCodes.GENERAL_ERROR, context
                    .getWorkItemId(), e);
        }
        finally {
            mLogger.exiting(methodName);
        }
    }    
    
    
}
