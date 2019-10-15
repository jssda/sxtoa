package pers.jssd.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限过滤器
 */
public class AuthFilter implements Filter {

    /**
     * 过滤路径是  /servlet/*  *.jsp
     * 某些jsp应该排除在外   login.jsp   register.jsp   index.jsp
     * 某些servlet应该排除在外  servlet/UserServlet?method=login  servlet/UserServlet?method=register
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httprequest = (HttpServletRequest) request;
        HttpServletResponse httpresponse = (HttpServletResponse) response;
        //1.请求到达目标资源之前的预处理操作
        //http://127.0.0.1:8081/myjspjstlel/admin2/login.jsp
        ////http://127.0.0.1:8081/myjspjstlel/admin2/register.jsp
        //http://127.0.0.1:8081/myjspjstlel/index.jsp
        //http://127.0.0.1:8081/myjspjstlel/servlet/UserServlet?method=login
        //http://127.0.0.1:8081/myjspjstlel/servlet/UserServlet?method=register`

        String uri = httprequest.getRequestURI();
        String qs = httprequest.getQueryString(); // 如果 没有查询字符串，就是null

        int n1 = uri.indexOf("login.jsp");//>=0 存在
        int n2 = uri.indexOf("register.jsp");
        int n3 = uri.indexOf("index.jsp");
        int n4 = -1;
        int n5 = -1;
        int n6 = -1;
        if (qs != null) {
            n4 = qs.indexOf("login");
            n5 = qs.indexOf("register");
            n6 = qs.indexOf("checkUserId");
        }


        if (n1 >= 0 || n2 >= 0 || n3 >= 0 || n4 >= 0 || n5 >= 0 || n6 >= 0) { //需要排除在外的资源
            //放行
            chain.doFilter(request, response);
        } else {
            //判断用户是否登录，如果登录，就允许访问，如果没有登录，就重定向到登录页面
            Object user = httprequest.getSession().getAttribute("user");
            if (user != null) {
                //就允许访问,调用下一个过滤器或者目标资源
                chain.doFilter(request, response);
                //3.响应离开目标资源后的后处理操作

            } else {
                //就重定向到登录页面
                httpresponse.sendRedirect(httprequest.getContextPath() + "/admin2/login.jsp");
            }
        }

    }
}
