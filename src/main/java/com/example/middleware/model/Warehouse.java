package com.example.middleware.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor  // ✅ Standard-Konstruktor, um Jackson und MongoDB-Probleme zu vermeiden
@Builder
@Document(collection = "warehouses")
public class Warehouse {
    @Id
    private String warehouseID;

    @Builder.Default
    private String warehouseName = "Unbekanntes Lager";

    @Builder.Default
    private String timestamp = "2025-03-04 00:00:00";

    @Builder.Default
    private int warehousePostalCode = 9999;

    @Builder.Default
    private String warehouseCity = "Unbekannte Stadt";

    @Builder.Default
    private String warehouseCountry = "Unbekanntes Land";

    @Builder.Default
    private List<Product> productData = new ArrayList<>();
}
