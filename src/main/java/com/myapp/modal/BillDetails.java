/**
 * Created By Anamika Pandey
 */
package com.myapp.modal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="bill_details")
public class BillDetails {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="bill_details_id_seq")
    @SequenceGenerator(name="bill_details_id_seq", sequenceName="bill_details_id_seq", allocationSize=1)
	private long id;
	
	@ManyToOne
	@JoinColumn (name="appln_user_id")
	private ApplnUser applnUser;
	
	@ManyToOne
	@JoinColumn (name="depo_id")
	private DepotMaster depotMaster;
	
	@ManyToOne
	@JoinColumn (name="customer_id")
	private CustomerMaster customerMaster;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_s;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date entry_date;
	
	private String invoiceNo;
	private String gstin;
	private double total_amount;
	private String truck_temp_no;
	private String batch_no;
	private long invoice_seq;
	
	private String status;
	
	public BillDetails() {
		
	}
	
	public BillDetails(long id, ApplnUser applnUser, DepotMaster depotMaster, CustomerMaster customerMaster,
			Date date_s, Date entry_date, String invoiceNo, String gstin, double total_amount, String truck_temp_no,
			String batch_no,long invoice_seq,String status) {
		this.id = id;
		this.applnUser = applnUser;
		this.depotMaster = depotMaster;
		this.customerMaster = customerMaster;
		this.date_s = date_s;
		this.entry_date = entry_date;
		this.invoiceNo = invoiceNo;
		this.gstin = gstin;
		this.total_amount = total_amount;
		this.truck_temp_no = truck_temp_no;
		this.batch_no = batch_no;
		this.invoice_seq = invoice_seq;
		this.status = status;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ApplnUser getApplnUser() {
		return applnUser;
	}
	public void setApplnUser(ApplnUser applnUser) {
		this.applnUser = applnUser;
	}
	public DepotMaster getDepotMaster() {
		return depotMaster;
	}
	public void setDepotMaster(DepotMaster depotMaster) {
		this.depotMaster = depotMaster;
	}
	public CustomerMaster getCustomerMaster() {
		return customerMaster;
	}
	public void setCustomerMaster(CustomerMaster customerMaster) {
		this.customerMaster = customerMaster;
	}
	public Date getDate_s() {
		return date_s;
	}
	public void setDate_s(Date date_s) {
		this.date_s = date_s;
	}
	public Date getEntry_date() {
		return entry_date;
	}
	public void setEntry_date(Date entry_date) {
		this.entry_date = entry_date;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getGstin() {
		return gstin;
	}
	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public String getTruck_temp_no() {
		return truck_temp_no;
	}

	public void setTruck_temp_no(String truck_temp_no) {
		this.truck_temp_no = truck_temp_no;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public long getInvoice_seq() {
		return invoice_seq;
	}

	public void setInvoice_seq(long invoice_seq) {
		this.invoice_seq = invoice_seq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
