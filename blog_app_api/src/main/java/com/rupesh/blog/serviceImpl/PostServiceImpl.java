package com.rupesh.blog.serviceImpl;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rupesh.blog.dto.PostDto;
import com.rupesh.blog.dto.PostResponse;
import com.rupesh.blog.entities.Category;
import com.rupesh.blog.entities.Post;
import com.rupesh.blog.entities.User;
import com.rupesh.blog.exceptions.ResourceNotFoundException;
import com.rupesh.blog.repositories.CategoryRepo;
import com.rupesh.blog.repositories.PostRepo;
import com.rupesh.blog.repositories.UserRepo;
import com.rupesh.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User id", userId));
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost=this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postid", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatePost = this.postRepo.save(post);
		return this.modelMapper.map(updatePost,PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postid", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy,String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);//imp:- Sort.by(sortBy).descending();// bydefault ascending
		Page<Post> pagePost =this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();
//		List<Post> AllPost = this.postRepo.findAll();
//		System.out.println(AllPost);
		List<PostDto> postDtos = allPosts.stream().map((post)->
			this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());	
		return postResponse;
	}
	
	@Override
	public List<PostDto> getAllPost() {
		List<Post> pagePost =this.postRepo.findAll();
		List<PostDto> postDtos = pagePost.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public PostDto getPostById(Integer postId) {
	 Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postid", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize) {
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		Page<Post> pagePost = this.postRepo.findByCategory(category,p);
		List<PostDto> postDtos = pagePost.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());	
		return postResponse;
	}

	@Override
	public PostResponse getPostsByUSer(Integer userId,Integer pageNumber,Integer pageSize) {
		Pageable p=PageRequest.of(pageNumber, pageSize);
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		Page<Post> pagePost = this.postRepo.findByUser(user,p);
		List<PostDto> postDtos =pagePost.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());	
		return postResponse;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
	List<Post> posts = this.postRepo.findByTitleContaining(keyword);
	List<PostDto> postDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

}
