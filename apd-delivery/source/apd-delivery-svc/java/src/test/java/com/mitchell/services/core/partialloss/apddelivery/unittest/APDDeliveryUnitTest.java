package com.mitchell.services.core.partialloss.apddelivery.unittest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cieca.bms.AssignmentAddRqDocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.CrossOverUserInfoDocument;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDRepairAssignmentInfoType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.core.partialloss.apddelivery.pojo.EmailDeliveryHandler;
import com.mitchell.services.core.partialloss.apddelivery.pojo.PlatformDeliveryHandler;
import com.mitchell.services.core.partialloss.apddelivery.pojo.PlatformDeliveryHandlerImpl;
import com.mitchell.services.core.partialloss.apddelivery.pojo.RepairAssignmentDeliveryHandler;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxyImpl;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ClaimServiceProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.CustomSettingProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.CustomSettingProxyImpl;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ECAlertProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.EstimatePackageProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxyImpl;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.UserInfoProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.UserInfoProxyImpl;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.WorkProcessServiceProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.AppLogHelper;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtilImpl;
import com.mitchell.services.technical.partialloss.estimate.bo.Document;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;


/**
 * @author jk100838
 * 
 */
public class APDDeliveryUnitTest {

	protected APDDeliveryUtilImpl apdDeliveryUtilImpl;
	protected RepairAssignmentDeliveryHandler repairAssignmentDeliveryHandler;	
	protected PlatformDeliveryHandlerImpl platformDeliveryHandlerImpl; 
	
	protected UserInfoProxy userInfoProxy;    
	protected WorkProcessServiceProxy workProcessServiceProxy;    
	protected EstimatePackageProxy estimatePackageProxy;    
	protected ECAlertProxy ecAlertProxy;    
	protected SystemConfigurationProxy systemConfigurationProxy;    
	protected APDCommonUtilProxy apdCommonUtilProxy;
	
	   
	protected ClaimServiceProxy claimServiceProxy;    
	protected CustomSettingProxy customSettingProxy;    
	protected APDDeliveryUtil apdDeliveryUtil;	
	private AppLogHelper appLogHelper;    
    private PlatformDeliveryHandler platformDeliveryHandler;
    private EmailDeliveryHandler emailDeliveryHandler;

	
	@Before
	public void setup() throws Exception {
		apdDeliveryUtilImpl = new APDDeliveryUtilImpl();
		repairAssignmentDeliveryHandler = new RepairAssignmentDeliveryHandler();
		platformDeliveryHandlerImpl = new PlatformDeliveryHandlerImpl();
		
		systemConfigurationProxy = mock(SystemConfigurationProxyImpl.class);
		userInfoProxy = mock(UserInfoProxyImpl.class);
		apdCommonUtilProxy = mock(APDCommonUtilProxyImpl.class);		
		customSettingProxy = mock(CustomSettingProxyImpl.class);
		apdDeliveryUtil = mock(APDDeliveryUtilImpl.class);
		estimatePackageProxy = mock(EstimatePackageProxy.class);
		claimServiceProxy = mock(ClaimServiceProxy.class);
		platformDeliveryHandler = mock(PlatformDeliveryHandlerImpl.class);
	}

	@After
	public void tearDown() {
		apdDeliveryUtilImpl = null;
		repairAssignmentDeliveryHandler = null;
		platformDeliveryHandlerImpl = null;		
		systemConfigurationProxy = null;
		userInfoProxy = null;
		apdCommonUtilProxy = null;		
		customSettingProxy = null;
		apdDeliveryUtil = null;
		estimatePackageProxy = null;
		claimServiceProxy = null;

	}
	
	@Test
	public void test_isEndpointPlatformWithOverride_1() throws Exception {
		
		try {			
			File file1 = new File("src/test/resources/AppraisalAssignment_OverrideRCEndPoint.xml");
			APDDeliveryContextDocument apdContext = APDDeliveryContextDocument.Factory
			.parse(file1);
			File file2 = new File("src/test/resources/CrossOverDocument.xml");
			BaseAPDCommonType baseAPDCommonType = apdContext.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo();
			CrossOverUserInfoDocument crossOverDoc = CrossOverUserInfoDocument.Factory.parse(file2);
			
			File file3 = new File("src/test/resources/UserInfo.xml");
			UserInfoDocument userInfo = UserInfoDocument.Factory.parse(file3);
			
			when(systemConfigurationProxy.getOverrideRCEndPointEnabled()).thenReturn("true");
			doNothing().when(apdCommonUtilProxy).logINFOMessage(anyString());
			
			when(systemConfigurationProxy.getAppCodeForStaff()).thenReturn("STAFF");
			when(systemConfigurationProxy.getAppCode()).thenReturn("RCCOM");
			when(systemConfigurationProxy.getAppCodeRCOverride()).thenReturn("RCRECM");
			when(systemConfigurationProxy.getSettingValue("", null)).thenReturn("WCAIER");
			when(userInfoProxy.getCrossOverUserInfoByOrgID(any(Long.class))).thenReturn(crossOverDoc);		
			when(userInfoProxy.getUserInfo(317450l)).thenReturn(userInfo);
			
			apdDeliveryUtilImpl.setApdCommonUtilProxy(apdCommonUtilProxy);
			apdDeliveryUtilImpl.setSystemConfigurationProxy(systemConfigurationProxy);
			apdDeliveryUtilImpl.setUserInfoProxy(userInfoProxy);
			
			boolean endpointPlatform = apdDeliveryUtilImpl.isEndpointPlatformWithOverride(baseAPDCommonType);
			assertFalse(endpointPlatform);

		} catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void test_isEndpointPlatformWithOverride_2() throws Exception {
		
		try {			
			File file1 = new File("src/test/resources/AppraisalAssignment_OverrideRCEndPoint.xml");
			APDDeliveryContextDocument apdContext = APDDeliveryContextDocument.Factory
			.parse(file1);
			File file2 = new File("src/test/resources/CrossOverDocument.xml");
			BaseAPDCommonType baseAPDCommonType = apdContext.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo();
			CrossOverUserInfoDocument crossOverDoc = CrossOverUserInfoDocument.Factory.parse(file2);
			
			File file3 = new File("src/test/resources/UserInfo.xml");
			UserInfoDocument userInfo = UserInfoDocument.Factory.parse(file3);
			
			when(systemConfigurationProxy.getOverrideRCEndPointEnabled()).thenReturn("true");
			doNothing().when(apdCommonUtilProxy).logINFOMessage(anyString());
			
			when(systemConfigurationProxy.getAppCodeForStaff()).thenReturn("STAFF");
			when(systemConfigurationProxy.getAppCode()).thenReturn("RCCOM");
			when(systemConfigurationProxy.getAppCodeRCOverride()).thenReturn("WCVSP");
			when(systemConfigurationProxy.getSettingValue("", null)).thenReturn("DUMMY");
			when(userInfoProxy.getCrossOverUserInfoByOrgID(any(Long.class))).thenReturn(crossOverDoc);		
			when(userInfoProxy.getUserInfo(317450l)).thenReturn(userInfo);
			
			apdDeliveryUtilImpl.setApdCommonUtilProxy(apdCommonUtilProxy);
			apdDeliveryUtilImpl.setSystemConfigurationProxy(systemConfigurationProxy);
			apdDeliveryUtilImpl.setUserInfoProxy(userInfoProxy);
			
			boolean endpointPlatform = apdDeliveryUtilImpl.isEndpointPlatformWithOverride(baseAPDCommonType);
			assertTrue(endpointPlatform);

		} catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void test_deliverRepairAssignment_1() throws Exception {
		
		boolean thrown = false;
		try {
			File file1 = new File("src/test/resources/Test_RC_Thumbnails.xml");
			APDDeliveryContextDocument apdContext = APDDeliveryContextDocument.Factory.parse(file1);		
			
			File file2 = new File("src/test/resources/UserInfo.xml");
			UserInfoDocument xzUserInfo = UserInfoDocument.Factory.parse(file2);
			AssignmentAddRqDocument assignmentAddRq = AssignmentAddRqDocument.Factory.newInstance();
			OrgInfoDocument orgInfo = null;
			Estimate estimate = null;
			Document[] photoDocs = null;
			List<String> docTypes = new ArrayList<String>();
			Long exposureId = null;
			Calendar cal=Calendar.getInstance();
			Date date = new Date();
			
			when(customSettingProxy.getCompanyCustomSetting(anyString(),anyString(),anyString())).thenReturn("Y");
			when(apdDeliveryUtil.getXZUserInfo(any(UserInfoDocument.class))).thenReturn(xzUserInfo);
			when(apdDeliveryUtil.getOrgInfo("OA")).thenReturn(orgInfo);
			when(apdDeliveryUtil.getPublicIndex("12345","OA")).thenReturn("");
			doNothing().when(apdDeliveryUtil).registerPublicKey("12345", "OA", "123");
			when(apdDeliveryUtil.getPrivateIndexForRepairAssignment("",123l,Long.parseLong("123"))).thenReturn("");
			doNothing().when(apdDeliveryUtil).registerPrivateKey(123l,123l,"OA","123");
			doNothing().when(apdCommonUtilProxy).logINFOMessage(anyString());
			doNothing().when(apdCommonUtilProxy).logFINEMessage(anyString());
			when(apdDeliveryUtil.getEstimate(123l)).thenReturn(estimate);
			when(apdDeliveryUtil.getEstimateAuthorType(estimate)).thenReturn("STAFF");
			when(apdDeliveryUtil.getActivityOperation("Create",true)).thenReturn("");
			when(systemConfigurationProxy.getOverrideCanUploadEstimateFlagCoCodes()).thenReturn("");
			when(estimatePackageProxy.getDocumentsByExposureId(exposureId, docTypes)).thenReturn(photoDocs);
			when(apdDeliveryUtil.dateToCalendar(date, "")).thenReturn(cal);
			when(systemConfigurationProxy.getRADeliveryEventId()).thenReturn("123");
			when(claimServiceProxy.readClaimInfoIntoBms(any(UserInfoDocument.class),anyString())).thenReturn(assignmentAddRq);
			
			platformDeliveryHandlerImpl.setApdCommonUtilProxy(apdCommonUtilProxy);
			platformDeliveryHandlerImpl.setApdDeliveryUtil(apdDeliveryUtil);
			platformDeliveryHandlerImpl.setCustomSettingProxy(customSettingProxy);
			platformDeliveryHandlerImpl.setEstimatePackageProxy(estimatePackageProxy);
			platformDeliveryHandlerImpl.setSystemConfigurationProxy(systemConfigurationProxy);
			platformDeliveryHandlerImpl.setClaimServiceProxy(claimServiceProxy);			
			platformDeliveryHandlerImpl.deliverRepairAssignment(apdContext);			

		} catch (Exception e) {			
			thrown = true;
		}
		
		assertFalse(thrown);
	}
	
	@Test
	public void test_deliverRepairAssignment_2() throws Exception {
		
		boolean thrown = false;
		try {
			File file1 = new File("src/test/resources/Test_RC_No_Thumbnails.xml");
			APDDeliveryContextDocument apdContext = APDDeliveryContextDocument.Factory.parse(file1);		
			
			File file2 = new File("src/test/resources/UserInfo.xml");
			UserInfoDocument xzUserInfo = UserInfoDocument.Factory.parse(file2);
			AssignmentAddRqDocument assignmentAddRq = AssignmentAddRqDocument.Factory.newInstance();
			OrgInfoDocument orgInfo = null;
			Estimate estimate = null;
			Document[] photoDocs = null;
			List<String> docTypes = new ArrayList<String>();
			Long exposureId = null;
			Calendar cal=Calendar.getInstance();
			Date date = new Date();
			
			when(customSettingProxy.getCompanyCustomSetting(anyString(),anyString(),anyString())).thenReturn("Y");
			when(apdDeliveryUtil.getXZUserInfo(any(UserInfoDocument.class))).thenReturn(xzUserInfo);
			when(apdDeliveryUtil.getOrgInfo("OA")).thenReturn(orgInfo);
			when(apdDeliveryUtil.getPublicIndex("12345","OA")).thenReturn("");
			doNothing().when(apdDeliveryUtil).registerPublicKey("12345", "OA", "123");
			when(apdDeliveryUtil.getPrivateIndexForRepairAssignment("",123l,Long.parseLong("123"))).thenReturn("");
			doNothing().when(apdDeliveryUtil).registerPrivateKey(123l,123l,"OA","123");
			doNothing().when(apdCommonUtilProxy).logINFOMessage(anyString());
			doNothing().when(apdCommonUtilProxy).logFINEMessage(anyString());
			when(apdDeliveryUtil.getEstimate(123l)).thenReturn(estimate);
			when(apdDeliveryUtil.getEstimateAuthorType(estimate)).thenReturn("STAFF");
			when(apdDeliveryUtil.getActivityOperation("Create",true)).thenReturn("");
			when(systemConfigurationProxy.getOverrideCanUploadEstimateFlagCoCodes()).thenReturn("");
			when(estimatePackageProxy.getDocumentsByExposureId(exposureId, docTypes)).thenReturn(photoDocs);
			when(apdDeliveryUtil.dateToCalendar(date, "")).thenReturn(cal);
			when(systemConfigurationProxy.getRADeliveryEventId()).thenReturn("123");
			when(claimServiceProxy.readClaimInfoIntoBms(any(UserInfoDocument.class),anyString())).thenReturn(assignmentAddRq);
			
			platformDeliveryHandlerImpl.setApdCommonUtilProxy(apdCommonUtilProxy);
			platformDeliveryHandlerImpl.setApdDeliveryUtil(apdDeliveryUtil);
			platformDeliveryHandlerImpl.setCustomSettingProxy(customSettingProxy);
			platformDeliveryHandlerImpl.setEstimatePackageProxy(estimatePackageProxy);
			platformDeliveryHandlerImpl.setSystemConfigurationProxy(systemConfigurationProxy);
			platformDeliveryHandlerImpl.setClaimServiceProxy(claimServiceProxy);			
			platformDeliveryHandlerImpl.deliverRepairAssignment(apdContext);			

		} catch (Exception e) {
				
			thrown = true;
		}
		assertFalse(thrown);
	}

	@Test
	public void test_deliverRepairAssignment_3() throws Exception {
		
		boolean thrown = false;
		try {
			File file1 = new File("src/test/resources/Test_RC_No_Thumbnails.xml");
			APDDeliveryContextDocument apdContext = APDDeliveryContextDocument.Factory.parse(file1);		
			
			File file2 = new File("src/test/resources/UserInfo.xml");
			UserInfoDocument userInfo = UserInfoDocument.Factory.parse(file2);
			UserInfoDocument xzUserInfo = UserInfoDocument.Factory.parse(file2);
			AssignmentAddRqDocument assignmentAddRq = AssignmentAddRqDocument.Factory.newInstance();
			OrgInfoDocument orgInfo = null;
			Estimate estimate = null;
			Document[] photoDocs = null;
			List<String> docTypes = new ArrayList<String>();
			Long exposureId = null;
			Calendar cal=Calendar.getInstance();
			Date date = new Date();
			
			when(customSettingProxy.getCompanyCustomSetting(anyString(),anyString(),anyString())).thenReturn("Y");
			when(apdDeliveryUtil.getXZUserInfo(userInfo)).thenReturn(xzUserInfo);
			when(apdDeliveryUtil.getOrgInfo("OA")).thenReturn(orgInfo);
			when(apdDeliveryUtil.getPublicIndex("12345","OA")).thenReturn("");
			doNothing().when(apdDeliveryUtil).registerPublicKey("12345", "OA", "123");
			when(apdDeliveryUtil.getPrivateIndexForRepairAssignment("",123l,Long.parseLong("123"))).thenReturn("");
			doNothing().when(apdDeliveryUtil).registerPrivateKey(123l,123l,"OA","123");
			doNothing().when(apdCommonUtilProxy).logINFOMessage(anyString());
			doNothing().when(apdCommonUtilProxy).logFINEMessage(anyString());
			when(apdDeliveryUtil.getEstimate(123l)).thenReturn(estimate);
			when(apdDeliveryUtil.getEstimateAuthorType(estimate)).thenReturn("STAFF");
			when(apdDeliveryUtil.getActivityOperation("Create",true)).thenReturn("");
			when(systemConfigurationProxy.getOverrideCanUploadEstimateFlagCoCodes()).thenReturn("");
			when(estimatePackageProxy.getDocumentsByExposureId(exposureId, docTypes)).thenReturn(photoDocs);
			when(apdDeliveryUtil.dateToCalendar(date, "")).thenReturn(cal);
			when(systemConfigurationProxy.getRADeliveryEventId()).thenReturn("123");
			when(claimServiceProxy.readClaimInfoIntoBms(any(UserInfoDocument.class),anyString())).thenReturn(assignmentAddRq);
			
			platformDeliveryHandlerImpl.setApdCommonUtilProxy(apdCommonUtilProxy);
			platformDeliveryHandlerImpl.setApdDeliveryUtil(apdDeliveryUtil);
			platformDeliveryHandlerImpl.setCustomSettingProxy(customSettingProxy);
			platformDeliveryHandlerImpl.setEstimatePackageProxy(estimatePackageProxy);
			platformDeliveryHandlerImpl.setSystemConfigurationProxy(systemConfigurationProxy);
			platformDeliveryHandlerImpl.setClaimServiceProxy(claimServiceProxy);
			
			platformDeliveryHandlerImpl.deliverRepairAssignment(apdContext);			

		} catch (Exception e) {				
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void test_deliverArtifact_RA_1() throws Exception {
		
		try {
			File file1 = new File("src/test/resources/test_RepairAssignmentDelivery_CreateForPlatform.xml");
			APDDeliveryContextDocument apdContext = APDDeliveryContextDocument.Factory.parse(file1);
			APDRepairAssignmentInfoType apdRAInfoType = apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo();
			BaseAPDCommonType baseAPDCommonType = apdRAInfoType.getAPDCommonInfo();
			UserInfoType targetUserInfo = apdRAInfoType.getAPDCommonInfo().getTargetUserInfo().getUserInfo();
			// AppLogHelperImpl - remove final		
			when(systemConfigurationProxy.getIgnoreRCApplicationCode()).thenReturn("");
			when(systemConfigurationProxy.getAssignmentEmailEnabled()).thenReturn("false");
			when(apdDeliveryUtil.isEndpointPlatform2(baseAPDCommonType)).thenReturn(true);
			doNothing().when(apdCommonUtilProxy).logINFOMessage(anyString());
			doNothing().when(apdCommonUtilProxy).logFINEMessage(anyString());
			when(apdDeliveryUtil.isShopUser(targetUserInfo)).thenReturn(true);
			when(apdDeliveryUtil.evaluateUserInfoApplicationCode(baseAPDCommonType,"")).thenReturn(true);
			
			repairAssignmentDeliveryHandler.setApdCommonUtilProxy(apdCommonUtilProxy);
			repairAssignmentDeliveryHandler.setApdDeliveryUtil(apdDeliveryUtil);
			repairAssignmentDeliveryHandler.setAppLogHelper(appLogHelper);
			repairAssignmentDeliveryHandler.setEmailDeliveryHandler(emailDeliveryHandler);
			repairAssignmentDeliveryHandler.setPlatformDeliveryHandler(platformDeliveryHandler);
			repairAssignmentDeliveryHandler.setSystemConfigurationProxy(systemConfigurationProxy);
			
			repairAssignmentDeliveryHandler.deliverArtifact(apdContext);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test_deliverArtifact_RA_2() throws Exception {
		
		boolean thrown = false;
		try {
			File file1 = new File("src/test/resources/test_RepairAssignmentDelivery_CreateForPlatform.xml");
			APDDeliveryContextDocument apdContext = APDDeliveryContextDocument.Factory.parse(file1);
			APDRepairAssignmentInfoType apdRAInfoType = apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo();
			BaseAPDCommonType baseAPDCommonType = apdRAInfoType.getAPDCommonInfo();
			UserInfoType targetUserInfo = apdRAInfoType.getAPDCommonInfo().getTargetUserInfo().getUserInfo();
			
			// AppLogHelperImpl - remove final		
			when(systemConfigurationProxy.getIgnoreRCApplicationCode()).thenReturn("WCRCIGN");
			when(systemConfigurationProxy.getAssignmentEmailEnabled()).thenReturn("false");
			when(apdDeliveryUtil.isEndpointPlatform2(baseAPDCommonType)).thenReturn(true);
			doNothing().when(apdCommonUtilProxy).logINFOMessage(anyString());
			doNothing().when(apdCommonUtilProxy).logFINEMessage(anyString());
			when(apdDeliveryUtil.isShopUser(targetUserInfo)).thenReturn(false);
			when(apdDeliveryUtil.evaluateUserInfoApplicationCode(baseAPDCommonType,"")).thenReturn(true);
			
			repairAssignmentDeliveryHandler.setApdCommonUtilProxy(apdCommonUtilProxy);
			repairAssignmentDeliveryHandler.setApdDeliveryUtil(apdDeliveryUtil);
			repairAssignmentDeliveryHandler.setAppLogHelper(appLogHelper);
			repairAssignmentDeliveryHandler.setEmailDeliveryHandler(emailDeliveryHandler);
			repairAssignmentDeliveryHandler.setPlatformDeliveryHandler(platformDeliveryHandler);
			repairAssignmentDeliveryHandler.setSystemConfigurationProxy(systemConfigurationProxy);
			repairAssignmentDeliveryHandler.deliverArtifact(apdContext);
		} catch (MitchellException e) {
			thrown = true;		
		}
		assertTrue(thrown);
	}
	
	@Test
	public void test_deliverArtifact_RA_3() throws Exception {
		
		boolean thrown = false;
		try {
			File file1 = new File("src/test/resources/test_RepairAssignmentDelivery_CreateForPlatform.xml");
			APDDeliveryContextDocument apdContext = APDDeliveryContextDocument.Factory.parse(file1);
			APDRepairAssignmentInfoType apdRAInfoType = apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo();
			BaseAPDCommonType baseAPDCommonType = apdRAInfoType.getAPDCommonInfo();
			UserInfoType targetUserInfo = apdRAInfoType.getAPDCommonInfo().getTargetUserInfo().getUserInfo();
			
			// AppLogHelperImpl - remove final		
			when(systemConfigurationProxy.getIgnoreRCApplicationCode()).thenReturn("WCRCIGN");
			when(systemConfigurationProxy.getAssignmentEmailEnabled()).thenReturn("false");
			when(apdDeliveryUtil.isEndpointPlatform2(baseAPDCommonType)).thenReturn(true);
			doNothing().when(apdCommonUtilProxy).logINFOMessage(anyString());
			doNothing().when(apdCommonUtilProxy).logFINEMessage(anyString());
			when(apdDeliveryUtil.isShopUser(targetUserInfo)).thenReturn(true);
			when(apdDeliveryUtil.evaluateUserInfoApplicationCode(baseAPDCommonType,"")).thenReturn(false);
			doNothing().when(platformDeliveryHandler).deliverRepairAssignment(apdContext);
			
			repairAssignmentDeliveryHandler.setApdCommonUtilProxy(apdCommonUtilProxy);
			repairAssignmentDeliveryHandler.setApdDeliveryUtil(apdDeliveryUtil);
			repairAssignmentDeliveryHandler.setAppLogHelper(appLogHelper);
			repairAssignmentDeliveryHandler.setEmailDeliveryHandler(emailDeliveryHandler);
			repairAssignmentDeliveryHandler.setPlatformDeliveryHandler(platformDeliveryHandler);
			repairAssignmentDeliveryHandler.setSystemConfigurationProxy(systemConfigurationProxy);
			repairAssignmentDeliveryHandler.deliverArtifact(apdContext);
		} catch (MitchellException e) {
			thrown = true;		
		}
		assertFalse(thrown);
	}
	
	@Test
	public void test_deliverArtifact_RA_4() throws Exception {
		
		boolean thrown = false;
		try {
			File file1 = new File("src/test/resources/test_RepairAssignmentDelivery_CreateForPlatform.xml");
			APDDeliveryContextDocument apdContext = APDDeliveryContextDocument.Factory.parse(file1);
			APDRepairAssignmentInfoType apdRAInfoType = apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo();
			BaseAPDCommonType baseAPDCommonType = apdRAInfoType.getAPDCommonInfo();
			UserInfoType targetUserInfo = apdRAInfoType.getAPDCommonInfo().getTargetUserInfo().getUserInfo();
			
			// AppLogHelperImpl - remove final		
			when(systemConfigurationProxy.getIgnoreRCApplicationCode()).thenReturn("WCRCIGN");
			when(systemConfigurationProxy.getAssignmentEmailEnabled()).thenReturn("false");
			when(apdDeliveryUtil.isEndpointPlatform2(baseAPDCommonType)).thenReturn(true);
			doNothing().when(apdCommonUtilProxy).logINFOMessage(anyString());
			doNothing().when(apdCommonUtilProxy).logFINEMessage(anyString());
			when(apdDeliveryUtil.isShopUser(targetUserInfo)).thenReturn(true);
			when(apdDeliveryUtil.evaluateUserInfoApplicationCode(baseAPDCommonType,"")).thenReturn(false);
			doNothing().when(platformDeliveryHandler).deliverRepairAssignment(apdContext);
			
			repairAssignmentDeliveryHandler.setApdCommonUtilProxy(apdCommonUtilProxy);
			repairAssignmentDeliveryHandler.setApdDeliveryUtil(apdDeliveryUtil);
			repairAssignmentDeliveryHandler.setAppLogHelper(appLogHelper);
			repairAssignmentDeliveryHandler.setEmailDeliveryHandler(emailDeliveryHandler);
			repairAssignmentDeliveryHandler.setPlatformDeliveryHandler(platformDeliveryHandler);
			repairAssignmentDeliveryHandler.setSystemConfigurationProxy(systemConfigurationProxy);
			repairAssignmentDeliveryHandler.deliverArtifact(apdContext);
		} catch (MitchellException e) {
			thrown = true;		
		}
		assertFalse(thrown);
	}
}