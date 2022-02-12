package com.aewm.microserviceproduct.Product.Domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProduct  extends JpaRepository<product,Integer> {

}
