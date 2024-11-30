package el173.auth.bff.config;

import el173.auth.bff.repository.RedisOAuth2AuthorizationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author hashithkarunarathne
 * @project Auth-BFF
 * @created 22/11/2024 - 23:53
 */

@Configuration
public class SecurityConfig {

    @Autowired
    private RedisOAuth2AuthorizationRequestRepository redisOAuth2AuthorizationRequestRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/app-login", "/get-token").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/auth-success", true)
                )
                .logout(logout -> logout.logoutSuccessUrl("/"));
        return http.build();
    }

}

