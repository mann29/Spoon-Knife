package com.mitchell.services.business.questionnaireevaluation.dto;

import java.io.Serializable;

public class QuestionnaireDetails implements Serializable {

   private static final long serialVersionUID = 1L;
   private String coCd;
   private long qustnnreId;
   private String qustnnreName;
   private int qustnnreVersion;
   private String qustnnreDesc;
   private String evltnCategory;
   private int b6;
   private String scoringType;
   private int maxPoints;
   private String isCustomScore;

   public String getCoCd() {
      return coCd;
   }

   public void setCoCd(String coCd) {
      this.coCd = coCd;
   }

   public long getQustnnreId() {
      return qustnnreId;
   }

   public void setQustnnreId(long qustnnreId) {
      this.qustnnreId = qustnnreId;
   }

   public String getQustnnreName() {
      return qustnnreName;
   }

   public void setQustnnreName(String qustnnreName) {
      this.qustnnreName = qustnnreName;
   }

   public int getQustnnreVersion() {
      return qustnnreVersion;
   }

   public void setQustnnreVersion(int qustnnreVersion) {
      this.qustnnreVersion = qustnnreVersion;
   }

   public String getQustnnreDesc() {
      return qustnnreDesc;
   }

   public void setQustnnreDesc(String qustnnreDesc) {
      this.qustnnreDesc = qustnnreDesc;
   }

   public String getEvltnCategory() {
      return evltnCategory;
   }

   public void setEvltnCategory(String evltnCategory) {
      this.evltnCategory = evltnCategory;
   }

   public int getB6() {
      return b6;
   }

   public void setB6(int b6) {
      this.b6 = b6;
   }

   public String getScoringType() {
      return scoringType;
   }

   public void setScoringType(String scoringType) {
      this.scoringType = scoringType;
   }

   public int getMaxPoints() {
      return maxPoints;
   }

   public void setMaxPoints(int maxPoints) {
      this.maxPoints = maxPoints;
   }

   public String getIsCustomScore() {
      return isCustomScore;
   }

   public void setIsCustomScore(String isCustomScore) {
      this.isCustomScore = isCustomScore;
   }

}
