package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import java.util.HashMap;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentConfig;
import com.mitchell.services.business.partialloss.appraisalassignment.util.WorkAssignmentHandler;

public class TestAssignmentHandlerImpl implements TestAssignmentHandler {

	private HashMap companyAndDispatcherLookUp = null;
	private static final String CLASS_NAME = TestAssignmentHandlerImpl.class.getName();
	private static final Logger logger = Logger.getLogger(CLASS_NAME);
	private WorkAssignmentHandler workAssignmentHandler = null;
	private IAppraisalAssignmentConfig appraisalAssignmentConfig = null;
	
	public void setAppraisalAssignmentConfig(IAppraisalAssignmentConfig appraisalAssignmentConfig) {
		this.appraisalAssignmentConfig = appraisalAssignmentConfig;
	}
	
	public void setWorkAssignmentHandler(WorkAssignmentHandler workAssignmentHandler) {
		this.workAssignmentHandler = workAssignmentHandler;
	}
	
	
	public void init() {
		try {
			if(companyAndDispatcherLookUp == null || (companyAndDispatcherLookUp !=null && companyAndDispatcherLookUp.isEmpty())) {
				//1. Initialize map to catch test company and userId
				String testAssignmentDispatcherList = appraisalAssignmentConfig.getTestAssignmentDispatcherList();
				companyAndDispatcherLookUp = new HashMap(); 
				
				final String[] allCompanyAndDispatchers = testAssignmentDispatcherList.split(",");
	
				for(int i=0; i<allCompanyAndDispatchers.length ; i++ ) {
					final String[] companyAndDispatcher = allCompanyAndDispatchers[i].split(":");
					for (int j=0 ; j < companyAndDispatcher.length ; j = j + 2) {
						companyAndDispatcherLookUp.put(companyAndDispatcher[j], companyAndDispatcher[j+1]);
					}
				}
			  }
			
		} catch (MitchellException mitchellException) {
			logger.warning("Error initializing the TestAssignmentHandlerImpl.companyAndDispatcherLookUp!!");
		}
	}
	
	public boolean isTestAssignmentUser(final UserInfoDocument userInfoDocument) throws MitchellException {
		final String COMPANY_CODE = userInfoDocument.getUserInfo().getOrgCode();
		final String DISPATCHER_ID = userInfoDocument.getUserInfo().getUserID();
		boolean isTestAssignmentDispatcher = false;
		String testUserIdForCompany = null;
		if(companyAndDispatcherLookUp != null && !companyAndDispatcherLookUp.isEmpty()) {			
			testUserIdForCompany = (String) companyAndDispatcherLookUp.get(COMPANY_CODE);
			if(DISPATCHER_ID.equals(testUserIdForCompany)) {
				isTestAssignmentDispatcher = true;
			} 
		}
		return isTestAssignmentDispatcher;
	}

	public WorkAssignmentDocument updateTestWorkAssignmentDocument(
			final WorkAssignmentDocument workAssignmentDocument,
			final UserInfoDocument userInfoDocument) {
			
			// 1. Set disposition to READY
			WorkAssignmentDocument updatedWorkAssignmentDocument = workAssignmentHandler.setupWorkAssignmentRequest(workAssignmentDocument,
				AppraisalAssignmentConstants.DISPOSITION_READY, "update", userInfoDocument);
			
			// 2. Unset Subscribed Event
			WorkAssignmentType workAssignmentType = updatedWorkAssignmentDocument.getWorkAssignment();
			if(workAssignmentType != null && workAssignmentType.isSetSubscribedEvents())
				workAssignmentType.unsetSubscribedEvents();
			return updatedWorkAssignmentDocument;
	}

}
