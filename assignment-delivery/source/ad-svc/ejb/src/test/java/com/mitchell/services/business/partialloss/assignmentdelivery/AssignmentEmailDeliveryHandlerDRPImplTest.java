package com.mitchell.services.business.partialloss.assignmentdelivery;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jmock.Mock;
import org.mockito.Mockito;

import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.impl.AssignmentEmailDeliveryHandlerDRPImpl;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.SystemConfigProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.XsltTransformer;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.XsltTransformerImpl;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.AssignmentEmailDeliveryUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.CustomSettingHelper;


public class AssignmentEmailDeliveryHandlerDRPImplTest extends
		org.jmock.cglib.MockObjectTestCase {

	private AssignmentEmailDeliveryHandlerDRPImpl handler;
	private Mock customSettingHelper;
	private Mock systemConfigProxy;

	XsltTransformer xsltTransformer = new XsltTransformerImpl();

	private final String xmlFileForAssignmentCreation = "APDDeliveryContext_ForDRPCreationEmail.xml";
	private final String xmlFileForEstimateUpload = "APDDeliveryContext_ForEstimateDRPUploadSuccess.xml";
	private final String testResourcesPath = "src"+File.separator+"test"+File.separator+"resources";

	public AssignmentEmailDeliveryHandlerDRPImplTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(AssignmentEmailDeliveryHandlerDRPImplTest.class);
	}

	protected void setUp() {

		handler = new AssignmentEmailDeliveryHandlerDRPImpl();
		customSettingHelper = new Mock(CustomSettingHelper.class);
		systemConfigProxy = new Mock(SystemConfigProxy.class);

		handler.setCustomSettingHelper((CustomSettingHelper) customSettingHelper.proxy());
		handler.setSystemConfigProxy((SystemConfigProxy) systemConfigProxy
				.proxy());
		handler.setXsltTransformer(xsltTransformer);
		
	}

	public void testCreateEmailMessage4CreationWithCustomSettingValueSet() {

		try {
			String apdcontext = readFileIntoString(this.getClass()
					.getClassLoader()
					.getResourceAsStream(xmlFileForAssignmentCreation));

			APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
					.parse(apdcontext);
			
			systemConfigProxy.expects(atLeastOnce()).method("getStringValue")
					.withAnyArguments().will(returnValue(testResourcesPath));

			customSettingHelper.expects(once())
					.method("getCompanyCustomSetting").withAnyArguments()
					.will(returnValue("fr-CA_en-US"));
			
			AssignmentEmailDeliveryUtils assignmentEmailDeliveryUtils = Mockito.mock(AssignmentEmailDeliveryUtils.class);
			handler.setAssignmentEmailDevlieryUtils(assignmentEmailDeliveryUtils);
			Mockito.doReturn(null).when(assignmentEmailDeliveryUtils).createMessageXml4EmailDRP(apdContextDoc, testResourcesPath, "fr-CA_en-US", true);
			XsltTransformer xsltTransformer = Mockito.mock(XsltTransformer.class);
			handler.setXsltTransformer(xsltTransformer);
			Mockito.doReturn("someMessage").when(xsltTransformer).transformXmlString(Mockito.anyString(), Mockito.anyString());
			String emailMessage = handler.createEmailMessage4Creation(
					apdContextDoc, "not required");
			assertNotNull("transformed mail is null", emailMessage);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception in AssignmentEmailDeliveryHandlerTest");
		}
	}

	public void testCreateEmailMessage4CreationWithNoCustomSettingSetAndUseDefaultValue() {

		try {
			String apdcontext = readFileIntoString(this.getClass()
					.getClassLoader()
					.getResourceAsStream(xmlFileForAssignmentCreation));

			APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
					.parse(apdcontext);

			systemConfigProxy.expects(atLeastOnce()).method("getStringValue")
					.withAnyArguments().will(returnValue(testResourcesPath));

			customSettingHelper.expects(once())
					.method("getCompanyCustomSetting").withAnyArguments()
					.will(returnValue(null));
			AssignmentEmailDeliveryUtils assignmentEmailDeliveryUtils = Mockito.mock(AssignmentEmailDeliveryUtils.class);
			handler.setAssignmentEmailDevlieryUtils(assignmentEmailDeliveryUtils);
			Mockito.doReturn(null).when(assignmentEmailDeliveryUtils).createMessageXml4EmailDRP(apdContextDoc, testResourcesPath, "fr-CA_en-US", true);
			XsltTransformer xsltTransformer = Mockito.mock(XsltTransformer.class);
			handler.setXsltTransformer(xsltTransformer);
			Mockito.doReturn("someMessage").when(xsltTransformer).transformXmlString(Mockito.anyString(), Mockito.anyString());
			String emailMessage = handler.createEmailMessage4Creation(
					apdContextDoc, "not required");
			assertNotNull("transformed mail is null", emailMessage);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception in AssignmentEmailDeliveryHandlerTest");
		}
	}

	public void testCreateEmailSubject4CreationWithCustomSettingValueSet() {

		try {
			String apdcontext = readFileIntoString(this.getClass()
					.getClassLoader()
					.getResourceAsStream(xmlFileForAssignmentCreation));

			APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
					.parse(apdcontext);

			systemConfigProxy.expects(atLeastOnce()).method("getStringValue")
					.withAnyArguments().will(returnValue(testResourcesPath));

			customSettingHelper.expects(once())
					.method("getCompanyCustomSetting").withAnyArguments()
					.will(returnValue("fr-CA_en-US"));

			String subject = handler.createEmailSubject4Creation(apdContextDoc,
					"not required");
			assertNotNull("transformed mail is null", subject);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception in AssignmentEmailDeliveryHandlerTest");
		}
	}
	
	
	public void testCreateEmailSubject4CreationWithCustomSettingValueSetFrench() {

		try {
			String apdcontext = readFileIntoString(this.getClass()
					.getClassLoader()
					.getResourceAsStream(xmlFileForAssignmentCreation));

			APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
					.parse(apdcontext);

			systemConfigProxy.expects(atLeastOnce()).method("getStringValue")
					.withAnyArguments().will(returnValue(testResourcesPath));

			customSettingHelper.expects(once())
					.method("getCompanyCustomSetting").withAnyArguments()
					.will(returnValue("fr-ca_en-us"));

			String subject = handler.createEmailSubject4NonDrpSuppEmail(apdContextDoc, "fr-ca_en-us");
			assertNotNull("transformed mail is null", subject);
			assertTrue(subject.contains("Nouvelle affectation de supplément"));

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception in AssignmentEmailDeliveryHandlerTest");
		}
	}
	
	
	public void testCreateEmailMessage4CreationWithNoCustomSettingSetAndUseDefaultValueEnglish() {

		try {
			String apdcontext = readFileIntoString(this.getClass()
					.getClassLoader()
					.getResourceAsStream(xmlFileForAssignmentCreation));

			APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
					.parse(apdcontext);

			systemConfigProxy.expects(atLeastOnce()).method("getStringValue")
					.withAnyArguments().will(returnValue(testResourcesPath));

			customSettingHelper.expects(once())
					.method("getCompanyCustomSetting").withAnyArguments()
					.will(returnValue("en-US"));

			String subject = handler.createEmailSubject4NonDrpSuppEmail(apdContextDoc, "en-US");
			assertNotNull("transformed mail is null", subject);
			assertTrue(subject.contains("New Supplement Assignment from"));

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception in AssignmentEmailDeliveryHandlerTest");
		}
	}
	
	
	public void testCreateEmailSubject4CreationWithNoCustomSettingSetAndUseDefaultValue() {

		try {
			String apdcontext = readFileIntoString(this.getClass()
					.getClassLoader()
					.getResourceAsStream(xmlFileForAssignmentCreation));

			APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
					.parse(apdcontext);

			systemConfigProxy.expects(atLeastOnce()).method("getStringValue")
					.withAnyArguments().will(returnValue(testResourcesPath));

			customSettingHelper.expects(once())
					.method("getCompanyCustomSetting").withAnyArguments()
					.will(returnValue(null));

			String subject = handler.createEmailSubject4Creation(apdContextDoc,
					"not required");
			assertNotNull("transformed mail is null", subject);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception in AssignmentEmailDeliveryHandlerTest");
		}
	}

	/*
	 * Below testcases are move to newly added testClass with Mocktio.
	 */
	/*public void testCreateEmailMessage4EstimateUploadWithCustomSettingValueSet() {

		try {
			
			String apdcontext = readFileIntoString(this.getClass()
					.getClassLoader()
					.getResourceAsStream(xmlFileForEstimateUpload));

			APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
					.parse(apdcontext);

			systemConfigProxy.expects(atLeastOnce()).method("getStringValue")
					.withAnyArguments().will(returnValue(testResourcesPath));

			customSettingHelper.expects(once())
					.method("getCompanyCustomSetting").withAnyArguments()
					.will(returnValue("fr-CA_en-US"));
			
			
			String emailMessage = handler.createEmailMessage4UploadSuccess(
					apdContextDoc, "fr-CA_en-US");
			

			assertNotNull("transformed mail is null", emailMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception in AssignmentEmailDeliveryHandlerTest");
		}
	}*/
	
	/*
	 * Below testcases are move to newly added testClass with Mocktio.
	 */

	/*public void testCreateEmailMessage4EstimateUploadWithNoCustomSettingSetAndUseDefaultValue() {

		try {
			String apdcontext = readFileIntoString(this.getClass()
					.getClassLoader()
					.getResourceAsStream(xmlFileForEstimateUpload));

			APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
					.parse(apdcontext);

			systemConfigProxy.expects(atLeastOnce()).method("getStringValue")
					.withAnyArguments().will(returnValue(testResourcesPath));

			customSettingHelper.expects(once())
					.method("getCompanyCustomSetting").withAnyArguments()
					.will(returnValue(null));

			String emailMessage = handler.createEmailMessage4UploadSuccess(
					apdContextDoc, "not required");
			assertNotNull("transformed mail is null", emailMessage);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception in AssignmentEmailDeliveryHandlerTest");
		}
	}*/

	public void testCreateEmailSubject4EstimateUploadWithCustomSettingValueSet() {

		try {
			String apdcontext = readFileIntoString(this.getClass()
					.getClassLoader()
					.getResourceAsStream(xmlFileForEstimateUpload));

			APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
					.parse(apdcontext);

			systemConfigProxy.expects(atLeastOnce()).method("getStringValue")
					.withAnyArguments().will(returnValue(testResourcesPath));

			customSettingHelper.expects(once())
					.method("getCompanyCustomSetting").withAnyArguments()
					.will(returnValue("fr-CA_en-US"));

			String emailMessage = handler.createEmailSubject4UploadSuccess(
					apdContextDoc, "fr-CA_en-US");
			assertNotNull("transformed mail is null", emailMessage);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception in AssignmentEmailDeliveryHandlerTest");
		}
	}

	public void testCreateEmailSubject4EstimateUploadNoCustomSettingSetAndUseDefaultValue() {

		try {
			String apdcontext = readFileIntoString(this.getClass()
					.getClassLoader()
					.getResourceAsStream(xmlFileForEstimateUpload));

			APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
					.parse(apdcontext);

			systemConfigProxy.expects(atLeastOnce()).method("getStringValue")
					.withAnyArguments().will(returnValue(testResourcesPath));

			customSettingHelper.expects(once())
					.method("getCompanyCustomSetting").withAnyArguments()
					.will(returnValue(null));

			String emailMessage = handler.createEmailSubject4UploadSuccess(
					apdContextDoc, "fr-CA_en-US");
			assertNotNull("transformed mail is null", emailMessage);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception in AssignmentEmailDeliveryHandlerTest");
		}
	}

	private static String readFileIntoString(InputStream inputStream)
			throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuffer strMsg = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			strMsg.append(line);
		}
		return strMsg.toString();
	}

}
