package com.invoice.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "INVOICE_GST_DETAILS")
public class InvoiceGST {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INVOICE_ID")
	private int invoiceId;

	@Column(name = "INVOICE_FLAG")
	private String invoiceFlag;

	@Column(name = "INVOICE_NO")
	private String invoiceNo;

	@ManyToOne
	@JoinColumn(name = "CLIENT_ID")
	private Client client;
	
	@Column(name = "CLIENT_NAME")
	private String clientName;

	@ManyToOne
	@JoinColumn(name = "INVOICE_USER_ID")
	private User User;

	@Column(name = "INVOICE_USER_NAME")
	private String invoiceUserName;

	@Column(name = "CLIENT_TYPE")
	private String clientType;

	@Column(name = "PARTICULARS", columnDefinition = "varchar(1024)")
	private String particulars;

	@Column(name = "INVOICE_TYPE")
	private String invoiceType;

	@Column(name = "INVOICE_DATE")
	private String invoiceDate;

	@Column(name = "INVOICE_AMOUNT")
	private String invoiceAmount;

	@Column(name = "MONTH")
	private String month;

	@Column(name = "YEAR")
	private String year;

	@Column(name = "FINANCIAL_YEAR")
	private String financialYear;

	@Column(name = "CGST_AMOUNT")
	private String cgstAmount;

	@Column(name = "SGST_AMOUNT")
	private String sgstAmount;

	@Column(name = "IGST_AMOUNT")
	private String igstAmount;
	
	@Column(name = "CGST_PERCENTAGE")
	private String cgstPercentage;

	@Column(name = "SGST_PERCENTAGE")
	private String sgstPercentage;

	@Column(name = "IGST_PERCENTAGE")
	private String igstPercentage;

	@Column(name = "GST_TYPE")
	private String gstType;

	@Column(name = "TOTAL_AMOUNT")
	private String totalAmount;

	@Column(name = "CLIENT_ADDRESS")
	private String clientAddress;

	@Column(name = "MODIFY_REMARKS")
	private String modifyRemarks;

	@Column(name = "DELETE_REMARKS")
	private String deleteRemarks;

	@Column(name = "PARTICULARS_AMOUNT")
	private String particularsAmount;

	@Column(name = "GSTIN")
	private String gstin;

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceFlag() {
		return invoiceFlag;
	}

	public void setInvoiceFlag(String invoiceFlag) {
		this.invoiceFlag = invoiceFlag;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public String getInvoiceUserName() {
		return invoiceUserName;
	}

	public void setInvoiceUserName(String invoiceUserName) {
		this.invoiceUserName = invoiceUserName;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getCgstAmount() {
		return cgstAmount;
	}

	public void setCgstAmount(String cgstAmount) {
		this.cgstAmount = cgstAmount;
	}

	public String getSgstAmount() {
		return sgstAmount;
	}

	public void setSgstAmount(String sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	public String getIgstAmount() {
		return igstAmount;
	}

	public void setIgstAmount(String igstAmount) {
		this.igstAmount = igstAmount;
	}

	public String getCgstPercentage() {
		return cgstPercentage;
	}

	public void setCgstPercentage(String cgstPercentage) {
		this.cgstPercentage = cgstPercentage;
	}

	public String getSgstPercentage() {
		return sgstPercentage;
	}

	public void setSgstPercentage(String sgstPercentage) {
		this.sgstPercentage = sgstPercentage;
	}

	public String getIgstPercentage() {
		return igstPercentage;
	}

	public void setIgstPercentage(String igstPercentage) {
		this.igstPercentage = igstPercentage;
	}

	public String getGstType() {
		return gstType;
	}

	public void setGstType(String gstType) {
		this.gstType = gstType;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getModifyRemarks() {
		return modifyRemarks;
	}

	public void setModifyRemarks(String modifyRemarks) {
		this.modifyRemarks = modifyRemarks;
	}

	public String getDeleteRemarks() {
		return deleteRemarks;
	}

	public void setDeleteRemarks(String deleteRemarks) {
		this.deleteRemarks = deleteRemarks;
	}

	public String getParticularsAmount() {
		return particularsAmount;
	}

	public void setParticularsAmount(String particularsAmount) {
		this.particularsAmount = particularsAmount;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

}
