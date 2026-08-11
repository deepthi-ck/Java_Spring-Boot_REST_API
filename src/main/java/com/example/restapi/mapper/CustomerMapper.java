package com.example.restapi.mapper;

import com.example.restapi.dto.CustomerRequest;
import com.example.restapi.model.Address;
import com.example.restapi.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toEntity(Long id, CustomerRequest request) {
        Address address = new Address(
                request.getLine1(),
                request.getCity(),
                request.getState(),
                request.getPostalCode(),
                request.getCountry()
        );
        return new Customer(id, request.getFullName(), request.getEmail(), request.getPhone(), address);
    }

    public void updateEntity(Customer customer, CustomerRequest request) {
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(new Address(
                request.getLine1(),
                request.getCity(),
                request.getState(),
                request.getPostalCode(),
                request.getCountry()
        ));
    }
}
