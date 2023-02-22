package com.codewithAshu.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithAshu.blog.config.AppConstants;
import com.codewithAshu.blog.entity.Comment;
import com.codewithAshu.blog.entity.Post;
import com.codewithAshu.blog.exceptions.ResourceNotFoundException;
import com.codewithAshu.blog.payloads.CommentDto;
import com.codewithAshu.blog.repositories.CommentRepo;
import com.codewithAshu.blog.repositories.PostRepo;
import com.codewithAshu.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST, AppConstants.POST_ID, postId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);

		comment.setPost(post);

		Comment savecomment = this.commentRepo.save(comment);

		return this.modelMapper.map(savecomment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.COMMENT, AppConstants.COMMENT_ID, commentId));
		this.commentRepo.delete(com);

	}

}
