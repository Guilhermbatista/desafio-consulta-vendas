package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.Projections.CustomerSaleMin;
import com.devsuperior.dsmeta.Projections.CustomerSellerMin;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(nativeQuery = true, value = "SELECT tb_sales.id, tb_seller.name, tb_sales.date, tb_sales.amount "
			+ "FROM tb_sales " + "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id "
			+ "WHERE tb_sales.date BETWEEN :minDate AND :maxDate "
			+ "AND UPPER(tb_seller.name) LIKE CONCAT('%', UPPER(:name), '%') ")
	Page<CustomerSaleMin> searchReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate,
			@Param("name") String name, Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT tb_seller.name, SUM(tb_sales.amount) AS TOTAL " + "FROM tb_seller "
			+ "INNER JOIN tb_sales ON tb_sales.seller_id = tb_seller.id "
			+ "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " + "GROUP BY tb_seller.name; ")
	List<CustomerSellerMin> searchSumary(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate);

}
