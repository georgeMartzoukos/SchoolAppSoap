package gr.aueb.cf.schoolapp.controller.usercontrollers;

import javax.servlet.*;
import java.io.IOException;

//@WebFilter(filterName = "GrFilter")
public class GrFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }
}
