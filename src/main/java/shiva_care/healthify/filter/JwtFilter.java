package shiva_care.healthify.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shiva_care.healthify.jwt.JwtUtil;
import shiva_care.healthify.service.token.TokenService;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {

    final JwtUtil util;
      // generate token by this calll
    final UserDetailsService userDetailsService;   // find the user details to username From db
    final TokenService tokenService;

    public JwtFilter(JwtUtil util, UserDetailsService userDetailsService, TokenService tokenService) {
        this.util = util;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }
    // this is used to check token is blocked aur not if need to
    // block it can be

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        String path = request.getServletPath();
        String header = request.getHeader("Authorization");
        String userName = null;
        String jwt = null;

        // Ab hum check karenge token sahi hai ya nahi aur user ke login
        // credentials sahi hai ya nahi

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            //Block List Checking

            if (tokenService.isBlacklist(token)) {
                throw new RuntimeException("Token is logout");
            }

            // Extract UserName

            userName = util.extractUserName(token);

        }
        // if swagger request aaye you should allow

        String pathURI = request.getRequestURI();

        if (pathURI.startsWith("/swagger-ui")
                || pathURI.startsWith("/v3/api-docs")
                || pathURI.equals("/error")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1️⃣ Skip public APIs
        // IF API is public then don't need to check api
        if (path.startsWith("/public")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2️⃣ Allow preflight
        // (if request is preflight then send them forward without checking)

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }


        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (util.validateToken(token, userDetails.getUsername())) {
                // who is user  and what it the role of user

                // dekh yaha user ka id card bana rahe hai aur eske
                // baad hum user ko bolenge bhai yah login hai sahi hai ganda
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // here we are givind user admit card to spring boot le bhai use sahi hai
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // 3️⃣ MOST IMPORTANT LINE
        filterChain.doFilter(request, response);

    }
}
