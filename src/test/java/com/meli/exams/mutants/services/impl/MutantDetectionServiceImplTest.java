package com.meli.exams.mutants.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.meli.exams.mutants.dao.DnaDao;
import com.meli.exams.mutants.model.Dna;
import com.meli.exams.mutants.model.MutantStats;

public class MutantDetectionServiceImplTest {
	
	@Mock
	DnaDao dnaDaoMock;
	
	@InjectMocks
	private MutantDetectionServiceImpl mutantDetectionService;

	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testIsMutantGivenMutantDNA1() {
		String[] mutantDNA = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", 
				"CCCCTA", "TCACTG" };
		boolean result = mutantDetectionService.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenMutantDNA2() {
		String[] mutantDNA = { "ATTTTA", "CAGGGG", "AAAAGT", "AGAAGG", 
				"CCCCTA", "TCACTG" };
		boolean result = mutantDetectionService.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenMutantDNA3() {
		String[] mutantDNA = { "ATTTTAGGGG", "CAGGAGGCCA", "ATGAATTAGT", "ACCGACTAGG", 
				"CTTCGGCCTA", "TCACTGAATT", "CTTCGGCCTA", "TCACTGAATT", "CTTCGGCCTA", "TCACTGAATT" };
		boolean result = mutantDetectionService.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenMutantDNA4() {
		String[] mutantDNA = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", 
				"CTGCTA", "TCACTG" };
		boolean result = mutantDetectionService.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenMutantDNA5() {
		String[] mutantDNA = { "ATGCGA", "CAGGCC", "TTATGT", 
				"AGAAGG", "CTGCTA", "TCACTG" };
		boolean result = mutantDetectionService.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenMutantDNA6() {
		String[] mutantDNA = { "ATGCGA", "CAGGTC", "TTATGT", 
				"AGTCGG", "CTGCTA", "TCACTG" };
		boolean result = mutantDetectionService.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenMutantDNA7() {
		String[] mutantDNA = { "ATGCGAG", "CAGGTCA", "TTATGTG", 
				"AGTCGGG", "CTGCTAA", "TCACTGG", "TCAATGG"
			};
		boolean result = mutantDetectionService.isMutant(mutantDNA);
		assertTrue(result);
	}
	
	@Test
	public void testIsMutantGivenNonMutantDNA1() {
		String[] mutantDNA = { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", 
				"GCGTCA", "TCACTG" };
		boolean result = mutantDetectionService.isMutant(mutantDNA);
		assertFalse(result);
	}
	
	@Test
	public void testIsMutantGivenNonMutantDNA2() {
		String[] mutantDNA = { "ATG", "CAG", "TTA" };
		boolean result = mutantDetectionService.isMutant(mutantDNA);
		assertFalse(result);
	}
	
	@Test
	public void testIsMutantGivenNonMutantDNA3() {
		String[] mutantDNA = { "A" };
		boolean result = mutantDetectionService.isMutant(mutantDNA);
		assertFalse(result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIsMutantGivenInvalidDNA1() {
		String[] mutantDNA = { "ATTTTAGGGG", "CAGGAGGCCAT", "ATGAATTAGT", "ACCGACTAGG", 
				"CTTCGGCCTA", "TCACTGAATT", "CTTCGGC", "TCACTGAATT", "CTTCGGCCTA", "TCACTGAATT" };
		mutantDetectionService.isMutant(mutantDNA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIsMutantGivenInvalidDNA2() {
		String[] mutantDNA = { "ATTTTAGGGG", "CAGGAGGCCAT", "ATGAATTAGT", "ACCGACTAGG", 
				"CTTCGGCCTA", "TCACTGAATT", "CTTCGGC", "TCACTGAATT" };
		mutantDetectionService.isMutant(mutantDNA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIsMutantGivenInvalidDNA3() {
		String[] mutantDNA = { "ATTTT", "CARGT", "ATWAA", 
				"CTTCG", "TCACT" };
		mutantDetectionService.isMutant(mutantDNA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIsMutantGivenInvalidDNA4() {
		String[] mutantDNA = null;
		boolean result = mutantDetectionService.isMutant(mutantDNA);
		assertFalse(result);
	}

	@Test
	public void testSave() {
		final Long id = 1l;
		when(dnaDaoMock.save(any(Dna.class))).thenReturn(id);
		final Dna dna = new Dna();
		mutantDetectionService.save(dna);
		verify(dnaDaoMock, times(1)).save(any(Dna.class));
	}
	
	@Test
	public void testStatsMixedDnas() {
		String[] dummyRawDna = new String[1];
		List<Dna> dummyDnaList = Arrays.asList(
					new Dna(dummyRawDna, true),
					new Dna(dummyRawDna, true),
					new Dna(dummyRawDna, false),
					new Dna(dummyRawDna, false),
					new Dna(dummyRawDna, false),
					new Dna(dummyRawDna, false)
				);
		when(dnaDaoMock.getAll()).thenReturn(dummyDnaList);
		MutantStats mutantStats = mutantDetectionService.stats();
		
		assertEquals(4, mutantStats.getHumans());
		assertEquals(2, mutantStats.getMutants());
		assertEquals(6, mutantStats.getTotal());
		assertThat(mutantStats.getRatio(), equalTo(0.5d));
	}
	
	@Test
	public void testStatsNoMutantsDna() {
		String[] dummyRawDna = new String[1];
		List<Dna> dummyDnaList = Arrays.asList(
					new Dna(dummyRawDna, false),
					new Dna(dummyRawDna, false),
					new Dna(dummyRawDna, false),
					new Dna(dummyRawDna, false),
					new Dna(dummyRawDna, false),
					new Dna(dummyRawDna, false)
				);
		when(dnaDaoMock.getAll()).thenReturn(dummyDnaList);
		MutantStats mutantStats = mutantDetectionService.stats();
		
		assertEquals(6, mutantStats.getHumans());
		assertEquals(0, mutantStats.getMutants());
		assertEquals(6, mutantStats.getTotal());
		assertThat(mutantStats.getRatio(), equalTo(0d));
	}
	
	@Test
	public void testStatsNoHumansDna() {
		String[] dummyRawDna = new String[1];
		List<Dna> dummyDnaList = Arrays.asList(
					new Dna(dummyRawDna, true),
					new Dna(dummyRawDna, true),
					new Dna(dummyRawDna, true),
					new Dna(dummyRawDna, true),
					new Dna(dummyRawDna, true),
					new Dna(dummyRawDna, true)
				);
		when(dnaDaoMock.getAll()).thenReturn(dummyDnaList);
		MutantStats mutantStats = mutantDetectionService.stats();
		
		assertEquals(0, mutantStats.getHumans());
		assertEquals(6, mutantStats.getMutants());
		assertEquals(6, mutantStats.getTotal());
		assertThat(mutantStats.getRatio(), equalTo(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public void testStatsNoDnas() {
		List<Dna> dummyDnaList = Lists.emptyList();
		when(dnaDaoMock.getAll()).thenReturn(dummyDnaList);
		MutantStats mutantStats = mutantDetectionService.stats();
		
		assertEquals(0, mutantStats.getHumans());
		assertEquals(0, mutantStats.getMutants());
		assertEquals(0, mutantStats.getTotal());
		assertThat(mutantStats.getRatio(), equalTo(0d));
	}
}
