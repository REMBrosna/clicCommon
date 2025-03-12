-- select *
select CTP_JOB, CTP_ID, tccp.CTP_AMOUNT, dn.DN_TOTAL , CTP_DT_CREATE, DN_DT_CREATE, tccp.*
from clickargo2.T_CK_CT_PAYMENT tccp, clickargo2.T_CK_CT_DEBIT_NOTE dn
where tccp.CTP_JOB = dn.DN_JOB_ID
	and CTP_REF like 'CT-DN%'
    and tccp.CTP_STATE != 'CAN'
    and tccp.CTP_AMOUNT != dn.DN_TOTAL;
    
-- select *
select CTP_JOB, tccp.CTP_AMOUNT, inv.INV_TOTAL , CTP_DT_CREATE, INV_DT_CREATE, (tccp.CTP_AMOUNT - inv.INV_TOTAL) d
from clickargo2.T_CK_PAYMENT_TXN txn
	, clickargo2.T_CK_CT_PAYMENT tccp
	, clickargo2.T_CK_CT_PLATFORM_INVOICE inv
    , clickargo2.T_CORE_ACCN accn
where txn.PTX_ID = tccp.CTP_PAYMENT_TXN
	and tccp.CTP_JOB = inv.INV_JOB_ID
    and inv.INV_TO = accn.ACCN_ID
	and txn.PTX_TYPE = 'BNKTF'
    and accn.ACCN_TYPE = 'ACC_TYPE_TO'
	and CTP_REF like 'CT-PF%'
    and tccp.CTP_STATE != 'CAN'
    and tccp.CTP_AMOUNT != inv.INV_TOTAL;

