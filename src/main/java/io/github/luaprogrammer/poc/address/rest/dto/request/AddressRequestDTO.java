package io.github.luaprogrammer.poc.address.rest.dto.request;

import io.github.luaprogrammer.poc.address.entity.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
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


    public Address convertForEntity() {
        return new Address(cep, logradouro, numero, complemento, bairro, localidade, uf, isPrincipal, LocalDateTime.now());
    }

    public Address convertForEntity(UUID id) {
        return new Address(id, cep, logradouro, numero, complemento, bairro, localidade, uf, isPrincipal, LocalDateTime.now());
    }
}
