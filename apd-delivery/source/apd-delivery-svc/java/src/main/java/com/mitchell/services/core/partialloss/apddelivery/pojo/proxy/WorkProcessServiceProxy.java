/**
 * 
 */
package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.workprocess.WorkProcessServiceClient;
import com.mitchell.services.core.workprocessservice.WPKeyRequestDocument;

/**
 * @author jagdish.kumar
 * 
 */
public interface WorkProcessServiceProxy {

	public static final String CTX_PUBLIC_CLIENT_CLAIM_NUMBER = WorkProcessServiceClient.CTX_PUBLIC_CLIENT_CLAIM_NUMBER;

	public static final String CTX_PRIVATE_APPRAISAL_ASSIGNMENT = WorkProcessServiceClient.CTX_PRIVATE_APPRAISAL_ASSIGNMENT;

	public static final String CTX_PRIVATE_WORKITEMID = WorkProcessServiceClient.CTX_PRIVATE_WORKITEMID;

	public static final String CTX_PRIVATE_ESTIMATE_DOCID = WorkProcessServiceClient.CTX_PRIVATE_ESTIMATE_DOCID;

	public static final String CTX_PRIVATE_REPAIR_ASSIGNMENT = WorkProcessServiceClient.CTX_PRIVATE_REPAIR_ASSIGNMENT;

	public static final String CTX_PRIVATE_RC_NOTIFICATION_ID = WorkProcessServiceClient.CTX_PRIVATE_RC_NOTIFICATION_ID;

	/**
	 * This method calls initWPKeyRequest using WorkProcessServiceClient.
	 * 
	 * @param contextIdentifier
	 * @return
	 * @throws MitchellException
	 */
	WPKeyRequestDocument initWPKeyRequest(String contextIdentifier)
			throws MitchellException;

	/**
	 * This method registers work process key using WorkProcessServiceClient.
	 * 
	 * @param coCode
	 * @param wpKeyReqDoc
	 * @param workProcessKey
	 * @throws MitchellException
	 */
	void registerWorkProcessKey(String coCode,
			WPKeyRequestDocument wpKeyReqDoc, String workProcessKey)
			throws MitchellException;

	/**
	 * This method gets work process key using WorkProcessServiceClient.
	 * 
	 * @param coCode
	 * @param wpKeyReqDoc
	 * @return
	 * @throws MitchellException
	 */
	String retrieveWorkProcessKey(String coCode,
			WPKeyRequestDocument wpKeyReqDoc) throws MitchellException;

}
