package com.devsuperior.dsmeta.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(
			@RequestParam(name = "minDate", required = false) @DateTimeFormat(iso = ISO.DATE) String minDate,
			@RequestParam(name = "maxDate", required = false) @DateTimeFormat(iso = ISO.DATE) String maxDate,
			@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable) {

		return ResponseEntity.ok(service.getreport(minDate, maxDate, name, pageable).map(x -> new SaleMinDTO(x)));
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SellerDTO>> getSummary(
			@RequestParam(name = "minDate", required = false) @DateTimeFormat(iso = ISO.DATE) String minDate,
			@RequestParam(name = "maxDate", required = false) @DateTimeFormat(iso = ISO.DATE) String maxDate) {

		return ResponseEntity.ok(
				service.getsummary(minDate, maxDate).stream().map(x -> new SellerDTO(x)).collect(Collectors.toList()));
	}
}
