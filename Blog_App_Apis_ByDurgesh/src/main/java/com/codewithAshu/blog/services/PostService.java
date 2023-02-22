package com.codewithAshu.blog.services;

import java.util.List;

import com.codewithAshu.blog.entity.Post;
import com.codewithAshu.blog.payloads.PostDto;
import com.codewithAshu.blog.payloads.PostResponse;

public interface PostService {

	// create

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// update

	PostDto updatePost(PostDto postDto, Integer postId);

	// delete

	void deletePost(Integer postId);

	// get all post

	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

	// get single post

	PostDto getPostById(Integer postId);

	// get all post By category

	List<PostDto> getPostByCategory(Integer categoryId);

	// get all post By User

	List<PostDto> getPostByUser(Integer userId);

	// search post

	List<PostDto> searchPosts(String keyword);

}
