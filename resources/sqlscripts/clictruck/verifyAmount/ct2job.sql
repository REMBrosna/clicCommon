SELECT jt.*, job.JOB_STATE, job.JOB_SHIPMENT_TYPE
FROM clickargo2.T_CK_JOB_TRUCK jt left join clickargo2.T_CK_JOB job on jt.JOB_PARENT = job.JOB_ID
where job.JOB_STATE = 'APP_BILL'
and jt.JOB_ID not in (select dn.DN_JOB_ID from clickargo2.T_CK_CT_DEBIT_NOTE dn);

SELECT jt.*, job.JOB_STATE, job.JOB_SHIPMENT_TYPE
FROM clickargo2.T_CK_JOB_TRUCK jt left join clickargo2.T_CK_JOB job on jt.JOB_PARENT = job.JOB_ID
where job.JOB_STATE = 'APP_BILL'
and jt.JOB_ID not in (select dn.INV_JOB_ID from clickargo2.T_CK_CT_PLATFORM_INVOICE dn)