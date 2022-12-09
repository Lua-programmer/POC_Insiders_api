package io.github.luaprogrammer.poc.customer.rest.dto.response;

import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {

    private UUID id;

    private String name;

    public static CustomerResponseDTO convertForDto(CorporateCustomer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }

    public static CustomerResponseDTO convertForDto(IndividualCustomer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }
}
