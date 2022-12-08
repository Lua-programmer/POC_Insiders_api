package io.github.luaprogrammer.poc.customer.rest.dto.request;

import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class IndividualCustomerRequestDTO extends CustomerRequestDTO {

    @NotBlank(message = "cpf is required")
    @CPF(message = "cpf invalid")
    private String cpf;

    public IndividualCustomerRequestDTO(String name, String email,  Long phone, AddressRequestDTO address, String cpf) {
        super(name, email, phone, address);
        this.cpf = cpf;
    }

    public IndividualCustomer convertCPForEntity() {
        return new IndividualCustomer(getName(), getEmail(), getPhone(), LocalDateTime.now(), cpf);
    }


    public IndividualCustomer convertCPForEntity(UUID id) {
        return new IndividualCustomer(id, getName(), getEmail(), getPhone(), LocalDateTime.now(), cpf);
    }

}
