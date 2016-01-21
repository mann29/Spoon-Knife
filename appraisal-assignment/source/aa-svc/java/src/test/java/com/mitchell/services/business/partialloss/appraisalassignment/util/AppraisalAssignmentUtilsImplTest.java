package com.mitchell.services.business.partialloss.appraisalassignment.util;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.CustomSettingProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.CustomSettingProxyImpl;
import com.mitchell.services.core.customsettings.types.xml.Profile;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;

@RunWith(MockitoJUnitRunner.class)
public class AppraisalAssignmentUtilsImplTest {

	
	private IAppraisalAssignmentUtils testClass;
	

	private CustomSettingProxy ejb;
	String coCode = "L4";
	private static final String userId = "userid";
	private static final String groupName = AppraisalAssignmentConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME;
	private static final String settingName = AppraisalAssignmentConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME;
	@Before
	public void setUp() throws Exception {

		ejb = Mockito.mock(CustomSettingProxyImpl.class);
		testClass = new AppraisalAssignmentUtils();
		testClass.setCustomSettingProxy(ejb);
		
	}


	@Test
	public void testGetUserCustomSettingTestWithValidInput()
			throws Exception {

		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);

		Mockito.when(ejb.getDefaultProfile(userId, coCode, AppraisalAssignmentConstants.COMPANY_TYPE))
				.thenReturn(profile);

		SettingValue value = SettingValue.Factory.newInstance();
		value.setValue("SettingValue");

		Mockito.when(
				ejb.getValue(userId, coCode, AppraisalAssignmentConstants.COMPANY_TYPE, profile.getId(),
						groupName, settingName)).thenReturn(value);

		String CompanyCustomSetting = testClass.retrieveCustomSettings(userId, coCode, groupName, settingName);
				
		Assert.assertNotNull(CompanyCustomSetting);

		Mockito.verify(ejb).getDefaultProfile(userId, coCode, AppraisalAssignmentConstants.COMPANY_TYPE);
		Mockito.verify(ejb).getValue(userId, coCode, AppraisalAssignmentConstants.COMPANY_TYPE, profile.getId(),
				groupName, settingName);

	}

	@Test(expected = MitchellException.class)
	public void testGetUserCustomSettingTestWithNullProfile()
			throws Exception {

		Mockito.when(ejb.getDefaultProfile(userId, coCode, AppraisalAssignmentConstants.USER_TYPE))
		.thenReturn(null);


		SettingValue value = SettingValue.Factory.newInstance();
		value.setValue("SettingValue");

		String CompanyCustomSetting = testClass.retrieveCustomSettings(userId, coCode, groupName, settingName);
		
		Assert.assertNull(CompanyCustomSetting);

		Mockito.verify(ejb).getDefaultProfile(userId, coCode, AppraisalAssignmentConstants.USER_TYPE);
		
		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);
		Mockito.verify(ejb).getValue(userId, coCode, AppraisalAssignmentConstants.USER_TYPE,
				profile.getId(), groupName, settingName);

	}

	@Test
	public void testGetUserCustomSettingTestWithNullSettingValue()
			throws Exception {

		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);

		Mockito.when(ejb.getDefaultProfile(userId, coCode, AppraisalAssignmentConstants.COMPANY_TYPE))
				.thenReturn(profile);

		SettingValue value = null;

		Mockito.when(
				ejb.getValue(userId, coCode, AppraisalAssignmentConstants.COMPANY_TYPE, profile.getId(),
						groupName, settingName)).thenReturn(value);

		String CompanyCustomSetting = testClass.retrieveCustomSettings(userId, coCode, groupName, settingName);

		Assert.assertNull(CompanyCustomSetting);

		Mockito.verify(ejb).getDefaultProfile(userId, coCode, AppraisalAssignmentConstants.COMPANY_TYPE);
		Mockito.verify(ejb).getValue(userId, coCode, AppraisalAssignmentConstants.COMPANY_TYPE,
				profile.getId(), groupName, settingName);

	}

	@Test(expected = MitchellException.class)
	public void testGetUserCustomSettingTestWithException() throws Exception {

		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);

		Mockito.when(ejb.getDefaultProfile(userId, coCode, AppraisalAssignmentConstants.USER_TYPE))
				.thenReturn(profile);
		
		SettingValue value = SettingValue.Factory.newInstance();
		value.setValue("SettingValue");

		Mockito.when(
				ejb.getValue(userId, coCode, AppraisalAssignmentConstants.USER_TYPE, profile.getId(),
						groupName, settingName)).thenThrow(
				MitchellException.class);

		String CompanyCustomSetting = testClass.retrieveCustomSettings(userId, coCode, groupName, settingName);

		Mockito.verify(ejb).getDefaultProfile(userId, coCode, AppraisalAssignmentConstants.USER_TYPE);
		Mockito.verify(ejb).getValue(userId, coCode, AppraisalAssignmentConstants.USER_TYPE,
				profile.getId(), groupName, settingName);
	}
	
	@Test
	public void testGetCustomSettingTest() throws Exception {

		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);

		Mockito.when(ejb.getDefaultProfile(userId, coCode, AppraisalAssignmentConstants.USER_TYPE))
				.thenReturn(profile);

		SettingValue value = SettingValue.Factory.newInstance();
		value.setValue("SettingValue");

		Mockito.when(
				ejb.getValue(userId, coCode, AppraisalAssignmentConstants.USER_TYPE, profile.getId(),
						groupName, settingName)).thenReturn(value);

		String CompanyCustomSetting = testClass.getCustomSettingValue(coCode, userId, groupName, settingName);
				
		Assert.assertNotNull(CompanyCustomSetting);

		Mockito.verify(ejb).getDefaultProfile(userId, coCode, AppraisalAssignmentConstants.USER_TYPE);
		Mockito.verify(ejb).getValue(userId, coCode, AppraisalAssignmentConstants.USER_TYPE, profile.getId(),
				groupName, settingName);
	}
	

	@Test
	public void testGetCustomSettingTestNullUserID() throws Exception {

		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);

		Mockito.when(ejb.getDefaultProfile(coCode, coCode, AppraisalAssignmentConstants.COMPANY_TYPE))
				.thenReturn(profile);

		SettingValue value = SettingValue.Factory.newInstance();
		value.setValue("SettingValue");

		Mockito.when(
				ejb.getValue(coCode, coCode, AppraisalAssignmentConstants.COMPANY_TYPE, profile.getId(),
						groupName, settingName)).thenReturn(value);

		String CompanyCustomSetting = testClass.getCustomSettingValue(coCode, null, groupName, settingName);
				
		Assert.assertNotNull(CompanyCustomSetting);

		Mockito.verify(ejb).getDefaultProfile(coCode, coCode, AppraisalAssignmentConstants.COMPANY_TYPE);
		Mockito.verify(ejb).getValue(coCode, coCode, AppraisalAssignmentConstants.COMPANY_TYPE, profile.getId(),
				groupName, settingName);
	}

	@After
	public void tearDown() throws Exception {
	}
}
