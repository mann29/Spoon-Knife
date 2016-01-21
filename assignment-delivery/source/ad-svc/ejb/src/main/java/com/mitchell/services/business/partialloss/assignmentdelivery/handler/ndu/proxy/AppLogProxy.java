package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;

public interface AppLogProxy {
	MitchellWorkflowMessageDocument logAppEvent(
			AppLoggingDocument logDoc, UserInfoDocument userInfoDoc,
			String workItemId, String appName, String businessServiceName,
			AppLoggingNVPairs appLogNvPairs) throws MitchellException;
}
