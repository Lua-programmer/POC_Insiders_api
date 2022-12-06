package io.github.luaprogrammer.poc.customer.rest.dto;

import io.github.luaprogrammer.poc.customer.entity.Customer;
import io.github.luaprogrammer.poc.customer.enums.Doc_Type;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Data
public class CustomerResponseDTO {

    private UUID customerId;

    private String name;

    private Long docValue;

    private Doc_Type docType;

    private String email;

    private Long phone;

    public static CustomerResponseDTO convertForDto(Customer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }
}
