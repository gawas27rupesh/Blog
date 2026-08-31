package com.rupesh.blog.idam.controller;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rupesh.blog.idam.dto.FullIdamReconRequest;
import com.rupesh.blog.idam.dto.IdamRequest;
import com.rupesh.blog.idam.dto.IdamRequestLoginTimestamp;
import com.rupesh.blog.idam.dto.IdamResponse;
import com.rupesh.blog.idam.services.IdamService;
import com.rupesh.blog.idam.util.EisCryptoUtil;
import com.rupesh.blog.idam.util.IdamUtil;
import com.rupesh.blog.idam.util.KeyPairObject;

@Controller
@RequestMapping("/idam/recon")
public class IdamController {

	@Autowired
	private IdamService idamService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private IdamUtil idamUtil;
	
	@Autowired
	private EisCryptoUtil eisCryptoUtil;
	

//	@RequestMapping(value = "/allDetails", method = RequestMethod.POST, produces = "application/json")
//	@ResponseBody
//	public IdamResponse getDetailsFull(@RequestBody String idamRequest, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		
//		// Step 1
//		String secretKey = UUID.randomUUID().toString().replace("-", "");
//		KeyPairObject keyPairObject = new KeyPairObject();
//		PublicKey publicKey = keyPairObject.getPublicKey();
//		PrivateKey privateKey = keyPairObject.getPrivateKey();
//		
//		IdamResponse idamResponse= new IdamResponse();
//		IdamRequest requestObjectPayload = (IdamRequest) new Gson().fromJson(idamRequest, IdamRequest.class);
//		String accessTokenRequest = request.getHeader("AccessToken");
//		
//		String requestAccessToken = idamUtil.decryptSHA256Message(accessTokenRequest,privateKey);
//		boolean requestDigiSign = idamUtil.signVerify(requestObjectPayload.getREQUEST(),requestObjectPayload.getDIGI_SIGN(),publicKey);
//		String requestPayload = idamUtil.decrypt_GCM(requestObjectPayload.getREQUEST(),requestAccessToken);
//		
//
//		FullIdamReconRequest payload = idamService.getallDetails();
//		String plainResponse = "{}";//new Gson().toJson(payload);
//
//		String responsePayload = idamUtil.encrypt_GCM(plainResponse,secretKey);
//		String responseDigiSign = idamUtil.signRequest(plainResponse,privateKey);
//		String responseAccessToken = idamUtil.encryptSHA256Message(secretKey,publicKey);
//		
//		idamResponse.setResponse(responsePayload);
//		idamResponse.setRequestReferenceNumber(requestObjectPayload.getREQUEST_REFERENCE_NUMBER());
//		idamResponse.setResponseDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
//		idamResponse.setDigiSign(responseDigiSign);
//		response.setHeader("AccessToken", responseAccessToken);
//		return idamResponse;
//	}
	
	
	@RequestMapping(value = "/allDetails", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public IdamResponse getDetailsFull(@RequestBody String idamRequest, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Step 1
		String secretKey = UUID.randomUUID().toString().replace("-", "");
		KeyPairObject keyPairObject = new KeyPairObject();
		PublicKey publicKey = keyPairObject.getPublicKey();
		PrivateKey privateKey = keyPairObject.getPrivateKey();
		
		IdamResponse idamResponse= new IdamResponse();
		IdamRequest requestObjectPayload = (IdamRequest) new Gson().fromJson(idamRequest, IdamRequest.class);
		String accessTokenRequest = request.getHeader("AccessToken");
		
//		String requestAccessToken = idamUtil.decryptSHA256Message(accessTokenRequest,privateKey);
//		boolean requestDigiSign = idamUtil.signVerify(requestObjectPayload.getREQUEST(),requestObjectPayload.getDIGI_SIGN(),publicKey);
//		String requestPayload = idamUtil.decrypt_GCM(requestObjectPayload.getREQUEST(),requestAccessToken);
//		

		FullIdamReconRequest payload = idamService.getallDetails();
		String plainResponse = "{}";//new Gson().toJson(payload);

		String responsePayload = idamUtil.encrypt_GCM(plainResponse,secretKey);
		String responseDigiSign = idamUtil.signRequest(plainResponse,privateKey);
		String responseAccessToken = idamUtil.encryptSHA256Message(secretKey,publicKey);
		
		idamResponse.setResponse(responsePayload);
		idamResponse.setRequestReferenceNumber(requestObjectPayload.getREQUEST_REFERENCE_NUMBER());
		idamResponse.setResponseDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
		idamResponse.setDigiSign(responseDigiSign);
		response.setHeader("AccessToken", responseAccessToken);
		return idamResponse;
	}

	@RequestMapping(value = "/getDetailsByLoginIdOrTimeStamp", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public FullIdamReconRequest getDetailsByLoginIdOrTimeStamp(@RequestBody String request)
			throws JsonMappingException, JsonProcessingException {
		IdamRequestLoginTimestamp requestMap = (IdamRequestLoginTimestamp) new Gson().fromJson(request,
				IdamRequestLoginTimestamp.class);
		return idamService.getDetailsByLoginIdOrTimeStamp(requestMap);
	}
	
}
