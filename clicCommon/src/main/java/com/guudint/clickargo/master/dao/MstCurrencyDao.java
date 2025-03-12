package com.guudint.clickargo.master.dao;

import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.master.model.TMstCurrency;

public interface MstCurrencyDao extends GenericDao<TMstCurrency, String> {

    TMstCurrency getByCodeAndStatus(String ccyCode, Character status);

}
