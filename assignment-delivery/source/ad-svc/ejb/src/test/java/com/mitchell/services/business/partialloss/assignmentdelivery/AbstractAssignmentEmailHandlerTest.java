package com.mitchell.services.business.partialloss.assignmentdelivery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.doThrow;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.AssignmentEmailDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.impl.AssignmentEmailDeliveryHandlerImpl;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.AppLogProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.ErrorLogProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.NotificationProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.SystemConfigProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.UserInfoProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.XsltTransformer;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.AssignmentEmailDeliveryUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.CustomSettingHelper;
import com.mitchell.services.core.notification.types.EmailRequestDocument;

public class AbstractAssignmentEmailHandlerTest 
{
		
	private static final String coCode = "OA";
	private static final String groupName = "CARRIER_GLOBAL_SETTINGS";
	public static final String SET_FILE_XSLT_PATH = "/AssignmentDelivery/AssignmentEmail/XSLTPath";
	private static final String settingName = "NOTIFICATION_XSLT_CLASS";
	private static final String emailUrl = "/EnterPrisePortal/EEPAssignments/Authentication.aspx";
	private static final String companyName = "CSAA Insurance Group";
	private static final String culture = "fr-CA_en-US";
	
	private static final String emailMessage = "This is email message";
	private static final String emailXMLMessage = "This is email XML message";
	private static final String emailSubject = "New Appraisal Assignment from CSAA Insurance Group, Claim#:OA9-01";
	
	private static final String workItemId="9854d5b1-ac18-371c-7d89-14c9bd10a863";

	AssignmentEmailDeliveryHandlerImpl impl=null;
	SystemConfigProxy mockSystemConfigProxy=null;
	ErrorLogProxy mockErrorLogProxy=null;
	AppLogProxy mockAppLogProxy=null;
	NotificationProxy mockNotificationProxy=null;
	XsltTransformer mockXsltTransformer=null;
	UserInfoProxy mockUserInfoProxy=null;
	
	AssignmentDeliveryLogger mockMLogger=null;
	AssignmentEmailDeliveryUtils mockAssignmentEmailUtils=null;
	CustomSettingHelper mockCustomSettingHelper=null; // called in createEmailSubject4Creation()
	
	APDDeliveryContextDocument aPDDeliveryContextDoc=null;
	UserInfoDocument userInfoDocument=null;
	AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfo=null;
	AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoWithoutToAddress=null;
	EmailRequestDocument emailRequestDocument=null;
	
	String toAddress = null;
	String toCCAddress = null;
	EmailRequestDocument emailReqDoc=null;
	Boolean isOverrideToEmailAddress;
	String fromDisplayName=null;
	String fromAddress=null;
	String emailLink=null;
	String companyCustomSetting=null;
	
	@Before
	public void setup() throws MitchellException
	{
		impl=new AssignmentEmailDeliveryHandlerImpl();
		
		//XML Required
		try 
		{
			aPDDeliveryContextDoc=APDDeliveryContextDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("APDDeliveryContextDocument1.xml"));
			System.out.println(aPDDeliveryContextDoc);
			userInfoDocument=UserInfoDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("UserInfoDocument.xml"));
			additionalAppraisalAssignmentInfo=AdditionalAppraisalAssignmentInfoDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("AdditionalAppraisalAssignmentInfo.xml"));
			additionalAppraisalAssignmentInfoWithoutToAddress=AdditionalAppraisalAssignmentInfoDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("additionalAppraisalAssignmentInfoWithoutToAddress.xml"));
			System.out.println(additionalAppraisalAssignmentInfoWithoutToAddress);
			emailRequestDocument=EmailRequestDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("EmailRequestDocument.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		mockSystemConfigProxy=Mockito.mock(SystemConfigProxy.class);
		mockErrorLogProxy=Mockito.mock(ErrorLogProxy.class);
		mockAppLogProxy=Mockito.mock(AppLogProxy.class);
		mockNotificationProxy=Mockito.mock(NotificationProxy.class);
		mockXsltTransformer=Mockito.mock(XsltTransformer.class);
		mockUserInfoProxy=Mockito.mock(UserInfoProxy.class);
		
		mockMLogger=Mockito.mock(AssignmentDeliveryLogger.class);
		mockAssignmentEmailUtils=Mockito.mock(AssignmentEmailDeliveryUtils.class);
		mockCustomSettingHelper=Mockito.mock(CustomSettingHelper.class);
		
	}
	
	@After
	public void destroy() throws MitchellException
	{
		impl=null;
		mockMLogger=null;
	}
	
	public void deliverCreationTest_initialize() throws MitchellException, IllegalStateException
	{
		try
		{
		
			//Check_proxy method mocking
			impl.setSystemConfigProxy(mockSystemConfigProxy);
			impl.setErrorLogProxy(mockErrorLogProxy);
			impl.setAppLogProxy(mockAppLogProxy);
			impl.setNotificationProxy(mockNotificationProxy);
			impl.setXsltTransformer(mockXsltTransformer);
			impl.setUserInfoProxy(mockUserInfoProxy);
			
			impl.setAssignmentEmailDevlieryUtils(mockAssignmentEmailUtils);
			
			//Logger
			impl.setmLogger(mockMLogger);
				
			Mockito.when(mockAssignmentEmailUtils.getCompanyName(aPDDeliveryContextDoc.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getTargetUserInfo())).thenReturn(companyName);
			fromDisplayName=mockAssignmentEmailUtils.getCompanyName(aPDDeliveryContextDoc.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getTargetUserInfo());
			
			Mockito.when(mockSystemConfigProxy.getStringValue(AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_FROMADDRESS,"donot_reply@mitchell.com")).thenReturn("donot_reply@mitchell.com");
			fromAddress=mockSystemConfigProxy.getStringValue(AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_FROMADDRESS,"donot_reply@mitchell.com");
	
			
			Mockito.when(mockSystemConfigProxy.getStringValue(AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_URLLINK)).thenReturn(emailUrl);
			emailLink=mockSystemConfigProxy.getStringValue(AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_URLLINK);
			
			Mockito.when(mockCustomSettingHelper.getCompanyCustomSetting(coCode,groupName,settingName)).thenReturn(culture);
			impl.setCustomSettingHelper(mockCustomSettingHelper);
			companyCustomSetting = mockCustomSettingHelper.getCompanyCustomSetting(coCode,groupName,settingName);
			
			Mockito.when(mockAssignmentEmailUtils.createMessageXml4EmailCreation(aPDDeliveryContextDoc, emailLink,culture,true)).thenReturn(emailMessage);
			Mockito.when(mockSystemConfigProxy.getStringValue(SET_FILE_XSLT_PATH)).thenReturn("src/test/resources");
			Mockito.when(mockXsltTransformer.transformXmlString("src/test/resources/AssignmentEmail_fr-CA_en-US.xslt", emailMessage)).thenReturn(emailXMLMessage);			
			
			Mockito.when(mockAssignmentEmailUtils.createEmailSubjectXml4Creation(aPDDeliveryContextDoc)).thenReturn(emailSubject);
			Mockito.when(mockSystemConfigProxy.getStringValue(SET_FILE_XSLT_PATH)).thenReturn("src/test/resources");
			Mockito.when(mockXsltTransformer.transformXmlString("src/test/resources/AssignmentEmail_fr-CA_en-US.xslt", emailSubject)).thenReturn(emailSubject);
			
			Mockito.when(mockAssignmentEmailUtils.getUserInfo(aPDDeliveryContextDoc.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo())).thenReturn(userInfoDocument);
			Mockito.when(mockAssignmentEmailUtils.getWorkItemId(aPDDeliveryContextDoc.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo())).thenReturn(workItemId);
			
		
			Mockito.when(mockNotificationProxy.buildEmailRequest(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(emailRequestDocument);
			emailReqDoc=mockNotificationProxy.buildEmailRequest(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString());
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void deliverCreationTest_OverrideToEmailAddressTrue() throws MitchellException, IllegalStateException
	{
			try {
				Mockito.when(mockAssignmentEmailUtils.getAdditionalAssignmentInfoDocFromAPDAssignmentContext(aPDDeliveryContextDoc)).thenReturn(additionalAppraisalAssignmentInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			deliverCreationTest_initialize();
			
			isOverrideToEmailAddress=true;
			
			deliverCreationTest_verify();
	}
	
	@Test
	public void deliverCreationTest_OverrideToEmailAddressFalse() throws MitchellException, IllegalStateException
	{
			try {
				Mockito.when(mockAssignmentEmailUtils.getAdditionalAssignmentInfoDocFromAPDAssignmentContext(aPDDeliveryContextDoc)).thenReturn(additionalAppraisalAssignmentInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			deliverCreationTest_initialize();
			
			isOverrideToEmailAddress=false;
			
			deliverCreationTest_verify();
	}
	

	@Test
	public void deliverCreationTest_OverrideToEmailAddressTrue_toEmailAddressEmpty() throws MitchellException, IllegalStateException
	{
			try {
				Mockito.when(mockAssignmentEmailUtils.getAdditionalAssignmentInfoDocFromAPDAssignmentContext(aPDDeliveryContextDoc)).thenReturn(additionalAppraisalAssignmentInfoWithoutToAddress);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			deliverCreationTest_initialize();
			
			isOverrideToEmailAddress=true;
			
			deliverCreationTest_verify();
	}
	
	@Test
	public void deliverCreationTest_OverrideToEmailAddressFalse_toEmailAddressEmpty() throws MitchellException, IllegalStateException
	{
			try {
				Mockito.when(mockAssignmentEmailUtils.getAdditionalAssignmentInfoDocFromAPDAssignmentContext(aPDDeliveryContextDoc)).thenReturn(additionalAppraisalAssignmentInfoWithoutToAddress);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			deliverCreationTest_initialize();
			
			isOverrideToEmailAddress=false;
			
			deliverCreationTest_verify();
	}
	
	
	@Test
	public void deliverCreationTest_getOverriddenEmailAddressException() throws MitchellException, IllegalStateException
	{
			
			MitchellException meThrow = new MitchellException("a", "bb", "ccc");
			try {
					Mockito.when(mockAssignmentEmailUtils.getAdditionalAssignmentInfoDocFromAPDAssignmentContext(aPDDeliveryContextDoc)).thenThrow(meThrow);
			} catch (Exception e1) {
					//text case when expected exception
			}
			
		  	deliverCreationTest_initialize();
		  	
			isOverrideToEmailAddress=true;
			
			deliverCreationTest_verify();
	}	
	
	@Test
	public void deliverCreationTest_getOverriddenCCEmailAddressException() throws MitchellException, IllegalStateException
	{
			
			MitchellException meThrow = new MitchellException("a", "bb", "ccc");
			try {
					Mockito.when(mockAssignmentEmailUtils.getAdditionalAssignmentInfoDocFromAPDAssignmentContext(aPDDeliveryContextDoc)).thenReturn(additionalAppraisalAssignmentInfo).thenThrow(meThrow);
			} catch (Exception e1) {
					//text case when expected exception
			}
			
		  	deliverCreationTest_initialize();
		  	
			isOverrideToEmailAddress=true;
			
			deliverCreationTest_verify();
	}	
	
	public void deliverCreationTest_verify() throws MitchellException, IllegalStateException
	{
		try{
		
			impl.deliverCreation(aPDDeliveryContextDoc, isOverrideToEmailAddress, culture);
			
			Mockito.verify(mockAssignmentEmailUtils,times(2)).getCompanyName(aPDDeliveryContextDoc.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getTargetUserInfo());
			Mockito.verify(mockSystemConfigProxy,times(2)).getStringValue(AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_URLLINK);
			Mockito.verify(mockCustomSettingHelper,times(3)).getCompanyCustomSetting(coCode,groupName,settingName);
			Mockito.verify(mockAssignmentEmailUtils,times(1)).createMessageXml4EmailCreation(aPDDeliveryContextDoc, emailLink,culture,true);
			Mockito.verify(mockAssignmentEmailUtils,times(1)).createEmailSubjectXml4Creation(aPDDeliveryContextDoc);
			Mockito.verify(mockAssignmentEmailUtils,times(1)).getUserInfo(aPDDeliveryContextDoc.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo());
			Mockito.verify(mockAssignmentEmailUtils,times(1)).getWorkItemId(aPDDeliveryContextDoc.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo());
			Mockito.verify(mockXsltTransformer,times(1)).transformXmlString("src/test/resources/AssignmentEmail_fr-CA_en-US.xslt", emailSubject);
			if(isOverrideToEmailAddress==true)
					Mockito.verify(mockAssignmentEmailUtils,times(2)).getAdditionalAssignmentInfoDocFromAPDAssignmentContext(aPDDeliveryContextDoc);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		
		assertNotNull(fromDisplayName);
		assertNotNull(emailLink);
		assertNotNull(fromAddress);
		assertNotNull(emailReqDoc);
		assertEquals("CSAA Insurance Group", fromDisplayName);
		assertEquals("donot_reply@mitchell.com",fromAddress);
		assertEquals(companyCustomSetting,culture);
	}
			
}
	
	
	

