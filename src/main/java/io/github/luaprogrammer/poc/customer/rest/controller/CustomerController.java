package io.github.luaprogrammer.poc.customer.rest.controller;

import io.github.luaprogrammer.poc.customer.rest.dto.CorporateCustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.CustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @PostMapping("/corporations")
    public ResponseEntity<CustomerResponseDTO> createCorporateCustomer(@RequestBody CorporateCustomerRequestDTO requestCustomer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.saveCorporateCustomer(requestCustomer));
    }

    @GetMapping("/corporations")
    public ResponseEntity<Page<CustomerResponseDTO>> readAllCorporateCustomers(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findAllCorporateCustomer(pageable));
    }

    @GetMapping("/corporations/{id}")
    public ResponseEntity<CustomerResponseDTO> readCorporateCustomerById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findCorporateCustomerById(id));

    }

    @PutMapping("/corporations/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCorporateCustomer(@PathVariable UUID id, @RequestBody CorporateCustomerRequestDTO requestCustomer) {
        return ResponseEntity.ok(customerService.updateCorporateCustomer(id, requestCustomer));
    }

    @DeleteMapping("corporations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCorporateCustomer(@PathVariable UUID id) {
        customerService.deleteCorporateCustomer(id);
    }
}
