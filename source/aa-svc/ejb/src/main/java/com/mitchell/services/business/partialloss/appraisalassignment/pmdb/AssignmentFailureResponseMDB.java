package com.mitchell.services.business.partialloss.appraisalassignment.pmdb;

import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.delegator.AssignmentFailureResponseHandler;
import com.mitchell.services.business.partialloss.appraisalassignment.delegator.AssignmentFailureResponseHandlerImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtilsImpl;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.misc.UUIDFactory;

@MessageDriven(name = "com.mitchell.services.business.partialloss.appraisalassignment.pmdb.AssignmentFailureResponseMDB", mappedName = "PARTIALLOSS.ASSIGNMENT_FAILURE_RESPONSE_INPUT", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "PARTIALLOSS.ASSIGNMENT_FAILURE_RESPONSE_INPUT"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "3"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Depends({
		"org.hornetq:module=JMS,name=\"PARTIALLOSS.ASSIGNMENT_FAILURE_RESPONSE_INPUT\",type=Queue",
		"org.hornetq:module=JMS,name=\"PARTIALLOSS.ASSIGNMENT_FAILURE_RESPONSE_INPUT_DEADLETTER\",type=Queue" })
@Pool(value = "StrictMaxPool", maxSize = 3, timeout = 10000)
/**
 * This is Message-Driven Bean listen on the PPARTIALLOSS.ASSIGNMENT_FAILURE_RESPONSE_INPUT queue.
 * and process for the Silent Failure Response Appraisal Assignment.
 * @author Ravi.Kandukuri
 *
 */
public class AssignmentFailureResponseMDB implements MessageListener {
	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = AssignmentFailureResponseMDB.class
			.getName();

	/**
	 * logger.
	 */
	private static Logger logger = Logger.getLogger(CLASS_NAME);

	private AssignmentFailureResponseHandler asFailureRespHandler;

	/**
	 * MessageDrivenContext.
	 */
	private MessageDrivenContext msgContext = null;

	/**
	 * Sets MessageDrivenContext to protected member: messageCtx.
	 * 
	 * @param context
	 *            MessageDrivenContext
	 */
	@Resource
	public void setMessageDrivenContext(MessageDrivenContext context) {

		String methodName = "setMessageDrivenContext";
		logger.entering(CLASS_NAME, methodName);

		msgContext = context;

		logger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * Main entry point the the Assignment Failure Response MDB.
	 */
	public void onMessage(Message msg) {

		String methodName = "onMessage";
		logger.entering(CLASS_NAME, methodName);

		String textMessage = null;

		if (logger.isLoggable(Level.INFO)) {
			/* Cannot use the word Failure in the logs and so making "Appraisal Assignment Failure " as AAF " */
			logger.info("Inside onMessage of AAF MDB, recvd msg is:\n"
					+ msg);
		}
		try {

			// get the text message
			if (msg != null) {
				textMessage = extractMessageText(msg);
				asFailureRespHandler = new AssignmentFailureResponseHandlerImpl();
				asFailureRespHandler.processMessage(textMessage);
			}

		} catch (MitchellException mex) {

			msgContext.setRollbackOnly();
			if (logger.isLoggable(Level.SEVERE)) {
				/* Cannot use the word Failure in the logs and so making "Appraisal Assignment Failure " as AAF " */
				logger.severe("Exception occurred while processing following AAF Response message incoming from Message queue: "

						+ "\n" + AppUtilities.getStackTraceString(mex));
			}
			new AASCommonUtilsImpl()
					.logError(
							AppraisalAssignmentConstants.ERROR_PROCESSING_FAILURE_RESPONSE_MESSAGE,
							CLASS_NAME,
							methodName,
							"Exception occurred while processing following AAF Response message incoming from Message queue:",
							mex);

		} catch (Throwable e) {

			msgContext.setRollbackOnly();
			String workItemId = UUIDFactory.getInstance().getUUID();
			MitchellException me = new MitchellException(
					AppraisalAssignmentConstants.ERROR_PROCESSING_FAILURE_RESPONSE_MESSAGE,
					CLASS_NAME, methodName, workItemId, "Exception: "
							+ e.getMessage(), e);

			me.setSeverity(MitchellException.SEVERITY.FATAL);
			me.setApplicationName(AppraisalAssignmentConstants.APP_NAME);
			me.setModuleName(AppraisalAssignmentConstants.MODULE_NAME);

			if (logger.isLoggable(Level.SEVERE)) {
				logger.severe("Exception occurred while processing following AAF Response message incoming from Message queue: "

						+ "\n" + AppUtilities.getStackTraceString(me));
			}
			new AASCommonUtilsImpl()
					.logError(
							AppraisalAssignmentConstants.ERROR_PROCESSING_FAILURE_RESPONSE_MESSAGE,
							CLASS_NAME,
							methodName,
							"Exception occurred while processing following AAF Response message incoming from Message queue:",
							me);

		}

		logger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * This method extracts message string from Message object.
	 * 
	 * @param message
	 *            Message message
	 * @return messageText String Messagetext
	 * @throws Exception
	 *             Exception
	 */
	private String extractMessageText(Message message)
			throws MitchellException, Exception {

		String methodName = "extractMessageText";
		logger.entering(CLASS_NAME, methodName);

		String strMessage = null;
		try {
			// verify if the message is a text message
			if (message instanceof TextMessage) {
				strMessage = ((TextMessage) message).getText();
			} else {
				throw new Exception("Input Message is not a text message "
						+ message);
			}
		} catch (JMSException je) {
			logger.severe("Exception while extracting the text message");
			throw new Exception(je);
		}
		logger.exiting(CLASS_NAME, methodName);
		return strMessage;
	}

}
