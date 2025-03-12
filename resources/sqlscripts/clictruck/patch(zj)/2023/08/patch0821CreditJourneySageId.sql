-- Update Bank Account and Sage id PT DAF
INSERT INTO clickargo2.T_CORE_ACCN_CONFIG VALUES('CK0657','SAGE_ACCN_ID','LID008','Sage ID',sysdate(),date_add(sysdate(),INTERVAL 5 YEAR),1,'A',sysdate(),'sys',null,null);
INSERT INTO clickargo2.T_CORE_ACCN_CONFIG VALUES('CK0657','BANK_DETAIL','MNC:206010005402525','Bank Account',sysdate(),date_add(sysdate(),INTERVAL 5 YEAR),1,'A',sysdate(),'sys',null,null);

-- Reverse Payment CV. JERIKO TOBA JAYA  
insert ignore into clickargo2.T_CK_CREDIT_JOURNAL(CJN_ID, CJN_SERVICE_TYPE, CJN_COMPANY, CJN_TXN_TYPE
, CJN_RESERVE, CJN_UTILIZED,CJN_TXN_REF, CJN_CCY, CJN_STATUS, CJN_DT_CREATE, CJN_UID_CREATE)
select UNIX_TIMESTAMP() , 'CLICTRUCK', ACCN_ID, 'CR_ADJUSTMENT', 0, 10333000,' Reverse Adjustment of Incoming Payment 210823', 'IDR', 'A',sysdate(),'sys'
from clickargo2.T_CORE_ACCN where ACCN_ID ='CK0372' ;

-- Update Credit limit  PT Awal Lestari Rezeki 
UPDATE clickargo2.T_CK_CREDIT
	SET CR_AMT=500000000
	WHERE CR_COMPANY ='CK0314';

UPDATE clickargo2.T_CK_CREDIT_SUMMARY
	SET CRS_AMT=500000000,CRS_BALANCE=CRS_BALANCE+350000000
	WHERE CRS_COMPANY ='CK0314';


