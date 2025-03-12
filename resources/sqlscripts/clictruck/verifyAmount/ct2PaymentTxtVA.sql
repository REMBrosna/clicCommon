SELECT PTX_ID, PTX_TYPE, PTX_SVC_REF, PTX_AMOUNT, pt.PTX_PAYMENT_STATE, (sum(DN_TOTAL) + sum(INV_TOTAL) ) as computeRst,
	PTX_AMOUNT - (sum(DN_TOTAL) + sum(INV_TOTAL) ) as rst
, sum(DN_TOTAL) sum_dn_amt, sum(INV_TOTAL) sum_inv_amt, PTX_DT_CREATE
FROM clickargo2.T_CK_PAYMENT_TXN pt left join clickargo2.T_CK_CT_PLATFORM_INVOICE inv on pt.PTX_PAYER = inv.INV_TO and locate(inv.INV_JOB_ID, pt.PTX_SVC_REF) > 0
		left join clickargo2.T_CK_CT_DEBIT_NOTE dn on pt.PTX_PAYER = dn.DN_TO and inv.INV_JOB_ID = dn.DN_JOB_ID and locate(dn.DN_JOB_ID, pt.PTX_SVC_REF) > 0
where PTX_TYPE = 'VA'
    and pt.PTX_PAYEE = 'GLI'
    and PTX_DT_CREATE > '2023-07-01'
    and PTX_PAYMENT_STATE != 'CANCELLED'
group by PTX_ID, PTX_SVC_REF
having PTX_AMOUNT != computeRst
order by PTX_DT_CREATE desc;