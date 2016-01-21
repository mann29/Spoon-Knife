package com.mitchell.services.business.partialloss.appraisalassignment.util;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;

public interface AASErrorLogUtil
{

  public void logError(int errorCode, String className, String methodName,
      String severity, String description, String msgDetail, Exception ex,
      UserInfoDocument userInfoDocument, String workitemId, String claimNumber)
      throws MitchellException;

  public void logError(int errorType, String correlationId, String className,
      String methodName, String severity, String workItemID,
      String description, String companyCode, int orgID, Exception exception)
      throws MitchellException;

  public void logAndThrowError(int errorCode, String className,
      String methodName, String severity, String description, String msgDetail,
      String errorDetail, UserInfoDocument userInfoDocument, String workitemId,
      String claimNumber)
      throws MitchellException;

  public void logAndThrowError(MitchellException mitchellException,
      String claimNumber, UserInfoDocument userInfoDocument)
      throws MitchellException;

  public void logAndThrowError(int errorCode, String className,
      String methodName, String severity, String description, String msgDetail,
      Exception exception, UserInfoDocument userInfoDocument,
      String workitemId, String claimNumber)
      throws MitchellException;

  public void logWarning(String className, String methodName, int errorEvent,
      String errorMessage, String workItemID, Exception exception,
      String claimNumber, UserInfoDocument logdInUsrInfo);

  public void logCorrelatedAndThrowError(final int errorCode,
      final String className, final String methodName, final String severity,
      final String description, String msgDetail, final Exception exception,
      final UserInfoDocument userInfoDocument, String workitemId,
      String claimNumber)
      throws MitchellException;

}
