package com.data2.opendoc.manager.aop;

import com.google.common.collect.Maps;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;

/**
 *
 * @author auto-generated
 * @date 2024/11/16
 */

public class HeaderResolveInterceptor implements HandlerInterceptor {
    public HeaderResolveInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> keys = request.getHeaderNames();
        HashMap params = Maps.newHashMap();

        while(keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            params.put(key, request.getHeader(key));
        }

        RequestContext.setHeaderParams(params);
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        RequestContext.removeHeaderParams();
    }

   }
