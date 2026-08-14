package com.infosys.finbank.customerservice.security;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;

import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.JwsHeader;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService() {

        try {

            // ==============================
            // Load Private Key
            // ==============================

            String privateKeyContent = Files.readString(
                    Path.of("src/main/resources/keys/private.pem")
            );

            privateKeyContent = privateKeyContent
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            byte[] privateKeyBytes =
                    Base64.getDecoder().decode(privateKeyContent);

            PKCS8EncodedKeySpec privateKeySpec =
                    new PKCS8EncodedKeySpec(privateKeyBytes);

            KeyFactory keyFactory =
                    KeyFactory.getInstance("RSA");

            RSAPrivateKey privateKey =
                    (RSAPrivateKey) keyFactory.generatePrivate(
                            privateKeySpec
                    );


            // ==============================
            // Load Public Key
            // ==============================

            String publicKeyContent = Files.readString(
                    Path.of("src/main/resources/keys/public.pem")
            );

            publicKeyContent = publicKeyContent
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", "");

            byte[] publicKeyBytes =
                    Base64.getDecoder().decode(publicKeyContent);

            X509EncodedKeySpec publicKeySpec =
                    new X509EncodedKeySpec(publicKeyBytes);

            RSAPublicKey publicKey =
                    (RSAPublicKey) keyFactory.generatePublic(
                            publicKeySpec
                    );


            // ==============================
            // Create RSA JWK
            // ==============================

            RSAKey rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .build();


            // ==============================
            // Create JWT Encoder
            // ==============================

            this.jwtEncoder = new NimbusJwtEncoder(
                    new ImmutableJWKSet<>(
                            new JWKSet(rsaKey)
                    )
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to load RSA keys for JWT",
                    e
            );
        }
    }


    // ==============================
    // Generate JWT
    // ==============================

    public String generateToken(
            Long custid,
            String email) {

        java.time.Instant now =
                java.time.Instant.now();

        JwtClaimsSet claims =
                JwtClaimsSet.builder()

                        .subject(
                                String.valueOf(custid)
                        )

                        .claim(
                                "email",
                                email
                        )

                        .issuedAt(now)

                        .expiresAt(
                                now.plusSeconds(60 * 60)
                        )

                        .build();


        return jwtEncoder.encode(

                JwtEncoderParameters.from(

                        claims

                )

        ).getTokenValue();
    }

}
