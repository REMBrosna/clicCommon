
with job as (SELECT jt.*, job.JOB_STATE FROM clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
where jt.JOB_PARENT = job.JOB_ID)
SELECT CJN_TXN_REF, sum(CJN_RESERVE ) SUM_CJN_RESERVE, sum(CJN_UTILIZED ) SUM_CJN_UTILIZED, job.JOB_STATE, job.JOB_TOTAL_CHARGE
	, sum(CJN_UTILIZED )- job.JOB_TOTAL_CHARGE - job.JOB_TOTAL_REIMBURSEMENTS as checkUtilized
    , max(CJN_DT_CREATE)
FROM  job left join clickargo2.T_CK_CREDIT_JOURNAL j on j.CJN_TXN_REF = job.JOB_ID
where JOB_STATE = 'APP_BILL'
group by CJN_TXN_REF
having (checkUtilized != 0 OR SUM_CJN_RESERVE != 0)
order by SUM_CJN_RESERVE DESC;


SELECT CJN_TXN_REF, sum(CJN_TXN_TYPE = 'JOB_SUBMIT')  sum_JOB_SUBMIT
	, sum(CJN_TXN_TYPE = 'JOB_CANCEL')  sum_JOB_CANCEL
	, sum(CJN_TXN_TYPE = 'JOB_REJECT')  JOB_REJECT
FROM clickargo2.T_CK_CREDIT_JOURNAL
group by CJN_TXN_REF
having sum_JOB_CANCEL > 0 and (sum_JOB_SUBMIT - sum_JOB_CANCEL) > 1;

with multiSubmit as ( SELECT CJN_TXN_REF, sum(CJN_TXN_TYPE = 'JOB_SUBMIT')  sum_JOB_SUBMIT
	, sum(CJN_TXN_TYPE = 'JOB_CANCEL')  sum_JOB_CANCEL
	, sum(CJN_TXN_TYPE = 'JOB_REJECT')  JOB_REJECT
FROM clickargo2.T_CK_CREDIT_JOURNAL
group by CJN_TXN_REF
having sum_JOB_CANCEL > 0 and (sum_JOB_SUBMIT - sum_JOB_CANCEL) > 1 )
select * from clickargo2.T_CK_CREDIT_JOURNAL j, multiSubmit
where j.CJN_TXN_REF = multiSubmit.CJN_TXN_REF;

select * FROM clickargo2.T_CK_CREDIT_JOURNAL
where CJN_RESERVE = 0 and CJN_UTILIZED = 0
	and CJN_TXN_TYPE = 'JOB_SUBMIT_REIMBURSEMENT'
order by CJN_DT_CREATE desc;


