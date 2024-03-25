package com.rupesh.blog.evalMapper;

import java.util.Optional;
import java.util.function.Function;


import com.rupesh.blog.dto.CategoryDto;
import com.rupesh.blog.entities.Category;
import static com.rupesh.blog.util.FunctionUtil.evalMapper;

public class CategoryMapper {

	private CategoryMapper() {
	}

	public static final Function<Category, Optional<CategoryDto>> TO_CATEGORY = e -> evalMapper(e,
			CategoryDto.class);
	
	public static final Function<CategoryDto, Optional<Category>> CATEGORY = e -> evalMapper(e,
			Category.class);
}
