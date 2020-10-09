package com.invoice.dao.home;

import java.util.List;

import com.invoice.bean.Client;
import com.invoice.bean.InvoiceGST;
import com.invoice.bean.User;

public interface InvoiceGstHomeDao {

	List<Client> getClientNameList(Client client);

	Client getClientsDetailsById(int clientId);

	User getUserDetailsById(int userId);

	boolean submitServiceInvoiceCreationRegular(InvoiceGST invoice);

	boolean submitServiceInvoiceCreationOneTime(InvoiceGST invoice);

	String getMaxInvoiceNo(String financialYear);

	List<InvoiceGST> getInvoiceListAllDetails();

	List<InvoiceGST> getInvoiceListDetails(int userId);

	boolean deletingServiceInvoiceDetails(int invoiceId);

	InvoiceGST getInvoiceDetailsById(int invoiceId);

	List<InvoiceGST> getInvoiceDetailsById(String fromDate, String toDate);

	

}
