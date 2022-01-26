package io.github.hackyoma.springclouddemo.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Date;

/**
 * JwtTokenUtil
 *
 * @author hackyo
 * @version 2018/8/22
 */
public final class JwtTokenUtil {

    public final static String TOKEN_HEADER = "Bearer ";
    private final static long VALID_TIME = 2592000L * 1000L;

    /**
     * generate token
     *
     * @param userId user id
     * @return token
     */
    public static String generateToken(String userId) {
        Date now = new Date();
        Claims claims = new DefaultClaims();
        claims.setSubject(userId);
        claims.setExpiration(new Date(now.getTime() + VALID_TIME));
        claims.setNotBefore(now);
        return TOKEN_HEADER + Jwts.builder().setClaims(claims).signWith(EncryptUtil.SECRET_KEY, EncryptUtil.SIGNATURE_ALGORITHM).compact();
    }

    /**
     * refresh token
     *
     * @param token old token
     * @return new token
     */
    public static String refreshToken(String token) {
        Date now = new Date();
        Claims claims = getClaims(token);
        claims.setExpiration(new Date(now.getTime() + VALID_TIME));
        claims.setNotBefore(now);
        return TOKEN_HEADER + Jwts.builder().setClaims(claims).signWith(EncryptUtil.SECRET_KEY, EncryptUtil.SIGNATURE_ALGORITHM).compact();
    }

    /**
     * get the claims from the token
     *
     * @param token token
     * @return claims
     */
    private static Claims getClaims(String token) {
        token = token.substring(TOKEN_HEADER.length());
        return Jwts.parserBuilder().setSigningKey(EncryptUtil.SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    /**
     * get the userId from the token
     *
     * @param token token
     * @return userId
     */
    public static String getUserIdFromToken(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * get the created time from the token
     *
     * @param token token
     * @return created time
     */
    private static Date getCreatedTime(String token) {
        return getClaims(token).getNotBefore();
    }

    /**
     * get the expiration from the token
     *
     * @param token token
     * @return expiration
     */
    private static Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    /**
     * validate the token validity period
     *
     * @param token token
     * @return the validity of
     */
    public static Boolean validateToken(String token) {
        return new Date().before(getExpiration(token));
    }

}
