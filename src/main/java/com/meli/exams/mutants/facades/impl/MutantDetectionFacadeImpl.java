package com.meli.exams.mutants.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.meli.exams.mutants.dto.DnaDto;
import com.meli.exams.mutants.dto.StatsDto;
import com.meli.exams.mutants.facades.MutantDetectionFacade;
import com.meli.exams.mutants.model.Dna;
import com.meli.exams.mutants.model.MutantStats;
import com.meli.exams.mutants.services.MutantDetectionService;

@Component
public class MutantDetectionFacadeImpl implements MutantDetectionFacade {

	private MutantDetectionService mutantDetectionService; 
	
	public MutantDetectionService getMutantDetectorService() {
		return mutantDetectionService;
	}

	@Autowired
	public void setMutantDetectorService(MutantDetectionService mutantDetectorService) {
		this.mutantDetectionService = mutantDetectorService;
	}
	
	@Override
	public boolean isMutant(DnaDto dnaDto) {
		boolean result = getMutantDetectorService().isMutant(dnaDto.getDna());
		Dna dna = new Dna(dnaDto.getDna(), result);
		getMutantDetectorService().save(dna);
		return result;
	}

	@Override
	public StatsDto stats() {
		final MutantStats stats = getMutantDetectorService().stats();
		return new StatsDto(stats.getHumans(), stats.getMutants(), stats.getRatio());
	}

}
