package com.invoice.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CLIENT_ID")
	private int clientId;

	@Column(name = "ACTIVE_FLAG")
	private int active;

	@Column(name = "CLIENT_NAME")
	private String clientname;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITY")
	private String clientCity;

	@Column(name = "STATE")
	private String clientState;

	@Column(name = "PIN_CODE")
	private String clientPinCode;

	@Column(name = "MOBILE")
	private String clientMobile;

	@Column(name = "PHONE_OFFICE")
	private String officePhone;

	@Column(name = "EMAIL1")
	private String clientEmail1;

	@Column(name = "ST_NO")
	private String stNo;

	@Column(name = "VAT_NO")
	private String vatNo;

	@Column(name = "CST_NO")
	private String cstNo;

	@Column(name = "PAN_NO")
	private String panNo;

	@Column(name = "TAN_NO")
	private String tanNo;

	@Column(name = "GSTIN")
	private String gstin;

	@Column(name = "CREATED_DATE")
	private String createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private String updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClientCity() {
		return clientCity;
	}

	public void setClientCity(String clientCity) {
		this.clientCity = clientCity;
	}

	public String getClientState() {
		return clientState;
	}

	public void setClientState(String clientState) {
		this.clientState = clientState;
	}

	public String getClientPinCode() {
		return clientPinCode;
	}

	public void setClientPinCode(String clientPinCode) {
		this.clientPinCode = clientPinCode;
	}

	public String getClientMobile() {
		return clientMobile;
	}

	public void setClientMobile(String clientMobile) {
		this.clientMobile = clientMobile;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getClientEmail1() {
		return clientEmail1;
	}

	public void setClientEmail1(String clientEmail1) {
		this.clientEmail1 = clientEmail1;
	}

	public String getStNo() {
		return stNo;
	}

	public void setStNo(String stNo) {
		this.stNo = stNo;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	public String getCstNo() {
		return cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getTanNo() {
		return tanNo;
	}

	public void setTanNo(String tanNo) {
		this.tanNo = tanNo;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
 
}
