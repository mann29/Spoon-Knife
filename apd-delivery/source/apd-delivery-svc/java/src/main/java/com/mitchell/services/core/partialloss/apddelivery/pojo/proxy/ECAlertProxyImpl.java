package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.services.business.partialloss.eclaimalertsvc.ECAlertSvcClient;
import com.mitchell.services.business.partialloss.eclaimalertsvc.ECAlertSvcException;
import com.mitchell.services.core.eclaimalert.EClaimAlertRequestDocument;
import com.mitchell.services.schemas.mcf.MCFPackageTyp;

/**
 * The Class ECAlertProxyImpl.
 * 
 * @see ECAlertProxy
 */
public class ECAlertProxyImpl implements ECAlertProxy {

	/**
	 * class name.
	 */
	private static final String CLASS_NAME = ECAlertProxyImpl.class.getName();
	/**
	 * logger instance.
	 */
	private static Logger logger = Logger.getLogger(CLASS_NAME);

	/**
	 * This method delivers Global Alerts to eClaim.
	 * 
	 * @param userInfo
	 *            UserInfoType object
	 * @param workItemId
	 *            workItemId
	 * @param origin
	 *            origin
	 * @param message
	 *            message
	 * @throws MitchellException
	 *             Mitchell Exception
	 */
	public void deliverGlobalAlert(UserInfoType userInfo, String workItemId,
			String origin, String message) throws MitchellException {
		String methodName = "deliverGlobalAlert";
		logger.entering(CLASS_NAME, methodName);
		
		String coCode = userInfo.getOrgCode();
		String userId = userInfo.getUserID();
		ECAlertSvcClient.sendGlobalAlert(coCode, userId, origin, message, workItemId);
		
		logger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * This method send Upload Accepted Alert using ECAlertSvcClient.
	 * 
	 * @param doc
	 * @throws MitchellException
	 */
	public void sendUploadAcceptedAlert(EClaimAlertRequestDocument doc)
			throws ECAlertSvcException, MitchellException {
		String methodName = "sendUploadAcceptedAlert";
		logger.entering(CLASS_NAME, methodName);
		ECAlertSvcClient.sendUploadAcceptedAlert(doc);
		
		logger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * This method send Upload Rejected Alert using ECAlertSvcClient.
	 * 
	 * @param doc
	 * @throws MitchellException
	 */
	public void sendUploadRejectedAlert(EClaimAlertRequestDocument doc)
			throws ECAlertSvcException, MitchellException {
		String methodName = "sendUploadRejectedAlert";
		logger.entering(CLASS_NAME, methodName);
		ECAlertSvcClient.sendUploadRejectedAlert(doc);
		logger.exiting(CLASS_NAME, methodName);

	}

	/**
	 * This method creates Alert Request using ECAlertSvcClient.
	 * 
	 * @param coCode
	 * @param userId
	 * @param origin
	 * @param message
	 * @param workItemId
	 * @param eClaimEstId
	 * @param supplementNumber
	 * @param correctionNumber
	 * @param FolderAI
	 * @param mcfFileName
	 * @param mcfPackagetype
	 * @return
	 */
	public EClaimAlertRequestDocument createAlertRequest(String coCode,
			String userId, String origin, String message, String workItemId,
			String eClaimEstId, int supplementNumber, int correctionNumber,
			String FolderAI, String mcfFileName,
			MCFPackageTyp.Enum mcfPackagetype) {
		String methodName = "createAlertRequest";
		logger.entering(CLASS_NAME, methodName);
		
		EClaimAlertRequestDocument doc = ECAlertSvcClient.createAlertRequest(
				coCode, userId, origin, message, workItemId, eClaimEstId,
				supplementNumber, correctionNumber, FolderAI, mcfFileName,
				mcfPackagetype);
		logger.exiting(CLASS_NAME, methodName);
		return doc;
	}
}
