package com.mitchell.services.business.questionnaireevaluation.dao;

import java.io.IOException;
import java.util.Map;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.evaluationdetails.MitchellEvaluationDetailsDocument;
import com.mitchell.schemas.evaluationdetails.ResultType;
import com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkDocument;
import com.mitchell.services.business.questionnaireevaluation.QuestionnaireEvaluationContext;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireDTO;
import com.mitchell.services.business.questionnaireevaluation.proxy.ClaimServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.DocStoreServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.ErrorLogProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.EstimatePackageServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.SystemConfigurationProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.TransactionalFileServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.util.QuestionnaireEvaluationUtilsProxy;

public interface QuestionnaireEvaluationDAOProxy {

	/**
	 * This method calls the estimate package service to save evaluation.
	 * 
	 * @param evalContext
	 * <code>QuestionnaireEvaluationContext</code> evalContext
	 * @param evaluationDetails
	 * <code>MitchellEvaluationDetailsDocument</code> evaluationDetails
	 * 
	 * @return documentId
	 * <code>String</code> documentId
	 * 
	 * @throws MitchellException
	 *   in case version is invalid
	 */
	Long saveEvaluation(
			QuestionnaireEvaluationContext evalContext,
			MitchellEvaluationDetailsDocument evaluationDetails)
			throws MitchellException;

	/**
	 * This method calls the estimate package service and 
	 * also the claim service if required.
	 * 
	 * @param evalContext
	 * <code>QuestionnaireEvaluationContext</code> evalContext
	 * @param evaluationDetails
	 * <code>MitchellEvaluationDetailsDocument</code> evaluationDetails
	 * 
	 * @return documentId
	 * <code>String</code> documentId
	 * 
	 * @throws MitchellException
	 *   in case version is invalid
	 */
	Long saveEvaluationWithClaim(
			QuestionnaireEvaluationContext evalContext,
			MitchellEvaluationDetailsDocument evaluationDetails)
			throws MitchellException;

	/**
	 * This method call estimate package service for Delete evaluation. 
	 * @param coCode
	 * <code>String</code> coCode
	 * @param externalRefCode
	 * <code>String</code> externalRefCode
	 * @param workItemID
	 * <code>String</code> workItemID
	 * 
	 * @throws MitchellException
	 * in case any errro while deleting
	 */
	void deleteEvaluation(String coCode,
			String externalRefCode, String workItemID) throws MitchellException;

	/**
	 * This methos calls estimate package service for Update evaluation.
	 * @param evalContext
	 * <code>QuestionnaireEvaluationContext</code> evalContext
	 * @param evaluationDetails
	 * <code>MitchellEvaluationDetailsDocument</code> evaluationDetails
	 * 
	 * @throws MitchellException
	 * in case any exception while updating
	 */
	void updateEvaluation(
			QuestionnaireEvaluationContext evalContext,
			MitchellEvaluationDetailsDocument evaluationDetails)
			throws MitchellException;

	/**
	 * This methos calls estimate package service for linking claim.
	 * @param evalContext
	 * 
	 * <code>QuestionnaireEvaluationContext</code> evalContext
	 * @return linkStatus
	 * <code>int</code>linkStatus
	 * <code>success</code>0
	 * <code>Not exist</code>1
	 * <code>Already linked</code>2
	 * 
	 * @throws MitchellException
	 * in caes any exception while linking claim
	 */

	int linkQuestionnaireEvaluationToClaim(
			QuestionnaireEvaluationContext evalContext)
			throws MitchellException;

	/**
	 * For getting evaluation Document from source location.
	 * 
	 * @param srcFileName
	 * <code>String</code> srcFileName
	 * 
	 * @return evaluationDetailsXmlNASData
	 * 
	 * @throws IOException
	 * in case any exception with the streams 
	 */
	String getEvaluationDocumentOnNAS(String srcFileName)
			throws IOException;

	/**
     * This method is used to Associate Questionnaire to claim
     * @param qstnrClaimLinkDoc
     *          QuestionnaireClaimLinkDocument qstnrClaimLinkDoc
     * @param UserInfoDocument
     *          userInfoDoc
     */
     void  associateQuestionnaireToClaimSuffix(QuestionnaireClaimLinkDocument qstnrClaimLinkDoc,
                                                    UserInfoDocument userInfoDoc)throws MitchellException;
    
    
	 ErrorLogProxy getErrorLogProxy() ;

	 void setErrorLogProxy(ErrorLogProxy errorLogProxy);

	 ClaimServiceProxy getClaimServiceProxy();

	 void setClaimServiceProxy(ClaimServiceProxy claimServiceProxy) ;

	 EstimatePackageServiceProxy getEstimatePackageServiceProxy();

	 void setEstimatePackageServiceProxy(
			EstimatePackageServiceProxy estimatePackageServiceProxy) ;

	 QuestionnaireEvaluationUtilsProxy getQeUtilsProxy() ;

	 void setQeUtilsProxy(QuestionnaireEvaluationUtilsProxy qeUtilsProxy) ;

	 DocStoreServiceProxy getDocStoreServiceProxy() ;

	 void setDocStoreServiceProxy(DocStoreServiceProxy docStoreServiceProxy);

	 TransactionalFileServiceProxy getTransactionalFileServiceProxy() ;

	 void setTransactionalFileServiceProxy(
			TransactionalFileServiceProxy transactionalFileServiceProxy) ;

	 SystemConfigurationProxy getSystemConfigProxy();

	 void setSystemConfigProxy(SystemConfigurationProxy systemConfigProxy);
	
	/**
	 * This method was created to update the custom score and comments fields for the Questionnaire Evaluation.
	 * @param miEvalResMap
	 * @param evaluationDocument
	 * @throws MitchellException
	 */
	 void updateCustomEvaluationFields(Map miEvalResMap,String companyCode,ResultType resultType)throws MitchellException;
	
	/**
	 * 
	 * @param coCode
	 * @param claimId
	 * @param suffixId
	 * @param questinnaireId
	 * @param setId
	 * @param reviewType
	 * @param questEvalDocId
	 * @throws MitchellException
	 */
	 void updateSetQuestionnareReviewDocId(String coCode,long claimId,long suffixId,long questinnaireId,long setId,String reviewType,long questEvalDocId,String userId)
	throws MitchellException;

	/**
	  * This method is used to get the original Questionnaire from the database which is configured for the carrier.
	  * @param currntQustnnreId
	  * @param coCd
	  * @return QuestionnaireEvaluationDBDTO
	  * @throws MitchellException
	  */
	QuestionnaireDTO getQuestionnaire(long questionnaireID,	String companyCode) throws MitchellException;
}