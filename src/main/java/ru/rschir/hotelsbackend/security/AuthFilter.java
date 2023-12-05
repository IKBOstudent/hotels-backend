package ru.rschir.hotelsbackend.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import ru.rschir.hotelsbackend.entities.User;
import ru.rschir.hotelsbackend.services.UserService;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final Pattern AUTH_HEADER_PATTERN = Pattern.compile("Bearer ([^ ]+)");

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            Optional<String> token = extractToken(request);
            if (token.isPresent()) {
                String username = jwtProvider.validateToken(token.get()).getSubject();
                User user = userService.loadUserByUsername(username);
                var authenticationToken = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (UsernameNotFoundException e) {
            log.info("invalid token content");
        } catch (ExpiredJwtException e) {
            log.info("token expired");
        } catch (SignatureException e) {
            log.info("malformed token");
        }

        filterChain.doFilter(request, response);
    }

    public Optional<String> extractToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(AUTH_HEADER_NAME);
            if (authHeader != null) {
                Matcher tokenMatcher = AUTH_HEADER_PATTERN.matcher(authHeader);
                if (tokenMatcher.find()) {
                    String token = tokenMatcher.group(1);
                    return Optional.of(token);
                }
            }
            return Optional.empty();
        } catch (RuntimeException e) {
            log.info("Token extraction failed");
            return Optional.empty();
        }
    }
}