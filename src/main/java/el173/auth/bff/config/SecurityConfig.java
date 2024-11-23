package el173.auth.bff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author hashithkarunarathne
 * @project Auth-BFF
 * @created 22/11/2024 - 23:53
 */

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/jwt", "/app-login").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/app-login")
                        .defaultSuccessUrl("/auth-success", true)
                )
                .logout(logout -> logout.logoutSuccessUrl("/"));
        return http.build();
    }
}

