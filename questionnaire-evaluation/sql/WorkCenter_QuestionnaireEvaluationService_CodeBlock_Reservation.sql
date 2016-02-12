-- *************************************************************************************************
-- **   Created By: Dominic Early, de3712
-- **         Date: 2009-12-01
-- **     Comments: Sample AppName_ModuleName_CodeBlock_Reservation.SQL script
-- **
-- **     !!!!!!!!!    THIS SCRIPT MUST ONLY BE ALTERED BY MITCHELL ARCHITECTURE      !!!!!!!!!
-- **
-- **         Date: 2010-7-12
-- **     Comments: WorkCenter_QuestionnaireEvaluationService_CodeBlock_Reservation.SQL script
-- **     Modified from original by Mark Armendariz, ma99993
-- **
-- *************************************************************************************************
-- To be executed by Mitchell DBAs as part of DB Work Order:
--
-- DBAs, please execute following script in the following two environments:
--
-- 1) NMA(x)
-- login as user "archweb_<X>" (archweb_qa for QA ,archweb_uat for UAT and archweb_prod for production) 
-- on NMA<X>(i.e. NMAT for QA,NMAU for UAT and NMAP for Production) 
--
-- 2) BILL(x)
-- login as user "archweb_<X>" (archweb_qa for QA ,archweb_uat for UAT and archweb_prod for production)
-- on BILL<X> (i.e. BILLT for QA,BILLU for UAT and BILLP for Production).
-- **************************************************************************************************/
-----------------------------------------------------------
-- Call the procedure to register new application name
-- It will insert row into table srv_applicationname_info
------------------------------------------------------------
begin
  srv_admin_utility_pkg.put_applicationname_info('WORKCENTER',
                                                 'WORKCENTER',
                                                 'ACTIVE');
-----------------------------------------------------------
-- Call procedure to register new module name
-- It will insert row into table srv_modulename_info
------------------------------------------------------------
  srv_admin_utility_pkg.put_modulename_info('QUESTIONNAIRE_EVALUATION_SERVICE',
                                            'Apply calculations to the values in a completed questionnaire form XML todetermine an evaluation score. The service allows support for different form templates to be added in the future. A configurable field weighting template is associated with each form.',
                                            'WORKCENTER');
-----------------------------------------------------------
-- Call procedure to register new range for module
-- It will insert row into table srv_modulename_detail
-- One procedure call for error type codes, one for event type codes
------------------------------------------------------------
  srv_admin_utility_pkg.put_modulename_detail('QUESTIONNAIRE_EVALUATION_SERVICE' ,
                                              'WORKCENTER',
                                              'EVENT_TYPE',   
                                              157600,
                                              157699);
											  
  srv_admin_utility_pkg.put_modulename_detail('QUESTIONNAIRE_EVALUATION_SERVICE' ,
                                              'WORKCENTER',
                                              'ERROR_TYPE',   
                                              157600,
                                              157699);     																					 
																						 
	COMMIT;																					 
end;
/
