package com.rizzatto.makeachat.infrastructure.security.filter;

import com.rizzatto.makeachat.domain.user.User;
import com.rizzatto.makeachat.domain.user.UserRepository;
import com.rizzatto.makeachat.domain.user.UserService;
import com.rizzatto.makeachat.infrastructure.security.service.CookieService;
import com.rizzatto.makeachat.infrastructure.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    CookieService cookieService;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = cookieService.recoverCookieToken(request);
        if(token != null){
            UUID userPublicId = UUID.fromString(jwtService.getSubjectFromToken(token));
            User user = userService.findUserByPublicID(userPublicId);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
