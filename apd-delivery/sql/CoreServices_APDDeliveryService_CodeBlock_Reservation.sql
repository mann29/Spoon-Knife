-- *************************************************************************************************
-- **   Created By: Dominic Early, de3712
-- **         Date: 2009-12-01
-- **     Comments: Sample AppName_ModuleName_CodeBlock_Reservation.SQL script
-- **
-- **     !!!!!!!!!    THIS SCRIPT MUST ONLY BE ALTERED BY MITCHELL ARCHITECTURE      !!!!!!!!!
-- **
-- **         Date: 2010-8-31
-- **     Comments: CoreServices_APDDelivery_CodeBlock_Reservation.sql script
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
  srv_admin_utility_pkg.put_applicationname_info('CORESERVICES',
                                                 'CORESERVICES',
                                                 'ACTIVE');
-----------------------------------------------------------
-- Call procedure to register new module name
-- It will insert row into table srv_modulename_info
------------------------------------------------------------
  srv_admin_utility_pkg.put_modulename_info('APD_DELIVERY_SERVICE',
                                            'Formats and Delivers messages and artifacts to Mitchell desktop clients.',
                                            'CORESERVICES');
-----------------------------------------------------------
-- Call procedure to register new range for module
-- It will insert row into table srv_modulename_detail
-- One procedure call for error type codes, one for event type codes
------------------------------------------------------------
  srv_admin_utility_pkg.put_modulename_detail('APD_DELIVERY_SERVICE' ,
                                              'CORESERVICES',
                                              'EVENT_TYPE',   
                                              159600,
                                              159699);
											  
  srv_admin_utility_pkg.put_modulename_detail('APD_DELIVERY_SERVICE' ,
                                              'CORESERVICES',
                                              'ERROR_TYPE',   
                                              159600,
                                              159699);     																					 
																						 
	COMMIT;																					 
end;
/
