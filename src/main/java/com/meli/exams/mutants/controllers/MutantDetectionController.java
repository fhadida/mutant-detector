package com.meli.exams.mutants.controllers;

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
import com.meli.exams.mutants.services.MutantDetectionService;

@RestController
public class MutantDetectionController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MutantDetectionController.class);

	private MutantDetectionService mutantDetectionService;
	
	@RequestMapping(method=RequestMethod.POST, value="/mutant")
	public ResponseEntity<?> mutant(@RequestBody DnaDto dnaDto) {
		if (mutantDetectionService.isMutant(dnaDto.getDna())) {
			return new ResponseEntity<>(HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}		
	}
	
	@ExceptionHandler(value=IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
		LOG.error(e.getLocalizedMessage(), e);
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	public MutantDetectionService getMutantDetectorService() {
		return mutantDetectionService;
	}

	@Autowired
	public void setMutantDetectorService(MutantDetectionService mutantDetectorService) {
		this.mutantDetectionService = mutantDetectorService;
	}
	
}
