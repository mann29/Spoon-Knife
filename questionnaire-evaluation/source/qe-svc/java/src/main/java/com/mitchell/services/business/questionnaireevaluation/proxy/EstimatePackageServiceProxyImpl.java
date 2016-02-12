package com.mitchell.services.business.questionnaireevaluation.proxy;

import org.apache.xmlbeans.XmlObject;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;

public class EstimatePackageServiceProxyImpl implements
		EstimatePackageServiceProxy {
	
	public Long saveQuestionnaireEvaluation(XmlObject evaluationDetails,
			UserInfoDocument userInfoDoc,
			Long claimId,
			Long exposureId)throws MitchellException{
		
		Long documentId = null;
		documentId = (new EstimatePackageClient()).saveQuestionnaireEvaluation(evaluationDetails,
																		userInfoDoc,
																		claimId,
																		exposureId);
		return documentId;
	}
	
	public Long getDocumentStoreIdByDocId(long documentId,
				String attachmentTypeCode)throws MitchellException{
		
		Long docStoreId = null;		
		docStoreId = (new EstimatePackageClient()).getDocumentStoreIdByDocId(documentId,																		
																attachmentTypeCode);
		return docStoreId;
		
	}
	
	public void deleteQuestionnaireEvaluation(String coCode,
			String externalRefCode)throws MitchellException{		
		
		(new EstimatePackageClient()).deleteQuestionnaireEvaluation(coCode,																		
				externalRefCode);
	}
	
	public void updateQuestionnaireEvaluation(long documentId,
			XmlObject evaluationDetailsDocumentNAS,
			UserInfoDocument userInfoDoc,
			Long tcn)throws MitchellException{
		
		(new EstimatePackageClient()).updateQuestionnaireEvaluation(documentId,
										evaluationDetailsDocumentNAS,
										userInfoDoc,
										tcn);
	}
	
	public Long getQuestionnaireEvaluationDocIdByRefCode(String coCode, 
    		String externalRefCode)throws MitchellException{
		
		Long documentId = null;
		documentId = (new EstimatePackageClient()).getQuestionnaireEvaluationDocIdByRefCode(coCode, 
	    											externalRefCode);
		return documentId;
		
	}
	
	public int linkQuestionnaireEvaluationToClaim(long documentId,
			long claimId,
			long exposureId,
			String coCode,
			UserInfoDocument userInfoDoc)throws MitchellException{
		
		int linkStatus = 0;
		
		linkStatus = (new EstimatePackageClient()).linkQuestionnaireEvaluationToClaim(documentId,
										claimId,
										exposureId,
										coCode,
										userInfoDoc);
		return linkStatus;
	}
}