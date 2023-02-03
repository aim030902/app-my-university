package com.company.appmyalllearnsproject.security;

import com.company.appmyalllearnsproject.entity.Role;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private static final long expireTime = 1000 * 60 * 60 * 24;
    private static final String secretKey = "BuMaxfiySecretKeyBuniHechkimBilmasligiKerakOKmiOkBolsaKettuv98765432107777777";

    public String generateToken(String username, Role role){
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .claim("role", role.getName())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

    }

    public String getUsernameFromToken(String token){
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
        catch (Exception e){
            return null;
        }
    }

    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }
        catch (SignatureException e){
            System.err.println("Invalid JWT signature");
        }
        catch (MalformedJwtException e){
            System.err.println("Invalid JWT token");
        }
        catch (ExpiredJwtException e){
            System.err.println("Expired JWT token");
        }
        catch (UnsupportedJwtException e){
            System.err.println("Unsupported JWT token");
        }
        catch (IllegalArgumentException e){
            System.err.println("JWT claims string is empty");
        }
        return false;
    }
}
