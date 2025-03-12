package com.guudint.clickargo.clicservice.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.clicservice.dao.CkSvcActionMaskDao;
import com.guudint.clickargo.clicservice.model.TCkSvcActionMask;
import com.guudint.clickargo.common.enums.JobActions;

public class CkSvcActionMaskService {

    @Autowired
    private CkSvcActionMaskDao ckSvcActionMaskDao;

    private static Logger LOG = Logger.getLogger(CkSvcActionMaskService.class);

    private static List<String> STATE = Arrays.asList("-", JobActions.APPROVE_BILL.name(),
            JobActions.VERIFY_BILL.name(), JobActions.BILLJOB.name(), JobActions.STOP.name(), JobActions.START.name(),
            JobActions.ASSIGN.name(), JobActions.ACCEPT.name(), JobActions.REJECT.name(), JobActions.WITHDRAW.name(),
            JobActions.SUBMIT.name(),JobActions.DELETE.name());

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<String> getActions(String appsCode, String accnType, String role, String scvType, List<String> state) {
        List<String> actions = new ArrayList<>();
        try {
            List<Integer> bitMasks = new ArrayList<>();
            for (String status : state) {
                Optional<TCkSvcActionMask> optTckSvcActionMasek = ckSvcActionMaskDao
                        .findByAppsCodeAndAccnTypeAndRoleAndScvTypeAndStateAndStatus(appsCode, accnType, role, scvType,
                                status, 'A');
                if (optTckSvcActionMasek.isPresent()) {
                    bitMasks.add(optTckSvcActionMasek.get().getSamMask());
                } else {
                    bitMasks.add(0);
                }
            }
            int result = 4095;
            for (Integer bitMask : bitMasks) {
                result = result & bitMask;
            }
            String resultBitMask = String.format("%12s", Integer.toBinaryString(result)).replaceAll(" ", "0");
            for (int i = 0; i < resultBitMask.length(); i++) {
                if (resultBitMask.charAt(i) == '1') {
                    actions.add(STATE.get(i));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return actions;
    }
}
