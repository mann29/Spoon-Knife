package com.mitchell.services.business.questionnaireevaluation.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jboss.ejb3.annotation.RemoteBinding;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.evaluationdetails.EvaluationDetailsType;
import com.mitchell.schemas.evaluationdetails.MitchellEvaluationDetailsDocument;
import com.mitchell.schemas.evaluationdetails.QuestionType;
import com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkDocument;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.services.business.questionnaireevaluation.dto.Answer;
import com.mitchell.services.business.questionnaireevaluation.dto.AnswerRqRsDto;
import com.mitchell.services.business.questionnaireevaluation.dto.ContextDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.Question;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionRqRsDto;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnairInfo;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireRqRsDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.QustnnrQustnRqRsDto;
import com.mitchell.services.business.questionnaireevaluation.impl.QuestionnaireEvaluationImplProxy;
import com.mitchell.services.business.questionnaireevaluation.impl.QuestionnaireManager;
import com.mitchell.services.business.questionnaireevaluation.util.BeanLocator;
import com.mitchell.services.business.questionnaireevaluation.util.QuestionnaireEvaluationUtils;
import com.mitchell.services.business.questionnaireevaluation.util.QuestionnaireEvaluationUtilsProxy;
import com.mitchell.services.business.questionnaireevaluation.util.SpringReferencedContextContainer;

/**
 * Stateless SessionBean QuestionnaireEvaluationEJB invoked by
 * QuestionnaireEvaluationWS to save or delete the
 * Evaluations passed.
 */

@Stateless(name = "QuestionnaireEvaluationEJB")
@Remote(QuestionnaireEvaluationEJBRemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@RemoteBinding(jndiBinding = "com.mitchell.services.business.questionnaireevaluation.ejb.QuestionnaireEvaluationEJBRemote")
public class QuestionnaireEvaluationEJB implements
    QuestionnaireEvaluationEJBRemote{

  /**
   * class name.
   */
  private static final String CLASS_NAME = QuestionnaireEvaluationEJB.class
      .getName();

  /**
   * logger.
   */
  private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
  private static final String QUSTNNRE_EVALUATION_IMPL  = "questionnaireEvaluationImpl";
  private static final String EXCEPTION_DESC  = "Exception: ";
  private static final String QUSTNNRE_MANAGER_IMPL ="questionnaireManager";
  private SpringReferencedContextContainer springContext = null;
	QuestionnaireManager manager = null;
	QuestionnaireEvaluationImplProxy qustnnrEvalImpl = null;
	
	
  public QuestionnaireManager getManager() {
		return manager;
	}

	public void setManager(QuestionnaireManager manager) {
		this.manager = manager;
	}

	public QuestionnaireEvaluationImplProxy getQustnnrEvalImpl() {
		return qustnnrEvalImpl;
	}

	public void setQustnnrEvalImpl(QuestionnaireEvaluationImplProxy qustnnrEvalImpl) {
		this.qustnnrEvalImpl = qustnnrEvalImpl;
	}

/**
   * Add a reference to the Spring Context for this EJB at time of EJB
   * construction.
 * @throws MitchellException 
   */
  @PostConstruct
  public void initializeResources() throws MitchellException  {
    this.springContext = (SpringReferencedContextContainer) new BeanLocator();
    this.springContext.addContextReference();
    try {
		manager = (QuestionnaireManager) BeanLocator
		.getBean(QUSTNNRE_MANAGER_IMPL);
			qustnnrEvalImpl = (QuestionnaireEvaluationImplProxy) BeanLocator
			.getBean(QUSTNNRE_EVALUATION_IMPL);
		 } catch (IllegalAccessException e) {
		throw new MitchellException(QuestionnaireEvaluationConstants.ILLEGAL_ACCESS_EXP,CLASS_NAME,"initializeResources",QuestionnaireEvaluationConstants.INVALID_COMPANY_CODE_MSG,e); 
	}
  }

  /**
   * Remove the reference from the Spring Context for this EJB at time of EJB
   * destruction.
   */
  @PreDestroy
  public void releaseResources()  {
    if (this.springContext != null) {
      this.springContext.removeContextReference();
    }
  }

  /**
   * 
   * This method returns Map after saving evaluation document
   * having the claim id, evaluation id, document if of the
   * saved evaluation through Impl class.
   * 
   * @param evaluationID
   *          <code>String</code> evaluationID
   * @param evaluationType
   *          <code>String</code> evaluationType
   * @param evaluationDetailsXmlData
   *          <code>String</code> evaluationDetails
   * @param workItemId
   *          <code>String</code> workItemId
   * @param mSuffixSvcRqRsData
   *          <code>String</code> mitchellSuffixSvcRqRsData
   * @param userInfoDoc
   *          <code>UserInfoDocument</code> evaluationDetails
   * @return responseMap
   * 
   * @throws MitchellException
   *           in case unable to save details
   * 
   */

  public Map<Object, Object> saveEvaluationAndLinkQEToClaim(
      String evaluationID, String clientClaimNumber, String evaluationType,
      String evaluationDetailsXmlData, String workItemId,
      String mSuffixSvcRqRsData, UserInfoDocument userInfoDoc)
      throws MitchellException  {
final String methodName = "saveEvaluationAndLinkQEToClaim";
    LOGGER.entering(CLASS_NAME, methodName);
    Map<Object, Object> responseMap = null;
    try {

      QuestionnaireEvaluationImplProxy impl = (QuestionnaireEvaluationImplProxy) BeanLocator
          .getBean(QUSTNNRE_EVALUATION_IMPL);
      responseMap = impl.saveEvaluationAndLinkQEToClaim(clientClaimNumber,
          evaluationID, evaluationType, mSuffixSvcRqRsData,
          evaluationDetailsXmlData, userInfoDoc, workItemId);

    }catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SAVING_DETAILS, CLASS_NAME,
          methodName, evaluationID, EXCEPTION_DESC
              + e.getMessage(), e);
     
    }
    LOGGER.exiting(CLASS_NAME, methodName);
    return responseMap;

  }

  /**
   * 
   * This method returns Map after saving evaluation document
   * having the claim id, evaluation id, document if of the
   * saved evaluation through Impl class.
   * 
   * @param evaluationID
   *          <code>String</code> evaluationID
   * @param evaluationType
   *          <code>String</code> evaluationType
   * @param evaluationDetailsXmlData
   *          <code>String</code> evaluationDetails
   * @param userInfoDoc
   *          <code>UserInfoDocument</code> evaluationDetails
   * 
   * 
   * @return responseMap
   * 
   * @throws MitchellException
   *           in case unable to save details
   * 
   */

  public Map saveEvaluation(String evaluationID, String evaluationType,
      String evaluationDetailsXmlData, UserInfoDocument userInfoDoc)
      throws MitchellException  {

    final String methodName = "saveEvaluation";
    LOGGER.entering(CLASS_NAME, methodName);

    long startTime = 0;
    startTime = System.currentTimeMillis();
    long endTime = 0;
    String workItemId = null;

    workItemId = evaluationID;
    Map responseMap = null;
    
    try {

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        LOGGER
            .info("QuestionnireEvaluationEJB.ejb - saveEvaluation(String evaluationID :"
                + evaluationID + " ...)- start Time :: " + startTime);
      }

      QuestionnaireEvaluationImplProxy impl = (QuestionnaireEvaluationImplProxy) BeanLocator
          .getBean(QUSTNNRE_EVALUATION_IMPL);
      responseMap = impl.saveEvaluation(evaluationID, evaluationType,
          evaluationDetailsXmlData, userInfoDoc, workItemId);

    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
     throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SAVING_DETAILS, CLASS_NAME,
          methodName, workItemId, EXCEPTION_DESC + e.getMessage(), e);
    }

    LOGGER.entering(CLASS_NAME, methodName);

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      endTime = System.currentTimeMillis();
      LOGGER
          .info("QuestionnireEvaluationEJB.ejb -saveEvaluation(String evaluationID : "
              + evaluationID + "...)- End Time :: " + endTime);
      LOGGER
          .info("QuestionnireEvaluationEJB.ejb- saveEvaluation(String evaluationID : "
              + evaluationID
              + " ...)- Time taken for saveEvaluation(String evaluationID...) :: "
              + (endTime - startTime));
    }

    return responseMap;
  }

  /**
   * 
   * This method updates evaluation document
   * through Impl class.
   * 
   * @param documentId
   *          <code>String</code> documentId
   * @param evaluationType
   *          <code>String</code> evaluationType
   * @param evaluationDetailsXmlData
   *          <code>String</code> evaluationDetailsXmlData
   * @param userInfoDoc
   *          <code>UserInfoDocument</code> userInfoDoc
   * @param claimId
   *          <code>String</code> claimId
   * @param suffixId
   *          <code>String</code> suffixId
   * @param tcn
   *          <code>long</code>tcn
   * @return responseMap
   * 
   * @throws MitchellException
   *           in case unable to save details
   * 
   */

  public Map updateEvaluation(long documentId, String evaluationType,
      String evaluationDetailsXmlData, UserInfoDocument userInfoDoc,
      long claimId, long suffixId, long tcn)
      throws MitchellException  {

    final String methodName = "updateEvaluation";

    Map responseMap = null;

    long startTime = 0;
    long endTime = 0;
    startTime = System.currentTimeMillis();
    String workItemId = null;

    try {
      QuestionnaireEvaluationImplProxy impl = (QuestionnaireEvaluationImplProxy) BeanLocator
          .getBean(QUSTNNRE_EVALUATION_IMPL);

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        LOGGER
            .info("QuestionnireEvaluationEJB.ejb -updateEvaluation(String documentId : "
                + documentId + " ...)- start Time:: " + startTime);
      }

      //Change made- instead of document Id set the evaluation Id or the claim Num as workItem Id
      // If both are available then Claim Num should be preffered.
      MitchellEvaluationDetailsDocument evaluationDocument = MitchellEvaluationDetailsDocument.Factory
          .parse(evaluationDetailsXmlData);
      //Change made- evaluation Id goes as work item id if present else Claim Number
      String evaluationId = evaluationDocument.getMitchellEvaluationDetails()
          .getEvaluationInfo().getEvaluationID();
      if (evaluationId != null && evaluationId.trim().length() > 0) {
        workItemId = evaluationId;
      } else {
        workItemId = evaluationDocument.getMitchellEvaluationDetails()
            .getEvaluationInfo().getClaimNumber();
      }

      responseMap = impl.updateEvaluation(documentId, evaluationType,
          evaluationDetailsXmlData, userInfoDoc, claimId, suffixId, workItemId,
          tcn);
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
     throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_UPDATING_DETAILS, CLASS_NAME,
          methodName, workItemId, EXCEPTION_DESC + e.getMessage(), e);
    }

    LOGGER.exiting(CLASS_NAME, methodName);

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      endTime = System.currentTimeMillis();
      LOGGER
          .info("QuestionnireEvaluationEJB.ejb- updateEvaluation(String documentId : "
              + documentId + " ..)- End Time :: " + endTime);
      LOGGER
          .info("QuestionnireEvaluationEJB.ejb - updateEvaluation(String documentId :"
              + documentId
              + " ...)- Time taken for updateEvaluation(String documentId...) :: "
              + (endTime - startTime));
    }

    return responseMap;
  }

  /**
   * This method returns documentID and context of saved evaluation
   * document with claim through Impl class.
   * 
   * @param claimID
   *          <code>String</code> claimID
   * @param suffixID
   *          <code>String</code> suffixID
   * @param evaluationType
   *          <code>String</code> evaluationType
   * @param evaluationDetailsXmlData
   *          <code>String</code> evaluationDetailsXmlData
   * @param userInfoDoc
   *          <code>UserInfoDocument</code> userInfoDoc
   * 
   * @return responseMap
   * 
   * @throws MitchellException
   *           in case unable to save details
   * 
   */

  public Map saveEvaluation(long claimID, long suffixID, String evaluationType,
      String evaluationDetailsXmlData, UserInfoDocument userInfoDoc)
      throws MitchellException  {

    final String methodName = "saveEvaluation";
    LOGGER.entering(CLASS_NAME, methodName);
    String workItemId = null;
    workItemId = String.valueOf(claimID)
        + QuestionnaireEvaluationConstants.HYPHEN + String.valueOf(suffixID);

    Map responseMap = null;

    long startTime = 0;
    long endTime = 0;
    startTime = System.currentTimeMillis();

    try {

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        LOGGER
            .info("QuestionnireEvaluationEJB.ejb -saveEvaluation(long claimID : "
                + claimID + " ...)- start Time ::" + startTime);
      }

      QuestionnaireEvaluationImplProxy impl = (QuestionnaireEvaluationImplProxy) BeanLocator
          .getBean(QUSTNNRE_EVALUATION_IMPL);
      responseMap = impl.saveEvaluation(claimID, suffixID, evaluationType,
          evaluationDetailsXmlData, userInfoDoc, workItemId);
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SAVING_DETAILS, CLASS_NAME,
          methodName, workItemId, EXCEPTION_DESC + e.getMessage(), e);
    }

    LOGGER.entering(CLASS_NAME, methodName);

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      endTime = System.currentTimeMillis();
      LOGGER
          .info("QuestionnireEvaluationEJB.ejb- saveEvaluation(long claimID : "
              + claimID + "..)- End Time :: " + endTime);
      LOGGER
          .info("QuestionnireEvaluationEJB.ejb - saveEvaluation(long claimID: "
              + claimID
              + " ...)- Time taken for saveEvaluation(long claimID...) :: "
              + (endTime - startTime));
    }

    return responseMap;
  }

  /**
   * This method deletes the evaluation through Impl class.
   * 
   * @param coCode
   *          <code>UserInfoDocument</code> userInfoDoc
   * @param evaluationID
   *          <code>String</code> evaluationID
   * 
   * @return responseMap
   * 
   * @throws MitchellException
   *           in case unable to delete details
   * 
   */
  public Map deleteEvaluation(UserInfoDocument userInfoDoc, String evaluationID)
      throws MitchellException  {

    final String methodName = "deleteEvaluation";
    LOGGER.entering(CLASS_NAME, methodName);

    String workItemId = null;
    workItemId = evaluationID;
    Map responseMap = null;

    try {

      QuestionnaireEvaluationImplProxy impl = (QuestionnaireEvaluationImplProxy) BeanLocator
          .getBean(QUSTNNRE_EVALUATION_IMPL);
      responseMap = impl
          .deleteEvaluation(userInfoDoc, evaluationID, workItemId);
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_DELETING_DETAILS, CLASS_NAME,
          methodName, workItemId, EXCEPTION_DESC + e.getMessage(), e);
    }

    LOGGER.exiting(CLASS_NAME, methodName);

    return responseMap;
  }

  /**
   * This method links claim to existing evaluation.
   * 
   * @param evaluationID
   *          <code>String</code> evaluationID
   * @param claimId
   *          <code>long</code> claimId
   * @param exposureId
   *          <code>long</code> exposureId
   * @param claimNumber
   *          <code>String</code> claimNumber
   * @param userInfo
   *          <code>UserInfoDocument</code> userInfo
   * 
   * @return linkStatus <code>int</code> linkStatus
   * 
   * @throws MitchellException
   *           in case unable to link claim to Evaluation
   * 
   */
  public int linkQuestionnaireEvaluationToClaim(String evaluationID,
      long claimId, long exposureId, String claimNumber,
      UserInfoDocument userInfo)
      throws MitchellException  {
    final String methodName = "linkQuestionnaireEvaluationToClaim";
    LOGGER.entering(CLASS_NAME, methodName);

    int linkStatus;
    String workItemId = null;
    workItemId = evaluationID;

    long startTime = 0;
    long endTime = 0;
    startTime = System.currentTimeMillis();

    try {

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        LOGGER
            .info("QuestionnireEvaluationEJB.ejb -linkQuestionnaireEvaluationToClaim(String evaluationID : "
                + evaluationID + " ...)- start Time::" + startTime);
      }

      QuestionnaireEvaluationImplProxy impl = (QuestionnaireEvaluationImplProxy) BeanLocator
          .getBean(QUSTNNRE_EVALUATION_IMPL);
      linkStatus = impl.linkQuestionnaireEvaluationToClaim(evaluationID,
          claimId, exposureId, claimNumber, userInfo, workItemId);
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
     throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_LINKING_CLAIM, CLASS_NAME,
          methodName, workItemId, EXCEPTION_DESC + e.getMessage(), e);
    }
    LOGGER.exiting(CLASS_NAME, methodName);
    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      endTime = System.currentTimeMillis();
      LOGGER
          .info("QuestionnireEvaluationEJB.ejb- linkQuestionnaireEvaluationToClaim(String evaluationID : "
              + evaluationID + ".)- End Time :: " + endTime);
      LOGGER
          .info("QuestionnireEvaluationEJB.ejb - linkQuestionnaireEvaluationToClaim(String evaluationID: "
              + evaluationID
              + " ...)- Time taken for saveEvaluation(String evaluationID...) :: "
              + (endTime - startTime));
    }
    return linkStatus;
  }

  /**
   * This method is used to Associate Questionnaire to claim
   * 
   * @param qstnrClaimLinkDoc
   *          QuestionnaireClaimLinkDocument qstnrClaimLinkDoc
   * @param UserInfoDocument
   *          userInfoDoc
   * @ejbgen:local-method transaction-attribute="Required"
   * @ejbgen:remote-method transaction-attribute="Required"
   */
  public void associateQuestionnaireToClaimSuffix(
      QuestionnaireClaimLinkDocument qstnrClaimLinkDoc,
      UserInfoDocument userInfo)
      throws MitchellException  {

    final String methodName = "associateQuestionnaireToClaimSuffix";
    LOGGER.entering(CLASS_NAME, methodName);
    String workItemId = null;

    try {
      QuestionnaireEvaluationImplProxy impl = (QuestionnaireEvaluationImplProxy) BeanLocator
          .getBean(QUSTNNRE_EVALUATION_IMPL);

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        LOGGER.info("EJB-userInfoDoc :" + userInfo);
        LOGGER.info("EJB-qstnrClaimLinkDoc :" + qstnrClaimLinkDoc);
      }

      impl.associateQuestionnaireToClaimSuffix(qstnrClaimLinkDoc, userInfo);
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_ASSOCIATING_QTNR_TO_CLAIMSUFFIX,
          CLASS_NAME, methodName, workItemId, EXCEPTION_DESC + e.getMessage(), e);
    }

    LOGGER.exiting(CLASS_NAME, methodName);
  }

  public void updateSetQuestionnaireReviewDocId(String coCode, long claimId,
      long suffixId, long questinnaireId, long setId, String reviewType,
      long questEvalDocId, String userId)
      throws MitchellException  {
    final String methodName = "updateSetQuestionnaireReviewDocId";
    LOGGER.entering(CLASS_NAME, methodName);
    String workItemId = null;

    try {

      QuestionnaireEvaluationImplProxy impl = (QuestionnaireEvaluationImplProxy) BeanLocator
          .getBean(QUSTNNRE_EVALUATION_IMPL);

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        LOGGER.info("updateSetQuestionnaireReviewDocId coCode:" + coCode
            + "claimId=" + claimId + "suffixId=" + suffixId + "setId=" + setId
            + "reviewType=" + questEvalDocId);
      }

      impl.updateSetQuestionnareReviewDocId(coCode, claimId, suffixId,
          questinnaireId, setId, reviewType, questEvalDocId, userId);

    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_ASSOCIATING_QTNR_TO_CLAIMSUFFIX,
          CLASS_NAME, methodName, workItemId, EXCEPTION_DESC + e.getMessage(), e);
    }

    LOGGER.exiting(CLASS_NAME, methodName);
  }

  
  /**
   * This API is used to save the evaluation details after each single question answered by the user and in return,
   * returns the next question that needs to be answered.
   * @param inputJson : This is a json object that contains QuestionniareDTO along with other context details.
   * @return String : Response string will contain the next question details that used needs to answer.
   * @throws MitchellException
   */
public String saveUpdateEvalAndGetNxtQustn(String inputJson)
		throws MitchellException {
	
	final String methodName = "saveUpdateEvalAndGetNxtQustn";
	LOGGER.entering(CLASS_NAME, methodName);
	String respnseJson=null; 
	try {
		QuestionnaireEvaluationUtilsProxy qeUtilsProxy = new QuestionnaireEvaluationUtils();
		QuestionnaireRqRsDTO currQustnnrRqRs = qeUtilsProxy.jsonToDto(inputJson);
		ContextDTO contextDTO = currQustnnrRqRs.getContextDto();
		QustnnrQustnRqRsDto qustnnrQustnRqRsDto = currQustnnrRqRs.getQustnnrQustnRqRsDto();
		if(qustnnrQustnRqRsDto != null){
			QuestionnairInfo questionnairInfo  = manager.getQuestionnairInfo(contextDTO);
			Question currDbQustn= manager.getCurrQustnQustnnr(questionnairInfo.getQuestionnaireTree() , qustnnrQustnRqRsDto.getQuestionsList().get(0));
			List<AnswerRqRsDto> answerList = qustnnrQustnRqRsDto.getQuestionsList().get(0).getAnswerList();
			Answer selectedAns = null;
			if(!answerList.isEmpty()){
				AnswerRqRsDto	answer = answerList.get(0);
				selectedAns = manager.getSelectedAnswer(currDbQustn ,answer);
			}			
			currQustnnrRqRs = qustnnrEvalImpl.saveOrUpdateQtnnreEvaluation(currQustnnrRqRs , questionnairInfo.getQuestionnaireDetails() , currDbQustn , selectedAns);
			if(LOGGER.isLoggable(Level.INFO)){
				LOGGER.info("currQustnnrRqRs    "+currQustnnrRqRs);
			}
		}
			respnseJson =manager.getNextQustn(currQustnnrRqRs);
			
	} catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SAVING_QNTNRE_EVAL,
          CLASS_NAME, methodName, EXCEPTION_DESC + e.getMessage(), e);
	}
    LOGGER.exiting(CLASS_NAME, methodName);
	return respnseJson;
}


/**
 * This API is used to get the next question of the partially saved questionnaire
 * @param String companyCd , String documentId - of the saved evaluation
 * @return String : Response string will contain the next question details that used needs to answer.
 * @throws MitchellException
 */
public String getNextPartialSavedQuestion(String companyCd , String documentId) throws MitchellException {
	String methodName = "getNextPartialSavedQuestion";
	LOGGER.entering(CLASS_NAME, methodName);
	String firstQustnRes = null;
	long evalDocId = 0L;
	try {
		
		QuestionnaireRqRsDTO qustnnrRqRsDTO = new QuestionnaireRqRsDTO();
		ContextDTO contextDTO = new ContextDTO();
		contextDTO.setCoCode(companyCd);
		evalDocId = Long.parseLong(documentId.trim());
		contextDTO.setDocumentId(evalDocId);
		MitchellEvaluationDetailsDocument	evaluationDetailsDocData = qustnnrEvalImpl.getEvaluationDocFromNAS(evalDocId);
		if(LOGGER.isLoggable(Level.INFO)){
			LOGGER.info("evaluationDetailsDocData    "+evaluationDetailsDocData);
		}
		if(evaluationDetailsDocData == null || evaluationDetailsDocData.getMitchellEvaluationDetails() == null || !evaluationDetailsDocData.validate()){
			throw new MitchellException(QuestionnaireEvaluationConstants.INVALID_EVALUATION_DOC,
			          CLASS_NAME, "validateSavedEvalDoc", QuestionnaireEvaluationConstants.INVALID_EVAL_DOC_MSG );
		}
		EvaluationDetailsType  evaluationDetailsType  = evaluationDetailsDocData.getMitchellEvaluationDetails() ;
		int sizeOfQustnnrArr = evaluationDetailsType.sizeOfQuestionnaireArray();
		if ((sizeOfQustnnrArr == 0)
					|| (evaluationDetailsType.getQuestionnaireArray(sizeOfQustnnrArr - 1).getQuestionsList() == null)){
			qustnnrRqRsDTO.setContextDto(contextDTO);
			firstQustnRes = manager.getNextQustn(qustnnrRqRsDTO);
		}else{
			String savedQustnnrId = evaluationDetailsDocData.getMitchellEvaluationDetails().getQuestionnaireArray(0).getQuestionnaireId();
			contextDTO.setQuestionnaireID(Long.parseLong(savedQustnnrId));
			QuestionType[] questionTypeArr =evaluationDetailsType.getQuestionnaireArray(sizeOfQustnnrArr - 1).getQuestionsList().getQuestionArray();
			QuestionType questionType = questionTypeArr[questionTypeArr.length - 1];
			QuestionType lastAnswrdQustnOnNAS = getLastAnswrdQustn(questionType) ;
			long qustnnreId = Long.parseLong(evaluationDetailsType.getQuestionnaireArray(sizeOfQustnnrArr - 1).getQuestionnaireId());
			int qustnnrVersion = evaluationDetailsType.getQuestionnaireArray(sizeOfQustnnrArr - 1).getQuestionnaireVersion().intValue();
			qustnnrRqRsDTO = populateReqDto(lastAnswrdQustnOnNAS , contextDTO,qustnnreId,  qustnnrVersion);
			if(LOGGER.isLoggable(Level.INFO)){
				LOGGER.info("qustnnrRqRsDTO    "+qustnnrRqRsDTO);
			}
			firstQustnRes = manager.getNextQustn(qustnnrRqRsDTO);
		}
	} catch (NumberFormatException e) {
		throw new MitchellException(QuestionnaireEvaluationConstants.NUMBER_FORMATE_EXP,
		          CLASS_NAME, methodName,QuestionnaireEvaluationConstants.NUMBER_FORMATE_EXCEPTION, e);
	}catch (MitchellException e) {
		throw e;
	}catch (Exception e) {
	      throw new MitchellException(
	              QuestionnaireEvaluationConstants.ERROR_GETTING_NEXT_SAVED_QUSTN,
	              CLASS_NAME, methodName, QuestionnaireEvaluationConstants.ERROR_GETTING_NEXT_SAVED_QUSTN_MSG, e);
	    	}
	 LOGGER.exiting(CLASS_NAME, methodName);
	return firstQustnRes;
}


 
/**
 * This API is used to get the first question of the FNOL questionnaire
 * @param String companyCd 
 * @return String : Response string will contain the next question details that used needs to answer.
 * @throws MitchellException
 */

public String getFirstQuestion(String companyCd) throws MitchellException {
	String methodName="getFirstQuestion";
	LOGGER.entering(CLASS_NAME, methodName);
	String firstQustnRes = null;
	ContextDTO contextDTO = new ContextDTO();
	QuestionnaireRqRsDTO qustnnrRqRsDTO = new QuestionnaireRqRsDTO();
	try {
		contextDTO.setCoCode(companyCd);
		qustnnrRqRsDTO.setContextDto(contextDTO);
		firstQustnRes = manager.getNextQustn(qustnnrRqRsDTO);
		
	} catch (MitchellException e) {
		throw e;
	}
	 LOGGER.exiting(CLASS_NAME, methodName);
	return firstQustnRes;
}

/**
 * This method is used to populate the request Dto to get the next question of the partially saved questionnaire
 * @param  QuestionType : Last saved question , ContextDTO : contains details of the companyCd,
 *  long qustnnreId : Questionnaire id of the saved evaluation,int : Questionnaire version of the saved questionnaire 
 * @return QuestionnaireRqRsDTO : Response string will contain the last saved question details .
 * @throws MitchellException
 */
private QuestionnaireRqRsDTO populateReqDto (QuestionType questionType ,ContextDTO contextDTO , long qustnnreId , int qustnnrVersion ) throws MitchellException{
	String methodName ="populateReqDto";
	LOGGER.entering(CLASS_NAME, methodName);
	QuestionnaireRqRsDTO qustnnrRqRsDTO = new QuestionnaireRqRsDTO();
	QustnnrQustnRqRsDto qustnnrQustnRqRsDto =new QustnnrQustnRqRsDto();
	QuestionRqRsDto questionRqRsDto = new QuestionRqRsDto ();
	AnswerRqRsDto answerRqRsDto = new AnswerRqRsDto();
	List<AnswerRqRsDto> answerList =  new ArrayList<AnswerRqRsDto>();
	List<QuestionRqRsDto> questionList =  new ArrayList<QuestionRqRsDto>();
	
	questionRqRsDto.setQustnText(questionType.getText());
	questionRqRsDto.setQustnnreQustnId(questionType.getQuestionScoreID().longValue());
	Question currDbQustn = null;
	QuestionnairInfo questionnairInfo  = manager.getQuestionnairInfo(contextDTO);
	if(questionnairInfo != null){
		 currDbQustn = manager.getCurrQustnQustnnr(questionnairInfo.getQuestionnaireTree() ,questionRqRsDto);
		
	}
	
	if(currDbQustn != null && QuestionnaireEvaluationConstants.ANSWR_CONTL_TYPE.equalsIgnoreCase(currDbQustn.getAnswrControlType())){
		String rangeValue = questionType.getAnswer();
		if(rangeValue !=null){
			String[] range =rangeValue.split("-"); 
			answerRqRsDto.setLowRangeValue(Integer.parseInt(range[0].trim()));
			answerRqRsDto.setHighRangeValue(Integer.parseInt(range[1].trim()));
			answerRqRsDto.setAnswerDisplayText(null);
		}
		
	}else{
		answerRqRsDto.setAnswerDisplayText(questionType.getAnswer());
	}
	answerRqRsDto.setQustnnreQustnId(questionType.getQuestionScoreID().longValue());
	answerList.add(answerRqRsDto);
	questionRqRsDto.setAnswerList(answerList);
	questionList.add(questionRqRsDto);
	
	qustnnrQustnRqRsDto.setQuestionsList(questionList);
	qustnnrQustnRqRsDto.setQustnnreId(qustnnreId);
	qustnnrQustnRqRsDto.setQustnnreVersion(qustnnrVersion);
	
	qustnnrRqRsDTO.setContextDto(contextDTO);
	qustnnrRqRsDTO.setQustnnrQustnRqRsDto(qustnnrQustnRqRsDto);
	LOGGER.exiting(CLASS_NAME, methodName);
	return qustnnrRqRsDTO;
}
/**
 * This method is used to get the last answered question in saved evaluation
 * @param  QuestionType :  saved question 
 * @return QuestionType : last saved question
 */
private QuestionType getLastAnswrdQustn(QuestionType questionType) {
	String methodName= "getLastAnswrdQustn";
	LOGGER.entering(CLASS_NAME, methodName);
	int sizeOfRelQustn = questionType.sizeOfRelatedQuestionsArray();
	QuestionType  lastQustn=null;
	if(sizeOfRelQustn ==0){
		lastQustn =questionType;
		return lastQustn;
	}else{
		lastQustn = questionType.getRelatedQuestionsArray(sizeOfRelQustn - 1);
		return getLastAnswrdQustn(lastQustn);
	}
	}
	

}