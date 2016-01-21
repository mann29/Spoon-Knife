package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.CIECADocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.ClaimProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.EstimatePackageProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.util.UserInfoUtils;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;
import com.mitchell.services.technical.claim.common.DTO.BmsClmOutDTO;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;

public class AutoSupplementAssignmentAASHandlerImplTest
{
  protected AppraisalAssignmentServiceHandlerImpl testClass;
  protected IntegrationTestHelperClient itHelper;
  protected ClaimProxy claimProxy;
  protected UserInfoUtils userInfoUtils;
  
  
  @Before
  public void setUp()
      throws Exception
  {	
    this.itHelper = new IntegrationTestHelperClient();

    this.testClass = new AppraisalAssignmentServiceHandlerImpl();
    this.testClass.estimatePackageProxy = mock(EstimatePackageProxy.class);
    this.testClass.claimProxy = mock(ClaimProxy.class);
    this.testClass.userInfoUtils = mock(UserInfoUtils.class);
  }

  @After
  public void tearDown()
      throws Exception
  {
    this.testClass = null;
    this.itHelper = null;
  }

  
  
  @Test
  public void createMinimalSupplementAssignmentBMSIntractionTest()
  	throws Exception
  {
	  
	  long assignorOrgId = 316405;
	  String clientClaimNumberSuffix = "OATEST0528ADDA-01";
	  long estimateDocumentID =100001817502L;
	  BmsClmOutDTO bmsClmOutDTO = null;
	  CIECADocument ciecaDoc = null;
	  String filePath = "src/test/resources/AppraisalAssignmentBMS.xml";
	  
	  UserInfoDocument assignorUserInfoDocument = MockTestUtil.getUserInfoDoc();
	  Estimate est = MockTestUtil.getEstimate();
	  
	  bmsClmOutDTO = new BmsClmOutDTO();
	  bmsClmOutDTO.setXmlObject(MockTestUtil.getAppraisalAssignmentBMSDoc(filePath));
	  
	  ciecaDoc = CIECADocument.Factory.newInstance();
      String retVal = ciecaDoc.xmlText();
      
      /*claimProxy.expects(atLeastOnce())
      .method("readClaimInfo").withAnyArguments()
      .will(returnValue(bmsClmOutDTO));*/
      
	  doReturn(assignorUserInfoDocument).when(this.testClass.userInfoUtils).getUserInfoDoc(assignorOrgId);
	  
	  doReturn(bmsClmOutDTO).when(this.testClass.claimProxy).readClaimInfo(assignorUserInfoDocument, clientClaimNumberSuffix);
	  doReturn(est).when(this.testClass.estimatePackageProxy).getEstimateAndDocByDocId(estimateDocumentID, true);
	  
	  retVal = this.testClass.createMinimalSupplementAssignmentBMS(clientClaimNumberSuffix, estimateDocumentID, assignorOrgId);
      verify(this.testClass.userInfoUtils).getUserInfoDoc(assignorOrgId);
      verify(this.testClass.userInfoUtils, atLeastOnce()).getUserInfoDoc(assignorOrgId);
      verify(this.testClass.claimProxy).readClaimInfo(assignorUserInfoDocument, clientClaimNumberSuffix);
      //verify(this.testClass).createMinimalSupplementAssignmentBMS(clientClaimNumberSuffix, estimateDocumentID, assignorOrgId);
      verifyNoMoreInteractions(this.testClass.claimProxy);
      assertNotNull(retVal);
  }
  
  @Test
  public void sanitizeAssignmentBMSTest()
  	throws Exception
  {
	  AssignmentAddRqDocument assignmentAddRqDocumentNew = null;
	  String filePath = "src/test/resources/AppraisalAssignmentToSanitizeBMS.xml";
	  AssignmentAddRqDocument assignmentAddRqDocument = MockTestUtil.getAppraisalAssignmentBMSDoc(filePath);
	  assignmentAddRqDocumentNew = this.testClass.sanitizeBMS(assignmentAddRqDocument);
	  assertNotNull("Country Code is NULL for PolicyHolder", assignmentAddRqDocumentNew.getAssignmentAddRq().getAdminInfo().
			  getPolicyHolder().getParty().getPersonInfo()
			  .getCommunicationsArray(0).getAddress().getCountryCode());
	  assertEquals("US", assignmentAddRqDocumentNew.getAssignmentAddRq().getAdminInfo().
			  getPolicyHolder().getParty().getPersonInfo()
			  .getCommunicationsArray(0).getAddress().getCountryCode());
	  assertNotNull("Country Code is NULL for Owner", assignmentAddRqDocumentNew.getAssignmentAddRq().getAdminInfo().
			  getOwner().getParty().getPersonInfo()
			  .getCommunicationsArray(0).getAddress().getCountryCode());
	  assertEquals("US", assignmentAddRqDocumentNew.getAssignmentAddRq().getAdminInfo().
			  getOwner().getParty().getPersonInfo()
			  .getCommunicationsArray(0).getAddress().getCountryCode());
	  
	  assertNotNull("Affiliation for Estimator is NULL...",assignmentAddRqDocumentNew.getAssignmentAddRq().
			  getAdminInfo().getEstimatorArray(0).getAffiliation());
	  
	  assertNull("Affiliation for Adjustor is NULL...",assignmentAddRqDocumentNew.getAssignmentAddRq().
			  getAdminInfo().getAdjuster().getAffiliation());
	  
	  assertNull("Transmission info is not NULL...", assignmentAddRqDocumentNew.getAssignmentAddRq().getVehicleDamageAssignment().
			  	getVehicleInfo().getPowertrain().getTransmissionInfo());
	  
	  assertNull("Odometer Reading Code is not NULL...", assignmentAddRqDocumentNew.getAssignmentAddRq().getVehicleDamageAssignment().
			  	getVehicleInfo().getVehicleDesc().getOdometerInfo().getOdometerReadingCode());
	  assertNull("Odometer Reading Measure is not NULL...", assignmentAddRqDocumentNew.getAssignmentAddRq().getVehicleDamageAssignment().
			  	getVehicleInfo().getVehicleDesc().getOdometerInfo().getOdometerReadingMeasure());
	  
	  assertNull("Condition Code is not null...",assignmentAddRqDocumentNew.getAssignmentAddRq().getVehicleDamageAssignment().
	  	getVehicleInfo().getCondition().getConditionCode());
	  
  }
  
  @Test
  public void retrieveClaimantTypeFromAssignmentBMSTest()
  	throws Exception
  {
	  String strClaimantInsuredType = null;
	  String filePath = "src/test/resources/AppraisalAssignmentToSanitizeBMS.xml";
	  AssignmentAddRqDocument assignmentAddRqDocument = MockTestUtil.getAppraisalAssignmentBMSDoc(filePath);
	  strClaimantInsuredType = this.testClass.retrieveClaimantInsuredType(assignmentAddRqDocument);
	  assertNotNull("Insured/Claimant is NULL for AppraisalAssignment.", strClaimantInsuredType);
	  assertEquals("Primary Contact (Claimant) is not present in AppraisalAssignment","claimant", strClaimantInsuredType);
  }
  
  @Test
  public void retrieveInsuredTypeFromAssignmentBMSTest()
  	throws Exception
  {
	  String strClaimantInsuredType = null;
	  String filePath = "src/test/resources/AppraisalAssignmentBMS_RetrieveInsured.xml";
	  AssignmentAddRqDocument assignmentAddRqDocument = MockTestUtil.getAppraisalAssignmentBMSDoc(filePath);
	  strClaimantInsuredType = this.testClass.retrieveClaimantInsuredType(assignmentAddRqDocument);
	  assertNotNull("Insured/Claimant is NULL for AppraisalAssignment.", strClaimantInsuredType);
	  assertEquals("Primary Contact (Insured) is not present in AppraisalAssignment","insured", strClaimantInsuredType);
  }
  
  @Test
  public void retrieveInsuredTypeWithoutPersonNameFromAssignmentBMSTest()
  	throws Exception
  {
	  String strClaimantInsuredType = null;
	  String filePath = "src/test/resources/AppraisalAssignmentBMS_RetrieveInsured_NoPersonName.xml";
	  AssignmentAddRqDocument assignmentAddRqDocument = MockTestUtil.getAppraisalAssignmentBMSDoc(filePath);
	  strClaimantInsuredType = this.testClass.retrieveClaimantInsuredType(assignmentAddRqDocument);
	  assertNotNull("Insured/Claimant is NULL for AppraisalAssignment.", strClaimantInsuredType);
	  assertEquals("Primary Contact (Insured) is not present in AppraisalAssignment","insured", strClaimantInsuredType);
  }
  
}
