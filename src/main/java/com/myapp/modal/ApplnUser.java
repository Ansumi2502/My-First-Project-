/**
 * Created By Anamika Pandey
 */
package com.myapp.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="appln_user")
public class ApplnUser {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="appln_user_id_seq")
    @SequenceGenerator(name="appln_user_id_seq", sequenceName="appln_user_id_seq", allocationSize=1)
    private long id;
	private String password;
	private String userId;
	private String name;
	private String email;
	private String userType;
	private String status;
	private String mobileNo;
	@ManyToOne
	@JoinColumn (name="depo_id")
	private DepotMaster depotMaster;
	
	public ApplnUser() {
		
	}
	
	public ApplnUser(String password, String userId, String name, String email, String userType, String status,
			String mobileNo) {
		this.password = password;
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.userType = userType;
		this.status = status;
		this.mobileNo = mobileNo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public DepotMaster getDepotMaster() {
		return depotMaster;
	}

	public void setDepotMaster(DepotMaster depotMaster) {
		this.depotMaster = depotMaster;
	}
}
