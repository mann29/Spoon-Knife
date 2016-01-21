package com.mitchell.services.business.partialloss.appraisalassignment.dto;

import org.apache.xmlbeans.GDuration;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.HoldInfo;
/**
 * 
 * Class to represent object being passed between AAH and AAS
 * 
 */
public class AppraisalAssignmentDTO implements java.io.Serializable {

    private static final long serialVersionUID = -2104896815077880937L;

    private long tcn = 0L;
    private long waTaskId = 0L;
    private long claimId = 0L;
    private long claimExposureId = 0L;
    private long documentID = 0L;
    private String status = "";
    private String disposition = "";
    private MitchellEnvelopeDocument mitchellEnvelopDoc = null;
    private String assignmentHasBeenUpdate = "N";
    private long assignmentID = 0L;
    private String clientClaimNumber = "";
    private String workItemID = "";
    private String eventReasonCode = "";
    private String eventMemo = "";
    private java.util.ArrayList eventNameList = null;
    private boolean isOriginalAssignment;
    private String reqAssociateDataCompletedInd = "N";
    private long workAssignmentTcn = 0L;
    private String timeZone = "";
    private HoldInfo holdInfo = null; 
    private boolean isSaveFromSSOPage = false; 
    private boolean saveAndSendFlag = false;
    private String subType = null;
    private GDuration duration = null;
    private Integer priority = null;
    private String shopEstimateAllowed = null;
    private String suffixStatus = null;
    
    /**
     * 
     * @return tcn TCN
     */
    public long getTcn() {
        return this.tcn;
    }

    /**
     * 
     * @param _tcn
     *            TCN
     */
    public void setTcn(final long _tcn) {
        this.tcn = _tcn;
    }

    /**
     * 
     * @return waTaskId WorkAssignmentTaskId
     */
    public long getWaTaskId() {
        return this.waTaskId;
    }

    /**
     * 
     * @param _waTaskId
     *            WorkAssignmentTaskId
     */
    public void setWaTaskId(final long _waTaskId) {
        this.waTaskId = _waTaskId;
    }

    /**
     * 
     * @return claimId ClaimID
     */
    public long getClaimId() {
        return this.claimId;
    }

    /**
     * 
     * @param _claimId
     *            ClaimID
     */
    public void setClaimId(final long _claimId) {
        this.claimId = _claimId;
    }

    /**
     * 
     * @return claimExposureId ClaimExposureId
     */
    public long getClaimExposureId() {
        return this.claimExposureId;
    }

    /**
     * 
     * @param _claimExposureId
     *            ClaimExposureId
     */
    public void setClaimExposureId(final long _claimExposureId) {
        this.claimExposureId = _claimExposureId;
    }

    /**
     * 
     * @return status WorkAssignment Status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 
     * @param sts
     *            WorkAssignment Status
     */
    public void setStatus(final String sts) {
        this.status = sts;
    }

    /**
     * 
     * @return disposition WorkAssignment Disposition
     */
    public String getDisposition() {
        return this.disposition;
    }

    /**
     * 
     * @param disp
     *            WorkAssignment Disposition
     */
    public void setDisposition(final String disp) {
        this.disposition = disp;
    }

    /**
     * 
     * @return mitchellEnvelopDoc MitchellEnvelopeDocument
     */
    public MitchellEnvelopeDocument getMitchellEnvelopDoc() {
        return this.mitchellEnvelopDoc;
    }

    /**
     * 
     * @param meDoc
     *            MitchellEnvelopeDocument
     */
    public void setMitchellEnvelopDoc(final MitchellEnvelopeDocument meDoc) {
        this.mitchellEnvelopDoc = meDoc;
    }

    /**
     * 
     * @return assignmentHasBeenUpdate WorkAssignment AssignmentHasBeenUpdate
     *         Flag
     */
    public String getAssignmentHasBeenUpdate() {
        return this.assignmentHasBeenUpdate;
    }

    /**
     * 
     * @param assignmentHasBeenUpdate
     *            WorkAssignment AssignmentHasBeenUpdate Flag
     */
    public void setAssignmentHasBeenUpdate(final String assignmentHasBeenUpdate) {
        this.assignmentHasBeenUpdate = assignmentHasBeenUpdate;
    }

    /**
     * 
     * @return documentID WorkAssignment DocumentID
     */
    public long getDocumentID() {
        return this.documentID;
    }

    /**
     * 
     * @param documentID
     *            WorkAssignment DocumentID
     */
    public void setDocumentID(final long documentID) {
        this.documentID = documentID;
    }

    /**
     * 
     * @return assignmentID WorkAssignment AssignmentID
     */
    public long getAssignmentID() {
        return assignmentID;
    }

    /**
     * 
     * @param assignmentID
     *            WorkAssignment AssignmentID
     */
    public void setAssignmentID(final long assignmentID) {
        this.assignmentID = assignmentID;
    }

    /**
     * 
     * @return clientClaimNumber ClientClaimNumber
     */
    public String getClientClaimNumber() {
        return clientClaimNumber;
    }

    /**
     * 
     * @param clientClaimNumber
     *            ClientClaimNumber
     */
    public void setClientClaimNumber(final String clientClaimNumber) {
        this.clientClaimNumber = clientClaimNumber;
    }

    /**
     * 
     * @return workItemID WorkItemID
     */
    public String getWorkItemID() {
        return workItemID;
    }

    /**
     * 
     * @param workItemID
     *            WorkItemID
     */
    public void setWorkItemID(final String workItemID) {
        this.workItemID = workItemID;
    }

    /**
     * 
     * @return eventReasonCode WorkAssignment EventReasonCode
     */
    public String getEventReasonCode() {
        return eventReasonCode;
    }

    /**
     * 
     * @param eventReasonCode
     *            WorkAssignment EventReasonCode
     */
    public void setEventReasonCode(final String eventReasonCode) {
        this.eventReasonCode = eventReasonCode;
    }

    /**
     * 
     * @return eventMemo WorkAssignment EventMemo
     */
    public String getEventMemo() {
        return eventMemo;
    }

    /**
     * 
     * @param eventMemo
     *            WorkAssignment EventMemo
     */
    public void setEventMemo(final String eventMemo) {
        this.eventMemo = eventMemo;
    }

    /**
     * 
     * @return eventNameList EventNameList
     */
    public java.util.ArrayList getEventNameList() {
        return eventNameList;
    }

    /**
     * 
     * @param eventNameList
     *            EventNameList
     */
    public void setEventNameList(final java.util.ArrayList eventNameList) {
        this.eventNameList = eventNameList;
    }

    // Start changes: for merge the saveAppraisalAssignment and
    // saveSupplementAppraisalAssignment into an one method
    public boolean isOriginalAssignment() {
        return isOriginalAssignment;
    }

    public void setOriginalAssignment(final boolean isOriginalAssignment) {
        this.isOriginalAssignment = isOriginalAssignment;
    }

    // End changes: for merge the saveAppraisalAssignment and
    // saveSupplementAppraisalAssignment into an one method

    /**
     * @param reqAssociateDataCompletedInd
     *            the reqAssociateDataCompletedInd to set
     */
    public void setReqAssociateDataCompletedInd(final String reqAssociateDataCompletedInd) {
        this.reqAssociateDataCompletedInd = reqAssociateDataCompletedInd;
    }

    /**
     * @return the reqAssociateDataCompletedInd
     */
    public String getReqAssociateDataCompletedInd() {
        return reqAssociateDataCompletedInd;
    }

    /**
     * 
     * @return Tcn workAssignmentTcn
     */
    public long getWorkAssignmentTcn() {
        return this.workAssignmentTcn;
    }

    /**
     * 
     * @param tcn
     *            _workAssignmentTcn
     */
    public void setWorkAssignmentTcn(final long _workAssignmentTcn) {
        this.workAssignmentTcn = _workAssignmentTcn;
    }

    /**
     * 
     * @return String timeZone
     */
    public String getTimeZone() {
        return this.timeZone;
    }

    /**
     * 
     * @param String
     *            _timeZone
     */
    public void setTimeZone(final String _timeZone) {
        this.timeZone = _timeZone;
    }
    
   /**
     * 
     * @param HoldInfo
     *            holdInfo
     */
    public void setHoldInfo(final HoldInfo _holdInfo) {
        this.holdInfo = _holdInfo;
    }
    
    /**
     * 
     * @return HoldInfo holdInfo
     */
    public HoldInfo getHoldInfo(){
        return this.holdInfo;
    }
    
    /**
     * 
     * @return boolean isSaveFromSSOPage
     */
    public boolean getIsSaveFromSSOPage() {
        return this.isSaveFromSSOPage;
    }

    /**
     * 
     * @param boolean isSaveFromSSOPage
     */
    public void setIsSaveFromSSOPage(final boolean isSaveFromSSOPage) {
        this.isSaveFromSSOPage = isSaveFromSSOPage;
    }
    
    /**
     * 
     * @return boolean SaveAndSendFlag
     */
    public boolean getSaveAndSendFlag() {
        return this.saveAndSendFlag;
    }

    /**
     * 
     * @param boolean SaveAndSendFlag
     */
    public void setSaveAndSendFlag(final boolean saveAndSendFlag) {
        this.saveAndSendFlag = saveAndSendFlag;
    }
    
     /**
     * 
     * @return String subType
     */
    public String getSubType() {
        return this.subType;
    }

    /**
     * 
     * @param String subType
     */
    public void setSubType(final String subType) {
        this.subType = subType;
    }

	public GDuration getDuration() {
		return duration;
	}

	public void setDuration(GDuration duration) {
		this.duration = duration;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getShopEstimateAllowed() {
		return shopEstimateAllowed;
	}

	public void setShopEstimateAllowed(String shopEstimateAllowed) {
		this.shopEstimateAllowed = shopEstimateAllowed;
	}

	public String getSuffixStatus() {
		return suffixStatus;
	}

	public void setSuffixStatus(String suffixStatus) {
		this.suffixStatus = suffixStatus;
	}
		
}