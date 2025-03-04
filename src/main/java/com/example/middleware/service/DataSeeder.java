package com.example.middleware.service;

import com.example.middleware.model.Product;
import com.example.middleware.model.Warehouse;
import com.example.middleware.repository.WarehouseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final WarehouseRepository warehouseRepository;

    public DataSeeder(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void run(String... args) {
        // Definierte Produktlisten für verschiedene Lagerstandorte
        List<Product> warehouse1Products = Arrays.asList(
                new Product("00-443175", "Bio Orangensaft Sonne", 2500),
                new Product("00-871895", "Bio Apfelsaft Gold", 3420),
                new Product("01-926885", "Ariel Waschmittel Color", 478)
        );

        List<Product> warehouse2Products = Arrays.asList(
                new Product("00-111222", "Milch 3.5%", 1500),
                new Product("00-333444", "Vollkorn Brot", 1200),
                new Product("00-555666", "Butter 250g", 800)
        );

        // Korrekt definierte Lagerstandorte
        Warehouse warehouse1 = new Warehouse(
                "1",  // String-ID manuell setzen
                "Linz Bahnhof",
                "2025-03-04 12:00:00",
                4010,
                "Linz",
                "Austria",
                warehouse1Products
        );

        Warehouse warehouse2 = new Warehouse(
                "2",  // String-ID manuell setzen
                "Wien Zentrum",
                "2025-03-04 12:30:00",
                1010,
                "Wien",
                "Austria",
                warehouse2Products
        );

        // Nur vollständige Objekte speichern
        warehouseRepository.saveAll(Arrays.asList(warehouse1, warehouse2));

        System.out.println("✅ Testdaten erfolgreich in die MongoDB eingefügt.");
    }
}
