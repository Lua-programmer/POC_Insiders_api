package io.github.luaprogrammer.poc.customer.rest.dto.request;

import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class CustomerRequestDTO {

    @NotBlank(message = "NAME")
    @Length(min = 10, max = 50)
    private String name;

    @NotBlank(message = "EMAIL")
    @Email(message = "EMAIL invalid")
    private String email;

    @NotNull(message = "PHONE")
    private Long phone;

    private AddressRequestDTO address;
}
