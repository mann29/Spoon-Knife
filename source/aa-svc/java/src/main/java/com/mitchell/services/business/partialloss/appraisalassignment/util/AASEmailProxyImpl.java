package com.mitchell.services.business.partialloss.appraisalassignment.util;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SuppRequestUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementReqBO;

/**
 * This class is responsible for AppraisalAssignment Service App/Event Logging.
 * 
 */
public class AASEmailProxyImpl implements AASEmailProxy
{

	/**
	 * Class Name.
	 */
	private static final String CLASS_NAME = AASEmailProxyImpl.class.getName();

	/**
	 * This method is to populate estimate info in supp req BO
	 * 
	 * @param suppBO
	 * @throws MitchellException
	 */
	public void populateEstimateInfo(SupplementReqBO suppBO)
			throws MitchellException {
		final String methodName = "populateEstimateInfo";
		try {
			SuppRequestUtils.populateEstimateInfo(suppBO);
		} catch (Exception e) {
			throw new MitchellException(CLASS_NAME, methodName,
					"Error in populating Estimate Info.");
		}

	}

}
