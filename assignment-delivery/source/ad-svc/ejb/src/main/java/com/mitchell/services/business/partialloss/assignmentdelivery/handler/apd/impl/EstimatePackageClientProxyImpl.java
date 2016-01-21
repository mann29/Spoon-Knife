package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.EstimatePackageClientProxy;
import com.mitchell.services.technical.partialloss.estimate.bo.Attachment;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;

public class EstimatePackageClientProxyImpl implements EstimatePackageClientProxy {

    public Estimate getEstimateAndDocByDocId(final long estimateDocId, final boolean includeSubObjects)
    throws MitchellException 
    {
    	//EstimatePackageRemote estimatePackageEJB = EstimatePackageClient.getEstimatePackageEJB();
    	EstimatePackageClient estClient = new EstimatePackageClient();
       	Estimate estimate =null;
		estimate = estClient.getEstimateAndDocByDocId(estimateDocId, includeSubObjects);
	   	return estimate;
    }
    
    public Attachment getAttachmentByDocIdEObject(final long estimateDocId, final int eObjectType)
    throws MitchellException 
    {
    	Attachment attachment =null;
    	EstimatePackageClient estClient = new EstimatePackageClient();
     // EstimatePackageRemote estimatePackageEJB = EstimatePackageClient.getEstimatePackageEJB();
		
			attachment = estClient.getAttachmentByDocIdEObject(estimateDocId, eObjectType);
			
		 	return attachment;
    }

    

    
}
