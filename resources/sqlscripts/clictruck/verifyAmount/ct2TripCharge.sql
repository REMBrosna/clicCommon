
with job as (SELECT jt.*, job.JOB_STATE, job.JOB_SHIPMENT_TYPE 
	FROM clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID),
	tripCharge as ( SELECT * 
		FROM clickargo2.T_CK_CT_TRIP t,  clickargo2.T_CK_CT_TRIP_CHARGE tc
		where t.TR_CHARGE = tc.TC_ID 
    )
select tripCharge.TR_JOB, job.JOB_PARTY_TO, job.JOB_PARTY_CO_FF, job.JOB_STATE, job.JOB_SHIPMENT_TYPE, JOB_OUT_PAYMENT_STATE
	, JOB_TOTAL_CHARGE, JOB_TOTAL_REIMBURSEMENTS, sum(TC_PRICE) as SUM_TC_PRICE
	, JOB_TOTAL_CHARGE - sum(TC_PRICE), JOB_DT_CREATE, JOB_DT_LUPD
from job left join  tripCharge on job.JOB_ID = tripCharge.TR_JOB 
group by job.JOB_ID
	HAVING JOB_TOTAL_CHARGE - SUM_TC_PRICE != 0
order by JOB_DT_CREATE desc;