package io.github.luaprogrammer.poc.customer.service.impl;


import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import io.github.luaprogrammer.poc.address.service.impl.AddressServiceImpl;
import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import io.github.luaprogrammer.poc.customer.repository.CorporateCustomerRepository;
import io.github.luaprogrammer.poc.customer.repository.IndividualCustomerRepository;
import io.github.luaprogrammer.poc.customer.rest.dto.request.CorporateCustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.request.IndividualCustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.CorporateCustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.CustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.IndividualCustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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

    private final AddressServiceImpl addressService;

    @Override
    public Page<CustomerResponseDTO> findAllCorporateCustomer(Pageable pageable) {
        return cRepository.findAll(pageable).map(CustomerResponseDTO::convertForDto);
    }

    @Override
    public CorporateCustomerResponseDTO findCorporateCustomerById(UUID id) {
        Optional<CorporateCustomer> corporateCustomer = cRepository.findById(id);
        if (corporateCustomer.isEmpty()) {
            throw new RuntimeException("id not found");
        }
        return CorporateCustomerResponseDTO.convertForDto(corporateCustomer.get());
    }

    @Override
    public CustomerResponseDTO saveCorporateCustomer(CorporateCustomerRequestDTO customer) {
        CorporateCustomer corporateCustomer = cRepository.save(customer.convertForEntity());
        return CustomerResponseDTO.convertForDto(corporateCustomer);
    }

    @Override
    public CorporateCustomerResponseDTO addAddressCorporateCustomer(UUID id, AddressRequestDTO addressRequest) throws Exception {
        CorporateCustomerResponseDTO customerUpdated = saveAddressForCorporateCustomer(id, addressRequest);
        CorporateCustomer corporateCustomer = CorporateCustomerRequestDTO.convertForEntity(customerUpdated);
        CorporateCustomer save = cRepository.save(corporateCustomer);
        return CorporateCustomerResponseDTO.convertForDto(save);
    }


    private CorporateCustomerResponseDTO saveAddressForCorporateCustomer(UUID id, AddressRequestDTO addressRequest) throws Exception {
        Optional<CorporateCustomer> corporateCustomerSaved = cRepository.findById(id);
        if (corporateCustomerSaved.isEmpty()) {
            throw new RuntimeException("id not found");
        }
        CorporateCustomerResponseDTO customer = CorporateCustomerResponseDTO.convertForDto(corporateCustomerSaved.get());
        AddressResponseDTO addressSaved = addressService.saveAddress(addressRequest);
        customer.getAddresses().add(addressSaved);
        return customer;
    }

    @Override
    public IndividualCustomerResponseDTO addAddressIndividualCustomer(UUID id, AddressRequestDTO addressRequest) throws Exception {
        IndividualCustomerResponseDTO customerUpdated = saveAddressForIndividualCustomer(id, addressRequest);
        IndividualCustomer individualCustomer = IndividualCustomerRequestDTO.convertForEntity(customerUpdated);
        IndividualCustomer save = iRepository.save(individualCustomer);
        return IndividualCustomerResponseDTO.convertForDto(save);
    }


    private IndividualCustomerResponseDTO saveAddressForIndividualCustomer(UUID id, AddressRequestDTO addressRequest) throws Exception {
        Optional<IndividualCustomer> individualCustomerSaved = iRepository.findById(id);
        if (individualCustomerSaved.isEmpty()) {
            throw new RuntimeException("id not found");
        }
        IndividualCustomerResponseDTO customer = IndividualCustomerResponseDTO.convertForDto(individualCustomerSaved.get());
        AddressResponseDTO addressSaved = addressService.saveAddress(addressRequest);
        customer.getAddresses().add(addressSaved);
        return customer;
    }

    @Override
    public CustomerResponseDTO updateCorporateCustomer(UUID id, CorporateCustomerRequestDTO customer) {
        Optional<CorporateCustomer> corporateCustomerSaved = cRepository.findById(id);
        if (corporateCustomerSaved.isEmpty()) {
            throw new RuntimeException("id not found");
        } else {
            BeanUtils.copyProperties(customer, corporateCustomerSaved);
        }

        CorporateCustomer corporateCustomerupdate = customer.convertForEntity(id);
        CorporateCustomer newCorporateCustomer = cRepository.save(corporateCustomerupdate);
        return CorporateCustomerResponseDTO.convertForDto(newCorporateCustomer);
    }

    @Override
    public void deleteCorporateCustomer(UUID id) {
        Optional<CorporateCustomer> corporateCustomer = cRepository.findById(id);
        if (corporateCustomer.isEmpty()) {
            throw new RuntimeException("id not found");
        }
        cRepository.deleteById(corporateCustomer.get().getId());
    }

    @Override
    public Page<CustomerResponseDTO> findAllIndividualCustomer(Pageable pageable) {
        return iRepository.findAll(pageable).map(CustomerResponseDTO::convertForDto);
    }

    @Override
    public IndividualCustomerResponseDTO findIndividualCustomerById(UUID id) {
        Optional<IndividualCustomer> individualCustomer = iRepository.findById(id);
        if (individualCustomer.isEmpty()) {
            throw new RuntimeException("id not found");
        }
        return IndividualCustomerResponseDTO.convertForDto(individualCustomer.get());
    }

    @Override
    public CustomerResponseDTO saveIndividualCustomer(IndividualCustomerRequestDTO customer) {
        IndividualCustomer individualCustomer = iRepository.save(customer.convertForEntity());
        return CustomerResponseDTO.convertForDto(individualCustomer);
    }

    @Override
    public CustomerResponseDTO updateIndividualCustomer(UUID id, IndividualCustomerRequestDTO customer) {
        Optional<IndividualCustomer> individualCustomerSaved = iRepository.findById(id);
        if (individualCustomerSaved.isEmpty()) {
            throw new RuntimeException("id not found");
        } else {
            BeanUtils.copyProperties(customer, individualCustomerSaved);
        }

        IndividualCustomer individualCustomerupdate = customer.convertForEntity(id);
        IndividualCustomer newIndividualCustomer = iRepository.save(individualCustomerupdate);
        return IndividualCustomerResponseDTO.convertForDto(newIndividualCustomer);
    }

    @Override
    public void deleteIndividualCustomer(UUID id) {
        Optional<IndividualCustomer> individualCustomer = iRepository.findById(id);
        if (individualCustomer.isEmpty()) {
            throw new RuntimeException("id not found");
        }
        iRepository.deleteById(individualCustomer.get().getId());
    }
}
