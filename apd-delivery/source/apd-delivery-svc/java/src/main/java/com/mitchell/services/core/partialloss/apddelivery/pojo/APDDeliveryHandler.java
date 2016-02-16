package com.mitchell.services.core.partialloss.apddelivery.pojo; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import java.util.ArrayList;

/**
 * This interface specifies methods that handle delivery of artifacts.
 * 
 * @version %I%, %G%
 * @since 1.0
 */

public interface APDDeliveryHandler { 
    
    /**
     * This method handles delivering artifacts.
     * 
     * @param context
     * Encapsulates the relevant infomation needed to handle alert delievry
     * @throws MitchellException
     * Mitchell Exception
     * @see APDDeliveryContext
     */
    void deliverArtifact(APDDeliveryContextDocument context) 
                                                    throws MitchellException;
    
    /**
     * This method handles delivering notification for artifacts.
     * <p>
     * Only AppraisalAssignmentDeliveryHandler provides implementation for this
     * method. Rest of handlers (AlertDeliveryHandler, RepairAssignmentDeliveryHandler, 
     * and EstimateStatusDeliveryHandler) provides No-OP/void implementation.
     * <p>
     * 
     * @param context
     * Encapsulates the relevant infomation needed to handle alert delievry
     * @throws MitchellException
     * Mitchell Exception
     * @see APDDeliveryContext
     */
    void deliverArtifactNotification(APDDeliveryContextDocument context) 
                                                    throws MitchellException;
                                                    
    /**
     * This method handles delivering AppraisalAssignments with attachments.
     * <p>
     * Only AppraisalAssignmentDeliveryHandler provides implementation for this
     * method. Rest of handlers (AlertDeliveryHandler, RepairAssignmentDeliveryHandler, 
     * and EstimateStatusDeliveryHandler) provides No-OP/void implementation.
     * <p>
     * 
     * @param context
     * Encapsulates the relevant infomation needed to handle alert delievry
     * @param attachments
     * ArrayList of Attachments
     * @throws MitchellException
     * Mitchell Exception
     * @see APDDeliveryContext
     */
    void deliverArtifact(APDDeliveryContextDocument context, ArrayList attachments) 
                                                    throws MitchellException;
} 
