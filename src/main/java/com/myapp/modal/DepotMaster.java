/**
 * Created By Anamika Pandey
 */
package com.myapp.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="depo_master")
public class DepotMaster {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="depo_master_id_seq")
    @SequenceGenerator(name="depo_master_id_seq", sequenceName="depo_master_id_seq", allocationSize=1)
	private long id;
	private String name;
	private String code;
	private String address;
	private String pan_tan_no;
	private String mobile_no;
	
	public DepotMaster() {
		
	}
	public DepotMaster(long id, String name, String code, String address, String pan_tan_no, String mobile_no) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.address = address;
		this.pan_tan_no = pan_tan_no;
		this.mobile_no = mobile_no;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPan_tan_no() {
		return pan_tan_no;
	}
	public void setPan_tan_no(String pan_tan_no) {
		this.pan_tan_no = pan_tan_no;
	}
	
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}	
	
}
