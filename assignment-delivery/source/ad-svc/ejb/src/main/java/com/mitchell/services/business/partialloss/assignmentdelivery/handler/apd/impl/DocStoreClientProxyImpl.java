package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.DocStoreResponseDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.DocStoreClientProxy;
import com.mitchell.services.core.documentstore.client.DocumentStoreClient;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;
import com.mitchell.services.core.documentstore.dto.PutDocResponse;

public class DocStoreClientProxyImpl implements DocStoreClientProxy {
	public PutDocResponse putDocument(
			String fullFilepath,
			String coCode,
			String applicationName,
			String moduleName,
			String mimeType,
			boolean duplicateAllowed ) throws MitchellException{
		return DocumentStoreClient.putDocument(fullFilepath, coCode, applicationName, moduleName, mimeType, duplicateAllowed);
	}

	public GetDocResponse getDocument(long mieDocStoreId) throws MitchellException {
		return DocumentStoreClient.getDocument(mieDocStoreId);
	}

	public DocStoreResponseDocument overwrite(long mieDocStoreId, String absolutePath,
			String orgCode) throws MitchellException {
		DocStoreResponseDocument overwrite = DocumentStoreClient.overwrite(mieDocStoreId, absolutePath, orgCode);
		return overwrite;
		
	}
}
