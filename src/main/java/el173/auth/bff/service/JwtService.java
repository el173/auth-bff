package el173.auth.bff.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


/**
 * @author hashithkarunarathne
 * @project Auth-BFF
 * @created 23/11/2024 - 12:23
 */
@Service
public class JwtService {

    private static final String SECRET_KEY = "dYbNUBxMKOHMNJ7bh0mD1V4TL_dccU8ePkhjXHqcOIg";

    public Map<String, String> generateTokens(Map<String, Object> claims, String subject) throws JOSEException {
        String accessToken = generateToken(claims, subject, 1000 * 60 * 60);

        String refreshToken = generateToken(claims, subject, 1000 * 60 * 60 * 24 * 7);

        return Map.of("access_token", accessToken, "refresh_token", refreshToken);
    }

    private String generateToken(Map<String, Object> claims, String subject, long expirationTimeMillis) throws JOSEException {
        JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer("your-issuer")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + expirationTimeMillis)); // Custom expiration time

        claims.forEach(claimsBuilder::claim);

        JWTClaimsSet claimsSet = claimsBuilder.build();

        byte[] secret = SECRET_KEY.getBytes();
        JWSSigner signer = new MACSigner(secret);

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claimsSet
        );

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }
}

