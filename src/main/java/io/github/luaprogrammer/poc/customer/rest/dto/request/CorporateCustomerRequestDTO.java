package io.github.luaprogrammer.poc.customer.rest.dto.request;

import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CorporateCustomerRequestDTO extends CustomerRequestDTO {

    @NotBlank(message = "cnpj is required")
    @CNPJ(message = "cnpj invalid")
    private String cnpj;

    public CorporateCustomerRequestDTO(String name, String email, Long phone, String cnpj) {
        super(name, email, phone);
        this.cnpj = cnpj;
    }

    public CorporateCustomer convertCNPJForEntity() {
        return new CorporateCustomer(getName(), getEmail(), getPhone(), LocalDateTime.now(), cnpj);
    }

    public CorporateCustomer convertCNPJForEntity(UUID id) {
        return new CorporateCustomer(id, getName(), getEmail(), getPhone(), LocalDateTime.now(), cnpj);
    }


}
