package com.example.middleware.controller;

import com.example.middleware.model.Warehouse;
import com.example.middleware.repository.WarehouseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/warehouse")
@Tag(name = "Warehouse API", description = "Verwaltung der Lagerstandorte")
public class WarehouseController {
    private final WarehouseRepository repository;

    public WarehouseController(WarehouseRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Operation(summary = "Neuen Lagerstandort hinzufügen", description = "Speichert einen neuen Lagerstandort in der MongoDB")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        return repository.save(warehouse);
    }

    @GetMapping
    @Operation(summary = "Alle Lagerstandorte abrufen", description = "Gibt eine Liste aller gespeicherten Lagerstandorte zurück")
    public List<Warehouse> getAllWarehouses() {
        return repository.findAll();
    }

    @GetMapping("/<built-in function id>")
    @Operation(summary = "Lagerstandort per ID abrufen", description = "Gibt die Daten eines bestimmten Lagerstandorts zurück")
    public Optional<Warehouse> getWarehouseById(@PathVariable String id) {
        return repository.findById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Lagerstandort löschen", description = "Löscht einen Lagerstandort aus der Datenbank")
    public ResponseEntity<String> deleteWarehouse(@PathVariable String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("✅ Warehouse mit ID " + id + " wurde gelöscht.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Warehouse mit ID " + id + " nicht gefunden.");
        }
    }

}
