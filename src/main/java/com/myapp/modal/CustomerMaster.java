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
@Table(name="customer_master")
public class CustomerMaster {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="customer_master_id_seq")
    @SequenceGenerator(name="customer_master_id_seq", sequenceName="customer_master_id_seq", allocationSize=1)
	private long id;
	private String name_of_licence;
	private String name_of_shop;
	private String address;
	private String licence_no_of_shop;
	private String firm_name;
	private String pan_no_of_firm_name;
	private String pan_no_of_licencee;
	
	@ManyToOne
	@JoinColumn (name="depo_id")
	private DepotMaster depotMaster;
	
	private String mobileNo;
	
	public CustomerMaster() {
		
	}
	
	public CustomerMaster(long id, String name_of_licence, String name_of_shop, String address,
			String licence_no_of_shop, String firm_name, String pan_no_of_firm_name, DepotMaster depotMaster
			, String mobileNo, String pan_no_of_licencee) {
		this.id = id;
		this.name_of_licence = name_of_licence;
		this.name_of_shop = name_of_shop;
		this.address = address;
		this.licence_no_of_shop = licence_no_of_shop;
		this.firm_name = firm_name;
		this.pan_no_of_firm_name = pan_no_of_firm_name;
		this.depotMaster = depotMaster;
		this.mobileNo = mobileNo;
		this.pan_no_of_licencee = pan_no_of_licencee;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName_of_licence() {
		return name_of_licence;
	}
	public void setName_of_licence(String name_of_licence) {
		this.name_of_licence = name_of_licence;
	}
	public String getName_of_shop() {
		return name_of_shop;
	}
	public void setName_of_shop(String name_of_shop) {
		this.name_of_shop = name_of_shop;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLicence_no_of_shop() {
		return licence_no_of_shop;
	}
	public void setLicence_no_of_shop(String licence_no_of_shop) {
		this.licence_no_of_shop = licence_no_of_shop;
	}
	public String getFirm_name() {
		return firm_name;
	}
	public void setFirm_name(String firm_name) {
		this.firm_name = firm_name;
	}
	public String getPan_no_of_firm_name() {
		return pan_no_of_firm_name;
	}
	public void setPan_no_of_firm_name(String pan_no_of_firm_name) {
		this.pan_no_of_firm_name = pan_no_of_firm_name;
	}

	public DepotMaster getDepotMaster() {
		return depotMaster;
	}

	public void setDepotMaster(DepotMaster depotMaster) {
		this.depotMaster = depotMaster;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPan_no_of_licencee() {
		return pan_no_of_licencee;
	}

	public void setPan_no_of_licencee(String pan_no_of_licencee) {
		this.pan_no_of_licencee = pan_no_of_licencee;
	}

}
