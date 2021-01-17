package ru.tsedrik.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Map;

/**
 * Сервис валидации токена
 */
@Component
public class TokenValidateService {

    public Claims validate(String token, String secretKey){
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(new SecretKeySpec(Decoders.BASE64.decode(secretKey), SignatureAlgorithm.HS256.getJcaName()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    public Claims validate(String token, String secretKey, Map<String, Object> validatedFields) {

        Claims claims = this.validate(token, secretKey);

        validatedFields.forEach(
                (k, v) -> {
                    Object tokenValue;
                    switch (k){
                        case "iss":
                            tokenValue = claims.getIssuer();
                            break;
                        case "sub":
                            tokenValue = claims.getSubject();
                            break;
                        case "aud":
                            tokenValue = claims.getAudience();
                            break;
                        case "exp":
                            tokenValue = claims.getExpiration();
                            break;
                        case "nbf":
                            tokenValue = claims.getNotBefore();
                            break;
                        case "iat":
                            tokenValue = claims.getIssuedAt();
                            break;
                        case "jti":
                            tokenValue = claims.getId();
                            break;
                        default:
                            tokenValue = claims.get(k);
                            break;
                    }

                    if (!tokenValue.equals(v)){
                        throw new JwtException("The value of field " + k + " is not as expected '" + v + "'. Real value is '" + tokenValue + "'.");
                    }
                }
        );

        return claims;
    }
}
