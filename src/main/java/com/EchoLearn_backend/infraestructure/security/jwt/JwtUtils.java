package com.EchoLearn_backend.infraestructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final String SECRET_KEY = "kjsudsywbr64544v343rjflspspdsid";
    private static final long EXPIRATION_MS = 86400000;

    public  String createJwt(String username, String roleAndAuthorities) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withSubject(username)
                .withIssuer("backend-echolear")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .withClaim("authorities", roleAndAuthorities)
                .sign(algorithm)
                ;
    }

    public Boolean isValid(String jwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWT.require(algorithm)
                    .build()
                    .verify(jwt); // Aquí explota si no es un JWT válido
            return true;
        } catch (JWTVerificationException e) {
            // Token con firma inválida, expirado, o datos incorrectos
            return false;
        } catch (IllegalArgumentException e) {
            // Token completamente inválido (estructura corrupta o vacía)
            return false;
        } catch (Exception e) {
            // Cualquier otro error no controlado
            return false;
        }
    }


    public String getUserName(String jwt){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT
                .require(algorithm)
                .build()
                .verify(jwt)
                .getSubject();
    }
}
