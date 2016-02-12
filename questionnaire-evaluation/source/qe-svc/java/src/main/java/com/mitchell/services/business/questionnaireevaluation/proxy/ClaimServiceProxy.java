package com.mitchell.services.business.questionnaireevaluation.proxy;

import com.cieca.bms.AssignmentAddRqDocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.technical.claim.common.DTO.BmsClmInputDTO;
import com.mitchell.services.technical.claim.common.DTO.ClaimInfoDTO;
import com.mitchell.services.technical.claim.exception.ClaimException;
import com.mitchell.services.technical.claim.mcf.McfClmOutDTO;

public interface ClaimServiceProxy {
	
	public Long writeActivityLog(Long claimId,
							Long suffixId,
							String comment,
							UserInfoDocument userInfo)throws MitchellException;


	public McfClmOutDTO saveClaimFromAssignmentBms(BmsClmInputDTO inputDTO)
			throws MitchellException;

	public BmsClmInputDTO createInputDTO(
			AssignmentAddRqDocument assignAddRqDoc,
			UserInfoDocument adjusterUserInfo, String parsingRule,
			UserInfoDocument userInfoDocument);
	
	public ClaimInfoDTO getSimpleClaimInfoByFullClaimNumber(
			final UserInfoDocument userInfoDoc, final String claimSuffixNumber,
			final String companyCode) throws MitchellException, ClaimException;
}
