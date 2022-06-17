package com.dt.wellorchestrator.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.wellorchestrator.model.WellRequest;
import com.dt.wellorchestrator.persistence.WellRepository;
import com.dt.wellorchestrator.persistence.entity.Well;

@Service
public class WellService {

	private final WellRepository wellRepository;

	@Autowired
	public WellService(WellRepository wellRepository) {
		this.wellRepository = wellRepository;
	}

	public Well getWell(UUID id) {
		return wellRepository.findById(id);
	}

	public List<Well> getAllWells() {
		return wellRepository.findAll();
	}

	public Well saveWell(WellRequest wellRequest) {
		System.out.println("Creating a well with name " + wellRequest.getName());
		Well well = new Well(UUID.randomUUID(), wellRequest.getName(), wellRequest.getWellInfo(), LocalDateTime.now());
		return wellRepository.save(well);
	}

	public Well updateWell(UUID id, WellRequest wellRequest) {
		Well well = getWell(id);
		if (Objects.isNull(well)) {
			//TODO throw exception
		}
		System.out.println("Updating well with id " + id);
		well.setName(wellRequest.getName());
		well.setWellInfo(wellRequest.getWellInfo());
		return wellRepository.save(well);
	}

	public void deleteWell(UUID id) {
		wellRepository.delete(id);
	}

}
