package com.aewm.microserviceproduct.configuration.filters;

import com.aewm.microserviceproduct.configuration.Jwt.Exception.JwtException;
import com.aewm.microserviceproduct.configuration.Jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.persistence.EntityManager;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class FilterValidation extends BasicAuthenticationFilter {


    @Autowired
    EntityManager manager;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager managerbuil;

    public FilterValidation(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String auth = request.getHeader("authorization");
        System.out.println("Entro aca");
        System.out.println(auth);
        try {

            if (jwtService.checkToken(auth)) {


                String user = jwtService.getUser(auth);





                List<String> roles = jwtService.roles(auth);
                List<GrantedAuthority> rolesauth = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, rolesauth);
                SecurityContextHolder.getContext().setAuthentication(token);


                chain.doFilter(request, response);

            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }


        } catch (Exception | JwtException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;

        }


    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path=request.getServletPath();

        if(path.startsWith("/rating") || path.startsWith("/api/products")){
            return true;
        }else{
            return false;
        }


    }


}
