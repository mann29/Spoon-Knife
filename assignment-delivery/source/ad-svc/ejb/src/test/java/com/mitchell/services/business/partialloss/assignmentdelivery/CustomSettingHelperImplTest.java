package com.mitchell.services.business.partialloss.assignmentdelivery;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.assignmentdelivery.AssignmentDeliveryEmailReqType;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.CustomSettingHelperImpl;
import com.mitchell.services.core.customsettings.ejb.CustomSettingsEJBRemote;
import com.mitchell.services.core.customsettings.types.xml.Profile;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;

public class CustomSettingHelperImplTest {

	private CustomSettingHelperImpl customSettingHelperImpl;
	private static final String coCode = "HT";
	private static final String groupName = AssignmentDeliveryConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME;
	private static final String settingName = AssignmentDeliveryConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME;
	private static final String userId = "userid";
	private CustomSettingsEJBRemote ejb;

	@Before
	public void setUp() throws Exception {

		ejb = Mockito.mock(CustomSettingsEJBRemote.class);
		customSettingHelperImpl = new CustomSettingHelperImpl();
		customSettingHelperImpl.setEjb(ejb);
	}

	@Test
	public void testGetCompanyCustomSettingTestWithValidInput()
			throws Exception {

		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);

		Mockito.when(ejb.getDefaultProfile(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE))
				.thenReturn(profile);

		SettingValue value = SettingValue.Factory.newInstance();
		value.setValue("SettingValue");

		Mockito.when(
				ejb.getValue(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE, profile.getId(),
						groupName, settingName)).thenReturn(value);

		String CompanyCustomSetting = customSettingHelperImpl
				.getCompanyCustomSetting(coCode, groupName, settingName);
		Assert.assertNotNull(CompanyCustomSetting);

		Mockito.verify(ejb).getDefaultProfile(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE);
		Mockito.verify(ejb).getValue(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE,
				profile.getId(), groupName, settingName);

	}

	@Test(expected = MitchellException.class)
	public void testGetCompanyCustomSettingTestWithNullProfile()
			throws Exception {

		Mockito.when(ejb.getDefaultProfile(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE))
				.thenReturn(null);

		SettingValue value = SettingValue.Factory.newInstance();
		value.setValue("SettingValue");

		String CompanyCustomSetting = customSettingHelperImpl
				.getCompanyCustomSetting(coCode, groupName, settingName);
		
		Assert.assertNull(CompanyCustomSetting);

		Mockito.verify(ejb).getDefaultProfile(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE);
		
		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);
		Mockito.verify(ejb).getValue(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE,
				profile.getId(), groupName, settingName);

	}

	@Test
	public void testGetCompanyCustomSettingTestWithNullSettingValue()
			throws Exception {

		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);

		Mockito.when(ejb.getDefaultProfile(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE))
				.thenReturn(profile);

		SettingValue value = null;

		Mockito.when(
				ejb.getValue(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE, profile.getId(),
						groupName, settingName)).thenReturn(value);

		String CompanyCustomSetting = customSettingHelperImpl
				.getCompanyCustomSetting(coCode, groupName, settingName);

		Assert.assertNull(CompanyCustomSetting);

		Mockito.verify(ejb).getDefaultProfile(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE);
		Mockito.verify(ejb).getValue(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE,
				profile.getId(), groupName, settingName);

	}

	@Test(expected = MitchellException.class)
	public void testGetCompanyCustomSettingTestWithException() throws Exception {

		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);

		Mockito.when(ejb.getDefaultProfile(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE))
				.thenReturn(profile);
		
		SettingValue value = SettingValue.Factory.newInstance();
		value.setValue("SettingValue");

		Mockito.when(
				ejb.getValue(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE, profile.getId(),
						groupName, settingName)).thenThrow(
				MitchellException.class);

		String CompanyCustomSetting = customSettingHelperImpl
				.getCompanyCustomSetting(coCode, groupName, settingName);

		Mockito.verify(ejb).getDefaultProfile(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE);
		Mockito.verify(ejb).getValue(coCode, coCode, AssignmentDeliveryConstants.COMPANY_TYPE,
				profile.getId(), groupName, settingName);
	}
	
	
	@Test
	public void testGetUserCustomSettingTestWithValidInput()
			throws Exception {

		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);

		Mockito.when(ejb.getDefaultProfile(userId, coCode, AssignmentDeliveryConstants.USER_TYPE))
				.thenReturn(profile);

		SettingValue value = SettingValue.Factory.newInstance();
		value.setValue("SettingValue");

		Mockito.when(
				ejb.getValue(userId, coCode, AssignmentDeliveryConstants.USER_TYPE, profile.getId(),
						groupName, settingName)).thenReturn(value);

		String CompanyCustomSetting = customSettingHelperImpl
				.getUserCustomSetting(userId, coCode, groupName, settingName);
		Assert.assertNotNull(CompanyCustomSetting);

		Mockito.verify(ejb).getDefaultProfile(userId, coCode, AssignmentDeliveryConstants.USER_TYPE);
		Mockito.verify(ejb).getValue(userId, coCode, AssignmentDeliveryConstants.USER_TYPE, profile.getId(),
				groupName, settingName);

	}

	@Test(expected = MitchellException.class)
	public void testGetUserCustomSettingTestWithNullProfile()
			throws Exception {

		Mockito.when(ejb.getDefaultProfile(userId, coCode, AssignmentDeliveryConstants.USER_TYPE))
		.thenReturn(null);


		SettingValue value = SettingValue.Factory.newInstance();
		value.setValue("SettingValue");

		String CompanyCustomSetting = customSettingHelperImpl
				.getUserCustomSetting(userId, coCode, groupName, settingName);
		
		Assert.assertNull(CompanyCustomSetting);

		Mockito.verify(ejb).getDefaultProfile(userId, coCode, AssignmentDeliveryConstants.USER_TYPE);
		
		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);
		Mockito.verify(ejb).getValue(userId, coCode, AssignmentDeliveryConstants.USER_TYPE,
				profile.getId(), groupName, settingName);

	}

	@Test
	public void testGetUserCustomSettingTestWithNullSettingValue()
			throws Exception {

		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);

		Mockito.when(ejb.getDefaultProfile(userId, coCode, AssignmentDeliveryConstants.USER_TYPE))
				.thenReturn(profile);

		SettingValue value = null;

		Mockito.when(
				ejb.getValue(userId, coCode, AssignmentDeliveryConstants.USER_TYPE, profile.getId(),
						groupName, settingName)).thenReturn(value);

		String CompanyCustomSetting = customSettingHelperImpl
				.getUserCustomSetting(userId, coCode, groupName, settingName);

		Assert.assertNull(CompanyCustomSetting);

		Mockito.verify(ejb).getDefaultProfile(userId, coCode, AssignmentDeliveryConstants.USER_TYPE);
		Mockito.verify(ejb).getValue(userId, coCode, AssignmentDeliveryConstants.USER_TYPE,
				profile.getId(), groupName, settingName);

	}

	@Test(expected = MitchellException.class)
	public void testGetUserCustomSettingTestWithException() throws Exception {

		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);

		Mockito.when(ejb.getDefaultProfile(userId, coCode, AssignmentDeliveryConstants.USER_TYPE))
				.thenReturn(profile);
		
		SettingValue value = SettingValue.Factory.newInstance();
		value.setValue("SettingValue");

		Mockito.when(
				ejb.getValue(userId, coCode, AssignmentDeliveryConstants.USER_TYPE, profile.getId(),
						groupName, settingName)).thenThrow(
				MitchellException.class);

		String CompanyCustomSetting = customSettingHelperImpl
				.getUserCustomSetting(userId, coCode, groupName, settingName);

		Mockito.verify(ejb).getDefaultProfile(userId, coCode, AssignmentDeliveryConstants.USER_TYPE);
		Mockito.verify(ejb).getValue(userId, coCode, AssignmentDeliveryConstants.USER_TYPE,
				profile.getId(), groupName, settingName);
	}
	
	@Test
	public void testGetCustomSettingTest() throws Exception {

		Profile profile = Profile.Factory.newInstance();
		profile.setId(12345);

		Mockito.when(ejb.getDefaultProfile(userId, coCode, AssignmentDeliveryConstants.USER_TYPE))
				.thenReturn(profile);

		SettingValue value = SettingValue.Factory.newInstance();
		value.setValue("SettingValue");

		Mockito.when(
				ejb.getValue(userId, coCode, AssignmentDeliveryConstants.USER_TYPE, profile.getId(),
						groupName, settingName)).thenReturn(value);

		String CompanyCustomSetting = customSettingHelperImpl
				.getUserCustomSetting(userId, coCode, groupName, settingName);
		Assert.assertNotNull(CompanyCustomSetting);

		Mockito.verify(ejb).getDefaultProfile(userId, coCode, AssignmentDeliveryConstants.USER_TYPE);
		Mockito.verify(ejb).getValue(userId, coCode, AssignmentDeliveryConstants.USER_TYPE, profile.getId(),
				groupName, settingName);
	}
	
	
}
