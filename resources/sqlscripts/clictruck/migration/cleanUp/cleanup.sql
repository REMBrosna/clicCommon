delete from clickargo2.T_CORE_ACCN_CONFIG where ACFG_ACCNID not in ('GLI');

delete from clickargo2.T_CK_SVC_SUB where SUB_SUBSCRIBER not in ('GLI');



SELECT * FROM clickargo2.T_CK_SVC_SUB;