package com.aewm.microserviceproduct.Rating.Application;

import com.aewm.microserviceproduct.Rating.Application.models.liked;
import com.aewm.microserviceproduct.Rating.Domain.IRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ratingCases {



    private IRating iRating;

    @Autowired
    public ratingCases(IRating iRating) {
        this.iRating = iRating;
    }

    public List<liked> getLista(int id) {

        return iRating.ratingProduct(id);
    }
}
