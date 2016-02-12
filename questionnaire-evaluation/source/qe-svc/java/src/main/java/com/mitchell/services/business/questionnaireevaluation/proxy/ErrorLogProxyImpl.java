package com.mitchell.services.business.questionnaireevaluation.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;

/**
 * The Class ErrorLogProxyImpl.
 */
public final class ErrorLogProxyImpl implements ErrorLogProxy
{

  /**
   * Log error.
   * 
   * @param mitchellException the mitchell exception
   * @return the string
   */
  public String logError(MitchellException mitchellException)
  {
    // Set default code if one is not defined
    mitchellException = updateToDefault(mitchellException);

    // Call ErrorLogging Service
    return ErrorLoggingService.logError(mitchellException);

  }

  /**
   * Set a default error code if the provided code does not fall within the
   * QEVAL error code range. The AppName and ModuleName are always updated.
   */
  protected MitchellException updateToDefault(
      MitchellException mitchellException)
  {
    // Set default error information if it is not set
    if (mitchellException.getType() < QuestionnaireEvaluationConstants.MIN_QEVAL_ERROR_CODE
        || mitchellException.getType() > QuestionnaireEvaluationConstants.MAX_QEVAL_ERROR_CODE) {

      mitchellException.setType(QuestionnaireEvaluationConstants.ERROR_UNKNOWN);

    }

    mitchellException
        .setApplicationName(QuestionnaireEvaluationConstants.APPLICATION_NAME);
    mitchellException
        .setModuleName(QuestionnaireEvaluationConstants.MODULE_NAME);

    return mitchellException;
  }
}
