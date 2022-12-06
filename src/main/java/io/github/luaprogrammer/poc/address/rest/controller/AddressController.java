package io.github.luaprogrammer.poc.address.rest.controller;


import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.address.rest.dto.AddressRequestDTO;
import io.github.luaprogrammer.poc.address.rest.dto.AddressResponseDTO;
import io.github.luaprogrammer.poc.address.service.impl.AddressServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressServiceImpl addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@RequestBody AddressRequestDTO requestAddress) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.saveAddress(requestAddress));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> readAllAddresses(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAllAddress(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> readAddressById(@PathVariable UUID id) {
        Optional<Address> address = addressService.findAddressById(id);
        return address.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(AddressResponseDTO.convertForDto(address.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable UUID id, @RequestBody AddressRequestDTO requestAddress) {
        Address addressUpdated = addressService.updateAddress(id, requestAddress.convertForEntity(id));
        return ResponseEntity.ok(AddressResponseDTO.convertForDto(addressUpdated));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
    }

}
