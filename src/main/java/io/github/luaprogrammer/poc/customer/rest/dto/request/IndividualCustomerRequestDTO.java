package io.github.luaprogrammer.poc.customer.rest.dto.request;

import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import io.github.luaprogrammer.poc.customer.rest.dto.response.IndividualCustomerResponseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class IndividualCustomerRequestDTO extends CustomerRequestDTO {

    @NotBlank(message = "CPF")
    @CPF(message = "CPF invalid")
    @Pattern(regexp="(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)",message = "CPF")
    private String cpf;

    public IndividualCustomer convertForEntity() {
        return new IndividualCustomer(getName(), getEmail(), getPhone(), LocalDateTime.now(), cpf);
    }

    public IndividualCustomer convertForEntity(UUID id) {
        return new IndividualCustomer(id, getName(), getEmail(), getPhone(), LocalDateTime.now(), cpf);
    }

    public static IndividualCustomer convertForEntity(IndividualCustomerResponseDTO customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, IndividualCustomer.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IndividualCustomerRequestDTO that = (IndividualCustomerRequestDTO) o;
        return Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cpf);
    }
}
