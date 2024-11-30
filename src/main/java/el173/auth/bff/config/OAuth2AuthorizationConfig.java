//package el173.auth.bff.config;
//
//import el173.auth.bff.repository.RedisOAuth2AuthorizationRequestRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
//
///**
// * @author hashithkarunarathne
// * @project Auth-BFF
// * @created 30/11/2024 - 00:34
// */
//@Configuration
//public class OAuth2AuthorizationConfig {
//
//    private final RedisOAuth2AuthorizationRequestRepository redisOAuth2AuthorizationRequestRepository;
//
//    public OAuth2AuthorizationConfig(RedisOAuth2AuthorizationRequestRepository redisOAuth2AuthorizationRequestRepository) {
//        this.redisOAuth2AuthorizationRequestRepository = redisOAuth2AuthorizationRequestRepository;
//    }
//
//    @Bean
//    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
//        return redisOAuth2AuthorizationRequestRepository;
//    }
//}
