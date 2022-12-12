package io.github.luaprogrammer.poc.customer.service;

import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.request.CorporateCustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.CorporateCustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.CustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.request.IndividualCustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.IndividualCustomerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomerService {
    Page<CustomerResponseDTO> findAllCorporateCustomer(Pageable pageable);

    CustomerResponseDTO findCorporateCustomerById(UUID id);

    CustomerResponseDTO saveCorporateCustomer(CorporateCustomerRequestDTO customer);

    CustomerResponseDTO addAddressCorporateCustomer(UUID id, AddressRequestDTO addressRequest) throws Exception;

    CustomerResponseDTO updateCorporateCustomer(UUID id, CorporateCustomerRequestDTO customer);

    CorporateCustomerResponseDTO updateAddressCorporateCustomer(UUID id, UUID addressId, AddressRequestDTO addressRequest) throws Exception;

    void deleteCorporateCustomer(UUID id);

    void deleteAddressCorporateCustomer(UUID customerId, UUID addressId);

    Page<CustomerResponseDTO> findAllIndividualCustomer(Pageable pageable);

    CustomerResponseDTO findIndividualCustomerById(UUID id);

    CustomerResponseDTO saveIndividualCustomer(IndividualCustomerRequestDTO customer);

    CustomerResponseDTO addAddressIndividualCustomer(UUID id, AddressRequestDTO addressRequest) throws Exception;

    CustomerResponseDTO updateIndividualCustomer(UUID id, IndividualCustomerRequestDTO customer);

    IndividualCustomerResponseDTO updateAddressIndividualCustomer(UUID id, UUID addressId, AddressRequestDTO addressRequest) throws Exception;

    void deleteIndividualCustomer(UUID id);

    void deleteAddressIndividualCustomer(UUID customerId, UUID addressId);
}
