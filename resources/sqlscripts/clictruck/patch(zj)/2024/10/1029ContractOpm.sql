
-- update from 5% to 2%

update clickargo2.T_CK_CT_CONTRACT_CHARGE
set CONC_PLTFEE_AMT = '2', CONC_DT_LUPD = sysdate()
where CONC_ID = 
(SELECT CON_CHARGE_TO FROM clickargo2.T_CK_CT_CONTRACT
where CON_TO = 'CK0010')
	and CONC_PLTFEE_AMT = 5;