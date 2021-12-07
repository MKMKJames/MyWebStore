package com.webstore.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.webstore.bean.Cart;
import com.webstore.bean.CartItem;
import com.webstore.bean.Order;
import com.webstore.bean.OrderItem;
import com.webstore.bean.User;
import com.webstore.service.ProductService;
import com.webstore.service.TransactService;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TransactionServlet extends BaseServlet {
	public void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pid = request.getParameter("pid");
		String pimage = request.getParameter("pimage");
		String pname = request.getParameter("pname");
		double shop_price = Double.parseDouble(request.getParameter("shop_price"));
		int orderNum = Integer.parseInt(request.getParameter("orderNum"));
		double sum_price = shop_price * orderNum;
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
		}
		Map<String, CartItem> map = cart.getMap();
		if (map.containsKey(pid)) {
			map.get(pid).setOrderNum(map.get(pid).getOrderNum() + orderNum);
			cart.setSumMoney(cart.getSumMoney() + sum_price);
			map.get(pid).setSum_price(map.get(pid).getSum_price() + sum_price);
			request.getSession().setAttribute("cart", cart);
		} else {
			CartItem item = new CartItem();
			item.setPid(pid);
			item.setPimage(pimage);
			item.setPname(pname);
			item.setShop_price(shop_price);
			item.setOrderNum(orderNum);
			item.setSum_price(sum_price);
			map.put(pid, item);
			cart.setSumMoney(sum_price + cart.getSumMoney());
			request.getSession().setAttribute("cart", cart);
		}
		response.sendRedirect(request.getContextPath() + "/payment/cart.jsp");
	}

	public void removeItem(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String pid = request.getParameter("pid");
		cart.setSumMoney(cart.getSumMoney() - cart.getMap().get(pid).getSum_price());
		cart.getMap().remove(pid);
		request.getSession().setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath() + "/payment/cart.jsp");
	}

	public void modifyAmount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String pid = request.getParameter("pid");
		int amount = Integer.parseInt(request.getParameter("amount"));
		int old_amount = Integer.parseInt(request.getParameter("old_amount"));
		double shop_price = Double.parseDouble(request.getParameter("shop_price"));
		Map<String, CartItem> map = cart.getMap();

		map.get(pid).setOrderNum(amount);
		cart.setSumMoney(cart.getSumMoney() + (amount - old_amount) * shop_price);
		map.get(pid).setSum_price(amount * shop_price);
		request.getSession().setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath() + "/payment/cart.jsp");
	}

	public void clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.getSession().removeAttribute("cart");
		response.sendRedirect(request.getContextPath() + "/payment/cart.jsp");
	}

	public void subOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null) {
			response.sendRedirect(request.getContextPath() + "/payment/cart.jsp");
		} else {
			Order order = new Order();
			String oid = UUID.randomUUID().toString();
			String ordertime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			double total = cart.getSumMoney();
			User user = (User) request.getSession().getAttribute("user");
			order.setOid(oid);
			order.setOrdertime(ordertime);
			order.setTotal(total);
			order.setUser(user);
			List<OrderItem> orderItems = order.getOrderItems();
			Map<String, CartItem> itemMap = cart.getMap();
			for (String key : itemMap.keySet()) {
				CartItem cartItem = itemMap.get(key);
				OrderItem orderItem = new OrderItem();
				orderItem.setItemid(UUID.randomUUID().toString());
				orderItem.setCount(cartItem.getOrderNum());
				orderItem.setSubtotal(cartItem.getSum_price());
				orderItem.setProduct(new ProductService().getProduct_info(cartItem.getPid()));
				orderItem.setOrder(order);
				orderItems.add(orderItem);
			}
			request.getSession().setAttribute("order", order);
			response.sendRedirect(request.getContextPath() + "/payment/order_info.jsp");
		}
	}

	private void sendEmail(String email) throws AddressException, MessagingException {
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");// 连接协议
		properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
		properties.put("mail.smtp.port", 465);// 端口号
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
		properties.put("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
		// 得到回话对象
		Session session = Session.getInstance(properties);
		// 获取邮件对象
		Message message = new MimeMessage(session);
		// 设置发件人邮箱地址
		message.setFrom(new InternetAddress("599039929@qq.com"));
		// 设置收件人邮箱地址
		// message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new
		// InternetAddress("599039929@qq.com"),new
		// InternetAddress("599039929@qq.com"),new InternetAddress("xxx@qq.com")});
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));// 一个收件人
		// 设置邮件标题
		message.setSubject("购买成功");
		// 设置邮件内容
		message.setText("您的订单已成功提交给商家！");
		// 得到邮差对象
		Transport transport = session.getTransport();
		// 连接自己的邮箱账户
		transport.connect("599039929@qq.com", "ajpjzqvhwemsbdja");// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
		// 发送邮件
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	public void payOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		TransactService service = new TransactService();
		Order order = (Order) request.getSession().getAttribute("order");
		order.setAddress(address);
		order.setName(name);
		order.setTelephone(telephone);
		boolean success = service.subOrder(order);
		if (success) {
			try {
				sendEmail(order.getUser().getEmail());
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("发送失败！");
				e.printStackTrace();
			}
			request.getSession().removeAttribute("cart");
			request.getSession().removeAttribute("order");
			request.getRequestDispatcher("pay_success_info.jsp").forward(request, response);
		} else {
			System.out.println("subOrder failed");
		}
	}

	public void findOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TransactService service = new TransactService();
		User user = (User) request.getSession().getAttribute("user");
		List<Order> orderList = service.findOrderList(user);
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("order_list.jsp").forward(request, response);
	}
}