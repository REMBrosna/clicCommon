
set @backupDate = '0906';

set @invItem = concat('create table zBackup.T_CK_CT_PLATFORM_INVOICE_ITEM_', @backupDate, ' SELECT * FROM clickargo2.T_CK_CT_PLATFORM_INVOICE_ITEM');
set @inv = concat('create table zBackup.T_CK_CT_PLATFORM_INVOICE_', @backupDate, ' SELECT * FROM clickargo2.T_CK_CT_PLATFORM_INVOICE');

set @dnItem = concat('create table zBackup.T_T_CK_CT_DEBIT_NOTE_ITEM_', @backupDate, ' SELECT * FROM clickargo2.T_CK_CT_DEBIT_NOTE_ITEM');
set @dn = concat('create table zBackup.T_T_CK_CT_DEBIT_NOTE_', @backupDate, ' SELECT * FROM clickargo2.T_CK_CT_DEBIT_NOTE');

set @payTxn = concat('create table zBackup.T_CK_PAYMENT_TXN_', @backupDate, ' as select * from clickargo2.T_CK_PAYMENT_TXN');
set @pay = concat('create table zBackup.T_CK_CT_TO_PAYMENT_', @backupDate, ' as select * from clickargo2.T_CK_CT_TO_PAYMENT');

PREPARE stmt FROM @invItem; EXECUTE stmt; DEALLOCATE PREPARE stmt; 
PREPARE stmt FROM @inv; EXECUTE stmt; DEALLOCATE PREPARE stmt; 

PREPARE stmt FROM @dnItem; EXECUTE stmt; DEALLOCATE PREPARE stmt; 
PREPARE stmt FROM @dn; EXECUTE stmt; DEALLOCATE PREPARE stmt; 

PREPARE stmt FROM @payTxn; EXECUTE stmt; DEALLOCATE PREPARE stmt; 
PREPARE stmt FROM @pay; EXECUTE stmt; DEALLOCATE PREPARE stmt; 