package com.mitchell.services.business.partialloss.appraisalassignment.pmdb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler.AASParallelRequestManager;
import com.mitchell.services.business.partialloss.appraisalassignment.process.ErrorLoggingProcessRemote;
import com.mitchell.services.business.partialloss.appraisalassignment.util.BeanLocator;
import com.mitchell.services.business.partialloss.appraisalassignment.util.SpringReferencedContextContainer;
import com.mitchell.utils.misc.AppUtilities;

@MessageDriven(name = "com.mitchell.services.business.partialloss.appraisalassignment.pmdb.AASParallelErrorMDB", mappedName = "PARTIALLOSS.AAS_PARALLELMDB_ERROR_INPUT", activationConfig = {
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "PARTIALLOSS.AAS_PARALLELMDB_ERROR_INPUT"),
    @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "10"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@org.jboss.ejb3.annotation.Pool(value = "StrictMaxPool", maxSize = 10, timeout = 10000)
@org.jboss.ejb3.annotation.Depends({
    "org.hornetq:module=JMS,type=Queue,name=\"PARTIALLOSS.AAS_PARALLELMDB_ERROR_INPUT\"",
    "org.hornetq:module=JMS,type=Queue,name=\"PARTIALLOSS.AAS_PARALLELMDB_RESPONSE\"",
    "org.hornetq:module=JMS,type=Queue,name=\"PARTIALLOSS.AAS_PARALLELMDB_EXPIRY\"" })
public class AASParallelErrorMDB extends BaseParallelMDB implements
    MessageListener
{
  private static Logger logger = Logger
      .getLogger("com.mitchell.services.business.partialloss.appraisalassignment.pmdb.AASParallelErrorMDB");

  @EJB
  protected AASParallelRequestManager parallelProcessor;

  @EJB
  protected ErrorLoggingProcessRemote errorLogProcess;

  @Resource
  protected MessageDrivenContext messageCtx;

  // Spring Referenced Counted Context Container
  private SpringReferencedContextContainer springContext = null;

  /**
   * Add a reference to the Spring Context for this EJB at time of EJB
   * construction.
   */
  @PostConstruct
  public void initializeResources()
  {
    this.springContext = (SpringReferencedContextContainer) new BeanLocator();
    this.springContext.addContextReference();
  }

  /**
   * Remove the reference from the Spring Context for this EJB at time of EJB
   * destruction.
   */
  @PreDestroy
  public void releaseResources()
  {
    if (this.springContext != null) {
      this.springContext.removeContextReference();
    }
  }

  /**
   * Main Entry Point for the MDB that listens to the AAS Parallel Processing
   * Error Queue.
   */
  public void onMessage(Message msg)
  {
    String messageText = null;

    try {
      if (msg instanceof TextMessage) {

        TextMessage tMsg = (TextMessage) msg;
        messageText = tMsg.getText();

        // Do processing and Response
        parallelProcessor.handleProcessingRequestError(tMsg);

      } else {
        logger.warn("AASParallelErrorMDB.onMessage received non-text message?");
      }
    } catch (MitchellException me) {

      messageCtx.setRollbackOnly();
      logger.error("AASParallelErrorMDB.onMessage():MitchellException: "
          + AppUtilities.getCleansedAppServerStackTraceString(me, true));

      this.errorLogProcess.handleMDBException(this.getIsRedelivered(msg), this
          .getClass().getName(), "OnMessage", me,
          "MitchellException: " + me.getMessage(),
          AppraisalAssignmentConstants.ERROR_UNEXPECTED_EXCEPTION, messageText);

    } catch (Throwable t) {

      messageCtx.setRollbackOnly();
      logger.error("AASParallelErrorMDB.onMessage():Throwable: "
          + AppUtilities.getCleansedAppServerStackTraceString(t, true));

      this.errorLogProcess.handleMDBException(this.getIsRedelivered(msg), this
          .getClass().getName(), "OnMessage", t,
          "Unexpected Exception: " + t.getMessage(),
          AppraisalAssignmentConstants.ERROR_UNEXPECTED_EXCEPTION, messageText);

    }
  }
}
