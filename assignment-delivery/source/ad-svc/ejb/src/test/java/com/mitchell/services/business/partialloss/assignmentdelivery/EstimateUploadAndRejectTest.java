package com.mitchell.services.business.partialloss.assignmentdelivery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.Assert;

import org.apache.xmlbeans.XmlException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.impl.AbstractAssignmentEmailHandler;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.impl.AssignmentEmailDeliveryHandlerDRPImpl;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.SystemConfigProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.SystemConfigProxyImpl;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.XsltTransformer;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.XsltTransformerImpl;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.AssignmentEmailDeliveryUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.CustomSettingHelperImpl;
import com.mitchell.services.core.customsettings.ejb.CustomSettingsEJBRemote;
import com.mitchell.services.core.customsettings.types.xml.Profile;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;

public class EstimateUploadAndRejectTest {
	
	private AbstractAssignmentEmailHandler assignEmailDeliverHandlerDrpImpl = null;
	private final String xmlFileForEstimateUpload = "APDDeliveryContext_L4_ForEstimateDRPUploadSuccess.xml";
	private AssignmentEmailDeliveryUtils assignmentEmailUtil = null;
	private CustomSettingsEJBRemote ejbRemote = null;
	private SystemConfigProxy systemConfigProxy = null;
	private XsltTransformer xsltTransformer = null;
	private CustomSettingHelperImpl customSettingHelperImpl = null;
	
	@Before
	public void setUp(){
		
		assignmentEmailUtil = mock(AssignmentEmailDeliveryUtils.class);
		ejbRemote = mock(CustomSettingsEJBRemote.class);
		systemConfigProxy = mock(SystemConfigProxy.class);
		xsltTransformer = mock(XsltTransformer.class);
		
		customSettingHelperImpl = new CustomSettingHelperImpl();
		customSettingHelperImpl.setEjb(ejbRemote);
		
		assignmentEmailUtil.setCustomSettingHelper(customSettingHelperImpl);
		
		assignEmailDeliverHandlerDrpImpl = new AssignmentEmailDeliveryHandlerDRPImpl();
		assignEmailDeliverHandlerDrpImpl.setAssignmentEmailDevlieryUtils(assignmentEmailUtil);
		assignEmailDeliverHandlerDrpImpl.setCustomSettingHelper(customSettingHelperImpl);
		assignEmailDeliverHandlerDrpImpl.setSystemConfigProxy(systemConfigProxy);
		assignEmailDeliverHandlerDrpImpl.setXsltTransformer(xsltTransformer);
	}
	
	
	@Test
	public void createEmailMessage4EstimateUploadWithCustomSetting() throws Exception{
		String apdcontext = readFileIntoString(this.getClass()
				.getClassLoader()
				.getResourceAsStream(xmlFileForEstimateUpload));

		APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
				.parse(apdcontext);
		Profile mockProfile = mock(Profile.class);
		mockProfile.setId(1245);

		SettingValue settingValue = SettingValue.Factory.newInstance();
		settingValue.setValue("SAMPLE");
		
		when(ejbRemote.getDefaultProfile("L4", "L4", "COMPANY")).thenReturn(mockProfile);
		when(ejbRemote.getValue("L4", "L4", "COMPANY", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME, AssignmentDeliveryConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME)).thenReturn(settingValue);
		when(systemConfigProxy.getStringValue(AssignmentDeliveryConstants.SET_FILE_XSLT_PATH)).thenReturn("src/test/resources");
		when(xsltTransformer.transformXmlString("src/test/resources/AssignmentEmail.xslt", null)).thenReturn("EMAIL");
		
		String emailMessage = assignEmailDeliverHandlerDrpImpl.createEmailMessage4UploadSuccess(apdContextDoc, "fr-CA_en-US");
		
		verify(ejbRemote, times(1)).getDefaultProfile("L4", "L4", "COMPANY");
		verify(ejbRemote, times(1)).getValue("L4", "L4", "COMPANY", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME, AssignmentDeliveryConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME);
		verify(systemConfigProxy, times(1)).getStringValue(AssignmentDeliveryConstants.SET_FILE_XSLT_PATH);
		verify(xsltTransformer, times(1)).transformXmlString("src/test/resources/AssignmentEmail.xslt", null);
	}
	
	
	@Test
	public void createEmailMessageXml4AlertSuccessTest() throws Exception{
		String apdcontext = readFileIntoString(this.getClass()
				.getClassLoader()
				.getResourceAsStream(xmlFileForEstimateUpload));
		APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
				.parse(apdcontext);
		AssignmentEmailDeliveryHandlerDRPImpl assignEmailDeliverHandlerDrpImpl = new AssignmentEmailDeliveryHandlerDRPImpl();
		assignEmailDeliverHandlerDrpImpl.setCustomSettingHelper(customSettingHelperImpl);
		
		SystemConfigProxyImpl systemConfigProxyImpl = mock(SystemConfigProxyImpl.class);
		
		
		Profile mockProfile = mock(Profile.class);
		mockProfile.setId(1245);

		SettingValue settingValue = SettingValue.Factory.newInstance();
		settingValue.setValue("SAMPLE");
		
		when(ejbRemote.getDefaultProfile("L4", "L4", "COMPANY")).thenReturn(mockProfile);
		when(ejbRemote.getValue("L4", "L4", "COMPANY", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP, AssignmentDeliveryConstants.SETTING_SUFFIX_LABEL)).thenReturn(settingValue);
		
		SettingValue settingClaimMask = SettingValue.Factory.newInstance();
		settingClaimMask.setValue("(\\w{1,9})-(\\w{1,10})");
		
		when(ejbRemote.getDefaultProfile("L4TESTPS", "L4", "USER")).thenReturn(mockProfile);
		when(ejbRemote.getValue("L4TESTPS", "L4", "USER", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP, AssignmentDeliveryConstants.SETTING_CLAIM_MASK)).thenReturn(settingClaimMask);
		
		when(systemConfigProxyImpl.getStringValue(AssignmentDeliveryConstants.STATIC_IMAGE_BASE_URL)).thenReturn("STATIC_URL");
		AssignmentEmailDeliveryUtils assignDeliveryUtils = AssignmentEmailDeliveryUtils.getInstance();
		assignDeliveryUtils.setSystemConfigProxyImpl(systemConfigProxyImpl);
		
		String emailMessageXml = assignDeliveryUtils.createEmailMessageXml4AlertSuccess(apdContextDoc, "not required");
		
		Assert.assertEquals(emailMessageXml.contains("STATIC_URL"), true);
		Assert.assertEquals(emailMessageXml.contains("<StaticImageBaseUrl>"), true);
		Assert.assertEquals(emailMessageXml.contains("<RecipientName>"), true);
		Assert.assertEquals(emailMessageXml.contains("<CurrentYear>"), true);
		Assert.assertEquals(emailMessageXml.contains("<Claim>"), true);
		Assert.assertEquals(emailMessageXml.contains("<Suffix>"), true);
		Assert.assertEquals(emailMessageXml.contains("<SuffixLabel>SAMPLE"), true);
		Assert.assertEquals(emailMessageXml.contains("<CoName>"), true);
		
		verify(ejbRemote, times(1)).getDefaultProfile("L4", "L4", "COMPANY");
		verify(ejbRemote, times(1)).getValue("L4", "L4", "COMPANY", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP, AssignmentDeliveryConstants.SETTING_SUFFIX_LABEL);
		verify(ejbRemote, times(1)).getDefaultProfile("L4TESTPS", "L4", "USER");
		verify(ejbRemote, times(1)).getValue("L4TESTPS", "L4", "USER", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP, AssignmentDeliveryConstants.SETTING_CLAIM_MASK);
		verify(systemConfigProxyImpl, times(1)).getStringValue(AssignmentDeliveryConstants.STATIC_IMAGE_BASE_URL);
		
	}
	
	
	@Test
	public void createEmailMessageXml4AlertFailTest() throws Exception{
		String apdcontext = readFileIntoString(this.getClass()
				.getClassLoader()
				.getResourceAsStream(xmlFileForEstimateUpload));
		APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
				.parse(apdcontext);
		AssignmentEmailDeliveryHandlerDRPImpl assignEmailDeliverHandlerDrpImpl = new AssignmentEmailDeliveryHandlerDRPImpl();
		assignEmailDeliverHandlerDrpImpl.setCustomSettingHelper(customSettingHelperImpl);
		
		SystemConfigProxyImpl systemConfigProxyImpl = mock(SystemConfigProxyImpl.class);
		
		apdContextDoc.getAPDDeliveryContext().getAPDAlertInfo().setMessage("FAIL_TEST");
		Profile mockProfile = mock(Profile.class);
		mockProfile.setId(1245);

		SettingValue settingValue = SettingValue.Factory.newInstance();
		settingValue.setValue("SAMPLE");
		
		when(ejbRemote.getDefaultProfile("L4", "L4", "COMPANY")).thenReturn(mockProfile);
		when(ejbRemote.getValue("L4", "L4", "COMPANY", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP, AssignmentDeliveryConstants.SETTING_SUFFIX_LABEL)).thenReturn(settingValue);
		
		SettingValue settingClaimMask = SettingValue.Factory.newInstance();
		settingClaimMask.setValue("(\\w{1,9})-(\\w{1,10})");
		
		when(ejbRemote.getDefaultProfile("L4TESTPS", "L4", "USER")).thenReturn(mockProfile);
		when(ejbRemote.getValue("L4TESTPS", "L4", "USER", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP, AssignmentDeliveryConstants.SETTING_CLAIM_MASK)).thenReturn(settingClaimMask);
		
		when(systemConfigProxyImpl.getStringValue(AssignmentDeliveryConstants.STATIC_IMAGE_BASE_URL)).thenReturn("STATIC_URL");
		AssignmentEmailDeliveryUtils assignDeliveryUtils = AssignmentEmailDeliveryUtils.getInstance();
		assignDeliveryUtils.setSystemConfigProxyImpl(systemConfigProxyImpl);
		
		String emailMessageXml = assignDeliveryUtils.createEmailMessageXml4AlertFail(apdContextDoc, "not required");
		
		Assert.assertEquals(emailMessageXml.contains("STATIC_URL"), true);
		Assert.assertEquals(emailMessageXml.contains("<StaticImageBaseUrl>"), true);
		Assert.assertEquals(emailMessageXml.contains("<RecipientName>"), true);
		Assert.assertEquals(emailMessageXml.contains("<CurrentYear>"), true);
		Assert.assertEquals(emailMessageXml.contains("<Claim>"), true);
		Assert.assertEquals(emailMessageXml.contains("<Suffix>"), true);
		Assert.assertEquals(emailMessageXml.contains("<SuffixLabel>SAMPLE"), true);
		Assert.assertEquals(emailMessageXml.contains("<Message>FAIL_TEST"), true);
		Assert.assertEquals(emailMessageXml.contains("<CoName>"), true);
		
		verify(ejbRemote, times(1)).getDefaultProfile("L4", "L4", "COMPANY");
		verify(ejbRemote, times(1)).getValue("L4", "L4", "COMPANY", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP, AssignmentDeliveryConstants.SETTING_SUFFIX_LABEL);
		verify(ejbRemote, times(1)).getDefaultProfile("L4TESTPS", "L4", "USER");
		verify(ejbRemote, times(1)).getValue("L4TESTPS", "L4", "USER", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP, AssignmentDeliveryConstants.SETTING_CLAIM_MASK);
		verify(systemConfigProxyImpl, times(1)).getStringValue(AssignmentDeliveryConstants.STATIC_IMAGE_BASE_URL);
	}
	
	@Test
	public void createEmailMessageXml4AlertFailMessageTest() throws Exception{
		String apdcontext = readFileIntoString(this.getClass()
				.getClassLoader()
				.getResourceAsStream(xmlFileForEstimateUpload));
		APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
				.parse(apdcontext);
		AssignmentEmailDeliveryHandlerDRPImpl assignEmailDeliverHandlerDrpImpl = new AssignmentEmailDeliveryHandlerDRPImpl();
		assignEmailDeliverHandlerDrpImpl.setCustomSettingHelper(customSettingHelperImpl);
		
		SystemConfigProxyImpl systemConfigProxyImpl = mock(SystemConfigProxyImpl.class);
		
		apdContextDoc.getAPDDeliveryContext().getAPDAlertInfo().setMessage(null);
		Profile mockProfile = mock(Profile.class);
		mockProfile.setId(1245);

		SettingValue settingValue = SettingValue.Factory.newInstance();
		settingValue.setValue("SAMPLE");
		
		when(ejbRemote.getDefaultProfile("L4", "L4", "COMPANY")).thenReturn(mockProfile);
		when(ejbRemote.getValue("L4", "L4", "COMPANY", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP, AssignmentDeliveryConstants.SETTING_SUFFIX_LABEL)).thenReturn(settingValue);
		
		SettingValue settingClaimMask = SettingValue.Factory.newInstance();
		settingClaimMask.setValue("(\\w{1,9})-(\\w{1,10})");
		
		when(ejbRemote.getDefaultProfile("L4TESTPS", "L4", "USER")).thenReturn(mockProfile);
		when(ejbRemote.getValue("L4TESTPS", "L4", "USER", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP, AssignmentDeliveryConstants.SETTING_CLAIM_MASK)).thenReturn(settingClaimMask);
		
		when(systemConfigProxyImpl.getStringValue(AssignmentDeliveryConstants.STATIC_IMAGE_BASE_URL)).thenReturn("STATIC_URL");
		AssignmentEmailDeliveryUtils assignDeliveryUtils = AssignmentEmailDeliveryUtils.getInstance();
		assignDeliveryUtils.setSystemConfigProxyImpl(systemConfigProxyImpl);
		
		String emailMessageXml = assignDeliveryUtils.createEmailMessageXml4AlertFail(apdContextDoc, "not required");
		
		Assert.assertEquals(emailMessageXml.contains("STATIC_URL"), true);
		Assert.assertEquals(emailMessageXml.contains("<StaticImageBaseUrl>"), true);
		Assert.assertEquals(emailMessageXml.contains("<RecipientName>"), true);
		Assert.assertEquals(emailMessageXml.contains("<CurrentYear>"), true);
		Assert.assertEquals(emailMessageXml.contains("<Claim>"), true);
		Assert.assertEquals(emailMessageXml.contains("<Suffix>"), true);
		Assert.assertEquals(emailMessageXml.contains("<SuffixLabel>SAMPLE"), true);
		Assert.assertEquals(emailMessageXml.contains("<Message>"), false);
		
		verify(ejbRemote, times(1)).getDefaultProfile("L4", "L4", "COMPANY");
		verify(ejbRemote, times(1)).getValue("L4", "L4", "COMPANY", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP, AssignmentDeliveryConstants.SETTING_SUFFIX_LABEL);
		verify(ejbRemote, times(1)).getDefaultProfile("L4TESTPS", "L4", "USER");
		verify(ejbRemote, times(1)).getValue("L4TESTPS", "L4", "USER", mockProfile.getId(), AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP, AssignmentDeliveryConstants.SETTING_CLAIM_MASK);
		verify(systemConfigProxyImpl, times(1)).getStringValue(AssignmentDeliveryConstants.STATIC_IMAGE_BASE_URL);
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
