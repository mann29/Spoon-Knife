package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applog.client.AppLogging;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;

public class AppLogProxyImpl implements AppLogProxy {

    // @Override
    public MitchellWorkflowMessageDocument logAppEvent(final AppLoggingDocument logDoc,
            final UserInfoDocument userInfoDoc, final String workItemId, final String appName,
            final String businessServiceName, final AppLoggingNVPairs appLogNvPairs) throws MitchellException {

        return AppLogging.logAppEvent(logDoc, userInfoDoc, workItemId, appName, businessServiceName, appLogNvPairs);
    }

}
