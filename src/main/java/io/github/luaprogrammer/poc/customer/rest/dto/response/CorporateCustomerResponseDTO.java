package io.github.luaprogrammer.poc.customer.rest.dto.response;

import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import io.github.luaprogrammer.poc.customer.entity.IndividualCustomer;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CorporateCustomerResponseDTO extends CustomerResponseDTO{

    private String email;

    private Long phone;


    private String type;

   private String cnpj;

    private List<AddressResponseDTO> addresses;

//    public CorporateCustomerResponseDTO(UUID customerId, String name, String email, Long phone, List<AddressResponseDTO> addresses, String cnpj) {
//        super(customerId, name, email, phone, addresses);
//        this.cnpj = cnpj;
//    }

    public static CorporateCustomerResponseDTO convertForDto(CorporateCustomer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, CorporateCustomerResponseDTO.class);
    }
}
