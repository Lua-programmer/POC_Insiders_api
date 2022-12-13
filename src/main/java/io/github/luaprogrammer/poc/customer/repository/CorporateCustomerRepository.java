package io.github.luaprogrammer.poc.customer.repository;

import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CorporateCustomerRepository extends JpaRepository<CorporateCustomer, UUID> {


//    @Query("SELECT c FROM Customer c WHERE c.name LIKE '%:name%' ")
    List<CorporateCustomer> findAllByNameLikeIgnoreCase(@Param("name") String name);
}
