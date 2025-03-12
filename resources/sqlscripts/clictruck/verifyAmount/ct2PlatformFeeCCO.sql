    
-- CO percent amount
with job as (SELECT jt.*, job.JOB_STATE 
	FROM clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID),
	con as ( SELECT CON_TO, CON_CO_FF, ccFF.*
		FROM clickargo2.T_CK_CT_CONTRACT c, clickargo2.T_CK_CT_CONTRACT_CHARGE ccTo
		, clickargo2.T_CK_CT_CONTRACT_CHARGE ccFF
		where c.CON_CHARGE_TO = ccTo.CONC_ID
			and c.CON_CHARGE_CO_FF = ccFF.CONC_ID ),
	platFeeCon as(
		SELECT INV_TO, inv.INV_AMT, con.*
			, round((JOB_TOTAL_CHARGE + JOB_TOTAL_REIMBURSEMENTS ) * CONC_PLTFEE_AMT/100) computedFee, job.*
		FROM job left join con on job.JOB_PARTY_CO_FF = con.CON_CO_FF and job.JOB_PARTY_TO = con.CON_TO 
				left join clickargo2.T_CK_CT_PLATFORM_INVOICE inv on inv.INV_JOB_ID = job.JOB_ID and inv.INV_TO = job.JOB_PARTY_CO_FF
		where JOB_STATE = 'APP_BILL'
    )
SELECT INV_AMT, computedFee, JOB_ID, JOB_PARTY_TO, JOB_PARTY_CO_FF, JOB_DT_CREATE, (JOB_TOTAL_CHARGE + JOB_TOTAL_REIMBURSEMENTS) amt
FROM platFeeCon t
where CONC_PLTFEE_TYPE = 'P'
	and INV_AMT != computedFee
    and JOB_DT_CREATE > '2023-10-01'
;

-- 
-- CO fix amount
with job as (SELECT jt.*, job.JOB_STATE 
	FROM clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID),
	con as ( SELECT CON_TO, CON_CO_FF, ccFF.*
		FROM clickargo2.T_CK_CT_CONTRACT c, clickargo2.T_CK_CT_CONTRACT_CHARGE ccTo
		, clickargo2.T_CK_CT_CONTRACT_CHARGE ccFF
		where c.CON_CHARGE_TO = ccTo.CONC_ID
			and c.CON_CHARGE_CO_FF = ccFF.CONC_ID ),
	platFeeCon as(
		SELECT INV_TO, inv.INV_AMT, con.*, INV_DT_CREATE
			, con.CONC_PLTFEE_AMT computedFee, job.*
		FROM job left join con on job.JOB_PARTY_CO_FF = con.CON_CO_FF and job.JOB_PARTY_TO = con.CON_TO 
				left join clickargo2.T_CK_CT_PLATFORM_INVOICE inv on inv.INV_JOB_ID = job.JOB_ID and inv.INV_TO = job.JOB_PARTY_CO_FF
		where JOB_STATE = 'APP_BILL'
    )
SELECT INV_AMT, computedFee, JOB_ID, INV_DT_CREATE, JOB_PARTY_TO, JOB_PARTY_CO_FF
FROM platFeeCon t
where CONC_PLTFEE_TYPE = 'F'
	and INV_AMT != computedFee
;
