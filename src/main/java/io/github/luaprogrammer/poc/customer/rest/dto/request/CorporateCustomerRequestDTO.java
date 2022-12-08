package io.github.luaprogrammer.poc.customer.rest.dto.request;

import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import io.github.luaprogrammer.poc.customer.rest.dto.response.CorporateCustomerResponseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class CorporateCustomerRequestDTO extends CustomerRequestDTO {

    @NotBlank(message = "cnpj is required")
    @CNPJ(message = "cnpj invalid")
    private String cnpj;

    public CorporateCustomer convertCNPJForEntity() {
        return new CorporateCustomer(getName(), getEmail(), getPhone(), LocalDateTime.now(), cnpj);
    }

    public CorporateCustomer convertCNPJForEntity(UUID id) {
        return new CorporateCustomer(id, getName(), getEmail(), getPhone(), LocalDateTime.now(), cnpj);
    }

    public static CorporateCustomer convertForEntity(CorporateCustomerResponseDTO customer) {
        return new CorporateCustomer(customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getPhone(), LocalDateTime.now(), customer.getAddresses(),customer.getCnpj());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CorporateCustomerRequestDTO that = (CorporateCustomerRequestDTO) o;
        return Objects.equals(cnpj, that.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cnpj);
    }
}
