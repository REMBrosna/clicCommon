

UPDATE `clickargo2`.`T_CK_PAYMENT_TXN` SET `PTX_PAYMENT_STATE` = 'PAYING' 
WHERE `PTX_ID` in ('','TXNDO5311713247455571');

set sql_safe_updates = 0;

UPDATE `clickargo2`.`T_CK_CT_TO_PAYMENT` SET `TOP_STATUS` = 'N' 
	WHERE TOP_STATUS = 'F' 
	and TOP_REFERENCE in ('', 'TXNDO5311713247455571');

-- -------
UPDATE `clickargo2`.`T_CORE_SYSPARAM` SET `SYS_VAL` = '{\"authUrl\":\"
https://account.mekari.com\",\"url\":\"https://api.mekari.com/v2/esign/v1\",\"clientId\":\"8dK7k5bxYO5tPwbH\",\"clientSecret\":\"L0UfDKSj5Io5oTtSrQQs2SxwtcdDUGiv\",\"contentType\":\"application/json\",\"code\":\"DGPnyP8W8nTRERCp1RT0ZQCV8bY6QErh\"}'
WHERE (`SYS_KEY` = 'STAMP_GATEWAY_CONFIG');