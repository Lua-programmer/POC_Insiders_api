package io.github.luaprogrammer.poc.customer.rest.dto.request;

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
}
