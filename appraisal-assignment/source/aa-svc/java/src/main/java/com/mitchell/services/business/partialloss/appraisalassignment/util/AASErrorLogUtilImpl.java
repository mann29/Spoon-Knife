package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.util.Calendar;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.utils.misc.AppUtilities;

public class AASErrorLogUtilImpl implements AASErrorLogUtil
{

  private final Logger logger = Logger.getLogger(AASErrorLogUtilImpl.class
      .getName());
  private static final String CLASS_NAME = AASErrorLogUtilImpl.class.getName();

  /**
   * Function to log error in the ErrorLog. Some details are passed in through
   * the calling method while the common ones like CompanyCode, ModuleName etc
   * are set using TrackingInfo when the error object is initiated.
   * 
   * @param errorCode
   *          - error code passed by each calling method
   * @param className
   *          - class where the error originated
   * @param methodName
   *          - method name passed in
   * @param severity
   *          - FATAL or WARNING
   * @param description
   *          - short description of the error. Actually the Error detail
   *          corresponding to the Error code in database.
   * @param msgDetail
   *          - additional details like filename, directory name. Can be
   *          null.
   * @param exception
   *          - Exception object
   */
  public void logError(final int errorCode, final String className,
      final String methodName, final String severity, final String description,
      String msgDetail, final Exception ex,
      final UserInfoDocument userInfoDocument, String workitemId,
      String claimNumber)
      throws MitchellException
  {
    logger.entering("AASErrorLogUtil", "logError()");

    logger
        .severe("AppraisalAssignmentEJB: Exception while processing AppraisalAssignmentEJB"
            + "\nMethod Name --> "
            + methodName
            + "\nException Message --> "
            + ex.getMessage()
            + "\nException StackTrace --> "
            + AppUtilities.getStackTraceString(ex));

    userInfoDocument.getUserInfo().getUserID();
    final String companyCode = userInfoDocument.getUserInfo().getOrgCode();
    final int orgId = Integer.parseInt(userInfoDocument.getUserInfo()
        .getOrgID());

    if (null == claimNumber || "".equalsIgnoreCase(claimNumber)) {
      claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
    }

    if (null == workitemId || "".equalsIgnoreCase(workitemId)) {
      workitemId = "UNKNOWN";
    }

    StringBuilder msgBuild = new StringBuilder("Error in ");
    msgBuild.append(AppraisalAssignmentConstants.MODULE_NAME);
    if (msgDetail != null && msgDetail.length() > 0) {
      msgBuild.append(" -> ");
      msgBuild.append(msgDetail);
    }

    // Add more details if Exception object available
    if (ex != null) {
      msgBuild.append(" -> ");
      msgBuild.append(ex.getMessage());
      if (ex.getCause() != null) {
        msgBuild.append(" -> ");
        msgBuild.append(ex.getCause().getMessage());
      }
    }

    String msgDesc = msgBuild.toString();

    try {
      MitchellException mitchEx = null;
      if (ex instanceof MitchellException) {
        mitchEx = (MitchellException) ex;
      } else {
        mitchEx = new MitchellException(className, methodName, description
            + " " + msgDesc, ex);
      }

      // mitchEx.setCorrelationId(correlationID); Commented setting of
      // correlation ID
      mitchEx.setCompanyCode(companyCode);
      mitchEx.setApplicationName(AppraisalAssignmentConstants.APP_NAME);
      mitchEx.setModuleName(AppraisalAssignmentConstants.MODULE_NAME);
      mitchEx.setClassName(className);
      mitchEx.setSeverity(severity);
      mitchEx.setOrgId(orgId);
      mitchEx.setTimestamp(java.util.Calendar.getInstance().getTime());
      mitchEx.setType(errorCode);
      mitchEx.setWorkItemId(workitemId);
      mitchEx.setStackTrace(ex.getStackTrace());
      mitchEx.setMethodName(methodName);
      mitchEx.setDescription(description + " " + msgDesc);
      ErrorLoggingService.logError(mitchEx, claimNumber);

    } catch (final Exception e) {
      AppUtilities.getCleansedAppServerStackTraceString(e, true);
      MitchellException mitchEx = null;
      if (e instanceof MitchellException) {
        mitchEx = (MitchellException) e;
      } else {
        mitchEx = new MitchellException(className, methodName,
            "Error Occured while calling ErrorLogging Service", e);
      }
      throw mitchEx;
    }

    logger.exiting("AASErrorLogUtil", "logError()");
  }

  /**
   * This method log the Mitchell Exception and throw . Some details are
   * passed in through the calling method while the common ones like
   * CompanyCode, ModuleName etc are set using TrackingInfo when the error
   * object is initiated.
   * 
   * @param errorCode
   *          - error code passed by each calling method
   * @param className
   *          - class where the error originated
   * @param methodName
   *          - method name passed in
   * @param severity
   *          - FATAL or WARNING
   * @param description
   *          - short description of the error. Actually the Error detail
   *          corresponding to the Error code in database.
   * @param msgDetail
   *          - additional details like filename, directory name. Can be
   *          null.
   * @param exception
   *          - Exception object
   */
  public void logAndThrowError(final int errorCode, final String className,
      final String methodName, final String severity, final String description,
      String msgDetail, final String errorDetail,
      final UserInfoDocument userInfoDocument, final String workitemId,
      final String claimNumber)
      throws MitchellException
  {

    logger.entering("AASErrorLogUtil", "logError()");

    logger
        .severe("AppraisalAssignmentEJB: Exception while processing  AppraisalAssignmentEJB"
            + "\nMethod Name --> "
            + methodName
            + "\nException Message --> "
            + errorDetail);

    String correlationID = null;
    final String companyCode = userInfoDocument.getUserInfo().getOrgCode();
    final int orgId = Integer.parseInt(userInfoDocument.getUserInfo()
        .getOrgID());

    if (msgDetail != null) {
      msgDetail = "Error in " + AppraisalAssignmentConstants.MODULE_NAME
          + " -> " + msgDetail;
    } else {
      msgDetail = "Error in " + AppraisalAssignmentConstants.MODULE_NAME;
    }
    if (errorDetail != null) {
      msgDetail = msgDetail + " -> " + errorDetail;

      final MitchellException mitchEx = new MitchellException(className,
          methodName, description + " " + msgDetail);
      // mitchEx.setCorrelationId(correlationID); Commented setting of
      // correlation ID
      mitchEx.setCompanyCode(companyCode);
      mitchEx.setApplicationName(AppraisalAssignmentConstants.APP_NAME);
      mitchEx.setModuleName(AppraisalAssignmentConstants.MODULE_NAME);
      mitchEx.setClassName(className);
      mitchEx.setSeverity(severity);
      mitchEx.setOrgId(orgId);
      mitchEx.setTimestamp(java.util.Calendar.getInstance().getTime());
      mitchEx.setType(errorCode);
      mitchEx.setWorkItemId(workitemId);
      mitchEx.setMethodName(methodName);
      correlationID = ErrorLoggingService.logError(mitchEx, claimNumber);
      mitchEx.setCorrelationId(correlationID);
      logger.exiting("AASErrorLogUtil", "logError()");
      throw mitchEx;

    }
  }

  /**
   * This method log the Mitchell Exception and throw . Some details are
   * passed in through the calling method while the common ones like
   * CompanyCode, ModuleName etc are set using TrackingInfo when the error
   * object is initiated.
   * 
   * @param errorCode
   *          - error code passed by each calling method
   * @param className
   *          - class where the error originated
   * @param methodName
   *          - method name passed in
   * @param severity
   *          - FATAL or WARNING
   * @param description
   *          - short description of the error. Actually the Error detail
   *          corresponding to the Error code in database.
   * @param msgDetail
   *          - additional details like filename, directory name. Can be
   *          null.
   * @param exception
   *          - Exception object
   */
  public void logAndThrowError(final MitchellException mitchellException,
      final String claimNumber, final UserInfoDocument userInfoDocument)
      throws MitchellException
  {

    logger.entering("AASErrorLogUtil", "logError()");
    String correlationID = null;

    logger
        .severe("AppraisalAssignmentEJB: Exception while processing AppraisalAssignmentEJB"
            + "\nMethod Name --> "
            + mitchellException.getMethodName()
            + "\nException StackTrace --> "
            + AppUtilities.getStackTraceString(mitchellException));
    correlationID = ErrorLoggingService
        .logError(mitchellException, claimNumber);
    mitchellException.setCorrelationId(correlationID);
    throw mitchellException;

  }

  /**
   * This method log the Mitchell Exception and throw . Some details are
   * passed in through the calling method while the common ones like
   * CompanyCode, ModuleName etc are set using TrackingInfo when the error
   * object is initiated.
   * 
   * @param errorCode
   *          - error code passed by each calling method
   * @param className
   *          - class where the error originated
   * @param methodName
   *          - method name passed in
   * @param severity
   *          - FATAL or WARNING
   * @param description
   *          - short description of the error. Actually the Error detail
   *          corresponding to the Error code in database.
   * @param msgDetail
   *          - additional details like filename, directory name. Can be
   *          null.
   * @param exception
   *          - Exception object
   * @param userInfoDocument
   *          - userInfoDocument object
   * @param workitemId
   *          - workitemId object
   * 
   * @param claimNumber
   *          - claimNumber object
   * @throws MitchellException
   *           - MitchellException object
   */
  public void logAndThrowError(final int errorCode, final String className,
      final String methodName, final String severity, final String description,
      String msgDetail, final Exception exception,
      final UserInfoDocument userInfoDocument, String workitemId,
      String claimNumber)
      throws MitchellException
  {
    final String METHOD_NAME = "logAndThrowError";
    logger.entering(CLASS_NAME, METHOD_NAME);

    logger
        .severe("AppraisalAssignmentEJB: Exception while processing AppraisalAssignmentEJB\nMethod Name --> "
            + methodName
            + "\nException Message --> "
            + exception.getMessage()
            + "\nException StackTrace --> "
            + AppUtilities.getStackTraceString(exception));
    String correlationID = null;
    final String userId = userInfoDocument.getUserInfo().getUserID();
    final String companyCode = userInfoDocument.getUserInfo().getOrgCode();
    final int orgId = Integer.parseInt(userInfoDocument.getUserInfo()
        .getOrgID());
    if (logger.isLoggable(java.util.logging.Level.INFO)) {
      logger.info("userId:" + userId + "CompCode:" + companyCode + "OrgID:"
          + orgId);
    }

    logger
        .severe("AppraisalAssignmentEJB: Exception while processing AppraisalAssignmentEJB"
            + "\nMethod Name --> "
            + methodName
            + "\nException Message --> "
            + exception.getMessage()
            + "\nException StackTrace --> "
            + AppUtilities.getStackTraceString(exception));

    if (null == claimNumber || "".equalsIgnoreCase(claimNumber)) {
      claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
    }

    if (null == workitemId || "".equalsIgnoreCase(workitemId)) {
      workitemId = "UNKNOWN";
    }

    if (msgDetail == null || msgDetail.length() == 0) {
      msgDetail = "Error in APPRAISAL_ASSIGNMENT_SERVICE";
      if (exception != null) {
        msgDetail = msgDetail + " -> " + exception.getMessage();
        if (exception.getCause() != null) {
          msgDetail = msgDetail + " -> " + exception.getCause().getMessage();
        }
      }
    } else {
      msgDetail = "Error in APPRAISAL_ASSIGNMENT_SERVICE -> " + msgDetail;
      if (exception != null) {
        msgDetail = msgDetail + " -> " + exception.getMessage();
        if (exception.getCause() != null) {
          msgDetail = msgDetail + " -> " + exception.getCause().getMessage();
        }
      }
    }
    MitchellException mitchEx = null;

    if (exception instanceof MitchellException) {
      mitchEx = (MitchellException) exception;
    } else {
      mitchEx = new MitchellException(className, methodName, description + " "
          + msgDetail, exception);
    }
    mitchEx.setApplicationName(AppraisalAssignmentConstants.APP_NAME);
    mitchEx.setModuleName(AppraisalAssignmentConstants.MODULE_NAME);
    mitchEx.setOrgId(orgId);
    mitchEx.setCompanyCode(companyCode);
    mitchEx.setClassName(className);
    mitchEx.setMethodName(methodName);
    mitchEx.setTimestamp(Calendar.getInstance().getTime());
    mitchEx.setWorkItemId(workitemId);
    mitchEx.setType(errorCode);
    mitchEx.setDescription(description + " " + msgDetail);
    correlationID = ErrorLoggingService.logError(mitchEx, claimNumber);
    mitchEx.setCorrelationId(correlationID);
    logger.exiting(CLASS_NAME, METHOD_NAME);
    throw mitchEx;
  }

  /**
   * 
   */
  public void logCorrelatedAndThrowError(final int errorCode,
      final String className, final String methodName, final String severity,
      final String description, String msgDetail, final Exception exception,
      final UserInfoDocument userInfoDocument, String workitemId,
      String claimNumber)
      throws MitchellException
  {
    String correlationID = null;

    final String companyCode = userInfoDocument.getUserInfo().getOrgCode();
    final int orgId = Integer.parseInt(userInfoDocument.getUserInfo()
        .getOrgID());

    // If details are provided log correlated

    if (msgDetail != null && msgDetail.length() > 0) {

      if (exception != null && exception instanceof MitchellException) {
        correlationID = ((MitchellException) exception).getCorrelationId();
      }

      StringBuilder xmlDetails = new StringBuilder("<CorrelatedDetails>");
      xmlDetails.append(msgDetail);
      xmlDetails.append("</CorrelatedDetails>");

      String corrDesc = "Correlated Error Information.";
      if (exception != null) {
        corrDesc = corrDesc + " " + exception.getMessage();
      }

      ErrorLoggingService.logError(errorCode, correlationID, className,
          methodName, ErrorLoggingService.SEVERITY.NONFATAL, workitemId,
          corrDesc, companyCode, orgId, xmlDetails.toString(), "XML",
          claimNumber);

    }

    // Build the exception to throw.

    MitchellException mitchEx = null;

    if (exception != null) {
      if (exception instanceof MitchellException) {
        mitchEx = (MitchellException) exception;
      } else {
        mitchEx = new MitchellException(errorCode, className, methodName,
            description, exception);
      }
    } else {
      mitchEx = new MitchellException(errorCode, className, methodName,
          description);
    }
    mitchEx.setApplicationName(AppraisalAssignmentConstants.APP_NAME);
    mitchEx.setModuleName(AppraisalAssignmentConstants.MODULE_NAME);
    mitchEx.setOrgId(orgId);
    mitchEx.setCompanyCode(companyCode);
    mitchEx.setWorkItemId(workitemId);
    mitchEx.setType(errorCode);
    mitchEx.setCorrelationId(correlationID);
    mitchEx.setSeverity(severity);

    throw mitchEx;
  }

  /**
   * This Method logs the Warning.
   * 
   * @param className
   *          className
   * @param methodName
   *          methodName
   * @param errorEvent
   *          errorEvent
   * @param errorMessage
   *          errorMessage
   * @param workItemID
   *          workItemID
   * @param exception
   *          exception
   * @param claimNumber
   *          claimNumber
   * 
   */
  public void logWarning(final String className, final String methodName,
      final int errorEvent, final String errorMessage, final String workItemID,
      final Exception exception, final String claimNumber,
      final UserInfoDocument logdInUsrInfo)
  {
    final String METHOD_NAME = "logWarning";
    logger.entering(CLASS_NAME, METHOD_NAME);
    logger.severe("Warning: Exception while processing" + "\nMethod Name --> "
        + methodName + "\nException Message --> " + exception.getMessage()
        + errorMessage + "\nworkItemID --> " + workItemID
        + "\nclaimNumber --> " + claimNumber + "\nException StackTrace --> "
        + AppUtilities.getStackTraceString(exception));
    String correlationId = null;
    final String companyCode = logdInUsrInfo != null ? logdInUsrInfo
        .getUserInfo().getOrgCode() : "";
    final int orgId = logdInUsrInfo != null ? Integer.parseInt(logdInUsrInfo
        .getUserInfo().getOrgID()) : -1;

    if (errorEvent != 0) {
      MitchellException mitchEx = null;

      if (exception instanceof MitchellException) {
        mitchEx = (MitchellException) exception;
      } else {
        mitchEx = new MitchellException(className, methodName, errorMessage,
            exception);
      }

      mitchEx.setCompanyCode(companyCode);
      mitchEx.setApplicationName(AppraisalAssignmentConstants.APP_NAME);
      mitchEx.setModuleName(AppraisalAssignmentConstants.MODULE_NAME);
      mitchEx.setClassName(className);
      mitchEx.setSeverity(ErrorLoggingService.SEVERITY.WARNING);
      mitchEx.setOrgId(orgId);
      mitchEx.setTimestamp(java.util.Calendar.getInstance().getTime());
      mitchEx.setType(errorEvent);
      mitchEx.setWorkItemId(workItemID);
      mitchEx.setStackTrace(mitchEx.getStackTrace());
      mitchEx.setMethodName(methodName);
      mitchEx.setDescription(errorMessage);
      correlationId = ErrorLoggingService.logError(mitchEx, claimNumber);
      mitchEx.setCorrelationId(correlationId);

    }
    logger.exiting(CLASS_NAME, METHOD_NAME);
  }

  public void logError(int errorType, String correlationId, String className,
      String methodName, String severity, String workItemID,
      String description, String companyCode, int orgID, Exception exception)
      throws MitchellException
  {
    ErrorLoggingService.logError(errorType, correlationId, className,
        methodName, severity, workItemID, description, companyCode, orgID,
        exception);
  }

}
