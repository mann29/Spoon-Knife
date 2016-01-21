--clean  txntype_to_wrkflw_event
delete from  Billadm.txntype_to_wrkflw_event tr where tr.transaction_type='108301' and tr.event_code='APPRAISAL_ASSIGNMENT_SAVE';
delete from  Billadm.txntype_to_wrkflw_event tr where tr.transaction_type='108313' and tr.event_code='APPRAISAL_ASSIGNMENT_UPDATE';
delete from  Billadm.txntype_to_wrkflw_event tr where tr.transaction_type='108305' and tr.event_code='APPRAISAL_ASSIGNMENT_CANCELED';
delete from  Billadm.txntype_to_wrkflw_event tr where tr.transaction_type='108307' and tr.event_code='APPRAISAL_ASSIGNMENT_DISPATCHED';
delete from  Billadm.txntype_to_wrkflw_event tr where tr.transaction_type='108311' and tr.event_code='APPRAISAL_ASSIGNMENT_UNCANCELED';
delete from  Billadm.txntype_to_wrkflw_event tr where tr.transaction_type='108315' and tr.event_code='APPRAISAL_ASSIGNMENT_ASSIGNED';
delete from  Billadm.txntype_to_wrkflw_event tr where tr.transaction_type='108321' and tr.event_code='APPRAISAL_ASSIGNMENT_UNSCHEDULED';
delete from  Billadm.txntype_to_wrkflw_event tr where tr.transaction_type='108324' and tr.event_code='APPRAISAL_ASSIGNMENT_UPDATE_DISPOSITION';
delete from  Billadm.txntype_to_wrkflw_event tr where tr.transaction_type='108322' and tr.event_code='APPRAISAL_ASSIGNMENT_ON_HOLD';
delete from  Billadm.txntype_to_wrkflw_event tr where tr.transaction_type='108323' and tr.event_code='APPRAISAL_ASSIGNMENT_REMOVE_ON_HOLD';

--clean workflow_event 
delete from  Billadm.workflow_event we where we.event_code='APPRAISAL_ASSIGNMENT_SAVE';
delete from  Billadm.workflow_event we where we.event_code='APPRAISAL_ASSIGNMENT_UPDATE';
delete from  Billadm.workflow_event we where we.event_code='APPRAISAL_ASSIGNMENT_CANCELED';
delete from  Billadm.workflow_event we where we.event_code='APPRAISAL_ASSIGNMENT_DISPATCHED';
delete from  Billadm.workflow_event we where we.event_code='APPRAISAL_ASSIGNMENT_UNCANCELED';
delete from  Billadm.workflow_event we where we.event_code='APPRAISAL_ASSIGNMENT_ASSIGNED';
delete from  Billadm.workflow_event we where we.event_code='APPRAISAL_ASSIGNMENT_UNSCHEDULED';
delete from  Billadm.workflow_event we where we.event_code='APPRAISAL_ASSIGNMENT_UPDATE_DISPOSITION';
delete from  Billadm.workflow_event we where we.event_code='APPRAISAL_ASSIGNMENT_ON_HOLD';
delete from  Billadm.workflow_event we where we.event_code='APPRAISAL_ASSIGNMENT_REMOVE_ON_HOLD';


--add to workflow_event
insert into Billadm.workflow_event values ('APPRAISAL_ASSIGNMENT_SAVED','The appraisal assignment was saved successfully','T','F','The appraisal assignment was saved successfully','WorkCenter Assignment',sysdate);
insert into Billadm.workflow_event values ('APPRAISAL_ASSIGNMENT_UPDATED','The appraisal assignment was updated successfully','T','F','The appraisal assignment was updated successfully','WorkCenter Assignment',sysdate);
insert into Billadm.workflow_event values ('APPRAISAL_ASSIGNMENT_CANCELED','Appraisal Assignment Canceled Successfully','T','F','Appraisal Assignment Canceled Successfully','WorkCenter Assignment',sysdate);
insert into Billadm.workflow_event values ('APPRAISAL_ASSIGNMENT_DISPATCHED','Appraisal Assignment Dispatched Successfully','T','F','Appraisal Assignment Updated Successfully','WorkCenter Assignment',sysdate);
insert into Billadm.workflow_event values ('APPRAISAL_ASSIGNMENT_UNCANCELED','Appraisal Assignment Uncanceled Successfully','T','F','Appraisal Assignment Uncanceled Successfully','WorkCenter Assignment',sysdate);
insert into Billadm.workflow_event values ('APPRAISAL_ASSIGNMENT_ASSIGNED','Appraisal Assignment Assigned  Successfully','T','F','Appraisal Assignment Assigned  Successfully','WorkCenter Assignment',sysdate);
insert into Billadm.workflow_event values ('APPRAISAL_ASSIGNMENT_UNSCHEDULED','Appraisal Assignment Unscheduled Successfully','T','F','Appraisal Assignment Unscheduled Successfully','WorkCenter Assignment',sysdate);
insert into Billadm.workflow_event values ('APPRAISAL_ASSIGNMENT_UPDATE_DISPOSITION','Appraisal Assignment Update Disposition Successfully','T','F','Appraisal Assignment Update Disposition Successfully','WorkCenter Assignment',sysdate);
insert into Billadm.workflow_event values ('APPRAISAL_ASSIGNMENT_ON_HOLD','Appraisal Assignment On-Hold Successfully','T','F','Appraisal Assignment On-Hold Successfully','WorkCenter Assignment',sysdate);
insert into Billadm.workflow_event values ('APPRAISAL_ASSIGNMENT_REMOVE_ON_HOLD','Appraisal Assignment Remove On-Hold Successfully','T','F','Appraisal Assignment Remove On-Hold Successfully','WorkCenter Assignment',sysdate);

--add to txntype_to_wrkflw_event
insert into Billadm.txntype_to_wrkflw_event values('108301','APPRAISAL_ASSIGNMENT_SAVED','WorkCenter Assignment',sysdate);
insert into Billadm.txntype_to_wrkflw_event values('108313','APPRAISAL_ASSIGNMENT_UPDATED','WorkCenter Assignment',sysdate);
insert into Billadm.txntype_to_wrkflw_event values('108305','APPRAISAL_ASSIGNMENT_CANCELED','WorkCenter Assignment',sysdate);
insert into Billadm.txntype_to_wrkflw_event values('108307','APPRAISAL_ASSIGNMENT_DISPATCHED','WorkCenter Assignment',sysdate);
insert into Billadm.txntype_to_wrkflw_event values('108311','APPRAISAL_ASSIGNMENT_UNCANCELED','WorkCenter Assignment',sysdate);
insert into Billadm.txntype_to_wrkflw_event values('108315','APPRAISAL_ASSIGNMENT_ASSIGNED','WorkCenter Assignment',sysdate);
insert into Billadm.txntype_to_wrkflw_event values('108321','APPRAISAL_ASSIGNMENT_UNSCHEDULED','WorkCenter Assignment',sysdate);
insert into Billadm.txntype_to_wrkflw_event values('108324','APPRAISAL_ASSIGNMENT_UPDATE_DISPOSITION','WorkCenter Assignment',sysdate);
insert into Billadm.txntype_to_wrkflw_event values('108322','APPRAISAL_ASSIGNMENT_ON_HOLD','WorkCenter Assignment',sysdate);
insert into Billadm.txntype_to_wrkflw_event values('108323','APPRAISAL_ASSIGNMENT_REMOVE_ON_HOLD','WorkCenter Assignment',sysdate);