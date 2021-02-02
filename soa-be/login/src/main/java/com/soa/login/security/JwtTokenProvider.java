package com.soa.login.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Autowired
    private JwtContext jwtContext;

    void addAuthentication(HttpServletResponse res, String username, String role) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + Long.valueOf(jwtContext.getExpiration())))
                .signWith(SignatureAlgorithm.HS512, jwtContext.getSecret())
                .compact();
        res.addHeader(jwtContext.getHeaderString(), jwtContext.getTokenPrefix() + " " + JWT);
        log.info("token" + JWT);
    }

    Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(jwtContext.getHeaderString());
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(jwtContext.getSecret())
                    .parseClaimsJws(token.replace(jwtContext.getTokenPrefix(), ""))
                    .getBody()
                    .getSubject();

            String authorization = Jwts.parser()
                    .setSigningKey(jwtContext.getSecret())
                    .parseClaimsJws(token.replace(jwtContext.getTokenPrefix(), ""))
                    .getBody()
                    .get("role").toString();

            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(authorization));
            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, authorities) :
                    null;
        }
        return null;
    }
}