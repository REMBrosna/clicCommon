update clickargo2.T_CK_CT_CONTRACT
set con_payterm_co_ff=45
where CON_ID ='CKCTCONC000052';
 
update clickargo2.T_CK_CT_CONTRACT_CHARGE
set CONC_PLTFEE_AMT=4
where CONC_ID='CKCTCCHF000052';