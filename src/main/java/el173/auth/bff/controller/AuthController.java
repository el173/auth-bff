package el173.auth.bff.controller;

import com.nimbusds.jose.JOSEException;
import el173.auth.bff.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hashithkarunarathne
 * @project Auth-BFF
 * @created 23/11/2024 - 12:26
 */
@RestController
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/auth-success")
    public ResponseEntity<Map<String, String>> handleAuthSuccess(OAuth2AuthenticationToken authentication) throws JOSEException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        String subject = "generate your app specific user subject";

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", "any");
        claims.put("name", "any");

        Map<String, String> tokens = jwtService.generateTokens(claims, subject);

        return ResponseEntity.ok(tokens);

    }
}
