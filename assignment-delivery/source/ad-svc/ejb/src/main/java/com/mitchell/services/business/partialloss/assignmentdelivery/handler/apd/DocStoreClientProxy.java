package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.DocStoreResponseDocument;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;
import com.mitchell.services.core.documentstore.dto.PutDocResponse;

public interface DocStoreClientProxy {

	public abstract PutDocResponse putDocument(String fullFilepath, String coCode, String applicationName,
			String moduleName, String mimeType, boolean duplicateAllowed) throws MitchellException;

	public abstract GetDocResponse getDocument(long mieDocStoreId) throws MitchellException;

	public abstract DocStoreResponseDocument overwrite(long mieDocStoreId, String absolutePath,
			String orgCode) throws MitchellException;
	
}
