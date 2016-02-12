package com.mitchell.services.business.questionnaireevaluation.dto;

import java.io.Serializable;
import java.util.List;

public class QustnnrQustnRqRsDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  long qustnnreId;
	private int qustnnreVersion;
	private List<QuestionRqRsDto> questionsList;
	
	public int getQustnnreVersion() {
		return qustnnreVersion;
	}
	public void setQustnnreVersion(int qustnnreVersion) {
		this.qustnnreVersion = qustnnreVersion;
	}
	public long getQustnnreId() {
		return qustnnreId;
	}
	public void setQustnnreId(long qustnnreId) {
		this.qustnnreId = qustnnreId;
	}
	public List<QuestionRqRsDto> getQuestionsList() {
		return questionsList;
	}
	public void setQuestionsList(List<QuestionRqRsDto> questionsList) {
		this.questionsList = questionsList;
	}
}
