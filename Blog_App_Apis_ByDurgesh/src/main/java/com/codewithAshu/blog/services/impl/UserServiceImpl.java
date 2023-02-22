package com.codewithAshu.blog.services.impl;

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
import com.codewithAshu.blog.exceptions.*;
import com.codewithAshu.blog.config.AppConstants;
import com.codewithAshu.blog.entity.User;
import com.codewithAshu.blog.payloads.UserDto;
import com.codewithAshu.blog.payloads.UserResponse;
import com.codewithAshu.blog.repositories.UserRepo;
import com.codewithAshu.blog.services.UserService;



/**
 * @implNote this class we created the multiple method for creating ,updating ,getting the
 * data through the dao class.
 *
 * @author  Ashwini patil
 * @see create user
 * @see update user
 * @see delete user
 * @see getall user
 * @see getsingle user
 * @
 *
 */


@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	

	/**
	 *   Saves a User entity in repo 
	 
	 * @param UserDto it takes the parameter from UserDto
	 * @return UserDto return to controller class saved the user in database.
	 * @param <UserDto>  takes the saved User data as a parameter
	 * 
	 */

	// create user
	@Override
	public UserDto createUser(UserDto userDto) {

		logger.info("Initiating Dao call for save user");

		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);

		logger.info("Completed Dao call for save user");

		return this.userToDto(savedUser);
	}
	
	
	/**
	 *   find id in repo and update a User entity  and save in repo 
	 
	 * @param UserDto it takes the parameter from UserDto
	 * @param userId takes the parameter as Integer userId
	 * @return UserDto find id from repo and return the controller
	 * @return UserDto updated id saved the repo and return the controller
	 * @param <UserDto>  takes the updated User data as a parameter
	 * @throws ResourceNotFoundException when the id not found then throw the exception
	 * 
	 */

	// update user

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		logger.info("Initiating Dao call for update user");

		User user = this.userRepo.findById(userId)

				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());

		User updateUser = this.userRepo.save(user);
		UserDto userToDto = this.userToDto(updateUser);

		logger.info("Completed Dao call for update user");

		return userToDto;
	}

	
	// getsengle user
	
	/**
	 *   find id in repo and return the id to controller
	 
	 * @param userId takes the parameter as Integer userId
	 * @return UserDto find id from repo and return the controller
	 * @throws ResourceNotFoundException when the id not found then throw the exception
	 * 
	 */

	

	@Override
	public UserDto getUserById(Integer userId) {
		
		logger.info("Initiating Dao call for getById user");


		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

		logger.info("Completed Dao call for getById user");

		return this.userToDto(user);
	}

	// getall user
	
	/**
	 *   return all Users entity to controller 
	 
	 * @param userId takes the parameter as Integer userId
	 * @return UserDto return to controller class saved the user in database.
	 * @param <UserDto>  takes the saved User data as a parameter
	 * 
	 */


	@Override
	public UserResponse getAllUser(Integer PageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		

		logger.info("Initiating Dao call for getall user");
		
		Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

		
		 Pageable page = PageRequest.of(PageNumber, pageSize,sort);

		 Page<User> pageuser = this.userRepo.findAll(page);
		 
		 List<User> alluser = pageuser.getContent();
		 
		List<UserDto> userDtos = alluser.stream().map((user) -> this.modelMapper.map(user,UserDto.class)).collect(Collectors.toList());

		UserResponse userResponse = new UserResponse();
		
		userResponse.setContent(userDtos);
		userResponse.setPageNumber(pageuser.getNumber());
		userResponse.setPageSize(pageuser.getSize());
		userResponse.setTotalElement(pageuser.getTotalElements());
		userResponse.setTotalPage(pageuser.getTotalPages());
		userResponse.setLastPage(pageuser.isLast());
		
		logger.info("Completed Dao call for getall user");

		return userResponse;
	}

	// delete user

	@Override
	public void deleteUser(Integer userId) {

		logger.info("Initiating Dao call for delete user");

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

		this.userRepo.delete(user);

		logger.info("Completed Dao call for delete user");

	}

	// convert the data user to userDto
	public User dtoToUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());

		return user;
	}

	// convert the data UserDto to user

	public UserDto userToDto(User user) {

		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());

		return userDto;
	}

}
