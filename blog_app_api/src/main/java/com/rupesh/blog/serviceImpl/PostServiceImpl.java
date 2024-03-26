package com.rupesh.blog.serviceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rupesh.blog.dto.PostDto;
import com.rupesh.blog.dto.PostResponseDto;
import com.rupesh.blog.entities.Category;
import com.rupesh.blog.entities.Post;
import com.rupesh.blog.entities.User;
import com.rupesh.blog.exceptions.ResourceNotFoundException;
import com.rupesh.blog.repositories.CategoryRepo;
import com.rupesh.blog.repositories.PostRepo;
import com.rupesh.blog.repositories.UserRepo;
import com.rupesh.blog.services.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepo postRepo;
	private final CategoryRepo categoryRepo;
	private final UserRepo userRepo;
	private final ModelMapper modelMapper;

	//private final AmazonClient s3Client;

	@Override
	@Cacheable("blogCache")
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId)
			throws IOException {
		log.info("Service Implementation");
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = this.postRepo.save(post);

		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	@CachePut("blogCache")
	public PostDto updatePost(PostDto postDto, Integer postId) {
		log.info("Service Implementation");
		Post map = modelMapper.map(postDto, Post.class);
		map.setPostId(postId);
		Post updatePost = this.postRepo.save(map);
		return this.modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	@CacheEvict("blogCache")
	public String deletePost(Integer postId) {
		log.info("Service Implementation");
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postid", postId));
		this.postRepo.delete(post);
		return "success";
	}

	@Override
	public PostResponseDto getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		log.info("Service Implementation");
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();
		
		List<PostDto> postDtos = allPosts.stream().map(post -> {
			PostDto postDto = this.modelMapper.map(post, PostDto.class);
			return postDto;
		}).collect(Collectors.toList());

		PostResponseDto postResponse = new PostResponseDto();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

//	@Override
//	@Cacheable("blogCache")
//	public List<PostDto> getAllPost() {
//		log.info("Service Implementation");
//		List<Post> pagePost = this.postRepo.findAll();
//		List<PostDto> postDtos = pagePost.stream().map(post -> this.modelMapper.map(post, PostDto.class))
//				.collect(Collectors.toList());
//		return postDtos;
//	}

	
	@Override
	@Cacheable("blogCache")
	public PostDto getPostById(Integer postId) {
		log.info("Service Implementation");
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postid", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	@Cacheable("blogCache")
	public PostResponseDto getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
		log.info("Service Implementation");
		Pageable p = PageRequest.of(pageNumber, pageSize);
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		Page<Post> pagePost = this.postRepo.findByCategory(category, p);
		
		List<PostDto> postDtos = pagePost.stream().map(dev->{
			PostDto map = modelMapper.map(dev, PostDto.class);
			return map;
		}).collect(Collectors.toList());
		
		PostResponseDto postResponse = new PostResponseDto();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	@Cacheable("blogCache")
	public PostResponseDto getPostsByUSer(Integer userId, Integer pageNumber, Integer pageSize) {
		log.info("Service Implementation");
		Pageable p = PageRequest.of(pageNumber, pageSize);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		Page<Post> pagePost = this.postRepo.findByUser(user, p);
		
		List<PostDto> postDtos = pagePost.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponseDto postResponse = new PostResponseDto();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

}
