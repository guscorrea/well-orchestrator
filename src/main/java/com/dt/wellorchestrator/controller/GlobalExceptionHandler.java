package com.dt.wellorchestrator.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dt.wellorchestrator.exception.WellNotFoundException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(WellNotFoundException.class)
	public ResponseEntity<List<String>> handleDocumentDuplicationException(WellNotFoundException e) {
		log.error(e.getMessage());
		return new ResponseEntity<>(toListMessages(e.getMessage()), HttpStatus.NOT_FOUND);
	}

	private List<String> toListMessages(String message) {
		return Collections.singletonList(message);
	}

}
