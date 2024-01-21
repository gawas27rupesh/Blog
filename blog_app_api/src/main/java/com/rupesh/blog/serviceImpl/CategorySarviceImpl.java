package com.rupesh.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.rupesh.blog.dto.CategoryDto;
import com.rupesh.blog.entities.Category;
import com.rupesh.blog.exceptions.ResourceNotFoundException;
import com.rupesh.blog.repositories.CategoryRepo;
import com.rupesh.blog.services.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategorySarviceImpl implements CategoryService {
	
	private final CategoryRepo categoryRepo;
	private final ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		log.info("Service Implementation");
		Category addedCat = this.categoryRepo.save(this.modelMapper.map(categoryDto,Category.class));		
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	//@CachePut(value="Cat",key="#categoryId")
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		log.info("Service Implementation");
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category UpdateCat = this.categoryRepo.save(cat);	
		return this.modelMapper.map(UpdateCat, CategoryDto.class);
	}

	@Override
	//@CacheEvict(value="Cat",allEntries = true)
	public void deleteCategory(Integer categoryId) {
		log.info("Service Implementation");
		Category delCat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		this.categoryRepo.delete(delCat);
	}

	

	@Override
	@Cacheable(value="Cat")
	public List<CategoryDto> getCategories() {
		log.info("Service Implementation");
		List<Category> getAll = this.categoryRepo.findAll();
		List<CategoryDto> catDtos = getAll.stream().map(cat-> this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
		return catDtos;
	}

	@Override
	@Cacheable(value="Cat")
	public Category getCategory(Integer categoryId) {
		log.info("Service Implementation");
		Category getCat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		//return this.modelMapper.map(getCat, CategoryDto.class);
		return getCat;
	}
}
