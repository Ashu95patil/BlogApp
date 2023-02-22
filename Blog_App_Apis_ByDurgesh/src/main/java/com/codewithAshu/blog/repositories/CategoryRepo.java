package com.codewithAshu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithAshu.blog.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
