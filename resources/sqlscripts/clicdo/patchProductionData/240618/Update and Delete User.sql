-- Update email and password user Thamas 
UPDATE clickargo2.T_CORE_USR 
SET 
USR_EMAIL = UCASE('Thamas.filipi@msc.com'), 
USR_PWD = md5(CONCAT('CKU01436',UCASE('Thamas.filipi@msc.com'),'123'))
WHERE USR_UID  = 'CKU01436';

-- Delete user Gianti Kusumawardani with address Trembesi Tower, because already create with same email and different address
DELETE FROM clickargo2.T_CORE_USR_ROLE 
WHERE UROL_UID = 'CKU01409';

DELETE FROM clickargo2.T_CORE_USR
WHERE USR_UID = 'CKU01409';

-- Add role for user Rohmat
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
'OPERATIONS', 					-- 3 UROL_ROLEID
'N', 							-- 4 UROL_ADMIN_OPT
'A', 							-- 5 UROL_STATUS
SYSDATE(), 						-- 6 UROL_DT_CREATE
'SYS', 							-- 7 UROL_UID_CREATE
SYSDATE(), 						-- 8 UROL_DT_LUPD
'SYS', 							-- 9 UROL_UID_LUPD
'N' 							-- 10 UROL_TEMP_ROLE
FROM clickargo2.T_CORE_USR
where USR_UID in ('CKU01438'); 	-- T_CORE_USR.USR_UID