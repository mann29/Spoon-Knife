package com.mitchell.services.core.partialloss.apddelivery.ejb;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jboss.ejb3.annotation.RemoteBinding;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.core.partialloss.apddelivery.pojo.APDDeliveryService;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.BeanLocator;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.SpringReferencedContextContainer;

@Stateless(name = "APDDeliveryServiceEJB")
@Remote(APDDeliveryServiceEJBRemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@RemoteBinding(jndiBinding = "com.mitchell.services.core.partialloss.apddelivery.ejb.APDDeliveryServiceEJBRemote")
public class APDDeliveryServiceEJB implements APDDeliveryServiceEJBRemote
{

  // Spring Referenced Counted Context Container
  private SpringReferencedContextContainer springContext = null;

  /**
   * EJB create.
   */
  public void ejbCreate()
  {
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

  /**
   * @param apdCcontext
   *          An instance of APDDeliveryContextDocument.
   * @throws MitchellException
   *           Mitchell Exception
   * @ejbgen:remote-method
   */
  public void deliverArtifact(APDDeliveryContextDocument apdCcontext)
      throws MitchellException
  {
    APDDeliveryService apdDeliveryService = (APDDeliveryService) getBeanFromLocator("apdDeliveryService");
    apdDeliveryService.deliverArtifact(apdCcontext);
  }

  /**
   * <p>
   * This method will handle delivering of appraisal assignments to platform or
   * non-platform. For all other types, it will throw unsupported operation
   * exception
   * 
   * @param apdContext
   *          An instance of APDDeliveryContextDocument.
   * @param attachments
   *          ArrayList of attachments of type parts list
   * @throws MitchellException
   *           Mitchell Exception
   * @ejbgen:remote-method
   */
  public void deliverAppraisalAssignment(APDDeliveryContextDocument apdContext,
      ArrayList attachments)
      throws MitchellException
  {
    APDDeliveryService apdDeliveryService = (APDDeliveryService) getBeanFromLocator("apdDeliveryService");
    apdDeliveryService.deliverAppraisalAssignment(apdContext, attachments);
  }

  /**
   * Wrapper call to BeanLocator that deals with the IllegalAccessException.
   */
  protected Object getBeanFromLocator(String beanName)
      throws MitchellException
  {
    Object o = null;
    try {
      o = BeanLocator.getBean(beanName);
    } catch (IllegalAccessException e) {
      throw new MitchellException(
          "APDDeliveryServiceEJB",
          "getBeanFromLocator",
          "Access exception from Spring BeanLocator, probably not properly initialized.",
          e);
    }
    return o;
  }
}
