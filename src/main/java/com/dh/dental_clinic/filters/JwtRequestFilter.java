package com.dh.dental_clinic.filters;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dh.dental_clinic.services.impl.JwtUtilService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    public Optional<String> getJWT(HttpServletRequest request) {
        String cookieHeader = request.getHeader("Cookie");
        logger.info("cookies: " + cookieHeader);
        if (cookieHeader != null) {
            String[] cookies = cookieHeader.split("; ");
            String jwt;

            for (String cookie : cookies) {
                if (cookie.startsWith("userData=")) {
                    String cookieValue = cookie.substring("userData=".length());
                    int startIndex = cookieValue.indexOf("\"jwt\":\"") + 7;
                    int endIndex = cookieValue.lastIndexOf("\"}");

                    if (startIndex != -1 && endIndex != -1) {
                        jwt = cookieValue.substring(startIndex, endIndex);
                    } else {
                        throw new IllegalArgumentException("Formato de cookie incorrecto");
                    }
                    return jwt == null ?  Optional.empty() : Optional.of(jwt);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String authorizationHeader = request.getHeader("Authorization");

        if (httpRequest.getRequestURI().contains("/h2-console")) {
            logger.info("h2-console");
            // logger.info(getJWT(request).get());
            Optional<String> jwt = getJWT(request);
            if (jwt.isPresent()) {
                logger.info("jwtForH2Console: " + jwt.get());
                authorizationHeader = "Bearer " + jwt.get();
            }
        }

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtilService.extractUsername(jwt);
            logger.info("username: " + username);
            logger.info("context: " + SecurityContextHolder.getContext().getAuthentication());
        }

        // && SecurityContextHolder.getContext().getAuthentication() == null

        if (username != null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            logger.info("userDetails: " + userDetails);

            if (jwtUtilService.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                logger.info("authority: " + userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
