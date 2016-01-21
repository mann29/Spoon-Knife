package com.mitchell.services.business.partialloss.assignmentdelivery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import com.cieca.bms.AdminInfoType;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.PersonInfoType;
import com.cieca.bms.PersonNameType;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.EnvelopeContextType;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.schemas.NameValuePairType;
import com.mitchell.schemas.apddelivery.APDAppraisalAssignmentInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.APDMitchellEnvelopeType;
import com.mitchell.schemas.assignmentdelivery.AssignmentDeliveryEmailReqType;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.AssignmentEmailDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.SystemConfigProxyImpl;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.AssignmentEmailDeliveryUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.CustomSettingHelperImpl;
import com.mitchell.sip.stdassignmentdelivery.StdADWFConstants;

/**
 * 
 * @author mukesh.upreti
 *
 */
public class AssignmentEmailDeliveryUtilsTest {

	
	AssignmentEmailDeliveryUtils aedu;
	CustomSettingHelperImpl customSettingHelper;
	SystemConfigProxyImpl systemConfigProxy;
	@Before
	public void setup() throws MitchellException{
		aedu = AssignmentEmailDeliveryUtils.getInstance(); 
		customSettingHelper = Mockito.mock(CustomSettingHelperImpl.class);
		systemConfigProxy = Mockito.mock(SystemConfigProxyImpl.class);
		aedu.setCustomSettingHelper(customSettingHelper);
		aedu.setSystemConfigProxyImpl(systemConfigProxy);
		Mockito.doNothing().when(customSettingHelper).initialize();
		Mockito.doReturn("Suffijo").when(customSettingHelper).getCompanyCustomSetting(Mockito.anyString(), Mockito.eq(AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP),
				 Mockito.eq(AssignmentDeliveryConstants.SETTING_SUFFIX_LABEL));
		Mockito.doReturn("Suffijo").when(customSettingHelper).getUserCustomSetting(Mockito.anyString(), Mockito.anyString(), Mockito.eq(AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP),
				Mockito.eq(AssignmentDeliveryConstants.SETTING_SUFFIX_LABEL));
		
		Mockito.doReturn("(\\w{1,9})-(\\w{1,10})").when(customSettingHelper).getCompanyCustomSetting(Mockito.anyString(), Mockito.eq(AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP),
				Mockito.eq(AssignmentDeliveryConstants.SETTING_CLAIM_MASK));
			
		Mockito.doReturn("/images/HeaderLoc.jpg").when(systemConfigProxy).getStringValue(AssignmentDeliveryConstants.STATIC_IMAGE_BASE_URL);
		
	}

	@Test
	public void getContactFromClaimantNoFirstNameTest()
			throws Exception
			{
		String testName = "Singh Kumar";		
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromClaimant",getCiecaDoc("","Kumar","Singh")));
		assertTrue(testName.equals(nameFromMethodCall[0]));

			}

	@Test
	public void getContactFromClaimantNoSecondLastNameTest()
			throws Exception
			{
		String testName = "Ajay Singh";
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromClaimant",getCiecaDoc("Ajay","","Singh")));
		assertTrue(testName.equals(nameFromMethodCall[0]));

			}

	@Test
	public void getContactFromClaimantNoLastNameTest()
			throws Exception
			{
		String testName = "Ajay Kumar";
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromClaimant",getCiecaDoc("Ajay","Kumar","")));
		assertTrue(testName.equals(nameFromMethodCall[0]));

			}

	@Test
	public void getContactFromClaimantNoNameTest()
			throws Exception
			{
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromClaimant",getCiecaDoc("","","")));
		assertNull(nameFromMethodCall[0]);
			}
	
	@Test
	public void getContactFromClaimantAllNameNullTest()
			throws Exception
			{
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromClaimant",getCiecaDoc(null,null,null)));
		assertNull(nameFromMethodCall[0]);
			}
	
	@Test
	public void getContactFromClaimantTestOnlyFirstName()
			throws Exception
			{
		String testName = "Ajay";
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromClaimant",getCiecaDoc("Ajay","","")));
		assertTrue(testName.equals(nameFromMethodCall[0]));

			}
	
	@Test
	public void getContactFromPolicyHolderTestOnlySecondName()
			throws Exception
			{
		String testName = "Kumar";
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromPolicyHolder",getCiecaDoc("","Kumar","")));
		assertTrue(testName.equals(nameFromMethodCall[0]));

			}
	
	@Test
	public void getContactFromPolicyHolderTestOnlyLastName()
			throws Exception
			{
		String testName = "Singh";
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromPolicyHolder",getCiecaDoc("","","Singh")));
		assertTrue(testName.equals(nameFromMethodCall[0]));

			}
	
	
	
	@Test
	public void getContactFromPolicyHolderTest()
			throws Exception
			{
		String testName = "Singh Kumar";		
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromPolicyHolder",getCiecaDoc("","Kumar","Singh")));
		assertTrue(testName.equals(nameFromMethodCall[0]));

			}

	@Test
	public void getContactFromPolicyHolderNoSecondLastNameTest()
			throws Exception
			{
		String testName = "Ajay Singh";
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromPolicyHolder",getCiecaDoc("Ajay","","Singh")));
		assertTrue(testName.equals(nameFromMethodCall[0]));

			}

	@Test
	public void getContactFromPolicyHolderLastNameTest()
			throws Exception
			{
		String testName = "Ajay Kumar";
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromPolicyHolder",getCiecaDoc("Ajay","Kumar","")));
		assertTrue(testName.equals(nameFromMethodCall[0]));

			}

	@Test
	public void getContactFromPolicyHolderNoNameTest()
			throws Exception
			{
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromPolicyHolder",getCiecaDoc("","","")));
		assertNull(nameFromMethodCall[0]);
			}
	
	@Test
	public void getContactFromPolicyHolderAllNameNullTest()
			throws Exception
			{
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromPolicyHolder",getCiecaDoc(null,null,null)));
		assertNull(nameFromMethodCall[0]);
			}
	
	@Test
	public void getContactFromPolicyHolderTestOnlyFirstName()
			throws Exception
			{
		String testName = "Ajay";
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromClaimant",getCiecaDoc("Ajay","","")));
		assertTrue(testName.equals(nameFromMethodCall[0]));

			}
	
	@Test
	public void getContactFromClaimantTestOnlySecondName()
			throws Exception
			{
		String testName = "Kumar";
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromClaimant",getCiecaDoc("","Kumar","")));
		assertTrue(testName.equals(nameFromMethodCall[0]));

			}
	
	@Test
	public void getContactFromClaimantTestOnlyLastName()
			throws Exception
			{
		String testName = "Singh";
		String nameFromMethodCall[] =(Whitebox.invokeMethod(aedu,"getContactFromClaimant",getCiecaDoc("","","Singh")));
		assertTrue(testName.equals(nameFromMethodCall[0]));

			}

	
	private CIECADocument getCiecaDoc(String firstName, String secondName, String lastName){
		CIECADocument cd=CIECADocument.Factory.newInstance();
		AdminInfoType adminInfo= cd.addNewCIECA().addNewAssignmentAddRq().addNewAdminInfo();
		PersonInfoType policyHolder=adminInfo.addNewPolicyHolder().addNewParty().addNewPersonInfo();
		PersonInfoType personInfo=adminInfo.addNewClaimant().addNewParty().addNewPersonInfo();
		PersonNameType pnt=PersonNameType.Factory.newInstance();
		pnt.setFirstName(firstName);
		pnt.setLastName(lastName);
		pnt.setSecondLastName(secondName);	
		personInfo.setPersonName(pnt);
		policyHolder.setPersonName(pnt);
		return cd;
	}
	
	@After
	public void destroy(){
		aedu=null;
	}
	
	@Test 
	public void createMessageXmlEmail4_NONDRP_EMAIL_ROOT() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessage", "en-US",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessage().getPoi();
		Assert.assertEquals("Rollover", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessage().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessage().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessage().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessage().getAssignmentMemo());
		Assert.assertEquals("Collision", asgnDeliveryEmailRqType.getEmailMessage().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessage().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessage().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessage().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessage().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessageDRP());
	}
	
	@Test 
	public void createMessageXmlEmail4_NONDRP_EMAIL_ROOTForFrench() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessage", "fr-ca_en-us",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessage().getPoi();
		Assert.assertEquals("Renversement;Rollover", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessage().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessage().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessage().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessage().getAssignmentMemo());
		Assert.assertEquals("Collision;Collision", asgnDeliveryEmailRqType.getEmailMessage().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessage().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessage().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessage().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessage().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessageDRP());
	}
	
	
	@Test 
	public void createMessageXmlEmail4_NONDRP_EMAIL_ROOTForIsEmailNotifyFalse() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessage", "en-US",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessage().getPoi();
		Assert.assertEquals("Rollover", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessage().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessage().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessage().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessage().getAssignmentMemo());
		Assert.assertEquals("Collision", asgnDeliveryEmailRqType.getEmailMessage().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessage().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessage().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessage().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessage().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessageDRP());
	}
	
	@Test 
	public void createMessageXmlEmail4_NONDRP_EMAIL_ROOTForFrenchIsEmailNotifyFalse() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessage", "fr-ca_en-us",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessage().getPoi();
		Assert.assertEquals("Tonneau", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessage().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessage().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessage().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessage().getAssignmentMemo());
		Assert.assertEquals("Collision", asgnDeliveryEmailRqType.getEmailMessage().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessage().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessage().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessage().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessage().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessageDRP());
	}
	
	@Test 
	public void createMessageXmlEmail4_EmailRoot_Null() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink",null, "en-US",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		Assert.assertNotNull(asgnDeliveryEmailRqType);
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessageDRP());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	}
	
	@Test 
	public void createMessageXmlEmail4_EmailRoot_NullForFrench() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink",null, "fr-ca_en-us",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		Assert.assertNotNull(asgnDeliveryEmailRqType);
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessageDRP());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	}
	
	@Test 
	public void createMessageXmlEmail4_EmailRoot_NullForIsEmailNotifyFalse() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink",null, "en-US",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		Assert.assertNotNull(asgnDeliveryEmailRqType);
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessageDRP());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	}
	
	@Test 
	public void createMessageXmlEmail4_EmailRoot_NullForFrenchIsEmailNotifyFalse() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink",null, "fr-ca_en-us",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		Assert.assertNotNull(asgnDeliveryEmailRqType);
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessageDRP());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	}
	
	
	@Test
	public void createMessageXml4EmailDRP() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "en-US",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Rollover", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Collision", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	} 
	
	@Test
	public void createMessageXml4EmailDRPForFrench() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "fr-ca_en-us",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Renversement;Rollover", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Collision;Collision", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	}
	
	
	@Test
	public void createMessageXml4EmailDRPForIsEmailNotifyFalse() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "en-US",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Rollover", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Collision", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	} 
	
	@Test
	public void createMessageXml4EmailDRPForFrenchIsEmailNotifyFalse() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "fr-ca_en-us",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Tonneau", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Collision", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	}
	
	@Test
	public void createMessageXml4FaxDRPForFrenchIsEmailNotifyFalse() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml4EmailDRP(apdDelContext, "testLink","fr-ca_en-us",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Tonneau", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Collision", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	} 
	
	@Test
	public void createMessageXml4FaxDRPForIsEmailNotifyFalse() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml4EmailDRP(apdDelContext, "testLink","en-US",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Rollover", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Collision", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	} 
	
	
	@Test
	public void createMessageXml4FaxDRP() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml4EmailDRP(apdDelContext, "testLink","en-US",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Rollover", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Collision", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	} 
	
	@Test
	public void createMessageXml4FaxDRPForFrench() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml4EmailDRP(apdDelContext, "testLink","fr-ca_en-us",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Renversement;Rollover", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Collision;Collision", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	}
	
	
	@Test
	public void createMessageXml4EmailCreation() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "en-US",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Rollover", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Collision", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	} 
	
	@Test
	public void createMessageXml4EmailCreationForFrench() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "fr-ca_en-us",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Renversement;Rollover", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Collision;Collision", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	}
	
	@Test
	public void createMessageXml4EmailCreationForIsEmailNotifyFalse() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "en-US",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Rollover", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Collision", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	}
	
	@Test
	public void createMessageXml4EmailCreationForFrenchIsEmailNotifyFalse() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "fr-ca_en-us",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Tonneau", poiMsg);
		Assert.assertEquals("OA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("AAA Northern CA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Collision", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("OA07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		Assert.assertNull(asgnDeliveryEmailRqType.getEmailMessage());
	}
	
	@Test 
	public void getAssignmentSubTypeDescription() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		List <String> allSubTypeCodes=asgSubTypeCodeList();
		List<String> allAsgSubTypeDesc=asgSubTypeDescList();
		int index=0;
		for (String subTypeCode : allSubTypeCodes) {
			apdDelContext=createDeliveryContext(subTypeCode);
			String asgSubTypeDesc = aedu.getAssignmentSubTypeDescription(apdDelContext);
			Assert.assertEquals(allAsgSubTypeDesc.get(index), asgSubTypeDesc);
			index++;
		}
	} 
	
	private APDDeliveryContextDocument createDeliveryContext(String subTypeDesc){
		APDDeliveryContextDocument apdContextDoc=APDDeliveryContextDocument.Factory.newInstance();
		 APDDeliveryContextType apdContextType=apdContextDoc.addNewAPDDeliveryContext();
		 APDAppraisalAssignmentInfoType apdAsginfoType=apdContextType.addNewAPDAppraisalAssignmentInfo();
		 APDMitchellEnvelopeType apdMitchellEnvType= apdAsginfoType.addNewAssignmentMitchellEnvelope();
		 MitchellEnvelopeType mitchellEnvType=apdMitchellEnvType.addNewMitchellEnvelope();
		 EnvelopeContextType envContextType=mitchellEnvType.addNewEnvelopeContext();
		 NameValuePairType nameValuePair=envContextType.addNewNameValuePair();
		 nameValuePair.setName(AssignmentEmailDeliveryConstants.MITCHELL_ENV_NAME_APPRAISAL_ASSIGNMENT_SUB_TYPE);
		 nameValuePair.addValue(subTypeDesc);
		 return apdContextDoc;
	}

	private List<String> asgSubTypeCodeList() {
		return Arrays.asList(new String[] { 
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_PHOTOS_AND_ESTIMATE_ONLY,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_PHOTOS_ONLY,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_TOTAL_LOSS,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_POLICE_REPORT,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_PRESERVE_EVIDENCE,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_REINSPECTION,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_SCENE_INVESTIGATION,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_THEFT_RECOVERY,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_CUSTOMER_SERVICE,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_SPECIAL_INVESTIGATION,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_INSPECT_ONLY,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_CATASTROPHE,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_DESK_WORK,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_AUDIT,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_DESK_SUPPLEMENT,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_OTHER_FIELD,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_SUPPLEMENT_TASK
				});
	}
	
	private List<String> asgSubTypeDescList() {
		return Arrays.asList(new String[] { 
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_PHOTOS_AND_ESTIMATE_ONLY_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_PHOTOS_ONLY_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_TOTAL_LOSS_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_POLICE_REPORT_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_PRESERVE_EVIDENCE_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_REINSPECTION_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_SCENE_INVESTIGATION_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_THEFT_RECOVERY_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_CUSTOMER_SERVICE_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_SPECIAL_INVESTIGATION_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_INSPECT_ONLY_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_CATASTROPHE_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_DESK_WORK_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_AUDIT_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_DESK_SUPPLEMENT_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_OTHER_FIELD_DESC,
				StdADWFConstants.ASSIGNMENT_SUB_TYPE_SUPPLEMENT_TASK_NEW_DESC
				});
	}
	
	@Test 
	public void getAssignmentSubTypeDescription_Null() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		final String msg = aedu.getAssignmentSubTypeDescription(apdDelContext);
		Assert.assertNull(msg);
	}
	
	@Test 
	public void getAssignmentSubTypeDescription_ME_NULL() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContext();
		apdDelContext.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().setAssignmentMitchellEnvelope(null);
		final String msg = aedu.getAssignmentSubTypeDescription(apdDelContext);
		Assert.assertNull(msg);
	}
	
		
	@Test 
	public void getLicensePlateTestDRP() throws Exception{
		APDDeliveryContextDocument apdDelContext = null;
		apdDelContext = initializeAPDDeliveryContextLDA();
		
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "es-L4",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType = AssignmentDeliveryEmailReqType.Factory.parse(msg);
		String licPlateDRP = asgnDeliveryEmailRqType.getEmailMessageDRP().getLicensePlate();
		Assert.assertEquals("LP1234", licPlateDRP);
	}
	@Test 
	public void getLicensePlateNullTest() throws Exception{
		APDDeliveryContextDocument apdDelContext = null;
		apdDelContext = initializeAPDDeliveryContext();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "es-L4",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType = AssignmentDeliveryEmailReqType.Factory.parse(msg);
		String licPlateDRP = asgnDeliveryEmailRqType.getEmailMessageDRP().getLicensePlate();
		Assert.assertEquals(null, licPlateDRP);
	}
	@Test 
	public void getLicensePlateTestNonDRP() throws Exception{
		APDDeliveryContextDocument apdDelContext = null;
		apdDelContext = initializeAPDDeliveryContextLDA();
		
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessage", "es-L4",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType = AssignmentDeliveryEmailReqType.Factory.parse(msg);
		String licPlate = asgnDeliveryEmailRqType.getEmailMessage().getLicensePlate();
		Assert.assertEquals("LP1234", licPlate);
		
	}
	@Test 
	public void getClaimAndSuffixNumberTestDRP() throws Exception{
		APDDeliveryContextDocument apdDelContext = null;
		apdDelContext = initializeAPDDeliveryContextLDA();
		
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "es-L4",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType = AssignmentDeliveryEmailReqType.Factory.parse(msg);
		String claimNumIdDRP = asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimId();
		String suffixDRP = asgnDeliveryEmailRqType.getEmailMessageDRP().getSuffix();
		String suffixLabelDRP = asgnDeliveryEmailRqType.getEmailMessageDRP().getSuffixLabel();
		
		Assert.assertEquals("A07194632", claimNumIdDRP);
		Assert.assertEquals("01", suffixDRP);
		Assert.assertEquals("Suffijo", suffixLabelDRP);
		
	}
	@Test 
	public void getClaimAndSuffixNumberTestNonDRP() throws Exception{
		APDDeliveryContextDocument apdDelContext = null;
		apdDelContext = initializeAPDDeliveryContextLDA();
		
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessage", "es-L4",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType = AssignmentDeliveryEmailReqType.Factory.parse(msg);
		
		String claimNumId = asgnDeliveryEmailRqType.getEmailMessage().getClaimId();
		String suffix = asgnDeliveryEmailRqType.getEmailMessage().getSuffix();
		String suffixLabel = asgnDeliveryEmailRqType.getEmailMessage().getSuffixLabel();
		Assert.assertEquals("A07194632", claimNumId);
		Assert.assertEquals("01", suffix);
		Assert.assertEquals("Suffijo", suffixLabel);
		
		
	}
	
	@Test 
	public void getImageTokensTestDRP() throws Exception{
		APDDeliveryContextDocument apdDelContext = null;
		apdDelContext = initializeAPDDeliveryContextLDA();
		
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "es-L4",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType = AssignmentDeliveryEmailReqType.Factory.parse(msg);
		String hdrImageDRP = asgnDeliveryEmailRqType.getEmailMessageDRP().getStaticImageBaseUrl();
		assertEquals(hdrImageDRP, "/images/HeaderLoc.jpg");
	
		
	}
	@Test 
	public void getImageTokensTestNonDRP() throws Exception{
		APDDeliveryContextDocument apdDelContext = null;
		apdDelContext = initializeAPDDeliveryContextLDA();
		
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessage", "es-L4",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType = AssignmentDeliveryEmailReqType.Factory.parse(msg);
		String hdrImage = asgnDeliveryEmailRqType.getEmailMessage().getStaticImageBaseUrl();
		assertEquals(hdrImage, "/images/HeaderLoc.jpg");
		
	}
	
	@Test 
	public void getCurrYearDRPTest() throws Exception{
		APDDeliveryContextDocument apdDelContext = null;
		apdDelContext = initializeAPDDeliveryContextLDA();
		
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "es-L4",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType = AssignmentDeliveryEmailReqType.Factory.parse(msg);
		String currYear = asgnDeliveryEmailRqType.getEmailMessageDRP().getCurrentYear();
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		assertEquals(currYear, year);
		
		
	}
	@Test 
	public void getCurrYearTest() throws Exception{
		APDDeliveryContextDocument apdDelContext = null;
		apdDelContext = initializeAPDDeliveryContextLDA();
		
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessage", "es-L4",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType = AssignmentDeliveryEmailReqType.Factory.parse(msg);
        String currYear = asgnDeliveryEmailRqType.getEmailMessage().getCurrentYear();
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		assertEquals(currYear, year);
		
	}
	@Test
	public void createMessageXml4EmailDRPL4Test() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContextLDA();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "es-L4",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessageDRP().getPoi();
		Assert.assertEquals("Vuelco", poiMsg);
		Assert.assertEquals("L4", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoCd());
		Assert.assertEquals("LINEA DIRECTA ASEGURADORA", asgnDeliveryEmailRqType.getEmailMessageDRP().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessageDRP().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentMemo());
		Assert.assertEquals("Colisión", asgnDeliveryEmailRqType.getEmailMessageDRP().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimantName());
		Assert.assertEquals("A07194632-01", asgnDeliveryEmailRqType.getEmailMessageDRP().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubTypeCode());
		Assert.assertEquals("Recuperación de robos", asgnDeliveryEmailRqType.getEmailMessageDRP().getAssignmentSubType());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessageDRP().getPreferMonth());
		
	} 
	
	@Test
	public void createMessageXml4EmailL4Test() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContextLDA();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessage", "es-L4",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessage().getPoi();
		Assert.assertEquals("Vuelco", poiMsg);
		Assert.assertEquals("L4", asgnDeliveryEmailRqType.getEmailMessage().getCoCd());
		Assert.assertEquals("LINEA DIRECTA ASEGURADORA", asgnDeliveryEmailRqType.getEmailMessage().getCoName());
		Assert.assertEquals("100004424808", asgnDeliveryEmailRqType.getEmailMessage().getTaskId());
		Assert.assertEquals("Check", asgnDeliveryEmailRqType.getEmailMessage().getAssignmentMemo());
		Assert.assertEquals("Colisión", asgnDeliveryEmailRqType.getEmailMessage().getCauseOfLossDesc());
		Assert.assertEquals("Last Name Photo", asgnDeliveryEmailRqType.getEmailMessage().getClaimantName());
		Assert.assertEquals("A07194632-01", asgnDeliveryEmailRqType.getEmailMessage().getClaimNumber());
		Assert.assertEquals("Photo and Estimate Only", asgnDeliveryEmailRqType.getEmailMessage().getAssignmentSubTypeCode());
		Assert.assertEquals("Recuperación de robos", asgnDeliveryEmailRqType.getEmailMessage().getAssignmentSubType());
		Assert.assertEquals("", asgnDeliveryEmailRqType.getEmailMessage().getPreferMonth());
		Mockito.verify(customSettingHelper).initialize();
	} 
	
	@Test
	public void customSettingTest() throws Exception{
		APDDeliveryContextDocument apdDelContext=null;
		apdDelContext= initializeAPDDeliveryContextLDA();
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessage", "es-L4",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType=AssignmentDeliveryEmailReqType.Factory.parse(msg);
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessage().getPoi();

		Mockito.doReturn(null).when(customSettingHelper).getUserCustomSetting("LDAL4PS1", "L4", AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP,
				AssignmentDeliveryConstants.SETTING_CLAIM_MASK);
		Mockito.verify(customSettingHelper).initialize();
		Mockito.verify(customSettingHelper).getUserCustomSetting("LDAL4PS1", "L4", AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP,
				AssignmentDeliveryConstants.SETTING_CLAIM_MASK);
		Mockito.verify(customSettingHelper).getCompanyCustomSetting("L4", AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP,
				AssignmentDeliveryConstants.SETTING_CLAIM_MASK);
		
	} 
	
	@Test
	public void customSettingUserIdNullTest() throws Exception{
		APDDeliveryContextDocument apdDelContext = null;
		apdDelContext= initializeAPDDeliveryContextLDA();
		
		apdDelContext.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
		.getAPDCommonInfo().getTargetUserInfo().getUserInfo().setUserID(null);
				
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessage", "es-L4",true);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType = AssignmentDeliveryEmailReqType.Factory.parse(msg);
		
		final String poiMsg = asgnDeliveryEmailRqType.getEmailMessage().getPoi();

		Mockito.doReturn(null).when(customSettingHelper).getUserCustomSetting("OA058028", "L4", AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP,
				AssignmentDeliveryConstants.SETTING_CLAIM_MASK);
		Mockito.verify(customSettingHelper).initialize();
		
		Mockito.verify(customSettingHelper).getCompanyCustomSetting("L4", AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP,
				AssignmentDeliveryConstants.SETTING_CLAIM_MASK);
		
	} 
	@Test 
	public void getDamageMemoDRPTest() throws Exception{
		APDDeliveryContextDocument apdDelContext = null;
		apdDelContext = initializeAPDDeliveryContextLDA();
		
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessageDRP", "es-L4",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType = AssignmentDeliveryEmailReqType.Factory.parse(msg);
        String damageMemo = asgnDeliveryEmailRqType.getEmailMessageDRP().getDamageMemo();
        assertEquals("Test Damage Memo", damageMemo);
		
	}
	
	@Test 
	public void getDamageMemoNonDRPTest() throws Exception{
		APDDeliveryContextDocument apdDelContext = null;
		apdDelContext = initializeAPDDeliveryContextLDA();
		
		final String msg = aedu.createMessageXml(apdDelContext, "testLink","EmailMessage", "es-L4",false);
		AssignmentDeliveryEmailReqType asgnDeliveryEmailRqType = AssignmentDeliveryEmailReqType.Factory.parse(msg);
        String damageMemo = asgnDeliveryEmailRqType.getEmailMessage().getDamageMemo();
        assertEquals("Test Damage Memo", damageMemo);
		
	}
	
	private File loadFileForTest(String fileRelativePath)
    {
      return new File(makeTestFilePath(fileRelativePath));
    }

    private String makeTestFilePath(String fileRelativePath)
    {
      // URL put's %20 in place of space but need the space for the file system.
      URL url = this.getClass().getResource("/" + fileRelativePath);
      return url.getFile().replace("%20", " ");
    }

    private APDDeliveryContextDocument initializeAPDDeliveryContext() throws Exception {
        final APDDeliveryContextDocument doc =APDDeliveryContextDocument.Factory.parse(loadFileForTest("APDDeliveryContextDocument.xml"));
        return doc;
    }
    
    private APDDeliveryContextDocument initializeAPDDeliveryContextLDA() throws Exception {
        final APDDeliveryContextDocument doc =APDDeliveryContextDocument.Factory.parse(loadFileForTest("APDDeliveryContextDocument_L4.xml"));
        return doc;
    }
}