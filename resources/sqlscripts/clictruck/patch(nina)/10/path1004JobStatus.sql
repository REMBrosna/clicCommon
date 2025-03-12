-- These jobs where having a race issue, two users acccessing at the same time, one billed and approved while the other rejected causing it to be 
-- submitted again for billing. On Sep 1, the doc verification flag was still turned off.

UPDATE `clickargo2`.`T_CK_JOB` SET `JOB_STATE` = 'APP_BILL' WHERE (`JOB_ID` = 'CKJOB2023090122177');
UPDATE `clickargo2`.`T_CK_JOB` SET `JOB_STATE` = 'APP_BILL' WHERE (`JOB_ID` = 'CKJOB2023090124166');
UPDATE `clickargo2`.`T_CK_JOB` SET `JOB_STATE` = 'APP_BILL' WHERE (`JOB_ID` = 'CKJOB2023090159819');

UPDATE `clickargo2`.`T_CK_RECORD_DATE` SET `RCD_DT_BILL_APPROVED` = '2023-10-04 18:22:50', `RCD_UID_BILL_APPROVED` = 'SYS' WHERE (`RCD_ID` = '2023090138142');
UPDATE `clickargo2`.`T_CK_RECORD_DATE` SET `RCD_DT_BILL_APPROVED` = '2023-10-04 18:22:50', `RCD_UID_BILL_APPROVED` = 'SYS' WHERE (`RCD_ID` = '2023090149690');
UPDATE `clickargo2`.`T_CK_RECORD_DATE` SET `RCD_DT_BILL_APPROVED` = '2023-10-04 18:22:50' WHERE (`RCD_ID` = '2023090188129');
