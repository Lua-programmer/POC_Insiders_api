package io.github.luaprogrammer.poc.customer.service.impl;


import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.address.repository.AddressRepository;
import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import io.github.luaprogrammer.poc.address.service.impl.AddressServiceImpl;
import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import io.github.luaprogrammer.poc.customer.entity.Customer;
import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import io.github.luaprogrammer.poc.customer.repository.CorporateCustomerRepository;
import io.github.luaprogrammer.poc.customer.repository.IndividualCustomerRepository;
import io.github.luaprogrammer.poc.customer.rest.dto.request.CorporateCustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.request.IndividualCustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.CorporateCustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.CustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.IndividualCustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.service.CustomerService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CorporateCustomerRepository cRepository;
    private final IndividualCustomerRepository iRepository;

    private final AddressRepository aRepository;

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
        Optional<CorporateCustomer> corporateCustomerSaved = cRepository.findById(id);
        if (corporateCustomerSaved.isEmpty()) {
            throw new RuntimeException("id not found");
        }

        CorporateCustomer customer = corporateCustomerSaved.get();
        addressRequest.setCustomerId(customer.getId());
        Address addressSaved = addressService.saveAddress(addressRequest);

        addressSaved.setCustomer(Customer.builder().id(addressRequest.getCustomerId()).build());

        customer.getAddresses().add(addressSaved);
        CorporateCustomer customerUpdated = cRepository.save(customer);
        return CorporateCustomerResponseDTO.convertForDto(customerUpdated);
    }


//    @Override
//    public IndividualCustomerResponseDTO addAddressIndividualCustomer(UUID id, AddressRequestDTO addressRequest) throws Exception {
//        IndividualCustomerResponseDTO customerUpdated = saveAddressForIndividualCustomer(id, addressRequest);
//        IndividualCustomer individualCustomer = IndividualCustomerRequestDTO.convertForEntity(customerUpdated);
//        IndividualCustomer save = iRepository.save(individualCustomer);
//        return IndividualCustomerResponseDTO.convertForDto(save);
//    }


//    private IndividualCustomerResponseDTO saveAddressForIndividualCustomer(UUID id, AddressRequestDTO addressRequest) throws Exception {
//        Optional<IndividualCustomer> individualCustomerSaved = iRepository.findById(id);
//        if (individualCustomerSaved.isEmpty()) {
//            throw new RuntimeException("id not found");
//        }
//        IndividualCustomerResponseDTO customer = IndividualCustomerResponseDTO.convertForDto(individualCustomerSaved.get());
//        Address addressSaved = addressService.saveAddress(addressRequest);
//        customer.getAddresses().add(addressSaved);
//        return customer;
//    }

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

    public CorporateCustomerResponseDTO updateAddressCorporateCustomer(UUID id, UUID addressId, AddressRequestDTO addressRequest) throws Exception {
        Optional<CorporateCustomer> corporateCustomerSaved = cRepository.findById(id);
        if (corporateCustomerSaved.isEmpty()) {
            throw new RuntimeException("id not found");
        }

        Optional<Address> addressIdSaved = aRepository.findById(addressId);

        AddressResponseDTO addressResponseDTO = addressService.updateAddress(addressIdSaved.get().getId(), addressRequest);

        UUID customerId = addressResponseDTO.getCustomerId();
       return findCorporateCustomerById(customerId);
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
    public void deleteAddressCorporateCustomer(UUID id) {
        Optional<Address> addressSaved = aRepository.findById(id);
        if (addressSaved.isEmpty()) {
            throw new RuntimeException("id not found");
        }

        addressSaved.get().setCustomer(null);
        aRepository.save(addressSaved.get());
        addressService.deleteAddress(addressSaved.get().getId());
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
