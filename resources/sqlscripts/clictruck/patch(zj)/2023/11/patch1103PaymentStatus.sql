select tcjt.JOB_ID, tcjt.JOB_OUT_PAYMENT_STATE , tcpt.* 
from clickargo2.T_CK_PAYMENT_TXN tcpt inner join clickargo2.T_CK_JOB_TRUCK tcjt on PTX_SVC_REF like CONCAT('%', tcjt.JOB_ID  ,'%')
where tcjt.JOB_OUT_PAYMENT_STATE ='NEW'
and tcpt.PTX_PAYMENT_STATE in ('PAID','PAYING')
and PTX_TYPE='BNKTF';

update
clickargo2.T_CK_PAYMENT_TXN tcpt join clickargo2.T_CK_JOB_TRUCK tcjt on PTX_SVC_REF like CONCAT('%', tcjt.JOB_ID  ,'%')
set tcjt.JOB_OUT_PAYMENT_STATE='PAID'
where tcjt.JOB_OUT_PAYMENT_STATE ='NEW'
and tcpt.PTX_PAYMENT_STATE in ('PAID','PAYING')
and PTX_TYPE='BNKTF';