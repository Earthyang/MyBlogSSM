package org.yang.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 创建一个拦截器，当请求中带有*yang*时检测当前session中是否存在用户实例
 * 若不存在则跳转到登陆页面
 * @author yang
 */
public class LoginInterceptors implements HandlerInterceptor {

    /**
     * 该方法将在请求处理之前进行调用。SpringMVC 中的Interceptor 是链式的调用的，
     * 在一个应用中或者说是在一个请求中可以同时存在多个Interceptor 。
     * 每个Interceptor 的调用会依据它的声明顺序依次执行，而且最先执行的都是Interceptor 中的preHandle 方法，
     * 所以可以在这个方法中进行一些前置初始化操作或者是对当前请求的一个预处理，
     * 也可以在这个方法中进行一些判断来决定请求是否要继续进行下去。
     * @param httpServletRequest [in]
     * @param httpServletResponse [in]
     * @param o [in]
     * @return [out]
     * @throws Exception [Exception]
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletRequest.setCharacterEncoding("UTF-8");
        StringBuffer requestURL = httpServletRequest.getRequestURL();
        if (requestURL.toString().contains("yang")&&!requestURL.toString().contains("yang/login")&&!requestURL.toString().contains("yang/dologin")) {
            Object user = httpServletRequest.getSession().getAttribute("user");
            if (user == null) {
                httpServletResponse.sendRedirect("/yang/login");
                return false;
            } else {
                return true;
            }
        }else{
            return true;
        }
    }

    /**
     * 只能是在当前所属的Interceptor 的preHandle 方法的返回值为true 时才能被调用。
     * postHandle 方法在当前请求进行处理之后，也就是Controller 方法调用之后执行，
     * 但是它会在DispatcherServlet 进行视图返回渲染之前被调用，
     * 可以在这个方法中对Controller 处理之后的ModelAndView 对象进行操作
     * @param httpServletRequest [in]
     * @param httpServletResponse [in]
     * @param o [in]
     * @param modelAndView [in]
     * @throws Exception [Exception]
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 该方法也是需要当前对应的Interceptor 的preHandle 方法的返回值为true 时才会执行。
     * 在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。
     * 这个方法的主要作用是用于进行资源清理工作的。
     * @param httpServletRequest [in]
     * @param httpServletResponse [in]
     * @param o [in]
     * @param e [in]
     * @throws Exception [Exception]
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
