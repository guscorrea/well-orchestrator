package com.dt.wellorchestrator.client;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dt.wellorchestrator.constants.ComponentPathConstants;
import com.dt.wellorchestrator.model.ComponentRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ComponentClient {

	private final String CHOKE_VALVE_URL;

	private final String ANM_URL;

	private final String TUBING_URL;

	private final RestTemplate restTemplate;

	public ComponentClient(@Value("${choke.valve.url}") String CHOKE_VALVE_URL, @Value("${anm.url}") String ANM_URL,
			@Value("${tubing.url}") String TUBING_URL, RestTemplate restTemplate) {
		this.CHOKE_VALVE_URL = CHOKE_VALVE_URL;
		this.ANM_URL = ANM_URL;
		this.TUBING_URL = TUBING_URL;
		this.restTemplate = restTemplate;
	}

	public boolean getComponent(ComponentRequest componentRequest) {
		switch (componentRequest.getType()) {
		case anm:
			return getAnmComponent(componentRequest.getComponentId());
		case choke:
			return getChokeComponent(componentRequest.getComponentId());
		case tubing:
			return getTubingComponent(componentRequest.getComponentId());
		default:
			log.warn("Component {} not implemented.", componentRequest.getType());
			return false;
		}

	}

	private boolean getAnmComponent(UUID componentId) {
		log.info("Checking if ANM component with id {} exists.", componentId);
		String pathVariable = "/" + componentId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(ANM_URL + ComponentPathConstants.ANM_PATH).path(pathVariable).toUriString();
		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.GET, createHeaders(), Object.class);
		return checkResponse(response);
	}

	private boolean getChokeComponent(UUID componentId) {
		log.info("Checking if Choke Valve component with id {} exists.", componentId);
		String pathVariable = "/" + componentId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(CHOKE_VALVE_URL + ComponentPathConstants.CHOKE_VALVE_PATH).path(pathVariable).toUriString();
		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.GET, createHeaders(), Object.class);
		return checkResponse(response);
	}

	private boolean getTubingComponent(UUID componentId) {
		log.info("Checking if Tubing component with id {} exists.", componentId);
		String pathVariable = "/" + componentId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(TUBING_URL + ComponentPathConstants.TUBING_PATH).path(pathVariable).toUriString();
		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.GET, createHeaders(), Object.class);
		return checkResponse(response);
	}

	private boolean checkResponse(ResponseEntity<Object> response) {
		return response.hasBody() && HttpStatus.OK.equals(response.getStatusCode());
	}

	private HttpEntity<String> createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

}
