package com.codewithAshu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithAshu.blog.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
