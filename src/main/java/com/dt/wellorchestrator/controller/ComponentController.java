package com.dt.wellorchestrator.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dt.wellorchestrator.model.ComponentRequest;
import com.dt.wellorchestrator.persistence.entity.Well;
import com.dt.wellorchestrator.service.ComponentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Component")
public class ComponentController {

	private final ComponentService componentService;

	@Autowired
	public ComponentController(ComponentService componentService) {
		this.componentService = componentService;
	}

	@PostMapping("/v1/add-component/{wellId}")
	@Operation(summary = "Associates a component with a well",
			description = "Associates a component to an existing well resource. Both component and well resources must exist, and "
					+ "are validated.", responses = {
			@ApiResponse(responseCode = "200", description = "Association was created",
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Well.class)) }),
			@ApiResponse(responseCode = "400", description = "The request failed validation.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = "string", example = "Field componentId: must not be null")) }),
			@ApiResponse(responseCode = "404", description = "The well or component was not found in the DB.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "string",
							example = "Well with id c5f2f64c-b02d-4635-8a34-c3d4cc2d955b not found in the database.")) }),
			@ApiResponse(responseCode = "500", description = "Unexpected error occurred",
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	public ResponseEntity<Well> addComponent(@PathVariable("wellId") UUID wellId, @RequestBody @Valid ComponentRequest componentRequest) {
		Well updatedWell = componentService.addComponent(wellId, componentRequest);
		return new ResponseEntity<>(updatedWell, HttpStatus.OK);
	}

	@DeleteMapping("/v1/remove-component/well/{wellId}/component/{componentId}")
	@Operation(summary = "Deletes an association between component and well",
			description = "Removes the link between well and a component with " + "given UUID. The well must exist for the operation to succeed.",
			responses = { @ApiResponse(responseCode = "204", description = "The Tubing was deleted."),
					@ApiResponse(responseCode = "404", description = "The well or component was not found in the DB.", content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "string",
									example = "Well with id c5f2f64c-b02d-4635-8a34-c3d4cc2d955b not found in the database.")) }) })
	public ResponseEntity<Void> removeComponent(@PathVariable("wellId") UUID wellId, @PathVariable("componentId") UUID componentId) {
		componentService.removeComponent(wellId, componentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
