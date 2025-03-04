package com.example.middleware.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "products")
public class Product {

    private String productID;
    private String productName;
    private int productQuantity;

    @JsonCreator
    public Product(
            @JsonProperty("productID") String productID,
            @JsonProperty("productName") String productName,
            @JsonProperty("productQuantity") int productQuantity) {
        this.productID = productID;
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public Product(String productID, String bioOrangensaftSonne, String getraenk) {
    }
}
