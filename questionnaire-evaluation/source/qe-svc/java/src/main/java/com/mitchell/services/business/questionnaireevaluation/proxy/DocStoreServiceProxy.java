package com.mitchell.services.business.questionnaireevaluation.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;

public interface DocStoreServiceProxy {

	
	public GetDocResponse getDocument(long docStoreId)throws MitchellException;
}
