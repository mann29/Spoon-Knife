package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.mitchell.common.exception.MitchellException;

@RunWith(MockitoJUnitRunner.class)
public class RetriveSupReqDelegatorSpy extends RetriveSupReqDelegator{

	
	public RetriveSupReqDelegatorSpy(long estimateDocId, long estimatorOrgId,
			long reviewerOrgId) throws MitchellException {
		super(estimateDocId, estimatorOrgId, reviewerOrgId);
	}

	public void injectDependencies(){
		//do nothing
	}

}
