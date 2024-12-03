package com.data2.opendoc.manager;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * rest 启动入口
 *
 * @author auto-generated
 * @date 2024/11/16
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.data2.opendoc.manager"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Bootstrap {
    public static void main(String[] args) {
        try {
            SpringApplication.run(Bootstrap.class, args);
            log.info("Swagger-UI: http://127.0.0.1:8888/opendoc/swagger-ui.html");
            log.info("应用测试请求: http://127.0.0.1:8888/opendoc/test");
        } catch (Exception e) {
            log.error("service start failed, cause:{}", Throwables.getStackTraceAsString(e));
        }
    }
}
