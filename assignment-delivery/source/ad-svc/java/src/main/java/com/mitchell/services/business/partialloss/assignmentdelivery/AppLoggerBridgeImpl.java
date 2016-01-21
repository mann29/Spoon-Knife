package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applog.client.AppLogging;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;

public class AppLoggerBridgeImpl implements AppLoggerBridge {

	public MitchellWorkflowMessageDocument logEvent(
			AppLoggingDocument appLoggingDocument,
			UserInfoDocument userInfoDocument, String workItemId,
			String appName, String businessServiceName,
			AppLoggingNVPairs nameValuePairs) throws MitchellException {
		return AppLogging.logAppEvent(appLoggingDocument, userInfoDocument,
				workItemId, appName, businessServiceName, nameValuePairs);
	}

}
