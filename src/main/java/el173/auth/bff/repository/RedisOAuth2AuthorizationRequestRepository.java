package el173.auth.bff.repository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Repository;

/**
 * @author hashithkarunarathne
 * @project Auth-BFF
 * @created 30/11/2024 - 00:35
 */
@Repository
public class RedisOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private static final String AUTHORIZATION_REQUEST_PREFIX = "oauth2:auth_request:";
    private final RedisTemplate<String, OAuth2AuthorizationRequest> redisTemplate;

    public RedisOAuth2AuthorizationRequestRepository(RedisTemplate<String, OAuth2AuthorizationRequest> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        String state = request.getParameter("state");
        if (state == null) {
            return null;
        }
        String key = getKey(state);
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        String state = authorizationRequest.getState();
        String key = getKey(state);
        redisTemplate.opsForValue().set(key, authorizationRequest);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        String state = request.getParameter("state");
        if (state == null) {
            return null;
        }
        String key = getKey(state);
        return redisTemplate.opsForValue().getAndDelete(key);
    }

    private String getKey(String state) {
        return AUTHORIZATION_REQUEST_PREFIX + state;
    }
}
