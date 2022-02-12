package com.aewm.microserviceproduct.customer.Domain;

import com.aewm.microserviceproduct.Product.Domain.product;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class ,property = "id_user",scope = customer.class)
public class customer implements Serializable {

    @Override
    public String toString() {
        return "customer{" +
                "id_customer=" + id_customer +
                ", id_user=" + iduser +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", photo='" + photo + '\'' +
                ", province='" + province + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_customer;

    private int iduser;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String last_name;
    @Column(length = 250)
    private String photo;
    @Column(length = 30)
    private String province;
    @Column(length = 100)
    private String address;
    @Pattern(regexp = "^(9)\\d{8}")
    @Column(length = 10)
    private String phone;


    @JoinTable(name = "fav_product",joinColumns = @JoinColumn(name = "id_customer",nullable = false),
            inverseJoinColumns =@JoinColumn(name = "id_product",nullable = false))
    @ManyToMany(cascade = CascadeType.ALL)
    private List<product> products;



    public List<product> getProducts() {
        return products;
    }

    public void setProducts(List<product> products) {
        this.products = products;
    }

    public boolean addProduct(product p){
        if(this.products==null){
            this.products=new ArrayList<>();
            return false;

        }else if(!this.products.contains(p)){
            this.products.add(p);
            return true;
        }else{
            this.products.remove(p);
            return false;
        }

    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
