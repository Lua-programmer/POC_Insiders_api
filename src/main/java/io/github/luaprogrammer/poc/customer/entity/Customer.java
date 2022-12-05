package io.github.luaprogrammer.poc.customer.entity;

import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.customer.enums.Doc_Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID customerId;

    @Column(name = "name_complete")
    private String name;

    @Column(name = "doc_value")
    private Long docValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type")
    private Doc_Type docType;

    private String email;

    private Long phone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Address> addresses;

    public Customer(UUID customerId, String name, Long docValue, Doc_Type docType, String email, Long phone, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.docValue = docValue;
        this.docType = docType;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    public Customer(String name, Long docValue, Doc_Type docType, String email, Long phone, LocalDateTime createdAt) {
        this.name = name;
        this.docValue = docValue;
        this.docType = docType;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
    }
}
