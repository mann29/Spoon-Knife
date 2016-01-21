-- Script: AppraisalAssignment_AppCodes.sql
--
-- This script is to insert new transaction type or update existing transaction type in the database
--
-- Run this scirpt as the following user in each environment
--
-- DEV:   BILLADM@BILLD
-- QA:    BILLADM@BILLT
-- UAT:   BILLADM@BILLU
-- PROD:  BILLADM@BILLP


BEGIN
  -- insert or update a row in BILLADM.TransactionType table
  -- 
  -- Procedure name: sav_txn_type
  --
  -- parameter 1: enter the name of the application, up to 100 characters.
  -- parameter 2: enter the transaction name, up to 128 characters.
  -- parameter 3: enter the transaction name, up to 30 characters.
  -- parameter 4: enter the source app, such as 'EDI', 'CORE', 'TLV', etc.
  -- parameter 5: enter NULL.
  -- parameter 6: enter 0 for internal, 1 for external, or NULL if you don't know.
  -- parameter 7: enter 0 if the transaction is non-billable, 1 if billable.
  -- parameter 8: enter 0 of manual, 1 if automatic, or NULL if you don't know.
  -- parameter 9: enter the application access family, e.g. 'HUBFAM', 'IMGFAM', or NULL if you don't know.
  -- parameter 10: enter 'Y' if we can archive the transactions, 'N' or NULL if the transaction has to stay forever.
  -- parameter 11: 'C' if collision, 'M' if medical

  -- AppraisalAssignmentService Events
  
  sav_txn_type('108301', 'WCAAService : Successfully processed SAVE/Create Appraisal Assignment Request.', 'WCAAS_SAVE_AA_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
 
  sav_txn_type('108305', 'WCAAService : Successfully processed CANCEL Appraisal Assignment Request.', 'WCAAS_CANCEL_AA_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');

  sav_txn_type('108307', 'WCAAService : Successfully processed DISPATCH Appraisal Assignment Request.', 'WCAAS_DISPATCH_AA_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');

  sav_txn_type('108309', 'WCAAService : Successfully processed Get Latest Estimate Request.', 'WCAAS_GET_LATEST_EST_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
  
  sav_txn_type('108311', 'WCAAService : Successfully processed RE-OPEN Appraisal Assignment Request.', 'WCAAS_UNCANCEL_AA_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
  
  sav_txn_type('108313', 'WCAAService : Successfully processed UPDATE Appraisal Assignment Request.', 'WCAAS_UPDATE_AA_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
  
  sav_txn_type('108315', 'WCAAService : Successfully processed ASSIGN/REASSIGN Appraisal Assignment Request.', 'WCAAS_ASSIGN_AA_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
  
  sav_txn_type('108317', 'WCAAService : Successfully processed IS ASSIGNMENT EVER DISPATCHED Request.', 'WCAAS_AA_EVER_DISPATCH_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
                 
  sav_txn_type('108321', 'WCAAService : Successfully processed UNSCHEDULE Appraisal Assignment Request.', 'WCAAS_UNSCHEDULE_AA_SUCCESS', 'CORE', 
					NULL, NULL, 0, NULL, NULL, 'Y', 'C');
 
  sav_txn_type('108322', 'WCAAService : Successfully processed ONHOLD Appraisal Assignment Request.', 'WCAAS_ONHOLD_AA_SUCCESS', 'CORE', 
					NULL, NULL, 0, NULL, NULL, 'Y', 'C');
  
  sav_txn_type('108323', 'WCAAService : Successfully processed REMOVE ONHOLD Appraisal Assignment Request.', 'WCAAS_REMOVE_ONHOLD_AA_SUCCESS', 'CORE', 
					NULL, NULL, 0, NULL, NULL, 'Y', 'C');
  
sav_txn_type('108324', 'WCAAService : Successfully processed UPDATE DISPOSITION Appraisal Assignment Request.', 'WCAAS_UPDATE_DISP_SUCCESS', 'CORE', 
					NULL, NULL, 0, NULL, NULL, 'Y', 'C');
  
sav_txn_type('108325', 'WCAAService : Successfully created Task for Request Supplement Assignment.', 'WCAAS_CREATE_TASK_REQSUPP', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');

sav_txn_type('108326', 'WCAAService : Successfully cancelled Task for Request Supplement Assignment.', 'WCAAS_CANCEL_TASK_REQSUPP', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
  
sav_txn_type('108327', 'WCAAService : Successfully processed rejected Task for Request Supplement Assignment.', 'WCAAS_REJECT_TASK_REQSUPP', 'CORE',
					NULL, NULL, 0, NULL, NULL, 'Y', 'C');
					
sav_txn_type('108328', 'WCAAService : Successfully called APD.', 'WCAAS_APD_CALL_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
					
sav_txn_type('108329', 'WCAAService : Successfully Updated Vehicle Tracking Status Request.', 'WCAAS_VEHICLE_UPDATE_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
					
sav_txn_type('108330', 'WCAAService : Successfully processed RESCHEDULE DISPOSITION.', 'WCAAS_RESCHEDULE_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
					
sav_txn_type('108331', 'WCAAService : Successfully processed INCOMPLETE DISPOSITION.', 'WCAAS_INCOMPLETE_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
					
sav_txn_type('108332', 'WCAAService : Successfully save and send the appraisal assignment.', 'WCAAS_SAVE_AND_SEND_AA_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
					
sav_txn_type('108333', 'WCAAService : Successfully updated the appraisal assignment address.', 'WCAAS_UPD_AA_ADDRESS_SUCCESS', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');

sav_txn_type('108334', 'WCAAService : Failure or Warning occurred while processing assignment.', 'WCAAS_FAILURE_RESPONSE', 'CORE',
                    NULL, NULL, 0, NULL, NULL, 'Y', 'C');
  COMMIT;

END;
/
