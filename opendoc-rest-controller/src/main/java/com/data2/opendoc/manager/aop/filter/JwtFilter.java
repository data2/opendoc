package com.data2.opendoc.manager.aop.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.data2.opendoc.manager.server.domain.User;
import com.data2.opendoc.manager.server.mapper.UserMapper;
import com.google.common.collect.Lists;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

@Component
public class JwtFilter implements javax.servlet.Filter {

    private static final List<String> UNCHECK_URL = Lists.newArrayList("/opendoc/loginuser","/opendoc/registuser",
            "/opendoc/searchArticleHomePage", "/opendoc/searchArticlePage","/opendoc/selectArticleById"
            ,"/opendoc/selectArticlePage","/opendoc/selectDiscussionPage");

    @Autowired
    private UserMapper userMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 获取请求的 URI
        String requestURI = httpRequest.getRequestURI();

        // 检查是否是登录请求
        if (UNCHECK_URL.contains(requestURI)) {
            // 如果是登录请求，直接放行
            chain.doFilter(request, response);
            return;
        }

        String authorizationHeader = httpRequest.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = URLDecoder.decode(authorizationHeader.substring(7));
            try {
                Claims claims = JwtUtil.extractClaims(jwtToken);
                username = claims.getSubject();
            }catch (Exception e){
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Unauthorized");
            }

        }

        if (username != null && JwtUtil.validateToken(jwtToken, username)) {
            QueryWrapper<User> query = new QueryWrapper<>();
            query.eq("email", username);
            User user = userMapper.selectOne(query);
            user.setPassword(jwtToken);
            JwtUtil.setCurrentUsername(user);
            chain.doFilter(request, response);
            // 清除ThreadLocal中的信息（可选，但推荐在请求结束时清除）
            JwtUtil.clearCurrentUsername();
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Unauthorized");
        }
    }

    @Override
    public void destroy() {
    }


}