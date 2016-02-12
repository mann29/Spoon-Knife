package com.mitchell.services.business.questionnaireevaluation.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import com.google.gson.Gson;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.TrackingInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.evaluationdetails.MitchellEvaluationDetailsDocument;
import com.mitchell.services.business.questionnaireevaluation.QuestionnaireEvaluationContext;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.services.business.questionnaireevaluation.dto.ContextDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireRqRsDTO;
import com.mitchell.services.business.questionnaireevaluation.proxy.AppLogProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.SystemConfigurationProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.TransactionalFileServiceProxy;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingType;
import com.mitchell.services.core.applog.client.AppLogging;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.messagebus.MessageDispatcher;
import com.mitchell.services.core.messagebus.WorkflowMsgUtil;
import com.mitchell.utils.misc.FileUtils;
import com.mitchell.utils.misc.UUIDFactory;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * Utility Class having all the generic methods to be used by other components.
 */
public class QuestionnaireEvaluationUtils implements
    QuestionnaireEvaluationUtilsProxy{

  /**
   * class name..
   */
  private static final String CLASS_NAME = QuestionnaireEvaluationUtils.class
      .getName();

  /**
   * logger..
   */
  private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

  private AppLogProxy appLogProxy = null;
  private SystemConfigurationProxy systemConfigProxy = null;
  private TransactionalFileServiceProxy transactionalFileServiceProxy = null;

  /**
   * For logging application events using APPLOG service.
   * 
   * @param responseMap
   *          <code>Map</code> responseMap
   * 
   * @param userInfo
   *          <code>UserInfoDocument</code> userInfo
   * 
   * @param appEventCode
   *          <code>String</code> appEventCode
   * 
   * @throws MitchellException
   *           in case not able to log
   * 
   */
  public void logAppEvent(Map responseMap, UserInfoDocument userInfo,
      String appEventCode)
      throws MitchellException  {

    String workItemId = null;
    final String methodName = "logAppEvent";
    try {

      LOGGER.entering(CLASS_NAME, methodName);

      AppLoggingNVPairs appLoggingNVPairs = new AppLoggingNVPairs();

      // populate values into these...

      AppLoggingDocument logDoc = AppLoggingDocument.Factory.newInstance();
      AppLoggingType appType = logDoc.addNewAppLogging();

      if (responseMap.get(QuestionnaireEvaluationConstants.CLAIM_ID) != null) {
        Long claimID = (Long) responseMap
            .get(QuestionnaireEvaluationConstants.CLAIM_ID);
        long claiId = claimID.longValue();
        if (claiId > 0) {
          appType.setClaimId(claiId);
        }
      }

      if (responseMap.get(QuestionnaireEvaluationConstants.SUFFIX_ID) != null) {
        Long suffixID = (Long) responseMap
            .get(QuestionnaireEvaluationConstants.SUFFIX_ID);
        long suffixId = suffixID.longValue();
        if (suffixId > 0) {
          appType.setClaimExposureId(suffixId);
        }
      }

      if (responseMap.get(QuestionnaireEvaluationConstants.DOCUMENT_ID) != null) {
        Long documentID = (Long) responseMap
            .get(QuestionnaireEvaluationConstants.DOCUMENT_ID);
        appLoggingNVPairs.addPair("DocumentId",
            String.valueOf(documentID.longValue()));
      }

      if (responseMap.get(QuestionnaireEvaluationConstants.EVALUATION_ID) != null) {
        appLoggingNVPairs.addPair("EvaluationId",
            responseMap.get(QuestionnaireEvaluationConstants.EVALUATION_ID)
                .toString());
      }

      if (userInfo != null && userInfo.getUserInfo() != null) {
        appType.setMitchellUserId(userInfo.getUserInfo().getUserID());
      }

      if (responseMap.get(QuestionnaireEvaluationConstants.LINK_STATUS) != null) {
        appLoggingNVPairs.addPair("LinkStatus",
            responseMap.get(QuestionnaireEvaluationConstants.LINK_STATUS)
                .toString());
      }
      appType.setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);
      appType.setStatus(AppLogging.APPLOG_ST_OK);
      appType.setTransactionType(appEventCode);
      appType.setWorkItemType("UNKNOWN");

      if (responseMap.get(QuestionnaireEvaluationConstants.WORKITEM_ID) != null) {
        workItemId = responseMap.get(
            QuestionnaireEvaluationConstants.WORKITEM_ID).toString();
      }

      appLogProxy.logAppEvent(logDoc, userInfo, workItemId,
          QuestionnaireEvaluationConstants.APPLICATION_NAME, appLoggingNVPairs);

    } catch (MitchellException mex) {
      throw mex;
    } catch (Throwable genExc) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.GENERIC_ERROR, CLASS_NAME,
          "logAppEvent", workItemId,
          QuestionnaireEvaluationConstants.GENERIC_ERROR + ": "
              + genExc.getMessage(), genExc);
    }
    LOGGER.exiting(CLASS_NAME, methodName);
  }

  /**
   * This method checks whether the Logger Level is FINE and then
   * logs the entire XML as a String.
   * 
   * @param message
   *          Message
   * 
   */
  public void logSEVEREMessage(String message)  {
    if (LOGGER.isLoggable(Level.SEVERE)) {
      LOGGER.severe(message);
    }
  }

  /**
   * This method checks whether the Logger Level is FINE and then
   * logs the entire XML as a String.
   * 
   * @param message
   *          <code>String</code> message
   */

  public void logFINEMessage(String message)  {
    if (LOGGER.isLoggable(Level.FINE)) {
      LOGGER.fine(message);
    }
  }

  /**
   * This method checks whether the Logger Level is INFO and then
   * logs the message as a String.
   * 
   * @param message
   *          <code>String</code> message
   */

  public void logINFOMessage(String message)  {
    if (LOGGER.isLoggable(Level.INFO)) {
      LOGGER.info(message);
    }
  }

  /**
   * THis method checks for the validity of XMlObject.
   * 
   * @return true - if xml validates schema else false
   * @param xo - XmlObject
   */

  public boolean isValid(XmlObject xo)  {
    boolean isValid = false;

    if (xo == null) {
      return false;
    }

    XmlOptions options = new XmlOptions();
    List schemaValidationErrors = new ArrayList();
    options.setErrorListener(schemaValidationErrors);

    isValid = xo.validate(options);

    if (!isValid) {

      logFINEMessage(" XMLDocument is invalid !!!! ");

      for (int i = 0; i < schemaValidationErrors.size(); i++) {
        XmlError error = (XmlError) schemaValidationErrors.get(i);

        if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
          logFINEMessage(" Message " + error.getMessage());
          logFINEMessage(" Location of invalid XML "
              + error.getCursorLocation().xmlText());
        }

      }
    }

    return isValid;
  }

  /**
   * Method to convert a stream to String.
   * 
   * @param is - InputStream
   * 
   * @return String
   * 
   * @throws IOException
   *           in case any exception with streams
   */

  public String convertStreamToString(InputStream is)
      throws IOException  {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
    if (is != null) {
      StringBuilder sb = new StringBuilder();
      String line;

      try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,
            "UTF-8"));
        while ((line = reader.readLine()) != null) {
          sb.append(line).append("\n");
        }
      } finally {
        is.close();
      }
      return sb.toString();
    } else {
      return null;
    }

  }

  /**
   * Method to retrieve the path for sub-directory based on current date.
   * 
   * @return subDirPath <code>String</code>, subDirPath in year/month/day form.
   * 
   */
  public String getCurrentDatePath()  {
    Calendar cal = new GregorianCalendar();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;
    int day = cal.get(Calendar.DATE);
    String newMonth = "0";

    if (month < 10) {
      newMonth += Integer.toString(month);
    } else {
      newMonth = Integer.toString(month);
    }
    String subDirPath = year + File.separator + newMonth + File.separator + day;
    return subDirPath;

  }

  // Validate Methods - 

  /**
   * Method to check the valid Company codes from SET file.
   * 
   * @param coCode
   *          <code>String</code> coCode
   * 
   * @return flag
   * 
   */
  public boolean validateCompanyCodes(String coCode)  {
    String coCodes = systemConfigProxy
        .getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_COMPANY_CODES);

    boolean flag = false;

    StringTokenizer st = new StringTokenizer(coCodes,
        QuestionnaireEvaluationConstants.PIPE_OPERATOR);

    while (st.hasMoreTokens()) {
      if (coCode != null && coCode.equalsIgnoreCase((String) st.nextToken())) {
        flag = true;
      }
    }

    return flag;
  }

  /*
   * Method to publish the message to the message bus
   * 
   * @param MessagingContext object
   */
  public void publishToMessageBus(MessagingContext msgContext)
      throws MitchellException  {
    String methodName = "publishToMessageBus";
    LOGGER.entering(CLASS_NAME, methodName);

    TrackingInfoDocument trackInfoDoc = WorkflowMsgUtil.createTrackingInfo(
    	// Name of the source application
    	QuestionnaireEvaluationConstants.APPLICATION_NAME, 
    	// Name of the Business Service
        QuestionnaireEvaluationConstants.BUSINESS_SERVICE_NAME, 
        // Name of the source module
        QuestionnaireEvaluationConstants.MODULE_NAME, 
        // workItemId of the business service instance// This should be a UUID
        msgContext.getWorkItemId(), 
        // UserInfoDoc for the user associated with the instance of this workflow
        msgContext.getUserInfoDoc(), 
        // COMMENT
        msgContext.getComment()); 

    // init the msg
    MitchellWorkflowMessageDocument mwmDoc = WorkflowMsgUtil
        .createWorkflowMessage(trackInfoDoc);

    try {
      // insert ME
      mwmDoc = WorkflowMsgUtil.insertDataBlock(mwmDoc,
          msgContext.getMitchellEnvelopeDocument(), msgContext.getEventId());

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        logINFOMessage("MWM document is:\n" + mwmDoc.toString());
        logINFOMessage("msgContext.getEventId()-" + msgContext.getEventId());
      }

     MessageDispatcher.postMessage(msgContext.getEventId(),
          mwmDoc.xmlText(), null, null);

    } catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_PUBLISHING_TO_MESSAGE_BUS,
          CLASS_NAME, methodName, null,
          QuestionnaireEvaluationConstants.ERROR_PUBLISHING_TO_MESSAGE_BUS_MSG,
          e);
    }

    LOGGER.exiting(CLASS_NAME, methodName);
  }

  /*
   * Method to create a MitchellEnvelopDocument document
   * 
   * @ param QuestionnaireEvaluationContext object
   * 
   * @ returns MitchellEnvelopeDocument
   */
  public MitchellEnvelopeDocument populateMeDocument(
      QuestionnaireEvaluationContext evalContext)
      throws MitchellException  {
    String methodName = "populateMeDocument";
    LOGGER.entering(CLASS_NAME, methodName);
    MitchellEnvelopeDocument meDoc = null;
    try {

      MitchellEnvelopeHelper meHelper = MitchellEnvelopeHelper.newInstance();

      meHelper.addEnvelopeContextNVPair(
          QuestionnaireEvaluationConstants.CLAIM_ID,
          Long.toString(evalContext.getClaimId()));
      meHelper.addEnvelopeContextNVPair(
          QuestionnaireEvaluationConstants.CLAIM_NUMBER,
          evalContext.getClaimNumber());
      meHelper.addEnvelopeContextNVPair(
          QuestionnaireEvaluationConstants.SUFFIX_ID,
          Long.toString(evalContext.getExposureId()));
      meHelper.addEnvelopeContextNVPair(
          QuestionnaireEvaluationConstants.EVALUATION_ID,
          evalContext.getEvaluationID());
      meHelper.addEnvelopeContextNVPair(
          QuestionnaireEvaluationConstants.CONTINGENCY_EVALUATION_FLAG,
          evalContext.getSourceSystem());

      meDoc = meHelper.getDoc();

    } catch (Exception e) {
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_PUBLISHING_TO_MESSAGE_BUS,
          CLASS_NAME, methodName, null,
          QuestionnaireEvaluationConstants.ERROR_PUBLISHING_TO_MESSAGE_BUS_MSG,
          e);
    }
    LOGGER.exiting(CLASS_NAME, methodName);

    return meDoc;
  }

  /**
   * Method to copy the evaluation files saved at temporary
   * location earlier to NAS location using TransactionalFileService.
   * 
   * @param evaluationXMLReturned
   *          <code>String</code> evaluationXMLReturned
   * 
   * @param docStoreId
   *          <code>Long</code> docStoreId
   * 
   * @throws MitchellException
   *           in case any exception
   */
  public void copyFileToNAS(String evaluationXMLReturned, Long docStoreId,
      String workItemId)
      throws MitchellException  {
    final String methodName = "copyFileToNAS";
    LOGGER.entering(CLASS_NAME, methodName);
    MitchellEvaluationDetailsDocument evaluationDocumentReturned = null;
    String coCode = "";
    String xmlDirectoryName = null;
    String xmlFileName = null;
    String xmlFilePath = null;
    try {
      boolean flag = false;

      evaluationDocumentReturned = MitchellEvaluationDetailsDocument.Factory
          .parse(evaluationXMLReturned);
      coCode = evaluationDocumentReturned.getMitchellEvaluationDetails()
          .getQuestionnaireArray(0).getEvaluationUserDetails().getCoCode();
      flag = validateCompanyCodes(coCode);

      if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
        logINFOMessage("flag=>" + flag);
      }

      if (!flag) {
        return;
      }

      try {

        xmlDirectoryName = systemConfigProxy
            .getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_LE_DIRECTORY_PATH)
            + File.separator + UUIDFactory.getInstance().getUUID();

        xmlFileName = QuestionnaireEvaluationConstants.SYSTEM_CONFIG_LE_FILE_NAME
            + QuestionnaireEvaluationConstants.UNDERSCORE
            + docStoreId
            + QuestionnaireEvaluationConstants.XML_EXTN;
        xmlFilePath = xmlDirectoryName + File.separator + xmlFileName;

        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
          logINFOMessage("Evaluation xmlFilePath = " + xmlFilePath);
        }

        FileUtils.createDirectory(xmlDirectoryName);

        String targetFileLocation = systemConfigProxy
            .getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_TARGET_FILE_PATH);

        targetFileLocation = targetFileLocation + File.separator + coCode
            + File.separator + getCurrentDatePath();

        boolean isExisting = FileUtils.isExistingDirectory(targetFileLocation);

        if (!isExisting) {
          FileUtils.createDirectory(targetFileLocation);
        }

        evaluationDocumentReturned.save(new File(xmlFilePath));
        try {
          transactionalFileServiceProxy.postCopyFileRequestCached(
              QuestionnaireEvaluationConstants.APPLICATION_NAME,
              QuestionnaireEvaluationConstants.MODULE_NAME, workItemId,
              xmlFilePath, targetFileLocation, null);
        } catch (Exception e) {
          throw new MitchellException(
              QuestionnaireEvaluationConstants.ERROR_CALLING_TFS, CLASS_NAME,
              methodName, workItemId,
              QuestionnaireEvaluationConstants.ERROR_CALLING_TFS + ": "
                  + e.getMessage(), e);
        }

      } catch (Exception e) {
        throw new MitchellException(
            QuestionnaireEvaluationConstants.ERROR_COPYING_TO_NAS, CLASS_NAME,
            methodName, workItemId,
            QuestionnaireEvaluationConstants.ERROR_COPYING_TO_NAS + ": "
                + e.getMessage(), e);
      }
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      logSEVEREMessage("Exception copying the file to NAS \n");
      throw new MitchellException(
          QuestionnaireEvaluationConstants.ERROR_COPYING_TO_NAS, CLASS_NAME,
          methodName, QuestionnaireEvaluationConstants.ERROR_COPYING_TO_NAS
              + ": " + e.getMessage(), e);
    } finally {
      //delete the file from source location
      if (xmlDirectoryName != null) {
        deleteFileFromSrc(new File(xmlDirectoryName));
      }
    }

    LOGGER.exiting(CLASS_NAME, methodName);

  }

  /**
   * Method to delete the file from temporary location.
   * 
   * 
   * @param sourceFileName
   *          <code>String</code> sourceFileName
   * 
   */
  private void deleteFileFromSrc(File sourceFile)  {

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      logINFOMessage("ENTERING deleteFileFromSrc with FileName:" + sourceFile);
    }

    boolean result = false;
    if (sourceFile.isDirectory()) {
      String[] children = sourceFile.list();
      for (int i = 0; i < children.length; i++) {
        deleteFileFromSrc(new File(sourceFile, children[i]));
      }
    }
    if (sourceFile.exists()) {
      result = sourceFile.delete();
    }

    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      logINFOMessage(CLASS_NAME + "File deletion status:" + result);
    }

  }

  public AppLogProxy getAppLogProxy()  {
    return appLogProxy;
  }

  public void setAppLogProxy(AppLogProxy appLogProxy)  {
    this.appLogProxy = appLogProxy;
  }

  public SystemConfigurationProxy getSystemConfigProxy()  {
    return systemConfigProxy;
  }

  public void setSystemConfigProxy(SystemConfigurationProxy systemConfigProxy)  {
    this.systemConfigProxy = systemConfigProxy;
  }

  public TransactionalFileServiceProxy getTransactionalFileServiceProxy()  {
    return transactionalFileServiceProxy;
  }

  public void setTransactionalFileServiceProxy(
      TransactionalFileServiceProxy transactionalFileServiceProxy)  {
    this.transactionalFileServiceProxy = transactionalFileServiceProxy;
  }

  /**
	 * Generates Random Evaluation ID
	 * 
	 * @return String
	 */
  public String generateEvaluationID(){
		return UUIDFactory.getInstance().getUUID();
	}
  /**
	 * Converts DTO to JSON
	 * 
	 * @param requestDto
	 * @return String
	 */
	public String dtoToJson(QuestionnaireRqRsDTO responseDto) {
		Gson gson = new Gson();
		return  gson.toJson(responseDto);
	}
	 /**
	  * Converts jSON to DTO
	  * @param inputJson
	  * @return QuestionnaireRqRsDTO
	  */
	 public QuestionnaireRqRsDTO jsonToDto(String inputJson){
			Gson gson = new Gson();
	      return gson.fromJson(inputJson, QuestionnaireRqRsDTO.class);
		}
	 
}
