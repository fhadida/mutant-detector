package com.meli.exams.mutants.helpers;

import static org.junit.Assert.*;

import org.junit.Test;

public class DnaHelperTest {

	@Test
	public void testDiagTopLeftBotomRight_4x4() {
		final String[] dna = { "ATGC", "CAGT", "TTAT", "AGAA" };
		final String[] dna_d2 = { "CGTA" };
		assertArrayEquals(dna_d2, DnaHelper.diagTopLeftBotomRight(dna, 4));
	}
	
	@Test
	public void testDiagTopLeftBotomRight_5x5() {
		final String[] dna = { "ATGCG", "CAGTG", "TTATG", "AGAAG", "CCCCT" };
		final String[] dna_d2 = { "CGTA", "GTAGC", "GTAC" };
		assertArrayEquals(dna_d2, DnaHelper.diagTopLeftBotomRight(dna, 4));
	}
	
	@Test
	public void testDiagTopLeftBotomRight_6x6() {
		final String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		final String[] dna_d2 = { "CGTA", "GTAGC", "AGTACT", "CGACC", "TGCA" };
		assertArrayEquals(dna_d2, DnaHelper.diagTopLeftBotomRight(dna, 4));
	}
	
	@Test
	public void testDiagTopLeftBotomRight_9x9() {
		final String[] dna = { "ATGCGACCA", "CAGTGCTCA", "TTATGTGGA", "AGAAGGTCT", "CCCCTAACC", "TCACTGTTT", "AGAAGGTCT", "CCCCTAACC", "TCACTGTTT" };
		final String[] dna_d2 = { "CGTA", "GTAGC", "AGTACT", "CCGACCA", "CTTGCAGC", "ACGGTCACT", "AGTATACC", "ACAGGCA", "TCTGTC", "CTTAT", "TCAG" };
		assertArrayEquals(dna_d2, DnaHelper.diagTopLeftBotomRight(dna, 4));
	}

	@Test
	public void testDiagTopRightBotomLeft_4x4() {
		final String[] dna = { "ATGC", "CAGT", "TTAT", "AGAA" };
		final String[] dna_d1 = { "AAAA" };
		assertArrayEquals(dna_d1, DnaHelper.diagTopRightBotomLeft(dna, 4));
	}
	
	@Test
	public void testDiagTopRightBotomLeft_5x5() {
		final String[] dna = { "ATGCG", "CAGTG", "TTATG", "AGAAG", "CCCCT" };
		final String[] dna_d1 = { "TGTG", "AAAAT", "CTAC" };
		assertArrayEquals(dna_d1, DnaHelper.diagTopRightBotomLeft(dna, 4));
	}
	
	@Test
	public void testDiagTopRightBotomLeft_6x6() {
		final String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		final String[] dna_d1 = { "GTGG", "TGTGA", "AAAATG", "CTACT", "TGCC" };
		assertArrayEquals(dna_d1, DnaHelper.diagTopRightBotomLeft(dna, 4));
	}
	
	@Test
	public void testDiagTopRightBotomLeft_9x9() {
		final String[] dna = { "ATGCGACCA", "CAGTGCTCA", "TTATGTGGA", "AGAAGGTCT", "CCCCTAACC", "TCACTGTTT", "AGAAGGTCT", "CCCCTAACC", "TCACTGTTT" };
		final String[] dna_d1 = { "ATGT", "GCGCC", "CGTTCT", "GTGGATT", "TGTGATCC", "AAAATGTCT", "CTACTGAT", "TGCCGAT", "ACAATG", "CCACT", "TGCC" };
		assertArrayEquals(dna_d1, DnaHelper.diagTopRightBotomLeft(dna, 4));
	}

	@Test
	public void testTranspose() {
		final String[] dna = { "ATGC", "CAGT", "TTAT", "AGAA" };
		final String[] dna_t = { "ACTA", "TATG", "GGAA", "CTTA" };
		assertArrayEquals(dna_t, DnaHelper.transpose(dna));
	}

	@Test
	public void testValidateDNAOk() {
		final String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		DnaHelper.validateDNA(dna);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateDNANull() {
		DnaHelper.validateDNA(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateDNA_NxM() {
		final String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA" };
		DnaHelper.validateDNA(dna);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateDNA_MxN() {
		final String[] dna = { "ATGC", "CAGT", "TTAT", "AGAA", "CCCC" };
		DnaHelper.validateDNA(dna);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateDNA_badRow() {
		final String[] dna = { "ATGCG", "CAGTG", "T", "AGAAG", "CCCCT" };
		DnaHelper.validateDNA(dna);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateDNA_invalidCharacter() {
		final String[] dna = { "ATGCG", "CAGQG", "TTATGT", "AGAAG", "CCCCT" };
		DnaHelper.validateDNA(dna);
	}

}
