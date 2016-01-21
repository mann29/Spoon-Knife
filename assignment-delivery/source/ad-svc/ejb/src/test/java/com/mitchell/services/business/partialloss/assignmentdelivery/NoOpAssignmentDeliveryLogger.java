package com.mitchell.services.business.partialloss.assignmentdelivery;


public class NoOpAssignmentDeliveryLogger extends AbstractAssignmentDeliveryLogger{

	protected NoOpAssignmentDeliveryLogger(String name,
			String resourceBundleName) {
		super(name, resourceBundleName);

	}

	public void entering(String methodName) {

		
	}

	public void exiting(String methodName) {

		
	}

	public void exiting() {

		
	}

	public void starting(String info) {

		
	}

	public void finished() {

		
	}

	public AssignmentDeliveryException createException(String desc) {

		return new AssignmentDeliveryException(null,null,null);
	}

	public AssignmentDeliveryException createException(int errNumber,
			String desc) {

		return new AssignmentDeliveryException(null,null,null);
	}

	public AssignmentDeliveryException createException(int errNumber,
			String workItemID, String desc) {
		return new AssignmentDeliveryException(null,null,null);
	}

	public AssignmentDeliveryException createException(Throwable t) {
		return new AssignmentDeliveryException(null,null,null);
	}

	public AssignmentDeliveryException createException(String desc, Throwable t) {
		return new AssignmentDeliveryException(null,null,null);
	}

	public AssignmentDeliveryException createException(int errNumber,
			String workItemID, String desc, Throwable t) {

		return new AssignmentDeliveryException(null,null,null);
	}

	public AssignmentDeliveryException createException(int errNumber,
			String workItemID, Throwable t) {
		return new AssignmentDeliveryException(null,null,null);
	}

	public AssignmentDeliveryException createException(int errNumber,
			Throwable t, String desc) {
		return new AssignmentDeliveryException(null,null,null);
	}

	public AssignmentDeliveryException createException(int errNumber,
			Throwable t) {

		return new AssignmentDeliveryException(null,null,null);
	}
	
}