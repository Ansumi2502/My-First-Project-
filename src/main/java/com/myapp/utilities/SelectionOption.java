/**
 * Created By Anamika Pandey
 */
package com.myapp.utilities;

import java.io.Serializable;

import org.springframework.util.ObjectUtils;

public class SelectionOption implements Serializable,Comparable {

	private String desc;

	private String text;

	private String value;

	public SelectionOption() {
		// Empty constructor;
	}

	public SelectionOption(String desc, String text, String value) {
		this.desc = desc;
		this.text = text;
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean equals(Object obj) {
		SelectionOption s = (SelectionOption) obj;
		if (s != null && ObjectUtils.nullSafeEquals(s.getDesc(), this.desc)
				&& ObjectUtils.nullSafeEquals(s.getValue(), this.value)
				&& ObjectUtils.nullSafeEquals(s.getText(), this.text)) {
			return true;
		}
		return false;
	}

	public String toString() {
		if (text != null) {
			return text;
		}
		return super.toString();
	}

	@Override
	public int compareTo(Object obj) {
		SelectionOption slOption= (SelectionOption)obj;
		
		//ascending order
		return this.text.compareToIgnoreCase(slOption.text);
	}
}
