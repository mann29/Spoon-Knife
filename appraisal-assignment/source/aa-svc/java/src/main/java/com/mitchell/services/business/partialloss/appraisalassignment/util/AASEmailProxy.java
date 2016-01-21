package com.mitchell.services.business.partialloss.appraisalassignment.util;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementReqBO;

public interface AASEmailProxy
{

    /**
     * This method is to populate estimate info in supp req BO
     * @param suppBO
     * @throws MitchellException
     */
	public void populateEstimateInfo(SupplementReqBO suppBO) throws MitchellException;
	
	
}
