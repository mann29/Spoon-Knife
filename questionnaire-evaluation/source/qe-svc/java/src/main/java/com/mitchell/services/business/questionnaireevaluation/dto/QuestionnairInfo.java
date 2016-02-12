package com.mitchell.services.business.questionnaireevaluation.dto;

import java.io.Serializable;

public class QuestionnairInfo  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionnaireDetails questionnaireDetails;
	private QuestionnaireTree questionnaireTree;
	public QuestionnaireDetails getQuestionnaireDetails() {
		return questionnaireDetails;
	}
	public void setQuestionnaireDetails(QuestionnaireDetails questionnaireDetails) {
		this.questionnaireDetails = questionnaireDetails;
	}
	public QuestionnaireTree getQuestionnaireTree() {
		return questionnaireTree;
	}
	public void setQuestionnaireTree(QuestionnaireTree questionnaireTree) {
		this.questionnaireTree = questionnaireTree;
	}
}
