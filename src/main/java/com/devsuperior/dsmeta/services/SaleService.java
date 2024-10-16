package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.Projections.CustomerSaleMin;
import com.devsuperior.dsmeta.Projections.CustomerSellerMin;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<CustomerSaleMin> getreport(String minDate, String maxDate, String name, Pageable pageable) {

		LocalDate maxdate = (maxDate == null) ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(maxDate);
		LocalDate mindate = (minDate == null) ? maxdate.minusYears(1L) : LocalDate.parse(minDate);

		if (name == null || name.isEmpty()) {
			return repository.searchReport(mindate, maxdate, "", pageable);
		}

		return repository.searchReport(mindate, maxdate, name, pageable);

	}

	public List<CustomerSellerMin> getsummary(String minDate, String maxDate) {

		LocalDate maxdate = (maxDate == null) ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(maxDate);
		LocalDate mindate = (minDate == null) ? maxdate.minusYears(1L) : LocalDate.parse(minDate);

		return repository.searchSumary(mindate, maxdate);
	}
}
