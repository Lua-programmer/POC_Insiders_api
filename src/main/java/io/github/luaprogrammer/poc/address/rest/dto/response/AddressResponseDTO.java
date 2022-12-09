package io.github.luaprogrammer.poc.address.rest.dto.response;

import io.github.luaprogrammer.poc.address.entity.Address;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class AddressResponseDTO {
    private UUID id;

    private String cep;

    private String logradouro;

    private Long numero;

    private String complemento;

    private String bairro;

    private String localidade;

    private String uf;

    private Boolean isPrincipal;
    public static AddressResponseDTO convertForDto(Address address) {
        ModelMapper modelmapper = new ModelMapper();
        return modelmapper.map(address, AddressResponseDTO.class);
    }

    public static Address convertForEntity(AddressResponseDTO address) {
        ModelMapper modelmapper = new ModelMapper();
        return modelmapper.map(address, Address.class);
    }
}