

update clickargo2.T_CK_CT_PLATFORM_INVOICE inv
set INV_TOTAL = INV_VAT + INV_AMT
where INV_TOTAL != INV_VAT + INV_AMT 
	and exists(
		SELECT * FROM clickargo2.T_CK_PAYMENT_TXN txn
			where locate(inv.INV_JOB_ID, txn.PTX_SVC_REF) > 0 
				and txn.PTX_PAYMENT_STATE = 'NEW'
            )
order by INV_DT_CREATE asc;

-- -----
update clickargo2.T_CK_CT_PLATFORM_INVOICE inv
set INV_TOTAL = INV_VAT + INV_AMT
where INV_TOTAL != INV_VAT + INV_AMT 
	and not exists(
		SELECT * FROM clickargo2.T_CK_PAYMENT_TXN txn
			where locate(inv.INV_JOB_ID, txn.PTX_SVC_REF)) > 0
order by INV_DT_CREATE asc;
--
UPDATE `clickargo2`.`T_CK_PAYMENT_TXN` SET `PTX_AMOUNT` = '44516059.00' WHERE (`PTX_ID` = 'TXNDO4003228990500729');
UPDATE `clickargo2`.`T_CK_PAYMENT_TXN` SET `PTX_AMOUNT` = '241536350.00' WHERE (`PTX_ID` = 'TXNDO4003119251249093');
UPDATE `clickargo2`.`T_CK_PAYMENT_TXN` SET `PTX_AMOUNT` = '232161444.00' WHERE (`PTX_ID` = 'TXNDO4002045461074843');
UPDATE `clickargo2`.`T_CK_PAYMENT_TXN` SET `PTX_AMOUNT` = '242053637.00' WHERE (`PTX_ID` = 'TXNDO4001756053380141');
UPDATE `clickargo2`.`T_CK_PAYMENT_TXN` SET `PTX_AMOUNT` = '245377921.00' WHERE (`PTX_ID` = 'TXNDO4001609647016189');
UPDATE `clickargo2`.`T_CK_PAYMENT_TXN` SET `PTX_AMOUNT` = '5940929.00' WHERE (`PTX_ID` = 'TXNDO4001258024798034');
--

update T_CK_CT_PLATFORM_INVOICE i, zBackup.BU_CK_CT_PLATFORM_INVOICE_0810 i8
set i.INV_REFRESH = 1
where i.INV_ID = i8.INV_ID
	and i.INV_TOTAL != i8.INV_TOTAL;

-- ---
SELECT PTX_ID, PTX_SVC_REF, PTX_AMOUNT, (sum(DN_TOTAL) - sum(INV_TOTAL) ) as computeRst,
	PTX_AMOUNT - (sum(DN_TOTAL) - sum(INV_TOTAL) ) as rst
, sum(DN_TOTAL) sum_dn_amt, sum(INV_TOTAL) sum_inv_amt, PTX_DT_CREATE
FROM clickargo2.T_CK_PAYMENT_TXN pt
	, clickargo2.T_CK_CT_PLATFORM_INVOICE inv
    , clickargo2.T_CK_CT_DEBIT_NOTE dn
where inv.INV_JOB_ID = dn.DN_JOB_ID
	and locate(inv.INV_JOB_ID, pt.PTX_SVC_REF) > 0
    and pt.PTX_PAYEE = inv.INV_TO
    and PTX_TYPE = 'BNKTF'
    and dn.DN_TO = 'GLI'
    and PTX_DT_CREATE > '2023-08-01'
group by PTX_ID, PTX_SVC_REF
having PTX_AMOUNT != computeRst
order by PTX_DT_CREATE desc;

-- ----------
select INV_AMT, INV_VAT, INV_TOTAL, '--', INV_AMT + INV_VAT,inv.* 
from clickargo2.T_CK_CT_PLATFORM_INVOICE inv
where INV_TOTAL != INV_VAT + INV_AMT 
	and exists(
		SELECT * FROM clickargo2.T_CK_PAYMENT_TXN txn
			where locate(inv.INV_JOB_ID, txn.PTX_SVC_REF) > 0 
				and txn.PTX_PAYMENT_STATE = 'NEW'
            )
order by INV_DT_CREATE asc;

select INV_AMT, INV_VAT, INV_TOTAL, '--', INV_AMT + INV_VAT, (INV_TOTAL -( INV_VAT + INV_AMT)) a, inv.* 
from clickargo2.T_CK_CT_PLATFORM_INVOICE inv
where INV_TOTAL != INV_VAT + INV_AMT 
order by INV_DT_CREATE asc;

-- -----------
SELECT PTX_ID, PTX_SVC_REF, PTX_AMOUNT, (sum(DN_TOTAL) - sum(INV_TOTAL) ) as computeRst,
	PTX_AMOUNT - (sum(DN_TOTAL) - sum(INV_TOTAL) ) as rst
, sum(DN_TOTAL) sum_dn_amt, sum(INV_TOTAL) sum_inv_amt, PTX_DT_CREATE
FROM clickargo2.T_CK_PAYMENT_TXN pt
	, clickargo2.T_CK_CT_PLATFORM_INVOICE inv
    , clickargo2.T_CK_CT_DEBIT_NOTE dn
where inv.INV_JOB_ID = dn.DN_JOB_ID
	and locate(inv.INV_JOB_ID, pt.PTX_SVC_REF) > 0
    and pt.PTX_PAYEE = inv.INV_TO
    and PTX_TYPE = 'BNKTF'
    and dn.DN_TO = 'GLI'
    and PTX_DT_CREATE > '2023-08-01'
group by PTX_ID, PTX_SVC_REF
having PTX_AMOUNT != computeRst
order by PTX_DT_CREATE desc;