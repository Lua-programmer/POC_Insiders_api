package io.github.luaprogrammer.poc.address.rest.dto.request;

import io.github.luaprogrammer.poc.address.entity.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class AddressRequestDTO {
    @NotBlank(message = "cep is required")
    private String cep;

    @NotBlank(message = "logradouro is required")
    private String logradouro;

    private String complemento;

    private Long numero;

    @NotBlank(message = "bairro is required")
    private String bairro;


    @NotBlank(message = "localidade is required")
    private String localidade;

    @NotBlank(message = "uf is required")
    private String uf;

    private Boolean isPrincipal = false;


    public Address convertForEntity() {
        return new Address(cep, logradouro, numero, complemento, bairro, localidade, uf, isPrincipal, LocalDateTime.now());
    }

    public Address convertForEntity(UUID id) {
        return new Address(id, cep, logradouro, numero, complemento, bairro, localidade, uf, isPrincipal, LocalDateTime.now());
    }
}
