package com.mitchell.services.business.partialloss.appraisalassignment.AASTest;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentMandFieldUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentMandFieldUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.AASTest.helper.AASTestHelper;
import com.mitchell.services.business.partialloss.appraisalassignment.AASTest.mandfield.BaseTestMEUpdateHandlerImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.AASTest.mandfield.ClaimaintTestMEUpdateHandlerImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.AASTest.mandfield.InsuredTestMEUpdateHandlerImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.AASTest.mandfield.MandFieldUnsetHandlerImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.AASTest.mandfield.OwnerTestMEUpdateHandlerImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.CustomSettingProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.UserInfoProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtilsImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.util.MitchellEnvelopeHandlerImpl;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;
import com.mitchell.services.core.customsettings.types.xml.SettingValues;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class MandFieldAppraisalAssignmentServiceTest extends MockObjectTestCase {
	static final int SKIP_VALUES = 4;
	//static final String BASE_DIR = MandFieldAppraisalAssignmentServiceTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static final String ME_FILE = AASTestHelper.getResourcePath("ME.XML");
	IAppraisalAssignmentMandFieldUtils appraisalAssignmentMandFieldUtils = null;
	Mock aASCommonUtilsMock = mock(AASCommonUtilsImpl.class);
	IAppraisalAssignmentUtils appraisalAssignmentUtils = new AppraisalAssignmentUtils();
	Mock userInfoProxyMoc = new Mock(UserInfoProxy.class);
	Mock customSettingProxyMoc = new Mock(CustomSettingProxy.class);
	BaseTestMEUpdateHandlerImpl baseMandFieldImpl = null;
	static HashMap MANDATORY_FIELDS = new HashMap(); // testCase and custom setting lookup

	static{
		// Insured
		MANDATORY_FIELDS.put("testInsuredFirstName","CONFIG_INSD_FIRST_NAME");

		MANDATORY_FIELDS.put("testInsuredAddress1","CONFIG_INSD_ADDRESS1");
		MANDATORY_FIELDS.put("testInsuredAddress2","CONFIG_INSD_ADDRESS2");
		MANDATORY_FIELDS.put("testInsuredCity","CONFIG_INSD_CITY");
		MANDATORY_FIELDS.put("testInsuredState","CONFIG_INSD_STATE");
		MANDATORY_FIELDS.put("testInsuredZip","CONFIG_INSD_ZIP");

		MANDATORY_FIELDS.put("testInsuredHomePhone","CONFIG_HOME_PHONE");
		MANDATORY_FIELDS.put("testInsuredWorkPhone","CONFIG_INSD_WORK_PH");
		MANDATORY_FIELDS.put("testInsuredCellPhone","CONFIG_INSD_CELL_PH");
		MANDATORY_FIELDS.put("testInsuredFax","CONFIG_INSD_FAX_PH");
		MANDATORY_FIELDS.put("testInsuredEmail","CONFIG_INSD_EMAIL");
		// Claimaint
		MANDATORY_FIELDS.put("testClaimaintFirstName","CONFIG_CLMT_FIRST_NAME");

		MANDATORY_FIELDS.put("testClaimaintAddress1","CONFIG_CLMT_ADDRESS1");
		MANDATORY_FIELDS.put("testClaimaintAddress2","CONFIG_CLMT_ADDRESS2");
		MANDATORY_FIELDS.put("testClaimaintCity","CONFIG_CLMT_CITY");
		MANDATORY_FIELDS.put("testClaimaintState","CONFIG_CLMT_STATE");
		MANDATORY_FIELDS.put("testClaimaintZip","CONFIG_CLMT_ZIP");

		MANDATORY_FIELDS.put("testClaimaintHomePhone","CONFIG_CLMT_HOME_PH");
		MANDATORY_FIELDS.put("testClaimaintWorkPhone","CONFIG_CLMT_WORK_PH");
		MANDATORY_FIELDS.put("testClaimaintCellPhone","CONFIG_CLMT_CELL_PH");
		MANDATORY_FIELDS.put("testClaimaintFax","CONFIG_CLMT_FAX");
		MANDATORY_FIELDS.put("testClaimaintEmail","CONFIG_CLMT_EMAIL");
		// Owner
		MANDATORY_FIELDS.put("testOwnerFirstName","CONFIG_OWNR_FIRST_NAME");
		MANDATORY_FIELDS.put("testOwnerLastName","CONFIG_OWNR_LAST_NAME");
		MANDATORY_FIELDS.put("testOwnerAddress1","CONFIG_OWNR_ADDRESS1");
		MANDATORY_FIELDS.put("testOwnerAddress2","CONFIG_OWNR_ADDRESS2");
		MANDATORY_FIELDS.put("testOwnerCity","CONFIG_OWNR_CITY");
		MANDATORY_FIELDS.put("testOwnerState","CONFIG_OWNR_STATE");
		MANDATORY_FIELDS.put("testOwnerZip","CONFIG_OWNR_ZIP");

		MANDATORY_FIELDS.put("testOwnerHomePhone","CONFIG_OWNR_HOME_PH");
		MANDATORY_FIELDS.put("testOwnerWorkPhone","CONFIG_OWNR_WORK_PH");
		MANDATORY_FIELDS.put("testOwnerCellPhone","CONFIG_OWNR_CELL_PH");
		MANDATORY_FIELDS.put("testOwnerFax","CONFIG_OWNR_FAX");
		MANDATORY_FIELDS.put("testOwnerEmail","CONFIG_OWNR_EMAIL");

	}
	@Override
	protected void setUp() {
		Properties p = System.getProperties();
		Set  s = p.keySet();
		Iterator it = s.iterator();
		while(it.hasNext()) {
			Object o = it.next();
			System.out.println( o + "::" + p.getProperty((String)o));
		}

		appraisalAssignmentMandFieldUtils = new AppraisalAssignmentMandFieldUtils();
		appraisalAssignmentUtils.setCustomSettingProxy((CustomSettingProxy)customSettingProxyMoc.proxy());
		appraisalAssignmentUtils.setUserInfoProxy((UserInfoProxy)userInfoProxyMoc.proxy());
		appraisalAssignmentMandFieldUtils.setAppraisalAssignmentUtils(appraisalAssignmentUtils);
		appraisalAssignmentMandFieldUtils.setCommonUtils((AASCommonUtilsImpl)aASCommonUtilsMock.proxy());
		aASCommonUtilsMock.expects(atLeastOnce()).method("logError").withAnyArguments().will(returnValue(new MitchellException("AppraisalAssignmentMandFieldUtils","hasAllMandatoryAppraisalAssignmentFields","Error")));
	}

	public void testInsuredFirstName() {
		try {
			final String methodName = "testInsuredFirstName";
			System.out.println("Testing " + methodName + "().." );
			firstNameTesting(methodName,new InsuredTestMEUpdateHandlerImpl());

		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testClaimaintFirstName() {
		try {
			final String methodName = "testClaimaintFirstName";
			System.out.println("Testing " + methodName + "().." );
			firstNameTesting(methodName,new ClaimaintTestMEUpdateHandlerImpl());

		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testOwnerFirstName() {
		try {
			final String methodName = "testOwnerFirstName";
			System.out.println("Testing " + methodName + "().." );
			firstNameTesting(methodName,new OwnerTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}

	public void testInsuredAddress1() {
		try {
			final String methodName = "testInsuredAddress1";
			System.out.println("Testing " + methodName + "().." );
			address1Testing(methodName, new InsuredTestMEUpdateHandlerImpl());

		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testClaimaintAddress1() {
		try {
			final String methodName = "testClaimaintAddress1";
			System.out.println("Testing " + methodName + "().." );
			address1Testing(methodName, new ClaimaintTestMEUpdateHandlerImpl());

		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testOwnerAddress1() {
		try {
			final String methodName = "testOwnerAddress1";
			System.out.println("Testing " + methodName + "().." );
			address1Testing(methodName, new OwnerTestMEUpdateHandlerImpl());

		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testInsuredAddress2() {
		try {
			final String methodName = "testInsuredAddress2";
			System.out.println("Testing " + methodName + "().." );
			address2Testing(methodName, new InsuredTestMEUpdateHandlerImpl());

		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testClaimaintAddress2() {
		try {
			final String methodName = "testClaimaintAddress2";
			System.out.println("Testing " + methodName + "().." );
			address2Testing(methodName, new ClaimaintTestMEUpdateHandlerImpl());

		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testOwnerAddress2() {
		try {
			final String methodName = "testOwnerAddress2";
			System.out.println("Testing " + methodName + "().." );
			address2Testing(methodName, new OwnerTestMEUpdateHandlerImpl());

		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testInsuredCity() {
		try {
			final String methodName = "testInsuredCity";
			System.out.println("Testing " + methodName + "().." );

			cityTesting(methodName, new InsuredTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testClaimaintCity() {
		try {
			final String methodName = "testClaimaintCity";
			System.out.println("Testing " + methodName + "().." );

			cityTesting(methodName, new ClaimaintTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testOwnerCity() {
		try {
			final String methodName = "testOwnerCity";
			System.out.println("Testing " + methodName + "().." );

			cityTesting(methodName, new OwnerTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testInsuredState() {
		try {
			final String methodName = "testInsuredState";
			System.out.println("Testing " + methodName + "().." );

			stateTesting(methodName, new InsuredTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testClaimaintState() {
		try {
			final String methodName = "testClaimaintState";
			System.out.println("Testing " + methodName + "().." );

			stateTesting(methodName, new ClaimaintTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testOwnerState() {
		try {
			final String methodName = "testOwnerState";
			System.out.println("Testing " + methodName + "().." );

			stateTesting(methodName, new OwnerTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testInsuredZip() {
		try {
			final String methodName = "testInsuredZip";
			System.out.println("Testing " + methodName + "().." );

			zipTesting(methodName, new InsuredTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testClaimaintZip() {
		try {
			final String methodName = "testClaimaintZip";
			System.out.println("Testing " + methodName + "().." );

			zipTesting(methodName, new ClaimaintTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testOwnerZip() {
		try {
			final String methodName = "testOwnerZip";
			System.out.println("Testing " + methodName + "().." );

			zipTesting(methodName, new OwnerTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testInsuredHomePhone() {
		try {
			final String methodName = "testInsuredHomePhone";
			System.out.println("Testing " + methodName + "().." );

			homePhoneTesting(methodName, new InsuredTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testClaimaintHomePhone() {
		try {
			final String methodName = "testClaimaintHomePhone";
			System.out.println("Testing " + methodName + "().." );

			homePhoneTesting(methodName, new ClaimaintTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testOwnerHomePhone() {
		try {
			final String methodName = "testOwnerHomePhone";
			System.out.println("Testing " + methodName + "().." );

			homePhoneTesting(methodName, new OwnerTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testInsuredWorkPhone() {
		try {
			final String methodName = "testInsuredWorkPhone";
			System.out.println("Testing " + methodName + "().." );

			workPhoneTesting(methodName, new InsuredTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testClaimaintWorkPhone() {
		try {
			final String methodName = "testClaimaintWorkPhone";
			System.out.println("Testing " + methodName + "().." );

			workPhoneTesting(methodName, new ClaimaintTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testOwnerWorkPhone() {
		try {
			final String methodName = "testOwnerWorkPhone";
			System.out.println("Testing " + methodName + "().." );

			workPhoneTesting(methodName, new  OwnerTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testInsuredCellPhone() {
		try {
			final String methodName = "testInsuredCellPhone";
			System.out.println("Testing " + methodName + "().." );

			cellPhoneTesting(methodName, new  InsuredTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testClaimaintCellPhone() {
		try {
			final String methodName = "testClaimaintCellPhone";
			System.out.println("Testing " + methodName + "().." );

			cellPhoneTesting(methodName, new  ClaimaintTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testOwnerCellPhone() {
		try {
			final String methodName = "testOwnerCellPhone";
			System.out.println("Testing " + methodName + "().." );

			cellPhoneTesting(methodName, new  OwnerTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testInsuredFax() {
		try {
			final String methodName = "testInsuredFax";
			System.out.println("Testing " + methodName + "().." );

			faxTesting(methodName, new  InsuredTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testOwnerFax() {
		try {
			final String methodName = "testOwnerFax";
			System.out.println("Testing " + methodName + "().." );

			faxTesting(methodName, new  OwnerTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testClaimaintFax() {
		try {
			final String methodName = "testClaimaintFax";
			System.out.println("Testing " + methodName + "().." );

			faxTesting(methodName, new  ClaimaintTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testInsuredEmail() {
		try {
			final String methodName = "testInsuredEmail";
			System.out.println("Testing " + methodName + "().." );

			emailTesting(methodName, new  InsuredTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testClaimaintEmail() {
		try {
			final String methodName = "testClaimaintEmail";
			System.out.println("Testing " + methodName + "().." );

			emailTesting(methodName, new  ClaimaintTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testOwnerEmail() {
		try {
			final String methodName = "testOwnerEmail";
			System.out.println("Testing " + methodName + "().." );

			emailTesting(methodName, new  OwnerTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	public void testOwnerLastName() {
		try {
			final String methodName = "testOwnerLastName";
			System.out.println("Testing " + methodName + "().." );

			lastNameTesting(methodName, new  OwnerTestMEUpdateHandlerImpl());
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Cause::" + e.getCause().getMessage());
			fail();
		}
	}
	// Set mandatory fields from custom setting.
	private void mandatoryFieldPresent(String methodName) {
		customSettingProxyMoc.expects(atLeastOnce()).method("getDefaultProfile").withAnyArguments().will(returnValue(com.mitchell.services.core.customsettings.types.xml.Profile.Factory.newInstance() ));
		// Set CustomSettings retrieved
		SettingValues settingValues = com.mitchell.services.core.customsettings.types.xml.SettingValues.Factory.newInstance();
		SettingValue settingValueArr[] = new SettingValue[SKIP_VALUES+1];

		for(int i=0; i<SKIP_VALUES ;i++) {
			SettingValue settingValue = com.mitchell.services.core.customsettings.types.xml.SettingValue.Factory.newInstance();
			settingValueArr[i] = settingValue;

		}

		SettingValue settingValue = com.mitchell.services.core.customsettings.types.xml.SettingValue.Factory.newInstance();
		settingValue.setValue("Y");
		settingValue.setPropertyName((String)MANDATORY_FIELDS.get(methodName));
		settingValueArr[SKIP_VALUES] = settingValue;


		settingValues.setSettingValueArray(settingValueArr);
		customSettingProxyMoc.expects(atLeastOnce()).method("getValuesByGroup").withAnyArguments().will(returnValue(settingValues));
	}

	void firstNameTesting(String methodName,BaseTestMEUpdateHandlerImpl baseMandFieldImpl) throws Exception {
		baseMandFieldImpl.setMandFieldUnsetHandler(new MandFieldUnsetHandlerImpl());
		mandatoryFieldPresent(methodName);

		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.parse(new File(ME_FILE));
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		// Positive test
		assertEquals(result,true);

		meDoc = baseMandFieldImpl.unsetFirstName(meDoc);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		assertEquals(result,false);
	}
	void lastNameTesting(String methodName,BaseTestMEUpdateHandlerImpl baseMandFieldImpl) throws Exception {
		baseMandFieldImpl.setMandFieldUnsetHandler(new MandFieldUnsetHandlerImpl());
		mandatoryFieldPresent(methodName);

		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.parse(new File(ME_FILE));
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		// Positive test
		assertEquals(result,true);

		meDoc = baseMandFieldImpl.unsetLastName(meDoc);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		assertEquals(result,false);
	}

	void address1Testing(String methodName,BaseTestMEUpdateHandlerImpl baseMandFieldImpl) throws Exception{
		baseMandFieldImpl.setMandFieldUnsetHandler(new MandFieldUnsetHandlerImpl());
		mandatoryFieldPresent(methodName);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.parse(new File(ME_FILE));
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		// Positive test
		assertEquals(result,true);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);


		// Negative test
		meDoc = baseMandFieldImpl.unsetAddress1(meDoc);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		assertEquals(result,false);
	}
	void address2Testing(String methodName,BaseTestMEUpdateHandlerImpl baseMandFieldImpl)throws Exception{
		baseMandFieldImpl.setMandFieldUnsetHandler(new MandFieldUnsetHandlerImpl());
		mandatoryFieldPresent(methodName);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.parse(new File(ME_FILE));
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		// Positive test
		assertEquals(result,true);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);


		// Negative test
		meDoc = baseMandFieldImpl.unsetAddress2(meDoc);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		assertEquals(result,false);
	}
	void cityTesting(String methodName,BaseTestMEUpdateHandlerImpl baseMandFieldImpl)throws Exception{
		baseMandFieldImpl.setMandFieldUnsetHandler(new MandFieldUnsetHandlerImpl());
		mandatoryFieldPresent(methodName);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.parse(new File(ME_FILE));
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		// Positive test
		assertEquals(result,true);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);


		// Negative test
		meDoc = baseMandFieldImpl.unsetCity(meDoc);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		assertEquals(result,false);
	}
	void stateTesting(String methodName,BaseTestMEUpdateHandlerImpl baseMandFieldImpl)throws Exception{
		baseMandFieldImpl.setMandFieldUnsetHandler(new MandFieldUnsetHandlerImpl());
		mandatoryFieldPresent(methodName);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.parse(new File(ME_FILE));
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		// Positive test
		assertEquals(result,true);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);


		// Negative test
		meDoc = baseMandFieldImpl.unsetState(meDoc);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		assertEquals(result,false);
	}
	void zipTesting(String methodName,BaseTestMEUpdateHandlerImpl baseMandFieldImpl)throws Exception{
		baseMandFieldImpl.setMandFieldUnsetHandler(new MandFieldUnsetHandlerImpl());
		mandatoryFieldPresent(methodName);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.parse(new File(ME_FILE));
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		// Positive test
		assertEquals(result,true);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);


		// Negative test
		meDoc = baseMandFieldImpl.unsetZip(meDoc);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		assertEquals(result,false);
	}

	void homePhoneTesting(String methodName, BaseTestMEUpdateHandlerImpl baseMandFieldImpl) throws Exception {
		baseMandFieldImpl.setMandFieldUnsetHandler(new MandFieldUnsetHandlerImpl());
		mandatoryFieldPresent(methodName);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.parse(new File(ME_FILE));
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		// Positive test
		assertEquals(result,true);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);


		// Negative test
		// Negative test
		meDoc = baseMandFieldImpl.unsetHomePhone(meDoc);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		assertEquals(result,false);
	}
	void workPhoneTesting(String methodName, BaseTestMEUpdateHandlerImpl baseMandFieldImpl) throws Exception {
		baseMandFieldImpl.setMandFieldUnsetHandler(new MandFieldUnsetHandlerImpl());
		mandatoryFieldPresent(methodName);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.parse(new File(ME_FILE));
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		// Positive test
		assertEquals(result,true);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);


		// Negative test
		// Negative test
		meDoc = baseMandFieldImpl.unsetWorkPhone(meDoc);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		assertEquals(result,false);
	}
	void cellPhoneTesting(String methodName, BaseTestMEUpdateHandlerImpl baseMandFieldImpl) throws Exception {
		baseMandFieldImpl.setMandFieldUnsetHandler(new MandFieldUnsetHandlerImpl());
		mandatoryFieldPresent(methodName);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.parse(new File(ME_FILE));
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		// Positive test
		assertEquals(result,true);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);


		// Negative test
		// Negative test
		meDoc = baseMandFieldImpl.unsetCellPhone(meDoc);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		assertEquals(result,false);
	}
	void faxTesting(String methodName, BaseTestMEUpdateHandlerImpl baseMandFieldImpl) throws Exception {
		baseMandFieldImpl.setMandFieldUnsetHandler(new MandFieldUnsetHandlerImpl());
		mandatoryFieldPresent(methodName);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.parse(new File(ME_FILE));
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		// Positive test
		assertEquals(result,true);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);


		// Negative test
		meDoc = baseMandFieldImpl.unsetFax(meDoc);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		assertEquals(result,false);
	}
	void emailTesting(String methodName, BaseTestMEUpdateHandlerImpl baseMandFieldImpl) throws Exception {
		baseMandFieldImpl.setMandFieldUnsetHandler(new MandFieldUnsetHandlerImpl());
		mandatoryFieldPresent(methodName);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.parse(new File(ME_FILE));
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		boolean result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		// Positive test
		assertEquals(result,true);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);


		// Negative test
		// Negative test
		meDoc = baseMandFieldImpl.unsetEmail(meDoc);
		meHelper = new MitchellEnvelopeHelper(meDoc);
		additionalAppraisalAssignmentInfoDocument =
				AdditionalAppraisalAssignmentInfoDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo")));
		additionalTaskConstraintsDocument =
				AdditionalTaskConstraintsDocument.Factory.parse(meHelper.getEnvelopeBodyContentAsString
						(meHelper.getEnvelopeBody("AdditionalTaskConstraints")));
		//result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meDoc);
		result = appraisalAssignmentMandFieldUtils.hasAllMandatoryAppraisalAssignmentFields(meHelper, additionalAppraisalAssignmentInfoDocument, additionalTaskConstraintsDocument);
		assertEquals(result,false);
	}


}
