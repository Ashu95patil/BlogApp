package com.codewithAshu.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithAshu.blog.config.AppConstants;
import com.codewithAshu.blog.entity.User;
import com.codewithAshu.blog.exceptions.ResourceNotFoundException;
import com.codewithAshu.blog.repositories.UserRepo;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	
	//loading user form database by username
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException(AppConstants.USER, "email : "+username, 0));
		
		
		return user;
	}

}
