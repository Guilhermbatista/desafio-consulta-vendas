package com.devsuperior.dsmeta.Projections;

import java.time.LocalDate;

public interface CustomerSaleMin {

    Long getId();
    String getName(); 
    LocalDate getDate();
    Double getAmount();
}
