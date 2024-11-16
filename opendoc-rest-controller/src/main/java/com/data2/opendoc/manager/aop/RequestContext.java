package com.data2.opendoc.manager.aop;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;

/** *
 * @author auto-generated
 * @date 2024/11/16
 */

public class RequestContext {
    private static ThreadLocal<Map<String, String>> headerParams = new ThreadLocal();
    public RequestContext() {
    }
    public static void setHeaderParams(Map<String, String> params) {
        if (!CollectionUtils.isEmpty(params)) {
            headerParams.set(params);
        }
    }

    public static Map<String, String> getHeaderParams() {
        Map<String, String> params = (Map)headerParams.get();
        if (params == null) {
            params = Collections.emptyMap();
        }
        return params;
    }

    public static String getHeaderParam(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        } else {
            Map<String, String> params = (Map)headerParams.get();
            return params != null ? (String)params.get(key.toLowerCase()) : null;
        }
    }

    public static void removeHeaderParams() {
        headerParams.remove();
    }
}
