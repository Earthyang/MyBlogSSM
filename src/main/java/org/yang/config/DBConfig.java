package org.yang.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Configuration : 声明当前类是个配置类,相当于一个Spring配置的xml文件.
 * Component : 组件,没有明确的角色
 * PropertySource ：注解主要是让Spring的Environment接口读取属性配置文件用的，这个注解是标识在@Configuration配置类
 * Value ：注解可以用在字段和方法上。通常用于从属性配置文件中读取属性值，也可以设置默认值
 * @author yang
 * @date 2018-12-18
 */
@Configuration
@Component
@PropertySource(value = "classpath:jdbc.properties",encoding = "UTF-8")
public class DBConfig {
    @Value("${db.driveClass}")
    public String driver;
    @Value("${db.url}")
    public String url;
    @Value("${db.username}")
    public String username;
    @Value("${db.password}")
    public String password;
    @Value("${db.initialSize}")
    public int initialSize;
    @Value("${db.maxActive}")
    public int maxActive;
    @Value("${db.maxIdle}")
    public int maxIdle;
    @Value("${db.minIdle}")
    public int minIdle;
    @Value("${db.maxWait}")
    public int maxWait;

}
