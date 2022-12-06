package io.github.luaprogrammer.poc.address.service.impl;

import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.address.repository.AddressRepository;
import io.github.luaprogrammer.poc.address.rest.dto.AddressRequestDTO;
import io.github.luaprogrammer.poc.address.rest.dto.AddressResponseDTO;
import io.github.luaprogrammer.poc.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<AddressResponseDTO> findAllAddress(Pageable pageable) {
        return addressRepository.findAll(pageable).stream().map(AddressResponseDTO::convertForDto).collect(Collectors.toList());
    }

    @Override
    public Optional<Address> findAddressById(UUID id) {
        return addressRepository.findById(id);
    }

    @Override
    public AddressResponseDTO saveAddress(AddressRequestDTO requestAddress) {
        Address addressSaved = addressRepository.save(requestAddress.convertForEntity());
        return AddressResponseDTO.convertForDto(addressSaved);
    }

    @Override
    public Address updateAddress(UUID id, Address address) {
        Address addressSaved = validateAddress(id);
        BeanUtils.copyProperties(address, addressSaved);
        return addressRepository.save(addressSaved);
    }


    @Override
    public void deleteAddress(UUID id) {
        addressRepository.deleteById(id);
    }

    private Address validateAddress(UUID id) {
        Optional<Address> addressByCode = findAddressById(id);
        if (addressByCode.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return addressByCode.get();
    }
}
