package com.aewm.microserviceproduct.Product.Infrastructure.api;

import com.aewm.microserviceproduct.Product.Application.ProductCases;
import com.aewm.microserviceproduct.Product.Application.models.productoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class productController {


    @Autowired
    ProductCases getProduct;


    @PreAuthorize("permitAll")
    @GetMapping("/products/fixed-search")
    @Cacheable(value = "productosliked")
    public ResponseEntity<?> liked() {
        return getProduct.getProductLiked();
    }


    @PreAuthorize("permitAll")
    @GetMapping("/products/search/{letter}")
    public ResponseEntity<?> search(@PathVariable("letter") String a) {
        return getProduct.searchProduct(a);

    }


    @PreAuthorize("permitAll")
    @GetMapping(path = "/products/{numero}")
    @Cacheable(value = "productos")
    public ResponseEntity<?> getProducts(@PathVariable("numero") int a) {

        return getProduct.getAllProduct(a);

    }

    @PreAuthorize("authenticated")
    @PostMapping("/fav/{id}")
    public ResponseEntity<?> getFav(@PathVariable("id") int a) {

        return getProduct.addFav(a);
    }

    @PreAuthorize("authenticated")
    @GetMapping("/fav/{id}")
    public ResponseEntity<?> getStatus(@PathVariable("id") int a) {

        return getProduct.status(a);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/favs/{id}")
    public ResponseEntity<?> getFavs(@PathVariable("id") int a) {
        return getProduct.favList(a);
    }


    @PreAuthorize("hasRole('admin')")
    @PostMapping("/product")
    @CacheEvict(value = "productos",allEntries = true)
    public ResponseEntity<?> crearProducto(@RequestBody productoModel modelo) {


        return this.getProduct.crearProducto(modelo);

    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/product/{id}")
    @CacheEvict(value = "productos",allEntries = true)
    public ResponseEntity<?> eliminarProducto(@PathVariable("id") int id) {

        return this.getProduct.eliminarProducto(id);

    }

     @PreAuthorize("permitAll")
    @GetMapping("/product/lista")
    public List listaProducto(){


        return  this.getProduct.lista();

    }



}
