package com.dt.wellorchestrator.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping("/well")
	public ResponseEntity<List<Well>> listWell() {
		List<Well> well = wellService.getAllWells();
		return new ResponseEntity<>(well, HttpStatus.OK);
	}

	@GetMapping("/well/{id}")
	public ResponseEntity<Well> getWell(@PathVariable("id") UUID id) {
		Well well = wellService.getWell(id);
		return new ResponseEntity<>(well, HttpStatus.OK);
	}

	@PostMapping("/well")
	public ResponseEntity<Well> createWell(@RequestBody @Valid WellRequest wellRequest) {
		Well well = wellService.saveWell(wellRequest);
		return new ResponseEntity<>(well, HttpStatus.OK);
	}

	@PutMapping("/well/{id}")
	public ResponseEntity<Well> updateWell(@PathVariable("id") UUID id, @RequestBody @Valid WellRequest wellRequest) {
		Well updatedWell = wellService.updateWell(id, wellRequest);
		return new ResponseEntity<>(updatedWell, HttpStatus.OK);
	}

	@DeleteMapping("/well/{id}")
	public ResponseEntity<Void> deleteWell(@PathVariable("id") UUID id) {
		wellService.deleteWell(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
