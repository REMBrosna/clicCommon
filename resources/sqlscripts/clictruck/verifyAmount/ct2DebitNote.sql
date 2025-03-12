
-- debit note
with job as (SELECT jt.*, job.JOB_STATE 
	FROM clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID),
	tripCharge as ( SELECT * 
		FROM clickargo2.T_CK_CT_TRIP t,  clickargo2.T_CK_CT_TRIP_CHARGE tc
		where t.TR_CHARGE = tc.TC_ID  )
SELECT job.JOB_ID, dn.DN_AMT, job.JOB_TOTAL_CHARGE, job.JOB_TOTAL_REIMBURSEMENTS, sum(TC_PRICE)
, (JOB_TOTAL_REIMBURSEMENTS + sum(TC_PRICE)) computedJobTotalCharge, JOB_DT_CREATE 
FROM job left join tripCharge on job.JOB_ID = tripCharge.TR_JOB  
	left join clickargo2.T_CK_CT_DEBIT_NOTE dn on dn.DN_JOB_ID = job.JOB_ID
where JOB_STATE = 'APP_BILL'
    and dn.DN_FROM = 'GLI'
group by job.JOB_ID
having DN_AMT != computedJobTotalCharge
order by JOB_DT_CREATE asc

