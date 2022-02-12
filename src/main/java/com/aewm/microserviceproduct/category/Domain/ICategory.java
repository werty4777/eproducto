package com.aewm.microserviceproduct.category.Domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategory  extends JpaRepository<category,Integer> {
}
