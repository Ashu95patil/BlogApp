package com.codewithAshu.blog.services;

import java.util.List;

import com.codewithAshu.blog.payloads.CategoryDto;
import com.codewithAshu.blog.payloads.CategoryResponse;

public interface CategoryService {

	// create

	CategoryDto createCategory(CategoryDto categoryDto);

	// update

	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	// delete

	void deleteCategory(Integer categoryId);

	// get

	CategoryDto getCategory(Integer categoryId);

	// getAll

   CategoryResponse getCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

}
