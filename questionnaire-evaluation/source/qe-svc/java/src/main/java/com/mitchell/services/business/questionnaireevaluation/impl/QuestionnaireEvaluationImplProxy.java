package com.mitchell.services.business.questionnaireevaluation.impl;

import java.util.Map;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.evaluationdetails.MitchellEvaluationDetailsDocument;
import com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkDocument;
import com.mitchell.services.business.questionnaireevaluation.dao.QuestionnaireEvaluationDAOProxy;
import com.mitchell.services.business.questionnaireevaluation.dto.Answer;
import com.mitchell.services.business.questionnaireevaluation.dto.Question;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireDetails;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireRqRsDTO;
import com.mitchell.services.business.questionnaireevaluation.proxy.ClaimServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.CustomSettingProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.ErrorLogProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.MSTransfromEngineProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.SystemConfigurationProxy;
import com.mitchell.services.business.questionnaireevaluation.util.QuestionnaireEvaluationUtilsProxy;

public interface QuestionnaireEvaluationImplProxy {

	
	
	
	Map<Object,Object> saveEvaluationAndLinkQEToClaim(String clientClaimNumber,
			String evaluationID, String evaluationType,
			String mSuffixSvcRqRsData,
			String evaluationDetailsXmlData,UserInfoDocument userInfoDoc, String workItemId)
			throws MitchellException;

	
	
	/**
	 * This method initializes the XML and returns documentID and other details 
	 * of saved evaluation document in Map sfrom QuestionnaireEvaluationDAO.
	 * 
	 * @param evaluationID
	 * <code>Long</code> evaluationID
	 * @param evaluationType
	 * <code>String</code> evaluationType
	 * @param evaluationDetailsXmlData
	 *   <code>String</code> evaluationDetailsXmlData
	 *  @param userInfoDoc
	 *   <code>UserInfoDocument</code> userInfoDoc
	 * @param workItemID
	 *   <code>String</code> workItemID
	 * 
	 * @return responseMap
	 * 
	 * @throws MitchellException
	 *   in case unable to save details
	 */

	Map saveEvaluation(String evaluationID,
			String evaluationType, String evaluationDetailsXmlData,
			UserInfoDocument userInfoDoc, String workItemID)
			throws MitchellException;

	/**
	 * This method initializes the XML and returns documentID and other
	 * details of saved evaluation document with claim in 
	 * Map from QuestionnaireEvaluationDAO.
	 *  
	 * @param claimID
	 * <code>String</code> claimID
	 * @param suffixID
	 * <code>String</code> suffixID
	 * @param evaluationType
	 *  <code>String</code> evaluationType
	 * @param evaluationDetailsXmlData
	 * <code>String</code> evaluationDetailsXmlData
	 * @param userInfoDoc
	 * <code>UserInfoDocument</code> userInfoDoc
	 * @param workItemID
	 * <code>UserInfoDocument</code> workItemID
	 *      
	 * 
	 * @return responseMap
	 * 
	 * @throws MitchellException
	 *   in case unable to save details
	 */

	 Map saveEvaluation(long claimID, long suffixID,
			String evaluationType, String evaluationDetailsXmlData,
			UserInfoDocument userInfoDoc, String workItemID)
			throws MitchellException;

	/**
	 * This method updates evaluation document from QuestionnaireEvaluationDAO.
	 *  
	 * @param documentId
	 * <code>String</code> documentId
	 * @param evaluationType
	 * <code>String</code> evaluationType
	 * @param evaluationDetailsXmlData
	 * <code>String</code> evaluationDetailsXmlData
	 * @param userInfoDoc
	 * <code>UserInfoDocument</code> userInfoDoc
	 * @param claimId
	 * <code>String</code> claimId
	 * @param suffixId
	 * <code>String</code> suffixId
	 * @param workItemID
	 * <code>String</code> workItemID
	 * @param tcn
	 * <code>long</code> tcn
	 *   
	 * 
	 * @return responseMap
	 * 
	 * @throws MitchellException
	 *   in case unable to save details
	 */

	 Map updateEvaluation(long documentId,
			String evaluationType, String evaluationDetailsXmlData,
			UserInfoDocument userInfoDoc, long claimId, long suffixId,
			String workItemID, long tcn) throws MitchellException;

	/**
	 * This method links claim to evaluation from QuestionnaireEvaluationDAO.
	 *  
	 * @param evaluationID
	 * <code>String</code> evaluationID
	 * @param claimId
	 * <code>long</code> claimId
	 * @param exposureId
	 * <code>long</code> exposureId
	 * @param userInfo
	 * <code>UserInfoDocument</code> userInfo
	 * @param claimNumber
	 * <code>String</code> claimNumber
	 * @param workItemID
	 * <code>String</code> workItemID
	 * 
	 * @return linkStatus
	 * <code>int</code> linkStatus
	 * 
	 * @throws MitchellException
	 *   in case unable to link details
	 */
	int linkQuestionnaireEvaluationToClaim(String evaluationID,
			long claimId, long exposureId, String claimNumber,
			UserInfoDocument userInfo, String workItemID)
			throws MitchellException;

	/**
	 * This method deletes the evaluation.
	 * 
	 * @param coCode
	 * <code>String</code>
	 * 
	 * @param evaluationID
	 *   <code>Long</code> evaluationID
	 *   @param workItemID
	 *  <code>String</code> workItemID
	 *  
	 *  @return responseMap
	 * 
	 * @throws MitchellException
	 *   in case unable to delete details
	 */

	 Map deleteEvaluation(UserInfoDocument userInfoDoc,
			String evaluationID, String workItemID) throws MitchellException;

	/**
	 * This method is taking the xml file path and create the document and genrate the xml doc and store save the claim
	 * to saved evaluation document in Map sfrom QuestionnaireEvaluationDAO.
	 * 
	 * @param msg
	 * <code>String</code> path of the file 
	 * @return responseMap
	 * 
	 * @throws MitchellException
	 *   in case unable to save details
	 */
	 Map saveEvaluationAsync(String msg)
			throws MitchellException;

	/**
     * This method is used to Associate Questionnaire to claim
     * @param qstnrClaimLinkDoc
     *          QuestionnaireClaimLinkDocument qstnrClaimLinkDoc
     * @param UserInfoDocument
     *          userInfoDoc
     */
     void  associateQuestionnaireToClaimSuffix(QuestionnaireClaimLinkDocument qstnrClaimLinkDoc,
                                                    UserInfoDocument userInfoDoc)throws MitchellException;
    
	 QuestionnaireEvaluationDAOProxy getEvaluationDAO() ;

	 void setEvaluationDAO(QuestionnaireEvaluationDAOProxy evaluationDAO) ;

	 ErrorLogProxy getErrorLogProxy() ;

	 void setErrorLogProxy(ErrorLogProxy errorLogProxy) ;

	 QuestionnaireEvaluationUtilsProxy getQeUtilsProxy() ;

	 void setQeUtilsProxy(QuestionnaireEvaluationUtilsProxy qeUtilsProxy);

	 SystemConfigurationProxy getSystemConfigProxy();

	 void setSystemConfigProxy(SystemConfigurationProxy systemConfigProxy);
	
	
	 ClaimServiceProxy getClaimServiceProxy();
		
	 void setClaimServiceProxy(ClaimServiceProxy claimServiceProxy);
	 CustomSettingProxy getCustomSettingProxy();

	 void setCustomSettingProxy(CustomSettingProxy customSettingProxy);

	 MSTransfromEngineProxy getmSTransfromEngineProxy() ;
	 void setmSTransfromEngineProxy(
			MSTransfromEngineProxy mSTransfromEngineProxy);
	
	
	 void updateSetQuestionnareReviewDocId(String coCode,long claimId,long suffixId,long questinnaireId,long setId,String reviewType,long questEvalDocId,String userId)
	throws MitchellException;
		
	/**
	   * This API is used to save the evaluation details after each single question answered by the user and in return,
	   * returns the next question that needs to be answered.
	   * @param inputJson : This is a json object that contains QuestionniareDTO along with other context details.
	   * @return String : Response string will contain the next question details that used needs to answer.
	   * @throws MitchellException
	   */
	QuestionnaireRqRsDTO saveOrUpdateQtnnreEvaluation(QuestionnaireRqRsDTO currQustnnrRqRs , QuestionnaireDetails  questionnaireDetails , Question currQuestion , Answer selectedAns )
	throws MitchellException;
	
	MitchellEvaluationDetailsDocument getEvaluationDocFromNAS(long evalDocId) throws MitchellException;
}