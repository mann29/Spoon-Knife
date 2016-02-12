package com.mitchell.services.business.questionnaireevaluation.dto;

import java.io.Serializable;

public class Answer implements Serializable{
  
  private static final long serialVersionUID = 1L;
  private  long qustnnerQustnId;
  private  long answerItemID;
  private  String answerDisplayText;
    private  int answerDisplayOrder;
    private  int lowRangeValue ;
    private  int highRangeValue; 
    private  int answerScore;
    private  float answrScorePercentWeight;
    private  int sysAnswrType;
    private  String isLeaf;
   
  public long getQustnnerQustnId() {
    return qustnnerQustnId;
  }
  public void setQustnnerQustnId(long qustnnerQustnId) {
    this.qustnnerQustnId = qustnnerQustnId;
  }
  public long getAnswerItemID() {
    return answerItemID;
  }
  public void setAnswerItemID(long answerItemID) {
    this.answerItemID = answerItemID;
  }
  public String getAnswerDisplayText() {
    return answerDisplayText;
  }
  public void setAnswerDisplayText(String answerDisplayText) {
    this.answerDisplayText = answerDisplayText;
  }
  public int getAnswerDisplayOrder() {
    return answerDisplayOrder;
  }
  public void setAnswerDisplayOrder(int answerDisplayOrder) {
    this.answerDisplayOrder = answerDisplayOrder;
  }
  public int getLowRangeValue() {
    return lowRangeValue;
  }
  public void setLowRangeValue(int lowRangeValue) {
    this.lowRangeValue = lowRangeValue;
  }
  public int getHighRangeValue() {
    return highRangeValue;
  }
  public void setHighRangeValue(int highRangeValue) {
    this.highRangeValue = highRangeValue;
  }
  public int getAnswerScore() {
    return answerScore;
  }
  public void setAnswerScore(int answerScore) {
    this.answerScore = answerScore;
  }
  public float getAnswrScorePercentWeight() {
    return answrScorePercentWeight;
  }
  public void setAnswrScorePercentWeight(float answrScorePercentWeight) {
    this.answrScorePercentWeight = answrScorePercentWeight;
  }
  public int getSysAnswrType() {
    return sysAnswrType;
  }
  public void setSysAnswrType(int sysAnswrType) {
    this.sysAnswrType = sysAnswrType;
  }
  public String getIsLeaf() {
    return isLeaf;
  }
  public void setIsLeaf(String isLeaf) {
    this.isLeaf = isLeaf;
  }
  

}
