package com.mitchell.services.business.partialloss.assignmentdelivery;

public class AssignmentDeliveryConfigBridgeImpl implements AssignmentDeliveryConfigBridge {
//	private String JNDIProviderUrl;
//	private String EJBName;
//	private String fileArchiveStagingSubdir;
//	private String tempDir;
//	private String cancellationAlertMsg;
//	private String suppToOrigNoticeFilePath;
//	private boolean simpleNotificationFlag;
//	private String errorEmailFromName;
//	private String errorEmailFromAddr;
//	private String errorFaxFromName;
//	private String errorFaxFromAddr;
//	private String errorFaxFromNumber;
//	private String primaryEmailFromName;
//	private String primaryEmailFromAddr;
//	private String primaryFaxFromName;
//	private String primaryFaxFromAddr;
//	private String primaryFaxFromNumber;
//	private String hanlderName;
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getJNDIProviderUrl()
	 */
	public String getJNDIProviderUrl() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getJNDIProviderUrl();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getEJBName()
	 */
	public String getEJBName() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getEJBName();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getFileArchiveStagingSubdir()
	 */
	public String getFileArchiveStagingSubdir() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getFileArchiveStagingSubdir();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getTempDir()
	 */
	public String getTempDir() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getTempDir();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getCancellationAlertMsg()
	 */
	public String getCancellationAlertMsg() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getCancellationAlertMsg();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getSuppToOrigNoticeFilePath()
	 */
	public String getSuppToOrigNoticeFilePath() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getSuppToOrigNoticeFilePath();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getSimpleNotificationFlag()
	 */
	public boolean getSimpleNotificationFlag() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getSimpleNotificationFlag();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getErrorEmailFromName()
	 */
	public String getErrorEmailFromName() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getErrorEmailFromName();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getErrorEmailFromAddr()
	 */
	public String getErrorEmailFromAddr() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getErrorEmailFromAddr();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getErrorFaxFromName()
	 */
	public String getErrorFaxFromName() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getErrorFaxFromName();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getErrorFaxFromAddr()
	 */
	public String getErrorFaxFromAddr() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getErrorFaxFromAddr();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getErrorFaxFromNumber()
	 */
	public String getErrorFaxFromNumber() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getErrorFaxFromNumber();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getPrimaryEmailFromName()
	 */
	public String getPrimaryEmailFromName() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getPrimaryEmailFromName();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getPrimaryEmailFromAddr()
	 */
	public String getPrimaryEmailFromAddr() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getPrimaryEmailFromAddr();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getPrimaryFaxFromName()
	 */
	public String getPrimaryFaxFromName() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getPrimaryFaxFromName();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getPrimaryFaxFromAddr()
	 */
	public String getPrimaryFaxFromAddr() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getPrimaryFaxFromAddr();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getPrimaryFaxFromNumber()
	 */
	public String getPrimaryFaxFromNumber() throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getPrimaryFaxFromNumber();
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge#getHanlderName(java.lang.String)
	 */
	public String getHanlderName(String handlerType) throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.getHanlderName(handlerType);
	}
	
	public boolean isCrossSupplementationAllowed()
            throws AssignmentDeliveryException {
		return AssignmentDeliveryConfig.isCrossSupplementationAllowed();
	}
}
