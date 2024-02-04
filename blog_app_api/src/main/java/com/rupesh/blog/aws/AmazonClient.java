package com.rupesh.blog.aws;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;

@Service
public class AmazonClient {

	public static final Logger log = LoggerFactory.getLogger(AmazonClient.class);

	private AmazonS3 s3client;

	@Value("${aws.client.endpointUrl}")
	private String endpointUrl;

	@Value("${aws.client.bucketName}")
	private String bucketName;

	@Value("${aws.client.accessKey}")
	private String accessKey;

	@Value("${aws.client.secretKey}")
	private String secretKey;

	@Value("${BlogFolderName}")
	private String blogFolderName;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	LocalDate localDate = LocalDate.now();

	@SuppressWarnings("deprecation")
	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		this.s3client = new AmazonS3Client(credentials);
	}

	public Map<String, Object> uploadFile(MultipartFile multipartFile) {

		Map<String, Object> map = new HashMap<>();
		String fileUrl = "";
		try {
			File file = convertMultiPartToFile(multipartFile);
			String fileName = generateFileName(multipartFile);
			String s3ObjectKey = blogFolderName + "/" + fileName;
			fileUrl = endpointUrl + "/" + blogFolderName + "/" + fileName;
			uploadFileTos3bucket(fileName, file);
			if (!file.delete()) {
				log.error("error while uploading images");
			}
			map.put("fileUrl", fileUrl);
			map.put("objectKey", s3ObjectKey);
			map.put("filename", fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}


	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		try(FileOutputStream fos = new FileOutputStream(convFile)) {
			fos.write(file.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		String st = null;
		try {
			st = multiPart.getOriginalFilename().replace(" ", "_");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return st;
	}
	
	
	private PutObjectResult uploadFileTos3bucket(String fileName, File file) {
		try {
			String s3ObjectKey = blogFolderName + "/" + fileName;
		    return s3client.putObject(new PutObjectRequest(bucketName, s3ObjectKey, file));
		}catch(Exception e) {
			log.error("error uploading request to S3 : ",e);
		}
		return null;
	}
	
	public Map<String, Object> getImagesFromS3(String objectKey){
		Map<String, Object> map = new HashMap<>();
		try{
			 String s3ObjectKey = blogFolderName + "/" +objectKey;
			 System.out.println("bucketname : "+bucketName+"\tFileName : "+s3ObjectKey);
			 S3Object s3Object = s3client.getObject(new GetObjectRequest(bucketName, s3ObjectKey));
			 byte[] objectContent = IOUtils.toByteArray(s3Object.getObjectContent());
			 map.put("Data", s3Object);
			 map.put("Data2", objectContent);
		}catch(Exception e) {
			log.error("error : ",e);
			map.put("Error ", e);
		}
		 return map;
	}
	
	public String deleteImageFromS3(String filename){
		try {
			String s3ObjectKey = blogFolderName + "/" +filename;
			System.out.println("bucketname : "+bucketName+"\tFileName : "+s3ObjectKey);
			s3client.deleteObject(bucketName, s3ObjectKey);
			return "success";
		}catch(Exception e) {
			log.error("error while deleting image from s3 : ",e);
		}
		return "error";
	}
	
	public List<String> listFileFromS3Bucket() {
		List<String> keyList = new ArrayList<>();
		try {
			ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withPrefix(blogFolderName)
					.withMaxKeys(2);
			ListObjectsV2Result result;
			do {
				result = s3client.listObjectsV2(req);

				for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
					keyList.add(objectSummary.getKey());
				}
				String token = result.getNextContinuationToken();
				req.setContinuationToken(token);
			} while (result.isTruncated());

			return keyList;
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} 
		return keyList;

	}

}
