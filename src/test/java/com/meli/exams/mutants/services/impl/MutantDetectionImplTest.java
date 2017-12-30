package com.meli.exams.mutants.services.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public void testIsMutantGivenNonMutantDNA1() {
		String[] mutantDNA = { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", 
				"GCGTCA", "TCACTG" };
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
	
//	@Test
	public void testRegex() {
		final String regex = "(A{4,}|T{4,}|C{4,}|G{4,})";
		final String string = "CAAAATGGGGTAAAATT";

		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(string);

		while (matcher.find()) {
		    System.out.println("Full match: " + matcher.group(0));
		    for (int i = 1; i <= matcher.groupCount(); i++) {
		        System.out.println("Group " + i + ": " + matcher.group(i));
		    }
		}
	}

}
