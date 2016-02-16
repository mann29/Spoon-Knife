package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;

/**
 * @author jagdish.kumar
 * 
 */
public interface DocumentStoreProxy {

	/**
	 * Gets the GetDocResponse using DocumentStoreClient.
	 * 
	 * @param docStoreFileReference
	 * @return
	 * @throws MitchellException
	 */
	GetDocResponse getDocument(long docStoreFileReference)
			throws MitchellException;

}
