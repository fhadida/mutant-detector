package com.meli.exams.mutants.facades;

import com.meli.exams.mutants.dto.DnaDto;
import com.meli.exams.mutants.dto.StatsDto;

public interface MutantDetectionFacade {
	
	boolean isMutant(DnaDto dnaDto);

	StatsDto stats();

}
