package com.aewm.microserviceproduct.Rating.Infrastructure.api;

import com.aewm.microserviceproduct.Rating.Application.models.liked;
import com.aewm.microserviceproduct.Rating.Application.ratingCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("permitAll")
@RequestMapping("/rating")
public class RatingController {



    private ratingCases ratingCases;

    @Autowired
    public RatingController(ratingCases ratingCases) {
        this.ratingCases = ratingCases;
    }

    @GetMapping("/lista/{id}")
    public List<liked> listaRating(@PathVariable("id") int id){

        return ratingCases.getLista(id);


    }
}
