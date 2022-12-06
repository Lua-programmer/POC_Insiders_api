package io.github.luaprogrammer.poc.customer.service.impl;


import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import io.github.luaprogrammer.poc.customer.repository.CorporateCustomerRepository;
import io.github.luaprogrammer.poc.customer.repository.IndividualCustomerRepository;
import io.github.luaprogrammer.poc.customer.rest.dto.CorporateCustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.CustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.CustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CorporateCustomerRepository cRepository;
    private final IndividualCustomerRepository iRepository;

    @Override
    public Page<CustomerResponseDTO> findAllCorporateCustomer(Pageable pageable) {
        return cRepository.findAll(pageable).map(CustomerResponseDTO::convertForDto);
    }

    @Override
    public CustomerResponseDTO findCorporateCustomerById(UUID id) {
        Optional<CorporateCustomer> corporateCustomer = cRepository.findById(id);
        if (corporateCustomer.isEmpty()) {
            throw new RuntimeException("id not found");
        }
        return CustomerResponseDTO.convertForDto(corporateCustomer.get());
    }

    @Override
    public CustomerResponseDTO saveCorporateCustomer(CorporateCustomerRequestDTO customer) {
       CorporateCustomer corporateCustomer = cRepository.save(customer.convertCNPJForEntity());
       return CustomerResponseDTO.convertForDto(corporateCustomer);
    }

    @Override
    public CustomerResponseDTO updateCorporateCustomer(UUID id, CorporateCustomerRequestDTO customer) {
        Optional<CorporateCustomer> corporateCustomerSaved = cRepository.findById(id);
        if (corporateCustomerSaved.isEmpty()) {
            throw new RuntimeException("id not found");
        } else {
            BeanUtils.copyProperties(customer, corporateCustomerSaved);
        }

        CorporateCustomer corporateCustomerupdate = customer.convertCNPJForEntity(id);
        CorporateCustomer newCorporateCustomer = cRepository.save(corporateCustomerupdate);
        return CustomerResponseDTO.convertForDto(newCorporateCustomer);
    }

    @Override
    public void deleteCorporateCustomer(UUID id) {
        Optional<CorporateCustomer> corporateCustomer = cRepository.findById(id);
        if (corporateCustomer.isEmpty()) {
            throw new RuntimeException("id not found");
        }
        cRepository.deleteById(corporateCustomer.get().getId());
    }
}
