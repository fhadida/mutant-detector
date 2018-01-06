package com.meli.exams.mutants.dao;

import java.util.List;

import com.google.cloud.datastore.Entity;
import com.meli.exams.mutants.model.Dna;

public interface DnaDao {

	Entity save(Dna dna);
	
	List<Dna> getAll();
	
}
