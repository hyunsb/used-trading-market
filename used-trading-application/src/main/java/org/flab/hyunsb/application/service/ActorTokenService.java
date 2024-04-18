package org.flab.hyunsb.application.service;

import static org.flab.hyunsb.application.exception.message.ActorTokenErrorMessage.TOKEN_AUTHENTICATION_FAILED;
import static org.flab.hyunsb.application.exception.message.ActorTokenErrorMessage.TOKEN_EXPIRED;
import static org.flab.hyunsb.application.exception.message.ActorTokenErrorMessage.TOKEN_FORMAT_INVALID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.flab.hyunsb.application.dto.Actor;
import org.flab.hyunsb.application.exception.authentication.ActorTokenInvalidException;
import org.flab.hyunsb.application.usecase.member.ActorTokenAuthUseCase;
import org.flab.hyunsb.application.util.DateGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ActorTokenService implements ActorTokenAuthUseCase {

    private static final long EXP = 1000L * 60 * 60 * 48;
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String TOKEN_DELIMITER = " ";
    private static final String CLAIM_NAME_ACTOR_ID = "actorId";
    private static final String CLAIM_NAME_REGION_ID = "regionId";

    private final DateGenerator dateGenerator;
    private final SecretKey secretKey;

    public ActorTokenService(DateGenerator dateGenerator, @Value("${jwt.secretKey}") String key) {
        this.dateGenerator = dateGenerator;
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String createActorToken(Long actorId, Long regionId) {
        String token = Jwts.builder()
            .claim(CLAIM_NAME_ACTOR_ID, actorId)
            .claim(CLAIM_NAME_REGION_ID, regionId)
            .expiration(dateGenerator.getExpireDate(EXP))
            .signWith(secretKey)
            .compact();

        return String.join(TOKEN_DELIMITER, TOKEN_PREFIX, token);
    }

    @Override
    public Actor authenticate(String token) {
        validateFormat(token);
        return readToken(token);
    }

    private void validateFormat(String token) {
        if (!token.startsWith(TOKEN_PREFIX)) {
            throw new ActorTokenInvalidException(TOKEN_FORMAT_INVALID);
        }
    }

    private Actor readToken(String token) {
        Claims payload = parsePayload(token);
        validateExpirationDate(payload.getExpiration());

        Long actorId = payload.get(CLAIM_NAME_ACTOR_ID, Long.class);
        Long regionId = payload.get(CLAIM_NAME_REGION_ID, Long.class);
        return new Actor(actorId, regionId);
    }

    private Claims parsePayload(String token) {
        try {
            String originToken = token.replace(TOKEN_PREFIX, "").trim();
            return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(originToken).getPayload();
        } catch (JwtException exception) {
            throw new ActorTokenInvalidException(TOKEN_AUTHENTICATION_FAILED);
        }
    }

    private void validateExpirationDate(Date expirationDate) {
        Date currentDate = dateGenerator.getCurrentDate();
        if (currentDate.after(expirationDate)) {
            throw new ActorTokenInvalidException(TOKEN_EXPIRED);
        }
    }
}
