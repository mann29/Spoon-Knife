package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import java.util.ArrayList;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;

public interface ADSProxy {

	/**
	 * This method delivers Assignment Email.
	 * @param context
	 * @throws MitchellException
	 */
	void deliverAssignmentEmail(APDDeliveryContextDocument context)
			throws MitchellException;

	/**
	 * This method delivers Upload Success Email.
	 * @param context
	 * @throws MitchellException
	 */
	void deliverUploadSuccessEmail(APDDeliveryContextDocument context)
			throws MitchellException;

	/**
	 * This method delivers Upload Fail Email.
	 * @param context
	 * @throws MitchellException
	 */
	void deliverUploadFailEmail(APDDeliveryContextDocument context)
			throws MitchellException;

	/**
	 * This method delivers Assignment APDDeliveryContextDocument.
	 * @param context
	 * @throws AssignmentDeliveryException
	 * @throws MitchellException
	 */
	void deliverAssignment(APDDeliveryContextDocument context)
			throws AssignmentDeliveryException, MitchellException;

	/**
	 * This method Deliver Assignment using AssignmentServiceContext.
	 * @param context
	 * @throws AssignmentDeliveryException
	 * @throws MitchellException
	 */
	void deliverAssignment(AssignmentServiceContext context)
			throws AssignmentDeliveryException, MitchellException;

	/**
	 * This method cancels Assignment using AssignmentServiceContext.
	 * @param context
	 * @throws AssignmentDeliveryException
	 * @throws MitchellException
	 */
	void cancelAssignment(AssignmentServiceContext context)
			throws AssignmentDeliveryException, MitchellException;

    /**
     * This method deliver Assignment email for DRP.
     * @param context
     * @throws MitchellException
     */
    void deliverAssignmentEmail4DRP(
            APDDeliveryContextDocument context) throws MitchellException;
    
    /**
     * This method delivers Upload Success Email for DRP.
     * @param context
     * @throws MitchellException
     */
    void deliverUploadSuccessEmail4DRP(
            APDDeliveryContextDocument context) throws MitchellException;
    
    /**
     * This method delivers Upload Fail Email for DRP.
     * @param context
     * @throws MitchellException
     */
    void deliverUploadFailEmail4DRP(
            APDDeliveryContextDocument context) throws MitchellException;
    
    /**
     * This method delivers Network Shop Supplement Assignment Email.
     * @param context
     * @param partsListAttachment
     * @throws MitchellException
     */
    void deliverNetworkShopSuppAsgEmail(
            APDDeliveryContextDocument context,
            ArrayList partsListAttachment) throws MitchellException;

    /**
     * This method delivers Non Network Shop Supplement Assignment Email. 
     * @param context
     * @param partsListAttachment
     * @throws MitchellException
     */
    void deliverNonNetworkShopSuppAsgEmail(
            APDDeliveryContextDocument context,
            ArrayList partsListAttachment) throws MitchellException;
    
    /**
     * This method deliver Assignment Email Notification.
     * @param apdDelContext
     * @param partsListAttachment
     * @param emailType
     * @throws MitchellException
     */
    void deliverAssignmentEmailNotification(
			APDDeliveryContextDocument apdDelContext,
			ArrayList partsListAttachment,
			String emailType) throws MitchellException;

    /**
     * This method delivers to RCWeb an Assignment APDDeliveryContextDocument.
     * @param context
     * @throws AssignmentDeliveryException
     * @throws MitchellException
     */
    void deliverRCWebAssignment(APDDeliveryContextDocument context)
            throws AssignmentDeliveryException, MitchellException;
}
