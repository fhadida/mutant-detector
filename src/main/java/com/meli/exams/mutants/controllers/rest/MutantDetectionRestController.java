package com.meli.exams.mutants.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meli.exams.mutants.dto.DnaDto;
import com.meli.exams.mutants.dto.StatsDto;
import com.meli.exams.mutants.facades.MutantDetectionFacade;

@RestController
public class MutantDetectionRestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MutantDetectionRestController.class);

	private MutantDetectionFacade mutantDetectionFacade;
	
	
	@RequestMapping(method=RequestMethod.POST, value="/mutant")
	public ResponseEntity<?> mutant(@RequestBody DnaDto dnaDto) {
		if (getMutantDetectionFacade().isMutant(dnaDto)) {
			return ResponseEntity.ok().build(); 
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}		
	}
	
	@RequestMapping(value="/stats")
	public StatsDto stats() {
		return getMutantDetectionFacade().stats();
	}
	
	@ExceptionHandler(value=IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
		LOG.error(e.getLocalizedMessage(), e);
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	public MutantDetectionFacade getMutantDetectionFacade() {
		return mutantDetectionFacade;
	}

	@Autowired
	public void setMutantDetectionFacade(MutantDetectionFacade mutantDetectionFacade) {
		this.mutantDetectionFacade = mutantDetectionFacade;
	}
	
	

	
}
