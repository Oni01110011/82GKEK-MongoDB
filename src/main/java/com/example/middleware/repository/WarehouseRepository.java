package com.example.middleware.repository;

import com.example.middleware.model.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
}
