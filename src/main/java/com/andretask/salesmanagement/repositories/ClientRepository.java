package com.andretask.salesmanagement.repositories;

import com.andretask.salesmanagement.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByMobile(String mobile);
}

