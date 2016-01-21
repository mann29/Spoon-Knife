package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.mitchell.common.exception.MitchellException;

/**
 * The AssignmentDeliveryException indicates a failure in the AssignmentDeliverySvc.
 */
public class AssignmentDeliveryException extends MitchellException 
{

	/**
	 * The only constructor brought up from MitchellException.
	 * 
	 * @param className
	 *            String The class where the error occurred.
	 * @param methodName
	 *            String The method where the error occurred.
	 * @param desc
	 *            String Description of the error.
	 */
	public AssignmentDeliveryException(String className, String methodName, String desc) {
		super(className, methodName, desc);
	}

	/**
	 * The only constructor brought up from MitchellException.
	 * 
	 * @param errorNumber
	 *            int the error number that is registered for the occurrence.
	 * @param className
	 *            String The class where the error occurred.
	 * @param methodName
	 *            String The method where the error occurred.
	 * @param desc
	 *            String Description of the error.
	 */
	public AssignmentDeliveryException(int errorNumber, String className, String methodName, String desc) {
		super(errorNumber, className, methodName, desc);
	}

	/**
	 * The only constructor brought up from MitchellException.
	 * 
	 * @param errorNumber
	 *            int the error number that is registered for the occurrence.
	 * @param className
	 *            String The class where the error occurred.
	 * @param methodName
	 *            String The method where the error occurred.
	 * @param workItemId
	 *            String The workflow where the error occured.
	 * @param desc
	 *            String Description of the error.
	 */
	public AssignmentDeliveryException(int errorNumber, String className, String methodName, String workItemID, String desc) {
		super(errorNumber, className, methodName, workItemID, desc);
	}

	/**
	 * The only constructor brought up from MitchellException.
	 * 
	 * @param className
	 *            String The class where the error occurred.
	 * @param methodName
	 *            String The method where the error occurred.
	 * @param desc
	 *            String Description of the error.
	 * @param t
	 *            Throwable The exception that occurred or is being created.
	 */
	public AssignmentDeliveryException(String className, String methodName, String desc, Throwable t) {
		super(className, methodName, desc, t);
	}

	/**
	 * The only constructor brought up from MitchellException.
	 * 
	 * @param errorNumber
	 *            int the error number that is registered for the occurrence.
	 * @param className
	 *            String The class where the error occurred.
	 * @param methodName
	 *            String The method where the error occurred.
	 * @param workItemId
	 *            String The workflow where the error occured.
	 * @param desc
	 *            String Description of the error.
	 * @param t
	 *            Throwable The exception that occurred or is being created.
	 */
	public AssignmentDeliveryException(int errorNumber, String className, String methodName, String workItemID, String desc, Throwable t) {
		super(errorNumber, className, methodName, workItemID, desc, t);
	}

	/**
	 * The only constructor brought up from MitchellException.
	 * 
	 * @param errorNumber
	 *            int the error number that is registered for the occurrence.
     * @param className
	 *            String The class where the error occurred.
	 * @param methodName
	 *            String The method where the error occurred.
	 * @param desc
	 *            String Description of the error.
	 * @param t
     *            Throwable The exception that occurred or is being created.
	 */
	public AssignmentDeliveryException(int errorNumber, String className, String methodName, String desc, Throwable t) {
		super(errorNumber, className, methodName, desc, t);
	}

}
