package com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.arc5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cieca.bms.CIECADocument;
import com.cieca.bms.ClaimInfoType;
import com.cieca.bms.PolicyInfoType;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AssignmentDeliveryHandler;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.HandlerUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.eclaim.BmsToMieConverter;
import com.mitchell.services.business.partialloss.assignmentdelivery.ioc.SpringHelper;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingType;
import com.mitchell.services.core.applog.client.AppLogging;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.services.core.mum.types.mie.DS01Type;
import com.mitchell.services.core.mum.types.mie.DS02Type;
import com.mitchell.services.core.mum.types.mie.EstimateType;
import com.mitchell.services.core.mum.types.mie.MieDocument;
import com.mitchell.services.core.mum.types.mie.MieMum;
import com.mitchell.services.core.mum.types.mie.MieType;
import com.mitchell.services.core.sequencegen.client.SequenceGenService;
import com.mitchell.services.core.transactionalfile.client.TransactionalFileService;
import com.mitchell.systemconfiguration.SystemConfiguration;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.misc.FileUtils;
import com.mitchell.utils.misc.UUIDFactory;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * Delivery Handler for handling MIEs to be delivered via ARC5.
 * 
 * All SF MIEs will be directed here. More specifically, companies with the
 * custom setting: SIP Assignment Delivery Settings > Destination set to "ARC5"
 * will be directed here.
 * 
 * If the context contains a DRP userinfo (i.e. non-staff) this handler will
 * delegate the delivery to the EClaimDelivery handler. Otherwise, this handler
 * will construct the ARC5 MIE by:
 * 
 * 1) Calling the MUM framework BMS to MIE transformation.
 * 
 * 2) Creating the DS01 and DS02 MIE records.
 * 
 * 3) Saving the MIE in the 132 + N/L ARC5 format.
 * 
 * 4) Delivering this MIE to the appropriate ARC5 sys config delivery dir.
 * 
 * @author cg97025
 * 
 */
public class ARC5DeliveryHandler implements AssignmentDeliveryHandler
{

  private final static String CLASS_NAME = "Arc5DeliveryHandler";
  // New applog event type for ARC5 delivery.
  private final static String ARC5_SUBMISSION_SUCCESS_APPLOG_TRNS_TYPE_FILE = "106801";
  private final static String NVPAIR_ASG_DELIVERY_FAR_ID_NAME = "asgDeliveryFileArchiveId";
  private final static String NVPAIR_ASG_DELIVERY_CIECA_BMS_ID_NAME = "asgDeliveryCiecaBmsId";

  private final AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(
      this.getClass().getName());
  private final Logger log = Logger.getLogger(ARC5DeliveryHandler.class
      .getName());

  private BmsToMieConverter m_converter = null;

  private AssignmentServiceContext m_context = null;
  private MitchellEnvelopeDocument m_meDoc = null;

  private String m_workItemId = null;

  private String m_tempDir = null;
  private String m_tempSubDir = null;

  private String m_hostname = null;
  private boolean m_isLocalhost = false;

  private UserInfoDocument m_uiDoc = null;
  private String m_coCd = null;
  private int m_orgId;

  private File m_mieFile = null;
  private String m_mieFilename = null;
  private MieDocument m_mieDoc = null;
  private MieType m_mieType = null;

  private MieMum m_mieMum = null;
  private String m_mieARC5Filename = null;

  private DS02Type[] m_ds02TypeArray = null;

  private String m_deliveryDirForARC5 = null;

  // Archive Id.
  private long m_arid = -1;

  private HandlerUtils m_handlerUtils = new HandlerUtils();

  private boolean m_doCleanup = false;

  /**
   * 'Main' calling method for ARC5DeliveyHandler class.
   */
  public void deliverAssignment(AssignmentServiceContext context)
      throws AssignmentDeliveryException
  {

    long startTime = System.currentTimeMillis();

    final String methodName = "deliverAssignment";

    AppLoggingNVPairs appLogNvPairs = new AppLoggingNVPairs();

    m_context = context;

    // Get WorkItemId as soon as possible, so if we have a subsequent error,
    // we can store this in error log.
    m_workItemId = context.getWorkItemId();

    // Validate the input context.
    m_context.validate();
    if (log.isLoggable(Level.FINE)) {
      log.fine("Input AssignmentServiceContext is valid.");
    }

    m_meDoc = context.getMitchellEnvDoc();

    // Useful for debugging.
    if (log.isLoggable(Level.FINER)) {
      logContextDetailsAsFiner();
    }

    // Does context contain DRP userinfo?
    boolean isDrpUser = m_context.isDrp();

    if (log.isLoggable(Level.FINE)) {
      log.fine("isDrpUser: " + isDrpUser);
    }

    // If context does contain DRP userinfo, delegate to EClaimDeliveryHandler.
    if (isDrpUser) {

      AssignmentDeliveryHandler handler;
      try {
        handler = (AssignmentDeliveryHandler) SpringHelper
            .getBean("ADS.ECLAIM.handler");
        handler.deliverAssignment(context);
      } catch (IllegalAccessException e) {

        // If this exception occurs it means most likely that the SpringHelper reference has not been
        // obtained by the enclosing EJB.
        throw mLogger.createException(
            AssignmentDeliveryErrorCodes.HANDLER_CLASS_ILLEGAL_ACCESS_ERROR,
            m_workItemId,
            "Exception calling SpringHelper for ADS.ECLAIM.handler", e);

      }

      return;
    }

    // Otherwise, is ARC5 (SF-staff) user, so continue.

    m_uiDoc = context.getUserInfo();
    m_coCd = m_uiDoc.getUserInfo().getOrgCode();
    m_orgId = new Integer(m_uiDoc.getUserInfo().getOrgID()).intValue();

    // If user does not have ECM appl access code, error-log a warning.
    boolean isECMUser = HandlerUtils.hasApplCode(m_uiDoc,
        AssignmentDeliveryConstants.APPL_ACCESS_CODE_ECLAIM_MANAGER);
    appLogNvPairs.addInfo("IsEClaimUserAPPLCode", isECMUser ? "True" : "False");

    if (log.isLoggable(Level.FINE)) {
      log.fine("isECMUser: " + isECMUser);
    }

    // Initialize system config values.
    setupConfigValues();

    // Check and see if it is a supplement that was converted to an original. If so, convert it
    // back to a supplement since ARC5 does the supplement to original conversion itself.

    boolean bSupplementConvertedToOriginalFlag = false;
    AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = null;
    try {
      aaaInfoDoc = m_handlerUtils.getAAAInfoDocFromMitchellEnv(
          context.getMitchellEnvDoc(), context.getWorkItemId(), false);

      if (aaaInfoDoc != null) {
        if (mLogger.isLoggable(Level.INFO)) {
          mLogger
              .info("Have aaaInfoDoc, now check bSupplementConvertedToOriginalFlag");
        }

        if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .isSetAssignmentDetails()) {
          if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .getAssignmentDetails().isSetSupplementConvertedToOriginalFlag()) {
            bSupplementConvertedToOriginalFlag = aaaInfoDoc
                .getAdditionalAppraisalAssignmentInfo().getAssignmentDetails()
                .getSupplementConvertedToOriginalFlag();
            if (mLogger.isLoggable(Level.INFO)) {
              mLogger
                  .info("Have aaaInfoDoc, bSupplementConvertedToOriginalFlag="
                      + bSupplementConvertedToOriginalFlag);
            }
          }
        }
      }

      // AppLog Details

      appLogNvPairs.addInfo("AAInfoSuppConvertedToOrigFlag",
          bSupplementConvertedToOriginalFlag ? "True" : "False");

      // Special Case: Supplement Assignment Converted To Original see we need to convert it back to a Supplement.
      //
      if (bSupplementConvertedToOriginalFlag) {
        CIECADocument ciecaDoc = m_handlerUtils.getCiecaDocFromMitchellEnv(
            m_meDoc, m_workItemId);

        ciecaDoc.getCIECA().getAssignmentAddRq().getDocumentInfo()
            .getDocumentVerArray()[0].setDocumentVerCode("SV");
        MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(m_meDoc);
        meHelper
            .updateEnvelopeBodyContent(meHelper.getEnvelopeBody(), ciecaDoc);

        // AppLog Details

        appLogNvPairs.addInfo("UpdatedBMSAssignmentBackToSupplement", "True");

        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
          mLogger.info("****changed original back to supplement "
              + m_meDoc.toString());
        }

      }

    } catch (Exception e) {
      int errorCode = AssignmentDeliveryErrorCodes.GENERAL_ERROR;

      ErrorLoggingService.logError(errorCode, null, CLASS_NAME, methodName,
          ErrorLoggingService.SEVERITY.FATAL, m_workItemId, e.getMessage(),
          m_coCd, m_orgId, e);

      throw mLogger
          .createException(
              errorCode,
              m_workItemId,
              "Error trying to convert a converted supp to orig assignment back to a supplement.",
              e);
    }

    // Transform BMS document in aqssignment context to MIE.
    transformBmsDocumentToMie();

    // Using GUID in MIE filename causes problems with ARU pickup, so
    // replace it with the result of a call to the SequenceGen service.
    renameMieFile();

    // Read MIE file into memory.
    unmarshalMieFile();

    // Create DS02 Records.
    createDS02Records();

    // Create DS01 Record.
    // This is called after creating the DS02 records as the DS01 record
    // contains a count of the number of DS02 records.
    createDS01Record();

    // Create ARC5-ready MIE.
    updateMieForARC5();

    // Write out (marshal) updated MIE file.
    marshalNewMieFile();

    // Archive Assignment MIE.
    archiveMIEForARC5();

    // Copy ARC5 MIE file to ARC5 Delivery Dir.
    copyARC5MieFileToARC5DeliveryDir(appLogNvPairs);

    // AppLog the ARC5 assignment delivery.
    logARC5DeliveryEvent(appLogNvPairs, startTime);

    // Cleanup temp files, if required...
    String doCleanup = SystemConfiguration
        .getSettingValue(AssignmentDeliveryConstants.SYS_CONFIG_BOOLEAN_CLEANUP);

    if (doCleanup != null) {
      m_doCleanup = doCleanup.equalsIgnoreCase("true") ? true : false;

      if (m_doCleanup) {
        cleanupTempFiles();
        // cleanupTempDir();
      }
    }
  }

  private void logContextDetailsAsFiner()
  {

    log.finer("AssignmentServiceContext context - WorkItemId: "
        + m_context.getWorkItemId());
    log.finer("UserInfo:\n" + m_context.getUserInfo());

    if (m_context.isDrp()) {
      log.finer("is DRP");

      log.finer("DRP UserInfo:\n" + m_context.getDrpUserInfo());
    } else {
      log.finer("is NOT DRP");
    }

    log.finer("MitchellEnvelope:\n" + m_context.getMitchellEnvDoc());
  }

  private void setupConfigValues()
  {

    m_hostname = SystemConfiguration
        .getSettingValue(AssignmentDeliveryConstants.SYS_CONFIG_HOSTNAME);

    if (m_hostname != null && m_hostname.equals("localhost")) {
      m_isLocalhost = true;
    }

    m_tempDir = SystemConfiguration
        .getSettingValue(AssignmentDeliveryConstants.SYS_CONFIG_BMS_TO_MIE_TEMP_DIR);
  }

  /*
   * Create temp dir in which files will be stored. These include the initial
   * BMS file in the context (in the MitchellEnvelope), the transformed MIE,
   * the ARC5-ready MIE.
   */
  private void setupTempDir()
      throws AssignmentDeliveryException
  {

    final String METHOD_NAME = "setupTempDir";

    m_tempSubDir = UUIDFactory.getInstance().getUUID();

    m_tempDir += File.separator + m_tempSubDir;

    // Create temporary dir.
    boolean success = (new File(m_tempDir).mkdir());

    if (!success) {
      // Directory creation failed.
      int errorCode = AssignmentDeliveryErrorCodes.GENERAL_ERROR;

      ErrorLoggingService.logError(errorCode, null, CLASS_NAME, METHOD_NAME,
          ErrorLoggingService.SEVERITY.FATAL, m_workItemId,
          "Error Creating Temporary Directory: " + m_tempDir, m_coCd, m_orgId,
          null);

      throw mLogger.createException(errorCode, m_workItemId,
          "Error Creating Temporary Directory: " + m_tempDir);
    }
  }

  private void transformBmsDocumentToMie()
      throws AssignmentDeliveryException
  {

    final String METHOD_NAME = "transformBmsDocumentToMie";

    try {
      // Transform BMS document to a MIE document.
      m_converter = new BmsToMieConverter(true); // Conversion should have ARC5-specific logic.
      m_mieFile = m_converter.transformBmsAsgToMieAsg(m_context);

      if (log.isLoggable(Level.FINE)) {
        log.fine("Converted BMSAssignment document to MIE file: "
            + m_mieFile.getAbsoluteFile());
      }
    } catch (Exception e) {
      int errorCode = AssignmentDeliveryErrorCodes.BMS_MIE_ERROR;

      ErrorLoggingService.logError(errorCode, null, CLASS_NAME, METHOD_NAME,
          ErrorLoggingService.SEVERITY.FATAL, m_workItemId, e.getMessage(),
          m_coCd, m_orgId, e);

      throw mLogger.createException(errorCode, m_workItemId,
          "Error Transforming BmsToMie - dir: " + m_tempDir, e);
    }
  }

  /*
   * Determine new name for MIE file - need to replace UUID with SequenceGen
   * result in order to shorten name for ARU, while still maintaining
   * guaranteed uniqueness.
   */
  private String determineNewMIEFilename(String filename)
      throws MitchellException
  {

    String retVal = null;
    // MIE filename format is COCD . WorkItemId . GUID. 1 . mie

    int indexOfDot1 = filename.indexOf(".");
    String coCd = filename.substring(0, indexOfDot1);
    String tmpStr1 = filename.substring(indexOfDot1 + 1);

    int indexOfDot2 = tmpStr1.indexOf(".");
    String workItemId = tmpStr1.substring(0, indexOfDot2);
    String tmpStr2 = tmpStr1.substring(indexOfDot2 + 1);

    int indexOfDot3 = tmpStr2.indexOf(".");
    String guid = tmpStr2.substring(0, indexOfDot3);
    String tmpStr3 = tmpStr2.substring(indexOfDot3 + 1);

    // Replace guid with seq_gen call ersult.
    String uniqifier = SequenceGenService.generateUniqueString();

    /**
     * MIE file name has been modified for claimId & ExposureId
     * Resolution: CID_XXXXX & EID_XXXXX added in mie file name.
     * Modified Date :-05/10/2010
     * Modified By : Preet Singh
     */

    HandlerUtils utilObj = new HandlerUtils();
    String[] result = utilObj.getClaimExposureIdsResult(m_context);
    if (result != null) {
      String claimId = result[0].trim();
      String claimExposureId = result[1].trim();

      // New name is COCD _ WorkItemId _ CID-XXXXX _ EID-XXXXX _ SEQGEN _ 1 . mie
      //retVal = coCd + "_" + workItemId + "_" + "CID-" + claimId + "_" + "EID-"
      //+ claimExposureId + "_" + uniqifier + "_" + tmpStr3;

      StringBuffer sb = new StringBuffer("");

      sb.append(coCd);
      sb.append("_");
      sb.append(workItemId);
      sb.append("_CID-");
      sb.append(claimId);
      sb.append("_EID-");
      sb.append(claimExposureId);
      sb.append("_");
      sb.append(uniqifier);
      sb.append("_");
      sb.append(tmpStr3);

      retVal = sb.toString();

      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger.info("ClaimId & Exposure ID : " + claimId + ","
            + claimExposureId);
      }

    } else {
      mLogger.info("Default MCFFilename created.");
      // Old name is COCD _ WorkItemId _ SEQGEN _ 1 . mie
      //retVal = coCd + "_" + workItemId + "_" + uniqifier + "_" + tmpStr3;

      StringBuffer sb = new StringBuffer("");
      sb.append(coCd);
      sb.append("_");
      sb.append(workItemId);
      sb.append("_");
      sb.append(uniqifier);
      sb.append("_");
      sb.append(tmpStr3);

      retVal = sb.toString();

    }

    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("MIEFileName : " + retVal);
    }
    //
    return retVal;
  }

  // Rename MIE file, for successful ARU pickup - replace the UUID with the
  // result of a call to SequenceGen srv.
  private void renameMieFile()
      throws AssignmentDeliveryException
  {

    final String METHOD_NAME = "unmarshalMieFile";

    File originalFile = m_mieFile;
    String newMieFileFullPath = null;

    try {
      String filenameOnly = m_mieFile.getName();

      String newMieFilenameOnly = determineNewMIEFilename(filenameOnly);

      newMieFileFullPath = m_mieFile.getParent() + File.separator
          + newMieFilenameOnly;

      File newFile = new File(newMieFileFullPath);

      originalFile.renameTo(newFile);

      m_mieFile = newFile;
    } catch (MitchellException e) {
      int errorCode = AssignmentDeliveryErrorCodes.ERROR_RENAMING_MIE_FILE_FOR_ARU_PICKUP;

      ErrorLoggingService.logError(errorCode, null, CLASS_NAME, METHOD_NAME,
          ErrorLoggingService.SEVERITY.FATAL, m_workItemId, e.getMessage(),
          m_coCd, m_orgId, e);

      throw mLogger.createException(errorCode, m_workItemId,
          "Error Renaming MIE File for ARU Pickup: " + originalFile.toString()
              + " to " + newMieFileFullPath, e);
    }
  }

  /*
   * Read (unmarshal) MIE file from file into memory (MUM XMLBean).
   */
  private void unmarshalMieFile()
      throws AssignmentDeliveryException
  {

    final String METHOD_NAME = "unmarshalMieFile";

    try {
      m_mieMum = new MieMum();

      // Read/unmarshal original file into MUM.
      m_mieFilename = m_mieFile.getAbsoluteFile().toString();
      m_mieMum.unmarshalNative(m_mieFilename);

      m_mieDoc = m_mieMum.getDocument();
      m_mieType = m_mieDoc.getMie();
    } catch (IOException e) {
      int errorCode = AssignmentDeliveryErrorCodes.ERROR_UNMARSHALING_MIE_FILE;

      ErrorLoggingService.logError(errorCode, null, CLASS_NAME, METHOD_NAME,
          ErrorLoggingService.SEVERITY.FATAL, m_workItemId, e.getMessage(),
          m_coCd, m_orgId, e);

      throw mLogger.createException(errorCode, m_workItemId,
          "Error Unmarshaling MIE File: " + m_mieFilename, e);
    }

  }

  /*
   * Create the DS01 record for the ARC5 MIE.
   */
  private void createDS01Record()
      throws AssignmentDeliveryException
  {

    final String METHOD_NAME = "createDS01Record";

    DSRecordsUtils utils = new DSRecordsUtils();
    DS01Type ds01Type = utils.getDS01Record(m_meDoc, m_workItemId,
        m_ds02TypeArray.length, m_coCd, m_orgId);

    EstimateType[] estTypeArray = m_mieType.getEstimateArray();

    if (estTypeArray != null && estTypeArray.length > 1) {

      // DS01 Record goes in second entry.
      EstimateType est1 = estTypeArray[1];
      est1.setDS01(ds01Type);
    } else {
      int errorCode = AssignmentDeliveryErrorCodes.MISSING_SECOND_ESTIMATE_IN_MIE_FILE;

      ErrorLoggingService.logError(errorCode, null, CLASS_NAME, METHOD_NAME,
          ErrorLoggingService.SEVERITY.FATAL, m_workItemId,
          "Missing Second Estimate in MIE File: " + m_mieFilename
              + " for DS Records", m_coCd, m_orgId, null);

      throw mLogger.createException(errorCode, m_workItemId,
          "Missing Second Estimate in MIE File: " + m_mieFilename
              + " for DS Records", null);
    }
  }

  /*
   * Create the DS02 records for the ARC5 MIE.
   */
  private void createDS02Records()
      throws AssignmentDeliveryException
  {

    final String METHOD_NAME = "createDS02Records";

    DSRecordsUtils utils = new DSRecordsUtils();
    m_ds02TypeArray = utils.getDS02Records(m_meDoc, m_workItemId, m_coCd,
        m_orgId);

    EstimateType[] estTypeArray = m_mieType.getEstimateArray();

    if (estTypeArray != null && estTypeArray.length > 1) {

      // DS02 Records go in a second MIE/Estimate entry.
      EstimateType est1 = estTypeArray[1];
      est1.setDS02Array(m_ds02TypeArray);
    } else {
      int errorCode = AssignmentDeliveryErrorCodes.MISSING_SECOND_ESTIMATE_IN_MIE_FILE;

      ErrorLoggingService.logError(errorCode, null, CLASS_NAME, METHOD_NAME,
          ErrorLoggingService.SEVERITY.FATAL, m_workItemId,
          "Missing Second Estimate in MIE File: " + m_mieFilename
              + " for DS Records", m_coCd, m_orgId, null);

      throw mLogger.createException(errorCode, m_workItemId,
          "Missing Second Estimate in MIE File: " + m_mieFilename
              + " for DS Records", null);
    }
  }

  /**
   * Use MUM marshaling parameters to increate the padding, and use N/Ls at
   * the end of each line.
   */
  private void updateMieForARC5()
  {

    m_mieMum.setPadLength(132);
    m_mieMum.setNewline(true);
  }

  private void marshalNewMieFile()
      throws AssignmentDeliveryException
  {

    final String METHOD_NAME = "marshalNewMieFile";

    try {
      // Write (marshal) new file.
      m_mieARC5Filename = getARC5FilenameFromMieFilename(m_mieFilename);
      m_mieMum.marshalNative(m_mieARC5Filename);

      if (log.isLoggable(Level.FINE)) {
        log.fine("Wrote MIE ARC5 file: " + m_mieARC5Filename);
      }

    } catch (IOException e) {
      int errorCode = AssignmentDeliveryErrorCodes.ERROR_CREATING_ARC5_MIE_FILE;

      ErrorLoggingService.logError(errorCode, null, CLASS_NAME, METHOD_NAME,
          ErrorLoggingService.SEVERITY.FATAL, m_workItemId, e.getMessage(),
          m_coCd, m_orgId, e);

      throw mLogger.createException(errorCode, m_workItemId,
          "Error Creating ARC5 MIE File: + " + m_mieARC5Filename, e);
    }
  }

  /*
   * Determine ARC5 MIE filename:
   * If input is:
   * 
   * xxx-yyy.mie
   * 
   * Output is:
   * 
   * xxx-yyy-arc5.mie
   */
  private String getARC5FilenameFromMieFilename(String filename)
  {

    int index = filename.lastIndexOf(".");

    return filename.substring(0, index) + "-arc5.mie";
  }

  /*
   * Helper method to do non-transactional file copy.
   */
  private void copyFile(String ipFilename, String opFilename)
      throws IOException
  {

    InputStream in = null;
    OutputStream out = null;

    try {
      in = new FileInputStream(ipFilename);
      out = new FileOutputStream(opFilename);

      // Copy file, 1K at a time...
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
    } finally {
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
    }
  }

  /**
   * Copy ARC5 MIE file to sys conf ARC5-delivery dir.
   * 
   * @throws AssignmentDeliveryException
   */
  private void copyARC5MieFileToARC5DeliveryDir(AppLoggingNVPairs appLogNvPairs)
      throws AssignmentDeliveryException
  {

    final String METHOD_NAME = "copyARC5MieFileToARC5DeliveryDir";

    // Get ARC5 delivery dir from .SET file.
    m_deliveryDirForARC5 = SystemConfiguration
        .getSettingValue(AssignmentDeliveryConstants.SYS_CONFIG_ARC5_COPYDIR);

    String filenameOnly = new File(m_mieARC5Filename).getName();

    String newFilename = m_deliveryDirForARC5 + File.separator + filenameOnly;

    appLogNvPairs.addFile("ARC5MieFile", newFilename);

    // If sys conf "localhost" set, use non-transactional file copy. 
    if (m_isLocalhost) {

      try {
        // Do normal file copy locally.
        copyFile(m_mieARC5Filename, newFilename);
      } catch (IOException e) {
        int errorCode = AssignmentDeliveryErrorCodes.ERROR_COPYING_ARC5_MIE_FILE;

        ErrorLoggingService.logError(errorCode, null, CLASS_NAME, METHOD_NAME,
            ErrorLoggingService.SEVERITY.FATAL, m_workItemId, e.getMessage(),
            m_coCd, m_orgId, e);

        throw mLogger.createException(errorCode, m_workItemId,
            "Error Copying ARC5 MIE File: + " + m_mieARC5Filename + " to "
                + m_deliveryDirForARC5, e);
      }
    } else {
      // Use TransactionFileService for transactional copying.

      if (log.isLoggable(Level.FINE)) {
        log.fine("ARC5Copy - m_mieARC5Filename: " + m_mieARC5Filename
            + ", m_deliveryDirForARC5: " + m_deliveryDirForARC5
            + ", filename: " + filenameOnly);
      }

      try {
        TransactionalFileService.postCopyFileRequestCached(
            AssignmentDeliveryConstants.APPLICATION_NAME,
            AssignmentDeliveryConstants.MODULE_NAME, m_workItemId,
            m_mieARC5Filename, m_deliveryDirForARC5, filenameOnly);
      } catch (MitchellException e) {
        int errorCode = AssignmentDeliveryErrorCodes.ERROR_COPYING_ARC5_MIE_FILE;

        ErrorLoggingService.logError(errorCode, null, CLASS_NAME, METHOD_NAME,
            ErrorLoggingService.SEVERITY.FATAL, m_workItemId, e.getMessage(),
            m_coCd, m_orgId, e);

        throw mLogger.createException(errorCode, m_workItemId,
            "Error Copying ARC5 MIE File: + " + m_mieARC5Filename + " to "
                + m_deliveryDirForARC5, e);
      }
    }
  }

  /*
   * Archive the ARC5-ready MIE file.
   */
  private void archiveMIEForARC5()
  {

    // Cannot archive local file.
    if (!m_isLocalhost) {

      // Archive MIE file for ARC5.
      // Method name (sic!)
      m_arid = m_handlerUtils.archieveAssignment(m_uiDoc, new File(
          m_mieARC5Filename), m_workItemId);

      if (log.isLoggable(Level.FINE)) {
        log.fine("Successfully archived MIE file: " + m_mieARC5Filename
            + " with Archive ID: " + m_arid);
      }
    }
  }

  /*
   * Cleanup the temporary files storing the BMS XML file, MIE, ARC5 MIE.
   */
  private void cleanupTempFiles()
      throws AssignmentDeliveryException
  {

    final String METHOD_NAME = "cleanupTempFiles";

    // These are the two files that need to be cleaned up.
    String[] cleanupFiles = new String[] { m_mieFilename, m_mieARC5Filename };
    String[] cleanupFilenamesOnly = new String[] {
        new File(m_mieFilename).getName(),
        new File(m_mieARC5Filename).getName() };

    // Cannot run TransactionalFileService locally.
    if (m_isLocalhost) {

      deleteFiles(cleanupFiles);
    } else {

      try {
        // We are requested to clean out the temporary files.
        TransactionalFileService.postListDeleteRequest(
            AssignmentDeliveryConstants.APPLICATION_NAME,
            AssignmentDeliveryConstants.MODULE_NAME, m_workItemId, m_tempDir,
            cleanupFilenamesOnly);
      } catch (Exception e) {
        int errorCode = AssignmentDeliveryErrorCodes.GENERAL_ERROR;

        // Failure to remove the temporary MIE files is nor fatal - log
        // a warning error and continue.
        ErrorLoggingService.logError(errorCode, null, CLASS_NAME, METHOD_NAME,
            ErrorLoggingService.SEVERITY.WARNING, m_workItemId,
            "Error Removing Temporary MIE Files: " + cleanupFilenamesOnly[0]
                + " and " + cleanupFilenamesOnly[1] + e.getMessage(), m_coCd,
            m_orgId, e);

        /*
         * throw mLogger.createException(errorCode, m_workItemId,
         * "Error Removing Temporary Directory: " + m_tempDir, e);
         */
      }
    }
  }

  /*
   * Cleanup the temporary dir storing the BMS XML file, MIE, ARC5 MIE.
   */
  private void cleanupTempDir()
      throws AssignmentDeliveryException
  {

    final String METHOD_NAME = "cleanupTempDir";

    // Cannot run TransactionalFileService locally.
    if (m_isLocalhost) {

      deleteDir(new File(m_tempDir));
    } else {

      try {
        // We are requested to clean out the working dir, so delete it.
        TransactionalFileService.postDeleteRequest(
            AssignmentDeliveryConstants.APPLICATION_NAME,
            AssignmentDeliveryConstants.MODULE_NAME, m_workItemId, m_tempDir,
            false);
      } catch (Exception e) {
        int errorCode = AssignmentDeliveryErrorCodes.GENERAL_ERROR;

        ErrorLoggingService.logError(errorCode, null, CLASS_NAME, METHOD_NAME,
            ErrorLoggingService.SEVERITY.FATAL, m_workItemId, e.getMessage(),
            m_coCd, m_orgId, e);

        throw mLogger.createException(errorCode, m_workItemId,
            "Error Removing Temporary Directory: " + m_tempDir, e);
      }
    }
  }

  private void deleteFiles(String[] files)
  {

    String nextFile = null;

    try {
      for (int i = 0; i < files.length; i++) {
        nextFile = files[i];
        FileUtils.deleteFile(nextFile);
      }
    }
    // Only deleting files locally, so handle the exception locally.
    catch (IOException e) {
      log.fine("Error Deleting File: " + nextFile);
    }
  }

  // Deletes all files and subdirectories under dir. Returns true if all
  // deletions were successful. If a deletion fails, stop attempting to delete
  // and return false.
  public static boolean deleteDir(File dir)
  {

    if (dir.isDirectory()) {

      String[] children = dir.list();

      for (int i = 0; i < children.length; i++) {

        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
    }

    // The directory is now empty so delete it.
    return dir.delete();
  }

  /**
   * Do APP_LOG for delivery of ARC5 MIE file.
   * 
   * @throws AssignmentDeliveryException
   */
  private void logARC5DeliveryEvent(AppLoggingNVPairs appLogNvPairs,
      long startTime)
      throws AssignmentDeliveryException
  {

    final String METHOD_NAME = "logARC5DeliveryEvent";

    try {
      MitchellEnvelopeDocument meDoc = m_context.getMitchellEnvDoc();

      CIECADocument ciecaDoc = m_handlerUtils.getCiecaDocFromMitchellEnv(meDoc,
          m_workItemId);

      AppLoggingDocument appLogDoc = AppLoggingDocument.Factory.newInstance();
      AppLoggingType appType = appLogDoc.addNewAppLogging();

      ClaimInfoType claimInfo = ciecaDoc.getCIECA().getAssignmentAddRq()
          .getClaimInfo();
      if (claimInfo.getClaimNum() != null) {
        appType.setClaimNumber(claimInfo.getClaimNum());
      }

      // PolicyInfoType is not a mandatory element of ClaimInfo so check
      // its nullability.
      PolicyInfoType policyInfo = claimInfo.getPolicyInfo();
      if (policyInfo != null && policyInfo.getPolicyNum() != null) {
        appType.setPolicyNumber(policyInfo.getPolicyNum());
      }

      appType.setCurrentWorkflowId(m_workItemId);
      appType.setMitchellUserId(m_uiDoc.getUserInfo().getUserID());
      appType.setModuleName(AssignmentDeliveryConstants.MODULE_NAME);
      appType.setStatus(0);
      appType.setTransactionType(ARC5_SUBMISSION_SUCCESS_APPLOG_TRNS_TYPE_FILE);

      appLogNvPairs.addFileArchiveId(NVPAIR_ASG_DELIVERY_FAR_ID_NAME,
          String.valueOf(m_arid));

      if (ciecaDoc.getCIECA().getAssignmentAddRq().getDocumentInfo()
          .getDocumentID() != null) {
        appLogNvPairs.addCiecaBMSRqUID(NVPAIR_ASG_DELIVERY_CIECA_BMS_ID_NAME,
            ciecaDoc.getCIECA().getAssignmentAddRq().getDocumentInfo()
                .getDocumentID());
      }

      // Processing time

      long endTime = System.currentTimeMillis();
      double totalTime = ((endTime - startTime) / 1000.0);
      appLogNvPairs.addInfo("TotalProcessingTimeSecs",
          String.valueOf(totalTime));

      // Machine Name/Server Name

      String machineInfo = AppUtilities.buildServerName();
      if (machineInfo != null && machineInfo.length() > 0) {
        appLogNvPairs.addInfo("ProcessingMachineInfo", machineInfo);
      }

      // Create app log message.
      AppLogging.logAppEvent(appLogDoc, m_uiDoc, m_workItemId,
          AssignmentDeliveryConstants.APPLICATION_NAME,
          AssignmentDeliveryConstants.APPLICATION_NAME, appLogNvPairs);

    } catch (Exception e) {
      log.severe(e.getMessage());
      ErrorLoggingService.logError(AssignmentDeliveryErrorCodes.APP_LOG_ERROR,
          null, CLASS_NAME, METHOD_NAME, ErrorLoggingService.SEVERITY.FATAL,
          m_workItemId, e.getMessage(), m_coCd, m_orgId, e);
      throw mLogger.createException(AssignmentDeliveryErrorCodes.APP_LOG_ERROR,
          m_workItemId, e);
    } finally {
      log.exiting(CLASS_NAME, METHOD_NAME);
    }
  }

  // --  New method for Jetta/SIP3.5 - stub return for now.
  public void cancelAssignment(AssignmentServiceContext context)
      throws AssignmentDeliveryException
  {

    final String methodName = "cancelAssignment(AssignmentServiceContext context)";
    mLogger.entering(CLASS_NAME, methodName);

    if (log.isLoggable(Level.FINE)) {
      log.fine("Returning from ARC5DeliveryHandler - cancelAssignment stub.");
    }

    return;
  }

}
