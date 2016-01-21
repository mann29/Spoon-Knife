package com.mitchell.services.business.partialloss.assignmentdelivery.ioc;

import java.util.logging.Logger;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring context BeanLocator that implements the reference counted container.
 * Typically the only modification that might be needed to use this class in a
 * service/app is to change the value of the APP_CONTEXT_FILE constant if the
 * spring context file name is different.
 * 
 * The Spring ClassPathXmlApplicationContext object must be closed upon exit of
 * the ear from the AppServer, if this is not done a memory leak occurs. In
 * JBoss, any time that an ear that uses a ClassPathXmlApplicationContext is for
 * example redeployed, if the close method is not called on the instance of the
 * ClassPathXmlApplicationContext class there is a PermGen memory leak. This
 * class provides a means, via reference counting, to create and access a
 * singleton instance of the ClassPathXmlApplicationContext and properly close
 * the instance. Below is an example of how to properly use this class with a
 * stateless EJB.
 * 
 */
/*- 
 * Given a stateless EJB (or MDB) do the following:
 * 
 * 1) Add a member variable to the EJB like this:
 * 
 * private SpringReferencedContextContainer springContext = null;
 * 
 * 2) Add a @PostConstruct method to the EJB like this:
 * 
 *  @PostConstruct
 *  public void initializeResources()
 *  {
 *    this.springContext = (SpringReferencedContextContainer) new
 *    BeanLocator();
 *    this.springContext.addContextReference();
 *  }
 * 
 * 3) Add a @PreDestroy method to the EJB like this:
 * 
 *  @PreDestroy
 *  public void releaseResources()
 *  {
 *    if (this.springContext != null) {
 *      this.springContext.removeContextReference();
 *    }
 *  }
 * 
 * 4) Then access the getBean method like: BeanLocator.getBean(...)
 * 
 * 5) As long as at least one call to addContextReference() is made prior to any call to BeanLocator.getBean(...)
 *  and a corresponding call to removeContextReference() is made for each addContextReference() then the
 *  ClassPathXmlApplicationContext will be properly closed.
 */
public class SpringHelper implements SpringReferencedContextContainer
{
  // --------------------------------------------------------------------------------
  // Put the name of the Spring Context XML file here.
  private static final String APP_CONTEXT_FILE = "assignment-delivery-ejb-context.xml";
  // --------------------------------------------------------------------------------

  private static final Object mutex = new Object();

  private static volatile ClassPathXmlApplicationContext appContext = null;
  private static long appContextRefCount = 0;

  private static final Logger logger = Logger.getLogger(SpringHelper.class
      .getName());

  private boolean isAquired = false;

  /**
   * Add Context Reference. Call this method at least once prior to any calls to
   * getBean(). This method is synchronized and will create a singleton instance
   * of the Spring context in the case where the singleton has not already be
   * created. The creation of the singleton is done this way rather than via a
   * static initializer because a static initializer causes load ordering
   * issues. Do not use a static initializer in conjunction with the
   * ClassPathXmlApplicationContext class. This method increments the reference
   * count by one. Each call to this method should ultimately result in a
   * corresponding call to the removeContextReference() method.
   */
  public void addContextReference()
  {
    synchronized (mutex) {
      if (!this.isAquired) {
        if (appContext == null) {
          createAppContext();
        }
        appContextRefCount++;
        this.isAquired = true;

        if (logger.isLoggable(java.util.logging.Level.INFO)) {
          logger.info("Spring Application Context Acquired: "
              + appContextRefCount);
        }

      }
    }
  }

  /**
   * Remove Context Reference. This method is synchronized and decrements the
   * reference count by one. If the reference count goes from a value of 1 to a
   * value of 0 the singleton Spring Context object is closed and set to a value
   * of null. This method should be called one to one in conjunction with the
   * addContextReference() method.
   */
  public void removeContextReference()
  {
    synchronized (mutex) {
      if (this.isAquired) {
        this.isAquired = false;
        if (appContextRefCount > 0) {
          appContextRefCount--;

          if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Spring Application Context Released: "
                + appContextRefCount);
          }

          if (appContextRefCount == 0) {
            closeAppContext();
          }
        }
      }
    }
  }

  /**
   * Get Bean. Call this method to get an instance of a Spring defined bean. At
   * least one call must be made to the addContextReference() method of an
   * instance of this class prior to calling this method. If this method is
   * called with an instance of the context singleton having not been properly
   * initialized, via the addContextReference(), an IllegalAccessException
   * exception is thrown. See the documentation in this class.
   * 
   * @param name Name of the bean from the XML context.
   * @return Returns the Spring initialized bean.
   * @throws IllegalAccessException
   */
  public static Object getBean(String name)
      throws IllegalAccessException
  {
    if (appContext == null) {
      throw new IllegalAccessException("Context reference is not available.");
    }
    return appContext.getBean(name);
  }

  /**
   * Creates an instance of the static appContext member variable if the value
   * of the member variable is currently null. It is assumed that
   * threading/concurrency issues are dealt with externally to this method.
   */
  private void createAppContext()
  {
    if (appContext == null) {
      appContext = new ClassPathXmlApplicationContext(
          new String[] { APP_CONTEXT_FILE });

      if (logger.isLoggable(java.util.logging.Level.INFO)) {
        logger.info("Spring Application Context Created.");
      }

    }
  }

  /**
   * Closes the non-null instance of the static appContext member variable and
   * sets the value to null. It is assumed that threading/concurrency issues are
   * dealt with externally to this method.
   */
  private void closeAppContext()
  {
    if (appContext != null) {

      try {
        appContext.close();
      } finally {
        appContext = null;
      }

      if (logger.isLoggable(java.util.logging.Level.INFO)) {
        logger.info("Spring Application Context Closed.");
      }

    }
  }

}
