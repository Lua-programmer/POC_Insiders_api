package io.github.luaprogrammer.poc.address.repository;


import io.github.luaprogrammer.poc.address.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Override
    Page<Address> findAll(Pageable pageable);

    Address findByCep(String cep);
}
