package com.aewm.microserviceproduct.Product.Domain;

import com.aewm.microserviceproduct.category.Domain.category;
import com.aewm.microserviceproduct.supplier.Domain.supplier;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class product  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_product;
    @OneToOne
    @JoinColumn(name = "id_category")
    @JsonIgnore
    private category id_category;
    @Column(length = 40)
    private String product_name;
    @Column
    private String image;
    @Column(precision = 10, scale = 6)
    private BigDecimal unite_price;
    @Column
    private int quantity_per_unit;
    @Column
    private String product_description;

    public supplier getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(supplier supplier_id) {
        this.supplier_id = supplier_id;
    }

    @OneToOne
    @JoinColumn(name = "supplier_id")
    private supplier supplier_id;


    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public category getId_category() {
        return id_category;
    }

    public void setId_category(category id_category) {
        this.id_category = id_category;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getUnite_price() {
        return unite_price;
    }

    public void setUnite_price(BigDecimal unite_price) {
        this.unite_price = unite_price;
    }

    public int getQuantity_per_unit() {
        return quantity_per_unit;
    }

    public void setQuantity_per_unit(int quantity_per_unit) {
        this.quantity_per_unit = quantity_per_unit;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }
}
