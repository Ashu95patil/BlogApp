package com.codewithAshu.blog.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codewithAshu.blog.entity.Category;
import com.codewithAshu.blog.exceptions.ResourceNotFoundException;
import com.codewithAshu.blog.payloads.CategoryDto;
import com.codewithAshu.blog.services.CategoryService;

@SpringBootTest(classes = CategoryControllerTest.class)
class CategoryControllerTest {

	@Mock
	private CategoryService categoryService;

	@InjectMocks
	private CategoryController controller;

	@Test
	void getCategorytest() throws ResourceNotFoundException {

		CategoryDto category = (new CategoryDto(1, "Program", "Java Program"));

		int catId = 3;
//	     
		when(categoryService.getCategory(catId)).thenReturn(category);
//	     
		ResponseEntity<CategoryDto> cat = controller.getCategory(catId);

		assertEquals(HttpStatus.OK, cat.getStatusCode());
//	     assertEquals(1, cat.getBody().getCategoryId());

//		ResourceNotFoundException assertThrows = assertThrows(ResourceNotFoundException.class,
//				() -> controller.getCategories());

//		assertEquals("Data Not found", assertThrows.getMessage());

	}

}
