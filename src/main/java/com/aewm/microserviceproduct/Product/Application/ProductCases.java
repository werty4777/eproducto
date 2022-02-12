package com.aewm.microserviceproduct.Product.Application;


import com.aewm.microserviceproduct.Product.Application.models.productoModel;
import com.aewm.microserviceproduct.Product.Application.models.productsModel;
import com.aewm.microserviceproduct.Product.Domain.Exception.ProductException;
import com.aewm.microserviceproduct.Product.Domain.IProduct;
import com.aewm.microserviceproduct.Product.Domain.product;
import com.aewm.microserviceproduct.Rating.Application.models.liked;
import com.aewm.microserviceproduct.Rating.Domain.IRating;
import com.aewm.microserviceproduct.Rating.Domain.rating;
import com.aewm.microserviceproduct.category.Domain.ICategory;
import com.aewm.microserviceproduct.customer.Domain.ICustomer;
import com.aewm.microserviceproduct.customer.Domain.customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductCases {


    private final ICategory iCategory;


    private final ICustomer iCustomer;

    private final IRating iRating;


    private final IProduct iProduct;

    @Autowired
    public ProductCases(ICategory iCategory, IRating iRating, IProduct iProduct, ICustomer iCustomer) {
        this.iCategory = iCategory;
        this.iRating = iRating;
        this.iProduct = iProduct;
        this.iCustomer = iCustomer;
    }


    public List<productsModel> lista() {
        return iProduct.findAll().stream().map(product -> {


            productsModel modelo = new productsModel();
            modelo.setNombre(product.getProduct_name());
            modelo.setPrecio(product.getUnite_price());
            modelo.setCategoria(product.getId_category().getDescription());
            modelo.setId(product.getId_product());
            modelo.setFoto(product.getImage());
             return modelo;

        }).collect(Collectors.toList());


    }


    public ResponseEntity<Map<String, Object>> getProductLiked() {


        List<liked> all = iRating.getLiked();


        Map<String, Object> mapa = new HashMap<>();
        try {
            List<liked> productlikeds = all.subList(0, 10);
            mapa.put("data", productlikeds);
            return new ResponseEntity<Map<String, Object>>(mapa, HttpStatus.OK);


        } catch (Exception ex) {
            mapa.put("data", all);
            return new ResponseEntity<Map<String, Object>>(mapa, HttpStatus.OK);
        }
    }


    public ResponseEntity<Map<String, Object>> searchProduct(String search) {
        try {
            List<liked> byPlaceContaining = iRating.search(search);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("data", byPlaceContaining);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ProductException("Error in search product");
        }
    }


    public ResponseEntity<Map<String, Object>> getAllProduct(int a) {
        int index = 0;
        Map<String, Object> mapa = new HashMap<>();
        try {


            if (a == 0) {
                index = (a * 10);
            } else if (a > 0) {
                index = (a * 10);
            }

            List<liked> all = iRating.getAll();

            if ((index + 10) > all.size()) {


                List<liked> productlikeds = all.subList(index, all.size());
                mapa.put("data", productlikeds);
                return new ResponseEntity<>(mapa, HttpStatus.OK);

            } else {
                List<liked> productlikeds = all.subList(index, index + 10);
                mapa.put("data", productlikeds);
                return new ResponseEntity<>(mapa, HttpStatus.OK);
            }


        } catch (Exception ex) {

            throw new ProductException("Error in search product /api/products/number");

        }


    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> addFav(int id) {


        try {
            Optional<product> byId = iProduct.findById(id);

            product error = byId.orElseThrow(() -> {
                return new ProductException("error");
            });

            String email = SecurityContextHolder.getContext().getAuthentication().getName();


            customer customer = getCustomer();
            boolean b = customer.addProduct(error);
            if (!b) {
                return new ResponseEntity<>(HttpStatus.IM_USED);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }


        } catch (Exception ex) {
            ex.printStackTrace();

            throw new ProductException("Error add product fav");

        }

    }

    public ResponseEntity<Map<String, Object>> favList(int a) {
        customer customer = getCustomer();

        System.out.println(customer);

        int index = 0;
        Map<String, Object> mapa = new HashMap<>();
        try {


            if (a == 0) {
                index = (a * 10);
            } else if (a > 0) {
                index = (a * 10);
            }

            List<liked> all = iRating.getfav(customer.getId_customer());

            if ((index + 10) > all.size()) {


                List<liked> productlikeds = all.subList(index, all.size());
                mapa.put("data", productlikeds);
                return new ResponseEntity<>(mapa, HttpStatus.OK);

            } else {
                List<liked> productlikeds = all.subList(index, index + 10);
                mapa.put("data", productlikeds);
                return new ResponseEntity<>(mapa, HttpStatus.OK);
            }


        } catch (Exception ex) {

            throw new ProductException("Error in search product /api/fav/{number}");

        }


    }


    public ResponseEntity<Map<String, Object>> status(int id) {
        try {
            Optional<product> byId = iProduct.findById(id);

            product error = byId.orElseThrow(() -> {
                return new ProductException("error");
            });

            customer customer = getCustomer();
            List<product> products = customer.getProducts();
            boolean contains = products.contains(error);
            int val = (contains) ? 1 : 0;
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("status", val);
            return new ResponseEntity<>(mapa, HttpStatus.OK);


        } catch (Exception ex) {
            throw new ProductException("Error");
        }


    }

    public customer getCustomer() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();


        Optional<Integer> customerFounded = iCustomer.findIdcustomer(name);
        Integer customerfo = customerFounded.orElseThrow(() -> new RuntimeException("Error customer not founded"));

        Optional<customer> customerById = iCustomer.findById(customerfo);

        return customerById.orElseThrow(() -> new RuntimeException("Erro customer not founded"));


    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> crearProducto(productoModel modelo) {


        try {
            product producto = new product();
            producto.setImage("null");
            producto.setProduct_description(modelo.getDescripcion());
            producto.setUnite_price(modelo.getPrecio());
            producto.setQuantity_per_unit(1);
            producto.setId_category(this.iCategory.findById(modelo.getIdcategoria()).get());
            producto.setProduct_name(modelo.getNombre());
            product save = this.iProduct.save(producto);
            rating rat = new rating();
            rat.setRating(BigDecimal.ZERO);
            rat.setId_product(save);

            rating save1 = this.iRating.save(rat);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error al crear el producto");
        }


    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> eliminarProducto(int id) {

        try {

            this.iProduct.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("error al eliminar el producto");

        }


    }
}
