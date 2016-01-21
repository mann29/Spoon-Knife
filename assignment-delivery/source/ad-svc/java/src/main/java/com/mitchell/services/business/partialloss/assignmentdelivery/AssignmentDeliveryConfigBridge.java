package com.mitchell.services.business.partialloss.assignmentdelivery;

public interface AssignmentDeliveryConfigBridge {

	public abstract String getJNDIProviderUrl()
			throws AssignmentDeliveryException;

	public abstract String getEJBName() throws AssignmentDeliveryException;

	public abstract String getFileArchiveStagingSubdir()
			throws AssignmentDeliveryException;

	public abstract String getTempDir() throws AssignmentDeliveryException;

	public abstract String getCancellationAlertMsg()
			throws AssignmentDeliveryException;

	public abstract String getSuppToOrigNoticeFilePath()
			throws AssignmentDeliveryException;

	public abstract boolean getSimpleNotificationFlag()
			throws AssignmentDeliveryException;

	public abstract String getErrorEmailFromName()
			throws AssignmentDeliveryException;

	public abstract String getErrorEmailFromAddr()
			throws AssignmentDeliveryException;

	public abstract String getErrorFaxFromName()
			throws AssignmentDeliveryException;

	public abstract String getErrorFaxFromAddr()
			throws AssignmentDeliveryException;

	public abstract String getErrorFaxFromNumber()
			throws AssignmentDeliveryException;

	public abstract String getPrimaryEmailFromName()
			throws AssignmentDeliveryException;

	public abstract String getPrimaryEmailFromAddr()
			throws AssignmentDeliveryException;

	public abstract String getPrimaryFaxFromName()
			throws AssignmentDeliveryException;

	public abstract String getPrimaryFaxFromAddr()
			throws AssignmentDeliveryException;

	public abstract String getPrimaryFaxFromNumber()
			throws AssignmentDeliveryException;

	public abstract String getHanlderName(String handlerType)
			throws AssignmentDeliveryException;

	public abstract boolean isCrossSupplementationAllowed() 
			throws AssignmentDeliveryException;
}