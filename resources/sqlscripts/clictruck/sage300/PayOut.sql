use clickargo2;

drop view V_CK_SAGE_PAY_OUT;

CREATE VIEW V_CK_SAGE_PAY_OUT AS 
with job as (SELECT jt.*, job.JOB_STATE, rd.RCD_DT_BILL_APPROVED 
	FROM T_CK_JOB_TRUCK jt, T_CK_JOB job, T_CK_RECORD_DATE rd
		where jt.JOB_PARENT = job.JOB_ID and job.JOB_DATES = rd.RCD_ID),
	accn as ( select * from T_CORE_ACCN acn left join T_CORE_ACCN_CONFIG acnfig
		on acn.accn_id = acnfig.ACFG_ACCNID and acnfig.ACFG_KEY = 'SAGE_ACCN_ID' )
-- CO DN
select 'ClicTruck' as SERVICE,'payment_out' as 'SAGE_TYPE', pay.PTX_ID as `REFERENCE`, DATE_FORMAT(pay.PTX_DT_PAID, '%Y-%m-%dT%T') as 'DATETIME'
	,round(pay.PTX_AMOUNT) as 'PAY_TOTAL', accn.ACFG_VAL as 'PROVIDER_ID', 'DebitNote' as 'DOC_TYPE'
	, dn.DN_NO as 'DOC_NO', DATE_FORMAT(dn.DN_DT_ISSUE,'%Y-%m-%d') as 'ISSUED_DATE'
    , 'IDR' as 'CCY', round(dn.DN_AMT) as 'AMOUNT', 0 as 'VAT', 0 as 'DUTY', concat('N', datediff(DN_DT_DUE, DN_DT_ISSUE)) as TERMS
    , '' as 'TAX_NO',round(dn.DN_TOTAL) as 'TOTAL', pay.PTX_DT_PAID
from accn as accn, T_CK_CT_DEBIT_NOTE dn, T_CK_PAYMENT_TXN pay
where pay.PTX_PAYEE = accn.ACCN_ID
	and accn.ACCN_ID = dn.DN_FROM
	AND pay.PTX_TYPE = 'BNKTF'
	AND pay.PTX_PAYMENT_STATE = 'PAID'
    and locate(dn.DN_JOB_ID, pay.PTX_SVC_REF)>0
union
-- CO Invoice
select 'ClicTruck' as service, 'payment_out' as 'type', pay.PTX_ID as `reference`, DATE_FORMAT(pay.PTX_DT_PAID, '%Y-%m-%dT%T') as 'dateTime'
	,round(pay.PTX_AMOUNT) as 'payTotal', accn.ACFG_VAL as 'PROVIDER_ID', 'Invoice' as 'docType'
    , INV_NO as 'DocNo', DATE_FORMAT(inv.INV_DT_ISSUE,'%Y-%m-%d') as 'issuedDate'
    , 'IDR' as 'ccy', round(-inv.INV_AMT) as 'amount', round(-INV_VAT) as 'vat', round(INV_STAMP_DUTY) as 'duty', concat('N',datediff(INV_DT_DUE, INV_DT_ISSUE)) as terms
    , INV_SAGE_TAX_NO as 'taxNo', round(-inv.INV_TOTAL) as 'total', pay.PTX_DT_PAID
from accn as accn, T_CK_CT_PLATFORM_INVOICE inv, T_CK_PAYMENT_TXN pay
where pay.PTX_PAYEE = accn.ACCN_ID
	and accn.ACCN_ID = inv.INV_TO
	AND pay.PTX_TYPE = 'BNKTF'
	AND pay.PTX_PAYMENT_STATE = 'PAID'
    and locate(inv.INV_JOB_ID, pay.PTX_SVC_REF)>0
order by `DATETIME` asc, DOC_NO asc;
