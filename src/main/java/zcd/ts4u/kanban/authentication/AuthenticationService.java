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
