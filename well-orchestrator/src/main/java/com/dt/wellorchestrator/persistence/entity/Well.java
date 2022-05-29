package com.dt.wellorchestrator.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "well")
public class Well {

	@PartitionKey
	private UUID wellId;

	@Column
	private String name;

	@Column
	private String wellInfo;

	@Column
	private LocalDateTime creationDateTime;

}
