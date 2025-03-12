

with job as (SELECT jt.*, job.JOB_STATE 
	FROM clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID),
	inv as (
		select * from T_CK_CT_PLATFORM_INVOICE i, job
        where i.INV_TO = job.JOB_PARTY_TO and i.INV_JOB_ID = job.JOB_ID
    ),
    txn as (
    SELECT PTX_DT_CREATE, PTX_ID, PTX_PAYMENT_STATE, PTX_SVC_REF,PTX_AMOUNT, 
		(select sum( inv.JOB_TOTAL_CHARGE + JOB_TOTAL_REIMBURSEMENTS - INV_TOTAL) from inv 
			where locate(inv.JOB_ID, t.PTX_SVC_REF) >0 ) computed
		FROM clickargo2.T_CK_PAYMENT_TXN t
		where PTX_PAYMENT_STATE != 'CANCELLED'
			and PTX_TYPE = 'BNKTF'
		)
select PTX_ID, TOP_DT_TRANSFER, TOP_ACCN_TO, PTX_AMOUNT, computed as correctAmt, (PTX_AMOUNT - computed) as mistake
from txn, T_CK_CT_TO_PAYMENT t
where txn.PTX_ID = t.TOP_REFERENCE
	and (PTX_AMOUNT - computed)  != 0	
order by PTX_DT_CREATE asc;
