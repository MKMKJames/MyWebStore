package com.webstore.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.iap.Response;

@WebFilter("/*")
public class UserLoginFilter implements Filter {

    public UserLoginFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String uri = request.getRequestURI();
        if(!uri.contains("payment")) {
            chain.doFilter(req, resp);
        } else {
            Object user = request.getSession().getAttribute("user");
            if(user != null) {
                chain.doFilter(req,resp);
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }

    }


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
