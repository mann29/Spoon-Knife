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
import com.mitchell.utils.logging.MIProcessingInstanceLogger;
import com.mitchell.utils.misc.AppUtilities;

@MessageDriven(name = "com.mitchell.services.business.partialloss.appraisalassignment.pmdb.AASParallelMDB", mappedName = "PARTIALLOSS.AAS_PARALLELMDB_INPUT", activationConfig = {
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "PARTIALLOSS.AAS_PARALLELMDB_INPUT"),
    @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "14"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@org.jboss.ejb3.annotation.Pool(value = "StrictMaxPool", maxSize = 14, timeout = 10000)
@org.jboss.ejb3.annotation.Depends({
    "org.hornetq:module=JMS,type=Queue,name=\"PARTIALLOSS.AAS_PARALLELMDB_INPUT\"",
    "org.hornetq:module=JMS,type=Queue,name=\"PARTIALLOSS.AAS_PARALLELMDB_ERROR_INPUT\"",
    "org.hornetq:module=JMS,type=Queue,name=\"PARTIALLOSS.AAS_PARALLELMDB_RESPONSE\"",
    "org.hornetq:module=JMS,type=Queue,name=\"PARTIALLOSS.AAS_PARALLELMDB_EXPIRY\"" })
public class AASParallelMDB extends BaseParallelMDB implements MessageListener
{

  private static Logger logger = Logger
      .getLogger("com.mitchell.services.business.partialloss.appraisalassignment.pmdb.AASParallelMDB");
  private static final MIProcessingInstanceLogger miProcLogger = new MIProcessingInstanceLogger();

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
   * Main entry point the the AAS Parallel Processing MDB.
   */
  public void onMessage(Message msg)
  {
    String messageText = null;
    long startTime = System.currentTimeMillis();
    try {
      if (msg instanceof TextMessage) {

        TextMessage tMsg = (TextMessage) msg;
        messageText = tMsg.getText();

        // Do processing and Response
        parallelProcessor.handleProcessingRequest(tMsg);

        if (miProcLogger.isLoggable()) {
          miProcLogger.logProcessingInfoTotalTime(
              AppraisalAssignmentConstants.MODULE_NAME, "NA",
              System.currentTimeMillis() - startTime, "onMessage", "SUCCESS",
              "");
        }

      } else {
        logger.warn("AASParallelMDB.onMessage received non-text message?");
      }
    } catch (MitchellException me) {

      messageCtx.setRollbackOnly();
      logger.error("AASParallelMDB.onMessage():MitchellException: "
          + AppUtilities.getCleansedAppServerStackTraceString(me, true));

      this.errorLogProcess.handleMDBException(this.getIsRedelivered(msg), this
          .getClass().getName(), "OnMessage", me,
          "MitchellException: " + me.getMessage(),
          AppraisalAssignmentConstants.ERROR_UNEXPECTED_EXCEPTION, messageText);

    } catch (Throwable t) {

      messageCtx.setRollbackOnly();
      logger.error("AASParallelMDB.onMessage():Throwable: "
          + AppUtilities.getCleansedAppServerStackTraceString(t, true));

      this.errorLogProcess.handleMDBException(this.getIsRedelivered(msg), this
          .getClass().getName(), "OnMessage", t,
          "Unexpected Exception: " + t.getMessage(),
          AppraisalAssignmentConstants.ERROR_UNEXPECTED_EXCEPTION, messageText);

    }
  }
}
