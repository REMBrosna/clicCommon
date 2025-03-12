-- use clickargoP;

CREATE VIEW V_CK_SAGE_PAY_IN AS 
with job as (SELECT jt.*, job.JOB_STATE, rd.RCD_DT_BILL_APPROVED 
	FROM T_CK_JOB_TRUCK jt, T_CK_JOB job, T_CK_RECORD_DATE rd
		where jt.JOB_PARENT = job.JOB_ID and job.JOB_DATES = rd.RCD_ID),
	accn as ( select * from T_CORE_ACCN acn left join T_CORE_ACCN_CONFIG acnfig
		on acn.accn_id = acnfig.ACFG_ACCNID and acnfig.ACFG_KEY = 'SAGE_ACCN_ID' )
-- CO DN
select 'ClicTruck' as SERVICE,'payment_in' as 'SAGE_TYPE', payIn.PTX_ID as `REFERENCE`, DATE_FORMAT(payIn.PTX_DT_PAID, '%Y-%m-%dT%T') as 'DATETIME'
	,round(payIn.PTX_AMOUNT) as 'PAY_TOTAL', accnCO.ACFG_VAL as 'CUNSUMER_ID', 'DebitNote' as 'DOC_TYPE'
	, dn.DN_NO as 'DOC_NO', DATE_FORMAT(dn.DN_DT_ISSUE,'%Y-%m-%d') as 'ISSUED_DATE'
    , 'IDR' as 'CCY', round(dn.DN_AMT) as 'AMOUNT', 0 as 'VAT', 0 as 'DUTY', concat('N', datediff(DN_DT_DUE, DN_DT_ISSUE)) as TERMS
    , '' as 'TAX_NO',round(dn.DN_TOTAL) as 'TOTAL', payIn.PTX_DT_PAID
from accn as accnCO, T_CK_CT_DEBIT_NOTE dn, T_CK_PAYMENT_TXN payIn
where payIn.PTX_PAYER = accnCO.ACCN_ID
	and accnCO.ACCN_ID = dn.DN_TO
	AND payIn.PTX_TYPE = 'VA'
	AND payIn.PTX_PAYMENT_STATE = 'PAID'
    and locate(dn.DN_JOB_ID, payIn.PTX_SVC_REF)>0
union
-- CO Invoice
select 'ClicTruck' as service, 'payment_in' as 'type', payIn.PTX_ID as `reference`, DATE_FORMAT(payIn.PTX_DT_PAID, '%Y-%m-%dT%T') as 'dateTime'
	,round(payIn.PTX_AMOUNT) as 'payTotal', accnCO.ACFG_VAL as 'CunsumerId', 'Invoice' as 'docType'
    , INV_NO as 'DocNo', DATE_FORMAT(inv.INV_DT_ISSUE,'%Y-%m-%d') as 'issuedDate'
    , 'IDR' as 'ccy', round(inv.INV_AMT) as 'amount', round(INV_VAT) as 'vat', round(INV_STAMP_DUTY) as 'duty', concat('N',datediff(INV_DT_DUE, INV_DT_ISSUE)) as terms
    , INV_SAGE_TAX_NO as 'taxNo', round(inv.INV_TOTAL) as 'total', payIn.PTX_DT_PAID
from accn as accnCO, T_CK_CT_PLATFORM_INVOICE inv, T_CK_PAYMENT_TXN payIn
where payIn.PTX_PAYER = accnCO.ACCN_ID
	and accnCO.ACCN_ID = inv.INV_TO
	AND payIn.PTX_TYPE = 'VA'
	AND payIn.PTX_PAYMENT_STATE = 'PAID'
    and locate(inv.INV_JOB_ID, payIn.PTX_SVC_REF)>0
order by DOC_TYPE asc, DOC_NO asc;
