package com.vimal.code.ToDo.Auth;

import com.vimal.code.ToDo.exp.AuthError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private Logger logger = LoggerFactory.getLogger(JWTAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Log the authentication exception
        logger.error("Authentication failed: " + authException.getMessage(), authException);

        // Send an error response
        PrintWriter writer = response.getWriter();
        writer.println("Authentication failed: " + authException.getMessage());
    }

}
