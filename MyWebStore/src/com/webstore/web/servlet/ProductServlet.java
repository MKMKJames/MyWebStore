package com.webstore.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.webstore.bean.Category;
import com.webstore.bean.PageBean;
import com.webstore.bean.Product;
import com.webstore.bean.User;
import com.webstore.bean.VisitLog;
import com.webstore.service.CategoryService;
import com.webstore.service.ProductService;
import com.webstore.service.VisitService;

public class ProductServlet extends BaseServlet {
	public void getCategoryList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CategoryService service = new CategoryService();
		List<Category> categoryList = service.fingAllCategory();
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
	}

	public void getProduct_info(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String pid = request.getParameter("pid");
		ProductService service = new ProductService();
		Product product = service.getProduct_info(pid);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			VisitLog visit = new VisitLog();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			visit.setTime(df.format(new Date()));
			visit.setPid(product.getPid());
			visit.setUid(user.getUid());
			VisitService visitService = new VisitService();
			visitService.logVisit(visit);
		}
		request.setAttribute("product", product);
		request.getRequestDispatcher("product_info.jsp").forward(request, response);
	}

	public void getProductList(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String cid = request.getParameter("cid");
		request.setAttribute("cid", cid);
		PageBean pageBean = new PageBean();
		int pageContent = 12;
		pageBean.setPageContent(pageContent);
		int pageIndex;
		if (request.getParameter("pageIndex") == null) {
			pageIndex = 1;
		} else {
			pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		}
		pageBean.setPageIndex(pageIndex);
		ProductService service = new ProductService();
		int totalRecord = service.totalRecord(cid);
		pageBean.setTotalRecord(totalRecord);
		int totalPage = (int) Math.ceil(1.0 * totalRecord / pageContent);
		pageBean.setTotalPage(totalPage);
		request.setAttribute("pageBean", pageBean);
		List<Product> productList = service.findCategoryProduct(pageBean, cid);
		request.setAttribute("productList", productList);
		request.getRequestDispatcher("product_list.jsp").forward(request, response);
	}

	public void getProductListByName(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String name = request.getParameter("bookname");
		request.setAttribute("bookname", name);
		PageBean pageBean = new PageBean();
		int pageContent = 12;
		pageBean.setPageContent(pageContent);
		int pageIndex;
		if (request.getParameter("pageIndex") == null) {
			pageIndex = 1;
		} else {
			pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		}
		pageBean.setPageIndex(pageIndex);
		ProductService service = new ProductService();
		int totalRecord = service.totalRecordByName(name);
		pageBean.setTotalRecord(totalRecord);
		int totalPage = (int) Math.ceil(1.0 * totalRecord / pageContent);
		pageBean.setTotalPage(totalPage);
		request.setAttribute("pageBean", pageBean);
		List<Product> productList = service.findCategoryProductByName(pageBean, name);
		request.setAttribute("productList", productList);
		request.getRequestDispatcher("product_list_by_name.jsp").forward(request, response);
	}
}
