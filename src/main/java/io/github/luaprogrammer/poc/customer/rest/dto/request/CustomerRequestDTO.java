package io.github.luaprogrammer.poc.customer.rest.dto.request;

import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class CustomerRequestDTO {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotNull(message = "phone is required")
    private Long phone;

    private AddressRequestDTO address;
}
