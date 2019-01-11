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
@Table(name="brand_master")
public class BrandMaster {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="brand_master_id_seq")
    @SequenceGenerator(name="brand_master_id_seq", sequenceName="brand_master_id_seq", allocationSize=1)
	private long id;
	private String name;
	private String percentage;
	private double amount;
	
	public BrandMaster() {
		
	}

	public BrandMaster(long id, String name, String percentage, double amount) {
		this.id = id;
		this.name = name;
		this.percentage = percentage;
		this.amount = amount;
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

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
