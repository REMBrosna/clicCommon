
-- backup
create table zBackup.BU_CK_CT_PLATFORM_INVOICE_ITEM_0814 SELECT * FROM clickargo2.T_CK_CT_PLATFORM_INVOICE_ITEM;
create table zBackup.BU_CK_CT_PLATFORM_INVOICE_0814 SELECT * FROM clickargo2.T_CK_CT_PLATFORM_INVOICE;

create table zBackup.BU_T_CK_CT_DEBIT_NOTE_ITEM_0814 SELECT * FROM clickargo2.T_CK_CT_DEBIT_NOTE_ITEM;
create table zBackup.BU_T_CK_CT_DEBIT_NOTE_0814 SELECT * FROM clickargo2.T_CK_CT_DEBIT_NOTE;

-- update percent TO by contract

update clickargo2.T_CK_CT_PLATFORM_INVOICE inv join
	( SELECT CON_TO, CON_CO_FF, ccTo.*
		FROM clickargo2.T_CK_CT_CONTRACT c, clickargo2.T_CK_CT_CONTRACT_CHARGE ccTo
		, clickargo2.T_CK_CT_CONTRACT_CHARGE ccFF
		where c.CON_CHARGE_TO = ccTo.CONC_ID
			and c.CON_CHARGE_CO_FF = ccFF.CONC_ID ) as con join 
            clickargo2.T_CK_JOB_TRUCK as job
set INV_AMT = round((JOB_TOTAL_CHARGE + JOB_TOTAL_REIMBURSEMENTS ) * CONC_PLTFEE_AMT/100)
where inv.INV_JOB_ID in ('CKCTJ2023080718242','CKCTJ2023080734113','CKCTJ2023080739014')
	AND inv.INV_JOB_ID = job.JOB_ID
			and inv.INV_TO = job.JOB_PARTY_TO
			and job.JOB_PARTY_CO_FF = con.CON_CO_FF 
			and job.JOB_PARTY_TO = con.CON_TO 
	and CONC_PLTFEE_TYPE = 'P';
    
   -- 
-- update percent CO by contract
update clickargo2.T_CK_CT_PLATFORM_INVOICE inv join
	( SELECT CON_TO, CON_CO_FF, ccFF.*
		FROM clickargo2.T_CK_CT_CONTRACT c, clickargo2.T_CK_CT_CONTRACT_CHARGE ccTo
		, clickargo2.T_CK_CT_CONTRACT_CHARGE ccFF
		where c.CON_CHARGE_TO = ccTo.CONC_ID
			and c.CON_CHARGE_CO_FF = ccFF.CONC_ID ) as con join 
            clickargo2.T_CK_JOB_TRUCK as job
set INV_AMT = con.CONC_PLTFEE_AMT
where inv.INV_JOB_ID in ('CKCTJ2023080718242','CKCTJ2023080734113','CKCTJ2023080739014')
	AND inv.INV_JOB_ID = job.JOB_ID
	and inv.INV_TO = job.JOB_PARTY_CO_FF
	and job.JOB_PARTY_CO_FF = con.CON_CO_FF 
	and job.JOB_PARTY_TO = con.CON_TO 
	and CONC_PLTFEE_TYPE = 'F';

-- update percent CO by contract
update clickargo2.T_CK_CT_PLATFORM_INVOICE inv join
	( SELECT CON_TO, CON_CO_FF, ccFF.*
		FROM clickargo2.T_CK_CT_CONTRACT c, clickargo2.T_CK_CT_CONTRACT_CHARGE ccTo
		, clickargo2.T_CK_CT_CONTRACT_CHARGE ccFF
		where c.CON_CHARGE_TO = ccTo.CONC_ID
			and c.CON_CHARGE_CO_FF = ccFF.CONC_ID ) as con join 
            clickargo2.T_CK_JOB_TRUCK as job
set INV_AMT = round((JOB_TOTAL_CHARGE + JOB_TOTAL_REIMBURSEMENTS ) * CONC_PLTFEE_AMT/100)
where inv.INV_JOB_ID in ('CKCTJ2023080705469','CKCTJ2023080709786','CKCTJ2023080721470','CKCTJ2023080731572','CKCTJ2023080772021','CKCTJ2023080777584','CKCTJ2023080786864')
	AND inv.INV_JOB_ID = job.JOB_ID
	and inv.INV_TO = job.JOB_PARTY_CO_FF
	and job.JOB_PARTY_CO_FF = con.CON_CO_FF 
	and job.JOB_PARTY_TO = con.CON_TO 
	and CONC_PLTFEE_TYPE = 'P';
    
-- 
-- tax
update T_CK_CT_PLATFORM_INVOICE inv 
set INV_VAT = round(INV_AMT * 0.11)
where INV_VAT != round(INV_AMT * 0.11)
    and INV_DT_ISSUE < '2023-08-11';

-- total
update T_CK_CT_PLATFORM_INVOICE inv 
set INV_TOTAL = INV_AMT + INV_VAT
where ((INV_TOTAL - (INV_VAT + INV_AMT)) > 2
	OR (INV_TOTAL - (INV_VAT + INV_AMT)) < -2) ;

-- INV_REFRESH
update T_CK_CT_PLATFORM_INVOICE inv
set inv.INV_REFRESH = 1
where INV_VAT != round(INV_AMT * 0.11)
    and INV_DT_ISSUE < '2023-08-11';
    
UPDATE T_CK_CT_PLATFORM_INVOICE_ITEM ii JOIN
	(SELECT * FROM T_CK_CT_PLATFORM_INVOICE ) i
set ii.ITM_AMOUNT = i.INV_AMT
where i.INV_ID = ii.ITM_INVOICE
	and i.INV_AMT != ii.ITM_AMOUNT;
    
update T_CK_CT_PLATFORM_INVOICE_ITEM
set ITM_UNIT_PRICE = ITM_AMOUNT
where ITM_UNIT_PRICE != ITM_AMOUNT;

UPDATE `clickargo2`.`T_CK_PAYMENT_TXN` SET `PTX_AMOUNT` = '49586250.00' WHERE (`PTX_ID` = 'TXNDO4335536046116239');



select bak.INV_ID, bak.INV_AMT, inv.INV_AMT, bak.INV_VAT, inv.INV_VAT, inv.INV_DT_ISSUE
from zBackup.BU_CK_CT_PLATFORM_INVOICE_0814 bak,  clickargo2.T_CK_CT_PLATFORM_INVOICE inv
where bak.INV_ID = inv.INV_ID
	and ( bak.INV_AMT != inv.INV_AMT or bak.INV_VAT != inv.INV_VAT);
    
    