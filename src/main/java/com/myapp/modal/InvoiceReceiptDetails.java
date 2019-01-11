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
@Table(name="invoice_receipt_details")
public class InvoiceReceiptDetails {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="invoice_receipt_details_id_seq")
    @SequenceGenerator(name="invoice_receipt_details_id_seq", sequenceName="invoice_receipt_details_id_seq", allocationSize=1)
	private long id;
	@ManyToOne
	@JoinColumn (name="bill_details_id")
	private BillDetails billDetails;
	
	@ManyToOne
	@JoinColumn (name="receipt_master_id")
	private ReceiptMaster receiptMaster;
	
	public InvoiceReceiptDetails() {
	}
	
	public InvoiceReceiptDetails(long id, BillDetails billDetails, ReceiptMaster receiptMaster) {
		super();
		this.id = id;
		this.billDetails = billDetails;
		this.receiptMaster = receiptMaster;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BillDetails getBillDetails() {
		return billDetails;
	}

	public void setBillDetails(BillDetails billDetails) {
		this.billDetails = billDetails;
	}

	public ReceiptMaster getReceiptMaster() {
		return receiptMaster;
	}

	public void setReceiptMaster(ReceiptMaster receiptMaster) {
		this.receiptMaster = receiptMaster;
	}
	
}
