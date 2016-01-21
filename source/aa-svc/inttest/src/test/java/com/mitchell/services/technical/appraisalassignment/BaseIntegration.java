package com.mitchell.services.technical.appraisalassignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.ejb.AppraisalAssignmentServiceRemote;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelper;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;
import com.mitchell.utils.misc.StringUtilities;
import com.mitchell.utils.misc.UUIDFactory;

public class BaseIntegration {
	// CONSTANTS
	protected static final String SYS_CONF_BASE_PATH = "/AppraisalAssignmentIntegrationTest/";
	protected static final String SYS_CONF_TEMPLATE_MITCHELL_ENVELOPE_PATH_FOR_SAVE_AA = SYS_CONF_BASE_PATH
			+ "TemplateMitchellEnvelopePathForSaveAA";
	protected static final String SYS_CONF_DEV_USER_ORG_ID = SYS_CONF_BASE_PATH
			+ "DevUserOrgId";
	protected static final String SYS_CONF_DEV_CLAIM_NUMBER_FORMAT_STRING = SYS_CONF_BASE_PATH
			+ "DevClaimNumberFormatString";
	protected static final String TEMPLATE_ME_NVPAIR_MITCHELL_WORK_ITEM_ID = "NVPAIR_MITCHELL_WORK_ITEM_ID";
	protected static final String TEMPLATE_ME_ADD_REQUEST_RQUID = "ADD_REQUEST_RQUID";
	protected static final String TEMPLATE_ME_ADD_REQUEST_CLAIM_NUMBER = "ADD_REQUEST_CLAIM_NUMBER";

	// MEMBER VARIABLES
	protected AppraisalAssignmentServiceRemote appraisalAssignmentEJB;
	protected IntegrationTestHelper intTestHelper;
	protected String templateMEForSaveCallFullPath;
	protected long devUserOrgId;
	protected String devClaimNumberFormat;
	protected UserInfoDocument testUserUserInfoDoc;
	EstimatePackageClient estimatePackageClient;

	protected AppraisalAssignmentDTO createAADTOForSaveCall() throws Exception {
		// Generate a random claim number
		String clientClaimNumber = StringUtilities
				.buildRandomizedFormattedString(this.devClaimNumberFormat,
						false, true);

		// Generate a random Work Item Id
		String workItemID = UUIDFactory.getInstance().getCeicaUUID();

		// Generate a random RqUID
		String rqUID = UUIDFactory.getInstance().getCeicaUUID();

		// Create the test Mitchell Envelope
		MitchellEnvelopeDocument mitchellEnvelopDoc = createTestMitchellEnvelopeForSave(
				workItemID, rqUID, clientClaimNumber);

		// Create the AppraisalAssignmentDTO and set all of it's fields
		AppraisalAssignmentDTO aaDTO = new AppraisalAssignmentDTO();

		aaDTO.setTcn(0L);
		aaDTO.setWaTaskId(0L);
		aaDTO.setClaimId(0L);
		aaDTO.setClaimExposureId(0L);
		aaDTO.setDocumentID(0L);
		aaDTO.setStatus("OPENED");
		aaDTO.setDisposition("NOT READY");
		aaDTO.setMitchellEnvelopDoc(mitchellEnvelopDoc);
		aaDTO.setAssignmentHasBeenUpdate("N");
		aaDTO.setAssignmentID(0L);
		aaDTO.setClientClaimNumber(clientClaimNumber);
		aaDTO.setWorkItemID(workItemID);
		aaDTO.setEventReasonCode("");
		aaDTO.setEventMemo("");
		aaDTO.setEventNameList(null);
		aaDTO.setOriginalAssignment(true);
		aaDTO.setReqAssociateDataCompletedInd("N");
		aaDTO.setWorkAssignmentTcn(0L);
		aaDTO.setTimeZone("");
		aaDTO.setHoldInfo(null);
		aaDTO.setIsSaveFromSSOPage(false);
		aaDTO.setSaveAndSendFlag(false);
		aaDTO.setSubType(null);
		aaDTO.setDuration(null);
		aaDTO.setPriority(null);

		return aaDTO;
	}

	protected MitchellEnvelopeDocument createTestMitchellEnvelopeForSave(
			String workItemID, String rqUID, String clientClaimNumber)
			throws Exception {

		// Read the template ME in as a string
		String meString = getXmlResourceAsString(this.templateMEForSaveCallFullPath);

		// Set the fields in the ME that need to be updated
		meString = meString.replaceAll(
				TEMPLATE_ME_NVPAIR_MITCHELL_WORK_ITEM_ID, workItemID);
		meString = meString.replaceAll(TEMPLATE_ME_ADD_REQUEST_RQUID, rqUID);
		meString = meString.replaceAll(TEMPLATE_ME_ADD_REQUEST_CLAIM_NUMBER,
				clientClaimNumber);

		// Parse the ME String into an ME Doc
		MitchellEnvelopeDocument mitchellEnvelopDoc = MitchellEnvelopeDocument.Factory
				.parse(meString);

		return mitchellEnvelopDoc;
	}

	protected String getXmlResourceAsString(String xmlResourceFullPath)
			throws Exception {
		// Create a File for the Xml resource
		File resourceFile = intTestHelper
				.makeFileFromResource(xmlResourceFullPath);

		// Determine the absolute path of the Xml resource File
		String resourceFileAbsolutePath = resourceFile.getAbsolutePath();

		// Read the Xml resource File in as a String
		String meString = loadXMLFileAsString(resourceFileAbsolutePath);

		return meString;
	}

	protected UserInfoDocument getUserInfoForTestUser() throws Exception {
		// Get the User Info EJB
		UserInfoServiceEJBRemote uiEJB = UserInfoClient.getUserInfoEJB();

		// Get the User Info Doc for the test
		UserInfoDocument testUserUserInfoDoc = uiEJB
				.getUserInfo(this.devUserOrgId);

		return testUserUserInfoDoc;
	}

	protected String loadXMLFileAsString(String filepath)
			throws MitchellException {
		String retval = null;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			InputStream inputStream = new FileInputStream(new File(filepath));
			org.w3c.dom.Document doc = documentBuilderFactory
					.newDocumentBuilder().parse(inputStream);
			StringWriter stw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance()
					.newTransformer();
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
			retval = stw.toString();
		} catch (Exception e) {
			throw new MitchellException(this.getClass().getName(),
					"ExternalAccessControlImpl", "Excpetion loading file: "
							+ filepath, e);
		}
		return retval;
	}

}
