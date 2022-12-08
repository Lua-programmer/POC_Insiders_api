package io.github.luaprogrammer.poc.customer.rest.dto.response;

import io.github.luaprogrammer.poc.address.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class CustomerResponseDTO {

    private UUID customerId;

    private String name;

    private String email;

    private Long phone;

    private List<Address> addresses = new ArrayList<>();
}
