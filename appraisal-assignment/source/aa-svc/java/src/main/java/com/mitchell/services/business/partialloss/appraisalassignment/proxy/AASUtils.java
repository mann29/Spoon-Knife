package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import java.util.Map;

import com.mitchell.common.exception.MitchellException;

public interface AASUtils {
    public Map retrieveCarrierSettings(String coCode) throws MitchellException;
}
