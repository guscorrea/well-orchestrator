package com.dt.wellorchestrator.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ComponentRequest {

	@NotNull
	@Schema(description = "Component unique identifier", example = "ccf9e52b-e2e4-45d8-8884-0721d3246a53")
	private UUID componentId;

	@NotNull
	private ComponentType type;

}
