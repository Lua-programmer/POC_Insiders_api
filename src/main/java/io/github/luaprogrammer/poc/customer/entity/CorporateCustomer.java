package io.github.luaprogrammer.poc.customer.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

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
