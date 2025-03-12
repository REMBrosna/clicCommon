
select *
FROM clickargo2.T_CK_CT_TO_PAYMENT p, clickargo2.T_CK_PAYMENT_TXN pt
where p.TOP_REFERENCE = pt.PTX_ID
	and p.TOP_AMT != pt.PTX_AMOUNT ;
    
SELECT JSON_EXTRACT(TOP_JSON, '$.refID'), p.* 
FROM clickargo2.T_CK_CT_TO_PAYMENT p
where TOP_JSON is not null
	and JSON_EXTRACT(TOP_JSON, '$.refID') != TOP_REFERENCE ;

SELECT JSON_EXTRACT(TOP_JSON, '$.amount'), p.* 
FROM clickargo2.T_CK_CT_TO_PAYMENT p
where TOP_JSON is not null
	and JSON_EXTRACT(TOP_JSON, '$.amount') != TOP_AMT ;

