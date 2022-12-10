package io.github.luaprogrammer.poc.customer.rest.dto.response;

import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import io.github.luaprogrammer.poc.customer.entity.CorporateCustomer;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
public class CorporateCustomerResponseDTO extends CustomerResponseDTO{

    private String email;

    private Long phone;

    private String type;

   private String cnpj;

    private List<AddressResponseDTO> addresses;

    public static CorporateCustomerResponseDTO convertForDto(CorporateCustomer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, CorporateCustomerResponseDTO.class);
    }
}
