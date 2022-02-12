package com.aewm.microserviceproduct.Rating.Domain;


import com.aewm.microserviceproduct.Rating.Application.models.liked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRating  extends JpaRepository<rating,Integer> {
    @Query(value = "select * from liked;",nativeQuery = true)
    public List<liked> getLiked();

    @Query(value="select * from liked where product_name like CONCAT('%',:product,'%')" ,nativeQuery=true)
    public List<liked> search(@Param("product")String a);


    @Query(value="select * from liked where id_product=?;   " ,nativeQuery=true)
    public List<liked> ratingProduct(int id);


    @Query(value = "select * from liked" , nativeQuery=true)
    public List<liked> getAll();


    @Query(value = "select pt.id_product,pt.product_name,pt.image,pt.unite_price,pt.product_description,0.0 as rating from fav_product fp inner join customer ct on (ct.id_customer=fp.id_customer) inner join product pt on(fp.id_product=pt.id_product) where ct.id_customer=?;",nativeQuery = true)
    public List<liked> getfav(int a);
}
