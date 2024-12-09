package com.data2.opendoc.manager.aop.filter;
import com.data2.opendoc.manager.server.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = "mySecretKey";

    // 生成 JWT
    public static String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 5)) //5天有效期
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 解析 JWT
    public static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // 验证 JWT
    public static boolean validateToken(String token, String username) {
        final String extractedUsername = extractClaims(token).getSubject();
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // 检查 JWT 是否过期
    private static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // 新增方法：从JWT中提取用户名
    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // 如果用户名是作为subject存储的，可以直接返回subject
        // 或者，如果用户名是作为claim存储的，可以使用以下代码：
        // return (String) claims.get("username");
    }

    // 使用ThreadLocal存储当前线程的用户信息
    private static final ThreadLocal<User> currentUsername = new ThreadLocal<>();

    // 静态辅助方法，用于设置当前线程的用户名
    public static void setCurrentUsername(User user) {
        currentUsername.set(user);
    }

    // 静态辅助方法，用于获取当前线程的用户名（注意：线程不安全，仅用于示例）
    public static User getCurrentUsername() {
        return currentUsername.get();
    }

    // 清除ThreadLocal中的信息，避免内存泄漏（通常在请求结束时调用）
    public static void clearCurrentUsername() {
        currentUsername.remove();
    }

    //
    public static void expire(String token) {
        extractClaims(token).setExpiration(new Date());
    }
}
