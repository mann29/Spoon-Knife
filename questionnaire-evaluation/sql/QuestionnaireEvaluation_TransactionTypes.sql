set echo on;

/*
				

Following are the Event codes for different components
--------------------------------------------------------

QuestionnaireEvaluation J2EE           	:  157600 to 157699


*/


begin

SAV_TXN_TYPE( '157601',  'QuestionnaireEvaluationService – Successfully saved evaluation details.', 'QstnEvaluation Saved',
              'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C' );

SAV_TXN_TYPE( '157602',  'QuestionnaireEvaluationService – Evaluation details saved with a new version.', 'QstnEvaluation Updated',
              'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C' );


SAV_TXN_TYPE( '157603',  'QuestionnaireEvaluationService – Successfully deleted evaluation details.', 'QstnEvaluation Deleted',
              'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C' );

SAV_TXN_TYPE( '157604',  'QuestionnaireEvaluationService – Successfully linked claim to evaluation ', 'QstnEvaluation Linked',
              'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C' );
			  
SAV_TXN_TYPE( '157605',  'QuestionnaireEvalutionService - Claim-Evaluation link status', 'QstnEvaluation LinkStatus',
              'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C' );
commit;

end;
/




