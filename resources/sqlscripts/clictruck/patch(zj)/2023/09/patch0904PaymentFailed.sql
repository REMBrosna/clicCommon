

update clickargo2.T_CK_PAYMENT_TXN set PTX_MERCHANT_BANK=UCASE('MANDIRI'), PTX_PAYMENT_STATE = 'APP_BILL' 
where ptx_id='TXNDO5883033385641074';

update clickargo2.T_CK_CT_TO_PAYMENT set top_json='{
  "node": "DNO",
  "serviceID": "CLICTRUCK",
  "refID": "TXNDO5883033385641074",
  "callBack": null,
  "senderAccount": "006600687575",
  "senderCardNumber": null,
  "va": "1200033993390",
  "amount": 29172250,
  "beneficiaryAccount": "1200033993390",
  "bank": "MANDIRI",
  "ccy": "IDR"
}', TOP_STATUS = 'N' 
where TOP_REFERENCE = 'TXNDO5883033385641074';