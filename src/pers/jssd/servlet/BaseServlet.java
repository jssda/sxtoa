package pers.jssd.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 该Servlet不需要进行配置，因为该Servlet从来不需要被直接访问，使用来被继承的
 *
 * @author jssdjing@gmail.com
 */
public abstract class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //解决post表单中文乱码问题
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        //获取method属性的值（方法名）
        String methodName = request.getParameter("method");
        //使用反射调用方法
        try {
            //获取当前Servlet的Class信息
            Class<?> clazz = this.getClass();//实际访问的Servlet，不是BaseServlet，是BaseServlet的子类比如UserServlet
            //使用反射创建对象
            //Object obj = clazz.newInstance();
            //获取方法
            Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //使用反射执行方法
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
