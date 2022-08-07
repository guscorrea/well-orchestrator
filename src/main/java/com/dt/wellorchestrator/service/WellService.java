package com.dt.wellorchestrator.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.wellorchestrator.exception.WellNotFoundException;
import com.dt.wellorchestrator.model.WellRequest;
import com.dt.wellorchestrator.persistence.WellRepository;
import com.dt.wellorchestrator.persistence.entity.Well;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WellService {

	private final WellRepository wellRepository;

	@Autowired
	public WellService(WellRepository wellRepository) {
		this.wellRepository = wellRepository;
	}

	public Well getWell(UUID id) {
		Well well = wellRepository.findById(id);
		if (Objects.isNull(well)) {
			log.error("Well with id {] not found in the DB.", id);
			throw new WellNotFoundException("Well with id " + id.toString() + " not found in the database.");
		}
		return well;
	}

	public List<Well> getAllWells() {
		return wellRepository.findAll();
	}

	public Well saveWell(WellRequest wellRequest) {
		log.info("Creating a well with name {}", wellRequest.getName());
		Well well = Well.builder()
				.wellId(UUID.randomUUID())
				.name(wellRequest.getName())
				.wellInfo(wellRequest.getWellInfo())
				.components(new HashMap<>())
				.creationDateTime(LocalDateTime.now())
				.build();
		return wellRepository.save(well);
	}

	public Well updateWell(UUID id, WellRequest wellRequest) {
		Well well = getWell(id);
		log.info("Updating well with id {}", id);
		well.setName(wellRequest.getName());
		well.setWellInfo(wellRequest.getWellInfo());
		return wellRepository.save(well);
	}

	public void deleteWell(UUID id) {
		log.info("Deleting well with id {}", id);
		wellRepository.delete(id);
	}

}
