package zcd.ts4u.kanban.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    //    The JwtAuthenticationFilter class extends OncePerRequestFilter, which is a convenience base class for filters that ensures that the doFilterInternal method is only executed once per request.
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        //    The doFilterInternal method is where the actual authentication logic is implemented. The method first checks if the request contains an Authorization header that starts with the string "Bearer ". If the header is not present or does not start with "Bearer ", the method simply calls the filterChain.doFilter method to pass the request to the next filter in the chain.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
//        If the Authorization header is present and starts with "Bearer ", the method extracts the JWT from the header, and then extracts the user email address from the JWT using the JwtService instance. If the email address is not null and there is no existing authentication context in the SecurityContextHolder, the method loads the user details from the UserDetailsService instance using the email address, and checks if the JWT is valid using the jwtService.isTokenValid method.
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//            If the JWT and user details are valid and the token is valid, the method creates an UsernamePasswordAuthenticationToken instance and sets it in the SecurityContextHolder to authenticate the user.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
//            Finally, the method calls filterChain.doFilter to pass the request to the next   filter in the chain.
            filterChain.doFilter(request, response);
        }
    }
}
