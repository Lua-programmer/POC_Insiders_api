package io.github.luaprogrammer.poc.address.service.impl;

import com.google.gson.Gson;
import io.github.luaprogrammer.poc.address.entity.Address;
import io.github.luaprogrammer.poc.address.repository.AddressRepository;
import io.github.luaprogrammer.poc.address.rest.dto.request.AddressRequestDTO;
import io.github.luaprogrammer.poc.address.rest.dto.response.AddressResponseDTO;
import io.github.luaprogrammer.poc.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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
            throw new RuntimeException("id not found");
        }

        return AddressResponseDTO.convertForDto(address.get());
    }

    @Override
    public AddressResponseDTO saveAddress(AddressRequestDTO requestAddress) throws Exception {


        URL url = new URL("https://viacep.com.br/ws/"+requestAddress.getCep()+"/json/");
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String cep = "";
        StringBuilder jsonCep = new StringBuilder();
        while((cep = reader.readLine()) != null) {
            jsonCep.append(cep);
        }

        AddressResponseDTO addressAux = new Gson().fromJson(jsonCep.toString(), AddressResponseDTO.class);
        requestAddress.setCep(addressAux.getCep());
        requestAddress.setLogradouro(addressAux.getLogradouro());
        requestAddress.setBairro(addressAux.getBairro());
        requestAddress.setLocalidade(addressAux.getLocalidade());
        requestAddress.setUf(addressAux.getUf());


        Address addressSaved = aRepository.save(requestAddress.convertForEntity());
        return AddressResponseDTO.convertForDto(addressSaved);
    }

    @Override
    public AddressResponseDTO updateAddress(UUID id, AddressRequestDTO address) {
        Optional<Address> addressSaved = aRepository.findById(id);
        if (addressSaved.isEmpty()) {
            throw new RuntimeException("id not found");
        } else {
            BeanUtils.copyProperties(address, addressSaved);
        }

        Address addressUpdate = address.convertForEntity(id);
        Address newAddress = aRepository.save(addressUpdate);
        return AddressResponseDTO.convertForDto(newAddress);
    }


    @Override
    public void deleteAddress(UUID id) {
        Optional<Address> address = aRepository.findById(id);
        if (address.isEmpty()) {
            throw new RuntimeException("id not found");
        }
        aRepository.deleteById(address.get().getId());
    }

}
