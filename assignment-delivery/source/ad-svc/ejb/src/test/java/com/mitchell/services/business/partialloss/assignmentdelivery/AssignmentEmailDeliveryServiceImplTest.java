package com.mitchell.services.business.partialloss.assignmentdelivery;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.xmlbeans.XmlException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.dao.CultureDAO;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.AssignmentEmailDeliveryHandler;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.service.AssignmentEmailDeliveryServiceImpl;

public class AssignmentEmailDeliveryServiceImplTest {

	AssignmentEmailDeliveryServiceImpl asgEmailDelService;
	AssignmentEmailDeliveryHandler assignmentEmailDeliveryHandlerDRP;
	AssignmentEmailDeliveryHandler assignmentEmailDeliveryHandler;
	CultureDAO cultureDAO;
	
	@Before
	public void setup() throws MitchellException{
		asgEmailDelService=new AssignmentEmailDeliveryServiceImpl();
		assignmentEmailDeliveryHandlerDRP=Mockito.mock(AssignmentEmailDeliveryHandler.class);
		assignmentEmailDeliveryHandler=Mockito.mock(AssignmentEmailDeliveryHandler.class);
		cultureDAO=Mockito.mock(CultureDAO.class);
		asgEmailDelService.setAssignmentEmailDeliveryHandlerDRP(assignmentEmailDeliveryHandlerDRP);
		asgEmailDelService.setAssignmentEmailDeliveryHandler(assignmentEmailDeliveryHandler);
		asgEmailDelService.setCultureDAO(cultureDAO);
	}
	@After
	public void destroy(){
		asgEmailDelService=null;
	}
	
	
	@Test
	public void deliveryAssignmentEmailNotificationTest_Original_Premium() throws MitchellException{
		try {
			APDDeliveryContextDocument aPDDeliveryContextDocument=APDDeliveryContextDocument.Factory.parse(new File("src/test/resources/APDDeliveryContextDocument.xml"));
			Mockito.doNothing().when(assignmentEmailDeliveryHandlerDRP).deliverIAEmail((APDDeliveryContextDocument)Mockito.anyObject(), Mockito.anyBoolean(), Mockito.anyString());
			asgEmailDelService.deliveryAssignmentEmailNotification(aPDDeliveryContextDocument, new ArrayList<String>(), "IA_PREMIUM_EMAIL_TYPE");
			Mockito.when(cultureDAO.getCultureByCompany(Mockito.anyString())).thenReturn(null);
			Mockito.verify(assignmentEmailDeliveryHandlerDRP).deliverIAEmail(aPDDeliveryContextDocument,true, "en-US");
			
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deliveryAssignmentEmailNotificationTest_Original_Basic() throws MitchellException{
		try {
			APDDeliveryContextDocument aPDDeliveryContextDocument=APDDeliveryContextDocument.Factory.parse(new File("src/test/resources/APDDeliveryContextDocument.xml"));
			Mockito.doNothing().when(assignmentEmailDeliveryHandler).deliverIAEmail((APDDeliveryContextDocument)Mockito.anyObject(), Mockito.anyBoolean(), Mockito.anyString());
			asgEmailDelService.deliveryAssignmentEmailNotification(aPDDeliveryContextDocument, new ArrayList<String>(), "IA_BASIC_EMAIL_TYPE");
			Mockito.when(cultureDAO.getCultureByCompany(Mockito.anyString())).thenReturn(null);
			Mockito.verify(assignmentEmailDeliveryHandler).deliverIAEmail(aPDDeliveryContextDocument,true, "en-US");
			
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deliveryAssignmentEmailNotificationTest_Supplement() throws MitchellException{
		try {
			APDDeliveryContextDocument aPDDeliveryContextDocument=APDDeliveryContextDocument.Factory.parse(new File("src/test/resources/APDDeliveryContextDocument.xml"));
			aPDDeliveryContextDocument.getAPDDeliveryContext().setMessageType("SUPPLEMENT");
			asgEmailDelService.deliveryAssignmentEmailNotification(aPDDeliveryContextDocument, new ArrayList<String>(), "IA_PREMIUM_EMAIL_TYPE");
			Mockito.when(cultureDAO.getCultureByCompany(Mockito.anyString())).thenReturn(null);
			Mockito.verify(assignmentEmailDeliveryHandlerDRP,Mockito.never()).deliverIAEmail(aPDDeliveryContextDocument,true, "en-US");
			
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
