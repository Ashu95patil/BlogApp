package com.codewithAshu.blog;

import java.util.List;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codewithAshu.blog.config.AppConstants;
import com.codewithAshu.blog.entity.Role;
import com.codewithAshu.blog.repositories.RoleRepo;


@SpringBootApplication
public class BlogAppApisByDurgeshApplication implements CommandLineRunner{

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisByDurgeshApplication.class, args);
	}
	
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	
// use for bcreypt password encoder show in console
	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("xyz"));
		
		try {
			
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role1 = new Role();
			role.setId(AppConstants.NORMAL_USER);
			role.setName("NORMAL_USER");
			
                 List<Role> roles = List.of(role,role1);
                 
                 List<Role> result = this.roleRepo.saveAll(roles);
                 
                 result.forEach(r -> {
                	 System.out.println(r.getName());
                 });
                 
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}


}
