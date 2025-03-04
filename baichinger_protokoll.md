# 📜 Projektprotokoll – Document Oriented Middleware using MongoDB
**Projektname:** Document Oriented Middleware using MongoDB
**Autor:** *Bernhard Aichinger-Ganas*
**Datum:** *04.03.2025*
**Repository:** https://github.com/Oni01110011/82GKEK-MongoDB.git

---

## 📌 1. Einführung
Dieses Projekt implementiert eine **dokumentenorientierte Middleware mit MongoDB**. Die Middleware speichert **Lagerstandorte (`warehouses`) und deren Produkte (`products`)** in einer NoSQL-Datenbank.
Die REST-API ermöglicht das **Erstellen, Abrufen und Löschen von Lagerstandorten und Produkten**.

### **Technologien:**
- **Spring Boot 3.2.2**
- **Spring Data MongoDB**
- **Swagger UI** für API-Dokumentation
- **Docker** für die MongoDB-Instanz
- **Gradle** als Build-Tool

---

## 📌 2. Auftretende Fehler und deren Lösungen

### 🔴 **Fehler 1: `Class com.sun.tools.javac.tree.JCTree$JCImport does not have member field`**
**❌ Ursache:** Inkompatibilität zwischen Lombok, JDK 21 und Gradle.
**✅ Lösung:**
- **JDK 17 statt JDK 21 verwenden**
- **Lombok-Version aktualisieren**
- **Gradle-Cache löschen:**
  ```sh
  ./gradlew clean build --refresh-dependencies
  ```

---

### 🔴 **Fehler 2: `Could not determine dependencies of task ':build'.`**
**❌ Ursache:** Inkompatibilität zwischen **Spring Boot 3.1.x und Gradle 8.8**.
**✅ Lösung:**
- **Spring Boot auf 3.2.2 aktualisieren**
- **Layered JAR deaktivieren in `build.gradle`:**
  ```gradle
  tasks.withType(BootJar).configureEach {
      layered = false
  }
  ```
- **Gradle-Version prüfen (`./gradlew -version`)**
Falls nötig, auf Gradle 8.5+ aktualisieren.

---

### 🔴 **Fehler 3: `StringIndexOutOfBoundsException` in Swagger UI**
**❌ Ursache:** **Springdoc OpenAPI 2.1.0 ist fehlerhaft**.
**✅ Lösung:** **Springdoc OpenAPI auf 2.0.2 downgraden**:
```gradle
dependencies {
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2'
}
```

---

### 🔴 **Fehler 4: `MappingException: No property number found on entity class Warehouse`**
**❌ Ursache:** **Spring Data MongoDB benötigt einen Standardkonstruktor.**
**✅ Lösung:** `@NoArgsConstructor` in `Warehouse.java` hinzufügen:
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
```

---

### 🔴 **Fehler 5: Leere `null`-Werte in Swagger UI**
**❌ Ursache:** **Fehlende Standardwerte in `Warehouse.java`**.
**✅ Lösung:** Standardwerte mit `@Builder.Default` setzen:
```java
@Builder.Default
private String warehouseName = "Unbekanntes Lager";
```

---

### 🔴 **Fehler 6: `MissingPathVariableException` bei DELETE**
**❌ Ursache:** **Falsche oder fehlende `id` im DELETE-Request**.
**✅ Lösung:**
- Korrekte API in `WarehouseController.java` sicherstellen:
  ```java
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteWarehouse(@PathVariable String id) { ... }
  ```
- **DELETE-URL richtig aufrufen:**
  ```sh
  DELETE http://localhost:8080/warehouse/1
  ```

---

### 🔴 **Fehler 7: `InvalidDefinitionException: Cannot construct instance of Product`**
**❌ Ursache:** **Jackson kann `Product` nicht korrekt serialisieren/deserialisieren.**
**✅ Lösung:**
- `@NoArgsConstructor` in `Product.java` hinzufügen:
  ```java
  @NoArgsConstructor
  ```
- **Falls nötig `@JsonCreator` und `@JsonProperty` verwenden:**
  ```java
  @JsonCreator
  public Product(
      @JsonProperty("productID") String productID,
      @JsonProperty("productName") String productName,
      @JsonProperty("productQuantity") int productQuantity) {
      this.productID = productID;
      this.productName = productName;
      this.productQuantity = productQuantity;
  }
  ```

---

## 📌 3. Wichtige MongoDB-Befehle

### 💾 **Datenbank anzeigen**
```sh
show dbs
```

### 🔍 **Alle Lagerstandorte anzeigen**
```sh
use warehouseDB
db.warehouses.find().pretty()
```

### 🔍 **Lagerbestand eines bestimmten Produkts über alle Lager**
```sh
db.warehouses.aggregate([
  { $unwind: "$productData" },
  { $match: { "productData.productName": "Bio Orangensaft Sonne" } },
  { $group: { _id: "$productData.productName", totalQuantity: { $sum: "$productData.productQuantity" } } }
])
```

### 🔍 **Lagerbestand eines Produkts in einem bestimmten Lager**
```sh
db.warehouses.find(
  { "warehouseID": "1", "productData.productName": "Bio Orangensaft Sonne" },
  { "productData.$": 1 }
)
```

---

## 📌 4. Theoretische Fragen & Antworten

### **1️⃣ 4 Vorteile eines NoSQL Repository im Vergleich zu relationalen DBMS**
1. **Flexible Datenstruktur** – JSON-basierte Speicherung erlaubt einfache Anpassungen.
2. **Hohe Skalierbarkeit** – Horizontale Skalierung über mehrere Server möglich.
3. **Schnelle Abfragen bei großen Datenmengen** – Keine teuren Joins wie in relationalen Datenbanken.
4. **Bessere Performance für bestimmte Workloads** – Besonders bei **Lese- und Schreibintensiven Anwendungen**.

---

### **2️⃣ 4 Nachteile eines NoSQL Repository**
1. **Fehlende ACID-Transaktionen** – Nicht so konsistent wie relationale Datenbanken.
2. **Komplexe Abfragen schwieriger** – Kein SQL, daher sind Joins nicht direkt möglich.
3. **Datenkonsistenz kann problematisch sein** – Eventual Consistency statt Strong Consistency.
4. **Nicht immer für jede Anwendung geeignet** – Relationale Datenbanken sind oft sinnvoller bei **hochstrukturierten Daten**.

---

### **3️⃣ Schwierigkeiten bei der Zusammenführung von Daten**
- **Unterschiedliche Datenformate** (JSON-Dokumente haben keine feste Struktur).
- **Duplikate und inkonsistente Daten** (MongoDB speichert oft redundante Daten).
- **Aggregation ist aufwendiger** (NoSQL benötigt Aggregation Pipelines statt Joins).
- **Eventual Consistency** – Daten müssen synchronisiert werden.

---

### **4️⃣ Arten von NoSQL-Datenbanken und Vertreter**
| NoSQL-Typ          | Vertreter  |
|--------------------|------------|
| **Dokumentenorientiert** | MongoDB, CouchDB |
| **Spaltenorientiert** | Apache Cassandra, HBase |
| **Key-Value Store** | Redis, DynamoDB |
| **Graph-Datenbanken** | Neo4j, ArangoDB |

---

### **5️⃣ CAP-Theorem: CA, CP, AP**
- **CA (Consistency & Availability)** → **Nicht möglich in verteilten Systemen**.
- **CP (Consistency & Partition Tolerance)** → **MongoDB, HBase**.
- **AP (Availability & Partition Tolerance)** → **Cassandra, DynamoDB**.

---

*Produkt hinzufügen:*


db.warehouses.updateOne(
    { _id: "warehouse1" },
    { $push: { products: { productId: "p10", name: "Schlüssel", category: "Werkzeug", quantity: 50 } } }
)



*Bestand aktualisieren:*


db.warehouses.updateOne(
    { "products.productId": "p10" },
    { $set: { "products.$.quantity": 120 } }
)



*Produkt löschen:*


db.warehouses.updateOne(
    { _id: "warehouse1" },
    { $pull: { products: { productId: "p10" } } }
)


