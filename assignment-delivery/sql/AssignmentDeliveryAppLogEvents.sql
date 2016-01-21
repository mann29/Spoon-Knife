set echo on;

begin

-- This script is to insert new transaction type or update existing transaction type in the database
--
-- Run this scirpt as the following user in each environment
--
-- DEV:   BILLADM@BILLD
-- QA:    BILLADM@BILLT
-- UAT:   BILLADM@BILLU
-- PROD:  BILLADM@BILLP
--
-- Insert or update a row in BILLADM.TransactionType table
--
-- SCHEMA  - BILLADM
-- TABLE   - TransactionType

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

SAV_TXN_TYPE('106800', 'AssignmentDeliverySvc - Assignment Submitted to EClaimInboxSvc', 'AsgSubmittedToEClaimInboxSvc', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
SAV_TXN_TYPE('106801', 'AssignmentDeliverySvc - Assignment Submitted to ARC5', 'AsgSubmittedToARC5', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');

--- Added for SIP3.5 -
SAV_TXN_TYPE('106802', 'AssignmentDeliverySvc - Cancellation Alert Submitted to EClaimInboxSvc', 'cancelAlrtSentToEClaimInboxSvc', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
SAV_TXN_TYPE('106803', 'AssignmentDeliverySvc - Shop Supplement Assignment Email sent to Estimator', 'supplementAsgRequestEmailSent', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
--- Added for RC/WC integration via APD Delivery Service
SAV_TXN_TYPE('106804', 'AssignmentDeliverySvc - Dispatch WorkFlowMessage posted to Repair Center', 'RCWorkFlowMessageDispatch', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
SAV_TXN_TYPE('106805', 'AssignmentDeliverySvc - Re-dispatch WorkFlowMessage posted to Repair Center', 'RCWorkFlowMessageReDispatch', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
SAV_TXN_TYPE('106806', 'AssignmentDeliverySvc - Cancel WorkFlowMessage posted to Repair Center', 'RCWorkFlowMessageCancel', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
--- Added for WCAP integration via APD Delivery Service
SAV_TXN_TYPE('106807', 'AssignmentDeliverySvc - Dispatch WorkFlowMessage posted to WC Appraisal', 'WCAPWorkFlowMessageDispatch', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
SAV_TXN_TYPE('106808', 'AssignmentDeliverySvc - Re-dispatch WorkFlowMessage posted to WC Appraisal', 'WCAPWorkFlowMessageReDispatch', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
SAV_TXN_TYPE('106809', 'AssignmentDeliverySvc - Cancel WorkFlowMessage posted to WC Appraisal', 'WCAPWorkFlowMessageCancel', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
-- Added for Assignment Email Delivery --
SAV_TXN_TYPE('106810', 'AssignmentDeliverySvc - Email Sent for Assignment', 'AssignmentEmailSent', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
SAV_TXN_TYPE('106811', 'AssignmentDeliverySvc - Email Sent for Estimate Upload Success.', 'UploadSuccessEmailSent', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
SAV_TXN_TYPE('106812', 'AssignmentDeliverySvc - Email Sent for Estimate Upload Fail.', 'UploadFailEmailSent', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
SAV_TXN_TYPE('106813', 'AssignmentDeliverySvc - Email Sent NonDrp Shop Supplement.', 'NonDrpSuppEmailSent', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
SAV_TXN_TYPE('106814', 'AssignmentDeliverySvc - Fax Sent for Assignment.', 'AssignmentFaxSent', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
SAV_TXN_TYPE('106815', 'AssignmentDeliverySvc - Complete WorkFlowMessage posted to Repair Center', 'RCWorkFlowMessageComplete', 'NA', NULL, NULL, 0, NULL, NULL, 'Y', 'C');
commit;

end;
/
