package com.mitchell.services.business.partialloss.assignmentdelivery;

public interface AssignmentDeliveryLoggerInterface {

	/**
	 * Convience method to require only method name as the instance variable
	 * containing class name is used. Calls super.entering(String, String).
	 * 
	 * @param methodName
	 *            String containing the method name to be used in the log
	 *            messages.
	 */
	public abstract void entering(String methodName);

	/**
	 * Overrides the super's method to store the class name if not already
	 * stored. The value of method name is stored in an instance variable for
	 * subsequent calls.
	 * 
	 * @param className
	 *            The class name to be logged.
	 * @param methodName
	 *            The method name to be logged.
	 */
	public abstract void entering(String className, String methodName);

	/**
	 * Convenience method to call super.exiting(String String) but requiring
	 * only the method name as the instance variable containing class name is
	 * used. Clears the instance method name.
	 * 
	 * @param methodName
	 *            String containing the method name to log.
	 */
	public abstract void exiting(String methodName);

	/**
	 * Convenience method that uses the stored class and method name. Clears the
	 * instance method name variable.
	 * 
	 */
	public abstract void exiting();

	public abstract void exiting(String className, String methodName);

	/**
	 * The starting method is used to output a log message as performed by
	 * calling super.info() and also stores the message to be used if an
	 * exception is created before finished() is called or another starting() is
	 * called.
	 */
	public abstract void starting(String info);

	/**
	 * Logs a "Finished..." message using the info from the last call to
	 * starting(). If starting has not been called then a message "Finished." is
	 * logged.
	 */
	public abstract void finished();

	public abstract void info(String msg);

	/**
	 * Convenience method to log an Exception using class and method names
	 * stored from previos calls. If these values
	 * 
	 * @param msg
	 *            String containing the information to be logged.
	 */
	public abstract void severe(String msg);

	/**
	 * This method creates an AssignmentDeliveryException using the class and
	 * method name's that were stored on previous calls.
	 * 
	 * @param desc
	 *            String containing the description.
	 */
	public abstract AssignmentDeliveryException createException(String desc);

	/**
	 * This method creates an AssignmentDeliveryException using the class and
	 * method name's that were stored on previous calls.
	 * 
	 * @param errNumber
	 *            int containing the error number that is registered in the
	 *            error logging service.
	 * @param desc
	 *            String containing the description.
	 */
	public abstract AssignmentDeliveryException createException(int errNumber,
			String desc);

	/**
	 * This method creates an AssignmentDeliveryException using the class and
	 * method name's that were stored on previous calls.
	 * 
	 * @param errNumber
	 *            int containing the error number that is registered in the
	 *            error logging service.
	 * @param workItemID
	 *            String containing the work item ID.
	 * @param desc
	 *            String containing the description.
	 */
	public abstract AssignmentDeliveryException createException(int errNumber,
			String workItemID, String desc);

	/**
	 * This method creates an AssignmentDeliveryException using the class and
	 * method name's that were stored on previous calls.
	 * 
	 * @param throwable
	 *            An exception that should be used as inner exception.
	 */
	public abstract AssignmentDeliveryException createException(Throwable t);

	/**
	 * This method creates an AssignmentDeliveryException using the class and
	 * method name's that were stored on previous calls.
	 * 
	 * @param desc
	 *            String containing the description.
	 * @param throwable
	 *            An exception that should be used as inner exception.
	 */
	public abstract AssignmentDeliveryException createException(String desc,
			Throwable t);

	/**
	 * This method creates an AssignmentDeliveryException using the class and
	 * method name's that were stored on previous calls.
	 * 
	 * @param errNumber
	 *            int containing the error number that is registered in the
	 *            error logging service.
	 * @param workItemID
	 *            String containing the work item ID.
	 * @param desc
	 *            String containing the description.
	 * @param throwable
	 *            An exception that should be used as inner exception.
	 */
	public abstract AssignmentDeliveryException createException(int errNumber,
			String workItemID, String desc, Throwable t);

	/**
	 * This method creates an AssignmentDeliveryException using the class and
	 * method name's that were stored on previous calls.
	 * 
	 * @param errNumber
	 *            int containing the error number that is registered in the
	 *            error logging service.
	 * @param workItemID
	 *            String containing the work item ID.
	 * @param throwable
	 *            An exception that should be used as inner exception.
	 */
	public abstract AssignmentDeliveryException createException(int errNumber,
			String workItemID, Throwable t);

	/**
	 * This method creates an AssignmentDeliveryException using the class and
	 * method name's that were stored on previous calls.
	 * 
	 * @param errNumber
	 *            int containing the error number that is registered in the
	 *            error logging service.
	 * @param throwable
	 *            An exception that should be used as inner exception.
	 * @param desc
	 *            String containing the description.
	 */
	public abstract AssignmentDeliveryException createException(int errNumber,
			Throwable t, String desc);

	/**
	 * This method creates an AssignmentDeliveryException using the class and
	 * method name's that were stored on previous calls.
	 * 
	 * @param errNumber
	 *            int containing the error number that is registered in the
	 *            error logging service.
	 * @param throwable
	 *            An exception that should be used as inner exception.
	 */
	public abstract AssignmentDeliveryException createException(int errNumber,
			Throwable t);

}