package com.meli.exams.mutants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meli.exams.mutants.dto.MutantReqDTO;
import com.meli.exams.mutants.services.MutantDetectionService;

@RestController
public class MutantDetectionController {

	private MutantDetectionService mutantDetectionService;
	
	@RequestMapping(method=RequestMethod.POST, value="/mutant")
	public ResponseEntity<?> mutant(@RequestBody MutantReqDTO req) {
		if (mutantDetectionService.isMutant(req.getDna())) {
			return new ResponseEntity<>(HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}		
	}

	public MutantDetectionService getMutantDetectorService() {
		return mutantDetectionService;
	}

	@Autowired
	public void setMutantDetectorService(MutantDetectionService mutantDetectorService) {
		this.mutantDetectionService = mutantDetectorService;
	}
	
}
