package com.mitchell.services.business.questionnaireevaluation.ejb;

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
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.ejb3.annotation.Depends;
import org.jboss.ejb3.annotation.Pool;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.questionnaireevaluation.impl.QuestionnaireEvaluationImplProxy;
import com.mitchell.services.business.questionnaireevaluation.util.BeanLocator;
import com.mitchell.services.business.questionnaireevaluation.util.SpringReferencedContextContainer;
import com.mitchell.utils.misc.AppUtilities;

@MessageDriven(name = "QuestionnaireEvaluationMDB", mappedName = "QuestionnaireEvaluationMDB", activationConfig = {
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "WORKCENTER.SAVE_CONTINGENCY_EVAL_MSG_HANDLER_INPUT"),
    @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "3"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Depends("org.hornetq:module=JMS,name=\"WORKCENTER.SAVE_CONTINGENCY_EVAL_MSG_HANDLER_INPUT\",type=Queue")
@Pool(value = "StrictMaxPool", maxSize = 3, timeout = 10000)
public class QuestionnaireEvaluationMDB implements MessageListener{
  /**
   * class name.
   */
  private static final String CLASS_NAME = QuestionnaireEvaluationMDB.class
      .getName();

  /**
   * Logger Instance.
   */
  private static Logger logger = Logger.getLogger(CLASS_NAME);

  /**
   * MessageDrivenContext
   */
  private MessageDrivenContext mdc = null;

  // Spring Referenced Counted Context Container
  private SpringReferencedContextContainer springContext = null;

  /**
   * Sets MessageDrivenContext to protected member: messageCtx.
   * 
   * @param context
   *          MessageDrivenContext
   */
  @Resource
  public void setMessageDrivenContext(MessageDrivenContext context)  {
    mdc = context;
  }

  /**
   * Add a reference to the Spring Context for this EJB at time of EJB
   * construction.
   */
  @PostConstruct
  public void initializeResources()  {
    this.springContext = (SpringReferencedContextContainer) new BeanLocator();
    this.springContext.addContextReference();
  }

  /**
   * Remove the reference from the Spring Context for this EJB at time of EJB
   * destruction.
   */
  @PreDestroy
  public void releaseResources()  {
    if (this.springContext != null) {
      this.springContext.removeContextReference();
    }
  }

  /**
   * This MDB is used to save the Evaluation generated during Contingency
   * period.
   */
  public void onMessage(Message message)  {
    String methodName = "onMessage";
    logger.entering(CLASS_NAME, methodName);
    try {
      TextMessage textMessage = (TextMessage) message;
      QuestionnaireEvaluationImplProxy qeImplProxy = (QuestionnaireEvaluationImplProxy) BeanLocator
          .getBean("questionnaireEvaluationImpl");
      qeImplProxy.saveEvaluationAsync(textMessage.getText());
    } catch (MitchellException mex) {
      mdc.setRollbackOnly();
      logger.severe("Got MitchellException: "
          + AppUtilities.getCleansedAppServerStackTraceString(mex, true));
    } catch (Throwable ex) {
      mdc.setRollbackOnly();
      logger.severe("Got Unknown Exception "
          + AppUtilities.getCleansedAppServerStackTraceString(ex, true));
    }
    logger.exiting(CLASS_NAME, methodName);
  }
}
