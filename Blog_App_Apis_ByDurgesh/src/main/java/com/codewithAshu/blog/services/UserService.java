package com.codewithAshu.blog.services;

import java.util.List;

import com.codewithAshu.blog.payloads.UserDto;
import com.codewithAshu.blog.payloads.UserResponse;

public interface UserService {

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

      UserResponse getAllUser(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

	void deleteUser(Integer userId);

}
