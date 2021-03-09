package ru.tsedrik.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.stereotype.Component;
import ru.tsedrik.service.exception.InvalidTokenException;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

/**
 * Сервис валидации токена
 */
@Component
public class TokenValidateService {

    @Value("${spring.application.name}")
    private String audience;

    @Value("${spring.application.token.issuer}")
    private String issuer;

    @Value("${spring.application.token.key}")
    private String secretKey;

    public MyClaims parseAndValidate(String token){
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

        MyClaims myClaims = null;
        try {
            myClaims = new MyClaims(Jwts.parserBuilder()
                    .setSigningKey(new SecretKeySpec(Decoders.BASE64.decode(secretKey), SignatureAlgorithm.HS256.getJcaName()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody());
        }catch (Exception e){
            throw new InvalidTokenException(e.getMessage());
        }

        validate(myClaims);

        return myClaims;
    }

    public void validate(MyClaims claims){
        if (!isCorrectSub(claims)){
            throw new InvalidTokenException("Не указан пользователь токена.");
        }
        if (!isNotExpired(claims)){
            throw new AccountExpiredException("Время действия токена истекло.");
        }
        if (!isCorrectIat(claims)){
            throw new InvalidTokenException("Время выдачи токена некорректно.");
        }
        if (!isCorrectJti(claims)){
            throw new InvalidTokenException("Не указан идентификатор токена.");
        }
        if (!isCorrectAud(claims)){
            throw new InvalidTokenException("Не верный получатель токена.");
        }

        if (!isCorrectIss(claims)){
            throw new InvalidTokenException("Не верный издатель токена.");
        }
        if (!isCorrectRole(claims)){
            throw new InvalidTokenException("Не верная роль пользователя.");
        }
    }

    public boolean isCorrectSub(MyClaims claims){
        String sub = claims.getSubject();
        return sub != null && !sub.isEmpty();
    }

    public boolean isNotExpired(MyClaims claims){
        Date exp = claims.getExpiration();
        return exp.after(new Date());
    }

    public boolean isCorrectAud (MyClaims claims){
        String aud = claims.getAudience();
        return aud.equals(audience);
    }

    public boolean isCorrectIat(MyClaims claims){
        Date iat = claims.getIssuedAt();
        return iat.before(new Date());
    }

    public boolean isCorrectJti(MyClaims claims){
        String jti = claims.getId();
        return jti != null && !jti.isEmpty();
    }

    public boolean isCorrectIss(MyClaims claims){
        String iss = claims.getIssuer();
        return iss.equals(issuer);
    }

    public boolean isCorrectRole(MyClaims claims){
        String role = claims.getRole();
        return role != null && !role.isEmpty();
    }

}
