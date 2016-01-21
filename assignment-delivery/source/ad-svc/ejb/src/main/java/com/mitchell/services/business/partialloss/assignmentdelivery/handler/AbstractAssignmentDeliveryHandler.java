package com.mitchell.services.business.partialloss.assignmentdelivery.handler;

import java.io.File;
import java.util.logging.Level;

import com.cieca.bms.CIECADocument;
import com.cieca.bms.ClaimInfoType;
import com.cieca.bms.PolicyInfoType;
import com.mitchell.services.business.partialloss.assignmentdelivery.AbstractAssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.AbstractAssignmentDeliveryUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.AppLoggerBridge;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfigBridge;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.eclaim.Converter;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingType;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.utils.misc.AppUtilities;

public abstract class AbstractAssignmentDeliveryHandler
{

  protected AbstractAssignmentDeliveryLogger mLogger;
  protected AbstractAssignmentDeliveryUtils assignmentDeliveryUtils;
  protected AppLoggerBridge appLoggerBridge;
  protected Converter converter;
  protected AbstractDispatchReportBuilder drBuilder;
  protected AbstractHandlerUtils handlerUtils;
  protected ErrorLoggingServiceWrapper errorLoggingService;
  protected AssignmentDeliveryConfigBridge assignmentDeliveryConfigBridge;
  protected static final int STATUS_TO_KEEP_LOGGING_API_HAPPY = 0;
  protected final String NVPAIR_ASG_DELIVERY_FAR_ID_NAME = "asgDeliveryFileArchiveId";
  protected final String ECINBOX_SUBMISSION_SUCCESS_APPLOG_TRNS_TYPE_FILE = "106800";
  protected final String NVPAIR_ASG_DELIVERY_CIECA_BMS_ID_NAME = "asgDeliveryCiecaBmsId";

  public AbstractAssignmentDeliveryLogger getLogger()
  {
    return mLogger;
  }

  public void setLogger(final AbstractAssignmentDeliveryLogger logger)
  {
    mLogger = logger;
  }

  protected void cleanUpFileIfPresent(final File file, final String description)
      throws AssignmentDeliveryException
  {
    if (file != null) {
      file.delete();
      if (mLogger.isLoggable(Level.INFO)) {
        mLogger.info("Successfully cleaned the " + description + " "
            + file.getAbsolutePath());
      }
    }
  }

  /**
   * Get the class name as you would like it in the logs.
   * 
   * @return
   */
  protected abstract String getClassName();

  public AssignmentDeliveryConfigBridge getAssignmentDeliveryConfigBridge()
  {
    return assignmentDeliveryConfigBridge;
  }

  public void setAssignmentDeliveryConfigBridge(
      final AssignmentDeliveryConfigBridge assignmentDeliveryConfigBridge)
  {
    this.assignmentDeliveryConfigBridge = assignmentDeliveryConfigBridge;
  }

  public AppLoggerBridge getAppLoggerBridge()
  {
    return appLoggerBridge;
  }

  public void setAppLoggerBridge(final AppLoggerBridge appLoggerBridge)
  {
    this.appLoggerBridge = appLoggerBridge;
  }

  public AbstractHandlerUtils getHandlerUtils()
  {
    return handlerUtils;
  }

  public void setHandlerUtils(final AbstractHandlerUtils handlerUtils)
  {
    this.handlerUtils = handlerUtils;
  }

  public ErrorLoggingServiceWrapper getErrorLoggingService()
  {
    return errorLoggingService;
  }

  public void setErrorLoggingService(
      final ErrorLoggingServiceWrapper errorLoggingService)
  {
    this.errorLoggingService = errorLoggingService;
  }

  public AbstractAssignmentDeliveryUtils getAssignmentDeliveryUtils()
  {
    return assignmentDeliveryUtils;
  }

  public void setAssignmentDeliveryUtils(
      final AbstractAssignmentDeliveryUtils assignmentDeliveryUtils)
  {
    this.assignmentDeliveryUtils = assignmentDeliveryUtils;
  }

  public Converter getConverter()
  {
    return converter;
  }

  public void setConverter(final Converter converter)
  {
    this.converter = converter;
  }

  public AbstractDispatchReportBuilder getDrBuilder()
  {
    return drBuilder;
  }

  public void setDrBuilder(final AbstractDispatchReportBuilder drBuilder)
  {
    this.drBuilder = drBuilder;
  }

  protected void logException(final String thisMethod, final String workItemId,
      final Exception e)
      throws AssignmentDeliveryException
  {
    mLogger.severe(e.getMessage());
    errorLoggingService.logError(AssignmentDeliveryErrorCodes.GENERAL_ERROR,
        null, getClassName(), thisMethod, ErrorLoggingService.SEVERITY.FATAL,
        workItemId, e.getMessage(), null, 0, e);
    throw mLogger.createException(AssignmentDeliveryErrorCodes.GENERAL_ERROR,
        workItemId, e);
  }

  protected AppLoggingDocument prepareAppLoggingDocument(
      final CIECADocument ciecaDoc, final int status, final String workItemId,
      final String appLogTxType, final String userInfoUserIdForAppLog)
  {
    final AppLoggingDocument appLogDoc = AppLoggingDocument.Factory
        .newInstance();
    final AppLoggingType appType = appLogDoc.addNewAppLogging();

    final ClaimInfoType claimInfo = ciecaDoc.getCIECA().getAssignmentAddRq()
        .getClaimInfo();
    if (claimInfo.getClaimNum() != null) {
      appType.setClaimNumber(claimInfo.getClaimNum());
    }

    // PloicyInfoType is not a mandatory element of ClaimInfo so check
    // its nullablity
    final PolicyInfoType policyInfo = claimInfo.getPolicyInfo();
    if (policyInfo != null && policyInfo.getPolicyNum() != null) {
      appType.setPolicyNumber(policyInfo.getPolicyNum());
    }
    appType.setCurrentWorkflowId(workItemId);
    appType.setMitchellUserId(userInfoUserIdForAppLog);

    if (mLogger.isLoggable(java.util.logging.Level.FINE)) {
      mLogger
          .fine("userInfoUserIdForAppLog is ....." + userInfoUserIdForAppLog);
    }

    appType.setModuleName(AssignmentDeliveryConstants.MODULE_NAME);
    appType.setStatus(status);
    appType.setTransactionType(appLogTxType);
    return appLogDoc;
  }

  protected AppLoggingNVPairs prepareAppLoggingNameValuePairs(
      final CIECADocument ciecaDoc, final long arid, final String appLogTxType,
      final boolean isDrp, final String reviewerUserId,
      final String reviewerCompanyCode)
  {
    final AppLoggingNVPairs appLogNvPairs = new AppLoggingNVPairs();

    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("logEClaimSubmissionEvent: Archive ID = " + arid);
    }

    if (arid > 0) {
      appLogNvPairs.addFileArchiveId(NVPAIR_ASG_DELIVERY_FAR_ID_NAME,
          String.valueOf(arid));
    }

    // Only Add for MCF delivery case
    // (ECINBOX_SUBMISSION_SUCCESS_APPLOG_TRNS_TYPE_FILE)
    // Do not add for Cancel or Supplement Emails cases.
    if ((ciecaDoc.getCIECA().getAssignmentAddRq().getDocumentInfo()
        .getDocumentID() != null)
        && (appLogTxType == ECINBOX_SUBMISSION_SUCCESS_APPLOG_TRNS_TYPE_FILE)) {
      appLogNvPairs.addCiecaBMSRqUID(NVPAIR_ASG_DELIVERY_CIECA_BMS_ID_NAME,
          ciecaDoc.getCIECA().getAssignmentAddRq().getDocumentInfo()
              .getDocumentID());
    }

    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("logEClaimSubmissionEvent: isDrp = " + isDrp);
    }

    if (isDrp) {
      appLogNvPairs.addPair("ReviewerUserId", reviewerUserId);
      appLogNvPairs.addPair("ReviewerCompanyCode", reviewerCompanyCode);
    }

    // Add Processing machine info

    String machineInfo = AppUtilities.buildServerName();
    if (machineInfo != null && machineInfo.length() > 0) {
      appLogNvPairs.addInfo("ProcessingMachineInfo", machineInfo);
    }

    return appLogNvPairs;
  }

}
