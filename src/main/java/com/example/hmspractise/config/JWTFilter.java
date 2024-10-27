package com.example.hmspractise.config;

import com.example.hmspractise.Model.UserData;
import com.example.hmspractise.repository.UserDataRepository;
import com.example.hmspractise.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserDataRepository useRep;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String tokenVal=request.getHeader("Authorization");

        if(tokenVal!=null && tokenVal.startsWith("Bearer ")){

            String tokenVall=tokenVal.substring(8,tokenVal.length()-1);

            String tokenUsername=jwtService.getUsernamefromToke(tokenVall);

            Optional<UserData>opUsername=useRep.findByUsername(tokenUsername);

            if(opUsername.isPresent()){
                UserData usd= opUsername.get();
                UsernamePasswordAuthenticationToken userAuth= new UsernamePasswordAuthenticationToken(opUsername,
                        null,Collections.singleton(new SimpleGrantedAuthority(usd.getRole())));
                userAuth.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userAuth);
            }

        }
        filterChain.doFilter(request,response);



    }
}
