package com.dt.wellorchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Well Orchestrator",
		description = "The well-orchestrator microservice is mostly responsible for managing a Well resource and its relationships with other"
				+ " components. A physical representation of a Well resource would be equivalent to the entire Oil Well itself, "
				+ "containing general data about the plant and the discrete DTs associated with it.",
		version = "1.0.0"), servers = { @Server(url = "http://localhost:8082", description = "Local Docker deployment URL") })
public class WellOrchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(WellOrchestratorApplication.class);
	}

}
