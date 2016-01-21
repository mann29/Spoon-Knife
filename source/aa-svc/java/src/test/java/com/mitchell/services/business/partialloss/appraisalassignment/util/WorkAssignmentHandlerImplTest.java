package com.mitchell.services.business.partialloss.appraisalassignment.util;


import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.ItineraryViewDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentType;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.WorkAssignmentProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.WorkAssignmentProxyImpl;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;
import com.mitchell.services.core.workassignment.bo.WorkAssignmentCLOB;


import org.apache.cxf.binding.corba.wsdl.Exception;
import org.apache.xmlbeans.*;

@RunWith(MockitoJUnitRunner.class)
public class WorkAssignmentHandlerImplTest {
	
	WorkAssignmentHandlerImpl workAssgnHandlImpl=null;
	WorkAssignment workAssignment=null;
	WorkAssignment workAssignmentreturn=null;
	WorkAssignment workAssignmentPass=null;
	WorkAssignmentDocument workAssignmentDocument=null;
	Logger loger=null ; 
	UserInfoDocument loggedInUserInfoDoc=null;
	WorkAssignmentProxy workAssignmentProxy=null;
	WorkAssignmentProxy workAssignmentProxyImp=null;
	ItineraryViewDocument itineraryXML =null;
	AASErrorLogUtil	errorLogUtil=null;
	
	
	String newDispositionCode="RESCHEDULE";
	String reasonCode="Reason";
	String comment=null;
	long taskId=0;
	String event="RESCHEDULE";
	String workAssignmentCLOB="";
	
	
	@Before
	public void setUp() throws  MitchellException 
	{
		workAssgnHandlImpl =new WorkAssignmentHandlerImpl();
		workAssignmentPass = createWADoc();
		loggedInUserInfoDoc = Mockito.mock(UserInfoDocument.class);
		loger =Mockito.mock(Logger.class);
		workAssignmentProxy= Mockito.mock(WorkAssignmentProxy.class);
		errorLogUtil=Mockito.mock(AASErrorLogUtil.class);
		
	}

	@Test
	public void saveWorkAssignmentStatusTest() throws MitchellException, XmlException, IOException
	
	{
	
		
		workAssgnHandlImpl.setWorkAssignmentProxy(workAssignmentProxy);
		workAssignment=new WorkAssignment();
		
		
		
		loggedInUserInfoDoc = UserInfoDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("UserInfoDoc.xml"));
		itineraryXML= ItineraryViewDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("ItineraryView.xml"));
		
		workAssignmentCLOB  = WorkAssignmentDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("clob.xml")).toString();
		workAssignmentDocument=WorkAssignmentDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("clob.xml"));
		workAssignmentPass.setWorkAssignmentCLOBB(workAssignmentCLOB);
		Mockito.when(workAssignmentProxy.save((WorkAssignmentDocument)Mockito.anyObject())).thenReturn(workAssignmentPass);
		workAssignmentreturn=workAssgnHandlImpl.saveWorkAssignmentStatus(workAssignmentPass,newDispositionCode, reasonCode, comment, loggedInUserInfoDoc, itineraryXML);
		Long waWorkAssignmentHistID = workAssignmentProxy.saveWorkAssignmentHist(taskId,event);
		Mockito.verify(workAssignmentProxy,times(2)).saveWorkAssignmentHist(taskId,event);
		
		assertNotNull(workAssignmentProxy.save(workAssignmentDocument));
		assertNotNull(waWorkAssignmentHistID);
		assertNotNull(workAssignmentreturn);
		assertEquals("OPENED", workAssignmentreturn.getWorkAssignmentStatus());
		
		
	}
	
	@Test(expected = NullPointerException.class)
	public void saveWorkAssignmentStatusTestException() throws  MitchellException, XmlException, IOException 
	{
			
	
		NullPointerException ne = new NullPointerException();
		errorLogUtil.logAndThrowError(1, "", "", "", "", "", ne, loggedInUserInfoDoc, "", "");
		

		workAssgnHandlImpl.setWorkAssignmentProxy(workAssignmentProxy);
		workAssignment=new WorkAssignment();
		
		
		
		loggedInUserInfoDoc = UserInfoDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("UserInfoDoc.xml"));
		itineraryXML= ItineraryViewDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("ItineraryView.xml"));
		
		workAssignmentCLOB  = WorkAssignmentDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("clob.xml")).toString();
		workAssignmentDocument=WorkAssignmentDocument.Factory.parse(this.getClass().getClassLoader().getResourceAsStream("clob.xml"));
		workAssignmentPass.setWorkAssignmentCLOBB(workAssignmentCLOB);
		
		Mockito.when(workAssignmentProxy.save((WorkAssignmentDocument)Mockito.anyObject())).thenThrow(ne);
		
		workAssignmentreturn=workAssgnHandlImpl.saveWorkAssignmentStatus(workAssignmentPass,newDispositionCode, reasonCode, comment, loggedInUserInfoDoc, itineraryXML);
		
		
		Long waWorkAssignmentHistID = workAssignmentProxy.saveWorkAssignmentHist(taskId,event);
		Mockito.verify(workAssignmentProxy,times(2)).saveWorkAssignmentHist(taskId,event);
		Mockito.verify(errorLogUtil,times(1)).logAndThrowError(1, "", "", "", "", "", ne, loggedInUserInfoDoc, "", "");
		
		
		assertNotNull(workAssignmentProxy.save(workAssignmentDocument));
		assertNotNull(waWorkAssignmentHistID);
		assertNotNull(workAssignmentreturn);
		assertEquals("OPENED", workAssignmentreturn.getWorkAssignmentStatus());
		
		

		
	}
	
	
    protected WorkAssignment createWADoc() {
    	
    	WorkAssignment workAssgnment = new WorkAssignment();
    	workAssgnment.setTaskID(12345L);
    	workAssgnment.setWorkAssignmentStatus("OPENED");
    	workAssgnment.setId(new Long(44));

    	workAssgnment.setCreatedBy("ADMIN");
        workAssgnment.setUpdatedBy("TEST");
        workAssgnment.setClaimExposureID(123456789L);
        workAssgnment.setReferenceId(9595655L);
        workAssgnment.setComments("comments");
        workAssgnment.setNotes("My Comments");
        WorkAssignmentDocument waDoc  = WorkAssignmentDocument.Factory.newInstance();
        
    	workAssgnment.setWorkAssignmentCLOBB(waDoc.toString());
    	
    	WorkAssignmentCLOB workAssignmentCLOB = new WorkAssignmentCLOB();
    	workAssignmentCLOB.setStringCLOB(workAssgnment.getWorkAssignmentCLOBB());
    	workAssignmentCLOB.setCreatedBy("TEST");
    	workAssignmentCLOB.setCreatedDate(Calendar.getInstance().getTime());
    	workAssignmentCLOB.setWorkAssignment(workAssgnment);
       
    	workAssgnment.setWorkAssignmentCLOB(workAssignmentCLOB);
    	
    	return workAssgnment;
    }
	@After
	public void destroy() throws  MitchellException 
	{
		workAssgnHandlImpl=null;
	}
}
