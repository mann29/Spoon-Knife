-- *************************************************************************************************
-- **   Created By: Dominic Early, de3712
-- **         Date: 2009-12-01
-- **     Comments: Sample AppName_ModuleName_CodeBlock_Reservation.SQL script
-- **
-- **     !!!!!!!!!    THIS SCRIPT MUST ONLY BE ALTERED BY MITCHELL ARCHITECTURE      !!!!!!!!!
-- **
-- **         Date: 2012-4-30
-- **     Comments: PartialLoss_AppraisalAssignmentService_CodeBlock_Reservation.SQL script
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
  srv_admin_utility_pkg.put_applicationname_info('PARTIALLOSS',
                                                 'PARTIALLOSS',
                                                 'ACTIVE');
-----------------------------------------------------------
-- Call procedure to register new module name
-- It will insert row into table srv_modulename_info
------------------------------------------------------------
  srv_admin_utility_pkg.put_modulename_info('APPRAISAL_ASSIGNMENT_SERVICE',
                                            'Service for the handling of appraisal assignments.',
                                            'PARTIALLOSS');
-----------------------------------------------------------
-- Call procedure to register new range for module
-- It will insert row into table srv_modulename_detail
-- One procedure call for error type codes, one for event type codes
-- Note: 108300 - 108399 were previous assigned to APPRAISAL_ASSIGNMENT_SERVICE prior to use code block reservation. These
-- are addition to the range that was originally assigned.
------------------------------------------------------------
  srv_admin_utility_pkg.put_modulename_detail('APPRAISAL_ASSIGNMENT_SERVICE' ,
                                              'PARTIALLOSS',
                                              'EVENT_TYPE',   
                                              108400,
                                              108499);
											  
  srv_admin_utility_pkg.put_modulename_detail('APPRAISAL_ASSIGNMENT_SERVICE' ,
                                              'PARTIALLOSS',
                                              'ERROR_TYPE',   
                                              108400,
                                              108499);     																					 
																						 
	COMMIT;																					 
end;
/
