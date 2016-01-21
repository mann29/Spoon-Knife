package com.mitchell.services.business.partialloss.assignmentdelivery.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.apache.xmlbeans.XmlException;

import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.EnvelopeBodyMetadataType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.EmailMessageDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.client.AppraisalAssignmentClient;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentDeliveryServiceDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.ejb.AppraisalAssignmentServiceRemote;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfig;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.dao.CultureDAO;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.AssignmentEmailDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.CustomSettingHelper;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.InternationlizeData;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.InternationlizeDataImpl;
import com.mitchell.services.business.partialloss.eclaimalertsvc.ECAlertSvcClient;
import com.mitchell.services.core.documentstore.client.DocumentStoreClient;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.services.core.filearchive.client.FileArchiveClient;
import com.mitchell.services.core.filearchive.client.FileArchiveServiceRemote;
import com.mitchell.services.core.filearchive.schemas.ArchiveRequestDocument;
import com.mitchell.services.core.filearchive.schemas.ResponseDocument;
import com.mitchell.services.core.mcfsvc.AttachmentObject;
import com.mitchell.services.technical.partialloss.estimate.bo.Attachment;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.services.technical.partialloss.estimate.bo.EstimateDwnldHist;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;
import com.mitchell.utils.mcf.MCFConstants;
import com.mitchell.utils.misc.FileUtils;
import com.mitchell.utils.misc.StringUtilities;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class HandlerUtils extends AbstractHandlerUtils
{
	
  protected CustomSettingHelper customSettingHelper;
  private CultureDAO cultureDAO;
  private static final String CLASS_NAME = "HandlerUtils";
  
  /* Mitchell NV Pairs in ME Doc */
  private static final String CLAIM_ID = "ClaimId";
  private static final String EXPOSURE_ID = "ExposureId";
  private static final String SYSTEM_USERID = "SystemUserId";

  private final AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(
	      this.getClass().getName());

  public long archieveAssignment(final UserInfoDocument userInfo,
      final File mcfFile, final String workItemId)
  {
    final String methodName = "archieveAssignment";
    mLogger.entering(methodName);

    long arid = 0;

    String farDirName = null;

    try {

      // copy assignment file to archive base source directory
      farDirName = FileArchiveClient.copyArchiveItemToSource(
          AssignmentDeliveryConstants.MODULE_NAME, mcfFile.getPath(), false); // do not delete after copy

      final ArchiveRequestDocument archReqDoc = FileArchiveClient
          .getArchiveRequestDoc(userInfo, workItemId,
              AssignmentDeliveryConstants.APPLICATION_NAME,
              AssignmentDeliveryConstants.MODULE_NAME,
              // mcfFileStagingRelativePath,
              farDirName, FileArchiveClient.FILE_TYPE_MCF, null, false, true,
              false);

      final FileArchiveServiceRemote farejb = FileArchiveClient.getClientEJB();
      final ResponseDocument resp = farejb.fileArchiveRequest(archReqDoc);

      if (resp.getResponse().getError().getErrorCode() == 0) {
        arid = resp.getResponse().getRequestID();
      } else {
        throw new Exception("Received Error Code = "
            + resp.getResponse().getError().getErrorCode()
            + " while archiving " + mcfFile.getName());
      }
    } catch (final Exception e) {
      mLogger.severe(e.getMessage());
    } finally {
      if (arid == 0) {
        mLogger.severe("Unable to archive MCF file: "
            + mcfFile.getAbsolutePath());
        ErrorLoggingService.logError(
            AssignmentDeliveryErrorCodes.MCF_FAR_ERROR, null, CLASS_NAME,
            methodName, ErrorLoggingService.SEVERITY.FATAL, workItemId,
            "Unable to archive MCF file: " + mcfFile.getAbsolutePath(),
            userInfo.getUserInfo().getOrgCode(), 0, null);
      }
    }

    return arid;

  }

  private String getStagingDir()
      throws Exception
  {
    String stagingDir = null;

    final String stagingRootDir = FileArchiveClient.getRootArchiveRequestDir();
    if (stagingRootDir == null || stagingRootDir.length() == 0) {
      throw new Exception("File Archive root directory is not defined");
    }
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("stagingRootDir: " + stagingRootDir);
    }

    final String stagingSubDir = getStagingSubDir();

    stagingDir = stagingRootDir + stagingSubDir;
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("stagingDir: " + stagingDir);
    }

    return stagingDir;
  }

  private String getStagingSubDir()
      throws AssignmentDeliveryException
  {
    String stagingSubDir = AssignmentDeliveryConfig
        .getFileArchiveStagingSubdir().trim();
    if (stagingSubDir == null || stagingSubDir.length() == 0) {
      mLogger
          .warning("StagingSubDir is not defined in System Configuration, so using default one = "
              + AssignmentDeliveryConstants.DEFAULT_STAGING_SUBDIR);
      stagingSubDir = AssignmentDeliveryConstants.DEFAULT_STAGING_SUBDIR;
    }
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("stagingSubDir: " + stagingSubDir);
    }

    return stagingSubDir;
  }

  public CIECADocument getCiecaDocumentFromMitchellEnvelope(
      final MitchellEnvelopeDocument document, final String workItemId)
      throws MitchellException, XmlException
  {
    return getCiecaDocumentFromMitchellEnvelope(document,
        AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER, workItemId);
  }

  public CIECADocument getCiecaDocumentFromMitchellEnvelope(
      final MitchellEnvelopeDocument document, final String ciecaId,
      final String workItemId)
      throws MitchellException, XmlException
  {
    final MitchellEnvelopeHelperProxy helper = new MitchellEnvelopeHelperProxyImpl();
    helper.setEnvelope(document);
    final EnvelopeBodyType body = helper.getEnvelopeBody(ciecaId);
    if (body == null) {
      throw new NullPointerException("Could not get body for[" + ciecaId
          + "] in " + helper.getEnvelope());
    }
    final EnvelopeBodyMetadataType metadata = body.getMetadata();
    final String xmlBeanClassname = metadata.getXmlBeanClassname();
    final String contentString = helper.getEnvelopeBodyContentAsString(body);
    CIECADocument ciecaDoc = null;
    if (xmlBeanClassname == null
        || xmlBeanClassname.equals(CIECADocument.class.getName())) {
      ciecaDoc = CIECADocument.Factory.parse(contentString);
    } else {
      mLogger.severe("MitchellEnvelope does not contain CIECA Document.");
      ErrorLoggingService
          .logError(
              AssignmentDeliveryErrorCodes.INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR,
              null, CLASS_NAME, "getCiecaDocumentFromMitchellEnvelope",
              ErrorLoggingService.SEVERITY.FATAL, workItemId,
              "MitchellEnvelope does not contain CIECA Document.", null, 0,
              null);
      throw mLogger
          .createException(
              AssignmentDeliveryErrorCodes.INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR,
              workItemId);
    }
    return ciecaDoc;
  }

  /**
   * This method returns an CIECADocument xml extracted from the provided
   * MitchellEnvelope (if present)
   * 
   * @param mEnvDoc
   *          MitchellEnvelopeDocument, input
   * @param workItemId
   *          workItemID for this workflow instance
   * @param returnException
   *          boolean, if true, return Exception
   * 
   * @return Returns an CIECADocument xml, if present in mEnvDoc
   * 
   */
  public CIECADocument getCiecaDocFromMitchellEnv(
      final MitchellEnvelopeDocument mEnvDoc, final String workItemId)
      throws Exception
  {
    final String methodName = "getCiecaDoc";
    mLogger.entering(CLASS_NAME, methodName);

    final CIECADocument ciecaDoc = null;

    final MitchellEnvelopeHelper mitchellEnvHelper = new MitchellEnvelopeHelper(
        mEnvDoc);

    // Get CIECADocument
    final EnvelopeBodyType envelopeBody = mitchellEnvHelper
        .getEnvelopeBody(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER);

    return extractCiecaDoc(workItemId, methodName, ciecaDoc, mitchellEnvHelper,
        envelopeBody);
  }

  /**
   * @param workItemId
   * @param methodName
   * @param ciecaDoc
   * @param mitchellEnvHelper
   * @param envelopeBody
   * @return
   * @throws MitchellException
   * @throws XmlException
   * @throws AssignmentDeliveryException
   */
  private CIECADocument extractCiecaDoc(final String workItemId,
      final String methodName, CIECADocument ciecaDoc,
      final MitchellEnvelopeHelper mitchellEnvHelper,
      final EnvelopeBodyType envelopeBody)
      throws MitchellException, XmlException, AssignmentDeliveryException
  {
    if (envelopeBody != null) {
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("***** DEBUG getCiecaDoc: Have a getEnvelopeBody for Type: CIECABMSAssignmentAddRq");
      }
    }
    if (envelopeBody == null) {
      throw new NullPointerException("No envelope body to extract CIECA from"
          + mitchellEnvHelper.getEnvelope().toString());
    }

    final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
    final String xmlBeanClassname = metadata.getXmlBeanClassname();
    final String contentString = mitchellEnvHelper
        .getEnvelopeBodyContentAsString(mitchellEnvHelper
            .getEnvelopeBody(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER));

    if (xmlBeanClassname == null
        || xmlBeanClassname.equals(CIECADocument.class.getName())) {
      ciecaDoc = CIECADocument.Factory.parse(contentString);
    } else {
      mLogger.severe("MitchellEnvelope does not contain CIECA Document.");
      ErrorLoggingService
          .logError(
              AssignmentDeliveryErrorCodes.INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR,
              null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
              workItemId, "MitchellEnvelope does not contain CIECA Document.",
              null, 0, null);
      throw mLogger
          .createException(
              AssignmentDeliveryErrorCodes.INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR,
              workItemId);
    }

    mLogger.exiting(CLASS_NAME, methodName);
    return ciecaDoc;
  }

  /**
   * 
   * @return
   */
  public static boolean hasApplCode(final UserInfoDocument uiDoc,
      final String applAccessCode)
  {

    boolean retVal = false;

    final String[] allApplAccessCodes = uiDoc.getUserInfo().getAppCodeArray();

    final int numApplAccessCodes = allApplAccessCodes.length;

    for (int i = 0; i < numApplAccessCodes; i++) {

      final String nextAppAccessCode = allApplAccessCodes[i];
      if (nextAppAccessCode != null
          && nextAppAccessCode.equalsIgnoreCase(applAccessCode)) {
        retVal = true;
        break;
      }
    }

    return retVal;
  }

  /**
   * Instance method due to dependencies.
   * 
   * @param uiDoc
   * @param errorCode
   * @param message
   * @param className
   * @param methodName
   * @param workItemId
   */
  public void logWarning(final UserInfoDocument uiDoc, final int errorCode,
      final String message, final String className, final String methodName,
      final String workItemId)
  {
    logWarningError(uiDoc, errorCode, message, className, methodName,
        workItemId);
  }

  /**
   * 
   * @param uiDoc
   * @param errorCode
   * @param message
   * @param className
   * @param methodName
   * @param workItemId
   * @deprecated Use the
   *             {@link #logWarning(UserInfoDocument, int, String, String, String, String)
   *             instance method} as this method call has dependencies.
   */

  public static void logWarningError(final UserInfoDocument uiDoc,
      final int errorCode, final String message, final String className,
      final String methodName, final String workItemId)
  {

    final UserInfoType uiType = uiDoc.getUserInfo();
    final String coCd = uiType.getOrgCode();
    final int orgId = new Integer(uiType.getOrgID()).intValue();

    ErrorLoggingService.logError(errorCode, null, className, methodName,
        ErrorLoggingService.SEVERITY.WARNING, workItemId, message, coCd, orgId,
        null);
  }

  public boolean isOriginalAssignment(final CIECADocument ciecaDoc)
  {
    final String methodName = "isOriginalAssignment";
    mLogger.entering(CLASS_NAME, methodName);
    boolean result = false;

    if (ciecaDoc.getCIECA().getAssignmentAddRq().getDocumentInfo()
        .getDocumentVerArray()[0].getDocumentVerCode().equals("EM")) {
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("\nIt is an Original Assignment");
      }
      result = true;
    } else {
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("\nIt is an Supplement Assignment");
      }
      result = false;
    }
    return result;
  }

  /**
   * This method verifies that CIECADocument provided is an Original or
   * Supplement
   * 
   * @param ciecaDoc
   *          CIECADocument
   * 
   * @return Returns boolean - true, if ciecaDoc is an Original or Supplement,
   *         else false
   * 
   */
  public boolean isOriginalOrSupplementAssignment(final CIECADocument ciecaDoc)
  {
    final String methodName = "isOriginalAssignment";
    mLogger.entering(CLASS_NAME, methodName);
    boolean result = false;

    if (ciecaDoc.getCIECA().getAssignmentAddRq().getDocumentInfo()
        .getDocumentVerArray()[0].getDocumentVerCode().equals("EM")) {
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("\nThis bms is an Original Assignment");
      }
      result = true;
    } else if (ciecaDoc.getCIECA().getAssignmentAddRq().getDocumentInfo()
        .getDocumentVerArray()[0].getDocumentVerCode().equals("SV")) {
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("\nThis bms is a Supplement Assignment");
      }
      result = true;
    } else {
      mLogger.severe("ERROR CASE: Not an Original or Supplement Assignment!!");
      result = false;
    }
    return result;
  }

  /**
   * This method returns an AdditionalAppraisalAssignmentInfoDocument xml
   * extracted from the provided MitchellEnvelope (if present)
   * 
   * @param mEnvDoc
   *          MitchellEnvelopeDocument, input
   * @param workItemId
   *          workItemID for thsi workflow instance
   * @param returnException
   *          boolean, if true, return Exception
   * 
   * @return Returns an AdditionalAppraisalAssignmentInfoDocument xml, if
   *         present in mEnvDoc
   * 
   */
  public AdditionalAppraisalAssignmentInfoDocument getAAAInfoDocFromMitchellEnv(
      final MitchellEnvelopeDocument mEnvDoc, final String workItemId,
      final boolean returnException)
      throws Exception
  {
    final String methodName = "getAAAInfoDocFromMitchellEnv";
    mLogger.entering(CLASS_NAME, methodName);

    AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = null;

    final MitchellEnvelopeHelper mitchellEnvHelper = new MitchellEnvelopeHelper(
        mEnvDoc);

    // Get AdditionalAppraisalAssignmentInfoDocument, if exists
    EnvelopeBodyType envelopeBody = null;
    envelopeBody = mitchellEnvHelper
        .getEnvelopeBody(AssignmentDeliveryConstants.ME_METADATA_AAAINFO_IDENTIFIER);

    if (envelopeBody != null) {

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("***** DEBUG getAAAInfoDocFromMitchellEnv: Have a getEnvelopeBody for Type: AdditionalAppraisalAssignmentInfo");
      }

      final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("***** DEBUG getAAAInfoDocFromMitchellEnv: after  getMetadata");
      }
      final String xmlBeanClassname = metadata.getXmlBeanClassname();
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("***** DEBUG getAAAInfoDocFromMitchellEnv: Have metadata xmlBeanClassname ="
                + xmlBeanClassname);
      }

      final String contentString = mitchellEnvHelper
          .getEnvelopeBodyContentAsString(mitchellEnvHelper
              .getEnvelopeBody(AssignmentDeliveryConstants.ME_METADATA_AAAINFO_IDENTIFIER));
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("***** DEBUG getAAAInfoDocFromMitchellEnv: Have mitchellEnvHelper contentString ="
                + contentString);
      }

      if (xmlBeanClassname == null
          || xmlBeanClassname
              .equals(AdditionalAppraisalAssignmentInfoDocument.class.getName())) {
        aaaInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory
            .parse(contentString);
      } else {

        // Only return Exception if required by caller, else return
        // aaaInfoDoc as NULL only
        if (returnException) {
          mLogger
              .severe("MitchellEnvelope does not contains AdditionalAppraisalAssignmentInfo Document.");
          ErrorLoggingService
              .logError(
                  AssignmentDeliveryErrorCodes.INVALID_AAAINFO_DOC_IN_MITCHELL_ENVELOPE_ERROR,
                  null,
                  CLASS_NAME,
                  methodName,
                  ErrorLoggingService.SEVERITY.FATAL,
                  workItemId,
                  "MitchellEnvelope does not contains AdditionalAppraisalAssignmentInfo Document.",
                  null, 0, null);
          throw mLogger
              .createException(
                  AssignmentDeliveryErrorCodes.INVALID_AAAINFO_DOC_IN_MITCHELL_ENVELOPE_ERROR,
                  workItemId);
        }
      }

    } else {

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("***** getAAAInfoDocFromMitchellEnv: Returning with a No AdditionalAppraisalAssignmentInfoDocument case. ");
      }
    }

    mLogger.exiting(CLASS_NAME, methodName);

    return aaaInfoDoc;
  }

  /**
   * This method returns an Original Estimate MIE (Disk File) from the
   * provided Estimate Document ID
   * 
   * @param orgEstimateDocumentID
   *          Document Id of the estimate object that is to be retrieved
   *          from CCDB
   * @param workItemId
   *          workItemID for this workflow instance
   * 
   * @return Returns a Disk File of an Original Estimate MIE, if found from
   *         input orgEstimateDocumentID
   * 
   */
  public File getOrigEstimateMieFileFromDocStore(
      final Long orgEstimateDocumentID, final String workItemId)
      throws Exception
  {

    final String methodName = "getOrigEstimateMieFileFromDocStore";
    mLogger.entering(CLASS_NAME, methodName);

    File orgEstMieFile = null;

    final long l_OrgEstimateDocumentID = orgEstimateDocumentID.longValue();
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("********** Debug: getOrigEstimateMieFileFromDocStore. Before EstimatePackageClient.getAttachmentByDocIdEObject: l_OrgEstimateDocumentID= "
              + l_OrgEstimateDocumentID);
    }

    // ====================================================================
    // NEW ESTIMATE PACKAGE METHOD FOR SIP3.5
    EstimatePackageClient estClient = new EstimatePackageClient();
    final Attachment attach = estClient.getAttachmentByDocIdEObject(
        l_OrgEstimateDocumentID, MCFConstants.ATTACH_OBJ_MIE);
    //        final Attachment attach = EstimatePackageClient.getAttachmentByDocIdEObject(l_OrgEstimateDocumentID,
    //                MCFConstants.ATTACH_OBJ_MIE);
    if (attach != null) {
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********** Debug: getOrigEstimateMieFileFromDocStore. After EstimatePackageClient.getAttachmentByDocIdEObject");
      }
      final Long origEstimateMieDocStoreId = attach.getReferenceId();

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********** Debug: getOrigEstimateMieFileFromDocStore. After attach.getReferenceId(),  origEstimateMieDocStoreId = "
                + origEstimateMieDocStoreId);
      }

      final GetDocResponse getResponse = DocumentStoreClient
          .getDocument(origEstimateMieDocStoreId.longValue());
      if (getResponse == null) {

        mLogger
            .severe("Response from DocumentStoreClient.getDocument is null.\nUnable to retrieve Original Mie from DocStore.");

        ErrorLoggingService
            .logError(
                AssignmentDeliveryErrorCodes.ERROR_DOCUMENT_RETRIEVAL_FAILURE_FROM_DOCSTORE,
                null,
                CLASS_NAME,
                methodName,
                ErrorLoggingService.SEVERITY.FATAL,
                workItemId,
                "Response from DocumentStoreClient.getDocument is null. Unable to retrieve Original Mie from DocStore.",
                null, 0, null);
        throw mLogger
            .createException(
                AssignmentDeliveryErrorCodes.ERROR_DOCUMENT_RETRIEVAL_FAILURE_FROM_DOCSTORE,
                workItemId);

      }
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("Got Original MIE file from DocStore: "
            + getResponse.getfilenameoriginal());
      }

      // copy MIE file into working directory
      FileUtils.copyFile(getResponse.getfilenameondisk(), getBmsToMieTempDir(),
          getResponse.getfilenameoriginal());
      FileUtils.deleteFile(getResponse.getfilenameondisk());
      orgEstMieFile = new File(getBmsToMieTempDir() + File.separator
          + getResponse.getfilenameoriginal());

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("Original MIE file copied into working dir: "
            + orgEstMieFile.getPath());
      }
    } else {
      mLogger
          .severe("ERROR - getOrigEstimateMieFileFromDocStore, "
              + "Unable to retrieve Original MIE file for Docstore for this l_OrgEstimateDocumentID= "
              + l_OrgEstimateDocumentID);
    }

    mLogger.exiting(CLASS_NAME, methodName);

    return orgEstMieFile;
  }

  /**
   * This method retrieves the Estimate Object and the Document Object from
   * the CCDB for the provided estimate document id.
   * 
   * @param estimateDocId
   *          Document Id of the estimate object that is to be retrieved
   *          from CCDB.
   * 
   * @return Returns the Estimate object (EstimatePackage Service BO).
   * 
   */
  public Estimate getEstimateFromCCDB(final long estimateDocId)
      throws MitchellException
  {
    Estimate retval = null;

    EstimatePackageClient estClient = new EstimatePackageClient();
    retval = estClient.getEstimateAndDocByDocId(estimateDocId);

    return retval;
  }

  /**
   * This method adds FileAttachements to the AssignmentServiceContext,
   * provided in the input AdditionalAppraisalAssignmentInfoDocument xml and
   * returns an array of FileAttachement (disk files)
   * 
   * @param context
   *          Assignment Delivery - AssignmentServiceContext object
   * @param aaaInfoDoc
   *          AdditionalAppraisalAssignmentInfoDocument xml doc
   * @param workItemId
   *          workItemID for this workflow instance
   * @param returnException
   *          boolean, if true, return Exception
   * 
   * @return Returns an array of FileAttachements (Disk Files)
   * 
   */
  public File[] addFileAttachementsToADContext(
      final AssignmentServiceContext context,
      final AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc,
      final String workItemId, final boolean returnException)
      throws Exception
  {

    final String methodName = "addFileAttachementsToADContext";
    mLogger.entering(CLASS_NAME, methodName);

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("***********Debug: Entering addFileAttachementsToADContext...");
    }

    File[] retVal = null;
    final ArrayList attachmentFileList = new ArrayList();

    try {

      // Get count of FileAttachments found in
      // AdditionalAppraisalAssignmentInfoDocument
      int numOfAttachments = 0;
      numOfAttachments = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
          .getAssociatedAttachments().getFileAttachments()
          .sizeOfFileAttachmentArray();

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********** Debug: addFileAttachementsToADContext. numOfAttachments= "
                + numOfAttachments);
      }

      if (numOfAttachments > 0) {

        final Long fileAttachDocStoreID[] = new Long[numOfAttachments];

        AttachmentObject attachmentObject1 = null;

        for (int i = 0; i < numOfAttachments; i++) {

          if (mLogger.isLoggable(Level.INFO)) {
            mLogger
                .info("***********Debug: in addFileAttachementsToADContext, before getFileAttachmentArray(i).getDocStoreID())");
          }

          fileAttachDocStoreID[i] = Long.valueOf(aaaInfoDoc
              .getAdditionalAppraisalAssignmentInfo()
              .getAssociatedAttachments().getFileAttachments()
              .getFileAttachmentArray(i).getDocStoreID());

          if (mLogger.isLoggable(Level.INFO)) {
            mLogger
                .info("***********Debug: in addFileAttachementsToADContext, fileAttachDocStoreID[i]= "
                    + fileAttachDocStoreID[i]);
          }

          String attachTitle = "";
          String attachFileName = "";
          String attachFileType = "";
          String attachType = "";
          String fileAltAttachType = "";

          if (!aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .getAssociatedAttachments().getFileAttachments()
              .getFileAttachmentArray(i).isNil()) {

            fileAltAttachType = aaaInfoDoc
                .getAdditionalAppraisalAssignmentInfo()
                .getAssociatedAttachments().getFileAttachments()
                .getFileAttachmentArray(i).getAltAttachmentType();
            attachTitle = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
                .getAssociatedAttachments().getFileAttachments()
                .getFileAttachmentArray(i).getFileTitle();
            attachFileType = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
                .getAssociatedAttachments().getFileAttachments()
                .getFileAttachmentArray(i).getFileType();
            attachType = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
                .getAssociatedAttachments().getFileAttachments()
                .getFileAttachmentArray(i).getAttachmentType();
            attachFileName = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
                .getAssociatedAttachments().getFileAttachments()
                .getFileAttachmentArray(i).getFileName();

            if (mLogger.isLoggable(Level.INFO)) {
              mLogger
                  .info("\n***********Debug: in addFileAttachementsToADContext, attachment #[ "
                      + i
                      + " ] \n a. attachTitle= "
                      + attachTitle
                      + " b. attachFileName= "
                      + attachFileName
                      + " c. attachFileType= "
                      + attachFileType
                      + " d. attachType= " + attachType + "\n");
            }
          }

          // 14Sep09 - reviewed with Mark Armanderiz. The
          // ActualFilename EObject attribute
          // will be derived fromthe Attachment File Object provided
          // by ADS to MCFService
          // and this attribute will be added later by MCFService.

          // Note: AltAttachmentType MUST BE provided as an integer in
          // the AAAInfoDoc xml.
          // AltAttachmentType provided is assumed to be an existing
          // EObject type.

          if (mLogger.isLoggable(Level.INFO)) {
            mLogger
                .info("***********Debug: in addFileAttachementsToADContext, before getAttachFileFromDocStore");
          }
          final File attachFile = getAttachFileFromDocStore(
              fileAttachDocStoreID[i], workItemId);
          final String absFilePath = attachFile.getAbsolutePath();

          if (mLogger.isLoggable(Level.INFO)) {
            mLogger
                .info("***********Debug: in addFileAttachementsToADContext, before add to returnArray");
          }
          attachmentFileList.add(attachFile);

          if (mLogger.isLoggable(Level.INFO)) {
            mLogger
                .info("***********Debug: in addFileAttachementsToADContext, After getAttachFileFromDocStore, absFilePath= "
                    + absFilePath);
            mLogger
                .info("\n\n****************************************************************************************************************");
            mLogger
                .info("***********Debug: in addFileAttachementsToADContext, fileAltAttachType= "
                    + fileAltAttachType);
          }

          // Set iFileAltAttachType, default to ATTACH_OBJ_DAT when
          // not provided in AAAInfoDoc.xml
          int iFileAltAttachType = 0;
          if ((attachFileType.equalsIgnoreCase("APPRAISAL_ASSIGNMENT"))
              && (absFilePath.toLowerCase().endsWith(".xml"))) {
            iFileAltAttachType = MCFConstants.ATTACH_OBJ_TEXT_NOTE;

          } else if (attachFileType.equalsIgnoreCase("THUMBNAIL")) {
            iFileAltAttachType = MCFConstants.ATTACH_OBJ_IMAGE;

          } else if (!StringUtilities.isEmpty(fileAltAttachType)) {
            mLogger
                .info("***********Debug: in addFileAttachementsToADContext, \nbefore Integer.parseInt(fileAltAttachType), fileAltAttachType="
                    + fileAltAttachType);
            iFileAltAttachType = Integer.parseInt(fileAltAttachType);

          } else {
            iFileAltAttachType = MCFConstants.ATTACH_OBJ_DAT;
            mLogger
                .info("***********WARNING**** In addFileAttachementsToADContext: fileAltAttachType is UNDEFINDED, Defaulting to AltAttachmentType= 5 (.DAT),\n Filename: absFilePath "
                    + absFilePath);
          }

          if (mLogger.isLoggable(Level.INFO)) {
            mLogger
                .info("***********Debug: in addFileAttachementsToADContext, After Integer.parseInt(fileAltAttachType)= "
                    + iFileAltAttachType);
            mLogger
                .info("****************************************************************************************************************\n\n");
            mLogger
                .info("********** Debug: addFileAttachementsToADContext, Before adding attachement object...");
          }
          // Add the attachement file(s) into attachment objects list
          if (attachFile != null) {

            attachmentObject1 = new AttachmentObject(attachFile,
                iFileAltAttachType);

            // Set Desc1 and Desc2 for attachment properties in ECM
            attachmentObject1.setDesc2(attachTitle + " - " + attachFileName);
            attachmentObject1.setDesc1(attachTitle + " - " + attachFileName);

            final String attObjDesc1Prop = attachmentObject1.getDesc1();
            mLogger
                .info("********** Debug: addFileAttachementsToADContext, attachmentObject1.getDesc1(), attObjDesc1Prop= "
                    + attObjDesc1Prop);

            // NOTE: Image attachments should NOT have a TabNumber
            // set
            // Else 1 all special Attachement should go in Tab 1 for
            // "Estimate Tab "
            // Else 2 all other Misc Attachement should go in Tab 2
            // for "Other Docs Tab"
            boolean b_setNoTabNumber = false;
            if (iFileAltAttachType == 2) {
              b_setNoTabNumber = true;
            } else if (iFileAltAttachType == 5) {
              // 11.20.09 - Since Tab Order is user-definable in
              // the ECM User Template
              // Jetta attachements will be defaulted to the same
              // tab as other
              // attachment objects: MIE, Dispatch Report, etc.
              attachmentObject1.setTabNumber(1);
              // attachmentObject1.setTabNumber(2);
            } else {
              attachmentObject1.setTabNumber(1);
            }

            context.getAttachmentObjects().add(attachmentObject1);

            if (mLogger.isLoggable(Level.INFO)) {
              mLogger
                  .info("********** Debug: addFileAttachementsToADContext, After adding attachement object # [ "
                      + (i + 1) + " ] of [ " + numOfAttachments + " ]");
            }

          }
        }
      }

    } catch (final Exception e) {
      mLogger.severe(e.getMessage());

      ErrorLoggingService.logError(
          AssignmentDeliveryErrorCodes.FILE_ATTACHMENT_ERROR, null, CLASS_NAME,
          methodName, ErrorLoggingService.SEVERITY.FATAL, workItemId,
          e.getMessage(), null, 0, e);
      throw mLogger.createException(
          AssignmentDeliveryErrorCodes.FILE_ATTACHMENT_ERROR, workItemId, e);

    }

    mLogger.exiting(CLASS_NAME, methodName);

    retVal = new File[attachmentFileList.size()];
    attachmentFileList.toArray(retVal);

    return retVal;
  }

  /**
   * This method retrieves an attachementFile (disk file) from DocStore for
   * the provided DocStore Id.
   * 
   * @param attachementDocStoreId
   *          DocStored ID for attachment file
   * @param workItemId
   *          workItemID for this workflow instance
   * 
   * @return Returns an FileAttachement (disk file) retrieved from DocStore
   * 
   */
  public File getAttachFileFromDocStore(final Long attachementDocStoreId,
      final String workItemId)
      throws Exception
  {

    final String methodName = "getAttachFileFromDocStore";
    mLogger.entering(CLASS_NAME, methodName);

    File attachementFile = null;

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("***********Debug: in getAttachFileFromDocStore, before DocumentStoreClient.getDocument,\nattachementDocStoreId.longValue()="
              + attachementDocStoreId.longValue());
    }

    final GetDocResponse getResponse = DocumentStoreClient
        .getDocument(attachementDocStoreId.longValue());

    if (getResponse == null) {

      mLogger
          .severe("Response from DocumentStoreClient.getDocument is null.\nUnable to retrieve Attachment File from DocStore.");
      ErrorLoggingService
          .logError(
              AssignmentDeliveryErrorCodes.ERROR_DOCUMENT_RETRIEVAL_FAILURE_FROM_DOCSTORE,
              null,
              CLASS_NAME,
              methodName,
              ErrorLoggingService.SEVERITY.FATAL,
              workItemId,
              "Response from DocumentStoreClient.getDocument is null. Unable to retrieve Attachment File from DocStore.",
              null, 0, null);
      throw mLogger
          .createException(
              AssignmentDeliveryErrorCodes.ERROR_DOCUMENT_RETRIEVAL_FAILURE_FROM_DOCSTORE,
              workItemId);

    }
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("Got Attachement file from DocStore: "
          + getResponse.getfilenameoriginal());
    }

    // copy attachment file into working directory
    //
    FileUtils.copyFile(getResponse.getfilenameondisk(), getBmsToMieTempDir(),
        getResponse.getfilenameoriginal());
    FileUtils.deleteFile(getResponse.getfilenameondisk());
    attachementFile = new File(getBmsToMieTempDir() + File.separator
        + getResponse.getfilenameoriginal());

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("Attachement File copied into working dir: "
          + attachementFile.getPath());
    }

    mLogger.exiting(CLASS_NAME, methodName);

    return attachementFile;
  }

  /**
   * This method retrieves a supplemenToOriginal notification text file (disk
   * file) from a NAS location provided by an application SET filepath param
   * 
   * @param userInfoDoc
   *          UserInfoDocument (optional)
   * @param workItemId
   *          workItemID for this workflow instance
   * 
   * @return Returns a supplemenToOriginal notification text file (disk file)
   * 
   */
  public File getsuppToOrigTextFile(final UserInfoDocument userInfoDoc,
      final String workItemId)
      throws Exception
  {

    final String methodName = "getsuppToOrigTextFile";
    mLogger.entering(CLASS_NAME, methodName);

    File suppToOrigTextFileNotice = null;

    final String suppToOrigNoticeFilePath = AssignmentDeliveryConfig
        .getSuppToOrigNoticeFilePath();

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("***********In getsuppToOrigTextFile: suppToOrigNoticeFilePath = "
              + suppToOrigNoticeFilePath);
    }

    final File tempFile = new File(suppToOrigNoticeFilePath);

    // If file exists, return file to be added as an attachement to MCF.
    if (tempFile.exists()) {

      mLogger
          .info("***********In getsuppToOrigTextFile: suppToOrigNoticeFilePath file exists!!");

      suppToOrigTextFileNotice = tempFile;

    } else {
      mLogger
          .severe("Notify Text file (Convert_Supp_To_Original_Notice.txt ) not found at configured location.");
      ErrorLoggingService
          .logError(
              AssignmentDeliveryErrorCodes.ERROR_DOCUMENT_RETRIEVAL_FAILURE_FROM_DOCSTORE,
              null,
              CLASS_NAME,
              methodName,
              ErrorLoggingService.SEVERITY.FATAL,
              workItemId,
              "Notify Text file (Convert_Supp_To_Original_Notice.txt ) not found at configured location.",
              null, 0, null);
      throw mLogger
          .createException(
              AssignmentDeliveryErrorCodes.ERROR_DOCUMENT_RETRIEVAL_FAILURE_FROM_DOCSTORE,
              workItemId);

    }

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("In getsuppToOrigTextFile: returning suppToOrigTextFileNotice full path= "
              + suppToOrigTextFileNotice.getAbsoluteFile());
    }

    mLogger.exiting(CLASS_NAME, methodName);

    return suppToOrigTextFileNotice;
  }

  /**
   * This method sends an Assignment Cancellation ECAlert (ie simplified
   * Global Alert message) to the Estimator's EClaim inbox for the provided
   * ClaimNumber
   * 
   * @param ciecaClaimNumber
   *          ClaimNumber (String) for Original or Supplement Assignment
   * @param userInfoDoc
   *          UserInfoDocument for Estimator to receive the ECAlert
   * @param isOriginal
   *          boolean, true indicates Original Assignment, false indicates
   *          Supplement Assignment
   * @param workItemId
   *          workItemID for this workflow instance
   * 
   * @return Returns - none
   * 
   */
  public void sendCancellationECAlert(final String ciecaClaimNumber,
      final UserInfoDocument userInfoDoc, final boolean isOriginal,
      final String workItemId)
      throws Exception
  {

    final String methodName = "sendCancellationECAlert";
    mLogger.entering(CLASS_NAME, methodName);

    // Design/Implementation Assumptions & Constraints (09.15.09) --
    //
    // 1a. CompanyCode and UserID will be obtained from the proper UserInfo
    // doc provided in the ADS Context.
    //
    // 1b. workItemID will be obtained from the ADS Context.
    //
    // 2. The Message Content for "msg" sent to ECM AlertService
    // will be provided in a SET File Param with a ciecaClaimNumber
    // appended.
    //
    // 3. Until Spec'd in Reqts, the Originator will be a constant defined
    // as the calling service/app.
    // (see reference ECALIM_ALERT_ORIGIN -
    // StateFarmMCFJava/WorkflowConstants.java )

    // Get Message from a settings file param and originator from Service
    // Constants
    final String cancel_msg_from_settings = AssignmentDeliveryConfig
        .getCancellationAlertMsg();
    final String msg_originator = AssignmentDeliveryConstants.ECLAIM_ALERT_ORIGIN;

    // Set Assignment Type
    String asgTypeStr = "";
    if (isOriginal) {
      asgTypeStr = "Original ";
    } else {
      asgTypeStr = "Supplement ";
    }

    // Append ciecaClaimNumber to aleart message, if provided.
    String msg = "";
    if (ciecaClaimNumber != null && ciecaClaimNumber.length() > 0) {
      msg = asgTypeStr + cancel_msg_from_settings + " [ " + ciecaClaimNumber
          + " ]";
    } else {
      msg = asgTypeStr + cancel_msg_from_settings + " [ UNKNOWN ]";
    }

    // Get UserID and CompanyCode from UserInfo provided
    final String companyCode = userInfoDoc.getUserInfo().getOrgCode();
    final String userID = userInfoDoc.getUserInfo().getUserID();

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("***** sendCancellationECAlert(Debug): msg = " + msg);
      mLogger.info("***** sendCancellationECAlert(Debug): msg_originator = "
          + msg_originator);
      mLogger.info("***** sendCancellationECAlert(Debug): CompanyCode: "
          + companyCode + " and UserID: " + userID);
      mLogger.info("***** sendCancellationECAlert(Debug): ciecaClaimNumber: "
          + ciecaClaimNumber);
      mLogger.info("***** sendCancellationECAlert(Debug): workItemId: "
          + workItemId);
      mLogger
          .info("***** sendCancellationECAlert(Debug): Cancel Message from ASD settings: "
              + cancel_msg_from_settings);
    }

    // Submit a simple Global Alert with Claim Number included in the
    // Message Sent.
    //
    ECAlertSvcClient.sendGlobalAlert(companyCode, userID, msg_originator, msg,
        workItemId);

    /**
     * New proposed Global ECAlertService API for Supplement Assignment
     * Alerts - 09.23.09 (with eClaimEstID, supplementNumber,
     * correctionNumber, estimateDate
     * 
     * ** DEFERRED UNTIL A LATER SIP JETTA "point" RELEASE ***
     * 
     * public static void sendGlobalAlert( String companyCode, String
     * userID, String eClaimEstID, int supplementNumber, int
     * correctionNumber, String estimateDate, String origin, String message,
     * String workItemID) throws ECAlertSvcException
     * 
     * 
     * -- New code snippet for retrieving Supplement Estimate data. Estimate
     * estimateObj = null; EstimatePackageRemote ejb =
     * EstimatePackageClient.getEstimatePackageEJB(); estimateObj =
     * ejb.getEstimateByEstimateDocId(orgEstimateDocumentID);
     * 
     * String clientEstimateID = estimateObj.getClientEstimateId(); Long
     * supplementNumber = estimateObj.getSupplementNumber(); Long
     * correctionNumber = estimateObj.getCorrectionNumber(); java.util.Date
     * commitDate = estimateObj.getCommitDate();
     * 
     */
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("***** sendCancellationECAlert: After calling ECAlertSvcClient.sendGlobalAlert...");
    }

    mLogger.exiting(CLASS_NAME, methodName);

  }

  // ========================================================
  // ====== NEW METHOD FOR HTML SUPPLEMENT REQUEST Q1-2010
  // ========================================================
  /**
   * This method generates/retrieves a Supplement Request document from
   * required inputs (a) an Estimate Document Id and (b) a pair of UserInfo
   * Ids provided in the AssignmentServiceContext
   * 
   * NOTE: The Retrieval method "retrieveSupplementRequestDoc" is a WCAA
   * refactored CARRHelper method
   * 
   * @param context
   *          Assignment Delivery - AssignmentServiceContext object
   * @param workItemId
   *          workItemID for this workflow instance
   * 
   * @return Returns Updated MitchellEnvelopeDocument containing a Supplement
   *         Request Email doc as a new Content Body
   * 
   */
  public MitchellEnvelopeDocument retrieveSupplementRequestXMLDocAsMEDoc(
      final AssignmentDeliveryServiceDTO dto, final String workItemId)
      throws Exception
  {
    final String methodName = "retrieveSupplementRequestXMLDocAsMEDoc";
    mLogger.entering(CLASS_NAME, methodName);

    MitchellEnvelopeDocument updatedMeDoc = null;

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("********ADS - Debug: retrieveSupplementRequestXMLDocAsMEDoc, before AppraisalAssignmentClient.getAppraisalAssignmentEJB().");
    }

    final AppraisalAssignmentServiceRemote ejb = AppraisalAssignmentClient
        .getAppraisalAssignmentEJB();

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("********ADS - Debug: before calling ejb.retrieveSupplementRequestXMLDocAsMEDoc...");
    }

    //
    // Retrieve HTML version of the Supplement Request Notification returned
    // in an ADS Mitchell Envelope.
    //
    updatedMeDoc = ejb.retrieveSupplementRequestXMLDocAsMEDoc(dto, workItemId);

    if (updatedMeDoc == null) {
      final String errmsg = "Error retrieving Shop Supplement HTML Request doc.";
      mLogger.severe(errmsg);
      ErrorLoggingService
          .logError(
              AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_HTML_SUPPLEMENT_REQUEST,
              null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
              workItemId, errmsg, null, 0, null);
      throw mLogger
          .createException(
              AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_HTML_SUPPLEMENT_REQUEST,
              workItemId);
    }

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("\n\n********ADS - Debug: After ejb.retrieveSupplementRequestXMLDocAsMEDoc, "
              + "\nReturn updatedMeDoc= \n" + updatedMeDoc.toString());
    }
    return updatedMeDoc;

  }

  /**
   * This method generates/retrieves a Supplement Request document from
   * required inputs (a) an Estimate Document Id and (b) a pair of UserInfo
   * Ids provided in the AssignmentServiceContext
   * 
   * NOTE: The Retrieval method "retrieveSupplementRequestDoc" is a WCAA
   * refactored CARRHelper method
   * 
   * @param context
   *          Assignment Delivery - AssignmentServiceContext object
   * @param workItemId
   *          workItemID for this workflow instance
   * 
   * @return Returns Supplement Request document (String)
   * 
   */

  public String createSupplementRequestDoc(
      final AssignmentServiceContext context, final String workItemId)
      throws Exception
  {

    final String methodName = "createSupplementRequestDoc";
    mLogger.entering(CLASS_NAME, methodName);

    String suppNotificationSimpleText = "";
    String estimatorCompanyCode = "";
    String senderUserId = "";
    UserInfoDocument senderUserInfo = null;

    try {

      long senderOrgID = -1;
      long estimateDocId = -1;
      long reviewerOrgId = -1;
      final long estimatorOrgId = Long.parseLong(context.getUserInfo()
          .getUserInfo().getOrgID());


      // ---------------------------------------------------------------------
      // Get Sender OrgID - Begin
      //
      // Get CIECADocument from MithellEnvelope
      final CIECADocument ciecaDoc = getCiecaDocFromMitchellEnv(
          context.getMitchellEnvDoc(), workItemId);

      if (ciecaDoc != null) {
        if (mLogger.isLoggable(Level.INFO)) {
          mLogger.info("*****createSupplementRequestDoc, inbound ciecaDoc: "
              + ciecaDoc.toString());
        }
      }

      // Company Code from "Carrier" Estimator UserInfo
      estimatorCompanyCode = context.getUserInfo().getUserInfo().getOrgCode();

      // Get SenderID from Cieca Doc - if available
      senderUserId = getSenderIDFromCieca(ciecaDoc);
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("*****createSupplementRequestDoc, senderUserId: "
            + senderUserId);
        mLogger.info("*****createSupplementRequestDoc, estimatorCompanyCode: "
            + estimatorCompanyCode);
      }

      // Get senderOrgID fom senderUserInfo
      if (senderUserId.length() > 0) {
        final AssignmentDeliveryUtils assignDeliveryUtils = new AssignmentDeliveryUtils();
        senderUserInfo = assignDeliveryUtils.retrieveUserInfo(
            estimatorCompanyCode, senderUserId);
        if (senderUserInfo != null) {
          senderOrgID = Long.parseLong(senderUserInfo.getUserInfo().getOrgID());
          if (mLogger.isLoggable(Level.INFO)) {
            mLogger.info("*****createSupplementRequestDoc, senderUserInfo: "
                + senderUserInfo);
            mLogger.info("*****createSupplementRequestDoc, senderOrgID: "
                + senderOrgID);
          }
        }
      }

      // If Cieaca has Sender UserID, then use the Sender OrgID,
      if (senderOrgID > 0) {
        reviewerOrgId = senderOrgID;

        // Else use the estimatorOrgId or Drp OrgID
      } else {
        if (context.isDrp()) {
          reviewerOrgId = Long.parseLong(context.getDrpUserInfo().getUserInfo()
              .getOrgID());

        } else {
          // TODO need Company System User OrgId for Staff Supplement
          // case ...
          // As alternative is EstimatorOrgID for now...
          reviewerOrgId = estimatorOrgId;
        }
      }

      // Get Sender OrgID - End
      // ---------------------------------------------------------------------

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("\n\n********Debug: createSupplementRequestDoc, estimatorOrgId= "
                + estimatorOrgId);
        mLogger
            .info("********Debug: createSupplementRequestDoc, reviewerOrgId= "
                + reviewerOrgId);
        mLogger
            .info("********Debug: createSupplementRequestDoc, Have a Shop assignment? = "
                + context.isDrp() + "\n\n");
      }

      // Get EstimateDocID from AdditionalAppraisalAssignmentInfoDocument

      AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = null;
      aaaInfoDoc = getAAAInfoDocFromMitchellEnv(context.getMitchellEnvDoc(),
          workItemId, false);
      if (aaaInfoDoc != null) {
        if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .isSetAssignmentDetails()) {
          if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .getAssignmentDetails().isSetRelatedEstimateDocumentID()) {
            estimateDocId = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
                .getAssignmentDetails().getRelatedEstimateDocumentID();
            if (mLogger.isLoggable(Level.INFO)) {
              mLogger
                  .info("********Debug: createSupplementRequestDoc, have RelatedEstimateDocumentID,  estimateDocId= [ "
                      + estimateDocId + " ]\n");
            }
          }
        }
      }

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("\n\n********Debug: createSupplementRequestDoc, before retrieveSupplementRequestDoc");
        mLogger
            .info("********Debug: , Integ. Testing ejb.retrieveSupplementRequestDoc");
        mLogger
            .info("********Debug: createSupplementRequestDoc, estimateDocId= [ "
                + estimateDocId + " ]");
        mLogger
            .info("********Debug: createSupplementRequestDoc, estimatorOrgId= [ "
                + estimatorOrgId + " ]");
        mLogger
            .info("********Debug: createSupplementRequestDoc, reviewerOrgId= [ "
                + reviewerOrgId + " ]\n\n");
      }

      if (estimateDocId > 0) {

        if (mLogger.isLoggable(Level.INFO)) {
          mLogger
              .info("********Debug: createSupplementRequestDoc, before AppraisalAssignmentClient.getAppraisalAssignmentEJB().");
        }

        final AppraisalAssignmentServiceRemote ejb = AppraisalAssignmentClient
            .getAppraisalAssignmentEJB();

        if (ejb != null) {
          if (mLogger.isLoggable(Level.INFO)) {
            mLogger
                .info("********Debug: createSupplementRequestDoc-  AppraisalAssignmentServiceEJB Found.");
          }
        }

        /**
         * Design decision 09.22.09 with Dev/Arch - use CARRHelper
         * notitification refactor into new AAS method New ADS Util
         * method needed to call this AAS method to retrieve the
         * EmailBody public String retrieveSupplementRequestDoc( long
         * estimateDocId, long estimatorOrgId, long reviewerOrgId)
         */
        if (mLogger.isLoggable(Level.INFO)) {
          mLogger
              .info("********Debug: before calling retrieveSupplementRequestDoc...");
        }

        suppNotificationSimpleText = ejb.retrieveSupplementRequestDoc(
            estimateDocId, estimatorOrgId, reviewerOrgId);

        if (suppNotificationSimpleText.length() > 0) {
          if (mLogger.isLoggable(Level.INFO)) {
            mLogger
                .info("********Debug: createSupplementRequestDoc Have a suppNotificationSimpleText!!");
          }
        }

      } else {
        if (mLogger.isLoggable(Level.INFO)) {
          mLogger
              .info("********Debug: createSupplementRequestDoc, Invalid estimateDocId!! estimateDocId=  "
                  + estimateDocId + "\n\n");
        }

        final String errmsg = "createSupplementRequestDoc: Invalid or Missing RelatedEstimateDocumentID, Not found in AdditionalAppraisalAssignmentInfo XML doc.";
        mLogger.severe(errmsg);

        ErrorLoggingService.logError(
            AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
            null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
            workItemId, errmsg, null, 0, null);
        throw mLogger.createException(
            AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
            workItemId);
      }

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********Debug: createSupplementRequestDoc, after retrieveSupplementRequestDoc, suppNotificationSimpleText= \n "
                + suppNotificationSimpleText + "\n\n");
      }

    } catch (final Exception e) {
      mLogger.severe(e.getMessage());

      ErrorLoggingService.logError(
          AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
          workItemId, e.getMessage(), null, 0, e);
      throw mLogger.createException(
          AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          workItemId, e);
    }

    mLogger.exiting(CLASS_NAME, methodName);

    return suppNotificationSimpleText;
  }

  /**
   * This method writes the Supplement Request Text buffer
   * (suppNotificationDoc) to a uniquely named Disk File for use as an
   * attachement.
   * 
   * @param suppNotificationDoc
   *          Supplement Request Notification String buffer
   * @param filePrefix
   *          Prefix for Filename for the DiskFile
   * @param fileExtension
   *          Extension for Filename for the DiskFile
   * @param workItemId
   *          workItemID for this workflow instance
   * 
   * @return Returns suppNotificationDocFile - a uniquely named Supplement
   *         Request doc (disk file)
   * 
   */
  public File writeSupplementRequestDocFile(final String suppNotificationDoc,
      String filePrefix, String fileExtension, final String workItemId)
      throws Exception
  {
    final String methodName = "writeSupplementRequestDocFile";
    mLogger.entering(CLASS_NAME, methodName);

    Writer writer = null;
    File suppNotificationDocFile = null;

    if (filePrefix == null) {
      filePrefix = AssignmentDeliveryConstants.SUPPLEMENT_REQUEST_PREFIX;
    }
    if (fileExtension == null) {
      fileExtension = AssignmentDeliveryConstants.FILE_EXTENSION_TXT;
    }

    try {

      // Get unique filename
      suppNotificationDocFile = FileUtils.createUniqueFile(
          getBmsToMieTempDir(), filePrefix + System.currentTimeMillis(),
          fileExtension, false);

      final String suppAbsFilePath = suppNotificationDocFile.getAbsolutePath();

      // Write out the Supplement Request Text buffer
      // (suppNotificationDoc) to a Disk File
      writer = new BufferedWriter(new FileWriter(suppNotificationDocFile));
      writer.write(suppNotificationDoc);
      if (suppNotificationDocFile.exists()) {
        if (mLogger.isLoggable(Level.INFO)) {
          mLogger
              .info("********Debug: writeSupplementRequestDocFile, suppNotificationDocFile exists!! \n\nsuppNotificationDoc FilePath= "
                  + suppAbsFilePath + "\n\n");
        }
      }

    } catch (final FileNotFoundException e) {

      mLogger.severe("writeSupplementRequestDocFile: File not found error.");
      mLogger.severe(e.getMessage());
      ErrorLoggingService.logError(
          AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
          workItemId, "writeSupplementRequestDocFile: File not found error.",
          null, 0, null);
      throw mLogger.createException(
          AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          workItemId);

    } catch (final IOException e) {

      mLogger.severe("writeSupplementRequestDocFile: File I/O exception.");
      mLogger.severe(e.getMessage());
      ErrorLoggingService.logError(
          AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
          workItemId, "writeSupplementRequestDocFile: File I/O exception.",
          null, 0, null);
      throw mLogger.createException(
          AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          workItemId);

    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (final IOException e) {
        mLogger.severe("writeSupplementRequestDocFile: File I/O exception.");
        mLogger.severe(e.getMessage());
        ErrorLoggingService.logError(
            AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
            null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
            workItemId, "writeSupplementRequestDocFile: File I/O exception.",
            null, 0, null);
        throw mLogger.createException(
            AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
            workItemId);

      }
    }

    mLogger.exiting(CLASS_NAME, methodName);
    return suppNotificationDocFile;
  }

  /**
   * This method writes the Supplement Request Text buffer
   * (suppNotificationDocText) to a uniquely named Disk File for use as an
   * attachement.
   * 
   * @param suppNotificationDocText
   *          Supplement Request Notification Text buffer
   * @param workItemId
   *          workItemID for this workflow instance
   * 
   * @return Returns suppNotificationDocFile - a uniquely named Supplement
   *         Request doc (disk file)
   * 
   */
  public File getSupplementRequestDocFile(final String suppNotificationDocText,
      final String workItemId)
      throws Exception
  {

    final String methodName = "getSupplementRequestDocFile";
    mLogger.entering(CLASS_NAME, methodName);

    Writer writer = null;
    File suppNotificationDocFile = null;

    try {

      // Get unique filename
      suppNotificationDocFile = FileUtils.createUniqueFile(
          getBmsToMieTempDir(),
          AssignmentDeliveryConstants.SUPPLEMENT_REQUEST_PREFIX
              + System.currentTimeMillis(),
          AssignmentDeliveryConstants.FILE_EXTENSION_TXT, false);

      final String suppAbsFilePath = suppNotificationDocFile.getAbsolutePath();

      // Write out the Supplement Request Text buffer
      // (suppNotificationDocText) to a Disk File
      writer = new BufferedWriter(new FileWriter(suppNotificationDocFile));
      writer.write(suppNotificationDocText);
      if (suppNotificationDocFile.exists()) {
        if (mLogger.isLoggable(Level.INFO)) {
          mLogger
              .info("********Debug: getSupplementRequestDocFile, suppNotificationDocFile exists!! \n\nsuppNotificationDoc FilePath= "
                  + suppAbsFilePath + "\n\n");
        }
      }

    } catch (final FileNotFoundException e) {

      mLogger.severe("getSupplementRequestDocFile: File not found error.");
      mLogger.severe(e.getMessage());
      ErrorLoggingService.logError(
          AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
          workItemId, "getSupplementRequestDocFile: File not found error.",
          null, 0, null);
      throw mLogger.createException(
          AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          workItemId);

    } catch (final IOException e) {

      mLogger.severe("getSupplementRequestDocFile: File I/O exception.");
      mLogger.severe(e.getMessage());
      ErrorLoggingService.logError(
          AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
          workItemId, "getSupplementRequestDocFile: File I/O exception.", null,
          0, null);
      throw mLogger.createException(
          AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          workItemId);

    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (final IOException e) {
        mLogger.severe("getSupplementRequestDocFile: File I/O exception.");
        mLogger.severe(e.getMessage());
        ErrorLoggingService.logError(
            AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
            null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
            workItemId, "getSupplementRequestDocFile: File I/O exception.",
            null, 0, null);
        throw mLogger.createException(
            AssignmentDeliveryErrorCodes.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
            workItemId);

      }
    }

    mLogger.exiting(CLASS_NAME, methodName);
    return suppNotificationDocFile;
  }

  /**
   * This method is a helper method which extracts EmailMessageDocument from
   * MitchellEnvelope Document.
   * 
   * @param meHelper
   *          MitchellEnvelopeHelper, input
   * 
   * @return EmailMessageDocument EmailMessageDocument
   * 
   * @throws Exception
   *           in case MitchellEnvelope Document doesn't contains
   *           EmailMessageDocument
   */
  private EmailMessageDocument getSuppRequestEmailDocFromME(
      final MitchellEnvelopeHelper meHelper)
      throws Exception
  {

    final String methodName = "getSuppRequestEmailDocFromME";
    mLogger.entering(CLASS_NAME, methodName);

    EmailMessageDocument supplementRequestDoc = null;
    String contentString = null;

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("getSuppRequestEmailDocFromME - Input Recieved: MitchellEnvelopeDocument: "
              + meHelper.getDoc());
    }

    final EnvelopeBodyType envelopeBody = meHelper
        .getEnvelopeBody("supplementrequestemail");
    final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
    final String xmlBeanClassname = metadata.getXmlBeanClassname();

    contentString = meHelper.getEnvelopeBodyContentAsString(envelopeBody);

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("getSuppRequestEmailDocFromME: Retrieved EmailMessageDocument"
              + " from meHelper as String is:" + contentString);
    }

    if (xmlBeanClassname == null
        || xmlBeanClassname.equals(EmailMessageDocument.class.getName())) {

      supplementRequestDoc = EmailMessageDocument.Factory.parse(contentString);

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("getSuppRequestEmailDocFromME: Retrieved EmailMessageDocument"
                + " by parsing ContentString:" + supplementRequestDoc);
      }

    } else {
      final String errMsg = "MitchellEnvelope does not contains EmailMessageDocument";
      throw new MitchellException(CLASS_NAME, "getSuppRequestEmailDocFromME",
          errMsg);
    }

    mLogger.exiting(CLASS_NAME, methodName);

    return supplementRequestDoc;
  }

  /**
   * This method sends a Supplement Assignment Notification Email/Fax to
   * NonStaff user
   * 
   * @param context
   *          Assignment Delivery - AssignmentServiceContext object
   * @param workItemId
   *          workItemID for this workflow instance
   * @param returnException
   *          boolean, if true, return Exception
   * 
   * @return Returns - none
   * 
   */
  public void sendNonStaffSuppNotification(
      final AssignmentServiceContext context, final String workItemId,
      final boolean returnException)
      throws Exception
  {

	    final String methodName = "sendNonStaffSuppNotification";
	    mLogger.entering(CLASS_NAME, methodName);

    AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = null;

    final AssignmentDeliveryUtils assignDeliveryUtils = new AssignmentDeliveryUtils();

    // Get CIECADocument from MithellEnvelope
    final CIECADocument ciecaDoc = getCiecaDocFromMitchellEnv(
        context.getMitchellEnvDoc(), workItemId);

    MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(
        context.getMitchellEnvDoc());
    ;
    String overrideFlag = meHelper
        .getEnvelopeContextNVPairValue(AssignmentEmailDeliveryConstants.EMAIL_OVERRIDE_CHECK);

    if (ciecaDoc != null) {
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("*****sendNonStaffSuppNotification, inbound ciecaDoc: "
            + ciecaDoc.toString());
      }
    }

    // Get the AdditionalAppraisalAssignmentInfo.xml doc, if exists
    aaaInfoDoc = getAAAInfoDocFromMitchellEnv(context.getMitchellEnvDoc(),
        workItemId, false);

    if (aaaInfoDoc != null) {
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("*****sendNonStaffSuppNotification, inbound aaaInfoDoc:\n "
                + aaaInfoDoc.toString());
      }
    }

    // =======================================================================================================
    //
    // First Get Default Appraiser Email Address (Carrier or Shop),
    // then Check for additional Notification Email Addresses provided by
    // WCAA
    //
    String emailRecipients[] = null;
    String toAddresses = "";
    String toAddressDB = "";
    String toAddressDB_loc1 = "";
    String toAddressDB_loc2 = "";
    String toCCAddresses = "";
    final boolean notifyEmailToRecipients = false;
    final boolean notifyEmailCCRecipients = false;
    boolean toEmailNotificationFlag = false;
    boolean ccEmailNotficationFlag = false;
    boolean sendEmailNotification = false;

    // Get a single email address from Ins Carrier or DRP/Bodyshop UserInfo
    // object, if exists.
    // First check for email in Insurance Carrier Userinfo (CC), then
    // BodyShop UserInfo (BS), else throw clean error.
    toAddressDB_loc1 = context.getUserInfo().getUserInfo().getEmail();
    toAddressDB_loc2 = context.getDrpUserInfo().getUserInfo().getEmail();

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("********Debug:  sendNonStaffSuppNotification, toAddressDB_loc1 (INS)= "
              + toAddressDB_loc1);
      mLogger
          .info("********Debug:  sendNonStaffSuppNotification, toAddressDB_loc2 (BS)= "
              + toAddressDB_loc2);
    }

    // Use email from Insurance Carrier UserInfo, if not null
    if (toAddressDB_loc1 != null && toAddressDB_loc1.length() > 0) {
      toAddressDB = toAddressDB_loc1;

      // Use email from DRP/Bodyshop UserInfo, if not null
    } else if (toAddressDB_loc2 != null && toAddressDB_loc2.length() > 0) {
      toAddressDB = toAddressDB_loc2;

    } else {
      // Else throw Exception error.
      mLogger
          .severe("Email Notification Failure!! Unable to retrieve Email Recipient Address from either UserInfo Object.");
      ErrorLoggingService
          .logError(
              AssignmentDeliveryErrorCodes.EMAIL_NOTIFICATION_ERROR,
              null,
              CLASS_NAME,
              methodName,
              ErrorLoggingService.SEVERITY.FATAL,
              workItemId,
              "Email Notification Failure, Unable to retrieve Email Recipient Address from either UserInfo Object.",
              null, 0, null);
      throw mLogger.createException(
          AssignmentDeliveryErrorCodes.EMAIL_NOTIFICATION_ERROR, workItemId);
    }

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("********Debug:  sendNonStaffSuppNotification, DEFAULT Appraiser, toAddressDB= "
              + toAddressDB);
    }

    // Get AdditionalInfo ToEmail Addresses, if provided
    //
    if (aaaInfoDoc != null) {
      if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo() != null
          && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .isSetNotificationDetails()
          && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .getNotificationDetails().isSetNotificationEmailTo()) {
        toEmailNotificationFlag = aaaInfoDoc
            .getAdditionalAppraisalAssignmentInfo().getNotificationDetails()
            .getNotificationEmailTo().getNotifyRecipients();

        if (mLogger.isLoggable(Level.INFO)) {
          mLogger
              .info("\n\n*****Additional Info Doc toEmailNotificationFlag = "
                  + toEmailNotificationFlag);
        }
      }
      // If alternate ToEmail Addresses found, append to list
      if (toEmailNotificationFlag) {
        sendEmailNotification = true;
        if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .isSetNotificationDetails()
            && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
                .getNotificationDetails().isSetNotificationEmailTo()) {
          emailRecipients = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .getNotificationDetails().getNotificationEmailTo()
              .getEmailAddressArray();

          // TT 0016683
          //
          // Rule: Always include the Appraiser email address from
          // either the Ins Carrier or dRP/Bodyshop UserInfo object
          // in the EmailTo address, else error condition thrown
          // above.
          // De-Dup email addresses
          //
          toAddresses += toAddressDB;
          final int len_toEmailRcpList = emailRecipients.length;

          if (mLogger.isLoggable(Level.INFO)) {
            mLogger.info(">>>> *****DEFAULT Appraiser - toAddresses= "
                + toAddresses);
            mLogger
                .info("*****Additional Info Doc contains toAddresses, emailRecipients.length= "
                    + len_toEmailRcpList);
          }

          if (len_toEmailRcpList > 0) {
            for (int i = 0; i < len_toEmailRcpList; i++) {

              if (mLogger.isLoggable(Level.INFO)) {
                mLogger.info("*****Currrent i= " + i);
              }

              if (!toAddressDB.equalsIgnoreCase(emailRecipients[i])) {
                toAddresses += ",";
                toAddresses += emailRecipients[i];

                if (mLogger.isLoggable(Level.INFO)) {
                  mLogger
                      .info("*****1a) Appended next 'toEmail' emailRecipients[i]= "
                          + emailRecipients[i]
                          + "\tand i="
                          + i
                          + "\tupddated toAddresses= [ " + toAddresses + " ]");
                }

                if (i < len_toEmailRcpList) {
                  toAddresses += ",";

                  if (mLogger.isLoggable(Level.INFO)) {
                    mLogger
                        .info("*****1b) Append comma, Updated list for toAddresses= [ "
                            + toAddresses + " ]\t and i=" + i);
                  }

                }

                if (mLogger.isLoggable(Level.INFO)) {
                  mLogger.info("*****2) Updated list for toAddresses= [ "
                      + toAddresses + " ]\t and i=" + i);
                }

              }
            }
          } else {
            if (mLogger.isLoggable(Level.INFO)) {
              mLogger
                  .info("*****Additional Info Doc does not contain toAddresses");
            }
            toAddresses = null;
            toEmailNotificationFlag = false;
          }
        }
      }
    }

    if (toAddresses.endsWith(",")) {

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********Debug: Strip last comma, sendNonStaffSuppNotification, before toAddresses.length()= "
                + toAddresses.length());
      }

      toAddresses = toAddresses.substring(0, (toAddresses.length() - 1));

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********Debug: Strip last comma, sendNonStaffSuppNotification, after toAddresses.length()= "
                + toAddresses.length());
        mLogger
            .info("********Debug: Strip last comma, sendNonStaffSuppNotification, toAddresses= "
                + toAddresses);
      }
    }

    //
    // Get AdditionalInfo for CCEmail Addresses, if provided
    //
    emailRecipients = null;
    if (aaaInfoDoc != null) {
      sendEmailNotification = true;
      if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo() != null
          && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .isSetNotificationDetails()
          && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .getNotificationDetails().isSetNotificationEmailCC()) {
        ccEmailNotficationFlag = aaaInfoDoc
            .getAdditionalAppraisalAssignmentInfo().getNotificationDetails()
            .getNotificationEmailCC().getNotifyRecipients();

        if (mLogger.isLoggable(Level.INFO)) {
          mLogger.info("\n\n*****Additional Info Doc ccEmailNotficationFlag = "
              + ccEmailNotficationFlag);
        }

      }
      if (ccEmailNotficationFlag) {
        if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .isSetNotificationDetails()
            && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
                .getNotificationDetails().isSetNotificationEmailCC()) {
          emailRecipients = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .getNotificationDetails().getNotificationEmailCC()
              .getEmailAddressArray();

          final int len_ccEmailRcpList = emailRecipients.length;

          if (mLogger.isLoggable(Level.INFO)) {
            mLogger
                .info("\n\n*****Additional Info Doc contains CCAddresses, emailRecipients.length= "
                    + len_ccEmailRcpList);
          }

          if (len_ccEmailRcpList > 0) {
            for (int i = 0; i < len_ccEmailRcpList; i++) {

              if (mLogger.isLoggable(Level.INFO)) {
                mLogger.info("*****1a) CCEmail: emailRecipients[i]= "
                    + emailRecipients[i] + "\t and i=" + i);
              }

              toCCAddresses += emailRecipients[i];
              if (i < len_ccEmailRcpList) {
                toCCAddresses += ",";

                if (mLogger.isLoggable(Level.INFO)) {
                  mLogger
                      .info("*****1b) Append comma, Updated list for toCCAddresses= [ "
                          + toCCAddresses + " ]\t and i=" + i);
                }

              }

              if (mLogger.isLoggable(Level.INFO)) {
                mLogger.info("*****2) Updated list for toCCAddresses= [ "
                    + toCCAddresses + " ]\t and i=" + i);
              }

            }
          } else {
            mLogger
                .info("*****Additional Info Doc does not contain toCCAddresses");
            toCCAddresses = null;
            ccEmailNotficationFlag = false;
          }
        }
      }
    }

    if (toCCAddresses.endsWith(",")) {

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********Debug: Strip last comma, sendNonStaffSuppNotification, before toCCAddresses.length()= "
                + toCCAddresses.length());
      }

      toCCAddresses = toCCAddresses.substring(0, (toCCAddresses.length() - 1));

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********Debug: Strip last comma, sendNonStaffSuppNotification, after toAddresses.length()= "
                + toCCAddresses.length());
        mLogger
            .info("********Debug: Strip last comma, sendNonStaffSuppNotification, toAddresses= "
                + toCCAddresses);
      }
    }

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("********Debug: 1) sendNonStaffSuppNotification, toEmailNotificationFlag= "
              + toEmailNotificationFlag);
      mLogger
          .info("********Debug: 1a) sendNonStaffSuppNotification, toAddresses= "
              + toAddresses);
      mLogger
          .info("********Debug: 1) sendNonStaffSuppNotification, toCCAddresses= "
              + toCCAddresses);
    }

    //
    // =======================================================================================================
    //
    // Only Send FAX notification if provided in WCAA
    //
    String faxRecipients[] = null;
    String toFaxNumbers = "";
    boolean sendFaxNotification = false;
    boolean altSendFaxNotification = false;

    final String fromName = AssignmentDeliveryConfig.getPrimaryEmailFromName();
    final String fromNumber = AssignmentDeliveryConfig
        .getPrimaryFaxFromNumber();
    final String fromAddress = AssignmentDeliveryConfig.getPrimaryFaxFromAddr();

    // Get WCAA AdditionalInfo Fax Numbers, if provided
    // Only Send FAX notification if provided in WCAA
    //
    if (aaaInfoDoc != null) {

      // Check for alternate Fax Numbers
      if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo() != null
          && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .isSetNotificationDetails()
          && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .getNotificationDetails().isSetNotificationFax()) {
        altSendFaxNotification = aaaInfoDoc
            .getAdditionalAppraisalAssignmentInfo().getNotificationDetails()
            .getNotificationFax().getNotifyRecipients();

        if (mLogger.isLoggable(Level.INFO)) {
          mLogger.info("\n\n*****Additional Info Doc altSendFaxNotification = "
              + altSendFaxNotification);
        }

      }
      // If alternate Fax Numbers found append to list
      if (altSendFaxNotification) {
        final String faxTmp[] = null;
        if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .isSetNotificationDetails()
            && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
                .getNotificationDetails().isSetNotificationFax()) {
          faxRecipients = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .getNotificationDetails().getNotificationFax()
              .getFaxNumberArray();

          final int len_FaxRcpList = faxRecipients.length;

          if (mLogger.isLoggable(Level.INFO)) {
            mLogger
                .info("\n\n*****Additional Info Doc contains Fax Numbers, faxRecipients.length= "
                    + len_FaxRcpList);
          }

          if (len_FaxRcpList > 0) {
            for (int i = 0; i < len_FaxRcpList; i++) {
              toFaxNumbers += faxRecipients[i];

              if (mLogger.isLoggable(Level.INFO)) {
                mLogger.info("*****Adding another faxRecipients[i]= "
                    + faxRecipients[i] + " and i=" + i);
              }

              if (i < (len_FaxRcpList - 1)) {
                toFaxNumbers += ",";
                if (mLogger.isLoggable(Level.INFO)) {
                  mLogger
                      .info("*****1) Append comma, Updated list for toFaxNumbers= "
                          + toFaxNumbers + " and i=" + i);
                }
              }

              if (mLogger.isLoggable(Level.INFO)) {
                mLogger.info("*****2) Updated list for toFaxNumbers= "
                    + toFaxNumbers + " and i=" + i);
              }
              sendFaxNotification = true;
            }
          } else {
            mLogger
                .info("*****Additional Info Doc does not contain alternate Fax Numbers");
            sendFaxNotification = false;
            altSendFaxNotification = false;
          }
        }
      }
    }

    // TT 0016683 - To avoid dup emails in Email To list,
    // Revised logic to append toAddressDB in the AdditionalInfo ToEmail
    // Addresses block above
    // Move code for retrieval of UserInfo emails (toAddressDB) before
    // getting the alternate WCAA ToEmails

    // Carrier Case - No alternate WCAA EmailTo addresses,
    // only use the Appraiser email address from either the Ins Carrier or
    // Bodyshop UserInfo object
    if (!toEmailNotificationFlag) {

      toAddresses = toAddressDB;
      sendEmailNotification = true; // default requires an appraiser
                                    // email, else error

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********Debug: 2) sendNonStaffSuppNotification, No alternate WCAA toEmails!!");
        mLogger
            .info("********Debug: 2) sendNonStaffSuppNotification, toAddressDB= "
                + toAddressDB);
        mLogger
            .info("********Debug: 2) sendNonStaffSuppNotification, toAddresses= "
                + toAddresses);
      }
    }

    String claimNumber = ciecaDoc.getCIECA().getAssignmentAddRq()
        .getClaimInfo().getClaimNum();
    if (claimNumber.length() > 20) {
      claimNumber = claimNumber.substring(0, 20);
    }
    
    String companyName = getCompanyNameFromCieca(ciecaDoc);

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("********Debug:  sendNonStaffSuppNotification, companyName= "
              + companyName);
    }

    // SAMPLE Subject-Line: New Supplement Assignment from GMAC Insurance
    // Com, Claim#: GC-1007-006602
    //
    String subject = null;
    if (companyName != null) {
    companyName = formatCompName(context, companyName);      
			subject = formatSubject(
					claimNumber,companyName,
					getSubject(
							context.getUserInfo(),
							AssignmentDeliveryConstants.SUPPLEMENT_REQUEST_PREFIX,
							sendEmailNotification));

    } else {
      subject = new String("New Supplement Assignment, Claim #: " + claimNumber);
    }

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("\n\n************************************************************");
      mLogger
          .info("********ADS - Debug: Before retrieveSupplementRequestXMLDocAsMEDoc() ");
      mLogger.info("********Debug:  sendNonStaffSuppNotification, subject= "
          + subject);
      mLogger
          .info("************************************************************\n\n");
    }

    // Get Supplement Request Doc (HTML and Text bodies) included in a
    // MitchellEnvelope
    //
    //final MitchellEnvelopeDocument updatedMeDoc = retrieveSupplementRequestXMLDocAsMEDoc(context, workItemId);
    AssignmentDeliveryServiceDTO dto = mapToAppraisalAssignmentDTO(context);
    final MitchellEnvelopeDocument updatedMeDoc = retrieveSupplementRequestXMLDocAsMEDoc(
        dto, workItemId);

    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      if (updatedMeDoc != null) {
        if (updatedMeDoc.toString().length() > 0) {
          mLogger
              .info("\n\n************************************************************");
          mLogger
              .info("********ADS - Debug: After retrieveSupplementRequestXMLDocAsMEDoc() - SUCCESS!!");
          mLogger
              .info("************************************************************\n\n");
          mLogger
              .info("********ADS - Debug: After retrieveSupplementRequestXMLDocAsMEDoc(), "
                  + "updatedMeDoc.toString()=" + updatedMeDoc.toString());
          mLogger
              .info("************************************************************\n\n");

        } else {
          mLogger
              .info("\n\n************************************************************");
          mLogger
              .info("********ADS - Debug: After retrieveSupplementRequestXMLDocAsMEDoc() - FAILED!!");
          mLogger
              .info("************************************************************\n\n");
        }
      }
    }

    // Extract HTML and Text Only versions of the Supplement Request
    //
    String suppNotificationDocText = "";
    String suppNotificationDocHTML = "";
    EmailMessageDocument emailDoc = null;
    if (updatedMeDoc.toString().length() > 0) {
      final MitchellEnvelopeHelper updMEnvHelper = new MitchellEnvelopeHelper(
          updatedMeDoc);
      emailDoc = getSuppRequestEmailDocFromME(updMEnvHelper);
      suppNotificationDocText = emailDoc.getEmailMessage().getTextFormat();
      suppNotificationDocHTML = emailDoc.getEmailMessage().getHTMLFormat();
    }

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("\n\n***************************************************************************************");
      mLogger
          .info("***************************************************************************************");
      mLogger
          .info("********MitchellEnvelopeDocument updatedMeDocDebug: After retrieveSupplementRequestXMLDocAsMEDoc(), "
              + "suppNotificationDocHTML.length()= "
              + suppNotificationDocHTML.length()
              + "suppNotificationDocText.length()= "
              + suppNotificationDocText.length());
      mLogger
          .info("***************************************************************************************");
      mLogger
          .info("***************************************************************************************\n\n");
    }

    // ---------------------------------------------------

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("\n\n********Debug: After createSupplementRequestDoc() suppNotificationDocText= "
              + suppNotificationDocText + "\n\n");
    }

    // Send Email Notification to Default Appraiser Email, plus any
    // AdditionalInfo ToEmails
    //
    //
    final String nonStaffSuppNotificationXsltFilePath = null;
    if (sendEmailNotification && suppNotificationDocText.length() > 0) {

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********Debug: Before assignDeliveryUtils.sendEmailNotification() ");
      }

      if (!("Y").equalsIgnoreCase(overrideFlag)) {
        assignDeliveryUtils.sendEmailNotification(context.getDrpUserInfo(),
            AssignmentDeliveryConfig.getPrimaryEmailFromName(),
            AssignmentDeliveryConfig.getPrimaryEmailFromAddr(), toAddresses,
            toCCAddresses, subject, suppNotificationDocHTML, // String messageBodyHTML,
            suppNotificationDocText, // String messageBodyText,
            nonStaffSuppNotificationXsltFilePath, workItemId);
      } else {
        mLogger.fine("Bypass SecondaryEmail Notification for RCConnect shops");
      }

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********Debug: After assignDeliveryUtils.sendEmailNotification ");
      }
    }

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("********Debug: sendNonStaffSuppNotification, sendFaxNotification= "
              + sendFaxNotification);
      mLogger.info("********Debug: altSendFaxNotification= "
          + altSendFaxNotification);
      mLogger.info("********Debug: toFaxNumbers= " + toFaxNumbers);
      mLogger.info("********Debug: suppNotificationDocText.length= "
          + suppNotificationDocText.length());
      if (!altSendFaxNotification) {
        mLogger
            .info("\n\n********Debug: sendNonStaffSuppNotification, NO FAX NOTIFICAITION WILL BE SENT, altSendFaxNotification= "
                + altSendFaxNotification + "\n\n");

      }
    }

    // Send Fax Notification to WCAA provided Fax Number in AdditionalInfo
    //
    // Rule: Only send Fax when WCAA Fax number is provided!!
    //
    if (sendFaxNotification && altSendFaxNotification
        && suppNotificationDocText.length() > 0) {

      if (mLogger.isLoggable(Level.INFO)) {
        mLogger
            .info("********Debug: Before assignDeliveryUtils.sendFaxNotification() ");
        mLogger
            .info("\n\n********Debug: sendNonStaffSuppNotification, toFaxNumbers = "
                + toFaxNumbers);
        mLogger.info("subject= " + subject);
        mLogger.info("fromName= " + fromName + " fromAddress= " + fromAddress
            + " fromNumber = " + fromNumber);
        mLogger.info("nonStaffSuppNotificationXsltFilePath= "
            + nonStaffSuppNotificationXsltFilePath);
        mLogger.info("workItemId= " + workItemId + "\n\n");
      }
      // Added logging for issue 0019857
      if (mLogger.isLoggable(Level.FINE)) {
        if (suppNotificationDocText != null
            && suppNotificationDocText.length() != 0) {
          mLogger.fine("Fax text [" + suppNotificationDocText + "]");
        } else {
          mLogger.fine("Fax text is ["
              + (suppNotificationDocText == null ? "NULL" : "EMPTY") + "]");
        }
      }
      String subjectForFax = null;
      subjectForFax = subject;
      if (sendFaxNotification && subject.length() > 64) {
        subjectForFax = new String("New Supplement Assignment, Claim #: "
            + claimNumber);
      }

      assignDeliveryUtils.sendFaxNotification(context.getDrpUserInfo(),
          fromName, fromAddress, fromNumber, toFaxNumbers, subjectForFax,
          suppNotificationDocHTML, // suppNotificationDocText,
          nonStaffSuppNotificationXsltFilePath, workItemId);

      if (mLogger.isLoggable(Level.FINE)) {
        mLogger
            .fine("********Debug: After assignDeliveryUtils.sendFaxNotification ");
      }

    }
    mLogger.exiting(CLASS_NAME, methodName);
  }

	/**
	 * This method returns comp name as it is if data centre 
	 * cust setting value is "EU"; else it formats the comp 
	 * name by trimming it upto 21 character length.
	 * @param context
	 * @param companyName
	 * @return
	 * @throws MitchellException
	 */
	protected String formatCompName(final AssignmentServiceContext context,
			String companyName) throws MitchellException {
		// get custome setting for DATA-CENTRE
		UserInfoType userInfo = context.getUserInfo().getUserInfo();
		if(userInfo != null){
		String userID = userInfo.getUserID();
		String coCode = userInfo.getOrgCode();
		String dataCentre = customSettingHelper
				.getUserCustomSetting(
						userID,
						coCode,
						AssignmentDeliveryConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME,
						AssignmentDeliveryConstants.SETTING_DATACENTER);
		// If data center is EU, don't trim the company name for 21 length.
		// In future if some carrier does not want to trim company name, just
		// add another && condition in the IF below.
		if (dataCentre != null
				&& !dataCentre
						.equalsIgnoreCase(AssignmentDeliveryConstants.DATACENTER_EU)) {
			if (companyName.length() > 21) {
				companyName = companyName.substring(0, 21);
			}
		}
		
	  }
		return companyName;
	}

  private String getBmsToMieTempDir()
      throws AssignmentDeliveryException
  {
    String bmsToMieTempDir = AssignmentDeliveryConfig.getTempDir().trim();
    if (bmsToMieTempDir == null || bmsToMieTempDir.length() == 0) {
      mLogger
          .warning("BmsToMieTempDir is not defined in System Configuration, so using default one = "
              + AssignmentDeliveryConstants.SYS_CONFIG_BMS_TO_MIE_TEMP_DIR);
      bmsToMieTempDir = AssignmentDeliveryConstants.SYS_CONFIG_BMS_TO_MIE_TEMP_DIR;
    }

    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("BmsToMieTempDir: " + bmsToMieTempDir);
    }

    return bmsToMieTempDir;
  }

  private String getCompanyNameFromCieca(final CIECADocument ciecaDoc)
  {
    final String methodName = "getCompanyNameFromCieca";
    mLogger.entering(CLASS_NAME, methodName);

    String companyName = null;
    if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo() != null) {
      if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo()
          .getInsuranceCompany() != null) {
        if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo()
            .getInsuranceCompany().getParty() != null) {
          if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo()
              .getInsuranceCompany().getParty().getOrgInfo() != null) {
            if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo()
                .getInsuranceCompany().getParty().getOrgInfo().getCompanyName() != null) {
              companyName = ciecaDoc.getCIECA().getAssignmentAddRq()
                  .getAdminInfo().getInsuranceCompany().getParty().getOrgInfo()
                  .getCompanyName();
            }
          }
        }
      }
    }

    mLogger.exiting(CLASS_NAME, methodName);
    return companyName;
  }

  private String getSenderIDFromCieca(final CIECADocument ciecaDoc)
  {
    final String methodName = "getSenderFromCieca";
    mLogger.entering(CLASS_NAME, methodName);

    String senderID = "";
    if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo() != null) {
      if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo().getSender() != null) {
        if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo().getSender()
            .getParty() != null) {
          if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo()
              .getSender().getParty().getPersonInfo() != null) {
            if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo()
                .getSender().getParty().getPersonInfo().getIDInfoArray(0) != null) {
              senderID = ciecaDoc.getCIECA().getAssignmentAddRq()
                  .getAdminInfo().getSender().getParty().getPersonInfo()
                  .getIDInfoArray(0).getIDNum();

            }
          }
        }
      }
    }

    mLogger.exiting(CLASS_NAME, methodName);
    return senderID;
  }

  /**
   * Method provides ClaimId & Exposure Id.
   * 
   * @param context
   *          Instance of AssignmentServiceContext class
   * @return String array either NULL
   */
  public String[] getClaimExposureIdsResult(
      final AssignmentServiceContext context)
  {

    String[] result = null;
    try {
      final MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(
          context.getMitchellEnvDoc());

      if (meHelper != null) {
        boolean validate = true;

        // Get claimId from NV pair
        final String claimId = meHelper.getEnvelopeContextNVPairValue(CLAIM_ID);
        // Get exposureId from NV pair
        final String claimExposureId = meHelper
            .getEnvelopeContextNVPairValue(EXPOSURE_ID);

        // logic to set result false if claim & exposureId is either
        // null of blank
        if (this.checkForNullOrBlank(claimId)) {
          validate = validate & false;
        } else if (this.checkForNullOrBlank(claimExposureId)) {
          validate = validate & false;
        }

        if (validate) {
          result = new String[2];
          result[0] = claimId;
          result[1] = claimExposureId;
        }

      } else {
        mLogger.warning("MitchellEnvelopeHelper instance is null.");
      }

    } catch (final Exception e) {
      mLogger.warning("ClaimID & ExposureID appending process failed. "
          + e.getMessage());
    }

    return result;
  }

  /**
   * Write a supplement history record.
   * 
   * @param orgEstimateDocumentID
   * @return true if successful, false otherwise.
   * @throws MitchellException
   * @throws RemoteException
   */
  public void writeRequestSupplementHistory(
      final long originalEstimateDocumentId, final String userId,
      final String companyCode)
      throws RemoteException, MitchellException
  {
    mLogger.entering(CLASS_NAME, "writeRequestSupplementHistory");
    if (mLogger.isLoggable(Level.FINE)) {
      mLogger
          .fine("Requesting a reference to EstimatePackageRemote for original estimate document id:["
              + originalEstimateDocumentId + "]");
    }
    EstimatePackageClient estClient = new EstimatePackageClient();
    final Estimate estimate = estClient
        .getEstimateAndDocByDocId(originalEstimateDocumentId);
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("Got a reference for original estimate document id:["
          + originalEstimateDocumentId + "]");
    }
    final EstimateDwnldHist estHistBo = new EstimateDwnldHist();
    if (mLogger.isLoggable(Level.FINE)) {
      mLogger
          .fine("assembling estimate history for original estimate document id:["
              + originalEstimateDocumentId + "]");
    }
    estHistBo.setClientEstimateId(estimate.getClientEstimateId());
    estHistBo.setCoCd(estimate.getCoCd());
    estHistBo.setCommitDate(estimate.getCommitDate());
    estHistBo.setSupplementNumber(estimate.getSupplementNumber());
    estHistBo.setCorrectionNumber(estimate.getCorrectionNumber());
    estHistBo.setDownloadUserCoCode(companyCode);
    estHistBo.setDownloadUserId(userId);
    if (mLogger.isLoggable(Level.FINE)) {
      mLogger
          .fine("Assembled estimate history for original estimate document id:["
              + originalEstimateDocumentId + "]");
      mLogger.fine(estHistBo.toString());
    }
    estClient.saveEstDownloadHistory(estHistBo);
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("Wrote request supplement history for original estimate document id:["
              + originalEstimateDocumentId + "]");
    }
    mLogger.exiting(CLASS_NAME, "writeRequestSupplementHistory");
  }

    /*
    * This method retrieves the line annotation report from Appraisal Assignment Service
    */
    public String getSupplementRequestDoc(long documentId, long estimatorOrgId, long reviewerOrgId) throws MitchellException {
        AppraisalAssignmentServiceRemote ejb = AppraisalAssignmentClient.getAppraisalAssignmentEJB();

        return ejb.retrieveSupplementRequestDoc(documentId, estimatorOrgId, reviewerOrgId);
    }

    /**
   * This method checks if the param string is null or blank
   * 
   * @param string
   *          The String to be checked
   * @return true, if the string variable is either null or blank
   */
  private boolean checkForNullOrBlank(final String string)
{
    boolean status = false;

    if (string == null || "".equals(string)) {
        status = true;
    }
    return status;
}

  private AssignmentDeliveryServiceDTO mapToAppraisalAssignmentDTO(
      AssignmentServiceContext context)
  {

    AssignmentDeliveryServiceDTO dto = new AssignmentDeliveryServiceDTO();
    if (context.getDrpUserInfo() != null) {
      dto.setDrpUserInfo(context.getDrpUserInfo());
    }
    if (context.getUserInfo() != null) {
      dto.setUserInfo(context.getUserInfo());
    }
    if (context.getMitchellEnvDoc() != null) {
      dto.setMitchellEnvDoc(context.getMitchellEnvDoc());
    }
    if (!checkForNullOrBlank(context.getWorkAssignmentId())) {
      dto.setWorkAssignmentId(context.getWorkAssignmentId());
    }

    dto.setDrp(context.isDrp());
    dto.setNonNetWorkShop(context.isNonNetWorkShop());
    return dto;
  }
  
  public CustomSettingHelper getCustomSettingHelper() {
		return customSettingHelper;
  }

  public void setCustomSettingHelper(CustomSettingHelper customSettingHelper) {
		this.customSettingHelper = customSettingHelper;
  }
  
  public CultureDAO getCultureDAO() {
	  return cultureDAO;
  }

  public void setCultureDAO(CultureDAO cultureDAO) {
	  this.cultureDAO = cultureDAO;
  }
  
  
	/**
	 * get Subject on basis of EmailType and NotificatinType
	 * 
	 * @param userInfo
	 * @param emailType
	 * @param sendEmailNotification
	 * @return
	 * @throws MitchellException
	 */
	public String getSubject(UserInfoDocument userInfo, String emailType,
			boolean sendEmailNotification) throws MitchellException {
		String subject = null;
		String coCode = null;
		if (userInfo != null && userInfo.getUserInfo() != null) {
			if (sendEmailNotification) {
				coCode = userInfo.getUserInfo().getOrgCode();
				subject = getSubjectForEmail(coCode, emailType);
			}

		}
		return subject;

	}

	/**
	 * get Subject on basis of EmailType
	 * 
	 * @param coCode
	 * @param emailType
	 * @return
	 * @throws MitchellException
	 */
	private String getSubjectForEmail(String coCode, String emailType)
			throws MitchellException {

		String languageFromCustomSettings = customSettingHelper
				.getCompanyCustomSetting(
						coCode,
						AssignmentDeliveryConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME,
						AssignmentDeliveryConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME);

		String culture = null;
		String subjectLine = null;
		InternationlizeData intData = new InternationlizeDataImpl();
		Locale locale = null;
		String key = emailType+AssignmentDeliveryConstants.SUBJECT;   
		ResourceBundle rs = null;

		if (languageFromCustomSettings != null
				&& !languageFromCustomSettings.isEmpty()) {

			culture = languageFromCustomSettings;
			locale = new Locale(culture);
			rs = intData.getResourceBundle(locale);
			subjectLine = rs.getString(key);

		}

		else {

			locale = new Locale(
					AssignmentDeliveryConstants.DEFAULT_CULTURE_CODE);
			rs = intData.getResourceBundle(locale);
			subjectLine = rs.getString(key);
		}
		return subjectLine;

	}

	/**
	 * returns the formatted subject by passing claim number to property file.
	 * 
	 * @param claimNumber
	 * @param subject
	 * @return
	 */
	private String formatSubject(String claimNumber,String coName, String subject) {
		
			
		String fomatValues[] =  {claimNumber,coName} ;
		MessageFormat formatter = new MessageFormat(subject);
		return  formatter.format(fomatValues);
		

	}
  
}
