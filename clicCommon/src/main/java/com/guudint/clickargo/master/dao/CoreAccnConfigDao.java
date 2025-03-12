package com.guudint.clickargo.master.dao;

import com.vcc.camelone.ccm.model.TCoreAccnConfig;
import com.vcc.camelone.ccm.model.TCoreAccnConfigId;

public interface CoreAccnConfigDao extends com.vcc.camelone.ccm.dao.CoreAccnConfigDao {

    TCoreAccnConfig getByIdAndStatus(TCoreAccnConfigId id, Character status);

}
