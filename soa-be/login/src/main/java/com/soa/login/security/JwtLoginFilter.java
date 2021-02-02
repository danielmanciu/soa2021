package com.soa.login.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soa.login.config.AuthenticationRequest;
import com.soa.login.helpers.EndpointConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;

@Component
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    final private JwtTokenProvider tokenProvider;

    @Autowired
    public JwtLoginFilter(@Value(EndpointConstants.LOGIN) String url, AuthenticationManager authManager, JwtTokenProvider tokenProvider) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException {
        AuthenticationRequest credentials = new ObjectMapper().readValue(req.getInputStream(), AuthenticationRequest.class);
        String decodedUserName = new String(Base64.getDecoder().decode(credentials.getUserName()));
        String decodedPassword = new String(Base64.getDecoder().decode(credentials.getPassword()));
        System.out.println("username: " + decodedUserName + ", pwd: " + decodedPassword);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(decodedUserName, decodedPassword));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth
    ) throws IOException, ServletException {
        tokenProvider.addAuthentication(res, auth.getName(), ((GrantedAuthority) auth.getAuthorities().toArray()[0]).getAuthority());
    }
}