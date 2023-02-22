package com.codewithAshu.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codewithAshu.blog.entity.Category;
import com.codewithAshu.blog.entity.Post;
import com.codewithAshu.blog.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	// Create custom finder method

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);
	
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);
	
	
}
