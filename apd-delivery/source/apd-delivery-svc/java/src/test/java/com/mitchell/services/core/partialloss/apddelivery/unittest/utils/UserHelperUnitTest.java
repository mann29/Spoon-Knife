package com.mitchell.services.core.partialloss.apddelivery.unittest.utils;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.UserInfoProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserData;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserHelperImpl;
import com.mitchell.services.core.partialloss.apddelivery.unittest.events.EventTest.When_toXmlString_is_called;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;

public class UserHelperUnitTest {

	private UserHelperImpl userHelperTestClass;
	private UserInfoProxy mockUserInfoProxy;
	private APDDeliveryUtil mockApdDeliveryUtil;
	private SystemConfigurationProxy mockSystemConfigurationProxy;
	
	private void initializeMock(){
		mockUserInfoProxy=Mockito.mock(UserInfoProxy.class);
		mockApdDeliveryUtil=Mockito.mock(APDDeliveryUtil.class);
		mockSystemConfigurationProxy=Mockito.mock(SystemConfigurationProxy.class);
	}
	
	private void setupMocks(){
		userHelperTestClass.setUserInfoProxy(mockUserInfoProxy);
		userHelperTestClass.setApdUtil(mockApdDeliveryUtil);
		userHelperTestClass.setSystemConfigurationProxy(mockSystemConfigurationProxy);
	}
	
	@Before
	public void setup() throws Exception {
		userHelperTestClass=new UserHelperImpl();
		initializeMock();
		setupMocks();
    } 

	@Test
	public void testCheckIAUserData_RCPremium() throws Exception{
		UserInfoDocument userDoc=createUserInfoDoc();
		userDoc.getUserInfo().addAppCode("WCVSP");
		Mockito.when(mockApdDeliveryUtil.getXZUserInfo(userDoc)).thenReturn(userDoc);
		Mockito.when(mockSystemConfigurationProxy.getSettingValue((String)Mockito.anyString())).thenReturn("WCVSP");
		UserData userData = null;
		userData=userHelperTestClass.checkUserData(userDoc);
		Assert.assertNotNull(userData);
	}
	
	@Test
	public void testCheckIAUserData_NoAppCode() throws Exception{
		UserInfoDocument userDoc=createUserInfoDoc();
		Mockito.when(mockApdDeliveryUtil.getXZUserInfo(userDoc)).thenReturn(userDoc);
		Mockito.when(mockSystemConfigurationProxy.getSettingValue((String)Mockito.anyString())).thenReturn("WCVSP");
		UserData userData = null;
		userData=userHelperTestClass.checkUserData(userDoc);
		Assert.assertNull(userData);
	}
	
	private UserInfoDocument createUserInfoDoc(){
		UserInfoDocument userDocument=UserInfoDocument.Factory.newInstance();
		UserInfoType userType=userDocument.addNewUserInfo();
		return userDocument;
	}
	
}
