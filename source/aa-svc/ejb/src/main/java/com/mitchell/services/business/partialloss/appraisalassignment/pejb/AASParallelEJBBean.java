package com.mitchell.services.business.partialloss.appraisalassignment.pejb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.xmlbeans.XmlException;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.external.AASExternalAccessor;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASJMSParallelManager;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelConstants;
import com.mitchell.utils.logging.MIProcessingInstanceLogger;

@Stateless
public class AASParallelEJBBean implements AASParallelEJB
{

  @EJB
  protected AASExternalAccessor extAccess;

  protected static Logger logger = Logger
      .getLogger("com.mitchell.services.business.partialloss.appraisalassignment.pejb.AASParallelEJBBean");
  private static final MIProcessingInstanceLogger miProcLogger = new MIProcessingInstanceLogger();

  /**
   * Main entry for submitting the request.
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public String submitRequests(List<String> meStrings)
      throws MitchellException
  {
    if (logger.isLoggable(java.util.logging.Level.INFO)) {
      logger.info("IN AASParallelEJBBean.submitRequests: " + meStrings.size());
    }

    long startTime = System.currentTimeMillis();

    String processingGroupId = null;

    try {

      // Create a parallel manager
      AASJMSParallelManager pMgr = newJMSParallelManager(
          getQueueConnectionFactoryName(), getParallelMDBInputQueueName(),
          getParallelResponseQueueName());

      // Submit
      processingGroupId = pMgr.submitProcessingRequests(meStrings);

    } catch (Exception e) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_PARALLEL_REQUEST_SUBMIT_ERROR,
          this.getClass().getName(), "submitRequests",
          "Exception posting messages.", e);
    }

    if (miProcLogger.isLoggable()) {
      miProcLogger.logProcessingInfoTotalTime(
          AppraisalAssignmentConstants.MODULE_NAME, "NA",
          System.currentTimeMillis() - startTime,
          "AASParallelEJBBean.submitRequests", "SUCCESS", "");
    }

    //
    return processingGroupId;
  }

  /**
   * Manage retrieving responses from both the response queue and the deadletter
   * queue.
   * 
   * @throws JMSException
   */
  public List<String> retrieveResponses(String responseGroupId,
      long maxNumberToReceive)
      throws MitchellException
  {
    List<Message> msgList = null;
    long startTime = System.currentTimeMillis();

    AASJMSParallelManager pMgr = newJMSParallelManager(
        getQueueConnectionFactoryName(), getParallelResponseQueueName());
    msgList = pMgr.retrieveProcessingResponses(responseGroupId,
        maxNumberToReceive);

    // Build the return list of strings

    List<String> returnList = null;
    try {

      if (msgList != null) {

        returnList = new ArrayList<String>();

        for (Message msg : msgList) {
          TextMessage tm = (TextMessage) msg;
          returnList.add(tm.getText());
        }

      }

    } catch (JMSException e) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_PARALLEL_RESPONSE_RETRIEVE_ERROR,
          this.getClass().getName(), "retrieveResponses",
          "Exception retrieving response messages.", e);
    }

    if (miProcLogger.isLoggable()) {
      miProcLogger.logProcessingInfoTotalTime(
          AppraisalAssignmentConstants.MODULE_NAME, "NA",
          System.currentTimeMillis() - startTime,
          "AASParallelEJBBean.retrieveResponses", "SUCCESS", "");
    }

    return returnList;
  }

  /**
   * Extract a Mitchell Envelope From JMS Message.
   */
  public MitchellEnvelopeDocument extractMitchellEnvelopeFromMessage(
      TextMessage msg)
      throws XmlException, JMSException
  {
    MitchellEnvelopeDocument doc = MitchellEnvelopeDocument.Factory.parse(msg
        .getText().trim());
    return doc;
  }

  /**
   * Submit the response message back to the response queue.
   * 
   * @throws MitchellException
   */
  public void submitResponse(TextMessage tMsg, String msg)
      throws MitchellException
  {
    AASJMSParallelManager pMgr = newJMSParallelManager(getQueueConnectionFactoryName());
    pMgr.submitProcessingResponse(tMsg, msg);
  }

  /**
   * Get the Queue Connection Factory Name.
   */
  protected String getQueueConnectionFactoryName()
  {
    return extAccess
        .getSystemConfigValue(AASParallelConstants.SYSCONF_QUEUE_CONN_FACTORY_NAME);
  }

  /**
   * Get the Parallel MDB Input Queue Name.
   */
  protected String getParallelMDBInputQueueName()
  {
    return extAccess
        .getSystemConfigValue(AASParallelConstants.SYSCONF_INPUT_QUEUE_NAME);
  }

  /**
   * Get the Parallel Response Queue Name.
   */
  protected String getParallelResponseQueueName()
  {
    return this.extAccess
        .getSystemConfigValue(AASParallelConstants.SYSCONF_RESPONSE_QUEUE_NAME);
  }

  /**
   * Isolate creation of the Parallel Manager.
   */
  protected AASJMSParallelManager newJMSParallelManager(
      String queueConnFactoryName, String inputQueueName,
      String responseQueueName)
  {
    return new AASJMSParallelManager(this.extAccess, queueConnFactoryName,
        inputQueueName, responseQueueName);
  }

  /**
   * Isolate creation of the Parallel Manager.
   */
  protected AASJMSParallelManager newJMSParallelManager(
      String queueConnectionFactoryName, String responseQueueName)
  {
    return new AASJMSParallelManager(this.extAccess,
        queueConnectionFactoryName, responseQueueName);
  }

  /**
   * Isolate creation of the Parallel Manager.
   */
  protected AASJMSParallelManager newJMSParallelManager(
      String queueConnectionFactoryName)
  {
    return new AASJMSParallelManager(this.extAccess, queueConnectionFactoryName);
  }

}
