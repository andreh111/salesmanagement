package com.andretask.salesmanagement.repositories;

import com.andretask.salesmanagement.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
