package com.codewithAshu.blog.controllers;


import java.util.List;




import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codewithAshu.blog.config.AppConstants;
import com.codewithAshu.blog.payloads.ApiResponse;
import com.codewithAshu.blog.payloads.UserDto;
import com.codewithAshu.blog.payloads.UserResponse;
import com.codewithAshu.blog.services.UserService;

/**
 * 
 * @apiNote this class  created the multiple api for creating ,updating ,getting the
 * data through the User.
 * 
 * @author Ashwini Patil
 * @see create User
 * @see update User
 * @see delete User
 * @see getAll User
 * @see getByid User
 * @since 
 * @
 *
 */

@RestController
@RequestMapping("/api/users")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * @apiNote  This method send the request to UserserviceImpl for save the user details and getting the response.
	 * 
	 * @param UserDto it takes the parameter from UserDto
	 * @return UserDto return all details of User from UserDto class as a response
	 * @param <UserDto> ResponseEntity takes the UserDtos class as a parameter
	 * {@code  201 created}
	 */

	// POST - create user

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		

		logger.info("Initiating request for save user");

		UserDto createUser = this.userService.createUser(userDto);

		logger.info("Completed request for save user");

		// logger.warn("User not found");

		return new ResponseEntity<>(createUser, HttpStatus.CREATED);
	}

	/**
	 * This method Update the User details .
	 * 
	 * @see updateuser method using {@link @PutMapping}
	 * 
	 * @param UserDto it takes the parameter from userDto
	 * @param userid  @pathvaribale takes the parameter as Integer userid
	 * @return UserDto return all details of user from UserDto class
	 * @param <UserDto> ResponseEntity takes the UserDto class as a parameter in
	 *  return {@code  201 created}
	 */

	// PUT - update user

	@PutMapping("/{userid}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userid) {
		
		logger.info("send request for update user");

		UserDto updateUser = this.userService.updateUser(userDto, userid);

		logger.info("Completed request for update user");

		return ResponseEntity.ok(updateUser);
	}

	/**
	 * This method delele the user details by userid .
	 * 
	 * @param UserDto it takes the parameter from userDto
	 * @param uid     @pathvaribale takes the parameter as userid
	 * @return response as string ,User deleted Succesfully
	 * @param <Apiresponse> ResponseEntity takes the Apiresponse class as a parameter in return 
	 * {@code  200 created}
	 */

	// DELETE -delete user
     @PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userid}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userid) {
		
		logger.info("send request for delete user");

		this.userService.deleteUser(userid);

		logger.info("Completed request for delete user");

		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
	}

	/**
	 * This method get all users details .
	 * 
	 *
	 * @return UserDto return all details of user from userDto class
	 * @param <List<UserDto>> ResponseEntity takes the UserDto class as a parameter in return List 
	 * {@code  200 created}
	 */

	// GET Alluserget

	@GetMapping("/")
	public ResponseEntity<UserResponse>  getAllUser(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir)

	{
		 logger.info("Initiating request for getAll users");

		UserResponse allUser = this.userService.getAllUser(pageNumber,pageSize,sortBy,sortDir);

		 logger.info("Completed request for getAll users");

		return new ResponseEntity<UserResponse>(allUser,HttpStatus.OK);

	}

	/**
	 * This method get the singleuser details by userid .
	 * 
	 * @param UserDto it takes the parameter from userDto .
	 * @param uid  @pathvaribale takes the parameter as userid.
	 * @return ResponseEntity<UserDto> userdto single data of user.
	 *         
	 *  {@code  200 created}
	 *
	 */

	// GET - Singleuser get
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userid)

	{

		// logger.info("Completed request for getById user");

		return ResponseEntity.ok(this.userService.getUserById(userid));
	}

}
