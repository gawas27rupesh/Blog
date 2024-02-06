package com.rupesh.blog.serviceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.multipart.MultipartFile;

import com.rupesh.blog.aws.AmazonClient;
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
import com.rupesh.blog.util.NewMultipartFile;

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

	private final AmazonClient s3Client;

	@Override
	@Cacheable("blogCache")
	public PostDto createPost(PostDto postDto, MultipartFile file, Integer userId, Integer categoryId)
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
		post.setImageName(file.getOriginalFilename());
		String fileKey = (new Date().getTime() + "_" + file.getOriginalFilename()).replaceAll(" ", "_");
		post.setObjectKey(fileKey);
		Post newPost = this.postRepo.save(post);
		MultipartFile newFile = new NewMultipartFile(file.getBytes(), fileKey, file.getContentType());

		s3Client.uploadFile(newFile);
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
	public void deletePost(Integer postId) {
		log.info("Service Implementation");
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postid", postId));
		this.postRepo.delete(post);
	}

	@Override
	@Cacheable("blogCache")
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		log.info("Service Implementation");
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();
		
		List<PostDto> postDtos = allPosts.stream().map(post -> {
			PostDto postDto = this.modelMapper.map(post, PostDto.class);
			Map<String, Object> imagesFromS3 = s3Client.getImagesFromS3(postDto.getObjectKey());
			postDto.setImage((byte[]) imagesFromS3.get("Data2"));
			return postDto;
		}).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
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
		Map<String, Object> imagesFromS3 = s3Client.getImagesFromS3(postDto.getObjectKey());
		postDto.setImage((byte[]) imagesFromS3.get("Data2"));
		return postDto;
	}

	@Override
	@Cacheable("blogCache")
	public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
		log.info("Service Implementation");
		Pageable p = PageRequest.of(pageNumber, pageSize);
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		Page<Post> pagePost = this.postRepo.findByCategory(category, p);
		
		List<PostDto> postDtos = pagePost.stream().map(dev->{
			PostDto map = modelMapper.map(dev, PostDto.class);
			Map<String, Object> imagesFromS3 = s3Client.getImagesFromS3(dev.getObjectKey());
			map.setImage((byte[]) imagesFromS3.get("Data2"));
			return map;
		}).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
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
	public PostResponse getPostsByUSer(Integer userId, Integer pageNumber, Integer pageSize) {
		log.info("Service Implementation");
		Pageable p = PageRequest.of(pageNumber, pageSize);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		Page<Post> pagePost = this.postRepo.findByUser(user, p);
		
		List<PostDto> postDtos = pagePost.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

}
