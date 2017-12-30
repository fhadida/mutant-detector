package com.meli.exams.mutants.services.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MutantDetectionImplTest {
	
	private MutantDetectionImpl mutantDetection = new MutantDetectionImpl();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsMutantGivenMutantDNA() {
		String[] mutantDNA = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", 
				"CCCCTA", "TCACTG" };
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenHumanDNA() {
		String[] mutantDNA = { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", 
				"GCGTCA", "TCACTG" };
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertFalse(result);
	}

}
