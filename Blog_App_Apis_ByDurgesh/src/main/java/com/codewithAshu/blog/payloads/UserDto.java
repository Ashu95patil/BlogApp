package com.codewithAshu.blog.payloads;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
              
   # start-of-string
(?=.*[0-9])       # a digit must occur at least once
(?=.*[a-z])       # a lower case letter must occur at least once
(?=.*[A-Z])       # an upper case letter must occur at least once
(?=.*[@#$%^&+=])  # a special character must occur at least once
(?=\S+$)          # no whitespace allowed in the entire string
.{8,}             # anything, at least eight places though
$                 # end-of-string
*/

@NoArgsConstructor

@Setter
@Getter
public class UserDto {

	private Integer id;

	@NotEmpty
	@Size(min = 4, message = "Username must be min of 4 character")
	private String name;

	@Email(message = "Email address is not valid !!")
	private String email;

	@NotEmpty
	// @Size(min = 8, max = 15, message = "Password must be min of 8 char and max of
	// 15 chars..!!")
	@Pattern(regexp = "^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[#$@!%&?])[A-Za-z\\d#$@!%&?]{8,}$")
	                 
	private String password;

	@NotEmpty
	private String about;

}
