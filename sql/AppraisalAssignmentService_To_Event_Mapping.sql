--add to workflow_event
insert into Billadm.workflow_event values ('FAILURE_RESPONSE','Failure or Warning occurred while processing assignment.','T','F','Failure or Warning occurred while processing assignment.','APPRAISAL_ASSIGNMENT_SERVICE',sysdate);


--add to txntype_to_wrkflw_event
insert into Billadm.txntype_to_wrkflw_event values('108334','FAILURE_RESPONSE','APPRAISAL_ASSIGNMENT_SERVICE',sysdate);
