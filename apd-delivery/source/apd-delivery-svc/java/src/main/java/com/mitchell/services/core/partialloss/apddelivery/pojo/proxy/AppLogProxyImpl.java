package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applog.client.AppLogging;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;

import java.util.logging.Logger;

/**
 * The Class AppLogProxyImpl.
 * 
 * @see AppLogProxy
 */
public class AppLogProxyImpl implements AppLogProxy {
    
    /**
     * class name.
     */
    private static final String CLASS_NAME = 
                                        AppLogProxyImpl.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);

    /**
     * Log app event.
     *
     * @param logDoc the log doc
     * @param userInfoDoc the user info doc
     * @param workItemId the work item id
     * @param appName the app name
     * @param businessServiceName the business service name
     * @param appLogNvPairs the app log nv pairs
     * @return MitchellWorkflowMessageDocument
     * the mitchell workflow message document
     * @throws MitchellException 
     * Mitchell Exception
     */
    public MitchellWorkflowMessageDocument logAppEvent(
            final AppLoggingDocument logDoc, final UserInfoDocument userInfoDoc,
            final String workItemId, final String appName,
            final String businessServiceName,
            final AppLoggingNVPairs appLogNvPairs) throws MitchellException {
    	String methodName = "logAppEvent";
    	logger.entering(CLASS_NAME, methodName);
    	MitchellWorkflowMessageDocument mwmDoc = AppLogging.logAppEvent(logDoc, userInfoDoc,
                workItemId, appName, businessServiceName, appLogNvPairs);
    	logger.exiting(CLASS_NAME, methodName);
        return mwmDoc;
    }
    
    /**
	 * Log app event.
	 * @param logDoc
	 * @param userInfoDoc
	 * @param workItemId
	 * @param businessServiceName
	 * @param appLogNvPairs
	 * @return
	 * @throws MitchellException
	 */
    public MitchellWorkflowMessageDocument logAppEvent(AppLoggingDocument logDoc,
			UserInfoDocument userInfoDoc, String workItemId,
			String businessServiceName, AppLoggingNVPairs appLogNvPairs)
			throws MitchellException {
    	String methodName = "logAppEvent";
    	logger.entering(CLASS_NAME, methodName);
    	MitchellWorkflowMessageDocument mwmDoc = AppLogging.logAppEvent(
                logDoc, 
                userInfoDoc, 
                workItemId, 
                businessServiceName, 
                appLogNvPairs);
    	logger.exiting(CLASS_NAME, methodName);
        return mwmDoc;
    }
    
}
