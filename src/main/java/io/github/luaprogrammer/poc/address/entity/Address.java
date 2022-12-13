package io.github.luaprogrammer.poc.address.entity;


import io.github.luaprogrammer.poc.customer.entity.Customer;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "id")
    private UUID id;

    private String cep;

    private String logradouro;

    private String complemento;

    private Long numero;
    private String bairro;

    private String localidade;

    private String uf;

    @Column(name = "is_principal")
    private Boolean isPrincipal = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    @ToString.Exclude
    private Customer customer;

    public Address(String cep, String logradouro, Long numero, String complemento, String bairro, String localidade, String uf, Boolean isPrincipal, LocalDateTime createdAt) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.isPrincipal = isPrincipal;
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Address address = (Address) o;
        return id != null && Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
