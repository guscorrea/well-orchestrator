package com.dt.wellorchestrator.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dt.wellorchestrator.exception.ComponentNotFoundException;
import com.dt.wellorchestrator.exception.WellNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(WellNotFoundException.class)
	public ResponseEntity<List<String>> handleWellNotFoundException(WellNotFoundException e) {
		return new ResponseEntity<>(toListMessages(e.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ComponentNotFoundException.class)
	public ResponseEntity<List<String>> handleComponentNotFoundException(ComponentNotFoundException e) {
		return new ResponseEntity<>(toListMessages(e.getMessage()), HttpStatus.NOT_FOUND);
	}

	private List<String> toListMessages(String message) {
		return Collections.singletonList(message);
	}

}
