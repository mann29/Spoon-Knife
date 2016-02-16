/**
 * 
 */
package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.workprocess.WorkProcessServiceClient;
import com.mitchell.services.core.workprocessservice.WPKeyRequestDocument;

/**
 * @author jagdish.kumar
 * 
 */
public class WorkProcessServiceProxyImpl implements WorkProcessServiceProxy {
	/**
	 * class name.
	 */
	private static final String CLASS_NAME = UserInfoProxyImpl.class.getName();
	/**
	 * logger instance.
	 */
	private static Logger logger = Logger.getLogger(CLASS_NAME);

	/**
	 * This method calls initWPKeyRequest using WorkProcessServiceClient.
	 * 
	 * @param contextIdentifier
	 * @return
	 * @throws MitchellException
	 */
	public WPKeyRequestDocument initWPKeyRequest(String contextIdentifier)
			throws MitchellException {
		String methodName = "initWPKeyRequest";
		logger.entering(CLASS_NAME, methodName);
		
		WorkProcessServiceClient client = new WorkProcessServiceClient();
		WPKeyRequestDocument keyDoc = client
				.initWPKeyRequest(contextIdentifier);
		logger.exiting(CLASS_NAME, methodName);
		return keyDoc;
	}

	/**
	 * This method registers work process key using WorkProcessServiceClient.
	 * 
	 * @param coCode
	 * @param wpKeyReqDoc
	 * @param workProcessKey
	 * @throws MitchellException
	 */
	public void registerWorkProcessKey(String coCode,
			WPKeyRequestDocument wpKeyReqDoc, String workProcessKey)
			throws MitchellException {
		String methodName = "registerWorkProcessKey";
		logger.entering(CLASS_NAME, methodName);
		
		WorkProcessServiceClient client = new WorkProcessServiceClient();
		client.registerWorkProcessKey(coCode, wpKeyReqDoc, workProcessKey);
		logger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * This method gets work process key using WorkProcessServiceClient.
	 * 
	 * @param coCode
	 * @param wpKeyReqDoc
	 * @return
	 * @throws MitchellException
	 */
	public String retrieveWorkProcessKey(String coCode,
			WPKeyRequestDocument wpKeyReqDoc) throws MitchellException {
		String methodName = "retrieveWorkProcessKey";
		logger.entering(CLASS_NAME, methodName);
		
		WorkProcessServiceClient client = new WorkProcessServiceClient();
		String key = client.retrieveWorkProcessKey(coCode, wpKeyReqDoc);
		logger.exiting(CLASS_NAME, methodName);
		return key;
	}

}
