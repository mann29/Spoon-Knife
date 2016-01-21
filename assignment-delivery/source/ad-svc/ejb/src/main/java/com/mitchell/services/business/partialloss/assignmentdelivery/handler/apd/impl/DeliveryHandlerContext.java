package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import com.cieca.bms.CIECADocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoType;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class DeliveryHandlerContext {

    private AdditionalAppraisalAssignmentInfoType additionalAppraisalAssignmentInfoType;

    private APDDeliveryContextDocument apdDeliveryContextDocument;

    public AnnotatedLogger getLog() {
        return log;
    }

    public void setLog(AnnotatedLogger log) {
        this.log = log;
    }

    private AnnotatedLogger log;

    private String primaryContact;

    private long vid;

    private Long estimateDocumentId;

    public APDDeliveryContextType getApdDeliveryContext() {
        return apdDeliveryContextType;
    }

    public void setApdDeliveryContextType(APDDeliveryContextType apdDeliveryContextType) {
        this.apdDeliveryContextType = apdDeliveryContextType;
    }

    private APDDeliveryContextType apdDeliveryContextType;


    public BaseAPDCommonType getApdCommonInfo() {
        return apdCommonInfo;
    }

    public void setApdCommonInfo(BaseAPDCommonType apdCommonInfo) {
        this.apdCommonInfo = apdCommonInfo;
    }

    private BaseAPDCommonType apdCommonInfo;


    public String getWorkItemId() {
        return getApdCommonInfo().getWorkItemId();
    }


    public MitchellEnvelopeHelper getInboundEnvelope() {
        return this.inboundEnvelope;
    }

    public void setInboundEnvelope(MitchellEnvelopeHelper inboundEnvelope) {
        this.inboundEnvelope = inboundEnvelope;
    }

    private MitchellEnvelopeHelper inboundEnvelope;


    public MitchellEnvelopeHelper getOutboundEnvelope() {
        return outboundEnvelope;
    }

    public void setOutboundEnvelope(MitchellEnvelopeHelper outboundEnvelope) {
        this.outboundEnvelope = outboundEnvelope;
    }

    private MitchellEnvelopeHelper outboundEnvelope;


    public CIECADocument getAssignmentBms() {
        return assignmentBms;
    }

    public void setAssignmentBms(CIECADocument assignmentBms) {
        this.assignmentBms = assignmentBms;
    }

    private CIECADocument assignmentBms;


    public boolean getIsReDispatch() {
        return isReDispatch;
    }

    public void setIsReDispatch(boolean isReDispatch) {
        this.isReDispatch = isReDispatch;
    }

    private boolean isReDispatch;


    public String getPostedMessageId() {
        return postedMessageId;
    }

    public void setPostedMessageId(String postedMessageId) {
        this.postedMessageId = postedMessageId;
    }

    private String postedMessageId;

    public long getVid() {
        return vid;
    }

    public void setVid(long vid) {
        this.vid = vid;
    }

    public String getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(String primaryContact) {
        this.primaryContact = primaryContact;
    }

    public void setAdditionalAppraisalAssignmentInfoType(AdditionalAppraisalAssignmentInfoType additionalAppraisalAssignmentInfoType) {
        this.additionalAppraisalAssignmentInfoType = additionalAppraisalAssignmentInfoType;
    }

    public AdditionalAppraisalAssignmentInfoType getAdditionalAppraisalAssignmentInfoType() {
        return additionalAppraisalAssignmentInfoType;
    }

    public void setApdDeliveryContextDocument(APDDeliveryContextDocument apdDeliveryContextDocument) {
        this.apdDeliveryContextDocument = apdDeliveryContextDocument;
    }

    public APDDeliveryContextDocument getApdDeliveryContextDocument() {
        return apdDeliveryContextDocument;
    }

    public Long getEstimateDocumentId() {
        return estimateDocumentId;
    }

    public void setEstimateDocumentId(Long estimateDocumentId) {
        this.estimateDocumentId = estimateDocumentId;
    }
}
