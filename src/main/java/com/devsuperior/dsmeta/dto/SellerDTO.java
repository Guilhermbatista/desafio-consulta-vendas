package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.Projections.CustomerSellerMin;

public class SellerDTO {

	private String name;

	private Double amount;

	public SellerDTO(String name, Double amount) {
		this.name = name;
		this.amount = amount;
	}

	public SellerDTO(CustomerSellerMin entity) {
		name = entity.getName();
		amount = entity.getTotal();
	}

	public String getName() {
		return name;
	}

	public Double getAmount() {
		return amount;
	}

}
