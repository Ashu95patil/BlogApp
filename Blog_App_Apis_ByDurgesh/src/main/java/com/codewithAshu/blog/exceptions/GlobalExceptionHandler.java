package com.codewithAshu.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codewithAshu.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();

		ApiResponse apiResponse = new ApiResponse(message, false);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException m) {
		Map<String, String> resp = new HashMap<>();

		m.getBindingResult().getAllErrors().forEach((error) -> {
			String field = ((FieldError) error).getField();
			String defaultMessage = error.getDefaultMessage();
			resp.put(field, defaultMessage);

		});
		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);

	}

}
