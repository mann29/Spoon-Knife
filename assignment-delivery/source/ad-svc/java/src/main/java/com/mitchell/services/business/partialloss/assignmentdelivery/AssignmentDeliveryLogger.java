package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.systemconfiguration.SystemConfiguration;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * AssignmentDeliveryLogger extends Logger to provide several convenience methods.
 */
public class AssignmentDeliveryLogger extends AbstractAssignmentDeliveryLogger{
    
	private final String SET_LOGGING_BANNER = "/ASSIGNMENTDELIVERY/Logging/Banner";

	private final String SET_LOGGING_FOOTER = "/ASSIGNMENTDELIVERY/Logging/Footer";

	private final String LOG_HEADER = SystemConfiguration
			.getSettingValue(SET_LOGGING_BANNER);

	private final String LOG_FOOTER = SystemConfiguration
			.getSettingValue(SET_LOGGING_FOOTER);

	private final String DEFAULT_MSG = "\n\t\t!!!ASSIGNMENTDELIVERY NEEDS YOUR ATTENTION!!!";

	private final String MSG_LEADIN = "\nASSIGNMENTDELIVERY\n\tASSIGNMENTDELIVERY\t\tASSIGNMENTDELIVERY\n\t\t\t";

	private final String PERIOD = ".";

	private String m_lastAction = null;

	private String m_className = "";

	private Stack m_methodStack = new Stack();

	private Logger m_logger = null;

	/**
	 * Constructor to accept a class name. Calls super() and stores the class
	 * name in an instance variable for later use when other methods requiring
	 * class name are called.
	 */
	public AssignmentDeliveryLogger(String className) {
		super(className, null);
		m_logger = super.getLogger(className);
		m_className = getClassName(className);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#entering(java.lang.String)
	 */
	public void entering(String methodName) {
		m_logger.entering(m_className, methodName);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#entering(java.lang.String, java.lang.String)
	 */
	public void entering(String className, String methodName) {
		m_logger.entering(className, methodName);
		if (className != null && className.length() > 0)
			m_className = className;
		try {
			m_methodStack.push((Object) methodName);
		} catch (Exception e) {
		}
		;
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#exiting(java.lang.String)
	 */
	public void exiting(String methodName) {
		this.exiting(m_className, methodName);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#exiting()
	 */
	public void exiting() {
		try {
			this.exiting(methodStackPeek());
		} catch (Exception e) {
		}
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#exiting(java.lang.String, java.lang.String)
	 */
	public void exiting(String className, String methodName) {
		m_logger.exiting(className, methodName);
		try {
			m_methodStack.pop();
		} catch (Exception e) {
		}
		;
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#starting(java.lang.String)
	 */
	public void starting(String info) {
		if (m_lastAction != null)
			finished();
		m_logger.info(info);
		m_lastAction = info;
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#finished()
	 */
	public void finished() {
		m_logger.info("Finished: " + m_lastAction);
		m_lastAction = null;
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#info(java.lang.String)
	 */
	public void info(String msg) {
		m_logger.info(msg);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#severe(java.lang.String)
	 */
	public void severe(String msg) {
		try {
			String text = (LOG_HEADER != null ? LOG_HEADER : DEFAULT_MSG);

			if (m_lastAction != null)
				text += "While attempting: " + m_lastAction
						+ "; the following occurred.\n\n";
			text += MSG_LEADIN + msg;
			text += (LOG_FOOTER != null ? LOG_FOOTER : DEFAULT_MSG);

			m_logger.severe(text);
			m_lastAction = null;
		} catch (Exception ex) {
			m_logger
					.severe("Exception occurred while trying to log an error to the error logging service.");
		}
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#createException(java.lang.String)
	 */
	public AssignmentDeliveryException createException(String desc) {

		return new AssignmentDeliveryException(m_className,
				methodStackPeek(), desc);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#createException(int, java.lang.String)
	 */
	public AssignmentDeliveryException createException(int errNumber, String desc) {

		return new AssignmentDeliveryException(errNumber, m_className,
				methodStackPeek(), desc);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#createException(int, java.lang.String, java.lang.String)
	 */
	public AssignmentDeliveryException createException(int errNumber,
			String workItemID, String desc) {

		return new AssignmentDeliveryException(errNumber, m_className,
				methodStackPeek(), workItemID, desc);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#createException(java.lang.Throwable)
	 */
	public AssignmentDeliveryException createException(Throwable t) {

		return new AssignmentDeliveryException(m_className,
				methodStackPeek(), t.getMessage(), t);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#createException(java.lang.String, java.lang.Throwable)
	 */
	public AssignmentDeliveryException createException(String desc, Throwable t) {

		return new AssignmentDeliveryException(m_className,
				methodStackPeek(), desc, t);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#createException(int, java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	public AssignmentDeliveryException createException(int errNumber,
			String workItemID, String desc, Throwable t) {

		return new AssignmentDeliveryException(errNumber, m_className,
				methodStackPeek(), workItemID, desc, t);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#createException(int, java.lang.String, java.lang.Throwable)
	 */
	public AssignmentDeliveryException createException(int errNumber,
			String workItemID, Throwable t) {

		return new AssignmentDeliveryException(errNumber, m_className,
				methodStackPeek(), workItemID, t.getMessage(), t);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#createException(int, java.lang.Throwable, java.lang.String)
	 */
	public AssignmentDeliveryException createException(int errNumber,
			Throwable t, String desc) {

		return new AssignmentDeliveryException(errNumber, m_className,
				methodStackPeek(), desc, t);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLoggerInterface#createException(int, java.lang.Throwable)
	 */
	public AssignmentDeliveryException createException(int errNumber,
			Throwable t) {

		return new AssignmentDeliveryException(errNumber, m_className,
				methodStackPeek(), t.getMessage(), t);
	}

	/*
	 * Returns a class name after getting it out of a package name.
	 */
	private String getClassName(String className) {
		return (className.indexOf(PERIOD) > 1 ? className.substring(className
				.lastIndexOf(PERIOD) + 1) : className);
	}

	private String methodStackPeek() {
		String methodName = "";
		try {
			methodName = (String) m_methodStack.peek();
		} catch (Exception e) {
			// do nothing - just eat it.
		}

		return methodName;
	}
}
