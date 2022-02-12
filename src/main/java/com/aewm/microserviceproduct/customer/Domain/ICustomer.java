package com.aewm.microserviceproduct.customer.Domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomer  extends JpaRepository<customer,Integer> {

    @Query(value = "select cus.id_customer from login  log inner join customer cus on(cus.iduser=log.id_user) where log.email=?",nativeQuery = true)
    Optional<Integer> findIdcustomer(String email);
}
