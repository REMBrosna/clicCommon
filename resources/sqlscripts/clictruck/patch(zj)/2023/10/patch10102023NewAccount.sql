-- Payment PT. LAZUARDO VITTORIO INDONESIA
insert  into clickargo2.T_CK_CREDIT_JOURNAL(CJN_ID, CJN_SERVICE_TYPE, CJN_COMPANY, CJN_TXN_TYPE
, CJN_RESERVE, CJN_UTILIZED,CJN_TXN_REF, CJN_CCY, CJN_STATUS, CJN_DT_CREATE, CJN_UID_CREATE)
select UNIX_TIMESTAMP() +ROUND(rand()*1000), 'CLICTRUCK', ACCN_ID, 'CR_ADJUSTMENT', 0, -99217000,'Adjustment of Incoming Payment 10102023', 'IDR', 'A',sysdate(),'sys'
from clickargo2.T_CORE_ACCN where ACCN_ID ='CK0287' ;

-- setup Company
insert ignore into clickargo2.T_CORE_ACCN(ACCN_ID, ACCN_STATUS, ACCN_NAME, ACCN_NAME_OTH, ACCN_TYPE, ACCN_COY_REGN, ACCN_ADDR1, ACCN_ADDR2, ACCN_ADDR3, ACCN_PCODE, ACCN_CITY, ACCN_PROV, ACCN_CTYCODE, ACCN_TEL, ACCN_FAX, ACCN_EMAIL,  ACCN_DT_REG) 
Select @coid2:= concat('CK', lpad(max(CAST(SUBSTR(tca.accn_id,3,4)as UNSIGNED))+1,4,'0')),'A',UCASE('PT JAGOAN KIRIMAN EKPRES'),UCASE('PT JAGOAN KIRIMAN EKPRES'),UCASE('ACC_TYPE_TO'),'602392839071000','THE CITY TOWER LEVEL.12-1N
JL.MH. THAMRIN NO 18 MENTENG MENTENG, JAKARTA PUSAT','','','','','','ID','0','0',UCASE('jagoankirimanexpress@gmail.com'),sysdate() from clickargo2.T_CORE_ACCN tca where tca.ACCN_ID  like 'CK%';

insert ignore into clickargo2.T_CORE_USR(USR_UID, USR_ACCNID, USR_STATUS, USR_TYPE_ONLINE, USR_TYPE_MBOX, USR_NAME, USR_EMAIL, USR_DT_REG, USR_DT_COMM, USR_PWD, USR_DT_PWD_LUPD, USR_LOGIN_INVCNT, USR_MBOX_ID,USR_CTYCODE)  
select  concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),@coid2,'A','Y','N','Admin',UCASE('jagoankirimanexpress@gmail.com'),sysdate(),sysdate(),md5(CONCAT(concat('CKU', lpad(max(CAST(SUBSTR(tcu.usr_uid,4,6)as UNSIGNED))+1,5,'0')),UCASE('jagoankirimanexpress@gmail.com'),'123')),sysdate(),0,'','ID' from clickargo2.T_CORE_USR  tcu where tcu.usr_uid like 'CKU%';

INSERT INTO clickargo2.T_CORE_ACCN_CONFIG VALUES(@coid2,'BANK_DETAIL','BCA:7571668886','BANK_DETAIL',sysdate(),date_add(sysdate(),INTERVAL 5 YEAR),1,'A',sysdate(),'sys',null,null);
INSERT INTO clickargo2.T_CORE_ACCN_CONFIG VALUES(@coid2,'SAGE_ACCN_ID','CLT0036','Sage ID',sysdate(),date_add(sysdate(),INTERVAL 5 YEAR),1,'A',sysdate(),'sys',null,null);
INSERT INTO clickargo2.T_CORE_ACCN_CONFIG VALUES(@coid2,'ACCN_MGR','Dyah','Account Manager',sysdate(),date_add(sysdate(),INTERVAL 5 YEAR),1,'A',sysdate(),'sys',null,null);

-- Contract PT Jagoan Kiriman Express as Trucking vendor of PT Damatrans.
 
 set @coid_con:=CONCAT('CKCTCCH',DATE_FORMAT(SYSDATE(),'%Y%m%d'),LPAD(round(RAND()*1000000), 5,'0')),@toid_con:=CONCAT('CKCTCCH',DATE_FORMAT(SYSDATE(),'%Y%m%d'),LPAD(round(RAND()*1000000), 5,'0'));
 set @coid:='CK0646',@toid:=@coid2;
INSERT INTO clickargo2.T_CK_CT_CONTRACT_CHARGE
(CONC_ID, CONC_PLTFEE_AMT, CONC_PLTFEE_TYPE, CONC_ADDTAX_AMT, CONC_ADDTAX_TYPE, CONC_WHTAX_AMT, CONC_WHTAX_TYPE, CONC_STATUS, 
CONC_DT_CREATE, CONC_UID_CREATE, CONC_DT_LUPD, CONC_UID_LUPD)
select 	@toid_con ,5,'P',11,'P',0,'F','A',sysdate(),'sys',sysdate(),'sys' -- TO Platform Fee
union 
select 	@coid_con ,25000,'F',11,'P',0,'F','A',sysdate(),'sys',sysdate(),'sys'; -- CO Platform Fee
INSERT INTO clickargo2.T_CK_CT_CONTRACT
(CON_ID, CON_NAME, CON_DESCRIPTION, CON_CCY, CON_DT_START, CON_DT_END, CON_TO, CON_CHARGE_TO, CON_PAYTERM_TO, CON_CO_FF, CON_CHARGE_CO_FF, CON_PAYTERM_CO_FF, CON_STATUS, CON_DT_CREATE, CON_UID_CREATE, CON_DT_LUPD, CON_UID_LUPD)
select 	CONCAT('CKCTCONC',DATE_FORMAT(SYSDATE(),'%Y%m%d'),LPAD(ROUND(RAND()*1000000), 5,'0')), CONCAT('Contract CO-TO : ',
(select accn_name from clickargo2.T_CORE_ACCN where accn_id =@coid) ,' - ',
(select accn_name from clickargo2.T_CORE_ACCN where accn_id =@toid)) , 
'Contract ','IDR',DATE_FORMAT('2023-08-25','%Y-%m-%d'),DATE_FORMAT('2024-08-24','%Y-%m-%d'),
@toid,@toid_con,
null,
@coid,@coid_con,
60, -- CO TOP
'A',sysdate(),'sys',null,null;

-- setup Role 

insert ignore into clickargo2.T_CORE_USR_ROLE(UROL_UID, UROL_APPSCODE, UROL_ROLEID, UROL_ADMIN_OPT, UROL_STATUS, UROL_DT_CREATE, UROL_UID_CREATE, UROL_DT_LUPD, UROL_UID_LUPD, UROL_TEMP_ROLE)
SELECT USR_UID, 'CKT', 'OFFICER', 'N', 'A', SYSDATE(), 'SYS', SYSDATE(), 'SYS','N' FROM clickargo2.T_CORE_USR
where USR_ACCNID in (SELECT ACCN_ID FROM clickargo2.T_CORE_ACCN where ACCN_TYPE = 'ACC_TYPE_TO') and USR_UID not in (select UROL_UID from clickargo2.T_CORE_USR_ROLE where UROL_ROLEID ='OFFICER');

insert ignore  into clickargo2.T_CORE_USR_ROLE(UROL_UID, UROL_APPSCODE, UROL_ROLEID, UROL_ADMIN_OPT, UROL_STATUS, UROL_DT_CREATE, UROL_UID_CREATE, UROL_DT_LUPD, UROL_UID_LUPD, UROL_TEMP_ROLE)
SELECT USR_UID, 'CK', 'FINANCE', 'N', 'A', SYSDATE(), 'SYS', SYSDATE(), 'SYS','N' FROM clickargo2.T_CORE_USR
where USR_ACCNID in (SELECT ACCN_ID FROM clickargo2.T_CORE_ACCN where ACCN_TYPE = 'ACC_TYPE_TO') and USR_UID not in (select UROL_UID from clickargo2.T_CORE_USR_ROLE where UROL_ROLEID ='FINANCE');


insert ignore  into clickargo2.T_CORE_USR_ROLE(UROL_UID, UROL_APPSCODE, UROL_ROLEID, UROL_ADMIN_OPT, UROL_STATUS, UROL_DT_CREATE, UROL_UID_CREATE, UROL_DT_LUPD, UROL_UID_LUPD, UROL_TEMP_ROLE)
SELECT USR_UID, 'CKT', 'FF_FINANCE', 'N', 'A', SYSDATE(), 'SYS', SYSDATE(), 'SYS','N' FROM clickargo2.T_CORE_USR
where USR_ACCNID in (SELECT ACCN_ID FROM clickargo2.T_CORE_ACCN where ACCN_TYPE = 'ACC_TYPE_TO') and USR_UID not in (select UROL_UID from clickargo2.T_CORE_USR_ROLE where UROL_ROLEID ='FF_FINANCE');

insert ignore into clickargo2.T_CORE_USR_ROLE(UROL_UID, UROL_APPSCODE, UROL_ROLEID, UROL_ADMIN_OPT, UROL_STATUS, UROL_DT_CREATE, UROL_UID_CREATE, UROL_DT_LUPD, UROL_UID_LUPD, UROL_TEMP_ROLE)
SELECT USR_UID, 'CKT', 'ADMIN', 'N', 'A', SYSDATE(), 'SYS', SYSDATE(), 'SYS','N' FROM clickargo2.T_CORE_USR
where USR_ACCNID in (SELECT ACCN_ID FROM clickargo2.T_CORE_ACCN where ACCN_TYPE = 'ACC_TYPE_TO') and USR_UID not in (select UROL_UID from clickargo2.T_CORE_USR_ROLE where UROL_ROLEID ='ADMIN');

insert ignore into clickargo2.T_CORE_USR_ROLE(UROL_UID, UROL_APPSCODE, UROL_ROLEID, UROL_ADMIN_OPT, UROL_STATUS, UROL_DT_CREATE, UROL_UID_CREATE, UROL_DT_LUPD, UROL_UID_LUPD, UROL_TEMP_ROLE)
SELECT USR_UID, 'CKT', 'OP_ADMIN', 'N', 'A', SYSDATE(), 'SYS', SYSDATE(), 'SYS','N' FROM clickargo2.T_CORE_USR
where USR_ACCNID in (SELECT ACCN_ID FROM clickargo2.T_CORE_ACCN where ACCN_TYPE = 'ACC_TYPE_TO') and  USR_UID not in (select UROL_UID from clickargo2.T_CORE_USR_ROLE where UROL_ROLEID ='OP_ADMIN');

-- setup services
INSERT INTO clickargo2.T_CK_SVC_SUB
(SUB_ID, SUB_TYPE, SUB_STATE, SUB_SUBSCRIBER, SUB_DT_START, SUB_DT_VALID, SUB_AUTO_RENEW, SUB_UID_VERIFY, SUB_DT_VERIFY, SUB_UID_APPROVE, SUB_DT_APPROVE, SUB_STATUS, SUB_DT_CREATE, SUB_UID_CREATE, SUB_DT_LUPD, SUB_UID_LUPD)
select CONCAT('CK0',ROUND(rand()*100000)), 'CLICTRUCK', 'APR',accn_id, '2023-01-01 00:00:00', '2033-01-01 00:00:00','N','sys',sysdate(),'sys',sysdate(),'A',sysdate(),'sys',null,null
from clickargo2.T_CORE_ACCN tca 
where tca.ACCN_ID not in (select distinct SUB_SUBSCRIBER from  clickargo2.T_CK_SVC_SUB where SUB_TYPE='CLICTRUCK' and SUB_STATE='APR' );
