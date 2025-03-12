
set sql_safe_updates = 0;

update clickargo2.T_CORE_ACCN_CONFIG tcac
set ACFG_VAL='Dyah'
where ACFG_VAL='Beny'
and ACFG_KEY='ACCN_MGR';