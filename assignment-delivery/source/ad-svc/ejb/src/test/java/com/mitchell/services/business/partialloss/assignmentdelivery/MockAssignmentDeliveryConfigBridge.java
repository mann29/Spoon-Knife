package com.mitchell.services.business.partialloss.assignmentdelivery;

public class MockAssignmentDeliveryConfigBridge implements
		AssignmentDeliveryConfigBridge {
	private String JNDIProviderUrl;
	private String EJBName;
	private String fileArchiveStagingSubdir;
	private String tempDir;
	private String cancellationAlertMsg;
	private String suppToOrigNoticeFilePath;
	private boolean simpleNotificationFlag;
	private String errorEmailFromName;
	private String errorEmailFromAddr;
	private String errorFaxFromName;
	private String errorFaxFromAddr;
	private String errorFaxFromNumber;
	private String primaryEmailFromName;
	private String primaryEmailFromAddr;
	private String primaryFaxFromName;
	private String primaryFaxFromAddr;
	private String primaryFaxFromNumber;
	private String hanlderName;

	public String getJNDIProviderUrl() {
		return JNDIProviderUrl;
	}
	public void setJNDIProviderUrl(String jNDIProviderUrl) {
		JNDIProviderUrl = jNDIProviderUrl;
	}
	public String getEJBName() {
		return EJBName;
	}
	public void setEJBName(String eJBName) {
		EJBName = eJBName;
	}
	public String getFileArchiveStagingSubdir() {
		return fileArchiveStagingSubdir;
	}
	public void setFileArchiveStagingSubdir(String fileArchiveStagingSubdir) {
		this.fileArchiveStagingSubdir = fileArchiveStagingSubdir;
	}
	public String getTempDir() {
		return tempDir;
	}
	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}
	public String getCancellationAlertMsg() {
		return cancellationAlertMsg;
	}
	public void setCancellationAlertMsg(String cancellationAlertMsg) {
		this.cancellationAlertMsg = cancellationAlertMsg;
	}
	public String getSuppToOrigNoticeFilePath() {
		return suppToOrigNoticeFilePath;
	}
	public void setSuppToOrigNoticeFilePath(String suppToOrigNoticeFilePath) {
		this.suppToOrigNoticeFilePath = suppToOrigNoticeFilePath;
	}
	public boolean getSimpleNotificationFlag() {
		return simpleNotificationFlag;
	}
	public void setSimpleNotificationFlag(boolean simpleNotificationFlag) {
		this.simpleNotificationFlag = simpleNotificationFlag;
	}
	public String getErrorEmailFromName() {
		return errorEmailFromName;
	}
	public void setErrorEmailFromName(String errorEmailFromName) {
		this.errorEmailFromName = errorEmailFromName;
	}
	public String getErrorEmailFromAddr() {
		return errorEmailFromAddr;
	}
	public void setErrorEmailFromAddr(String errorEmailFromAddr) {
		this.errorEmailFromAddr = errorEmailFromAddr;
	}
	public String getErrorFaxFromName() {
		return errorFaxFromName;
	}
	public void setErrorFaxFromName(String errorFaxFromName) {
		this.errorFaxFromName = errorFaxFromName;
	}
	public String getErrorFaxFromAddr() {
		return errorFaxFromAddr;
	}
	public void setErrorFaxFromAddr(String errorFaxFromAddr) {
		this.errorFaxFromAddr = errorFaxFromAddr;
	}
	public String getErrorFaxFromNumber() {
		return errorFaxFromNumber;
	}
	public void setErrorFaxFromNumber(String errorFaxFromNumber) {
		this.errorFaxFromNumber = errorFaxFromNumber;
	}
	public String getPrimaryEmailFromName() {
		return primaryEmailFromName;
	}
	public void setPrimaryEmailFromName(String primaryEmailFromName) {
		this.primaryEmailFromName = primaryEmailFromName;
	}
	public String getPrimaryEmailFromAddr() {
		return primaryEmailFromAddr;
	}
	public void setPrimaryEmailFromAddr(String primaryEmailFromAddr) {
		this.primaryEmailFromAddr = primaryEmailFromAddr;
	}
	public String getPrimaryFaxFromName() {
		return primaryFaxFromName;
	}
	public void setPrimaryFaxFromName(String primaryFaxFromName) {
		this.primaryFaxFromName = primaryFaxFromName;
	}
	public String getPrimaryFaxFromAddr() {
		return primaryFaxFromAddr;
	}
	public void setPrimaryFaxFromAddr(String primaryFaxFromAddr) {
		this.primaryFaxFromAddr = primaryFaxFromAddr;
	}
	public String getPrimaryFaxFromNumber() {
		return primaryFaxFromNumber;
	}
	public void setPrimaryFaxFromNumber(String primaryFaxFromNumber) {
		this.primaryFaxFromNumber = primaryFaxFromNumber;
	}
	public String getHanlderName(String handlerType) {
		return hanlderName;
	}
	public void setHanlderName(String handlerType, String hanlderName) {
		this.hanlderName = hanlderName;
	}
	
	public boolean isCrossSupplementationAllowed()
			throws AssignmentDeliveryException {
		// TODO Auto-generated method stub
		return false;
	}
}
