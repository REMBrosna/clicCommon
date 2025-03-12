use ctMigration;

ALTER TABLE `ctMigration`.`TMP_RATE` ADD COLUMN `CO_ACCN_ID` VARCHAR(45) NULL FIRST;
ALTER TABLE `ctMigration`.`TMP_RATE` ADD COLUMN `TO_ACCN_ID` VARCHAR(100) AFTER `CO_ACCN_ID`;
ALTER TABLE `ctMigration`.`TMP_RATE` ADD COLUMN `FROM_LOC_ID` VARCHAR(70)  AFTER `TO_ACCN_ID`;
ALTER TABLE `ctMigration`.`TMP_RATE` ADD COLUMN `END_LOC_ID` VARCHAR(70)  AFTER `FROM_LOC_ID`;
ALTER TABLE `ctMigration`.`TMP_RATE` ADD COLUMN `RATE_ID` VARCHAR(100) AFTER `END_LOC_ID`;
ALTER TABLE `ctMigration`.`TMP_RATE` ADD COLUMN `TRAP_RATE_ID` VARCHAR(100) AFTER `RATE_ID`;
ALTER TABLE `ctMigration`.`TMP_RATE` ADD COLUMN `VHTY_ID` VARCHAR(100) AFTER `TRAP_RATE_ID`;

update ctMigration.TMP_RATE set CO_ACCN_ID = '';
update ctMigration.TMP_RATE set TO_ACCN_ID = '';
update ctMigration.TMP_RATE set FROM_LOC_ID = '';
update ctMigration.TMP_RATE set END_LOC_ID = '';
update ctMigration.TMP_RATE set RATE_ID = '';
update ctMigration.TMP_RATE set TRAP_RATE_ID = '';
update ctMigration.TMP_RATE set VHTY_ID = '';


UPDATE TMP_RATE AS c JOIN
 ( SELECT ACCN_ID, ID, NAME FROM ctMigration.TMP_COMPANY
    ) AS sub
  ON  c.CO_FF = sub.NAME
SET c.CO_ACCN_ID = sub.ACCN_ID;

UPDATE TMP_RATE AS c JOIN
 ( SELECT ACCN_ID, ID, NAME FROM ctMigration.TMP_COMPANY
    ) AS sub
  ON  c.TO_COMPANY = sub.NAME
SET c.TO_ACCN_ID = sub.ACCN_ID;
-- --
DROP TABLE IF EXISTS TMP_RATE_LOC;

create table TMP_RATE_LOC AS 
SELECT TO_ACCN_ID, From_Loc FROM TMP_RATE where TO_ACCN_ID in (select ACCN_ID from clickargo2.T_CORE_ACCN) 
union 
SELECT TO_ACCN_ID, To_Loc FROM TMP_RATE where TO_ACCN_ID in (select ACCN_ID from clickargo2.T_CORE_ACCN) ;


ALTER TABLE `ctMigration`.`TMP_RATE_LOC` ADD COLUMN `LOC_ID` VARCHAR(70)  FIRST;
ALTER TABLE `ctMigration`.`TMP_RATE_LOC` ADD COLUMN `LOC_NAME` VARCHAR(70) after LOC_ID;
ALTER TABLE `ctMigration`.`TMP_RATE_LOC` ADD COLUMN `LOC_NAME_SEQ` VARCHAR(70) after LOC_NAME;

ALTER TABLE `ctMigration`.`TMP_RATE_LOC` CHANGE COLUMN `From_Loc` `From_Loc` VARCHAR(256) NULL DEFAULT NULL ;

ALTER TABLE `ctMigration`.`TMP_RATE_LOC` 
CHANGE COLUMN `LOC_ID` `LOC_ID` VARCHAR(70) NULL ,
ADD INDEX `IDX_RATE_LOC_1` (`LOC_ID` ASC) VISIBLE;


UPDATE TMP_RATE_LOC AS c JOIN
 ( SELECT TO_ACCN_ID, From_Loc, row_number() OVER (ORDER BY TO_ACCN_ID, From_Loc) AS rn 
      FROM TMP_RATE_LOC
    ) AS sub
  ON  c.TO_ACCN_ID = sub.TO_ACCN_ID AND c.From_Loc = sub.From_Loc 
SET c.LOC_ID = concat('CKCTLOCA', lpad(sub.rn,6,'0') );

update ctMigration.TMP_RATE_LOC set From_Loc = upper(From_Loc);

update ctMigration.TMP_RATE_LOC set LOC_NAME = replace(SUBSTRING_INDEX(From_Loc,' ',3),',','');


UPDATE TMP_RATE_LOC AS c JOIN
 ( SELECT LOC_ID, TO_ACCN_ID, LOC_NAME, row_number() OVER (PARTITION BY TO_ACCN_ID,LOC_NAME ORDER BY TO_ACCN_ID, LOC_NAME) AS rn 
      FROM TMP_RATE_LOC
    ) AS sub
  ON  c.LOC_ID = sub.LOC_ID 
SET c.LOC_NAME_SEQ = rn-1  ;

update ctMigration.TMP_RATE_LOC set LOC_NAME = concat(LOC_NAME, ' (', LOC_NAME_SEQ, ')') where LOC_NAME_SEQ > 0;

-- -- c.TO_ACCN_ID = sub.TO_ACCN_ID
UPDATE TMP_RATE AS c JOIN
 ( SELECT LOC_ID, TO_ACCN_ID, From_Loc FROM ctMigration.TMP_RATE_LOC
    ) AS sub
  ON  c.From_Loc = sub.From_Loc and c.TO_ACCN_ID = sub.TO_ACCN_ID
SET c.FROM_LOC_ID = sub.LOC_ID;

UPDATE TMP_RATE AS c JOIN
 ( SELECT LOC_ID, TO_ACCN_ID, From_Loc FROM ctMigration.TMP_RATE_LOC
    ) AS sub
  ON  c.To_Loc = sub.From_Loc and c.TO_ACCN_ID = sub.TO_ACCN_ID
SET c.END_LOC_ID = sub.LOC_ID;

-- RATE
UPDATE TMP_RATE AS c JOIN
 ( SELECT CO_ACCN_ID, TO_ACCN_ID, row_number() OVER (ORDER BY CO_ACCN_ID, TO_ACCN_ID) AS rn 
      FROM TMP_RATE
    ) AS sub
  ON  c.CO_ACCN_ID = sub.CO_ACCN_ID AND c.TO_ACCN_ID = sub.TO_ACCN_ID 
SET c.RATE_ID = concat('CKCTRT', lpad(sub.rn,6,'0') );


-- RATE
-- RATE_ID, FROM_LOC_ID, END_LOC_ID
UPDATE TMP_RATE AS c JOIN
 ( SELECT RATE_ID, FROM_LOC_ID, END_LOC_ID, Veh_Type, row_number() OVER (ORDER BY RATE_ID, FROM_LOC_ID, END_LOC_ID, Veh_Type) AS rn 
      FROM TMP_RATE
    ) AS sub
  ON  c.RATE_ID = sub.RATE_ID AND c.FROM_LOC_ID = sub.FROM_LOC_ID AND c.END_LOC_ID = sub.END_LOC_ID AND c.Veh_Type = sub.Veh_Type 
SET c.TRAP_RATE_ID = concat('JKSM', lpad(sub.rn,6,'0') );


update TMP_RATE set Veh_Type  = UCASE(Veh_Type) ;




-- LOC_ID, TO_ACCN_ID, From_Loc
-- vehicle type

update ctMigration.TMP_RATE set Veh_Type = 'CONTAINER 20FT' 
where Veh_Type is null;

INSERT ignore INTO clickargo2.T_CK_CT_MST_VEH_TYPE(VHTY_ID, VHTY_NAME, VHTY_DESC, VHTY_DESC_OTH, VHTY_STATUS, VHTY_DT_CREATE, VHTY_UID_CREATE)
SELECT DISTINCT Veh_Type, Veh_Type, Veh_Type, Veh_Type, 'A', SYSDATE(), 'SYS' FROM ctMigration.TMP_RATE
where Veh_Type NOT IN (SELECT VHTY_NAME FROM clickargo2.T_CK_CT_MST_VEH_TYPE);

UPDATE clickargo2.T_CK_CT_MST_VEH_TYPE SET VHTY_ID = REPLACE(VHTY_ID, ' ', ''); 

UPDATE TMP_RATE AS c JOIN
 ( SELECT VHTY_ID, VHTY_NAME FROM clickargo2.T_CK_CT_MST_VEH_TYPE
    ) AS sub
  ON  c.Veh_Type = sub.VHTY_NAME
SET c.VHTY_ID = sub.VHTY_ID;


-- location
insert ignore into clickargo2.T_CK_CT_LOCATION(LOC_ID, LOC_TYPE, LOC_COMPANY, LOC_NAME, LOC_ADDRESS, LOC_DT_START,LOC_DT_END, LOC_STATUS, LOC_DT_CREATE, LOC_UID_CREATE)
SELECT LOC_ID, 'ADDRESS', TO_ACCN_ID, LOC_NAME, From_Loc, sysdate(), '2040-12-31 00:00:00', 'A', sysdate(), 'sys' 
FROM ctMigration.TMP_RATE_LOC
where TO_ACCN_ID in (select ACCN_ID from clickargo2.T_CORE_ACCN);

-- rate table
insert ignore into clickargo2.T_CK_CT_RATE_TABLE(RT_ID, RT_COMPANY, RT_CO_FF, RT_NAME, RT_DESCRIPTION, RT_CCY
, RT_DT_START, RT_DT_END, RT_REMARKS, RT_STATUS, RT_DT_CREATE, RT_UID_CREATE, RT_DT_LUPD, RT_UID_LUPD)
select distinct RATE_ID,TO_ACCN_ID,CO_ACCN_ID, RATE_ID,'-'
	,'IDR', sysdate(), '2040-12-31','','A',sysdate(),'sys' ,sysdate(),'sys'   
FROM ctMigration.TMP_RATE
where TO_ACCN_ID in (select ACCN_ID from clickargo2.T_CORE_ACCN) 
	and CO_ACCN_ID in (select ACCN_ID from clickargo2.T_CORE_ACCN) ;
    
-- trip rate
insert ignore into clickargo2.T_CK_CT_TRIP_RATE(TR_ID, TR_RATE_TABLE, TR_VEH_TYPE, TR_LOC_FROM, TR_LOC_TO, TR_CHARGE
, TR_STATUS, TR_DT_CREATE, TR_UID_CREATE, TR_DT_LUPD, TR_UID_LUPD)
select distinct TRAP_RATE_ID, RATE_ID, VHTY_ID, FROM_LOC_ID, END_LOC_ID, max(TO_Price)
	,'A',sysdate(),'sys' ,sysdate(),'sys'   
FROM ctMigration.TMP_RATE
where TO_ACCN_ID in (select ACCN_ID from clickargo2.T_CORE_ACCN) 
	and CO_ACCN_ID in (select ACCN_ID from clickargo2.T_CORE_ACCN)
    and VHTY_ID is not null
    and length(trim(VHTY_ID)) > 0
group by TRAP_RATE_ID, RATE_ID, VHTY_ID, FROM_LOC_ID, END_LOC_ID;
