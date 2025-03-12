update ctMigration.TMP_ACTIVE_COMPANY set COMPANY_NAME = ucase(COMPANY_NAME);
set foreign_key_checks = 0;

delete from ctMigration.TMP_COMPANY where ID not in ( select UUID from TMP_ACTIVE_COMPANY);
delete from ctMigration.TMP_USER where COMPANY_ID not in ( select UUID from TMP_ACTIVE_COMPANY);

delete from ctMigration.TMP_CONTRACT where `TO` not in ( select UUID from TMP_ACTIVE_COMPANY) 
	or CO_FF not in ( select UUID from TMP_ACTIVE_COMPANY);
-- delete from clickargo2.T_CORE_ACCN where ACCN_NAME not in (se)

delete FROM ctMigration.TMP_DRV where COMPANY  not in ( select UUID from TMP_ACTIVE_COMPANY);
delete FROM ctMigration.TMP_TRUCK where COMPANY  not in ( select UUID from TMP_ACTIVE_COMPANY);

delete FROM ctMigration.TMP_CREDIT where COMPANY  not in ( select UUID from TMP_ACTIVE_COMPANY);