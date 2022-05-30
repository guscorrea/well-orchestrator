package com.dt.wellorchestrator.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.wellorchestrator.model.WellRequest;
import com.dt.wellorchestrator.persistence.WellRepository;
import com.dt.wellorchestrator.persistence.entity.Well;

@Service
public class WellService {

	@Autowired
	private WellRepository wellRepository;

	public Well saveWell(WellRequest wellRequest) {
		System.out.println("Creating a well with name " + wellRequest.getName());
		Well well = new Well(UUID.randomUUID(), wellRequest.getName(), wellRequest.getWellInfo(), LocalDateTime.now());
		return wellRepository.save(well);
	}

	public List<Well> getAllWells() {
		return wellRepository.findAll();
	}

}
