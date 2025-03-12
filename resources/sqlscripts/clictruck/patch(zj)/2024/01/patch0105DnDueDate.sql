with approveDateTab as ( SELECT t.*
	, (select min(RCD_DT_BILL_APPROVED) 
		from clickargo2.T_CK_RECORD_DATE d
		where d.RCD_ID in (
			SELECT job.JOB_DATES
			FROM clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
			where jt.JOB_PARENT = job.JOB_ID
				and locate(jt.JOB_ID, PTX_SVC_REF) > 0)
		) approveDate 
FROM  clickargo2.T_CK_PAYMENT_TXN t
where  PTX_DT_CREATE > '2023-12-28'
	and PTX_TYPE = 'BNKTF'
order by PTX_DT_CREATE desc
)
update clickargo2.T_CK_PAYMENT_TXN txn, approveDateTab t
set txn.PTX_DT_DUE = DATE_ADD(t.approveDate, INTERVAL 7 DAY)
where txn.PTX_ID = t.PTX_ID
;


with approveDateTab as ( SELECT jt.*, RCD_DT_BILL_APPROVED 
		from clickargo2.T_CK_RECORD_DATE d, clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID 
			and d.RCD_ID = job.JOB_DATES
)
update clickargo2.T_CK_CT_DEBIT_NOTE dn, approveDateTab t
set dn.DN_DT_DUE = DATE_ADD(t.RCD_DT_BILL_APPROVED, INTERVAL 7 DAY)
where dn.DN_JOB_ID = t.JOB_ID
	and DN_TO = 'GLI'
	and DN_DT_DUE > '2100-01-01'
    and DN_STATE in ('PENDING', 'NEW')
;

with approveDateTab as ( SELECT jt.*, RCD_DT_BILL_APPROVED 
		from clickargo2.T_CK_RECORD_DATE d, clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID 
			and d.RCD_ID = job.JOB_DATES
)
update clickargo2.T_CK_CT_PLATFORM_INVOICE inv, T_CORE_ACCN accn, approveDateTab t
set inv.INV_DT_DUE = DATE_ADD(t.RCD_DT_BILL_APPROVED, INTERVAL 7 DAY)
	where inv.INV_TO = accn.ACCN_ID
		AND inv.INV_JOB_ID = t.JOB_ID
		and accn.ACCN_TYPE = 'ACC_TYPE_TO'
        AND INV_DT_DUE > '2100-01-01'
        AND INV_STATE IN ('PENDING','NEW') ;

-- ------------------- -- ------------------- -- ------------------- -- ------------------- 

with approveDateTab as ( SELECT t.*
	, (select min(RCD_DT_BILL_APPROVED) 
		from clickargo2.T_CK_RECORD_DATE d
		where d.RCD_ID in (
			SELECT job.JOB_DATES
			FROM clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
			where jt.JOB_PARENT = job.JOB_ID
				and locate(jt.JOB_ID, PTX_SVC_REF) > 0)
		) approveDate 
FROM  clickargo2.T_CK_PAYMENT_TXN t
where  PTX_DT_CREATE > '2023-12-28'
	and PTX_TYPE = 'BNKTF'
order by PTX_DT_CREATE desc
)
select * from approveDateTab;


with approveDateTab as ( SELECT jt.*, RCD_DT_BILL_APPROVED 
		from clickargo2.T_CK_RECORD_DATE d, clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID 
			and d.RCD_ID = job.JOB_DATES
)
SELECT * FROM clickargo2.T_CK_CT_DEBIT_NOTE dn, approveDateTab t
where dn.DN_JOB_ID = t.JOB_ID
	and DN_TO = 'GLI'
	and DN_DT_DUE > '2100-01-01'
    and DN_STATE in ('PENDING', 'NEW')
order by DN_DT_CREATE desc;

with approveDateTab as ( SELECT jt.*, RCD_DT_BILL_APPROVED 
		from clickargo2.T_CK_RECORD_DATE d, clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID 
			and d.RCD_ID = job.JOB_DATES
)
SELECT * FROM clickargo2.T_CK_CT_PLATFORM_INVOICE inv, approveDateTab t
where dn.DN_JOB_ID = t.JOB_ID
	and DN_TO = 'GLI'
	and DN_DT_DUE > '2100-01-01'
    and DN_STATE in ('PENDING', 'NEW')
order by DN_DT_CREATE desc;
    
with approveDateTab as ( SELECT jt.*, RCD_DT_BILL_APPROVED 
		from clickargo2.T_CK_RECORD_DATE d, clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID 
			and d.RCD_ID = job.JOB_DATES
)
SELECT * 
FROM clickargo2.T_CK_CT_PLATFORM_INVOICE inv, T_CORE_ACCN accn, approveDateTab t
	where inv.INV_TO = accn.ACCN_ID
		AND inv.INV_JOB_ID = t.JOB_ID
		and accn.ACCN_TYPE = 'ACC_TYPE_TO'
        AND INV_DT_DUE > '2100-01-01'
        AND INV_STATE IN ('PENDING','NEW')
order by INV_DT_CREATE desc;
    