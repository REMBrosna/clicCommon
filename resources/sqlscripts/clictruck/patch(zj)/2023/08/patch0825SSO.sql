
delete from T_CK_SVC_SUB;

select * from T_CK_SVC_SUB;

insert into T_CK_SVC_SUB(SUB_ID, SUB_TYPE, SUB_STATE, SUB_SUBSCRIBER, SUB_DT_START, SUB_DT_VALID, SUB_AUTO_RENEW
	, SUB_UID_VERIFY, SUB_DT_VERIFY, SUB_UID_APPROVE, SUB_DT_APPROVE, SUB_STATUS, SUB_DT_CREATE, SUB_UID_CREATE)
    select concat(ACCN_ID, round(rand()*1000)), 'CLICTRUCK', 'APR', ACCN_ID, '2023-01-01', '2033-01-01','N'
    , 'sys', sysdate(), 'sys', sysdate(), 'A', sysdate(), 'sys' from T_CORE_ACCN;