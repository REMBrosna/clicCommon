package com.guudint.clickargo.master.dao.impl;

import com.guudint.clickargo.master.dao.CoreAccnConfigDao;
import com.vcc.camelone.ccm.model.TCoreAccnConfig;
import com.vcc.camelone.ccm.model.TCoreAccnConfigId;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Primary
public class CoreAccnConfigDaoImpl extends GenericDaoImpl<TCoreAccnConfig, TCoreAccnConfigId> implements CoreAccnConfigDao {

    @Override
    @Transactional(readOnly = true)
    public TCoreAccnConfig getByIdAndStatus(TCoreAccnConfigId id, Character status) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TCoreAccnConfig.class);
            dc.add(Restrictions.eq("id", id));
            dc.add(Restrictions.eq("acfgStatus", status));
            return getOne(dc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
