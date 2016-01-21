package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import java.rmi.RemoteException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SupplementRequestDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.dao.CultureDAO;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.CustomSettingProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SuppRequestUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementNotification;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementReqBO;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementRequestDocBuildr;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASEmailProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.util.UserInfoUtils;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.utils.misc.AppUtilities;

@RunWith(MockitoJUnitRunner.class)
public class RetriveSupReqDelegatorTest {

	RetriveSupReqDelegator delegator;
	
	@Mock
	UserInfoUtils userInfoUtils;
	@Mock
	CultureDAO cultureDAO;
	@Mock
	CustomSettingProxy customSettingProxy;
	@Mock
	SuppRequestUtils suppRequestUtils;
	@Mock
	SupplementRequestDocBuildr supplementRequestDocBuildr;
	@Mock
	SupplementNotification supplementNotification;
	@Mock
	SupplementRequestDocument supplementRequestDocument;
	@Mock
	AASCommonUtils aasCommonUtils;
	@Mock
	AASEmailProxy aasEmailUtils;
	IAppraisalAssignmentUtils appraisalAssignmentUtils;

	long estimateDocId = 1234l;
	long estimatorOrgId = 5868l;
	long reviewerOrgId = 6598l;
	
	@Before
	public void setUp() throws Exception {
		
		appraisalAssignmentUtils = new AppraisalAssignmentUtils();
		delegator = Mockito.spy(new RetriveSupReqDelegatorSpy(estimateDocId, estimatorOrgId, reviewerOrgId));
		appraisalAssignmentUtils.setCustomSettingProxy(customSettingProxy);
		delegator.setAasCommonUtils(aasCommonUtils);
		delegator.setAasEmailUtils(aasEmailUtils);
		delegator.setUtils(appraisalAssignmentUtils);
		delegator.setSuppBldDoc(supplementRequestDocBuildr);
		delegator.setSuppReqDocument(supplementRequestDocument);
		delegator.setUserInfoUtils(userInfoUtils);
		delegator.setCultureDAO(cultureDAO);
		Mockito.doReturn(userInfoUtils).when(delegator).getSpringBean("UserInfoUtils");
		Mockito.doReturn(cultureDAO).when(delegator).getSpringBean("CultureDAO");
		Mockito.doReturn(appraisalAssignmentUtils).when(delegator).getSpringBean(AppraisalAssignmentConstants.APPRAISAL_ASSIGNMENT_UTILS_BEAN);
		Mockito.doReturn(supplementRequestDocBuildr).when(delegator).getSpringBean("SupplementRequestDocBuildr");
		Mockito.doReturn(supplementNotification).when(delegator).getSpringBean("SupplementNotification");
		Mockito.doReturn(aasCommonUtils).when(delegator).getSpringBean(AppraisalAssignmentConstants.AAS_COMMON_UTILS_BEAN);
		Mockito.doReturn(aasEmailUtils).when(delegator).getSpringBean(AppraisalAssignmentConstants.AAS_EMAIL_UTILS_BEAN);
		
		Mockito.doReturn(createUserInfo()).when(userInfoUtils).getUserInfoDoc(Mockito.anyLong());
		Mockito.doReturn(createUserDetails()).when(userInfoUtils).getUserDetailDoc(Mockito.anyString(), Mockito.anyString());
		com.mitchell.services.core.customsettings.types.xml.Profile profile = com.mitchell.services.core.customsettings.types.xml.Profile.Factory.newInstance();
		Mockito.doReturn(profile).when(customSettingProxy).getDefaultProfile(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		Mockito.doReturn("$").when(cultureDAO).getCurrency(Mockito.anyString());
	}

	@Test
	public void test_retriveSupplementRequest_With_Custom_Language() {

		try {
			SettingValue value = SettingValue.Factory.newInstance();
			value.setValue("fr-CA_en-US");
			Mockito.doReturn(value).when(customSettingProxy).getValue(
					Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString());
			
			Mockito.doNothing().when(delegator).populateSupplementBO(Mockito.anyString());
			
			SupplementRequestDocument suppRequestDocument = SupplementRequestDocument.Factory.newInstance();
		    suppRequestDocument.addNewSupplementRequest();
		    
		    Mockito.doReturn(suppRequestDocument).when(supplementRequestDocBuildr).populateSupplementRequest((SupplementReqBO)Mockito.anyObject());
		    Mockito.doReturn("transformed text").when(supplementNotification).retrieveNotificationDoc((SupplementReqBO)Mockito.anyObject(), (SupplementRequestDocument)Mockito.anyObject());
		    Mockito.doNothing().when(delegator).transformHTML(Mockito.anyString());
		    
		    Mockito.doReturn("TestPath").when(aasCommonUtils).getSystemConfigurationSettingValue(Mockito.anyString(), Mockito.anyString());
		   
		    delegator.retriveSupplementRequest();
		  
			Mockito.verify(customSettingProxy).getValue(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString());
			Mockito.verify(delegator).populateSupplementBO("fr-CA_en-US");
		    
		} catch (Exception e) {
			Assert.fail("test_retriveSupplementRequest_With_Custom_Language failed with exception "
					+ AppUtilities.getStackTraceString(e, true));
		}
	}
	
	@Test
	public void test_retriveSupplementRequest_With_Culture_Logic() {

		try {
			SettingValue value = SettingValue.Factory.newInstance();
			value.setValue(null);
			Mockito.doReturn(value).when(customSettingProxy).getValue(
					Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString());
			
			Mockito.doReturn("fr-CA").when(cultureDAO).getCultureByCompany(Mockito.anyString());
		
			Mockito.doNothing().when(delegator).populateSupplementBO(Mockito.anyString());
			
			SupplementRequestDocument suppRequestDocument = SupplementRequestDocument.Factory.newInstance();
		    suppRequestDocument.addNewSupplementRequest();
		    
		    Mockito.doReturn(suppRequestDocument).when(supplementRequestDocBuildr).populateSupplementRequest((SupplementReqBO)Mockito.anyObject());
		    Mockito.doReturn("transformed text").when(supplementNotification).retrieveNotificationDoc((SupplementReqBO)Mockito.anyObject(), (SupplementRequestDocument)Mockito.anyObject());
		    Mockito.doNothing().when(delegator).transformHTML(Mockito.anyString());
		    
		    Mockito.doReturn("TestPath").when(aasCommonUtils).getSystemConfigurationSettingValue(Mockito.anyString(), Mockito.anyString());
		    
		    delegator.retriveSupplementRequest();
		    
		    Mockito.verify(cultureDAO).getCultureByCompany(Mockito.anyString());
		    Mockito.verify(cultureDAO).getCurrency(Mockito.anyString());
		    Mockito.verify(delegator).populateSupplementBO("fr");
		} catch (Exception e) {
			Assert.fail("test_retriveSupplementRequest_With_Culture_Logic failed with exception "
					+ AppUtilities.getStackTraceString(e, true));
		}
	}
	@Test
	public void testPopulateSupplementBONetworkShop() throws Exception {
		String SETTING_NOT_SET = "NOT_SET";
		Mockito.doReturn("TestUrl").when(aasCommonUtils).getSystemConfigurationSettingValue("/AssignmentDelivery/AssignmentEmail/URLLink4DRP", SETTING_NOT_SET);
		
		Mockito.doNothing().when(aasEmailUtils).populateEstimateInfo((SupplementReqBO)Mockito.anyObject());
		
		Mockito.doReturn("staticImageUrl").when(aasCommonUtils).getSystemConfigurationSettingValue(AppraisalAssignmentConstants.STATIC_IMAGE_BASE_URL,
				SETTING_NOT_SET);
		
		SettingValue value1 = SettingValue.Factory.newInstance();
		value1.setValue("(\\w{1,9})-(\\w{1,10})");
		Mockito.doReturn(value1).when(customSettingProxy).getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.eq(AppraisalAssignmentConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP),
				Mockito.eq(AppraisalAssignmentConstants.SETTING_CLAIM_MASK));
		
		SettingValue value2 = SettingValue.Factory.newInstance();
		value2.setValue("Suffijo");
		Mockito.doReturn(value2).when(customSettingProxy).getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.eq(AppraisalAssignmentConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP),
				Mockito.eq(AppraisalAssignmentConstants.SETTING_SUFFIX_LABEL));
		
		SettingValue value3 = SettingValue.Factory.newInstance();
		value3.setValue("signatureUrl");
		Mockito.doReturn(value3).when(customSettingProxy).getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.eq(AppraisalAssignmentConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME),
				Mockito.eq(AppraisalAssignmentConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME));
		
		UserInfoDocument estUserInfoDoc = UserInfoDocument.Factory.newInstance();
		UserInfoType estimatorUserInfo = estUserInfoDoc.addNewUserInfo();
		estimatorUserInfo.setOrgCode("L4");
		estimatorUserInfo.setUserID("userId");
		Mockito.doReturn(estUserInfoDoc).when(userInfoUtils).getEstimatorInfo(estimateDocId);
		
		delegator.setNetWorkShop(true);
		
		
		delegator.populateSupplementBO("es-L4");
        Mockito.verify(aasCommonUtils).getSystemConfigurationSettingValue("/AssignmentDelivery/AssignmentEmail/URLLink4DRP", SETTING_NOT_SET);
		
		Mockito.verify(aasEmailUtils).populateEstimateInfo((SupplementReqBO)Mockito.anyObject());
		Mockito.verify(aasCommonUtils).getSystemConfigurationSettingValue(AppraisalAssignmentConstants.STATIC_IMAGE_BASE_URL,
				SETTING_NOT_SET);
		
		
		Mockito.verify(customSettingProxy).getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.eq(AppraisalAssignmentConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP),
				Mockito.eq(AppraisalAssignmentConstants.SETTING_CLAIM_MASK));
		
		Mockito.verify(customSettingProxy).getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.eq(AppraisalAssignmentConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP),
				Mockito.eq(AppraisalAssignmentConstants.SETTING_SUFFIX_LABEL));
		
		Mockito.verify(customSettingProxy).getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.eq(AppraisalAssignmentConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME),
				Mockito.eq(AppraisalAssignmentConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME));
	}
	
	@Test
	public void testPopulateSupplementBONonNetworkShop() throws Exception {
		String SETTING_NOT_SET = "NOT_SET";
		Mockito.doReturn("TestUrl").when(aasCommonUtils).getSystemConfigurationSettingValue("/AssignmentDelivery/AssignmentEmail/URLLink", SETTING_NOT_SET);
		
		Mockito.doNothing().when(aasEmailUtils).populateEstimateInfo((SupplementReqBO)Mockito.anyObject());
		
		Mockito.doReturn("staticImageUrl").when(aasCommonUtils).getSystemConfigurationSettingValue(AppraisalAssignmentConstants.STATIC_IMAGE_BASE_URL,
				SETTING_NOT_SET);
		SettingValue value1 = SettingValue.Factory.newInstance();
		value1.setValue("(\\w{1,9})-(\\w{1,10})");
		Mockito.doReturn(value1).when(customSettingProxy).getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.eq(AppraisalAssignmentConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP),
				Mockito.eq(AppraisalAssignmentConstants.SETTING_CLAIM_MASK));
		
		SettingValue value2 = SettingValue.Factory.newInstance();
		value2.setValue("Suffijo");
		Mockito.doReturn(value2).when(customSettingProxy).getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.eq(AppraisalAssignmentConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP),
				Mockito.eq(AppraisalAssignmentConstants.SETTING_SUFFIX_LABEL));
		
		SettingValue value3 = SettingValue.Factory.newInstance();
		value3.setValue("signatureUrl");
		Mockito.doReturn(value3).when(customSettingProxy).getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.eq(AppraisalAssignmentConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME),
				Mockito.eq(AppraisalAssignmentConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME));
		
		delegator.setNonNetworkShop(true);
		UserInfoDocument estUserInfoDoc = UserInfoDocument.Factory.newInstance();
		UserInfoType estimatorUserInfo = estUserInfoDoc.addNewUserInfo();
		estimatorUserInfo.setOrgCode("L4");
		estimatorUserInfo.setUserID("userId");
		Mockito.doReturn(estUserInfoDoc).when(userInfoUtils).getEstimatorInfo(estimateDocId);
		
		delegator.populateSupplementBO("es-L4");
        Mockito.verify(aasCommonUtils).getSystemConfigurationSettingValue("/AssignmentDelivery/AssignmentEmail/URLLink", SETTING_NOT_SET);
		
		Mockito.verify(aasEmailUtils).populateEstimateInfo((SupplementReqBO)Mockito.anyObject());
		Mockito.verify(aasCommonUtils).getSystemConfigurationSettingValue(AppraisalAssignmentConstants.STATIC_IMAGE_BASE_URL,
				SETTING_NOT_SET);
		
		Mockito.verify(customSettingProxy).getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.eq(AppraisalAssignmentConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP),
				Mockito.eq(AppraisalAssignmentConstants.SETTING_CLAIM_MASK));
		
		Mockito.verify(customSettingProxy).getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.eq(AppraisalAssignmentConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP),
				Mockito.eq(AppraisalAssignmentConstants.SETTING_SUFFIX_LABEL));
		Mockito.verify(customSettingProxy).getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.eq(AppraisalAssignmentConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME),
				Mockito.eq(AppraisalAssignmentConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME));
		
	}
	private UserInfoDocument createUserInfo() {
		UserInfoDocument userInfo = UserInfoDocument.Factory.newInstance();
		userInfo.addNewUserInfo();
		userInfo.getUserInfo().setOrgCode("I9");
		userInfo.getUserInfo().setUserID("Test");
		userInfo.getUserInfo().setEmail("test@mitchell.com");
		userInfo.getUserInfo().addNewUserHier();
		userInfo.getUserInfo().getUserHier().addNewHierNode();
		userInfo.getUserInfo().getUserHier().getHierNode().setName("Desjardins");
		return userInfo;
	}
	
	private UserDetailDocument createUserDetails() {
		
		UserDetailDocument userDetailDocument = UserDetailDocument.Factory.newInstance();
		userDetailDocument.addNewUserDetail();
		userDetailDocument.getUserDetail();
		return userDetailDocument;
	}
	

}
