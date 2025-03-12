SET FOREIGN_KEY_CHECKS=0;

truncate table T_CK_ACCN  ;
truncate table T_CK_ACCN_ATT  ;
truncate table T_CK_ACCN_CONFIG_EXT  ;
truncate table T_CK_ACCN_OPM  ;

truncate table T_CK_CREDIT  ;
truncate table T_CK_CREDIT_JOURNAL  ;
truncate table T_CK_CREDIT_REQUEST  ;
truncate table T_CK_CREDIT_SUMMARY  ;
truncate table T_CK_CREDIT_SUMMARY_MONTH  ;

truncate table T_CK_CS_ACCN  ;
truncate table T_CK_CT_ADD_ATTR_LIST  ;
truncate table T_CK_CT_CHASSIS  ;
truncate table T_CK_CT_CO2X  ;
truncate table T_CK_CT_CONTACT_DETAIL  ;
truncate table T_CK_CT_CONTRACT  ;
truncate table T_CK_CT_CONTRACT_CHARGE  ;
truncate table T_CK_CT_CONTRACT_REQ  ;
truncate table T_CK_CT_CON_ADD_ATTR  ;
truncate table T_CK_CT_DEBIT_NOTE  ;
truncate table T_CK_CT_DEBIT_NOTE_ITEM  ;
truncate table T_CK_CT_DEPT  ;
truncate table T_CK_CT_DEPT_USR  ;
truncate table T_CK_CT_DEPT_VEH  ;
truncate table T_CK_CT_DRV  ;
truncate table T_CK_CT_FF_CO  ;
truncate table T_CK_CT_JOB_TERM  ;
truncate table T_CK_CT_JOB_TERM_REQ  ;
truncate table T_CK_CT_JOB_TRIP_DELIVERY  ;
truncate table T_CK_CT_LOCATION  ;


truncate table T_CK_CT_PARTY  ;
truncate table T_CK_CT_PAYMENT  ;
truncate table T_CK_CT_PLATFORM_INVOICE  ;
truncate table T_CK_CT_PLATFORM_INVOICE_ITEM  ;
-- truncate table T_CK_CT_POD_TEMPALTE  ;
truncate table T_CK_CT_RATE_TABLE  ;
truncate table T_CK_CT_RATE_TABLE_REMARK  ;
truncate table T_CK_CT_RENTAL_APP  ;
truncate table T_CK_CT_RENTAL_TABLE  ;
truncate table T_CK_CT_RENTAL_VEH  ;
truncate table T_CK_CT_SAGE  ;
truncate table T_CK_CT_SAGE_TAX  ;


truncate table T_CK_CT_SHIPMENT  ;
truncate table T_CK_CT_TO_INVOICE  ;
truncate table T_CK_CT_TO_PAYMENT  ;
truncate table T_CK_CT_TRACK_DEVICE  ;
truncate table T_CK_CT_TRACK_DISTANCE  ;
truncate table T_CK_CT_TRACK_IMEI_STATUS  ;
truncate table T_CK_CT_TRACK_LOC  ;
truncate table T_CK_CT_TRACK_LOC_ITEM  ;
truncate table T_CK_CT_TRACK_NOTIFY  ;
truncate table T_CK_CT_TRIP  ;
truncate table T_CK_CT_TRIP_ATTACH  ;
truncate table T_CK_CT_TRIP_CARGO_FM  ;
truncate table T_CK_CT_TRIP_CARGO_MM  ;
truncate table T_CK_CT_TRIP_CHARGE  ;
truncate table T_CK_CT_TRIP_DO  ;
truncate table T_CK_CT_TRIP_DO_ATTACH  ;
truncate table T_CK_CT_TRIP_LOCATION  ;
truncate table T_CK_CT_TRIP_RATE  ;
truncate table T_CK_CT_TRIP_REIMBURSEMENT  ;
truncate table T_CK_CT_VEH  ;
truncate table T_CK_CT_VEH_EXT  ;
truncate table T_CK_CT_VEH_MLOG  ;


truncate table T_CK_ESTAMP_DOC  ;
truncate table T_CK_ESTAMP_TXN  ;


truncate table T_CK_JOB  ;
truncate table T_CK_JOB_ATTACH  ;
truncate table T_CK_JOB_DO_CLAIM  ;
truncate table T_CK_JOB_DO_EXT  ;
truncate table T_CK_JOB_QUERY  ;
truncate table T_CK_JOB_REJECT  ;
truncate table T_CK_JOB_REMARKS  ;
truncate table T_CK_JOB_TRUCK  ;
truncate table T_CK_JOB_TRUCK_ADD_ATTR  ;
truncate table T_CK_JOB_TRUCK_EXT  ;
truncate table T_CK_JOB_TRUCK_POD  ;
truncate table T_CK_JOB_UPLOAD  ;

-- truncate table T_CK_MSCGW_AUDIT  ;


truncate table T_CK_PAYMENT_AUDIT  ;
truncate table T_CK_PAYMENT_LEDGER  ;
truncate table T_CK_PAYMENT_TERMS  ;
truncate table T_CK_PAYMENT_TXN  ;
truncate table T_CK_PAYMENT_TXN_INVOICE  ;
truncate table T_CK_PAYMENT_TXN_LOG  ;


truncate table T_CK_RECORD_DATE  ;
truncate table T_CK_SAGE_INTEGRATION  ;
truncate table T_CK_SEQUENCE_NO  ;
truncate table T_CK_SUSPENSION_LOG  ;


truncate table T_CK_CT_SHELL_BATCH_WINDOW  ;
truncate table T_CK_CT_SHELL_CARD  ;
truncate table T_CK_CT_SHELL_CARD_TRUCK  ;
truncate table T_CK_CT_SHELL_INVOICE  ;
truncate table T_CK_CT_SHELL_INVOICE_ITEM  ;
truncate table T_CK_CT_SHELL_KIOSK  ;
truncate table T_CK_CT_SHELL_TXN  ;

truncate table T_CK_TASK  ;
truncate table T_CK_TASK_ATTACH  ;
truncate table T_CK_TAX_INVOICE  ;
truncate table T_CK_TAX_REPORT  ;
truncate table T_CK_UNGRANT_ACCOUNT_EX  ;
truncate table T_CK_UNGRANT_USER_EX  ;
truncate table T_CK_USR_EXT  ;

truncate table T_CK_ESTAMP_TXN;
-- truncate table T_CK_MSCGW_AUDIT;
truncate table T_CK_JOB_TRUCK_EXT;
truncate table T_CK_CT_SHIPMENT;

truncate table T_CORE_EXCEPTION	;
truncate table T_CORE_SCHEDULE_JOBLOG;
truncate table T_CORE_NOTIFICATION_LOG;
truncate table T_CORE_SESSION  ;
truncate table T_CORE_AUDITLOG  ;

truncate table T_CORE_USR_ROLE  ;

truncate table T_CK_CT_DEPT;
truncate table T_CK_CT_DEPT_USR;
truncate table T_CK_CT_DEPT_VEH;
truncate table T_CORE_USR_ROLE  ;
truncate table T_CORE_USR  ;
truncate table T_CORE_ACCN  ;


truncate table T_CK_WORKFLOW_REMARK;
truncate table T_CK_TAX_REPORT;
truncate table T_CK_TAX_INVOICE;
truncate table T_CK_SVC_JOURNAL;
truncate table T_CK_SVC_AUTH;
truncate table T_CK_ESTAMP_TXN;
truncate table T_CK_TAX_REPORT;

SET FOREIGN_KEY_CHECKS=1;



flush tables;
