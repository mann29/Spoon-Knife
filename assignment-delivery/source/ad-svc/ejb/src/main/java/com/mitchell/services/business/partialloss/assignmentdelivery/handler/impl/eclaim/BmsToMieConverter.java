package com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.eclaim;

import java.io.File;
import java.io.IOException;

import com.cieca.bms.CIECADocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfig;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.BmsCleanser;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.HandlerUtils;
import com.mitchell.services.core.transformation.ciecabmstomiecontext.CiecaBmsToMieContextDocument;
import com.mitchell.services.core.transformation.ciecabmstomiecontext.CiecaBmsToMieContextType;
import com.mitchell.services.core.transformation.mum.MumMappingAdaptor;
import com.mitchell.services.core.transformation.transformationresults.ErrorReportType;
import com.mitchell.services.core.transformation.transformationresults.ErrorReportType.ErrorDetails;
import com.mitchell.services.core.transformation.transformationresults.StatusType;
import com.mitchell.services.core.transformation.transformationresults.TransformationResultsDocument;
import com.mitchell.services.core.transformation.transformationresults.TransformationResultsType;
import com.mitchell.systemconfiguration.SystemConfiguration;
import com.mitchell.utils.misc.UUIDFactory;

/**
 * 
 * @author <a href="mailto://prashant.khanwale@mitchell.com"> Prashant sadashiv
 *         Khanwale </a> Created/Modified on Aug 18, 2010
 */
public class BmsToMieConverter implements Converter
{
  private static final String CLASS_NAME = "BmsToMieConverter";

  protected static final String TRANSFORMATION_NAME = "CiecaBmsToMie";
  protected static final String SYS_CONF_BMSCLEANSE_FLAG = "/AssignmentDelivery/DoBMSCleaningForMIE";

  protected final AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(
      this.getClass().getName());

  // For storing whether we need to set 'isARC5' in the context, for the
  // transformation.
  protected boolean m_isARC5 = false;

  /**
   * Need explicit constructor, as we've added the 'boolean' one below.
   */
  public BmsToMieConverter()
  {
  }

  public BmsToMieConverter(final boolean isArc5)
  {
    m_isARC5 = isArc5;
  }

  /**
   * Transform Bms Asg To Mie Asg.
   * 
   * @see com.mitchell.services.business.partialloss.assignmentdelivery.handler
   *      .impl
   *      .eclaim.Converter#transformBmsAsgToMieAsg(com.mitchell.services.business
   *      .partialloss.assignmentdelivery.AssignmentServiceContext)
   */
  public File transformBmsAsgToMieAsg(
      final AssignmentServiceContext assignmentSvcContext)
      throws Exception
  {

    final String methodName = "transformBmsAsgToMieAsg";
    mLogger.entering(CLASS_NAME, methodName);

    final HandlerUtils handlerUtils = new HandlerUtils();

    final String workItemId = assignmentSvcContext.getWorkItemId();
    final UserInfoDocument userInfo = assignmentSvcContext.getUserInfo();
    final CIECADocument ciecaDoc = handlerUtils.getCiecaDocFromMitchellEnv(
        assignmentSvcContext.getMitchellEnvDoc(), workItemId);

    final File nonLineDelimitedMIEFile = getMieFromCiecaDocument(
        assignmentSvcContext, methodName, workItemId, userInfo, ciecaDoc);

    return nonLineDelimitedMIEFile;
  }

  /**
   * Transform Bms Asg To Mie Asg.
   */
  public File transformBmsAsgToMieAsg(final APDDeliveryContextType context,
      final MitchellEnvelopeDocument med, final String ciecaId)
      throws Exception
  {
    final HandlerUtils handlerUtils = new HandlerUtils();
    final BaseAPDCommonType apdCommonInfo = context
        .getAPDAppraisalAssignmentInfo().getAPDCommonInfo();
    final String workItemId = apdCommonInfo.getWorkItemId();
    final String workAssignmentId = context.getAPDAppraisalAssignmentInfo()
        .getTaskId() + "";
    final boolean isDRP = (apdCommonInfo.getTargetDRPUserInfo() != null);
    final UserInfoType userInfo = apdCommonInfo.getTargetUserInfo()
        .getUserInfo();
    UserInfoType drpUserInfo = null;
    if (isDRP) {
      drpUserInfo = apdCommonInfo.getTargetDRPUserInfo().getUserInfo();
    }
    final CIECADocument ciecaDoc = handlerUtils
        .getCiecaDocumentFromMitchellEnvelope(med, ciecaId, workItemId);
    return convertCiecaToMie("transformBmsAsgToMieAsg", workItemId,
        (isDRP ? drpUserInfo : userInfo), ciecaDoc, isDRP, workAssignmentId,
        drpUserInfo);
  }

  /**
   * Get Mie From Cieca Document.
   * 
   * @param assignmentSvcContext
   * @param methodName
   * @param workItemId
   * @param userInfo
   * @param ciecaDoc
   * @return
   * @throws AssignmentDeliveryException
   * @throws IOException
   * @throws Exception
   */
  public File getMieFromCiecaDocument(
      final AssignmentServiceContext assignmentSvcContext,
      final String methodName, final String workItemId,
      final UserInfoDocument userInfo, final CIECADocument ciecaDoc)
      throws AssignmentDeliveryException, IOException, Exception
  {
    final boolean isDrp = assignmentSvcContext.isDrp();

    if (mLogger.isLoggable(java.util.logging.Level.FINE)) {
      mLogger.fine("assignmentSvcContext.isDrp::::" + isDrp);
    }

    final String workAssignmentId = assignmentSvcContext.getWorkAssignmentId();
    final UserInfoType drpUserInfo = isDrp ? assignmentSvcContext
        .getDrpUserInfo().getUserInfo() : null;

    return convertCiecaToMie(methodName, workItemId, userInfo.getUserInfo(),
        ciecaDoc, isDrp, workAssignmentId, drpUserInfo);
  }

  /**
   * Convert Cieca To Mie.
   * 
   * @param methodName
   * @param workItemId
   * @param userInfo
   * @param ciecaDoc
   * @param isDrp
   * @param workAssignmentId
   * @param drpUserInfo
   * @return
   * @throws AssignmentDeliveryException
   * @throws IOException
   * @throws Exception
   */
  public File convertCiecaToMie(final String methodName,
      final String workItemId, final UserInfoType userInfo,
      final CIECADocument ciecaDoc, final boolean isDrp,
      final String workAssignmentId, final UserInfoType drpUserInfo)
      throws AssignmentDeliveryException, IOException, Exception
  {
    File ciecaBmsAsgFile = null;
    File nonLineDelimitedMIEFile = null;
    File mieFileWithNLExt = null;
    File mieFileWithXMLExt = null;

    try {

      TransformationResultsDocument transformationResultsDocument = null;

      // Get a cleansed CIECA Document
      CIECADocument cleanCiecaDoc = cleanseCiecaDocForMieTransformation(ciecaDoc);

      // Save input CIECA BMS assignment as a temporary file as BMStoMie
      // transformation just take file as input parameter.
      final String ciecaBmsAsgFileName = getTempDir() + File.separator
          + userInfo.getOrgCode() + "." + workItemId + "."
          + UUIDFactory.getInstance().getUUID() + ".xml";
      ciecaBmsAsgFile = new File(ciecaBmsAsgFileName);
      cleanCiecaDoc.save(ciecaBmsAsgFile);

      final String nonLineDelimitedMIEFileName = getNonLineDelimitedMIEFilename(
          userInfo, workItemId, workAssignmentId);

      // Transform from BMS to MIE
      final CiecaBmsToMieContextDocument contextDocument = CiecaBmsToMieContextDocument.Factory
          .newInstance();
      final CiecaBmsToMieContextType context = contextDocument
          .addNewCiecaBmsToMieContext();

      // Set parameters for context object
      context.setCompanyCode(userInfo.getOrgCode());

      if (isDrp == true) {
        context.setDRPCrossoverUserID(drpUserInfo.getUserID());
        context.setBodyShopCompanyCode(drpUserInfo.getOrgCode());
      }

      // CG: New flag for whether this is an ARC5 delivery.
      // Setting this flag true in the context enables ARC5-specific logic
      // in the BMS to MIE transformation.
      if (m_isARC5) {
        context.setIsARC5(true);
      }

      //
      context.setIsOfficeLevelRouting(false);

      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger.info("contextDocument for MumMapping::::"
            + contextDocument.getCiecaBmsToMieContext());
      }

      final MumMappingAdaptor adaptor = new MumMappingAdaptor();
      transformationResultsDocument = adaptor.transformDocument(
          ciecaBmsAsgFileName, nonLineDelimitedMIEFileName,
          TRANSFORMATION_NAME, contextDocument);

      // CG - see if any errors from transformation.
      final TransformationResultsType transRslt = transformationResultsDocument
          .getTransformationResults();
      final StatusType.Enum status = transRslt.getStatus();

      // Any errors during transformation?
      if (status.intValue() == StatusType.Enum.forString("FAILURE").intValue()) {

        final ErrorReportType report = transRslt.getTransformationErrors();
        final ErrorDetails details = report.getErrorDetailsArray(0);
        final Exception e = new Exception(details.toString());
        throw e;
      }

      nonLineDelimitedMIEFile = new File(nonLineDelimitedMIEFileName);

      mieFileWithXMLExt = new File(nonLineDelimitedMIEFileName + ".xml");
      mieFileWithNLExt = new File(nonLineDelimitedMIEFileName + ".nl");

    } finally {

      // Check whether files created, since they might not have done if an
      // exception has occurred.
      if (ciecaBmsAsgFile != null) {
        if (ciecaBmsAsgFile.exists()) {
          ciecaBmsAsgFile.delete();
        }
      }

      if (mieFileWithXMLExt != null) {
        if (mieFileWithXMLExt.exists()) {
          mieFileWithXMLExt.delete();
        }
      }

      if (mieFileWithNLExt != null) {
        if (mieFileWithNLExt.exists()) {
          mieFileWithNLExt.delete();
        }
      }

      mLogger.exiting(CLASS_NAME, methodName);
    }
    return nonLineDelimitedMIEFile;
  }

  /**
   * This method returns a filename (with path) for the MIE file which results
   * from the BMS to MIE transformation. For non-line-delimited MIE, naming
   * convention is: <companyCode>.YYYYMMDD.HHMMSS.<PID>.<sequenceNbr>.mie
   * 
   * @param userInfo
   *          -
   * @param workItemId
   *          - The unique workflow id
   * @param workAssignmentId
   * 
   * @return Appropriately named line-delimited MIE filename with path
   */
  private String getNonLineDelimitedMIEFilename(final UserInfoType userInfo,
      final String workItemId, final String workAssignmentId)
      throws AssignmentDeliveryException
  {
    final String methodName = "getNonLineDelimitedMIEFilename";
    mLogger.entering(CLASS_NAME, methodName);

    final String sequenceNumber = "1"; // hard-coded for assignments
    final String fName = getTempDir() + File.separator;
    /**
     * SCR# 10577 Adding WorkAssignmentId as a part of file name so that
     * ARC5 can retrieve it ModifiedBy Kiran Saini
     */
    final StringBuffer sbf = new StringBuffer(fName);
    if (workAssignmentId != null && !"".equals(workAssignmentId)) {
      sbf.append("WAID_");
      sbf.append(workAssignmentId);
      sbf.append("_");
    }
    sbf.append(userInfo.getOrgCode());
    sbf.append(".");
    sbf.append(workItemId);
    sbf.append(".");
    sbf.append(UUIDFactory.getInstance().getUUID());
    sbf.append(".");
    sbf.append(sequenceNumber);
    sbf.append(".mie");

    mLogger.exiting(CLASS_NAME, methodName);

    return sbf.toString();
  }

  private String getTempDir()
      throws AssignmentDeliveryException
  {
    String tempdir = AssignmentDeliveryConfig.getTempDir();
    if (tempdir == null || tempdir.length() == 0) {
      mLogger
          .warning("Temporary directory is not defined in System configuration, so using default one = "
              + AssignmentDeliveryConstants.DEFAULT_TEMP_DIR);
      tempdir = AssignmentDeliveryConstants.DEFAULT_TEMP_DIR;
    }

    return tempdir;
  }

  /**
   * Cleanse CiecaDoc For Mie Transformation.
   */
  protected CIECADocument cleanseCiecaDocForMieTransformation(
      CIECADocument ciecaDoc)
  {
    CIECADocument cleanCieca = ciecaDoc;
    if (doBmsCleansing()) {
      BmsCleanser bmsCleanse = newBmsCleanser();
      cleanCieca = bmsCleanse.cleansForMIETransformation(ciecaDoc);
    }
    //
    return cleanCieca;
  }

  /**
   * Isolate for unit testing.
   */
  protected BmsCleanser newBmsCleanser()
  {
    return new BmsCleanser();
  }

  /**
   * Get BMS Cleaning Switch.
   */
  protected boolean doBmsCleansing()
  {
    boolean retval = true;
    String ss = getSysConfigValue(SYS_CONF_BMSCLEANSE_FLAG);
    if (ss != null) {
      retval = Boolean.valueOf(ss).booleanValue();
    }
    return retval;
  }

  /**
   * Isolate call to SystemConfig for unit-testability.
   */
  protected String getSysConfigValue(String sysConfPath)
  {
    return SystemConfiguration.getSettingValue(sysConfPath);
  }
}
