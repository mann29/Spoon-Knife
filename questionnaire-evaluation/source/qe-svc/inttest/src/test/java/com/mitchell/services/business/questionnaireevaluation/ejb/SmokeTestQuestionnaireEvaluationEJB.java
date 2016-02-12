package com.mitchell.services.test.GMR;

import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.evaluationdetails.MitchellEvaluationDetailsDocument;
import com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkDocument;
import com.mitchell.services.business.questionnaireevaluation.client.QuestionnaireEvaluationClient;
import com.mitchell.services.business.questionnaireevaluation.ejb.QuestionnaireEvaluationEJBRemote;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;

/**
 * @author ps101318
 * 
 */
 
public class SmokeTestQuestionnaireEvaluationEJB {
	
	QuestionnaireEvaluationEJBRemote ejb = null;		
	UserInfoServiceEJBRemote userInfoRemote = null;
	  
	public SmokeTestQuestionnaireEvaluationEJB(){}	
	   
	@Before
	public void setup()throws Exception{		
	    ejb = QuestionnaireEvaluationClient.getEJB();	    
	    userInfoRemote = UserInfoClient.getUserInfoEJB();
	}
	
	@After
	public void tearDown(){
		ejb = null;
	}
		
	//enrtry goes in table clm_xtrnl_doc_key and clm_qustnnre_evltn
	@Test	
	public void saveEvaluationStandalone()throws Exception{
		
		String methodName = "saveEvaluationStandalone";
		String evaluationID = null;
		String evaluationType = null;
		String evaluationDetailsXmlData = null;
		UserInfoDocument userInfoDoc = null;
		MitchellEvaluationDetailsDocument evaluationDoc = null;
		try{
			evaluationID = "preeti891";
			evaluationType = "LOSSEVALUATION";
			
			evaluationDoc = MitchellEvaluationDetailsDocument.Factory.parse(new File("src/test/resources/GMR/SaveEvlNoClaim.xml"));
			evaluationDetailsXmlData = evaluationDoc.toString();
			userInfoDoc = getUserInfo(344757);
			
			ejb.saveEvaluation(evaluationID,
						evaluationType,
						evaluationDetailsXmlData,
						userInfoDoc);
			logTestSuccess(methodName);
			
		}catch(Exception e){
			logTestFail(methodName);
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void saveEvaluationWithClaim()throws Exception{
		
		String methodName = "saveEvaluationWithClaim";
		long claimID = 0;
		long suffixID = 0;		
		String evaluationType = null;
		String evaluationDetailsXmlData = null;
		UserInfoDocument userInfoDoc = null;
		
		try{
			claimID = 814093;
			suffixID = 615367;		
			evaluationType = "TEST";
			MitchellEvaluationDetailsDocument evaluationDoc = MitchellEvaluationDetailsDocument.Factory.parse(new File("src/test/resources/GMR/SaveEvlWithClaim.xml"));
			evaluationDetailsXmlData = evaluationDoc.toString();
			userInfoDoc = getUserInfo(344757);
			
			ejb.saveEvaluation(claimID,
								suffixID,
								evaluationType,
								evaluationDetailsXmlData,
								userInfoDoc);
			logTestSuccess(methodName);
		
		}catch(Exception e){
			logTestFail(methodName);
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void updateEvaluation()throws Exception{
	
		String methodName = "updateEvaluation";
		long documentId = 728512;		
		String evaluationType = "LOSSEVALUATION";
		String evaluationDetailsXmlData = null;
		UserInfoDocument userInfoDoc = null;
		MitchellEvaluationDetailsDocument evaluationDoc = null;
		long claimId = 814093;
		long suffixId = 615367;
		long tcn = 1;
		
		try{			
			evaluationDoc = MitchellEvaluationDetailsDocument.Factory.parse(new File("src/test/resources/GMR/SaveEvlWithClaim.xml"));
			evaluationDetailsXmlData = evaluationDoc.toString();
			userInfoDoc = getUserInfo(344757);
			
			claimId = 814093;
			suffixId = 615367;
			tcn = 1;
			
			ejb.updateEvaluation(documentId,
								evaluationType,
								evaluationDetailsXmlData,
								userInfoDoc,
								claimId,
								suffixId,
								tcn);
			logTestSuccess(methodName);
			
		}catch(Exception e){
			logTestFail(methodName);
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void linkQuestionnaireEvaluationToClaim()throws Exception{
		
		String methodName = "linkQuestionnaireEvaluationToClaim";
		String evaluationID = null;
		UserInfoDocument userInfoDoc = null;
		String claimNumber = null;
		long claimId = 0;
		long suffixId = 0;
		
		try{			
			evaluationID = "preeti890";				
			userInfoDoc = getUserInfo(344757);
			claimNumber = "1360072941828-690";
			claimId = 814093;
			suffixId = 615367;
					
			ejb.linkQuestionnaireEvaluationToClaim(evaluationID,
								claimId,
								suffixId,
								claimNumber,
								userInfoDoc);
			logTestSuccess(methodName);
			
		}catch(Exception e){
			logTestFail(methodName);
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void deleteEvaluation()throws Exception{
		
		String methodName = "deleteEvaluation";
		UserInfoDocument userInfoDoc = null;
		String evaluationID = null;
		try{
				
			userInfoDoc = getUserInfo(344757);		
			evaluationID = "preeti891";	
			
			ejb.deleteEvaluation(userInfoDoc,
								evaluationID);
			logTestSuccess(methodName);
		
		}catch(Exception e){
			logTestFail(methodName);
			e.printStackTrace();
			throw e;
		}		
	}
		
	@Test
	public void associateQuestionnaireClmSuffix()throws Exception{		
		
		String methodName = "associateQuestionnaireClmSuffix";
		QuestionnaireClaimLinkDocument qstnrClaimLinkDoc = null;
		UserInfoDocument userInfoDoc = null;
		
		try{
			qstnrClaimLinkDoc = QuestionnaireClaimLinkDocument.Factory.parse(new File("src/test/resources/GMR/QstnrClaimLinkValid.xml"));
			userInfoDoc = getUserInfo(344757);			
			ejb.associateQuestionnaireToClaimSuffix(qstnrClaimLinkDoc, userInfoDoc);
			logTestSuccess(methodName);
			
		}catch(Exception e){
			logTestFail(methodName);
			e.printStackTrace();
			throw e;
		}
	}
	@Test
	public void updateDocumentIdAfterAssociation()throws Exception{
		
		String methodName = "updateDocumentIdAfterAssociation";
		long claimID = 0;
		long suffixID = 0;		
		String evaluationType = null;
		String evaluationDetailsXmlData = null;
		UserInfoDocument userInfoDoc = null;
		
		try{
			claimID = 277763;
			suffixID = 265530;		
			evaluationType = "TEST";
			MitchellEvaluationDetailsDocument evaluationDoc = MitchellEvaluationDetailsDocument.Factory.parse(new File("src/test/resources/GMR/UpdatedocIdAfterAssociation.xml"));
			evaluationDetailsXmlData = evaluationDoc.toString();
			userInfoDoc = getUserInfo(344757);
			
			ejb.saveEvaluation(claimID,
								suffixID,
								evaluationType,
								evaluationDetailsXmlData,
								userInfoDoc);
			logTestSuccess(methodName);
			
		}catch(Exception e){
			logTestFail(methodName);
			e.printStackTrace();
			throw e;			
		}
	}
	private UserInfoDocument getUserInfo(long orgId){	
		
		UserInfoDocument userInfoDoc = null;
		
		try{		
			userInfoDoc = userInfoRemote.getUserInfo(orgId);		
	
		}catch(Exception e){
			e.printStackTrace();			
		}
		
		return userInfoDoc;
	}
	
	private void logTestSuccess(String testMethod) {
		System.out.println(testMethod + " : Test Successfull! " + "\n");
	}

	private void logTestFail(String testMethod) {
		System.out.println(testMethod + " : Test Failed. " + "\n");
	}
}