package com.invoice.controllers.home;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.invoice.bean.Client;
import com.invoice.bean.InvoiceGST;
import com.invoice.bean.ResponseCA;
import com.invoice.bean.User;
import com.invoice.service.home.InvoiceGstHomeService;
import com.invoice.utils.AppUtil;
import com.invoice.utils.NumberInWords;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

@Controller
@RequestMapping(value = "/invoiceGst")
public class InvoiceGstHomeController {
	@Autowired
	InvoiceGstHomeService invoiceHomeService;
	
	@RequestMapping(value = "/home")
	public ModelAndView adminInvoiceHome(ModelMap model, HttpServletRequest request, HttpSession session) {
		
		System.out.println("Welcome to Invoice-GST System.");
		
		return new ModelAndView("home/ca_invoice_gst_home");
	}
	
	@RequestMapping(value = "/createServiceInvoice")
	public ModelAndView createServiceInvoice(ModelMap model,HttpServletRequest request,Client client) {
		List<Client> clientNameList = invoiceHomeService.getClientNameList(client);
		model.addAttribute("clientNameList", clientNameList);
		return new ModelAndView("invoiceGst/ca_create_service_invoice",model);
	}
	
	
	@RequestMapping(value = "/submitServiceInvoiceCreationRegularDetails")
	public @ResponseBody Object submitServiceInvoiceCreationRegularDetails(InvoiceGST invoice, HttpServletRequest request,
		   @RequestParam("clientId") int clientId, @RequestParam("invoiceDate") String invoiceDate,
		   @RequestParam("allParticularsName") String allParticularsName, @RequestParam("allParticularsAmount") String allParticularsAmount) {

		ResponseCA response = new ResponseCA();
		
		int userId = 0;
		try {
			Client client = invoiceHomeService.getClientsDetailsById(clientId);
			invoice.setClient(client);
			invoice.setClientName(client.getClientname());
			invoice.setClientAddress(client.getAddress());
			invoice.setGstin(client.getGstin());

			userId = (int) request.getSession().getAttribute("userId");
			User user = invoiceHomeService.getUserDetailsById(userId);
			invoice.setUser(user);
			invoice.setInvoiceUserName(user.getUsername());

			invoice.setInvoiceDate(AppUtil.changeDateFormat(invoiceDate));
			invoice.setMonth(AppUtil.monthInWordsWithDate(invoiceDate));
			invoice.setFinancialYear(AppUtil.financialYearWithAnyDate(invoiceDate));
			invoice.setYear(AppUtil.yearWithDate(invoiceDate));

			invoice.setInvoiceFlag("Open");
			
			invoice.setGstType(invoice.getGstType());
			invoice.setParticulars(allParticularsName);
			invoice.setParticularsAmount(allParticularsAmount);
			
			String maxInvoiceNo = invoiceHomeService.getMaxInvoiceNo(invoice.getFinancialYear());
			if (maxInvoiceNo == null) {
				maxInvoiceNo = "0";
			} else {
				String[] strArr = maxInvoiceNo.split("/");
				maxInvoiceNo = strArr[0];
			}
			String invoiceFiveDigitNo = AppUtil.getFiveDigitsWithZero(Integer.parseInt(maxInvoiceNo));
			String invoiceNo = invoiceFiveDigitNo + "/"
					/*+ AppUtil.monthInDigits(AppUtil.changeDateFormatToClash(invoiceDetails.getInvoiceDate())) + "/"*/
					+ AppUtil.financialYearWithAnyDate(AppUtil.changeDateFormatToClash(invoice.getInvoiceDate())).substring(2, 7);
			System.out.println("Generated InvoiceNo : " + invoiceNo);
			invoice.setInvoiceNo(invoiceNo);
			
			boolean flag = invoiceHomeService.submitServiceInvoiceCreationRegular(invoice);
			if (flag == true) {
				response.setMessage("Submitted Successfully");
			} else {
				response.setMessage("Submitted UnSuccessfully Try again!");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			response.setMessage("Submitted UnSuccessfully Try again!");
		}
		return response;
	}
	
	@RequestMapping(value = "/submitServiceInvoiceCreationOneTimeDetails")
	public @ResponseBody Object submitServiceInvoiceCreationOneTimeDetails(InvoiceGST invoice, HttpServletRequest request, 
		   @RequestParam("invoiceDate") String invoiceDate, @RequestParam("allParticularsName") String allParticularsName,
		   @RequestParam("allParticularsAmount") String allParticularsAmount) {

		ResponseCA response = new ResponseCA();
		int userId = 0;
		try {
			userId = (int) request.getSession().getAttribute("userId");

			System.out.println("userId : " + userId);
			User user = invoiceHomeService.getUserDetailsById(userId);
			invoice.setUser(user);
			invoice.setInvoiceUserName(user.getUsername());

			invoice.setClientName(invoice.getClientName().toUpperCase());
			invoice.setInvoiceDate(AppUtil.changeDateFormat(invoiceDate));
			invoice.setMonth(AppUtil.monthInWordsWithDate(invoiceDate));
			invoice.setFinancialYear(AppUtil.financialYearWithAnyDate(invoiceDate));
			invoice.setYear(AppUtil.yearWithDate(invoiceDate));
			

			invoice.setInvoiceFlag("Open");
			
			invoice.setGstType(invoice.getGstType());
			invoice.setParticulars(allParticularsName);
			invoice.setParticularsAmount(allParticularsAmount);
			
			String maxInvoiceNo = invoiceHomeService.getMaxInvoiceNo(invoice.getFinancialYear());
			if (maxInvoiceNo == null) {
				maxInvoiceNo = "0";
			} else {
				String[] strArr = maxInvoiceNo.split("/");
				maxInvoiceNo = strArr[0];
			}
			String invoiceFiveDigitNo = AppUtil.getFiveDigitsWithZero(Integer.parseInt(maxInvoiceNo));
			String invoiceNo = invoiceFiveDigitNo + "/"
					/*+ AppUtil.monthInDigits(AppUtil.changeDateFormatToClash(invoiceDetails.getInvoiceDate())) + "/"*/
					+ AppUtil.financialYearWithAnyDate(AppUtil.changeDateFormatToClash(invoice.getInvoiceDate())).substring(2, 7);
			System.out.println("Generated InvoiceNo : " + invoiceNo);
			invoice.setInvoiceNo(invoiceNo);
			
			if (invoice.getGstin().isEmpty()) { invoice.setGstin(null); }

			boolean flag = invoiceHomeService.submitServiceInvoiceCreationOneTime(invoice);
			if (flag == true) {
				response.setMessage("Submitted Successfully");
			} else {
				response.setMessage("Submitted UnSuccessfully Try again!");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			response.setMessage("Submitted UnSuccessfully Try again!");
		}
		return response;
	}
	
	@RequestMapping(value = "/viewServiceInvoice")
	public ModelAndView viewServiceInvoice(ModelMap model,HttpServletRequest request,Client client,HttpSession session) {
		int userId = 0;
		try {
			userId= (int) request.getSession().getAttribute("userId");
		} catch (Exception e) {}
		
		User user = invoiceHomeService.getUserDetailsById(userId);
		
		if (user.getDesignation().getDesignationName().equalsIgnoreCase("ADMIN")) {
			List<InvoiceGST> invoiceList = invoiceHomeService.getInvoiceListAllDetails();
			model.addAttribute("invoiceList", invoiceList);
		} else {
			List<InvoiceGST> invoiceList = invoiceHomeService.getInvoiceListDetails(userId);
			model.addAttribute("invoiceList", invoiceList);
		}
		return new ModelAndView("invoiceGst/ca_view_service_invoice",model);
	}
	
	@RequestMapping(value = "/editingServiceInvoice")
	public ModelAndView editingServiceInvoice(ModelMap model,HttpServletRequest request,@RequestParam("invoiceId") int invoiceId, Client client) {
		System.out.println("InvoiceId for editing : "+invoiceId);
		List<Client> clientNameList = invoiceHomeService.getClientNameList(client);
		model.addAttribute("clientNameList", clientNameList);
		InvoiceGST invoiceDetails = invoiceHomeService.getInvoiceDetailsById(invoiceId);
		invoiceDetails.setInvoiceDate(AppUtil.changeDateFormatToClash(invoiceDetails.getInvoiceDate()));
		model.addAttribute("invoiceDetails", invoiceDetails);
		return new ModelAndView("invoiceGst/ca_edit_service_invoice",model);
	}
	
	@RequestMapping(value = "/deletingServiceInvoiceDetails")
	public @ResponseBody Object deletingServiceInvoiceDetails(@RequestParam("invoiceId") int invoiceId) {
			
		ResponseCA response = new ResponseCA();
		System.out.println("InvoiceId for deleting : "+invoiceId);
		
	    boolean flag = invoiceHomeService.deletingServiceInvoiceDetails(invoiceId);
		if (flag == true) {
			response.setMessage("Deleted Successfully");
		} else {
			response.setMessage("Deleted UnSuccessfully Try again!");
		}
		return response;
	}
	
	@RequestMapping(value = "/updateServiceInvoiceCreationRegularDetails")
	public @ResponseBody Object updateServiceInvoiceCreationRegularDetails(InvoiceGST invoice, HttpServletRequest request,
		   @RequestParam("clientId") int clientId, @RequestParam("invoiceDate") String invoiceDate,
		   @RequestParam("allParticularsName") String allParticularsName, @RequestParam("allParticularsAmount") String allParticularsAmount) {

		ResponseCA response = new ResponseCA();
		
		int userId = 0;
		try {
			InvoiceGST invoiceDetails = invoiceHomeService.getInvoiceDetailsById(invoice.getInvoiceId());
			
			invoice.setInvoiceId(invoiceDetails.getInvoiceId());
			Client client = invoiceHomeService.getClientsDetailsById(clientId);
			invoice.setClient(client);
			invoice.setClientName(client.getClientname());
			invoice.setClientAddress(client.getAddress());
			invoice.setGstin(client.getGstin());

			userId = (int) request.getSession().getAttribute("userId");
			User user = invoiceHomeService.getUserDetailsById(userId);
			invoice.setUser(user);
			invoice.setInvoiceUserName(user.getUsername());
			invoice.setInvoiceDate(AppUtil.changeDateFormat(invoiceDate));
			invoice.setMonth(AppUtil.monthInWordsWithDate(invoiceDate));
			invoice.setFinancialYear(AppUtil.financialYearWithAnyDate(invoiceDate));
			invoice.setYear(AppUtil.yearWithDate(invoiceDate));

			invoice.setInvoiceFlag("Modify");
			
			invoice.setGstType(invoice.getGstType());
			invoice.setParticulars(allParticularsName);
			invoice.setParticularsAmount(allParticularsAmount);
			
			invoice.setInvoiceNo(invoiceDetails.getInvoiceNo());
			
			boolean flag = invoiceHomeService.submitServiceInvoiceCreationRegular(invoice);
			if (flag == true) {
				response.setMessage("Updated Successfully");
			} else {
				response.setMessage("Updated UnSuccessfully Try again!");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			response.setMessage("Updated UnSuccessfully Try again!");
		}
		return response;
	}
	
	@RequestMapping(value = "/updateServiceInvoiceCreationOneTimeDetails")
	public @ResponseBody Object updateServiceInvoiceCreationOneTimeDetails(InvoiceGST invoice, HttpServletRequest request, 
		   @RequestParam("invoiceDate") String invoiceDate, @RequestParam("allParticularsName") String allParticularsName,
		   @RequestParam("allParticularsAmount") String allParticularsAmount) {

		ResponseCA response = new ResponseCA();
		int userId = 0;
		try {
			
			InvoiceGST invoiceDetails = invoiceHomeService.getInvoiceDetailsById(invoice.getInvoiceId());
			invoice.setInvoiceId(invoiceDetails.getInvoiceId());
			
			userId = (int) request.getSession().getAttribute("userId");
			System.out.println("userId : " + userId);
			User user = invoiceHomeService.getUserDetailsById(userId);
			invoice.setUser(user);
			invoice.setInvoiceUserName(user.getUsername());

			invoice.setClientName(invoice.getClientName().toUpperCase());
			String invoiceDate1 = AppUtil.changeDateFormatToClash(invoiceDate);
			invoice.setInvoiceDate(AppUtil.changeDateFormat(AppUtil.changeDateFormatToClash(invoiceDate1)));
			invoice.setMonth(AppUtil.monthInWordsWithDate(invoiceDate1));
			invoice.setFinancialYear(AppUtil.financialYearWithAnyDate(invoiceDate1));
			invoice.setYear(AppUtil.yearWithDate(invoiceDate1));
			

			invoice.setInvoiceFlag("Modify");
			
			invoice.setGstType(invoice.getGstType());
			invoice.setParticulars(allParticularsName);
			invoice.setParticularsAmount(allParticularsAmount);
			
			invoice.setInvoiceNo(invoiceDetails.getInvoiceNo());
			
			if (invoice.getGstin().isEmpty()) { invoice.setGstin(null); }

			boolean flag = invoiceHomeService.submitServiceInvoiceCreationOneTime(invoice);
			if (flag == true) {
				response.setMessage("Updated Successfully");
			} else {
				response.setMessage("Updated UnSuccessfully Try again!");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			response.setMessage("Updated UnSuccessfully Try again!");
		}
		return response;
	}
	
	@RequestMapping(value = "/printInvoice")
	public ModelAndView printInvoice(ModelMap model,HttpServletRequest request,Client client,HttpSession session) {
		int userId = 0;
		try {
			userId= (int) request.getSession().getAttribute("userId");
		} catch (Exception e) {}
		
		User user = invoiceHomeService.getUserDetailsById(userId);
		
		if (user.getDesignation().getDesignationName().equalsIgnoreCase("ADMIN")) {
			List<InvoiceGST> invoiceList = invoiceHomeService.getInvoiceListAllDetails();
			model.addAttribute("invoiceList", invoiceList);
		} else {
			List<InvoiceGST> invoiceList = invoiceHomeService.getInvoiceListDetails(userId);
			model.addAttribute("invoiceList", invoiceList);
		}
		return new ModelAndView("invoiceGst/ca_print_service_invoice",model);
	}
	

	@RequestMapping(value = "/printInvoiceCopy/{invoiceId}")
	public ModelAndView printInvoiceCopy(HttpServletRequest request, ModelMap model, @PathVariable("invoiceId") int invoiceId){
		
		System.out.println("InvoiceId: "+invoiceId);
		InvoiceGST  invoiceDetailsById = invoiceHomeService.getInvoiceDetailsById(invoiceId);
		model.addAttribute("invoiceDetailsById", invoiceDetailsById);
		String date = AppUtil.changeDateFormatToClash(invoiceDetailsById.getInvoiceDate());
		model.addAttribute("dayOfMonth", AppUtil.dayOfMonth(date));
		model.addAttribute("monthNameAndYear", AppUtil.monthNameAndYear(date));
		model.addAttribute("dayOfMonthSuffix", AppUtil.getDayOfMonthSuffix(date));
		model.addAttribute("numberInWords", NumberInWords.convert(invoiceDetailsById.getTotalAmount()));
		
		return new ModelAndView("invoiceGst/ca_view_print_invoice",model);
	}
	
	@RequestMapping(value = "/printPdfInvoiceClientCopy/{invoiceId}")
	public @ResponseBody void printPdfInvoiceClientCopy(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("invoiceId") int invoiceId)throws ServletException, IOException, DocumentException  {
		
		InvoiceGST  invoiceDetails = invoiceHomeService.getInvoiceDetailsById(invoiceId);
		String date = AppUtil.changeDateFormatToClash(invoiceDetails.getInvoiceDate());
		String totalAmount = invoiceDetails.getTotalAmount();
		String numberInWords = NumberInWords.convert(totalAmount);
		
		response.setContentType("application/pdf");
		
		Font font11 = new Font(FontFamily.TIMES_ROMAN, 11);
		Font font11Bold = new Font(FontFamily.TIMES_ROMAN, 11, Font.BOLD);
		Font font12Bold = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		Font font14Bold = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD);
		
		
		Document documentClientCopy = new Document();
		documentClientCopy.setPageSize(PageSize.A4);
		
		try {
			PdfWriter.getInstance(documentClientCopy, response.getOutputStream()); 
			documentClientCopy.open();
			Chunk glue = new Chunk(new VerticalPositionMark());
			
		    //creating header
			PdfPTable headingTable = new PdfPTable(1);
			headingTable.setWidthPercentage(100);
			PdfPCell headingCell = new PdfPCell(new Paragraph("", font14Bold));
			//headingCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			headingCell.setBorderColor(BaseColor.WHITE);
			headingTable.addCell(headingCell);
			
			PdfPCell headingCell2 = new PdfPCell(new Paragraph("", font12Bold));
			headingCell2.setBorderColor(BaseColor.WHITE);
			headingTable.addCell(headingCell2);
			
			PdfPCell headingCell3 = new PdfPCell();
			headingCell3.setBorderColor(BaseColor.WHITE);
			headingTable.addCell(headingCell3);
			documentClientCopy.add(headingTable);
			
			documentClientCopy.add(Chunk.NEWLINE);
			documentClientCopy.add(new Paragraph("          "));
			documentClientCopy.add(new Paragraph("          " ));
			documentClientCopy.add(new Paragraph("          "));
			
			
			PdfPTable rowTable = new PdfPTable(1);
			rowTable.setWidthPercentage(100);
			Paragraph p1;
			if (invoiceDetails.getClientType().equalsIgnoreCase("OneTime")) {
				p1=new Paragraph(" Our GSTIN: "+"XXXXXXXXXXXXXX",font11Bold);
			} else {
				p1=new Paragraph(" Our GSTIN: "+"XXXXXXXXXXXXXX",font11Bold);
			}
			PdfPCell rowCell = new PdfPCell(p1);
			rowCell.setBorderColor(BaseColor.WHITE);
			rowCell.setPadding(0);
			rowTable.addCell(rowCell);
			
			Paragraph p2=new Paragraph("Bill No: "+invoiceDetails.getInvoiceNo(),font11Bold);
			p2.add(new Chunk(glue));
			p2.add("Date: "+AppUtil.dayOfMonth(date)+AppUtil.getDayOfMonthSuffix(date)+" "+AppUtil.monthNameAndYear(date));
			PdfPCell rowCellp2 = new PdfPCell(p2);
			rowCellp2.setBorderColor(BaseColor.WHITE);
			rowTable.addCell(rowCellp2);
			documentClientCopy.add(rowTable);
			
			documentClientCopy.add(Chunk.NEWLINE);
			
			Paragraph p3=new Paragraph("To,",font11);
			documentClientCopy.add(p3);
			
			String gstin = "";
			if ((invoiceDetails.getGstin() != null) && (!invoiceDetails.getGstin().isEmpty())) { gstin = invoiceDetails.getGstin(); }
			
			PdfPTable rowTablep4 = new PdfPTable(1);
			rowTablep4.setWidthPercentage(60);
			rowTablep4.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell rowCellp4 = new PdfPCell(new Paragraph(invoiceDetails.getClientName()+"\n"+invoiceDetails.getClientAddress()+
					"\n\nGSTIN: "+gstin,font11));
			rowCellp4.setBorderColor(BaseColor.WHITE);
			rowCellp4.setBorder(0);
			rowCellp4.setHorizontalAlignment(Element.ALIGN_LEFT);
			rowTablep4.addCell(rowCellp4);
			documentClientCopy.add(rowTablep4);
			
			documentClientCopy.add(Chunk.NEWLINE);
			documentClientCopy.add(new Paragraph("          "));
			
			Paragraph p6=new Paragraph("Dear Sir/Madam,",font11);
			documentClientCopy.add(p6);
			
			documentClientCopy.add(Chunk.NEWLINE);
			
			Chunk underline = new Chunk("Invoice",font11Bold);
		    underline.setUnderline(0.2f, -2f); //0.2 thick, -2 y-location
		     
			PdfPTable rowTableSub = new PdfPTable(1);
			rowTableSub.setWidthPercentage(100);
			PdfPCell rowCellSub = new PdfPCell(new Phrase(underline));
			rowCellSub.setBorderColor(BaseColor.WHITE);
			rowCellSub.setPadding(0);
			rowCellSub.setHorizontalAlignment(Element.ALIGN_CENTER);
			rowTableSub.addCell(rowCellSub);
			documentClientCopy.add(rowTableSub);
			
			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setWidths(new float[] {1f,6f,2f});
			
			PdfPCell cell0 = new PdfPCell(new Paragraph("Sl.No", font11Bold));
			cell0.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell0.setBorderColor(BaseColor.BLACK);
			cell0.setPadding(7);
			table.addCell(cell0);
			
			/*PdfPCell cell1 = new PdfPCell(new Paragraph("SAC", font11Bold));
			cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setBorderColor(BaseColor.BLACK);
			cell1.setPadding(7);
			table.addCell(cell1);*/
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("Particulars", font11Bold));
			cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setBorderColor(BaseColor.BLACK);
			cell1.setPadding(7);
			table.addCell(cell1);


			PdfPCell cell3 = new PdfPCell(new Paragraph("Amt.in Rs.", font11Bold));
			cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setBorderColor(BaseColor.BLACK);
			cell3.setPadding(7);
			table.addCell(cell3);

			documentClientCopy.add(Chunk.NEWLINE);
			
			// first row in the table
			if (invoiceDetails.getInvoiceType().equalsIgnoreCase("Retainer Invoice")) {
				
				PdfPCell cellRow2 = new PdfPCell(new Paragraph("1", font11));
				cellRow2.setPadding(6);
				cellRow2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellRow2.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
				table.addCell(cellRow2);
				
				/*PdfPCell cellRow21 = new PdfPCell(new Paragraph(invoiceDetails.getSac(), font11));
				cellRow21.setPadding(6);
				cellRow21.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellRow21.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
				table.addCell(cellRow21);
*/				
				PdfPCell cellRow1 = new PdfPCell(new Paragraph("Professional Fee for the month of "+AppUtil.monthNameAndYear(date).replace(',', '-'), font11));
				cellRow1.setPadding(6);
				cellRow1.setHorizontalAlignment(Element.ALIGN_LEFT);
				//cellRow1.setBorder(PdfPCell.NO_BORDER);
				cellRow1.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
				table.addCell(cellRow1);
				
				PdfPCell cellRow11 = new PdfPCell(new Paragraph("", font11));
				cellRow11.setPadding(6);
				cellRow11.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//cellRow11.setBorder(PdfPCell.NO_BORDER);
				cellRow11.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
				table.addCell(cellRow11);
			}else {
			 if(invoiceDetails.getParticulars()!=null && invoiceDetails.getParticularsAmount()!=null){
				//String sac[]= invoiceDetails.getSac().split(",");
				String particulars[]= invoiceDetails.getParticulars().split(",");
				String particularsAmount[]= invoiceDetails.getParticularsAmount().split(",");
				
				if (particulars.length == particularsAmount.length) {
					for (int i = 0; i < particulars.length; i++) {
						int count = i + 1;
						PdfPCell cellRow2i = new PdfPCell(new Paragraph(count+"", font11));
						cellRow2i.setPadding(6);
						cellRow2i.setHorizontalAlignment(Element.ALIGN_CENTER);
						cellRow2i.setBorder(Rectangle.LEFT);
						table.addCell(cellRow2i);
						
						/*PdfPCell cellRow21i = new PdfPCell(new Paragraph("", font11));
						cellRow21i.setPadding(6);
						cellRow21i.setHorizontalAlignment(Element.ALIGN_CENTER);
						cellRow21i.setBorder(Rectangle.LEFT);
						table.addCell(cellRow21i);*/
						
						PdfPCell cellRow1i = new PdfPCell(new Paragraph(particulars[i], font11));
						cellRow1i.setPadding(6);
						cellRow1i.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellRow1i.setBorder(Rectangle.LEFT);
						table.addCell(cellRow1i);
						
						PdfPCell cellRow11i = new PdfPCell(new Paragraph(particularsAmount[i], font11));
						cellRow11i.setPadding(6);
						cellRow11i.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cellRow11i.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
						table.addCell(cellRow11i);
					}
				  }
			    }	
				PdfPCell cellRow1 = new PdfPCell(new Paragraph("Sub-Total Amount", font11Bold));
				cellRow1.setPadding(6);
				cellRow1.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellRow1.setBorder(Rectangle.LEFT | Rectangle.TOP);
				cellRow1.setColspan(2);
				table.addCell(cellRow1);
				
				PdfPCell cellRow11 = new PdfPCell(new Paragraph(invoiceDetails.getInvoiceAmount(), font11Bold));
				cellRow11.setPadding(6);
				cellRow11.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellRow11.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.TOP);
				table.addCell(cellRow11);
			}

		  if(invoiceDetails.getGstType().equalsIgnoreCase("Local")) {
			PdfPCell cellRow2 = new PdfPCell(new Paragraph("Add: CGST @ 9.00%", font11));
			cellRow2.setPadding(6);
			cellRow2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellRow2.setBorder(Rectangle.LEFT);
			cellRow2.setColspan(2);
			table.addCell(cellRow2);
			PdfPCell cellRow21 = new PdfPCell(new Paragraph(invoiceDetails.getCgstAmount(), font11));
			cellRow21.setPadding(6);
			cellRow21.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellRow21.setBorder(Rectangle.RIGHT |Rectangle.LEFT);
			table.addCell(cellRow21);
			
			
			PdfPCell cellRow3 = new PdfPCell(new Paragraph("Add: SGST @ 9.00%", font11));
			cellRow3.setPadding(6);
			cellRow3.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellRow3.setBorder(Rectangle.LEFT);
			cellRow3.setColspan(2);
			table.addCell(cellRow3);
			PdfPCell cellRow31 = new PdfPCell(new Paragraph(invoiceDetails.getSgstAmount(), font11));
			cellRow31.setPadding(6);
			cellRow31.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellRow31.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			table.addCell(cellRow31);
		   }
			
		   if(invoiceDetails.getGstType().equalsIgnoreCase("Interstate")) {
			PdfPCell cellRow4 = new PdfPCell(new Paragraph("Add: IGST @ 18.00%", font11));
			cellRow4.setPadding(6);
			cellRow4.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellRow4.setBorder(Rectangle.LEFT);
			cellRow4.setColspan(2);
			table.addCell(cellRow4);
			PdfPCell cellRow41 = new PdfPCell(new Paragraph(invoiceDetails.getIgstAmount(), font11));
			cellRow41.setPadding(6);
			cellRow41.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellRow41.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			table.addCell(cellRow41);
		   }
			
			PdfPCell cellRow5 = new PdfPCell(new Paragraph("(Rupees "+numberInWords+")", font11Bold));
			cellRow5.setPadding(6);
			cellRow5.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellRow5.setBorderColor(BaseColor.BLACK);
			cellRow5.setColspan(2);
			table.addCell(cellRow5);
			PdfPCell cellRow51 = new PdfPCell(new Paragraph(totalAmount, font11Bold));
			cellRow51.setPadding(6);
			cellRow51.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellRow51.setBorderColor(BaseColor.BLACK);
			table.addCell(cellRow51);
			documentClientCopy.add(table);
			
			documentClientCopy.add(Chunk.NEWLINE);
			
			PdfPTable headingTable2s = new PdfPTable(2);
			headingTable2s.setWidthPercentage(104);
			headingTable2s.setWidths(new float[] {0.3f,5.7f});
			
			PdfPCell headingCell21s = new PdfPCell(new Paragraph("for", font11));
			headingCell21s.setBorderColor(BaseColor.WHITE);
			headingCell21s.setBorder(0);
			headingCell21s.setHorizontalAlignment(Element.ALIGN_RIGHT);
			headingTable2s.addCell(headingCell21s);
			
			PdfPCell headingCell21s2 = new PdfPCell(new Paragraph("< COMPANY NAME >", font12Bold));
			headingCell21s2.setBorderColor(BaseColor.WHITE);
			headingCell21s2.setBorder(0);
			headingCell21s2.setHorizontalAlignment(Element.ALIGN_LEFT);
			headingTable2s.addCell(headingCell21s2);
			
			documentClientCopy.add(headingTable2s);
			
			PdfPTable headingTable2 = new PdfPTable(1);
			headingTable2.setWidthPercentage(100);
			PdfPCell headingCell22 = new PdfPCell(new Paragraph(" ", font11));
			headingCell22.setBorderColor(BaseColor.WHITE);
			headingTable2.addCell(headingCell22);
			
			PdfPCell headingCell23 = new PdfPCell();
			headingCell23.setBorderColor(BaseColor.WHITE);
			headingTable2.addCell(headingCell23);
			documentClientCopy.add(headingTable2);
			
			documentClientCopy.add(Chunk.NEWLINE);
			documentClientCopy.add(new Paragraph("          "));
			
			documentClientCopy.add(Chunk.NEWLINE);
			documentClientCopy.add(new Paragraph("          "));
			
			PdfPTable headingTable3 = new PdfPTable(1);
			headingTable3.setWidthPercentage(100);
			PdfPCell headingCell31 = new PdfPCell(new Paragraph("Authorised Signatory", font12Bold));
			headingCell31.setBorderColor(BaseColor.WHITE);
			headingTable3.addCell(headingCell31);
			
			PdfPCell headingCell33 = new PdfPCell();
			headingCell33.setBorderColor(BaseColor.WHITE);
			headingTable3.addCell(headingCell33);
			documentClientCopy.add(headingTable3);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		documentClientCopy.close();
	}
	
	@RequestMapping(value = "/report")
	public ModelAndView invoiceReport(ModelMap model, HttpServletRequest request) {
		return new ModelAndView("invoiceGst/ca_invoice_report");
	}
	
	@RequestMapping(value = "/approvedInvoiceReportListForAdmin")
	public ModelAndView approvedInvoiceReportListForAdmin(ModelMap model, HttpServletRequest request,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		fromDate = AppUtil.changeDateFormat(fromDate);
		toDate = AppUtil.changeDateFormat(toDate);
		System.out.println(fromDate+"-->"+toDate);
		List<InvoiceGST> approvedInvoiceReport = invoiceHomeService.getApprovedInvoiceReportListForAdmin(fromDate, toDate);
		System.out.println("Approved Invoice Report Size for Admin: "+approvedInvoiceReport.size());
		model.addAttribute("approvedInvoiceReport", approvedInvoiceReport);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		return new ModelAndView("invoiceGst/ca_invoice_report_table", model);
	}
	
	
	@RequestMapping(value = "/invoiceReportListExcel")
	public @ResponseBody Object invoiceReportListExcel(ModelMap model, HttpServletRequest request,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		ResponseCA response = new ResponseCA();
		try {
			List<InvoiceGST> approvedInvoiceReport = null;
			System.out.println("fromDate: " + fromDate + " And toDate: " + toDate);
			//String designation = (String) request.getSession().getAttribute("designation");
			
			int userId = 0;
			try {
				userId= (int) request.getSession().getAttribute("userId");
			} catch (Exception e) {}
			
				approvedInvoiceReport = invoiceHomeService.getApprovedInvoiceReportListForAdmin(fromDate, toDate);
				System.out.println("Approved Invoice Report Size for Admin: " + approvedInvoiceReport.size());

			int count = 1;
			int countSlNo = 0;
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Invoice Report Details");

			Map<Integer, Object[]> map = new HashMap<Integer, Object[]>();
			map.put(count,
					new Object[] { "SL.NO", "INVOICE NO", "CLIENT NAME", "CLIENT TYPE", "INVOICE TYPE", "GST TYPE", "GSTIN", "PARTICULARS",
							"PARTICULARS AMOUNT", "INVOICE DATE", "INVOICE AMOUNT", "CGST AMOUNT", "SGST AMOUNT", "IGST AMOUNT", "TOTAL AMOUNT", 
							"MONTH", "FINANCIAL YEAR"});

			for (InvoiceGST invoice : approvedInvoiceReport) {
				count += 1;
				countSlNo += 1;
				
				int invoiceAmount = 0;
				String particulars = null;
				String particularsAmount = null;
				if (invoice.getInvoiceType().equalsIgnoreCase("Service Invoice")) {
					invoiceAmount = Integer.parseInt(invoice.getInvoiceAmount());
					particulars = invoice.getParticulars().replaceAll(",", " , ");
					particularsAmount = invoice.getParticularsAmount().replaceAll(",", " , ");
				}
				
				map.put(count, new Object[] { 
					countSlNo, invoice.getInvoiceNo(), invoice.getClientName(), invoice.getClientType(), invoice.getInvoiceType(),
					invoice.getGstType(), invoice.getGstin(),particulars, particularsAmount, AppUtil.changeDateFormatToClash(invoice.getInvoiceDate()),
					invoiceAmount, Integer.parseInt(invoice.getCgstAmount()), Integer.parseInt(invoice.getSgstAmount()), 
					Integer.parseInt(invoice.getIgstAmount()), Integer.parseInt(invoice.getTotalAmount()),
					invoice.getMonth(), invoice.getFinancialYear() });
			}

			Set<Integer> keyset = map.keySet();
			int rowNum = 0;
			for (Integer key : keyset) {
				Row row = sheet.createRow(rowNum++);
				Object[] objkey = map.get(key);
				int cellnum = 0;
				for (Object obj : objkey) {
					Cell cell = row.createCell(cellnum++);
					if (obj instanceof String)
						cell.setCellValue((String) obj);
					else if (obj instanceof Integer)
						cell.setCellValue((Integer) obj);
					else if (obj instanceof Double)
						cell.setCellValue((Double) obj);
					else if (obj instanceof Boolean)
						cell.setCellValue((Boolean) obj);
				}
			}
			String rootPath = request.getSession().getServletContext().getRealPath("");
			File dir = new File(rootPath + File.separator + "resources" + File.separator + "Uploads" + File.separator
					+ "Invoice GST Excel");
			if (!dir.exists())
				dir.mkdirs();
			// Create the file on server
			File serverFile = new File(dir.getAbsolutePath() + File.separator + userId+"_"+"INVOICE_GST_REPORT_DETAILS.xls");
			FileOutputStream out = new FileOutputStream(serverFile);
			workbook.write(out);
			out.close();

			response.setMessage("Excel File Created & Exported Data Successfully!");

		} catch (Exception e) {
			response.setMessage("Excel File Created & Exported Data UnSuccessfully!");
			e.printStackTrace();
		}
		return response;
	}
	
}

