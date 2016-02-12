set echo on;
/*
				

Following are the error codes for different components
--------------------------------------------------------

QuestionnaireEvaliation J2EE	157600 to 157699

*/

begin

ERR_LOG_PKG.SAV_ERR_TYPE(
    157601, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Invalid Evaluation XML', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157602, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Validation exception - Invalid Evaluation Id', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157603, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Validation exception - Invalid SuffixID', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157604, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Validation exception - Invalid Claim Id', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157605, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Validation exception - Invalid Evaluation Type', 'Y'
    );


ERR_LOG_PKG.SAV_ERR_TYPE(
    157606, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error saving evaluation', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157607, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Claim Activity Log Exception', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157608, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error deleting Evaluation', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157609, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Generic Exception', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157610, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error getting EJB client', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157611, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error Linking Claim', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157612, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error while copy to NAS location', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157613, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Validation exception - Invalid Version', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157614, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error updating evaluation', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157615, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Invalid Document Id', 'Y'
    );
    
ERR_LOG_PKG.SAV_ERR_TYPE(
    157616, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error calling Estimate Package Service', 'Y'
    );
 
ERR_LOG_PKG.SAV_ERR_TYPE(
    157617, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error calling TransactionalFile Service', 'Y'
    );
    
ERR_LOG_PKG.SAV_ERR_TYPE(
    157618, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error occurred while publishing message to MessageBus.', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157619, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error occurred while publishing EDOG event', 'Y'
    );
    
ERR_LOG_PKG.SAV_ERR_TYPE(
    157620, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'SQL Exception occurred', 'Y'
    );
	
ERR_LOG_PKG.SAV_ERR_TYPE(
    157621, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error attaching DocId to Claim Questionnaire', 'Y'
    );
	
ERR_LOG_PKG.SAV_ERR_TYPE(
    157622, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error Associating Questionnaire To Claim Suffix', 'Y'
    );
	
ERR_LOG_PKG.SAV_ERR_TYPE(
    157623, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Unexpected Error', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157624, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'error while parsing assignmentaddrqrs doc', 'Y'
    );
   
ERR_LOG_PKG.SAV_ERR_TYPE(
    157625, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'error while parsing cieca doc', 'Y'
    );
       

   ERR_LOG_PKG.SAV_ERR_TYPE(
    157626, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'error while validating cieca doc', 'Y'
    );
       
  ERR_LOG_PKG.SAV_ERR_TYPE(
    157627, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'error while validating suffixsvcrqrs doc', 'Y'
    );
       	
ERR_LOG_PKG.SAV_ERR_TYPE(
    157628, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'error while calling trans engine service', 'Y'
    );
       	
ERR_LOG_PKG.SAV_ERR_TYPE(
    157629, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'unknown error while calling  service', 'Y'
    );
	
ERR_LOG_PKG.SAV_ERR_TYPE(
    157630, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'unknown error in question evaluation service', 'Y'
    );

ERR_LOG_PKG.SAV_ERR_TYPE(
    157631, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'error while calling user info service', 'Y'
    );
 
 ERR_LOG_PKG.SAV_ERR_TYPE(
    157632, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'input evaluation_rq is null', 'Y'
    );
   
    ERR_LOG_PKG.SAV_ERR_TYPE(
    157633, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'null after calling claim service', 'Y'
    );
	ERR_LOG_PKG.SAV_ERR_TYPE(
    157634, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'adjuster id is null or missing', 'Y'
    );
	ERR_LOG_PKG.SAV_ERR_TYPE(
    157645, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Exception occured while posting Evaluation Document in Reporting Queue', 'Y'
    );
	
	ERR_LOG_PKG.SAV_ERR_TYPE(
    157646, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Exception occured while Saving Evaluation Document', 'Y'
    );
	
	ERR_LOG_PKG.SAV_ERR_TYPE(
    157647, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'IO Exception occured while Saving Evaluation Document', 'Y'
    );
	
	ERR_LOG_PKG.SAV_ERR_TYPE(
    157648, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'XML Exception occured while Saving Evaluation Document', 'Y'
    );
	
	ERR_LOG_PKG.SAV_ERR_TYPE(
    157649, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error occured while getting the Questionnaire', 'Y'
    );
	
	ERR_LOG_PKG.SAV_ERR_TYPE(
    157650, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'input is not correct. Either input QuestionnaireRqRsDTO or Context DTO is null', 'Y'
    );
	
	ERR_LOG_PKG.SAV_ERR_TYPE(
    157651, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'company code is null or invalid', 'Y'
    );
	
	ERR_LOG_PKG.SAV_ERR_TYPE(
    157652, 'WORKCENTER', 'QUESTIONNAIRE_EVALUATION_SERVICE',
    'Error occured while creating cache key for questionnaire', 'Y'
    );
	
end;
/

