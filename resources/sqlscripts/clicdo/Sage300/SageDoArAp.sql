-- DO Sage Booking

drop view if exists V_DO_SAGE_AR_AP;

CREATE VIEW V_DO_SAGE_AR_AP  as
WITH accn as ( select * from T_CORE_ACCN acn left join T_CORE_ACCN_CONFIG acnfig
		on acn.accn_id = acnfig.ACFG_ACCNID and acnfig.ACFG_KEY = 'SAGE_ACCN_ID' )
select 'ClicDO' as SERVICE,'book' as 'SAGE_TYPE', inv.INV_JOB_ID as `REFERENCE`, DATE_FORMAT(inv.INV_DT_CREATE, '%Y-%m-%dT%T') as 'DATETIME'
, 'Provider' as 'TYPE_ID', accn.ACCN_ID as 'SAGE_ID', 'DebitNote' as 'TRAN_TYPE', inv.INV_NO as 'DOC_NO', DATE_FORMAT(inv.INV_DT_CREATE,'%Y-%m-%d') as 'ISSUED_DATE'
    , 'IDR' as 'CCY', round(inv.INV_AMOUNT) as 'AMOUNT', inv.INV_TAX_AMOUNT as 'VAT', inv.INV_STAMP_DUTY as 'DUTY', 'COD' as TERMS
   , inv.INV_TAX_NO as 'TAX_NO', round(inv.INV_AMOUNT + IFNULL(inv.INV_TAX_AMOUNT,0) + IFNULL(inv.INV_STAMP_DUTY, 0)) as 'TOTAL', inv.INV_DT_CREATE as DATETIME_FIELD
from accn, T_CK_DO_INVOICE inv, T_CK_JOB job
where accn.ACCN_ID = job.JOB_OWNER_ACCN
	and inv.INV_JOB_ID = job.JOB_ID
    and inv.INV_TYPE not in ('I-PLF','I-PLFE')
    and job.JOB_STATE not in ('DEL')
    and inv.INV_AMOUNT > 0 
union
select 'ClicDO' as SERVICE,'book' as 'SAGE_TYPE', inv.INV_JOB_ID as `REFERENCE`, DATE_FORMAT(inv.INV_DT_CREATE, '%Y-%m-%dT%T') as 'DATETIME'
, 'Provider' as 'TYPE_ID', accn.ACCN_ID as 'SAGE_ID', 'Invoice' as 'TRAN_TYPE', inv.INV_NO as 'DOC_NO', DATE_FORMAT(inv.INV_DT_CREATE,'%Y-%m-%d') as 'ISSUED_DATE'
    , 'IDR' as 'CCY', round(inv.INV_AMOUNT) as 'AMOUNT', inv.INV_TAX_AMOUNT as 'VAT', '0' as 'DUTY', 'COD' as TERMS
   , inv.INV_TAX_NO as 'TAX_NO', round(inv.INV_AMOUNT + IFNULL(inv.INV_TAX_AMOUNT,0)) as 'TOTAL', inv.INV_DT_CREATE as DATETIME_FIELD
from accn, T_CK_DO_INVOICE inv, T_CK_JOB job
where accn.ACCN_ID = job.JOB_OWNER_ACCN
	and inv.INV_JOB_ID = job.JOB_ID
    and inv.INV_TYPE in ('I-PLF','I-PLFE')
    and job.JOB_STATE not in ('DEL')
    and inv.INV_AMOUNT > 0 
union
select 'ClicDO' as SERVICE,'book' as 'SAGE_TYPE', inv.INV_JOB_ID as `REFERENCE`, DATE_FORMAT(inv.INV_DT_CREATE, '%Y-%m-%dT%T') as 'DATETIME'
, 'Consumer' as 'TYPE_ID', accn.ACCN_ID as 'SAGE_ID', 'DebitNote' as 'TRAN_TYPE', concat(inv.INV_NO, "_P") as 'DOC_NO', DATE_FORMAT(inv.INV_DT_CREATE,'%Y-%m-%d') as 'ISSUED_DATE'
    , 'IDR' as 'CCY', round(inv.INV_AMOUNT) as 'AMOUNT', inv.INV_TAX_AMOUNT as 'VAT', '0' as 'DUTY', 'COD' as TERMS
   , inv.INV_TAX_NO as 'TAX_NO', round(inv.INV_AMOUNT + IFNULL(inv.INV_TAX_AMOUNT,0)) as 'TOTAL', inv.INV_DT_CREATE as DATETIME_FIELD
from accn, T_CK_DO_INVOICE inv, T_CK_JOB job
where accn.ACCN_ID = job.JOB_SL_ACCN
	and inv.INV_JOB_ID = job.JOB_ID
    and inv.INV_TYPE not in ('I-PLF','I-PLFE')
    and job.JOB_STATE not in ('DEL')
    and inv.INV_AMOUNT > 0  
;