package com.mitchell.services.business.questionnaireevaluation.dto;

import java.io.Serializable;

public class ContextDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	long documentId;
	String coCode;
	long questionnaireID;
	
	public long getQuestionnaireID() {
		return questionnaireID;
	}
	public void setQuestionnaireID(long questionnaireID) {
		this.questionnaireID = questionnaireID;
	}
	public String getCoCode() {
		return coCode;
	}
	public void setCoCode(String coCode) {
		this.coCode = coCode;
	}
	public long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}
	
	
}
