/**
 * 
 */
package com.mitchell.services.business.partialloss.appraisalassignment.dto;

import java.io.Serializable;

/**
 * @author rk104152
 *
 */
public class AssignmentFailureResponseDTO implements Serializable {
	private static final long serialVersionUID = 6128016096756071381L;
	private String errDesciption = "";
	private int errCode;
	private String userId = "";
	private String claimNumber = "";
	private String transactionType = "";
	private String reqUid = "";
	private String partnerKey = "";
	private long claimId;
	private long suffixId;
	private String compCode = "";
	private String userInfoDocument = "";
    private String responseMessage = "";
    private String errorType = "";
    private String correlationId = "";
    private String workItemId = "";
    private String submitterId="";
    
	public String getSubmitterId() {
		return submitterId;
	}
	public void setSubmitterId(String submitterId) {
		this.submitterId = submitterId;
	}
	public String getWorkItemId() {
		return workItemId;
	}
	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getUserInfoDocument() {
		return userInfoDocument;
	}
	public void setUserInfoDocument(String userInfoDocument) {
		this.userInfoDocument = userInfoDocument;
	}
	public long getClaimId() {
		return claimId;
	}
	public void setClaimId(long l) {
		this.claimId = l;
	}
	public long getSuffixId() {
		return suffixId;
	}
	public void setSuffixId(long suffixId) {
		this.suffixId = suffixId;
	}
	public String getCompCode() {
		return compCode;
	}
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
	public String getErrDesciption() {
		return errDesciption;
	}
	public void setErrDesciption(String errDesciption) {
		this.errDesciption = errDesciption;
	}
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getReqUid() {
		return reqUid;
	}
	public void setReqUid(String reqUid) {
		this.reqUid = reqUid;
	}
	public String getPartnerKey() {
		return partnerKey;
	}
	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

}
