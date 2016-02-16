package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.services.business.partialloss.eclaimalertsvc.ECAlertSvcException;
import com.mitchell.services.core.eclaimalert.EClaimAlertRequestDocument;
import com.mitchell.services.schemas.mcf.MCFPackageTyp;

/**
 * The Interface ECAlertProxy.
 */
public interface ECAlertProxy {

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
	void deliverGlobalAlert(UserInfoType userInfo, String workItemId,
			String origin, String message) throws MitchellException;

	/**
	 * This method send Upload Accepted Alert using ECAlertSvcClient.
	 * 
	 * @param doc
	 * @throws MitchellException
	 */
	void sendUploadAcceptedAlert(EClaimAlertRequestDocument doc)
			throws ECAlertSvcException, MitchellException;

	/**
	 * This method send Upload Rejected Alert using ECAlertSvcClient.
	 * 
	 * @param doc
	 * @throws MitchellException
	 */
	void sendUploadRejectedAlert(EClaimAlertRequestDocument doc)
			throws ECAlertSvcException, MitchellException;

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
	EClaimAlertRequestDocument createAlertRequest(String coCode, String userId,
			String origin, String message, String workItemId,
			String eClaimEstId, int supplementNumber, int correctionNumber,
			String FolderAI, String mcfFileName,
			MCFPackageTyp.Enum mcfPackagetype);
}
