package com.rupesh.blog.idam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdamResponse {

    @JsonProperty("REQUEST_REFERENCE_NUMBER")
    private String requestReferenceNumber;

    @JsonProperty("RESPONSE")
    private String response;

    @JsonProperty("RESPONSE_DATE")
    private String responseDate;
    
    @JsonProperty("DIGI_SIGN")
    private String digiSign;;
    

    public String getRequestReferenceNumber() {
        return requestReferenceNumber;
    }

    public void setRequestReferenceNumber(String requestReferenceNumber) {
        this.requestReferenceNumber = requestReferenceNumber;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

	public String getDigiSign() {
		return digiSign;
	}

	public void setDigiSign(String digiSign) {
		this.digiSign = digiSign;
	}
    
    
}

