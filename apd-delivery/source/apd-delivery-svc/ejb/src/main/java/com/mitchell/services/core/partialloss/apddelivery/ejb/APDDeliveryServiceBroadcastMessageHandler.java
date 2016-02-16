package com.mitchell.services.core.partialloss.apddelivery.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.ejb3.annotation.Depends;
import org.jboss.ejb3.annotation.Pool;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.core.partialloss.apddelivery.pojo.delegator.APDBroadcastMessageDelegator;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ErrorLogProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.BeanLocator;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.SpringReferencedContextContainer;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.CommonUtil;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.misc.UUIDFactory;

@MessageDriven(name = "APDDeliveryServiceBroadcastMessageHandler", mappedName = "APDDeliveryServiceBroadcastMessageHandler", activationConfig = {
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "APD.BroadcastMessage"),
    @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "4"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
@Depends("org.hornetq:module=JMS,name=\"APD.BroadcastMessage\",type=Queue")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Pool(value = "StrictMaxPool", maxSize = 4, timeout = 10000)
public class APDDeliveryServiceBroadcastMessageHandler implements
    MessageListener
{

  private static final String CLASS_NAME = APDDeliveryServiceBroadcastMessageHandler.class
      .getName();

  private static final Logger logger = Logger.getLogger(CLASS_NAME);

  // Spring Referenced Counted Context Container
  private SpringReferencedContextContainer springContext = null;

  /**
   * MessageDrivenContext.
   */
  private MessageDrivenContext msgContext = null;

  /**
   * Sets MessageDrivenContext to protected member: messageCtx.
   * 
   * @param context
   *          MessageDrivenContext
   */
  @Resource
  public void setMessageDrivenContext(MessageDrivenContext context)
  {
    msgContext = context;
  }

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

  public void onMessage(Message msg)
  {

    String methodName = "onMessage";

    logger.entering(CLASS_NAME, methodName);
    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Brodcast Message data received: " + msg);
    }
    String textMessage = null;

    try {

      textMessage = extractMessageText(msg);
      // parse the message
      APDDeliveryContextDocument apdDeliveryContext = this
          .getAPDDeliveryContext(textMessage);
      APDBroadcastMessageDelegator apdBmMsgDel = (APDBroadcastMessageDelegator) BeanLocator
          .getBean("apdBroadcastMessageDelegator");
      apdBmMsgDel.sendBroadcastMessage(apdDeliveryContext);

    } catch (MitchellException me) {

      // rollback the tx
      msgContext.setRollbackOnly();
      logger
          .severe("Exception occurred while processing following Broadcast Message: "
              + AppUtilities.getCleansedAppServerStackTraceString(me, true));

      processLogError(me, me.getWorkItemId(), textMessage);

    } catch (Throwable e) {

      // rollback the tx
      msgContext.setRollbackOnly();

      //This exception is thrown for all unknown error scenarios
      String workItemId = UUIDFactory.getInstance().getUUID();
      MitchellException me = new MitchellException(
          APDDeliveryConstants.ERROR_APD_BROADCAST_MESSAGE_MDB, CLASS_NAME,
          methodName, workItemId,
          APDDeliveryConstants.ERROR_APD_BROADCAST_MESSAGE_MDB_MSG, e);

      me.setSeverity(MitchellException.SEVERITY.FATAL);
      me.setApplicationName(APDDeliveryConstants.APP_NAME);
      me.setModuleName(APDDeliveryConstants.MODULE_NAME);

      logger
          .severe("Exception occurred while processing following Broadcast Message: "
              + AppUtilities.getCleansedAppServerStackTraceString(me, true));

      processLogError(me, workItemId, textMessage);

    }
  }

  private String extractMessageText(Message message)
      throws MitchellException
  {

    String methodName = "extractMessageText";
    logger.entering(CLASS_NAME, methodName);

    String textMessage = null;
    // verify if the message is a text message
    try {
      if (message instanceof TextMessage) {
        textMessage = ((TextMessage) message).getText();
      } else {
        throw new MitchellException(CLASS_NAME, methodName,
            "Input Message is not a text message " + message);
      }
    } catch (JMSException jmse) {
      throw new MitchellException(CLASS_NAME, methodName, "JMS error occurred",
          jmse);
    }

    logger.exiting(CLASS_NAME, methodName);
    return textMessage;
  }

  /**
   * This method parses message string into MitchellWorkflowMessage.
   * 
   * @param messageText
   *          String message text containing MWM doc
   * @return MitchellWorkflowMessageDocument
   *         MitchellWorkflowMessageDocument object
   * @throws MitchellException
   *           Mitchell Exception
   */
  private APDDeliveryContextDocument getAPDDeliveryContext(String messageText)
      throws MitchellException
  {

    String methodName = "getAPDDeliveryContext";
    logger.entering(CLASS_NAME, methodName);

    APDDeliveryContextDocument apdContext = null;
    try {
      // check if the message is null
      CommonUtil commonUtil = (CommonUtil) BeanLocator.getBean("commonUtil");
      if (commonUtil.isNullOrEmpty(messageText)) {
        throw new Exception("Input message is null");
      }

      // get the mwmDoc
      apdContext = APDDeliveryContextDocument.Factory.parse(messageText);

    } catch (Exception e) {

      MitchellException me = new MitchellException(
          APDDeliveryConstants.ERROR_PARSE_JMS_MSG, CLASS_NAME, methodName,
          UUIDFactory.getInstance().getUUID(),
          APDDeliveryConstants.ERROR_PARSE_JMS_MSG_MSG + "\n"
              + AppUtilities.getStackTraceString(e, true));

      me.setSeverity(MitchellException.SEVERITY.FATAL);
      me.setApplicationName(APDDeliveryConstants.APP_NAME);
      me.setModuleName(APDDeliveryConstants.MODULE_NAME);

      throw me;
    }

    logger.exiting(CLASS_NAME, methodName);
    return apdContext;
  }

  /**
   * 
   */
  protected void processLogError(MitchellException me, String workItemId,
      String textMessage)
  {
    try {
      ErrorLogProxy errorLogProxy = (ErrorLogProxy) BeanLocator
          .getBean("errorLogProxy");
      errorLogProxy.logError(me);
      errorLogProxy.logCorrelatedError(workItemId, textMessage, me);
    } catch (IllegalAccessException e) {
      logger.severe("Spring BeanLocator Exception while logging an Error. "
          + AppUtilities.getCleansedAppServerStackTraceString(e, true));
    }
  }

}
