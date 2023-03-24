package zcd.ts4u.kanban.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import zcd.ts4u.kanban.user.UserRepository;

//The class is annotated with @Configuration, which tells Spring to treat it as a configuration class.
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;

//    The ApplicationConfig class has several methods annotated with @Bean, which are used by Spring to manage and instantiate beans in the application context.

//    The userDetailsService method returns an implementation of the UserDetailsService interface, which is used to retrieve user details for authentication purposes. The implementation uses a UserRepository instance to find the user details based on the provided email address. If the user is not found, it throws a UsernameNotFoundException.
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
    }

//    The authenticationProvider method returns an instance of DaoAuthenticationProvider, which is an implementation of the AuthenticationProvider interface. This provider uses the UserDetailsService returned by the userDetailsService method to authenticate users, and also sets a password encoder to ensure secure password storage.
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

//    The authenticationManager method returns an instance of AuthenticationManager, which is used to authenticate users in the application. The method takes an AuthenticationConfiguration object as a parameter, which is used to retrieve the AuthenticationManager.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    The passwordEncoder method returns an instance of BCryptPasswordEncoder, which is a password encoder that uses the bcrypt hashing algorithm to store and compare passwords.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
