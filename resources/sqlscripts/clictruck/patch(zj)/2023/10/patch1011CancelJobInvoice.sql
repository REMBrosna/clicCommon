
update clickargo2.T_CK_CT_PLATFORM_INVOICE tccpi
join clickargo2.T_CK_JOB_TRUCK tcjt on tccpi.INV_JOB_ID = tcjt.JOB_ID
join clickargo2.T_CK_JOB tcj on tcjt.JOB_PARENT = tcj.JOB_ID
set tccpi.INV_STATE='CANCELLED'
where tcj.JOB_STATE ='CAN'
and tccpi.INV_STATE='NEW';

 

update clickargo2.T_CK_CT_DEBIT_NOTE tccdn
join clickargo2.T_CK_JOB_TRUCK tcjt on tccdn.DN_JOB_ID  = tcjt.JOB_ID
join clickargo2.T_CK_JOB tcj on tcjt.JOB_PARENT = tcj.JOB_ID
set tccdn.DN_STATE ='CANCELLED'
where tcj.JOB_STATE ='CAN'
and tccdn.DN_STATE  ='NEW';

