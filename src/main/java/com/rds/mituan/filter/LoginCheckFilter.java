package com.rds.mituan.filter;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.rds.mituan.common.BaseContext;
import com.rds.mituan.common.R;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.JsonAggregateFunction;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;


@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //得到request和response,httpServletRequest才有getRequestURI()方法
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        //定义不需要请求的路径
        String[] urls=new String[]{
                "/employee/logout",
                "/employee/login",
                "/backend/**",
                "/front/**"
        };
        //检查是否需要处理本次请求
        boolean check=check(urls,request.getRequestURI());
        if(check){
            filterChain.doFilter(request,response);
            return;
        }
        //判断登录状态
        if(request.getSession().getAttribute("employee")!=null) {

            Long empId=(Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }
        //未登录的处理
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
    public boolean check(String[] urls,String requestURI){
        for (String url: urls) {
            if(antPathMatcher.match(url,requestURI)) return true;
        }
        return false;
    }
}
