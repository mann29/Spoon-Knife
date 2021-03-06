package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;

/**
 * The Interface AppLogProxy.
 */
public interface AppLogProxy {

    /**
     * Log app event.
     *
     * @param logDoc the log doc
     * @param userInfoDoc the user info doc
     * @param workItemId the work item id
     * @param appName the app name
     * @param businessServiceName the business service name
     * @param appLogNvPairs the app log nv pairs
     * @return the mitchell workflow message document
     * @throws MitchellException the mitchell exception
     */
    MitchellWorkflowMessageDocument logAppEvent(
            AppLoggingDocument logDoc, UserInfoDocument userInfoDoc,
            String workItemId, String appName, String businessServiceName,
            AppLoggingNVPairs appLogNvPairs) throws MitchellException;
    
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
	MitchellWorkflowMessageDocument logAppEvent(AppLoggingDocument logDoc,
			UserInfoDocument userInfoDoc, String workItemId,
			String businessServiceName, AppLoggingNVPairs appLogNvPairs)
			throws MitchellException;


            
}
