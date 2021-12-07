package com.webstore.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.webstore.bean.AdminUser;
import com.webstore.bean.User;
import com.webstore.service.UserService;

public class UserServlet extends BaseServlet {

	public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		UserService service = new UserService();
		boolean flag = service.checkUsername(username);
		String json = "{\"isExit\":" + flag + "}";
		response.getWriter().write(json);
	}
	
	public void checkEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		String email = request.getParameter("email");
		UserService service = new UserService();
		boolean flag = service.checkEmail(email);
		String json = "{\"isExit\":" + flag + "}";
		response.getWriter().write(json);
	}


	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code1 = request.getParameter("checkCode");
		String code2 = (String) request.getSession().getAttribute("checkcode_session");
		request.getSession().removeAttribute("checkcode_session");
		if (!code1.equals(code2)) {
			request.setAttribute("register_info", "验证码错误");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		} else {
			try {
				Map<String, String[]> parameterMap = request.getParameterMap();
				User user = new User();
				BeanUtils.populate(user, parameterMap);
				user.setUid(UUID.randomUUID().toString());
				UserService service = new UserService();
				boolean flag = service.register(user);
				if (flag == true) {
					request.getRequestDispatcher("/register_success_info.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("/register.jsp").forward(request, response);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String code1 = request.getParameter("checkcode");
		String code2 = (String) request.getSession().getAttribute("checkcode_session");
		request.getSession().removeAttribute("checkcode_session");
		if (!code1.equals(code2)) {
			request.setAttribute("login_info", "验证码错误");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			UserService service = new UserService();
			User user = null;
			try {
				user = service.login(username, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect(request.getContextPath());
			} else {
				request.setAttribute("log_info", "用户名或密码错误");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
	}

	public void logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("user");
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}
}
