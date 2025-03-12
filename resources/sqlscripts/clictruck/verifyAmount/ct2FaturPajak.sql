
 select INV_NO ,INV_AMT, TI_INV_NO ,TI_AMT, (INV_AMT - TI_AMT)
from clickargo2.T_CK_CT_PLATFORM_INVOICE tccpi, clickargo2.T_CK_TAX_INVOICE tcti
where INV_NO = TI_INV_NO
	and INV_AMT != TI_AMT;
    
select *
from clickargo2.T_CK_CT_PLATFORM_INVOICE tccpi, clickargo2.T_CK_TAX_INVOICE tcti
where INV_NO = TI_INV_NO
	and INV_AMT != TI_AMT;
    
    
SELECT platInv.INV_NO, inv.TI_AMT, platInv.INV_AMT
FROM clickargo2.T_CK_TAX_INVOICE inv, clickargo2.T_CK_CT_PLATFORM_INVOICE platInv
where inv.TI_INV_NO = platInv.INV_NO
	and inv.TI_AMT != platInv.INV_AMT;