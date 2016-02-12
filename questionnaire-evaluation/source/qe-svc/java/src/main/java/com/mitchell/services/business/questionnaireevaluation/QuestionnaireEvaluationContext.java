package com.mitchell.services.business.questionnaireevaluation; 

import com.mitchell.common.types.UserInfoDocument;

/**
 * Context object..
 */

public class QuestionnaireEvaluationContext { 
    
    /**
     * evaluationID..
     */
    private String evaluationID;
    
    /**
     * claimID..
     */
    private long claimID;
    
    /**
     * suffixID..
     */
    private long suffixID;
    
    /**
     * userInfoDoc..
     */
    private UserInfoDocument userInfoDoc;
    
    /**
     * workItemId..
     */
    private String workItemId;
    
    /**
     * documentId..
     */
    private long documentId;
    
    /**
     * TCN.
     */
    private long tcn;
    
    /**
     * exposureId..
     */
   private long exposureId;
   
   /**
    * claimNumber..
    */
   private String claimNumber;
   
   /**
    * sourceSystem..
    */
   private String sourceSystem;   

   private String companyCode;
   
   private long questionnaireId;
   
   private boolean isSetDocumentId;
   
   private boolean isSetExposureId;
    /**
    * This method gets sourceSystem.
    * @return sourceSystem
    */
    public String getSourceSystem() {
        return sourceSystem;
    }

    /**
    * This method sets sourceSystem.
    * @param sourceSystem
    * <code>String</code> sourceSystem
    */
    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }
   
   
    /**
    * This method gets evaluationID.
    * @return evaluationID
    */
    public String getEvaluationID() {
        return evaluationID;
    }

    /**
    * This method sets evaluationID.
    * @param evaluationId
    * <code>Long</code> evaluationId
    */
    public void setEvaluationID(String evaluationId) {
        this.evaluationID = evaluationId;
    }
    
    /**
    * This method gets claimNumber.
    * @return claimNumber
    */
    public String getClaimNumber() {
        return claimNumber;
    }

    /**
    * This method sets claimNumber.
    * @param claimNo
    * <code>Long</code> claimNo
    */
    public void setClaimNumber(String claimNo) {
        this.claimNumber = claimNo;
    }
    /**
    * This method gets userInfoDoc.
    * @return userInfoDoc
    */
    public UserInfoDocument getUserInfoDoc() {
        return userInfoDoc;
    }
    
    /**
    * This method sets userInfoDoc.
    * @param userInfo
    * <code>UserInfoDocument</code> userInfo
    */
    public void setUserInfoDoc(UserInfoDocument userInfo) {
        this.userInfoDoc = userInfo;
    }
    
    /**
    * This method gets claimID.
    * @return claimID
    */
    public long getClaimId() {
        return claimID;
    }

    /**
    * This method sets claimID.
    * @param claimId
    * <code>Long</code> claimId
    */
    public void setClaimId(long claimId) {
        this.claimID = claimId;
    }
    
    /**
    * This method gets suffixID.
    * @return suffixID
    */
    public long getSuffixId() {
        return suffixID;
    }
    
    /**
    * This method sets suffixID.
    * @param suffixId
    * <code>Long</code> suffixId
    */
    public void setSuffixId(long suffixId) {
        this.suffixID = suffixId;
    }
    
    /**
    * This method gets exposureId.
    * @return exposureId
    */
    public long getExposureId() {
        return exposureId;
    }

    /**
    * This method sets exposureId.
    * @param exposureID
    * <code>Long</code> exposureID
    */
    public void setExposureId(long exposureID) {
        this.exposureId = exposureID;
    }

/**
    * This method gets workItemID.
    * @return workItemID
    */
    public String getWorkItemId() {
        return workItemId;
    }

    /**
    * This method sets workItemID.
    * @param workItemID
    * <code>Long</code> workItemID
    */
    public void setWorkItemId(String workItemID) {
        this.workItemId = workItemID;
    }
    
    /**
    * This method gets documentId.
    * @return documentId
    */
    public long getDocumentId() {
        return documentId;
    }

    /**
    * This method sets documentId.
    * @param documentID
    * <code>String</code> documentID
    */
    public void setDocumentId(long documentID) {
        this.documentId = documentID;
    }
    
    /**
    * This method gets TCN.
    * @return TCN
    */
    public long getTcn() {
        return tcn;
    }

    /**
    * This method sets TCN.
    * @param transactionControlNo
    * <code>long</code> transactionControlNo
    */
    public void setTcn(long transactionControlNo) {
        this.tcn = transactionControlNo;
    }


    
    /**
    * This method gets companyCode.
    * @return companyCode
    */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
    * This method sets companyCode.
    * @param companyCode
    * <code>String</code> companyCode
    */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }   
   
   /**
    * This method gets questionnaireId.
    * @return questionnaireId
    */
    public long getQuestionnaireId() {
        return questionnaireId;
    }

    /**
    * This method sets questionnaireId.
    * @param questionnaireId
    * <code>String</code> questionnaireId
    */
    public void setQuestionnaireId(long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }
   
    /**
    * This method gets isSetDocumentId.
    * @return isSetDocumentId
    */
    public boolean getIsSetDocumentId() {
        return isSetDocumentId;
    }

    /**
    * This method sets isSetDocumentId.
    * @param isSetDocumentId
    * <code>boolean</code> isSetDocumentId
    */
    public void setIsSetDocumentId(boolean isSetDocumentId) {
        this.isSetDocumentId = isSetDocumentId;
    }
     
    /**
    * This method gets isSetExposureId.
    * @return isSetExposureId
    */
    public boolean getIsSetExposureId() {
        return isSetExposureId;
    }

    /**
    * This method sets isSetExposureId.
    * @param isSetExposureId
    * <code>boolean</code> isSetExposureId
    */
    public void setIsSetExposureId(boolean isSetExposureId) {
        this.isSetExposureId = isSetExposureId;
    }
} 
