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

@Entity
@Table(name="receipt_master")
public class ReceiptMaster {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="receipt_master_id_seq")
    @SequenceGenerator(name="receipt_master_id_seq", sequenceName="receipt_master_id_seq", allocationSize=1)
	private long id;
	
	private String receiptNo;
	private Date receiptDate;
	private double amount;
	private String paymode;
	private String rtgs_utr_no;
	private long receipt_seq;
	private String status;
	
	public ReceiptMaster() {
	}
	public ReceiptMaster(long id, String receiptNo, Date receiptDate,double amount,
		String paymode,String rtgs_utr_no,long receipt_seq,String status) {
		this.id = id;
		this.receiptNo = receiptNo;
		this.receiptDate = receiptDate;
		this.amount=amount;
		this.paymode = paymode;
		this.rtgs_utr_no = rtgs_utr_no;
		this.receipt_seq = receipt_seq;
		this.status = status;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPaymode() {
		return paymode;
	}
	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}
	public String getRtgs_utr_no() {
		return rtgs_utr_no;
	}
	public void setRtgs_utr_no(String rtgs_utr_no) {
		this.rtgs_utr_no = rtgs_utr_no;
	}
	public Date getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
	public long getReceipt_seq() {
		return receipt_seq;
	}
	public void setReceipt_seq(long receipt_seq) {
		this.receipt_seq = receipt_seq;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
