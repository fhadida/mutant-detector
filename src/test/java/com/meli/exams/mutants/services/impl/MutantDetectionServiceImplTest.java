package com.meli.exams.mutants.services.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MutantDetectionServiceImplTest {
	
	private MutantDetectionServiceImpl mutantDetection = new MutantDetectionServiceImpl();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsMutantGivenMutantDNA1() {
		String[] mutantDNA = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", 
				"CCCCTA", "TCACTG" };
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenMutantDNA2() {
		String[] mutantDNA = { "ATTTTA", "CAGGGG", "AAAAGT", "AGAAGG", 
				"CCCCTA", "TCACTG" };
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenMutantDNA3() {
		String[] mutantDNA = { "ATTTTAGGGG", "CAGGAGGCCA", "ATGAATTAGT", "ACCGACTAGG", 
				"CTTCGGCCTA", "TCACTGAATT", "CTTCGGCCTA", "TCACTGAATT", "CTTCGGCCTA", "TCACTGAATT" };
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenMutantDNA4() {
		String[] mutantDNA = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", 
				"CTGCTA", "TCACTG" };
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenMutantDNA5() {
		String[] mutantDNA = { "ATGCGA", "CAGGCC", "TTATGT", 
				"AGAAGG", "CTGCTA", "TCACTG" };
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenMutantDNA6() {
		String[] mutantDNA = { "ATGCGA", "CAGGTC", "TTATGT", 
				"AGTCGG", "CTGCTA", "TCACTG" };
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenMutantDNA7() {
		String[] mutantDNA = { "ATGCGAG", "CAGGTCA", "TTATGTG", 
				"AGTCGGG", "CTGCTAA", "TCACTGG", "TCAATGG"
			};
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenNonMutantDNA1() {
		String[] mutantDNA = { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", 
				"GCGTCA", "TCACTG" };
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertFalse(result);
	}
	
	@Test
	public void testIsMutantGivenNonMutantDNA2() {
		String[] mutantDNA = { "ATG", "CAG", "TTA" };
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertFalse(result);
	}
	
	@Test
	public void testIsMutantGivenNonMutantDNA3() {
		String[] mutantDNA = { "A" };
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertFalse(result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIsMutantGivenInvalidDNA1() {
		String[] mutantDNA = { "ATTTTAGGGG", "CAGGAGGCCAT", "ATGAATTAGT", "ACCGACTAGG", 
				"CTTCGGCCTA", "TCACTGAATT", "CTTCGGC", "TCACTGAATT", "CTTCGGCCTA", "TCACTGAATT" };
		mutantDetection.isMutant(mutantDNA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIsMutantGivenInvalidDNA2() {
		String[] mutantDNA = { "ATTTTAGGGG", "CAGGAGGCCAT", "ATGAATTAGT", "ACCGACTAGG", 
				"CTTCGGCCTA", "TCACTGAATT", "CTTCGGC", "TCACTGAATT" };
		mutantDetection.isMutant(mutantDNA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIsMutantGivenInvalidDNA3() {
		String[] mutantDNA = { "ATTTT", "CARGT", "ATWAA", 
				"CTTCG", "TCACT" };
		mutantDetection.isMutant(mutantDNA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIsMutantGivenInvalidDNA4() {
		String[] mutantDNA = null;
		boolean result = mutantDetection.isMutant(mutantDNA);
		assertFalse(result);
	}

}
