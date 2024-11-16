package com.data2.opendoc.manager.server.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.stereotype.Component;

/**
 * 通过ACM实现动态配置推送、使用NacosValue注解、如下例子：<p>
 *     connectTimeoutInMills 是动态变量；该变量在ACM上配置；改变量在ACM上修改后、应用变量会随之修改
 *     autoRefreshed = true 设置成自动刷新
 * </p>
 *
 */
@Component
public class DynamicConfig {

}
