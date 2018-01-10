package com.meli.exams.mutants.dao;

import com.meli.exams.mutants.model.Dna;

public interface DnaDao {

	Long save(Dna dna);
	
	long countMutants();
	
	long total();
	
}
