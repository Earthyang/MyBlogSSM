package org.yang.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * MVC配置文件与DispatcherServlet
 * @author yang
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.yang")
public class MyMVCConfig extends WebMvcConfigurerAdapter {
    @Resource
    DBConfig dbConfig;

    /**
     * 配置JSP视图解析器，查找视图文件并添加前后缀
     * @return viewResolver
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    /**
     * 添加处理程序以提供静态资源，例如来自Web应用程序根目录下的特定位置的图像，js和css文件，类路径等。
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //两个*表示以/assets开始的任意层级的路径都可以访问得到图片，如<img src="../assets/img/1.png">
        //一个*表示只可以访问assets目录下的图片文件
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
    }

    /**
     *通过JavaBeans属性配置的javax.sql.DataSource的基本实现
     */
    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    /**
     * SqlSessionFactoryBean 实现了 Spring 的 FactoryBean 接口,由 Spring创建的bean不是SqlSessionFactoryBean本身,
     * 而是工厂类的 getObject()返回的方法的结果.
     * session 工厂将会被注入到 MapperFactoryBean 或其它扩 展了 SqlSessionDaoSupport 的 DAO
     * mapperLocations属性:mapperLocations 属性使用一个资源位置的list,这个属性可以用来指定 MyBatis 的 XML 映射器文件的位置
     * @return sqlSessionFactoryBean
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        try {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapping/*.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactoryBean;
    }

    /**
     * 使用一个 MapperScannerConfigurer,它将会查找类路径下的映射器并自动将它们创建成MapperFactoryBean
     * 为了代替手工使用 SqlSessionDaoSupport 或 SqlSessionTemplate 编写数据访问对象 (DAO)的代码,
     * MyBatis-Spring 提供了一个动态代理的实现:MapperFactoryBean。
     * 这个类 可以让你直接注入数据映射器接口到你的 service 层 bean 中。
     * 当使用映射器时,你仅仅如调 用你的 DAO 一样调用它们就可以了,但是你不需要编写任何 DAO 实现的代码,
     * 因为 MyBatis-Spring 将会为你创建代理。
     * @return mapper
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("org.yang.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return mapperScannerConfigurer;
    }

    /**
     * 在单一的JDBC DataSource中管理事务
     * @return data
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    /**
     * registry.addInterceptor可以通过此方法注册拦截器, 可以是spring提供的或者自己添加的
     * @param registry [in]
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptors());
    }

    /**
     * 创建一个拦截器实例
     * @return new LoginInterceptors
     */
    @Bean
    public LoginInterceptors loginInterceptors() {
        return new LoginInterceptors();
    }

}
