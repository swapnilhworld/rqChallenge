package com.example.rqchallenge.exceptions;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.rqchallenge.constants.EmployeeConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ControllerAdvice
public class EmployeeExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(EmployeeExceptionHandler.class);
	
	@ExceptionHandler(value = { EmployeeInputException.class })
	protected ResponseEntity<Object> EmployeeInputExceptionHandler(RuntimeException ex, WebRequest request) {
		log.error(EmployeeConstants.ERROR_INVALID_INPUT);
		return handleExceptionInternal(ex, EmployeeConstants.ERROR_INVALID_INPUT, new HttpHeaders(),
				HttpStatus.EXPECTATION_FAILED, request);
	}
	
}
