package com.better.isum.project.server;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import javax.crypto.spec.SecretKeySpec;

import java.security.Key;
import java.security.Security;

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
    private Key key;

    final MetLogger LOGGER = MetLogger.getInstance();

    private JWTUtils() {
        // try {
        Security.addProvider(new BouncyCastleProvider());

        String secretString = "ABCD";
        byte[] secretBytes = Base64.getDecoder().decode(secretString);
        key = new SecretKeySpec(secretBytes, "HS512");

        // } catch (NoSuchAlgorithmException e) {
        // LOGGER.LogMessage(
        // String.format("Jwt keys not loaded [NoSuchAlgorithmException]: ",
        // e.getMessage()), Level.SEVERE);
        // } catch (NoSuchProviderException e) {
        // LOGGER.LogMessage(
        // String.format("Jwt keys not loaded [NoSuchProviderException]: ",
        // e.getMessage()), Level.SEVERE);
        // }
        // catch (IOException e) {
        // LOGGER.LogMessage(
        // String.format("Jwt keys not loaded [IOException]: ", e.getMessage()),
        // Level.SEVERE);
        // } catch (InvalidKeySpecException e) {
        // LOGGER.LogMessage(
        // String.format("Jwt keys not loaded [InvalidKeySpecException]: ",
        // e.getMessage()), Level.SEVERE);
        // }
    }

    public static synchronized JWTUtils getInstance() {
        if (instance == null) {
            instance = new JWTUtils();
        }
        return instance;
    }

    // public final Key getPublicKey() {
    // return keyPair.getPublic();
    // }

    // private final Key getPrivateKey() {
    // return keyPair.getPublic();
    // }

    private final Key getSecret() {
        return this.key;
    }

    public String generateToken(User user) {
        final long EXPIRATION_TIME_IN_MINUTES = 45;
        final Date EXPIRATION_TIME = Date.from(
                LocalDateTime.now().plusMinutes(EXPIRATION_TIME_IN_MINUTES).atZone(ZoneId.systemDefault())
                        .toInstant());

        Map<String, Object> header = new HashMap<>() {
            {
                put("alg", "HS512");
                put("typ", "JWT");
            }
        };

        Map<String, Object> claims = Map.of(
                "UUID", UUID.randomUUID().toString(),
                "exp", EXPIRATION_TIME.getTime());

        final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
        return Jwts.builder()
                .setHeader(header)
                .addClaims(claims)
                .signWith(SIGNATURE_ALGORITHM, key)
                .compact();
    }

    public Map<String, String> validateToken(String token) {
        try {
            token = token.substring(7);
            Jwts.parser()
                    .setSigningKey(getSecret())
                    .parseClaimsJws(token);

            return Map.of(
                    "valid", "true");

        } catch (ExpiredJwtException e) {
            return Map.of(
                    "valid", "false",
                    "Error", "Token expired");
        } catch (SignatureException | UnsupportedJwtException | PrematureJwtException | MalformedJwtException
                | NullPointerException e) {
            return Map.of(
                    "valid", "false",
                    "Error", "Token invalid");
        } catch (Exception e) {
            LOGGER.LogMessage(e.toString(), Level.WARNING);
            return Map.of(
                    "valid", "false",
                    "Error", e.toString());
        }
    }
}