SET FOREIGN_KEY_CHECKS=0;


drop table  if exists  T_CK_CP_ASSIGNMENT_VA  ;
drop table  if exists  T_CK_CP_AUDIT  ;
drop table  if exists  T_CK_CP_CLIENT_TXN  ;
drop table  if exists  T_CK_CP_CREATION_VA  ;
drop table  if exists  T_CK_CP_DISBURSEMENT_NON_VA  ;
drop table  if exists  T_CK_CP_DISBURSEMENT_VA  ;
drop table  if exists  T_CK_CP_LEDGER  ;
drop table  if exists  T_CK_CP_NODE  ;
drop table  if exists  T_CK_CP_NODE_CONFIG  ;
drop table  if exists  T_CK_CP_PAYMENT_VA  ;
drop table  if exists  T_CK_CP_SESSION  ;
drop table  if exists  T_CK_CP_TXN  ;
drop table  if exists  T_CK_CP_MST_AUDIT_TYPE  ;
drop table  if exists  T_CK_CP_MST_NODE_STATE  ;
drop table  if exists  T_CK_CP_MST_TXN_STATE  ;
drop table  if exists  T_CK_CP_MST_TXN_TYPE  ;

drop table if exists T_CK_DO_BL  ;
drop table if exists T_CK_DO_CLAIM  ;
drop table if exists T_CK_DO_CNT  ;
drop table if exists T_CK_DO_DAILY_RATE  ;
drop table if exists T_CK_DO_DOC_ATTACH  ;
drop table if exists T_CK_DO_DOC_HISTORY  ;
drop table if exists T_CK_DO_ESTAMP_DOC  ;
drop table if exists T_CK_DO_EXCHANGE_RATE  ;
drop table if exists T_CK_DO_EXT  ;
drop table if exists T_CK_DO_EXT_CNT  ;
drop table if exists T_CK_DO_EXT_MST_CNT_CATEGORY  ;
drop table if exists T_CK_DO_EXT_TARIFF_BAND  ;
drop table if exists T_CK_DO_EXT_TARIFF_CHARGE  ;
drop table if exists T_CK_DO_INVOICE  ;
drop table if exists T_CK_DO_PAYMENT  ;
drop table if exists T_CK_DO_PRINTOUT  ;
drop table if exists T_CK_DO_SAP  ;
drop table if exists T_CK_DO_SAP_PROFIT_CENTER_STATE  ;
drop table if exists T_CK_DO_SHIPPING_LINE_JOURNAL  ;
drop table if exists T_CK_DO_VESSEL_VOYAGE  ;
drop table if exists T_CK_DO  ;
drop table if exists T_CK_CNT ;

drop table  if exists  T_CK_CD_BC33;
drop table  if exists  T_CK_CD_BC33_BANK;
drop table  if exists  T_CK_CD_BC33_DOC;
drop table  if exists  T_CK_CD_BC33_GOODS;
drop table  if exists  T_CK_CD_BC33_GOODS_DOCUMENT;
drop table  if exists  T_CK_CD_BC33_GOODS_ORIGIN;
drop table  if exists  T_CK_CD_BC33_GOODS_OWNER;
drop table  if exists  T_CK_CD_BC33_GOODS_TARIF;

drop table  if exists  T_CK_CD_CLP;
drop table  if exists  T_CK_CD_FF_CO;
drop table  if exists  T_CK_CD_MST_BC33_STATE;
drop table  if exists  T_CK_CD_MST_CLP_STATE;

DROP TABLE if exists  T_TMP_USER;
DROP TABLE if exists  dsv_xml_shipment;
DROP TABLE if exists  T_CK_MSCGW_AUDIT;

drop table if exists T_CK_MST_DO_DOC_ATTACH_STATE  ;
drop table if exists T_CK_MST_DO_PARTY_TYPE  ;
drop table if exists T_CK_MST_DO_STATE  ;

drop view if exists CONFIRMED_DO_JOBS_VIEW  ;
drop view if exists DISBURSEMENT_DO_JOBS_VIEW  ;

SET FOREIGN_KEY_CHECKS=1;



flush tables;