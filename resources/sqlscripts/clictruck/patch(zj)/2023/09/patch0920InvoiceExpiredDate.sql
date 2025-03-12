
create table zBackup.T_CK_CT_PLATFORM_INVOICE_0920 SELECT * FROM clickargo2.T_CK_CT_PLATFORM_INVOICE;

update clickargo2.T_CK_CT_PLATFORM_INVOICE inv, clickargo2.T_CK_CT_DEBIT_NOTE dn
set INV_DT_DUE = DATE_ADD(INV_DT_ISSUE, INTERVAL datediff(DN_DT_DUE, DN_DT_ISSUE) DAY)
WHERE inv.INV_JOB_ID = dn.DN_JOB_ID
	and inv.INV_TO = DN_TO
	and datediff(INV_DT_DUE, INV_DT_ISSUE) < 7
    
    -- 340 rows