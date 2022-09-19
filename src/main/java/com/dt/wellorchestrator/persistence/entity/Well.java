package com.dt.wellorchestrator.persistence.entity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.dt.wellorchestrator.model.ComponentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "well")
@Schema(description = "Well resource")
public class Well {

	@PartitionKey
	@Schema(description = "The virtual well unique identifier", example = "ccf9e52b-e2e4-45d8-8884-0721d3246a53")
	private UUID wellId;

	@Column
	@Schema(description = "The name of the virtual well resource", example = "Well #1")
	private String name;

	@Column
	@Schema(description = "Additional information for well resource", example = "Additional info")
	private String wellInfo;

	@Column
	@Schema(description = "Map with corresponding component type and its unique identifier")
	private Map<UUID, ComponentType> components;

	@Column
	@Schema(description = "Resource creation date and time")
	private LocalDateTime creationDateTime;

}
