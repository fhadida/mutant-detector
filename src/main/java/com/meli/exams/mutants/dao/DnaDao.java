package com.meli.exams.mutants.dao;

import java.util.List;

import com.meli.exams.mutants.model.Dna;

public interface DnaDao {

	Long save(Dna dna);
	
	List<Dna> getAll();
	
}
