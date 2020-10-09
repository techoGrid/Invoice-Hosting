package com.invoice.service.home;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoice.bean.Client;
import com.invoice.bean.InvoiceGST;
import com.invoice.bean.User;
import com.invoice.dao.home.InvoiceGstHomeDao;


@Service
public class InvoiceGstHomeServiceImpl implements InvoiceGstHomeService{

	@Autowired
	InvoiceGstHomeDao invoiceHomeDao;

	@Override
	public List<Client> getClientNameList(Client client) {
		return invoiceHomeDao.getClientNameList(client);
	}

	@Override
	public Client getClientsDetailsById(int clientId) {
		return invoiceHomeDao.getClientsDetailsById(clientId);
	}

	@Override
	public User getUserDetailsById(int userId) {
		return invoiceHomeDao.getUserDetailsById(userId);
	}

	@Override
	public boolean submitServiceInvoiceCreationRegular(InvoiceGST invoice) {
		return invoiceHomeDao.submitServiceInvoiceCreationRegular(invoice);
	}

	@Override
	public boolean submitServiceInvoiceCreationOneTime(InvoiceGST invoice) {
		return invoiceHomeDao.submitServiceInvoiceCreationOneTime(invoice);
	}

	@Override
	public String getMaxInvoiceNo(String financialYear) {
		return invoiceHomeDao.getMaxInvoiceNo(financialYear);
	}

	@Override
	public List<InvoiceGST> getInvoiceListAllDetails() {
		return invoiceHomeDao.getInvoiceListAllDetails();
	}

	@Override
	public List<InvoiceGST> getInvoiceListDetails(int userId) {
		return invoiceHomeDao.getInvoiceListDetails(userId);
	}

	@Override
	public boolean deletingServiceInvoiceDetails(int invoiceId) {
		return invoiceHomeDao.deletingServiceInvoiceDetails(invoiceId);
	}

	@Override
	public InvoiceGST getInvoiceDetailsById(int invoiceId) {
		return invoiceHomeDao.getInvoiceDetailsById(invoiceId);
	}

	@Override
	public List<InvoiceGST> getApprovedInvoiceReportListForAdmin(String fromDate, String toDate) {
		return invoiceHomeDao.getInvoiceDetailsById(fromDate,toDate);
	}

	
}
