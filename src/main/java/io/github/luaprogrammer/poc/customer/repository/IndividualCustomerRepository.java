package io.github.luaprogrammer.poc.customer.repository;

import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IndividualCustomerRepository extends JpaRepository<IndividualCustomer, UUID> {
}
