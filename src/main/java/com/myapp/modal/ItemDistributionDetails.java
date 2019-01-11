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
@Table(name="item_distribution_details")
public class ItemDistributionDetails {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="item_distribution_details_id_seq")
    @SequenceGenerator(name="item_distribution_details_id_seq", sequenceName="item_distribution_details_id_seq", allocationSize=1)
	private long id;
	
	@ManyToOne
	@JoinColumn (name="bill_details_id")
	private BillDetails billDetails;
	
	@ManyToOne
	@JoinColumn (name="brand_id")
	private BrandMaster brandMaster;
	
	private double amount;
	
	private double cases;
	
	private double pieces;
	
	public ItemDistributionDetails() {
		
	}

	public ItemDistributionDetails(long id, BillDetails billDetails, BrandMaster brandMaster, double amount
			, int cases, int pieces) {
		
		this.id = id;
		this.billDetails = billDetails;
		this.brandMaster = brandMaster;
		this.amount = amount;
		this.cases = cases;
		this.pieces = pieces;
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

	public BrandMaster getBrandMaster() {
		return brandMaster;
	}

	public void setBrandMaster(BrandMaster brandMaster) {
		this.brandMaster = brandMaster;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getCases() {
		return cases;
	}

	public void setCases(double cases) {
		this.cases = cases;
	}

	public double getPieces() {
		return pieces;
	}

	public void setPieces(double pieces) {
		this.pieces = pieces;
	}
	
	
}
