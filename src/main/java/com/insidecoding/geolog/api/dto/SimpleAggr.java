package com.insidecoding.geolog.api.dto;

public class SimpleAggr {
	private String label;
	private Long value;

	public SimpleAggr(String n, Long v) {
		this.label = n;
		this.value = v;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long amount) {
		this.value = amount;
	}

	public String toCsv() {
		return label + "," + value;
	}
}
