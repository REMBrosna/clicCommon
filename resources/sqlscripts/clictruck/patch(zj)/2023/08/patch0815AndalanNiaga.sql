
-- confirm from Dhimas:
-- CO : PT. ANDALAN NIAGA INDONESIA LOGISTIC; Payterm : 30 ; Pf: 2.5%

create table zBackup.T_CK_CT_CONTRACT_0815 select * FROM clickargo2.T_CK_CT_CONTRACT c;
create table zBackup.T_CK_CT_CONTRACT_CHARGE_0815 select * FROM clickargo2.T_CK_CT_CONTRACT_CHARGE ccTo;

update T_CK_CT_CONTRACT set CON_PAYTERM_CO_FF = 30 where CON_CO_FF = 'CK0336' and CON_TO = 'CK0337';

update T_CK_CT_CONTRACT_CHARGE ccFF join
	(select * FROM clickargo2.T_CK_CT_CONTRACT ) c
set CONC_PLTFEE_AMT = 2.5
where CON_CO_FF = 'CK0336' and CON_TO = 'CK0337'
	and c.CON_CHARGE_CO_FF = ccFF.CONC_ID;