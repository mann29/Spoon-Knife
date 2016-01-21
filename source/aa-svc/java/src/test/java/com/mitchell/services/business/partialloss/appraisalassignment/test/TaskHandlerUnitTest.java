package com.mitchell.services.business.partialloss.appraisalassignment.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.delegator.AssignmentTaskHandlerImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.AASUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.APDProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.AppLogProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.ClaimProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.ErrorLogProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.EstimatePackageProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.UserInfoProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.WorkAssignmentProxy;
import com.mitchell.services.technical.claim.common.DTO.ClaimInfoDTO;
import com.mitchell.services.technical.partialloss.estimate.bo.Document;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
 

public class TaskHandlerUnitTest extends MockObjectTestCase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		junit.textui.TestRunner.run(suite());
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite("TaskHandlerUnitTest");
		suite.addTest(new TestSuite(TaskHandlerUnitTest.class));
		
		return suite;
	}
	
	protected void setUp() {
		impl = (AssignmentTaskHandlerImpl)AssignmentTaskHandlerImpl.getHandler();
		apdProxy = mock(APDProxy.class);
		appLogProxy = mock(AppLogProxy.class);
		claimProxy = mock(ClaimProxy.class);
		errorLogProxy = mock(ErrorLogProxy.class);
		estimatePackageProxy = mock(EstimatePackageProxy.class);
		userInfoProxy = mock(UserInfoProxy.class);
		workAssignmentProxy = mock(WorkAssignmentProxy.class);
		aasUtils = mock(AASUtils.class);
		
		impl.setApdProxy((APDProxy)apdProxy.proxy());
		impl.setAppLogProxy((AppLogProxy)appLogProxy.proxy());
		impl.setClaimProxy((ClaimProxy)claimProxy.proxy());
		impl.setErrorLogProxy((ErrorLogProxy)errorLogProxy.proxy());
		impl.setEstimatePackageProxy((EstimatePackageProxy)estimatePackageProxy.proxy());
		impl.setUserInfoProxy((UserInfoProxy)userInfoProxy.proxy());
		impl.setWorkAssignmentProxy((WorkAssignmentProxy)workAssignmentProxy.proxy());
		impl.setAasUtils((AASUtils)aasUtils.proxy());
	}
	
	public void testCreateSupplementTask() {
		final UserInfoDocument reviewerUserInfo = createMockUserInfoDoc();
		
		try {
			//set expectation for Claimproxy
			setExpectation4ClaimProxy();
			
			// set expectations for EstimatePackageProxy
			final Estimate mockEstimate = createMockEstimate();
			estimatePackageProxy.expects(atLeastOnce()).method("findEstimateByClaimNumber").withAnyArguments().will(returnValue(new Estimate[]{mockEstimate}));
			final Document mockDocument = createMockDocument();
			estimatePackageProxy.expects(atLeastOnce()).method("getDocumentByEstimateId").withAnyArguments().will(returnValue(mockDocument));

			// set expectations for WorkAssignmentProxy
			workAssignmentProxy.expects(atLeastOnce()).method("getWorkAssignmentsByClaimIdExposureId").withAnyArguments().will(returnValue(null));
			workAssignmentProxy.expects(atLeastOnce()).method("save").withAnyArguments();
			
			// set expectations for ErrorLogProxy and AppLogProxy
			errorLogProxy.expects(atMostOnce()).method("logAndThrowError").withAnyArguments();
			appLogProxy.expects(atLeastOnce()).method("logAppEvent").withAnyArguments();
			
			// set expectations for UserInfoProxy
			userInfoProxy.expects(atLeastOnce()).method("getUserInfo").withAnyArguments().will(returnValue(reviewerUserInfo));
			userInfoProxy.expects(atLeastOnce()).method("isUserStaff").withAnyArguments().will(returnValue(true));
			
			// set expectations for AASUtils
			aasUtils.expects(atLeastOnce()).method("retrieveCarrierSettings").withAnyArguments().will(returnValue(new java.util.HashMap()));
			
			// execute the test
			impl.createSupplementTask(CLAIM_SUFFIX_NUMBER, reviewerUserInfo, WORKITEM_ID,"Notes", "USER1", "1234");
		} catch(MitchellException e) {
			e.printStackTrace();
			fail("Unexpected MitchellException in unit test");
		}
	}

	public void testcreateAssignSupplementTaskToNCRTUSer() {
		final UserInfoDocument reviewerUserInfo = createMockUserInfoDoc();
		
		try {
			//set expectation for Claimproxy
			setExpectation4ClaimProxy();
			
			// set expectations for EstimatePackageProxy
			final Estimate mockEstimate = createMockEstimate();
			estimatePackageProxy.expects(atLeastOnce()).method("findEstimateByClaimNumber").withAnyArguments().will(returnValue(new Estimate[]{mockEstimate}));
			final Document mockDocument = createMockDocument();
			estimatePackageProxy.expects(atLeastOnce()).method("getDocumentByEstimateId").withAnyArguments().will(returnValue(mockDocument));

			// set expectations for WorkAssignmentProxy
			workAssignmentProxy.expects(atLeastOnce()).method("getWorkAssignmentsByClaimIdExposureId").withAnyArguments().will(returnValue(null));
			workAssignmentProxy.expects(atLeastOnce()).method("save").withAnyArguments();
			
			// set expectations for ErrorLogProxy and AppLogProxy
			errorLogProxy.expects(atMostOnce()).method("logAndThrowError").withAnyArguments();
			appLogProxy.expects(atLeastOnce()).method("logAppEvent").withAnyArguments();
			
			// set expectations for UserInfoProxy
			userInfoProxy.expects(atLeastOnce()).method("getUserInfo").withAnyArguments().will(returnValue(reviewerUserInfo));
			userInfoProxy.expects(atLeastOnce()).method("isUserStaff").withAnyArguments().will(returnValue(true));
			
			// set expectations for AASUtils
			aasUtils.expects(atLeastOnce()).method("retrieveCarrierSettings").withAnyArguments().will(returnValue(new java.util.HashMap()));
			
			// execute the test
			impl.createAssignSupplementTaskToNCRTUSer(CLAIM_SUFFIX_NUMBER, reviewerUserInfo, WORKITEM_ID,"Notes", "USER1", "1234");
		} catch(MitchellException e) {
			e.printStackTrace();
			fail("Unexpected MitchellException in unit test");
		}
	}
	
	AssignmentTaskHandlerImpl impl;
	Mock apdProxy;
	Mock appLogProxy;
	Mock errorLogProxy;
	Mock claimProxy;
	Mock estimatePackageProxy;
	Mock userInfoProxy;
	Mock workAssignmentProxy;
	Mock aasUtils;
	
	// all mock info
	private static final Long CLAIM_ID = new Long(1);
	private static final Long EXPOSURE_ID = new Long(1);
	private static final String CLAIM_NUMBER = "TESTCLM";
	private static final String EXPOSURE_NUMBER = "TESTEXP";
	
	private static final String CLAIM_SUFFIX_NUMBER = "TESTCLM-TESTEXP";
	private static final String WORKITEM_ID = "TEST_WKITEM_ID";
	
	private ClaimInfoDTO createMockClaimInfoDTO() {
		final ClaimInfoDTO dto = new ClaimInfoDTO();
		
		dto.setClaimNumber(CLAIM_NUMBER);
		dto.setExposureNumber(EXPOSURE_NUMBER);
		dto.setClaimId(CLAIM_ID);
		dto.setClaimExposureId(EXPOSURE_ID);
		return dto;
	}
	
	private UserInfoDocument createMockUserInfoDoc() {
		UserInfoDocument uinfo = UserInfoDocument.Factory.newInstance();
		uinfo.addNewUserInfo();
		uinfo.getUserInfo().setOrgCode("TC");
		uinfo.getUserInfo().setUserID("TU");
		uinfo.getUserInfo().setFirstName("TF");
		uinfo.getUserInfo().setLastName("TL");
		
		return uinfo;
	}
	
	private Estimate createMockEstimate() {
		Estimate estimate = new Estimate();
		
		estimate.setId(new Long(1));
		return estimate;
	}
	
	private Document createMockDocument() {
		Document doc = new Document();
		doc.setId(new Long(1));
		doc.setServiceProviderId(new Long(1));
		
		return doc;
	}
	
	// register expectation on constraint and stub
	private void setExpectation4ClaimProxy() {
		final ClaimInfoDTO claimInfoDto = createMockClaimInfoDTO();
		claimProxy.expects(atLeastOnce()).method("getSimpleClaimInfoByFullClaimNumber").withAnyArguments().will(returnValue(claimInfoDto));
		claimProxy.expects(atLeastOnce()).method("writeExposureActLog").withAnyArguments();
	}
	
}
