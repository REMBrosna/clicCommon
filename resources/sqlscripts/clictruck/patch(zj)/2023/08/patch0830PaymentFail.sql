
-- update bank information
UPDATE `clickargo2`.`T_CK_CT_TO_PAYMENT` SET `TOP_JSON` = '{\"node\":\"DNO\",\"serviceID\":\"CLICTRUCK\",\"refID\":\"TXNDO5449943860656023\",\"callBack\":null,\"senderAccount\":\"006600687575\",\"senderCardNumber\":null,\"va\":\"1200033993390\",\"amount\":63944500.00,\"beneficiaryAccount\":\"1200033993390\",\"bank\":\"MANDIRI\",\"ccy\":\"IDR\"}' 
WHERE (`TOP_ID` = 'TOP2023082978164') and TOP_STATUS = 'F';

UPDATE `clickargo2`.`T_CK_CT_TO_PAYMENT` SET `TOP_STATUS` = 'N' 
WHERE (`TOP_ID` = 'TOP2023082978164') and TOP_STATUS = 'F';

-- update bank information
update clickargo2.T_CK_PAYMENT_TXN tcpt
set PTX_MERCHANT_BANK='MANDIRI'
where PTX_ID ='TXNDO5449943860656023' and PTX_PAYMENT_STATE = 'FAILED';

-- updated to APP_BILL is correct, NEW is not correct.
UPDATE `clickargo2`.`T_CK_PAYMENT_TXN` SET `PTX_PAYMENT_STATE` = 'APP_BILL' 
WHERE (`PTX_ID` = 'TXNDO5449943860656023') and PTX_PAYMENT_STATE = 'FAILED';
