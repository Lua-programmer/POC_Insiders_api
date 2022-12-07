package io.github.luaprogrammer.poc.address.repository;


import io.github.luaprogrammer.poc.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Address findByCep(String cep);
}
