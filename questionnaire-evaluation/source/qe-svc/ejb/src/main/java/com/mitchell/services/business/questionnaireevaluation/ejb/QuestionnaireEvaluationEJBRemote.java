package com.mitchell.services.business.questionnaireevaluation.ejb;

import java.util.Map;

import javax.ejb.Remote;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkDocument;


@Remote
public interface QuestionnaireEvaluationEJBRemote {
	
	 Map saveEvaluationAndLinkQEToClaim(
			String evaluationID, String clientClaimNumber,String evaluationType,
			 String evaluationDetailsXmlData,String workItemId,String mSuffixSvcRqRsData,
			 UserInfoDocument userInfoDoc)		throws MitchellException;
	
	 Map saveEvaluation(String evaluationID, String evaluationType,
            String evaluationDetailsXmlData,
            UserInfoDocument userInfoDoc) 
            throws MitchellException;
	 Map updateEvaluation(long documentId, String evaluationType,
            String evaluationDetailsXmlData,
            UserInfoDocument userInfoDoc,
            long claimId, long suffixId,
            long tcn) 
            throws MitchellException;
	 Map saveEvaluation(long claimID, long suffixID,
            String evaluationType, String evaluationDetailsXmlData,
                UserInfoDocument userInfoDoc) throws MitchellException;
	 Map deleteEvaluation(UserInfoDocument userInfoDoc, String evaluationID) 
    			throws MitchellException;
	 int linkQuestionnaireEvaluationToClaim(String evaluationID, long claimId,
            long exposureId, String claimNumber, UserInfoDocument userInfo)
            throws MitchellException;
    

	 void  associateQuestionnaireToClaimSuffix(QuestionnaireClaimLinkDocument qstnrClaimLinkDoc,
				UserInfoDocument userInfo)
				throws MitchellException;
	
	 void updateSetQuestionnaireReviewDocId(String coCode,long claimId,long suffixId,long questinnaireId,long setId,String reviewType,long questEvalDocId,String userId) throws MitchellException;
    
	
	 /**
	   * This API is used to save the evaluation details after each single question answered by the user and in return,
	   * returns the next question that needs to be answered.
	   * @param inputJson : This is a json object that contains QuestionniareDTO along with other context details.
	   * @return String : Response string will contain the next question details that used needs to answer.
	   * @throws MitchellException
	   */
	String saveUpdateEvalAndGetNxtQustn(String inputJson) throws MitchellException;
	/**
	 * This API is used to get the next question of the partially saved questionnaire
	 * @param String companyCd , String documentId - of the saved evaluation
	 * @return String : Response string will contain the next question details that used needs to answer.
	 * @throws MitchellException
	 */
	String getNextPartialSavedQuestion(String companyCd , String documentId) throws MitchellException;
	/**
	 * This API is used to get the first question of the FNOL questionnaire
	 * @param String companyCd 
	 * @return String : Response string will contain the next question details that used needs to answer.
	 * @throws MitchellException
	 */
	String getFirstQuestion(String companyCd) throws MitchellException;
}
