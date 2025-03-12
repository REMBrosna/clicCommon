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
and ACCN_ID ='GLI'; 			-- T_CORE_ACCN.ACCN_ID 

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
'GLI_WAHYU', 																															-- 1 USR_UID
'GLI', 																																	-- 2 USR_ACCNID
'A', 																																	-- 3 USR_STATUS
'Y', 																																	-- 4 USR_TYPE_ONLINE
'N', 																																	-- 5 USR_TYPE_MBOX
'Ahmad Wahyudi', 																														-- 6 USR_NAME
UCASE('ahmad.wahyudi@guud.company'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT('GLI_WAHYU',UCASE('ahmad.wahyudi@guud.company'),'123')), 																	-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'0', 																														-- 15 USR_TEL
'ADMIN',																																-- 16 USR_TITLE
'Jakarta'																																-- 17 USR_ADDR1
from clickargo2.T_CORE_USR tcu limit 1;

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
'ADMIN', 						-- 3 UROL_ROLEID
'N', 							-- 4 UROL_ADMIN_OPT
'A', 							-- 5 UROL_STATUS
SYSDATE(), 						-- 6 UROL_DT_CREATE
'SYS', 							-- 7 UROL_UID_CREATE
SYSDATE(), 						-- 8 UROL_DT_LUPD
'SYS', 							-- 9 UROL_UID_LUPD
'N' 							-- 10 UROL_TEMP_ROLE
FROM clickargo2.T_CORE_USR
where USR_UID in ('GLI_WAHYU'); 	-- T_CORE_USR.USR_UID

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
'ADMIN', 						-- 3 UROL_ROLEID
'N', 							-- 4 UROL_ADMIN_OPT
'A', 							-- 5 UROL_STATUS
SYSDATE(), 						-- 6 UROL_DT_CREATE
'SYS', 							-- 7 UROL_UID_CREATE
SYSDATE(), 						-- 8 UROL_DT_LUPD
'SYS', 							-- 9 UROL_UID_LUPD
'N' 							-- 10 UROL_TEMP_ROLE
FROM clickargo2.T_CORE_USR
where USR_UID in ('GLI_L1_001'); 	-- T_CORE_USR.USR_UID

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
'ADMIN', 						-- 3 UROL_ROLEID
'N', 							-- 4 UROL_ADMIN_OPT
'A', 							-- 5 UROL_STATUS
SYSDATE(), 						-- 6 UROL_DT_CREATE
'SYS', 							-- 7 UROL_UID_CREATE
SYSDATE(), 						-- 8 UROL_DT_LUPD
'SYS', 							-- 9 UROL_UID_LUPD
'N' 							-- 10 UROL_TEMP_ROLE
FROM clickargo2.T_CORE_USR
where USR_UID in ('GLI_VER_ADITH'); 	-- T_CORE_USR.USR_UID

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
'ADMIN', 						-- 3 UROL_ROLEID
'N', 							-- 4 UROL_ADMIN_OPT
'A', 							-- 5 UROL_STATUS
SYSDATE(), 						-- 6 UROL_DT_CREATE
'SYS', 							-- 7 UROL_UID_CREATE
SYSDATE(), 						-- 8 UROL_DT_LUPD
'SYS', 							-- 9 UROL_UID_LUPD
'N' 							-- 10 UROL_TEMP_ROLE
FROM clickargo2.T_CORE_USR
where USR_UID in ('REZA'); 	-- T_CORE_USR.USR_UID

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
'ADMIN', 						-- 3 UROL_ROLEID
'N', 							-- 4 UROL_ADMIN_OPT
'A', 							-- 5 UROL_STATUS
SYSDATE(), 						-- 6 UROL_DT_CREATE
'SYS', 							-- 7 UROL_UID_CREATE
SYSDATE(), 						-- 8 UROL_DT_LUPD
'SYS', 							-- 9 UROL_UID_LUPD
'N' 							-- 10 UROL_TEMP_ROLE
FROM clickargo2.T_CORE_USR
where USR_UID in ('SANDI_GLI'); 	-- T_CORE_USR.USR_UID

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
'ADMIN', 						-- 3 UROL_ROLEID
'N', 							-- 4 UROL_ADMIN_OPT
'A', 							-- 5 UROL_STATUS
SYSDATE(), 						-- 6 UROL_DT_CREATE
'SYS', 							-- 7 UROL_UID_CREATE
SYSDATE(), 						-- 8 UROL_DT_LUPD
'SYS', 							-- 9 UROL_UID_LUPD
'N' 							-- 10 UROL_TEMP_ROLE
FROM clickargo2.T_CORE_USR
where USR_UID in ('GLI_U001'); 	-- T_CORE_USR.USR_UID