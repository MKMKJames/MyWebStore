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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.webstore.bean.PurchaseLog;
import com.webstore.bean.VisitLog;
import com.webstore.service.PurchaseService;
import com.webstore.service.VisitService;

public class DownLoadServlet extends BaseServlet {
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
				csvPrinter.printRecord(purchase.getOrdertime(), purchase.getUid(), purchase.getCount(), purchase.getPid());
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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}