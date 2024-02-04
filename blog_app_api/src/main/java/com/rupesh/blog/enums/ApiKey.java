package com.rupesh.blog.enums;

public enum ApiKey {
	
	MESSAGE("message"), SUCCESS("SUCCESS");

	String value;
	
	private ApiKey(String value) {
		this.value= value;
	}
	
	public String val() {
		return value;
	}
}
