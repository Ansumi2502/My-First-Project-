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
@Table(name="key_master")
public class KeyMaster {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="key_master_id_seq")
    @SequenceGenerator(name="key_master_id_seq", sequenceName="key_master_id_seq", allocationSize=1)
    private long id;
	private String name;
	private String value;
	
	public KeyMaster(long id, String name, String value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}
	public KeyMaster(String name, String value) {
		this.name = name;
		this.value = value;
	}
	public KeyMaster() {
		// TODO Auto-generated constructor stub
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
