
-- TO percent amount
with job as (SELECT jt.*, job.JOB_STATE 
	FROM clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID),
	con as ( SELECT CON_TO, CON_CO_FF, ccTo.*
		FROM clickargo2.T_CK_CT_CONTRACT c, clickargo2.T_CK_CT_CONTRACT_CHARGE ccTo
		, clickargo2.T_CK_CT_CONTRACT_CHARGE ccFF
		where c.CON_CHARGE_TO = ccTo.CONC_ID
			and c.CON_CHARGE_CO_FF = ccFF.CONC_ID ),
	platFeeCon as(
		SELECT INV_TO, inv.INV_AMT, con.*
			, round((JOB_TOTAL_CHARGE + JOB_TOTAL_REIMBURSEMENTS ) * CONC_PLTFEE_AMT/100) computedFee, job.*
		FROM job left join con on job.JOB_PARTY_CO_FF = con.CON_CO_FF and job.JOB_PARTY_TO = con.CON_TO 
				left join clickargo2.T_CK_CT_PLATFORM_INVOICE inv on inv.INV_JOB_ID = job.JOB_ID and inv.INV_TO = job.JOB_PARTY_TO
		where JOB_STATE = 'APP_BILL'
    )
SELECT INV_AMT, computedFee, JOB_ID, JOB_PARTY_TO, JOB_PARTY_CO_FF
FROM platFeeCon t
where CONC_PLTFEE_TYPE = 'P'
	and INV_AMT != computedFee
;
-- 
    
-- TO fixed amount
with job as (SELECT jt.*, job.JOB_STATE 
	FROM clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID),
	con as ( SELECT CON_TO, CON_CO_FF, ccTo.*
		FROM clickargo2.T_CK_CT_CONTRACT c, clickargo2.T_CK_CT_CONTRACT_CHARGE ccTo
		, clickargo2.T_CK_CT_CONTRACT_CHARGE ccFF
		where c.CON_CHARGE_TO = ccTo.CONC_ID
			and c.CON_CHARGE_CO_FF = ccFF.CONC_ID ),
	platFeeCon as(
		SELECT INV_TO, inv.INV_AMT, con.CONC_PLTFEE_AMT computedFee, CONC_PLTFEE_TYPE, INV_DT_CREATE, job.*
		FROM job left join con on job.JOB_PARTY_CO_FF = con.CON_CO_FF and job.JOB_PARTY_TO = con.CON_TO 
				left join clickargo2.T_CK_CT_PLATFORM_INVOICE inv on inv.INV_JOB_ID = job.JOB_ID and inv.INV_TO = job.JOB_PARTY_TO
		where JOB_STATE = 'APP_BILL'
    )
SELECT JOB_ID, INV_TO, INV_AMT, computedFee, INV_DT_CREATE, JOB_PARTY_TO, JOB_PARTY_CO_FF
FROM platFeeCon t
where CONC_PLTFEE_TYPE = 'F'
	and INV_AMT != computedFee
    and INV_DT_CREATE > '2023-10-01'
;
-- 5 transaction has error between 375 and376
-- 38 transaction has error between 589 and306 because changed contract.
-- ---
select INV_AMT, INV_VAT, INV_TOTAL, (INV_VAT - round(INV_AMT * 0.11)) diff, inv.* 
from clickargo2.T_CK_CT_PLATFORM_INVOICE inv
where INV_VAT != round(INV_AMT * 0.11)
    and INV_DT_ISSUE < '2023-08-11';
    
select INV_AMT, INV_VAT, INV_TOTAL, (INV_VAT - floor(INV_AMT * 0.11)) diff, inv.* 
from clickargo2.T_CK_CT_PLATFORM_INVOICE inv
where INV_VAT != floor(INV_AMT * 0.11)
    and INV_DT_ISSUE > '2023-08-11';
    
select INV_AMT, INV_VAT, INV_TOTAL, (INV_TOTAL - (INV_VAT + INV_AMT)) diff, inv.* 
from clickargo2.T_CK_CT_PLATFORM_INVOICE inv
where ((INV_TOTAL - (INV_VAT + INV_AMT)) > 2
	OR (INV_TOTAL - (INV_VAT + INV_AMT)) < -2) ;
-- --
-- 
--

-- select INV_AMT, INV_VAT, inv.* from clickargo2.T_CK_CT_PLATFORM_INVOICE inv;


select i.INV_ID, i.INV_AMT,  ii.ITM_AMOUNT
from T_CK_CT_PLATFORM_INVOICE i, T_CK_CT_PLATFORM_INVOICE_ITEM ii
where i.INV_ID = ii.ITM_INVOICE
	and i.INV_AMT != ii.ITM_AMOUNT;
    
select * from T_CK_CT_PLATFORM_INVOICE_ITEM
where ITM_UNIT_PRICE != ITM_AMOUNT;

select * from T_CK_CT_PLATFORM_INVOICE_ITEM
where ITM_SNO > 1;

-- only 1 item for 1 invoice;
select ITM_SNO, ITM_INVOICE, count(*) 
from T_CK_CT_PLATFORM_INVOICE_ITEM
group by ITM_SNO, ITM_INVOICE
having count(*)>1 ;

-- should 2 for job id;
select ITM_SNO, ITM_REF, count(*) 
from T_CK_CT_PLATFORM_INVOICE_ITEM
group by ITM_SNO, ITM_REF
having count(*) !=2 ;