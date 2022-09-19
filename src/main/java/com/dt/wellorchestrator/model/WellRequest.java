package com.dt.wellorchestrator.model;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class WellRequest {

	@NotBlank
	@Schema(description = "The name of the well resource", required = true)
	private String name;

	@Schema(description = "Additional information for choke valve resource")
	private String wellInfo;

}
