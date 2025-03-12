-- reverse utilized amount 

INSERT INTO clickargo2.T_CK_CREDIT_JOURNAL
(CJN_ID, CJN_SERVICE_TYPE, CJN_COMPANY, CJN_TXN_TYPE, CJN_TXN_REF, CJN_RESERVE, CJN_UTILIZED, CJN_CCY, CJN_STATUS, CJN_DT_CREATE, CJN_UID_CREATE, CJN_DT_LUPD, CJN_UID_LUPD)
select UNIX_TIMESTAMP() +ROUND(rand()*100000) CJN_ID,'CLICTRUCK' CJN_SERVICE_TYPE, CJN_COMPANY,'CR_ADJUSTMENT' CJN_TXN_TYPE,Concat('CK/COM.INT/VII-2023/001 - ',CJN_TXN_REF) CJN_TXN_REF, 
   sum(CJN_UTILIZED)*-1 util, 0,'IDR' CJN_CCY,'A' CJN_STATUS, sysdate() CJN_DT_CREATE, 'sys' CJN_UID_CREATE,null,null
   from clickargo2.T_CK_CREDIT_JOURNAL tccj 
where CJN_TXN_REF in (
SELECT jb.JOB_ID
FROM clickargo2.T_CK_JOB_TRUCK jb, clickargo2.T_CK_JOB b, clickargo2.T_CK_RECORD_DATE d
where jb.JOB_PARENT = b.JOB_ID
                and b.JOB_DATES = d.RCD_ID
    and JOB_STATE = 'APP_BILL'
                and JOB_PARTY_CO_FF in ('CK0222','CK0138') and JOB_OUT_PAYMENT_STATE not in ('PAID')
)
group by tccj.CJN_TXN_REF;

-- Cancel Payment 
update clickargo2.T_CK_PAYMENT_TXN
set PTX_PAYMENT_STATE='CANCELLED',PTX_STATUS='I'
where PTX_TYPE='BNKTF' and ptx_id in (
	select PTX_ID from
	(SELECT tcpt.PTX_ID 
	FROM clickargo2.T_CK_JOB_TRUCK jb, clickargo2.T_CK_JOB b, clickargo2.T_CK_RECORD_DATE d,clickargo2.T_CK_PAYMENT_TXN tcpt
	where jb.JOB_PARENT = b.JOB_ID
					and b.JOB_DATES = d.RCD_ID
		and JOB_STATE = 'APP_BILL'
					and JOB_PARTY_CO_FF in ('CK0222','CK0138') and JOB_OUT_PAYMENT_STATE not in ('PAID')
					and tcpt.PTX_SVC_REF  like concat ('%',jb.JOB_ID,'%')) t );

-- Set Invoice to Inactive
update clickargo2.T_CK_CT_PLATFORM_INVOICE
set INV_STATUS='I'
where INV_JOB_ID in (
SELECT jb.JOB_ID
FROM clickargo2.T_CK_JOB_TRUCK jb, clickargo2.T_CK_JOB b, clickargo2.T_CK_RECORD_DATE d
where jb.JOB_PARENT = b.JOB_ID
                and b.JOB_DATES = d.RCD_ID
    and JOB_STATE = 'APP_BILL'
                and JOB_PARTY_CO_FF in ('CK0222','CK0138') and JOB_OUT_PAYMENT_STATE not in ('PAID')
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
                and JOB_PARTY_CO_FF in ('CK0222','CK0138') and JOB_OUT_PAYMENT_STATE not in ('PAID')
);

-- set Debit Note to Inactive
/*
update clickargo2.T_CK_CT_DEBIT_NOTE
set  TI_STATUS='I'
where TI_JOB_NO in (
SELECT jb.JOB_ID
FROM clickargo2.T_CK_JOB_TRUCK jb, clickargo2.T_CK_JOB b, clickargo2.T_CK_RECORD_DATE d
where jb.JOB_PARENT = b.JOB_ID
                and b.JOB_DATES = d.RCD_ID
    and JOB_STATE = 'APP_BILL'
                and JOB_PARTY_CO_FF in ('CK0222','CK0138') and JOB_OUT_PAYMENT_STATE not in ('PAID')
);
*/
-- Update Job Status to Cancel 
Update  clickargo2.T_CK_JOB_TRUCK jb, clickargo2.T_CK_JOB b, clickargo2.T_CK_RECORD_DATE d
set jb.JOB_STATUS='I',b.JOB_STATE='CAN'
where jb.JOB_PARENT = b.JOB_ID
                and b.JOB_DATES = d.RCD_ID
    and JOB_STATE = 'APP_BILL'
                and JOB_PARTY_CO_FF in ('CK0222','CK0138') and JOB_OUT_PAYMENT_STATE not in ('PAID');
               

select * from clickargo2.T_CK_PAYMENT_TXN tcpt where PTX_PAYEE in ('CK0222','CK0138');
