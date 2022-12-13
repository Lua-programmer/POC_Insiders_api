package io.github.luaprogrammer.poc.customer.rest.controller;

import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.request.CorporateCustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.request.IndividualCustomerRequestDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.CorporateCustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.CustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.IndividualCustomerResponseDTO;
import io.github.luaprogrammer.poc.customer.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @PostMapping("/corporations")
    public ResponseEntity<CustomerResponseDTO> createCorporateCustomer(@RequestBody @Valid CorporateCustomerRequestDTO requestCustomer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.saveCorporateCustomer(requestCustomer));
    }

    @GetMapping("/corporations")
    public ResponseEntity<Page<CustomerResponseDTO>> readAllCorporateCustomers(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findAllCorporateCustomer(pageable));
    }

    @GetMapping("/corporations/{id}")
    public ResponseEntity<CorporateCustomerResponseDTO> readCorporateCustomerById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findCorporateCustomerById(id));

    }

    @PutMapping("/corporations/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCorporateCustomer(@PathVariable UUID id, @RequestBody @Valid CorporateCustomerRequestDTO requestCustomer) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(customerService.updateCorporateCustomer(id, requestCustomer));
    }
    @PatchMapping("/corporations/{id}/add-address")
    public ResponseEntity<CorporateCustomerResponseDTO> addAddressCorporateCustomer(@PathVariable("id") UUID id, @RequestBody @Valid AddressRequestDTO addressRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customerService.addAddressCorporateCustomer(id, addressRequest));
    }


    @PatchMapping("/corporations/{id}/update-address/{addressId}")
    public ResponseEntity<CorporateCustomerResponseDTO> updateAddressCorporateCustomer(@PathVariable("id") UUID id, @PathVariable("addressId") UUID addressId, @RequestBody @Valid AddressRequestDTO addressRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customerService.updateAddressCorporateCustomer(id, addressId, addressRequest));
    }

    @DeleteMapping("corporations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCorporateCustomer(@PathVariable UUID id) {
        customerService.deleteCorporateCustomer(id);
    }

    @DeleteMapping("corporations/{id}/delete-address/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddressCorporateCustomer(@PathVariable("id") UUID id, @PathVariable("addressId") UUID addressId) {
        customerService.deleteAddressCorporateCustomer(id, addressId);
    }

    @PostMapping("/individual")
    public ResponseEntity<CustomerResponseDTO> createIndividualCustomer(@RequestBody @Valid IndividualCustomerRequestDTO requestCustomer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.saveIndividualCustomer(requestCustomer));
    }

    @GetMapping("/individual")
    public ResponseEntity<Page<CustomerResponseDTO>> readAllIndividualCustomers(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAllIndividualCustomer(pageable));
    }

    @GetMapping("/individual/{id}")
    public ResponseEntity<IndividualCustomerResponseDTO> readIndividualCustomerById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findIndividualCustomerById(id));
    }

    @PutMapping("/individual/{id}")
    public ResponseEntity<CustomerResponseDTO> updateIndividualCustomer(@PathVariable UUID id, @RequestBody @Valid IndividualCustomerRequestDTO requestCustomer) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(customerService.updateIndividualCustomer(id, requestCustomer));
    }

    @PatchMapping("/individual/{id}/add-address")
    public ResponseEntity<IndividualCustomerResponseDTO> addAddressIndividualCustomer(@PathVariable("id") UUID id, @RequestBody @Valid AddressRequestDTO addressRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customerService.addAddressIndividualCustomer(id, addressRequest));
    }


    @PatchMapping("/individual/{id}/update-address/{addressId}")
    public ResponseEntity<IndividualCustomerResponseDTO> updateAddressIndividualCustomer(@PathVariable("id") UUID id, @PathVariable("addressId") UUID addressId, @RequestBody @Valid AddressRequestDTO addressRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customerService.updateAddressIndividualCustomer(id, addressId, addressRequest));
    }

    @DeleteMapping("individual/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIndividualCustomer(@PathVariable UUID id) {
        customerService.deleteIndividualCustomer(id);
    }

    @DeleteMapping("individual/{id}/delete-address/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddressIndividualCustomer(@PathVariable("id") UUID id, @PathVariable("addressId") UUID addressId ) {
        customerService.deleteAddressIndividualCustomer(id, addressId);
    }
}
