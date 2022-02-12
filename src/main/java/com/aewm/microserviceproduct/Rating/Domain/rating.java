package com.aewm.microserviceproduct.Rating.Domain;


import com.aewm.microserviceproduct.Product.Domain.product;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_rating;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product")
    private product id_product;
    @Column
    private BigDecimal rating;

    public int getId_rating() {
        return id_rating;
    }

    public void setId_rating(int id_rating) {
        this.id_rating = id_rating;
    }

    public product getId_product() {
        return id_product;
    }

    public void setId_product(product id_product) {
        this.id_product = id_product;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

}
