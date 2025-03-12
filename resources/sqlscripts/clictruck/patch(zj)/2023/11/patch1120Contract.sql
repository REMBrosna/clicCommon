
update clickargo2.T_CK_CT_CONTRACT
set con_payterm_co_ff=60
where CON_ID='CKCTCONC000055';
 
update clickargo2.T_CK_CT_CONTRACT_CHARGE
set CONC_PLTFEE_AMT=6
where CONC_ID='CKCTCCHT000055';