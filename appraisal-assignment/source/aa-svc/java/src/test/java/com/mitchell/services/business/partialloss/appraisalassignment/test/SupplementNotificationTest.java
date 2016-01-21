package com.mitchell.services.business.partialloss.appraisalassignment.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mitchell.schemas.appraisalassignment.supplementrequestemail.EstimatePlatformType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SupplementRequestDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.GTMotiveTextEmailFormatter;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementNotification;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementReqBO;
import com.mitchell.systemconfiguration.SystemConfiguration;



@RunWith(PowerMockRunner.class)
@PrepareForTest({SystemConfiguration.class,SupplementNotification.class})
public class SupplementNotificationTest {
	
	@Mock
	GTMotiveTextEmailFormatter gTMotiveTextEmailFormatter;
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void testRetrieveEmailNotificationDoc() throws Exception {
		PowerMockito.mockStatic(SystemConfiguration.class);	
		when(SystemConfiguration.getSettingValue(anyString())).thenReturn("");		
		SupplementRequestDocument supplementRequestDocument= SupplementRequestDocument.Factory.newInstance();
		supplementRequestDocument.addNewSupplementRequest(); 		
		supplementRequestDocument.getSupplementRequest().addNewAdminInfo();
		supplementRequestDocument.getSupplementRequest().getAdminInfo().setClaimNumber("132");
		
		supplementRequestDocument.getSupplementRequest().setEstimateType(EstimatePlatformType.GTMOTIVE);
		SupplementNotification supplementNotification=PowerMockito.spy(new SupplementNotification());		
		SupplementReqBO suppReqBO= new SupplementReqBO();	
		suppReqBO.setEmail(true);
		suppReqBO.setCulture("es-ES");
		PowerMockito.whenNew(GTMotiveTextEmailFormatter.class).withNoArguments().thenReturn(gTMotiveTextEmailFormatter);
		when(gTMotiveTextEmailFormatter.createTextEmail(supplementRequestDocument,"es-ES")).thenReturn("GTMOTIVE");
		String result=supplementNotification.retrieveNotificationDoc(suppReqBO, supplementRequestDocument);
		assertEquals("GTmotive estiamte", "GTMOTIVE", result);		
	  
	}
	

}
