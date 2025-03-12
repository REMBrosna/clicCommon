
use tmp;

DROP TABLE IF EXISTS TMP_DEPT;
DROP TABLE IF EXISTS TMP_DEPT_USR;

create table TMP_DEPT AS SELECT  ct.uuid as DEPT_UUID, company_uuid AS ACCN_UUID, teams_name AS DEPT_NAME, teams_description as DEPT_DESC, company_type AS ACCN_TYPE
	, color AS DEPT_COLOR, created_at as CREATE_AT, updated_at AS UPDATE_AT, cs.COMPANY_NAME as ACCN_NAME, cs.ACCN_ID
FROM prod_clickargo_company.company_teams ct left join tmp.TMP_COMPANY_SG cs
on ct.company_uuid = cs.UUID
WHERE deleted_at is null;


create table TMP_DEPT_USR AS Select usr.uuid USR_UUID, usr.fullname USR_NAME, DEPT_NAME,  DEPT_UUID, ACCN_UUID, ACCN_NAME
From `prod_clickargo_gateway`.users usr inner join TMP_DEPT dept on usr.company_uuid = dept.ACCN_UUID 
	inner join prod_clickargo_gateway.user_has_teams ut on ut.teams_uuid = dept.DEPT_UUID and ut.user_uuid = usr.uuid
where usr.deleted_at is null;

-- CO2


-- Rental


-- 
UPDATE `clickargo2`.`T_CORE_ACCN` SET `ACCN_TYPE` = 'ACC_TYPE_TO_WJ' WHERE (`ACCN_ID` = 'BOKS');
