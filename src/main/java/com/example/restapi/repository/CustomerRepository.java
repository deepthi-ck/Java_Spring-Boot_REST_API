package com.example.restapi.repository;

import com.example.restapi.model.Customer;
import com.example.restapi.util.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CustomerRepository {
    private final ConcurrentHashMap<Long, Customer> store = new ConcurrentHashMap<>();
    private final IdGenerator ids = new IdGenerator(1);

    public List<Customer> findAll() { return new ArrayList<>(store.values()); }

    public Optional<Customer> findById(Long id) { return Optional.ofNullable(store.get(id)); }

    public Optional<Customer> findByEmail(String email) {
        return store.values().stream().filter(c -> c.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(ids.nextId());
        }
        store.put(customer.getId(), customer);
        return customer;
    }

    public boolean deleteById(Long id) { return store.remove(id) != null; }

    public long count() { return store.size(); }
}
