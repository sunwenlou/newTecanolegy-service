package com.sun.wen.lou.newtec.entity;

public class ResourceType {
	private int code_value;
	private String code_name;

	public ResourceType() {
	}

	public ResourceType(int code_value, String code_name) {
		this.code_value = code_value;
		this.code_name = code_name;
	}

	public int getCode_value() {
		return this.code_value;
	}

	public void setCode_value(int code_value) {
		this.code_value = code_value;
	}

	public String getCode_name() {
		return this.code_name;
	}

	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
}