package com.mitchell.services.technical.appraisalassignment;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import java.math.MathContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.NameValuePairType;
import com.mitchell.services.business.partialloss.appraisalassignment.client.AppraisalAssignmentClient;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;
import com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignment;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;
import com.mitchell.systemconfiguration.SystemConfiguration;
import com.mitchell.utils.misc.UUIDFactory;

public class UpdateAppraisalAssignmentTest extends BaseIntegration {

	// CONSTANTS
	private static final String TEMPLATE_MITCHELL_ENVELOPE_PATH_FOR_UPDATE_AA = "/AppraisalAssignmentIntegrationTest/TemplateMitchellEnvelopePathForUpdateAA";
	private static final String TEMPLATE_ME_NVPAIR_CLAIM_ID = "NVPAIR_CLAIM_ID";
	private static final String TEMPLATE_ME_NVPAIR_EXPOSURE_ID = "NVPAIR_EXPOSURE_ID";
	private static final Double EXPECTED_LATITUDE_ROUNDED = new Double(32.852);
	private static final Double EXPECTED_LONGITUDE_ROUNDED = new Double(-117.18);

	// Member Variables
	private String templateMEForUpdateCallFullPath;

	@Before
	public void setUp() throws Exception {
		this.appraisalAssignmentEJB = AppraisalAssignmentClient
				.getAppraisalAssignmentEJB();

		this.intTestHelper = new IntegrationTestHelperClient();

		this.templateMEForSaveCallFullPath = SystemConfiguration
				.getSettingValue(SYS_CONF_TEMPLATE_MITCHELL_ENVELOPE_PATH_FOR_SAVE_AA);

		this.templateMEForUpdateCallFullPath = SystemConfiguration
				.getSettingValue(TEMPLATE_MITCHELL_ENVELOPE_PATH_FOR_UPDATE_AA);

		this.devUserOrgId = Long.parseLong(SystemConfiguration
				.getSettingValue(SYS_CONF_DEV_USER_ORG_ID));

		this.devClaimNumberFormat = SystemConfiguration
				.getSettingValue(SYS_CONF_DEV_CLAIM_NUMBER_FORMAT_STRING);

		this.testUserUserInfoDoc = getUserInfoForTestUser();

		this.estimatePackageClient = new EstimatePackageClient();
	}

	@Test
	public void happyPath() throws Exception {
		Integer expectedPriority = new Integer(99);
		BigDecimal expectedDurationFraction = new BigDecimal(0);

		// Create the AADTO to call the SaveAA method with
		AppraisalAssignmentDTO aaDTOForSave = createAADTOForSaveCall();

		// Call the save method first. This is required as the update call
		// will need the claim ID, exposure Id, WA Task Id, etc to be created
		// in order to succeed.
		// Call the Save method in the AAS
		AppraisalAssignmentDTO aaDTOAfterSave = appraisalAssignmentEJB
				.saveAppraisalAssignment(aaDTOForSave, this.testUserUserInfoDoc);

		// Create the AADTO for the update call
		AppraisalAssignmentDTO aaDTOForUpdateCall = createAADTOForUpdateCall(aaDTOAfterSave);

		// Call the Update method in the AAS
		AppraisalAssignmentDTO result = appraisalAssignmentEJB
				.updateAppraisalAssignment(aaDTOForUpdateCall,
						this.testUserUserInfoDoc);

		// Assertions on return result from call
		assertNotNull(result);
		assertNotNull(result.getMitchellEnvelopDoc());
		assertTrue(result.getMitchellEnvelopDoc().validate());
		assertEquals(0L, result.getTcn());
		assertEquals(aaDTOAfterSave.getWaTaskId(), result.getWaTaskId());
		assertEquals(aaDTOAfterSave.getClaimId(), result.getClaimId());
		assertEquals(aaDTOAfterSave.getClaimExposureId(),
				result.getClaimExposureId());
		assertEquals(aaDTOAfterSave.getDocumentID(), result.getDocumentID());
		assertEquals("OPENED", result.getStatus());
		assertEquals("DISPATCHED", result.getDisposition());
		assertEquals("N", result.getAssignmentHasBeenUpdate());
		assertFalse(aaDTOForUpdateCall.getAssignmentID() == result
				.getAssignmentID());
		assertEquals(aaDTOAfterSave.getClientClaimNumber(),
				result.getClientClaimNumber());
		assertEquals(aaDTOAfterSave.getWorkItemID(), result.getWorkItemID());
		assertEquals("", result.getEventReasonCode());
		assertEquals("", result.getEventMemo());
		assertNull(result.getEventNameList());
		assertFalse(result.isOriginalAssignment());
		assertEquals("Y", result.getReqAssociateDataCompletedInd());
		assertEquals(1L, result.getWorkAssignmentTcn());
		assertEquals("Pacific Standard Time", result.getTimeZone());
		assertNull(result.getHoldInfo());
		assertFalse(result.getIsSaveFromSSOPage());
		assertFalse(result.getSaveAndSendFlag());
		assertNull(result.getSubType());
		assertNotNull(result.getDuration());
		assertEquals(expectedPriority, result.getPriority());
		assertNotNull(result.getDuration());
		assertEquals(0L, result.getDuration().getDay());
		assertEquals(expectedDurationFraction, result.getDuration()
				.getFraction());
		assertEquals(0L, result.getDuration().getHour());
		assertEquals(5L, result.getDuration().getMinute());
		assertEquals(0L, result.getDuration().getMonth());
		assertEquals(0L, result.getDuration().getSecond());
		assertEquals(1L, result.getDuration().getSign());
		assertEquals(0L, result.getDuration().getYear());

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
		assertEquals("2014-11-20 00:00:00.0", aaBO.getDateOfLoss().toString());
		assertNotNull(aaBO.getDocument());
		assertEquals("test@mitchell.com", aaBO.getEmailAddress());
		assertNotNull(aaBO.getId());
		assertEquals("6220 Greenwich Dr", aaBO.getInspectionAddress1());
		assertNull(aaBO.getInspectionAddress2());
		assertEquals("San Diego", aaBO.getInspectionCity());
		assertEquals("CA", aaBO.getInspectionStateProvince());
		assertEquals("92122", aaBO.getInspectionZipPostalCode());
		assertNotNull("U", aaBO.getIsDrivable());
		assertEquals("858-3687234", aaBO.getLocationPhone());
		assertEquals("SHOP", aaBO.getLocationType());
		assertEquals("INSURED", aaBO.getPrimaryContact());
		assertEquals("first", aaBO.getPrimaryContactFirstName());
		assertEquals("last", aaBO.getPrimaryContactLastName());
		assertNull(aaBO.getPrimaryContactSecondLastName());
		assertEquals("MANUAL", aaBO.getScheduleMethod());
		assertEquals(1L, aaBO.getTcn().longValue());
		assertEquals("Y", aaBO.getTravelRequired());
		assertEquals(this.testUserUserInfoDoc.getUserInfo().getUserID(),
				aaBO.getUpdatedBy());
		assertNotNull(aaBO.getUpdatedDate());
		assertEquals("NNS", aaBO.getUserPreferredMoi());
		assertEquals("Y", aaBO.getValidInspectionAddress());
		assertEquals("Car", aaBO.getVehicleType());
		assertNull(aaBO.getVendorSrc());
		assertNotNull("123-4567890+123", aaBO.getWorkPhone());

		// Verify the latitude and longitude are not null
		assertNotNull(aaBO.getInspectionLatitude());
		assertNotNull(aaBO.getInspectionLongitude());

		// Get the rounded Lat/Long from the Appraisal Assignment Business
		// Object
		Double latitudeRounded = calculateRoundedDoubleWithPrecision(
				aaBO.getInspectionLatitude(), 5);
		Double longitudeRounded = calculateRoundedDoubleWithPrecision(
				aaBO.getInspectionLongitude(), 5);

		// Assertions on the value of the rounded latitude and longitude
		assertEquals(EXPECTED_LATITUDE_ROUNDED, latitudeRounded);
		assertEquals(EXPECTED_LONGITUDE_ROUNDED, longitudeRounded);
	}

	protected String getWorkItemIdFromSaveCallResultAADTO(
			AppraisalAssignmentDTO aaDTOAfterSave) {
		NameValuePairType[] meFromSaveCallNVPairs = aaDTOAfterSave
				.getMitchellEnvelopDoc().getMitchellEnvelope()
				.getEnvelopeContext().getNameValuePairArray();

		String workItemIdFromSaveDTO = null;

		for (NameValuePairType currentNVPair : meFromSaveCallNVPairs) {
			if (currentNVPair.getName().equalsIgnoreCase("MitchellWorkItemId")) {
				workItemIdFromSaveDTO = currentNVPair.getValueArray(0);
				break;
			}
		}

		return workItemIdFromSaveDTO;
	}

	protected AppraisalAssignmentDTO createAADTOForUpdateCall(
			AppraisalAssignmentDTO aaDTOAfterSave) throws Exception {
		Integer priority = new Integer(99);

		// Get data from the AADTO returned from the Save call
		long waTaskId = aaDTOAfterSave.getWaTaskId();
		long claimId = aaDTOAfterSave.getClaimId();
		long claimExposureId = aaDTOAfterSave.getClaimExposureId();
		long documentID = aaDTOAfterSave.getDocumentID();
		String clientClaimNumber = aaDTOAfterSave.getClientClaimNumber();
		String workItemID = aaDTOAfterSave.getWorkItemID();

		// Create the ME doc for the update AADTO
		MitchellEnvelopeDocument updateMEDoc = createTestMitchellEnvelopeForUpdate(aaDTOAfterSave);

		// Create the AADTO for the update call and set all of its fields
		AppraisalAssignmentDTO aaDTOForUpdateCall = new AppraisalAssignmentDTO();

		// SET ALL FIELDS IN THE AADTO for the update call
		aaDTOForUpdateCall.setTcn(0L);
		aaDTOForUpdateCall.setWaTaskId(waTaskId);
		aaDTOForUpdateCall.setClaimId(claimId);
		aaDTOForUpdateCall.setClaimExposureId(claimExposureId);
		aaDTOForUpdateCall.setDocumentID(documentID);
		aaDTOForUpdateCall.setStatus("OPENED");
		aaDTOForUpdateCall.setDisposition("DISPATCHED");
		aaDTOForUpdateCall.setMitchellEnvelopDoc(updateMEDoc);
		aaDTOForUpdateCall.setAssignmentHasBeenUpdate("N");
		aaDTOForUpdateCall.setAssignmentID(0L);
		aaDTOForUpdateCall.setClientClaimNumber(clientClaimNumber);
		aaDTOForUpdateCall.setWorkItemID(workItemID);
		aaDTOForUpdateCall.setEventReasonCode("");
		aaDTOForUpdateCall.setEventMemo("");
		aaDTOForUpdateCall.setEventNameList(null);
		aaDTOForUpdateCall.setOriginalAssignment(true);
		aaDTOForUpdateCall.setReqAssociateDataCompletedInd("N");
		aaDTOForUpdateCall.setWorkAssignmentTcn(0L);
		aaDTOForUpdateCall.setTimeZone("Pacific Standard Time");
		aaDTOForUpdateCall.setHoldInfo(null);
		aaDTOForUpdateCall.setIsSaveFromSSOPage(false);
		aaDTOForUpdateCall.setSaveAndSendFlag(true);
		aaDTOForUpdateCall.setSubType(null);
		aaDTOForUpdateCall.setDuration(null);
		aaDTOForUpdateCall.setPriority(priority);

		return aaDTOForUpdateCall;
	}

	protected MitchellEnvelopeDocument createTestMitchellEnvelopeForUpdate(
			AppraisalAssignmentDTO aaDTOAfterSave) throws Exception {
		// Get the template Mitchell Envelope for the Update call from the
		// resource directory
		String meDocString = getXmlResourceAsString(this.templateMEForUpdateCallFullPath);

		// Retrieve the Work Item Id from the AADTO that was returned from the
		// Save call
		String workItemIdFromSaveDTO = getWorkItemIdFromSaveCallResultAADTO(aaDTOAfterSave);

		// Set the fields in the ME that need to be updated
		meDocString = meDocString
				.replaceAll(TEMPLATE_ME_NVPAIR_MITCHELL_WORK_ITEM_ID,
						workItemIdFromSaveDTO);
		meDocString = meDocString.replaceAll(TEMPLATE_ME_NVPAIR_CLAIM_ID, ""
				+ aaDTOAfterSave.getClaimId());
		meDocString = meDocString.replaceAll(TEMPLATE_ME_NVPAIR_EXPOSURE_ID, ""
				+ aaDTOAfterSave.getClaimExposureId());
		meDocString = meDocString.replaceAll(TEMPLATE_ME_ADD_REQUEST_RQUID,
				UUIDFactory.getInstance().getCeicaUUID());
		meDocString = meDocString.replaceAll(
				TEMPLATE_ME_ADD_REQUEST_CLAIM_NUMBER,
				aaDTOAfterSave.getClientClaimNumber());

		// Parse the ME String into an ME Doc
		MitchellEnvelopeDocument updateMEDoc = MitchellEnvelopeDocument.Factory
				.parse(meDocString);

		return updateMEDoc;
	}

	protected Double calculateRoundedDoubleWithPrecision(Double doubleToRound,
			int precision) {
		// Create the Big Decimal using the provided Double
		BigDecimal latBD = new BigDecimal(doubleToRound.doubleValue());

		// Round the Big Decimal using the provided precision
		latBD = latBD.round(new MathContext(precision));

		// Create a Double out of the rounded double
		Double retVal = new Double(latBD.doubleValue());

		return retVal;
	}

	@After
	public void tearDown() throws Exception {
		this.appraisalAssignmentEJB = null;
		this.intTestHelper = null;
		this.templateMEForSaveCallFullPath = null;
		this.devUserOrgId = 0L;
		this.devClaimNumberFormat = null;
		this.templateMEForUpdateCallFullPath = null;
		this.estimatePackageClient = null;
	}
}
