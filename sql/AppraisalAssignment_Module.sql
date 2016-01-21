set echo on;

begin

SRV_ADMIN_PKG.SAV_SRV_APPLICATION_NAME('PARTIALLOSS', 'PartialLoss Application');

SRV_ADMIN_PKG.SAV_SRV_MODULE_NAME('APPRAISAL_ASSIGNMENT_SERVICE', 'Appraisal Assignment Service',
                'PARTIALLOSS',
                108300, 108399,
                108300, 108399);

commit;

end;
/
