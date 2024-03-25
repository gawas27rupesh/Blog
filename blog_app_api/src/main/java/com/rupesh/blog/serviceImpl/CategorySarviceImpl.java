package com.rupesh.blog.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.rupesh.blog.dto.CategoryDto;
import com.rupesh.blog.entities.Category;
import com.rupesh.blog.exceptions.ResourceNotFoundException;
import com.rupesh.blog.repositories.CategoryRepo;
import com.rupesh.blog.services.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.rupesh.blog.evalMapper.CategoryMapper.TO_CATEGORY;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategorySarviceImpl implements CategoryService {
	
	private final CategoryRepo categoryRepo;
	private final ModelMapper modelMapper;

	@Override
	public Optional<CategoryDto> createCategory(CategoryDto categoryDto) {
		log.info("Service Implementation");
		Category addedCat =categoryRepo.save(modelMapper.map(categoryDto,Category.class));		
		return TO_CATEGORY.apply(addedCat);
	}

	@Override
	public Optional<CategoryDto> updateCategory(CategoryDto categoryDto,Integer categoryId) {
		log.info("Service Implementation");
		Category map = modelMapper.map(categoryDto, Category.class);
		map.setCategoryId(categoryId);
		Category updateCat = categoryRepo.save(map);
		return TO_CATEGORY.apply(updateCat);
	}

	@Override
	public String deleteCategory(Integer categoryId) {
		log.info("Service Implementation");
		Category delCat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		categoryRepo.delete(delCat);
		return "success";
	}

	

	@Override
	public List<Optional<CategoryDto>> getCategories() {
		log.info("Service Implementation");
		List<Category> getAll = this.categoryRepo.findAll();
		//List<Optional<CategoryDto>> catDtos = getAll.stream().map(cat -> TO_CATEGORY.apply(cat)).collect(Collectors.toList());
		List<Optional<CategoryDto>> catDtos = getAll.stream().map(TO_CATEGORY::apply).collect(Collectors.toList());
		return catDtos;
	}

	@Override
	public Optional<CategoryDto> getCategory(Integer categoryId) {
		log.info("Service Implementation");
		Category getCat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		return TO_CATEGORY.apply(getCat);
		//return this.modelMapper.map(getCat, CategoryDto.class);
	}
}
