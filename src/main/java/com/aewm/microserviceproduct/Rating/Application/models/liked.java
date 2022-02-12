package com.aewm.microserviceproduct.Rating.Application.models;

import java.math.BigDecimal;

public interface liked {
    int  getId_product();
    String  getProduct_name();
    String getImage();
    BigDecimal getUnite_price();
    String getProduct_description();
    BigDecimal getRating();
}
