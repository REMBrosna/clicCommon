
-- DSV CO account

INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('DSV', 'A', 'DSV A/S', 'DSV A/S', 'ACC_TYPE_CO', 'dsv', 'Singapore', '9 Pioneer View 627581 Singapore'
	, sysdate(), '627581', 'Singapore', 'Singapore', 'SG');
    
update `clickargo2`.`T_CORE_ACCN` set ACCN_TYPE = 'ACC_TYPE_FF' where ACCN_ID = 'DSV';

REPLACE INTO `clickargo2`.`T_CORE_USR` (USR_UID, USR_ACCNID, USR_STATUS, USR_TYPE_ONLINE, USR_TYPE_MBOX, USR_NAME
	, USR_DT_REG, USR_DT_COMM, USR_DT_PWD_LUPD, USR_LOGIN_INVCNT, USR_MBOX_ID
	, USR_ADDR1, USR_PCODE, USR_CITY, USR_EMAIL, USR_PWD) 
VALUES ('DSV_U0001', 'DSV', 'A', 'Y', 'N', 'DSV admin', SYSDATE(), SYSDATE(), SYSDATE(), 0, ''
	, '9 Pioneer View 627581 Singapore', '627581', 'Singapore', 'SIN@SG.DVS.COM','1dcee42389ca19452fd47a7d6df6e8ef');

INSERT INTO `clickargo2`.`T_CORE_USR_ROLE` (`UROL_UID`, `UROL_APPSCODE`, `UROL_ROLEID`, `UROL_ADMIN_OPT`, `UROL_STATUS`, `UROL_DT_CREATE`, `UROL_UID_CREATE`, `UROL_DT_LUPD`, `UROL_UID_LUPD`, `UROL_TEMP_ROLE`)
 VALUES ('DSV_U0001', 'CKT', 'ADMIN', 'N', 'A', SYSDATE(), 'SYS', SYSDATE(), 'SYS', 'N');
INSERT INTO `clickargo2`.`T_CORE_USR_ROLE` (`UROL_UID`, `UROL_APPSCODE`, `UROL_ROLEID`, `UROL_ADMIN_OPT`, `UROL_STATUS`, `UROL_DT_CREATE`, `UROL_UID_CREATE`, `UROL_DT_LUPD`, `UROL_UID_LUPD`, `UROL_TEMP_ROLE`) 
VALUES ('DSV_U0001', 'CKT', 'FF_FINANCE', 'N', 'A', SYSDATE(), 'SYS', SYSDATE(), 'SYS', 'N');
INSERT INTO `clickargo2`.`T_CORE_USR_ROLE` (`UROL_UID`, `UROL_APPSCODE`, `UROL_ROLEID`, `UROL_ADMIN_OPT`, `UROL_STATUS`, `UROL_DT_CREATE`, `UROL_UID_CREATE`, `UROL_DT_LUPD`, `UROL_UID_LUPD`, `UROL_TEMP_ROLE`) 
VALUES ('DSV_U0001', 'CKT', 'OFFICER', 'N', 'A', SYSDATE(), 'SYS', SYSDATE(), 'SYS', 'N');

INSERT INTO `clickargo2`.`T_CK_CT_MST_VEH_TYPE` (`VHTY_ID`, `VHTY_NAME`, `VHTY_DESC`, `VHTY_DESC_OTH`, `VHTY_STATUS`, `VHTY_DT_CREATE`, `VHTY_UID_CREATE`) 
VALUES ('UNDEFINE', 'UNDEFINE', 'UNDEFINE', 'UNDEFINE', 'A', SYSDATE(), 'SYS');

-- DSV TO Begin Begin
-- DeliveryLocalCartage type Party in DSV xml file

-- DSV TO End End

-- credit
INSERT INTO `clickargo2`.`T_CK_CREDIT` (`CR_ID`, `CR_SERVICE_TYPE`, `CR_COMPANY`, `CR_STATE`, `CR_AMT`, `CR_TXN_CAP`, `CR_CCY`, `CR_DT_START`, `CR_DT_END`, `CR_STATUS`, `CR_DT_CREATE`) 
VALUES ('CR_DSV', 'CLICTRUCK', 'DSV', 'APPROVED', '100000', '100000', 'IDR', '2023-11-16 15:23:00', '2040-05-30 00:00:00', 'A', SYSDATE());


alter table T_CK_CT_PARTY drop CONSTRAINT FK_CK_CT_PARTY_CK_CT_MST_PARTY_TYPE;

-- --- --- --- --- -


drop view if exists view_shipment;

create view view_shipment_distinct as
with fromLoc as(
SELECT TLOC_LOC_ADDRESS,trip.TR_JOB
		FROM clickargo2.T_CK_CT_TRIP_LOCATION tl, clickargo2.T_CK_CT_TRIP trip
        where trip.TR_FROM = tl.TLOC_ID
	),
    toLoc as (
    SELECT TLOC_LOC_ADDRESS,trip.TR_JOB
		FROM clickargo2.T_CK_CT_TRIP_LOCATION tl, clickargo2.T_CK_CT_TRIP trip
        where trip.TR_TO = tl.TLOC_ID
    )
SELECT distinct SH_MSG_ID, SH_NAME, SH_STATE, SH_REMARK, JOB_PARTY_TO, ifnull(accn.ACCN_NAME,'') as ACCN_NAME
, ifnull(truck_company_name,'') as truck_company_name, ifnull(pickup,'') as pickup, ifnull(destination,'') as destination
, ifnull(fromLoc.TLOC_LOC_ADDRESS,'') as fromLoc, ifnull(toLoc.TLOC_LOC_ADDRESS,'') as toLoc
FROM clickargo2.T_CK_CT_SHIPMENT c2 inner join  clickargo2.dsv_xml_shipment_disc c1 on (c2.SH_NAME = c1.filename and c2.SH_MSG_ID = c1.shipment_id)
	left join clickargo2.T_CK_JOB_TRUCK jt on c2.SH_JOB = jt.JOB_PARENT
    left join clickargo2.T_CORE_ACCN accn on jt.JOB_PARTY_TO = accn.ACCN_ID
    left join fromLoc on jt.JOB_ID = fromLoc.TR_JOB
    left join toLoc on jt.JOB_ID = toLoc.TR_JOB
 order by SH_MSG_ID desc;
 
-- create in sg production
drop table if exists tmp.dsv_xml_shipment;

create table tmp.dsv_xml_shipment as
SELECT
    cxr.shipment_id,
    cxr.filename,
    cxr.description,
    cj.uuid as job_uuid,
    cj.job_no,
    cj.truck_company_name,
    (
        SELECT
            address
        FROM
            prod_clicktruck.clicktruck_job_locations jl
        WHERE jl.job_uuid = cj.uuid AND jl.type = 'pickup'
    ) as pickup,
    (
        SELECT
            end
        FROM
            prod_clicktruck.clicktruck_job_destinations jd
        WHERE jd.job_uuid = cj.uuid ORDER BY id DESC LIMIT 1
    ) as destination

FROM prod_clicktruck.clicktruck_jobs cj
RIGHT JOIN prod_clicktruck.clicktruck_xml_record cxr on cj.job_no = cxr.job_no
WHERE cj.deleted_at IS NULL AND cxr.deleted_at IS NULL
	and cj.created_at > '2024-01-01';


drop view if exists view_shipment;

create view view_shipment as
with fromLoc as(
SELECT TLOC_LOC_ADDRESS,trip.TR_JOB
		FROM clickargo2.T_CK_CT_TRIP_LOCATION tl, clickargo2.T_CK_CT_TRIP trip
        where trip.TR_FROM = tl.TLOC_ID
	),
    toLoc as (
    SELECT TLOC_LOC_ADDRESS,trip.TR_JOB
		FROM clickargo2.T_CK_CT_TRIP_LOCATION tl, clickargo2.T_CK_CT_TRIP trip
        where trip.TR_TO = tl.TLOC_ID
    )
SELECT distinct SH_MSG_ID, SH_NAME, SH_STATE, SH_REMARK, JOB_PARTY_TO, ifnull(accn.ACCN_NAME,'') as ACCN_NAME
, ifnull(truck_company_name,'') as truck_company_name, ifnull(pickup,'') as pickup, ifnull(destination,'') as destination
, ifnull(fromLoc.TLOC_LOC_ADDRESS,'') as fromLoc, ifnull(toLoc.TLOC_LOC_ADDRESS,'') as toLoc
FROM clickargo2.T_CK_CT_SHIPMENT c2 inner join  clickargo2.dsv_xml_shipment c1 on (c2.SH_NAME = c1.filename or c2.SH_MSG_ID = c1.shipment_id)
	left join clickargo2.T_CK_JOB_TRUCK jt on c2.SH_JOB = jt.JOB_PARENT
    left join clickargo2.T_CORE_ACCN accn on jt.JOB_PARTY_TO = accn.ACCN_ID
    left join fromLoc on jt.JOB_ID = fromLoc.TR_JOB
    left join toLoc on jt.JOB_ID = toLoc.TR_JOB
 order by SH_MSG_ID desc;
 
-- 
select SH_MSG_ID, SH_NAME, ACCN_NAME
, truck_company_name , (ACCN_NAME = truck_company_name)
, fromLoc, pickup,(fromLoc != pickup)
, toLoc, destination, (toLoc != destination)
from view_shipment
 where ACCN_NAME != truck_company_name
	or fromLoc != pickup
	or toLoc != destination ;

-- ------------ begin insert Account
-- 1-5
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('LLWCM', 'A', 'LIAN LEE WOODEN CASE MAKER CO PTE LTD', 'LIAN LEE WOODEN CASE MAKER CO PTE LTD', 'ACC_TYPE_TO', '123456789', 'Singapore', '9 CHANGI SOUTH STREET 3 #01-03 Singapore'
	, sysdate(), '486361', 'Singapore', 'Singapore', 'SG');

INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('SSA', 'A', 'SG SAGAWA AMEROID PTE LTD', 'SG SAGAWA AMEROID PTE LTD', 'ACC_TYPE_TO', '123456789', 'Singapore', '15 Pioneer Walk, #02-01,Ameroid Pionner Hub BLDG Singapore'
	, sysdate(), '627753', 'Singapore', 'Singapore', 'SG');

--
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('SSHL', 'A', 'SIN HIN HANG LOGISTICS PTE LTD', 'SIN HIN HANG LOGISTICS PTE LTD', 'ACC_TYPE_TO', '123456789', 'Singapore', '26 QANTAS DRIVE BRISBANE AIRPORT AUSTRALIA'
	, sysdate(), '408708', 'AUSTRALIA', 'AUSTRALIA', 'AU');

--
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('IODSF', 'A', 'InterOffice Dummy SIN - FDA', 'InterOffice Dummy SIN - FDA', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('AAK', 'A', 'AAK Logistics Services Pte Ltd', 'AAK Logistics Services Pte Ltd', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
-- 6-10
   
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('DSVTS', 'A', 'DSV TRANSPORTATION & SERVICES PTE. LTD.', 'DSV TRANSPORTATION & SERVICES PTE. LTD.', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');

INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('BL', 'A', 'Beta Logistics', 'Beta Logistics', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('TL', 'A', 'Try Logistics', 'Try Logistics', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('BST', 'A', 'Bill Sing TV1', 'Bill Sing TV1', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('DFD', 'A', 'DSVS-KFC Delivery', 'DSVS-KFC Delivery', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');

-- 11- 15
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('GL', 'A', 'Godspeed Logistics', 'Godspeed Logistics', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('NVL', 'A', 'New Vision Logistics', 'New Vision Logistics', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('WT', 'A', 'Widhi Truckers', 'Widhi Truckers', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('EYPT', 'A', 'EY Parthenon Truckers', 'EY Parthenon Truckers', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('CLP', 'A', 'Cavio Logistics Pte Ltd', 'Cavio Logistics Pte Ltd', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');

-- 16-20
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('BFL', 'A', 'Big Foot Logistics', 'Big Foot Logistics', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('JWPT', 'A', 'JD Waters Pte Ltd', 'JD Waters Pte Ltd', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('NECS', 'A', 'Network Express Courier Services Pte Ltd', 'Network Express Courier Services Pte Ltd', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('DSVR', 'A', 'DSV A/S Road', 'DSV A/S Road', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('CLEP', 'A', 'Call Lade Enterprises Pte Ltd', 'Call Lade Enterprises Pte Ltd', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');

-- 21-24
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('OWTE', 'A', '123 Express Pte Ltd', '123 Express Pte Ltd', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('MGM', 'A', 'MG Movers', 'MG Movers', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('CS', 'A', 'Clasquin Singapore Pte Ltd', 'Clasquin Singapore Pte Ltd', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');
    
INSERT INTO `clickargo2`.`T_CORE_ACCN` (`ACCN_ID`, `ACCN_STATUS`, `ACCN_NAME`, `ACCN_NAME_OTH`, `ACCN_TYPE`, `ACCN_COY_REGN`
	, `ACCN_NATIONALITY`, `ACCN_ADDR1`, `ACCN_DT_REG`, `ACCN_PCODE`, `ACCN_CITY`, `ACCN_PROV`, `ACCN_CTYCODE`) 
VALUES ('AE', 'A', 'Ana Exports Pte Ltd', 'Ana Exports Pte Ltd', 'ACC_TYPE_TO', '123456789', 'Singapore', '119 Airport Cargo Road #03-09 498801 Singapore'
	, sysdate(), '498801', 'Singapore', 'Singapore', 'SG');

-- -------------end insert account



-- user begin
REPLACE INTO `clickargo2`.`T_CORE_USR` (USR_UID, USR_ACCNID, USR_STATUS, USR_TYPE_ONLINE, USR_TYPE_MBOX, USR_NAME
	, USR_DT_REG, USR_DT_COMM, USR_DT_PWD_LUPD, USR_LOGIN_INVCNT, USR_MBOX_ID
	, USR_ADDR1, USR_PCODE, USR_CITY, USR_EMAIL, USR_PWD) 
VALUES ('IODSF_U0001', 'IODSF', 'A', 'Y', 'N', 'DSV admin', SYSDATE(), SYSDATE(), SYSDATE(), 0, ''
	, '9 Pioneer View 627581 Singapore', '627581', 'Singapore', 'ADMIN@IODSF.COM','8d97906bd51df1f7920914aba2d748df');
-- user end

-- user role begin
INSERT INTO `clickargo2`.`T_CORE_USR_ROLE` (`UROL_UID`, `UROL_APPSCODE`, `UROL_ROLEID`, `UROL_ADMIN_OPT`, `UROL_STATUS`, `UROL_DT_CREATE`, `UROL_UID_CREATE`, `UROL_DT_LUPD`, `UROL_UID_LUPD`, `UROL_TEMP_ROLE`)
VALUES ('IODSF_U0001', 'CKT', 'ADMIN', 'N', 'A', SYSDATE(), 'SYS', SYSDATE(), 'SYS', 'N');
INSERT INTO `clickargo2`.`T_CORE_USR_ROLE` (`UROL_UID`, `UROL_APPSCODE`, `UROL_ROLEID`, `UROL_ADMIN_OPT`, `UROL_STATUS`, `UROL_DT_CREATE`, `UROL_UID_CREATE`, `UROL_DT_LUPD`, `UROL_UID_LUPD`, `UROL_TEMP_ROLE`) 
VALUES ('IODSF_U0001', 'CKT', 'FF_FINANCE', 'N', 'A', SYSDATE(), 'SYS', SYSDATE(), 'SYS', 'N');
INSERT INTO `clickargo2`.`T_CORE_USR_ROLE` (`UROL_UID`, `UROL_APPSCODE`, `UROL_ROLEID`, `UROL_ADMIN_OPT`, `UROL_STATUS`, `UROL_DT_CREATE`, `UROL_UID_CREATE`, `UROL_DT_LUPD`, `UROL_UID_LUPD`, `UROL_TEMP_ROLE`) 
VALUES ('IODSF_U0001', 'CKT', 'OFFICER', 'N', 'A', SYSDATE(), 'SYS', SYSDATE(), 'SYS', 'N');
INSERT INTO `clickargo2`.`T_CORE_USR_ROLE` (`UROL_UID`, `UROL_APPSCODE`, `UROL_ROLEID`, `UROL_ADMIN_OPT`, `UROL_STATUS`, `UROL_DT_CREATE`, `UROL_UID_CREATE`, `UROL_DT_LUPD`, `UROL_UID_LUPD`, `UROL_TEMP_ROLE`) 
VALUES ('IODSF_U0001', 'CKT', 'OP_ADMIN', 'N', 'A', SYSDATE(), 'SYS', SYSDATE(), 'SYS', 'N');
-- user role end


-- contract
INSERT INTO `clickargo2`.`T_CK_CT_CONTRACT_CHARGE` (`CONC_ID`, `CONC_PLTFEE_AMT`, `CONC_PLTFEE_TYPE`, `CONC_ADDTAX_AMT`, `CONC_ADDTAX_TYPE`, `CONC_WHTAX_AMT`, `CONC_WHTAX_TYPE`, `CONC_STATUS`, `CONC_DT_CREATE`, `CONC_UID_CREATE`) 
VALUES ('CKCTCCHT0IODSF', '3.00', 'P', '11.00', 'P', '0.00', 'F', 'A', '2023-06-30 06:34:55', 'sys');

INSERT INTO `clickargo2`.`T_CK_CT_CONTRACT_CHARGE` (`CONC_ID`, `CONC_PLTFEE_AMT`, `CONC_PLTFEE_TYPE`, `CONC_ADDTAX_AMT`, `CONC_ADDTAX_TYPE`, `CONC_WHTAX_AMT`, `CONC_WHTAX_TYPE`, `CONC_STATUS`, `CONC_DT_CREATE`, `CONC_UID_CREATE`) 
VALUES ('CKCTCCHT0DSV', '3.00', 'P', '11.00', 'P', '0.00', 'F', 'A', '2023-06-30 06:34:55', 'sys');

REPLACE INTO `clickargo2`.`T_CK_CT_CONTRACT` (`CON_ID`, `CON_NAME`, `CON_DESCRIPTION`, `CON_CCY`, `CON_DT_START`, `CON_DT_END`, `CON_TO`, `CON_CHARGE_TO`, `CON_PAYTERM_TO`, `CON_CO_FF`, `CON_CHARGE_CO_FF`, `CON_PAYTERM_CO_FF`, `CON_STATUS`, `CON_DT_CREATE`, `CON_UID_CREATE`, `CON_DT_LUPD`, `CON_UID_LUPD`) 
VALUES ('CKCTCONC000IODSV_DSF', 'IODSV_DSV', '', 'IDR', '2023-06-07 16:59:30', '2025-06-09 16:59:00', 'IODSF', 'CKCTCCHT0IODSF', '12', 'DSV', 'CKCTCCHT0DSV', '45', 'A', '2023-06-07 17:00:54', 'BERAM_U001', '2023-06-12 18:56:33', 'GLI_U001');


INSERT INTO `clickargo2`.`T_CK_CT_CONTRACT_CHARGE` (`CONC_ID`, `CONC_PLTFEE_AMT`, `CONC_PLTFEE_TYPE`, `CONC_ADDTAX_AMT`, `CONC_ADDTAX_TYPE`, `CONC_WHTAX_AMT`, `CONC_WHTAX_TYPE`, `CONC_STATUS`, `CONC_DT_CREATE`, `CONC_UID_CREATE`) 
VALUES ('CKCTCCHT0SSA', '3.00', 'P', '11.00', 'P', '0.00', 'F', 'A', '2023-06-30 06:34:55', 'sys');

INSERT INTO `clickargo2`.`T_CK_CT_CONTRACT_CHARGE` (`CONC_ID`, `CONC_PLTFEE_AMT`, `CONC_PLTFEE_TYPE`, `CONC_ADDTAX_AMT`, `CONC_ADDTAX_TYPE`, `CONC_WHTAX_AMT`, `CONC_WHTAX_TYPE`, `CONC_STATUS`, `CONC_DT_CREATE`, `CONC_UID_CREATE`) 
VALUES ('CKCTCCHT0DSV_SSA', '3.00', 'P', '11.00', 'P', '0.00', 'F', 'A', '2023-06-30 06:34:55', 'sys');

REPLACE INTO `clickargo2`.`T_CK_CT_CONTRACT` (`CON_ID`, `CON_NAME`, `CON_DESCRIPTION`, `CON_CCY`, `CON_DT_START`, `CON_DT_END`, `CON_TO`, `CON_CHARGE_TO`, `CON_PAYTERM_TO`, `CON_CO_FF`, `CON_CHARGE_CO_FF`, `CON_PAYTERM_CO_FF`, `CON_STATUS`, `CON_DT_CREATE`, `CON_UID_CREATE`, `CON_DT_LUPD`, `CON_UID_LUPD`) 
VALUES ('CKCTCONC000IODSV_SSA', 'CKCTCONC000IODSV_SSA', '', 'IDR', '2023-06-07 16:59:30', '2025-06-09 16:59:00', 'SSA', 'CKCTCCHT0SSA', '12', 'DSV', 'CKCTCCHT0DSV_SSA', '45', 'A', '2023-06-07 17:00:54', 'BERAM_U001', '2023-06-12 18:56:33', 'GLI_U001');


-- driver
INSERT INTO `clickargo2`.`T_CK_CT_DRV` (`DRV_ID`, `DRV_NAME`, `DRV_STATE`, `DRV_COMPANY`, `DRV_LICENSE_NO`, `DRV_LICENSE_EXPIRY`, `DRV_LICENSE_PHOTO_NAME`, `DRV_LICENSE_PHOTO_LOC`, `DRV_EMAIL`, `DRV_PHONE`, `DRV_MOBILE_ID`, `DRV_MOBILE_PASSWORD`, `DRV_LOGIN_FAILURE`, `DRV_MOBILE_LANG`, `DRV_STATUS`, `DRV_DT_CREATE`, `DRV_UID_CREATE`, `DRV_DT_LUPD`, `DRV_UID_LUPD`) 
VALUES ('CKCTDRV2023IODSF', 'Novan', 'UNASSIGNED', 'IODSF', 'nv081333', '2040-12-31 00:00:00', 'GG FILTER.pdf', '/home/vcc/appAttachments/clictruck/GG FILTER.pdf', 'novan@gmail.com', '081313131313', 'Novan', '21a75a33896ef95238558a65ac29d08b', '0', 'en', 'A', '2023-05-23 17:02:34', 'PTO_U001', '2023-06-18 13:30:25', 'BERAM_U001');

-- vehicle
INSERT INTO `clickargo2`.`T_CK_CT_VEH` (`VH_ID`, `VH_TYPE`, `VH_STATE`, `VH_COMPANY`, `VH_PLATE_NO`, `VH_CLASS`, `VH_LENGTH`, `VH_WIDTH`, `VH_HEIGHT`, `VH_WEIGHT`, `VH_VOLUME`, `VH_CHASSIS_NO`, `VH_IS_MAINTENANCE`, `VH_GPS_IMEI`, `VH_STATUS`, `VH_DT_CREATE`, `VH_UID_CREATE`) 
VALUES ('CKCTVEH0IODSF', 'CONTAINER40FT', 'UNASSIGNED', 'IODSF', 'H 8710 OW- 40F', '0', '0', '0', '0', '0', '0', 'H 8710 OW- 40F', 'N', '', 'A', '2023-06-30 06:34:55', 'sys');

-- --

INSERT INTO `clickargo2`.`T_CK_SVC_SUB` (`SUB_ID`, `SUB_TYPE`, `SUB_STATE`, `SUB_SUBSCRIBER`, `SUB_DT_START`, `SUB_DT_VALID`, `SUB_AUTO_RENEW`, `SUB_UID_VERIFY`, `SUB_DT_VERIFY`, `SUB_UID_APPROVE`, `SUB_DT_APPROVE`, `SUB_STATUS`, `SUB_DT_CREATE`, `SUB_UID_CREATE`) 
VALUES ('CT2-DVS', 'CLICTRUCK', 'APR', 'DSV', '2023-01-01 00:00:00', '2033-01-01 00:00:00', 'N', 'sys', '2023-08-26 16:38:25', 'sys', '2023-08-26 16:38:25', 'A', '2023-08-26 16:38:25', 'sys');


INSERT INTO `clickargo2`.`T_CORE_NOTIFICATION_TEMPLATE` (`NTPL_ID`, `NTPL_APPSCODE`, `NTPL_CHANNEL_TYPE`, `NTPL_CONTENT_TYPE`, `NTPL_SUBJECT`, `NTPL_TEMPALTE`, `NTPL_SEQ`, `NTPL_DESC`, `NTPL_STATUS`, `NTPL_UID_CREATE`, `NTPL_DT_CREATE`, `NTPL_UID_LUPD`, `NTPL_DT_LUPD`) 
VALUES ('CKT_NTL_0061', 'CKT', 'CHN_TYPE_EMAIL', 'CNT_TYPE_HTML', '[ediDocManager SHP :subject]', '<html><html>', '50', 'Notification to CO when account is suspended by scheduler due to unpaid invoices', 'A', 'SYS', '2023-10-25 01:57:10', 'SYS', '2023-10-25 01:57:10');

-- select md5('IODSF@GMAIL.COMABC');
-- update CKCTDRV2023IODSF set

INSERT INTO `clickargo2`.`T_CORE_ACCN_CONFIG` (`ACFG_ACCNID`, `ACFG_KEY`, `ACFG_VAL`, `ACFG_DESC`, `ACFG_VALID_FROM_DT`, `ACFG_VALID_TO_DT`, `ACFG_SEQ`, `ACFG_STATUS`, `ACFG_DT_CREATE`, `ACFG_UID_CREATE`, `ACFG_DT_LUPD`) 
VALUES ('IODSF', 'MOBILE_ENABLED', 'Y', 'MOBILE_ENABLED', '2023-09-13 09:43:40', '2023-09-13 09:43:40', '1', 'A', '2023-09-13 09:43:40', 'SYS', '2023-11-23 11:09:37');

UPDATE `clickargo2`.`T_CK_CT_DRV` SET `DRV_EMAIL` = 'IODSF@GMAIL.COM', `DRV_MOBILE_ID` = 'IODSF@GMAIL.COM', `DRV_MOBILE_PASSWORD` = '059b1e16c45fb0bb588de933588bcb7e' 
WHERE (`DRV_ID` = 'CKCTDRV2023IODSF');

INSERT INTO `clickargo2`.`T_CORE_SYSPARAM` (`SYS_KEY`, `SYS_VAL`, `SYS_DESC`, `SYS_DT_CREATE`, `SYS_UID_CREATE`, `SYS_DT_LUPD`, `SYS_UID_LUPD`, `SYS_STATUS`) 
VALUES ('CLICTRUCK_DSV_EMAIL_RECEIVER', 'Zhang.Ji@guud.company, nina.catudio@guud.company', 'CLICTRUCK_DSV_EMAIL_RECEIVER', '2023-05-05 00:00:00', 'SYS', '2023-05-05 00:00:00', 'SYS', 'A');
