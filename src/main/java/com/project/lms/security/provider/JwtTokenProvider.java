package com.project.lms.security.provider;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.project.lms.vo.TokenVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final Integer tokenExpireMinutes = 60 ; //토근 만료시간 (현재 일주일)
    private final Integer refreshExpireMinutes = 60*24*24; //리프레쉬 토큰 만료시간(현재 한달) 자동로그인도 풀리는경우
    @Value("${jwt.secretKey}") String secretKey;

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    public TokenVO generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        Date expires = new Date((new Date()).getTime()+tokenExpireMinutes*60*1000);
        System.out.println(expires);
        Date refreshExpires = new Date((new Date()).getTime()+refreshExpireMinutes*60*1000);
        System.out.println(refreshExpires);
        String accessToken = Jwts.builder().setSubject(authentication.getName()).claim("auth", authorities).setExpiration(expires)
                .signWith(key, SignatureAlgorithm.HS256).compact();
        String refreshToken = Jwts.builder().setExpiration(refreshExpires)
                .signWith(key, SignatureAlgorithm.HS256).compact();
        return TokenVO.builder().grantType("Bearer").accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) { //토큰이 유효한지 검사
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch(SecurityException | MalformedJwtException e) {
            System.out.println("Invalid JWT Token"+e); //등록안됨
        } catch(ExpiredJwtException e) {
            System.out.println("Expired JWT Token"+e);
        } catch(UnsupportedJwtException e) {
            System.out.println("Unsupported JWT Token"+e); //토큰형태가 아님
        } catch(IllegalArgumentException e) {
            System.out.println("JWT claims string is empty."+e); //없는 토큰임
        }
        return false;
    }

    // public boolean validateRefreshToken(String refreshToken, String username) {
    //     try {
    //         // Claims claims = Jwts.parserBuilder()
    //         //     .setSigningKey(key)
    //         //     .build()
    //         //     .parseClaimsJws(refreshToken)
    //         //     .getBody();
    //         System.out.println("Aaa");
    //         String storedRefreshToken = (String)redisTemplate.opsForValue().get("RT:" + username);
    //         System.out.println("bbb");
            
    //         return refreshToken.equals(storedRefreshToken);
    //     } catch (JwtException | IllegalArgumentException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }
    // public TokenVO reissueAtk(AccountResponse accountResponse) throws JsonProcessingException {

    // }

    public boolean isRefreshTokenExpired(String refreshToken) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken).getBody();
    
        Date expiration = claims.getExpiration();
    
        return expiration.before(new Date());
    }
}






























