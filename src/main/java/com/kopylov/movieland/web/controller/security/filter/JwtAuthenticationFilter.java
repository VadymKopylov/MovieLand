package com.kopylov.movieland.web.controller.security.filter;

import com.kopylov.movieland.service.impl.MyUserDetailsService;
import com.kopylov.movieland.web.controller.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.kopylov.movieland.web.controller.security.JwtService.AUTH_SCHEME;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwtToken;
        final String userEmail;

        if (authorizationHeader == null || !authorizationHeader.startsWith(AUTH_SCHEME)) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authorizationHeader.substring(AUTH_SCHEME.length());
        userEmail = jwtService.extractUserName(jwtToken);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}
