package io.github.luaprogrammer.poc.address.rest.controller;


import com.google.gson.Gson;
import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import io.github.luaprogrammer.poc.address.service.impl.AddressServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressServiceImpl addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@RequestBody @Valid AddressRequestDTO requestAddress) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.saveAddress(requestAddress));
    }

    @GetMapping
    public ResponseEntity<Page<AddressResponseDTO>> readAllAddresses(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(addressService.findAllAddress(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> readAddressById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(addressService.findAddressById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable UUID id, @RequestBody @Valid AddressRequestDTO requestAddress) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(addressService.updateAddress(id, requestAddress));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
    }

}
