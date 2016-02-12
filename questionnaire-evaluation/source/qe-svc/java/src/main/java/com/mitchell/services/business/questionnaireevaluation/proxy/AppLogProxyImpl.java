package com.mitchell.services.business.questionnaireevaluation.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applog.client.AppLogging;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;


/**
 * The Class AppLogProxyImpl.
 * 
 * @see AppLogProxy
 */
public final class AppLogProxyImpl implements AppLogProxy {

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
            final String workItemId,
            final String businessServiceName,
            final AppLoggingNVPairs appLogNvPairs) throws MitchellException {

        return AppLogging.logAppEvent(logDoc, userInfoDoc,
                workItemId, businessServiceName, appLogNvPairs);
    }
    
}
