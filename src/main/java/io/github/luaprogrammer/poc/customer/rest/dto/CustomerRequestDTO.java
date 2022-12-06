package io.github.luaprogrammer.poc.customer.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class CustomerRequestDTO {

    private String name;

    private String email;

    private Long phone;

//    public IndividualCustomer convertCPForEntity() {
//        return new IndividualCustomer(name, type, email, phone, LocalDateTime.now(), cpf);
//    }


//    public IndividualCustomer convertCPForEntity(UUID id) {
//        return new IndividualCustomer(id, name, type, email, phone, LocalDateTime.now(), cpf);
//    }



}
