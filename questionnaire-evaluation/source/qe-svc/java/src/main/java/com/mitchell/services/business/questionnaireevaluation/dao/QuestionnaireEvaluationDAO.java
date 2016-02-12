package com.mitchell.services.business.questionnaireevaluation.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleTypes;
import com.mitchell.common.dao.CommonBaseDAO;
import com.mitchell.common.dao.MICommonDAOException;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.evaluationdetails.EvaluationInfoType;
import com.mitchell.schemas.evaluationdetails.MitchellEvaluationDetailsDocument;
import com.mitchell.schemas.evaluationdetails.QuestionnaireType;
import com.mitchell.schemas.evaluationdetails.ResultType;
import com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkDocument;
import com.mitchell.services.business.questionnaireevaluation.QuestionnaireEvaluationContext;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.services.business.questionnaireevaluation.dto.Answer;
import com.mitchell.services.business.questionnaireevaluation.dto.Question;
import com.mitchell.services.business.questionnaireevaluation.dto.Questionnaire;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireDTO;
import com.mitchell.services.business.questionnaireevaluation.proxy.ClaimServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.DocStoreServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.ErrorLogProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.EstimatePackageServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.SystemConfigurationProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.TransactionalFileServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.util.MessagingContext;
import com.mitchell.services.business.questionnaireevaluation.util.QuestionnaireEvaluationUtilsProxy;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;

/**
 * Class which interacts with the Estimate Package service to save,
 * update or delete the Evaluations passed.
 * 
 */

public class QuestionnaireEvaluationDAO extends CommonBaseDAO implements
    QuestionnaireEvaluationDAOProxy{

  /**
   * class name..
   */
  private static final String CLASS_NAME = QuestionnaireEvaluationDAO.class
      .getName();
  /**
   * logger..
   */
  private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
  /**
   * Constant Four
   */
  private static final int LINK_STATUS_DOCUMENT_NULL = 1;

  private ErrorLogProxy errorLogProxy = null;
  private ClaimServiceProxy claimServiceProxy = null;
  private EstimatePackageServiceProxy estimatePackageServiceProxy = null;
  private QuestionnaireEvaluationUtilsProxy qeUtilsProxy = null;
  private DocStoreServiceProxy docStoreServiceProxy = null;
  private TransactionalFileServiceProxy transactionalFileServiceProxy = null;
  private SystemConfigurationProxy systemConfigProxy = null;

  /**
   * This method calls the estimate package service to save evaluation.
   * 
   * @param evalContext
   *          <code>QuestionnaireEvaluationContext</code> evalContext
   * @param evaluationDetails
   *          <code>MitchellEvaluationDetailsDocument</code> evaluationDetails
   * 
   * @return documentId <code>String</code> documentId
   * 
   * @throws MitchellException
   *           in case version is invalid
   */
  public Long saveEvaluation(QuestionnaireEvaluationContext evalContext,
      MitchellEvaluationDetailsDocument evaluationDetails)
      throws MitchellException  {

    final String methodName = "saveEvaluation";
    LOGGER.entering(CLASS_NAME, methodName);

    Long documentId = null;
    long startTime = 0;
    long endTime = 0;
    String workItemId = null;
    try {

      UserInfoDocument userInfoDoc = evalContext.getUserInfoDoc();

      workItemId = evalContext.getWorkItemId();

      if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
        qeUtilsProxy.logFINEMessage("calling estimatePackage to save");
      }

      startTime = System.currentTimeMillis();
      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        qeUtilsProxy
            .logINFOMessage("QuestionnaireEvaluationDAO.java - calling EStimatePKG start saveQE(String evaluationID : "
                + evalContext.getEvaluationID()
                + " ...)- start Time ::"
                + startTime);
      }

      documentId = estimatePackageServiceProxy.saveQuestionnaireEvaluation(
          evaluationDetails, userInfoDoc, null, null);

      endTime = System.currentTimeMillis();

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        qeUtilsProxy
            .logINFOMessage("QuestionnireEvaluationDAO.java - calling EStimatePKG end saveQE(String evaluationID : "
                + evalContext.getEvaluationID()
                + " ...)- end Time ::"
                + endTime);
        qeUtilsProxy
            .logINFOMessage("QuestionnireEvaluationDAO.java - calling EStimatePKG total saveQE(String evaluationID : "
                + evalContext.getEvaluationID()
                + " ...)- total Time ::"
                + (endTime - startTime));
        qeUtilsProxy.logINFOMessage("Returning document Id :: " + documentId);
      }

      if (documentId != null) {
        Long docStoreId;

        startTime = System.currentTimeMillis();
        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy
              .logINFOMessage("QuestionnireEvaluationDAO.java - calling EStimatePKG start getDocStoreId() : "
                  + evalContext.getEvaluationID()
                  + " ...)- start Time :"
                  + startTime);
        }

        docStoreId = estimatePackageServiceProxy.getDocumentStoreIdByDocId(
            documentId, null);

        endTime = System.currentTimeMillis();
        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy
              .logINFOMessage("QuestionnireEvaluationDAO.java - calling EStimatePKG end getDocStoreId() : "
                  + evalContext.getEvaluationID()
                  + " ...)- end Time::"
                  + endTime);
          qeUtilsProxy
              .logINFOMessage("QuestionnireEvaluationDAO.java - calling EStimatePKG total getDocStoreId() : "
                  + evalContext.getEvaluationID()
                  + " ...)- total Time::"
                  + (endTime - startTime));
        }

        startTime = System.currentTimeMillis();
        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy
              .logINFOMessage("QuestionnireEvaluationDAO.java- calling copyFileToNAS() : "
                  + evalContext.getEvaluationID()
                  + " ...)- start Time : "
                  + startTime);
        }

        qeUtilsProxy.copyFileToNAS(evaluationDetails.toString(), docStoreId,
            workItemId);

        endTime = System.currentTimeMillis();
        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy
              .logINFOMessage("QuestionnireEvaluationDAO.java -calling copyFileToNAS() : "
                  + evalContext.getEvaluationID()
                  + " ...)-end Time ::"
                  + endTime);
          qeUtilsProxy
              .logINFOMessage("QuestionnireEvaluationDAO.java-calling copyFileToNAS() : "
                  + evalContext.getEvaluationID()
                  + " ...)-total Time::"
                  + (endTime - startTime));
        }

      } else {

        throw new MitchellException(
            QuestionnaireEvaluationConstants.INVALID_DOCUMENT_ID, CLASS_NAME,
            methodName, workItemId,
            QuestionnaireEvaluationConstants.INVALID_DOCUMENT_ID
                + QuestionnaireEvaluationConstants.DOCUMENT_ID_NULL_MESSAGE);

      }
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SAVING_DETAILS, CLASS_NAME,
          methodName, workItemId, e.getMessage(), e);
    }

    LOGGER.exiting(CLASS_NAME, methodName);

    return documentId;

  }

  /**
   * This method calls the estimate package service and
   * also the claim service if required.
   * 
   * @param evalContext
   *          <code>QuestionnaireEvaluationContext</code> evalContext
   * @param evaluationDetails
   *          <code>MitchellEvaluationDetailsDocument</code> evaluationDetails
   * 
   * @return documentId <code>String</code> documentId
   * 
   * @throws MitchellException
   *           in case version is invalid
   */
  public Long saveEvaluationWithClaim(
      QuestionnaireEvaluationContext evalContext,
      MitchellEvaluationDetailsDocument evaluationDetails)
      throws MitchellException  {

    final String methodName = "saveEvaluationWithClaim";
    LOGGER.entering(CLASS_NAME, methodName);
    long startTime = 0;
    long endTime = 0;
    Long documentId = null;
    Long docStoreId;
    String workItemId = null;
    qeUtilsProxy.logINFOMessage("inside saveEvaluationWithClaim of DAO layer ");
    try {
      UserInfoDocument userInfoDoc = evalContext.getUserInfoDoc();
      workItemId = evalContext.getWorkItemId();

      try {
        //if claim id is null then exposureId can also be null
        startTime = System.currentTimeMillis();
        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy
              .logINFOMessage("QuestionEvlDAO-saveEvaluationWithClaim-EstimatePkg- saveQuestionnaireEvaluation - evalContext.getClaimId(): "
                  + evalContext.getClaimId() + " -- start time::" + startTime);
        }

        documentId = estimatePackageServiceProxy.saveQuestionnaireEvaluation(
            evaluationDetails, userInfoDoc,
            Long.valueOf(evalContext.getClaimId()),
            Long.valueOf(evalContext.getSuffixId()));

        endTime = System.currentTimeMillis();
        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy
              .logINFOMessage("QuestionEvlDAO-saveEvaluationWithClaim-EstimatePkg -saveQuestionnaireEvaluation - evalContext.getClaimId(): "
                  + evalContext.getClaimId() + "--end time::  " + endTime);
          qeUtilsProxy
              .logINFOMessage("QuestionEvlDAO-saveEvaluationWithClaim-EstimatePkg - saveQuestionnaireEvaluation - evalContext.getClaimId(): "
                  + evalContext.getClaimId()
                  + " - time taken::"
                  + (endTime - startTime));
        }

        if (documentId != null) {
          startTime = System.currentTimeMillis();
          if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
            qeUtilsProxy
                .logINFOMessage("QuestionEvlDAO-saveEvaluationWithClaim- EstimatePkg-getDocStoreId- evalContext.getClaimId() -  "
                    + evalContext.getClaimId() + " -start time:" + startTime);
          }

          docStoreId = estimatePackageServiceProxy.getDocumentStoreIdByDocId(
              documentId, null);

          endTime = System.currentTimeMillis();
          if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
            qeUtilsProxy
                .logINFOMessage("QuestionEvlDAO-saveEvaluationWithClaim -EstimatePkg-getDocStoreId- evalContext.getClaimId() -  "
                    + evalContext.getClaimId() + " --end time::" + endTime);
            qeUtilsProxy
                .logINFOMessage("QuestionEvlDAO-saveEvaluationWithClaim - EstimatePkg-getDocStoreId- evalContext.getClaimId() -  "
                    + evalContext.getClaimId()
                    + " - time taken::"
                    + (endTime - startTime));
          }

        } else {
          throw new MitchellException(
              QuestionnaireEvaluationConstants.INVALID_DOCUMENT_ID, CLASS_NAME,
              methodName, workItemId,
              QuestionnaireEvaluationConstants.INVALID_DOCUMENT_ID
                  + QuestionnaireEvaluationConstants.DOCUMENT_ID_NULL_MESSAGE);
        }

        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy
              .logFINEMessage("Calling Claim Service with documentID :: "
                  + documentId);
        }

        if (evalContext.getSuffixId() >= 0) {

          try {

            startTime = System.currentTimeMillis();
            if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
              qeUtilsProxy
                  .logINFOMessage("QuestionEvlDAO-saveEvaluationWithClaim -writeExposureActLog- evalContext.getClaimId() -  "
                      + evalContext.getClaimId() + " - start time::" + startTime);
            }

            claimServiceProxy.writeActivityLog(
                Long.valueOf(evalContext.getClaimId()),
                Long.valueOf(evalContext.getSuffixId()),
                QuestionnaireEvaluationConstants.LOSS_EVALUATION_SUCCESS_LOG,
                evalContext.getUserInfoDoc());

            endTime = System.currentTimeMillis();
            if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
              qeUtilsProxy
                  .logINFOMessage("QuestionEvlDAO-saveEvaluationWithClaim- writeExposureActLog- evalContext.getClaimId() -  "
                      + evalContext.getClaimId() + " --end time:: " + endTime);
              qeUtilsProxy
                  .logINFOMessage("QuestionEvlDAO-saveEvaluationWithClaim - writeExposureActLog- evalContext.getClaimId() -  "
                      + evalContext.getClaimId()
                      + " -time taken: :"
                      + (endTime - startTime));
            }

          } catch (Exception e) {
            throw new MitchellException(
                QuestionnaireEvaluationConstants.ERROR_CALLING_CLAIM_SERVICE,
                CLASS_NAME, methodName, workItemId, e.getMessage(), e);
          }

        }

        if (!evaluationDetails
            .getMitchellEvaluationDetails()
            .getEvaluationInfo()
            .getEvaluationType()
            .equalsIgnoreCase(
                QuestionnaireEvaluationConstants.CATEGORY_LOSS_EVALUATION)
            && !evaluationDetails.getMitchellEvaluationDetails()
                .getEvaluationInfo().getEvaluationType()
                .equalsIgnoreCase(QuestionnaireEvaluationConstants.CATEGORY_ER)
            && !evaluationDetails.getMitchellEvaluationDetails()
                .getEvaluationInfo().getEvaluationType()
                .equalsIgnoreCase(QuestionnaireEvaluationConstants.CATEGORY_PR)
            && !evaluationDetails.getMitchellEvaluationDetails()
                .getEvaluationInfo().getEvaluationType()
                .equalsIgnoreCase(QuestionnaireEvaluationConstants.CATEGORY_FR)
            && !evaluationDetails
                .getMitchellEvaluationDetails()
                .getEvaluationInfo()
                .getEvaluationType()
                .equalsIgnoreCase(QuestionnaireEvaluationConstants.CATEGORY_TLR)) {

          attachDocIdToClaimQuestionnaires(evaluationDetails
              .getMitchellEvaluationDetails().getEvaluationInfo().getCoCode(),
              evalContext.getClaimId(), evalContext.getSuffixId(),
              evaluationDetails.getMitchellEvaluationDetails()
                  .getQuestionnaireArray(0).getQuestionnaireId(), documentId);
        }
        // If new evaluation then call save on estimate package service.

        // copy the evaluation files saved at temporary location earlier to NAS location
        // using TransactionalFileService
        String copyDocStoreFlag = systemConfigProxy
            .getSettingValue(QuestionnaireEvaluationConstants.COPY_DOC_STORE_SET_VALUE);

        startTime = System.currentTimeMillis();
        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy
              .logINFOMessage("QuestionEvlDAO-saveEvaluationWithClaim- copyFileToNAS- evalContext.getClaimId() -  "
                  + evalContext.getClaimId() + " --start time::" + startTime);
        }

        if ("Y".equalsIgnoreCase(copyDocStoreFlag)) {
          qeUtilsProxy.copyFileToNAS(evaluationDetails.toString(), docStoreId,
              workItemId);
        }

        endTime = System.currentTimeMillis();
        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy
              .logINFOMessage("QuestionEvlDAO-saveEvaluationWithClaim -copyFileToNAS- evalContext.getClaimId() -  "
                  + evalContext.getClaimId() + " -- end time::" + endTime);
          qeUtilsProxy
              .logINFOMessage("QuestionEvlDAO-saveEvaluationWithClaim - copyFileToNAS- evalContext.getClaimId() -  "
                  + evalContext.getClaimId()
                  + " -time taken:: "
                  + (endTime - startTime));
        }

      } catch (Exception e) {
        throw new MitchellException(
            QuestionnaireEvaluationConstants.ERROR_CALLING_EPS, CLASS_NAME,
            methodName, workItemId, e.getMessage(), e);
      }

      if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
        qeUtilsProxy.logFINEMessage("Returning document Id :: " + documentId);
      }

    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SAVING_DETAILS, CLASS_NAME,
          methodName, workItemId, e.getMessage(), e);
    }
    LOGGER.exiting(CLASS_NAME, methodName);

    return documentId;
  }

  /**
   * This method call estimate package service for Delete evaluation.
   * 
   * @param coCode
   *          <code>String</code> coCode
   * @param externalRefCode
   *          <code>String</code> externalRefCode
   * @param workItemID
   *          <code>String</code> workItemID
   * 
   * @throws MitchellException
   *           in case any error while deleting
   */
  public void deleteEvaluation(String coCode, String externalRefCode,
      String workItemID)
      throws MitchellException  {

    final String methodName = "deleteEvaluation";
    LOGGER.entering(CLASS_NAME, methodName);
    String workItemId = null;
    try {
      workItemId = workItemID;
      try {

        estimatePackageServiceProxy.deleteQuestionnaireEvaluation(coCode,
            externalRefCode);

      } catch (Exception e) {
        throw new MitchellException(
            QuestionnaireEvaluationConstants.ERROR_CALLING_EPS, CLASS_NAME,
            methodName, workItemId, e.getMessage(), e);
      }

      String targetLocation = QuestionnaireEvaluationConstants.SYSTEM_CONFIG_TARGET_FILE_PATH
          + File.separator + qeUtilsProxy.getCurrentDatePath();

      try {
        transactionalFileServiceProxy.postDeleteRequest(
            QuestionnaireEvaluationConstants.APPLICATION_NAME,
            QuestionnaireEvaluationConstants.MODULE_NAME, workItemId,
            targetLocation, false);
      } catch (Exception e) {
        throw new MitchellException(
            QuestionnaireEvaluationConstants.ERROR_CALLING_TFS, CLASS_NAME,
            methodName, workItemId, e.getMessage(), e);
      }
    } catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_DELETING_DETAILS, CLASS_NAME,
          methodName, workItemId, e.getMessage(), e);

    }

    LOGGER.exiting(CLASS_NAME, methodName);

  }

  /**
   * This methos calls estimate package service for Update evaluation.
   * 
   * @param evalContext
   *          <code>QuestionnaireEvaluationContext</code> evalContext
   * @param evaluationDetails
   *          <code>MitchellEvaluationDetailsDocument</code> evaluationDetails
   * 
   * @throws MitchellException
   *           in case any exception while updating
   */
  public void updateEvaluation(QuestionnaireEvaluationContext evalContext,
      MitchellEvaluationDetailsDocument evaluationDetails)
      throws MitchellException  {

    final String methodName = "updateEvaluation";
    LOGGER.entering(CLASS_NAME, methodName);

    Long documentId = Long.valueOf(evalContext.getDocumentId());

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      qeUtilsProxy.logINFOMessage(CLASS_NAME + methodName + "documentId =>"
          + documentId);
    }

    UserInfoDocument userInfoDoc = evalContext.getUserInfoDoc();
    String sourceFileName = null;
    String workItemId = null;
    MitchellEvaluationDetailsDocument evaluationDetailsDocumentNAS = null;

    try {
      workItemId = evalContext.getWorkItemId();

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        qeUtilsProxy.logINFOMessage(CLASS_NAME + methodName + "workItemId = >"
            + workItemId);
      }

      Long docStoreId;

      try {

        docStoreId = estimatePackageServiceProxy.getDocumentStoreIdByDocId(
            evalContext.getDocumentId(), null);

        GetDocResponse docResponse = docStoreServiceProxy
            .getDocument(docStoreId.longValue());

        sourceFileName = docResponse.getfilenameondisk();

        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy.logINFOMessage("sourceFileName==" + sourceFileName);
        }

        //Read the file contents            
        String evaluationDetailsXmlNASData = getEvaluationDocumentOnNAS(sourceFileName);

        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy.logINFOMessage("evaluationDetailsXmlNASData=="
              + evaluationDetailsXmlNASData);
        }

        evaluationDetailsDocumentNAS = MitchellEvaluationDetailsDocument.Factory
            .parse(evaluationDetailsXmlNASData);

        // Check for the evaluationDetailsDocumentNAS and if not null then set the values
        if (evaluationDetailsDocumentNAS != null) {

          if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
            qeUtilsProxy
                .logINFOMessage("evaluationDetailsDocumentNAS before merge=="
                    + evaluationDetailsDocumentNAS);
          }

          QuestionnaireType newQuestionnaire = evaluationDetailsDocumentNAS
              .getMitchellEvaluationDetails().addNewQuestionnaire();
          QuestionnaireType currQuestionnaire = evaluationDetails
              .getMitchellEvaluationDetails().getQuestionnaireArray(0);

          if (currQuestionnaire.getEvaluationUserDetails() != null) {
            newQuestionnaire.setEvaluationUserDetails(currQuestionnaire
                .getEvaluationUserDetails());
          }

          if (currQuestionnaire.getCreateDateTimeStamp() != null) {
            newQuestionnaire.setCreateDateTimeStamp(currQuestionnaire
                .getCreateDateTimeStamp());
          }

          if (currQuestionnaire.getParameters() != null) {
            newQuestionnaire.setParameters(currQuestionnaire.getParameters());
          }

          if (currQuestionnaire.getQuestionnaireId() != null) {
            newQuestionnaire.setQuestionnaireId(currQuestionnaire
                .getQuestionnaireId());
          }

          if (currQuestionnaire.getQuestionnaireVersion() != null) {
            newQuestionnaire.setQuestionnaireVersion(currQuestionnaire
                .getQuestionnaireVersion());
          }

          if (currQuestionnaire.getQuestionsList() != null) {
            newQuestionnaire.setQuestionsList(currQuestionnaire
                .getQuestionsList());
          }

          if (currQuestionnaire.getResult() != null) {
            newQuestionnaire.setResult(currQuestionnaire.getResult());
          }

          if (currQuestionnaire.getEvaluationVersion() != null) {
            newQuestionnaire.setEvaluationVersion(currQuestionnaire
                .getEvaluationVersion());
          }

          // Changes for phase 2 - Update the EvaluationInfoType tag start
          EvaluationInfoType evalInfoType = evaluationDetailsDocumentNAS
              .getMitchellEvaluationDetails().getEvaluationInfo();

          // if input xml has scoring type present 
          if (evaluationDetails.getMitchellEvaluationDetails()
              .getEvaluationInfo().isSetScoringType()) {
            evalInfoType.setScoringType(evaluationDetails
                .getMitchellEvaluationDetails().getEvaluationInfo()
                .getScoringType());
          }

          // if input xml has Source System present
          if (evaluationDetails.getMitchellEvaluationDetails()
              .getEvaluationInfo().getSourceSystem() != null) {
            evalInfoType.setSourceSystem(evaluationDetails
                .getMitchellEvaluationDetails().getEvaluationInfo()
                .getSourceSystem());
          }
          // Changes for phase 2 - Update the EvaluationInfoType tag end
          if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
            qeUtilsProxy.logINFOMessage("Final evaluationDetailsXmlNAS =>"
                + evaluationDetailsDocumentNAS.toString());
          }

        }

        estimatePackageServiceProxy.updateQuestionnaireEvaluation(
            documentId.longValue(), evaluationDetailsDocumentNAS, userInfoDoc,
            null);

      } catch (Exception e) {
        throw new MitchellException(
            QuestionnaireEvaluationConstants.ERROR_CALLING_EPS, CLASS_NAME,
            methodName, workItemId, e.getMessage(), e);
      }

      //Now copy the merged XML to NAS location
      if (evaluationDetailsDocumentNAS != null) {

        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy.logINFOMessage(evaluationDetailsDocumentNAS.toString());
        }

        qeUtilsProxy.copyFileToNAS(evaluationDetailsDocumentNAS.toString(),
            docStoreId, workItemId);
      }

      if (evalContext.getSuffixId() > 0) {
        try {

          claimServiceProxy
              .writeActivityLog(
                  Long.valueOf(evalContext.getClaimId()),
                  Long.valueOf(evalContext.getSuffixId()),
                  QuestionnaireEvaluationConstants.LOSS_EVALUATION_SUCCESS_UPDATE_LOG,
                  evalContext.getUserInfoDoc());
        } catch (Exception e) {
          throw new MitchellException(
              QuestionnaireEvaluationConstants.ERROR_CALLING_CLAIM_SERVICE,
              CLASS_NAME, methodName, workItemId, e.getMessage(), e);
        }
      }

      qeUtilsProxy.logFINEMessage("After CLaim Activity logging");

    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {

      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_UPDATING_DETAILS, CLASS_NAME,
          methodName, workItemId, e.getMessage(), e);

    }

    LOGGER.exiting(CLASS_NAME, methodName);
  }

  /**
   * This methos calls estimate package service for linking claim.
   * 
   * @param evalContext
   * 
   *          <code>QuestionnaireEvaluationContext</code> evalContext
   * @return linkStatus <code>int</code>linkStatus <code>success</code>0
   *         <code>Not exist</code>1 <code>Already linked</code>2
   * 
   * @throws MitchellException
   *           in caes any exception while linking claim
   */

  public int linkQuestionnaireEvaluationToClaim(
      QuestionnaireEvaluationContext evalContext)
      throws MitchellException  {
    final String methodName = "linkQuestionnaireEvaluationToClaim";
    LOGGER.entering(CLASS_NAME, methodName);

    Long documentId;
    String coCode = null;
    String sourceFileName;
    Long docStoreId = null;
    int linkStatus;
    String workItemId = null;

    MitchellEvaluationDetailsDocument evaluationDetailsDocumentNAS = null;

    if (evalContext.getUserInfoDoc() != null
        && evalContext.getUserInfoDoc().getUserInfo() != null) {
      coCode = evalContext.getUserInfoDoc().getUserInfo().getOrgCode();
    }

    try {
      workItemId = evalContext.getWorkItemId();

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        qeUtilsProxy.logINFOMessage(CLASS_NAME + methodName + "workItemId =>"
            + workItemId);
      }

      // Get the document Id of corresponding evaluationId
      // Calling EPS getQuestionnaireEvaluationDocIdByRefCode to retrieve documentId.
      try {

        documentId = estimatePackageServiceProxy
            .getQuestionnaireEvaluationDocIdByRefCode(coCode,
                evalContext.getEvaluationID());

        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy.logINFOMessage(CLASS_NAME + methodName + "documentId =>"
              + documentId);
        }

        if (documentId != null) {

          //Call EPS linkQuestionnaireEvaluationToClaim
          linkStatus = estimatePackageServiceProxy
              .linkQuestionnaireEvaluationToClaim(documentId.longValue(),
                  evalContext.getClaimId(), evalContext.getExposureId(),
                  coCode, evalContext.getUserInfoDoc());
          //After the document Id is retrieved get the evaluation xml document stored on NAS

          docStoreId = estimatePackageServiceProxy.getDocumentStoreIdByDocId(
              documentId, null);

          if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
            qeUtilsProxy.logINFOMessage(CLASS_NAME + methodName
                + "docStoreId =>" + docStoreId);
          }

        } else {
          // Change implemented in order to let the claim get saved
          // even if the evaluation does not have a document Id
          linkStatus = LINK_STATUS_DOCUMENT_NULL;
          MitchellException me = new MitchellException(
              QuestionnaireEvaluationConstants.INVALID_DOCUMENT_ID, CLASS_NAME,
              methodName, workItemId,
              QuestionnaireEvaluationConstants.INVALID_DOCUMENT_ID
                  + QuestionnaireEvaluationConstants.DOCUMENT_ID_NULL_MESSAGE);
          // Do the error logging                    
          errorLogProxy.logError(me);
        }
      } catch (Exception e) {
        throw new MitchellException(
            QuestionnaireEvaluationConstants.ERROR_CALLING_EPS, CLASS_NAME,
            methodName, workItemId, e.getMessage(), e);
      }

      /**
       * linkStatus values:
       * Success = 0
       * Not Exist = 1
       * Already Linked = 2
       * EPS will return link status as 3 when evaluation is already linked to
       * same claim/suffix combination and status 2 when evaluation is already
       * linked to different claim/suffix combination.
       */
      // Copy file to NAS in case of link Success only else throw the exception
      String copyDocStoreFlag = systemConfigProxy
          .getSettingValue(QuestionnaireEvaluationConstants.COPY_DOC_STORE_SET_VALUE);

      if (linkStatus == 0 && ("Y".equalsIgnoreCase(copyDocStoreFlag))) {

        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy.logINFOMessage(CLASS_NAME + methodName
              + "COPY_DOC_STORE_SET_VALUE is true");
        }

        GetDocResponse docResponse = docStoreServiceProxy
            .getDocument(docStoreId.longValue());

        sourceFileName = docResponse.getfilenameondisk();

        evaluationDetailsDocumentNAS = MitchellEvaluationDetailsDocument.Factory
            .parse(getEvaluationDocumentOnNAS(sourceFileName));
        //Set the Claim Number in the xml retrieved
        if (evaluationDetailsDocumentNAS != null) {
          evaluationDetailsDocumentNAS.getMitchellEvaluationDetails()
              .getEvaluationInfo().setClaimNumber(evalContext.getClaimNumber());

          if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
            qeUtilsProxy.logINFOMessage("Final evaluationDetailsXmlNAS =>"
                + evaluationDetailsDocumentNAS.toString());
          }

          //Save updated evaluation XML using EPS. Copy to NAS
          if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
            qeUtilsProxy.logINFOMessage(CLASS_NAME + methodName
                + evaluationDetailsDocumentNAS.toString());
          }
          //Call EPS update method to update evaluation at doc store and DB

          estimatePackageServiceProxy.updateQuestionnaireEvaluation(
              documentId.longValue(), evaluationDetailsDocumentNAS,
              evalContext.getUserInfoDoc(), Long.valueOf(docResponse.getTCN()));

          qeUtilsProxy.copyFileToNAS(evaluationDetailsDocumentNAS.toString(),
              docStoreId, workItemId);

          //checking the value of sourcesystem
          // if its value is 1 -- the evaluation is created using the contingency window
          // else created throw workcentre
          BigInteger sourcesystem = evaluationDetailsDocumentNAS
              .getMitchellEvaluationDetails().getEvaluationInfo()
              .getSourceSystem();
          if (sourcesystem != null) {

            if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
              qeUtilsProxy.logINFOMessage(CLASS_NAME + methodName
                  + "sourcesystem =>" + sourcesystem.intValue());
            }

            evalContext.setSourceSystem(sourcesystem.toString());

            // Only for contigency evaluations
            if (1 == sourcesystem.intValue()) {
              publishEDOGEvent(evalContext);
            }
          } 

        }
        if (evalContext.getExposureId() > 0) {
          try {

            claimServiceProxy
                .writeActivityLog(
                    Long.valueOf(evalContext.getClaimId()),
                    Long.valueOf(evalContext.getExposureId()),
                    QuestionnaireEvaluationConstants.LOSS_EVALUATION_SUCCESS_LINK_LOG,
                    evalContext.getUserInfoDoc());

          } catch (Exception e) {
            throw new MitchellException(
                QuestionnaireEvaluationConstants.ERROR_CALLING_CLAIM_SERVICE,
                CLASS_NAME, methodName, workItemId, e.getMessage(), e);
          }
        }
      }

    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {

      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_LINKING_CLAIM, CLASS_NAME,
          methodName, workItemId, e.getMessage(), e);

    }
    LOGGER.exiting(CLASS_NAME, methodName);
    return linkStatus;
  }

  /**
   * Default method implemented by Common Base DAO..
   * 
   * @return 1
   */
  protected int defaultDAOErrorType()  {
    return 1;
  }

  /**
   * For getting evaluation Document from source location.
   * 
   * @param srcFileName
   *          <code>String</code> srcFileName
   * 
   * @return evaluationDetailsXmlNASData
   * 
   * @throws IOException
   *           in case any exception with the streams
   */
  public String getEvaluationDocumentOnNAS(String srcFileName)
      throws IOException  {
    InputStream is = null;
    String evaluationDetailsXmlNASData;

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      qeUtilsProxy.logINFOMessage("sourceFileName==" + srcFileName);
    }

    //Read the file contents
    try {
      is = new BufferedInputStream(new FileInputStream(new File(srcFileName)));

      evaluationDetailsXmlNASData = qeUtilsProxy.convertStreamToString(is);

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        qeUtilsProxy.logINFOMessage("evaluationDetailsXmlNASData=="
            + evaluationDetailsXmlNASData);
      }

    } catch (IOException ioe) {
      throw ioe;
    } finally {
      try {
        if (is != null) {
          is.close();
          is = null;
        }
      } catch (IOException ioe) {
        throw ioe;
      }

    }
    return evaluationDetailsXmlNASData;
  }

  /*
   * Method to publish a message to message bus
   * 
   * @param questionnare evaluationcontext
   */

  private void publishEDOGEvent(QuestionnaireEvaluationContext evalContext)
      throws MitchellException  {
    String methodName = "publishEDOGEvent";
    LOGGER.entering(CLASS_NAME, methodName);

    try {

      MitchellEnvelopeDocument meDoc = qeUtilsProxy
          .populateMeDocument(evalContext);
      String eventId = null;

      //Getting event Id from Custom setting...        
      eventId = systemConfigProxy
          .getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_CONTINGENCY_EVENT_ID);

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        qeUtilsProxy.logINFOMessage("Publishing EventId to EDOG :: " + eventId);
      }

      if (eventId != null && eventId.length() > 0) {
        MessagingContext msgContext = new MessagingContext(
            Integer.parseInt(eventId), meDoc, evalContext.getUserInfoDoc(),
            evalContext.getWorkItemId(), "");

        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy.logINFOMessage("Mitchell Envelope Document for EDOG :: "
              + meDoc);
        }

        // publish message to message bus
        qeUtilsProxy.publishToMessageBus(msgContext);

        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy.logINFOMessage("EDOGEvent published on message bus");
        }

      }
    } catch (MitchellException me) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_PUBLISH_EDOG_EVENT,
          CLASS_NAME, methodName, null,
          QuestionnaireEvaluationConstants.ERROR_PUBLISH_EDOG_EVENT_MSG, me);

    }

    LOGGER.exiting(CLASS_NAME, methodName);
  }

  private void attachDocIdToClaimQuestionnaires(String coCode, long claimId,
      long suffixId, String questionnaireId, Long documentId)
      throws MitchellException  {

    String methodName = "attachDocIdToClaimQuestionnaires";
    LOGGER.entering(CLASS_NAME, methodName);

    CallableStatement stmt = null;
    Connection dbConnection = null;
    int index = 1;

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      qeUtilsProxy.logINFOMessage("Parameter values before SP call-");
      qeUtilsProxy.logINFOMessage("coCode:" + coCode);
      qeUtilsProxy.logINFOMessage("claimId:" + claimId);
      qeUtilsProxy.logINFOMessage("suffixId:" + suffixId);
      qeUtilsProxy.logINFOMessage("questionnaireId:" + questionnaireId);
      qeUtilsProxy.logINFOMessage("documentId:" + documentId.longValue());
    }

    try {
      String spCall = "begin CLM.Pkg_questionnaire.AssociateDocIdToClaimQustnnres(?, ?, ?, ?, ?); end;";
      dbConnection = this.getDBConnection();
      stmt = dbConnection.prepareCall(spCall);

      stmt.setString(index++, coCode);
      stmt.setLong(index++, claimId);
      stmt.setLong(index++, suffixId);
      stmt.setString(index++, questionnaireId);
      stmt.setLong(index++, documentId.longValue());

      stmt.execute();

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        qeUtilsProxy.logINFOMessage("sp executed successfully");
      }

    } catch (MICommonDAOException de) {
      throw de;
    } catch (SQLException se) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SQL, CLASS_NAME, methodName,
          null, QuestionnaireEvaluationConstants.ERROR_SQL_MSG, se);
      me.setSeverity(MitchellException.SEVERITY.FATAL);
      me.setApplicationName(QuestionnaireEvaluationConstants.APPLICATION_NAME);
      me.setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);
      throw me;
    } catch (Exception e) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_ATTACHING_DOCID_TO_QID_CLAIM,
          CLASS_NAME,
          methodName,
          null,
          QuestionnaireEvaluationConstants.ERROR_ATTACHING_DOCID_TO_QID_CLAIM_MSG,
          e);
      me.setSeverity(MitchellException.SEVERITY.FATAL);
      me.setApplicationName(QuestionnaireEvaluationConstants.APPLICATION_NAME);
      me.setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);
      throw me;
    } finally {
      this.cleanupConnection(dbConnection, stmt);
    }

    LOGGER.exiting(CLASS_NAME, methodName);

  }

  /**
   * This method is used to get the connection to database.
   * 
   * @return Connection
   *         Connection
   * @throws Exception
   *           Exception
   */
  private Connection getDBConnection()
      throws MitchellException  {

    String methodName = "getDBConnection";
    LOGGER.entering(CLASS_NAME, methodName);

    Connection connection = null;

    try {

      connection = this.getConnection(systemConfigProxy
          .getSettingValue(QuestionnaireEvaluationConstants.DATASOURCE),
          systemConfigProxy
              .getSettingValue(QuestionnaireEvaluationConstants.JNDIFACTORY),
          systemConfigProxy
              .getSettingValue(QuestionnaireEvaluationConstants.PROVIDERURL));

    } catch (javax.naming.NamingException namExp) {
    	throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_UNKNOWN, CLASS_NAME,
          methodName, null, QuestionnaireEvaluationConstants.ERROR_UNKNOWN_MSG,
          namExp);

    } catch (java.sql.SQLException sqlExp) {
    	throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_UNKNOWN, CLASS_NAME,
          methodName, null, QuestionnaireEvaluationConstants.ERROR_UNKNOWN_MSG,
          sqlExp);
    }
    LOGGER.exiting(CLASS_NAME, methodName);
    return connection;
  }

  /**
   * This method is used to Associate Questionnaire to claim
   * 
   * @param qstnrClaimLinkDoc
   *          QuestionnaireClaimLinkDocument qstnrClaimLinkDoc
   * @param UserInfoDocument
   *          userInfoDoc
   */
  public void associateQuestionnaireToClaimSuffix(
      QuestionnaireClaimLinkDocument qstnrClaimLinkDoc,
      UserInfoDocument userInfoDoc)
      throws MitchellException  {

    String methodName = "associateQuestionnaireToClaimSuffix";
    LOGGER.entering(CLASS_NAME, methodName);

    CallableStatement stmt = null;
    Connection dbConnection = null;
    int questionnaireArraySize = 0;
    int i = 0;
    QuestionnaireEvaluationContext context = null;

    try {

      String spCall = "begin CLM.Pkg_questionnaire.Add_Forms_qustnnres_to_Claim(?, ?, ?, ?, ?); end;";

      questionnaireArraySize = qstnrClaimLinkDoc.getQuestionnaireClaimLink()
          .getQuestionnairesAssociations().getQuestionnaireInfoArray().length;

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        qeUtilsProxy.logINFOMessage("questionnaireArraySize:"
            + questionnaireArraySize);
      }

      dbConnection = this.getDBConnection();

      for (i = 0; i < questionnaireArraySize; i++) {
        context = new QuestionnaireEvaluationContext();
        context.setCompanyCode(qstnrClaimLinkDoc.getQuestionnaireClaimLink()
            .getCoCd());
        context.setClaimId(qstnrClaimLinkDoc.getQuestionnaireClaimLink()
            .getClaimID());

        context.setQuestionnaireId(qstnrClaimLinkDoc
            .getQuestionnaireClaimLink().getQuestionnairesAssociations()
            .getQuestionnaireInfoArray(i).getQuestionnaireID());

        context.setIsSetExposureId(qstnrClaimLinkDoc
            .getQuestionnaireClaimLink().getQuestionnairesAssociations()
            .getQuestionnaireInfoArray(i).isSetClaimExposureID());
        context.setExposureId(qstnrClaimLinkDoc.getQuestionnaireClaimLink()
            .getQuestionnairesAssociations().getQuestionnaireInfoArray(i)
            .getClaimExposureID());

        context.setIsSetDocumentId(qstnrClaimLinkDoc
            .getQuestionnaireClaimLink().getQuestionnairesAssociations()
            .getQuestionnaireInfoArray(i).isSetQuestionnaireDocID());
        context.setDocumentId(qstnrClaimLinkDoc.getQuestionnaireClaimLink()
            .getQuestionnairesAssociations().getQuestionnaireInfoArray(i)
            .getQuestionnaireDocID());

        stmt = dbConnection.prepareCall(spCall);

        insertIntoClmQuestinnaireLink(stmt, context);

        stmt.close();

      }
    } catch (MICommonDAOException de) {
      throw de;
    } catch (SQLException se) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SQL, CLASS_NAME, methodName,
          null, QuestionnaireEvaluationConstants.ERROR_SQL_MSG, se);
      me.setSeverity(MitchellException.SEVERITY.FATAL);
      me.setApplicationName(QuestionnaireEvaluationConstants.APPLICATION_NAME);
      me.setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);
      throw me;
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_ASSOCIATING_QTNR_TO_CLAIMSUFFIX,
          CLASS_NAME,
          methodName,
          null,
          QuestionnaireEvaluationConstants.ERROR_ASSOCIATING_QTNR_TO_CLAIMSUFFIX_MSG,
          e);
      me.setSeverity(MitchellException.SEVERITY.FATAL);
      me.setApplicationName(QuestionnaireEvaluationConstants.APPLICATION_NAME);
      me.setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);
      throw me;
    } finally {
      this.cleanupConnection(dbConnection, stmt);
    }
    LOGGER.exiting(CLASS_NAME, methodName);
  }

  private void insertIntoClmQuestinnaireLink(CallableStatement stmt,
      QuestionnaireEvaluationContext qeContext)
      throws MitchellException  {
    String methodName = "insertIntoClmQuestinnaireLink";
    LOGGER.entering(CLASS_NAME, methodName);

    int index = 1;
    try {

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        qeUtilsProxy.logINFOMessage("Parameter values before SP call-");
        qeUtilsProxy.logINFOMessage("qeContext.getCompanyCode()-"
            + qeContext.getCompanyCode());
        qeUtilsProxy.logINFOMessage("qeContext.getClaimId()-"
            + qeContext.getClaimId());
        qeUtilsProxy.logINFOMessage("qeContext.getIsSetExposureId()-"
            + qeContext.getIsSetExposureId());
        qeUtilsProxy.logINFOMessage("qeContext.getExposureId()-"
            + qeContext.getExposureId());
        qeUtilsProxy.logINFOMessage("qeContext.getQuestionnaireId()-"
            + qeContext.getQuestionnaireId());
        qeUtilsProxy.logINFOMessage("qeContext.getIsSetDocumentId()-"
            + qeContext.getIsSetDocumentId());
        qeUtilsProxy.logINFOMessage("qeContext.getDocumentId()-"
            + qeContext.getDocumentId());
      }

      stmt.setString(index++, qeContext.getCompanyCode());
      stmt.setLong(index++, qeContext.getClaimId());

      if (qeContext.getIsSetExposureId()) {
        stmt.setLong(index++, qeContext.getExposureId());
      } else {
        stmt.setNull(index++, java.sql.Types.BIGINT);
      }

      stmt.setLong(index++, qeContext.getQuestionnaireId());

      if (qeContext.getIsSetDocumentId()) {
        stmt.setLong(index++, qeContext.getDocumentId());
      } else {
        stmt.setNull(index++, java.sql.Types.BIGINT);
      }

      stmt.execute();
      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        qeUtilsProxy.logINFOMessage("sp executed successfully");
      }

    } catch (SQLException se) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SQL, CLASS_NAME, methodName,
          null, QuestionnaireEvaluationConstants.ERROR_SQL_MSG, se);
      me.setSeverity(MitchellException.SEVERITY.FATAL);
      me.setApplicationName(QuestionnaireEvaluationConstants.APPLICATION_NAME);
      me.setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);
      throw me;
    } catch (Exception e) {
    	throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_UNKNOWN, CLASS_NAME,
          methodName, null, QuestionnaireEvaluationConstants.ERROR_UNKNOWN_MSG,
          e);
    }
    LOGGER.exiting(CLASS_NAME, methodName);
  }

  public ErrorLogProxy getErrorLogProxy()  {
    return errorLogProxy;
  }

  public void setErrorLogProxy(ErrorLogProxy errorLogProxy)  {
    this.errorLogProxy = errorLogProxy;
  }

  public ClaimServiceProxy getClaimServiceProxy()  {
    return claimServiceProxy;
  }

  public void setClaimServiceProxy(ClaimServiceProxy claimServiceProxy)  {
    this.claimServiceProxy = claimServiceProxy;
  }

  public EstimatePackageServiceProxy getEstimatePackageServiceProxy()  {
    return estimatePackageServiceProxy;
  }

  public void setEstimatePackageServiceProxy(
      EstimatePackageServiceProxy estimatePackageServiceProxy)  {
    this.estimatePackageServiceProxy = estimatePackageServiceProxy;
  }

  public QuestionnaireEvaluationUtilsProxy getQeUtilsProxy()  {
    return qeUtilsProxy;
  }

  public void setQeUtilsProxy(QuestionnaireEvaluationUtilsProxy qeUtilsProxy)  {
    this.qeUtilsProxy = qeUtilsProxy;
  }

  public DocStoreServiceProxy getDocStoreServiceProxy()  {
    return docStoreServiceProxy;
  }

  public void setDocStoreServiceProxy(DocStoreServiceProxy docStoreServiceProxy)  {
    this.docStoreServiceProxy = docStoreServiceProxy;
  }

  public TransactionalFileServiceProxy getTransactionalFileServiceProxy()  {
    return transactionalFileServiceProxy;
  }

  public void setTransactionalFileServiceProxy(
      TransactionalFileServiceProxy transactionalFileServiceProxy)  {
    this.transactionalFileServiceProxy = transactionalFileServiceProxy;
  }

  public SystemConfigurationProxy getSystemConfigProxy()  {
    return systemConfigProxy;
  }

  public void setSystemConfigProxy(SystemConfigurationProxy systemConfigProxy)  {
    this.systemConfigProxy = systemConfigProxy;
  }

  /**
   * This method was created to update the custom score and comments fields for
   * the Questionnaire Evaluation.
   * 
   * @param miEvalResMap
   * @param evaluationDocument
   * @throws MitchellException
   */
  public void updateCustomEvaluationFields(Map miEvalResMap,
      String companyCode, ResultType resultType)
      throws MitchellException  {

    final String methodName = "updateCustomEvaluationFields";
    LOGGER.entering(CLASS_NAME, methodName);

    CallableStatement stmt = null;
    Connection dbConnection = null;

    String workItemId = (String) (miEvalResMap
        .get(QuestionnaireEvaluationConstants.WORKITEM_ID));
    String evaluationScoreOverrideComment = resultType
        .getEvaluationScoreOverrideComments();
    BigDecimal evaluationScoreOverride = resultType
        .getEvaluationScoreOverride();

    if (LOGGER.isLoggable(Level.INFO)) {
      LOGGER.info("Input Parameters in miEvalResMap are -"
          + miEvalResMap.values().toString()
          + "\n evaluationScoreOverrideComment value -"
          + evaluationScoreOverrideComment
          + "\n evaluationScoreOverrideComment value -"
          + evaluationScoreOverrideComment);
    }

    int index = 1;

    try {
      String spCall = "begin CLM.Pkg_questionnaire.update_clm_qustnnre_evltn(?, ?, ?, ?, ?,?); end;";
      dbConnection = this.getDBConnection();

      stmt = dbConnection.prepareCall(spCall);

      stmt.setString(index++, companyCode);
      stmt.setLong(index++,
          (Long) (miEvalResMap.get(QuestionnaireEvaluationConstants.CLAIM_ID)));
      stmt.setLong(index++,
          (Long) (miEvalResMap.get(QuestionnaireEvaluationConstants.SUFFIX_ID)));
      stmt.setLong(index++, (Long) (miEvalResMap
          .get(QuestionnaireEvaluationConstants.DOCUMENT_ID)));
      stmt.setBigDecimal(index++, evaluationScoreOverride);
      stmt.setString(index++, evaluationScoreOverrideComment);

      stmt.execute();

    } catch (MICommonDAOException de) {
      throw de;
    } catch (SQLException se) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SQL, CLASS_NAME, methodName,
          null, QuestionnaireEvaluationConstants.ERROR_SQL_MSG, se);
      me.setSeverity(MitchellException.SEVERITY.FATAL);
      me.setApplicationName(QuestionnaireEvaluationConstants.APPLICATION_NAME);
      me.setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);
      throw me;
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SAVING_DETAILS, CLASS_NAME,
          methodName, workItemId, e.getMessage(), e);
    } finally {
      this.cleanupConnection(dbConnection, stmt);
    }
    LOGGER.exiting(CLASS_NAME, methodName);
  }

  public void updateSetQuestionnareReviewDocId(String coCode, long claimId,
      long suffixId, long questinnaireId, long setId, String reviewType,
      long questEvalDocId, String userId)
      throws MitchellException  {
    final String methodName = "updateSetQuestionnareReviewDocId";
    LOGGER.entering(CLASS_NAME, methodName);

    CallableStatement stmt = null;
    Connection dbConnection = null;

    if (LOGGER.isLoggable(Level.INFO)) {
      LOGGER.info("Input Parameters in coCode are -" + coCode
          + "\n claimId value -" + claimId + "\n suffixId value -" + suffixId
          + "\n questinnaireId value -" + questinnaireId + "\n setId value -"
          + setId + "\n reviewType value -" + reviewType
          + "\n questEvalDocId value -" + questEvalDocId

      );

    }

    int index = 1;

    try {
      String spCall = "begin CLM.Pkg_questionnaire.AssociateDocIdToClaimQustnnres(?, ?, ?, ?, ?,?,?,?); end;";

      dbConnection = this.getDBConnection();

      stmt = dbConnection.prepareCall(spCall);

      stmt.setString(index++, coCode);
      stmt.setLong(index++, setId);
      stmt.setString(index++, reviewType);
      stmt.setLong(index++, (Long) (claimId));
      stmt.setLong(index++, (Long) (suffixId));
      stmt.setLong(index++, (Long) (questinnaireId));
      stmt.setLong(index++, (Long) (questEvalDocId));
      stmt.setString(index++, userId);
      stmt.execute();

    } catch (MICommonDAOException de) {
      throw de;
    } catch (SQLException se) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SQL,
          CLASS_NAME,
          methodName,
          null,
          QuestionnaireEvaluationConstants.ERROR_OCCURED_WHILE_SET_DOCID_UPDATE,
          se);
      me.setSeverity(MitchellException.SEVERITY.FATAL);
      me.setApplicationName(QuestionnaireEvaluationConstants.APPLICATION_NAME);
      me.setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);
      throw me;
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SAVING_DETAILS,
          CLASS_NAME,
          methodName,
          Long.toString(claimId)  + QuestionnaireEvaluationConstants.HYPHEN + suffixId,
          QuestionnaireEvaluationConstants.ERROR_OCCURED_WHILE_SET_DOCID_UPDATE,
          e);
    } finally {
      this.cleanupConnection(dbConnection, stmt);
    }
    LOGGER.exiting(CLASS_NAME, methodName);
  }

  
  /**
   * This method is used to get the original Questionnaire from the database which is configured for the carrier.
   * @param currntQustnnreId
   * @param coCd
   * @return QuestionnaireEvaluationDBDTO
   * @throws MitchellException
   */
  public QuestionnaireDTO getQuestionnaire(
			long questionnaireID, String companyCode) throws MitchellException {
		final String methodName = "evaluationServiceGetQues";
		LOGGER.entering(CLASS_NAME, methodName);
		ResultSet questionnairResultSet = null;
		ResultSet questionsResultSet = null;
		ResultSet answersResultSet = null;
		CallableStatement stmt = null;
		Connection dbConnection = null;
		QuestionnaireDTO questionnaireDTO = new QuestionnaireDTO();
		ArrayList<Answer> answerList = new ArrayList<Answer>();
		ArrayList<Question> questionList = new ArrayList<Question>();
		Questionnaire questionnaire = new Questionnaire();
		try {
			String spCall = "begin CLM.pkg_questionnaire.get_a_qustnnre(?,?,?,?,?); end;";
			dbConnection = this.getDBConnection();
			stmt = dbConnection.prepareCall(spCall);
			stmt.setString(1, companyCode);
			stmt.setLong(2, questionnaireID);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
			questionnairResultSet = (ResultSet) stmt.getObject(3);
			questionsResultSet = (ResultSet) stmt.getObject(4);
			answersResultSet = (ResultSet) stmt.getObject(5);

			while (questionnairResultSet.next()) {
				questionnaire
						.setCoCd(questionnairResultSet.getString("CO_CD"));
				questionnaire.setQustnnreId(questionnairResultSet
						.getLong("QUSTNNRE_ID"));
				questionnaire.setQustnnreName(questionnairResultSet
						.getString("QUSTNNRE_NAME"));
				questionnaire.setQustnnreVersion(questionnairResultSet
						.getInt("QUSTNNRE_VERSION"));
				questionnaire.setQustnnreDesc(questionnairResultSet
						.getString("QUSTNNRE_DESCRIPTION"));
				questionnaire.setEvltnCategory(questionnairResultSet
						.getString("EVLTN_CATEGORY"));
				questionnaire.setScoringType(questionnairResultSet
						.getString("SCORING_TYPE"));
				questionnaire.setMaxPoints(questionnairResultSet
						.getInt("MAX_POINTS"));
				questionnaire.setB6(questionnairResultSet.getInt(":B6"));
				questionnaire.setIsCustomScore(questionnairResultSet
						.getString("IS_CUSTOM_SCORE"));
			}
			questionnaireDTO.setQuestionnaire(questionnaire);
			Question ques = null;
			Clob clob=null;
			String qstnFormattedText=null;
			while (questionsResultSet.next()) {
				ques = new Question();

				ques.setAncstrQustnnrQustnId(questionsResultSet
						.getLong("ANCSTR_QUSTNNRE_QUSTN_ID"));
				ques.setAncstrAnswerItemId(questionsResultSet
						.getLong("ANCSTR_ANSWR_ITEM_ID"));
				ques.setLevelNumber(questionsResultSet.getInt("LEVEL_NUMBER"));
				ques.setQustnnreQustnId(questionsResultSet
						.getLong("QUSTNNRE_QUSTN_ID"));
				ques.setSiblingOrder(questionsResultSet
						.getInt("SIBLING_ORDER"));
				ques.setQustnId(questionsResultSet.getLong("QUSTN_ID"));
				ques.setSysQustnType(questionsResultSet
						.getString("SYS_QUSTN_TYPE"));
				ques.setQustnText(questionsResultSet.getString("QUSTN_TEXT"));
				ques.setAnswrControlType(questionsResultSet
						.getString("ANSWR_CONTROL_TYPE"));
				ques.setEvltnCategoryDesc(questionsResultSet
						.getString("EVLTN_CATEGORY_DESC"));
				ques.setIsLeaf(questionsResultSet.getString("IS_LEAF"));
				clob = questionsResultSet.getClob("QUSTN_FORMATTED_TEXT");

                qstnFormattedText = clobToFormattedText(clob);
                if(qstnFormattedText!=null)
                {
                       ques.setQustnFormattedText(qstnFormattedText);  
                }
                else
                {
                       ques.setQustnFormattedText(QuestionnaireEvaluationConstants.FORMATTED_TEXT);
                }

				questionList.add(ques);

			}
			questionnaireDTO.setQuestions(questionList);
			
			Answer ans = null;
			while (answersResultSet.next()) {
				
				ans = new Answer();

				ans.setQustnnerQustnId(answersResultSet
						.getLong("QUSTNNRE_QUSTN_ID"));
				ans.setAnswerItemID(answersResultSet.getLong("ANSWR_ITEM_ID"));
				ans.setAnswerDisplayText(answersResultSet
						.getString("ANSWR_DISPLAY_TEXT"));
				ans.setAnswerDisplayOrder(answersResultSet
						.getInt("ANSWR_DISPLAY_ORDER"));
				ans.setLowRangeValue(answersResultSet
						.getInt("LOW_RANGE_VALUE"));
				ans.setHighRangeValue(answersResultSet
						.getInt("HIGH_RANGE_VALUE"));

				ans.setAnswerScore(answersResultSet.getInt("ANSWR_SCORE"));

				ans.setAnswrScorePercentWeight(answersResultSet
						.getInt("ANSWR_SCORE_PERCENT_WIEGHT"));

				ans.setSysAnswrType(answersResultSet.getInt("SYS_ANSWR_TYPE"));

				ans.setIsLeaf(answersResultSet.getString("IS_LEAF"));
				answerList.add(ans);

			}
			questionnaireDTO.setAnswers(answerList);
			
		} catch (SQLException se) {
			MitchellException me = new MitchellException(
					QuestionnaireEvaluationConstants.ERROR_SQL, CLASS_NAME,
					methodName, null,
					QuestionnaireEvaluationConstants.ERROR_SQL_MSG, se);
			me.setSeverity(MitchellException.SEVERITY.FATAL);
			me.setApplicationName(QuestionnaireEvaluationConstants.APPLICATION_NAME);
			me.setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);
			throw me;
		} catch (Exception e) {
			MitchellException me = new MitchellException(QuestionnaireEvaluationConstants.ERROR_GETTING_QUESTIONNAIRE_FROM_DB,
					CLASS_NAME,
					methodName,QuestionnaireEvaluationConstants.ERROR_GETTING_QUESTIONNAIRE_MSG, e);
			me.setSeverity(MitchellException.SEVERITY.FATAL);
			me.setApplicationName(QuestionnaireEvaluationConstants.APPLICATION_NAME);
			me.setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);
			throw me;
		} finally {

			this.cleanupConnection(dbConnection, stmt);
			
		}
		return questionnaireDTO;
	}

	private String clobToFormattedText(Clob clob) throws SQLException {

		String formattedText = null;
		if (clob != null) {
			if ((int) clob.length() > 0) {
				formattedText = clob.getSubString(1, (int) clob.length());
			}
		}
		return formattedText;
	}


	
}
