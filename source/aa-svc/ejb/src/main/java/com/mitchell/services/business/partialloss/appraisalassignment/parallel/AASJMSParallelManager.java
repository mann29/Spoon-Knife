package com.mitchell.services.business.partialloss.appraisalassignment.parallel;

import com.mitchell.services.business.partialloss.appraisalassignment.external.AASExternalAccessor;
import com.mitchell.utils.misc.JMSParallelManager5;

/**
 * This class overrides the JMSParallelManager5 methods that return the wait
 * timeout and message expiration values such that they can be retrieved from
 * SystemConfiguration settings.
 */
public class AASJMSParallelManager extends JMSParallelManager5
{

  protected AASExternalAccessor extProxy;

  /**
   * Constructor
   */
  public AASJMSParallelManager(AASExternalAccessor sysConf,
      String qConnectionFactoryName)
  {
    super(qConnectionFactoryName);
    this.extProxy = sysConf;
  }

  /**
   * Constructor
   */
  public AASJMSParallelManager(AASExternalAccessor sysConf,
      String qConnectionFactoryName, String processingResponseQName)
  {
    super(qConnectionFactoryName, processingResponseQName);
    this.extProxy = sysConf;
  }

  /**
   * Constructor
   */
  public AASJMSParallelManager(AASExternalAccessor sysConf,
      String qConnectionFactoryName, String processingRequestQName,
      String processingResponseQName)
  {
    super(qConnectionFactoryName, processingRequestQName,
        processingResponseQName);
    this.extProxy = sysConf;
  }

  /**
   * Get the JMS Message Expiration value in Seconds from SystemConfiguration.
   */
  @Override
  public long getJmsMessageExpirationSeconds()
  {
    return getSysConfLong(AASParallelConstants.SYSCONF_MESSAGE_EXPIRATION_SECS,
        super.getJmsMessageExpirationSeconds());
  }

  /**
   * Get the Maximum Response Wait Time in Millis from SystemConfiguration.
   */
  @Override
  public long getMaxResponseWaitTimeMillis()
  {
    return getSysConfLong(AASParallelConstants.SYSCONF_MAX_RESPONSE_WAITTIME,
        super.getMaxResponseWaitTimeMillis());
  }

  /**
   * Get System Config Long value with default.
   */
  protected long getSysConfLong(String settingName, long defaultValue)
  {
    long retval = defaultValue;
    try {
      String ss = this.extProxy.getSystemConfigValue(settingName);
      if (ss != null && ss.length() > 0) {
        retval = Long.parseLong(ss);
      }
    } catch (Exception e) {
      retval = defaultValue;
    }
    return retval;

  }
}
