-- PAY IN

drop view if exists V_DO_SAGE_PAY_IN;

CREATE VIEW V_DO_SAGE_PAY_IN  as
WITH accn as ( select * from T_CORE_ACCN acn left join T_CORE_ACCN_CONFIG acnfig
		on acn.accn_id = acnfig.ACFG_ACCNID and acnfig.ACFG_KEY = 'SAGE_ACCN_ID' )
select 'ClicDO' as SERVICE,'payment_in' as 'SAGE_TYPE', payIn.PTX_ID as `REFERENCE`, DATE_FORMAT(payIn.PTX_DT_PAID, '%Y-%m-%dT%T') as 'DATETIME'
	,round(payIn.PTX_AMOUNT) as 'PAY_TOTAL', accn.ACCN_ID as 'CUNSUMER_ID', 'DebitNote' as 'DOC_TYPE'
	, p.DOP_INV_NO as 'DOC_NO', DATE_FORMAT(p.DOP_DT_CREATE,'%Y-%m-%d') as 'ISSUED_DATE'
    , 'IDR' as 'CCY', round(doInv.INV_AMOUNT) as 'AMOUNT', round(doInv.INV_TAX_AMOUNT) as 'VAT', (doInv.INV_STAMP_DUTY) as 'DUTY', 'COD' as TERMS
    , '' as 'TAX_NO',round(p.DOP_AMOUNT) as 'TOTAL', payIn.PTX_DT_PAID as DATETIME_FIELD
from accn, T_CK_DO_INVOICE doInv, T_CK_PAYMENT_TXN payIn, T_CK_DO_PAYMENT p
where doInv.INV_NO = p.DOP_INV_NO
	and payIn.PTX_ID = p.DOP_PAYMENT_TXN
	and accn.ACCN_ID = payIn.PTX_PAYER
	and payIn.PTX_SVC = 'CLICDO'
	AND payIn.PTX_PAYMENT_STATE = 'SUCCESS'
	AND payIn.PTX_PAYEE = 'GLI'
    and p.DOP_INV_TYPE not in ('I-PLF','I-PLFE')
    and p.DOP_AMOUNT > 0 
union
select 'ClicDO' as SERVICE,'payment_in' as 'SAGE_TYPE', payIn.PTX_ID as `REFERENCE`, DATE_FORMAT(payIn.PTX_DT_PAID, '%Y-%m-%dT%T') as 'DATETIME'
	,round(payIn.PTX_AMOUNT) as 'PAY_TOTAL', accn.ACCN_ID as 'CUNSUMER_ID', 'Invoice' as 'DOC_TYPE'
	, p.DOP_INV_NO as 'DOC_NO', DATE_FORMAT(p.DOP_DT_CREATE,'%Y-%m-%d') as 'ISSUED_DATE'
    , 'IDR' as 'CCY', round(doInv.INV_AMOUNT) as 'AMOUNT', round(doInv.INV_TAX_AMOUNT) as 'VAT', (doInv.INV_STAMP_DUTY) as 'DUTY', 'COD' as TERMS
    , doInv.INV_TAX_NO as 'TAX_NO',round(p.DOP_AMOUNT) as 'TOTAL', payIn.PTX_DT_PAID as DATETIME_FIELD
from accn, T_CK_DO_INVOICE doInv, T_CK_PAYMENT_TXN payIn, T_CK_DO_PAYMENT p
where doInv.INV_NO = p.DOP_INV_NO
	and payIn.PTX_ID = p.DOP_PAYMENT_TXN
	and accn.ACCN_ID = payIn.PTX_PAYER
	and payIn.PTX_SVC = 'CLICDO'
	AND payIn.PTX_PAYMENT_STATE = 'SUCCESS'
	AND payIn.PTX_PAYEE = 'GLI'
    and p.DOP_INV_TYPE in ('I-PLF','I-PLFE')
    AND p.DOP_AMOUNT > 0
 ;