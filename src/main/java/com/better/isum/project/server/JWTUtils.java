package com.better.isum.project.server;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class JWTUtils {
    private static JWTUtils instance = null;
    private KeyPair keyPair;

    final MetLogger LOGGER = MetLogger.getInstance();

    private JWTUtils() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");

            Path path = Paths.get("better-isum-project-server/src/main/resources/", "private_key.pem");
            LOGGER.LogMessage(path.toAbsolutePath().toString(), Level.CONFIG);

            byte[] privateKeyBytes = Files.readAllBytes(
                    Paths.get("better-isum-project-server/src/main/resources/", "private_key.pem"));
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            byte[] publicKeyBytes = Files.readAllBytes(
                    Paths.get("better-isum-project-server/src/main/resources/", "public_key.pem"));
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

            keyPair = new KeyPair(keyFactory.generatePublic(publicKeySpec), keyFactory.generatePrivate(privateKeySpec));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.LogMessage(
                    String.format("Jwt keys not loaded [NoSuchAlgorithmException]: ", e.getMessage()), Level.SEVERE);
        } catch (NoSuchProviderException e) {
            LOGGER.LogMessage(
                    String.format("Jwt keys not loaded [NoSuchProviderException]: ", e.getMessage()), Level.SEVERE);
        } catch (IOException e) {
            LOGGER.LogMessage(
                    String.format("Jwt keys not loaded [IOException]: ", e.getMessage()), Level.SEVERE);
        } catch (InvalidKeySpecException e) {
            LOGGER.LogMessage(
                    String.format("Jwt keys not loaded [InvalidKeySpecException]: ", e.getMessage()), Level.SEVERE);
        }
    }

    public static synchronized JWTUtils getInstance() {
        if (instance == null) {
            instance = new JWTUtils();
        }
        return instance;
    }

    public final Key getPublicKey() {
        return keyPair.getPublic();
    }

    private final Key getPrivateKey() {
        return keyPair.getPublic();
    }

    public String generateToken(Student user) {
        final long EXPIRATION_TIME_IN_MINUTES = 45;
        final Date EXPIRATION_TIME = Date.from(
                LocalDateTime.now().plusMinutes(EXPIRATION_TIME_IN_MINUTES).atZone(ZoneId.systemDefault())
                        .toInstant());

        Map<String, Object> header = new HashMap<>() {
            {
                put("alg", "ES512");
                put("typ", "JWT");
            }
        };

        Map<String, Object> claims = Map.of(
                "UUID", UUID.randomUUID().toString(),
                "exp", EXPIRATION_TIME.getTime());

        Key key = keyPair.getPrivate();

        final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.ES512;
        return Jwts.builder()
                .setHeader(header)
                .addClaims(claims)
                .signWith(SIGNATURE_ALGORITHM, key)
                .compact();
    }

    public Map<String, String> validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(this.getPrivateKey())
                    .parseClaimsJws(token);

            return Map.of(
                    "valid", "true");

        } catch (ExpiredJwtException e) {
            return Map.of(
                    "valid", "false",
                    "Error", "Token expired");
        } catch (MalformedJwtException e) {
            return Map.of(
                    "valid", "false",
                    "Error", "Bad token format");
        } catch (PrematureJwtException e) {
            return Map.of(
                    "valid", "false",
                    "Error", "Token not yet valid");
        } catch (SignatureException e) {
            return Map.of(
                    "valid", "false",
                    "Error", "Token signature invalid");
        } catch (UnsupportedJwtException e) {
            return Map.of(
                    "valid", "false",
                    "Error", "Token not supported");
        } catch (Exception e) {
            return Map.of(
                    "valid", "false",
                    "Error", e.toString());
        }
    }
}