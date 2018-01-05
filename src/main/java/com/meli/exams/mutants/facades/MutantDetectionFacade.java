package com.meli.exams.mutants.facades;

import com.meli.exams.mutants.dto.DnaDto;

public interface MutantDetectionFacade {
	
	boolean isMutant(DnaDto dnaDto);

}
