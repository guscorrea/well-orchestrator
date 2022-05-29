package com.dt.wellorchestrator.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class WellRequest {

	@NotBlank
	String name;

	String wellInfo;

}
