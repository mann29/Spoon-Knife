package com.mitchell.services.business.questionnaireevaluation.proxy;

import com.mitchell.common.exception.MitchellException;

public interface TransactionalFileServiceProxy {

	
	public void postDeleteRequest(String appName, 
            String moduleName,
            String workItemId, 
            String targetLocation,
            boolean deleteSourceAfterCopy)throws MitchellException;
	
	
	public void postCopyFileRequestCached(String appName, 
            String moduleName,
            String workItemId, 
            String sourceFilePath,
            String targetLocation,
            String arg)throws MitchellException;
	
	
}
