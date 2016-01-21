package com.mitchell.services.business.partialloss.assignmentdelivery;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.mitchell.services.business.partialloss.assignmentdelivery.handler.daytona.DaytonaRAHandler;
import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.PlatformDelRouter;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl.AbstractMessageBusDeliveryHandler;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.AssignmentEmailDeliveryHandler;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.service.AssignmentEmailDeliveryService;
import com.mitchell.services.business.partialloss.assignmentdelivery.impl.AssignmentDeliveryService;
import com.mitchell.services.business.partialloss.assignmentdelivery.ioc.SpringHelper;
import com.mitchell.services.business.partialloss.assignmentdelivery.ioc.SpringReferencedContextContainer;

@Stateless(name = "AssignmentDelivery")
@Remote(AssignmentDeliveryRemote.class)
@Local(AssignmentDeliveryLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@RemoteBinding(jndiBinding = "com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryRemote")
@LocalBinding(jndiBinding = "com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLocal")
public class AssignmentDeliveryEJB implements AssignmentDeliveryLocal,
    AssignmentDeliveryRemote
{

  private SessionContext context = null;
  private final AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(
      this.getClass().getName());
  private static String CLASS_NAME = "AssignmentDeliveryEJB";
  transient private AssignmentDeliveryService deliverySerivce;
  transient private PlatformDelRouter platformDeliveryRouter;
  transient private AssignmentEmailDeliveryHandler assignmentEmailDeliveryHandler;
  transient private AssignmentEmailDeliveryHandler assignmentEmailDeliveryHandlerDRP;
  transient private AssignmentEmailDeliveryService assignmentEmailDeliveryService;
  transient private AbstractMessageBusDeliveryHandler webAssignmentDeliveryHandler;
  transient private DaytonaRAHandler daytonaRAHandler;

  // Spring Referenced Counted Context Container
  private SpringReferencedContextContainer springContext = null;

  @Resource
  private void setSessionContext(SessionContext sctx)
  {
    this.context = sctx;
  }

  /**
   * Add a reference to the Spring Context for this EJB at time of EJB
   * construction.
   * 
   * @throws IllegalAccessException
   */
  @PostConstruct
  public void initializeResources()
      throws IllegalAccessException
  {
    this.springContext = (SpringReferencedContextContainer) new SpringHelper();
    this.springContext.addContextReference();

    mLogger
        .fine("Getting a reference to ADS.mitchell.assignmentDeliveryService");
    deliverySerivce = (AssignmentDeliveryService) SpringHelper
        .getBean("ADS.mitchell.assignmentDeliveryService");

    mLogger.fine("Getting a reference to ADS.mitchell.apd.integration.router");
    platformDeliveryRouter = (PlatformDelRouter) SpringHelper
        .getBean("ADS.mitchell.apd.integration.router");

    mLogger.fine("Getting a reference to AssignmentEmailDeliveryHandler");
    assignmentEmailDeliveryHandler = (AssignmentEmailDeliveryHandler) SpringHelper
        .getBean("AssignmentEmailDeliveryHandler");

    mLogger.fine("Getting a reference to AssignmentEmailDeliveryHandlerDRP");
    assignmentEmailDeliveryHandlerDRP = (AssignmentEmailDeliveryHandler) SpringHelper
        .getBean("AssignmentEmailDeliveryHandlerDRP");

    mLogger.fine("Getting a reference to AssignmentEmailDeliveryService");
    assignmentEmailDeliveryService = (AssignmentEmailDeliveryService) SpringHelper
        .getBean("AssignmentEmailDeliveryService");

    mLogger
        .fine("Getting a reference to WebAssignmentMessageBusDeliveryHandler");
    webAssignmentDeliveryHandler = (AbstractMessageBusDeliveryHandler) SpringHelper
        .getBean("WebAssignmentMessageBusDeliveryHandler");

    mLogger
        .fine("Getting a reference to DaytonaRAHandler");
    daytonaRAHandler = (DaytonaRAHandler) SpringHelper
            .getBean("DaytonaRAHandler");

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

  public void cancelAssignment(AssignmentServiceContext assignmentSvcContext)
      throws AssignmentDeliveryException
  {
    deliverySerivce.cancelAssignment(assignmentSvcContext);

  }

  public void deliverAssignment(AssignmentServiceContext assignmentSvcContext)
      throws AssignmentDeliveryException
  {
    deliverySerivce.deliverAssignment(assignmentSvcContext);

  }

  public void deliverAssignment(APDDeliveryContextDocument context)
      throws AssignmentDeliveryException
  {
    try {
      platformDeliveryRouter.route(context);
    } catch (MitchellException e) {
      throw new AssignmentDeliveryException(getClass().getName(),
          "deliverAssignment(final APDDeliveryContextDocument context)",
          "Error occurred", e);
    }

  }

  public void deliverAssignmentEmail(APDDeliveryContextDocument context)
      throws MitchellException
  {
    mLogger.info("Entering in deliverAssignmentEmail in AssignmentDeliveryEJB");
    try {
      assignmentEmailDeliveryHandler.deliverCreation(context, false, "en-US");
      mLogger.info("Context :" + context);
    } catch (MitchellException e) {
      this.context.setRollbackOnly();
      throw e;
    } catch (Exception e) {
      this.context.setRollbackOnly();
      throw new MitchellException(AssignmentDeliveryErrorCodes.ERROR_ASG_EMAIL,
          getClass().getName(), "deliverAssignmentEmail", e.getMessage(), e);
    } catch (Throwable e) {
      this.context.setRollbackOnly();
      throw new MitchellException(AssignmentDeliveryErrorCodes.ERROR_ASG_EMAIL,
          getClass().getName(), "deliverAssignmentEmail", e.getMessage(), e);
    }
    mLogger.info("Exiting in deliverAssignmentEmail in AssignmentDeliveryEJB");

  }

  public void deliverAssignmentEmail4DRP(
      final APDDeliveryContextDocument context)
      throws MitchellException
  {
    mLogger
        .info("Entering in deliverAssignmentEmail4DRP in AssignmentDeliveryEJB");
    mLogger.info("Context :" + context);
    try {
      assignmentEmailDeliveryHandlerDRP
          .deliverCreation(context, false, "en-US");
    } catch (MitchellException e) {
      this.context.setRollbackOnly();
      throw e;
    } catch (Exception e) {
      this.context.setRollbackOnly();
      throw new MitchellException(AssignmentDeliveryErrorCodes.ERROR_ASG_EMAIL,
          getClass().getName(), "deliverAssignmentEmail", e.getMessage(), e);
    } catch (Throwable e) {
      this.context.setRollbackOnly();
      throw new MitchellException(AssignmentDeliveryErrorCodes.ERROR_ASG_EMAIL,
          getClass().getName(), "deliverAssignmentEmail", e.getMessage(), e);
    }
    mLogger
        .info("Entering in deliverAssignmentEmail4DRP in AssignmentDeliveryEJB");
  }

  public void deliverEstimateUploadFailEmail(APDDeliveryContextDocument context)
      throws MitchellException
  {
    mLogger
        .info("Entering in deliverEstimateUploadFailEmail in AssignmentDeliveryEJB");
    try {
      assignmentEmailDeliveryHandler.deliverUploadFail(context);
    } catch (MitchellException e) {
      this.context.setRollbackOnly();
      throw e;
    } catch (Exception e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_UPLOAD_FAIL_EMAIL, getClass()
              .getName(), "deliverEstimateUploadFailEmail", e.getMessage(), e);
    } catch (Throwable e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_UPLOAD_FAIL_EMAIL, getClass()
              .getName(), "deliverEstimateUploadFailEmail", e.getMessage(), e);
    }
    mLogger
        .info("Exiting  deliverEstimateUploadFailEmail AssignmentDeliveryEJB");

  }

  public void deliverEstimateUploadFailEmailDRP(
      final APDDeliveryContextDocument context)
      throws MitchellException
  {
    mLogger
        .info("Entering in deliverEstimateUploadFailEmailDRP in AssignmentDeliveryEJB");
    try {
      assignmentEmailDeliveryHandlerDRP.deliverUploadFail(context);
    } catch (MitchellException e) {
      this.context.setRollbackOnly();
      throw e;
    } catch (Exception e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_UPLOAD_FAIL_EMAIL, getClass()
              .getName(), "deliverEstimateUploadFailEmail", e.getMessage(), e);
    } catch (Throwable e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_UPLOAD_FAIL_EMAIL, getClass()
              .getName(), "deliverEstimateUploadFailEmail", e.getMessage(), e);
    }

    mLogger
        .info("Exiting  deliverEstimateUploadFailEmailDRP in  AssignmentDeliveryEJB");
  }

  public void deliverEstimateUploadSuccessEmail(
      APDDeliveryContextDocument context)
      throws MitchellException
  {
    mLogger
        .info("Entering in deliverEstimateUploadSuccessEmail in AssignmentDeliveryEJB");
    try {
      assignmentEmailDeliveryHandler.deliverUploadSuccess(context);
    } catch (MitchellException e) {
      this.context.setRollbackOnly();
      throw e;
    } catch (Exception e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_UPLOAD_SUCCESS_EMAIL, getClass()
              .getName(), "deliverEstimateUploadSuccessEmail", e.getMessage(),
          e);
    } catch (Throwable e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_UPLOAD_SUCCESS_EMAIL, getClass()
              .getName(), "deliverEstimateUploadSuccessEmail", e.getMessage(),
          e);
    }
    mLogger
        .info("Exiting  deliverEstimateUploadSuccessEmail in AssignmentDeliveryEJB");
  }

  public void deliverEstimateUploadSuccessEmailDRP(
      final APDDeliveryContextDocument context)
      throws MitchellException
  {
    mLogger
        .info("Entering in deliverEstimateUploadSuccessEmailDRP in AssignmentDeliveryEJB");
    try {
      assignmentEmailDeliveryHandlerDRP.deliverUploadSuccess(context);
    } catch (MitchellException e) {
      this.context.setRollbackOnly();
      throw e;
    } catch (Exception e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_UPLOAD_SUCCESS_EMAIL, getClass()
              .getName(), "deliverEstimateUploadSuccessEmail", e.getMessage(),
          e);
    } catch (Throwable e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_UPLOAD_SUCCESS_EMAIL, getClass()
              .getName(), "deliverEstimateUploadSuccessEmail", e.getMessage(),
          e);
    }
    mLogger
        .info("Exiting deliverEstimateUploadSuccessEmailDRP in AssignmentDeliveryEJB");
  }

  public void deliverNonDrpShopSuppAssignmentEmail(
      APDDeliveryContextDocument context, ArrayList partsListAttachement)
      throws MitchellException
  {
    try {

      assignmentEmailDeliveryHandler.deliverSupplementEmail(context,
          partsListAttachement, false, "en-US");
    } catch (MitchellException e) {
      this.context.setRollbackOnly();
      throw e;
    } catch (Exception e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_SUPP_ASG_EMAIL, getClass()
              .getName(), "deliverNonDrpShopSuppAssignmentEmail",
          e.getMessage(), e);
    } catch (Throwable e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_SUPP_ASG_EMAIL, getClass()
              .getName(), "deliverNonDrpShopSuppAssignmentEmail",
          e.getMessage(), e);
    }

  }

  public void deliverDrpShopSuppAssignmentEmail(
      final APDDeliveryContextDocument context,
      final ArrayList partsListAttachement)
      throws MitchellException
  {
    try {
      assignmentEmailDeliveryHandlerDRP.deliverSupplementEmail(context,
          partsListAttachement, false, "en-US");
    } catch (MitchellException e) {
      this.context.setRollbackOnly();
      throw e;
    } catch (Exception e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_SUPP_ASG_EMAIL, getClass()
              .getName(), "deliverNonDrpShopSuppAssignmentEmail",
          e.getMessage(), e);
    } catch (Throwable e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_SUPP_ASG_EMAIL, getClass()
              .getName(), "deliverNonDrpShopSuppAssignmentEmail",
          e.getMessage(), e);
    }
  }

  public void deliverAssignmentEmailNotification(
      final APDDeliveryContextDocument context,
      final ArrayList partsListAttachment, final String emailtype)
      throws MitchellException
  {
    try {
      assignmentEmailDeliveryService.deliveryAssignmentEmailNotification(
          context, partsListAttachment, emailtype);
    } catch (MitchellException e) {
      throw e;
    } catch (Exception e) {
      throw new MitchellException(AssignmentDeliveryErrorCodes.ERROR_ASG_EMAIL,
          getClass().getName(), "deliverAssignmentEmailNotification",
          e.getMessage(), e);
    }
  }

  public void deliverRCWebAssignment(APDDeliveryContextDocument context)
      throws MitchellException
  {

    final String methodName = "deliverRCWebAssignment";
    this.mLogger.entering(CLASS_NAME, methodName);

    try {
      String assignmentType = context.getAPDDeliveryContext().getMessageType();

      if (assignmentType.equalsIgnoreCase("REPAIR_ASSIGNMENT") || assignmentType.equalsIgnoreCase("REWORK_ASSIGNMENT")) {
          this.daytonaRAHandler.deliverAssignment(context);
      } else {
          this.webAssignmentDeliveryHandler.deliverAssignment(context);
      }
    } catch (MitchellException e) {
      this.context.setRollbackOnly();
      throw e;
    } catch (Exception e) {
      this.context.setRollbackOnly();
      // Need to update error codes and use the correct ones
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_UPLOAD_FAIL_EMAIL, getClass()
              .getName(), methodName, e.getMessage(), e);
    } catch (Throwable e) {
      this.context.setRollbackOnly();
      throw new MitchellException(
          AssignmentDeliveryErrorCodes.ERROR_UPLOAD_FAIL_EMAIL, getClass()
              .getName(), methodName, e.getMessage(), e);
    }
    this.mLogger.exiting(CLASS_NAME, methodName);
  }

}
