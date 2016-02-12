package com.mitchell.services.business.questionnaireevaluation.proxy;

import org.apache.log4j.Logger;

import com.cieca.bms.AssignmentAddRqDocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.technical.claim.client.ClaimServiceClient;
import com.mitchell.services.technical.claim.common.DTO.BmsClmInputDTO;
import com.mitchell.services.technical.claim.common.DTO.ClaimInfoDTO;
import com.mitchell.services.technical.claim.ejb.ClaimServiceRemote;
import com.mitchell.services.technical.claim.exception.ClaimException;
import com.mitchell.services.technical.claim.mcf.McfClmOutDTO;

public class ClaimServiceProxyImpl implements ClaimServiceProxy {

	private Logger logger = Logger.getLogger("ClaimServiceProxyImpl");	
	 private static final String CLASS_NAME = ClaimServiceProxyImpl.class.getName();

	public Long writeActivityLog(Long claimId,
								Long suffixId,
								String comment,
								UserInfoDocument userInfo)throws MitchellException{
		
		ClaimServiceRemote ejb = null;
    	ejb = ClaimServiceClient.getEjb();
    	Long actLogId = ejb.writeActivityLog(claimId,
    										suffixId,
    										comment,
    										userInfo);
    	
    	return actLogId;
		
	}
	
	public ClaimInfoDTO getSimpleClaimInfoByFullClaimNumber(
			final UserInfoDocument userInfoDoc, final String claimSuffixNumber,
			final String companyCode) throws MitchellException, ClaimException {
		ClaimInfoDTO claimInfoDTO = null;

		try {

			final ClaimServiceRemote claimServiceRemote = ClaimServiceClient
					.getEjb();
			claimInfoDTO = claimServiceRemote
					.getSimpleClaimInfoByFullClaimNumber(userInfoDoc,
							claimSuffixNumber, companyCode);
		}

		catch (ClaimException me) {
		/*	final String desc = "Exception from ClaimService:: " + me;
			logger.fatal("Got Exception:"
					+ AppUtilities.getStackTraceString(me));
			MitchellException mitchellException = new MitchellException(
					DCIHandlerConstants.DCIH_CALL_CLAIM_SERVICE_ERROR,
					CLASS_NAME, "getSimpleClaimInfoByFullClaimNumber", desc, me);
			throw mitchellException;*/
		}

		catch (MitchellException me) {
			/*final String desc = "Exception from ClaimService:: " + me;
			logger.fatal("Got Exception:"
					+ AppUtilities.getStackTraceString(me));
			MitchellException mitchellException = new MitchellException(
					DCIHandlerConstants.DCIH_CALL_CLAIM_SERVICE_ERROR,
					CLASS_NAME, "getSimpleClaimInfoByFullClaimNumber", desc, me);
			throw mitchellException;*/
		}

		catch (Exception me) {
			/*final String desc = "Exception from ClaimService:: " + me;
			logger.fatal("Got Exception:"
					+ AppUtilities.getStackTraceString(me));
			MitchellException mitchellException = new MitchellException(
					DCIHandlerConstants.DCIH_CALL_SERVICE_UNKNOWN_ERROR,
					CLASS_NAME, "getSimpleClaimInfoByFullClaimNumber", desc, me);
			throw mitchellException;*/
		}

		return claimInfoDTO;

	}

	public BmsClmInputDTO createInputDTO(
			AssignmentAddRqDocument assignAddRqDoc,
			UserInfoDocument adjusterUserInfo, String parsingRule,
			UserInfoDocument userInfoDocument) {

		return ClaimServiceClient.createInputDTO(assignAddRqDoc,
				adjusterUserInfo, parsingRule, userInfoDocument);
	}

	
	
	public McfClmOutDTO saveClaimFromAssignmentBms(BmsClmInputDTO inputDTO)
			throws MitchellException {
		ClaimServiceRemote claimServiceRemote;
		McfClmOutDTO mcfClmOutDTO = null;
		try {
			claimServiceRemote = ClaimServiceClient.getEjb();
			mcfClmOutDTO = claimServiceRemote
					.saveClaimFromAssignmentBms(inputDTO);
		} catch (MitchellException me) {
			/*final String desc = "Exception from ClaimService:: " + me;
			logger.fatal("Got Exception:"
					+ AppUtilities.getStackTraceString(me));
			MitchellException mitchellException = new MitchellException(
					DCIHandlerConstants.DCIH_CALL_CLAIM_SERVICE_ERROR,
					CLASS_NAME, "saveClaimFromAssignmentBms", desc, me);
			throw mitchellException;*/
		}

		catch (Exception me) {
			/*final String desc = "Exception from ClaimService:: " + me;
			logger.fatal("Got Exception:"
					+ AppUtilities.getStackTraceString(me));
			MitchellException mitchellException = new MitchellException(
					DCIHandlerConstants.DCIH_CALL_SERVICE_UNKNOWN_ERROR,
					CLASS_NAME, "saveClaimFromAssignmentBms", desc, me);
			throw mitchellException;*/
		}

		return mcfClmOutDTO;
	}

	
}
