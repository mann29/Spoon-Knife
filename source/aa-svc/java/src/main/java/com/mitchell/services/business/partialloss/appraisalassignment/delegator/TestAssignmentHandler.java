package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentConfig;
import com.mitchell.services.business.partialloss.appraisalassignment.util.WorkAssignmentHandler;

public interface TestAssignmentHandler {
	public boolean isTestAssignmentUser(final UserInfoDocument userInfoDocument) throws MitchellException;
	public WorkAssignmentDocument updateTestWorkAssignmentDocument(final WorkAssignmentDocument workAssignmentDocument, final UserInfoDocument userInfoDocument) throws MitchellException;
	public void setAppraisalAssignmentConfig(IAppraisalAssignmentConfig appraisalAssignmentConfig);	
	public void setWorkAssignmentHandler(WorkAssignmentHandler workAssignmentHandler);
	public void init();
}