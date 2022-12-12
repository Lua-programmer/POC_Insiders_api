package io.github.luaprogrammer.poc.address.service.impl;

import com.google.gson.Gson;
import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.address.repository.AddressRepository;
import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import io.github.luaprogrammer.poc.address.service.AddressService;
import io.github.luaprogrammer.poc.customer.entity.Customer;
import io.github.luaprogrammer.poc.exception.RuleBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository aRepository;

    @Override
    public Page<AddressResponseDTO> findAllAddress(Pageable pageable) {
        return aRepository.findAll(pageable).map(AddressResponseDTO::convertForDto);
    }

    @Override
    public AddressResponseDTO findAddressById(UUID id) {
        Optional<Address> address = aRepository.findById(id);
        if (address.isEmpty()) {
            throw new EmptyResultDataAccessException("id not found", 404);
        }

        return AddressResponseDTO.convertForDto(address.get());
    }

    @Override
    public Address saveAddress(AddressRequestDTO requestAddress) throws Exception {

        Address address = validateCep(requestAddress);

        return aRepository.save(address);
    }

    @Override
    public AddressResponseDTO updateAddress(UUID id, AddressRequestDTO requestAddress) throws Exception {
        Optional<Address> addressSaved = aRepository.findById(id);
        if (addressSaved.isEmpty()) {
            throw new EmptyResultDataAccessException("id not found", 404);
        }

        Address addressUpdated = validateCep(requestAddress);
        if (Boolean.TRUE.equals(requestAddress.getIsPrincipal())) {
            throw new RuleBusinessException("A primary address already exists for this customer.");
        }
        for (int i = 0; i < addressSaved.get().getCustomer().getAddresses().size(); i++) {
            if (addressSaved.get().getCustomer().getAddresses().get(i).getLogradouro().equals(requestAddress.getLogradouro())
            && !addressSaved.get().getCustomer().getAddresses().get(i).getId().equals(id)) {
                throw new RuleBusinessException("This zip code is already registered for this customer.");
            }
        }
        BeanUtils.copyProperties(addressSaved, addressUpdated);
        addressUpdated.setId(addressSaved.get().getId());
        addressUpdated.setCustomer(addressSaved.get().getCustomer());

        Address save = aRepository.save(addressUpdated);
        return AddressResponseDTO.convertForDto(save);
    }


    @Override
    public void deleteAddress(UUID id) {
        Address address = aRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("id not found", 404)
        );

        if (address.getCustomer() != null) {
            throw new RuleBusinessException("Address cannot be deleted because there is a Customer linked to it.");
        }
        aRepository.deleteById(address.getId());
    }

    private Address validateCep(AddressRequestDTO requestAddress) throws Exception {

        URL url = new URL("https://viacep.com.br/ws/" + requestAddress.getCep() + "/json/");
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String cep = "";
        StringBuilder jsonCep = new StringBuilder();
        while ((cep = reader.readLine()) != null) {
            jsonCep.append(cep);
        }

        AddressResponseDTO addressAux = new Gson().fromJson(jsonCep.toString(), AddressResponseDTO.class);
        requestAddress.setCep(addressAux.getCep());
        requestAddress.setLogradouro(addressAux.getLogradouro());
        requestAddress.setBairro(addressAux.getBairro());
        requestAddress.setLocalidade(addressAux.getLocalidade());
        requestAddress.setUf(addressAux.getUf());

        return requestAddress.convertForEntity();
    }

}
