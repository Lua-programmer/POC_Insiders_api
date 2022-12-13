package io.github.luaprogrammer.poc.customer.repository;

import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface IndividualCustomerRepository extends JpaRepository<IndividualCustomer, UUID> {
    @Query("SELECT ic FROM IndividualCustomer ic WHERE ic.name LIKE %:name% ")
    List<IndividualCustomer> findIndividualCustomerByNameLikeIgnoreCase(@RequestParam("name") String name);
}
