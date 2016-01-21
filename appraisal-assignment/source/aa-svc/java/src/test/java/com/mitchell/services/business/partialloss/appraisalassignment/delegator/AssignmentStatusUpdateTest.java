package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.ItineraryViewDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConfig;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentConfig;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.EstimatePackageProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASAppLogUtil;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.MitchellEnvelopeHandler;
import com.mitchell.services.business.partialloss.appraisalassignment.util.UserInfoUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.WorkAssignmentHandler;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AppraisalAssignmentConfig.class})
public class AssignmentStatusUpdateTest {

	protected AppraisalAssignmentServiceHandlerImpl	aasServeHandler;

	protected IAppraisalAssignmentConfig				appraisalAssignmentConfig;
	protected AASCommonUtils						commonUtils;
	protected MitchellEnvelopeHandler				mitchellEnvelopeHandler	= null;
	WorkAssignmentHandler							workAssignmentHandler;
	UserInfoUtils									userInfoUtils;
	AASAppLogUtil									appLogUtil;

	long											workAssignmentTaskID;
	String											newDispositionCode;
	String											reasonCode;
	String											reasonComment;
	long											requestTCN;
	UserInfoDocument								loggedInUserInfoDocument;
	ItineraryViewDocument							itineraryViewDocument;

	@Before
	public void setUp() throws Exception {

		this.aasServeHandler = mock(AppraisalAssignmentServiceHandlerImpl.class);

		this.aasServeHandler.estimatePackageProxy = mock(EstimatePackageProxy.class);

		this.workAssignmentHandler = mock(WorkAssignmentHandler.class);
		this.aasServeHandler.workAssignmentHandler = this.workAssignmentHandler;

		this.userInfoUtils = mock(UserInfoUtils.class);
		this.aasServeHandler.userInfoUtils = this.userInfoUtils;

		this.appLogUtil = mock(AASAppLogUtil.class);
		this.aasServeHandler.appLogUtil = this.appLogUtil;

		this.aasServeHandler.estimatePackageProxy = mock(EstimatePackageProxy.class);
		this.aasServeHandler.workAssignmentHandler = mock(WorkAssignmentHandler.class);

		this.aasServeHandler.appraisalAssignmentConfig = PowerMockito.mock(AppraisalAssignmentConfig.class);

		this.aasServeHandler.commonUtils = mock(AASCommonUtils.class);
		this.aasServeHandler.mitchellEnvelopeHandler = mock(MitchellEnvelopeHandler.class);

		File iItineraryViewDocument = new File("src/test/resources/handler/It");
		itineraryViewDocument = ItineraryViewDocument.Factory.parse(iItineraryViewDocument);

		File userInfo = new File("src/test/resources/handler/user");
		loggedInUserInfoDocument = UserInfoDocument.Factory.parse(userInfo);

		workAssignmentTaskID = itineraryViewDocument.getItineraryView().getWorkAssignmentTaskID();
		newDispositionCode = itineraryViewDocument.getItineraryView().getDisposition();
		reasonCode = itineraryViewDocument.getItineraryView().getReasonCode();
		reasonComment = itineraryViewDocument.getItineraryView().getNotes();
		requestTCN = itineraryViewDocument.getItineraryView().getTCN();

	}

	@After
	public void tearDown() throws Exception {
		this.aasServeHandler = null;
	}

	@Test
	public void assignmentStatusUpdate_CLOSED_Assignment_Test() throws Exception {

		WorkAssignment workAssignment = getWorkAssignment();
		workAssignment.setWorkAssignmentStatus(AppraisalAssignmentConstants.WA_STATUS_CLOSED);

		when(this.aasServeHandler.workAssignmentHandler.getWorkAssignmentByTaskId(Mockito.anyLong(), (UserInfoDocument) anyObject())).thenReturn(
			workAssignment);

		when(
			aasServeHandler.assignmentStatusUpdate(workAssignmentTaskID, AppraisalAssignmentConstants.WA_STATUS_CLOSED, reasonCode, reasonComment,
				requestTCN, loggedInUserInfoDocument, itineraryViewDocument)).thenCallRealMethod();

		int statusCode = aasServeHandler.assignmentStatusUpdate(workAssignmentTaskID, AppraisalAssignmentConstants.WA_STATUS_CLOSED, reasonCode,
			reasonComment, requestTCN, loggedInUserInfoDocument, itineraryViewDocument);
		assertEquals(AppraisalAssignmentConstants.CANCELED_CLOSED_TASK, statusCode);

	}

	@Test
	@Ignore
	public void assignmentStatusUpdate_reschedule_Assignment_Test() throws Exception {

		WorkAssignment workAssignment = getWorkAssignment();
		workAssignment.setTcn(50L);

		when(this.aasServeHandler.workAssignmentHandler.getWorkAssignmentByTaskId(Mockito.anyLong(), (UserInfoDocument) anyObject())).thenReturn(
			workAssignment);

		PowerMockito.when(this.aasServeHandler, "rescheduleOrIncompleteAssignment", Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(),
			Mockito.anyString(), (UserInfoDocument) anyObject(), Mockito.anyString()).thenReturn(3);
		
		when(
			aasServeHandler.assignmentStatusUpdate(workAssignmentTaskID, AppraisalAssignmentConstants.DISPOSITION_RESCHEDULE, reasonCode,
				reasonComment, requestTCN, loggedInUserInfoDocument, itineraryViewDocument)).thenCallRealMethod();

		int statusCode = aasServeHandler.assignmentStatusUpdate(workAssignmentTaskID, AppraisalAssignmentConstants.DISPOSITION_RESCHEDULE,
			reasonCode, reasonComment, requestTCN, loggedInUserInfoDocument, itineraryViewDocument);

		assertEquals(AppraisalAssignmentConstants.CANCELED_CLOSED_TASK, statusCode);
	}

	@Test
	public void assignmentStatusUpdate_inprogress_Assignment_Test() throws Exception {

		when(this.aasServeHandler.workAssignmentHandler.getWorkAssignmentByTaskId(Mockito.anyLong(), (UserInfoDocument) anyObject())).thenReturn(
			getWorkAssignment());

		when(
			aasServeHandler.assignmentStatusUpdate(workAssignmentTaskID, AppraisalAssignmentConstants.DISPOSITION_IN_PROGRESS, reasonCode,
				reasonComment, requestTCN, loggedInUserInfoDocument, itineraryViewDocument)).thenCallRealMethod();

		when(this.aasServeHandler.userInfoUtils.retrieveUserInfo((String) anyObject(), (String) anyObject())).thenReturn(loggedInUserInfoDocument);

		when(
			this.aasServeHandler.workAssignmentHandler.saveWorkAssignmentStatus((WorkAssignment) anyObject(), (String) anyObject(),
				(String) anyObject(), (String) anyObject(), (UserInfoDocument) anyObject(), (ItineraryViewDocument) anyObject())).thenReturn(
			getWorkAssignment());

		int statusCode = aasServeHandler.assignmentStatusUpdate(workAssignmentTaskID, AppraisalAssignmentConstants.DISPOSITION_IN_PROGRESS,
			reasonCode, reasonComment, requestTCN, loggedInUserInfoDocument, itineraryViewDocument);

		assertEquals(AppraisalAssignmentConstants.SUCCESS, statusCode);
	}
	
	@Test
	public void assignmentStatusUpdate_rejected_Assignment_Test() throws Exception {

		when(this.aasServeHandler.workAssignmentHandler.getWorkAssignmentByTaskId(Mockito.anyLong(), (UserInfoDocument) anyObject())).thenReturn(
			getWorkAssignment());

		when(
			aasServeHandler.assignmentStatusUpdate(workAssignmentTaskID, AppraisalAssignmentConstants.DISPOSITION_REJECTED, reasonCode,
				reasonComment, requestTCN, loggedInUserInfoDocument, itineraryViewDocument)).thenCallRealMethod();

		when(this.aasServeHandler.userInfoUtils.retrieveUserInfo((String) anyObject(), (String) anyObject())).thenReturn(loggedInUserInfoDocument);

		when(
			this.aasServeHandler.workAssignmentHandler.saveWorkAssignmentStatus((WorkAssignment) anyObject(), (String) anyObject(),
				(String) anyObject(), (String) anyObject(), (UserInfoDocument) anyObject(), (ItineraryViewDocument) anyObject())).thenReturn(
			getWorkAssignment());

		int statusCode = aasServeHandler.assignmentStatusUpdate(workAssignmentTaskID, AppraisalAssignmentConstants.DISPOSITION_REJECTED,
			reasonCode, reasonComment, requestTCN, loggedInUserInfoDocument, itineraryViewDocument);

		assertEquals(AppraisalAssignmentConstants.SUCCESS, statusCode);
	}
	
	@Test
	public void assignmentStatusUpdate_cancelled_Assignment_Test() throws Exception {

		when(this.aasServeHandler.workAssignmentHandler.getWorkAssignmentByTaskId(Mockito.anyLong(), (UserInfoDocument) anyObject())).thenReturn(
			getWorkAssignment());

		when(
			aasServeHandler.assignmentStatusUpdate(workAssignmentTaskID, AppraisalAssignmentConstants.DISPOSITION_CANCELLED, reasonCode,
				reasonComment, requestTCN, loggedInUserInfoDocument, itineraryViewDocument)).thenCallRealMethod();

		when(this.aasServeHandler.userInfoUtils.retrieveUserInfo((String) anyObject(), (String) anyObject())).thenReturn(loggedInUserInfoDocument);

		when(
			this.aasServeHandler.workAssignmentHandler.saveWorkAssignmentStatus((WorkAssignment) anyObject(), (String) anyObject(),
				(String) anyObject(), (String) anyObject(), (UserInfoDocument) anyObject(), (ItineraryViewDocument) anyObject())).thenReturn(
			getWorkAssignment());

		int statusCode = aasServeHandler.assignmentStatusUpdate(workAssignmentTaskID, AppraisalAssignmentConstants.DISPOSITION_CANCELLED,
			reasonCode, reasonComment, requestTCN, loggedInUserInfoDocument, itineraryViewDocument);

		assertEquals(AppraisalAssignmentConstants.SUCCESS, statusCode);
	}
	
	@Test
	public void assignmentStatusUpdate_COMPLETED_Assignment_Test() throws Exception {

		when(this.aasServeHandler.workAssignmentHandler.getWorkAssignmentByTaskId(Mockito.anyLong(), (UserInfoDocument) anyObject())).thenReturn(
			getWorkAssignment());

		when(
			aasServeHandler.assignmentStatusUpdate(workAssignmentTaskID, AppraisalAssignmentConstants.DISPOSITION_COMPLETED, reasonCode,
				reasonComment, requestTCN, loggedInUserInfoDocument, itineraryViewDocument)).thenCallRealMethod();

		when(this.aasServeHandler.userInfoUtils.retrieveUserInfo((String) anyObject(), (String) anyObject())).thenReturn(loggedInUserInfoDocument);

		when(
			this.aasServeHandler.workAssignmentHandler.saveWorkAssignmentStatus((WorkAssignment) anyObject(), (String) anyObject(),
				(String) anyObject(), (String) anyObject(), (UserInfoDocument) anyObject(), (ItineraryViewDocument) anyObject())).thenReturn(
			getWorkAssignment());

		int statusCode = aasServeHandler.assignmentStatusUpdate(workAssignmentTaskID, AppraisalAssignmentConstants.DISPOSITION_COMPLETED,
			reasonCode, reasonComment, requestTCN, loggedInUserInfoDocument, itineraryViewDocument);

		assertEquals(AppraisalAssignmentConstants.SUCCESS, statusCode);
	}

	private WorkAssignment getWorkAssignment() {
		WorkAssignment workAssignment = new WorkAssignment();
		workAssignment.setWorkAssignmentStatus("");
		workAssignment.setId(workAssignmentTaskID);
		return workAssignment;
	}

}
