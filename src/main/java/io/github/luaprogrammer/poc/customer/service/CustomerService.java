package io.github.luaprogrammer.poc.customer.service;

import io.github.luaprogrammer.poc.customer.entity.Customer;
import io.github.luaprogrammer.poc.customer.rest.dto.CustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.CustomerResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerResponseDTO> findAllCustomers();

    Optional<Customer> findCustomerById(UUID id);

    CustomerResponseDTO saveCustomer(CustomerRequestDTO customer, String docType);

    Customer updateCustomer(UUID id, Customer customer);

    void deleteCustomer(UUID id);
}
