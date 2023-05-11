package com.incedo.smart_inventory.controllers.models;

public class ErrorData {
	
	private String code = null;
	private String message = null;
	private String field = null;
	private String value = null;
	
	public ErrorData(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public ErrorData(String code, String message, String field, String value) {
		super();
		this.code = code;
		this.message = message;
		this.field = field;
		this.value = value;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
