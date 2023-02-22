package com.codewithAshu.blog.services.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithAshu.blog.config.AppConstants;
import com.codewithAshu.blog.entity.Category;
import com.codewithAshu.blog.exceptions.ResourceNotFoundException;
import com.codewithAshu.blog.payloads.CategoryDto;
import com.codewithAshu.blog.payloads.CategoryResponse;
import com.codewithAshu.blog.repositories.CategoryRepo;
import com.codewithAshu.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);


	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	// create category

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		logger.info("Initiating Dao call for save category");

		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedcat = this.categoryRepo.save(cat);
		
		logger.info("Completed Dao call for save category");


		return this.modelMapper.map(addedcat, CategoryDto.class);
	}

	// update category

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		logger.info("Initiating Dao call for update category");

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatecat = this.categoryRepo.save(cat);
		
		logger.info("Completed Dao call for update category");

		return this.modelMapper.map(updatecat, CategoryDto.class);
	}

	// delete category

	@Override
	public void deleteCategory(Integer categoryId) {
		
		logger.info("Initiating Dao call for delete category");

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		this.categoryRepo.delete(cat);
		
		logger.info("Completed Dao call for delete category");


	}

	// get single category

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		
		logger.info("Initiating Dao call for getById category");

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

		
		logger.info("Completed Dao call for getById category");

		return this.modelMapper.map(cat, CategoryDto.class);
		
		
		
	}

	// getall category

	@Override
	public CategoryResponse getCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		logger.info("Initiating Dao call for getall category");
		
		Sort sort= (sortDir.equalsIgnoreCase(AppConstants.SORT_DIR)?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
		
		PageRequest p = PageRequest.of(pageNumber, pageSize, sort);


 Page<Category> pagecat = this.categoryRepo.findAll(p);
	 
	 List<Category> allCategories = pagecat.getContent();

		List<CategoryDto> catDtos = allCategories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		
		CategoryResponse categoryResponse=new CategoryResponse();
		
		categoryResponse.setContent(catDtos);
		categoryResponse.setPageNumber(pagecat.getNumber());
		categoryResponse.setPageSize(pagecat.getSize());
		categoryResponse.setTotalElement(pagecat.getTotalElements());
		categoryResponse.setTotalPage(pagecat.getTotalPages());
		categoryResponse.setLastPage(pagecat.isLast());
	
		logger.info("Completed Dao call for getall category");

		
		
		return categoryResponse;
	}

}
