package com.mitchell.services.business.questionnaireevaluation.impl;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.questionnaireevaluation.dto.Answer;
import com.mitchell.services.business.questionnaireevaluation.dto.AnswerRqRsDto;
import com.mitchell.services.business.questionnaireevaluation.dto.ContextDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.Question;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionRqRsDto;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnairInfo;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireRqRsDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireTree;

public interface QuestionnaireManager {
	String getNextQustn(QuestionnaireRqRsDTO requestDto)
			throws MitchellException;

	QuestionnairInfo getQuestionnairInfo(ContextDTO contextDto)
			throws MitchellException;

	Question getCurrQustnQustnnr(QuestionnaireTree questnrtree,
			QuestionRqRsDto currQuestion);

	Answer getSelectedAnswer(Question currentQuestion, AnswerRqRsDto answer);
}
