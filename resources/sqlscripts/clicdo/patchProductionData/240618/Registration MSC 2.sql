-- T_CORE_ACCN
insert ignore into clickargo2.T_CORE_ACCN(
ACCN_ID, 		-- 1
ACCN_STATUS, 	-- 2
ACCN_NAME, 		-- 3
ACCN_NAME_OTH, 	-- 4
ACCN_TYPE, 		-- 5
ACCN_COY_REGN, 	-- 6
ACCN_ADDR1, 	-- 7
ACCN_ADDR2, 	-- 8
ACCN_ADDR3, 	-- 9
ACCN_PCODE, 	-- 10
ACCN_CITY, 		-- 11
ACCN_PROV, 		-- 12
ACCN_CTYCODE, 	-- 13
ACCN_TEL, 		-- 14
ACCN_FAX, 		-- 15
ACCN_EMAIL, 	-- 16
ACCN_DT_REG 	-- 17 
)
Select
@coid1:=concat('CK', lpad(max(CAST(SUBSTR(tca.accn_id,3,4) as UNSIGNED))+1,4,'0')), 												-- 1 ACCN_ID
'A', 																																-- 2 ACCN_STATUS
UCASE('MEDITERRANEAN SHIPPING COMPANY SA'), 																						-- 3 ACCN_NAME
UCASE('MEDITERRANEAN SHIPPING COMPANY SA'), 																						-- 4 ACCN_NAME_OTH
UCASE('ACC_TYPE_SL'), 																												-- 5 ACCN_TYPE
'0', 																																-- 6 ACCN_COY_REGN
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt', 					-- 7 ACCN_ADDR1													-- 7 ACCN_ADDR1
'', 																																-- 8 ACCN_ADDR2
'', 																																-- 9 ACCN_ADDR3
'12710', 																															-- 10 ACCN_PCODE
'Jakarta Selatan', 																													-- 11 ACCN_CITY
'Jakarta', 																															-- 12 ACCN_PROV
'ID', 																																-- 13 ACCN_CTYCODE
'02150805000', 																														-- 14 ACCN_TEL
'0', 																																-- 15 ACCN_FAX
UCASE('idn-info@msc.com'), 																											-- 16 ACCN_EMAIL
sysdate() 																															-- 17 ACCN_DT_REG
from clickargo2.T_CORE_ACCN tca where tca.ACCN_ID like 'CK%';

-- T_CK_SVC_SUB
INSERT INTO clickargo2.T_CK_SVC_SUB(
SUB_ID, 			-- 1
SUB_TYPE, 			-- 2
SUB_STATE, 			-- 3
SUB_SUBSCRIBER, 	-- 4
SUB_DT_START, 		-- 5
SUB_DT_VALID, 		-- 6
SUB_AUTO_RENEW, 	-- 7
SUB_UID_VERIFY, 	-- 8
SUB_DT_VERIFY, 		-- 9
SUB_UID_APPROVE, 	-- 10
SUB_DT_APPROVE, 	-- 11
SUB_STATUS, 		-- 12
SUB_DT_CREATE, 		-- 13
SUB_UID_CREATE 	-- 14
)
select 
CONCAT('CK0',ROUND(rand()*100000)), -- 1 SUB_ID
'CLICDO', 							-- 2 SUB_TYPE
'APR', 								-- 3 SUB_STATE
accn_id, 							-- 4 SUB_SUBSCRIBER
'2024-06-14 00:00:00', 				-- 5 SUB_DT_START
'2034-06-14 00:00:00', 				-- 6 SUB_DT_VALID
'N', 								-- 7 SUB_AUTO_RENEW
'sys', 								-- 8 SUB_UID_VERIFY
sysdate(), 							-- 9 SUB_DT_VERIFY
'sys', 								-- 10 SUB_UID_APPROVE
sysdate(), 							-- 11 SUB_DT_APPROVE
'A', 								-- 12 SUB_STATUS
sysdate(), 							-- 13 SUB_DT_CREATE
'sys' 								-- 14 SUB_UID_CREATE
from clickargo2.T_CORE_ACCN tca
where tca.ACCN_ID not in (
   select distinct SUB_SUBSCRIBER 
   from clickargo2.T_CK_SVC_SUB 
   where SUB_TYPE='CLICDO' and SUB_STATE='APR'
)
and ACCN_ID = @coid1; 			-- T_CORE_ACCN.ACCN_ID 


-- T_CORE_USER
insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Hizaz', 																													-- 6 USR_NAME
UCASE('abdul.hizaz@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('abdul.hizaz@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE AND OPERATIONS',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';


insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Yoga', 																													-- 6 USR_NAME
UCASE('yoga.kusuma@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('yoga.kusuma@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE AND OPERATIONS',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Philippe', 																													-- 6 USR_NAME
UCASE('philippe.olivero@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('philippe.olivero@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE AND OPERATIONS',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';


insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Herryanto Tan', 																													-- 6 USR_NAME
UCASE('herryanto.tan@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('herryanto.tan@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Frederick Meluk', 																													-- 6 USR_NAME
UCASE('frederick.meluk@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('frederick.meluk@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Fita Kamsahamita', 																													-- 6 USR_NAME
UCASE('fita.kamsahamita@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('fita.kamsahamita@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Siti Rohyani', 																													-- 6 USR_NAME
UCASE('siti.rohyani@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('siti.rohyani@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Mieko Timothea', 																													-- 6 USR_NAME
UCASE('mieko.timothea@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('mieko.timothea@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';


insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Safitri Lubis', 																													-- 6 USR_NAME
UCASE('safitri.lubis@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('safitri.lubis@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Eviyanti', 																													-- 6 USR_NAME
UCASE('eviyanti.simanjuntak@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('eviyanti.simanjuntak@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Dahlia Meliara', 																													-- 6 USR_NAME
UCASE('dahlia.meliara@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('dahlia.meliara@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Debi Pertiwi', 																													-- 6 USR_NAME
UCASE('debi.pertiwi@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('debi.pertiwi@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Rahmi Wahyuni', 																													-- 6 USR_NAME
UCASE('rahmi.wahyuni@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('rahmi.wahyuni@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Ainun Lutfiah', 																													-- 6 USR_NAME
UCASE('ainun.lutfiah@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('ainun.lutfiah@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Ari Novianti', 																													-- 6 USR_NAME
UCASE('ari.novianti@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('ari.novianti@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Anggi Fridayanti', 																													-- 6 USR_NAME
UCASE('anggi.fridayanti@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('anggi.fridayanti@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Nafilah Diva', 																													-- 6 USR_NAME
UCASE('nafilah.diva@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('nafilah.diva@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Andina Khairunnisa', 																													-- 6 USR_NAME
UCASE('andina.khairunnisa@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('andina.khairunnisa@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Fathan Anshori', 																													-- 6 USR_NAME
UCASE('fathan.anshori@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('fathan.anshori@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Topari', 																													-- 6 USR_NAME
UCASE('topari.id@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('topari.id@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Yuni', 																													-- 6 USR_NAME
UCASE('yuni.id@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('yuni.id@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Miftakhur Rozi', 																													-- 6 USR_NAME
UCASE('miftakhur.rozi@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('miftakhur.rozi@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Yesi Septiani', 																													-- 6 USR_NAME
UCASE('yesi.septiani@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('yesi.septiani@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'FINANCE',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Thamas', 																													-- 6 USR_NAME
UCASE('hamas.filipi@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('hamas.filipi@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'OPERATIONS',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Yasmin', 																													-- 6 USR_NAME
UCASE('sri.yasmin@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('sri.yasmin@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'OPERATIONS',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Rohmat', 																													-- 6 USR_NAME
UCASE('rohmat.hidayat@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('rohmat.hidayat@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'OPERATIONS',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

insert ignore into clickargo2.T_CORE_USR(
USR_UID, 			-- 1
USR_ACCNID, 		-- 2
USR_STATUS, 		-- 3
USR_TYPE_ONLINE, 	-- 4 
USR_TYPE_MBOX, 		-- 5
USR_NAME, 			-- 6
USR_EMAIL, 			-- 7
USR_DT_REG, 		-- 8
USR_DT_COMM, 		-- 9
USR_PWD, 			-- 10
USR_DT_PWD_LUPD, 	-- 11 
USR_LOGIN_INVCNT, 	-- 12
USR_MBOX_ID, 		-- 13
USR_CTYCODE, 		-- 14
USR_TEL, 			-- 15
USR_TITLE,			-- 16
USR_ADDR1			-- 17
) 
select 
concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')), 															-- 1 USR_UID
@coid1, 																																-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Syafiq', 																													-- 6 USR_NAME
UCASE('syafiq.hibatullah@msc.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('syafiq.hibatullah@msc.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'', 																															-- 15 USR_TEL
'OPERATIONS',																																	-- 16 USR_TITLE
'Capital Place Building 39th floor, Jl. Gatot Subroto No.Kav. 18, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt'	-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu where tcu.usr_uid like 'CKU%';

-- T_CORE_USR_ROLE
insert ignore into clickargo2.T_CORE_USR_ROLE(
UROL_UID, 						-- 1
UROL_APPSCODE, 					-- 2
UROL_ROLEID, 					-- 3
UROL_ADMIN_OPT, 				-- 4
UROL_STATUS, 					-- 5
UROL_DT_CREATE, 				-- 6
UROL_UID_CREATE, 				-- 7
UROL_DT_LUPD, 					-- 8
UROL_UID_LUPD, 					-- 9
UROL_TEMP_ROLE 					-- 10
)
SELECT 
USR_UID, 						-- 1 UROL_UID
'CKDO', 						-- 2 UROL_APPSCODE
'FINANCE', 						-- 3 UROL_ROLEID
'N', 							-- 4 UROL_ADMIN_OPT
'A', 							-- 5 UROL_STATUS
SYSDATE(), 						-- 6 UROL_DT_CREATE
'SYS', 							-- 7 UROL_UID_CREATE
SYSDATE(), 						-- 8 UROL_DT_LUPD
'SYS', 							-- 9 UROL_UID_LUPD
'N' 							-- 10 UROL_TEMP_ROLE
FROM clickargo2.T_CORE_USR
where USR_UID in ('CKU****'); 	-- T_CORE_USR.USR_UID


insert ignore into clickargo2.T_CORE_USR_ROLE(
UROL_UID, 						-- 1
UROL_APPSCODE, 					-- 2
UROL_ROLEID, 					-- 3
UROL_ADMIN_OPT, 				-- 4
UROL_STATUS, 					-- 5
UROL_DT_CREATE, 				-- 6
UROL_UID_CREATE, 				-- 7
UROL_DT_LUPD, 					-- 8
UROL_UID_LUPD, 					-- 9
UROL_TEMP_ROLE 					-- 10
)
SELECT 
USR_UID, 						-- 1 UROL_UID
'CKDO', 						-- 2 UROL_APPSCODE
'OPERATIONS', 				-- 3 UROL_ROLEID
'N', 							-- 4 UROL_ADMIN_OPT
'A', 							-- 5 UROL_STATUS
SYSDATE(), 						-- 6 UROL_DT_CREATE
'SYS', 							-- 7 UROL_UID_CREATE
SYSDATE(), 						-- 8 UROL_DT_LUPD
'SYS', 							-- 9 UROL_UID_LUPD
'N' 							-- 10 UROL_TEMP_ROLE
FROM clickargo2.T_CORE_USR
where USR_UID in ('CKU****'); 	-- T_CORE_USR.USR_UID


-- T_CK_SVC_AUTH
/*INSERT INTO clickargo2.T_CK_SVC_AUTH(
SVAU_ID, 						-- 1
SVAU_SERVICE, 					-- 2
SVAU_STATE, 					-- 3
SVAU_ACCN_AUTHORIZER, 			-- 4
SVAU_USR_AUTHORIZER, 			-- 5
SVAU_POSITION_AUTHORIZER, 		-- 6
SVAU_ACCN_AUTHORIZED, 			-- 7
SVAU_USR_AUTHORIZED, 			-- 8
SVAU_POSITION_AUTHORIZED, 		-- 9
SVAU_ACCN_SERVICE, 				-- 10
SVAU_DT_SERVICE_START, 			-- 11
SVAU_DT_SERVICE_VALID, 			-- 12
SVAU_REMARKS_AUTHORIZER, 		-- 13
SVAU_REMARKS_AUTHORIZED, 		-- 14
SVAU_STATUS, 					-- 15
SVAU_DT_CREATE, 				-- 16
SVAU_UID_CREATE, 				-- 17
)
VALUES(
'Last ID ******', 				-- 1 SVAU_ID
'CLICDO', 						-- 2 SVAU_SERVICE
'AUTH', 						-- 3 SVAU_STATE
'CO - CK***', 					-- 4 SVAU_ACCN_AUTHORIZER
'CO - CKU***', 					-- 5 SVAU_USR_AUTHORIZER
'Manager', 						-- 6 SVAU_POSITION_AUTHORIZER
'FF - CK***', 					-- 7 SVAU_ACCN_AUTHORIZED
'FF - CKU***', 					-- 8 SVAU_USR_AUTHORIZED
'Manager', 						-- 9 SVAU_POSITION_AUTHORIZED
'MSC - CK***', 					-- 10 SVAU_ACCN_SERVICE
'2024-06-14 00:00:00', 			-- 11 SVAU_DT_SERVICE_START
'2034-06-14 00:00:00', 			-- 12 SVAU_DT_SERVICE_VALID
'-', 							-- 13 SVAU_REMARKS_AUTHORIZER
'-', 							-- 14 SVAU_REMARKS_AUTHORIZED
'A', 							-- 15 SVAU_STATUS
SYSDATE(), 						-- 16 SVAU_DT_CREATE
'SYS', 							-- 17 SVAU_UID_CREATE
);*/

