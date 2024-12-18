package el173.auth.bff.controller;

import com.nimbusds.jose.JOSEException;
import el173.auth.bff.service.JwtService;
import el173.auth.bff.service.RedisService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author hashithkarunarathne
 * @project Auth-BFF
 * @created 23/11/2024 - 12:26
 */
@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisService redisService;


    @GetMapping("/auth-success")
    public ResponseEntity<Void> handleAuthSuccess(OAuth2AuthenticationToken authentication) throws JOSEException, IOException, IOException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        String subject = oidcUser.getName();  // You can modify this based on your app's logic

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", oidcUser.getEmail());
        claims.put("name", oidcUser.getFullName());

        Map<String, String> tokens = jwtService.generateTokens(claims, subject);

        String uuid = UUID.randomUUID().toString();
        redisService.save(uuid + "_access_token", tokens.get("access_token"));
        redisService.save(uuid + "_refresh_token", tokens.get("refresh_token"));

        String redirectUri = redisService.get("redirectUri");

        if (redirectUri == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No redirect URI found in session");
        }

        String redirectUrlWithUuid = appendUuidToRedirectUrl(redirectUri, uuid);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrlWithUuid))
                .build();
    }

    @PostMapping("/get-token")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody Map<String, String> requestBody) {
        String uuid = requestBody.get("code");

        if (uuid == null) {
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(Map.of("error", "UUID is required"));
        }

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", redisService.get(uuid + "_access_token"));
        tokens.put("refresh_token", redisService.get(uuid + "_refresh_token"));
        if (tokens == null) {
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(Map.of("error", "Token not found"));
        }

        return ResponseEntity.ok(tokens);
    }


    private String appendUuidToRedirectUrl(String redirectUri, String uuid) {
        if (redirectUri.contains("?")) {
            return redirectUri + "&code=" + uuid;
        } else {
            return redirectUri + "?code=" + uuid;
        }
    }
}
