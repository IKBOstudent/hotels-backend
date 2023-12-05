package ru.rschir.hotelsbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.rschir.hotelsbackend.entities.User;

import javax.crypto.SecretKey;
import java.security.SignatureException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class JwtProvider {

    private final SecretKey jwtAccessSecret;

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(User user) {
        long expirationMinutes = 10;
        Date accessExpiration = Date.from(LocalDateTime.now()
                                                  .plusMinutes(expirationMinutes)
                                                  .atZone(ZoneId.systemDefault())
                                                  .toInstant());

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .compact();
    }

    public Claims validateToken(String token) throws ExpiredJwtException, SignatureException {
        return (Claims) Jwts.parserBuilder()
                .setSigningKey(jwtAccessSecret)
                .build()
                .parse(token)
                .getBody();
    }
}