package com.dt.wellorchestrator.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dt.wellorchestrator.model.ComponentRequest;
import com.dt.wellorchestrator.persistence.entity.Well;
import com.dt.wellorchestrator.service.ComponentService;

@RestController
public class ComponentController {

	private final ComponentService componentService;

	@Autowired
	public ComponentController(ComponentService componentService) {
		this.componentService = componentService;
	}

	@PostMapping("/add-component/{wellId}")
	public ResponseEntity<Well> addComponent(@PathVariable("wellId") UUID wellId, @RequestBody @Valid ComponentRequest componentRequest) {
		Well updatedWell = componentService.addComponent(wellId, componentRequest);
		return new ResponseEntity<>(updatedWell, HttpStatus.OK);
	}

	@DeleteMapping("/remove-component/well/{wellId}/component/{componentId}")
	public ResponseEntity<Void> removeComponent(@PathVariable("wellId") UUID wellId, @PathVariable("componentId") UUID componentId) {
		componentService.removeComponent(wellId, componentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
