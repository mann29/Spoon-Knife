package com.mitchell.services.business.questionnaireevaluation.dto;

import java.io.Serializable;

public class AnswerRqRsDto implements Serializable {

   private static final long serialVersionUID = 1L;

   private long qustnnreQustnId;
   private long answerItemID;
   private String answerDisplayText;
   private int answerDisplayOrder;
   private int lowRangeValue;
   private int highRangeValue;
   
   public long getQustnnreQustnId() {
      return qustnnreQustnId;
   }
   public void setQustnnreQustnId(long qustnnreQustnId) {
      this.qustnnreQustnId = qustnnreQustnId;
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
   
   
}
