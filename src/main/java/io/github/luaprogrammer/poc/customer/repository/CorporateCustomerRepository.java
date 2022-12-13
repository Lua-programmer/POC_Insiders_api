package io.github.luaprogrammer.poc.customer.repository;

import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface CorporateCustomerRepository extends JpaRepository<CorporateCustomer, UUID> {


    @Query("SELECT cc FROM CorporateCustomer cc WHERE cc.name LIKE %:name% ")
    List<CorporateCustomer> findCorporateCustomersByNameIsLikeIgnoreCase(@RequestParam("name") String name);
}
