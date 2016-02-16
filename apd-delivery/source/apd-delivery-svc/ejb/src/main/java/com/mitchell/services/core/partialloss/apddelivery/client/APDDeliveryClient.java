package com.mitchell.services.core.partialloss.apddelivery.client;

import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.partialloss.apddelivery.ejb.APDDeliveryServiceEJBRemote;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxyImpl;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;

/**
 * This class is used by the clients of APDDeliveryService
 * to get the EJB Remote interface stub.
 * 
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 */
public class APDDeliveryClient
{

  /**
   * Class name.
   */
  private static final String CLASS_NAME = APDDeliveryClient.class.getName();
  /**
   * logger instance.
   */
  private static Logger logger = Logger.getLogger(CLASS_NAME);

  /**
   * Default constructor.
   */
  protected APDDeliveryClient()
  {
  }

  /**
   * This method returns the APDDeliveryServiceEJB.
   * 
   * @return APDDeliveryServiceEJBRemote
   * @throws MitchellException
   *           Mitchell Exception
   */
  public static APDDeliveryServiceEJBRemote getAPDDeliveryEJB()
      throws MitchellException
  {

    String methodName = "getAPDDeliveryEJB";
    logger.entering(CLASS_NAME, methodName);

    APDDeliveryServiceEJBRemote remote = null;
    SystemConfigurationProxyImpl systemConfigurationProxy = null;

    try {
      // get properties for lookup
      systemConfigurationProxy = new SystemConfigurationProxyImpl();

      String providerUrl = systemConfigurationProxy.getProviderUrl();
      String name = systemConfigurationProxy.getEJBJndi();
      String contextFactory = systemConfigurationProxy.getJndiFactory();

      Properties environment = new Properties();
      environment.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
      environment.put(Context.PROVIDER_URL, providerUrl);
      Context ctx = new InitialContext(environment);

      remote = (APDDeliveryServiceEJBRemote) ctx.lookup(name);

    } catch (Exception e) {

      throw new MitchellException(APDDeliveryConstants.ERROR_EJB, CLASS_NAME,
          methodName, APDDeliveryConstants.ERROR_EJB_MSG, e);
    }

    logger.exiting(CLASS_NAME, methodName);
    return remote;
  }
}
