package com.mitchell.services.business.questionnaireevaluation.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.transactionalfile.client.TransactionalFileService;

public class TransactionalFileServiceProxyImpl implements
		TransactionalFileServiceProxy {

	public void postDeleteRequest(String appName, 
            String moduleName,
            String workItemId, 
            String targetLocation,
            boolean deleteSourceAfterCopy)throws MitchellException{	
		
							TransactionalFileService.postDeleteRequest(appName, 
									moduleName,
						            workItemId, 
						            targetLocation,
						            deleteSourceAfterCopy);
							
	}
	
	public void postCopyFileRequestCached(String appName, 
            String moduleName,
            String workItemId, 
            String sourceFilePath,
            String targetLocation,
            String arg)throws MitchellException{
		
							TransactionalFileService.postCopyFileRequestCached(appName, 
									moduleName,
						            workItemId,
						            sourceFilePath,
						            targetLocation,
						            arg);
	}
}