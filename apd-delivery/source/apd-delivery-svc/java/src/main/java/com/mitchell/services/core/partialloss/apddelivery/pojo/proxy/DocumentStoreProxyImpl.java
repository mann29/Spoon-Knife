/**
 * 
 */
package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.documentstore.client.DocumentStoreClient;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;

/**
 * @author jagdish.kumar
 * 
 */
public class DocumentStoreProxyImpl implements DocumentStoreProxy {

	/**
	 * class name.
	 */
	private static final String CLASS_NAME = DocumentStoreProxyImpl.class
			.getName();
	/**
	 * logger instance.
	 */
	private static Logger logger = Logger.getLogger(CLASS_NAME);
	/**
	 * Gets the GetDocResponse using DocumentStoreClient.
	 * 
	 * @param docStoreFileReference
	 * @return
	 * @throws MitchellException
	 */
	public GetDocResponse getDocument(long docStoreFileReference)
			throws MitchellException {
		String methodName = "getDocument";
		logger.entering(CLASS_NAME, methodName);
		
		GetDocResponse docResponse = null;
		docResponse = DocumentStoreClient.getDocument(docStoreFileReference);
		logger.exiting(CLASS_NAME, methodName);
		return docResponse;
	}

}
