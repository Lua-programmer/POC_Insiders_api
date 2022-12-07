package io.github.luaprogrammer.poc.customer.rest.dto.response;

import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Data
public class CustomerResponseDTO {

    private UUID customerId;

    private String name;

    private String email;

    private Long phone;

    public static CustomerResponseDTO convertForDto(IndividualCustomer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }

    public static CustomerResponseDTO convertForDto(CorporateCustomer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }
}
