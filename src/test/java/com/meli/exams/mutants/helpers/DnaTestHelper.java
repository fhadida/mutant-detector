package com.meli.exams.mutants.helpers;

import org.apache.commons.text.RandomStringGenerator;

public class DnaTestHelper {
	
	public static String[] generateRandomDNA(int size) {
		String[] dna = new String[size];
		RandomStringGenerator rsg = new RandomStringGenerator.Builder()
				.selectFrom('A', 'C', 'G', 'T')
				.build();
		for (int i = 0; i < size; i++) {
			dna[i] = rsg.generate(size);
		}
		return dna;
	}

}
