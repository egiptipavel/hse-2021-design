package com.hse.boards.security;

import com.hse.boards.models.User;
import com.hse.boards.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    public JwtFilter(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) {
        String token = getTokenFromRequest(httpServletRequest);
        if (token != null && jwtProvider.validateToken(token)) {
            String username = jwtProvider.getUsernameFromToken(token);
            User user = userService.getUserByLogin(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            SecurityContextHolder.clearContext();
        }
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (IOException | ServletException exception) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}