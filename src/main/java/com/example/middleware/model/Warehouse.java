package com.example.middleware.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor  // ✅ WICHTIG: Standard-Konstruktor hinzufügen!
@Document(collection = "warehouses")
public class Warehouse {
    @Id
    private String warehouseID;
    private String warehouseName;
    private String timestamp;
    private int warehousePostalCode;
    private String warehouseCity;
    private String warehouseCountry;
    private List<Product> productData;

    public Warehouse(String number, String linzBahnhof, String s, int i, String linz, String austria, List<Product> warehouse1Products) {
    }
}


