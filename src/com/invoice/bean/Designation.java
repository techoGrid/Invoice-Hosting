package com.invoice.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DESIGNATION")
public class Designation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DESIGNATION_ID")
	private int designationId;

	@Column(name = "ACTIVE_FLAG")
	private int active;

	@Column(name = "DESIGNATION_NAME")
	private String designationName;

	@Column(name = "ROLE_NAME")
	private String roleName;

	public int getDesignationId() {
		return designationId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
