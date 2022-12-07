package io.github.luaprogrammer.poc.customer.rest.dto.request;

import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class IndividualCustomerRequestDTO extends CustomerRequestDTO {
    private String cpf;

    public IndividualCustomerRequestDTO(String name, String email, Long phone, String cpf) {
        super(name, email, phone);
        this.cpf = cpf;
    }


    public IndividualCustomer convertCPForEntity() {
        return new IndividualCustomer(getName(), getEmail(), getPhone(), LocalDateTime.now(), cpf);
    }


    public IndividualCustomer convertCPForEntity(UUID id) {
        return new IndividualCustomer(id, getName(), getEmail(), getPhone(), LocalDateTime.now(), cpf);
    }

}
