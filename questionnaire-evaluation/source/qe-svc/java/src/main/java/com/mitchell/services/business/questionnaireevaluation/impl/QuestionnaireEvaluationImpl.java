package com.mitchell.services.business.questionnaireevaluation.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.AssignmentAddRqDocument.AssignmentAddRq;
import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.NodeType;
import com.mitchell.common.types.UserHierType;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.eventgenerator.beans.FileEventDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.schemas.MitchellEnvelopeType.EnvelopeBodyList;
import com.mitchell.schemas.NameValuePairType;
import com.mitchell.schemas.evaluationdetails.EvaluationDetailsType;
import com.mitchell.schemas.evaluationdetails.EvaluationInfoType;
import com.mitchell.schemas.evaluationdetails.EvaluationUserType;
import com.mitchell.schemas.evaluationdetails.MitchellEvaluationDetailsDocument;
import com.mitchell.schemas.evaluationdetails.QuestionListType;
import com.mitchell.schemas.evaluationdetails.QuestionType;
import com.mitchell.schemas.evaluationdetails.QuestionnaireType;
import com.mitchell.schemas.evaluationdetails.ResultType;
import com.mitchell.schemas.evaluationdetails.ScoreType;
import com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument;
import com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.EvaluationRq;
import com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType;
import com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkDocument;
import com.mitchell.services.business.questionnaireevaluation.QuestionnaireEvaluationContext;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.services.business.questionnaireevaluation.dao.QuestionnaireEvaluationDAOProxy;
import com.mitchell.services.business.questionnaireevaluation.dto.Answer;
import com.mitchell.services.business.questionnaireevaluation.dto.ContextDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.Question;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireDetails;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireRqRsDTO;
import com.mitchell.services.business.questionnaireevaluation.proxy.ClaimServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.CustomSettingProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.DocStoreServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.ErrorLogProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.EstimatePackageServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.MSTransfromEngineProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.SystemConfigurationProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.UserInfoProxy;
import com.mitchell.services.business.questionnaireevaluation.util.MessagingContext;
import com.mitchell.services.business.questionnaireevaluation.util.QuestionnaireEvaluationUtilsProxy;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;
import com.mitchell.services.technical.claim.client.ClaimServiceClient;
import com.mitchell.services.technical.claim.common.DTO.BmsClmInputDTO;
import com.mitchell.services.technical.claim.common.DTO.ClaimInfoDTO;
import com.mitchell.services.technical.claim.dao.vo.ClaimProperty;
import com.mitchell.services.technical.claim.mcf.McfClmOutDTO;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.misc.StringUtilities;
import com.mitchell.utils.misc.UUIDFactory;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * Implementation Class used by QuestionnaireEvaluationEJB to save or delete the
 * Evaluations passed.
 * 
 */

public class QuestionnaireEvaluationImpl implements QuestionnaireEvaluationImplProxy{
  /**
   * class name..
   */
  private static final String CLASS_NAME = QuestionnaireEvaluationImpl.class
      .getName();
  /**
   * logger..
   */
  private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
  
  private static final String EXCEPTION_DESC= "Exception: ";

  /**
   * DAO object reference..
   */
  private QuestionnaireEvaluationDAOProxy evaluationDAO;
  private ErrorLogProxy errorLogProxy = null;
 
  private SystemConfigurationProxy systemConfigProxy = null;
  private ClaimServiceProxy claimServiceProxy = null;
  private CustomSettingProxy customSettingProxy = null;
  private QuestionnaireEvaluationUtilsProxy qeUtilsProxy = null;
  private MSTransfromEngineProxy mSTransfromEngineProxy = null;
  private UserInfoProxy userInfoProxy = null;
  private EstimatePackageServiceProxy estimatePackageServiceProxy = null;
  private DocStoreServiceProxy docStoreServiceProxy = null;
  
public void setDocStoreServiceProxy(DocStoreServiceProxy docStoreServiceProxy) {
	this.docStoreServiceProxy = docStoreServiceProxy;
}

public void setEstimatePackageServiceProxy(
		EstimatePackageServiceProxy estimatePackageServiceProxy) {
	this.estimatePackageServiceProxy = estimatePackageServiceProxy;
}

  public void setUserInfoProxyImpl(UserInfoProxy userInfoProxy){
    this.userInfoProxy = userInfoProxy;
  }

  public void setEvaluationDAO(QuestionnaireEvaluationDAOProxy evaluationDAO){
    this.evaluationDAO = evaluationDAO;
  }

  public ErrorLogProxy getErrorLogProxy() {
    return errorLogProxy;
  }

  public ClaimServiceProxy getClaimServiceProxy(){
    return claimServiceProxy;
  }

  public void setClaimServiceProxy(ClaimServiceProxy claimServiceProxy) {
    this.claimServiceProxy = claimServiceProxy;
  }

  public CustomSettingProxy getCustomSettingProxy(){
    return customSettingProxy;
  }

  public void setCustomSettingProxy(CustomSettingProxy customSettingProxy){
    this.customSettingProxy = customSettingProxy;
  }

  public MSTransfromEngineProxy getmSTransfromEngineProxy(){
    return mSTransfromEngineProxy;
  }

  public void setmSTransfromEngineProxy(
      MSTransfromEngineProxy mSTransfromEngineProxy) {
    this.mSTransfromEngineProxy = mSTransfromEngineProxy;
  }

  public void setErrorLogProxy(ErrorLogProxy errorLogProxy) {
    this.errorLogProxy = errorLogProxy;
  }

  public QuestionnaireEvaluationUtilsProxy getQeUtilsProxy() {
    return qeUtilsProxy;
  }

  public void setQeUtilsProxy(QuestionnaireEvaluationUtilsProxy qeUtilsProxy) {
    this.qeUtilsProxy = qeUtilsProxy;
  }

  public SystemConfigurationProxy getSystemConfigProxy() {
    return systemConfigProxy;
  }

  public void setSystemConfigProxy(SystemConfigurationProxy systemConfigProxy){
    this.systemConfigProxy = systemConfigProxy;
  }

  public Map<Object, Object> saveEvaluationAndLinkQEToClaim(
      String clientClaimNumber, String evaluationID, String evaluationType,
      String mSuffixSvcRqRsData, String evaluationDetailsXmlData,
      UserInfoDocument userInfoDoc, String workItemId)
      throws MitchellException {
    McfClmOutDTO mcfClmOutDTO = null;
    ClaimInfoDTO claimInfoDTO = null;
    Map<Object, Object> responseData = null;

    responseData = saveEvaluation(evaluationID, evaluationType,
        evaluationDetailsXmlData, userInfoDoc, workItemId);

    if (!StringUtilities.isEmpty(clientClaimNumber)) {
      String orgCode = null;
      if (userInfoDoc != null && userInfoDoc.getUserInfo() != null) {
        orgCode = userInfoDoc.getUserInfo().getOrgCode();

      }
      String parsingRule = customSettingProxy.getCustomValue(orgCode, orgCode,
          QuestionnaireEvaluationConstants.CSET_CLIAM_GROUP_NAME,
          QuestionnaireEvaluationConstants.CSET_CLAIM_SETTING_CLAIMNUMBERMASK);

      claimInfoDTO = claimServiceProxy.getSimpleClaimInfoByFullClaimNumber(
          userInfoDoc, clientClaimNumber, orgCode);

      if (claimInfoDTO == null || claimInfoDTO.getClaimId() == null
          || claimInfoDTO.getClaimExposureId() == null) {
        mcfClmOutDTO = createClaim(clientClaimNumber, userInfoDoc, orgCode,
            parsingRule, workItemId, mSuffixSvcRqRsData);

        if (mcfClmOutDTO != null) {
          linkQuestionnaireEvaluationToClaim(evaluationID,
              mcfClmOutDTO.getClaimID(), mcfClmOutDTO.getExposureID(),
              mcfClmOutDTO.getClaim().getClaimNumber(), userInfoDoc, workItemId);
        }
      } else {
        linkQuestionnaireEvaluationToClaim(evaluationID,
            claimInfoDTO.getClaimId(), claimInfoDTO.getClaimExposureId(),
            claimInfoDTO.getClientClaimNumber(), userInfoDoc, workItemId);
      }
    }

    return responseData;
  }

  private MitchellEnvelopeDocument convertToMitchellDoc(
      MitchellSuffixSvcRqRsDocument mSRqRsDoc, String worlkItemId) {

    MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
        .newInstance();
    MitchellEnvelopeType meType = meDoc.addNewMitchellEnvelope();
    EnvelopeBodyList envBody = meType.addNewEnvelopeBodyList();
    envBody
        .addNewEnvelopeBody()
        .addNewContent()
        .set(
            mSRqRsDoc.getMitchellSuffixSvcRqRs().getEvaluationRq()
                .getClaimInfo());
    envBody
        .addNewEnvelopeBody()
        .addNewContent()
        .set(mSRqRsDoc.getMitchellSuffixSvcRqRs().getEvaluationRq().getSuffix());
    NameValuePairType nameValuePair = meType.addNewEnvelopeContext()
        .addNewNameValuePair();
    nameValuePair.setName(QuestionnaireEvaluationConstants.QE_CORRELATION_ID);
    String[] paramNameValue = new String[1];
    paramNameValue[0] = worlkItemId;
    nameValuePair.setValueArray(paramNameValue);
    return meDoc;

  }

  private McfClmOutDTO createClaim(String claimNo,
      UserInfoDocument userInfoDocument, String orgId, String parsingRule,
      String workItemId, String mSuffixSvcRqRsData)
      throws MitchellException {

     final String methodName = "createClaim";
    McfClmOutDTO mcfClmOutDTO = null;
    AssignmentAddRqDocument assignAddRqDoc = null;
    BmsClmInputDTO inputDTO = null;
    UserInfoDocument adjusterUserinfo = null;
    MitchellSuffixSvcRqRsDocument mSuffixSvcRqRsDoc = (MitchellSuffixSvcRqRsDocument) parseDocument(
        mSuffixSvcRqRsData,
        QuestionnaireEvaluationConstants.QE_DOCUMENT_SUFFIXRQRS_TYPE);
    validateDocument(mSuffixSvcRqRsDoc,
        QuestionnaireEvaluationConstants.QE_SUFFIXRQRSDOC_VALIDATION_ERROR);
    EvaluationRq evaluationRq = mSuffixSvcRqRsDoc.getMitchellSuffixSvcRqRs()
        .getEvaluationRq();
    if (evaluationRq == null) {
       throw new MitchellException(
          QuestionnaireEvaluationConstants.QE_INPUT_EVALUATIONRQ_ISNULL,
                 CLASS_NAME, methodName, "Evaluation Request is Null");
    }
    MitchellEnvelopeDocument meDoc = convertToMitchellDoc(mSuffixSvcRqRsDoc,
        workItemId);
    XmlObject ciecaDoc = mSTransfromEngineProxy.getTransFormData(meDoc);
    try {
      assignAddRqDoc = AssignmentAddRqDocument.Factory
          .parse(transformXmlToAssignmentAddRq(ciecaDoc.xmlText()).getDomNode());
    } catch (XmlException xmlExcep) {
      final String desc = "XMLException occured while parsing AssignmentAddRqDocument: "
          + xmlExcep;
      LOGGER.severe("Got Exception:"
          + AppUtilities.getStackTraceString(xmlExcep));
      throw new MitchellException(
          QuestionnaireEvaluationConstants.QE_ASSIGNMENT_ADDRQRS_PARSE_ERROR,
          CLASS_NAME, methodName, desc, xmlExcep);
    }
    assignAddRqDoc.getAssignmentAddRq().getClaimInfo().setClaimNum(claimNo);
    validateDocument(assignAddRqDoc,
        QuestionnaireEvaluationConstants.QE_SUFFIXRQRSDOC_VALIDATION_ERROR);

    if (evaluationRq.getSuffix() != null
        && !StringUtilities.isEmpty(evaluationRq.getSuffix()
            .getClaimAdjusterID())) {
      String claimAdjusterID = evaluationRq.getSuffix().getClaimAdjusterID();
      adjusterUserinfo = userInfoProxy.getUserInfo(orgId, claimAdjusterID);
    }

    if (adjusterUserinfo == null || adjusterUserinfo.getUserInfo() == null) {
      LOGGER.info("Adjuster Not found : getting Default Adjuster");
      adjusterUserinfo = getDefaultAdjuster(orgId);

    }

    inputDTO = claimServiceProxy.createInputDTO(assignAddRqDoc,
        adjusterUserinfo, parsingRule, userInfoDocument);

    if (inputDTO != null) {
      if (evaluationRq.getSuffix() != null) {
        inputDTO.setSuffixMemo(mSuffixSvcRqRsDoc.getMitchellSuffixSvcRqRs()
            .getEvaluationRq().getSuffix().getSuffixMemo());
        setPropertyInfo(mSuffixSvcRqRsDoc, inputDTO);
      }

      if (evaluationRq.getClaimInfo() != null) {
        inputDTO.setCatIndicator(evaluationRq.getClaimInfo().getCatFlag());
      }
    }
    mcfClmOutDTO = claimServiceProxy.saveClaimFromAssignmentBms(inputDTO);
    if (mcfClmOutDTO == null) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.QE_SAVECLAIM_NULL_EXCEPTION,
          CLASS_NAME, methodName, "null after saved claim");
    }

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      LOGGER.info("Claim Saved Successfully with claimId = "
          + mcfClmOutDTO.getClaimID());
    }

    return mcfClmOutDTO;

  }

  private UserInfoDocument getDefaultAdjuster(String coCd)
      throws MitchellException {
    UserInfoDocument userInfoDocument = null;

    String defaultAdjusterId = customSettingProxy.getCustomValue(coCd, coCd,
        QuestionnaireEvaluationConstants.CSET_CARRIER_GROUP_SETTING,
        QuestionnaireEvaluationConstants.CSET_DEFAULT_ADJUSTER_ID);
    userInfoDocument = userInfoProxy.getUserInfo(coCd, defaultAdjusterId);
    if (userInfoDocument == null || userInfoDocument.getUserInfo() == null) {
      final String errorMessage = "Adjuster is mandatory and can not be null/invalid.";
      LOGGER.severe(errorMessage);
     throw new MitchellException(
          QuestionnaireEvaluationConstants.INVALID_OR_MISSING_ADJUSTER_ERROR,
          CLASS_NAME, "getDefaultAdjuster", errorMessage);
        }
    return userInfoDocument;
  }

  private void setPropertyInfo(MitchellSuffixSvcRqRsDocument mSuffixSvcRqRsDoc,
      BmsClmInputDTO inputDTO) {
    ClaimProperty claimProperty = null;
    if (mSuffixSvcRqRsDoc.getMitchellSuffixSvcRqRs().getEvaluationRq()
        .getSuffix().getPropertyInfo() != null
        && mSuffixSvcRqRsDoc.getMitchellSuffixSvcRqRs().getEvaluationRq()
            .getSuffix().getPropertyInfo().getPropertyDamageAssignment() != null) {
      claimProperty = new ClaimProperty();
      PropertyDamageAssignmentType propDamageAssgn = mSuffixSvcRqRsDoc
          .getMitchellSuffixSvcRqRs().getEvaluationRq().getSuffix()
          .getPropertyInfo().getPropertyDamageAssignment();
      //remove hardcoding FXD_PRPRTY in next cycle and put propDamageAssgn.getPropertyType() to take input data
      inputDTO.setPropertyType("FXD_PRPRTY");
      claimProperty.setPropertyDescription(propDamageAssgn
          .getPropertyDescription());
      if (propDamageAssgn.getPropertyAddress() != null) {
        claimProperty.setAddress1(propDamageAssgn.getPropertyAddress()
            .getAddress1());
        claimProperty.setAddress2(propDamageAssgn.getPropertyAddress()
            .getAddress2());
        claimProperty.setCity(propDamageAssgn.getPropertyAddress().getCity());
        claimProperty.setStateProvince(propDamageAssgn.getPropertyAddress()
            .getState());
        claimProperty.setCountry(propDamageAssgn.getPropertyAddress()
            .getCountryCode());
        claimProperty.setZipPostalCode(propDamageAssgn.getPropertyAddress()
            .getZipPostalCode());
      }
      inputDTO.setClaimProperty(claimProperty);
    }
  }

  private AssignmentAddRq transformXmlToAssignmentAddRq(String bmsData)
      throws MitchellException{
    CIECADocument ciecaDoc = null;
    AssignmentAddRq assignAddRqDoc = null;
    ciecaDoc = (CIECADocument) parseDocument(bmsData,
        QuestionnaireEvaluationConstants.QE_DOCUMENT_CIECA_TYPE);
    validateDocument(ciecaDoc,
        QuestionnaireEvaluationConstants.QE_CIECA_VALIDATION_ERROR);
    assignAddRqDoc = ciecaDoc.getCIECA().getAssignmentAddRq();
    return assignAddRqDoc;

  }

  private XmlObject parseDocument(String data, String documentType)
      throws MitchellException {

    XmlObject xmlObject = null;
    int errorType = 0;
    try {

      if (documentType
          .equalsIgnoreCase(QuestionnaireEvaluationConstants.QE_DOCUMENT_CIECA_TYPE)) {
        errorType = QuestionnaireEvaluationConstants.QE_CIECA_PARSE_ERROR;
        xmlObject = CIECADocument.Factory.parse(data);
      }

      if (documentType
          .equalsIgnoreCase(QuestionnaireEvaluationConstants.QE_DOCUMENT_SUFFIXRQRS_TYPE)) {
        errorType = QuestionnaireEvaluationConstants.QE_CIECA_PARSE_ERROR;
        xmlObject = MitchellSuffixSvcRqRsDocument.Factory.parse(data);
      }
    } catch (XmlException xmlExcep) {
      final String desc = "XMLException occured while parsing DocType: "
          + documentType + xmlExcep;
      LOGGER.severe("Got Exception:"
          + AppUtilities.getStackTraceString(xmlExcep));
      throw new MitchellException(errorType,
          CLASS_NAME, "parseDocument", desc, xmlExcep);
    }
    return xmlObject;
  }

  private void validateDocument(XmlObject xmlObject, int errorType)
      throws MitchellException{

    final String methodName = "validDateDocument";
    boolean isDocumentValid = false;

    Collection<Object> validationErrors = new java.util.ArrayList<Object>();
    String errorDescription = null;
    isDocumentValid = xmlObject.validate(getXmlOptions(validationErrors));
    if (!isDocumentValid) {

      String desc = "Validation Failed of ErrorType: " + errorType;
      LOGGER.severe(desc + "\n" + xmlObject.toString());
      String message = getXmlValidationErrors(validationErrors);
      errorDescription = desc + "\nMessage : " + message + "\nErrroType : "
          + errorType + xmlObject;
      throw new MitchellException(errorType,
          CLASS_NAME, methodName, errorDescription);
        }

  }

  /**
   * This method is used while validating an XML.
   * 
   * @param validationErrorsArrayList
   *          ArrayList representing validation errors.
   * @return com.bea.xml.XmlOptions XMLOptions to be returned for validate()
   *         method.
   * */
  private XmlOptions getXmlOptions(Collection<Object> validationErrorsArrayList){

    final XmlOptions xmlOptions = new XmlOptions();
    xmlOptions.setLoadLineNumbers();
    xmlOptions.setErrorListener(validationErrorsArrayList);

    return xmlOptions;
  }

  /**
   * This method is used while validating an XML.
   * 
   * @param validationErrorsArrayList
   *          Validation errors for XML validation failures.
   * @return String Error string representing the XML validation failure
   *         errors.
   * */
  private String getXmlValidationErrors(
      Collection<Object> validationErrorsArrayList) {

    StringBuilder invalidDocErrors = new StringBuilder();
    java.util.Iterator<Object> errorsIterator = validationErrorsArrayList
        .iterator();
    while (errorsIterator.hasNext()) {
      invalidDocErrors.append(errorsIterator.next().toString());
    }
    return invalidDocErrors.toString();
  }

  /**
   * This method initializes the XML and returns documentID and other details
   * of saved evaluation document in Map sfrom QuestionnaireEvaluationDAO.
   * 
   * @param evaluationID
   *          <code>Long</code> evaluationID
   * @param evaluationType
   *          <code>String</code> evaluationType
   * @param evaluationDetailsXmlData
   *          <code>String</code> evaluationDetailsXmlData
   * @param userInfoDoc
   *          <code>UserInfoDocument</code> userInfoDoc
   * @param workItemID
   *          <code>String</code> workItemID
   * 
   * @return responseMap
   * 
   * @throws MitchellException
   *           in case unable to save details
   */

  public Map<Object, Object> saveEvaluation(String evaluationID,
      String evaluationType, String evaluationDetailsXmlData,
      UserInfoDocument userInfoDoc, String workItemID)
      throws MitchellException {
	  
    final String methodName = "saveEvaluation";
    LOGGER.entering(CLASS_NAME, methodName);

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      qeUtilsProxy.logINFOMessage("evaluationID== " + evaluationID);
      qeUtilsProxy.logINFOMessage("evaluationDetailsXmlData === "
          + evaluationDetailsXmlData);
    }
    
    QuestionnaireEvaluationContext contextObj = null;
    contextObj = new QuestionnaireEvaluationContext();

    Map<Object, Object> responseMap = new HashMap<Object, Object>();
    Long documentID = null;
    MitchellEvaluationDetailsDocument evaluationDocument;

    try {
      // validate input..
      evaluationDocument = validateXML(evaluationDetailsXmlData, workItemID);

      validateEvaluationID(evaluationID, workItemID);

      // validate version , it should not be zero..
      /**
       * if (evaluationDocument.getMitchellEvaluationDetails() != null &&
       * evaluationDocument
       * .getMitchellEvaluationDetails().getQuestionnaireArray() != null
       * && evaluationDocument
       * .getMitchellEvaluationDetails().getQuestionnaireArray().length >
       * 0 && evaluationDocument.getMitchellEvaluationDetails().
       * getQuestionnaireArray(0).getEvaluationVersion() != null) {
       **/
      validateVersion(evaluationDocument, workItemID);

    
      // validate evaluationType, should be in set file
      validateEvaluationType(evaluationType, workItemID);

      // validate userInfoDocument, should not be null
      validateUserInfo(userInfoDoc, workItemID);

      // populate context object..
      contextObj.setEvaluationID(evaluationID);
      contextObj.setUserInfoDoc(userInfoDoc);
      contextObj.setWorkItemId(workItemID);

      documentID = evaluationDAO.saveEvaluation(contextObj, evaluationDocument);
      
      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        qeUtilsProxy.logINFOMessage(CLASS_NAME + methodName + "documentID =>"
            + documentID);
      }
      
      //Questionnaire Evaluation Reporting
      if (documentID != null) {
    	  contextObj.setDocumentId(documentID);
    	  processEvalDocForReporting(evaluationDocument,contextObj);
      }

      responseMap.put(QuestionnaireEvaluationConstants.RESPONSE_KEY_DOCUMENTID,
          documentID);

      responseMap.put(QuestionnaireEvaluationConstants.EVALUATION_ID,
          evaluationID);

      responseMap.put(QuestionnaireEvaluationConstants.WORKITEM_ID, workItemID);

      responseMap.put(QuestionnaireEvaluationConstants.USER_ID, userInfoDoc
          .getUserInfo().getUserID());

      if (evaluationDocument.getMitchellEvaluationDetails().getEvaluationInfo() != null) {
        responseMap.put(QuestionnaireEvaluationConstants.CLAIM_NUMBER,
            evaluationDocument.getMitchellEvaluationDetails()
                .getEvaluationInfo().getClaimNumber());
      }

      /**
       * if (evaluationDocument.getMitchellEvaluationDetails() != null &&
       * evaluationDocument
       * .getMitchellEvaluationDetails().getQuestionnaireArray() != null
       * && evaluationDocument
       * .getMitchellEvaluationDetails().getQuestionnaireArray().length >
       * 0 && evaluationDocument.getMitchellEvaluationDetails().
       * getQuestionnaireArray(0).getEvaluationVersion() != null) {
       **/
      responseMap.put(QuestionnaireEvaluationConstants.RESPONSE_KEY_VERSION,
          evaluationDocument.getMitchellEvaluationDetails()
              .getQuestionnaireArray(0).getEvaluationVersion().toString());
   
      // Log activity Success saving..

      if (documentID != null) {

        // log event for save ...
        qeUtilsProxy.logAppEvent(responseMap, userInfoDoc,
            QuestionnaireEvaluationConstants.SAVE_EVALUATION_SUCCESS);

      }

    } catch (MitchellException me) {
      errorLogProxy.logError(me);
      throw me;
    } catch (Exception e) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SAVING_DETAILS, CLASS_NAME,
          methodName, workItemID, EXCEPTION_DESC + e.getMessage(), e);
      errorLogProxy.logError(me);
      throw me;
    }

    LOGGER.exiting(CLASS_NAME, methodName);

    return responseMap;
  }

  /**
   * This method initializes the XML and returns documentID and other details
   * of saved evaluation document with claim in Map from
   * QuestionnaireEvaluationDAO.
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
   * @param workItemID
   *          <code>UserInfoDocument</code> workItemID
   * 
   * 
   * @return responseMap
   * 
   * @throws MitchellException
   *           in case unable to save details
   */

  public Map saveEvaluation(long claimID, long suffixID, String evaluationType,
      String evaluationDetailsXmlData, UserInfoDocument userInfoDoc,
      String workItemID)
      throws MitchellException  {
    final String methodName = "saveEvaluation";
    LOGGER.entering(CLASS_NAME, methodName);

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      qeUtilsProxy.logINFOMessage("claimID==" + claimID);
      qeUtilsProxy.logINFOMessage("evaluationDetailsXmlData= ="
          + evaluationDetailsXmlData);
    }
    LOGGER.info("evaluationDetailsXmlData =="
            + evaluationDetailsXmlData);
    QuestionnaireEvaluationContext contextObj = null;
    contextObj = new QuestionnaireEvaluationContext();

    Map responseMap = new HashMap();

    Long documentID = null;
    MitchellEvaluationDetailsDocument evaluationDocument = null;

    try {
      // Validate input
      evaluationDocument = validateXML(evaluationDetailsXmlData, workItemID);

      validateClaimID(claimID, workItemID);

      validateSuffixID(suffixID, workItemID);

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        qeUtilsProxy.logINFOMessage("suffixID::validated::" + suffixID);
      }

      // validate version , it should not be zero..
      validateVersion(evaluationDocument, workItemID);

      validateUserInfo(userInfoDoc, workItemID);

      // Populate Context
      contextObj.setClaimId(claimID);
      contextObj.setSuffixId(suffixID);
      contextObj.setUserInfoDoc(userInfoDoc);
      contextObj.setWorkItemId(workItemID);

      documentID = evaluationDAO.saveEvaluationWithClaim(contextObj,
          evaluationDocument);
      qeUtilsProxy.logINFOMessage("documentID is :" + documentID);
      
      if (documentID != null) {
    	  contextObj.setDocumentId(documentID);
    	  processEvalDocForReporting(evaluationDocument,contextObj);
      }
      
      responseMap.put(QuestionnaireEvaluationConstants.RESPONSE_KEY_DOCUMENTID,
          documentID);
      responseMap.put(QuestionnaireEvaluationConstants.CLAIM_ID,
          Long.valueOf(claimID));
      responseMap.put(QuestionnaireEvaluationConstants.SUFFIX_ID,
          Long.valueOf(suffixID));
      responseMap.put(QuestionnaireEvaluationConstants.WORKITEM_ID, workItemID);

      responseMap.put(QuestionnaireEvaluationConstants.USER_ID, userInfoDoc
          .getUserInfo().getUserID());

      if (evaluationDocument.getMitchellEvaluationDetails().getEvaluationInfo() != null) {
        responseMap.put(QuestionnaireEvaluationConstants.CLAIM_NUMBER,
            evaluationDocument.getMitchellEvaluationDetails()
                .getEvaluationInfo().getClaimNumber());
      }

      /**
       * if (evaluationDocument.getMitchellEvaluationDetails() != null &&
       * evaluationDocument
       * .getMitchellEvaluationDetails().getQuestionnaireArray() != null
       * && evaluationDocument
       * .getMitchellEvaluationDetails().getQuestionnaireArray().length >
       * 0 && evaluationDocument.getMitchellEvaluationDetails().
       * getQuestionnaireArray(0).getEvaluationVersion() != null) {
       **/
      responseMap.put(QuestionnaireEvaluationConstants.RESPONSE_KEY_VERSION,
          evaluationDocument.getMitchellEvaluationDetails()
              .getQuestionnaireArray(0).getEvaluationVersion().toString());
      // Log activity Success saving..
      /*
       * Start--adding condition to update the custom evaluation score and
       * comments fields for Questionnaire Evaluation
       */
      
      LOGGER.info("responseMap %%%%%%%%%"
              + responseMap);
      updateQuestionnareEvaluationCustomFields(evaluationDocument, responseMap);
      /*
       * End--adding condition to update the custom evaluation score and
       * comments fields for Questionnaire Evaluation
       */

      if (documentID != null) {
        // log event for save ...
        qeUtilsProxy.logAppEvent(responseMap, userInfoDoc,
            QuestionnaireEvaluationConstants.SAVE_EVALUATION_SUCCESS);
      }

    } catch (MitchellException me) {
      errorLogProxy.logError(me);
      throw me;
    } catch (Exception e) {

      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SAVING_DETAILS, CLASS_NAME,
          methodName, workItemID, EXCEPTION_DESC + e.getMessage(), e);
      errorLogProxy.logError(me);
      throw me;
    }

    LOGGER.exiting(CLASS_NAME, methodName);

    return responseMap;
  }

  /**processEvalDocForReporting method processes the Evaluation Document and post the document in questionnaire reporting queue 
 * @param evaluationDocument
 * @param contextObj
 * @throws MitchellException
 * @author Rohit.Pant
 */
  private void processEvalDocForReporting(MitchellEvaluationDetailsDocument evaluationDocument, QuestionnaireEvaluationContext contextObj) throws MitchellException {
	  try{
		  final String methodName = "processEvalDocForReporting";
		  if (LOGGER.isLoggable(Level.INFO)) {
           LOGGER.info(CLASS_NAME +methodName);
		  }
		  
		  final Long claimId = contextObj.getClaimId();
		  
		  if (claimId == null || claimId <=0){
			  if (LOGGER.isLoggable(Level.INFO)) {
				  LOGGER.info("  Questionnaire Reporting is not allowed for non Claim Data");
			  }
			  return ;
		  }
		  
		  final String claimNumber = ClaimServiceClient.getEjb().getClaim(claimId).getClaimNumber();
		  contextObj.setClaimNumber(claimNumber);
	
		  
		  //Check Is Reporting Allowed 
		  if ((systemConfigProxy.getSettingValue(QuestionnaireEvaluationConstants.IS_QUESTIONNAIRE_REPORTING_ALLOWED) == null) ||
           (!("Y".equalsIgnoreCase(systemConfigProxy.getSettingValue(QuestionnaireEvaluationConstants.IS_QUESTIONNAIRE_REPORTING_ALLOWED))))){
			  if (LOGGER.isLoggable(Level.INFO)) {
				  LOGGER.info(" Questionnaire Reporting is not allowed");
			  }
			  return;
		  }
		  
		  // Reporting Not allowed for Non Claim Evaluation
		  if (claimNumber == null || claimNumber.isEmpty()){
			  if (LOGGER.isLoggable(Level.INFO)) {
				  LOGGER.info("  Questionnaire Reporting is not allowed for non Claim Data");
			  }
			  return ;
		  }
		  
		  if (LOGGER.isLoggable(Level.INFO)) {
			  LOGGER.info("  Questionnaire Reporting is allowed");
		  }
		  
		  //Create Mitchell Envelope Document.
		  MitchellEnvelopeDocument meDoc = buildMEForQuestionnaireReporting(evaluationDocument,contextObj);
		  if (LOGGER.isLoggable(Level.INFO)) {
			  LOGGER.info(" MitchellEnvelopeDocument meDoc " +meDoc.toString());
		  }
		  
		  //Fetch event type from set file, Reporting Not allowed if its null or empty
		  String eventId = fetchQuestionnaireReportingEventId();
		  if (eventId == null || eventId.length() <= 0) {
			  return ;
		  }
		  
		  //Create MessagingContext 
		  MessagingContext msgContext = new MessagingContext(
			  Integer.parseInt(eventId), meDoc, contextObj.getUserInfoDoc(),
			  contextObj.getWorkItemId(), "");
		  
		  
		  //Use QuestionnaireEvaluationUtils to post message to message Bus
		  qeUtilsProxy.publishToMessageBus(msgContext);
		  
		  if (LOGGER.isLoggable(Level.INFO)) {
			  LOGGER.info("Questionnaire Reporting Event successfully published on message bus");
			  LOGGER.info("Exiting "+methodName);
		  }
	  }catch(Exception expPostMWM){
			final String desc = "Got Exception while posting Evaluation Document in Questionnaire Reporting Queue "+ evaluationDocument.toString();
			MitchellException mitchellException = new MitchellException(QuestionnaireEvaluationConstants.ERROR_POST_EVAL_IN_REPORTING,
				this.getClass().getName(), "postMcfXmlInComplianceReportingQueue", desc, expPostMWM);
			LOGGER.severe("Got Exception while posting Evaluation Document " + evaluationDocument.toString());
			mitchellException.setApplicationName(QuestionnaireEvaluationConstants.APPLICATION_NAME);
			mitchellException.setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);
			mitchellException.setWorkItemId(UUIDFactory.getInstance().getUUID());
		  
			errorLogProxy.logError(mitchellException);
	  }
  }

	/**buildMEForQuestionnaireReporting creates and returns the MitchellEnvelope Document
	 * @param evaluationDocument
	 * @param ctx
	 * @return
	 * @throws MitchellException
	 * @throws XmlException
	 * @author Rohit.Pant
	 */
	protected MitchellEnvelopeDocument buildMEForQuestionnaireReporting(
			MitchellEvaluationDetailsDocument evaluationDocument, QuestionnaireEvaluationContext ctx)
			throws MitchellException, XmlException {

		final String methodName = "buildMEForQuestionnaireReporting";
		  if (LOGGER.isLoggable(Level.INFO)) {
           LOGGER.info(CLASS_NAME +methodName);
		  }
		MitchellEnvelopeHelper mitchellEnvHelper = MitchellEnvelopeHelper.newInstance();

		mitchellEnvHelper.addNewEnvelopeBody("", evaluationDocument, "");
		mitchellEnvHelper.addEnvelopeContextNVPair(QuestionnaireEvaluationConstants.CLAIM_ID,
			String.valueOf(ctx.getClaimId()));
		mitchellEnvHelper.addEnvelopeContextNVPair(QuestionnaireEvaluationConstants.CLAIM_NUMBER,
			String.valueOf(ctx.getClaimNumber()));
		mitchellEnvHelper.addEnvelopeContextNVPair(QuestionnaireEvaluationConstants.EXPOSURE_ID,
			String.valueOf(ctx.getExposureId()));
		mitchellEnvHelper.addEnvelopeContextNVPair(QuestionnaireEvaluationConstants.SUFFIX_ID,
			String.valueOf(ctx.getSuffixId()));
		mitchellEnvHelper.addEnvelopeContextNVPair(QuestionnaireEvaluationConstants.EVALUATION_ID,
			String.valueOf(ctx.getEvaluationID()));

		if (LOGGER.isLoggable(Level.INFO)) {
			  LOGGER.info(" Exiting "+methodName);
		  }
		return mitchellEnvHelper.getDoc();
	}
	
	private String fetchQuestionnaireReportingEventId() {
		// Getting event Id from Custom setting...
		final String methodName = "fetchQuestionnaireReportingEventId";
		  if (LOGGER.isLoggable(Level.INFO)) {
           LOGGER.info(CLASS_NAME +methodName);
		  }
		String eventId = null;

		eventId = systemConfigProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_QUESTIONNAIRE_REPORTING_LISTENER_ID);
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Publishing EventId For Questionnaire Reporting:: " + eventId);
			LOGGER.info(" Exiting "+methodName);
		}
		return eventId;
	}
  /**
   * This method updates evaluation document from QuestionnaireEvaluationDAO.
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
   * @param workItemID
   *          <code>String</code> workItemID
   * @param tcn
   *          <code>long</code> tcn
   * 
   * 
   * @return responseMap
   * 
   * @throws MitchellException
   *           in case unable to save details
   */

  public Map updateEvaluation(long documentId, String evaluationType,
      String evaluationDetailsXmlData, UserInfoDocument userInfoDoc,
      long claimId, long suffixId, String workItemID, long tcn)
      throws MitchellException  {
    final String methodName = "updateEvaluation";
    LOGGER.entering(CLASS_NAME, methodName);

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      qeUtilsProxy.logINFOMessage("documentId==" + documentId);
      qeUtilsProxy.logINFOMessage("evaluationDetailsXmlData == "
          + evaluationDetailsXmlData);
    }
   
    QuestionnaireEvaluationContext contextObj = null;
    contextObj = new QuestionnaireEvaluationContext();

    Map responseMap = new HashMap();
    MitchellEvaluationDetailsDocument evaluationDocument = null;

    try {
      // Validate input
      validateDocumentID(documentId, workItemID);

      evaluationDocument = validateXML(evaluationDetailsXmlData, workItemID);

      // validate version , it should not be zero..
      validateVersion(evaluationDocument, workItemID);

      // validation for Reinspection EvaluationType

      validateUserInfo(userInfoDoc, workItemID);

      // Populate Context
      if (claimId > 0) {
        contextObj.setClaimId(claimId);
      }

      if (suffixId > 0) {
        contextObj.setSuffixId(suffixId);
      }

      contextObj.setUserInfoDoc(userInfoDoc);
      contextObj.setDocumentId(documentId);
      contextObj.setWorkItemId(workItemID);

      contextObj.setTcn(tcn);

      evaluationDAO.updateEvaluation(contextObj, evaluationDocument);

      qeUtilsProxy.logFINEMessage("workItemId: " + workItemID);
      //Update Workflow for Questionnaire Reporting
      processEvalDocForReporting(evaluationDocument,contextObj);

      responseMap.put(QuestionnaireEvaluationConstants.RESPONSE_KEY_DOCUMENTID,
          Long.valueOf(documentId));

      if (claimId > 0) {
        responseMap.put(QuestionnaireEvaluationConstants.CLAIM_ID,
            Long.valueOf(claimId));
      }

      if (suffixId > 0) {
        responseMap.put(QuestionnaireEvaluationConstants.SUFFIX_ID,
            Long.valueOf(suffixId));
      }

      responseMap.put(QuestionnaireEvaluationConstants.WORKITEM_ID, workItemID);

      responseMap.put(QuestionnaireEvaluationConstants.USER_ID, userInfoDoc
          .getUserInfo().getUserID());

      if (evaluationDocument.getMitchellEvaluationDetails().getEvaluationInfo() != null
          && evaluationDocument.getMitchellEvaluationDetails()
              .getEvaluationInfo().getClaimNumber() != null) {
        responseMap.put(QuestionnaireEvaluationConstants.CLAIM_NUMBER,
            evaluationDocument.getMitchellEvaluationDetails()
                .getEvaluationInfo().getClaimNumber());
      }
      // Log activity Success saving..

      // Start--update the custom evaluation score and comments fields for
      // Questionnaire Evaluation

      updateQuestionnareEvaluationCustomFields(evaluationDocument, responseMap);

      // End--update the custom evaluation score and comments fields for
      // Questionnaire Evaluation

      // log event for update ...
      qeUtilsProxy.logAppEvent(responseMap, userInfoDoc,
          QuestionnaireEvaluationConstants.UPDATE_EVALUATION_SUCCESS);

    } catch (MitchellException me) {
      errorLogProxy.logError(me);
      throw me;
    } catch (Exception e) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_UPDATING_DETAILS, CLASS_NAME,
          methodName, workItemID, EXCEPTION_DESC + e.getMessage(), e);
      errorLogProxy.logError(me);
      throw me;
    }

    LOGGER.exiting(CLASS_NAME, methodName);

    return responseMap;
  }

  /**
   * This method links claim to evaluation from QuestionnaireEvaluationDAO.
   * 
   * @param evaluationID
   *          <code>String</code> evaluationID
   * @param claimId
   *          <code>long</code> claimId
   * @param exposureId
   *          <code>long</code> exposureId
   * @param userInfo
   *          <code>UserInfoDocument</code> userInfo
   * @param claimNumber
   *          <code>String</code> claimNumber
   * @param workItemID
   *          <code>String</code> workItemID
   * 
   * @return linkStatus <code>int</code> linkStatus
   * 
   * @throws MitchellException
   *           in case unable to link details
   */
  public int linkQuestionnaireEvaluationToClaim(String evaluationID,
      long claimId, long exposureId, String claimNumber,
      UserInfoDocument userInfo, String workItemID)
      throws MitchellException {
    final String methodName = "linkQuestionnaireEvaluationToClaim";
    LOGGER.entering(CLASS_NAME, methodName);

    int linkStatus;

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      qeUtilsProxy.logINFOMessage("evaluationID==" + evaluationID);
      qeUtilsProxy.logINFOMessage("claimId==" + claimId);
    }

    QuestionnaireEvaluationContext contextObj = null;
    contextObj = new QuestionnaireEvaluationContext();

    Map logAppEventMap = new HashMap();

    try {
      validateUserInfo(userInfo, workItemID);

      // Populate Context
      if (claimId >= 0) {
        contextObj.setClaimId(claimId);
        logAppEventMap.put(QuestionnaireEvaluationConstants.CLAIM_ID,
            Long.valueOf(claimId));
      }

      if (!StringUtilities.isEmpty(evaluationID)) {
        contextObj.setEvaluationID(evaluationID);
        logAppEventMap.put(QuestionnaireEvaluationConstants.EVALUATION_ID,
            evaluationID);
      }

      if (exposureId >= 0) {
        contextObj.setExposureId(exposureId);
        logAppEventMap.put(QuestionnaireEvaluationConstants.EXPOSURE_ID,
            Long.valueOf(exposureId));
      }

      if (!StringUtilities.isEmpty(claimNumber)) {
        contextObj.setClaimNumber(claimNumber);
        logAppEventMap.put(QuestionnaireEvaluationConstants.CLAIM_NUMBER,
            claimNumber);
      }

      contextObj.setWorkItemId(workItemID);
      logAppEventMap.put(QuestionnaireEvaluationConstants.WORKITEM_ID,
          workItemID);

      contextObj.setUserInfoDoc(userInfo);

      linkStatus = evaluationDAO.linkQuestionnaireEvaluationToClaim(contextObj);
      logAppEventMap.put(QuestionnaireEvaluationConstants.LINK_STATUS,
          Integer.toString(linkStatus));

      //Reporting Workflow : Test Page
      if (linkStatus == 0){
    	  MitchellEvaluationDetailsDocument evaluationDocument = null;
    	  try {
    		  final Long documentId = getDocumentIdForEvaluation(contextObj);
    		  contextObj.setDocumentId(documentId);
    		  evaluationDocument = getEvaluationDocument(contextObj);
    		  contextObj.setSuffixId(contextObj.getExposureId());
    		  processEvalDocForReporting(evaluationDocument, contextObj);
    	  }catch(Exception expPostMWM){
    		  final String desc = "Got Exception while posting Evaluation Document in Questionnaire Reporting Queue "+ evaluationDocument.toString();
  			MitchellException mitchellException = new MitchellException(QuestionnaireEvaluationConstants.ERROR_POST_EVAL_IN_REPORTING,
  				this.getClass().getName(), "postMcfXmlInComplianceReportingQueue", desc, expPostMWM);
  			LOGGER.severe("Got Exception while posting Evaluation Document " + evaluationDocument.toString());
  			mitchellException.setApplicationName(QuestionnaireEvaluationConstants.APPLICATION_NAME);
  			mitchellException.setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);
  			mitchellException.setWorkItemId(UUIDFactory.getInstance().getUUID());
  		  
  			errorLogProxy.logError(mitchellException);
    	  }
    	  
      }
      // linkStatus as 0 indicates a success
      qeUtilsProxy
          .logAppEvent(
              logAppEventMap,
              userInfo,
              linkStatus == 0 ? QuestionnaireEvaluationConstants.LINKING_CLAIM_SUCCESS
                  : QuestionnaireEvaluationConstants.LINKING_CLAIM_STATUS);

    } catch (MitchellException me) {
      errorLogProxy.logError(me);
      throw me;
    } catch (Exception e) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_LINKING_CLAIM, CLASS_NAME,
          methodName, workItemID, EXCEPTION_DESC + e.getMessage(), e);
      errorLogProxy.logError(me);
      throw me;
    }

    LOGGER.exiting(CLASS_NAME, methodName);

    return linkStatus;

  }
  
  

  /** getDocumentIdForEvaluation return the documentId for evaluationId
 * @param contextObj
 * @return documentId
 * @throws MitchellException
 * @author Rohit.Pant
 */
private Long getDocumentIdForEvaluation(QuestionnaireEvaluationContext contextObj) throws MitchellException {
	  final  String coCode = contextObj.getUserInfoDoc().getUserInfo().getOrgCode();
     return evaluationDAO.getEstimatePackageServiceProxy().getQuestionnaireEvaluationDocIdByRefCode(coCode, contextObj.getEvaluationID());
    
}

/** getEvaluationDocument will return the MitchellEvaluationDetailsDocument based on documentId
 * @param contextObj
 * @return
 * @throws MitchellException
 * @throws IOException
 * @throws XmlException
 * @author Rohit.Pant
 */
private MitchellEvaluationDetailsDocument getEvaluationDocument(QuestionnaireEvaluationContext contextObj) throws MitchellException, IOException, XmlException {
	  final Long documentId= contextObj.getDocumentId();
	  final Long docStoreId = evaluationDAO.getEstimatePackageServiceProxy().getDocumentStoreIdByDocId(documentId, null);
	  final GetDocResponse docResponse = evaluationDAO.getDocStoreServiceProxy().getDocument(docStoreId.longValue());
	  final String sourceFileName = docResponse.getfilenameondisk();
	  final String evaluationDetailsXmlNASData = evaluationDAO.getEvaluationDocumentOnNAS(sourceFileName);
     return MitchellEvaluationDetailsDocument.Factory.parse(evaluationDetailsXmlNASData);
}

/**
   * This method deletes the evaluation.
   * 
   * @param coCode
   *          <code>String</code>
   * 
   * @param evaluationID
   *          <code>Long</code> evaluationID
   * @param workItemID
   *          <code>String</code> workItemID
   * 
   * @return responseMap
   * 
   * @throws MitchellException
   *           in case unable to delete details
   */

  public Map deleteEvaluation(UserInfoDocument userInfoDoc,
      String evaluationID, String workItemID)
      throws MitchellException  {
    final String methodName = "deleteEvaluation";
    LOGGER.entering(CLASS_NAME, methodName);
    String coCode = null;

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      qeUtilsProxy.logINFOMessage("evaluationID: " + evaluationID);
      qeUtilsProxy.logINFOMessage("workItemId: " + workItemID);
    }

    Map responseMap = new HashMap();

    if (userInfoDoc != null && userInfoDoc.getUserInfo() != null) {
      coCode = userInfoDoc.getUserInfo().getOrgCode();
    }

    try {

      validateEvaluationID(evaluationID, workItemID);

      evaluationDAO.deleteEvaluation(coCode, evaluationID, workItemID);

      responseMap.put(QuestionnaireEvaluationConstants.WORKITEM_ID, workItemID);
      responseMap.put(QuestionnaireEvaluationConstants.EVALUATION_ID,
          evaluationID);
      // Log activity Success saving..
      // log event for delete ...
      qeUtilsProxy.logAppEvent(responseMap, userInfoDoc,
          QuestionnaireEvaluationConstants.DELETE_EVALUATION_SUCCESS);

    } catch (MitchellException me) {
      errorLogProxy.logError(me);
      throw me;
    } catch (Exception e) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_DELETING_DETAILS, CLASS_NAME,
          methodName, workItemID, EXCEPTION_DESC + e.getMessage(), e);
      errorLogProxy.logError(me);
      throw me;
    }

    LOGGER.exiting(CLASS_NAME, methodName);
    return responseMap;
  }

  /**
   * This method validates the XML against schema.
   * 
   * @param evaluationDetailsXmlData
   *          <code>String</code> evaluationDetailsXmlData
   * 
   * @throws MitchellException
   *           in case evaluationDetails is invalid
   * 
   */

  private MitchellEvaluationDetailsDocument validateXML(
      String evaluationDetailsXmlData, String workItemId)
      throws MitchellException  {
    final String methodName = "validateXML";
    LOGGER.entering(CLASS_NAME, methodName);
    MitchellEvaluationDetailsDocument evaluationDocument = null;
    boolean valid = false;
    try {

      evaluationDocument = MitchellEvaluationDetailsDocument.Factory
          .parse(evaluationDetailsXmlData);
      valid = evaluationDocument.validate();

      // Added this validation as review comment
      // Check if the Questionnaire tag is present
      if (valid && (evaluationDocument.getMitchellEvaluationDetails()
            .getQuestionnaireArray() == null
            || evaluationDocument.getMitchellEvaluationDetails()
                  .getQuestionnaireArray().length <= 0)) {
          valid = false;
        }
    
      if (!valid) {
       throw new MitchellException(
            QuestionnaireEvaluationConstants.ERROR_PARSING_DOCUMENT,
            CLASS_NAME, methodName, workItemId,
            QuestionnaireEvaluationConstants.ERROR_PARSING_DOCUMENT + "\n");
      }

    } catch (XmlException e) {
      List errorList = new ArrayList();
      errorList.addAll(e.getErrors());
      Iterator iterator = errorList.iterator();
      while (iterator.hasNext()) {
        LOGGER.fine("error at node=>" + iterator.next());
      }
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_PARSING_DOCUMENT, CLASS_NAME,
          methodName, workItemId, EXCEPTION_DESC + e.getMessage(), e);
    }

    LOGGER.exiting(CLASS_NAME, methodName);

    return evaluationDocument;
  }

  /**
   * This method validates evaluationID.
   * 
   * @param evaluationID
   *          <code>Long</code> evaluationID
   * 
   * @throws MitchellException
   *           in case evaluationID is invalid
   */

  private void validateEvaluationID(String evaluationID, String workItemId)
      throws MitchellException {
    final String methodName = "validateEvaluationID";
    LOGGER.entering(CLASS_NAME, methodName);
    if (StringUtilities.isEmpty(evaluationID)) {
      qeUtilsProxy.logSEVEREMessage("evaluationID is invalid \n");
      throw new MitchellException(
          QuestionnaireEvaluationConstants.INVALID_EVALUATION_ID, CLASS_NAME,
          methodName, workItemId,
          QuestionnaireEvaluationConstants.INVALID_EVALUATION_ID + "\n");
    }
  }

  /**
   * This method validates claimID.
   * 
   * @param claimID
   *          <code>Long</code> claimID
   * 
   * @throws MitchellException
   *           in case claimID is invalid
   */

  private void validateClaimID(long claimID, String workItemId)
      throws MitchellException  {
    final String methodName = "validateClaimID";
    LOGGER.entering(CLASS_NAME, methodName);
    if (claimID <= 0) {
      qeUtilsProxy.logSEVEREMessage("claimID is invalid \n");
      throw new MitchellException(
          QuestionnaireEvaluationConstants.INVALID_CLAIM_ID, CLASS_NAME,
          methodName, workItemId,
          QuestionnaireEvaluationConstants.INVALID_CLAIM_ID + "\n");
    }
    LOGGER.exiting(CLASS_NAME, methodName);
  }

  /**
   * This method validates suffixID.
   * 
   * @param suffixID
   *          <code>Long</code> suffixID
   * 
   * @throws MitchellException
   *           in case suffixID is invalid
   */

  private void validateSuffixID(long suffixID, String workItemId)
      throws MitchellException  {
    final String methodName = "validateSuffixID";
    LOGGER.entering(CLASS_NAME, methodName);

    if (suffixID <= 0) {
      qeUtilsProxy.logSEVEREMessage("suffixID is invalid \n");
      throw new MitchellException(
          QuestionnaireEvaluationConstants.INVALID_SUFFIX_ID, CLASS_NAME,
          methodName, workItemId,
          QuestionnaireEvaluationConstants.INVALID_SUFFIX_ID + "\n");

    }
    LOGGER.exiting(CLASS_NAME, methodName);
  }

  /**
   * This method validates version.
   * 
   * @param evaluationDoc
   *          <code>MitchellEvaluationDetailsDocument</code> evaluationDoc
   * 
   * @throws MitchellException
   *           in case version is invalid
   */
  private void validateVersion(MitchellEvaluationDetailsDocument evaluationDoc,
      String workItemId)    throws MitchellException {
    final String methodName = "validateVersion";
    LOGGER.entering(CLASS_NAME, methodName);

    if (evaluationDoc.getMitchellEvaluationDetails().getQuestionnaireArray(0)
        .getEvaluationVersion().floatValue() < QuestionnaireEvaluationConstants.DEFAULT_VERSION) {
      qeUtilsProxy.logSEVEREMessage("Version is invalid \n");
      throw new MitchellException(
          QuestionnaireEvaluationConstants.GENERIC_ERROR, CLASS_NAME,
          methodName, workItemId,
          QuestionnaireEvaluationConstants.GENERIC_ERROR + "\n");
    }

    LOGGER.exiting(CLASS_NAME, methodName);
  }

  /**
   * Method to check the valid EvaluationType from SET file.
   * 
   * @param evaluationType
   *          <code>String</code> evaluationType
   * 
   * @throws MitchellException
   *           in case EvaluationType is not in set file
   * 
   */
  private void validateEvaluationType(String evaluationType, String workItemId)
      throws MitchellException {

    final String methodName = "validateEvaluationType";
    LOGGER.entering(CLASS_NAME, methodName);
    String evalTypes = systemConfigProxy
        .getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE);
    boolean flag = false;

    if (!StringUtilities.isEmpty(evalTypes)) {

      StringTokenizer st = new StringTokenizer(evalTypes,
          QuestionnaireEvaluationConstants.PIPE_OPERATOR);

      while (st.hasMoreTokens()) {
        String evalType = (String) st.nextToken();

        if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
          qeUtilsProxy.logFINEMessage("evalTypes from set file: " + evalType);
        }

        if (evaluationType != null && evaluationType.equalsIgnoreCase(evalType)) {
          flag = true;
        }

        if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
          LOGGER.fine("flag=>" + flag);
        }

      }
    }

    if (!flag) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.INVALID_EVALUATION_TYPE, CLASS_NAME,
          methodName, workItemId,
          QuestionnaireEvaluationConstants.INVALID_EVALUATION_TYPE + "\n");
    }

    LOGGER.exiting(CLASS_NAME, methodName);

  }

  /**
   * This method validates userInfoDoc for not being null.
   * 
   * @param userInfoDoc
   *          <code>UserInfoDocument</code>
   * @throws MitchellException
   *           in case UserInfo is null
   */
  private void validateUserInfo(UserInfoDocument userInfoDoc, String workItemId)
      throws MitchellException {
    final String methodName = "validateUserInfo";
    LOGGER.entering(CLASS_NAME, methodName);
    if (userInfoDoc == null) {
      qeUtilsProxy.logSEVEREMessage("userInfoDoc is null \n");
      throw new MitchellException(
          QuestionnaireEvaluationConstants.GENERIC_ERROR, CLASS_NAME,
          methodName, workItemId,
          QuestionnaireEvaluationConstants.GENERIC_ERROR + "\n");

    }
    LOGGER.exiting(CLASS_NAME, methodName);
  }

  /**
   * This method validates documentID.
   * 
   * @param documentID
   *          <code>long</code> documentID
   * 
   * @throws MitchellException
   *           in case documentID is invalid
   */

  private void validateDocumentID(long documentID, String workItemId)
      throws MitchellException  {
    final String methodName = "validatedocumentID";
    LOGGER.entering(CLASS_NAME, methodName);
    if (documentID <= 0) {
      qeUtilsProxy.logSEVEREMessage("documentID is invalid \n");
      throw new MitchellException(
          QuestionnaireEvaluationConstants.INVALID_DOCUMENT_ID, CLASS_NAME,
          methodName, workItemId,
          QuestionnaireEvaluationConstants.INVALID_DOCUMENT_ID + "\n");
    }

    LOGGER.exiting(CLASS_NAME, methodName);
  }

  /**
   * This method is taking the xml file path and create the document and
   * genrate the xml doc and store save the claim to saved evaluation document
   * in Map sfrom QuestionnaireEvaluationDAO.
   * 
   * @param msg
   *          <code>String</code> path of the file
   * @return responseMap
   * 
   * @throws MitchellException
   *           in case unable to save details
   */
  public Map saveEvaluationAsync(String msg)
      throws MitchellException  {
    final String methodName = "saveEvaluationFromContingencyPage";
    LOGGER.entering(CLASS_NAME, methodName);
    UserInfoDocument userInfoDoc = null;
    MitchellEvaluationDetailsDocument evaluationDetailsDocumentNAS = null;
    String evaluationDetailsXmlNASData = null;
    Map responseMap = new HashMap();
    Long documentID = null;
    QuestionnaireEvaluationContext contextObj = null;

    // getting the XML file form NAS location
    if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
      qeUtilsProxy.logINFOMessage("sourceFileName == " + msg);
    }

    String workItemId = null;
    MitchellEvaluationDetailsDocument evaluationDocument = null;
    // Read the file contents
    try {

      // parsing the MEGEN input FileEvent xml..
      FileEventDocument document = FileEventDocument.Factory.parse(msg);

      // valid against schema
            String sourceFile = null;
        if (document != null && document.getFileEvent() != null) {
          sourceFile = document.getFileEvent().getTargetFile();
        }
        if (sourceFile != null) {
          if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
            qeUtilsProxy.logINFOMessage("Contingency input file name==  "
                + sourceFile);
          }

          evaluationDetailsXmlNASData = evaluationDAO
              .getEvaluationDocumentOnNAS(sourceFile);
        }
    } catch (Exception e) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_COPYING_TO_NAS, CLASS_NAME,
          methodName, workItemId, EXCEPTION_DESC + e.getMessage(), e);
      errorLogProxy.logError(me);
      throw me;
    }

    // validate and save the xml file in DB
    try {

      if (evaluationDetailsXmlNASData != null
          && evaluationDetailsXmlNASData.length() > 0) {
        // validate the xml data
        evaluationDocument = validateXML(evaluationDetailsXmlNASData,
            workItemId);
        // create the document of the xml string
        evaluationDetailsDocumentNAS = MitchellEvaluationDetailsDocument.Factory
            .parse(evaluationDetailsXmlNASData);
        // validate the version of document
        validateVersion(evaluationDetailsDocumentNAS, workItemId);
        String evaluationID = evaluationDetailsDocumentNAS
            .getMitchellEvaluationDetails().getEvaluationInfo()
            .getEvaluationID();
        validateEvaluationID(evaluationID, workItemId);
        // validate evaluationType, should be in set file
        validateEvaluationType(evaluationDetailsDocumentNAS
            .getMitchellEvaluationDetails().getEvaluationInfo()
            .getEvaluationType(), workItemId);
        String coCode = evaluationDetailsDocumentNAS
            .getMitchellEvaluationDetails().getEvaluationInfo().getCoCode();
        // creating userInfo document
        userInfoDoc = getClaimSystemUserInfoDoc(coCode);
        contextObj = new QuestionnaireEvaluationContext();
        // validate userInfoDocument, should not be null
        validateUserInfo(userInfoDoc, workItemId);
        // populate context object..
        contextObj.setEvaluationID(evaluationID);
        contextObj.setUserInfoDoc(userInfoDoc);
        contextObj.setWorkItemId(evaluationID);
        documentID = evaluationDAO.saveEvaluation(contextObj,
            evaluationDocument);

        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          qeUtilsProxy.logINFOMessage(CLASS_NAME + methodName + "documentID=>"
              + documentID);
        }

        responseMap.put(
            QuestionnaireEvaluationConstants.RESPONSE_KEY_DOCUMENTID,
            documentID);
        responseMap.put(QuestionnaireEvaluationConstants.EVALUATION_ID,
            evaluationID);
        responseMap.put(QuestionnaireEvaluationConstants.WORKITEM_ID,
            workItemId);
        responseMap.put(QuestionnaireEvaluationConstants.USER_ID, userInfoDoc
            .getUserInfo().getUserID());
        if (evaluationDocument.getMitchellEvaluationDetails()
            .getEvaluationInfo() != null) {
          responseMap.put(QuestionnaireEvaluationConstants.CLAIM_NUMBER,
              evaluationDocument.getMitchellEvaluationDetails()
                  .getEvaluationInfo().getClaimNumber());

        }
        responseMap.put(QuestionnaireEvaluationConstants.RESPONSE_KEY_VERSION,
            evaluationDocument.getMitchellEvaluationDetails()
                .getQuestionnaireArray(0).getEvaluationVersion().toString());
        // Log activity Success saving..
        if (documentID != null) {
          // log event for save ...
          qeUtilsProxy.logAppEvent(responseMap, userInfoDoc,
              QuestionnaireEvaluationConstants.SAVE_EVALUATION_SUCCESS);
        }
      } else {
        // evaluation data from NAS is null
        qeUtilsProxy.logINFOMessage("No valid evaluation found ");
      }
    } catch (MitchellException me) {
      errorLogProxy.logError(me);
      throw me;
    } catch (Exception e) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_SAVING_DETAILS, CLASS_NAME,
          methodName, workItemId, EXCEPTION_DESC + e.getMessage(), e);
      errorLogProxy.logError(me);
      throw me;
    }
    LOGGER.exiting(CLASS_NAME, methodName);
    return responseMap;
  }

  /**
   * This method is used to genrated the UserInfoDocument object.
   * 
   * @param coCode
   *          String coCode
   * @return UserInfoDocument UserInfoDocument
   */
  private UserInfoDocument getClaimSystemUserInfoDoc(String coCode)  {
    // Create a System User Info Document
    // This is needed to call certain claim service methods
    UserInfoDocument doc = UserInfoDocument.Factory.newInstance();
    // Extractting the Company Code information from the Message.
    UserInfoType userInfo = doc.addNewUserInfo();
    userInfo.setUserID("System");
    userInfo.setFirstName("System");
    userInfo.setLastName("User");
    userInfo.setOrgCode(coCode);
    userInfo.setOrgID("349853");
    UserHierType userHier = userInfo.addNewUserHier();
    NodeType node = userHier.addNewHierNode();
    node.setName("PROGRESSIVE INSURANCE");
    node.setLevel("Company");
    node.setCode(coCode);
    node.setID("72586");
    String[] permissions = { "CMALL", "CMCRC", "CMEDC", "CMEDE", "CMCRD" };
    if (permissions != null) {
      for (int i = 0; i < permissions.length; i++) {
        userInfo.addAppCode(permissions[i]);
      }
    }
    return doc;
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
    final String methodName = "associateQuestionnaireToClaimSuffix";
    LOGGER.entering(CLASS_NAME, methodName);
    String workItemId = null;
    try {
      validateUserInfo(userInfoDoc, workItemId);
      evaluationDAO.associateQuestionnaireToClaimSuffix(qstnrClaimLinkDoc,
          userInfoDoc);
    } catch (MitchellException me) {
      errorLogProxy.logError(me);
      throw me;
    } catch (Exception e) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_ASSOCIATING_QTNR_TO_CLAIMSUFFIX,
          CLASS_NAME, methodName, workItemId, EXCEPTION_DESC + e.getMessage(), e);
      errorLogProxy.logError(me);
      throw me;
    }
    LOGGER.exiting(CLASS_NAME, methodName);

  }

  public QuestionnaireEvaluationDAOProxy getEvaluationDAO()  {
    return evaluationDAO;
  }

  /**
   * @param evaluationDocument
   * @param responseMap
   * @throws MitchellException
   *           updates the questionnaire evaluation custom score and
   *           comments.
   */
  private void updateQuestionnareEvaluationCustomFields(
      MitchellEvaluationDetailsDocument evaluationDocument, Map responseMap)
      throws MitchellException {

    final String methodName = "updateQuestionnareEvaluationCustomFields";
    LOGGER.entering(CLASS_NAME, methodName);

    ResultType resultType = null;
    EvaluationDetailsType evaluationDetailsType = evaluationDocument
        .getMitchellEvaluationDetails();
    String companyCode = evaluationDetailsType.getEvaluationInfo().getCoCode();

    if (evaluationDetailsType.getQuestionnaireArray() != null
        && evaluationDetailsType.getQuestionnaireArray().length > 0) {
      resultType = evaluationDetailsType.getQuestionnaireArray(0).getResult();
    }

    if (resultType != null && resultType.getEvaluationScoreOverride() != null) {
      evaluationDAO.updateCustomEvaluationFields(responseMap, companyCode,
          resultType);
    }
    LOGGER.exiting(CLASS_NAME, methodName);
  }

  /**
   * @param evaluationDocument
   * @param responseMap
   * @throws MitchellException
   *           updates the questionnaire evaluation custom score and
   *           comments.
   */
  public void updateSetQuestionnareReviewDocId(String coCode, long claimId,
      long suffixId, long questinnaireId, long setId, String reviewType,
      long questEvalDocId, String userId)
      throws MitchellException {

    final String methodName = "updateSetQuestionnareReviewDocId";

    LOGGER.entering(CLASS_NAME, methodName);
    try {

      evaluationDAO.updateSetQuestionnareReviewDocId(coCode, claimId, suffixId,
          questinnaireId, setId, reviewType, questEvalDocId, userId);

    } catch (MitchellException me) {
      errorLogProxy.logError(me);
      throw me;
    } catch (Exception e) {
      MitchellException me = new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_ASSOCIATING_QTNR_TO_CLAIMSUFFIX,
          CLASS_NAME, methodName,  Long.toString(claimId)
              + QuestionnaireEvaluationConstants.HYPHEN + suffixId,
          EXCEPTION_DESC + e.getMessage(), e);

      errorLogProxy.logError(me);
      throw me;
    }

    LOGGER.exiting(CLASS_NAME, methodName);
  }
  
  
  /**
   * This API is used to save the evaluation details after each single question answered by the user and in return,
   * returns the next question that needs to be answered.
   * @param QuestionnaireRqRsDTO : request dto which contains information of the answered question, 
   * QuestionnaireDetails : contains information of the questionnaire, 
   * Question : details of the answered question, Answer : details of the selected answer
   * @return QuestionnaireRqRsDTO : Response string will contain the current question details with evaluation doc id populated in context dto.
   * @throws MitchellException
   */
  public QuestionnaireRqRsDTO saveOrUpdateQtnnreEvaluation(QuestionnaireRqRsDTO requestDto , QuestionnaireDetails  questionnaireDetails, Question question , Answer selectedAns)
			throws MitchellException {
        String methodName = "saveOrUpdateQtnnreEvaluation";
	  	LOGGER.entering(CLASS_NAME, methodName); 
		Map<Object, Object> saveResponseMap = null;
		String userId = null;
		ContextDTO contextDto =null;
		long docId = 0L;
		try {
			contextDto = requestDto.getContextDto();
			docId = contextDto.getDocumentId();
			String evaluationId = qeUtilsProxy.generateEvaluationID();
			String workItemId = evaluationId;
			String coCd = contextDto.getCoCode();
			String evaluationType = QuestionnaireEvaluationConstants.EVALUATION_TYPE;
			
		userId = customSettingProxy.getCustomValue(coCd,
				coCd, QuestionnaireEvaluationConstants.CSET_CARRIER_GROUP_SETTING,
				QuestionnaireEvaluationConstants.CSET_SYSTEM_SETTING_USER_ID);
		UserInfoDocument userInfoDoc = userInfoProxy.getUserInfo(coCd, userId);
		if (docId <= 0) {
			MitchellEvaluationDetailsDocument evaluationDetailsDoc = createEvaluationDetailsDoc(coCd, evaluationId, userId, evaluationType,questionnaireDetails ,question, selectedAns);
			saveResponseMap = this.saveEvaluation(evaluationId, evaluationType,
					evaluationDetailsDoc.toString(), userInfoDoc, workItemId);
			docId = (Long) saveResponseMap
					.get(QuestionnaireEvaluationConstants.RESPONSE_KEY_DOCUMENTID);
		} else {
         updateEvaluationDetails(docId, question,  selectedAns,
					userInfoDoc, evaluationType, workItemId);
		}
		}catch(MitchellException me) {
			errorLogProxy.logError(me);
			throw me;
		}catch (Exception e) {
			MitchellException me = new MitchellException(
			          QuestionnaireEvaluationConstants.ERROR_SAVING_QNTNRE_EVAL,
			          CLASS_NAME, methodName, "" +  
                   EXCEPTION_DESC + e.getMessage(), e);
			
			      errorLogProxy.logError(me);
			      throw me;
		}
      contextDto.setDocumentId(docId);
      requestDto.setContextDto(contextDto);
	  LOGGER.exiting(CLASS_NAME, methodName);
      return requestDto;
	}
  
  /**
   * This method generates EvaluationDetails document that needs to be saved.
   * @param currentQuestionnr
   * @param coCd
   * @param evaluationId
   * @param userId
   * @param evaluationType
   * @return MitchellEvaluationDetailsDocument
   */
	private MitchellEvaluationDetailsDocument createEvaluationDetailsDoc(String coCd, String evaluationId,
			String userId, String evaluationType ,  QuestionnaireDetails  questionnaireDetails ,Question question, Answer selectedAns) {
		String methodName = "createEvaluationDetailsDoc";
		LOGGER.entering(CLASS_NAME, methodName);
		MitchellEvaluationDetailsDocument evaluationDetailsDoc = null;
		evaluationDetailsDoc = MitchellEvaluationDetailsDocument.Factory
				.newInstance();
		EvaluationDetailsType evaluationDetailsType = evaluationDetailsDoc
				.addNewMitchellEvaluationDetails();
		EvaluationInfoType evaluationInfoType = evaluationDetailsType
				.addNewEvaluationInfo();
		evaluationInfoType.setCoCode(coCd);
		evaluationInfoType.setEvaluationID(evaluationId);
		evaluationInfoType.setEvaluationType(evaluationType);
		if (QuestionnaireEvaluationConstants.SCORING_TYPE_PERCENTAGE.equalsIgnoreCase(questionnaireDetails.getScoringType())) {
         evaluationInfoType.setScoringType(ScoreType.PERCENTAGE);
		} else if(QuestionnaireEvaluationConstants.SCORING_TYPE_POINT.equalsIgnoreCase(questionnaireDetails.getScoringType())){
         evaluationInfoType.setScoringType(ScoreType.POINTS);
		}else{
			evaluationInfoType.setScoringType(ScoreType.NONE);
		}
		QuestionnaireType questionnaireType = evaluationDetailsType
				.addNewQuestionnaire();
		EvaluationUserType evaluationUserType = questionnaireType
				.addNewEvaluationUserDetails();
		QuestionListType questionListType = questionnaireType
				.addNewQuestionsList();
		evaluationUserType.setCoCode(coCd);
		evaluationUserType.setUserId(userId);
		QuestionType questionType = questionListType.addNewQuestion();
		
		setCurrentQustn(questionType, question, selectedAns);
		Calendar calendar = Calendar.getInstance();
		questionnaireType.setCreateDateTimeStamp(calendar);
		questionnaireType.setEvaluationVersion(new BigDecimal(QuestionnaireEvaluationConstants.EVALUATION_VERSION));
		questionnaireType.setQuestionnaireId(String.valueOf(questionnaireDetails
				.getQustnnreId()));
		questionnaireType.setQuestionnaireVersion(new BigDecimal(String
				.valueOf(questionnaireDetails.getQustnnreVersion())));
		LOGGER.exiting(CLASS_NAME, methodName);
		return evaluationDetailsDoc;
	}
	

	
	/**
	 * This method is called to update existing questonniare evaluation with the details of the latest question answered by the user. 
	 * It also returns the next question that is sent to user to answer.
	 * @param inputQustnQustnnr
	 * @param docId
	 * @param userInfoDoc
	 * @param evaluationType
	 * @param workItemId
	 * @return requestDto
	 * @throws MitchellException
	 */
   public void updateEvaluationDetails(
			long docId, Question question, Answer selectedAnswer, UserInfoDocument userInfoDoc, String evaluationType,
			String workItemId) throws MitchellException {
		String methodName = "updateEvalAndGetNxtQustnnr";
		LOGGER.entering(CLASS_NAME, methodName);		
		MitchellEvaluationDetailsDocument evaluationDetailsDocumentNAS = null;
		MitchellEvaluationDetailsDocument evaluationDetailsDoc = null;
		String evaluationDetailsXml = null;
		
		try {
			long ancstrQstnnrQustnId = question.getAncstrQustnnrQustnId();
			evaluationDetailsDocumentNAS = getEvaluationDocFromNAS(docId);
			 if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info("partially saved evaluationDetailsDocumentNAS   "
						+ evaluationDetailsDocumentNAS);
			}
				QuestionnaireType[] questionnaireType = evaluationDetailsDocumentNAS
						.getMitchellEvaluationDetails().getQuestionnaireArray();
				QuestionListType savedQustnListType = questionnaireType[questionnaireType.length - 1]
						.getQuestionsList();
				QuestionType[] questionType = savedQustnListType
						.getQuestionArray();
				QuestionType currentQustn = null;
				if (ancstrQstnnrQustnId == 0) {
					currentQustn = savedQustnListType.addNewQuestion();
					setCurrentQustn(currentQustn, question, selectedAnswer);
				} else {
					updateAsRelatdQustn(questionType , ancstrQstnnrQustnId , question,	selectedAnswer);
					}
				evaluationDetailsDoc = createUpdatedEvaluationDetailsDoc(evaluationDetailsDocumentNAS);
				evaluationDetailsXml = evaluationDetailsDoc.toString();
				 if (LOGGER.isLoggable(Level.INFO)) {
					LOGGER.info("updated  evaluationDetailsXml   "
							+ evaluationDetailsXml);
				}
         updateEvaluation(docId, evaluationType,
						evaluationDetailsXml, userInfoDoc, 0L, 0L, workItemId,
						0L);
		}catch (MitchellException me) {
			throw me;
		} catch(Exception e){
			 MitchellException me = new MitchellException(
			          QuestionnaireEvaluationConstants.ERROR_SAVING_QNTNRE_EVAL,
			          CLASS_NAME, methodName,  
                  EXCEPTION_DESC + e.getMessage(), e);
			 errorLogProxy.logError(me);
			      throw me;
		}
		LOGGER.exiting(CLASS_NAME, methodName);
	
	}
 /**
  * This method is used to update the current question as related question in the document.
  * 
  * */
   private void updateAsRelatdQustn(QuestionType[] questionType, long ancstrQstnnrQustnId , Question question, Answer selectedAnswer){

	   boolean flag = true;
	   QuestionType currentQustn = null;
		QuestionType savedParentQustn = questionType[questionType.length - 1];
		QuestionType[] relatedQustnArray = null;
		while (flag) {
			if (ancstrQstnnrQustnId == savedParentQustn
					.getQuestionScoreID().longValue()) {
				currentQustn = savedParentQustn
						.addNewRelatedQuestions();
				setCurrentQustn(currentQustn, question,
						selectedAnswer);
				flag = false;
			} else if(savedParentQustn.getRelatedQuestionsArray().length !=0){
				relatedQustnArray = savedParentQustn
						.getRelatedQuestionsArray();
           savedParentQustn = relatedQustnArray[relatedQustnArray.length - 1];
			} else {
					flag = false;
			}

			}
		}

	/**
	 * This method is used to create evaluation details document for update workflow of save evaluation.
	 * For updating the latest answered question in the evalaution details, first fetch the existing evaluation document.
	 * It contains multiple Questionnaire tags.
	 * Fetch the latest Questionnaire tag from this document. Update the latest answered question in this Questionnaire.
	 * Create a MItchellEvaluationDetailsDOcument for this updated Questionnaire and then called existing API for update evaluation.
	 * @param evaluationDetailsDocumentNAS
	 * @return MitchellEvaluationDetailsDocument
	 * @throws MitchellException
	 */
 private MitchellEvaluationDetailsDocument createUpdatedEvaluationDetailsDoc(MitchellEvaluationDetailsDocument evaluationDetailsDocumentNAS) throws MitchellException{
		String methodName = "createUpdatedEvaluationDetailsDoc";
		LOGGER.entering(CLASS_NAME, methodName);	
	 MitchellEvaluationDetailsDocument evaluationDetailsDoc = null;
	 evaluationDetailsDoc = MitchellEvaluationDetailsDocument.Factory.newInstance();
	 EvaluationDetailsType newEvaluationDetailsType  =  evaluationDetailsDoc.addNewMitchellEvaluationDetails();
	 EvaluationDetailsType  updatedEvaluationDetailsType  =evaluationDetailsDocumentNAS.getMitchellEvaluationDetails();
	 newEvaluationDetailsType.addNewEvaluationInfo();
	 newEvaluationDetailsType.addNewQuestionnaire();
	 newEvaluationDetailsType.setEvaluationInfo(updatedEvaluationDetailsType.getEvaluationInfo());
	 int len = updatedEvaluationDetailsType.getQuestionnaireArray().length;
	 newEvaluationDetailsType.setQuestionnaireArray(0, updatedEvaluationDetailsType.getQuestionnaireArray(len-1));
	 LOGGER.exiting(CLASS_NAME, methodName);
	
	 return evaluationDetailsDoc;
 }
 
  /**
  * This method sets the latest answered question object
  * @param currentQustn
  * @param question
  * @param selectedAns
  */
private void setCurrentQustn(QuestionType currentQustn ,Question question , Answer selectedAns ){
	 String methodName = "setCurrentQustn";
	 LOGGER.entering(CLASS_NAME, methodName);	
		 if(question.getSysQustnType() != null){
			 currentQustn.setQuestionParamName(question.getSysQustnType());
		 }
	 	currentQustn.setID(new BigInteger(String.valueOf(question.getQustnId())));
		currentQustn.setText(question.getQustnText());
		currentQustn.setQuestionScoreID(new BigInteger(String.valueOf(question.getQustnnreQustnId())));

		if(selectedAns != null ){
			if(selectedAns.getAnswerDisplayText() !=null){
				currentQustn.setAnswer(selectedAns.getAnswerDisplayText());
		}else if(QuestionnaireEvaluationConstants.ANSWR_CONTL_TYPE.equalsIgnoreCase(question.getAnswrControlType())){
			String rangeValue = selectedAns.getLowRangeValue()+"-"+selectedAns.getHighRangeValue();
			currentQustn.setAnswer(rangeValue);
		}
		currentQustn.setScore(new BigInteger(String.valueOf(selectedAns.getAnswerScore())));
		}
      
      LOGGER.exiting(CLASS_NAME, methodName);
	}
/**
 * This method is used to get the document stored in the Doc Store
 * @param long : Doc Store  Id
 * @return String : Stored document
 * @throws MitchellException
 * */
private String getDocfromNAS(long docStoreId) throws MitchellException{
	String methodName = "getDocfromNAS";
	LOGGER.entering(CLASS_NAME, methodName);
	String document = null;
	try {
	GetDocResponse docResponse = docStoreServiceProxy
			.getDocument(docStoreId);
	String sourceFileName = docResponse.getfilenameondisk();
	document = evaluationDAO.getEvaluationDocumentOnNAS(sourceFileName);
	if(document == null){
			 throw new MitchellException(CLASS_NAME, methodName,"No document found on NAS location");
	}
	
	} catch (IOException e) {
		 MitchellException me = new MitchellException(
		          QuestionnaireEvaluationConstants.IO_ERROR_IN_UPDT_EVAL,
		          CLASS_NAME, methodName,  
              EXCEPTION_DESC + e.getMessage(), e);
		      errorLogProxy.logError(me);
		      throw me;
	}catch (MitchellException me) {
		throw me;
	}
	LOGGER.exiting(CLASS_NAME, methodName);
	return document;
}

public MitchellEvaluationDetailsDocument getEvaluationDocFromNAS(long evalDocId) throws MitchellException{
	 String methodName = "getEvaluationDocFromNAS";
	LOGGER.entering(CLASS_NAME, methodName);	
	long docStoreId = 0L;
	String  evaluationDetailsXmlNASData = null;
	MitchellEvaluationDetailsDocument evaluationDetailsDocumentNAS = null;
	try {
		docStoreId = estimatePackageServiceProxy.getDocumentStoreIdByDocId(
				evalDocId, null);
		evaluationDetailsXmlNASData = getDocfromNAS(docStoreId);
		evaluationDetailsDocumentNAS = MitchellEvaluationDetailsDocument.Factory.parse(evaluationDetailsXmlNASData);
	}catch (XmlException e) {
		 MitchellException me = new MitchellException(
		          QuestionnaireEvaluationConstants.XML_EXCPTN_IN_UPDT_EVAL,
		          CLASS_NAME, methodName,   
              EXCEPTION_DESC + e.getMessage(), e);
		 errorLogProxy.logError(me);
		      throw me;
	}catch (MitchellException me) {
		throw me;
	}
	return evaluationDetailsDocumentNAS;
	
}

}
