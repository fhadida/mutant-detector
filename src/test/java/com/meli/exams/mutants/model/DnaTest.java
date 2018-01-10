package com.meli.exams.mutants.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.Objects;

import org.junit.Test;

public class DnaTest {
	
	private String[] rawDna1 = { "ATGCGAG", "CAGGTCA", "TTATGTG", "AGTCGGG", "CTGCTAA", "TCACTGG", "TCAATGG" };
	private String[] rawDna2 = { "CAGGTC", "TTATTG", "AGCGGG", "CTGCAA", "TCACTG", "TCATGG" };
	
	
	@Test
	public void testHashCode() {
		
		Dna dna1 = new Dna(rawDna1, true);
		Dna dna2 = new Dna(rawDna1, false);
		Dna dna3 = new Dna(rawDna2, true);
		
		assertEquals(Objects.hash(Arrays.toString(rawDna1)), dna1.hashCode());
		assertNotEquals(dna3.hashCode(), dna1.hashCode());
		assertEquals(dna1.hashCode(), dna2.hashCode());
		assertNotEquals(dna3.hashCode(), dna2.hashCode());
	}

	@Test
	public void testEqualsObject() {
		Dna dna1 = new Dna(rawDna1, true);
		Dna dna2 = new Dna(rawDna1, false);
		Dna dna3 = new Dna(rawDna2, true);
		Dna dna4 = null;
		
		assertEquals(dna2, dna1);
		assertNotEquals(dna3, dna1);
		assertNotEquals(dna3, dna2);
		assertNotEquals(dna4, dna1);
		assertNotEquals(dna4, dna2);
		assertNotEquals(dna4, dna3);
	}

}
