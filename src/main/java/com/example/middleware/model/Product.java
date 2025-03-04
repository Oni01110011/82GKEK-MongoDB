package com.example.middleware.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor  // ✅ Wichtig für Jackson und MongoDB Deserialisierung
@Document
public class Product {
    private String productID;
    private String productName;
    private int productQuantity;
}