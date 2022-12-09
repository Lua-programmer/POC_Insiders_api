package io.github.luaprogrammer.poc.customer.rest.dto.request;

import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import io.github.luaprogrammer.poc.customer.rest.dto.response.IndividualCustomerResponseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class IndividualCustomerRequestDTO extends CustomerRequestDTO {

    @NotBlank(message = "cpf is required")
    @CPF(message = "cpf invalid")
    private String cpf;

    public IndividualCustomer convertForEntity() {
        return new IndividualCustomer(getName(), getEmail(), getPhone(), LocalDateTime.now(), cpf);
    }

    public IndividualCustomer convertForEntity(UUID id) {
        return new IndividualCustomer(id, getName(), getEmail(), getPhone(), LocalDateTime.now(), cpf);
    }

    public static IndividualCustomer convertForEntity(IndividualCustomerResponseDTO customer) {

        List<Address> addresses = new ArrayList();
        for (int i = 0; i < customer.getAddresses().size(); i++) {
            Address address = AddressResponseDTO.convertForEntity(customer.getAddresses().get(i));
            addresses.add(address);
        }

        return new IndividualCustomer(customer.getId(), customer.getName(), customer.getType(), customer.getEmail(),
                customer.getPhone(), LocalDateTime.now(), addresses,
                customer.getCpf());
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
