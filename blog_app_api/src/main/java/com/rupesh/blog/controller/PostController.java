package com.rupesh.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rupesh.blog.config.AppConstants;
import com.rupesh.blog.payloads.ApiResponse;
import com.rupesh.blog.payloads.PostDto;
import com.rupesh.blog.payloads.PostResponse;
import com.rupesh.blog.services.FileService;
import com.rupesh.blog.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")//project.image from application.properties
	private String path;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
											  @PathVariable Integer userId,
											  @PathVariable Integer categoryId){
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);	
	}
	
	//get by user
//	@GetMapping("/user/{userId}/posts")
//	public ResponseEntity<PostResponse> getPostByUser(
//			@PathVariable Integer userId,
//			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
//			@RequestParam(value = "pageSize",defaultValue = "3",required = false) Integer pageize
//			) {
//		PostResponse posts = this.postService.getPostsByUSer(userId,pageNumber,pageize);
//		return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);	
//	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostByUser(
			@PathVariable Integer userId,
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "3",required = false) Integer pageize
			) {
		PostResponse posts = this.postService.getPostsByUSer(userId,pageNumber,pageize);
		return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);	
	}
	
	//get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(
			@PathVariable Integer categoryId,
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "4",required = false) Integer pageize
			) {
		PostResponse posts = this.postService.getPostsByCategory(categoryId,pageNumber,pageize);
		return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);	
	}
	
	//get all posts
	@GetMapping("/")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber",defaultValue =AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false) Integer pageize,
			@RequestParam(value = "sortBy",defaultValue =AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue =AppConstants.SORT_DIR,required = false) String sortDir
			) {
		PostResponse allPost = this.postService.getAllPost(pageNumber,pageize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);		
	}
	
//	@GetMapping("/")
//	public ResponseEntity<List<PostDto>> getAllPost() {
//		List<PostDto> allPost = this.postService.getAllPost();
//		return new ResponseEntity<List<PostDto>>(allPost,HttpStatus.OK);		
//	}
	
	//get post details by id
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);		
	}
	
	//delete post
	@DeleteMapping("/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post is successfully deleted !!",true);	
	}
	
	//update post
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePodt(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//Search
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDto> result = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);	
	}
	
	//post image upload
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image, @PathVariable Integer postId) throws IOException {
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//method to serve files
	@GetMapping(value = "/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName")String imageName,HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
