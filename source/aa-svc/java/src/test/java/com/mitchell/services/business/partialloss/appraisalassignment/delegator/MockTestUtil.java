package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlCursor.TokenType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.PersonNameType;
import com.cieca.bms.VehicleDamageEstimateAddRqDocument;
import com.cieca.bms.VehicleDamageEstimateAddRqDocument.VehicleDamageEstimateAddRq;
import com.cieca.bms.VehicleDescType;
import com.cieca.bms.VehicleInfoType;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.technical.claim.dao.vo.ClaimExposure;
import com.mitchell.services.technical.claim.dao.vo.ExposureRole;
import com.mitchell.services.technical.claim.dao.vo.Party;
import com.mitchell.services.technical.partialloss.estimate.bo.Document;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.services.technical.partialloss.estimate.bo.EstimateXmlEstimate;

public class MockTestUtil {

	/**
	 * @param dataNode
	 * @return
	 * @throws Exception
	 */
	public static String extractPayload(XmlObject dataNode) throws Exception {

		String payload = null;
		try {
			// Set output option to drop xml fragment enclosing tag of XMLBeans
			XmlOptions xmlOptions = new XmlOptions();
			xmlOptions.setSaveOuter();
			XmlCursor cursor = dataNode.newCursor();
			// go to first child element node\
			cursor.toFirstChild();
			while (cursor.hasNextToken()
					&& cursor.currentTokenType().intValue() != TokenType.INT_START) {
				cursor.toNextToken();
			}
			payload = cursor.xmlText(xmlOptions);
		} catch (Exception ex) {
			throw ex;
		}
		return payload;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public static Document estimateDetails() throws Exception {

		Document doc = null;
		try {
			doc = new Document();
			doc.setDocStatusCode("");
			Estimate estimate = new Estimate();
			estimate.setCreatedBy("OAAKST01");
			estimate.setExposureNumber("1");
			estimate.setClaimNumber("AS01APR1306-");
			estimate.setClientClaimNumber("AS01APR1306-01");
			estimate.setBusinessPartnerId(new Long("12345"));
			estimate.setSupplementNumber(new Long(1));
			doc.setEstimate(estimate);

		} catch (Exception ex) {
			throw ex;
		}
		return doc;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public static Estimate getEstimate() throws Exception {

		VehicleDamageEstimateAddRqDocument bmsDoc = VehicleDamageEstimateAddRqDocument.Factory
				.newInstance();
		VehicleDamageEstimateAddRq bmsReq = bmsDoc
				.addNewVehicleDamageEstimateAddRq();

		bmsDoc.addNewVehicleDamageEstimateAddRq().addNewClaimInfo()
				.setClaimNum("");
		VehicleInfoType vehicleInfo = bmsReq.addNewVehicleInfo();
		VehicleDescType vehicleDesc = vehicleInfo.addNewVehicleDesc();
		// vehicleDesc.setModelYear(arg0);
		vehicleDesc.setMakeDesc("HYUNDAI");
		vehicleDesc.setModelName("I10");

		// bmsReq.getAdminInfo().getClaimant().getParty().getPersonInfo().getPersonName().getFirstName();
		PersonNameType personType = bmsReq.addNewAdminInfo().addNewClaimant()
				.addNewParty().addNewPersonInfo().addNewPersonName();
		personType.setFirstName("First");
		personType.setLastName("Last");

		Estimate est = new Estimate();
		est.setEstimateNetTotal(new BigDecimal("1234"));

		EstimateXmlEstimate estimateXmlEstimate = new EstimateXmlEstimate();
		estimateXmlEstimate.setXmlData(bmsDoc.toString());
		est.setEstimateXmlEstimate(estimateXmlEstimate);
		est.setBusinessPartnerId(379703L);
		
		return est;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument() throws Exception {
		Estimate est = getEstimate();
		Document doc = new Document();
		doc.setEstimate(est);
		return doc;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public static Estimate getEstimateWithVehicleDetails() throws Exception {

		VehicleDamageEstimateAddRqDocument bmsDoc = VehicleDamageEstimateAddRqDocument.Factory
				.newInstance();
		VehicleDamageEstimateAddRq bmsReq = bmsDoc
				.addNewVehicleDamageEstimateAddRq();
		bmsReq.addNewClaimInfo().setClaimNum("1234");
		VehicleInfoType vehicleInfo = bmsReq.addNewVehicleInfo();
		VehicleDescType vehicleDesc = vehicleInfo.addNewVehicleDesc();
		Calendar cal = Calendar.getInstance();
		vehicleDesc.setModelYear(cal);
		vehicleDesc.setMakeDesc("HYUNDAI");
		vehicleDesc.setModelName("I10");

		Estimate est = new Estimate();
		est.setEstimateNetTotal(new BigDecimal("1234"));
		EstimateXmlEstimate estimateXmlEstimate = new EstimateXmlEstimate();
		estimateXmlEstimate.setXmlData(extractPayload(bmsDoc));
		est.setEstimateXmlEstimate(estimateXmlEstimate);

		return est;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public static UserInfoDocument getUserInfoDoc() throws Exception {

		File file = new File("src/test/resources/userinfo.xml");
		UserInfoDocument userInfoDoc = UserInfoDocument.Factory.parse(file);
		return userInfoDoc;

	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public static AssignmentAddRqDocument getAppraisalAssignmentBMSDoc(String filePath) throws Exception {

		File file = new File(filePath);
		AssignmentAddRqDocument assignmentAddRqDocument = AssignmentAddRqDocument.Factory.parse(file);
		return assignmentAddRqDocument;

	}

	public static ClaimExposure getClaimExposure() throws Exception {
		ClaimExposure claimExposure = new ClaimExposure();

		Party claimantParty = new Party();
		claimantParty.setFirstName("Abc");
		claimantParty.setLastName("Xyz");

		Party insuredParty = new Party();
		insuredParty.setFirstName("Efg");
		insuredParty.setLastName("Hij");

		ExposureRole exposureRole1 = new ExposureRole();
		exposureRole1.setExposureRoleType(1);
		exposureRole1.setParty(claimantParty);

		ExposureRole exposureRole2 = new ExposureRole();
		exposureRole2.setExposureRoleType(2);
		exposureRole2.setParty(insuredParty);

		Set exposureRoleSet = new HashSet();
		exposureRoleSet.add(exposureRole1);
		exposureRoleSet.add(exposureRole2);
		claimExposure.setExposureRoleSet(exposureRoleSet);
		return claimExposure;
	}
	
	public static ClaimExposure getClaimExposureMock() throws Exception {
		ClaimExposure claimExposure = new ClaimExposure();

		Party claimantParty = new Party();
		claimantParty.setFirstName("Abc");
		claimantParty.setLastName("Xyz");

		Party insuredParty = new Party();
		insuredParty.setFirstName("Efg");
		insuredParty.setLastName("Hij");

		ExposureRole exposureRole1 = new ExposureRole();
		exposureRole1.setExposureRoleType(1);
		exposureRole1.setParty(claimantParty);

		ExposureRole exposureRole2 = new ExposureRole();
		exposureRole2.setExposureRoleType(2);
		exposureRole2.setParty(insuredParty);

		Set exposureRoleSet = new HashSet();
		exposureRoleSet.add(exposureRole1);
		exposureRoleSet.add(exposureRole2);
		claimExposure.setExposureRoleSet(exposureRoleSet);
		return claimExposure;
	}

	private static String readFileAsString(String filePath)
	throws java.io.IOException {
		// determine the number of characters in file
		int fileLength = (int) new java.io.File(filePath).length();
		// get an empty byte array of number of characters
		// in given file as length
		byte[] buffer = new byte[fileLength];
		// create an input stream for given file
		InputStream f = new FileInputStream(filePath);
		// read stream to byte array
		f.read(buffer);
		// get string value out of byte array
		return new String(buffer);
	}

}
