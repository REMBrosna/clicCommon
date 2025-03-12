-- reverse utilized amount 

INSERT INTO clickargo2.T_CK_CREDIT_JOURNAL
(CJN_ID, CJN_SERVICE_TYPE, CJN_COMPANY, CJN_TXN_TYPE, CJN_TXN_REF, CJN_RESERVE, CJN_UTILIZED, CJN_CCY, CJN_STATUS, CJN_DT_CREATE, CJN_UID_CREATE, CJN_DT_LUPD, CJN_UID_LUPD)

select UNIX_TIMESTAMP() +ROUND(rand()*1000) CJN_ID,'CLICTRUCK' CJN_SERVICE_TYPE, CJN_COMPANY,'CR_ADJUSTMENT' CJN_TXN_TYPE,Concat('CK/COM.INT/VII-2023/001 - ',CJN_TXN_REF) CJN_TXN_REF, 
   0, sum(CJN_UTILIZED)*-1 util,'IDR' CJN_CCY,'A' CJN_STATUS, sysdate() CJN_DT_CREATE, 'sys' CJN_UID_CREATE,null,null
   from clickargo2.T_CK_CREDIT_JOURNAL tccj 
where CJN_TXN_REF in (
SELECT jb.JOB_ID
FROM clickargo2.T_CK_JOB_TRUCK jb, clickargo2.T_CK_JOB b, clickargo2.T_CK_RECORD_DATE d
where jb.JOB_PARENT = b.JOB_ID
                and b.JOB_DATES = d.RCD_ID
    and JOB_STATE = 'APP_BILL'
                and JOB_PARTY_CO_FF in ('CK0244','CK0277','CK0294','CK0193','CK0255','CK0228','CK0215') and JOB_OUT_PAYMENT_STATE not in ('PAID')
)
group by tccj.CJN_TXN_REF;


-- Set Invoice to Inactive
update clickargo2.T_CK_CT_PLATFORM_INVOICE
set INV_STATUS='I'
where INV_JOB_ID in (
SELECT jb.JOB_ID
FROM clickargo2.T_CK_JOB_TRUCK jb, clickargo2.T_CK_JOB b, clickargo2.T_CK_RECORD_DATE d
where jb.JOB_PARENT = b.JOB_ID
                and b.JOB_DATES = d.RCD_ID
    and JOB_STATE = 'APP_BILL'
                and JOB_PARTY_CO_FF in ('CK0244','CK0277','CK0294','CK0193','CK0255','CK0228','CK0215') and JOB_OUT_PAYMENT_STATE not in ('PAID')
);

-- set Debit Note to Inactive
update clickargo2.T_CK_CT_DEBIT_NOTE
set  DN_STATUS='I'
where DN_JOB_ID in (
SELECT jb.JOB_ID
FROM clickargo2.T_CK_JOB_TRUCK jb, clickargo2.T_CK_JOB b, clickargo2.T_CK_RECORD_DATE d
where jb.JOB_PARENT = b.JOB_ID
                and b.JOB_DATES = d.RCD_ID
    and JOB_STATE = 'APP_BILL'
                and JOB_PARTY_CO_FF in ('CK0244','CK0277','CK0294','CK0193','CK0255','CK0228','CK0215') and JOB_OUT_PAYMENT_STATE not in ('PAID')
);

-- set Debit Note to Inactive
update clickargo2.T_CK_CT_DEBIT_NOTE
set  TI_STATUS='I'
where TI_JOB_NO in (
SELECT jb.JOB_ID
FROM clickargo2.T_CK_JOB_TRUCK jb, clickargo2.T_CK_JOB b, clickargo2.T_CK_RECORD_DATE d
where jb.JOB_PARENT = b.JOB_ID
                and b.JOB_DATES = d.RCD_ID
    and JOB_STATE = 'APP_BILL'
                and JOB_PARTY_CO_FF in ('CK0244','CK0277','CK0294','CK0193','CK0255','CK0228','CK0215') and JOB_OUT_PAYMENT_STATE not in ('PAID')
);

-- Cancel Payment 
update clickargo2.T_CK_PAYMENT_TXN tcpt 
set PTX_PAYMENT_STATE='CANCELLED',PTX_STATUS='I'
where PTX_TYPE='BNKTF' and ptx_id in ('TXNDO4342538768018374','TXNDO4758371792948697');


-- Update Job Status to Cancel 
Update  clickargo2.T_CK_JOB_TRUCK jb, clickargo2.T_CK_JOB b, clickargo2.T_CK_RECORD_DATE d
set jb.JOB_STATUS='I',b.JOB_STATE='CAN'
where jb.JOB_PARENT = b.JOB_ID
                and b.JOB_DATES = d.RCD_ID
    and JOB_STATE = 'APP_BILL'
                and JOB_PARTY_CO_FF in ('CK0244','CK0277','CK0294','CK0193','CK0255','CK0228','CK0215') and JOB_OUT_PAYMENT_STATE not in ('PAID');
               
-- ------
update clickargo2.T_CK_CREDIT_JOURNAL
    set cjn_reserve = cjn_utilized
    where cjn_id in ('1693543051',
'1693543144',
'1693543220',
'1693543235',
'1693543449',
'1693543493',
'1693543545',
'1693543558',
'1693543602',
'1693543653',
'1693543697',
'1693543711',
'1693543791',
'1693543881',
'1693543966');

 

    update clickargo2.T_CK_CREDIT_JOURNAL
    set cjn_utilized=0
    where cjn_id in ('1693543051',
'1693543144',
'1693543220',
'1693543235',
'1693543449',
'1693543493',
'1693543545',
'1693543558',
'1693543602',
'1693543653',
'1693543697',
'1693543711',
'1693543791',
'1693543881',
'1693543966');