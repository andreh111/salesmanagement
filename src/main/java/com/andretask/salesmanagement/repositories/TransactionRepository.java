package com.andretask.salesmanagement.repositories;

import com.andretask.salesmanagement.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
