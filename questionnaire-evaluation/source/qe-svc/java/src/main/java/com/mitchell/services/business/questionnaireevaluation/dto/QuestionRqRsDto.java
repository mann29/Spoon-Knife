package com.mitchell.services.business.questionnaireevaluation.dto;

import java.io.Serializable;
import java.util.List;

public class QuestionRqRsDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  long qustnnreQustnId;
	private  String  qustnText;
	private  String answrControlType;
	private String qustnFormattedText;
	List<AnswerRqRsDto> answerList;
	
	public String getQustnFormattedText() {
		return qustnFormattedText;
	}
	public void setQustnFormattedText(String qustnFormattedText) {
		this.qustnFormattedText = qustnFormattedText;
	}
	public long getQustnnreQustnId() {
		return qustnnreQustnId;
	}
	public void setQustnnreQustnId(long qustnnreQustnId) {
		this.qustnnreQustnId = qustnnreQustnId;
	}
	public String getQustnText() {
		return qustnText;
	}
	public void setQustnText(String qustnText) {
		this.qustnText = qustnText;
	}
	public String getAnswrControlType() {
		return answrControlType;
	}
	public void setAnswrControlType(String answrControlType) {
		this.answrControlType = answrControlType;
	}
	public List<AnswerRqRsDto> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List<AnswerRqRsDto> answerList) {
		this.answerList = answerList;
	}
	
}
