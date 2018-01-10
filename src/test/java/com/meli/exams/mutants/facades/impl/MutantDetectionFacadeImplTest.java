package com.meli.exams.mutants.facades.impl;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.meli.exams.mutants.dto.DnaDto;
import com.meli.exams.mutants.dto.StatsDto;
import com.meli.exams.mutants.model.Dna;
import com.meli.exams.mutants.model.MutantStats;
import com.meli.exams.mutants.services.MutantDetectionService;

public class MutantDetectionFacadeImplTest {

	@Mock
	MutantDetectionService serviceMock;
	
	@InjectMocks
	private MutantDetectionFacadeImpl facade;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsMutantForMutant() {
		String[] rawDna = { "ATGCGAG", "CAGGTCA", "TTATGTG", "AGTCGGG", "CTGCTAA", "TCACTGG", "TCAATGG" };
		DnaDto dnaDto = new DnaDto(rawDna);
		Dna dna = new Dna(rawDna, true);
		when(serviceMock.isMutant(rawDna)).thenReturn(Boolean.TRUE);
		facade.isMutant(dnaDto);
		verify(serviceMock, times(1)).save(eq(dna));
	}
	
	@Test
	public void testIsMutantForNoMutant() {
		String[] rawDna = { "ATGCGAG", "CAGGTCA", "TTATGTG", "AGTCGGG", "CTGCTAA", "TCACTGG", "TCAATGG" };
		DnaDto dnaDto = new DnaDto(rawDna);
		Dna dna = new Dna(rawDna, false);
		when(serviceMock.isMutant(rawDna)).thenReturn(Boolean.FALSE);
		facade.isMutant(dnaDto);
		verify(serviceMock, times(1)).save(eq(dna));
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = IllegalArgumentException.class)
	public void testIsMutantForInvalidDNA() {
		when(serviceMock.isMutant(any(String[].class))).thenThrow(IllegalArgumentException.class);
		facade.isMutant(new DnaDto());
		verify(serviceMock, never()).save(any(Dna.class));
	}

	@Test
	public void testStats() {
		MutantStats stats = new MutantStats(10, 2);
		when(serviceMock.stats()).thenReturn(stats);
		StatsDto dto = facade.stats();
		
		assertThat(dto.getHumans(), equalTo(8l));
		assertThat(dto.getMutants(), equalTo(2l));
		assertThat(dto.getRatio(), equalTo(2d / 8d));
	}

}
