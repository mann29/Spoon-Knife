package com.mitchell.services.technical.appraisalassignment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.mitchell.services.business.partialloss.appraisalassignment.client.AppraisalAssignmentClient;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;
import com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignment;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;
import com.mitchell.systemconfiguration.SystemConfiguration;

public class SaveAppraisalAssignmentTest extends BaseIntegration {

	@Before
	public void setUp() throws Exception {
		this.appraisalAssignmentEJB = AppraisalAssignmentClient
				.getAppraisalAssignmentEJB();

		this.intTestHelper = new IntegrationTestHelperClient();

		this.templateMEForSaveCallFullPath = SystemConfiguration
				.getSettingValue(SYS_CONF_TEMPLATE_MITCHELL_ENVELOPE_PATH_FOR_SAVE_AA);

		this.devUserOrgId = Long.parseLong(SystemConfiguration
				.getSettingValue(SYS_CONF_DEV_USER_ORG_ID));

		this.devClaimNumberFormat = SystemConfiguration
				.getSettingValue(SYS_CONF_DEV_CLAIM_NUMBER_FORMAT_STRING);

		this.testUserUserInfoDoc = getUserInfoForTestUser();

		this.estimatePackageClient = new EstimatePackageClient();
	}

	@Test
	public void happyPath() throws Exception {
		// Create the test AADTO including the ME
		AppraisalAssignmentDTO testAADTO = createAADTOForSaveCall();

		// Call the Save method in the AAS
		AppraisalAssignmentDTO result = appraisalAssignmentEJB
				.saveAppraisalAssignment(testAADTO, this.testUserUserInfoDoc);

		// Assertions on the result of the call
		assertNotNull(result);
		assertNotNull(result.getMitchellEnvelopDoc());
		assertTrue(result.getMitchellEnvelopDoc().validate());
		assertEquals(0L, result.getTcn());
		assertFalse(0L == result.getWaTaskId());
		assertFalse(0L == result.getClaimId());
		assertFalse(0L == result.getClaimExposureId());
		assertFalse(0L == result.getDocumentID());
		assertEquals("OPENED", result.getStatus());
		assertEquals("NOT READY", result.getDisposition());
		assertEquals("N", result.getAssignmentHasBeenUpdate());
		assertFalse(0L == result.getAssignmentID());
		assertEquals(testAADTO.getClientClaimNumber(),
				result.getClientClaimNumber());
		assertNotNull(result.getWorkItemID());
		assertEquals("", result.getEventReasonCode());
		assertEquals("", result.getEventMemo());
		assertNull(result.getEventNameList());
		assertFalse(result.isOriginalAssignment());
		assertEquals("Y", result.getReqAssociateDataCompletedInd());
		assertEquals(0L, result.getWorkAssignmentTcn());
		assertNull(result.getTimeZone());
		assertNull(result.getHoldInfo());
		assertFalse(result.getIsSaveFromSSOPage());
		assertFalse(result.getSaveAndSendFlag());
		assertNull(result.getSubType());
		assertNull(result.getDuration());
		assertNull(result.getPriority());

		// Retrieve the Appraisal Assignment BO from the Estimate Package
		// Service using the Document Id from the result AADTO in order to
		// verify all of its fields
		AppraisalAssignment aaBO = this.estimatePackageClient
				.findAppraislAssignmentByDocId(result.getDocumentID());

		assertEquals(this.testUserUserInfoDoc.getUserInfo().getUserID(),
				aaBO.getAssignmentCreatedBy());
		assertNull(aaBO.getCallNeeded());
		assertNotNull("123-4567890", aaBO.getCellPhone());
		assertEquals(result.getClaimExposureId(), aaBO.getClaimExposureId()
				.longValue());
		assertEquals(result.getClaimId(), aaBO.getClaimId().longValue());
		assertEquals(result.getClientClaimNumber(), aaBO.getClientClaimNumber());
		assertNull(aaBO.getClientEstimateId());
		assertNotNull(aaBO.getCoCd());
		assertNotNull(this.testUserUserInfoDoc.getUserInfo().getUserID(),
				aaBO.getCreatedBy());
		assertNotNull(aaBO.getCreatedDate());
		assertEquals("2014-11-16 00:00:00.0", aaBO.getDateOfLoss().toString());
		assertNotNull(aaBO.getDocument());
		assertEquals("test@mitchell.com", aaBO.getEmailAddress());
		assertNotNull(aaBO.getId());
		assertNull(aaBO.getInspectionAddress1());
		assertNull(aaBO.getInspectionAddress2());
		assertNull(aaBO.getInspectionCity());
		assertNull(aaBO.getInspectionLatitude());
		assertNull(aaBO.getInspectionLongitude());
		assertNull(aaBO.getInspectionStateProvince());
		assertNull(aaBO.getInspectionZipPostalCode());
		assertNotNull("U", aaBO.getIsDrivable());
		assertNull(aaBO.getLocationPhone());
		assertNull(aaBO.getLocationType());
		assertEquals("INSURED", aaBO.getPrimaryContact());
		assertEquals("first", aaBO.getPrimaryContactFirstName());
		assertEquals("last", aaBO.getPrimaryContactLastName());
		assertNull(aaBO.getPrimaryContactSecondLastName());
		assertEquals("MANUAL", aaBO.getScheduleMethod());
		assertEquals(0L, aaBO.getTcn().longValue());
		assertNull(aaBO.getTravelRequired());
		assertNull(aaBO.getUpdatedBy());
		assertNull(aaBO.getUpdatedDate());
		assertNull(aaBO.getUserPreferredMoi());
		assertNull(aaBO.getValidInspectionAddress());
		assertEquals("Car", aaBO.getVehicleType());
		assertNull(aaBO.getVendorSrc());
		assertNotNull("123-4567890+123", aaBO.getWorkPhone());
	}

	@After
	public void tearDown() throws Exception {
		this.appraisalAssignmentEJB = null;
		this.intTestHelper = null;
		this.templateMEForSaveCallFullPath = null;
		this.devUserOrgId = 0L;
		this.devClaimNumberFormat = null;
		this.estimatePackageClient = null;
	}
}
