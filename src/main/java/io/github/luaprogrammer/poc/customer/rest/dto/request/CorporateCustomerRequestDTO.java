package io.github.luaprogrammer.poc.customer.rest.dto.request;

import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class CorporateCustomerRequestDTO extends CustomerRequestDTO {

    @NotBlank(message = "CNPJ ")
    @CNPJ(message = "CNPJ invalid")
    @Pattern(regexp="(^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$)",message = "CNPJ")
    private String cnpj;

    public CorporateCustomer convertForEntity() {
        return new CorporateCustomer(getName(), getEmail(), getPhone(), LocalDateTime.now(), cnpj);
    }

    public CorporateCustomer convertForEntity(UUID id) {
        return new CorporateCustomer(id, getName(), getEmail(), getPhone(), LocalDateTime.now(), cnpj);
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
