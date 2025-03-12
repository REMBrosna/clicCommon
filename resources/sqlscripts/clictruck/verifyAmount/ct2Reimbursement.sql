

select jt.JOB_ID, j.JOB_STATE, jt.JOB_TOTAL_REIMBURSEMENTS, jt.JOB_OUT_PAYMENT_DT_DUE,  jt.JOB_OUT_PAYMENT_STATE, sum(TR_TOTAL), j.JOB_STATE
FROM clickargo2.T_CK_JOB_TRUCK jt left join	clickargo2.T_CK_CT_TRIP t on jt.JOB_ID = t.TR_JOB
	left join clickargo2.T_CK_JOB j on jt.JOB_PARENT = j.JOB_ID
	left join clickargo2.T_CK_CT_TRIP_REIMBURSEMENT r on t.TR_ID = r.TR_TRIP
where r.TR_STATUS = 'A'
group by jt.JOB_ID, jt.JOB_TOTAL_REIMBURSEMENTS 
having jt.JOB_TOTAL_REIMBURSEMENTS != sum(TR_TOTAL);
