package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.technical.partialloss.estimate.bo.Attachment;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;

public interface EstimatePackageClientProxy {

      public abstract Estimate getEstimateAndDocByDocId(final long estimateDocId, 
    		  final boolean includeSubObjects)  throws MitchellException;

      public abstract Attachment getAttachmentByDocIdEObject(final long estimateDocId, final int eObjectType) 
                   throws MitchellException;

      
            
}
