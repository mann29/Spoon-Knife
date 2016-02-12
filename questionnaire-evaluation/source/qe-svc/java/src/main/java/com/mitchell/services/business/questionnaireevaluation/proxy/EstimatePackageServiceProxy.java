package com.mitchell.services.business.questionnaireevaluation.proxy;

import org.apache.xmlbeans.XmlObject;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;

public interface EstimatePackageServiceProxy {

	 Long saveQuestionnaireEvaluation(XmlObject evaluationDetails,
					UserInfoDocument userInfoDoc,
					Long claimId,
					Long exposureId)throws MitchellException;
	
	 Long getDocumentStoreIdByDocId(long documentId,
					String attachmentTypeCode)throws MitchellException;
	
	 void deleteQuestionnaireEvaluation(String coCode,
					String externalRefCode)throws MitchellException;
	
	 void updateQuestionnaireEvaluation(long documentId,
					XmlObject evaluationDetailsDocumentNAS,
					UserInfoDocument userInfoDoc,
					Long tcn)throws MitchellException;
	
	 Long getQuestionnaireEvaluationDocIdByRefCode(String coCode, 
            		String externalRefCode)throws MitchellException;
	
	 int linkQuestionnaireEvaluationToClaim(long documentId,
					long claimId,
					long exposureId,
					String coCode,
					UserInfoDocument userInfoDoc)throws MitchellException;
	
}
