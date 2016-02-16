package com.mitchell.services.core.partialloss.apddelivery.pojo;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;

public interface PlatformDeliveryHandler {

	/**
	 * This method delivers Alerts of following types.
	 * <ul>
	 * <li>Upload accepted
	 * <li>Upload rejected
	 * <li>Global alerts
	 * </ul>
	 * <p>
	 * 
	 * @param apdContext
	 * A XML Bean of type APDDeliveryContextDocument,
	 * that encapsulates the relevant infomation needed to handle alert delievry
	 * @param shopUser
	 * true- if target is a Shop user
	 * false- target is a Staff user
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract void deliverAlert(APDDeliveryContextDocument apdContext,
			boolean shopUser) throws MitchellException;

	/**
	 * This method handles delivery of Repair Assignment and Rework Assignment
	 * to Platform.
	 * 
	 * @param apdContext
	 * A XML Bean of type APDDeliveryContextDocument,
	 * that encapsulates the relevant infomation needed to handle alert delievry
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract void deliverRepairAssignment(
			APDDeliveryContextDocument apdContext) throws MitchellException;

	/**
	 * This method delivers Estimate Status.
	 * 
	 * @param apdDeliveryDoc
	 * Encapsulates the relevant infomation needed to handle 
	 * Estimate Status  delievry
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract void deliverEstimateStatus(
			APDDeliveryContextDocument apdDeliveryDoc) throws MitchellException;

	/**
	 * This method deliveres Appraisal Assignment Notification to Platform.
	 * @param apdDeliveryDoc
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract void deliverAppraisalAssignmentNotification(
			APDDeliveryContextDocument apdDeliveryDoc) throws MitchellException;

	/**
	 * This method delivers request for Staff Supplement.
	 * @param apdContext
	 * A XML Bean of type APDDeliveryContextDocument,
	 * that encapsulates the relevant infomation needed.
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract void deliverRequestForStaffSupplement(
			APDDeliveryContextDocument apdContext) throws MitchellException;

}