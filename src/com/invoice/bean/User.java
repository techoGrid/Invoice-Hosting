package com.invoice.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(columnNames = { "USER_NAME", "EMAIL_ID" }) )
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private int userId;

	@ManyToOne
	@JoinColumn(name = "DESIGNATION_ID")
	private Designation designation;

	@Column(name = "ACTIVE_FLAG")
	private int active;

	@Column(name = "DISPLAY_NAME")
	private String displayname;

	@Column(name = "USER_NAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "CREATED_DATE")
	private String createdDate;

	@Column(name = "UPDATED_DATE")
	private String updatedDate;

	@Column(name = "EMAIL_ID")
	private String email;

	@Column(name = "MOBILE_NO")
	private String mobile;

	@Column(name = "LOCK_STATUS")
	private String lockStatus;

	@Column(name = "NO_OF_FAILURE_ATTEMPTS")
	private int noOfFailureAttempts;

	@Column(name = "FAILURE_ATTEMPTS_DATE")
	private String failureAttemptsDate;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public int getNoOfFailureAttempts() {
		return noOfFailureAttempts;
	}

	public void setNoOfFailureAttempts(int noOfFailureAttempts) {
		this.noOfFailureAttempts = noOfFailureAttempts;
	}

	public String getFailureAttemptsDate() {
		return failureAttemptsDate;
	}

	public void setFailureAttemptsDate(String failureAttemptsDate) {
		this.failureAttemptsDate = failureAttemptsDate;
	}

}