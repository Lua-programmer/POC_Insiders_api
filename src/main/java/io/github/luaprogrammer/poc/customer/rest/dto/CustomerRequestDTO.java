package io.github.luaprogrammer.poc.customer.rest.dto;

import io.github.luaprogrammer.poc.customer.entity.Customer;
import io.github.luaprogrammer.poc.customer.enums.Doc_Type;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class CustomerRequestDTO {

    private String name;

    private Long docValue;

    private String email;

    private Long phone;

    public Customer convertForEntity(Doc_Type documentoTipo) {
        return new Customer(name, docValue, documentoTipo, email, phone, LocalDateTime.now());
    }

    public Customer convertForEntity(UUID id, Doc_Type documentoTipo) {
        return new Customer(id, name, docValue, documentoTipo, email, phone, LocalDateTime.now());
    }
}
