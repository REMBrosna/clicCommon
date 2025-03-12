package com.guudint.clickargo.credit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guudint.clickargo.clicservice.controller.CkSvcEntityServiceController;
import com.guudint.clickargo.controller.AbstractCkController;
import com.guudint.clickargo.credit.dto.CkCredit;
import com.guudint.clickargo.credit.service.ICreditService;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityFilterResponse;
import com.vcc.camelone.common.controller.entity.EntityOrderBy;
import com.vcc.camelone.common.controller.entity.EntityOrderBy.ORDERED;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;

import io.jsonwebtoken.lang.Collections;

@RequestMapping(value = "/api/v1/clickargo/credit")
@CrossOrigin
@RestController
public class CreditController extends AbstractCkController {

    private static Logger log = Logger.getLogger(CkSvcEntityServiceController.class);

    @Autowired
    private ICreditService creditService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<Object> list(@RequestParam Map<String, String> params) {
        log.debug("list");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            if (Collections.isEmpty(params))
                throw new ParameterException("param params null");

            EntityFilterRequest filterRequest = new EntityFilterRequest();
            filterRequest.setDisplayStart(
                    params.containsKey("iDisplayStart") ? Integer.valueOf((String) params.get("iDisplayStart")) : -1);
            filterRequest.setDisplayLength(
                    params.containsKey("iDisplayLength") ? Integer.valueOf((String) params.get("iDisplayLength")) : -1);

            ArrayList<EntityWhere> whereList = new ArrayList<>();
            List<String> searches = params.keySet().stream().filter(x -> x.contains("sSearch_"))
                    .collect(Collectors.toList());

            for (int nIndex = 1; nIndex <= searches.size(); nIndex++) {
                String searchParam = params.get("sSearch_" + String.valueOf(nIndex));
                String valueParam = params.get("mDataProp_" + String.valueOf(nIndex));
                whereList.add(new EntityWhere(valueParam, searchParam));
            }

            filterRequest.setWhereList(whereList);
            Optional<String> opSortAttribute = Optional.ofNullable(params.get("mDataProp_0"));
            Optional<String> opSortOrder = Optional.ofNullable(params.get("sSortDir_0"));
            if (opSortAttribute.isPresent() && opSortOrder.isPresent()) {
                EntityOrderBy orderBy = new EntityOrderBy();
                orderBy.setAttribute(opSortAttribute.get());
                orderBy.setOrdered(opSortOrder.get().equalsIgnoreCase("desc") ? ORDERED.DESC : ORDERED.ASC);
                filterRequest.setOrderBy(orderBy);
            }

            EntityFilterResponse filterResponse = new EntityFilterResponse();
            if (!filterRequest.isValid()) {
                throw new ProcessingException("Invalid request: " + filterRequest.toJson());
            } else {
                List<CkCredit> entities = creditService.filterBy(filterRequest);

                filterResponse.setiTotalRecords(entities.size());
                filterResponse.setAaData(ArrayList.class.cast(entities));
                filterResponse.setiTotalDisplayRecords(filterRequest.getTotalRecords());
            }
            Optional<Object> opEntity = Optional.of(filterResponse);
            return new ResponseEntity<Object>(opEntity.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("find", e);
            serviceStatus.setStatus(STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-100, e));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable String id) {
        log.debug("findById");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(creditService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error("findById", e);
            serviceStatus.setStatus(STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-100, e));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ResponseEntity<Object> find(@RequestBody CkCredit request) {
        log.debug("find");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(creditService.find(request, getPrincipal()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("find", e);
            serviceStatus.setStatus(STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-100, e));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fetch", method = RequestMethod.POST)
    public ResponseEntity<Object> fetch(@RequestBody CkCredit request) {
        log.debug("find");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(creditService.fetch(request), HttpStatus.OK);
        } catch (Exception e) {
            log.error("find", e);
            serviceStatus.setStatus(STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-100, e));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/createCredit", method = RequestMethod.POST)
    public ResponseEntity<Object> createCredit(@RequestBody CkCredit request) {
        log.debug("createCredit");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(creditService.createCredit(request, getPrincipal()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("createCredit", e);
            serviceStatus.setStatus(STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-100, e));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/verifyCredit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> verifyCredit(@RequestBody CkCredit request, @PathVariable String id) {
        log.debug("verifyCredit");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(creditService.verifyCredit(request, id, getPrincipal()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("verifyCredit", e);
            serviceStatus.setStatus(STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-100, e));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/approveCredit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> approveCredit(@RequestBody CkCredit request, @PathVariable String id) {
        log.debug("approveCredit");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(creditService.approveCredit(request, id, getPrincipal()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("approveCredit", e);
            serviceStatus.setStatus(STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-100, e));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/rejectCredit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> rejectCredit(@RequestBody CkCredit request, @PathVariable String id) {
        log.debug("rejectCredit");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(creditService.rejectCredit(request, id, getPrincipal()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("rejectCredit", e);
            serviceStatus.setStatus(STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-100, e));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/suspendCredit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> suspendCredit(@RequestBody CkCredit request, @PathVariable String id) {
        log.debug("suspendCredit");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(creditService.suspendCredit(request, id, getPrincipal()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("suspendCredit", e);
            serviceStatus.setStatus(STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-100, e));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/unsuspendCredit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> unsuspendCredit(@RequestBody CkCredit request, @PathVariable String id) {
        log.debug("unsuspendCredit");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(creditService.unsuspendCredit(request, id, getPrincipal()),
                    HttpStatus.OK);
        } catch (Exception e) {
            log.error("unsuspendCredit", e);
            serviceStatus.setStatus(STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-100, e));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company")
    public ResponseEntity<?> getCompany() {
        log.debug("getCompany");
        return ResponseEntity.ok().body(creditService.findDistinctCompany());
    }

    @GetMapping("/service")
    public ResponseEntity<?> getService(@RequestParam String companyId) {
        log.debug("getService");
        return ResponseEntity.ok().body(creditService.findDistinctServiceByCompany(companyId));
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getDetail(@RequestParam String serviceType, @RequestParam String accnId) {
        log.debug("getDetail");
        try {
            CkCredit ckCredit = creditService.findByServiceTypeAndAccnId(serviceType, accnId);
            return ResponseEntity.ok().body(ckCredit);
        } catch (ParameterException e) {
            log.error(e.getMessage(), e);
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setErr(new ServiceError(-100, e));
            serviceStatus.setStatus(STATUS.EXCEPTION);
            return ResponseEntity.badRequest().body(serviceStatus);
        }
    }
}
