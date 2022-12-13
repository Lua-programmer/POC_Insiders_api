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
import io.github.luaprogrammer.poc.exception.RuleBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public List<CustomerResponseDTO> findAllFilterCorporateCustomer(String name) {
        return cRepository.findAllByNameLikeIgnoreCase(name).stream().map(CustomerResponseDTO::convertForDto).collect(Collectors.toList());
    }

    @Override
    public CorporateCustomerResponseDTO findCorporateCustomerById(UUID id) {
        CorporateCustomer corporateCustomer = cRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("Id " + id + " not found", 404));
        return CorporateCustomerResponseDTO.convertForDto(corporateCustomer);
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
            throw new EmptyResultDataAccessException("Id " + id + "not found", 404);
        }

        CorporateCustomer customer = corporateCustomerSaved.get();
        addressRequest.setCustomerId(customer.getId());
        Address addressSaved = addressService.saveAddress(addressRequest);

        addressSaved.setCustomer(Customer.builder().id(addressRequest.getCustomerId()).build());

        if (customer.getAddresses().size() <= 4) {
            if (customer.getAddresses().isEmpty()) {
                addressSaved.setIsPrincipal(true);
            }
            for (int i = 0; i < customer.getAddresses().size(); i++) {
                if (customer.getAddresses().get(i).getIsPrincipal() && addressSaved.getIsPrincipal()) {
                    customer.getAddresses().get(i).setIsPrincipal(false);
                }
                if (customer.getAddresses().get(i).getLogradouro().equals(addressSaved.getLogradouro())
                        && customer.getAddresses().get(i).getNumero().equals(addressSaved.getNumero())) {
                    throw new RuleBusinessException("This zip code is already registered for this customer.");
                }
            }
        } else {
            throw new RuleBusinessException("This customer already has 5 addresses saved");
        }
        customer.getAddresses().add(addressSaved);
        CorporateCustomer customerUpdated = cRepository.save(customer);
        return CorporateCustomerResponseDTO.convertForDto(customerUpdated);
    }

    @Override
    public CustomerResponseDTO updateCorporateCustomer(UUID id, CorporateCustomerRequestDTO customer) {
        CorporateCustomer corporateCustomerSaved = cRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("Id " + id + " customer not found", 404)
        );

        BeanUtils.copyProperties(customer, corporateCustomerSaved);

        CorporateCustomer corporateCustomerupdate = customer.convertForEntity(id);
        CorporateCustomer newCorporateCustomer = cRepository.save(corporateCustomerupdate);
        return CorporateCustomerResponseDTO.convertForDto(newCorporateCustomer);
    }

    @Override
    public CorporateCustomerResponseDTO updateAddressCorporateCustomer(UUID id, UUID addressId, AddressRequestDTO addressRequest) throws Exception {
        Optional<CorporateCustomer> corporateCustomerSaved = cRepository.findById(id);
        if (corporateCustomerSaved.isEmpty()) {
            throw new EmptyResultDataAccessException("Id " + id + "customer not found", 404);
        }

        Optional<Address> addressIdSaved = aRepository.findById(addressId);
        if (addressIdSaved.isEmpty()) {
            throw new EmptyResultDataAccessException("Id " + id + " address not found", 404);
        }

        AddressResponseDTO addressResponseDTO = addressService.updateAddress(addressIdSaved.get().getId(), addressRequest);

        UUID customerId = addressResponseDTO.getCustomerId();
        return findCorporateCustomerById(customerId);
    }

    @Override
    public void deleteCorporateCustomer(UUID id) {
        CorporateCustomer corporateCustomer = cRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("Id " + id + " not found", 404)
        );
        cRepository.deleteById(corporateCustomer.getId());
    }

    @Override
    public void deleteAddressCorporateCustomer(UUID customerId, UUID addressId) {
        Optional<CorporateCustomer> corporateCustomerById = cRepository.findById(customerId);
        Optional<Address> addressSaved = aRepository.findById(addressId);
        if (addressSaved.isEmpty()) {
            throw new EmptyResultDataAccessException("Id " + addressId + " address not found", 404);
        }

        if (corporateCustomerById.isEmpty()) {
            throw new EmptyResultDataAccessException("Id " + customerId + " customer not found", 404);
        }

        if (Boolean.TRUE.equals(addressSaved.get().getIsPrincipal())) {
            throw new RuleBusinessException("Main address cannot be deleted.");
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
        IndividualCustomer individualCustomer = iRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("Id " + id + " not found", 404)
        );

        return IndividualCustomerResponseDTO.convertForDto(individualCustomer);
    }

    @Override
    public CustomerResponseDTO saveIndividualCustomer(IndividualCustomerRequestDTO customer) {
        IndividualCustomer individualCustomer = iRepository.save(customer.convertForEntity());
        return CustomerResponseDTO.convertForDto(individualCustomer);
    }

    @Override
    public IndividualCustomerResponseDTO addAddressIndividualCustomer(UUID id, AddressRequestDTO addressRequest) throws Exception {
        Optional<IndividualCustomer> individualCustomerSaved = iRepository.findById(id);
        if (individualCustomerSaved.isEmpty()) {
            throw new EmptyResultDataAccessException("Id " + id + "not found", 404);
        }

        addressRequest.setCustomerId(individualCustomerSaved.get().getId());
        Address addressSaved = addressService.saveAddress(addressRequest);

        addressSaved.setCustomer(Customer.builder().id(addressRequest.getCustomerId()).build());

        if (individualCustomerSaved.get().getAddresses().size() <= 4) {
            if (individualCustomerSaved.get().getAddresses().isEmpty()) {
                addressSaved.setIsPrincipal(true);
            }
            for (int i = 0; i < individualCustomerSaved.get().getAddresses().size(); i++) {
                if (individualCustomerSaved.get().getAddresses().get(i).getIsPrincipal() && addressSaved.getIsPrincipal()) {
                    individualCustomerSaved.get().getAddresses().get(i).setIsPrincipal(false);
                }
                if (individualCustomerSaved.get().getAddresses().get(i).getLogradouro().equals(addressSaved.getLogradouro())
                && individualCustomerSaved.get().getAddresses().get(i).getNumero().equals(addressSaved.getNumero())) {
                    throw new RuleBusinessException("This zip code is already registered for this customer.");
                }
            }
        } else {
            throw new EmptyResultDataAccessException("Id " + id + " address not found", 404);
        }

        individualCustomerSaved.get().getAddresses().add(addressSaved);
        IndividualCustomer customerUpdated = iRepository.save(individualCustomerSaved.get());
        return IndividualCustomerResponseDTO.convertForDto(customerUpdated);
    }

    @Override
    public CustomerResponseDTO updateIndividualCustomer(UUID id, IndividualCustomerRequestDTO customer) {
        IndividualCustomer individualCustomerSaved = iRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("Id " + id + " customer not found", 404)
        );

        BeanUtils.copyProperties(customer, individualCustomerSaved);

        IndividualCustomer individualCustomerupdate = customer.convertForEntity(id);
        IndividualCustomer newIndividualCustomer = iRepository.save(individualCustomerupdate);
        return IndividualCustomerResponseDTO.convertForDto(newIndividualCustomer);
    }

    @Override
    public IndividualCustomerResponseDTO updateAddressIndividualCustomer(UUID id, UUID addressId, AddressRequestDTO addressRequest) throws Exception {
        Optional<IndividualCustomer> individualCustomerSaved = iRepository.findById(id);
        if (individualCustomerSaved.isEmpty()) {
            throw new EmptyResultDataAccessException("Id " + id + "customer not found", 404);
        }

        Optional<Address> addressIdSaved = aRepository.findById(addressId);

        if (addressIdSaved.isEmpty()) {
            throw new EmptyResultDataAccessException("Id " + id + " address not found", 404);
        }

        AddressResponseDTO addressResponseDTO = addressService.updateAddress(addressIdSaved.get().getId(), addressRequest);

        UUID customerId = addressResponseDTO.getCustomerId();
        return findIndividualCustomerById(customerId);
    }

    @Override
    public void deleteIndividualCustomer(UUID id) {
        IndividualCustomer individualCustomer = iRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("Id " + id + " not found", 404)
        );

        iRepository.deleteById(individualCustomer.getId());
    }

    @Override
    public void deleteAddressIndividualCustomer(UUID customerId, UUID addressId) {
        Optional<IndividualCustomer> individualCustomerById = iRepository.findById(customerId);
        Optional<Address> addressSaved = aRepository.findById(addressId);
        if (addressSaved.isEmpty()) {
            throw new EmptyResultDataAccessException("Id " + addressId + " address not found", 404);
        }

        if (individualCustomerById.isEmpty()) {
            throw new EmptyResultDataAccessException("Id " + customerId + " customer not found", 404);
        }

        if (Boolean.TRUE.equals(addressSaved.get().getIsPrincipal())) {
            throw new RuleBusinessException("Main address cannot be deleted.");
        }

        addressSaved.get().setCustomer(null);
        aRepository.save(addressSaved.get());
        addressService.deleteAddress(addressSaved.get().getId());
    }
}
