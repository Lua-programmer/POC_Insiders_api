package io.github.luaprogrammer.poc.customer.rest.dto.response;

import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
public class IndividualCustomerResponseDTO extends CustomerResponseDTO {
    private String email;

    private Long phone;

    private String type;

    private String cpf;

    private List<AddressResponseDTO> addresses;

    public static IndividualCustomerResponseDTO convertForDto(IndividualCustomer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, IndividualCustomerResponseDTO.class);
    }
}
