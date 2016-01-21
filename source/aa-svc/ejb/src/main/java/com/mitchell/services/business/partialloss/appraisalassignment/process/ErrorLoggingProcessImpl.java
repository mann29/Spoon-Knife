package com.mitchell.services.business.partialloss.appraisalassignment.process;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.external.AASExternalAccessor;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;

@Stateless
public class ErrorLoggingProcessImpl implements ErrorLoggingProcess,
    ErrorLoggingProcessRemote
{
  private static final Logger logger = Logger
      .getLogger("com.mitchell.carr.carrstandard.processor.ErrorProcessor");

  @EJB
  protected AASExternalAccessor extAccess;

  /**
   * This method processing error logging for MDBs. If the provided Message does
   * not have the getJMSRedelivered() flag set true then an error is logged with
   * the ErrorLogging Service.
   * 
   * @param msg The message that is being processed
   * @param className Name of calling class.
   * @param methodName Name of calling method.
   * @param e The original Exception
   * @param desc The description about the error
   * @param errorNo The error number
   * @param details Details of the error.
   * 
   * @return none
   * @exception none
   */
  public void handleMDBException(boolean isRedelivered, String className,
      String methodName, Throwable e, String desc, int errorNo, String details)
  {

    // Since the message is being handled for the first time
    // Send a message to Error Logging Service

    if (!isRedelivered) {

      String workItemId = null;
      String corrId = null;
      String companyCode = null;
      if (e instanceof MitchellException) {
        corrId = ((MitchellException) e).getCorrelationId();
        workItemId = ((MitchellException) e).getWorkItemId();
        companyCode = ((MitchellException) e).getCompanyCode();
      }

      // Log a correlated Error if details are provided.

      if (details != null) {
        corrId = this.extAccess.logError(
            AppraisalAssignmentConstants.ERROR_CORRELATED_INFO, corrId,
            className, methodName, ErrorLoggingService.SEVERITY.NONFATAL,
            workItemId, "Correlated error information", companyCode, 0,
            details, "STRING");
      }

      if (e instanceof MitchellException) {

        MitchellException me = (MitchellException) e;
        me.setApplicationName(AppraisalAssignmentConstants.APP_NAME);
        me.setModuleName(AppraisalAssignmentConstants.MODULE_NAME);
        me.setCorrelationId(corrId);
        if (me.getType() < 1) {
          me.setType(errorNo);
        }
        this.extAccess.logError(me);

      } else {

        MitchellException me = new MitchellException(errorNo, className,
            methodName, desc, e);
        me.setApplicationName(AppraisalAssignmentConstants.APP_NAME);
        me.setModuleName(AppraisalAssignmentConstants.MODULE_NAME);
        me.setCorrelationId(corrId);
        me.setSeverity(ErrorLoggingService.SEVERITY.FATAL);
        this.extAccess.logError(me);

      }
    }

  }

}
