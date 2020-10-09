package com.invoice.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/viewFile")
public class ViewFile {

	@RequestMapping(value = "/downloadExcelForInvoiceGstDetails/{invoiceExcelName}")
	public void downloadExcelForInvoiceGstDetails(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("invoiceExcelName") String invoiceExcelName) throws ServletException, IOException {
		
		int userId = 0;
		try { userId= (int) request.getSession().getAttribute("userId"); }
		catch (Exception e) {}
		
		response.setContentType("text/html;charset=UTF-8");

		ServletOutputStream outs = response.getOutputStream();
		
		response.setContentType("application/vnd.ms-excel");
		
		// ---------------------------------------------------------------
		// create an input stream from fileURL
		// ---------------------------------------------------------------
		String rootPath = request.getSession().getServletContext().getRealPath("");
		String filepath = rootPath + File.separator + "resources"
				+ File.separator + "Uploads" + File.separator
				+ "Invoice GST Excel" + File.separator + userId+"_"+invoiceExcelName+".xls";

		File file = new File(filepath);

		// ------------------------------------------------------------
		// Content-disposition header - don't open in browser and
		// set the "Save As..." filename.
		// *There is reportedly a bug in IE4.0 which ignores this...
		// ------------------------------------------------------------
		
		//response.setHeader("Content-disposition", "inline; filename=\"" +  "INVOICE_REPORT_DETAILS.xls" + "\"");
		//										(OR)
		response.setHeader("Content-disposition", "attachment; filename="+userId+"_"+invoiceExcelName+".xls");

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {

			InputStream isr = new FileInputStream(file);
			bis = new BufferedInputStream(isr);
			bos = new BufferedOutputStream(outs);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			System.out.println("Exception --- Message --->" + e);
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
	
}
