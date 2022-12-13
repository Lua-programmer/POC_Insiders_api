package io.github.luaprogrammer.poc.customer.rest.dto.request;

import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class CustomerRequestDTO {

    @NotBlank(message = "NAME")
    @Length(message = "NAME", min = 10, max = 50)
    private String name;

    @NotBlank(message = "EMAIL")
    @Email(message = "EMAIL invalid")
    private String email;

    @NotNull(message = "PHONE")
    private Long phone;

    private AddressRequestDTO address;
}
