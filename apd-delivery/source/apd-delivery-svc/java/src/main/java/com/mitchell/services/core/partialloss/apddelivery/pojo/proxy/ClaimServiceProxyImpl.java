/**
 * 
 */
package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import java.util.Set;
import java.util.logging.Logger;

import com.cieca.bms.AssignmentAddRqDocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.technical.claim.client.ClaimServiceClient;
import com.mitchell.services.technical.claim.constant.InflatableObjectCodes;
import com.mitchell.services.technical.claim.dao.vo.ClaimExposure;
import com.mitchell.services.technical.claim.exception.ClaimException;

/**
 * @author jagdish.kumar
 *
 */
public class ClaimServiceProxyImpl implements ClaimServiceProxy {
	/**
     * class name.
     */
    private static final String CLASS_NAME = ClaimServiceProxyImpl.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);

	/**
	 * This method returns ClaimExposure using ClaimServiceClient.  
	 * @param userInfoDoc
	 * @param claimId
	 * @param suffixId
	 * @param set
	 * @return claimExposure
	 * @throws MitchellException
	 */
	public ClaimExposure readExposureCustomGraph(UserInfoDocument userInfoDoc,
			Long claimId, Long suffixId, Set<InflatableObjectCodes> set)
			throws ClaimException, MitchellException {
		String methodName = "readExposureCustomGraph";
		logger.entering(CLASS_NAME, methodName);
		
		ClaimExposure claimExposure = ClaimServiceClient.getEjb().readExposureCustomGraph(
				userInfoDoc, 
                claimId,
                suffixId,
                set);
		logger.exiting(CLASS_NAME, methodName);
		return claimExposure;
	}

	/**
	 * This method returns AssignmentAddRqDocument using ClaimServiceClient.
	 * @param userInfo
	 * @param clientClaimNumber
	 * @return doc
	 * @throws ClaimException
	 * @throws MitchellException
	 */
	public AssignmentAddRqDocument readClaimInfoIntoBms(UserInfoDocument userInfo,
			String clientClaimNumber) throws ClaimException, MitchellException {
		String methodName = "readClaimInfoIntoBms";
		logger.entering(CLASS_NAME, methodName);
		
		// TODO : confirm if casting is to be done here.
		AssignmentAddRqDocument doc = (AssignmentAddRqDocument)ClaimServiceClient.getEjb().readClaimInfoIntoBms(
                userInfo, 
                clientClaimNumber);
		logger.exiting(CLASS_NAME, methodName);
		return doc;
	}

}
