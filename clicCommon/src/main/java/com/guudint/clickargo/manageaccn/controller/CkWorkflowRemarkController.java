package com.guudint.clickargo.manageaccn.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guudint.clickargo.common.dto.CkWorkflowRemark;
import com.guudint.clickargo.common.model.TCkWorkflowRemark;
import com.guudint.clickargo.controller.AbstractCkController;
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
import com.vcc.camelone.common.service.entity.IEntityService;

import io.jsonwebtoken.lang.Collections;

@RequestMapping(value = "/api/v1/clickargo/ckWfRmk")
@CrossOrigin
@RestController
public class CkWorkflowRemarkController extends AbstractCkController {
    
    private static Logger log = Logger.getLogger(CkWorkflowRemarkController.class);

    @Autowired
	private IEntityService<TCkWorkflowRemark, String, CkWorkflowRemark> ckWorkflowRemarkService;

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
                List<CkWorkflowRemark> entities = ckWorkflowRemarkService.filterBy(filterRequest);
                
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
	public ResponseEntity<Object> getRemarkDetails(@PathVariable String id) {
		log.debug("getRemarkDetails");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			Optional<CkWorkflowRemark> opCkWorkflowRemark = Optional.ofNullable(ckWorkflowRemarkService.findById(id));
			if (!opCkWorkflowRemark.isPresent())
				throw new ProcessingException("account details not found");
			
			return ResponseEntity.ok(opCkWorkflowRemark.get());

		} catch (Exception ex) {
			log.error("getAccountDetails", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

}
