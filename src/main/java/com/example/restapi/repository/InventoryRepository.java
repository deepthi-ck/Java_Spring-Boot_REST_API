package com.example.restapi.repository;

import com.example.restapi.model.InventoryMovement;
import com.example.restapi.util.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class InventoryRepository {
    private final List<InventoryMovement> movements = new CopyOnWriteArrayList<>();
    private final IdGenerator ids = new IdGenerator(1);

    public InventoryMovement save(InventoryMovement movement) {
        if (movement.getId() == null) {
            movement.setId(ids.nextId());
        }
        movements.add(movement);
        return movement;
    }

    public List<InventoryMovement> findByItemId(Long itemId) {
        return movements.stream().filter(m -> m.getItemId().equals(itemId)).collect(Collectors.toList());
    }

    public List<InventoryMovement> findAll() {
        return new ArrayList<>(movements);
    }
}
