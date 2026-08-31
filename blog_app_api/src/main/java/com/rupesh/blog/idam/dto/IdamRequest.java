package com.rupesh.blog.idam.dto;

public class IdamRequest {
	
	private String REQUEST_REFERENCE_NUMBER;
	private String DIGI_SIGN;
	private String REQUEST;
	
	public String getREQUEST_REFERENCE_NUMBER() {
		return REQUEST_REFERENCE_NUMBER;
	}
	public void setREQUEST_REFERENCE_NUMBER(String rEQUEST_REFERENCE_NUMBER) {
		REQUEST_REFERENCE_NUMBER = rEQUEST_REFERENCE_NUMBER;
	}
	public String getDIGI_SIGN() {
		return DIGI_SIGN;
	}
	public void setDIGI_SIGN(String dIGI_SIGN) {
		DIGI_SIGN = dIGI_SIGN;
	}
	public String getREQUEST() {
		return REQUEST;
	}
	public void setREQUEST(String rEQUEST) {
		REQUEST = rEQUEST;
	}
	
	
}
