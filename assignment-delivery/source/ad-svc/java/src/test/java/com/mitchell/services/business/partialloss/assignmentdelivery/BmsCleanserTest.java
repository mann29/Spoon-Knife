package com.mitchell.services.business.partialloss.assignmentdelivery;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import com.cieca.bms.AddressType;
import com.cieca.bms.AdminInfoType;
import com.cieca.bms.AssignmentAddRqDocument.AssignmentAddRq;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.CIECADocument.CIECA;
import com.cieca.bms.CommunicationsType;
import com.cieca.bms.EstimatorType;
import com.cieca.bms.OrgInfoType;
import com.cieca.bms.OtherPartyType;
import com.cieca.bms.PartyType;
import com.cieca.bms.PersonInfoType;
import com.cieca.bms.PersonNameType;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;

@RunWith(MockitoJUnitRunner.class)
public class BmsCleanserTest {
	protected static final String CLEANSE_TEST_BMS_FILE1 = "bms-cleanse-test-1.xml";

	@Mock
	protected BmsCleanser testClass;

	protected IntegrationTestHelperClient itHelper;

	@Before
	public void setUp() throws Exception {
		this.itHelper = new IntegrationTestHelperClient();
	}

	@Test
	public void cleansForMIETransformationTestNull() throws Exception {
		CIECADocument cDoc = null;

		// Call method in test
		Mockito.when(this.testClass.cleansForMIETransformation(cDoc))
				.thenCallRealMethod();
		CIECADocument retval = this.testClass.cleansForMIETransformation(cDoc);
		assertNull(retval);

	}

	@Test
	public void cleansForMIETransformationTestNullAsg() throws Exception {
		CIECADocument cDoc = CIECADocument.Factory.newInstance();
		CIECA cieca = cDoc.addNewCIECA();

		// Call method in test
		Mockito.when(this.testClass.cleansForMIETransformation(cDoc))
				.thenCallRealMethod();
		CIECADocument retval = this.testClass.cleansForMIETransformation(cDoc);
		assertNotNull(retval);
		assertNull(retval.getCIECA().getAssignmentAddRq());

	}

	@Test
	public void cleansForMIETransformationTestAsgNotNull() throws Exception {
		CIECADocument cDoc = CIECADocument.Factory.newInstance();
		CIECA cieca = cDoc.addNewCIECA();
		AssignmentAddRq asgAddRq = cieca.addNewAssignmentAddRq();

		// Call method in test
		Mockito.when(this.testClass.cleansForMIETransformation(cDoc))
				.thenCallRealMethod();
		CIECADocument retval = this.testClass.cleansForMIETransformation(cDoc);
		assertNotNull(retval);
		assertNotNull(retval.getCIECA().getAssignmentAddRq());

		Mockito.verify(this.testClass).cleanseAsgAddRqForMIE(
				retval.getCIECA().getAssignmentAddRq());

	}

	@Test
	public void cleanseAsgAddRqForMIETest() throws Exception {
		AssignmentAddRq asgAddRq = AssignmentAddRq.Factory.newInstance();
		asgAddRq.addNewAdminInfo().addNewAdjuster().setAffiliation("abcd");

		Mockito.when(this.testClass.cleanseAdminInfoForMIE(asgAddRq))
				.thenReturn(asgAddRq);

		// Call method in test
		Mockito.when(this.testClass.cleanseAsgAddRqForMIE(asgAddRq))
				.thenCallRealMethod();
		AssignmentAddRq retval = this.testClass.cleanseAsgAddRqForMIE(asgAddRq);
		assertNotNull(retval);

		assertTrue("abcd".equals(retval.getAdminInfo().getAdjuster()
				.getAffiliation()));
		Mockito.verify(this.testClass).cleanseAdminInfoForMIE(asgAddRq);

	}

	@Test
	public void cleanseAdminInfoForMIETestNull() throws Exception {
		AssignmentAddRq asgAddRq = AssignmentAddRq.Factory.newInstance();
		AdminInfoType ait = asgAddRq.addNewAdminInfo();

		// Call method in test
		Mockito.when(this.testClass.cleanseAdminInfoForMIE(asgAddRq))
				.thenCallRealMethod();
		AssignmentAddRq retval = this.testClass
				.cleanseAdminInfoForMIE(asgAddRq);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.never()).cleansePartyForMIE(
				(PartyType) Mockito.anyObject());

	}

	@Test
	public void cleanseAdminInfoForMIETestPolicyHolder() throws Exception {
		AssignmentAddRq asgAddRq = AssignmentAddRq.Factory.newInstance();
		AdminInfoType ait = asgAddRq.addNewAdminInfo();

		ait.addNewPolicyHolder().addNewParty();

		// Call method in test
		Mockito.when(this.testClass.cleanseAdminInfoForMIE(asgAddRq))
				.thenCallRealMethod();
		AssignmentAddRq retval = this.testClass
				.cleanseAdminInfoForMIE(asgAddRq);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.times(1)).cleansePartyForMIE(
				(PartyType) Mockito.anyObject());

	}

	@Test
	public void cleanseAdminInfoForMIETestInsured() throws Exception {
		AssignmentAddRq asgAddRq = AssignmentAddRq.Factory.newInstance();
		AdminInfoType ait = asgAddRq.addNewAdminInfo();

		ait.addNewInsured().addNewParty();

		// Call method in test
		Mockito.when(this.testClass.cleanseAdminInfoForMIE(asgAddRq))
				.thenCallRealMethod();
		AssignmentAddRq retval = this.testClass
				.cleanseAdminInfoForMIE(asgAddRq);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.times(1)).cleansePartyForMIE(
				(PartyType) Mockito.anyObject());

	}

	@Test
	public void cleanseAdminInfoForMIETestClaimant() throws Exception {
		AssignmentAddRq asgAddRq = AssignmentAddRq.Factory.newInstance();
		AdminInfoType ait = asgAddRq.addNewAdminInfo();

		ait.addNewClaimant().addNewParty();

		// Call method in test
		Mockito.when(this.testClass.cleanseAdminInfoForMIE(asgAddRq))
				.thenCallRealMethod();
		AssignmentAddRq retval = this.testClass
				.cleanseAdminInfoForMIE(asgAddRq);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.times(1)).cleansePartyForMIE(
				(PartyType) Mockito.anyObject());

	}

	@Test
	public void cleanseAdminInfoForMIETestOwner() throws Exception {
		AssignmentAddRq asgAddRq = AssignmentAddRq.Factory.newInstance();
		AdminInfoType ait = asgAddRq.addNewAdminInfo();

		ait.addNewOwner().addNewParty();

		// Call method in test
		Mockito.when(this.testClass.cleanseAdminInfoForMIE(asgAddRq))
				.thenCallRealMethod();
		AssignmentAddRq retval = this.testClass
				.cleanseAdminInfoForMIE(asgAddRq);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.times(1)).cleansePartyForMIE(
				(PartyType) Mockito.anyObject());

	}

	@Test
	public void cleanseAdminInfoForMIETestAdjuster() throws Exception {
		AssignmentAddRq asgAddRq = AssignmentAddRq.Factory.newInstance();
		AdminInfoType ait = asgAddRq.addNewAdminInfo();

		ait.addNewAdjuster().addNewParty();

		// Call method in test
		Mockito.when(this.testClass.cleanseAdminInfoForMIE(asgAddRq))
				.thenCallRealMethod();
		AssignmentAddRq retval = this.testClass
				.cleanseAdminInfoForMIE(asgAddRq);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.times(1)).cleansePartyForMIE(
				(PartyType) Mockito.anyObject());

	}

	@Test
	public void cleanseAdminInfoForMIETestInsuranceCompany() throws Exception {
		AssignmentAddRq asgAddRq = AssignmentAddRq.Factory.newInstance();
		AdminInfoType ait = asgAddRq.addNewAdminInfo();

		ait.addNewInsuranceCompany().addNewParty();

		// Call method in test
		Mockito.when(this.testClass.cleanseAdminInfoForMIE(asgAddRq))
				.thenCallRealMethod();
		AssignmentAddRq retval = this.testClass
				.cleanseAdminInfoForMIE(asgAddRq);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.times(1)).cleansePartyForMIE(
				(PartyType) Mockito.anyObject());

	}

	@Test
	public void cleanseAdminInfoForMIETestRepairFacility() throws Exception {
		AssignmentAddRq asgAddRq = AssignmentAddRq.Factory.newInstance();
		AdminInfoType ait = asgAddRq.addNewAdminInfo();

		ait.addNewRepairFacility().addNewParty();

		// Call method in test
		Mockito.when(this.testClass.cleanseAdminInfoForMIE(asgAddRq))
				.thenCallRealMethod();
		AssignmentAddRq retval = this.testClass
				.cleanseAdminInfoForMIE(asgAddRq);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.times(1)).cleansePartyForMIE(
				(PartyType) Mockito.anyObject());

	}

	@Test
	public void cleanseAdminInfoForMIETestInspectionSite() throws Exception {
		AssignmentAddRq asgAddRq = AssignmentAddRq.Factory.newInstance();
		AdminInfoType ait = asgAddRq.addNewAdminInfo();

		ait.addNewInspectionSite().addNewParty();

		// Call method in test
		Mockito.when(this.testClass.cleanseAdminInfoForMIE(asgAddRq))
				.thenCallRealMethod();
		AssignmentAddRq retval = this.testClass
				.cleanseAdminInfoForMIE(asgAddRq);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.times(1)).cleansePartyForMIE(
				(PartyType) Mockito.anyObject());

	}

	@Test
	public void cleanseAdminInfoForMIETestOtherPartyArray() throws Exception {
		AssignmentAddRq asgAddRq = AssignmentAddRq.Factory.newInstance();
		AdminInfoType ait = asgAddRq.addNewAdminInfo();

		OtherPartyType opt = ait.addNewOtherParty();
		OtherPartyType opt2 = ait.addNewOtherParty();

		// Call method in test
		Mockito.when(this.testClass.cleanseAdminInfoForMIE(asgAddRq))
				.thenCallRealMethod();
		AssignmentAddRq retval = this.testClass
				.cleanseAdminInfoForMIE(asgAddRq);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.times(2)).cleansePartyForMIE(
				(PartyType) Mockito.anyObject());

	}

	@Test
	public void cleanseAdminInfoForMIETestEstimatorArray() throws Exception {
		AssignmentAddRq asgAddRq = AssignmentAddRq.Factory.newInstance();
		AdminInfoType ait = asgAddRq.addNewAdminInfo();

		EstimatorType opt = ait.addNewEstimator();
		EstimatorType opt2 = ait.addNewEstimator();

		// Call method in test
		Mockito.when(this.testClass.cleanseAdminInfoForMIE(asgAddRq))
				.thenCallRealMethod();
		AssignmentAddRq retval = this.testClass
				.cleanseAdminInfoForMIE(asgAddRq);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.times(2)).cleansePartyForMIE(
				(PartyType) Mockito.anyObject());

	}

	@Test
	public void cleansePartyForMIETestNull() throws Exception {
		PartyType pt = null;

		// Call method in test
		Mockito.when(this.testClass.cleansePartyForMIE(pt))
				.thenCallRealMethod();
		PartyType retval = this.testClass.cleansePartyForMIE(pt);
		assertNull(retval);

	}

	@Test
	public void cleansePartyForMIETest() throws Exception {
		PartyType pt = PartyType.Factory.newInstance();

		// Call method in test
		Mockito.when(this.testClass.cleansePartyForMIE(pt))
				.thenCallRealMethod();
		PartyType retval = this.testClass.cleansePartyForMIE(pt);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.never()).cleanseOrgInfoForMIE(
				(OrgInfoType) Mockito.anyObject());
		Mockito.verify(this.testClass, Mockito.never())
				.cleansePersonInfoForMIE((PersonInfoType) Mockito.anyObject());

	}

	@Test
	public void cleansePartyForMIETestOrgInfo() throws Exception {
		PartyType pt = PartyType.Factory.newInstance();
		pt.addNewOrgInfo();

		// Call method in test
		Mockito.when(this.testClass.cleansePartyForMIE(pt))
				.thenCallRealMethod();
		PartyType retval = this.testClass.cleansePartyForMIE(pt);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.times(1)).cleanseOrgInfoForMIE(
				(OrgInfoType) Mockito.anyObject());
		Mockito.verify(this.testClass, Mockito.never())
				.cleansePersonInfoForMIE((PersonInfoType) Mockito.anyObject());

	}

	@Test
	public void cleansePartyForMIETestPersonInfo() throws Exception {
		PartyType pt = PartyType.Factory.newInstance();
		pt.addNewPersonInfo();

		// Call method in test
		Mockito.when(this.testClass.cleansePartyForMIE(pt))
				.thenCallRealMethod();
		PartyType retval = this.testClass.cleansePartyForMIE(pt);
		assertNotNull(retval);

		Mockito.verify(this.testClass, Mockito.never()).cleanseOrgInfoForMIE(
				(OrgInfoType) Mockito.anyObject());
		Mockito.verify(this.testClass, Mockito.times(1))
				.cleansePersonInfoForMIE((PersonInfoType) Mockito.anyObject());

	}

	@Test
	public void cleanseOrgInfoForMIETestNull() throws Exception {
		OrgInfoType orgInfo = null;

		// Call method in test
		Mockito.when(this.testClass.cleanseOrgInfoForMIE(orgInfo))
				.thenCallRealMethod();
		OrgInfoType retval = this.testClass.cleanseOrgInfoForMIE(orgInfo);
		assertNull(retval);

	}

	@Test
	public void cleanseOrgInfoForMIETestNoCompanyName() throws Exception {
		OrgInfoType orgInfo = OrgInfoType.Factory.newInstance();

		// Call method in test
		Mockito.when(this.testClass.cleanseOrgInfoForMIE(orgInfo))
				.thenCallRealMethod();
		OrgInfoType retval = this.testClass.cleanseOrgInfoForMIE(orgInfo);
		assertNotNull(retval);
	}

	@Test
	public void cleanseOrgInfoForMIETest() throws Exception {
		OrgInfoType orgInfo = OrgInfoType.Factory.newInstance();
		orgInfo.setCompanyName("abcdef");

		// Call method in test
		Mockito.when(this.testClass.cleanseOrgInfoForMIE(orgInfo))
				.thenCallRealMethod();
		OrgInfoType retval = this.testClass.cleanseOrgInfoForMIE(orgInfo);
		assertNotNull(retval);
	}

	@Test
	public void cleansePersonInfoForMIETestNull() throws Exception {
		PersonInfoType personInfo = null;

		// Call method in test
		Mockito.when(this.testClass.cleansePersonInfoForMIE(personInfo))
				.thenCallRealMethod();
		PersonInfoType retval = this.testClass
				.cleansePersonInfoForMIE(personInfo);
		assertNull(retval);

	}

	@Test
	public void cleansePersonInfoForMIETestNoName() throws Exception {
		PersonInfoType personInfo = PersonInfoType.Factory.newInstance();

		// Call method in test
		Mockito.when(this.testClass.cleansePersonInfoForMIE(personInfo))
				.thenCallRealMethod();
		PersonInfoType retval = this.testClass
				.cleansePersonInfoForMIE(personInfo);
		assertNotNull(retval);
		Mockito.verify(this.testClass).cleanseCommsForMIE(
				(CommunicationsType[]) Mockito.anyObject());
	}

	@Test
	public void cleansePersonInfoForMIETestWithName() throws Exception {
		PersonInfoType personInfo = PersonInfoType.Factory.newInstance();

		PersonNameType pnt = personInfo.addNewPersonName();
		pnt.setFirstName("first");
		pnt.setLastName("last");

		// Call method in test
		Mockito.when(this.testClass.cleansePersonInfoForMIE(personInfo))
				.thenCallRealMethod();
		PersonInfoType retval = this.testClass
				.cleansePersonInfoForMIE(personInfo);
		assertNotNull(retval);

		Mockito.verify(this.testClass).cleanseCommsForMIE(
				(CommunicationsType[]) Mockito.anyObject());

	}

	@Test
	public void cleanseCommsForMIETestNull() throws Exception {
		CommunicationsType[] comms = null;

		// Call method in test
		Mockito.doCallRealMethod().when(this.testClass)
				.cleanseCommsForMIE(comms);
		this.testClass.cleanseCommsForMIE(comms);

		Mockito.verify(this.testClass, Mockito.never()).cleanseAddress(
				(AddressType) Mockito.anyObject());

	}

	@Test
	public void cleanseCommsForMIETestNoAddress() throws Exception {
		CommunicationsType[] comms = new CommunicationsType[1];

		comms[0] = CommunicationsType.Factory.newInstance();

		// Call method in test
		Mockito.doCallRealMethod().when(this.testClass)
				.cleanseCommsForMIE(comms);
		this.testClass.cleanseCommsForMIE(comms);

		Mockito.verify(this.testClass, Mockito.never()).cleanseAddress(
				(AddressType) Mockito.anyObject());

	}

	@Test
	public void cleanseCommsForMIETestAddress() throws Exception {
		CommunicationsType[] comms = new CommunicationsType[1];

		comms[0] = CommunicationsType.Factory.newInstance();
		comms[0].addNewAddress().setAddress1("abcd");

		// Call method in test
		Mockito.doCallRealMethod().when(this.testClass)
				.cleanseCommsForMIE(comms);
		this.testClass.cleanseCommsForMIE(comms);

		Mockito.verify(this.testClass, Mockito.times(1)).cleanseAddress(
				comms[0].getAddress());

	}

	@Test
	public void cleanseAddressTestNull() throws Exception {
		AddressType address = null;

		// Call method in test
		Mockito.when(this.testClass.cleanseAddress(address))
				.thenCallRealMethod();
		AddressType retval = this.testClass.cleanseAddress(address);
		assertNull(retval);
	}

	@Test
	public void cleanseAddressTestNoAddressInfo() throws Exception {
		AddressType address = AddressType.Factory.newInstance();

		// Call method in test
		Mockito.when(this.testClass.cleanseAddress(address))
				.thenCallRealMethod();
		AddressType retval = this.testClass.cleanseAddress(address);
		assertNotNull(retval);
	}

	@Test
	public void cleanseAddressTest() throws Exception {
		AddressType address = AddressType.Factory.newInstance();

		address.setAddress1("adr1");
		address.setAddress2("adr2");
		address.setCity("city");

		// Call method in test
		Mockito.when(this.testClass.cleanseAddress(address))
				.thenCallRealMethod();
		AddressType retval = this.testClass.cleanseAddress(address);
		assertNotNull(retval);
	}

	@Test
	public void cleansForMIETransformationTest() throws Exception {
		// Load the BMS file
		File ff = this.itHelper.makeFileFromResource(CLEANSE_TEST_BMS_FILE1);
		CIECADocument cDoc = CIECADocument.Factory.parse(ff);

		// Call method in test - not using the Mock
		BmsCleanser testCleanser = new BmsCleanser();

		CIECADocument retval = testCleanser.cleansForMIETransformation(cDoc);
		assertNotNull(retval);
		assertNotNull(retval.getCIECA().getAssignmentAddRq());

		String ssOrig = cDoc.getCIECA().getAssignmentAddRq().getAdminInfo()
				.getInspectionSite().getParty().getOrgInfo().getCompanyName();
		String ssFixed = retval.getCIECA().getAssignmentAddRq().getAdminInfo()
				.getInspectionSite().getParty().getOrgInfo().getCompanyName();

		assertTrue(ssOrig.equals(ssFixed));
		assertTrue("Wrangler”s Auto Body".equals(ssFixed));
	}

}
