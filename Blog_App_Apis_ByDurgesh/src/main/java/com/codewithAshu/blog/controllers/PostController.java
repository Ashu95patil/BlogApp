package com.codewithAshu.blog.controllers;

import java.io.IOException;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.codewithAshu.blog.config.AppConstants;
import com.codewithAshu.blog.payloads.ApiResponse;
import com.codewithAshu.blog.payloads.PostDto;
import com.codewithAshu.blog.payloads.PostResponse;
import com.codewithAshu.blog.services.FileService;
import com.codewithAshu.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	Logger logger = LoggerFactory.getLogger(PostController.class);


	@Autowired
	private PostService postService;
	
	
	@Autowired
	private FileService fileService;
	
	
	@Value("$(project.image)")
	private String path;

	// create

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		
		logger.info("Initiating request for save post");

		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		
		logger.info("Completed request for save post");


		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	// update post

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		
		logger.info("Initiating request for update post");

		
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		logger.info("Completed request for update post");


		return new ResponseEntity<PostDto>(updatePost, HttpStatus.CREATED);
	}

	// delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		
		logger.info("Initiating request for delete post");

		this.postService.deletePost(postId);
		
		logger.info("Completed request for delete post");

		return new ApiResponse("Post is successfully deleted..!!", true);
	}

	// getAll post

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir) {
		
		logger.info("Initiating request for geall post");

		
		
		PostResponse allPost = this.postService.getAllPost(pageNumber, pageSize,sortBy,sortDir);
		
		logger.info("Completed request for getall post");


		return new ResponseEntity<PostResponse>(allPost, HttpStatus.OK);
	}

	// getsingle post

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		
		logger.info("Initiating request for getById post");

		PostDto postById = this.postService.getPostById(postId);
		
		logger.info("Completed request for getById post");

		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
	}

	// get by category

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		
		logger.info("Initiating request for getpost by categoryId");

		List<PostDto> postByCategory = this.postService.getPostByCategory(categoryId);

		logger.info("Completed request for getpost by categoryId");

		return new ResponseEntity<List<PostDto>>(postByCategory, HttpStatus.OK);
	}

	// get by user

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
		
		logger.info("Initiating request for getpost by userId");

		List<PostDto> postByUser = this.postService.getPostByUser(userId);
		
		logger.info("Completed request for getpost by userId");


		return new ResponseEntity<List<PostDto>>(postByUser, HttpStatus.OK);
	}
	
	//search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable ("keywords") String keywords)
	{
		List<PostDto> searchPosts = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.OK);
	}
	
	
	//post image upload
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException
	{
	     PostDto postDto = this.postService.getPostById(postId);	

		String filename = this.fileService.uploadImage(path, image);
	     postDto.setImageName(filename);
	     PostDto updatePost = this.postService.updatePost(postDto, postId);
	
	 return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	
	}
	
	//method to serve file
	@GetMapping(value = "/post/image/{imagename}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imagename") String imagename,HttpServletResponse response) throws IOException
	{
		InputStream resource = this.fileService.getResource(path, imagename);
		
       response.setContentType(MediaType.IMAGE_JPEG_VALUE);
       StreamUtils.copy(resource,response.getOutputStream());
	}
	
	
	

}
