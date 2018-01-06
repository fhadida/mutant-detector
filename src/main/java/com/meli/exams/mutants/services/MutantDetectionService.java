package com.meli.exams.mutants.services;

import com.google.cloud.datastore.Entity;
import com.meli.exams.mutants.model.Dna;
import com.meli.exams.mutants.model.MutantStats;

public interface MutantDetectionService {
	
	boolean isMutant(String[] dna);

	Entity save(Dna dna);

	MutantStats stats();

}
