package io.github.luaprogrammer.poc.address.rest.dto;

import io.github.luaprogrammer.poc.address.entity.Address;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class AddressResponseDTO {
    private Long adressId;

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
}
