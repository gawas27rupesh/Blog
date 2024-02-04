package com.rupesh.blog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rupesh.blog.aws.AmazonClient;

import lombok.RequiredArgsConstructor;

@Lazy
@RestController
@RequestMapping("/aws")
@RequiredArgsConstructor
public class AwsRestController {
	
	private final AmazonClient amazonClient;

	@PostMapping("/saveImage")
	public ResponseEntity<Map<String, Object>> uploadImageToS3(@RequestParam MultipartFile file){
		return new ResponseEntity<>(amazonClient.uploadFile(file), HttpStatus.OK);
	}
	@GetMapping("/getImageFromS3")
	public ResponseEntity<Map<String, Object>> getImageFromS3(@RequestParam String objectKey){
		return new ResponseEntity<>(amazonClient.getImagesFromS3(objectKey), HttpStatus.OK);
	}
	@DeleteMapping("/deleteImageFromS3")
	public ResponseEntity<String> deleteImageFromS3(@RequestParam String objectKey){
		return new ResponseEntity<>(amazonClient.deleteImageFromS3(objectKey), HttpStatus.OK);
	}
	@GetMapping("/getAllImageFromS3")
	public ResponseEntity<List<String>> getAllImageFromS3(){
		return new ResponseEntity<>(amazonClient.listFileFromS3Bucket(), HttpStatus.OK);
	}
	
}
