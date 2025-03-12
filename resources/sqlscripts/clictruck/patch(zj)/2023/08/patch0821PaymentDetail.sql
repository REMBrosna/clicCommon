

create table zBackup.T_CK_CT_PAYMENT_0821 as select * from clickargo2.T_CK_CT_PAYMENT tccp;

update clickargo2.T_CK_CT_PAYMENT tccp join
	(select CTP_JOB, CTP_AMOUNT, inv.INV_TOTAL , CTP_DT_CREATE, INV_DT_CREATE, (tccp1.CTP_AMOUNT - inv.INV_TOTAL) diff
	from clickargo2.T_CK_PAYMENT_TXN txn
		, clickargo2.T_CK_CT_PAYMENT tccp1
		, clickargo2.T_CK_CT_PLATFORM_INVOICE inv
		, clickargo2.T_CORE_ACCN accn
	where txn.PTX_ID = tccp1.CTP_PAYMENT_TXN
		and tccp1.CTP_JOB = inv.INV_JOB_ID
		and inv.INV_TO = accn.ACCN_ID
		and txn.PTX_TYPE = 'BNKTF'
		and accn.ACCN_TYPE = 'ACC_TYPE_TO'
		and CTP_REF like 'CT-PF%'
		and tccp1.CTP_STATE != 'CAN'
		and tccp1.CTP_AMOUNT != inv.INV_TOTAL) d
set tccp.CTP_AMOUNT = d.INV_TOTAL
where  tccp.CTP_JOB = d.CTP_JOB
	and tccp.CTP_REF like 'CT-PF%'
	and tccp.CTP_STATE != 'CAN'
	and tccp.CTP_AMOUNT != d.INV_TOTAL
;

UPDATE `clickargo2`.`T_CK_CT_PAYMENT` SET `CTP_AMOUNT` = '8100000.00' WHERE (`CTP_ID` = 'CTPCO1308064525419695') and CTP_JOB = 'CKCTJ2023070799185';
UPDATE `clickargo2`.`T_CK_CT_PAYMENT` SET `CTP_AMOUNT` = '8700000.00' WHERE (`CTP_ID` = 'CTPCO1318854422596244') and CTP_JOB = 'CKCTJ2023070715678';


-- select * 
select tccp.CTP_AMOUNT, d.INV_TOTAL
from clickargo2.T_CK_CT_PAYMENT tccp join
	(select CTP_JOB, CTP_AMOUNT, inv.INV_TOTAL , CTP_DT_CREATE, INV_DT_CREATE, (tccp1.CTP_AMOUNT - inv.INV_TOTAL) diff
	from clickargo2.T_CK_PAYMENT_TXN txn
		, clickargo2.T_CK_CT_PAYMENT tccp1
		, clickargo2.T_CK_CT_PLATFORM_INVOICE inv
		, clickargo2.T_CORE_ACCN accn
	where txn.PTX_ID = tccp1.CTP_PAYMENT_TXN
		and tccp1.CTP_JOB = inv.INV_JOB_ID
		and inv.INV_TO = accn.ACCN_ID
		and txn.PTX_TYPE = 'BNKTF'
		and accn.ACCN_TYPE = 'ACC_TYPE_TO'
		and CTP_REF like 'CT-PF%'
		and tccp1.CTP_STATE != 'CAN'
		and tccp1.CTP_AMOUNT != inv.INV_TOTAL) d
where tccp.CTP_JOB = d.CTP_JOB
		and tccp.CTP_REF like 'CT-PF%'
		and tccp.CTP_STATE != 'CAN'
	and tccp.CTP_AMOUNT != d.INV_TOTAL
;


select CTP_JOB, CTP_AMOUNT, inv.INV_TOTAL , CTP_DT_CREATE, INV_DT_CREATE, (tccp.CTP_AMOUNT - inv.INV_TOTAL) diff
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
		and tccp.CTP_AMOUNT != inv.INV_TOTAL