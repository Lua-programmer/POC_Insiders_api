package io.github.luaprogrammer.poc.address.service.impl;

import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.address.repository.AddressRepository;
import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import io.github.luaprogrammer.poc.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository aRepository;

    @Override
    public Page<AddressResponseDTO> findAllAddress(Pageable pageable) {
        return aRepository.findAll(pageable).map(AddressResponseDTO::convertForDto);
    }

    @Override
    public AddressResponseDTO findAddressById(UUID id) {
        Optional<Address> address = aRepository.findById(id);
        if (address.isEmpty()) {
            throw new RuntimeException("id not found");
        }

        return AddressResponseDTO.convertForDto(address.get());
    }

    @Override
    public AddressResponseDTO saveAddress(AddressRequestDTO requestAddress) {
        Address addressSaved = aRepository.save(requestAddress.convertForEntity());
        return AddressResponseDTO.convertForDto(addressSaved);
    }

    @Override
    public AddressResponseDTO updateAddress(UUID id, AddressRequestDTO address) {
        Optional<Address> addressSaved = aRepository.findById(id);
        if (addressSaved.isEmpty()) {
            throw new RuntimeException("id not found");
        } else {
            BeanUtils.copyProperties(address, addressSaved);
        }

        Address addressUpdate = address.convertForEntity(id);
        Address newAddress = aRepository.save(addressUpdate);
        return AddressResponseDTO.convertForDto(newAddress);
    }


    @Override
    public void deleteAddress(UUID id) {
        Optional<Address> address = aRepository.findById(id);
        if (address.isEmpty()) {
            throw new RuntimeException("id not found");
        }
        aRepository.deleteById(address.get().getId());
    }

}
