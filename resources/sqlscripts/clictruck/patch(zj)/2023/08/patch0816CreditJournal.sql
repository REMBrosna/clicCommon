-- Payment  PT Handal Deliveri Logistik    

insert ignore into clickargo2.T_CK_CREDIT_JOURNAL(CJN_ID, CJN_SERVICE_TYPE, CJN_COMPANY, CJN_TXN_TYPE
, CJN_RESERVE, CJN_UTILIZED,CJN_TXN_REF, CJN_CCY, CJN_STATUS, CJN_DT_CREATE, CJN_UID_CREATE)
select UNIX_TIMESTAMP() , 'CLICTRUCK', ACCN_ID, 'CR_ADJUSTMENT', 0, -15027750,'Adjustment of Incoming Payment 160823', 'IDR', 'A',sysdate(),'sys'
from clickargo2.T_CORE_ACCN where ACCN_ID in('CK0259') ;

-- Payment PT Lazuardo Vittorio Indonesia

insert ignore into clickargo2.T_CK_CREDIT_JOURNAL(CJN_ID, CJN_SERVICE_TYPE, CJN_COMPANY, CJN_TXN_TYPE
, CJN_RESERVE, CJN_UTILIZED,CJN_TXN_REF, CJN_CCY, CJN_STATUS, CJN_DT_CREATE, CJN_UID_CREATE)
select UNIX_TIMESTAMP() , 'CLICTRUCK', ACCN_ID, 'CR_ADJUSTMENT', 0, -367314000,'Adjustment of Incoming Payment 160823', 'IDR', 'A',sysdate(),'sys'
from clickargo2.T_CORE_ACCN where ACCN_ID in('CK0287') ;

-- Payment PT Transindo Jayatama

insert ignore into clickargo2.T_CK_CREDIT_JOURNAL(CJN_ID, CJN_SERVICE_TYPE, CJN_COMPANY, CJN_TXN_TYPE
, CJN_RESERVE, CJN_UTILIZED,CJN_TXN_REF, CJN_CCY, CJN_STATUS, CJN_DT_CREATE, CJN_UID_CREATE)
select UNIX_TIMESTAMP() , 'CLICTRUCK', ACCN_ID, 'CR_ADJUSTMENT', 0, -722125863,'Adjustment of Incoming Payment 160823', 'IDR', 'A',sysdate(),'sys'
from clickargo2.T_CORE_ACCN where ACCN_ID in('CK0210') ;