with approveDateTab as ( SELECT TOP_ID, TOP_DT_TRANSFER,  PTX_SVC_REF
	, (select min(RCD_DT_BILL_APPROVED) 
		from clickargo2.T_CK_RECORD_DATE d
		where d.RCD_ID in (
			SELECT job.JOB_DATES
			FROM clickargo2.T_CK_JOB_TRUCK jt, clickargo2.T_CK_JOB job
			where jt.JOB_PARENT = job.JOB_ID
				and locate(jt.JOB_ID,PTX_SVC_REF) > 0)
		) approveDate 
FROM clickargo2.T_CK_CT_TO_PAYMENT p, clickargo2.T_CK_PAYMENT_TXN t
where p.TOP_REFERENCE = t.PTX_ID
	and TOP_STATUS != 'S'
	and TOP_DT_TRANSFER > sysdate()
order by TOP_DT_CREATE desc )

update clickargo2.T_CK_CT_TO_PAYMENT p, approveDateTab
	set p.TOP_DT_TRANSFER = DATE_ADD(approveDateTab.approveDate, INTERVAL 7 DAY)
	where p.TOP_ID = approveDateTab.TOP_ID