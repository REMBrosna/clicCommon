
drop view if exists V_CK_SAGE_BOOKING;

CREATE VIEW V_CK_SAGE_BOOKING AS 
with job as (SELECT jt.*, job.JOB_STATE
	FROM clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
		where jt.JOB_PARENT = job.JOB_ID ),
	accn as ( select * from T_CORE_ACCN acn left join T_CORE_ACCN_CONFIG acnfig
		on acn.accn_id = acnfig.ACFG_ACCNID and acnfig.ACFG_KEY = 'SAGE_ACCN_ID' )
-- CO DN
select 'clicTruck' as SERVICE,'book' as 'SAGE_TYPE', job.JOB_ID as `REFERENCE`, DATE_FORMAT(pay.PTX_DT_PAID, '%Y-%m-%dT%T') as 'DATETIME', 'Consumer' as 'TYPE_ID'
	, accnCO.ACFG_VAL as 'SAGE_ID', 'DebitNote' as 'TRAN_TYPE', dn.DN_NO as 'DOC_NO', DATE_FORMAT(dn.DN_DT_ISSUE,'%Y-%m-%d') as 'ISSUED_DATE'
    , 'IDR' as 'CCY', round(dn.DN_AMT) as 'AMOUNT', 0 as 'VAT', dn.DN_STAMP_DUTY as 'DUTY', concat('N',datediff(DN_DT_DUE, DN_DT_ISSUE)) as TERMS
    , '' as 'TAX_NO', round(dn.DN_TOTAL) as 'TOTAL', pay.PTX_DT_PAID as RCD_DT_BILL_APPROVED, pay.PTX_DT_PAID
from job, accn as accnCO, T_CK_CT_DEBIT_NOTE dn, T_CK_PAYMENT_TXN pay
where job.JOB_PARTY_CO_FF = accnCO.ACCN_ID
	and accnCO.ACCN_ID = dn.DN_TO
	AND job.JOB_STATE = 'APP_BILL'
    and job.JOB_ID = dn.DN_JOB_ID
	AND pay.PTX_TYPE = 'BNKTF'
	AND pay.PTX_PAYMENT_STATE = 'PAID'
    and locate(dn.DN_JOB_ID, pay.PTX_SVC_REF)>0
union
-- TO DN
select 'clicTruck' as service,'book' as 'type', job.JOB_ID as `reference`, DATE_FORMAT(pay.PTX_DT_PAID, '%Y-%m-%dT%T') as 'dateTime', 'Provider' as 'typeid'
	, accnTO.ACFG_VAL as 'Cust./ProvicerId', 'DebitNote' as 'Type', dn.DN_NO as 'DocNo', DATE_FORMAT(dn.DN_DT_ISSUE,'%Y-%m-%d') as 'issuedDate'
    , 'IDR' as 'ccy', round(dn.DN_AMT) as 'amount', 0 as 'vat', dn.DN_STAMP_DUTY as 'duty', concat('N',datediff(DN_DT_DUE, DN_DT_ISSUE)) as terms
    , '' as 'taxNo', round(dn.DN_TOTAL) as 'total', pay.PTX_DT_PAID as RCD_DT_BILL_APPROVED, pay.PTX_DT_PAID
from job, accn as accnTO, T_CK_CT_DEBIT_NOTE dn, T_CK_PAYMENT_TXN pay
where job.JOB_PARTY_TO = accnTO.ACCN_ID
	and accnTO.ACCN_ID = dn.DN_FROM
	AND job.JOB_STATE = 'APP_BILL'
    and job.JOB_ID = dn.DN_JOB_ID
	AND pay.PTX_TYPE = 'BNKTF'
	AND pay.PTX_PAYMENT_STATE = 'PAID'
    and locate(dn.DN_JOB_ID, pay.PTX_SVC_REF)>0
union
-- CO Invoice
select 'clicTruck' as service,'book' as 'type', job.JOB_ID as `reference`, DATE_FORMAT(pay.PTX_DT_PAID, '%Y-%m-%dT%T') as 'dateTime', 'Consumer' as 'typeid'
	, accnCO.ACFG_VAL as 'Cust./ProvicerId', 'Invoice' as 'Type', INV_NO as 'DocNo', DATE_FORMAT(inv.INV_DT_ISSUE,'%Y-%m-%d') as 'issuedDate'
    , 'IDR' as 'ccy', round(inv.INV_AMT) as 'amount', round(INV_VAT) as 'vat', round(INV_STAMP_DUTY) as 'duty', concat('N',datediff(INV_DT_DUE, INV_DT_ISSUE)) as terms
    , INV_SAGE_TAX_NO as 'taxNo', round(inv.INV_TOTAL) as 'total', pay.PTX_DT_PAID as RCD_DT_BILL_APPROVED, pay.PTX_DT_PAID
from job, accn as accnCO, T_CK_CT_PLATFORM_INVOICE inv, T_CK_PAYMENT_TXN pay
where job.JOB_PARTY_CO_FF = accnCO.ACCN_ID
	and accnCO.ACCN_ID = inv.INV_TO
	AND job.JOB_STATE = 'APP_BILL'
    and job.JOB_ID = inv.INV_JOB_ID
	AND pay.PTX_TYPE = 'BNKTF'
	AND pay.PTX_PAYMENT_STATE = 'PAID'
    and locate(inv.INV_JOB_ID, pay.PTX_SVC_REF)>0
union
-- TO Invoice
select 'clicTruck' as service,'book' as 'type', job.JOB_ID as `reference`, DATE_FORMAT(pay.PTX_DT_PAID, '%Y-%m-%dT%T') as 'dateTime', 'Provider' as 'typeid'
	, accnTO.ACFG_VAL as 'Cust./ProvicerId', 'Invoice' as 'Type', INV_NO as 'DocNo', DATE_FORMAT(inv.INV_DT_ISSUE,'%Y-%m-%d') as 'issuedDate'
    , 'IDR' as 'ccy', round(-inv.INV_AMT) as 'amount', round(-INV_VAT) as 'vat', round(-INV_STAMP_DUTY) as 'duty', concat('N',datediff(INV_DT_DUE, INV_DT_ISSUE)) as terms
    , INV_SAGE_TAX_NO as 'taxNo', round(-inv.INV_TOTAL) as 'total', pay.PTX_DT_PAID as RCD_DT_BILL_APPROVED, pay.PTX_DT_PAID
from job, accn as accnTO, T_CK_CT_PLATFORM_INVOICE inv, T_CK_PAYMENT_TXN pay
where job.JOB_PARTY_TO = accnTO.ACCN_ID
	and accnTO.ACCN_ID = inv.INV_TO
	AND job.JOB_STATE = 'APP_BILL'
    and job.JOB_ID = inv.INV_JOB_ID
	AND pay.PTX_TYPE = 'BNKTF'
	AND pay.PTX_PAYMENT_STATE = 'PAID'
    and locate(inv.INV_JOB_ID, pay.PTX_SVC_REF)>0
order by dateTime asc, TYPE_ID desc, TRAN_TYPE desc
