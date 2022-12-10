package io.github.luaprogrammer.poc.customer.service;

import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.request.CorporateCustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.CustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.request.IndividualCustomerRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomerService {
    Page<CustomerResponseDTO> findAllCorporateCustomer(Pageable pageable);

    CustomerResponseDTO findCorporateCustomerById(UUID id);

    CustomerResponseDTO saveCorporateCustomer(CorporateCustomerRequestDTO customer);

    CustomerResponseDTO updateCorporateCustomer(UUID id, CorporateCustomerRequestDTO customer);

    CustomerResponseDTO addAddressCorporateCustomer(UUID id, AddressRequestDTO addressRequest) throws Exception;
//    CustomerResponseDTO addAddressIndividualCustomer(UUID id, AddressRequestDTO addressRequest) throws Exception;

    void deleteCorporateCustomer(UUID id);

    Page<CustomerResponseDTO> findAllIndividualCustomer(Pageable pageable);

    CustomerResponseDTO findIndividualCustomerById(UUID id);

    CustomerResponseDTO saveIndividualCustomer(IndividualCustomerRequestDTO customer);

    CustomerResponseDTO updateIndividualCustomer(UUID id, IndividualCustomerRequestDTO customer);

    void deleteIndividualCustomer(UUID id);
}
