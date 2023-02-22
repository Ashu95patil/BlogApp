package com.codewithAshu.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.codewithAshu.blog.repositories.UserRepo;

@SpringBootTest
class BlogAppApisByDurgeshApplicationTests {

	
	
	@Autowired
	private UserRepo userRepo;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void repoTest()
	{
		String name = this.userRepo.getClass().getName();
		System.out.println(name);
		String packageName = this.userRepo.getClass().getPackageName();
		System.out.println(packageName);
	}

}
