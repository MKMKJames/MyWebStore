package com.webstore.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.webstore.bean.AdminUser;
import com.webstore.bean.PageBean;
import com.webstore.bean.Product;
import com.webstore.bean.PurchaseLog;
import com.webstore.bean.VisitLog;
import com.webstore.service.ProductService;
import com.webstore.service.PurchaseService;
import com.webstore.service.UserService;
import com.webstore.service.VisitService;

public class AdminServlet extends BaseServlet {
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	public void adminLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserService service = new UserService();
		AdminUser user = null;
		try {
			user = service.adminLogin(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("admin_user", user);
			response.sendRedirect(request.getContextPath() + "/admin/home.jsp");
		} else {
			// request.setAttribute("log_info", "用户名或密码错误");
			response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
		}
	}

	public void message(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileType = request.getParameter("fileType");
		String fileName = null;
		File file = File.createTempFile("TMP", ".csv");
		if (fileType.equals("visit")) {
			fileName = "用户浏览记录.csv";
			Appendable printWriter = new PrintWriter(file);
			VisitService service = new VisitService();
			List<VisitLog> list = service.getVisitList();
			CSVPrinter csvPrinter = CSVFormat.EXCEL.withHeader("time", "uid", "pid").print(printWriter);
			for (VisitLog visit : list) {
				csvPrinter.printRecord(visit.getTime(), visit.getUid(), visit.getPid());
			}
			csvPrinter.flush();
			csvPrinter.close();
		} else if (fileType.equals("purchase")) {
			fileName = "用户购物记录.csv";
			Appendable printWriter = new PrintWriter(file);
			PurchaseService service = new PurchaseService();
			List<PurchaseLog> list = service.getPurchaseList();
			CSVPrinter csvPrinter = CSVFormat.EXCEL.withHeader("time", "uid", "amount", "pid").print(printWriter);
			for (PurchaseLog purchase : list) {
				csvPrinter.printRecord(purchase.getOrdertime(), purchase.getUid(), purchase.getCount(),
						purchase.getPid());
			}
			csvPrinter.flush();
			csvPrinter.close();
		}
		String realname = fileName.substring(fileName.indexOf("_") + 1);
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
		FileInputStream in = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		byte buffer[] = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
		file.delete();
	}

	public void getAdminProductListByName(HttpServletRequest request, HttpServletResponse response)
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
		request.getRequestDispatcher("admin/product/list.jsp").forward(request, response);
	}

	private void delFile(String fileName) {
		try {
			fileName = this.getServletContext().getRealPath("/cover") + "/" + fileName;
			System.out.println(fileName);
			File file = new File(fileName);
			if (file != null && file.exists()) {
				file.delete();
				System.out.println("delete success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		String fileName = pid + ".jpg";
		delFile(fileName);
		ProductService service = new ProductService();
		service.delProduct(pid);
		request.setAttribute("message", "删除成功!");
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}

	public void updateProduct(HttpServletRequest request, HttpServletResponse response) {
		try {
			modify(request, response, true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void modify(HttpServletRequest request, HttpServletResponse response, boolean isUpdate) throws IOException {
		String uploadPath = this.getServletContext().getRealPath("/cover");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);
		upload.setHeaderEncoding("UTF-8");
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		try {
			@SuppressWarnings("unchecked")
			Product product = new Product();
			ProductService service = new ProductService();
			List<FileItem> formItems = upload.parseRequest(request);
			if (formItems != null && formItems.size() > 0) {
				if (isUpdate) {
					for (FileItem item : formItems) {
						String name = item.getFieldName();
						String value = item.getString();
						value = new String(value.getBytes("iso-8859-1"), "utf-8");
						if (value != null && !value.equals("") && name.equals("pid")) {
							product.setPid(value);
							break;
						}
					}
					product = service.getProduct_info(product.getPid());
					service.delProduct(product.getPid());
				}
				FileItem pictItem = null;
				for (FileItem item : formItems) {
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						if (fileName != null && !fileName.equals("")) {
							pictItem = item;
						}
					} else {
						String name = item.getFieldName();
						String value = item.getString();
						if (value != null && !value.equals("")) {
							value = new String(value.getBytes("iso-8859-1"), "utf-8");
							if (name.equals("pid")) {
								product.setPid(value);
							} else if (name.equals("pname")) {
								product.setPname(value);
							} else if (name.equals("market_price")) {
								product.setMarket_price(Double.parseDouble(value));
							} else if (name.equals("shop_price")) {
								product.setShop_price(Double.parseDouble(value));
							} else if (name.equals("pdesc")) {
								product.setPdesc(value);
							} else if (name.equals("cid")) {
								product.setCid(value);
							}
						}
					}
				}
				if (isUpdate)
					delFile(product.getPid() + ".jpg");
				String filePath = uploadPath + File.separator + product.getPid() + ".jpg";
				File storeFile = new File(filePath);
				pictItem.write(storeFile);
				product.setPimage("cover/" + product.getPid() + ".jpg");
				service.addNewProduct(product);
				String message = isUpdate ? "修改成功!" : "添加成功!";
				request.setAttribute("message", message);
				if (isUpdate) {
					// request.setAttribute("pid", product.getPid());
					request.getRequestDispatcher("/admin/product/edit.jsp?pid=" + product.getPid()).forward(request,
							response);
				} else {
					request.getRequestDispatcher("/admin/product/add.jsp").forward(request, response);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			String msg = "<b>增添/修改失败，请检查字段是否为空、是否存在输入错误，或商品编号有重复</b>";
			System.out.println(e);
			response.getWriter().write(msg);
		}
	}

	public void addProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		modify(request, response, false);
	}
}
