package com.codewithAshu.blog.services.impl;

import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithAshu.blog.config.AppConstants;
import com.codewithAshu.blog.entity.Category;
import com.codewithAshu.blog.entity.Post;
import com.codewithAshu.blog.entity.User;
import com.codewithAshu.blog.exceptions.ResourceNotFoundException;
import com.codewithAshu.blog.payloads.PostDto;
import com.codewithAshu.blog.payloads.PostResponse;
import com.codewithAshu.blog.repositories.CategoryRepo;
import com.codewithAshu.blog.repositories.PostRepo;
import com.codewithAshu.blog.repositories.UserRepo;
import com.codewithAshu.blog.services.PostService;

import net.bytebuddy.asm.Advice.This;

@Service
public class PostServiceImpl implements PostService {
	
	Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);


	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	// create Post

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		logger.info("Initiating Dao call for save post");


		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);

		post.setImageName("default.png");
		post.setAddeDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);
		
		logger.info("Completed Dao call for save post");


		return this.modelMapper.map(newPost, PostDto.class);
	}

	// update post

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		logger.info("Initiating Dao call for update post");


		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST, AppConstants.POST_ID, postId));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());

		Post updatedpost = this.postRepo.save(post);
		
		logger.info("Completed Dao call for update post");

		return this.modelMapper.map(updatedpost, PostDto.class);
	}

	// delete post

	@Override
	public void deletePost(Integer postId) {
		
		logger.info("Initiating Dao call for delete post");


		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST, AppConstants.POST_ID, postId));

		this.postRepo.delete(post);
		
		logger.info("Completed Dao call for delete post");


	}

	// getall post

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		logger.info("Initiating Dao call for getall post");

		
		// pagination

		// using ternary operater sorting

		Sort sort = (sortDir.equalsIgnoreCase(AppConstants.SORT_DIR) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

		// sorting using if-else
		/*
		 * Sort sort=null;
		 * 
		 * if(sortDir.equalsIgnoreCase("asc")) { sort=Sort.by(sortBy).ascending(); }else
		 * { sort=Sort.by(sortBy).descending(); }
		 */

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = this.postRepo.findAll(p);

		List<Post> allPosts = pagePost.getContent();

		List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();

		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPage(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		logger.info("Completed Dao call for getall post");

		

		return postResponse;
	}

	// getsingle post

	@Override
	public PostDto getPostById(Integer postId) {

		logger.info("Initiating Dao call for getpostById");

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST, AppConstants.POST_ID, postId));

		logger.info("Completed Dao call for getpostById");

		return this.modelMapper.map(post, PostDto.class);
	}

	// getpost by category

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
		logger.info("Initiating Dao call for getpost by category");


		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

		List<Post> posts = this.postRepo.findByCategory(cat);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		logger.info("Completed Dao call for getpost by category");

		return postDtos;
	}

	// getpost by user

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
		logger.info("Initiating Dao call for getpost by user");


		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

		List<Post> posts = this.postRepo.findByUser(user);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		logger.info("Completed Dao call for getpost by user");

		return postDtos;
	}

	// search post

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		
		List<PostDto> postDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		
		return postDto;
	}

}
