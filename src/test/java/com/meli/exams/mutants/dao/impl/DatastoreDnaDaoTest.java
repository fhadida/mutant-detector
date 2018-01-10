package com.meli.exams.mutants.dao.impl;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.meli.exams.mutants.helpers.DnaTestHelper;
import com.meli.exams.mutants.model.Dna;

public class DatastoreDnaDaoTest {
	
	private final LocalServiceTestHelper helper =
		      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	@InjectMocks
	private DatastoreDnaDao dao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		helper.setUp();
		dao.setDatastore(DatastoreServiceFactory.getDatastoreService());
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void testSave() {
		assertEquals(0, dao.total());
		String[] mutantRawDNA = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Dna mutantDna1 = new Dna(mutantRawDNA, true);
		dao.save(mutantDna1);
		assertEquals(1, dao.total());
	}

	@Test
	public void testSaveSameDnaTwice() {
		assertEquals(0, dao.total());
		
		String[] mutantRawDNA = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Dna mutantDna1 = new Dna(mutantRawDNA, true);
		dao.save(mutantDna1);
		
		Dna mutantDna2 = new Dna(mutantRawDNA, true);
		dao.save(mutantDna2);
		
		assertEquals(1, dao.total());
	}
	
	@Test
	public void testSave2Dnas() {
		assertEquals(0, dao.total());
		
		String[] mutantRawDNA = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Dna mutantDna = new Dna(mutantRawDNA, true);
		dao.save(mutantDna);
		
		String[] nonMutantRawDNA = { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG" };
		Dna nonMutantDna = new Dna(nonMutantRawDNA, false);
		dao.save(nonMutantDna);
		
		assertEquals(2, dao.total());
	}
	
	@Test
	public void testSaveMassive() {
		assertEquals(0, dao.total());
		Dna dna = null;
		Random r = new Random();
		int nonMutants = 1500,
			mutants = 60;
		for (int i = 0; i < nonMutants; i++) {
			int size = r.ints(4, 100).findFirst().getAsInt();
			dna = new Dna(DnaTestHelper.generateRandomDNA(size), false);
			dao.save(dna);
		}
		for (int i = 0; i < mutants; i++) {
			int size = r.ints(4, 100).findFirst().getAsInt();
			dna = new Dna(DnaTestHelper.generateRandomDNA(size), true);
			dao.save(dna);
		}
		assertEquals(nonMutants + mutants, dao.total());
		assertEquals(mutants, dao.countMutants());
	}
	
	@Test
	public void testCountMutantsAndTotal() {
		assertEquals(0, dao.total());
		assertEquals(0, dao.countMutants());
		
		String[] mutantRawDNA1 = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Dna mutantDna1 = new Dna(mutantRawDNA1, true);
		dao.save(mutantDna1);
		
		String[] mutantRawDNA2 = { "ATGCGAG", "CAGGTCA", "TTATGTG", "AGTCGGG", "CTGCTAA", "TCACTGG", "TCAATGG" };
		Dna mutantDna2 = new Dna(mutantRawDNA2, true);
		dao.save(mutantDna2);
		
		String[] nonMutantRawDNA1= { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG" };
		Dna nonMutantDna1 = new Dna(nonMutantRawDNA1, false);
		dao.save(nonMutantDna1);
		
		assertEquals(3, dao.total());
		assertEquals(2, dao.countMutants());
	}

}
