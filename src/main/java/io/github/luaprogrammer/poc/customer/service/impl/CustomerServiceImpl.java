package io.github.luaprogrammer.poc.customer.service.impl;


import io.github.luaprogrammer.poc.customer.entity.Customer;
import io.github.luaprogrammer.poc.customer.enums.Doc_Type;
import io.github.luaprogrammer.poc.customer.repository.CustomerRepository;
import io.github.luaprogrammer.poc.customer.rest.dto.CustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.CustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerResponseDTO> findAllCustomers() {
        return customerRepository.findAll().stream().map(CustomerResponseDTO::convertForDto).collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    public CustomerResponseDTO saveCustomer(CustomerRequestDTO customer, String docType) {
        Doc_Type documetType = Objects.equals(docType, "cpf") ? Doc_Type.CPF : Doc_Type.CNPJ;
        Customer customerSaved = customerRepository.save(customer.convertForEntity(documetType));
        return CustomerResponseDTO.convertForDto(customerSaved);
    }

    @Override
    public Customer updateCustomer(UUID id, Customer customer) {
        Customer customerSaved = validateCustomer(id);
        BeanUtils.copyProperties(customer, customerSaved);
        return customerRepository.save(customerSaved);
    }

    @Override
    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }

    private Customer validateCustomer(UUID id) {
        Optional<Customer> customerById = findCustomerById(id);
        if (customerById.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return customerById.get();
    }
}
