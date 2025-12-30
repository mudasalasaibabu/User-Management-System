package com.example.usermanagement.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.usermanagement.serviceimplementation.SystemSettingsService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Lazy // ✅ ADD: load filter lazily (startup faster)
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private SystemSettingsService settingsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // ✅ ADD: Skip health check (very important for Render uptime pings)
        String path = request.getServletPath();
        if (path.equals("/health")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Allow authentication APIs (EXISTING LOGIC - UNCHANGED)
        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // JWT AUTHENTICATION FIRST (UNCHANGED)

        String token = null;
        String username = null;

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null) {
            try {
                username = tokenService.extractUserName(token);
            } catch (ExpiredJwtException e) {

                Cookie deleteCookie = new Cookie("jwt", "");
                deleteCookie.setPath("/");
                deleteCookie.setHttpOnly(true);
                deleteCookie.setMaxAge(0); // delete cookie

                response.addCookie(deleteCookie);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return; // STOP request
            }
        }

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userService.loadUserByUsername(username);

            if (tokenService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // ✅ ADD: Skip maintenance check if user is not authenticated
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // MAINTENANCE MODE CHECK (EXISTING LOGIC - UNCHANGED)
        if (settingsService.getSettings().isMaintenanceMode()) {

            boolean isAdmin =
                    SecurityContextHolder.getContext().getAuthentication() != null &&
                    SecurityContextHolder.getContext().getAuthentication()
                            .getAuthorities()
                            .stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                response.getWriter().write("System is under maintenance");
                return; // Block non-admin users
            }
        }

        // Continue request
        filterChain.doFilter(request, response);
    }
}
