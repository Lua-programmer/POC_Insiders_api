package io.github.luaprogrammer.poc.customer.rest.dto.response;

import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class IndividualCustomerResponseDTO extends CustomerResponseDTO{
    private String type;

    private String cpf;

    public IndividualCustomerResponseDTO(UUID customerId, String name, String email, Long phone, List<Address> addresses, String type, String cpf) {
        super(customerId, name, email, phone, addresses);
        this.type = type;
        this.cpf = cpf;
    }

    public static IndividualCustomerResponseDTO convertForDto(IndividualCustomer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, IndividualCustomerResponseDTO.class);
    }
}
