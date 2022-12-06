package io.github.luaprogrammer.poc.customer.rest.controller;

import io.github.luaprogrammer.poc.customer.entity.Customer;
import io.github.luaprogrammer.poc.customer.enums.Doc_Type;
import io.github.luaprogrammer.poc.customer.rest.dto.CustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.CustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO requestCustomer, @RequestParam("docType") String docType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.saveCustomer(requestCustomer, docType));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> readAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> readCustomerById(@PathVariable UUID id) {
        Optional<Customer> customer = customerService.findCustomerById(id);
        return customer.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(CustomerResponseDTO.convertForDto(customer.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable UUID id, @RequestBody CustomerRequestDTO requestCustomer, @RequestParam("docType") String docType) {
        Doc_Type documentoTipo = Objects.equals(docType, "cpf") ? Doc_Type.CPF : Doc_Type.CNPJ;
        Customer customerUpdated = customerService.updateCustomer(id, requestCustomer.convertForEntity(id, documentoTipo));
        return ResponseEntity.ok(CustomerResponseDTO.convertForDto(customerUpdated));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
    }
}
