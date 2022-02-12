package com.aewm.microserviceproduct.configuration.Jwt.service;

import com.aewm.microserviceproduct.configuration.Jwt.Exception.JwtException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class JwtService  {
    private static final String BEARER = "Bearer ";
    private static final String USER = "user";
    private static final String ISSUER = "aewm";
    private static final String SECRET = "willyeatpenis";
    private static final String ROLES = "roles";



    public boolean checkToken(String token) {



        return token != null && token.startsWith(BEARER) && token.split("\\.").length == 3;

    }

    public String getUser(String token) throws JwtException {

        return this.decodeJWT(token).getClaim(USER).asString();


    }

    //validar token
    public DecodedJWT decodeJWT(String token) throws JwtException {

        if (!this.checkToken(token)) {
            throw new JwtException("Invalid token");
        }

        try {

            return JWT.require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build().verify(token.substring(BEARER.length()));


        } catch (Exception ex) {
            throw new JwtException("Error in decode jwt");
        }

    }

    public List<String> roles(String token) throws JwtException {

        return Arrays.asList(this.decodeJWT(token).getClaim(ROLES).asString());

    }


}
