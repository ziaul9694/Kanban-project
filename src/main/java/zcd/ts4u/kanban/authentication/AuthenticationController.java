package zcd.ts4u.kanban.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

//    This method is annotated with @PostMapping("/register"), which means it handles HTTP POST requests with the /account/register URL. The method takes a single parameter of type RegisterRequest, which is annotated with @RequestBody to indicate that this parameter should be deserialized from the request body. The method calls the register method of the AuthenticationService and returns a ResponseEntity with an AuthenticationResponse object and an HTTP status code of 200 (OK).
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

//    This method is annotated with @PostMapping("/login"), which means it handles HTTP POST requests with the /account/login URL. The method takes a single parameter of type LoginRequest, which is annotated with @RequestBody to indicate that this parameter should be deserialized from the request body. The method calls the login method of the AuthenticationService and returns a ResponseEntity with an AuthenticationResponse object and an HTTP status code of 200 (OK).
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ){
        return ResponseEntity.ok(service.login(request));
    }

}
