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
@DiscriminatorValue(value = "F")
public class IndividualCustomer extends Customer {

    private String cpf;

    public IndividualCustomer(UUID id, String name, String email, Long phone, LocalDateTime createdAt, String cpf) {
        super(id, name, email, phone, createdAt);
        this.cpf = cpf;
    }

    public IndividualCustomer(String name, String email, Long phone, LocalDateTime createdAt, String cpf) {
        super(name, email, phone, createdAt);
        this.cpf = cpf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IndividualCustomer that = (IndividualCustomer) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
