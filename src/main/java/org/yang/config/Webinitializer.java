package org.yang.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * WebApplicationInitializer 接口只有一个方法 onStartup，
 * 通过他的参数 servletContext 我们可以实现注册我们的自定义
 * Servlet 和自定义 Filter 到 SpringMVC 中
 * @author yang
 * @date 2018-12-18
 */
public class Webinitializer implements WebApplicationInitializer{
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException{
        //There is 'Web'
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(MyMVCConfig.class);
        servletContext.addFilter("encodingFilter",new CharacterEncodingFilter("UTF-8",true));
        ctx.setServletContext(servletContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher",new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);

    }
}
