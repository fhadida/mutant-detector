package com.meli.exams.mutants.services;

import com.meli.exams.mutants.model.Dna;
import com.meli.exams.mutants.model.MutantStats;

public interface MutantDetectionService {
	
	boolean isMutant(String[] dna);

	void save(Dna dna);

	MutantStats stats();

}
