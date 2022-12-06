package io.github.luaprogrammer.poc.address.service;

import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.address.rest.dto.AddressRequestDTO;
import io.github.luaprogrammer.poc.address.rest.dto.AddressResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressService {
    List<AddressResponseDTO> findAllAddress(Pageable pageable);

    Optional<Address> findAddressById(UUID id);

    AddressResponseDTO saveAddress(AddressRequestDTO requestAddress);

    Address updateAddress(UUID id, Address address);

    void deleteAddress(UUID id);
}
