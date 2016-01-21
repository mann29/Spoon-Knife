package com.mitchell.services.business.partialloss.appraisalassignment.util;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentFailureResponseContext;

/**
 * @author rk104152
 *
 */
public interface AssignmentAppLogHelper {
	
	 public void appLog(UserInfoDocument ui, AssignmentFailureResponseContext context) throws MitchellException;

}
