package com.guudint.clickargo.credit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guudint.clickargo.credit.service.impl.CreditHistoryListingService;
import com.guudint.clickargo.journal.dto.CkCreditJournal;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityFilterResponse;
import com.vcc.camelone.common.controller.entity.EntityOrderBy;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;

@RequestMapping(value = "/api/v1/clickargo/credit/history")
@CrossOrigin
@RestController
public class CreditHistoryController {

	@Autowired
	private CreditHistoryListingService creditHistoryListingService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<Object> getEntitiesBy(@RequestParam Map<String, String> params) {

		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			Optional<Object> opEntity = this.getEntitiesByProxy(params);
			return ResponseEntity.ok(opEntity.get());
		} catch (Exception ex) {
			serviceStatus.setStatus(ServiceStatus.STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	protected Optional<Object> getEntitiesByProxy(Map<String, String> params)
			throws ParameterException, ProcessingException {

		try {

			if (params != null && params.isEmpty())
				throw new ParameterException("param params null or empty");

			EntityFilterRequest filterRequest = new EntityFilterRequest();
			// start and length parameter extraction
			filterRequest.setDisplayStart(
					params.containsKey("iDisplayStart") ? Integer.valueOf(params.get("iDisplayStart")).intValue() : -1);
			filterRequest.setDisplayLength(
					params.containsKey("iDisplayLength") ? Integer.valueOf(params.get("iDisplayLength")).intValue()
							: -1);
			// where parameters extraction
			ArrayList<EntityWhere> whereList = new ArrayList<>();
			List<String> searches = params.keySet().stream().filter(x -> x.contains("sSearch_"))
					.collect(Collectors.toList());
			for (int nIndex = 1; nIndex <= searches.size(); nIndex++) {
				String searchParam = params.get("sSearch_" + nIndex);
				String valueParam = params.get("mDataProp_" + nIndex);
				whereList.add(new EntityWhere(valueParam, searchParam));
			}

			filterRequest.setWhereList(whereList);
			// order by parameters extraction
			Optional<String> opSortAttribute = Optional.ofNullable(params.get("mDataProp_0"));
			Optional<String> opSortOrder = Optional.ofNullable(params.get("sSortDir_0"));
			if (opSortAttribute.isPresent() && opSortOrder.isPresent()) {
				EntityOrderBy orderBy = new EntityOrderBy();
				orderBy.setAttribute(opSortAttribute.get());
				orderBy.setOrdered(opSortOrder.get().equalsIgnoreCase("desc") ? EntityOrderBy.ORDERED.DESC
						: EntityOrderBy.ORDERED.ASC);
				filterRequest.setOrderBy(orderBy);
			}

			if (!filterRequest.isValid()) {
				throw new ProcessingException("Invalid request: " + filterRequest.toJson());
			}
			List<CkCreditJournal> en = creditHistoryListingService.filterBy(filterRequest);
			List<Object> entities = List.class.cast(en);
			EntityFilterResponse filterResponse = new EntityFilterResponse();
			filterResponse.setiTotalRecords(entities.size());
			filterResponse.setiTotalDisplayRecords(filterRequest.getTotalRecords());
			filterResponse.setAaData((ArrayList<Object>) entities);
			return Optional.of(filterResponse);
		} catch (ParameterException | ProcessingException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ProcessingException(ex);
		}
	}
}
