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
UCASE('PT. DHL Global Forwarding Indonesia'), 																						-- 3 ACCN_NAME
UCASE('PT. DHL Global Forwarding Indonesia'), 																						-- 4 ACCN_NAME_OTH
UCASE('ACC_TYPE_FF'), 																												-- 5 ACCN_TYPE
'19579218058000', 																													-- 6 ACCN_COY_REGN
'Trembesi Tower, Suite 15, Jalan Pahlawan Seribu Kav. CBD Lot VIIA, BSD, Kel. Lengkong Gudang, Kec. Serpong, Tangerang Selatan', 	-- 7 ACCN_ADDR1													-- 7 ACCN_ADDR1
'', 																																-- 8 ACCN_ADDR2
'', 																																-- 9 ACCN_ADDR3
'15321', 																															-- 10 ACCN_PCODE
'Tangerang Selatan', 																												-- 11 ACCN_CITY
'Banten', 																															-- 12 ACCN_PROV
'ID', 																																-- 13 ACCN_CTYCODE
'02140055100', 																														-- 14 ACCN_TEL
'0', 																																-- 15 ACCN_FAX
UCASE('importbeef@surinusantara.com'), 																								-- 16 ACCN_EMAIL
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
'Mahardhito Gifari', 																													-- 6 USR_NAME
UCASE('mahardhito.ghifari@dhl.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('mahardhito.ghifari@dhl.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'087785236772', 																															-- 15 USR_TEL
'OFFICER AND OFFICER_FINANCE',																																	-- 16 USR_TITLE
'Trembesi Tower, Suite 15, Jalan Pahlawan Seribu Kav. CBD Lot VIIA, BSD, Kel. Lengkong Gudang, Kec. Serpong, Tangerang Selatan, 15321'	-- 17 USR_ADDR1
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
'Iman Sudirman', 																														-- 6 USR_NAME
UCASE('iman.sudirman@dhl.com'), 																										-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('iman.sudirman@dhl.com'),'123')), 			-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'081291770007', 																															-- 15 USR_TEL
'OFFICER AND OFFICER_FINANCE',																																	-- 16 USR_TITLE
'Trembesi Tower, Suite 15, Jalan Pahlawan Seribu Kav. CBD Lot VIIA, BSD, Kel. Lengkong Gudang, Kec. Serpong, Tangerang Selatan, 15321'	-- 17 USR_ADDR1
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
'Wahid Nurkodin', 																														-- 6 USR_NAME
UCASE('wahid.nurkodin@dhl.com'), 																										-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('wahid.nurkodin@dhl.com'),'123')), 			-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'087777114540', 																															-- 15 USR_TEL
'OFFICER_FINANCE',																																-- 16 USR_TITLE
'Trembesi Tower, Suite 15, Jalan Pahlawan Seribu Kav. CBD Lot VIIA, BSD, Kel. Lengkong Gudang, Kec. Serpong, Tangerang Selatan, 15321'	-- 17 USR_ADDR1
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
'Wiwiek Widyastuti', 																													-- 6 USR_NAME
UCASE('wiwiek.widyastuti@dhl.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('wiwiek.widyastuti@dhl.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'081311492171', 																															-- 15 USR_TEL
'OFFICER',																																-- 16 USR_TITLE
'Trembesi Tower, Suite 15, Jalan Pahlawan Seribu Kav. CBD Lot VIIA, BSD, Kel. Lengkong Gudang, Kec. Serpong, Tangerang Selatan, 15321'	-- 17 USR_ADDR1
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
'Rizka Arlia Kartika', 																													-- 6 USR_NAME
UCASE('rizka.kartika@dhl.com'), 																										-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('rizka.kartika@dhl.com'),'123')), 			-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'081957178720', 																															-- 15 USR_TEL
'OFFICER',																																-- 16 USR_TITLE
'Trembesi Tower, Suite 15, Jalan Pahlawan Seribu Kav. CBD Lot VIIA, BSD, Kel. Lengkong Gudang, Kec. Serpong, Tangerang Selatan, 15321'	-- 17 USR_ADDR1
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
'Moh. Rizki Munggaran', 																												-- 6 USR_NAME
UCASE('rizki.munggaran@dhl.com'), 																										-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('rizki.munggaran@dhl.com'),'123')), 		-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'08111170093', 																															-- 15 USR_TEL
'OFFICER',																																-- 16 USR_TITLE
'Trembesi Tower, Suite 15, Jalan Pahlawan Seribu Kav. CBD Lot VIIA, BSD, Kel. Lengkong Gudang, Kec. Serpong, Tangerang Selatan, 15321'	-- 17 USR_ADDR1
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
'Andi Widodo', 																															-- 6 USR_NAME
UCASE('andi.widodo@dhl.com'), 																											-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('andi.widodo@dhl.com'),'123')), 			-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'089643747900', 																															-- 15 USR_TEL
'OFFICER',																																-- 16 USR_TITLE
'Trembesi Tower, Suite 15, Jalan Pahlawan Seribu Kav. CBD Lot VIIA, BSD, Kel. Lengkong Gudang, Kec. Serpong, Tangerang Selatan, 15321'	-- 17 USR_ADDR1
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
'Gianti Kusumawardani', 																												-- 6 USR_NAME
UCASE('gianti.kusumawardani@dhl.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('gianti.kusumawardani@dhl.com'),'123')), 	-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'0', 																																	-- 15 USR_TEL
'OFFICER',																																-- 16 USR_TITLE
'Trembesi Tower, Suite 15, Jalan Pahlawan Seribu Kav. CBD Lot VIIA, BSD, Kel. Lengkong Gudang, Kec. Serpong, Tangerang Selatan, 15321'	-- 17 USR_ADDR1
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
'Gianti Kusumawardani', 																												-- 6 USR_NAME
UCASE('gianti.kusumawardani@dhl.com'), 																									-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('gianti.kusumawardani@dhl.com'),'123')), 	-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'0', 																																	-- 15 USR_TEL
'OFFICER',																																-- 16 USR_TITLE
'Magna Commercial, Jl. Magna Boulevard No 38, Sumarecon Bandung, Kel. Rancabolang, Kec Gedebage, Kota Bandung, Jawa Barat, 40294'		-- 17 USR_ADDR1
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
'Iwan Novianto', 																														-- 6 USR_NAME
UCASE('iwan.novianto@dhl.com'), 																										-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('iwan.novianto@dhl.com'),'123')), 			-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'0', 																																	-- 15 USR_TEL
'OFFICER',																																-- 16 USR_TITLE
'Graha Pena Building 20th Floor Room 2002 JL Ahmad Yani No. 88, Surabaya, Jawa Timur, 60234'											-- 17 USR_ADDR1
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
'Hari Santoso', 																														-- 6 USR_NAME
UCASE('hari.santoso@dhl.com'), 																											-- 7 USR_EMAIL
sysdate(), 																																-- 8 USR_DT_REG
sysdate(), 																																-- 9 USR_DT_COMM
md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('hari.santoso@dhl.com'),'123')), 			-- 10 USR_PWD
sysdate(), 																																-- 11 USR_DT_PWD_LUPD
0, 																																		-- 12 USR_LOGIN_INVCNT
'', 																																	-- 13 USR_MBOX_ID
'ID', 																																	-- 14 USR_CTYCODE
'0', 																																	-- 15 USR_TEL
'OFFICER',																																-- 16 USR_TITLE
'SINARMAS LAND PLAZA - 8th Floor # 810  Jl. P.Diponegoro no. 18, Medan, Sumatera Utara, 20152'											-- 17 USR_ADDR1
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
'OFFICER', 						-- 3 UROL_ROLEID
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
'OFFICER_FINANCE', 				-- 3 UROL_ROLEID
'N', 							-- 4 UROL_ADMIN_OPT
'A', 							-- 5 UROL_STATUS
SYSDATE(), 						-- 6 UROL_DT_CREATE
'SYS', 							-- 7 UROL_UID_CREATE
SYSDATE(), 						-- 8 UROL_DT_LUPD
'SYS', 							-- 9 UROL_UID_LUPD
'N' 							-- 10 UROL_TEMP_ROLE
FROM clickargo2.T_CORE_USR
where USR_UID in ('CKU****'); 	-- T_CORE_USR.USR_UID


-- T_CORE_ACCN_CONFIG
INSERT INTO clickargo2.T_CORE_ACCN_CONFIG(
ACFG_ACCNID, 				-- 1
ACFG_KEY, 					-- 2
ACFG_VAL, 					-- 3
ACFG_DESC, 					-- 4
ACFG_VALID_FROM_DT, 		-- 5
ACFG_VALID_TO_DT, 			-- 6
ACFG_SEQ, 					-- 7
ACFG_STATUS, 				-- 8
ACFG_DT_CREATE, 			-- 9
ACFG_UID_CREATE 			-- 10
)
VALUES(
'CK0710', 					-- 1 ACFG_ACCNID
'CO2SL_VA_IDR', 			-- 2 ACFG_KEY
'7851001000963224', 		-- 3 ACFG_VAL
'SL Payee Account (IDR)', 	-- 4 ACFG_DESC
'2024-06-14 00:00:00', 		-- 5 ACFG_VALID_FROM_DT
'2034-06-14 00:00:00', 		-- 6 ACFG_VALID_TO_DT
1, 							-- 7 ACFG_SEQ
'A', 						-- 8 ACFG_STATUS
SYSDATE(), 					-- 9 ACFG_DT_CREATE
'sys' 						-- 10 ACFG_UID_CREATE
);

INSERT INTO clickargo2.T_CORE_ACCN_CONFIG(
ACFG_ACCNID, 				-- 1
ACFG_KEY, 					-- 2
ACFG_VAL, 					-- 3
ACFG_DESC, 					-- 4
ACFG_VALID_FROM_DT, 		-- 5
ACFG_VALID_TO_DT, 			-- 6
ACFG_SEQ, 					-- 7
ACFG_STATUS, 				-- 8
ACFG_DT_CREATE, 			-- 9
ACFG_UID_CREATE 			-- 10
)
VALUES(
'CK0710', 					-- 1 ACFG_ACCNID
'CO2SL_VA_USD', 			-- 2 ACFG_KEY
'7852001000963224', 		-- 3 ACFG_VAL
'SL Payee Account (USD)', 	-- 4 ACFG_DESC
'2024-06-14 00:00:00', 		-- 5 ACFG_VALID_FROM_DT
'2034-06-14 00:00:00', 		-- 6 ACFG_VALID_TO_DT
1, 							-- 7 ACFG_SEQ
'A', 						-- 8 ACFG_STATUS
SYSDATE(), 					-- 9 ACFG_DT_CREATE
'sys' 						-- 10 ACFG_UID_CREATE
);

/*INSERT INTO clickargo2.T_CORE_ACCN_CONFIG(
ACFG_ACCNID, 				-- 1
ACFG_KEY, 					-- 2
ACFG_VAL, 					-- 3
ACFG_DESC, 					-- 4
ACFG_VALID_FROM_DT, 		-- 5
ACFG_VALID_TO_DT, 			-- 6
ACFG_SEQ, 					-- 7
ACFG_STATUS, 				-- 8
ACFG_DT_CREATE, 			-- 9
ACFG_UID_CREATE 			-- 10
)
VALUES(
'CK****', 					-- 1 ACFG_ACCNID
'FF2SP_VA_IDR', 			-- 2 ACFG_KEY
'****', 					-- 3 ACFG_VAL
'FF Payment Account (IDR)', -- 4 ACFG_DESC
'2024-06-14 00:00:00', 		-- 5 ACFG_VALID_FROM_DT
'2034-06-14 00:00:00', 		-- 6 ACFG_VALID_TO_DT
1, 							-- 7 ACFG_SEQ
'A', 						-- 8 ACFG_STATUS
SYSDATE(), 					-- 9 ACFG_DT_CREATE
'sys' 						-- 10 ACFG_UID_CREATE
);*/


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
SVAU_UID_CREATE 				-- 17
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
'SYS' 							-- 17 SVAU_UID_CREATE
);*/

