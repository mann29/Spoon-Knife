package com.mitchell.services.business.questionnaireevaluation.ejb;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.evaluationdetails.MitchellEvaluationDetailsDocument;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.services.business.questionnaireevaluation.ejb.QuestionnaireEvaluationEJBRemote;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.systemconfiguration.SystemConfiguration;

public class TestQuestionnaireEvalEJB {

	String evaluationID = "testing000062";
	String evaluationType = "LOSSEVALUATION";
	String workItemID ="testing0001";
	String providerUrl = null;
	String jndiName = null;
	String contextFactory = null;
	QuestionnaireEvaluationEJBRemote ejb = null;
	Context ctx = null;
	  
	public TestQuestionnaireEvalEJB(){}	
	
	@Before
	public void setup()throws Exception{
		
	    providerUrl = SystemConfiguration
	        .getSettingValue("/QuestionnaireEvaluationService/QEJavaClient/ProviderUrl");
	    jndiName = SystemConfiguration
	        .getSettingValue("/QuestionnaireEvaluationService/QEJavaClient/EJBJndi");
	    contextFactory = SystemConfiguration
	        .getSettingValue("/QuestionnaireEvaluationService/QEJavaClient/JndiFactory");
	    
	    Properties environment = new Properties();
	    environment.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
	    environment.put(Context.PROVIDER_URL, providerUrl);
	    ctx = new InitialContext(environment);
	    ejb = (QuestionnaireEvaluationEJBRemote) ctx.lookup(jndiName);
	}
	
	@After
	public void tearDown(){	
		ejb = null;
	}
	
	@Test
	public void saveEvaluation()throws Exception{		
		MitchellEvaluationDetailsDocument evaluationDoc = MitchellEvaluationDetailsDocument.Factory.parse(new File("src/test/resources/EvaluationXml.xml"));
		String evaluationDetailsXmlData = evaluationDoc.toString();
		UserInfoDocument userInfoDoc = TestQuestionnaireEvalEJB.getUserInfo(345976);		
		
		Map map = ejb.saveEvaluation(evaluationID,
							evaluationType,
							evaluationDetailsXmlData,
							userInfoDoc);
		
		assertNotNull(map.get(QuestionnaireEvaluationConstants.RESPONSE_KEY_DOCUMENTID));
			
	}
	
	
	@Test
	public void saveEvaluationWithClaim()throws Exception{
		
		MitchellEvaluationDetailsDocument evaluationDoc = MitchellEvaluationDetailsDocument.Factory.parse(new File("src/test/resources/EvaluationXml.xml"));
		String evaluationDetailsXmlData = evaluationDoc.toString();
		UserInfoDocument userInfoDoc = TestQuestionnaireEvalEJB.getUserInfo(345976);
		
		//long claimID=1000062712;
		long claimID= 100002017888L;
		
		//long suffixID=1000073100;
		long suffixID=100001813165L;
		
		
		Map responseMap = ejb.saveEvaluation(claimID,
							suffixID,
							evaluationType,
							evaluationDetailsXmlData,
							userInfoDoc);
		assertNotNull(responseMap.get(QuestionnaireEvaluationConstants.RESPONSE_KEY_DOCUMENTID));         
                 
	}
	
	@Test
	public void updateEvaluation()throws Exception{
		Map responseMap  = null;
		long documentId = 0;
		
		MitchellEvaluationDetailsDocument evaluationDoc = MitchellEvaluationDetailsDocument.Factory.parse(new File("src/test/resources/EvaluationXml.xml"));
		String evaluationDetailsXmlData = evaluationDoc.toString();
		UserInfoDocument userInfoDoc = TestQuestionnaireEvalEJB.getUserInfo(345976);
		
		documentId = 100001886597L;
		
		long claimID=100002017943L;
		long suffixID=100001813220L;
		long tcn=0;
		
		responseMap = ejb.updateEvaluation(documentId,
				evaluationType,
				evaluationDetailsXmlData,
                userInfoDoc,
                claimID,
                suffixID ,
                tcn); 
		
		assertNotNull(responseMap.get(QuestionnaireEvaluationConstants.RESPONSE_KEY_DOCUMENTID));           
		
	}
	
	@Test
	public void deleteEvaluation()throws Exception{
		
		 Map responseMap;
		 UserInfoDocument userInfoDoc = TestQuestionnaireEvalEJB.getUserInfo(345976);
		 evaluationID = "preeti89102";
		 
		 try{
		 responseMap = ejb.deleteEvaluation(userInfoDoc, 
				 			evaluationID
				 			);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	}
	
	@Test
	public void linkQuestionnaireEvaluationToClaim()throws Exception{
	
		UserInfoDocument userInfoDoc = TestQuestionnaireEvalEJB.getUserInfo(345976);
		long claimID=100002017888L;
		long suffixID=100001813165L;
		
		String claimNumber = "55-ARUN-";
		evaluationID = "priya89102";
		
		int linkStatus = ejb.linkQuestionnaireEvaluationToClaim(evaluationID,
										claimID,
										suffixID,
										claimNumber,
										userInfoDoc);
		
		assertNotSame(linkStatus,1);
		
	}
	
	private static UserInfoDocument getUserInfo(long orgId)
    throws Exception {
		UserInfoDocument userInfo = null;
		try {
			UserInfoServiceEJBRemote remote = UserInfoClient.getUserInfoEJB();
			userInfo = remote.getUserInfo(orgId);
			
			if(userInfo == null) {
					throw new Exception(" Cannot find UserInfo for " + orgId);                
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return userInfo;    
	}
}