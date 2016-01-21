package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import com.mitchell.common.exception.MitchellException;

/**
 * @author rk104152
 *
 */
public interface AssignmentFailureResponseHandler {
	public void processMessage(String msg) throws MitchellException;

}
