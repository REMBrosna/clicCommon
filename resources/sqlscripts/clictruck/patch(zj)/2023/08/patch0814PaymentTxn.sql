

UPDATE `clickargo2`.`T_CK_PAYMENT_TXN` SET `PTX_AMOUNT` = '87099670.00' WHERE (`PTX_ID` = 'TXNDO4001391009717504');

-- UPDATE `clickargo2`.`T_CK_CT_TO_PAYMENT` SET `TOP_AMT` = '87099670' WHERE (`TOP_ID` = 'TOP2023081196625');

UPDATE `clickargo2`.`T_CK_CT_TO_PAYMENT` SET `TOP_AMT` = '87099670', `TOP_JSON` = '{\"node\":\"DNO\",\"serviceID\":\"CLICTRUCK\",\"refID\":\"TXNDO4001391009717504\",\"callBack\":null,\"senderAccount\":\"006600687575\",\"senderCardNumber\":null,\"va\":\"3890343405\",\"amount\":87099670,\"beneficiaryAccount\":\"3890343405\",\"bank\":\"BCA\",\"ccy\":\"IDR\"}'
 WHERE (`TOP_ID` = 'TOP2023081196625');
