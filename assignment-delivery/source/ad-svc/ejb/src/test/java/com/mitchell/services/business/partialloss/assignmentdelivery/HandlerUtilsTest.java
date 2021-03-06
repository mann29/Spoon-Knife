package com.mitchell.services.business.partialloss.assignmentdelivery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.HandlerUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.CustomSettingHelper;
import com.mitchell.systemconfiguration.SystemConfiguration;


@RunWith(PowerMockRunner.class)
@PrepareForTest({HandlerUtils.class})
public class HandlerUtilsTest {

	private CustomSettingHelper customSettingHelper;
	private static final String coCode = "HT";
	private static final String groupName = AssignmentDeliveryConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME;
	private static final String settingName = AssignmentDeliveryConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME;

	private HandlerUtils handlerUtils;

	@Before
	public void setUp() throws Exception {
		AssignmentDeliveryLogger client = Mockito.mock(AssignmentDeliveryLogger.class);
		//PowerMockito.whenNew(AssignmentDeliveryLogger.class).withParameterTypes(String.class).withArguments("com.mitchell.services.business.partialloss.assignmentdelivery.handler.HandlerUtils");
		PowerMockito.whenNew(AssignmentDeliveryLogger.class).withArguments("com.mitchell.services.business.partialloss.assignmentdelivery.handler.HandlerUtils") .thenReturn(client);;
		
		/*	 ("com.mitchell.services.business.partialloss.assignmentdelivery.handler.HandlerUtils")
	        .thenReturn(client);
*/
		customSettingHelper = Mockito.mock(CustomSettingHelper.class);
		handlerUtils = new HandlerUtils();
		handlerUtils.setCustomSettingHelper(customSettingHelper);
		
       	}

	@Test
	public void testGetSubjectFrench() throws Exception {

		  
	
		UserInfoDocument userInfo = UserInfoDocument.Factory.newInstance();
		UserInfoType userInfoType = UserInfoType.Factory.newInstance();
		userInfoType.setOrgCode(coCode);
		userInfo.setUserInfo(userInfoType);
		Mockito.when(
				customSettingHelper.getCompanyCustomSetting(coCode, groupName,
						settingName)).thenReturn("fr-ca_en-us");
		String subject = handlerUtils.getSubject(userInfo, "SupplementRequest_", true);
		Assert.assertNotNull("subject is not null", subject);
		Assert.assertTrue(subject.contains("Nouvelle assignation")
				&& subject.contains("Supplement Assignment"));
		Mockito.verify(customSettingHelper).getCompanyCustomSetting(coCode,
				groupName, settingName);

	}

	@Test
	public void testGetSubjectEnglish() throws MitchellException {

		UserInfoDocument userInfo = UserInfoDocument.Factory.newInstance();
		UserInfoType userInfoType = UserInfoType.Factory.newInstance();
		userInfoType.setOrgCode(coCode);
		userInfo.setUserInfo(userInfoType);

		Mockito.when(
				customSettingHelper.getCompanyCustomSetting(coCode, groupName,
						settingName)).thenReturn("en-us");

		String subject = handlerUtils.getSubject(userInfo, "SupplementRequest_", true);
		Assert.assertNotNull("subject is not null", subject);
		Assert.assertTrue(subject.contains("New Supplement"));

		Mockito.verify(customSettingHelper).getCompanyCustomSetting(coCode,
				groupName, settingName);

	}

	@Test
	public void testGetSubjectEnglishFalse() throws MitchellException {

		UserInfoDocument userInfo = UserInfoDocument.Factory.newInstance();
		UserInfoType userInfoType = UserInfoType.Factory.newInstance();
		userInfoType.setOrgCode(coCode);
		userInfo.setUserInfo(userInfoType);

		Mockito.when(
				customSettingHelper.getCompanyCustomSetting(coCode, groupName,
						settingName)).thenReturn("en-us");

		String subject = handlerUtils.getSubject(userInfo, "SupplementRequest_", false);
		Assert.assertNull("subject is not null", subject);

	}

	@Test
	public void testGetSubjectFrenchFalse() throws MitchellException {

		UserInfoDocument userInfo = UserInfoDocument.Factory.newInstance();
		UserInfoType userInfoType = UserInfoType.Factory.newInstance();
		userInfoType.setOrgCode(coCode);
		userInfo.setUserInfo(userInfoType);

		Mockito.when(
				customSettingHelper.getCompanyCustomSetting(coCode, groupName,
						settingName)).thenReturn("fr-ca_en-us");

		String subject = handlerUtils.getSubject(userInfo, "SupplementRequest_", false);
		Assert.assertNull("subject is not null", subject);

	}

	@Test
	public void testGetSubjectForUserInfoDocNull() throws MitchellException {

		Mockito.when(
				customSettingHelper.getCompanyCustomSetting(coCode, groupName,
						settingName)).thenReturn("en-us");

		String subject = handlerUtils.getSubject(null, "SupplementRequest_", true);
		Assert.assertNull("subject is not null", subject);

	}

}
