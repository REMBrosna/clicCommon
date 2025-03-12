-- update Bank account 
update clickargo2.T_CORE_ACCN_CONFIG
set ACFG_VAL=UCASE('MANDIRI:1560021041746')
where ACFG_ACCNID='CK0686'
and ACFG_KEY='BANK_DETAIL';

update clickargo2.T_CORE_ACCN_CONFIG
set ACFG_VAL=UCASE('MANDIRI:1200033993390')
where ACFG_ACCNID='CK0305'
and ACFG_KEY='BANK_DETAIL';
--


-- update bank information
UPDATE `clickargo2`.`T_CK_CT_TO_PAYMENT` SET `TOP_JSON` = '{\"node\":\"DNO\",\"serviceID\":\"CLICTRUCK\",\"refID\":\"TXNDO5531318316033622\",\"callBack\":null,\"senderAccount\":\"006600687575\",\"senderCardNumber\":null,\"va\":\"156002141746\",\"amount\":128916750.00,\"beneficiaryAccount\":\"1560021041746\",\"bank\":\"MANDIRI\",\"ccy\":\"IDR\"}' 
WHERE (`TOP_ID` = 'TOP2023083000268') and TOP_STATUS = 'F';

UPDATE `clickargo2`.`T_CK_CT_TO_PAYMENT` SET `TOP_JSON` = '{\"node\":\"DNO\",\"serviceID\":\"CLICTRUCK\",\"refID\":\"TXNDO5807890263315949\",\"callBack\":null,\"senderAccount\":\"006600687575\",\"senderCardNumber\":null,\"va\":\"1200033993390\",\"amount\":100316750.00,\"beneficiaryAccount\":\"1200033993390\",\"bank\":\"MANDIRI\",\"ccy\":\"IDR\"}' 
WHERE (`TOP_ID` = 'TOP2023083128797') and TOP_STATUS = 'F';

UPDATE `clickargo2`.`T_CK_CT_TO_PAYMENT` SET `TOP_STATUS` = 'N' 
WHERE (`TOP_ID` = 'TOP2023083000268') and TOP_STATUS = 'F';
UPDATE `clickargo2`.`T_CK_CT_TO_PAYMENT` SET `TOP_STATUS` = 'N' 
WHERE (`TOP_ID` = 'TOP2023083128797') and TOP_STATUS = 'F';

-- update bank information
update clickargo2.T_CK_PAYMENT_TXN tcpt
set PTX_MERCHANT_BANK='MANDIRI', PTX_PAYEE_BANK_ACCN = '1200033993390', `PTX_PAYMENT_STATE` = 'APP_BILL' 
where PTX_ID ='TXNDO5807890263315949' and PTX_PAYMENT_STATE = 'FAILED' 
	and PTX_PAYEE = 'CK0305' and PTX_TYPE='BNKTF';

-- update bank information
update clickargo2.T_CK_PAYMENT_TXN tcpt
set PTX_MERCHANT_BANK='MANDIRI', PTX_PAYEE_BANK_ACCN = '1560021041746', `PTX_PAYMENT_STATE` = 'APP_BILL' 
where PTX_ID ='TXNDO5531318316033622' and PTX_PAYMENT_STATE = 'FAILED' 
	and PTX_PAYEE = 'CK0686' and PTX_TYPE='BNKTF';


