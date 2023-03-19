package com.codewithAshu.blog.controllers;

import java.util.List;




import javax.validation.Valid;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codewithAshu.blog.config.AppConstants;
import com.codewithAshu.blog.payloads.ApiResponse;
import com.codewithAshu.blog.payloads.CategoryDto;
import com.codewithAshu.blog.payloads.CategoryResponse;
import com.codewithAshu.blog.services.CategoryService;


/**
 * 
 * In this class we created the multiple Api for creating ,updating ,getting the data through the category.
 * @author Ashwini Patil
 * @see createCategory
 * @see updateCategory
 * @see deleteCategory
 * @see getAllCategory
 * @see getByid
 * @since 
 * 
 *
 */

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	Logger logger = LoggerFactory.getLogger(CategoryController.class);


	@Autowired
	private CategoryService categoryService;
	
	
	/**
	 * This method save the category details .
	 * 
	 * @param CategoryDto  it takes the parameter from CategoryDto 
	 * @return CategoryDto return all details of category from CategoryDto class as a response
	 * @param <UserDto> ResponseEntity takes the CategoryDtos class as a parameter
	 * {@code  201 created}
	 */

	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		
		logger.info("Initiating request for save category");

		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		
		logger.info("Completed request for save category");

		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

	}

	
	/**
	 * This method Update the Category details .
	 * @see updatecategory method using  {@link @PutMapping}
	 * 
	 * @param CategoryDto  it takes the parameter from userDto 
	 * @param uid @pathvaribale takes the parameter as Integer categoryrid
	 * @return CategoryDto return all details of category from CategoryDto class
	 * @param <CategoryDto> ResponseEntity takes the CategoryDto class as a parameter in return
	 * {@code  201 created}
	 */
	// update

	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer catId) {
		
		logger.info("send request for update category");
		
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, catId);
		
		logger.info("Completed request for update category");

		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.CREATED);

	}
	
	/**
	 * This method delele the category details by categoryid .
	 * 
	 * 
	 * @param catId @pathvaribale takes the parameter as catId
	 * @return  response as string ,Category deleted Succesfully
	 * @param <Apiresponse> ResponseEntity takes the Apiresponse class as a parameter in return
	 * {@code  200 ok}
	 */

	// delete

	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
		
		logger.info("send request for delete category");

		this.categoryService.deleteCategory(catId);
		
		logger.info("completed request for delete category");

		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted Successfully..!!", true), HttpStatus.OK);

	}
	
	/**
	 * This method get the singleuser details by categoryid .
	 * 
	 * @param CategoryDto  it takes the parameter from CategoryDto .
	 * @param catId @pathvaribale takes the parameter as userid.
	 * @return ResponseEntity<CategoryDto> categoryDto single data of category. 
	 * 
	 * {@code  200 ok}
	 *
	 */

	// get

	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
		
		logger.info("send request for getById category");

		CategoryDto categoryDto = this.categoryService.getCategory(catId);
		
		logger.info("completed request for getById category");

		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);

	}
	
	/**
	 * This method get all  users details .
	 * 
	 *
	 * @return CategoryDto return all details of category from CategoryDto class
	 * @param <List<CategoryDto>> ResponseEntity takes the CategoryDto class as a parameter in return List
	 * {@code  200 ok}
	 */

	// getAll 

	@GetMapping("/")
	public ResponseEntity<CategoryResponse> getCategories(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir) {
		
		logger.info("send request for getall category");

  CategoryResponse allcategories = this.categoryService.getCategories(pageNumber,pageSize,sortBy,sortDir);
		
		logger.info("completed request for getall category");

		return new  ResponseEntity<CategoryResponse>(allcategories,HttpStatus.OK);

	}

}
