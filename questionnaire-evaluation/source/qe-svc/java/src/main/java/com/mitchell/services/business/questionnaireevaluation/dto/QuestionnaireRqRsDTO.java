package com.mitchell.services.business.questionnaireevaluation.dto;

import java.io.Serializable;

public class QuestionnaireRqRsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	ContextDTO contextDto;
	QustnnrQustnRqRsDto qustnnrQustnRqRsDto;
	
	public QustnnrQustnRqRsDto getQustnnrQustnRqRsDto() {
		return qustnnrQustnRqRsDto;
	}
	public void setQustnnrQustnRqRsDto(QustnnrQustnRqRsDto qustnnrQustnRqRsDto) {
		this.qustnnrQustnRqRsDto = qustnnrQustnRqRsDto;
	}
	public ContextDTO getContextDto() {
		return contextDto;
	}
	public void setContextDto(ContextDTO contextDto) {
		this.contextDto = contextDto;
	}
	
	
}
