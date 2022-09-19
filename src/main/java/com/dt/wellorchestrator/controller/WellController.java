package com.dt.wellorchestrator.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Well")
public class WellController {

	private final WellService wellService;

	@Autowired
	public WellController(WellService wellService) {
		this.wellService = wellService;
	}

	@GetMapping("/v1/well")
	@Operation(summary = "Retrieves all wells", description = "Retrieves all well resources in a list.", responses = {
			@ApiResponse(responseCode = "200", description = "The list of wells was retrieved.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							array = @ArraySchema(schema = @Schema(implementation = Well.class))) }) })
	public ResponseEntity<List<Well>> listWells() {
		List<Well> well = wellService.getAllWells();
		return new ResponseEntity<>(well, HttpStatus.OK);
	}

	@GetMapping("/v1/well/{id}")
	@Operation(summary = "Retrieves a well resource.", description = "Retrieves a well resource with a given UUID", responses = {
			@ApiResponse(responseCode = "200", description = "The well was retrieved.",
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Well.class)) }),
			@ApiResponse(responseCode = "400", description = "The request failed validation.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "string", example = "Invalid UUID string")) }),
			@ApiResponse(responseCode = "404", description = "The well was not found in the DB.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "string",
							example = "Well with id c5f2f64c-b02d-4635-8a34-c3d4cc2d955b not found in the database.")) }) })
	public ResponseEntity<Well> getWell(@PathVariable("id") UUID id) {
		Well well = wellService.getWell(id);
		return new ResponseEntity<>(well, HttpStatus.OK);
	}

	@PostMapping("/v1/well")
	@Operation(summary = "Creates a well resource",
			description = "Sends a post request, validates input data, and saves the generated resource into the Scylla database.", responses = {
			@ApiResponse(responseCode = "200", description = "Well resource was created",
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Well.class)) }),
			@ApiResponse(responseCode = "400", description = "The request failed validation.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = "string", example = "Field name: must not be null")) }),
			@ApiResponse(responseCode = "500", description = "Unexpected error occurred",
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	public ResponseEntity<Well> createWell(@RequestBody @Valid WellRequest wellRequest) {
		Well well = wellService.saveWell(wellRequest);
		return new ResponseEntity<>(well, HttpStatus.OK);
	}

	@PutMapping("/v1/well/{id}")
	@Operation(summary = "Updates a well resource",
			description = "Sends a put request, validates input data, checks if the current resource exists, and saves the updated resource into the "
					+ "Scylla database.", responses = {
			@ApiResponse(responseCode = "200", description = "Well resource was updated",
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Well.class)) }),
			@ApiResponse(responseCode = "400", description = "The request failed validation.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = "string", example = "Field name: must not be null")) }),
			@ApiResponse(responseCode = "404", description = "The well was not found in the DB.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "string",
							example = "Well with id c5f2f64c-b02d-4635-8a34-c3d4cc2d955b not found in the database.")) }),
			@ApiResponse(responseCode = "500", description = "Unexpected error occurred",
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	public ResponseEntity<Well> updateWell(@PathVariable("id") UUID id, @RequestBody @Valid WellRequest wellRequest) {
		Well updatedWell = wellService.updateWell(id, wellRequest);
		return new ResponseEntity<>(updatedWell, HttpStatus.OK);
	}

	@DeleteMapping("/v1/well/{id}")
	@Operation(summary = "Deletes a well resource", description = "Deletes a well resource with given UUID.",
			responses = { @ApiResponse(responseCode = "204", description = "The well was deleted.") })
	public ResponseEntity<Void> deleteWell(@PathVariable("id") UUID id) {
		wellService.deleteWell(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
