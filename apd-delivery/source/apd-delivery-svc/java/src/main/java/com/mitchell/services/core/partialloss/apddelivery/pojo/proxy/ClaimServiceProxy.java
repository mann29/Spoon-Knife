/**
 * 
 */
package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import java.util.Set;

import com.cieca.bms.AssignmentAddRqDocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.technical.claim.constant.InflatableObjectCodes;
import com.mitchell.services.technical.claim.dao.vo.ClaimExposure;
import com.mitchell.services.technical.claim.exception.ClaimException;

/**
 * @author jagdish.kumar
 *
 */
public interface ClaimServiceProxy {
	
	/**
	 * This method returns ClaimExposure using ClaimServiceClient.  
	 * @param userInfoDoc
	 * @param claimId
	 * @param suffixId
	 * @param set
	 * @return
	 * @throws MitchellException
	 */
	ClaimExposure readExposureCustomGraph(UserInfoDocument userInfoDoc, Long claimId, Long suffixId, Set<InflatableObjectCodes> set) throws ClaimException, MitchellException;

	/**
	 * This method returns AssignmentAddRqDocument using ClaimServiceClient.
	 * @param userInfo
	 * @param clientClaimNumber
	 * @return
	 * @throws ClaimException
	 * @throws MitchellException
	 */
	AssignmentAddRqDocument readClaimInfoIntoBms(UserInfoDocument userInfo, String clientClaimNumber) throws ClaimException, MitchellException;

}
