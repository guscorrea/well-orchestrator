package com.dt.wellorchestrator.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dt.wellorchestrator.model.WellRequest;
import com.dt.wellorchestrator.persistence.entity.Well;
import com.dt.wellorchestrator.service.WellService;

@RestController
public class WellController {

	private final WellService wellService;

	@Autowired
	public WellController(WellService wellService) {
		this.wellService = wellService;
	}

	@PostMapping("/well")
	public ResponseEntity<Well> createWell(@RequestBody @Valid WellRequest wellRequest) {
		Well well = wellService.saveWell(wellRequest);
		return new ResponseEntity<>(well, HttpStatus.OK);
	}

	@GetMapping("/well")
	public ResponseEntity<List<Well>> listWell() {
		List<Well> well = wellService.getAllWells();
		return new ResponseEntity<>(well, HttpStatus.OK);
	}

}
