/**
 * 
 */
package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentFailureResponseContext;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.AppLogProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.AppLogProxyImpl;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingToEventPublishingContextDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingType;
import com.mitchell.services.core.applicationlogging.AppLoggingToEventPublishingContextDocument.AppLoggingToEventPublishingContext;
import com.mitchell.services.core.applicationlogging.AppLoggingToEventPublishingContextDocument.AppLoggingToEventPublishingContext.EventDetails;
import com.mitchell.services.core.applicationlogging.AppLoggingToEventPublishingContextDocument.AppLoggingToEventPublishingContext.EventDetails.NameValuePair;
import com.mitchell.services.core.applog.client.AppLogging;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.utils.misc.AppUtilities;

/**
 * @author rk104152
 * 
 */
public class AssignmentAppLogHelperImpl implements AssignmentAppLogHelper {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = AssignmentAppLogHelperImpl.class
			.getName();
	/**
	 * logger.
	 */
	private static Logger logger = Logger.getLogger(CLASS_NAME);
	private AppLogProxy appLogProxy;

	/**
	 * 
	 * This method is used for publishing Event to Applog.
	 * 
	 * 
	 * @param UserInfoDocument
	 *            ui
	 * @param AssignmentFailureResponseContext
	 *            context
	 * 
	 * @throws MitchellException
	 * 
	 */
	public void appLog(UserInfoDocument ui,
			AssignmentFailureResponseContext context) throws MitchellException {
		String methodName = "appLog";
		logger.entering(CLASS_NAME, methodName);
		try {
			if (ui != null) {
				AppLoggingNVPairs appLoggingNVPairs = new AppLoggingNVPairs();
				Map<String, String> contextMap = context.getMap();
				if (contextMap != null) {
					Iterator<String> iterator = contextMap.keySet().iterator();

					String key = null;
					String value = null;

					while (iterator.hasNext()) {
						key = iterator.next().toString();
						value = contextMap.get(key).toString();
						appLoggingNVPairs.addPair(key, value);
					}
				}

				AppLoggingDocument logDoc = AppLoggingDocument.Factory
						.newInstance();
				AppLoggingType appType = logDoc.addNewAppLogging();
				appType.setMitchellUserId(context.getUserId());
				appType.setClaimExposureId(context.getSuffixId());
				appType.setClaimId(context.getClaimId());
				appType.setModuleName(AppraisalAssignmentConstants.MODULE_NAME);
				appType.setStatus(AppLogging.APPLOG_ST_OK);
				appType.setTransactionType(context.getTransactiontype());

				if (context.getClaimNumber() != null) {
					appType.setClaimNumber(context.getClaimNumber());
				}
				appType.setAppLoggingToEventPublishingContext(populateAppLogToEventPublishContext(context));
				appLogProxy = new AppLogProxyImpl();
				if (logger.isLoggable(Level.INFO)) {
					/*Cannot use FAILURE word and so Replacing Assignment Failure with AF */
					logger.info("Invoking AppLog method:logAppEvent() for Publishing Event AF!!\n");
				}
				appLogProxy.logAppEvent(logDoc, ui, context.getWorkItemId(),
						AppraisalAssignmentConstants.APP_NAME,
						AppraisalAssignmentConstants.MODULE_NAME,
						appLoggingNVPairs);

			}
		} catch (Exception ex) {
			MitchellException mex1 = new MitchellException(
					AppraisalAssignmentConstants.ERROR_PROCESSING_FAILURE_RESPONSE_DTO,
					CLASS_NAME, methodName,
					"Error processing AppLog for Assignment Failure Response"
							+ "\n" + AppUtilities.getStackTraceString(ex, true));
			throw mex1;
		}
		logger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * 
	 * This method is used for populating AppLogContext.
	 * 
	 * @param AssignmentFailureResponseContext
	 *            context
	 * @return AppLoggingToEventPublishingContext
	 * 
	 * @throws MitchellException
	 * 
	 */
	private AppLoggingToEventPublishingContext populateAppLogToEventPublishContext(
			AssignmentFailureResponseContext context) throws MitchellException {
		String methodName = "populateAppLogToEventPublishContext";
		logger.entering(CLASS_NAME, methodName);
		AppLoggingToEventPublishingContextDocument appLoggingToEventPublishingContextDoc = AppLoggingToEventPublishingContextDocument.Factory
				.newInstance();
		AppLoggingToEventPublishingContext appLoggingToEventPublishingContext = appLoggingToEventPublishingContextDoc
				.addNewAppLoggingToEventPublishingContext();
		if (context.getClaimNumber() == null) {
			appLoggingToEventPublishingContext.setClaimNumber("N/A");
		} else {
			appLoggingToEventPublishingContext.setClaimNumber(context
					.getClaimNumber());
		}
		EventDetails evDetails = appLoggingToEventPublishingContext
				.addNewEventDetails();
		Map<String, String> contextMap = context.getMap();
		if (contextMap != null) {
			Iterator<String> iterator = contextMap.keySet().iterator();
			String key = null;
			String value = null;
			NameValuePair nvPair;
			while (iterator.hasNext()) {
				nvPair = evDetails.addNewNameValuePair();

				key = iterator.next().toString();
				value = contextMap.get(key).toString();
				nvPair.setName(key);
				nvPair.addValue(value);
			}
		}
		logger.exiting(CLASS_NAME, methodName);

		return appLoggingToEventPublishingContext;
	}

}
