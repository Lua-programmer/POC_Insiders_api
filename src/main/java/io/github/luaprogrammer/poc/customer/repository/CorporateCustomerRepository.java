package io.github.luaprogrammer.poc.customer.repository;

import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CorporateCustomerRepository extends JpaRepository<CorporateCustomer, UUID> {
}
