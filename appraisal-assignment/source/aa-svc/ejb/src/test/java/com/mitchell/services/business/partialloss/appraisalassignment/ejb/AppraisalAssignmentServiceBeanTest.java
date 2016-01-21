package com.mitchell.services.business.partialloss.appraisalassignment.ejb;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cieca.bms.CIECADocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.schedule.AssignTaskType;
import com.mitchell.schemas.schedule.ScheduleInfoXMLDocument;
import com.mitchell.schemas.schedule.ScheduleInfoXMLDocument.ScheduleInfoXML;
import com.mitchell.services.business.partialloss.appraisalassignment.external.AASExternalAccessor;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.submit.AASAssignScheduleSubmitEJB;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.EstimatePackageProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.util.MitchellEnvelopeHandler;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;
import com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class AppraisalAssignmentServiceBeanTest {
	protected AppraisalAssignmentServiceBean testClass;
	protected IntegrationTestHelperClient itHelper;

	@Before
	public void setUp() throws Exception {
		this.itHelper = new IntegrationTestHelperClient();

		this.testClass = mock(AppraisalAssignmentServiceBean.class);
		this.testClass.extAccess = mock(AASExternalAccessor.class);
		this.testClass.estimatePackageProxy=mock(EstimatePackageProxy.class);
		this.testClass.mitchellEnvelopeHandler=mock(MitchellEnvelopeHandler.class);

	}

	@After
	public void tearDown() throws Exception {
		this.testClass = null;
		this.itHelper = null;
	}

	@Test
	public void assignScheduleResourceToMultipleAssignmentsTestNotParallel()
			throws Exception {
		ScheduleInfoXMLDocument scheduleInfoXmlObject = ScheduleInfoXMLDocument.Factory
				.newInstance();
		ScheduleInfoXML siXml = scheduleInfoXmlObject.addNewScheduleInfoXML();
		AssignTaskType at1 = siXml.addNewAssignTask();
		at1.setTaskId(1234);

		UserInfoDocument assignorUserInfoDocument = UserInfoDocument.Factory
				.newInstance();

		when(
				this.testClass
						.isAssignScheduleParallel((AssignTaskType[]) anyObject()))
				.thenReturn(false);
		when(this.testClass.getAppraisalAssignmentEJB()).thenReturn(
				this.testClass);

		int assignResult = 44;
		when(
				this.testClass
						.assignScheduleAppraisalAssignment_Requires_NewTX(at1,
								assignorUserInfoDocument)).thenReturn(
				assignResult);

		// Call method in test
		when(
				this.testClass.assignScheduleResourceToMultipleAssignments(
						scheduleInfoXmlObject, assignorUserInfoDocument))
				.thenCallRealMethod();
		java.util.HashMap retval = this.testClass
				.assignScheduleResourceToMultipleAssignments(
						scheduleInfoXmlObject, assignorUserInfoDocument);
		assertNotNull(retval);

		assertTrue(retval.size() == 1);

		Integer ival = (Integer) retval.get(new Long(1234));
		assertNotNull(ival);
		assertTrue(ival.intValue() == assignResult);

	}

	@Test
	public void assignScheduleResourceToMultipleAssignmentsTestParallel()
			throws Exception {
		ScheduleInfoXMLDocument scheduleInfoXmlObject = ScheduleInfoXMLDocument.Factory
				.newInstance();
		ScheduleInfoXML siXml = scheduleInfoXmlObject.addNewScheduleInfoXML();
		AssignTaskType at1 = siXml.addNewAssignTask();
		at1.setTaskId(1234);

		UserInfoDocument assignorUserInfoDocument = UserInfoDocument.Factory
				.newInstance();

		when(
				this.testClass
						.isAssignScheduleParallel((AssignTaskType[]) anyObject()))
				.thenReturn(true);

		java.util.HashMap mapResultSet = new java.util.HashMap();
		mapResultSet.put("aa", "bb");

		this.testClass.assignScheduleSubmitEjb = mock(AASAssignScheduleSubmitEJB.class);
		when(
				this.testClass.assignScheduleSubmitEjb
						.submitAssignScheduleAppraisalAssignment(
								(AssignTaskType[]) anyObject(),
								(UserInfoDocument) anyObject())).thenReturn(
				mapResultSet);

		// Call method in test
		when(
				this.testClass.assignScheduleResourceToMultipleAssignments(
						scheduleInfoXmlObject, assignorUserInfoDocument))
				.thenCallRealMethod();
		java.util.HashMap retval = this.testClass
				.assignScheduleResourceToMultipleAssignments(
						scheduleInfoXmlObject, assignorUserInfoDocument);
		assertNotNull(retval);

		assertTrue(retval.size() == 1);

		String val = (String) retval.get("aa");
		assertTrue("bb".equals(val));

	}

	@Test
	public void isParallelMinListSizeTestEqual() throws Exception {
		int listSize = 2;

		// System.out.println("mocked testclass: " + this.testClass);
		System.out.println("extAccess: " + this.testClass.extAccess);
		when(
				this.testClass.extAccess
						.getSystemConfigValue(AASParallelConstants.SYSCONF_PARALLEL_MIN_LIST_SIZE))
				.thenReturn("2");

		// Call method in test
		when(this.testClass.isParallelMinListSize(listSize))
				.thenCallRealMethod();
		boolean retval = this.testClass.isParallelMinListSize(listSize);
		assertTrue(retval);

	}

	@Test
	public void isParallelMinListSizeTestLess() throws Exception {
		int listSize = 1;
		when(
				this.testClass.extAccess
						.getSystemConfigValue(AASParallelConstants.SYSCONF_PARALLEL_MIN_LIST_SIZE))
				.thenReturn("2");

		// Call method in test
		when(this.testClass.isParallelMinListSize(listSize))
				.thenCallRealMethod();
		boolean retval = this.testClass.isParallelMinListSize(listSize);
		assertFalse(retval);

	}

	 @Test
	public void isParallelMinListSizeTestMore() throws Exception {
		int listSize = 3;
		when(
				this.testClass.extAccess
						.getSystemConfigValue(AASParallelConstants.SYSCONF_PARALLEL_MIN_LIST_SIZE))
				.thenReturn("2");

		// Call method in test
		when(this.testClass.isParallelMinListSize(listSize))
				.thenCallRealMethod();
		boolean retval = this.testClass.isParallelMinListSize(listSize);
		assertTrue(retval);

	}

	@Test
	public void isAssignScheduleParallelTestLength() throws Exception {
		AssignTaskType[] tdoc = new AssignTaskType[2];

		when(this.testClass.isParallelMinListSize(tdoc.length))
				.thenReturn(true);

		// Call method in test
		when(this.testClass.isAssignScheduleParallel(tdoc))
				.thenCallRealMethod();
		boolean retval = this.testClass.isAssignScheduleParallel(tdoc);
		assertTrue(retval);

	}

	@Test
	public void isAssignScheduleParallelTestNoLunch() throws Exception {
		AssignTaskType[] tdoc = new AssignTaskType[2];

		tdoc[0] = AssignTaskType.Factory.newInstance();
		tdoc[1] = AssignTaskType.Factory.newInstance();
		tdoc[1].setAssignmentType("akjdfuase");

		when(this.testClass.isParallelMinListSize(tdoc.length)).thenReturn(
				false);

		// Call method in test
		when(this.testClass.isAssignScheduleParallel(tdoc))
				.thenCallRealMethod();
		boolean retval = this.testClass.isAssignScheduleParallel(tdoc);
		assertFalse(retval);
	}

	@Test
	public void isAssignScheduleParallelTestLunch() throws Exception {
		AssignTaskType[] tdoc = new AssignTaskType[2];

		tdoc[0] = AssignTaskType.Factory.newInstance();
		tdoc[1] = AssignTaskType.Factory.newInstance();
		tdoc[1].setAssignmentType("lunch");

		when(this.testClass.isParallelMinListSize(tdoc.length)).thenReturn(
				false);

		// Call method in test
		when(this.testClass.isAssignScheduleParallel(tdoc))
				.thenCallRealMethod();
		boolean retval = this.testClass.isAssignScheduleParallel(tdoc);
		assertTrue(retval);
	}

	@Test
	public void isCreateMinimalSupplementAssignmentBMSTest() throws Exception {
		when(this.testClass.createMinimalSupplementAssignmentBMS(null, 0, 0))
				.thenReturn("test");
		String retval = this.testClass.createMinimalSupplementAssignmentBMS(
				null, 0, 0);
		assertNotNull(retval);
	}
	
	@Test
	public void getCiecaFromAprasgDtoTest() throws Exception{
		MitchellEnvelopeDocument meDoc=MitchellEnvelopeDocument.Factory.parse(new File("src/test/resources/ME.XML"));
		AppraisalAssignmentDTO apprAsgDto=new AppraisalAssignmentDTO();
		apprAsgDto.setAppraisalAssignmentMEStr(meDoc.toString());
		CIECADocument ciecaDoc=CIECADocument.Factory.newInstance();
		when(this.testClass.estimatePackageProxy.getAppraisalAssignmentDocument(anyLong())).thenReturn(apprAsgDto);
		when(this.testClass.mitchellEnvelopeHandler.getCiecaFromME((MitchellEnvelopeHelper)anyObject())).thenReturn(ciecaDoc);
		when(this.testClass.getCiecaFromAprasgDto(anyLong())).thenCallRealMethod();
		
		CIECADocument cicaActual=(CIECADocument)this.testClass.getCiecaFromAprasgDto(123456l);
		assertNotNull(cicaActual);
	}

	@Test
	public void getCiecaFromAprasgDtoTest_Null() throws Exception{
		MitchellEnvelopeDocument meDoc=MitchellEnvelopeDocument.Factory.parse(new File("src/test/resources/ME.XML"));
		AppraisalAssignmentDTO apprAsgDto=new AppraisalAssignmentDTO();
		apprAsgDto.setAppraisalAssignmentMEStr(meDoc.toString());
		CIECADocument ciecaDoc=null;
		when(this.testClass.estimatePackageProxy.getAppraisalAssignmentDocument(anyLong())).thenReturn(apprAsgDto);
		when(this.testClass.mitchellEnvelopeHandler.getCiecaFromME((MitchellEnvelopeHelper)anyObject())).thenReturn(ciecaDoc);
		when(this.testClass.getCiecaFromAprasgDto(anyLong())).thenCallRealMethod();
		
		CIECADocument cicaActual=(CIECADocument)this.testClass.getCiecaFromAprasgDto(123456l);
		assertNull(cicaActual);
	}
	
	@Test(expected=Exception.class)
	public void getCiecaFromAprasgDtoTest_Exception() throws Exception{
		MitchellEnvelopeDocument meDoc=MitchellEnvelopeDocument.Factory.parse(new File("src/test/resources/ME.XML"));
		AppraisalAssignmentDTO apprAsgDto=new AppraisalAssignmentDTO();
		apprAsgDto.setAppraisalAssignmentMEStr(meDoc.toString());
		CIECADocument ciecaDoc=null;
		this.testClass.estimatePackageProxy=null; 
		when(this.testClass.getCiecaFromAprasgDto(anyLong())).thenCallRealMethod();
		
		CIECADocument cicaActual=(CIECADocument)this.testClass.getCiecaFromAprasgDto(123456l);
		assertNull(cicaActual);
	}

}
