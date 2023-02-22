package com.codewithAshu.blog.services;

import com.codewithAshu.blog.payloads.CommentDto;

public interface CommentService {
	
	
	CommentDto createComment(CommentDto commentDto,Integer postId);
	
	void deleteComment(Integer commentId);

}
