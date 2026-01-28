package com.example.usermanagement.security;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
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

        String path = request.getRequestURI();

        //Allow authentication & system settings APIs
        if (path.startsWith("/api/auth")
                || path.equals("/health")
                || path.startsWith("/api/users/register")
                || path.equals("/")) {

            filterChain.doFilter(request, response);
            return;
        }



        // JWT AUTHENTICATION FIRST 

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
                return; //STOP request
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
                /*First: “details” ante enti? (VERY SIMPLE)
						 details = request gurinchi extra information
						Like:Ee request ekkadanunchi vachindi User IP address enti Anthe. Inka em ledu.*/
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                /*SecurityContextHolder anedi Spring Security already create chesina class, current request user info store cheyyadaniki.*/
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // MAINTENANCE MODE CHECK (AFTER AUTH)

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