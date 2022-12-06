package io.github.luaprogrammer.poc.customer.service;

import io.github.luaprogrammer.poc.customer.rest.dto.CorporateCustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.CustomerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomerService {
    Page<CustomerResponseDTO> findAllCorporateCustomer(Pageable pageable);

    CustomerResponseDTO findCorporateCustomerById(UUID id);

    CustomerResponseDTO saveCorporateCustomer(CorporateCustomerRequestDTO customer);

    CustomerResponseDTO updateCorporateCustomer(UUID id, CorporateCustomerRequestDTO customer);

    void deleteCorporateCustomer(UUID id);
}
