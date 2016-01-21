package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoType;
import com.mitchell.schemas.appraisalassignment.MOIDetailsType;
import com.mitchell.schemas.appraisalassignment.MOIType;
import com.mitchell.schemas.workassignment.PrimaryIDsType;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentType;
import com.mitchell.services.business.partialloss.appraisalassignment.AASTest.helper.AASTestHelper;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.MessageBusProxy;
import com.mitchell.services.core.messagebus.WorkflowMsgUtil;

@RunWith(MockitoJUnitRunner.class)
public class MessageBusHandlerImplTest {

	protected MessageBusHandlerImpl testClass;

	static final String ME_FILE = AASTestHelper.getResourcePath("ME.XML");

	@Mock
	MessageBusProxy messageBusProxy;
	
	@Mock
	WorkflowMsgUtil workflowMsgUtil;
	
	@Before
	public void setUp() throws Exception {
		this.testClass = mock(MessageBusHandlerImpl.class);
	}

	@After
	public void tearDown() throws Exception {
		this.testClass = null;
	}

	@Test
	public void publishEventToMessageBusTest_MoiTypeNull() throws Exception {
		doCallRealMethod().when(this.testClass).setMessageBusProxy((MessageBusProxy)messageBusProxy);
		this.testClass.setMessageBusProxy(messageBusProxy);
		doCallRealMethod().when(this.testClass).publishEventToMessageBus(Mockito.anyInt(), (WorkAssignmentDocument) anyObject(),(UserInfoDocument) anyObject(),(AdditionalAppraisalAssignmentInfoDocument)anyObject());
		this.testClass.publishEventToMessageBus(108301, craeteWorkAssignemnt(),createUserInfo(),null);
	}
	
	@Test
	public void publishEventToMessageBusTest_MoiTypeNotNull() throws Exception {
		doCallRealMethod().when(this.testClass).setMessageBusProxy((MessageBusProxy)messageBusProxy);
		this.testClass.setMessageBusProxy(messageBusProxy);
		AdditionalAppraisalAssignmentInfoDocument aditionalAprAsgInfoDoc = createAdditionalAppraisalAssignmentInfoDoc();
		doCallRealMethod().when(this.testClass).publishEventToMessageBus(Mockito.anyInt(), (WorkAssignmentDocument) anyObject(),(UserInfoDocument) anyObject(),(AdditionalAppraisalAssignmentInfoDocument)anyObject());
		this.testClass.publishEventToMessageBus(108301, craeteWorkAssignemnt(),createUserInfo(),aditionalAprAsgInfoDoc);
	}
	

	private AdditionalAppraisalAssignmentInfoDocument createAdditionalAppraisalAssignmentInfoDoc() {
		AdditionalAppraisalAssignmentInfoDocument additionalAprAsgInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory.newInstance();
		AdditionalAppraisalAssignmentInfoType asgInfoType = additionalAprAsgInfoDoc.addNewAdditionalAppraisalAssignmentInfo();
		MOIDetailsType moiDetailsType = asgInfoType.addNewMOIDetails();
		MOIType moiType = moiDetailsType.addNewTempUserSelectedMOI();
		moiType.setMethodOfInspection("SCDO");
		moiType.setResourceSelectionDeferredFlag(true);
		moiType.setMOIOrgID(349822);
		return additionalAprAsgInfoDoc;
	}

	private UserInfoDocument createUserInfo() {
		UserInfoDocument userInfo = UserInfoDocument.Factory.newInstance();
		userInfo.addNewUserInfo();
		userInfo.getUserInfo().setOrgCode("I9");
		userInfo.getUserInfo().setUserID("Test");
		userInfo.getUserInfo().setEmail("test@mitchell.com");
		userInfo.getUserInfo().addNewUserHier();
		userInfo.getUserInfo().getUserHier().addNewHierNode();
		userInfo.getUserInfo().getUserHier().getHierNode().setName("Desjardins");
		return userInfo;
	}

	private WorkAssignmentDocument craeteWorkAssignemnt() {
		WorkAssignmentDocument waDoc = WorkAssignmentDocument.Factory
				.newInstance();
		WorkAssignmentType wat = waDoc.addNewWorkAssignment();
		PrimaryIDsType primryType = wat.addNewPrimaryIDs();
		primryType.setWorkItemID("1234-avg2-4r45-123a");
		primryType.setCompanyCode("IF");
		primryType.setClaimNumber("81-2015201-01");
		primryType.setClientClaimNumber("81-2015201-01");
		primryType.setClaimID(1234567891l);
		primryType.setClaimExposureID(1234567891l);
		primryType.setGroupID("45236");
		wat.addNewAssignorInfo().setAssignorID("rmadmin");
		wat.addNewEventInfo().setUpdatedByID("rmadmin");
		wat.addNewWorkAssignmentReference().addNewReferenceID()
				.setID(new BigInteger("125432542"));
		return waDoc;

	}
}