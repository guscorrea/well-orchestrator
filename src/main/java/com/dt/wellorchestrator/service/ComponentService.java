package com.dt.wellorchestrator.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.wellorchestrator.model.ComponentRequest;
import com.dt.wellorchestrator.model.ComponentType;
import com.dt.wellorchestrator.persistence.WellRepository;
import com.dt.wellorchestrator.persistence.entity.Well;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ComponentService {

	private final WellService wellService;

	private final WellRepository wellRepository;

	@Autowired
	public ComponentService(WellService wellService, WellRepository wellRepository) {
		this.wellService = wellService;
		this.wellRepository = wellRepository;
	}

	public Well addComponent(UUID wellId, ComponentRequest componentRequest) {
		Well well = wellService.getWell(wellId);
		//TODO validate if the component exists componentRequest.getComponentId()
		log.info("Adding component {} to well with id: {}", componentRequest.getComponentId(), wellId);
		if (wellHasNoComponents(well)) {
			Map<UUID, ComponentType> newComponent = new HashMap<>();
			newComponent.put(componentRequest.getComponentId(), componentRequest.getType());
			well.setComponents(newComponent);
			wellRepository.save(well);
			return well;
		}

		well.getComponents().put(componentRequest.getComponentId(), componentRequest.getType());
		wellRepository.save(well);
		return well;
	}

	public void removeComponent(UUID wellId, UUID componentId) {
		Well well = wellService.getWell(wellId);
		if (wellHasNoComponents(well)) {
			return;
		}
		log.info("Removing component {} from well with id: {}", wellId);
		well.getComponents().remove(componentId);
		wellRepository.save(well);
	}

	private boolean wellHasNoComponents(Well well) {
		return Objects.isNull(well.getComponents());
	}

}
