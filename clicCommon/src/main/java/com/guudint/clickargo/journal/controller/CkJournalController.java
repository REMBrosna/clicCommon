package com.guudint.clickargo.journal.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guudint.clickargo.controller.AbstractCkController;
import com.guudint.clickargo.journal.dto.CkCreditJournal;
import com.guudint.clickargo.journal.service.IJournalService;
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

@RequestMapping(value = "/api/v1/clickargo/journal")
@CrossOrigin
@RestController
public class CkJournalController extends AbstractCkController {
    
    private static Logger log = Logger.getLogger(CkJournalController.class);

    @Autowired
    private IJournalService journalService;

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<Object> list(@RequestParam Map<String, String> params) {
		log.debug("list");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            if (Collections.isEmpty(params))
                throw new ParameterException("param params null");

            EntityFilterRequest filterRequest = new EntityFilterRequest();
            filterRequest.setDisplayStart(params.containsKey("iDisplayStart") ? Integer.valueOf((String)params.get("iDisplayStart")) : -1);
            filterRequest.setDisplayLength(params.containsKey("iDisplayLength") ? Integer.valueOf((String)params.get("iDisplayLength")) : -1);
            
            ArrayList<EntityWhere> whereList = new ArrayList<>();
            List<String> searches = params.keySet().stream().filter(x -> x.contains("sSearch_")).collect(Collectors.toList());
            
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
                List<CkCreditJournal> entities = journalService.filterBy(filterRequest);
                
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
            return new ResponseEntity<Object>(journalService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error("findById", e);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public ResponseEntity<Object> reserve(@RequestBody CkCreditJournal request) {
        log.debug("reserve");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(journalService.reserve(request, getPrincipal()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("reserve", e);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/reverse", method = RequestMethod.POST)
    public ResponseEntity<Object> reverse(@RequestBody CkCreditJournal request) {
        log.debug("reverse");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(journalService.reverse(request, getPrincipal()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("reverse", e);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/utilize", method = RequestMethod.POST)
    public ResponseEntity<Object> utilize(@RequestBody CkCreditJournal request) {
        log.debug("utilize");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(journalService.utilize(request, getPrincipal()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("utilize", e);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseEntity<Object> pay(@RequestBody CkCreditJournal request) {
        log.debug("pay");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(journalService.pay(request, null), HttpStatus.OK);
        } catch (Exception e) {
            log.error("pay", e);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
