package io.github.luaprogrammer.poc.address.service;

import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AddressService {
    Page<AddressResponseDTO> findAllAddress(Pageable pageable);

    AddressResponseDTO findAddressById(UUID id);

    Address saveAddress(AddressRequestDTO requestAddress) throws Exception;

    AddressResponseDTO updateAddress(UUID id, AddressRequestDTO address);

    void deleteAddress(UUID id);
}
