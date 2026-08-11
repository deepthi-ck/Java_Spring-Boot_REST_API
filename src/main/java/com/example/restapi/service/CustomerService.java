package com.example.restapi.service;

import com.example.restapi.dto.CustomerRequest;
import com.example.restapi.exception.ConflictException;
import com.example.restapi.exception.ResourceNotFoundException;
import com.example.restapi.mapper.CustomerMapper;
import com.example.restapi.model.Customer;
import com.example.restapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<Customer> findAll() { return customerRepository.findAll(); }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    public Customer create(CustomerRequest request) {
        customerRepository.findByEmail(request.getEmail()).ifPresent(c -> {
            throw new ConflictException("Email already registered: " + request.getEmail());
        });
        return customerRepository.save(customerMapper.toEntity(null, request));
    }

    public Customer update(Long id, CustomerRequest request) {
        Customer customer = findById(id);
        customerRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ConflictException("Email already registered: " + request.getEmail());
            }
        });
        customerMapper.updateEntity(customer, request);
        return customerRepository.save(customer);
    }

    public void delete(Long id) {
        if (!customerRepository.deleteById(id)) {
            throw new ResourceNotFoundException("Customer not found: " + id);
        }
    }

    public long count() { return customerRepository.count(); }
}
