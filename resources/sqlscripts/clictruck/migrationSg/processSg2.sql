
-- T_CK_CT_DEPT
ALTER TABLE `ctMigration`.`TMP_DEPT`  ADD COLUMN `DEPT_ID` VARCHAR(45) NULL FIRST;

UPDATE TMP_DEPT AS c JOIN
 ( SELECT DEPT_UUID, row_number() OVER (ORDER BY DEPT_UUID) AS rn 
      FROM TMP_DEPT
    ) AS sub
  ON  c.DEPT_UUID = sub.DEPT_UUID
SET c.DEPT_ID = concat('DEPT', lpad(sub.rn,5,'0') );

-- T_CK_CT_DEPT_USR
ALTER TABLE `ctMigration`.`TMP_DEPT_USR`  ADD COLUMN `DU_ID` VARCHAR(45) NULL FIRST;
ALTER TABLE `ctMigration`.`TMP_DEPT_USR`  ADD COLUMN `DEPT_ID` VARCHAR(45) NULL AFTER DU_ID;
ALTER TABLE `ctMigration`.`TMP_DEPT_USR`  ADD COLUMN `USR_ID` VARCHAR(45) NULL AFTER DEPT_ID;

update `ctMigration`.`TMP_DEPT_USR` AS du join TMP_DEPT as dept on du.DEPT_UUID = dept.DEPT_UUID
set du.DEPT_ID = dept.DEPT_ID;

update `ctMigration`.`TMP_DEPT_USR` AS du join TMP_USER as usr on du.USR_UUID = usr.UID
set du.USR_ID = usr.USER_ID;

set @duId = 0;
update `ctMigration`.`TMP_DEPT_USR` set DU_ID = concat('DU', lpad((@duId :=@duId + 1) ,5,'0') )  order by USR_UUID asc;

-- T_CK_CT_DEPT_VEH
ALTER TABLE `ctMigration`.`TMP_TRUCK`  ADD COLUMN `DV_ID` VARCHAR(45) NULL FIRST;
ALTER TABLE `ctMigration`.`TMP_TRUCK`  ADD COLUMN `DEPT_ID` VARCHAR(45) NULL AFTER DV_ID;

update `ctMigration`.`TMP_TRUCK` AS tt join TMP_DEPT as dept on tt.DEP_UUID = dept.DEPT_UUID
set tt.DEPT_ID = dept.DEPT_ID;

set @duId := 0;
update `ctMigration`.`TMP_TRUCK` set DV_ID = concat('DV', lpad((@duId :=@duId + 1) ,5,'0') ) where DEPT_ID is not null order by TRUCK_ID asc;

-- T_CK_CT_COX2
ALTER TABLE `ctMigration`.`TMP_COMPANY`  ADD COLUMN `CO2X_ID` VARCHAR(45) NULL FIRST;

set @duId = 0;
update `ctMigration`.`TMP_COMPANY` set CO2X_ID = concat('CO2X', lpad((@duId :=@duId + 1) ,5,'0') ) 
where ASCENT_EMAIL is not null and ACCN_ID is not null
order by ACCN_ID asc;

-- T_CK_CT_RENTAL_APP

insert into clickargo2.T_CK_CT_RENTAL_APP(VR_ID, VR_ACCN, VR_PROVIDER, VR_TRUCK, VR_LEASE, VR_PRICE, VR_QTY, VR_CT_NAME, VR_CT_MOBILE, VR_CT_EMAIL, VR_STATUS, VR_DT_CREATE, VR_UID_CREATE)
values('REN00001', 'BTL', 'YINSON','KING LONG 6500 (2022)', 36, 50, 2, 'BETA LOGISTICS','','beta.logistics@yopmail.com','S', sysdate(), 'SYS');

insert IGNORE into clickargo2.T_CK_CT_DEPT(DEPT_ID, DEPT_ACCN, DEPT_NAME, DEPT_DESC, DEPT_COLOR, DEPT_STATUS, DEPT_DT_CREATE, DEPT_UID_CREATE )
SELECT DEPT_ID, ACCN_ID,  DEPT_NAME, DEPT_DESC, '1', 'A', CREATE_AT, 'sys' FROM ctMigration.TMP_DEPT 
	WHERE ACCN_ID IN (SELECT ACCN_ID FROM clickargo2.T_CORE_ACCN);
    
insert IGNORE into clickargo2.T_CK_CT_DEPT_USR(DU_ID, DU_DEPT, DU_USR, DU_STATUS, DU_DT_CREATE, DU_UID_CREATE)
SELECT DU_ID, DEPT_ID, USR_ID, 'A', sysdate(), 'sys' FROM ctMigration.TMP_DEPT_USR
	where USR_ID IN (SELECT USR_ID FROM clickargo2.T_CORE_USR);

insert IGNORE into clickargo2.T_CK_CT_DEPT_VEH(DV_ID, DV_DEPT, DV_VEH, DV_STATUS, DV_DT_CREATE, DV_UID_CREATE)
SELECT DV_ID, DEPT_ID, TRUCK_ID, 'A', sysdate(), 'sys' FROM ctMigration.TMP_TRUCK 
where TRUCK_ID in (select VH_ID from clickargo2.T_CK_CT_VEH)
	AND DEPT_ID IN (SELECT DEPT_ID FROM clickargo2.T_CK_CT_DEPT)
	and DV_ID IS NOT NULL;
    
insert into clickargo2.T_CK_CT_CO2X(CO2X_ID, CO2X_ACCN, CO2X_ACCN_NAME, CO2X_COY_ID, CO2X_DT_EXPIRY, CO2X_UID, CO2X_PWD, CO2X_STATUS, CO2X_DT_CREATE, CO2X_UID_CREATE)
SELECT CO2X_ID,   ACCN_ID, `NAME`, ASCENT_COMPANY_ID, DATE_ADD(sysdate(), INTERVAL 3 YEAR) , ASCENT_EMAIL,  ASCENT_PASSWORD, 'A', sysdate(), 'sys'
FROM ctMigration.TMP_COMPANY 
	where ACCN_ID IS NOT NULL
	AND ASCENT_EMAIL IS NOT NULL
    and CO2X_ID is not null;


-- co NON_FINANCE NON_FINANCE
INSERT ignore INTO `clickargo2`.`T_CORE_ACCN_CONFIG` (`ACFG_ACCNID`, `ACFG_KEY`, `ACFG_VAL`, `ACFG_DESC`, `ACFG_VALID_FROM_DT`, `ACFG_VALID_TO_DT`, `ACFG_SEQ`, `ACFG_STATUS`, `ACFG_DT_CREATE`, `ACFG_UID_CREATE`) 
select ACCN_ID, 'FINANCE_OPTIONS', 'NON_FINANCE', 'FINANCE_OPTIONS', sysdate(), sysdate(), '1', 'A', sysdate(), 'GLI_U001'
 FROM clickargo2.T_CORE_ACCN where ACCN_TYPE != 'ACC_TYPE_TO';

-- TO MOBILE_ENABLED Y
-- ---
INSERT ignore INTO `clickargo2`.`T_CORE_ACCN_CONFIG` (`ACFG_ACCNID`, `ACFG_KEY`, `ACFG_VAL`, `ACFG_DESC`, `ACFG_VALID_FROM_DT`, `ACFG_VALID_TO_DT`, `ACFG_SEQ`, `ACFG_STATUS`, `ACFG_DT_CREATE`, `ACFG_UID_CREATE`) 
select ACCN_ID, 'MOBILE_ENABLED', 'Y', 'MOBILE_ENABLED', sysdate(), sysdate(), '1', 'A', sysdate(), 'GLI_U001'
 FROM clickargo2.T_CORE_ACCN where ACCN_TYPE = 'ACC_TYPE_TO';


INSERT INTO `clickargo2`.`T_CK_ACCN` (`CACCN_ID`, `CACCN_FINANCING_TYPE`, `CACCN_FINANCER`, `CACCN_EXCEL_TEMPLATE`, `CACCN_EPOD_JRXML`, `CACCN_STATUS`, `CACCN_DT_CREATE`, `CACCN_UID_CREATE`, `CACCN_DT_LUPD`, `CACCN_UID_LUPD`) VALUES ('CQS', 'BC', 'GLI', '[\n {\n  \"field\": \"contract_id\",\n  \"label\": \"contract_id\",\n  \"default\": \"Clasquin\"\n },\n {\n  \"field\": \"country\",\n  \"label\": \"country\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"start_location\",\n  \"label\": \"start_location\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"end_location\",\n  \"label\": \"end_location\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"date_of_delivery\",\n  \"label\": \"date_of_delivery\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"time_of_delivery\",\n  \"label\": \"time_of_delivery\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"plan_date\",\n  \"label\": \"plan_date\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"booking_date\",\n  \"label\": \"booking_date\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"loading\",\n  \"label\": \"loading\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"email_notification\",\n  \"label\": \"email_notification\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"description\",\n  \"label\": \"description\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"payment_method\",\n  \"label\": \"payment_method\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"cargo_type\",\n  \"label\": \"cargo_type\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"cargo_weight\",\n  \"label\": \"cargo_weight\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"cargo_truck_type\",\n  \"label\": \"cargo_truck_type\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"job_linking_number\",\n  \"label\": \"Clasquin_HBOD_#\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"driver_username\",\n  \"label\": \"driver_username\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"truck_license_plate_number\",\n  \"label\": \"truck_license_plate_number\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"dropoff_remark\",\n  \"label\": \"dropoff_remark\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"shipment_ref_no\",\n  \"label\": \"customer_invoice_number\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"job_linking_number\",\n  \"label\": \"Clasquin_HBOD_#\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"customer_name\",\n  \"label\": \"Customer_name\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"house_bill\",\n  \"label\": \"house_bill\",\n  \"default\": \"\"\n }\n]', 'docs/Epod_Clasquin.jrxml', 'A', '2024-04-04 12:59:24', 'GLI_U001', '2024-04-04 12:59:24', 'GLI_U001');
INSERT INTO `clickargo2`.`T_CK_ACCN` (`CACCN_ID`, `CACCN_FINANCING_TYPE`, `CACCN_FINANCER`, `CACCN_EXCEL_TEMPLATE`, `CACCN_EPOD_JRXML`, `CACCN_STATUS`, `CACCN_DT_CREATE`, `CACCN_UID_CREATE`, `CACCN_DT_LUPD`, `CACCN_UID_LUPD`) VALUES ('CSP', 'BC', 'GLI', '[\n {\n  \"field\": \"contract_id\",\n  \"label\": \"contract_id\",\n  \"default\": \"Clasquin\"\n },\n {\n  \"field\": \"country\",\n  \"label\": \"country\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"start_location\",\n  \"label\": \"start_location\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"end_location\",\n  \"label\": \"end_location\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"date_of_delivery\",\n  \"label\": \"date_of_delivery\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"time_of_delivery\",\n  \"label\": \"time_of_delivery\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"plan_date\",\n  \"label\": \"plan_date\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"booking_date\",\n  \"label\": \"booking_date\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"loading\",\n  \"label\": \"loading\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"email_notification\",\n  \"label\": \"email_notification\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"description\",\n  \"label\": \"description\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"payment_method\",\n  \"label\": \"payment_method\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"cargo_type\",\n  \"label\": \"cargo_type\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"cargo_weight\",\n  \"label\": \"cargo_weight\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"cargo_truck_type\",\n  \"label\": \"cargo_truck_type\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"job_linking_number\",\n  \"label\": \"Clasquin_HBOD_#\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"driver_username\",\n  \"label\": \"driver_username\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"truck_license_plate_number\",\n  \"label\": \"truck_license_plate_number\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"dropoff_remark\",\n  \"label\": \"dropoff_remark\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"shipment_ref_no\",\n  \"label\": \"customer_invoice_number\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"job_linking_number\",\n  \"label\": \"Clasquin_HBOD_#\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"customer_name\",\n  \"label\": \"Customer_name\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"house_bill\",\n  \"label\": \"house_bill\",\n  \"default\": \"\"\n }\n]', 'docs/Epod_Clasquin.jrxml', 'A', '2024-04-04 12:59:24', 'GLI_U001', '2024-04-04 12:59:24', 'GLI_U001');

INSERT INTO `clickargo2`.`T_CK_ACCN` (`CACCN_ID`, `CACCN_FINANCING_TYPE`, `CACCN_FINANCER`, `CACCN_EXCEL_TEMPLATE`, `CACCN_STATUS`, `CACCN_DT_CREATE`, `CACCN_UID_CREATE`, `CACCN_DT_LUPD`, `CACCN_UID_LUPD`) VALUES ('DSVS', 'BC', 'GLI', '[\n {\n  \"field\": \"contract_id\",\n  \"label\": \"contract_id\",\n  \"default\": \"DSVS-WEITAO-BSH\"\n },\n {\n  \"field\": \"country\",\n  \"label\": \"country\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"start_location\",\n  \"label\": \"start_location\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"end_location\",\n  \"label\": \"end_location\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"date_of_delivery\",\n  \"label\": \"date_of_delivery\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"time_of_delivery\",\n  \"label\": \"time_of_delivery\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"plan_date\",\n  \"label\": \"plan_date\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"booking_date\",\n  \"label\": \"booking_date\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"loading\",\n  \"label\": \"loading\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"email_notification\",\n  \"label\": \"email_notification\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"description\",\n  \"label\": \"description\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"payment_method\",\n  \"label\": \"payment_method\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"cargo_type\",\n  \"label\": \"cargo_type\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"cargo_weight\",\n  \"label\": \"cargo_weight\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"cargo_truck_type\",\n  \"label\": \"cargo_truck_type\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"job_linking_number\",\n  \"label\": \"job_number\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"driver_username\",\n  \"label\": \"driver_username\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"truck_license_plate_number\",\n  \"label\": \"truck_license_plate_number\",\n  \"default\": \"\"\n },\n {\n  \"field\": \"dropoff_remark\",\n  \"label\": \"dropoff_remark\",\n  \"default\": \"\"\n }\n]', 'A', '2024-04-04 12:59:24', 'GLI_U001', '2024-04-04 12:59:24', 'GLI_U001');

insert ignore into clickargo2.T_CK_ACCN( CACCN_ID, CACCN_FINANCING_TYPE, CACCN_FINANCER, CACCN_STATUS, CACCN_DT_CREATE, CACCN_UID_CREATE) 
select ACCN_ID, 'BC', 'GLI', 'A', sysdate(), 'GLI_U001'
 FROM clickargo2.T_CORE_ACCN where ACCN_TYPE = 'ACC_TYPE_TO';

update clickargo2.T_CK_ACCN ca
	 join clickargo2.T_CORE_ACCN accn ON ca.CACCN_ID = accn.ACCN_ID
	join ctMigration.TMP_COMPANY_SG cs on ca.CACCN_ID = cs.ACCN_ID
	set CACCN_ISPOT_SUB_ACCN = cs.UUID
where accn.ACCN_TYPE = 'ACC_TYPE_TO';



INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`, `ACCN_ADDR1`, `ACCN_EMAIL`, `ACCN_DT_REG`) 
VALUES ('MLS', 'A', 'MILLESIMA', 'MILLESIMA', 'ACC_TYPE_FF_CO', '1995013501', 'NO 19 TAI SENG AVENUE #05-06, SINGAPORE, 534054', 'MIKEHADLEY9@GMAIL.COM', sysdate());

INSERT INTO `clickargo2`.`T_CK_CT_FF_CO` (`FFCO_ID`, `FFCO_FF`, `FFCO_CO`, `FFCO_STATUS`, `FFCO_DT_CREATE`) 
VALUES ('FFCO000007', 'CSP', 'MLS', 'A', '2024-09-11 03:24:46');


UPDATE clickargo2.T_CORE_USR SET USR_PWD = MD5(CONCAT(USR_UID, 'password')) WHERE USR_ACCNID != 'GLI';
