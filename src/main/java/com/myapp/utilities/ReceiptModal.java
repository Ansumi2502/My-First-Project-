package com.myapp.utilities;

import java.util.Date;

public class ReceiptModal {
	
	private String invoiceNumber;
	
	private String ReceiptNumber;
	
	private String customerName;
	
	private String DepotName;
	
	private Date receiptDate;
	
	private double amount;
	
	private String paymentMode;
	
	private String rtgsUtrNo;
	
	private long receiptMasterId;
	
	private String status;

	public ReceiptModal() {
		
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getReceiptNumber() {
		return ReceiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		ReceiptNumber = receiptNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDepotName() {
		return DepotName;
	}

	public void setDepotName(String depotName) {
		DepotName = depotName;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getRtgsUtrNo() {
		return rtgsUtrNo;
	}

	public void setRtgsUtrNo(String rtgsUtrNo) {
		this.rtgsUtrNo = rtgsUtrNo;
	}

	public long getReceiptMasterId() {
		return receiptMasterId;
	}

	public void setReceiptMasterId(long receiptMasterId) {
		this.receiptMasterId = receiptMasterId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
