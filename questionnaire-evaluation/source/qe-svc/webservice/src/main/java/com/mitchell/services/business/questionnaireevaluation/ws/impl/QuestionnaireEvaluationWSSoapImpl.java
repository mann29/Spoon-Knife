package com.mitchell.services.business.questionnaireevaluation.ws.impl;

import java.io.StringWriter;
import java.util.Map;
import java.util.logging.Logger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.WebServiceException;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkDocument;
import com.mitchell.services.business.questionnaireevaluation.client.QuestionnaireEvaluationClient;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.services.business.questionnaireevaluation.ejb.QuestionnaireEvaluationEJBRemote;
import com.mitchell.services.business.questionnaireevaluation.ws.ObjectFactory;
import com.mitchell.services.business.questionnaireevaluation.ws.QuestionnaireEvaluationWSSoap;
import com.mitchell.services.business.questionnaireevaluation.ws.UserInfoType;
import com.mitchell.utils.misc.AppUtilities;

@WebService(name = "QuestionnaireEvaluationWSSoap", targetNamespace = "http://www.openuri.org/", serviceName = "QuestionnaireEvaluationWS", portName = "QuestionnaireEvaluationWSSoap", endpointInterface = "com.mitchell.services.business.questionnaireevaluation.ws.QuestionnaireEvaluationWSSoap")
@XmlSeeAlso({ ObjectFactory.class })
public class QuestionnaireEvaluationWSSoapImpl implements
    QuestionnaireEvaluationWSSoap
{

  /**
   * class name..
   */
  private static final String CLASS_NAME = QuestionnaireEvaluationWSSoapImpl.class
      .getName();

  /**
   * Logger..
   */
  private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

  @WebMethod(action = "http://www.openuri.org/saveEvaluationAndLinkQEToClaim")
  @RequestWrapper(localName = "saveEvaluationAndLinkQEToClaim", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.SaveEvaluationAndLinkQEToClaim")
  @ResponseWrapper(localName = "saveEvaluationAndLinkQEToClaimResponse", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.SaveEvaluationAndLinkQEToClaimResponse")
  public void saveEvaluationAndLinkQEToClaim(String evaluationID,
      String clientClaimNumber, String evaluationType,
      String evaluationDetailsXmlData, String workItemId,
      String mSuffixSvcRqRsData, UserInfoType userInfoType)
  {
    LOGGER.entering("In save Evaluation with Claim method :: ", CLASS_NAME);

    try {

      if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {

        LOGGER.fine("evaluationID :: " + evaluationID);
        LOGGER.fine("ClientClaimNumber  :: " + clientClaimNumber);
        LOGGER.fine("Evaluation Type  :: " + evaluationType);
        LOGGER
            .fine("EvaluationDetailsXmlData   :: " + evaluationDetailsXmlData);
        LOGGER.fine("WorkItemId   :: " + workItemId);
        LOGGER.fine("mSuffixSvcRqRsDoc   :: " + mSuffixSvcRqRsData);
        LOGGER.fine("UserInfoType   :: " + userInfoType);
      }

      UserInfoDocument userInfoDoc = getUserInfoXmlBeanType(userInfoType);
      QuestionnaireEvaluationEJBRemote remote = QuestionnaireEvaluationClient
          .getEJB();
      remote.saveEvaluationAndLinkQEToClaim(evaluationID, clientClaimNumber,
          evaluationType, evaluationDetailsXmlData, workItemId,
          mSuffixSvcRqRsData, userInfoDoc);

    } catch (MitchellException mitchellException) {
      LOGGER.severe("Mitchell Exception while save Evaluation : "
          + AppUtilities.getCleansedAppServerStackTraceString(
              mitchellException, true));

      throw new WebServiceException(mitchellException);
    } catch (Exception genExc) {

      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.GENERIC_ERROR, CLASS_NAME,
          "saveEvaluationWithClaim", null, "Exception: " + genExc.getMessage(),
          genExc);
      throw new WebServiceException(me);
    }
    LOGGER.exiting("Returning from save Evaluation with Claim Method",
        CLASS_NAME);

  }

  @WebMethod(action = "http://www.openuri.org/saveEvaluation")
  @RequestWrapper(localName = "saveEvaluation", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.SaveEvaluation")
  @ResponseWrapper(localName = "saveEvaluationResponse", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.SaveEvaluationResponse")
  public void saveEvaluation(String evaluationID, String evaluationType,
      String evaluationDetailsXmlData, UserInfoType userInfoType)
  {

    LOGGER.entering("In save Evaluation method", CLASS_NAME);

    Map responseMap = null;

    try {

      if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
        LOGGER.fine("Evaluation Id  :: " + evaluationID);
        LOGGER.fine("Evaluation Type  :: " + evaluationType);
        LOGGER
            .fine("EvaluationDetailsXmlData   :: " + evaluationDetailsXmlData);
      }

      UserInfoDocument userInfoDoc = getUserInfoXmlBeanType(userInfoType);

      QuestionnaireEvaluationEJBRemote remote = QuestionnaireEvaluationClient
          .getEJB();
      responseMap = remote.saveEvaluation(evaluationID, evaluationType,
          evaluationDetailsXmlData, userInfoDoc);
    } catch (MitchellException mitchellException) {
      LOGGER.severe("Mitchell Exception while save Evaluation : "
          + AppUtilities.getCleansedAppServerStackTraceString(
              mitchellException, true));
      throw new WebServiceException(mitchellException);

    } catch (Exception genExc) {
      LOGGER.severe("Mitchell Exception while save Evaluation : "
          + AppUtilities.getCleansedAppServerStackTraceString(genExc, true));

      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.GENERIC_ERROR, CLASS_NAME,
          "saveEvaluation", null, "Exception: " + genExc.getMessage(), genExc);

      throw new WebServiceException(me);
    }

    LOGGER.exiting("Returning from save Evaluation Method", CLASS_NAME);
  }

  @WebMethod(action = "http://www.openuri.org/saveEvaluationWithClaim")
  @RequestWrapper(localName = "saveEvaluationWithClaim", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.SaveEvaluationWithClaim")
  @ResponseWrapper(localName = "saveEvaluationWithClaimResponse", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.SaveEvaluationWithClaimResponse")
  public void saveEvaluationWithClaim(long claimID, long suffixID,
      String evaluationType, String evaluationDetailsXmlData,
      UserInfoType userInfoType)
  {

    LOGGER.entering("In save Evaluation with Claim method :: ", CLASS_NAME);

    Map responseMap = null;

    try {
      if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
        LOGGER.fine("Claim Id  :: " + claimID);
        LOGGER.fine("Suffix Id  :: " + suffixID);
        LOGGER.fine("Evaluation Type  :: " + evaluationType);
        LOGGER
            .fine("EvaluationDetailsXmlData   :: " + evaluationDetailsXmlData);
      }

      UserInfoDocument userInfoDoc = getUserInfoXmlBeanType(userInfoType);

      QuestionnaireEvaluationEJBRemote remote = QuestionnaireEvaluationClient
          .getEJB();
      responseMap = remote.saveEvaluation(claimID, suffixID, evaluationType,
          evaluationDetailsXmlData, userInfoDoc);

    } catch (MitchellException mitchellException) {
      LOGGER.severe("Mitchell Exception while save Evaluation : "
          + AppUtilities.getCleansedAppServerStackTraceString(
              mitchellException, true));

      throw new WebServiceException(mitchellException);
    } catch (Exception genExc) {

      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.GENERIC_ERROR, CLASS_NAME,
          "saveEvaluationWithClaim", null, "Exception: " + genExc.getMessage(),
          genExc);

      throw new WebServiceException(me);
    }
    LOGGER.exiting("Returning from save Evaluation with Claim Method",
        CLASS_NAME);

  }

  @WebMethod(action = "http://www.openuri.org/updateEvaluation")
  @RequestWrapper(localName = "updateEvaluation", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.UpdateEvaluation")
  @ResponseWrapper(localName = "updateEvaluationResponse", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.UpdateEvaluationResponse")
  public void updateEvaluation(long documentID, String evaluationType,
      String evaluationDetailsXmlData, UserInfoType userInfo, long claimId,
      long suffixId, long tcn)
  {

    LOGGER.entering("In update Evaluation method", CLASS_NAME);
    Map responseMap = null;

    try {
      if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
        LOGGER.fine("Document Id  :: " + documentID);
        LOGGER.fine("Evaluation Type  :: " + evaluationType);
        LOGGER.fine("Evaluation Details  :: " + evaluationDetailsXmlData);
      }

      UserInfoDocument userInfoDoc = getUserInfoXmlBeanType(userInfo);

      QuestionnaireEvaluationEJBRemote remote = QuestionnaireEvaluationClient
          .getEJB();
      responseMap = remote.updateEvaluation(documentID, evaluationType,
          evaluationDetailsXmlData, userInfoDoc, claimId, suffixId, tcn);

    } catch (MitchellException mitchellException) {
      LOGGER.severe("Mitchell Exception while save Evaluation : "
          + AppUtilities.getCleansedAppServerStackTraceString(
              mitchellException, true));

      throw new WebServiceException(mitchellException);
    } catch (Exception genExc) {

      LOGGER.severe("Mitchell Exception while save Evaluation : "
          + AppUtilities.getCleansedAppServerStackTraceString(genExc, true));

      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.GENERIC_ERROR, CLASS_NAME,
          "updateEvaluation", null, "Exception: " + genExc.getMessage(), genExc);

      throw new WebServiceException(me);
    }
    LOGGER.exiting("Returning from save Evaluation Method", CLASS_NAME);

  }

  @WebMethod(action = "http://www.openuri.org/deleteEvaluation")
  @RequestWrapper(localName = "deleteEvaluation", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.DeleteEvaluation")
  @ResponseWrapper(localName = "deleteEvaluationResponse", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.DeleteEvaluationResponse")
  public void deleteEvaluation(String evaluationId, UserInfoType userInfoType)
  {
    LOGGER.entering("In Delete Evaluation Method :: ", CLASS_NAME);
    Map responseMap = null;

    try {

      UserInfoDocument userInfoDoc = getUserInfoXmlBeanType(userInfoType);

      if (userInfoDoc != null && userInfoDoc.getUserInfo() != null) {

        QuestionnaireEvaluationEJBRemote remote = QuestionnaireEvaluationClient
            .getEJB();
        responseMap = remote.deleteEvaluation(userInfoDoc, evaluationId);
      }
    } catch (MitchellException mitchellException) {
      LOGGER.severe("Mitchell Exception while Delete Evaluation : "
          + AppUtilities.getCleansedAppServerStackTraceString(
              mitchellException, true));

      throw new WebServiceException(mitchellException);
    } catch (Exception genExc) {

      LOGGER.severe("Mitchell Exception while Delete Evaluation : "
          + AppUtilities.getCleansedAppServerStackTraceString(genExc, true));

      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.GENERIC_ERROR, CLASS_NAME,
          "deleteEvaluation", null, "Exception: " + genExc.getMessage(), genExc);

      throw new WebServiceException(me);
    }

    LOGGER.exiting("Returning from Delete Evaluation Method", CLASS_NAME);
  }

  @WebMethod(action = "http://www.openuri.org/linkQuestionnaireEvaluationToClaim")
  @WebResult(name = "linkQuestionnaireEvaluationToClaimResult", targetNamespace = "http://www.openuri.org/")
  @RequestWrapper(localName = "linkQuestionnaireEvaluationToClaim", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.LinkQuestionnaireEvaluationToClaim")
  @ResponseWrapper(localName = "linkQuestionnaireEvaluationToClaimResponse", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.LinkQuestionnaireEvaluationToClaimResponse")
  public int linkQuestionnaireEvaluationToClaim(String evaluationID,
      long claimId, long exposureId, String claimNumber, UserInfoType userInfo)
  {
    String methodName = "linkQuestionnaireEvaluationToClaim";
    LOGGER.entering(methodName, CLASS_NAME);
    int linkStatus;

    try {
      if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
        LOGGER.fine("evaluation ID  :: " + evaluationID);
        LOGGER.fine("claim Id  :: " + claimId);
        LOGGER.fine("exposure Id  :: " + exposureId);
        LOGGER.fine("claim Number  :: " + claimNumber);
      }

      UserInfoDocument userInfoDoc = getUserInfoXmlBeanType(userInfo);

      QuestionnaireEvaluationEJBRemote remote = QuestionnaireEvaluationClient
          .getEJB();
      linkStatus = remote.linkQuestionnaireEvaluationToClaim(evaluationID,
          claimId, exposureId, claimNumber, userInfoDoc);

    } catch (MitchellException mitchellException) {

      LOGGER
          .severe("Mitchell Exception while linkQuestionnaireEvaluationToClaim Evaluation : "
              + AppUtilities.getCleansedAppServerStackTraceString(
                  mitchellException, true));

      throw new WebServiceException(mitchellException);
    } catch (Exception genExc) {
      LOGGER
          .severe("Mitchell Exception while linkQuestionnaireEvaluationToClaim Evaluation : "
              + AppUtilities.getCleansedAppServerStackTraceString(genExc, true));

      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.GENERIC_ERROR, CLASS_NAME,
          methodName, null, "Exception: " + genExc.getMessage(), genExc);

      throw new WebServiceException(me);
    }
    return linkStatus;
  }

  /**
   * This method is used to Associate Questionnaire to claim
   * 
   * @param claimQuestionnaireAssociation
   * @param userInfo
   */
  @WebMethod(action = "http://www.openuri.org/associateQuestionnaireToClaimSuffix")
  @RequestWrapper(localName = "associateQuestionnaireToClaimSuffix", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.AssociateQuestionnaireToClaimSuffix")
  @ResponseWrapper(localName = "associateQuestionnaireToClaimSuffixResponse", targetNamespace = "http://www.openuri.org/", className = "com.mitchell.services.business.questionnaireevaluation.ws.AssociateQuestionnaireToClaimSuffixResponse")
  public void associateQuestionnaireToClaimSuffix(
      @WebParam(name = "claimQuestionnaireAssociation", targetNamespace = "http://www.openuri.org/") String claimQuestionnaireAssociation,
      @WebParam(name = "userInfo", targetNamespace = "http://www.openuri.org/") UserInfoType userInfo)
  {

    String methodName = "associateQuestionnaireToClaimSuffix";
    LOGGER.entering(methodName, CLASS_NAME);

    UserInfoDocument userInfoDoc = null;

    try {

      if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
        LOGGER.fine("string-claimQuestionnaireAssociation:"
            + claimQuestionnaireAssociation);
      }

      QuestionnaireClaimLinkDocument qstnrClaimLinkDoc = QuestionnaireClaimLinkDocument.Factory
          .parse(claimQuestionnaireAssociation);

      userInfoDoc = getUserInfoXmlBeanType(userInfo);

      if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
        LOGGER.fine("WS-userInfoDoc :" + userInfoDoc);
        LOGGER.fine("WS-qstnrClaimLinkDoc :" + qstnrClaimLinkDoc);
      }

      QuestionnaireEvaluationEJBRemote remote = QuestionnaireEvaluationClient
          .getEJB();
      remote
          .associateQuestionnaireToClaimSuffix(qstnrClaimLinkDoc, userInfoDoc);

    } catch (MitchellException mitchellException) {

      LOGGER
          .severe("Mitchell Exception while linkQuestionnaireEvaluationToClaim Evaluation : "
              + AppUtilities.getCleansedAppServerStackTraceString(
                  mitchellException, true));

      throw new WebServiceException(mitchellException);

    } catch (Exception genExc) {
      LOGGER
          .severe("Mitchell Exception while linkQuestionnaireEvaluationToClaim Evaluation : "
              + AppUtilities.getCleansedAppServerStackTraceString(genExc, true));

      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.GENERIC_ERROR, CLASS_NAME,
          methodName, null, "Exception: " + genExc.getMessage(), genExc);

      throw new WebServiceException(me);
    }

    LOGGER.exiting(methodName, CLASS_NAME);
  }

  private UserInfoDocument getUserInfoXmlBeanType(UserInfoType userInfo)
  {

    UserInfoDocument userInfoDoc = null;
    try {
      if (userInfo != null) {
        JAXBContext jc = JAXBContext.newInstance(UserInfoType.class
            .getPackage().getName());
        Marshaller marshaller = jc.createMarshaller();
        JAXBElement<UserInfoType> errorElement = (new ObjectFactory())
            .createUserInfo(userInfo);
        StringWriter sw = new StringWriter();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(errorElement, sw);
        userInfoDoc = UserInfoDocument.Factory.parse(sw.toString());
      }
    } catch (Exception ex) {
      LOGGER.severe(AppUtilities.getStackTraceString(ex));
      throw new WebServiceException(ex);
    }
    return userInfoDoc;
  }

}
