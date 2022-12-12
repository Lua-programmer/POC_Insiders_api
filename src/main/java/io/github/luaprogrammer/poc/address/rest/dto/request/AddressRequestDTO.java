package io.github.luaprogrammer.poc.address.rest.dto.request;

import io.github.luaprogrammer.poc.address.entity.Address;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class AddressRequestDTO {
    @NotBlank(message = "cep is required")
    private String cep;

    private String logradouro;

    private String complemento;

    private Long numero;

    private String bairro;

    private String localidade;

    private String uf;

    private Boolean isPrincipal = false;

    private UUID customerId;


    public Address convertForEntity() {
        return new Address(cep, logradouro, numero, complemento, bairro, localidade, uf, isPrincipal, LocalDateTime.now());
    }
}
