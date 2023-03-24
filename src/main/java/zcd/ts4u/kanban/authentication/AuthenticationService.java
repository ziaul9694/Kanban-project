package zcd.ts4u.kanban.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zcd.ts4u.kanban.security.JwtService;
import zcd.ts4u.kanban.user.Role;
import zcd.ts4u.kanban.user.User;
import zcd.ts4u.kanban.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

//    This method takes a RegisterRequest object as a parameter, creates a new User object with the data provided in the request, saves the user to the database via the UserRepository, generates a new JWT token using the JwtService, and returns an AuthenticationResponse object with the JWT token.
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.NORMAL)
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        return response;
    }

//    This method takes an LoginRequest object as a parameter, attempts to authenticate the user with the email and password provided in the request using the AuthenticationManager, generates a new JWT token using the JwtService, saves the new token to the database using the TokenRepository, and returns an AuthenticationResponse object with the JWT token.
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        String jwtToken = jwtService.generateToken(user);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        return response;
    }
}
