package io.github.luaprogrammer.poc.customer.entity;


import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import io.github.luaprogrammer.poc.customer.rest.dto.response.CustomerResponseDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value = "J")
public class CorporateCustomer extends Customer {
    private String cnpj;

    public CorporateCustomer(UUID id, String name, String email, Long phone, LocalDateTime createdAt, String cnpj) {
        super(id, name, email, phone, createdAt);
        this.cnpj = cnpj;
    }

    public CorporateCustomer(String name, String email, Long phone, LocalDateTime createdAt, String cnpj) {
        super(name, email, phone, createdAt);
        this.cnpj = cnpj;
    }

    public CorporateCustomer(UUID id, String name, String type, String email, Long phone, LocalDateTime createdAt, List<Address> addresses, String cnpj) {
        super(id, name, type, email, phone, createdAt, addresses);
        this.cnpj = cnpj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CorporateCustomer that = (CorporateCustomer) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
