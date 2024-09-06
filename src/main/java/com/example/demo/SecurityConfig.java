package com.example.demo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain, defining security policies and authentication mechanisms.
     *
     * @param http The HttpSecurity object to configure security policies.
     * @return A configured SecurityFilterChain instance.
     * @throws Exception If an error occurs while configuring HttpSecurity.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Using the new approach for configuring HttpSecurity
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()  // All endpoints require authentication
                )
                .httpBasic(Customizer.withDefaults());  // Enable HTTP Basic authentication

        // Build and return the security filter chain
        return http.build();
    }


    /**
     * Configures an in-memory user store with a single user for demonstration purposes.
     * In a real-world application, this would typically be replaced by a database-backed user store.
     *
     * @return A UserDetailsService that manages user details in memory.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // Create an in-memory user with the username "user" and password "password"
        // "{noop}" indicates that no password encoding is used. This is only for demonstration purposes and should not be used in production.
        UserDetails user = User.withUsername("user")
                .password("{noop}password")  // NoOpPasswordEncoder for simplicity
                .roles("USER")   // Assign the "USER" role to this user
                .build();

        // Return an in-memory user details manager that manages this user
        return new InMemoryUserDetailsManager(user);
    }
}
